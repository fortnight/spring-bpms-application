package com.company.model;

public class Patient implements java.io.Serializable {

    static final long serialVersionUID = 1L;

private Long id;
    
private String firstName = "";
private String lastName = "";
private String fullName = "";

public Patient() {
    super();
}

public Patient(String firstName, String lastName, String fullName) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = fullName;
}

public String toString() {
    return String.format(
        "Model[id=%d, firstName='%s', lastName='%s',fullName='%s']",
        id, firstName, lastName, fullName);
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
