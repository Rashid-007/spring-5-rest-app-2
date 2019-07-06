package throne.springreacto.spring5restapp2.services;

import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;

import java.util.List;

public interface VendorService {
    List<VendorDto> getVendors();

    VendorDto createVendor(VendorDto vendorDto);
    VendorDto updateVendor(VendorDto vendorDto, Long id);

    VendorDto patchVendor(Long id, VendorDto vendorDto);

    VendorDto getVendorById(Long id);

    void deleteVendorById(Long id);
}
