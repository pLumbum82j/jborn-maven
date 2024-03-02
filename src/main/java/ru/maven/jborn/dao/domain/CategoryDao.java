package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao implements Dao<Category, Integer> {
    private static CategoryDao categoryDao;

    public static CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }

    private CategoryDao() {
    }

    @Override
    public Category findById(Integer integer) {
        return null;
    }

    @Override
    public List<Category> findByAll() {
        return null;
    }

    @Override
    public Category insert(Category category) {
        Category checkDuplicateCategory = findByName(category);
        if (checkDuplicateCategory.getId() != null) {
            return new Category();
        }

        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("insert into spending_category(category_name) values (?)");
            ps.setString(1, category.getCategoryName());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return findByName(category);
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    private Category findByName(Category category) {
        Category resultCategory = new Category();
        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id from spending_category where category_name = ?");
            ps.setString(1, category.getCategoryName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                resultCategory.setId(rs.getInt("id"));
                resultCategory.setCategoryName(category.getCategoryName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultCategory;
    }
}
