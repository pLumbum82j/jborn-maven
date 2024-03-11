package ru.maven.jborn.models;

import java.util.Date;
import java.util.Objects;

public class Transaction {
   private Integer id;
   private Date date;
   private String nameAccount;
   private Integer values;
   private String categoryName;
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

   public Integer getValues() {
      return values;
   }

   public void setValues(Integer values) {
      this.values = values;
   }

   public String getCategoryName() {
      return categoryName;
   }

   public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
   }

   @Override
   public String toString() {
      return "Transaction{" +
              "id=" + id +
              ", date=" + date +
              ", nameAccount=" + nameAccount +
              ", values=" + values +
              ", spendingCategoryName=" + categoryName +
              ", userId=" + userId +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Transaction that = (Transaction) o;
      return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(nameAccount, that.nameAccount) && Objects.equals(values, that.values) && Objects.equals(categoryName, that.categoryName) && Objects.equals(userId, that.userId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, date, nameAccount, values, categoryName, userId);
   }
}
