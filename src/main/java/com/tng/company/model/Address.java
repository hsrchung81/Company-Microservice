package com.tng.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//public class TngPropertyAddress implements Serializable, Persistable<UUID> {
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fullAddress;
    private String streetNumber;
    private String streetName;
    private String streetType;
    private String streetDirection;
    private String unitNumber;
    private String city;
    private String province;
    private String postalCode;
    private String countryCode;
    private BigDecimal latitude;
    private BigDecimal longitude;

    private Timestamp createdDate;
    private Timestamp lastUpdated;
    private boolean active = true;

}
