package org.example.repository;

import org.example.model.Entity;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractFileRepository<T extends Entity> implements FileRepository<T> {

    abstract String getFilePath();

    abstract long generateUniqueId();

    abstract T fromString(String line);

    abstract String toString(T entity);

    String filePropertiesHeader() {
        return null;
    }

    @Override
    public List<T> readAll() {
        return entityList();
    }

    @Override
    public List<T> readAll(Predicate<T> predicate) {
        return entityList()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public T read(long id) {
        List<T> entityList = readAll(s -> s.getId() == id);
        if (entityList.isEmpty()) {
            return null;
        }
        return entityList.getFirst();
    }

    @Override
    public T add(T entity) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath(), true));
             BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            long id = generateUniqueId();
            prepareFile(reader, writer);
            entity.setId(id);
            writer.print(toString(entity));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public boolean update(T entity) {
        Long id = entity.getId();
        if (id == null || isIdNotInFile(id)) {
            return false;
        }
        List<T> entities = readAll().stream()
                .map(e -> {
                    if (Objects.equals(e.getId(), id)) {
                        return entity;
                    } return e;
                })
                .toList();
        return replaceAll(entities);
    }

    @Override
    public boolean delete(Predicate<T> predicate) {
        List<T> entities = readAll().stream()
                .filter(predicate.negate())
                .toList();
        return replaceAll(entities);
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || isIdNotInFile(id)) {
            return false;
        }
        return delete(entity -> Objects.equals(entity.getId(), id));
    }

    @Override
    public boolean replaceAll(List<T> entities) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            if (filePropertiesHeader() != null) {
                writer.println(filePropertiesHeader());
            }
            for (T entity : entities) {
                writer.println(toString(entity));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    List<T> entityList() {
        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(getFilePath()), StandardCharsets.UTF_8)) {
            return reader.lines()
                    .skip(filePropertiesHeader() == null ? 0 : 1)
                    .filter(s -> !s.isBlank())
                    .map(this::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isIdNotInFile(long id) {
        return readAll(s -> s.getId() == id).isEmpty();
    }

    private void prepareFile(BufferedReader reader, PrintWriter writer) throws IOException {
        List<String> lines = reader.lines().toList();
        if (lines.isEmpty() && filePropertiesHeader() != null) {
            writer.println(filePropertiesHeader());
        }
        String lastLine = getLastLine(lines);
        if (!lastLine.isBlank()) {
            writer.println();
        }
    }

    private String getLastLine(List<String> lines) {
        if (lines.isEmpty()) {
            return "";
        }
        int lastIdx = lines.size() - 1;
        return lines.get(lastIdx);
    }
}
