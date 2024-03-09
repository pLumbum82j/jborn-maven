package ru.maven.jborn.models.dto;

import java.util.Date;

public class TransactionDto {
    Integer id;
    Date date;
    String nameAccount;
    Integer values;
    String spendingCategoryName;
    Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public Integer getValues() {
        return values;
    }

    public void setValues(Integer values) {
        this.values = values;
    }

    public String getSpendingCategoryName() {
        return spendingCategoryName;
    }

    public void setSpendingCategoryName(String spendingCategoryName) {
        this.spendingCategoryName = spendingCategoryName;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", date=" + date +
                ", nameAccount=" + nameAccount +
                ", values=" + values +
                ", spendingCategoryIName=" + spendingCategoryName +
                '}';
    }
}
