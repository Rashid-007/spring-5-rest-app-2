package throne.springreacto.spring5restapp2.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5restapp2.api.v1.model.CategoryDTO;
import throne.springreacto.spring5restapp2.domain.Category;

import static org.junit.Assert.*;


public class CategoryMapperTest {
    public static final String NAME = "Joe";
    public static final long ID = 1L;

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void categoryToCategoryDtoTest() {

        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());

    }
}