package ru.maven.jborn.models.dto;

public class BillDto {

    public Integer id;
    public String nameAccount;

    public Integer values;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAccounts() {
        return nameAccount;
    }

    public void setNameAccounts(String numberAccounts) {
        this.nameAccount = numberAccounts;
    }

    public Integer getValues() {
        return values;
    }

    public void setValues(Integer values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "BillDto{" +
                "id=" + id +
                ", nameAccount=" + nameAccount +
                ", values=" + values +
                '}';
    }
}
