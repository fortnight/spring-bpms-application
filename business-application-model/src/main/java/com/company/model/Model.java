package com.company.model;


public class Model {

static final long serialVersionUID = 1L;

private Integer id;

private String firstName = "";
private String lastName = "";
private String fullName = "";

public Model() {
	    super();
}

public Model(String firstName, String lastName, String fullName) {
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

public void setId(Integer id) {
	    this.id = id;
}
public Integer getId() {
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
