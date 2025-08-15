PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS User (
                                    UserID INTEGER PRIMARY KEY AUTOINCREMENT,
                                    FirstName TEXT NOT NULL,
                                    LastName TEXT NOT NULL,
                                    Email TEXT NOT NULL UNIQUE,
                                    PasswordHash TEXT NOT NULL,
                                    Role TEXT NOT NULL CHECK(Role IN ('ADMIN','TRAINER','STAFF'))
    );

CREATE TABLE IF NOT EXISTS Member (
                                      MemberID INTEGER PRIMARY KEY AUTOINCREMENT,
                                      UserID INTEGER,
                                      JoinDate TEXT NOT NULL,
                                      FirstName TEXT NOT NULL,
                                      LastName TEXT NOT NULL,
                                      Email TEXT NOT NULL UNIQUE,
                                      DateOfBirth TEXT,
                                      Gender TEXT CHECK(Gender IN ('M','F','O')),
    UID TEXT,
    FOREIGN KEY(UserID) REFERENCES User(UserID) ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS Mem_Phone (
                                         MemberID INTEGER NOT NULL,
                                         MPhone   TEXT NOT NULL,
                                         PRIMARY KEY (MemberID, MPhone),
    FOREIGN KEY(MemberID) REFERENCES Member(MemberID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Trainer (
                                       TrainerID INTEGER PRIMARY KEY AUTOINCREMENT,
                                       Phone TEXT,
                                       Specialty TEXT,
                                       FirstName TEXT NOT NULL,
                                       LastName  TEXT NOT NULL,
                                       Email     TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Session (
                                       SessionID   INTEGER PRIMARY KEY AUTOINCREMENT,
                                       StartTime   TEXT NOT NULL,
                                       EndTime     TEXT NOT NULL,
                                       Title       TEXT NOT NULL,
                                       TrainerID   INTEGER NOT NULL,
                                       MaxCapacity INTEGER NOT NULL,
                                       FOREIGN KEY(TrainerID) REFERENCES Trainer(TrainerID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Subscription (
                                            SubscriptionID INTEGER PRIMARY KEY AUTOINCREMENT,
                                            MemberID INTEGER NOT NULL,
                                            StartDate TEXT NOT NULL,
                                            EndDate   TEXT NOT NULL,
                                            AmountPaid REAL NOT NULL,
                                            SubscriptionType TEXT NOT NULL,
                                            FOREIGN KEY(MemberID) REFERENCES Member(MemberID) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Attendance (
                                          AttendanceID INTEGER PRIMARY KEY AUTOINCREMENT,
                                          MemberID INTEGER NOT NULL,
                                          SessionID INTEGER NOT NULL,
                                          AttendanceDate TEXT NOT NULL,
                                          Status TEXT NOT NULL CHECK(Status IN ('BOOKED','ATTENDED','MISSED','CANCELLED')),
    UNIQUE(MemberID, SessionID, AttendanceDate),
    FOREIGN KEY(MemberID) REFERENCES Member(MemberID) ON DELETE CASCADE,
    FOREIGN KEY(SessionID) REFERENCES Session(SessionID) ON DELETE CASCADE
    );