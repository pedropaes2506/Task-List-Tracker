package main.task_list_tracker.service;

import main.task_list_tracker.domain.Task;
import main.task_list_tracker.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Scanner;

public class TaskService {
    private static final Scanner SCANNER = new Scanner(System.in);

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
        String status;
        switch (op) {
            case 1: status = "DONE";
            case 2: status = "TO DO";
            case 3: status = "IN PROGRESS";
            default: status = "TO DO";
        }

        Task task = Task.builder()
                .name(name)
                .description(description)
                .status(status)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        TaskRepository.save(task);
    }
}
