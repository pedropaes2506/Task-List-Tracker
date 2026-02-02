package task_list_tracker.service;

import task_list_tracker.domain.Task;
import task_list_tracker.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void findAll() {
        TaskRepository.findAll()
                .forEach(System.out::println);
    }

    public static void findByStatus() {
        System.out.println("Find all tasks where status is:");
        System.out.println("1. Done");
        System.out.println("2. To do");
        System.out.println("3. In progress");

        int op = Integer.parseInt(SCANNER.nextLine());
        String status = switch (op) {
            case 1 -> "DONE";
            case 3 -> "IN PROGRESS";
            default -> "TO DO";
        };

        TaskRepository.findByStatus(status)
                .forEach(System.out::println);
    }

    public static void save(){
        System.out.println("Type the name of the task you want to add");
        String name = SCANNER.nextLine();
        System.out.println("Type a description to the task");
        String description = SCANNER.nextLine();
        System.out.println("Mark the task as:");
        System.out.println("1. Done");
        System.out.println("2. To do");
        System.out.println("3. In progress");

        int op = Integer.parseInt(SCANNER.nextLine());
        String status = switch (op) {
            case 1 -> "DONE";
            case 3 -> "IN PROGRESS";
            default -> "TO DO";
        };

        Task task = Task.builder()
                .name(name)
                .description(description)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        TaskRepository.save(task);
    }

    public static void update() {
        TaskRepository.findAll()
                .forEach(System.out::println);
        System.out.println("Type the id of the task you want to update");
        Integer id = Integer.parseInt(SCANNER.nextLine());
        Optional<Task> taskOptional = TaskRepository.findById(id);
        if (taskOptional.isEmpty()){
            System.out.println("Task not found");
            return;
        }
        Task taskFromDb = taskOptional.get();
        System.out.println("Task found " + taskFromDb);
        System.out.println("Type the new name or empty to keep the same");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? taskFromDb.getName() : name;

        System.out.println("Type the new description or empty to keep the same");
        String description = SCANNER.nextLine();
        description = description.isEmpty() ? taskFromDb.getDescription() : description;

        System.out.println("Mark the task as:");
        System.out.println("1. Done");
        System.out.println("2. To do");
        System.out.println("3. In progress");

        int op = Integer.parseInt(SCANNER.nextLine());
        String status = switch (op) {
            case 1 -> "DONE";
            case 3 -> "IN PROGRESS";
            default -> "TO DO";
        };

        Task taskToUpdate = Task.builder()
                .id(taskFromDb.getId())
                .name(name)
                .description(description)
                .status(status)
                .createdAt(taskFromDb.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        TaskRepository.update(taskToUpdate);
    }

    public static void delete() {
        TaskRepository.findAll()
                .forEach(System.out::println);
        System.out.println("Type the ID of the task you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y/N");
        String choice = SCANNER.nextLine();
        if ("y".equalsIgnoreCase(choice)) {
            TaskRepository.delete(id);
        }
        else {
            System.out.println("Delete process canceled");
        }
    }
}
