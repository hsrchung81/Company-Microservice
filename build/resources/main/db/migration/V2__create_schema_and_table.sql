
CREATE SCHEMA IF NOT EXISTS common;
CREATE TABLE IF NOT EXISTS common.companies_profile
(
	id uuid default uuid_generate_v4() not null
		constraint companies_pk
			primary key,
	company_name varchar(100),
	uuid_address varchar(35),
	language_preference varchar(15),
	company_url text,
	company_logo_url text,
	uuid_account_info varchar(35),
	void_cheque_url text,
	sla_signed varchar(5),cpa
	signed_sla_url text,
	map_access varchar(5),
	created_date timestamp,
	last_updated timestamp,
	active boolean
);

ALTER TABLE common.companies_profile owner to postgres;