package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Bill;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillDao implements Dao<Bill, Integer> {
    private static DataSource dataSource;

    public BillDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Bill findById(Integer id) {
        Bill bill = new Bill();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select * from  bill where id =?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bill.setId(rs.getInt("id"));
                bill.setNameAccount(rs.getString("name_account"));
                bill.setId(rs.getInt("user_id"));
                bill.setValues(rs.getInt("values"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bill;
    }

    @Override
    public List<Bill> findByAll() {
        List<Bill> resultAllBill = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from bill");
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setUserId(rs.getInt("user_id"));
                bill.setNameAccount(rs.getString("name_account"));
                bill.setValues(rs.getInt("values"));
                resultAllBill.add(bill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultAllBill;
    }

    @Override
    public Bill insert(Bill bill) {
        Bill resultBill = new Bill();
        try (Connection connection = dataSource.getConnection()) {
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
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("update bill set values = ? where id = ?");
            ps.setInt(1, bill.getValues());
            ps.setInt(2, bill.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bill;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection
                    .prepareStatement("delete from bill where id = ?");
            ps.setInt(1, id);
            result = ps.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Map<Integer, String> checkDuplicateInvoiceAndCount(Bill bill) {
        Map<Integer, String> resultAccount = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id, name_account from bill where user_id = ?");
            ps.setInt(1, bill.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name_account");
                resultAccount.put(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultAccount;
    }

    public Integer getBillId(Bill bill) {
        Integer result = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id from bill where name_account = ? and user_id = ?");
            ps.setString(1, bill.getNameAccount());
            ps.setInt(2, bill.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
