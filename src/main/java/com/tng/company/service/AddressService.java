package com.tng.company.service;

import com.tng.company.model.Address;
import com.tng.company.model.CompanyProfileDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AddressService {
    Mono<Address> createAddressViaMicroService(CompanyProfileDTO companyProfileDTO);
    Mono<CompanyProfileDTO> getDetailsFromAddressMicroService(CompanyProfileDTO companyProfileDTO);
	Mono<Address> updateAddressDetailsViaMicroService(CompanyProfileDTO companyProfileDTO);
//    Flux<CompanyProfile> getAllAddresses();
}
