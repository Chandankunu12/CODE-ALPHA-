package CodeAlpha_JavaProgramming_01;

import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Double> grades;
    public Student(){}
    public Student(String name, ArrayList<Double> grades) {
        this.name = name;
        this.grades = grades;
    }

    public static void addStudent(ArrayList<Student> studentList, String name, ArrayList<Double> grades) {
        Student newStudent = new Student(name, grades);
        studentList.add(newStudent);
    }

    // Get low, high, and average grades
    public static double getLowGrad(ArrayList<Double> grades) {
        double result = grades.get(0);
        for (double number : grades) {
            if (number < result) {
                result = number;
            }
        }
        return result;
    }

    public static double getHighGrad(ArrayList<Double> grades) {
        double result = grades.get(0);
        for (double number : grades) {
            if (number > result) {
                result = number;
            }
        }
        return result;
    }

    public static double Avg(ArrayList<Double> numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.size();
    }

    // Grade A, B, C
    public static char gradeLetter(ArrayList<Double> numbers) {
        double avg = Avg(numbers);
        char result;
        if (avg >= 90) {
            result = 'A';
        } else if (avg >= 80) {
            result = 'B';
        } else if (avg >= 70) {
            result = 'C';
        } else if (avg >= 65) {
            result = 'D';
        } else {
            result = 'F';
        }
        return result;
    }

}
public class StudentGradeTracker {
        // {90, 85, 75, 85, 95}, {100, 85, 75, 55, 95}, {55, 85, 65, 85, 95},
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    ArrayList<Student> studentList = new ArrayList<>();
    System.out.println("Welcome to the Student Grade Tracker\n");
    // Adding students
    while (true) {
        System.out.print("Enter student name (or 'done' to finish): ");
        String studentName = input.nextLine();

        if (studentName.equalsIgnoreCase("done")) {
            break;
        }

        System.out.print("Enter comma-separated grades for " + studentName + ": ");
        String[] gradesInput = input.nextLine().split(",");
        ArrayList<Double> studentGrades = new ArrayList<>();

        for (String grade : gradesInput) {
            studentGrades.add(Double.parseDouble(grade.trim()));
        }

        Student.addStudent(studentList, studentName, studentGrades);
    }

    // Displaying student information
    System.out.println("\n------------------Student Information:--------------------");

    for (Student s : studentList) {
        System.out.println("\nStudent Name: " + s.name);
        System.out.println("Grades: " + s.grades);
        System.out.println("Average: " + Student.Avg(s.grades));
        System.out.println("Lowest Grade: " + Student.getLowGrad(s.grades));
        System.out.println("Highest Grade: " + Student.getHighGrad(s.grades));
        System.out.println("Grade Letter: " + Student.gradeLetter(s.grades));
        System.out.println("-----------------------");
    	}

    	input.close();
	}
}