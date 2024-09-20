package online_examination;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static User currentUser;
    private static ArrayList<Question> questions = new ArrayList<>();
    private static final int EXAM_DURATION = 60; 
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        loadQuestions();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("<<< 1.Register >>>\n <<< 2. Login >>>\n<<< 3. Exit >>>");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    if (currentUser != null) {
                        startExam(scanner);
                    }
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }

    private static void loadQuestions() {
        questions.add(new Question("the Harry Potter series is written by ?", new String[]{"K K Rowling", "K J Rowling", "J K Rowling", "A K Rowling"}, 2));
        questions.add(new Question("What is the capital of india?", new String[]{"Berlin", "New Delhi", "Paris", "London"}, 1));
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    private static void startExam(Scanner scanner) {
        while (true) {
            System.out.println("1. Take Exam\n2. Update Profile\n3. Logout");
            int choice = scanner.nextInt();
            if (choice == 1) {
                takeExam(scanner);
                break; 
            } else if (choice == 2) {
                updateProfile(scanner);
            } else if (choice == 3) {
                currentUser = null; 
                break; 
            }
        }
    }

    private static void takeExam(Scanner scanner) {
        System.out.println("Exam started! You have " + EXAM_DURATION + " seconds.");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Submitting your answers...");
                submitExam();
                timer.cancel();
            }
        }, EXAM_DURATION * 1000);

        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ": " + options[i]);
            }
            System.out.print("Select an answer (1-" + options.length + "): ");
            int answer = scanner.nextInt();
            currentUser.addAnswer(question, answer - 1);
        }

        timer.cancel();
        submitExam();
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.next();
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        currentUser.updateProfile(newUsername, newPassword);
        System.out.println("Profile updated successfully!");
    }

    private static void submitExam() {
        int score = currentUser.calculateScore(questions);
        System.out.println("Your score: " + score + "/" + questions.size());
        currentUser = null; 
    }
}