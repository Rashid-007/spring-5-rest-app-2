package throne.springreacto.spring5restapp2.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import throne.springreacto.spring5restapp2.domain.Category;
import throne.springreacto.spring5restapp2.domain.Customer;
import throne.springreacto.spring5restapp2.domain.Vendor;
import throne.springreacto.spring5restapp2.repositories.CategoryRepository;
import throne.springreacto.spring5restapp2.repositories.CustomerRepository;
import throne.springreacto.spring5restapp2.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRespository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();

    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data Loaded = " + categoryRepository.count() );
    }

    private void loadCustomers() {
        //given
        Customer customer1 = new Customer();
        customer1.setId(1l);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        customerRepository.save(customer2);

        System.out.println("Customers Loaded: " + customerRepository.count());
    }

    private void loadVendors(){
        Vendor vendor1 = new Vendor();
        vendor1.setName("XY corporate");

        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("YZ corporate");
        vendorRepository.save(vendor2);

    }
}
