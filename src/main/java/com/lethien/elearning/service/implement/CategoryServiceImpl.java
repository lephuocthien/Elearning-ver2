/**
 * Dec 18, 2020
 * 4:51:23 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.CategoryDto;
import com.lethien.elearning.entity.Category;
import com.lethien.elearning.repository.CategoryRepository;
import com.lethien.elearning.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * @param categoryRepository
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super();
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtos = new ArrayList<CategoryDto>();
        for (Category category : categories) {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setTitle(category.getTitle());
            dto.setIcon(category.getIcon());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public CategoryDto getById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        CategoryDto dto = new CategoryDto();
        if (category != null) {
            dto.setId(category.getId());
            dto.setTitle(category.getTitle());
            dto.setIcon(category.getIcon());
        }
        return dto;
    }

    @Override
    public void save(CategoryDto dto) {
        Category category = new Category();
        category.setTitle(dto.getTitle());
        category.setIcon(dto.getIcon());
        categoryRepository.save(category);
    }

    @Override
    public void edit(CategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId()).orElse(null);
        if (category != null) {
            category.setTitle(dto.getTitle());
            category.setIcon(dto.getIcon());
            categoryRepository.save(category);
        }
    }

    @Override
    public void remove(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<CategoryDto> getCategoryDtoPaging(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<CategoryDto> categoryDtoPage;
        if (categoryRepository.count() < startItem) {
            categoryDtoPage = new PageImpl<CategoryDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    categoryRepository.count()
            );
        } else {
            categoryDtoPage = categoryRepository.getCategoryDtoPaging(
                    PageRequest.of(currentPage, pageSize)
            );
        }
        return categoryDtoPage;
    }

    @Override
    public Page<CategoryDto> getCategoryDtoResultPaging(
            Pageable pageable,
            String key
    ) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<CategoryDto> categoryDtoPage;
        if (categoryRepository.getCategoryDtoResultCount(key) < startItem) {
            categoryDtoPage = new PageImpl<CategoryDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    categoryRepository.getCategoryDtoResultCount(key)
            );
        } else {
            categoryDtoPage = categoryRepository.getCategoryDtoResultPaging(
                    PageRequest.of(currentPage, pageSize),
                    key
            );
        }
        return categoryDtoPage;
    }
}
