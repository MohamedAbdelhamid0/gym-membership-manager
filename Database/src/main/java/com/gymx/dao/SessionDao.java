package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionDao {
    public List<Session> findAll() {
        List<Session> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement("SELECT * FROM Session ORDER BY SessionID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Session s = new Session();
                    s.setSessionId(rs.getInt("SessionID"));
                    s.setStartTime(rs.getString("StartTime"));
                    s.setEndTime(rs.getString("EndTime"));
                    s.setTitle(rs.getString("Title"));
                    s.setTrainerId(rs.getInt("TrainerID"));
                    s.setMaxCapacity(rs.getInt("MaxCapacity"));
                    list.add(s);
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Session insert(Session s) {
        String sql = "INSERT INTO Session(StartTime, EndTime, Title, TrainerID, MaxCapacity) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getStartTime());
            ps.setString(2, s.getEndTime());
            ps.setString(3, s.getTitle());
            ps.setInt(4, s.getTrainerId());
            ps.setInt(5, s.getMaxCapacity());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) s.setSessionId(k.getInt(1)); }
            return s;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Session s) {
        String sql = "UPDATE Session SET StartTime=?, EndTime=?, Title=?, TrainerID=?, MaxCapacity=? WHERE SessionID=?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setString(1, s.getStartTime());
            ps.setString(2, s.getEndTime());
            ps.setString(3, s.getTitle());
            ps.setInt(4, s.getTrainerId());
            ps.setInt(5, s.getMaxCapacity());
            ps.setInt(6, s.getSessionId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        try (PreparedStatement ps = Database.get().prepareStatement("DELETE FROM Session WHERE SessionID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}