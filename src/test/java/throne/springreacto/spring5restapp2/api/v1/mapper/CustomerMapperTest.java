package throne.springreacto.spring5restapp2.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;
import throne.springreacto.spring5restapp2.domain.Customer;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final long ID = 1L;
    public static final String FIRSTNAME = "ab";
    public static final String LASTNAME = "cd";
    @Mock
    CustomerMapper customerMapper;

    @Before
    public void setUp() throws Exception {
        customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    public void testCustomerToCustomerDtoConversion(){
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        CustomerDTO customerDTO = customerMapper.convertCustomerToCustomerDto(customer);

        assertNotNull(customerDTO);
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void testCustomerDtoToCustomerConversion(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerUrl("/api/v1/customers/1");

        Customer customer = customerMapper.convertCustomerDtoToCustomer(customerDTO);

        assertNotNull(customer);
        assertEquals(FIRSTNAME, customer.getFirstname());
        assertEquals(LASTNAME, customer.getLastname());
    }
}