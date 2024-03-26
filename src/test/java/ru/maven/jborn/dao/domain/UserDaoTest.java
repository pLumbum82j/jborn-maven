package ru.maven.jborn.dao.domain;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.dao.DaoFactory;
import ru.maven.jborn.models.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoTest {

    UserDao userDao;
    BillDao billDao;
    User user;

    @Before
    public void setUp() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUsername", "sa");
        System.setProperty("jdbcPassword", "");

        userDao = DaoFactory.getUserDao();
        billDao = DaoFactory.getBillDao();

        billDao.findByAll().forEach(billElement -> billDao.delete(billElement.getId()));
        userDao.findByAll().forEach(person -> userDao.delete(person.getId()));

        user = new User();
        user.setLogin("testLogin");
        user.setFirstName("firstNameTest");
        user.setLastName("lastNameTest");
        user.setEmail("test@mail.ru");
        user.setPassword("123456");
    }

    @Test
    public void findById_Success() {
        User tempUser = userDao.insert(user);

        User result = userDao.findById(tempUser.getId());

        assertEquals(result.getFirstName(), user.getFirstName());
        assertEquals(result.getLastName(), user.getLastName());
        assertEquals(result.getEmail(), user.getEmail());
    }

    @Test
    public void findByAll_Success() {
        userDao.insert(user);

        List<User> result = userDao.findByAll();

        assertEquals(1, result.size());
        assertEquals(result.get(0).getLogin(), user.getLogin());
    }

    @Test
    public void findByAll_Empty() {
        List<User> result = userDao.findByAll();

        assertTrue(result.isEmpty());
    }

    @Test
    public void insert_Success() {
        User result = userDao.insert(user);

        assertEquals(result.getLogin(), user.getLogin());
        assertEquals(result.getFirstName(), user.getFirstName());
    }

    @Test
    public void delete_Success() {
        User tempUser = userDao.insert(user);

        boolean result = userDao.delete(tempUser.getId());

        assertTrue(result);
    }

    @Test
    public void delete_NotFound() {
        boolean result = userDao.delete(999);

        assertFalse(result);
    }

    @Test
    public void duplicateCheck_Found() {
        userDao.insert(user);

        Integer result = userDao.duplicateCheck(user);

        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void getUser_Success() {
        userDao.insert(user);

        User result = userDao.getUser(user.getLogin(), user.getPassword());

        assertEquals(result.getLogin(), user.getLogin());
        assertEquals(result.getFirstName(), user.getFirstName());
        assertEquals(result.getLastName(), user.getLastName());
    }

    @Test
    public void getUser_NotFound() {
        User result = userDao.getUser(user.getLogin(), user.getPassword());

        assertNull(result.getLogin());
        assertNull(result.getFirstName());
    }
}