package com.tng.company.service.impl;

import com.tng.company.entity.CompanyDAO;
import com.tng.company.model.AccountingInfo;
import com.tng.company.model.CompanyProfileDTO;
import com.tng.company.service.AccountingInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class AccountingInfoImpl implements AccountingInfoService {

    @Autowired
    private Environment env;
	//Accounting Microservice  - Create AccountInfo
    public Mono<AccountingInfo> createAccountingViaMicroService(CompanyProfileDTO companyProfileDTO){
        WebClient webClient = WebClient.create(env.getProperty("microserviceurl.accountingInfoMicroServiceUrl"));
       return webClient.post()
				.body(Mono.just(companyProfileDTO), CompanyProfileDTO.class)
                .retrieve()
                .bodyToMono(AccountingInfo.class)
//			    .map(accountingInfo-> {
//			    	new ModelMapper().map(accountingInfo,companyProfile);
//			    	return companyProfile;
//
//				})
                .log("createAccountInfo");
    }

	//Accounting Microservice  - Get Details By UUID Accountfs
    public Mono<CompanyProfileDTO> getAccountingDetailsViaMicroService(CompanyDAO companyDAO) {
        WebClient webClient = WebClient.create(env.getProperty("microserviceurl.accountingInfoMicroServiceUrl"));
		CompanyProfileDTO companyProfileDTO = new CompanyProfileDTO();
		ModelMapper modelMapper = new ModelMapper();
        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(companyDAO.getUuidAccountInfo()))
                .headers(httpHeaders -> httpHeaders.add("content-type", "application/json"))
                .retrieve()
                .bodyToMono(AccountingInfo.class)
				.map(accountingInfoDAO -> {
					modelMapper.map(accountingInfoDAO, companyProfileDTO);
					modelMapper.map(companyDAO, companyProfileDTO);
					return companyProfileDTO;
				})
				.onErrorReturn(companyProfileDTO)
				.log("getAccountInfo");
    }

	public Mono<AccountingInfo> updateAccountingDetailsViaMicroService(CompanyProfileDTO companyProfileDTO){
		WebClient webClient = WebClient.create(env.getProperty("microserviceurl.accountingInfoMicroServiceUrl"));
		return webClient.put()
				.uri(uriBuilder -> uriBuilder
						.path("/{id}")
						.build(companyProfileDTO.getUuidAccountInfo()))
				.body(Mono.just(companyProfileDTO), CompanyProfileDTO.class)
				.retrieve()
				.bodyToMono(AccountingInfo.class)
//			    .map(accountingInfo-> {
//			    	new ModelMapper().map(accountingInfo,companyProfile);
//			    	return companyProfile;
//
//				})
				.log("updateAccountInfo");
	}
}