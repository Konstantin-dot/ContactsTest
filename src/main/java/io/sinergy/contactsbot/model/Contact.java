package io.sinergy.contactsbot.model;

public class Contact {
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;

    public Contact(String lastName, String firstName, String middleName, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, %s", lastName, firstName, middleName, phone);
    }
}
