package com.gymx.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public final class Database {
    private static Connection connection;
    private Database() {}

    public static synchronized Connection get() {
        if (connection == null) {
            try {
                Files.createDirectories(Path.of("data"));
                connection = DriverManager.getConnection("jdbc:sqlite:data/gym.db");
                try (Statement st = connection.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON");
                }
            } catch (Exception e) {
                throw new RuntimeException("Cannot open SQLite DB", e);
            }
        }
        return connection;
    }

    // Always apply schema.sql (uses CREATE TABLE IF NOT EXISTS, so it’s idempotent)
    public static void initialize() {
        get(); // open connection
        try (var in = Database.class.getResourceAsStream("/schema.sql")) {
            if (in == null) throw new IllegalStateException("schema.sql missing on classpath");
            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) sb.append(line).append('\n');
            }
            String[] statements = sb.toString().split(";\\s*(?:\\r?\\n|$)");
            for (String s : statements) {
                String stmt = s.trim();
                if (stmt.isEmpty()) continue;
                try (Statement st = get().createStatement()) {
                    st.execute(stmt);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply schema.sql", e);
        }
    }
}