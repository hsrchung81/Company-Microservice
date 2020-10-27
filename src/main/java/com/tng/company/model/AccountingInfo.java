package com.tng.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
//import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountingInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //private UUID id;
    private String id;
    private String accpacVendorId;
    private String businessNumber;
    private String voidChequeUrl;
    private String paymentMethod;
    private Timestamp createdDate;
    private Timestamp lastUpdated;

}
