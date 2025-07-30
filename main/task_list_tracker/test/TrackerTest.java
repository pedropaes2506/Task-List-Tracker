package main.task_list_tracker.test;

import lombok.extern.log4j.Log4j2;
import main.task_list_tracker.repository.TaskRepository;
import main.task_list_tracker.service.TaskService;

import java.util.Scanner;

@Log4j2
public class TrackerTest {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
//        int op;
//        while(true) {
//            menu();
//            op = Integer.parseInt(SCANNER.nextLine());
//            if (op == 0) break;
//            switch (op) {
//                case 1: TaskService.save();
//            }
//        }
        System.out.println(TaskRepository.findAll());
    }

    private static void menu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Add task");
        System.out.println("0. Exit");
    }
}
