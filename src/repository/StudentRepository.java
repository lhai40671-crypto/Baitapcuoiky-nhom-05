package repository;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final String fileName =
            "students.txt";

    public void save(
            List<Student> students)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(fileName))) {

            for (Student student : students) {

                writer.write(
                        student.getStudentId()
                                + "|"
                                + student.getFullName()
                                + "|"
                                + student.getPhone()
                                + "|"
                                + student.getClassName()
                                + "|"
                                + student.getEmail());

                writer.newLine();
            }
        }
    }

    public List<Student> load()
            throws IOException {

        List<Student> students =
                new ArrayList<>();

        File file =
                new File(fileName);

        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] data =
                        line.split("\\|");

                if (data.length != 5) {
                    continue;
                }

                Student student =
                        new Student(
                                data[0],
                                data[1],
                                data[2],
                                data[3],
                                data[4]);

                students.add(student);
            }
        }

        return students;
    }

    public Student findById(
            List<Student> students,
            String id) {

        for (Student student : students) {

            if (student.getStudentId()
                    .equals(id)) {

                return student;
            }
        }

        return null;
    }
}