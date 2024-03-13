package ru.maven.jborn.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.maven.jborn.dao.domain.CategoryDao;
import ru.maven.jborn.mappers.CategoryMapper;
import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;
    @Mock
    CategoryMapper categoryMapper;
    @Mock
    CategoryDao categoryDao;
    Category category;
    CategoryDto categoryDto;
    Integer id = 1;
    String nameCategory = "TestCategory";

    @Before
    public void setUp() {

        categoryDto = new CategoryDto();
        category = new Category();
        category.setId(id);
        category.setCategoryName(nameCategory);
        categoryDto.setId(id);
        categoryDto.setCategoryName(nameCategory);
    }

    @Test
    public void createCategory_Success() {
        Category newCategory = new Category();
        newCategory.setCategoryName(nameCategory);
        ;
        when(categoryDao.insert(newCategory)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.createCategory(nameCategory);

        assertEquals(result.getId(), categoryDto.getId());
        assertEquals(result.getCategoryName(), categoryDto.getCategoryName());
    }

    @Test
    public void createCategory_Duplicate() {
        Category newCategory = new Category();
        newCategory.setCategoryName("TestCategory");
        when(categoryDao.insert(newCategory)).thenReturn(null);
        when(categoryMapper.categoryToCategoryDto(null)).thenReturn(null);

        CategoryDto result = categoryService.createCategory(nameCategory);

        assertNull(result);
    }

    @Test
    public void findByAllCategory_Success() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        when(categoryDao.findByAll()).thenReturn(categoryList);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.findByAllCategory();

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getCategoryName(), nameCategory);
    }

    @Test
    public void findByAllCategory_isEmpty() {
        List<Category> categoryList = new ArrayList<>();
        when(categoryDao.findByAll()).thenReturn(categoryList);

        List<CategoryDto> result = categoryService.findByAllCategory();

        assertTrue(result.isEmpty());
    }

    @Test
    public void findCategoryById_Success() {
        when(categoryDao.findById(id)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        CategoryDto result = categoryService.findCategoryById(id);

        assertEquals(result.getId(), categoryDto.getId());
        assertEquals(result.getCategoryName(), categoryDto.getCategoryName());
    }

    @Test
    public void findCategoryById_NotFound() {
        when(categoryDao.findById(id)).thenReturn(new Category());

        CategoryDto result = categoryService.findCategoryById(id);

        assertNull(result.getId());
        assertNull(result.getCategoryName());
    }

    @Test
    public void removeCategoryById_Success() {
        when(categoryDao.delete(id)).thenReturn(true);

        boolean result = categoryService.removeCategoryById(id);

        assertTrue(result);
    }

    @Test
    public void removeCategoryById_NotFoundAndNotRemove() {
        when(categoryDao.delete(id)).thenReturn(false);

        boolean result = categoryService.removeCategoryById(id);

        assertFalse(result);
    }
}