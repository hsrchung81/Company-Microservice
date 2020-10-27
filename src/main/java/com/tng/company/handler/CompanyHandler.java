package com.tng.company.handler;

import com.tng.company.common.utils.ModelMapperUtils;
import com.tng.company.entity.CompanyDAO;
import com.tng.company.model.CompanyProfileDTO;
import com.tng.company.repository.CompanyProfileRepositoryInterface;
import com.tng.company.service.AccountingInfoService;
import com.tng.company.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/company")
public class CompanyHandler {

    @Autowired
    private CompanyProfileRepositoryInterface companyProfileRepository;
    @Autowired
    private AccountingInfoService accountInfoService;
    @Autowired
	private AddressService addressService;


	/*
	 * Retrieve the list of all company profiles
	 * - Call Accounting Microservice By accounting UUID and get the details
	 * - Call Address Microservice By address UUID and get the details
	 * - Combine the info and return
	 * */
    @GetMapping()
    public Flux<CompanyProfileDTO> allActive() {
		CompanyProfileDTO companyProfileDTO = new CompanyProfileDTO();
		return companyProfileRepository.getAllActive()
				.flatMap(company-> accountInfoService.getAccountingDetailsViaMicroService(company))
				.flatMap(company-> addressService.getDetailsFromAddressMicroService(company));

    }

//    @GetMapping("/name/{companyName}")
//    public Mono<CompanyProfileDTO> get(@PathVariable("companyName") String companyName) {
//        return companyProfileRepository.findByCompanyName(companyName)
//				.flatMap(company-> accountInfoService.getAccountingDetailsViaMicroService(company))
//				.flatMap(company-> addressService.getDetailsFromAddressMicroService(company));
////                .map((s) -> new ResponseEntity<>(s, HttpStatus.OK))
////                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    /*
    * Retrieve the company profile info by company Profile ID
    * - Call Accounting Microservice By accounting UUID and get the details
    * - Call Address Microservice By address UUID and get the details
    * - Combine the info and return
    * */
    @GetMapping("/{id}")
    public Mono<CompanyProfileDTO> get(@PathVariable("id") UUID id) {
       return companyProfileRepository.findById(id)
                .flatMap(companyDAO -> accountInfoService.getAccountingDetailsViaMicroService(companyDAO))
               	.flatMap(companyDAO-> addressService.getDetailsFromAddressMicroService(companyDAO));
    }

    /**
	 * Create a company profile by calling
	 * - Accounting Microservice to save the accounting details and get the accounting UUID
	 * - Address Microservice to save the address details and get the address UUID
	 * - Once the require UUID for address and microservic are return, then save the company profile
	 * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CompanyProfileDTO> create(@RequestBody CompanyProfileDTO companyProfileDTO) {
    	ModelMapper modelMapper = new ModelMapper();
        return accountInfoService.createAccountingViaMicroService(companyProfileDTO) //Call Accounting MicroService and Insert Accounting Info
                .flatMap(accountingInfo-> {
                	companyProfileDTO.setUuidAccountInfo(accountingInfo.getId());
                    return addressService.createAddressViaMicroService(companyProfileDTO)  //Call Address MicroService and Insert Accounting Info
                            .flatMap(address-> {
								companyProfileDTO.setUuidAddress(address.getId());
                                CompanyDAO companyDAO = new CompanyDAO();
                                new ModelMapper().map(companyProfileDTO, companyDAO);
                                return companyProfileRepository.save(companyDAO)
										.map(sts ->{
											modelMapper.map(sts, companyProfileDTO);
											return companyProfileDTO;
										});
                            });
                });

    }



	/**
	 * - Update Company Profile
	 * - Call Address Microservice to update Address info
	 * - Call Accounting Microservice to update Acccounting Info
	 */

    @PutMapping("/{id}")
    public Mono<CompanyProfileDTO> update(@PathVariable("id") UUID id, @RequestBody CompanyProfileDTO companyProfileDTO) {
    	ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return companyProfileRepository.findById(id)
                .map(st -> {
					modelMapper.addMappings(ModelMapperUtils.skipFieldsToBeMappedForCompanyProfileDTOtoCompanyDAO); //Skip auto set value for certain fields
                	modelMapper.map(companyProfileDTO,st);
					st.setId(id); //Needed in case the ID in body payload  doesn't match to prevent overwrite
                    return st;
                })
                .flatMap(st -> {
					return companyProfileRepository.save(st) //save to Company
							.map(sts -> {
								modelMapper.map(sts, companyProfileDTO);
								return companyProfileDTO;
							});
				})
				.flatMap(st -> {
					//Save Accounting Details via Microservice
					companyProfileDTO.setUuidAccountInfo(st.getUuidAccountInfo());
					return accountInfoService.updateAccountingDetailsViaMicroService(companyProfileDTO)
							.map(sts ->{
								modelMapper.map(sts, companyProfileDTO);
								return companyProfileDTO;
							});
				})
				.flatMap(st -> {
					//Save Address Details via Microservice
					companyProfileDTO.setUuidAddress(st.getUuidAddress());
					return addressService.updateAddressDetailsViaMicroService(companyProfileDTO)
							.map(sts ->{
								modelMapper.map(sts, companyProfileDTO);
								return companyProfileDTO;
							});
				});

    }


	/**
	 * Soft Delete - Set Active Flag to false which will prevent the getAllActive() to display the inactive records.
	 */
    @DeleteMapping("/{id}")
    public Mono<CompanyDAO> update(@PathVariable("id") UUID id) {
        return companyProfileRepository.findById(id)
                .map(st -> {
                    st.setActive(false);
                    return st;
                })
                .flatMap(st -> companyProfileRepository.save(st));
    }

    /**
	 * Permanent Delete from Database
	 * */
    @DeleteMapping("/{id}/remove")
    public Mono<Void> delete(@PathVariable("id") UUID id) {
        return companyProfileRepository.deleteById(id);
    }

    @GetMapping("/listHeaders")
    public ResponseEntity<String> listAllHeaders(
            @RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.printf("Header '%s' = %s%n", key, value);
        });

        return new ResponseEntity<String>(
                String.format("Listed %d headers", headers.size()), HttpStatus.OK);
    }


}
