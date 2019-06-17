package throne.springreacto.spring5restapp2.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.domain.Vendor;

import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final long ID = 1L;
    public static final String VENDOR_NAME = "vendor";
    public static final String SELF_URL = "self_url";
    VendorMapper vendorMapper;

    @Before
    public void setUp(){
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void testConvertVendorToVendorDto(){

        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);

        //when
        VendorDto result = vendorMapper.convertVendorToVendortDto(vendor);

        //then
        assertNotNull(result);
        assertEquals(VENDOR_NAME, result.getName());
        assertNull(result.getVendorUrl());
    }

    @Test
    public void testConvertVendorDtoToVendor(){

        //given
        VendorDto vendorDto = new VendorDto();
        vendorDto.setName(VENDOR_NAME);
        vendorDto.setVendorUrl(SELF_URL);

        //when
        Vendor result = vendorMapper.convertVendorDtoToVendor(vendorDto);

        //then
        assertNotNull(result);
        assertEquals(VENDOR_NAME, result.getName());

    }

}