package throne.springreacto.spring5restapp2.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.spring5restapp2.api.v1.mapper.CustomerMapper;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;
import throne.springreacto.spring5restapp2.domain.Customer;
import throne.springreacto.spring5restapp2.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {


    public static final long ID = 1L;
    public static final String FIRSTNAME = "Julian";
    public static final String LASTNAME = "Mueller";
    @Mock
    CustomerRepository customerRepository;
    //@Mock
    //CustomerMapper customerMapper;
    CustomerService sut;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);

    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customerList = List.of(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customerList);

        //when
        List<CustomerDTO> customers = sut.getAllCustomers();

        //then
        assertEquals(3, customers.size());

    }

    @Test
    public void getCustomerById() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerById = sut.getCustomerById(anyLong());

        //then
        assertNotNull(customerById);
        assertEquals(FIRSTNAME, customerById.getFirstname());
    }
    @Test
    public void testCreateNewCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());

        when(customerRepository.save(any())).thenReturn(savedCustomer);

        //when
        CustomerDTO newCustomer = sut.createNewCustomer(customerDTO);

        //then
        assertNotNull(newCustomer);
        assertEquals(FIRSTNAME, newCustomer.getFirstname());
        assertEquals(LASTNAME, newCustomer.getLastname());
    }
}