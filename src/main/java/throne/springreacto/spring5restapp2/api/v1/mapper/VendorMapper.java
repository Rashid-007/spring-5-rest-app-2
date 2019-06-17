package throne.springreacto.spring5restapp2.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.domain.Vendor;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDto convertVendorToVendortDto(Vendor vendor);
    Vendor convertVendorDtoToVendor(VendorDto vendorDto);
}
