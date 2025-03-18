INSERT INTO public.companies VALUES (1, true, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'SPISOFT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.employees VALUES (1, true, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, '001', NULL, NULL, NULL, 'MOHAMED', NULL, NULL, NULL, NULL, 'KHEMIRI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO public.users VALUES (1, true, NULL, NULL, NULL, NULL, 1, 1, 'user@yopmail.com', true, '2023-02-26 19:18:51.983', NULL, '$2a$10$Ke.BfnmWqDr5nW5aPcctpO4hOsJd4Y4yAqYDN03qMTsiDK4MBD73m', NULL, '{GL_A,GL_S,ST_R,ST_W,ST_R,ST_A,ST_S,AC_R,AC_W,AC_R,AC_A,AC_S,CO_R,CO_W,CO_R,CO_A,CO_S,FI_R,FI_W,FI_R,FI_A,FI_S,CR_R,CR_W,CR_R,CR_A,CR_S,SV_W,SV_R,SV_A,SV_S,PJ_R,PJ_W,PJ_R,PJ_A,PJ_S,AN_R,AN_W,AN_R,AN_A,AN_S,AL_R,AL_W,AL_R,AL_A,AL_S,PR_M_R,PR_M_W,PR_M_R,PR_M_A,PR_M_S,PR_A_R,PR_A_W,PR_A_R,PR_A_A,PR_A_S,QT_R,QT_W,QT_R,QT_A,QT_S,HR_R,HR_W,HR_R,HR_A,HR_S,MS_R,MS_W,MS_R,MS_A,MS_S,ERP_A}', NULL ,1);

INSERT INTO public.client VALUES (1, true, NULL, NULL, NULL, NULL, 0, 0000, 'yass', 'yass', 1);
INSERT INTO public.client VALUES (2, true, NULL, NULL, NULL, NULL, 0, 0001, 'amal', 'amal', 1);





INSERT INTO public.risk_assessment VALUES (1, true, NULL, NULL, NULL, NULL, 0, 'desc', NULL, 'fffff', 'sfsfsf', 12, 1);
INSERT INTO public.risk_assessment VALUES (2, true, 'MOHAMED KHEMIRI', '2023-02-26 19:21:15.953', '2023-02-26 19:21:15.953', 'MOHAMED KHEMIRI', 0, 'string', 'string', 'string', 'string', 0, 1);



SELECT pg_catalog.setval('public.cherry_ref_configs_seq', 1, false);

SELECT pg_catalog.setval('public.companies_seq', 1, false);

SELECT pg_catalog.setval('public.currencies_seq', 1, false);

SELECT pg_catalog.setval('public.discussion_messages_seq', 1, false);

SELECT pg_catalog.setval('public.discussions_seq', 1, false);

SELECT pg_catalog.setval('public.document_taxes_seq', 1, false);

SELECT pg_catalog.setval('public.employees_seq', 1, false);

SELECT pg_catalog.setval('public.generic_parameters_seq', 1, false);

SELECT pg_catalog.setval('public.licenses_seq', 1, false);

SELECT pg_catalog.setval('public.notifications_seq', 1, false);

SELECT pg_catalog.setval('public.payment_history_seq', 1, false);

SELECT pg_catalog.setval('public.response_rest_msg_seq', 1, false);

SELECT pg_catalog.setval('public.risk_assessment_seq', 2, true);

SELECT pg_catalog.setval('public.taxes_seq', 1, false);

SELECT pg_catalog.setval('public.users_seq', 1, false);

/*
 // LOGIN
 http://localhost:8888/cherry-hse/login/authenticate
 {
  "latitude": 0,
  "longitude": 0,
  "password": "123456",
  "username": "user@yopmail.com"
}

 token => Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQHlvcG......
 */
