package com.gymx.dao;

import com.gymx.db.Database;
import com.gymx.model.User;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public User findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE Email = ?";
        try (PreparedStatement ps = Database.get().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("PasswordHash"),
                            rs.getString("Role")
                    );
                }
            }
        } catch (Exception e) { throw new RuntimeException(e); }
        return null;
    }

    public User insert(User u) {
        String sql = "INSERT INTO User(FirstName, LastName, Email, PasswordHash, Role) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = Database.get().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPasswordHash());
            ps.setString(5, u.getRole());
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) { if (k.next()) u.setUserId(k.getInt(1)); }
            return u;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String sha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(value.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}


