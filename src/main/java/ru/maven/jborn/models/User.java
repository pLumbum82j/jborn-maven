package ru.maven.jborn.models;

import java.util.Map;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private Map<String, Integer> account;

    public User(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public User(String firstName, String lastName, String login, String email, String password, Map<String, Integer> account) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, Integer> getAccount() {
        return account;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(Map<String, Integer> account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", account=" + account +
                '}';
    }
}
