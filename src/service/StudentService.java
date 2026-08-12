package service;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students;

    public StudentService() {
        students = new ArrayList<>();
    }

    // Thêm sinh viên
    public void addStudent(Student student) {
        if (student == null) {
            return;
        }

        if (findById(student.getUserId()) != null) {
            return;
        }

        students.add(student);
    }

    // Tìm sinh viên theo mã
    public Student findById(String studentId) {
        if (studentId == null) {
            return null;
        }

        for (Student student : students) {
            if (student.getUserId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    // Lấy toàn bộ danh sách sinh viên
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Xóa sinh viên
    public boolean removeStudent(String studentId) {
        Student student = findById(studentId);

        if (student == null) {
            return false;
        }

        students.remove(student);
        return true;
    }
}