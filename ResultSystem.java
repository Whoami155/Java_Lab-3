import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String msg) {
        super(msg);
    }
}

class Student {
    int roll;
    String name;
    int marks[] = new int[3];

    public Student(int roll, String name, int[] m) throws InvalidMarksException {
        this.roll = roll;
        this.name = name;
        this.marks = m;
        validateMarks();
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < 3; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks: " + marks[i]);
            }
        }
    }

    public double avg() {
        double s = 0;
        for (int x : marks) s += x;
        return s / 3.0;
    }

    public void display() {
        System.out.println("Roll: " + roll);
        System.out.println("Name: " + name);
        System.out.print("Marks: ");
        for (int x : marks) System.out.print(x + " ");
        System.out.println("\nAverage: " + avg());
        System.out.println("Result: " + (avg() >= 40 ? "Pass" : "Fail"));
    }
}

public class ResultSystem {
    Student[] list = new Student[100];
    int count = 0;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        new ResultSystem().menu();
    }

    void menu() {
        while (true) {
            System.out.println("\n1. Add Student\n2. Show Student\n3. Exit");
            System.out.print("Choice: ");

            try {
                int c = Integer.parseInt(sc.nextLine());
                if (c == 1) addStudent();
                else if (c == 2) showStudent();
                else if (c == 3) break;
                else System.out.println("Invalid choice");
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
        System.out.println("Thank you!");
    }

    void addStudent() {
        try {
            System.out.print("Roll: ");
            int r = Integer.parseInt(sc.nextLine());

            System.out.print("Name: ");
            String n = sc.nextLine();

            int m[] = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Marks " + (i + 1) + ": ");
                m[i] = Integer.parseInt(sc.nextLine());
            }

            list[count++] = new Student(r, n, m);
            System.out.println("Student added.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Marks/Roll must be numeric.");
        } finally {
            System.out.println("Operation done.");
        }
    }

    void showStudent() {
        try {
            System.out.print("Enter Roll: ");
            int r = Integer.parseInt(sc.nextLine());

            for (int i = 0; i < count; i++) {
                if (list[i].roll == r) {
                    list[i].display();
                    return;
                }
            }
            System.out.println("Student not found.");

        } catch (Exception e) {
            System.out.println("Invalid roll number.");
        } finally {
            System.out.println("Search finished.");
        }
    }
}

