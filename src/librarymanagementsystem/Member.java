package librarymanagementsystem;

public class Member {
    private String memberId;
    private String fullName;
    private String phoneNumber;
    private String email;

    public Member(String memberId, String fullName, String phoneNumber, String email) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String toFileString() {
        return memberId + "|" + fullName + "|" + phoneNumber + "|" + email;
    }
}
