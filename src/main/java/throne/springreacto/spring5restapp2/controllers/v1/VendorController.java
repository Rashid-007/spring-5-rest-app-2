package throne.springreacto.spring5restapp2.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.api.v1.model.VendorListDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.services.VendorService;

@RestController
@RequestMapping(Constants.VENDOR_BASE_URL)
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDto getVendors(){
        return new VendorListDto(vendorService.getVendors());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDto createVendor(@RequestBody VendorDto vendorDto){
        return vendorService.createVendor(vendorDto);
    }
}
