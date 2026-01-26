package com.example.findbuddy.console;

import com.example.findbuddy.model.User;
import com.example.findbuddy.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final AuthService authService;
    private final InterestService interestService;
    private final UserService userService;
    private final SearchService searchService;
    private final AvailabilityService availabilityService;

    private final Scanner scanner = new Scanner(System.in);
    private Long currentUserId;

    public ConsoleApp(AuthService authService,
                      InterestService interestService,
                      UserService userService,
                      SearchService searchService,
                      AvailabilityService availabilityService) {
        this.authService = authService;
        this.interestService = interestService;
        this.userService = userService;
        this.searchService = searchService;
        this.availabilityService = availabilityService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            if (currentUserId == null) showAuthMenu();
            else showUserMenu();
        }
    }

    private void showAuthMenu() {
        System.out.println("\n===FindBuddy v1.0===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> register();
            case "2" -> login();
            case "3" -> System.exit(0);
            default -> System.out.println("Invalid option");
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("City: ");
        String city = scanner.nextLine().trim();

        authService.register(username, password, city);
        System.out.println("Registration successful");
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Optional<User> user = authService.login(username, password);
        if (user.isPresent()) {
            currentUserId = user.get().getId();
            System.out.println("Welcome, " + user.get().getUsername());
        } else {
            System.out.println("Invalid credentials");
        }
    }

    private void showUserMenu() {
        System.out.println("\n=== User Menu ===");
        System.out.println("1. Show profile");
        System.out.println("2. Add interest");
        System.out.println("3. Find people by interest");
        System.out.println("4. Find people by multiple interests (match %)");
        System.out.println("5. Add availability");
        System.out.println("6. Show my availability");
        System.out.println("7. Find people by interest + availability");
        System.out.println("8. Logout");
        System.out.print("> ");

        switch (scanner.nextLine().trim()) {
            case "1" -> showProfile();
            case "2" -> addInterest();
            case "3" -> findPeopleByInterest();
            case "4" -> findPeopleByMultipleInterests();
            case "5" -> addAvailability();
            case "6" -> showMyAvailability();
            case "7" -> findPeopleByInterestAndAvailability();
            case "8" -> logout();
            default -> System.out.println("Invalid option");
        }
    }

    private void showProfile() {
        var user = userService.getUserWithInterests(currentUserId);

        System.out.println("\n--- Profile ---");
        System.out.println("ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("City: " + user.getCity());

        if (user.getInterests().isEmpty()) {
            System.out.println("Interests: none");
        } else {
            System.out.println("Interests:");
            user.getInterests().forEach(i -> System.out.println(" - " + i.getName()));
        }
    }

    private void addInterest() {
        System.out.print("Enter your interests (comma separated): ");
        String input = scanner.nextLine().trim();

        String[] interests = input.split(",");

        for (String interest : interests) {
            String name = interest.trim();
            if (!name.isBlank()) {
                interestService.addInterestToUser(currentUserId, name);
            }
        }

        System.out.println("Interests added!");
    }

    private void findPeopleByInterest() {
        System.out.print("Enter interest to search: ");
        String interest = scanner.nextLine().trim();

        var matches = searchService.findPeopleByInterest(currentUserId, interest);

        if (matches.isEmpty()) {
            System.out.println("No matches found 😢");
            return;
        }

        System.out.println("\nMatches:");
        for (var u : matches) {
            System.out.println("- " + u.getUsername() + " (" + u.getCity() + ")");
        }
    }

    private void findPeopleByMultipleInterests() {
        System.out.print("Enter interests (comma separated): ");
        String input = scanner.nextLine().trim();

        var results = searchService.findPeopleByMultipleInterests(currentUserId, input);

        if (results.isEmpty()) {
            System.out.println("No matches found 😢");
            return;
        }

        System.out.println("\nMatches:");
        for (var r : results) {
            System.out.println("- " + r.getUsername() +
                    " (" + r.getCity() + ")" +
                    " matches: " + r.getMatchesCount() +
                    " (" + r.getMatchPercent() + "%)");
        }
    }

    private void addAvailability() {
        try {
            System.out.println("Choose day of week:");
            System.out.println("1 = Monday");
            System.out.println("2 = Tuesday");
            System.out.println("3 = Wednesday");
            System.out.println("4 = Thursday");
            System.out.println("5 = Friday");
            System.out.println("6 = Saturday");
            System.out.println("7 = Sunday");
            System.out.print("> ");

            int dayNum = Integer.parseInt(scanner.nextLine().trim());
            DayOfWeek day = DayOfWeek.of(dayNum);

            System.out.print("Time from (HH:mm): ");
            LocalTime from = LocalTime.parse(scanner.nextLine().trim());

            System.out.print("Time to (HH:mm): ");
            LocalTime to = LocalTime.parse(scanner.nextLine().trim());

            if (to.isBefore(from) || to.equals(from)) {
                System.out.println("Invalid time range (timeTo must be after timeFrom)");
                return;
            }

            availabilityService.addAvailability(currentUserId, day, from, to);
            System.out.println("Availability added!");
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input. Example: day=6, time=18:30");
        }
    }

    private void showMyAvailability() {
        var slots = availabilityService.getAvailability(currentUserId);

        if (slots.isEmpty()) {
            System.out.println("No availability slots yet.");
            return;
        }

        System.out.println("\n--- My Availability ---");
        for (var s : slots) {
            System.out.println("- " + s.getDay() + ": " + s.getTimeFrom() + " - " + s.getTimeTo());
        }
    }

    private void findPeopleByInterestAndAvailability() {
        try {
            System.out.print("Enter interest: ");
            String interest = scanner.nextLine().trim();

            System.out.print("Choose day of week (1-7): ");
            int dayNum = Integer.parseInt(scanner.nextLine().trim());
            DayOfWeek day = DayOfWeek.of(dayNum);

            System.out.print("Time from (HH:mm): ");
            LocalTime from = LocalTime.parse(scanner.nextLine().trim());

            System.out.print("Time to (HH:mm): ");
            LocalTime to = LocalTime.parse(scanner.nextLine().trim());

            if (to.isBefore(from) || to.equals(from)) {
                System.out.println("Invalid time range (timeTo must be after timeFrom)");
                return;
            }

            var matches = searchService.findPeopleByInterestAndAvailability(
                    currentUserId, interest, day, from, to
            );

            if (matches.isEmpty()) {
                System.out.println("No matches found 😢");
                return;
            }

            System.out.println("\nMatches:");
            for (var u : matches) {
                System.out.println("- " + u.getUsername() + " (" + u.getCity() + ")");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private void logout() {
        currentUserId = null;
        System.out.println("Logged out");
    }
}
