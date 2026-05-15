package attendancemanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private final List<Student> students;
    private final File studentFile;

    public StudentManager(String filePath) {
        this.students = new ArrayList<>();
        this.studentFile = new File(filePath);
        loadStudents();
    }

    public boolean addStudent(Student student) {
        if (findByStudentId(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        saveStudents();
        return true;
    }

    public boolean updateStudent(Student updatedStudent) {
        Student existing = findByStudentId(updatedStudent.getStudentId());
        if (existing == null) {
            return false;
        }
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setGender(updatedStudent.getGender());
        existing.setMajor(updatedStudent.getMajor());
        existing.setPhoneNumber(updatedStudent.getPhoneNumber());
        existing.setEmail(updatedStudent.getEmail());
        saveStudents();
        return true;
    }

    public boolean deleteStudent(String studentId) {
        Student existing = findByStudentId(studentId);
        if (existing == null) {
            return false;
        }
        students.remove(existing);
        saveStudents();
        return true;
    }

    public Student findByStudentId(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> searchByIdOrName(String keyword) {
        List<Student> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Student student : students) {
            if (student.getStudentId().toLowerCase().contains(lowerKeyword)
                    || student.getFirstName().toLowerCase().contains(lowerKeyword)
                    || student.getLastName().toLowerCase().contains(lowerKeyword)
                    || student.getFullName().toLowerCase().contains(lowerKeyword)) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public void loadStudents() {
        students.clear();
        if (!studentFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile))) {
            for (Student student : students) {
                writer.write(student.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
