package attendancemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private final StudentManager studentManager;
    private final AttendanceManager attendanceManager;

    private JLabel lblTotalStudents;
    private JLabel lblTotalPresent;
    private JLabel lblTotalAbsent;
    private JLabel lblTotalLate;

    private JTextField txtStudentId;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JComboBox<String> cmbGender;
    private JTextField txtMajor;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtStudentSearch;
    private JTable tblStudents;

    private JTextField txtAttendanceId;
    private JTextField txtAttendanceStudentId;
    private JTextField txtAttendanceDate;
    private JComboBox<String> cmbStatus;
    private JTextField txtRemark;
    private JTextField txtAttendanceSearch;
    private JTable tblAttendanceRecords;

    public MainFrame() {
        this.studentManager = new StudentManager("students.txt");
        this.attendanceManager = new AttendanceManager("attendance_records.txt");

        setTitle("Attendance Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Student Management", createStudentPanel());
        tabbedPane.addTab("Mark Attendance", createMarkAttendancePanel());
        tabbedPane.addTab("Attendance Records", createAttendanceRecordsPanel());
        tabbedPane.addTab("Attendance Summary", createSummaryPanel());

        add(tabbedPane);
        refreshStudentTable(studentManager.getAllStudents());
        refreshAttendanceTable(attendanceManager.getAllRecords());
        refreshDashboardCounts();
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblTotalStudents = createCountLabel("Total Students: 0");
        lblTotalPresent = createCountLabel("Total Present: 0");
        lblTotalAbsent = createCountLabel("Total Absent: 0");
        lblTotalLate = createCountLabel("Total Late: 0");

        panel.add(lblTotalStudents);
        panel.add(lblTotalPresent);
        panel.add(lblTotalAbsent);
        panel.add(lblTotalLate);
        return panel;
    }

    private JLabel createCountLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 4, 8, 8));

        txtStudentId = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        txtMajor = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        formPanel.add(new JLabel("Student ID"));
        formPanel.add(txtStudentId);
        formPanel.add(new JLabel("First Name"));
        formPanel.add(txtFirstName);
        formPanel.add(new JLabel("Last Name"));
        formPanel.add(txtLastName);
        formPanel.add(new JLabel("Gender"));
        formPanel.add(cmbGender);
        formPanel.add(new JLabel("Major"));
        formPanel.add(txtMajor);
        formPanel.add(new JLabel("Phone Number"));
        formPanel.add(txtPhone);
        formPanel.add(new JLabel("Email"));
        formPanel.add(txtEmail);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAddStudent = new JButton("Add Student");
        JButton btnUpdateStudent = new JButton("Update Student");
        JButton btnDeleteStudent = new JButton("Delete Student");
        JButton btnClear = new JButton("Clear");

        buttonPanel.add(btnAddStudent);
        buttonPanel.add(btnUpdateStudent);
        buttonPanel.add(btnDeleteStudent);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        tblStudents = new JTable(new DefaultTableModel(
                new Object[]{"Student ID", "First Name", "Last Name", "Gender", "Major", "Phone", "Email"}, 0));
        JScrollPane scrollPane = new JScrollPane(tblStudents);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtStudentSearch = new JTextField(20);
        JButton btnSearchStudent = new JButton("Search Student");
        JButton btnViewAllStudents = new JButton("View All Students");
        searchPanel.add(new JLabel("Search"));
        searchPanel.add(txtStudentSearch);
        searchPanel.add(btnSearchStudent);
        searchPanel.add(btnViewAllStudents);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.SOUTH);

        btnAddStudent.addActionListener(e -> addStudent());
        btnUpdateStudent.addActionListener(e -> updateStudent());
        btnDeleteStudent.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearStudentFields());
        btnSearchStudent.addActionListener(e -> searchStudents());
        btnViewAllStudents.addActionListener(e -> refreshStudentTable(studentManager.getAllStudents()));

        tblStudents.getSelectionModel().addListSelectionListener(e -> fillStudentFormFromTable());
        return panel;
    }

    private JPanel createMarkAttendancePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 8, 8));
        txtAttendanceId = new JTextField();
        txtAttendanceStudentId = new JTextField();
        txtAttendanceDate = new JTextField();
        cmbStatus = new JComboBox<>(new String[]{"Present", "Absent", "Late"});
        txtRemark = new JTextField();

        formPanel.add(new JLabel("Attendance ID"));
        formPanel.add(txtAttendanceId);
        formPanel.add(new JLabel("Student ID"));
        formPanel.add(txtAttendanceStudentId);
        formPanel.add(new JLabel("Date (yyyy-MM-dd)"));
        formPanel.add(txtAttendanceDate);
        formPanel.add(new JLabel("Status"));
        formPanel.add(cmbStatus);
        formPanel.add(new JLabel("Remark"));
        formPanel.add(txtRemark);

        JButton btnMarkAttendance = new JButton("Mark Attendance");
        JButton btnClear = new JButton("Clear");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnMarkAttendance);
        buttonPanel.add(btnClear);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnMarkAttendance.addActionListener(e -> markAttendance());
        btnClear.addActionListener(e -> clearAttendanceFields());

        return panel;
    }

    private JPanel createAttendanceRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tblAttendanceRecords = new JTable(new DefaultTableModel(
                new Object[]{"Attendance ID", "Student ID", "Student Name", "Date", "Status", "Remark"}, 0));
        panel.add(new JScrollPane(tblAttendanceRecords), BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtAttendanceSearch = new JTextField(20);
        JButton btnSearchAttendance = new JButton("Search Attendance");
        JButton btnViewAllAttendance = new JButton("View All Attendance");

        searchPanel.add(new JLabel("Search"));
        searchPanel.add(txtAttendanceSearch);
        searchPanel.add(btnSearchAttendance);
        searchPanel.add(btnViewAllAttendance);

        btnSearchAttendance.addActionListener(e -> searchAttendance());
        btnViewAllAttendance.addActionListener(e -> refreshAttendanceTable(attendanceManager.getAllRecords()));

        panel.add(searchPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

        JButton btnRefreshSummary = new JButton("Refresh Summary");
        btnRefreshSummary.addActionListener(e -> {
            Map<String, Integer> summary = attendanceManager.getSummary();
            summaryArea.setText("Attendance Summary\n\n"
                    + "Present: " + summary.get("Present") + "\n"
                    + "Absent : " + summary.get("Absent") + "\n"
                    + "Late   : " + summary.get("Late") + "\n");
            refreshDashboardCounts();
        });

        panel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        panel.add(btnRefreshSummary, BorderLayout.SOUTH);
        return panel;
    }

    private void addStudent() {
        String validation = validateStudentInput();
        if (validation != null) {
            JOptionPane.showMessageDialog(this, validation, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(
                txtStudentId.getText().trim(),
                txtFirstName.getText().trim(),
                txtLastName.getText().trim(),
                cmbGender.getSelectedItem().toString(),
                txtMajor.getText().trim(),
                txtPhone.getText().trim(),
                txtEmail.getText().trim()
        );

        if (studentManager.addStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshStudentTable(studentManager.getAllStudents());
            refreshDashboardCounts();
            clearStudentFields();
        } else {
            JOptionPane.showMessageDialog(this, "Duplicate Student ID is not allowed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        String validation = validateStudentInput();
        if (validation != null) {
            JOptionPane.showMessageDialog(this, validation, "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(
                txtStudentId.getText().trim(), txtFirstName.getText().trim(), txtLastName.getText().trim(),
                cmbGender.getSelectedItem().toString(), txtMajor.getText().trim(), txtPhone.getText().trim(), txtEmail.getText().trim());

        if (studentManager.updateStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshStudentTable(studentManager.getAllStudents());
            clearStudentFields();
        } else {
            JOptionPane.showMessageDialog(this, "Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String studentId = txtStudentId.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (studentManager.deleteStudent(studentId)) {
            JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshStudentTable(studentManager.getAllStudents());
            refreshDashboardCounts();
            clearStudentFields();
        } else {
            JOptionPane.showMessageDialog(this, "Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudents() {
        String keyword = txtStudentSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID or Name to search.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        refreshStudentTable(studentManager.searchByIdOrName(keyword));
    }

    private void markAttendance() {
        String attendanceId = txtAttendanceId.getText().trim();
        String studentId = txtAttendanceStudentId.getText().trim();
        String date = txtAttendanceDate.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();
        String remark = txtRemark.getText().trim();

        if (attendanceId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Attendance ID must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Attendance date must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(status.equals("Present") || status.equals("Absent") || status.equals("Late"))) {
            JOptionPane.showMessageDialog(this, "Attendance status must be Present, Absent, or Late.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = studentManager.findByStudentId(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student ID does not exist.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AttendanceRecord record = new AttendanceRecord(attendanceId, studentId, student.getFullName(), date, status, remark);

        if (attendanceManager.addAttendanceRecord(record)) {
            JOptionPane.showMessageDialog(this, "Attendance marked successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshAttendanceTable(attendanceManager.getAllRecords());
            refreshDashboardCounts();
            clearAttendanceFields();
        } else {
            JOptionPane.showMessageDialog(this, "Attendance for this student and date already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchAttendance() {
        String keyword = txtAttendanceSearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID, Name, or Date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        refreshAttendanceTable(attendanceManager.searchByStudentIdNameOrDate(keyword));
    }

    private void refreshStudentTable(List<Student> students) {
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);
        for (Student student : students) {
            model.addRow(new Object[]{student.getStudentId(), student.getFirstName(), student.getLastName(), student.getGender(),
                    student.getMajor(), student.getPhoneNumber(), student.getEmail()});
        }
    }

    private void refreshAttendanceTable(List<AttendanceRecord> records) {
        DefaultTableModel model = (DefaultTableModel) tblAttendanceRecords.getModel();
        model.setRowCount(0);
        for (AttendanceRecord record : records) {
            model.addRow(new Object[]{record.getAttendanceId(), record.getStudentId(), record.getStudentName(),
                    record.getDate(), record.getStatus(), record.getRemark()});
        }
    }

    private void refreshDashboardCounts() {
        lblTotalStudents.setText("Total Students: " + studentManager.getAllStudents().size());
        Map<String, Integer> summary = attendanceManager.getSummary();
        lblTotalPresent.setText("Total Present: " + summary.get("Present"));
        lblTotalAbsent.setText("Total Absent: " + summary.get("Absent"));
        lblTotalLate.setText("Total Late: " + summary.get("Late"));
    }

    private String validateStudentInput() {
        if (txtStudentId.getText().trim().isEmpty()) return "Student ID must not be empty.";
        if (txtFirstName.getText().trim().isEmpty()) return "First name must not be empty.";
        if (txtLastName.getText().trim().isEmpty()) return "Last name must not be empty.";
        if (txtMajor.getText().trim().isEmpty()) return "Major must not be empty.";
        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) return "Please enter a valid email address.";
        return null;
    }

    private void clearStudentFields() {
        txtStudentId.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        cmbGender.setSelectedIndex(0);
        txtMajor.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }

    private void clearAttendanceFields() {
        txtAttendanceId.setText("");
        txtAttendanceStudentId.setText("");
        txtAttendanceDate.setText("");
        cmbStatus.setSelectedIndex(0);
        txtRemark.setText("");
    }

    private void fillStudentFormFromTable() {
        int selectedRow = tblStudents.getSelectedRow();
        if (selectedRow == -1) return;

        txtStudentId.setText(tblStudents.getValueAt(selectedRow, 0).toString());
        txtFirstName.setText(tblStudents.getValueAt(selectedRow, 1).toString());
        txtLastName.setText(tblStudents.getValueAt(selectedRow, 2).toString());
        cmbGender.setSelectedItem(tblStudents.getValueAt(selectedRow, 3).toString());
        txtMajor.setText(tblStudents.getValueAt(selectedRow, 4).toString());
        txtPhone.setText(tblStudents.getValueAt(selectedRow, 5).toString());
        txtEmail.setText(tblStudents.getValueAt(selectedRow, 6).toString());
    }
}
