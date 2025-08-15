package com.gymx.model;

public class Member {
    private Integer memberId;
    private Integer userId;
    private String joinDate;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String uid;

    public Member() {}
    public Member(Integer memberId, Integer userId, String joinDate, String firstName, String lastName,
                  String email, String dateOfBirth, String gender, String uid) {
        this.memberId = memberId; this.userId = userId; this.joinDate = joinDate;
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.dateOfBirth = dateOfBirth; this.gender = gender; this.uid = uid;
    }
    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
}