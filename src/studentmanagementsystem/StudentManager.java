package studentmanagementsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles student list operations and file read/write.
 */
public class StudentManager {
    private final List<Student> students;
    private final File dataFile;

    public StudentManager(String filePath) {
        this.students = new ArrayList<>();
        this.dataFile = new File(filePath);
        loadFromFile();
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean addStudent(Student student) {
        if (findById(student.getStudentId()) != null) {
            return false;
        }
        students.add(student);
        return true;
    }

    public Student findById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Student student : students) {
            String fullName = (student.getFirstName() + " " + student.getLastName()).toLowerCase();
            if (fullName.contains(key)) {
                result.add(student);
            }
        }
        return result;
    }

    public boolean updateStudent(String originalId, Student updatedStudent) {
        Student existing = findById(originalId);
        if (existing == null) {
            return false;
        }

        if (!originalId.equalsIgnoreCase(updatedStudent.getStudentId())
                && findById(updatedStudent.getStudentId()) != null) {
            return false;
        }

        existing.setStudentId(updatedStudent.getStudentId());
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setGender(updatedStudent.getGender());
        existing.setMajor(updatedStudent.getMajor());
        existing.setPhoneNumber(updatedStudent.getPhoneNumber());
        existing.setEmail(updatedStudent.getEmail());
        return true;
    }

    public boolean deleteStudent(String studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return false;
        }
        return students.remove(student);
    }

    public void saveToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Student student : students) {
                writer.write(student.toFileString());
                writer.newLine();
            }
        }
    }

    public final void loadFromFile() {
        students.clear();

        if (!dataFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Student student = Student.fromFileString(line);
                if (student != null) {
                    students.add(student);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error loading file: " + ex.getMessage());
        }
    }
}
