package throne.springreacto.spring5restapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import throne.springreacto.spring5restapp2.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
