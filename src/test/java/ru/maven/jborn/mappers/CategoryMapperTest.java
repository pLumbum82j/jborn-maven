package ru.maven.jborn.mappers;

import org.junit.Before;
import org.junit.Test;
import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.dto.CategoryDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CategoryMapperTest {
    CategoryMapper categoryMapper;
    Category category;

    @Before
    public void setUp() {
        categoryMapper = new CategoryMapper();
        category = new Category();
    }

    @Test
    public void categoryToCategoryDto_Success() {
        category.setId(1);
        category.setCategoryName("CategoryName");

        CategoryDto result = categoryMapper.categoryToCategoryDto(category);

        assertEquals(result.getId(), category.getId());
        assertEquals(result.getCategoryName(), category.getCategoryName());
    }

    @Test
    public void categoryToCategoryDto_FieldsNull() {
        CategoryDto result = categoryMapper.categoryToCategoryDto(null);

        assertNull(result.getId());
        assertNull(result.getCategoryName());
    }
}