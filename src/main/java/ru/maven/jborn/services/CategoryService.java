package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.CategoryDao;
import ru.maven.jborn.mappers.CategoryMapper;
import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.dto.CategoryDto;

public class CategoryService {

    CategoryMapper categoryMapper = new CategoryMapper();
    CategoryDao categoryDao = CategoryDao.getCategoryDao();

    public CategoryDto createCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return categoryMapper.categoryToCategoryDto(categoryDao.insert(category));
    }

}
