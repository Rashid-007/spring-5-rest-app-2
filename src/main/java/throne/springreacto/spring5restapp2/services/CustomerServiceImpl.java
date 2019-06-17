package throne.springreacto.spring5restapp2.services;

import org.springframework.stereotype.Component;
import throne.springreacto.spring5restapp2.api.v1.mapper.CustomerMapper;
import throne.springreacto.spring5restapp2.api.v1.model.CustomerDTO;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.domain.Customer;
import throne.springreacto.spring5restapp2.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.convertCustomerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(getCusotmerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customer -> {
            CustomerDTO customerDTO = customerMapper.convertCustomerToCustomerDto(customer);
            customerDTO.setCustomerUrl(getCusotmerUrl(customer.getId()));
            return customerDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.convertCustomerDtoToCustomer(customerDTO);
        return saveCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDto) {

        Customer customer = customerMapper.convertCustomerDtoToCustomer(customerDto);
        customer.setId(customerId);
        return saveCustomer(customer);

    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {

            if(customerDTO.getFirstname() != null){
                customer.setFirstname(customerDTO.getFirstname());
            }

            if(customerDTO.getLastname() != null){
                customer.setLastname(customerDTO.getLastname());
            }

            CustomerDTO returnDto = customerMapper.convertCustomerToCustomerDto(customerRepository.save(customer));

            returnDto.setCustomerUrl(getCusotmerUrl(id));

            return returnDto;

        }).orElseThrow(() -> new ResourceNotFoundException("No customer found with the given ID"));
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO saveCustomer(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO customerDTO = customerMapper.convertCustomerToCustomerDto(savedCustomer);
        customerDTO.setCustomerUrl(getCusotmerUrl(savedCustomer.getId()));

        return customerDTO;
    }

    private String getCusotmerUrl(Long id){
        return Constants.CUSTOMER_BASE_URL + "/" + id;
    }
}
