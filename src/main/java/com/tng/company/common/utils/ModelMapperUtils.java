package com.tng.company.common.utils;

import com.tng.company.entity.CompanyDAO;
import com.tng.company.model.CompanyProfileDTO;
import org.modelmapper.PropertyMap;

public class ModelMapperUtils {

	/*
	 * This method will be call by modelMapper map function which will skip settings certain fields as define
	 * */
	public static PropertyMap<CompanyProfileDTO, CompanyDAO> skipFieldsToBeMappedForCompanyProfileDTOtoCompanyDAO = new PropertyMap<CompanyProfileDTO, CompanyDAO>() {
		@Override
		protected void configure() {
//			skip().setModifiedBy(null);
//			skip().setModifiedDate(null);
//			skip(destination.getModifiedDate());
//			skip(destination.getCreatedDate());
			skip(destination.getUuidAccountInfo());
			skip(destination.getUuidAddress());
			skip(destination.getId());
		}
	};
}
