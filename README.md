# GymX DBMS (JavaFX + SQLite)

A modern, stylish desktop app to manage a gym’s data. Built with JavaFX 17 and SQLite, featuring a neon-dark theme, authentication gateway (Login/Signup), and full CRUD for Members, Trainers, Sessions, Subscriptions, and Attendance.

## Table of Contents
- Features
- Tech Stack
- Project Structure
- Getting Started
- Run
- Database
- Usage
- Styling
- Security Notes
- Troubleshooting
- Contributing
- License

## Features
- Authentication gateway
  - Login and Signup with roles: ADMIN, TRAINER, STAFF
  - Passwords hashed with SHA-256
- Dashboard with tabs
  - Members, Trainers, Sessions, Subscriptions, Attendance
  - Full CRUD via simple forms + tables
- Modern dark theme
  - High-contrast tab headers, neon cyan accents, polished controls
- SQLite storage
  - DB auto-created at data/gym.db
  - Schema auto-applied from resources/schema.sql

## Tech Stack
- Java 17
- JavaFX 17 (controls, fxml)
- SQLite via sqlite-jdbc
- Maven

## Project Structure
```text
src/
  main/
    java/
      com/gymx/
        MainApp.java                # App entry (starts at auth)
        controller/
          AuthController.java
          MembersController.java
          TrainersController.java
          SessionsController.java
          SubscriptionsController.java
          AttendanceController.java
        dao/
          UserDao.java
          MemberDao.java
          TrainerDao.java
          SessionDao.java
          SubscriptionDao.java
          AttendanceDao.java
        db/
          Database.java             # SQLite bootstrap + schema apply
        model/
          User.java
          Member.java
          Trainer.java
          Session.java
          Subscription.java
          Attendance.java
      module-info.java
    resources/
      fxml/
        auth-view.fxml
        dashboard.fxml
        members-view.fxml
        trainers-view.fxml
        sessions-view.fxml
        subscriptions-view.fxml
        attendance-view.fxml
      styles/
        app.css                     # Dark neon theme
      schema.sql                    # CREATE TABLE IF NOT EXISTS
data/
  gym.db                            # Created on first run
```

## Getting Started
Prerequisites:
- JDK 17
- Maven 3.8+ (optional if running from IDE)

Clone and build:
```bash
git clone <YOUR_REPO_URL>
cd Database
mvn -DskipTests package
```

## Run
Recommended (IntelliJ IDEA):
- Open the project
- Set Project SDK to JDK 17
- Run configuration: Main class `com.gymx.MainApp`, module `com.gymx`
- Run. On first launch:
  - Choose “Sign up” to create your first user
  - Switch to “Login” to enter the app


## Database
- SQLite database at `data/gym.db` (auto-created)
- `schema.sql` is applied at startup and is idempotent
- To reset, close the app and delete `data/gym.db`

Tables (high-level):
- `User(UserID, FirstName, LastName, Email [UNIQUE], PasswordHash, Role [ADMIN|TRAINER|STAFF])`
- `Member(MemberID, UserID, JoinDate, FirstName, LastName, Email [UNIQUE], DateOfBirth, Gender [M|F|O], UID)`
- `Trainer(TrainerID, Phone, Specialty, FirstName, LastName, Email [UNIQUE])`
- `Session(SessionID, StartTime, EndTime, Title, TrainerID, MaxCapacity)`
- `Subscription(SubscriptionID, MemberID, StartDate, EndDate, AmountPaid, SubscriptionType)`
- `Attendance(AttendanceID, MemberID, SessionID, AttendanceDate, Status [BOOKED|ATTENDED|MISSED|CANCELLED])`

## Usage
- Sign up or Login on the auth screen
  - When “Login” is selected, signup-only fields (First/Last/Role) hide automatically
- Use top tabs to manage each entity
- Tables show data and placeholders when empty
- Forms allow Add/Update/Delete; status labels provide feedback

## Styling
- Global theme in `resources/styles/app.css`
- Accent color: `#22d3ee` (cyan), with dark backgrounds for high contrast
- Styled components: `TabPane`, `TableView`, `TextField`, `PasswordField`, `ComboBox`, `Button`, `ToggleButton`, scrollbars
- To retheme, adjust `-fx-accent`, backgrounds, and radii in `app.css`

## Security Notes
- Passwords are hashed with SHA-256 (`UserDao.sha256`)
- For production, replace with a salted password KDF (e.g., bcrypt/scrypt/Argon2), add rate limiting, and secure storage

## Troubleshooting
- SLF4J “StaticLoggerBinder” warning: harmless (no-op logger)
- JavaFX module path issues (CLI):
  - Ensure JavaFX modules are on the `--module-path`, and `--add-modules` includes `javafx.controls,javafx.fxml`
- “mvn not found”:
  - Install Maven or run directly from IDE
- UI contrast issues:
  - This app targets a dark theme; avoid overriding styles unless you also adjust text colors
 
-- Found Something? DM me on Twitter @0xtkmo

# Contributing
- Fork the repository and create a feature branch
- Follow clear naming and concise methods; prefer early returns and avoid deep nesting
- Submit a pull request with a brief description and screenshots/GIFs for UI changes

