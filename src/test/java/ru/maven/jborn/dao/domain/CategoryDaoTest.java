package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Category;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CategoryDaoTest {
    CategoryDao categoryDao;
    Category category;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        categoryDao = DaoFactory.getCategoryDao();

        category = new Category();
        category.setId(1);
        category.setCategoryName("CategoryName");
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByAll() {
    }

    @Test
    public void insert() {
        categoryDao.insert(category);
        assertEquals(Optional.of(1), Optional.ofNullable(categoryDao.findById(1).getId()));
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}