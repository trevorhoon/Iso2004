package librarymanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private final List<Member> members = new ArrayList<>();
    private final File file;

    public MemberManager(String filePath) {
        this.file = new File(filePath);
        loadMembers();
    }

    public List<Member> getAllMembers() { return members; }

    public boolean addMember(Member member) {
        if (findMemberById(member.getMemberId()) != null) return false;
        members.add(member);
        saveMembers();
        return true;
    }

    public Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) return member;
        }
        return null;
    }

    public List<Member> searchMembers(String keyword) {
        List<Member> results = new ArrayList<>();
        String search = keyword.toLowerCase();
        for (Member member : members) {
            if (member.getMemberId().toLowerCase().contains(search)
                    || member.getFullName().toLowerCase().contains(search)) {
                results.add(member);
            }
        }
        return results;
    }

    public boolean updateMember(Member updatedMember) {
        Member existing = findMemberById(updatedMember.getMemberId());
        if (existing == null) return false;
        existing.setFullName(updatedMember.getFullName());
        existing.setPhoneNumber(updatedMember.getPhoneNumber());
        existing.setEmail(updatedMember.getEmail());
        saveMembers();
        return true;
    }

    public boolean deleteMember(String memberId) {
        Member existing = findMemberById(memberId);
        if (existing == null) return false;
        members.remove(existing);
        saveMembers();
        return true;
    }

    public void saveMembers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Member member : members) {
                writer.println(member.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Failed to save members: " + e.getMessage());
        }
    }

    public final void loadMembers() {
        members.clear();
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    members.add(new Member(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load members: " + e.getMessage());
        }
    }
}
