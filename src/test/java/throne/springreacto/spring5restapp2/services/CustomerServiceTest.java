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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {


    public static final Long ID = 1L;
    public static final String FIRSTNAME = "Julian";
    public static final String LASTNAME = "Mueller";
    @Mock
    CustomerRepository customerRepository;

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

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO newCustomer = sut.createNewCustomer(customerDTO);

        //then
        assertNotNull(newCustomer);
        assertEquals(FIRSTNAME, newCustomer.getFirstname());
        assertEquals(LASTNAME, newCustomer.getLastname());
    }

    @Test
    public void testUpdateCustomer(){

        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstname(FIRSTNAME);
        customerDto.setLastname(LASTNAME);

        Customer savedCustomer= new Customer();
        savedCustomer.setId(ID);
        savedCustomer.setFirstname(customerDto.getFirstname());
        savedCustomer.setLastname(customerDto.getLastname());

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO result = sut.updateCustomer(ID, customerDto);

        assertNotNull(result);
        assertEquals(FIRSTNAME, result.getFirstname());
        assertEquals(LASTNAME, result.getLastname());
    }

    @Test
    public void testDeleteCustomerById(){

        sut.deleteCustomer(ID);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}