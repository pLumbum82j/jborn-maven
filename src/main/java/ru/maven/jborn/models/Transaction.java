package ru.maven.jborn.models;

import java.util.Date;

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
}
