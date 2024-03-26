package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CategoryDaoTest {
    CategoryDao categoryDao;
    Category category;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        categoryDao = DaoFactory.getCategoryDao();

        categoryDao.findByAll().forEach(cat -> categoryDao.delete(cat.getId()));

        category = new Category();
        category.setId(1);
        category.setCategoryName("CategoryName");
    }


    @Test
    public void createCategory_Success() {
        Category result = categoryDao.insert(category);

        assertNotNull(result.getId());
        assertEquals(result.getCategoryName(), category.getCategoryName());

    }

    @Test
    public void findById_Success() {
        Category tempCategory = categoryDao.insert(category);

        Category result = categoryDao.findById(tempCategory.getId());

        assertEquals(category.getCategoryName(), result.getCategoryName());

    }

    @Test
    public void findById_NotFound() {
        categoryDao.insert(category);

        Category result = categoryDao.findById(999);

        assertNull(result.getId());
        assertNull(result.getCategoryName());

    }

    @Test
    public void findByAll_Empty() {
        List<Category> result = categoryDao.findByAll();

        assertTrue(result.isEmpty());

    }

    @Test
    public void findByAll_Success() {
        categoryDao.insert(category);

        List<Category> result = categoryDao.findByAll();

        assertEquals(Optional.of(1), Optional.ofNullable(categoryDao.findById(1).getId()));
        assertEquals(1, result.size());
    }


    @Test
    public void createCategory_Duplicate() {
        //Непонятно как залезть в findByName
        //Наверное проверка должна быть на уровне Service перед передачей в DAO слой
    }
}