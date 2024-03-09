package ru.maven.jborn.mappers;

import ru.maven.jborn.models.Category;
import ru.maven.jborn.models.dto.CategoryDto;

public class CategoryMapper {

    public CategoryDto categoryToCategoryDto(Category category) {
        CategoryDto resultcategoryDto = new CategoryDto();
        if (category == null) {
            return resultcategoryDto;
        }
        resultcategoryDto.setId(category.getId());
        resultcategoryDto.setCategoryName(category.getCategoryName());
        return resultcategoryDto;
    }
}
