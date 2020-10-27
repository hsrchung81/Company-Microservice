package com.tng.company.service.impl;

import com.tng.company.model.AccountingInfo;
import com.tng.company.model.Address;
import com.tng.company.model.CompanyProfileDTO;
import com.tng.company.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class AddressServiceImpl implements AddressService {

//    @Autowired
//    TokenAction tokenAction;
//    @Autowired
//    AddressReactiveRepository addressReactiveRepository;
//    @Autowired
//    AddressRepository addressRepository;
    @Autowired
    private Environment env;

   public Mono<Address> createAddressViaMicroService(CompanyProfileDTO companyProfileDTO){
        WebClient webClient = WebClient.create(env.getProperty("microserviceurl.addressMicroServiceUrl"));

	   CompanyProfileDTO updatedCompanyProfileDTO = new CompanyProfileDTO();
        return webClient.post()
                .body(Mono.just(companyProfileDTO), CompanyProfileDTO.class)
                .retrieve()
                .bodyToMono(Address.class)
//				.map(address -> {
//					new ModelMapper().map(address, companyProfile);
//					return companyProfile;
//				})
                .log("createAddress");
    }


//	public Mono<CompanyProfile> getDetailsFromAddressMicroService(CompanyProfile companyDAO){
//		CompanyProfile companyProfile = new CompanyProfile();
//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.map(companyDAO, companyProfile);
//		return addressService.getAddressDetails(companyDAO.getUuidAddress())
//				.map(address -> {
//					modelMapper.map(address,companyProfile);
//					return companyProfile;
//				}).onErrorReturn(companyProfile);
//	}

	public Mono<CompanyProfileDTO> getDetailsFromAddressMicroService(CompanyProfileDTO companyProfileDTO) {
        WebClient webClient = WebClient.create(env.getProperty("microserviceurl.addressMicroServiceUrl"));
		ModelMapper modelMapper = new ModelMapper();
		CompanyProfileDTO updatedCompanyProfileDTO = new CompanyProfileDTO();

        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(companyProfileDTO.getUuidAddress()))
                .headers(httpHeaders -> httpHeaders.add("content-type", "application/json"))
                .retrieve()
                .bodyToMono(Address.class)
				.map(address -> {
					modelMapper.map(companyProfileDTO, updatedCompanyProfileDTO);
					modelMapper.map(address, updatedCompanyProfileDTO);
					return updatedCompanyProfileDTO;
				})
				.log("getAddressDetails");
    }

	public Mono<Address> updateAddressDetailsViaMicroService(CompanyProfileDTO companyProfileDTO){
		WebClient webClient = WebClient.create(env.getProperty("microserviceurl.addressMicroServiceUrl"));
		return webClient.put()
				.uri(uriBuilder -> uriBuilder
						.path("/{id}")
						.build(companyProfileDTO.getUuidAddress()))
				.body(Mono.just(companyProfileDTO), CompanyProfileDTO.class)
				.retrieve()
				.bodyToMono(Address.class)
//			    .map(accountingInfo-> {
//			    	new ModelMapper().map(accountingInfo,companyProfile);
//			    	return companyProfile;
//
//				})
				.log("updateAccountInfo");
	}

//	public Flux<Address> getAllAddresses() {
//		WebClient webClient = WebClient.create(env.getProperty("microserviceurl.addressMicroServiceUrl"));
//		return  webClient.get()
//				.headers(httpHeaders -> httpHeaders.add("content-type", "application/json"))
//				.retrieve()
//				.bodyToFlux(Address.class)
//				.log("getAllAddresses");
//	}
}