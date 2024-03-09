package ru.maven.jborn.models;

public class Bill {
    private Integer id;
    private String nameAccount;
    private Integer userId;
    private Integer values;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String numberAccounts) {
        this.nameAccount = numberAccounts;
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
                ", nameAccount=" + nameAccount +
                ", userId=" + userId +
                ", values=" + values +
                '}';
    }
}
