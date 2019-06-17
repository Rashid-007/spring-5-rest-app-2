package throne.springreacto.spring5restapp2.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import throne.springreacto.spring5restapp2.api.v1.model.CategoryDTO;
import throne.springreacto.spring5restapp2.api.v1.model.CatorgoryListDTO;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.services.CategoryService;

@RestController //cleaner code compared to @Controller
@RequestMapping(Constants.CATEGORY_BASE_URL)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CatorgoryListDTO getallCatetories(){
        return new CatorgoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name){
        return categoryService.getCategoryByName(name);
    }
}
