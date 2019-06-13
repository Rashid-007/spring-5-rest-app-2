package throne.springreacto.spring5restapp2.services;

import throne.springreacto.spring5restapp2.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
