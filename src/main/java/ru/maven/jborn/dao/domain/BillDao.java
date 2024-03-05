package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDao implements Dao<Bill, Integer> {

    public static BillDao billDao;

    public static BillDao getBillDao() {
        if (billDao == null) {
            billDao = new BillDao();
        }
        return billDao;
    }

    private BillDao() {

    }

    @Override
    public Bill findById(Integer integer) {
        return null;
    }

    @Override
    public List<Bill> findByAll() {
        return null;
    }

    @Override
    public Bill insert(Bill bill) {
        Bill resultBill = new Bill();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("insert into bill(name_account, user_id, values) values (?,?,?)");
            ps.setString(1, bill.getNameAccount());
            ps.setInt(2, bill.getUserId());
            ps.setInt(3, bill.getValues());
            ps.executeUpdate();
            PreparedStatement psGetId = connection.prepareStatement("select id from bill where user_id = ? and name_account = ?");
            psGetId.setInt(1, bill.getUserId());
            psGetId.setString(2, bill.getNameAccount());
            ResultSet rs = psGetId.executeQuery();
            while (rs.next()) {
                resultBill.setId(rs.getInt("id"));
                resultBill.setUserId(bill.getUserId());
                resultBill.setNameAccount(bill.getNameAccount());
                resultBill.setValues(bill.getValues());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultBill;
    }

    @Override
    public Bill update(Bill bill) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    public Map<Integer, String> checkDuplicateInvoiceAndCount(Bill bill) {
        Map<Integer, String> resultAccout = new HashMap<>();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id, name_account from bill where user_id = ?");
            ps.setInt(1, bill.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name_account");
                resultAccout.put(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultAccout;
    }
}
