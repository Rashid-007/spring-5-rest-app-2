package throne.springreacto.spring5restapp2.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.controllers.RestControllerExceptionHandler;
import throne.springreacto.spring5restapp2.services.CustomerService;
import throne.springreacto.spring5restapp2.services.ResourceNotFoundException;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @InjectMocks
    CustomerController sut;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(sut)
                .setControllerAdvice(new RestControllerExceptionHandler()).build();
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

        mockMvc.perform(get(Constants.CUSTOMER_BASE_URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception{
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(Constants.CUSTOMER_BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON))
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

        objectToJsonStirng(customerDto);

        mockMvc.perform(post(Constants.CUSTOMER_BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
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

        mockMvc.perform(put(Constants.CUSTOMER_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(objectToJsonStirng(customerDto))).andReturn().getResponse().getContentAsString();

        mockMvc.perform(put(Constants.CUSTOMER_BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(objectToJsonStirng(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(SELF_URL)));
    }

    @Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname("Flintstone");
        returnDTO.setCustomerUrl(Constants.CUSTOMER_BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(Constants.CUSTOMER_BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonStirng(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(Constants.CUSTOMER_BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception{

        mockMvc.perform(delete(Constants.CUSTOMER_BASE_URL + "/1"))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomer(anyLong());
    }
    @Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(Constants.CUSTOMER_BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}