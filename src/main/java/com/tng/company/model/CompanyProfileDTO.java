package com.tng.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileDTO implements Serializable{
        private static final long serialVersionUID = 1L;

        private UUID id;
        private String companyName;
        private String uuidAddress;
        private String countryCode;
        private String postalCode;
        private String province;
        private String city;
        private String streetDirection;
        private String streetType;
        private String streetName;
        private String streetNumber;
        private String unitNumber;
        private String languagePreference;
        private String companyUrl;
        private String companyLogoUrl; //url Logo link to AWS S3

		private String uuidAccountInfo;
        private String businessNumber; //GST/HST/QST
        private String paymentMethod;
        private String accpacVendorId; //Accpac generated ID for this company?

		private String mapAccess;
        private String voidChequeUrl; //Void Cheque uRL for now or store as an image??
        private String slaSigned;
        private String signedSlaUrl;
}
