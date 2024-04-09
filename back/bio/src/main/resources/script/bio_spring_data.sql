--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2

-- Started on 2024-03-18 14:33:21 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


--
-- TOC entry 3445 (class 0 OID 16437)
-- Dependencies: 224
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.role (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO public.role (role_id, role_name) VALUES (2, 'USER');


--
-- TOC entry 3446 (class 0 OID 16443)
-- Dependencies: 225
-- Data for Name: user_table; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.user_table (user_id, enable, user_address, user_city, user_country, user_first_name, user_mail, user_name, user_password, user_phone, user_postal_code, role_id) VALUES (1, true, NULL, NULL, NULL, 'admin', 'admin@admin.com', 'admin', '$2a$10$kXFu1Kzj/SIdAH54F4sFIeT6cUot9bm14di6EFx9YcsZkLBBz917q', NULL, NULL, 1);
INSERT INTO public.user_table (user_id, enable, user_address, user_city, user_country, user_first_name, user_mail, user_name, user_password, user_phone, user_postal_code, role_id) VALUES (2, true, 'XX rue la porte', 'Bordeaux', 'France', 'user', 'user@user.com', 'user', '$2a$10$j2LM3I78EAFAt8LePeeRqOdBfRAZR91TdzHoUVk17WxPXZbdAjDKW', '00 00 00 00 00', '33800', 2);


--
-- TOC entry 3447 (class 0 OID 16450)
-- Dependencies: 226
-- Data for Name: validation; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.validation (validation_id, activation, code, creation, expire, user_id) VALUES (1, '2024-03-18 13:11:55.441346+00', '245787', '2024-03-18 13:11:32.191658+00', '2024-03-18 13:21:32.191658+00', 1);
INSERT INTO public.validation (validation_id, activation, code, creation, expire, user_id) VALUES (2, '2024-03-18 14:14:03.224045+00', '670013', '2024-03-18 14:13:45.154602+00', '2024-03-18 14:23:45.154602+00', 2);


--
-- TOC entry 3436 (class 0 OID 16389)
-- Dependencies: 215
-- Data for Name: body_part; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.body_part (body_id, body_name) VALUES (1, 'Mains');
INSERT INTO public.body_part (body_id, body_name) VALUES (2, 'Visage');
INSERT INTO public.body_part (body_id, body_name) VALUES (3, 'Corps');
INSERT INTO public.body_part (body_id, body_name) VALUES (52, 'Gel Douche');


--
-- TOC entry 3444 (class 0 OID 16432)
-- Dependencies: 223
-- Data for Name: product_type; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.product_type (type_id, type_name) VALUES (1, 'Crème');
INSERT INTO public.product_type (type_id, type_name) VALUES (2, 'Pommade');
INSERT INTO public.product_type (type_id, type_name) VALUES (3, 'Huile de soin');
INSERT INTO public.product_type (type_id, type_name) VALUES (4, 'Huile Essentiel');


--
-- TOC entry 3437 (class 0 OID 16394)
-- Dependencies: 216
-- Data for Name: house; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.house (house_id, house_description, house_link, house_name) VALUES (1, '', 'bioaroma_logo.png', 'BioAroma');
INSERT INTO public.house (house_id, house_description, house_link, house_name) VALUES (2, '', 'On_The_Wild_Side.png', 'On The Wild Side');
INSERT INTO public.house (house_id, house_description, house_link, house_name) VALUES (3, '', 'Logo_Kaki.webp', 'Rosazecuna');


--
-- TOC entry 3441 (class 0 OID 16419)
-- Dependencies: 220
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (2, true, 'Huile Démaquillante', 'huile_dema.png', 'Huile Démaquillante', 9, 7, 3);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (3, true, 'Masque pour visage soin ', 'masque_exo.png', 'Masque Exfoliant', 8, 10, 2);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (4, true, 'Creme pour les mains', 'the_angelis.jpg', 'The Angelis', 10, 12, 1);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (5, true, 'Huile Essentiel', 'renforcer.png', 'Renforcer', 7, 10, 2);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (6, true, 'pour les mains', 'Rosazucena_Packshot_2.webp', 'Pommade hydratante', 10, 17, 3);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (7, true, 'Huile de soin pour le corps', 'huile_lumiere.jpeg', 'Huile de lumiere', 8, 14, NULL);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (9, true, 'creme', 'Rosazucena_Packshot_21.webp', 'Creme anti age', 9, 11, 3);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (8, true, 'Gel Douche', 'Rosazucena_Packshot_15.webp', 'Gel Douche', 11, 21, 3);
INSERT INTO public.product (product_id, is_visible, product_description, product_link, product_name, product_stock, product_unit_price, house_id) VALUES (1, true, 'creme', 'rosa_creme.jpg', 'Crème Anti Age', 7, 10, 1);



--
-- TOC entry 3442 (class 0 OID 16426)
-- Dependencies: 221
-- Data for Name: product_body_part; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (1, 2);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (2, 2);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (3, 2);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (4, 1);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (5, 3);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (6, 1);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (7, 3);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (8, 52);
INSERT INTO public.product_body_part (product_id, body_part_id) VALUES (9, 2);


--
-- TOC entry 3443 (class 0 OID 16429)
-- Dependencies: 222
-- Data for Name: product_product_type; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (1, 1);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (2, 3);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (3, 1);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (4, 1);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (5, 4);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (6, 2);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (7, 3);
INSERT INTO public.product_product_type (product_id, product_type_id) VALUES (9, 1);

--
-- TOC entry 3440 (class 0 OID 16413)
-- Dependencies: 219
-- Data for Name: procurement; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.procurement (procurement_id, procurement_date, procurement_state, user_id) VALUES (1, '2024-03-18 14:17:26.732133+00', 'COMPLETE', 2);
INSERT INTO public.procurement (procurement_id, procurement_date, procurement_state, user_id) VALUES (2, '2024-03-18 14:17:45.076134+00', 'COMPLETE', 2);
INSERT INTO public.procurement (procurement_id, procurement_date, procurement_state, user_id) VALUES (3, '2024-03-18 14:30:38.770606+00', 'VALIDATE', 2);
INSERT INTO public.procurement (procurement_id, procurement_date, procurement_state, user_id) VALUES (4, '2024-03-18 14:30:57.536873+00', 'CREATED', 2);


--
-- TOC entry 3439 (class 0 OID 16408)
-- Dependencies: 218
-- Data for Name: line_procurement; Type: TABLE DATA; Schema: public; Owner: springuser
--

INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (1, 3, 30, 1, 1);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (2, 1, 10, 2, 1);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (4, 1, 21, 3, 8);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (3, 2, 20, 3, 1);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (7, 2, 34, 4, 6);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (6, 2, 42, 4, 8);
INSERT INTO public.line_procurement (line_id, line_quantity, line_unit_price, procurement_id, product_id) VALUES (5, 2, 24, 4, 4);










--
-- TOC entry 3463 (class 0 OID 0)
-- Dependencies: 227
-- Name: body_part_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.body_part_seq', 101, true);


--
-- TOC entry 3464 (class 0 OID 0)
-- Dependencies: 228
-- Name: house_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.house_seq', 51, true);


--
-- TOC entry 3465 (class 0 OID 0)
-- Dependencies: 229
-- Name: jwt_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.jwt_seq', 201, true);


--
-- TOC entry 3466 (class 0 OID 0)
-- Dependencies: 230
-- Name: line_procurement_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.line_procurement_seq', 51, true);


--
-- TOC entry 3467 (class 0 OID 0)
-- Dependencies: 231
-- Name: procurement_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.procurement_seq', 51, true);


--
-- TOC entry 3468 (class 0 OID 0)
-- Dependencies: 232
-- Name: product_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.product_seq', 51, true);


--
-- TOC entry 3469 (class 0 OID 0)
-- Dependencies: 233
-- Name: product_type_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.product_type_seq', 51, true);


--
-- TOC entry 3470 (class 0 OID 0)
-- Dependencies: 234
-- Name: role_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.role_seq', 51, true);


--
-- TOC entry 3471 (class 0 OID 0)
-- Dependencies: 235
-- Name: user_table_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.user_table_seq', 51, true);


--
-- TOC entry 3472 (class 0 OID 0)
-- Dependencies: 236
-- Name: validation_seq; Type: SEQUENCE SET; Schema: public; Owner: springuser
--

SELECT pg_catalog.setval('public.validation_seq', 51, true);


-- Completed on 2024-03-18 14:33:21 UTC

--
-- PostgreSQL database dump complete
--

