package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.Attendance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDao {
    public List<Attendance> findAll() {
        List<Attendance> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement("SELECT * FROM Attendance ORDER BY AttendanceID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance a = new Attendance();
                    a.setAttendanceId(rs.getInt("AttendanceID"));
                    a.setMemberId(rs.getInt("MemberID"));
                    a.setSessionId(rs.getInt("SessionID"));
                    a.setAttendanceDate(rs.getString("AttendanceDate"));
                    a.setStatus(rs.getString("Status"));
                    list.add(a);
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Attendance insert(Attendance a) {
        String sql = "INSERT INTO Attendance(MemberID, SessionID, AttendanceDate, Status) VALUES(?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getMemberId());
            ps.setInt(2, a.getSessionId());
            ps.setString(3, a.getAttendanceDate());
            ps.setString(4, a.getStatus());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) a.setAttendanceId(k.getInt(1)); }
            return a;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Attendance a) {
        String sql = "UPDATE Attendance SET MemberID=?, SessionID=?, AttendanceDate=?, Status=? WHERE AttendanceID=?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setInt(1, a.getMemberId());
            ps.setInt(2, a.getSessionId());
            ps.setString(3, a.getAttendanceDate());
            ps.setString(4, a.getStatus());
            ps.setInt(5, a.getAttendanceId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        try (PreparedStatement ps = Database.get().prepareStatement("DELETE FROM Attendance WHERE AttendanceID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}