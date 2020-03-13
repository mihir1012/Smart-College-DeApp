package com.mihir1012.smartcollege;

public class StudentInformation {
    private String FullNameStudent;
    private String EmailStudent;
    private Long EnrolmentStudent;
    private Long Contact;
    private String Branch;
    private Integer yearStudent;
    private String AreaofInterest;

    public String getEmailStudent() {
        return EmailStudent;
    }

    public void setEmailStudent(String emailStudent) {
        EmailStudent = emailStudent;
    }

    public Long getEnrolmentStudent() {
        return EnrolmentStudent;
    }

    public void setEnrolmentStudent(Long enrolmentStudent) {
        EnrolmentStudent = enrolmentStudent;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public Integer getYearStudent() {
        return yearStudent;
    }

    public void setYearStudent(Integer yearStudent) {
        this.yearStudent = yearStudent;
    }

    public String getAreaofInterest() {
        return AreaofInterest;
    }

    public void setAreaofInterest(String areaofInterest) {
        AreaofInterest = areaofInterest;
    }

    public String getFullNameStudent() {
        return FullNameStudent;
    }

    public void setFullNameStudent(String fullNameStudent) {
        FullNameStudent = fullNameStudent;
    }
    public Long getContact() {
        return Contact;
    }

    public void setContact(Long contact) {
        Contact = contact;
    }


}
