package throne.springreacto.spring5restapp2.api.v1.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import throne.springreacto.spring5restapp2.api.v1.model.CategoryDTO;
import throne.springreacto.spring5restapp2.domain.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "id", target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
