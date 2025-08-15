package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.Subscription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDao {
    public List<Subscription> findAll() {
        List<Subscription> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement("SELECT * FROM Subscription ORDER BY SubscriptionID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subscription s = new Subscription();
                    s.setSubscriptionId(rs.getInt("SubscriptionID"));
                    s.setMemberId(rs.getInt("MemberID"));
                    s.setStartDate(rs.getString("StartDate"));
                    s.setEndDate(rs.getString("EndDate"));
                    s.setAmountPaid(rs.getDouble("AmountPaid"));
                    s.setSubscriptionType(rs.getString("SubscriptionType"));
                    list.add(s);
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Subscription insert(Subscription s) {
        String sql = "INSERT INTO Subscription(MemberID, StartDate, EndDate, AmountPaid, SubscriptionType) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getMemberId());
            ps.setString(2, s.getStartDate());
            ps.setString(3, s.getEndDate());
            ps.setDouble(4, s.getAmountPaid());
            ps.setString(5, s.getSubscriptionType());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) s.setSubscriptionId(k.getInt(1)); }
            return s;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Subscription s) {
        String sql = "UPDATE Subscription SET MemberID=?, StartDate=?, EndDate=?, AmountPaid=?, SubscriptionType=? WHERE SubscriptionID=?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setInt(1, s.getMemberId());
            ps.setString(2, s.getStartDate());
            ps.setString(3, s.getEndDate());
            ps.setDouble(4, s.getAmountPaid());
            ps.setString(5, s.getSubscriptionType());
            ps.setInt(6, s.getSubscriptionId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        try (PreparedStatement ps = Database.get().prepareStatement("DELETE FROM Subscription WHERE SubscriptionID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}