package throne.springreacto.spring5restapp2.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import throne.springreacto.spring5restapp2.api.v1.mapper.VendorMapper;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.bootstrap.Bootstrap;
import throne.springreacto.spring5restapp2.repositories.CategoryRepository;
import throne.springreacto.spring5restapp2.repositories.CustomerRepository;
import throne.springreacto.spring5restapp2.repositories.VendorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceITest {
    public static final long VENDOR_ID = 1L;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    VendorRepository vendorRepository;

    VendorService sut;

    Bootstrap bootstrap;

    @Before
    public void setUp() throws Exception{
        bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        sut = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void testVendorNameChange(){


        VendorDto vendorDto = new VendorDto();

        sut.patchVendor(getVendorId(), vendorDto);
    }

    private Long getVendorId(){

        return vendorRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(IllegalArgumentException::new).getId();


    }

}
