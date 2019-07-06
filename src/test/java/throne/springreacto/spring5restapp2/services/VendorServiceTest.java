package throne.springreacto.spring5restapp2.services;

import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
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
    public void testGetVendorById(){
        //when
        Vendor vendor = new Vendor();
        vendor.setName(CORPORATE_NAME);

         when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDto result = sut.getVendorById(anyLong());

        verify(vendorRepository, times(1)).findById(anyLong());
        assertEquals(CORPORATE_NAME, result.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetVendorByIdNotFound(){

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.empty());

        sut.getVendorById(anyLong());

        //then
        //throw exception
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

    @Test
    public void testUpdateVendor(){

        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(CORPORATE_NAME);

        Vendor vendor = new Vendor();
        vendor.setName(vendorDto.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorDto result = sut.updateVendor(vendorDto, anyLong());

        assertEquals(CORPORATE_NAME, result.getName());

        verify(vendorRepository, times(1)).save(any(Vendor.class));
    }
}