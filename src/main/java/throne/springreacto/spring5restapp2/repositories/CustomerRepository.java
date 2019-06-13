package throne.springreacto.spring5restapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import throne.springreacto.spring5restapp2.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
