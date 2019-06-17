package throne.springreacto.spring5restapp2.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import throne.springreacto.spring5restapp2.api.v1.mapper.VendorMapper;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.domain.Vendor;
import throne.springreacto.spring5restapp2.repositories.VendorRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VendorServiceTest {
    public static final String CORPORATE_NAME = "Corporate X";
    public static final long ID = 1L;
    public static final String SELF_URL = Constants.VENDOR_BASE_URL + "/1";
    @Mock
    VendorRepository vendorRepository;

    VendorService sut;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sut = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void testGetVendors() {

        //given
        List<Vendor> vendors = List.of(new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDto> result = sut.getVendors();

        //then
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateVendor() {
        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(CORPORATE_NAME);
        vendorDto.setVendorUrl(SELF_URL);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDto.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        VendorDto result = sut.createVendor(vendorDto);

        assertNotNull(result);
        assertEquals(CORPORATE_NAME, result.getName());
        assertEquals(SELF_URL, result.getVendorUrl());
    }
}