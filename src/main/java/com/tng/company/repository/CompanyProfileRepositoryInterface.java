package com.tng.company.repository;

import com.tng.company.entity.CompanyDAO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CompanyProfileRepositoryInterface extends ReactiveCrudRepository<CompanyDAO, UUID> {
    @Query("SELECT * from common.companies_profile where active = true")
    Flux<CompanyDAO> getAllActive();

    @Query("SELECT * from common.companies_profile where company_name = (:companyName)")
    Mono<CompanyDAO> findByCompanyName(String companyName);
}
