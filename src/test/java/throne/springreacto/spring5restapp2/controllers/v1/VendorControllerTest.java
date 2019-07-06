package throne.springreacto.spring5restapp2.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.services.VendorService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class}) // we bring alive part of Spring Context similar to JpaDataTest
public class VendorControllerTest {

    public static final String NAME = "Julian";
    public static final String VENDOR = "vendor";

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    VendorDto vendorDTO_1;

    @Before
    public void setUp() throws Exception {

        vendorDTO_1 = new VendorDto("Vendor 1", Constants.VENDOR_BASE_URL + "/1");
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
    public void testGetVendorById() throws Exception {

        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(VENDOR);
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDto);

        mockMvc.perform(get(Constants.VENDOR_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(VENDOR)));
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
                .content(objectToJsonStirng(vendorDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(VENDOR)));

        verify(vendorService, times(1)).createVendor(any(VendorDto.class));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(VENDOR);

        VendorDto resturnedDto = new VendorDto();
        resturnedDto.setName(vendorDto.getName());

        when(vendorService.updateVendor(eq(vendorDto), anyLong())).thenReturn(resturnedDto);

        mockMvc.perform(put(Constants.VENDOR_BASE_URL + "/1")
                .content(objectToJsonStirng(vendorDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo(VENDOR)))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).updateVendor(any(VendorDto.class), anyLong());
    }

    @Test
    public void patchVendor() throws Exception {
        given(vendorService.patchVendor(anyLong(), any(VendorDto.class))).willReturn(vendorDTO_1);

        mockMvc.perform(patch(Constants.VENDOR_BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonStirng(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(Constants.VENDOR_BASE_URL + "/1"))
                .andExpect(status().isOk());

        then(vendorService).should().deleteVendorById(anyLong());
    }
}