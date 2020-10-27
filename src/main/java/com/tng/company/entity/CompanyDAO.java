package com.tng.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


/*@Entity
@Table("tng_property_address")
@NamedQuery("TngPropertyAddress.findAll", query = "SELECT t FROM TngPropertyAddress t")*/
@Table("common.companies_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//public class TngPropertyAddress implements Serializable, Persistable<UUID> {
public class CompanyDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String companyName;

    private String uuidAddress;

    private String languagePreference;
    private String companyUrl;
    private String companyLogoUrl; //url Logo link or base64 content?? (TBD)

    private String uuidAccountInfo;
    private String voidChequeUrl; //Void Cheque uRL for now or store as an image??
    private String slaSigned;
    private String signedSlaUrl;
    private String mapAccess;

    @CreatedDate
    private Timestamp createdDate;
    @LastModifiedDate
    private Timestamp last_updated;

    private boolean active = true; //For soft delete and select statement to filter out inactive records
}
