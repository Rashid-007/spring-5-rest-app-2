package throne.springreacto.spring5restapp2.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;
import throne.springreacto.spring5restapp2.domain.Customer;
import throne.springreacto.spring5restapp2.services.CustomerService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static throne.springreacto.spring5restapp2.controllers.v1.JsonObjectConverter.objectToJsonStirng;

public class CustomerControllerTest {
    public static final String FIRSTNAME = "Julian";
    public static final String LASTNAME = "Muelller";
    public static final String SELF_URL = "selfUrl";
    @Mock
    CustomerService customerService;

    CustomerController sut;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new CustomerController(customerService);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getCustomers() throws Exception{
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname(FIRSTNAME);
        customerDTO1.setLastname(LASTNAME);

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname(FIRSTNAME);
        customerDTO2.setLastname(LASTNAME);

        List<CustomerDTO> customers = List.of(customerDTO1, customerDTO2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception{
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));

    }

    @Test
    public void testCreateCustomer() throws Exception {

        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstname(FIRSTNAME);
        customerDto.setLastname(LASTNAME);

        CustomerDTO returnedCustomerDto = new CustomerDTO();
        returnedCustomerDto.setFirstname(customerDto.getFirstname());
        returnedCustomerDto.setLastname(customerDto.getLastname());
        returnedCustomerDto.setCustomerUrl(SELF_URL);

        when(customerService.createNewCustomer(any())).thenReturn(returnedCustomerDto);

        String st = objectToJsonStirng(customerDto);

        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON).content(objectToJsonStirng(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(SELF_URL)));
    }

    @Test
    public void testUpdateCustomer() throws Exception {

        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstname(FIRSTNAME);
        customerDto.setLastname(LASTNAME);

        CustomerDTO returnedCustomerDto = new CustomerDTO();
        returnedCustomerDto.setFirstname(customerDto.getFirstname());
        returnedCustomerDto.setLastname(customerDto.getLastname());
        returnedCustomerDto.setCustomerUrl(SELF_URL);

        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnedCustomerDto);

        String st = mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectToJsonStirng(customerDto))).andReturn().getResponse().getContentAsString();

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectToJsonStirng(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(SELF_URL)));
    }
}