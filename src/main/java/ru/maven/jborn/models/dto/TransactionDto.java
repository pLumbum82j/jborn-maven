package ru.maven.jborn.models.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class TransactionDto {
    private Integer id;
    private Date date;
    private String nameAccount;
    //private Integer values;
    private BigDecimal values;
    private String spendingCategoryName;
    private Integer userId;

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

    public BigDecimal getValues() {
        return values;
    }

    public void setValues(BigDecimal values) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(nameAccount, that.nameAccount) && Objects.equals(values, that.values) && Objects.equals(spendingCategoryName, that.spendingCategoryName) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, nameAccount, values, spendingCategoryName, userId);
    }
}
