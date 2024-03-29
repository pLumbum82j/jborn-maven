package ru.maven.jborn.dao.domain;

import ru.maven.jborn.dao.Dao;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements Dao<Category, Integer> {
    private static DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private CategoryDao() {
    }

    @Override
    public Category findById(Integer id) {
        Category category = new Category();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("select id, category_name from spending_category where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public List<Category> findByAll() {
        List<Category> resultListCategory = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select id, category_name from spending_category");
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setCategoryName(rs.getString("category_name"));
                resultListCategory.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultListCategory;
    }

    @Override
    public Category insert(Category category) {
        Category checkDuplicateCategory = findByName(category);
        if (checkDuplicateCategory.getId() != null) {
            return new Category();
        }

        try (Connection connection = dataSource.getConnection()) {
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
    public boolean delete(Integer id) {
        boolean result = false;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("delete from spending_category where id = ?");
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

    private Category findByName(Category category) {
        Category resultCategory = new Category();
        try (Connection connection = dataSource.getConnection()) {
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
