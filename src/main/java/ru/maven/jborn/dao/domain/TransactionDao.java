package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDao implements Dao<Transaction, Integer> {
    private static TransactionDao transactionDao;
    private final CategoryDao categoryDao = CategoryDao.getCategoryDao();

    public static TransactionDao getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDao();
        }
        return transactionDao;
    }

    private TransactionDao() {
    }


    @Override
    public Transaction findById(Integer id) {
        Transaction transaction = new Transaction();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select tr.id, tr.date, b.name_account, tr.values, sc.category_name, u.id as userId " +
                    "from transaction as tr " +
                    "join public.bill b on b.id = tr.name_account_id " +
                    "join public.spending_category sc on sc.id = tr.spending_category_id " +
                    "join public.users u on b.user_id = u.id " +
                    "where tr.id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getDate("date"));
                transaction.setNameAccount(rs.getString("name_account"));
                transaction.setValues(rs.getInt("values"));
                transaction.setCategoryName(rs.getString("category_name"));
                transaction.setUserId(rs.getInt("userId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    @Override
    public List<Transaction> findByAll() {
        List<Transaction> transactionsAll = new ArrayList<>();
        try (Connection connection = DaoFactory.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select tr.id, tr.date, b.name_account, tr.values, sp.category_name, u.id as userid\n" +
                    "from transaction as tr\n" +
                    "join spending_category as sp ON tr.spending_category_id = sp.id\n" +
                    "join bill as b ON b.id = tr.name_account_id\n" +
                    "join users as u ON b.user_id = u.id;");
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setDate(rs.getDate("date"));
                transaction.setNameAccount(rs.getString("name_account"));
                transaction.setValues(rs.getInt("values"));
                transaction.setCategoryName(rs.getString("category_name"));
                transaction.setUserId(rs.getInt("userid"));
                transactionsAll.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionsAll;
    }

    @Override
    public Transaction insert(Transaction transaction) {

        int nameAccountId = getNameAccountId(transaction);

        Integer onlyCategory = categoryDao.findByAll().stream()
                .filter(x -> x.getCategoryName().equals(transaction.getCategoryName()))
                .findFirst().get().getId();

        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection
                    .prepareStatement("insert into transaction(DATE, NAME_ACCOUNT_ID, VALUES, SPENDING_CATEGORY_ID) VALUES (?,?,?,?)");
            ps.setObject(1, transaction.getDate(), Types.DATE);
            ps.setInt(2, nameAccountId);
            ps.setInt(3, transaction.getValues());
            ps.setInt(4, onlyCategory);
            ps.executeUpdate();

            PreparedStatement psId = connection.prepareStatement("select id from transaction " +
                    "where date = ? and name_account_id = ? and values = ?");
            psId.setObject(1, transaction.getDate(), Types.DATE);
            psId.setInt(2, nameAccountId);
            psId.setInt(3, transaction.getValues());
            ResultSet rs = psId.executeQuery();
            while (rs.next()) {
                transaction.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from transaction where id =?");
            ps.setInt(1, id);
            int check = ps.executeUpdate();
            if (check == 1) {
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private int getNameAccountId(Transaction transaction) {
        Map<String, Integer> billUser = new HashMap<>();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select b. id, b.name_account from bill as b " +
                    "join users as u on b.user_id = u.id where u.id = ?");
            ps.setInt(1, transaction.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nameAccount = rs.getString("name_account");
                Integer idAccount = rs.getInt("id");
                billUser.put(nameAccount, idAccount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int nameAccountId = 0;
        for (Map.Entry<String, Integer> ser : billUser.entrySet()) {
            if (ser.getKey().equals(transaction.getNameAccount())) {
                nameAccountId = ser.getValue();
            }
        }
        return nameAccountId;
    }
}

