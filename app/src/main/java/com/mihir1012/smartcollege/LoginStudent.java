package com.mihir1012.smartcollege;

public class LoginStudent {
    private Long EnrolStudent;

    public Long getEnrolStudent() {
        return EnrolStudent;
    }

    public void setEnrolStudent(Long enrolStudent) {
        EnrolStudent = enrolStudent;
    }

    public String getNameStudent() {
        return NameStudent;
    }

    public void setNameStudent(String nameStudent) {
        NameStudent = nameStudent;
    }

    public String getPasswordStudent() {
        return PasswordStudent;
    }

    public void setPasswordStudent(String passwordStudent) {
        PasswordStudent = passwordStudent;
    }

    private String NameStudent;
    private String PasswordStudent;
}
