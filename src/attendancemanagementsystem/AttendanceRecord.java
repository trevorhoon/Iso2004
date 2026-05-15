package attendancemanagementsystem;

public class AttendanceRecord {
    private String attendanceId;
    private String studentId;
    private String studentName;
    private String date;
    private String status;
    private String remark;

    public AttendanceRecord(String attendanceId, String studentId, String studentName,
                            String date, String status, String remark) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.status = status;
        this.remark = remark;
    }

    public String getAttendanceId() { return attendanceId; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public String getRemark() { return remark; }

    public String toFileString() {
        return attendanceId + "|" + studentId + "|" + studentName + "|" + date + "|"
                + status + "|" + remark;
    }
}
