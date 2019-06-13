package throne.springreacto.spring5restapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import throne.springreacto.spring5restapp2.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
