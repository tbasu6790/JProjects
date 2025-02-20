import java.util.*;

class User {
    private String username;
    private String password;
    private String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }

    public void setPassword(String newPassword) { this.password = newPassword; }
    public void setFullName(String newName) { this.fullName = newName; }
}

class Exam {
    private Map<String, String> questions = new LinkedHashMap<>();
    private Map<String, String> userAnswers = new HashMap<>();
    private int timeLimit = 30; // Exam time in seconds

    public Exam() {
        // Sample Questions
        questions.put("1. What is the capital of France?\n(a) Berlin  (b) Madrid  (c) Paris  (d) Rome", "c");
        questions.put("2. Java is a _____ language.\n(a) Compiled  (b) Interpreted  (c) Both  (d) None", "c");
        questions.put("3. Who developed Java?\n(a) Microsoft  (b) Sun Microsystems  (c) Google  (d) IBM", "b");
    }

    public void startExam() {
        Scanner sc = new Scanner(System.in);
        Timer timer = new Timer();
        final int[] remainingTime = {timeLimit};

        System.out.println("\nExam started! You have " + timeLimit + " seconds.");

        TimerTask countdownTask = new TimerTask() {
            public void run() {
                if (remainingTime[0] > 0) {
                    System.out.print("\rTime Left: " + remainingTime[0] + "s ");
                    remainingTime[0]--;
                } else {
                    System.out.println("\nTime is up! Auto-submitting your exam...");
                    timer.cancel();
                    submitExam();
                    System.exit(0);
                }
            }
        };

        // Schedule the timer to update every second
        timer.scheduleAtFixedRate(countdownTask, 0, 1000);

        for (String question : questions.keySet()) {
            System.out.println("\n" + question);
            System.out.print("Your Answer (a/b/c/d): ");
            String answer = sc.nextLine().toLowerCase();
            userAnswers.put(question, answer);

            if (remainingTime[0] <= 0) {
                System.out.println("\nTime is up! Auto-submitting your exam...");
                break;
            }
        }

        timer.cancel(); // Stop timer if user finishes early
        submitExam();
    }

    public void submitExam() {
        System.out.println("\nExam submitted! Checking answers...");
        int score = 0;
        for (Map.Entry<String, String> entry : questions.entrySet()) {
            if (userAnswers.getOrDefault(entry.getKey(), "").equals(entry.getValue())) {
                score++;
            }
        }
        System.out.println("Your Score: " + score + "/" + questions.size());
    }
}

public class OnlineExamSystem {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser;

    public static void main(String[] args) {
        users.put("admin", new User("admin", "admin123", "Admin User"));

        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            if (choice == 1) {
                if (login()) {
                    showMenu();
                }
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public static boolean login() {
        System.out.print("\nEnter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login Successful! Welcome, " + currentUser.getFullName());
            return true;
        } else {
            System.out.println("Invalid Credentials!");
            return false;
        }
    }

    public static void showMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Start Exam");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    new Exam().startExam();
                    break;
                case 2:
                    updateProfile();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void updateProfile() {
        System.out.print("\nEnter new full name: ");
        String newName = sc.nextLine();
        currentUser.setFullName(newName);
        System.out.println("Profile updated successfully!");
    }

    public static void changePassword() {
        System.out.print("\nEnter new password: ");
        String newPassword = sc.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password changed successfully!");
    }
}
