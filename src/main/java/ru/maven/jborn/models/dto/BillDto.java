package ru.maven.jborn.models.dto;

public class BillDto {

    public Integer id;
    public Integer numberAccounts;

    public Integer values;

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
                ", numberAccounts=" + numberAccounts +
                ", values=" + values +
                '}';
    }
}
