package ru.maven.jborn.services;

import ru.maven.jborn.dao.domain.CategoryDao;
import ru.maven.jborn.mappers.CategoryMapper;
import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.dto.CategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryDao categoryDao;

    public CategoryService(CategoryMapper categoryMapper, CategoryDao categoryDao) {
        this.categoryMapper = categoryMapper;
        this.categoryDao = categoryDao;
    }

    public CategoryDto createCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return categoryMapper.categoryToCategoryDto(categoryDao.insert(category));
    }

    public List<CategoryDto> findByAllCategory() {
        List<Category> tempListAllCategory = categoryDao.findByAll();
        if (tempListAllCategory.isEmpty()) {
            return new ArrayList<>();
        } else {
            return tempListAllCategory.stream().map(categoryMapper::categoryToCategoryDto).collect(Collectors.toList());
        }
    }

    public CategoryDto findCategoryById(Integer id) {
        Category resultCategory = categoryDao.findById(id);
        if (resultCategory.getId() == null) {
            return new CategoryDto();
        } else {
            return categoryMapper.categoryToCategoryDto(resultCategory);
        }
    }

    public boolean removeCategoryById(Integer id) {
        return categoryDao.delete(id);
    }
}
