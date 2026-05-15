package studentmanagementsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystemFrame extends JFrame {
    private final StudentManager studentManager;

    private JTextField txtStudentId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> comboGender;
    private JTextField txtMajor;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentManagementSystemFrame() {
        studentManager = new StudentManager("students.txt");
        initComponents();
        loadTable(studentManager.getAllStudents());
    }

    private void initComponents() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));

        txtStudentId = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        comboGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        txtMajor = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtSearch = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(txtStudentId);
        formPanel.add(new JLabel("First name:"));
        formPanel.add(txtFirstName);
        formPanel.add(new JLabel("Last name:"));
        formPanel.add(txtLastName);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(comboGender);
        formPanel.add(new JLabel("Major:"));
        formPanel.add(txtMajor);
        formPanel.add(new JLabel("Phone number:"));
        formPanel.add(txtPhone);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Search (ID or name):"));
        formPanel.add(txtSearch);

        add(formPanel, BorderLayout.WEST);

        String[] columnNames = {"Student ID", "First Name", "Last Name", "Gender", "Major", "Phone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(620, 450));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JButton btnAdd = new JButton("Add Student");
        JButton btnViewAll = new JButton("View All");
        JButton btnSearch = new JButton("Search");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnSave = new JButton("Save File");
        JButton btnExit = new JButton("Exit");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnViewAll);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addStudent());
        btnViewAll.addActionListener(e -> loadTable(studentManager.getAllStudents()));
        btnSearch.addActionListener(e -> searchStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnSave.addActionListener(e -> saveData());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void loadTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getGender(),
                student.getMajor(),
                student.getPhoneNumber(),
                student.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    private Student readStudentFromFields() {
        String studentId = txtStudentId.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String gender = (String) comboGender.getSelectedItem();
        String major = txtMajor.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (studentId.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                || major.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new Student(studentId, firstName, lastName, gender, major, phone, email);
    }

    private void addStudent() {
        Student student = readStudentFromFields();
        if (student == null) {
            return;
        }

        if (studentManager.addStudent(student)) {
            loadTable(studentManager.getAllStudents());
            JOptionPane.showMessageDialog(this, "Student added successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Student ID already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID or name to search.",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student byId = studentManager.findById(keyword);
        if (byId != null) {
            loadTable(java.util.List.of(byId));
            return;
        }

        List<Student> results = studentManager.searchByName(keyword);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No student found.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        loadTable(results);
    }

    private void updateStudent() {
        String originalId = txtStudentId.getText().trim();
        if (originalId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID to update.",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Student updatedStudent = readStudentFromFields();
        if (updatedStudent == null) {
            return;
        }

        if (studentManager.updateStudent(originalId, updatedStudent)) {
            loadTable(studentManager.getAllStudents());
            JOptionPane.showMessageDialog(this, "Student updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Update failed. Student not found or new ID already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String studentId = txtStudentId.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter student ID to delete.",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (studentManager.deleteStudent(studentId)) {
            loadTable(studentManager.getAllStudents());
            JOptionPane.showMessageDialog(this, "Student deleted successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Student not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData() {
        try {
            studentManager.saveToFile();
            JOptionPane.showMessageDialog(this, "Data saved to students.txt successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error saving file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtStudentId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        comboGender.setSelectedIndex(0);
        txtMajor.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystemFrame frame = new StudentManagementSystemFrame();
            frame.setVisible(true);
        });
    }
}
