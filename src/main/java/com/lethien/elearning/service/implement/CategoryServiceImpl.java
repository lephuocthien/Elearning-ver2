/**
 * Dec 18, 2020
 * 4:51:23 PM
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.CategoryDto;
import com.lethien.elearning.entity.Category;
import com.lethien.elearning.repository.CategoryRepository;
import com.lethien.elearning.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

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
		Category category = categoryRepository.findById(id).get();
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setTitle(category.getTitle());
		dto.setIcon(category.getIcon());
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
		Category category = categoryRepository.findById(dto.getId()).get();
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

}
