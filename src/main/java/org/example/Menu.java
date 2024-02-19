package org.example;

import lombok.SneakyThrows;
import org.example.model.Manufacturer;
import org.example.model.Souvenir;
import org.example.service.ManufacturerService;
import org.example.service.SouvenirService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    private static final String EXIT = "exit";
    private static final String ADD_SOUVENIR = "addSouvenir";
    private static final String ADD_MANUFACTURER = "addManufacturer";
    private static final String FIND_SOUVENIR = "findSouvenir";
    private static final String FIND_MANUFACTURER = "findManufacturer";
    private static final String UPDATE_SOUVENIR = "updateSouvenir";
    private static final String UPDATE_MANUFACTURER = "updateManufacturer";
    private static final String DELETE_SOUVENIR = "deleteSouvenir";
    private static final String DELETE_MANUFACTURER_AND_SOUVENIRS = "deleteManufacturerAndSouvenirs";
    private static final String VIEW_MANUFACTURERS_AND_SOUVENIRS = "viewManufacturersAndSouvenirs";
    private static final String ALL = "all";
    private static final String BY_ID = "byId";
    private static final String BY_YEAR = "byYear";
    private static final String BY_PRICE = "byPrice";
    private static final String BY_COUNTRY = "byCountry";
    private static final String BY_MANUFACTURER_ID = "byManufacturerId";
    private static final Scanner scanner = new Scanner(System.in);
    private static final ManufacturerService manufacturerService = ManufacturerService.getInstance();
    private static final SouvenirService souvenirService = SouvenirService.getInstance();

    public static void run() {
        String action = "";
        printInstructions();
        while (!action.equalsIgnoreCase(EXIT)) {
            action = scanner.nextLine();
            if (action.equalsIgnoreCase(ADD_SOUVENIR)) {
                addSouvenir();
            } else if (action.equalsIgnoreCase(ADD_MANUFACTURER)) {
                addManufacturer();
            } else if (action.equalsIgnoreCase(FIND_SOUVENIR)) {
                findSouvenir();
            } else if (action.equalsIgnoreCase(FIND_MANUFACTURER)) {
                findManufacturer();
            } else if (action.equalsIgnoreCase(UPDATE_SOUVENIR)) {
                updateSouvenir();
            } else if (action.equalsIgnoreCase(UPDATE_MANUFACTURER)) {
                updateManufacturer();
            } else if (action.equalsIgnoreCase(DELETE_SOUVENIR)) {
                deleteSouvenir();
            } else if (action.equalsIgnoreCase(DELETE_MANUFACTURER_AND_SOUVENIRS)) {
                deleteManufacturerAndSouvenirs();
            } else if (action.equalsIgnoreCase(VIEW_MANUFACTURERS_AND_SOUVENIRS)) {
                printManufacturerAndSouvenirs();
            } else if (action.equalsIgnoreCase(EXIT)) {
                break;
            }
            printInstructions();
        }
        scanner.close();
    }

    private static void printInstructions() {
        System.out.println("\nTo close the program, enter " + EXIT);
        System.out.println("To add a new souvenir, enter " + ADD_SOUVENIR);
        System.out.println("To add a new manufacturer, enter " + ADD_MANUFACTURER);
        System.out.println("To view all manufacturers and all their souvenirs, enter " + VIEW_MANUFACTURERS_AND_SOUVENIRS);
        System.out.println("To find souvenirs, print " + FIND_SOUVENIR);
        System.out.println("To find manufacturers, print " + FIND_MANUFACTURER);
        System.out.println("To update a souvenir, print " + UPDATE_SOUVENIR);
        System.out.println("To update a manufacturer, print " + UPDATE_MANUFACTURER);
        System.out.println("To delete a souvenir, print " + DELETE_SOUVENIR);
        System.out.println("To delete a manufacturer and corresponding souvenirs, print " + DELETE_MANUFACTURER_AND_SOUVENIRS);
    }

    @SneakyThrows
    private static void addSouvenir() {
        System.out.println("You are about to add a new souvenir!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            try {
                Souvenir souvenir = new Souvenir();
                System.out.println("Enter name: ");
                souvenir.setName(scanner.nextLine());
                System.out.println("Enter manufacturing date in ISO 8601 format YYYY-MM-DD (for example, 2024-02-13): ");
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    souvenir.setManufacturingDate(dateFormat.parse(scanner.nextLine()));
                } catch (ParseException e) {
                    System.out.println("Error: " + e.getMessage());
                    break;
                }
                System.out.println("Enter price(only digits and decimal point are acceptable!): ");
                souvenir.setPrice(Double.parseDouble(scanner.nextLine()));
                System.out.println("Enter manufacturer id: ");
                Long manufacturerId = Long.parseLong(scanner.nextLine());
                if (manufacturerService.read(manufacturerId) == null) {
                    System.out.println("Manufacturer with this id does not exist!");
                    break;
                }
                souvenir.setManufacturerId(manufacturerId);
                System.out.println("Souvenir is being added...");
                Souvenir persistedSouvenir = souvenirService.save(souvenir);
                if (persistedSouvenir != null) {
                    System.out.println("Souvenir has been added to the file!");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish adding new souvenirs or anything else to proceed with adding");
            nextAction = scanner.nextLine();
        }
    }

    private static void addManufacturer() {
        System.out.println("You are about to add a new manufacturer!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            try {
                Manufacturer manufacturer = new Manufacturer();
                System.out.println("Enter name: ");
                manufacturer.setName(scanner.nextLine());
                System.out.println("Enter country: ");
                manufacturer.setCountry(scanner.nextLine());
                System.out.println("Manufacturer is being added...");
                Manufacturer persistedManufacturer = manufacturerService.save(manufacturer);
                if (persistedManufacturer != null) {
                    System.out.println("Manufacturer has been added to the file!");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish adding new manufacturers or anything else to proceed with adding");
            nextAction = scanner.nextLine();
        }
    }

    private static void deleteSouvenir() {
        System.out.println("You are about to delete a souvenir!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            System.out.println("Enter id of the souvenir you want to delete: ");
            try {
                Long id = Long.parseLong(scanner.nextLine());
                boolean removed = souvenirService.delete(id);
                if (removed) {
                    System.out.println("Souvenir has been successfully deleted");
                } else {
                    System.out.println("It was not possible to delete souvenir with id: " + id);
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish deleting souvenirs or anything else to proceed with deleting");
            nextAction = scanner.nextLine();
        }
    }

    private static void deleteManufacturerAndSouvenirs() {
        System.out.println("You are about to delete a manufacturer and corresponding souvenirs!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            System.out.println("Enter id of the manufacturer you want to delete: ");
            try {
                long id = Long.parseLong(scanner.nextLine());
                boolean removed = manufacturerService.deleteManufacturerAndSouvenirs(id);
                if (removed) {
                    System.out.println("Manufacturer and corresponding souvenirs have been successfully deleted");
                } else {
                    System.out.println("It was not possible to delete manufacturer with id: " + id + " and corresponding souvenirs");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish deleting manufacturers or anything else to proceed with deleting");
            nextAction = scanner.nextLine();
        }
    }

    @SneakyThrows
    private static void updateSouvenir() {
        System.out.println("You are about to update a souvenir!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            try {
                System.out.println("Enter id of the souvenir you want to update: ");
                Long id = Long.parseLong(scanner.nextLine());
                Souvenir souvenir = souvenirService.read(id);
                if (souvenir == null) {
                    System.out.println("There is no souvenir with id " + id);
                    break;
                }
                System.out.println("Enter a new name or skip if you want to leave the same name: ");
                String name = scanner.nextLine();
                souvenir.setName(name.isBlank() ? souvenir.getName() : name);
                System.out.println("Enter a new price or skip if you want to leave the same price: ");
                String price = scanner.nextLine();
                souvenir.setPrice(price.isBlank() ? souvenir.getPrice() : Double.parseDouble(price));
                System.out.println("Enter a new manufacturing date in ISO 8601 format YYYY-MM-DD (for example, 2024-02-13) or skip if you want to leave the same date: ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = scanner.nextLine();
                souvenir.setManufacturingDate(date.isBlank() ? souvenir.getManufacturingDate() : dateFormat.parse(date));
                System.out.println("Enter manufacturer id: ");
                String newManufacturerId = scanner.nextLine();
                Long manufacturerId = newManufacturerId.isBlank() ? souvenir.getManufacturerId() : Long.parseLong(newManufacturerId);
                if (manufacturerService.read(manufacturerId) == null) {
                    System.out.println("Manufacturer with this id does not exist!");
                    break;
                }
                souvenir.setManufacturerId(manufacturerId);
                System.out.println("Souvenir is being updated...");
                if (souvenirService.update(souvenir)) {
                    System.out.println("Souvenir has been successfully updated!");
                } else {
                    System.out.println("It was not possible to update the souvenir with id " + id);
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish updating souvenirs or anything else to proceed with updating");
            nextAction = scanner.nextLine();
        }
    }

    private static void updateManufacturer() {
        System.out.println("You are about to update a manufacturer!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            try {
                System.out.println("Enter id of the manufacturer you want to update: ");
                Long id = Long.parseLong(scanner.nextLine());
                Manufacturer manufacturer = manufacturerService.read(id);
                if (manufacturer == null) {
                    System.out.println("There is no manufacturer with id " + id);
                    break;
                }
                System.out.println("Enter a new name or skip if you want to leave the same name: ");
                String name = scanner.nextLine();
                manufacturer.setName(name.isBlank() ? manufacturer.getName() : name);
                System.out.println("Enter a new country or skip if you want to leave the same country: ");
                String country = scanner.nextLine();
                manufacturer.setCountry(country.isBlank() ? manufacturer.getCountry() : country);
                System.out.println("Manufacturer is being updated...");
                if (manufacturerService.update(manufacturer)) {
                    System.out.println("Manufacturer has been successfully updated!");
                } else {
                    System.out.println("It was not possible to update the manufacturer with id " + id);
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Enter " + EXIT + " to finish updating manufacturers or anything else to proceed with updating");
            nextAction = scanner.nextLine();
        }
    }

    private static void findManufacturer() {
        System.out.println("Your are about to view manufacturer(s)!");
        String nextAction = "";
        label:
        while (!nextAction.equals(EXIT)) {
            System.out.println("To view all manufacturers enter: " + ALL);
            System.out.println("To view manufacturer with specific id, print: " + BY_ID);
            System.out.println("To view all manufacturers whose souvenirs were produced in specific year, enter: " + BY_YEAR);
            System.out.println("To view all manufacturers which have souvenirs with the prices below the desired one, enter: " + BY_PRICE);
            String searchType = scanner.nextLine();
            switch (searchType) {
                case ALL:
                    manufacturerService.readAll().forEach(System.out::println);
                    break;
                case BY_ID:
                    System.out.println("Enter manufacturer id: ");
                    try {
                        long id = Long.parseLong(scanner.nextLine());
                        System.out.println(manufacturerService.read(id));
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case BY_YEAR:
                    System.out.println("Enter year of manufacturing: ");
                    try {
                        int year = Integer.parseInt(scanner.nextLine());
                        manufacturerService.readManufacturersWhereSouvenirsOfYear(year).forEach(System.out::println);
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case BY_PRICE:
                    System.out.println("Enter price: ");
                    try {
                        double price = Double.parseDouble(scanner.nextLine());
                        manufacturerService.readManufacturersWithPricesLessThan(price).forEach(System.out::println);
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case EXIT:
                    break label;
            }
            System.out.println("Enter " + EXIT + " to finish viewing manufacturers or anything else to proceed with viewing");
            nextAction = scanner.nextLine();
        }
    }

    private static void findSouvenir() {
        System.out.println("You are about to view souvenir(s)!");
        String nextAction = "";
        while (!nextAction.equals(EXIT)) {
            System.out.println("To view all souvenirs, enter: " + ALL);
            System.out.println("To view souvenir with specific id, enter: " + BY_ID);
            System.out.println("To view all souvenirs manufactured in specific year, enter: " + BY_YEAR);
            System.out.println("To view all souvenirs manufactured in specific country, enter: " + BY_COUNTRY);
            System.out.println("To view all souvenirs produced by manufacturer with specific id, enter: " + BY_MANUFACTURER_ID);
            String findType = scanner.nextLine();
            switch (findType) {
                case ALL -> souvenirService.readAll().forEach(System.out::println);
                case BY_ID -> {
                    System.out.println("Enter id: ");
                    try {
                        long id = Long.parseLong(scanner.nextLine());
                        System.out.println(souvenirService.read(id));
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case BY_YEAR -> {
                    System.out.println("Enter year: ");
                    try {
                        int year = Integer.parseInt(scanner.nextLine());
                        souvenirService.readAllByYear(year).forEach(System.out::println);
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case BY_COUNTRY -> {
                    System.out.println("Enter country: ");
                    String country = scanner.nextLine();
                    souvenirService.readAllByCountry(country).forEach(System.out::println);
                }
                case BY_MANUFACTURER_ID -> {
                    System.out.println("Enter manufacturer id: ");
                    try {
                        long manufacturerId = Long.parseLong(scanner.nextLine());
                        souvenirService.readAllByManufacturerId(manufacturerId).forEach(System.out::println);
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }
            System.out.println("Enter " + EXIT + " to finish viewing Souvenirs or anything else to proceed with viewing");
            nextAction = scanner.nextLine();
        }
    }

    private static void printManufacturerAndSouvenirs() {
        Map<Manufacturer, List<Souvenir>> manufacturerAndSouvenirs =
                manufacturerService.readAll()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        m -> m,
                                        m -> souvenirService.readAllByManufacturerId(m.getId())
                                )
                        );
        for (Map.Entry<Manufacturer, List<Souvenir>> entry : manufacturerAndSouvenirs.entrySet()) {
            System.out.println("\t" + entry.getKey() + ": ");
            entry.getValue().forEach(souvenir -> System.out.println("\t\t"+ souvenir));
            System.out.println();
        }
    }
}
