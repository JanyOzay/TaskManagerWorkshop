package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;




public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadDataToTab(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showOptions();
            System.out.print(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
            String input = scanner.nextLine();

            switch (input) {
                case "add":
                    addTask(scanner); // TODO
                    break;
                case "remove":
                    removeTask(scanner); // TODO
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    saveTasks(); // TODO
                    System.out.println(ConsoleColors.RED + "Exiting program." + ConsoleColors.RESET);
                    running = false;
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
        }
        scanner.close();
    }

    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File does not exist.");
            System.exit(0);
        }

        String[][] tab = null;

        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void showOptions() {
        System.out.println(ConsoleColors.BLUE + "Options:");
        String[] options = {"add", "remove", "list", "exit"};
        for (String option : options) {
            System.out.println(" - " + option);
        }
        System.out.println(ConsoleColors.RESET);
    }

    public static void listTasks() {
        if (tasks == null || tasks.length == 0) {
            System.out.println(ConsoleColors.YELLOW + "No tasks to display." + ConsoleColors.RESET);
            return;
        }

        for (int i = 0; i < tasks.length; i++) {
            String[] task = tasks[i];
            System.out.println(i + " : " +
                    "[" + task[0] + "] | " +
                    "[" + task[1] + "] | " +
                    "[" + task[2] + "]");
        }
    }

    public static void addTask(Scanner scanner) {
        System.out.print("Please add task description: ");
        String description = scanner.nextLine();

        System.out.print("Please add task due date (e.g. 2025-08-01): ");
        String date = scanner.nextLine();

        System.out.print("Is your task important? (true/false): ");
        String isImportant = scanner.nextLine();

        // Nový úkol jako řádek pole
        String[] newTask = {description, date, isImportant};

        // Rozšíříme pole úkolů o 1
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = newTask;

        System.out.println(ConsoleColors.GREEN + "Task added." + ConsoleColors.RESET);
    }

    public static void saveTasks() {
        Path path = Paths.get(FILE_NAME);

        List<String> lines = new ArrayList<>();
        for (String[] task : tasks) {
            lines.add(String.join(",", task));
        }

        try {
            Files.write(path, lines);
            System.out.println(ConsoleColors.GREEN + "Tasks saved to file." + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving tasks: " + e.getMessage() + ConsoleColors.RESET);
        }
    }


    public static void removeTask(Scanner scanner) {
        System.out.print("Please enter the number of the task to remove: ");
        String input = scanner.nextLine();

        if (!NumberUtils.isParsable(input)) {
            System.out.println(ConsoleColors.RED + "Invalid input. Please enter a valid number." + ConsoleColors.RESET);
            return;
        }

        int index = Integer.parseInt(input);
        if (index < 0 || index >= tasks.length) {
            System.out.println(ConsoleColors.RED + "Task number out of range." + ConsoleColors.RESET);
            return;
        }

        tasks = ArrayUtils.remove(tasks, index);
        System.out.println(ConsoleColors.GREEN + "Task successfully removed." + ConsoleColors.RESET);
    }


    // TODO: Doplnit metody saveTasks(), addTask(), removeTask()
}
