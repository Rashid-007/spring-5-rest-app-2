package throne.springreacto.spring5restapp2.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.domain.Vendor;
import throne.springreacto.spring5restapp2.services.VendorService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {
    public static final String NAME = "Julian";
    public static final String VENDOR = "vendor";
    @Mock
    VendorService vendorService;

    VendorController sut;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new VendorController(vendorService);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void testGetVendors() throws Exception{
        VendorDto vendor1 = new VendorDto();
        vendor1.setName(NAME);
        vendor1.setVendorUrl(Constants.VENDOR_BASE_URL + "/1");

        VendorDto vendor2 = new VendorDto();
        vendor2.setName(NAME);
        vendor2.setVendorUrl(Constants.VENDOR_BASE_URL + "/2");

        List<VendorDto> vendorDtoList = List.of(vendor1, vendor2);

        when(vendorService.getVendors()).thenReturn(vendorDtoList);

        mockMvc.perform(get(Constants.VENDOR_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

        verify(vendorService, times(1)).getVendors();

    }

    @Test
    public void testCreateVendor() throws Exception{
        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(VENDOR);

        VendorDto returnedVendorDto = new VendorDto();
        returnedVendorDto.setName(vendorDto.getName());
        returnedVendorDto.setVendorUrl(Constants.VENDOR_BASE_URL + "/1");

        when(vendorService.createVendor(any(VendorDto.class))).thenReturn(returnedVendorDto);

        mockMvc.perform(post(Constants.VENDOR_BASE_URL)
                .content(JsonObjectConverter.objectToJsonStirng(vendorDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(VENDOR)));

        verify(vendorService, times(1)).createVendor(any(VendorDto.class));
    }
}