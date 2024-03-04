package ru.maven.jborn.models;

public class Bill {
    private Integer id;
    private Integer numberAccounts;
    private Integer userId;
    private Integer values;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberAccounts() {
        return numberAccounts;
    }

    public void setNumberAccounts(Integer numberAccounts) {
        this.numberAccounts = numberAccounts;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getValues() {
        return values;
    }

    public void setValues(Integer values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", numberAccounts=" + numberAccounts +
                ", userId=" + userId +
                ", values=" + values +
                '}';
    }
}
