package attendancemanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceManager {
    private final List<AttendanceRecord> records;
    private final File attendanceFile;

    public AttendanceManager(String filePath) {
        this.records = new ArrayList<>();
        this.attendanceFile = new File(filePath);
        loadAttendanceRecords();
    }

    public boolean addAttendanceRecord(AttendanceRecord record) {
        if (isDuplicateAttendance(record.getStudentId(), record.getDate())) {
            return false;
        }
        records.add(record);
        saveAttendanceRecords();
        return true;
    }

    public boolean isDuplicateAttendance(String studentId, String date) {
        for (AttendanceRecord record : records) {
            if (record.getStudentId().equalsIgnoreCase(studentId)
                    && record.getDate().equalsIgnoreCase(date)) {
                return true;
            }
        }
        return false;
    }

    public List<AttendanceRecord> searchByStudentIdNameOrDate(String keyword) {
        List<AttendanceRecord> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (AttendanceRecord record : records) {
            if (record.getStudentId().toLowerCase().contains(lowerKeyword)
                    || record.getStudentName().toLowerCase().contains(lowerKeyword)
                    || record.getDate().toLowerCase().contains(lowerKeyword)) {
                result.add(record);
            }
        }
        return result;
    }

    public List<AttendanceRecord> getAllRecords() {
        return new ArrayList<>(records);
    }

    public Map<String, Integer> getSummary() {
        Map<String, Integer> summary = new HashMap<>();
        summary.put("Present", 0);
        summary.put("Absent", 0);
        summary.put("Late", 0);

        for (AttendanceRecord record : records) {
            String status = record.getStatus();
            if (summary.containsKey(status)) {
                summary.put(status, summary.get(status) + 1);
            }
        }
        return summary;
    }

    public void loadAttendanceRecords() {
        records.clear();
        if (!attendanceFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    records.add(new AttendanceRecord(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAttendanceRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(attendanceFile))) {
            for (AttendanceRecord record : records) {
                writer.write(record.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
