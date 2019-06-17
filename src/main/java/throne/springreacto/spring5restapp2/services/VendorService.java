package throne.springreacto.spring5restapp2.services;

import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;

import java.util.List;

public interface VendorService {
    List<VendorDto> getVendors();

    VendorDto createVendor(VendorDto vendorDto);
}
