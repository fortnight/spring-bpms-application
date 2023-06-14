package com.company.model;

public class PatientApplication implements java.io.Serializable {

static final long serialVersionUID = 1L;

private Long id;
//PROCESS ID
private long pid; 
private String firstName = "";
private String lastName = "";
private String fullName = "";

public PatientApplication() {
    super();
}

public PatientApplication(long pid, String firstName, String lastName, String fullName) {
    super();
    this.pid = pid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = fullName;
}

public String toString() {
    return String.format(
        "Model[id=%d, pid=%d, firstName='%s', lastName='%s',fullName='%s']",
        id, pid, firstName, lastName, fullName);
}

public long getPid() {
    return pid;
}

public void setPid(long pid) {
    this.pid = pid;
}

public void setId(Long id) {
    this.id = id;
}
public Long getId() {
    return id;
}
public String getFirstName() {
    return firstName;
}
public void setFirstName(String firstName) {
    this.firstName = firstName;
}
public String getLastName() {
    return lastName;
}
public void setLastName(String lastName) {
    this.lastName = lastName;
}
public String getFullName() {
    return fullName;
}
public void setFullName(String fullName) {
    this.fullName = fullName;
}

}
