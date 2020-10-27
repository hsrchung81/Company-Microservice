package com.tng.company.repository;

import com.tng.company.entity.CompanyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.data.relational.core.query.Criteria.where;

@RequiredArgsConstructor
@Component
@Repository
public class CompanyProfileRepository {
    private final DatabaseClient databaseClient = null;


    public Flux<CompanyDAO> findAllCompanies() {
        return this.databaseClient.select()
                .from(CompanyDAO.class)
                .fetch()
                .all();
    }

    public Mono<CompanyDAO> findByCompanyID(UUID uuid) {
        return databaseClient.select()
                .from(CompanyDAO.class)
                .matching(where("id").is(uuid))
                .fetch()
                .one();
    }

    public Mono<CompanyDAO> findByCompanyName(String companyName) {
        return databaseClient.select()
                .from(CompanyDAO.class)
                .matching(where("company_name").is(companyName))
                .fetch()
                .one();
    }

    public Mono<UUID> saveCompany(CompanyDAO companyDAO) {
        return this.databaseClient.insert().into(CompanyDAO.class)
                .using(companyDAO)
                .fetch()
                .one()
                .map(m -> (UUID) m.get("id"));
    }

    public Mono<Integer> update(CompanyDAO companyDAO) {
        return this.databaseClient.update()
                .table(CompanyDAO.class)
                .using(companyDAO)
                .fetch()
                .rowsUpdated();
    }

    public Mono<Integer> deleteByCompanyId(UUID uuid) {
        return this.databaseClient.delete().from(CompanyDAO.class)
                .matching(where("id").is(uuid))
                .fetch()
                .rowsUpdated();
    }
}
