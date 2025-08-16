package com.gymx.controller;

import com.gymx.dao.UserDao;
import com.gymx.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AuthController {
    @FXML private ToggleButton loginToggle;
    @FXML private ToggleButton signupToggle;
    @FXML private TextField first;
    @FXML private TextField last;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private ComboBox<String> role;
    @FXML private Label labelFirst;
    @FXML private Label labelLast;
    @FXML private Label labelRole;
    @FXML private Label status;

    private final UserDao userDao = new UserDao();

    @FXML
    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        loginToggle.setToggleGroup(group);
        signupToggle.setToggleGroup(group);
        loginToggle.setSelected(true);
        updateFormVisibility();
        group.selectedToggleProperty().addListener((obs, o, n) -> updateFormVisibility());
    }

    private void updateFormVisibility() {
        boolean isSignup = signupToggle.isSelected();
        first.setDisable(!isSignup);
        last.setDisable(!isSignup);
        role.setDisable(!isSignup);
        first.setManaged(isSignup);
        last.setManaged(isSignup);
        role.setManaged(isSignup);
        first.setVisible(isSignup);
        last.setVisible(isSignup);
        role.setVisible(isSignup);
        if (labelFirst != null) { labelFirst.setManaged(isSignup); labelFirst.setVisible(isSignup); }
        if (labelLast != null) { labelLast.setManaged(isSignup); labelLast.setVisible(isSignup); }
        if (labelRole != null) { labelRole.setManaged(isSignup); labelRole.setVisible(isSignup); }
    }

    @FXML
    public void onContinue(ActionEvent e) {
        String emailValue = email.getText() == null ? "" : email.getText().trim();
        String passwordValue = password.getText() == null ? "" : password.getText();
        if (emailValue.isEmpty() || passwordValue.isEmpty()) {
            status.setText("Email and password are required");
            return;
        }

        try {
            if (signupToggle.isSelected()) {
                String firstName = valueOrEmpty(first.getText());
                String lastName = valueOrEmpty(last.getText());
                String roleValue = role.getSelectionModel().getSelectedItem();
                if (firstName.isEmpty() || lastName.isEmpty() || roleValue == null) {
                    status.setText("Please fill all signup fields");
                    return;
                }
                if (userDao.findByEmail(emailValue) != null) {
                    status.setText("Email already registered");
                    return;
                }
                String hash = UserDao.sha256(passwordValue);
                User u = new User(null, firstName, lastName, emailValue, hash, roleValue);
                userDao.insert(u);
                status.setText("Account created. You can login now.");
                loginToggle.setSelected(true);
                updateFormVisibility();
                return;
            }

            User existing = userDao.findByEmail(emailValue);
            if (existing == null) {
                status.setText("No account found. Sign up.");
                return;
            }
            String hash = UserDao.sha256(passwordValue);
            if (!hash.equals(existing.getPasswordHash())) {
                status.setText("Invalid credentials");
                return;
            }
            proceedToApp();
        } catch (Exception ex) {
            status.setText("Error: " + ex.getMessage());
        }
    }

    private void proceedToApp() {
        try {
            Stage stage = (Stage) status.getScene().getWindow();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Scene scene = new Scene(fxml.load(), 1200, 800);
            scene.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception ex) {
            status.setText("Failed to open app");
        }
    }

    private static String valueOrEmpty(String s) { return s == null ? "" : s.trim(); }
}


