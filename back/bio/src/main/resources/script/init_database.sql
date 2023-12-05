#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

CREATE DATABASE IF NOT EXISTS bio; 

USE bio;

#------------------------------------------------------------
# Table: USER
#------------------------------------------------------------

CREATE TABLE USER(
        user_id          Int NOT NULL ,
        user_name        Varchar (100) NOT NULL ,
        user_first_name  Varchar (100) NOT NULL ,
        user_phone       Varchar (100) NOT NULL ,
        user_address     Varchar (100) NOT NULL ,
        user_postal_code Varchar (100) NOT NULL ,
        user_city        Varchar (100) NOT NULL ,
        user_country     Varchar (100) NOT NULL ,
        user_mail        Varchar (100) NOT NULL ,
        user_password    Varchar (255) NOT NULL
	,CONSTRAINT USER_PK PRIMARY KEY (user_id),
	INDEX idx_mail (user_mail)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: HOUSE
#------------------------------------------------------------

CREATE TABLE HOUSE(
        house_id          Int NOT NULL ,
        house_name        Varchar (100) NOT NULL ,
        house_description Varchar (255) NOT NULL ,
        house_link        Varchar (255) NOT NULL
	,CONSTRAINT HOUSE_PK PRIMARY KEY (house_id),
	INDEX idx_name (house_name)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: PRODUCT
#------------------------------------------------------------

CREATE TABLE PRODUCT(
        product_id          Int NOT NULL ,
        product_name        Varchar (100) NOT NULL ,
        product_description Varchar (255) NOT NULL ,
        product_unit_price  Decimal (65) NOT NULL ,
        product_stock       Int NOT NULL ,
        product_link        Varchar (255) NOT NULL ,
        house_id            Int
	,CONSTRAINT PRODUCT_PK PRIMARY KEY (product_id)

	,CONSTRAINT PRODUCT_HOUSE_FK FOREIGN KEY (house_id) REFERENCES HOUSE(house_id),
	INDEX idx_name (product_name)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: PRODUCT_TYPE
#------------------------------------------------------------

CREATE TABLE PRODUCT_TYPE(
        type_id   Int NOT NULL ,
        type_name Varchar (100) NOT NULL
	,CONSTRAINT PRODUCT_TYPE_PK PRIMARY KEY (type_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: BODY_PART
#------------------------------------------------------------

CREATE TABLE BODY_PART(
        body_id   Int NOT NULL ,
        body_name Varchar (100) NOT NULL
	,CONSTRAINT BODY_PART_PK PRIMARY KEY (body_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: ORDER
#------------------------------------------------------------

CREATE TABLE `ORDER`(
        order_id    Int NOT NULL ,
        order_date  Date NOT NULL ,
        order_state Varchar (100) NOT NULL ,
        user_id     Int NOT NULL
	,CONSTRAINT ORDER_PK PRIMARY KEY (order_id)

	,CONSTRAINT ORDER_USER_FK FOREIGN KEY (user_id) REFERENCES USER(user_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: LINE_ORDER
#------------------------------------------------------------

CREATE TABLE LINE_ORDER(
        line_id         Int NOT NULL ,
        line_unit_price Decimal (65) NOT NULL ,
        line_quantity   Int NOT NULL ,
        order_id        Int NOT NULL ,
        product_id      Int NOT NULL
	,CONSTRAINT LINE_ORDER_PK PRIMARY KEY (line_id)

	,CONSTRAINT LINE_ORDER_ORDER_FK FOREIGN KEY (order_id) REFERENCES `ORDER`(order_id)
	,CONSTRAINT LINE_ORDER_PRODUCT0_FK FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CUSTOMER
#------------------------------------------------------------

CREATE TABLE CUSTOMER(
        user_id          Int NOT NULL ,
        user_name        Varchar (100) NOT NULL ,
        user_first_name  Varchar (100) NOT NULL ,
        user_phone       Varchar (100) NOT NULL ,
        user_address     Varchar (100) NOT NULL ,
        user_postal_code Varchar (100) NOT NULL ,
        user_city        Varchar (100) NOT NULL ,
        user_country     Varchar (100) NOT NULL ,
        user_mail        Varchar (100) NOT NULL ,
        user_password    Varchar (255) NOT NULL
	,CONSTRAINT CUSTOMER_PK PRIMARY KEY (user_id)

	,CONSTRAINT CUSTOMER_USER_FK FOREIGN KEY (user_id) REFERENCES USER(user_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: ADMIN
#------------------------------------------------------------

CREATE TABLE ADMIN(
        user_id          Int NOT NULL ,
        user_name        Varchar (100) NOT NULL ,
        user_first_name  Varchar (100) NOT NULL ,
        user_phone       Varchar (100) NOT NULL ,
        user_address     Varchar (100) NOT NULL ,
        user_postal_code Varchar (100) NOT NULL ,
        user_city        Varchar (100) NOT NULL ,
        user_country     Varchar (100) NOT NULL ,
        user_mail        Varchar (100) NOT NULL ,
        user_password    Varchar (255) NOT NULL
	,CONSTRAINT ADMIN_PK PRIMARY KEY (user_id)

	,CONSTRAINT ADMIN_USER_FK FOREIGN KEY (user_id) REFERENCES USER(user_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CONNECTION_BODY_PRODUCT
#------------------------------------------------------------

CREATE TABLE CONNECTION_BODY_PRODUCT(
        body_id    Int NOT NULL ,
        product_id Int NOT NULL
	,CONSTRAINT CONNECTION_BODY_PRODUCT_PK PRIMARY KEY (body_id,product_id)

	,CONSTRAINT CONNECTION_BODY_PRODUCT_BODY_PART_FK FOREIGN KEY (body_id) REFERENCES BODY_PART(body_id)
	,CONSTRAINT CONNECTION_BODY_PRODUCT_PRODUCT0_FK FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: CONNECTION_TYPE_PRODUCT
#------------------------------------------------------------

CREATE TABLE CONNECTION_TYPE_PRODUCT(
        product_id Int NOT NULL ,
        type_id    Int NOT NULL
	,CONSTRAINT CONNECTION_TYPE_PRODUCT_PK PRIMARY KEY (product_id,type_id)

	,CONSTRAINT CONNECTION_TYPE_PRODUCT_PRODUCT_FK FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
	,CONSTRAINT CONNECTION_TYPE_PRODUCT_PRODUCT_TYPE0_FK FOREIGN KEY (type_id) REFERENCES PRODUCT_TYPE(type_id)
)ENGINE=InnoDB;

