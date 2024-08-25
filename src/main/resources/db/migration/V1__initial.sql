CREATE TABLE documents
(
    id                BIGSERIAL PRIMARY KEY,
    doc_id            VARCHAR(100) UNIQUE NOT NULL,
	doc_status        VARCHAR(100),
	doc_type 	      VARCHAR(100),
	descr_id 		  BIGINT,
	import_request    BOOLEAN,
    owner_inn 	      VARCHAR(100),
	participant_inn   VARCHAR(100),
	producer_inn	  VARCHAR(100),
	production_date   DATE,
	production_type   VARCHAR(100),
	reg_date 		  DATE,
	reg_number		  VARCHAR(100)
);

CREATE TABLE description
(
	id 				BIGSERIAL 	PRIMARY KEY,
	doc_id 	     	VARCHAR(100) NOT NULL,
	param_name   	VARCHAR(100),
	param_value  	VARCHAR(100)
);

CREATE TABLE products
(
    id            	BIGSERIAL PRIMARY KEY,
    doc_id 		  	BIGINT NOT NULL,
    cert_doc 	  	VARCHAR(100),
    cert_doc_date 	DATE,
    cert_doc_num  	INT,
	owner_inn 	  	VARCHAR(100),
	producer_inn  	VARCHAR(100),
	production_date DATE,
	tnved_code 		VARCHAR(100),
	uit_code 		VARCHAR(100),
	uitu_code   	VARCHAR(100),
	reg_date 		DATE,
	reg_number      VARCHAR(100)
);