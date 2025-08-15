package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.Trainer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainerDao {
    public List<Trainer> findAll() {
        List<Trainer> list = new ArrayList<>();
        try (PreparedStatement ps = Database.get().prepareStatement("SELECT * FROM Trainer ORDER BY TrainerID")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Trainer t = new Trainer();
                    t.setTrainerId(rs.getInt("TrainerID"));
                    t.setPhone(rs.getString("Phone"));
                    t.setSpecialty(rs.getString("Specialty"));
                    t.setFirstName(rs.getString("FirstName"));
                    t.setLastName(rs.getString("LastName"));
                    t.setEmail(rs.getString("Email"));
                    list.add(t);
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return list;
    }

    public Trainer insert(Trainer t) {
        String sql = "INSERT INTO Trainer(Phone, Specialty, FirstName, LastName, Email) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getPhone());
            ps.setString(2, t.getSpecialty());
            ps.setString(3, t.getFirstName());
            ps.setString(4, t.getLastName());
            ps.setString(5, t.getEmail());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) t.setTrainerId(k.getInt(1)); }
            return t;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void update(Trainer t) {
        String sql = "UPDATE Trainer SET Phone=?, Specialty=?, FirstName=?, LastName=?, Email=? WHERE TrainerID=?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setString(1, t.getPhone());
            ps.setString(2, t.getSpecialty());
            ps.setString(3, t.getFirstName());
            ps.setString(4, t.getLastName());
            ps.setString(5, t.getEmail());
            ps.setInt(6, t.getTrainerId());
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void delete(int id) {
        try (PreparedStatement ps = Database.get().prepareStatement("DELETE FROM Trainer WHERE TrainerID=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}