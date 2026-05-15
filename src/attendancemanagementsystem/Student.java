package attendancemanagementsystem;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private String gender;
    private String major;
    private String phoneNumber;
    private String email;

    public Student(String studentId, String firstName, String lastName, String gender,
                   String major, String phoneNumber, String email) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.major = major;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String toFileString() {
        return studentId + "|" + firstName + "|" + lastName + "|" + gender + "|"
                + major + "|" + phoneNumber + "|" + email;
    }
}
