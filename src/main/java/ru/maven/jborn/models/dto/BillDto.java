package ru.maven.jborn.models.dto;

import java.util.Objects;

public class BillDto {

    private Integer id;
    private String nameAccount;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDto billDto = (BillDto) o;
        return Objects.equals(id, billDto.id) && Objects.equals(nameAccount, billDto.nameAccount) && Objects.equals(values, billDto.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameAccount, values);
    }
}
