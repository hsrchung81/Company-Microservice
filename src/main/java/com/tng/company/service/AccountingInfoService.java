package com.tng.company.service;

import com.tng.company.entity.CompanyDAO;
import com.tng.company.model.AccountingInfo;
import com.tng.company.model.CompanyProfileDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface AccountingInfoService {
    Mono<AccountingInfo> createAccountingViaMicroService(CompanyProfileDTO companyProfileDTO);
//    Flux<CompanyProfile> getAllAccountInfo();
    Mono<CompanyProfileDTO> getAccountingDetailsViaMicroService(CompanyDAO companyDAO);
	Mono<AccountingInfo> updateAccountingDetailsViaMicroService(CompanyProfileDTO companyProfileDTO);
}
