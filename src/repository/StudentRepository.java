StudentRepository

        package repository;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static final String FILE_NAME = "data/students.txt";

    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 5) {
                    continue;
                }

                // studentId|name|phone|className|email
                Student student = new Student(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                students.add(student);
            }

        } catch (IOException e) {
            System.out.println("Loi doc file sinh vien: " + e.getMessage());
        }

        return students;
    }

    public Student findById(String studentId) {

        for (Student student : getAll()) {
            if (student.getUserId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    public void save(Student student) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_NAME, true))) {

            writer.write(
                    student.getUserId() + "|" +
                            student.getName() + "|" +
                            student.getPhone() + "|" +
                            student.getClassName() + "|" +
                            student.getEmail()
            );

            writer.newLine();

        } catch (IOException e) {
            System.out.println("Loi ghi file sinh vien: " + e.getMessage());
        }
    }
}