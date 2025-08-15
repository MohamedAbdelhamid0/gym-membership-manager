package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.Member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement("SELECT * FROM Member ORDER BY MemberID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Member m = new Member(
                            rs.getInt("MemberID"),
                            (Integer) rs.getObject("UserID"),
                            rs.getString("JoinDate"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("DateOfBirth"),
                            rs.getString("Gender"),
                            rs.getString("UID")
                    );
                    list.add(m);
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Member insert(Member m) {
        String sql = "INSERT INTO Member(UserID, JoinDate, FirstName, LastName, Email, DateOfBirth, Gender, UID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, m.getUserId());
            ps.setString(2, m.getJoinDate());
            ps.setString(3, m.getFirstName());
            ps.setString(4, m.getLastName());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getDateOfBirth());
            ps.setString(7, m.getGender());
            ps.setString(8, m.getUid());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) m.setMemberId(k.getInt(1)); }
            return m;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Member m) {
        String sql = "UPDATE Member SET UserID=?, JoinDate=?, FirstName=?, LastName=?, Email=?, DateOfBirth=?, Gender=?, UID=? WHERE MemberID=?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setObject(1, m.getUserId());
            ps.setString(2, m.getJoinDate());
            ps.setString(3, m.getFirstName());
            ps.setString(4, m.getLastName());
            ps.setString(5, m.getEmail());
            ps.setString(6, m.getDateOfBirth());
            ps.setString(7, m.getGender());
            ps.setString(8, m.getUid());
            ps.setInt(9, m.getMemberId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        try (PreparedStatement ps = Database.get().prepareStatement("DELETE FROM Member WHERE MemberID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}