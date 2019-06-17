package throne.springreacto.spring5restapp2.services;

import org.springframework.stereotype.Component;
import throne.springreacto.spring5restapp2.api.v1.mapper.VendorMapper;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.domain.Vendor;
import throne.springreacto.spring5restapp2.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDto> getVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDto vendorDto = vendorMapper.convertVendorToVendortDto(vendor);
                    vendorDto.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDto createVendor(VendorDto vendorDto) {

        Vendor vendor = vendorMapper.convertVendorDtoToVendor(vendorDto);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDto savedVendorDto = vendorMapper.convertVendorToVendortDto(savedVendor);
        savedVendorDto.setVendorUrl(getVendorUrl(savedVendor.getId()));

        return savedVendorDto;
    }

    private String getVendorUrl(Long id){
        return Constants.VENDOR_BASE_URL + "/" + id;
    }

}
