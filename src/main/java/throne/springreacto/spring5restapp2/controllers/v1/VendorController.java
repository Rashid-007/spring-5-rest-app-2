package throne.springreacto.spring5restapp2.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import throne.springreacto.spring5restapp2.api.v1.model.VendorDto;
import throne.springreacto.spring5restapp2.api.v1.model.VendorListDto;
import throne.springreacto.spring5restapp2.config.Constants;
import throne.springreacto.spring5restapp2.services.VendorService;

@Api(description = "This is vendor resource handler")
@RestController
@RequestMapping(Constants.VENDOR_BASE_URL)
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "Vendoor list", notes = "Get the list of all vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDto getVendors(){
        return new VendorListDto(vendorService.getVendors());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDto createVendor(@RequestBody VendorDto vendorDto){
        return vendorService.createVendor(vendorDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto updateVendor(@RequestBody VendorDto vendorDto, @PathVariable Long id){
        return vendorService.updateVendor(vendorDto, id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDto patchVendor(@PathVariable Long id, @RequestBody VendorDto vendorDto){
        return vendorService.patchVendor(id, vendorDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }

}
