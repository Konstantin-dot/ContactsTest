package io.sinergy.contactsbot.model;

import java.util.UUID;

public class Contact {
    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;

    public Contact(String lastName, String firstName, String middleName, String phone) {
        this.id = UUID.randomUUID().toString();
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, %s", lastName, firstName, middleName, phone);
    }
}