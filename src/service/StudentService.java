package service;

import model.Student;
import repository.StudentRepository;

import java.util.List;

public class StudentService {

    private StudentRepository studentRepository;

    public StudentService() {
        studentRepository = new StudentRepository();
    }

    // Lấy danh sách sinh viên
    public List<Student> getAllStudents() {
        return studentRepository.getAll();
    }

    // Tìm sinh viên theo mã
    public Student findStudentById(String studentId) {

        return studentRepository.findById(studentId);
    }

    // Thêm sinh viên
    public void addStudent(Student student) {

        if (student == null) {
            System.out.println(
                    "Sinh vien khong hop le!"
            );
            return;
        }

        if (studentRepository.findById(
                student.getStudentId()) != null) {

            System.out.println(
                    "Ma sinh vien da ton tai!"
            );
            return;
        }

        studentRepository.save(student);

        System.out.println(
                "Them sinh vien thanh cong!"
        );
    }
}