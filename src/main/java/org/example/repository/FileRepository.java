package org.example.repository;

import java.util.List;
import java.util.function.Predicate;

public interface FileRepository<T> {
    T add(T entity);
    List<T> readAll();
    List<T> readAll(Predicate<T> predicate);
    T read(long id);
    boolean update(T entity);
    boolean delete(Predicate<T> predicate);
    boolean delete(Long id);
    boolean replaceAll(List<T> entities);

}
