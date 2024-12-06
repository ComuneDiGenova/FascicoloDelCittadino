--------------------------------------------------------
--  File creato - venerdì-novembre-08-2024   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for View CZRM_SERVIZI
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."CZRM_SERVIZI" ("SERVIZIO_VALUE", "SERVIZIO_TEXT", "SOTTO_FASCICOLO_VALUE", "SOTTO_FASCICOLO_ID") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
    f.servizio servizio_value, 
    f.servizio servizio_text, 
    f.sotto_fascicolo sotto_fascicolo_value,
    null sotto_fascicolo_id
 FROM CFG_CZRM_SERVIZI f
 order by f.servizio
;
  GRANT SELECT ON "LDFDCADM"."CZRM_SERVIZI" TO "LDFDC";
--------------------------------------------------------
--  DDL for View CZRM_SOTTO_FASCICOLI
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."CZRM_SOTTO_FASCICOLI" ("SOTTO_FASCICOLO_VALUE", "SOTTO_FASCICOLO_TEXT") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  Select 'VARIE' sotto_fascicolo_value, 'VARIE' sotto_fascicolo_text from dual
UNION ALL
SELECT
    UPPER(s.descrizione_sez),
    UPPER(s.descrizione_sez)
FROM
    cfg_t_cat_sez s
where s.id_sez > 0 
and UPPER(s.descrizione_sez) NOT IN ('IO SEGNALO')
and exists (select 1 from cfg_t_cat_comp c 
                                             inner join cfg_t_cat_funz f on c.id_comp = f.id_comp
           
                            where c.id_sez = s.id_sez)
order by 1
;
  GRANT SELECT ON "LDFDCADM"."CZRM_SOTTO_FASCICOLI" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CATALOGO_DELEGHE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CATALOGO_DELEGHE" ("JSON_RES") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  WITH t1 (
        cod_cefriel,
        id_delega,
        id_delega_padre,
        livello,
        path,
        service_name,
        service_description,
        delegation_mode_on_line,
        delegation_mode_off_line
        
    ) AS (
        SELECT
            cod_cefriel,
            id_delega,
            id_delega_padre,
            1 AS livello,
            path,
            service_name,
            service_description,
        delegation_mode_on_line,
        delegation_mode_off_line
        FROM
            cfg_t_cat_funz_deleghe
        WHERE
            id_delega_padre IS NULL
            AND id_delega <> 0
        UNION ALL
        SELECT
            t2.cod_cefriel,
            t2.id_delega,
            t2.id_delega_padre,
            livello + 1,
            t1.path
            || '/'
            || t2.path,
            t2.service_name,
            t2.service_description,
        t2.delegation_mode_on_line,
        t2.delegation_mode_off_line
        FROM
                 cfg_t_cat_funz_deleghe t2
            INNER JOIN t1 ON t2.id_delega_padre = t1.id_delega
        WHERE
            t2.id_delega_padre <> 0
    )
        SEARCH DEPTH FIRST BY id_delega SET order1, t1_with_leadlag AS (
        SELECT
            r.*,
            LAG(livello)
            OVER(
                ORDER BY
                    order1
            ) AS lag_lvl,
            LEAD(livello, 1, 1)
            OVER(
                ORDER BY
                    order1
            ) AS lead_lvl,
            case when coalesce(LEAD(livello) OVER(ORDER BY order1), -1) <= livello then
            JSON_OBJECT(
                    'id' VALUE cod_cefriel,
                            'service_name#it' VALUE service_name,
                            'service_description#it' VALUE service_description,
                            'service_url' VALUE lower(cfg_value || path),
                            'delegation_use_mode' value json_array(
                                case when delegation_mode_on_line = 1 then 'online' end,
                                case when delegation_mode_off_line = 1 then 'offline' end
                            )                            
                )
                else
                JSON_OBJECT(
                    'id' VALUE cod_cefriel,
                            'service_name#it' VALUE service_name,
                            'service_description#it' VALUE service_description                            
                )
                end
            jso
        FROM
            t1 r,
            cfg_key_value
        WHERE
            cfg_key = 'FDC'
    )
    SELECT
        '['
        || XMLCAST(XMLAGG(XMLELEMENT(
            e,
       CASE
           WHEN lead_lvl > livello
                AND lag_lvl IS NOT NULL THEN
                        ','
       END
       ||
       CASE
                    WHEN livello - lag_lvl = 1 THEN
                        ',"children":['
                    WHEN livello > 1           THEN
                        ','
       END
       || substr(jso,
                 1,
                 length(jso) - 1)
       ||
       CASE
           WHEN livello >= lead_lvl THEN
                        '}'
                        || rpad(' ',(livello - lead_lvl) * 2 + 1, ']}')
       END
        )
            ORDER BY
                order1
        ) AS CLOB)
        || ']' AS json_res
    FROM
        t1_with_leadlag
;
  GRANT SELECT ON "LDFDCADM"."V_CATALOGO_DELEGHE" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CATALOGO_SIRAC
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CATALOGO_SIRAC" ("JSON_RES") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
        JSON_ARRAYAGG(y.z format json RETURNING CLOB STRICT)
    FROM
        (
            SELECT
                    JSON_OBJECT(
                        KEY 'service_name#it' VALUE d.service_name,
                        KEY 'service_backend_url' VALUE cfg_value
                                                        || a.path,
                        KEY 'spidLevel' VALUE d.spidlevel,
                        KEY 'service_attribute_class' VALUE d.service_attribute_class,
                        KEY 'ssoGroup' VALUE d.ssogroup
                    )
                z
            FROM
                     (
                    WITH t1 (
                        id_delega,
                        id_delega_padre,
                        livello,
                        path
                    ) AS (
                        SELECT
                            id_delega,
                            id_delega_padre,
                            1 AS livello,
                            lower(path)
                        FROM
                            cfg_t_cat_funz_deleghe
                        WHERE
                            id_delega_padre IS NULL
                            AND id_delega <> 0
                        UNION ALL
                        SELECT
                            t2.id_delega,
                            t2.id_delega_padre,
                            livello + 1,
                            lower(t1.path
                                  || '/'
                                  || t2.path)
                        FROM
                                 cfg_t_cat_funz_deleghe t2
                            INNER JOIN t1 ON t2.id_delega_padre = t1.id_delega
                    )
                        SEARCH DEPTH FIRST BY id_delega SET order1
                    SELECT
                        id_delega,
                        id_delega_padre,
                        livello,
                        path,
                        LEAD(livello, 1, 1)
                        OVER(
                            ORDER BY
                                order1
                        ),
                        LAG(livello, 1, 1)
                        OVER(
                            ORDER BY
                                order1
                        ),
                        CASE
                            WHEN LEAD(livello, 1, 1)
                                 OVER(
                                ORDER BY
                                    order1
                                 ) <= livello THEN
                                1
                            ELSE
                                0
                        END AS foglia
                    FROM
                        t1
                ) a
                INNER JOIN cfg_t_cat_funz_deleghe d ON a.id_delega = d.id_delega,
                cfg_key_value
            WHERE
                    a.foglia = 1
                AND cfg_key = 'FDC_INTERNO'
            UNION ALL
            SELECT
                JSON_OBJECT(
                    KEY 'service_name#it' VALUE service_name,
                            KEY 'service_backend_url' VALUE cfg_value || lower(path),
                            KEY 'spidLevel' VALUE spidlevel,
                            KEY 'service_attribute_class' VALUE service_attribute_class,
                            KEY 'ssoGroup' VALUE ssogroup
                )
            FROM
                cfg_t_cat_funz_deleghe,
                cfg_key_value
            WHERE
                    id_delega = 0
                AND cfg_key = 'FDC_INTERNO'
        ) y
;
  GRANT SELECT ON "LDFDCADM"."V_CATALOGO_SIRAC" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CERTIFICATI_TIPI
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CERTIFICATI_TIPI" ("ID_T_CERTIFICATO", "CODICE_ANPR", "TIPO", "EVENTO", "CERTIFICATO", "INVIO", "ANNO_DA", "ANNO_A", "MARCA_DA_BOLLO", "RIMBORSO_SPESE", "DIRITTI_DI_SEGRETERIA", "RESTRIZIONI", "INFO_DA_CHIEDERE", "ID_STATO_REC_TIPO", "ID_STATO_REC_INVIO", "UTENTE_INS", "DATA_INS", "UTENTE_AGG", "DATA_AGG") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
    t.id_t_certificato,
    t.codice_anpr,
    t.tipo,
    t.evento,
    t.certificato,
    i.invio,
    i.anno_da,
    i.anno_a,
    i.marca_da_bollo,
    i.rimborso_spese,
    i.diritti_di_segreteria,
    t.restrizioni,
    t.info_da_chiedere,
    t.id_stato_rec id_stato_rec_tipo,
    i.id_stato_rec id_stato_rec_invio,
    t.utente_ins,
    t.data_ins,
    t.utente_agg,
    t.data_agg
FROM
    CERTIFICATI_TIPI t inner join CERTIFICATI_TIPI_invio i on t.id_t_certificato = i.id_t_certificato
;
  GRANT SELECT ON "LDFDCADM"."V_CERTIFICATI_TIPI" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_R_FCITT_COMP
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_R_FCITT_COMP" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_CFG_R_FCITT_COMP", "ID_FCITT", "DATA_REGISTRAZ_FCITT_COMP", "DATA_DEREGISTRAZ_FCITT_COMP", "FLAG_ABILITAZIONE_CITT_COMP", "PERSON_ID") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  r.ID_CFG_R_FCITT_COMP,
  r.ID_FCITT,
  r.DATA_REGISTRAZ_FCITT_COMP,
  r.DATA_DEREGISTRAZ_FCITT_COMP,
  r.flag_abilitazione flag_abilitazione_citt_COMP,
  u.PERSON_ID
FROM CFG_T_CAT_COMP c
     inner join CFG_T_CAT_SEZ s on c.ID_SEZ = s.ID_SEZ
     inner join CFG_R_FCITT_COMP r on c.ID_COMP = r.ID_COMP
     inner join CFG_T_FCITT u on r.ID_FCITT = u.ID_FCITT
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_R_FCITT_COMP" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_R_FCITT_NOTIFICHE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_R_FCITT_NOTIFICHE" ("ID_NOTIFICA", "ID_COMP", "ID_SEZ", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "URI_SEZ", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "ORDINAMENTO", "FLAG_ABILITAZIONE_COMP", "TESTO_NOTIFICA", "URI_NOTIFICA", "DATA_NOTIFICA", "FLAG_ABILITAZIONE_NOTIF", "ID_FCITT_NOTIFICHE", "ID_FCITT", "PERSON_ID", "DATA_PRESA_VISIONE", "ID_STATO_REC") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  m.ID_NOTIFICA,
  m.ID_COMP,
  c.ID_SEZ,
  sz.DENOMINAZIONE_SEZ,
  sz.DESCRIZIONE_SEZ,
  sz.URI_SEZ,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  nvl(c.URI_COMP, sz.URI_SEZ) URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.ORDINAMENTO,
  c.FLAG_ABILITAZIONE FLAG_ABILITAZIONE_COMP,
  m.TESTO_NOTIFICA,
  m.URI_NOTIFICA,
  nvl(r.DATA_INS,m.DATA_NOTIFICA) DATA_NOTIFICA,
  m.flag_abilitazione FLAG_ABILITAZIONE_NOTIF,
  r.ID_FCITT_NOTIFICHE,
  r.ID_FCITT,
  u.PERSON_ID,
  r.DATA_PRESA_VISIONE,
  r.ID_STATO_REC
FROM CFG_T_NOTIFICHE m 
     inner join CFG_T_CAT_COMP c on m.ID_COMP = c.ID_COMP
     inner join CFG_T_CAT_SEZ sz on c.ID_SEZ = sz.ID_SEZ
     inner join CFG_R_FCITT_NOTIFICHE r on m.ID_NOTIFICA = r.ID_NOTIFICA
     inner join CFG_T_FCITT u on r.ID_FCITT = u.ID_FCITT
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_R_FCITT_NOTIFICHE" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_R_FCITT_WIDG
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_R_FCITT_WIDG" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_DEFAULT", "FLAG_ABILITAZIONE_WIDG", "ID_CFG_R_FCITT_WIDG", "ID_FCITT", "DATA_ASSOCIAZIONE_FCITT_WIDG", "DATA_DEASSOCIAZIONE_FCITT_WIDG", "FLAG_ABILITAZIONE_FCITT_WIDG", "PERSON_ID", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  w.ORDINAMENTO ORDINAMENTO_W,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.flag_abilitazione flag_abilitazione_funz,
  ss.ID_FUNZ_SOSP,
  ss.DATA_INIZIO_SOSP,
  ss.DATA_FINE_SOSP,
  ss.TIPO_SOSP,
  ss.flag_abilitazione flag_abilitazione_FUNZ_SOSP,
  w.ID_WIDG,
  w.DENOMINAZIONE_WIDG,
  w.DESCRIZIONE_WIDG,
  w.URI_WIDG,
  w.DATA_CATALOGAZIONE_WIDG,
  w.FLAG_DEFAULT,
  w.flag_abilitazione flag_abilitazione_WIDG,
  r.ID_CFG_R_FCITT_WIDG,
  r.ID_FCITT,
  r.DATA_ASSOCIAZIONE_FCITT_WIDG,
  r.DATA_DEASSOCIAZIONE_FCITT_WIDG,
  r.flag_abilitazione flag_abilitazione_FCITT_WIDG,
  u.PERSON_ID,
  f.FLAG_RESIDENTE,
  f.FLAG_NON_RESIDENTE
FROM CFG_T_CAT_SEZ s
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     inner join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     inner join CFG_T_CAT_WIDG w on f.ID_FUNZ = w.ID_FUNZ
     left outer join CFG_T_CAT_FUNZ_SOSP ss on f.ID_FUNZ = ss.ID_FUNZ
     inner join CFG_R_FCITT_WIDG r on w.ID_WIDG = r.ID_WIDG
     inner join CFG_T_FCITT u on r.ID_FCITT = u.ID_FCITT
order by s.ORDINAMENTO, c.ordinamento, w.ordinamento

;
  GRANT SELECT ON "LDFDCADM"."V_CFG_R_FCITT_WIDG" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_R_FCITT_WIDG_DELEGHE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_R_FCITT_WIDG_DELEGHE" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_DEFAULT", "FLAG_ABILITAZIONE_WIDG", "ID_CFG_R_FCITT_WIDG", "ID_FCITT", "DATA_ASSOCIAZIONE_FCITT_WIDG", "DATA_DEASSOCIAZIONE_FCITT_WIDG", "FLAG_ABILITAZIONE_FCITT_WIDG", "PERSON_ID", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_DELEGA") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  w.ORDINAMENTO ORDINAMENTO_W,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.flag_abilitazione flag_abilitazione_funz,
  ss.ID_FUNZ_SOSP,
  ss.DATA_INIZIO_SOSP,
  ss.DATA_FINE_SOSP,
  ss.TIPO_SOSP,
  ss.flag_abilitazione flag_abilitazione_FUNZ_SOSP,
  w.ID_WIDG,
  w.DENOMINAZIONE_WIDG,
  w.DESCRIZIONE_WIDG,
  w.URI_WIDG,
  w.DATA_CATALOGAZIONE_WIDG,
  w.FLAG_DEFAULT,
  w.flag_abilitazione flag_abilitazione_WIDG,
  r.ID_CFG_R_FCITT_WIDG,
  r.ID_FCITT,
  r.DATA_ASSOCIAZIONE_FCITT_WIDG,
  r.DATA_DEASSOCIAZIONE_FCITT_WIDG,
  r.flag_abilitazione flag_abilitazione_FCITT_WIDG,
  u.PERSON_ID,
  f.FLAG_RESIDENTE,
  f.FLAG_NON_RESIDENTE,
  f.id_delega
FROM CFG_T_CAT_SEZ s
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     inner join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     inner join CFG_T_CAT_WIDG w on f.ID_FUNZ = w.ID_FUNZ
     left outer join CFG_T_CAT_FUNZ_SOSP ss on f.ID_FUNZ = ss.ID_FUNZ
     inner join CFG_R_FCITT_WIDG r on w.ID_WIDG = r.ID_WIDG
     inner join CFG_T_FCITT u on r.ID_FCITT = u.ID_FCITT
order by s.ORDINAMENTO, c.ordinamento, w.ordinamento

;
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_BREADCRUMB
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_BREADCRUMB" ("FIGLIOFUNZ", "FUNZ", "DESCRFUNZ", "IDPAGINA", "ISFIGLIO", "ISSEZIONE", "ISDELEGABILE", "ID_SEZ", "DESCRIZIONE_SEZ", "URI_SEZ") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
   FiglioFunz, 
   Funz, 
   DescrFunz, 
   IdPagina,
   isFiglio,
   isSezione,
   isDelegabile,
   ID_SEZ, 
   DESCRIZIONE_SEZ, 
   URI_SEZ 
FROM (
SELECT 
       figlio.ID_FUNZ as FiglioFunz,
       figlio.ID_FUNZ as Funz, 
       figlio.DESCRIZIONE_FUNZ as DescrFunz,
       figlio.ID_PAGINA as IdPagina,
       1 as isFiglio,
       0 as isSezione,
       CASE WHEN figlio.id_delega IS NOT NULL AND figlio.id_delega > 0 THEN 1 ELSE 0 END isdelegabile,
       NULL as ID_SEZ, 
       NULL AS DESCRIZIONE_SEZ,
       NULL AS URI_SEZ
FROM CFG_T_CAT_FUNZ figlio 
UNION ALL 
SELECT figlio.ID_FUNZ as FiglioFunz,
         padre.ID_FUNZ as Funz, 
       padre.DESCRIZIONE_FUNZ as DescrFunz,
       padre.ID_PAGINA as IdPagina,
       0 AS isFiglio,
       0 as isSezione, 
       CASE WHEN padre.id_delega IS NOT NULL AND padre.id_delega > 0 THEN 1 ELSE 0 END isdelegabile,
        NULL as ID_SEZ, 
       NULL AS DESCRIZIONE_SEZ,
       NULL AS URI_SEZ
FROM CFG_T_CAT_FUNZ figlio
INNER JOIN CFG_T_CAT_FUNZ padre ON figlio.ID_FUNZ_PADRE = padre.ID_FUNZ
UNION ALL 
SELECT 
       figlio.ID_FUNZ as FiglioFunz,
       null as Funz, 
       NULL as DescrFunz,
       NULL as IdPagina,
       0 as isFiglio,
       1 as isSezione,
       0 as isDelegabile,
       s.ID_SEZ, 
       s.DESCRIZIONE_SEZ,
       s.DENOMINAZIONE_SEZ
FROM CFG_T_CAT_FUNZ padre 
INNER JOIN CFG_T_CAT_FUNZ figlio 
ON padre.ID_FUNZ = figlio.ID_FUNZ_PADRE
INNER JOIN CFG_T_CAT_COMP c ON padre.ID_COMP = c.ID_COMP
INNER JOIN CFG_T_CAT_SEZ s on c.ID_SEZ = s.ID_SEZ
) A
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_BREADCRUMB" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_COMP
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_COMP" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp
FROM CFG_T_CAT_SEZ s 
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
order by s.ORDINAMENTO, c.ordinamento
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_COMP" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_FUNZ
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_FUNZ" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "ICONA_CSS", "FLAG_ABILITAZIONE_FUNZ", "ID_STATO_REC", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_FUNZ_PADRE", "ID_PAGINA", "PATH", "ID_DELEGA", "CLASSE_PADRE", "PATH_PADRE") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.ICONA_CSS,
  f.flag_abilitazione flag_abilitazione_funz,
  f.ID_STATO_REC,
  f.FLAG_RESIDENTE,
  f.FLAG_NON_RESIDENTE,
  f.ID_FUNZ_PADRE,
  f.id_pagina,
  d.path,
  d.id_delega,
  f1.classe_pagina_std as classe_padre,
  d1.path as path_padre
FROM CFG_T_CAT_SEZ s
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     inner join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     left join CFG_T_CAT_FUNZ_DELEGHE d on d.id_delega = f.id_delega
     left join CFG_T_CAT_FUNZ f1 on f1.id_funz=f.ID_FUNZ_PADRE
     left join CFG_T_CAT_FUNZ_DELEGHE d1 on d1.id_delega = f1.id_delega
where nvl(f.CLASSE_PAGINA_STD,'ASSENTE') <> 'GloboPage'
UNION
SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.ICONA_CSS,
  f.flag_abilitazione flag_abilitazione_funz,
  f.ID_STATO_REC,
  f.FLAG_RESIDENTE,
  f.FLAG_NON_RESIDENTE,
  f.ID_FUNZ_PADRE,
  f.id_pagina,
  d.path,
  d.id_delega,
  f1.classe_pagina_std as classe_padre,
  d1.path as path_padre
FROM CFG_T_CAT_SEZ s
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     inner join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     left join CFG_T_CAT_FUNZ_DELEGHE d on d.id_delega = f.id_delega
     left join CFG_T_CAT_FUNZ f1 on f1.id_funz=f.ID_FUNZ_PADRE
     left join CFG_T_CAT_FUNZ_DELEGHE d1 on d1.id_delega = f1.id_delega
where nvl(f.CLASSE_PAGINA_STD,'ASSENTE') = 'GloboPage'
      and f.ID_FUNZ in (select distinct id_funz from v_cfg_t_cat_globo where flag_abilitazione = 1)
order by ORDINAMENTO_S, ORDINAMENTO_C, ID_FUNZ

;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_FUNZ" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_FUNZ_SOSP
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_FUNZ_SOSP" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "ID_STATO_REC", "TIPO_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.flag_abilitazione flag_abilitazione_funz,
  f.ID_STATO_REC,
  nvl(s.TIPO_SOSP,0) TIPO_SOSP,
  nvl(s.DATA_INIZIO_SOSP,sysdate) DATA_INIZIO_SOSP,
  nvl(s.DATA_FINE_SOSP,sysdate) DATA_FINE_SOSP
FROM CFG_T_CAT_SEZ s 
     inner join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     inner join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     left outer join CFG_T_CAT_FUNZ_SOSP s on f.ID_FUNZ = s.ID_FUNZ
order by s.ORDINAMENTO, c.ordinamento, f.ID_FUNZ
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_FUNZ_SOSP" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_GLOBO
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_GLOBO" ("ID_FUNZ", "ID_COMP", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE", "ID_STATO_REC", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_PAGINA", "ICONA_CSS", "ID_PROCEDIMENTO", "ID_GLOBO", "ID_MAGGIOLI", "DENOMINAZIONE_PROCEDIMENTO", "URL_GLOBO_NEW", "URL_GLOBO_LISTA", "URL_SITO", "FLAG_ABILITAZIONE_GLOBO", "ID_STATO_REC_GLOBO", "FLAG_RESIDENTE_GLOBO", "FLAG_NON_RESIDENTE_GLOBO", "URN_PROCEDIMENTO_GLOBO", "DATA_AGG") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
    f.id_funz,
    f.id_comp,
    f.denominazione_funz,
    f.descrizione_funz,
    f.wicket_label_id_std,
    f.wicket_label_id_alt,
    f.classe_pagina_std,
    f.classe_pagina_alt,
    f.wicket_title_std,
    f.wicket_title_alt,
    case f.flag_abilitazione
     when 0 then f.flag_abilitazione
     else g.flag_abilitazione
    end flag_abilitazione,
    f.id_stato_rec,
    f.flag_residente,
    f.flag_non_residente,
    f.id_pagina,
    f.icona_css,
    fg.id_procedimento,
    g.id_globo,
    g.id_maggioli,
    g.denominazione_procedimento,
    g.url_globo_new,
    g.url_globo_lista,
    g.url_sito,
    g.flag_abilitazione flag_abilitazione_globo,
    g.id_stato_rec id_stato_rec_globo,
    g.flag_residente flag_residente_globo,
    g.flag_non_residente flag_non_residente_globo,
    g.URN_PROCEDIMENTO_GLOBO,
    g.DATA_AGG
FROM
    cfg_t_cat_globo g
       left outer join cfg_t_cat_funz_globo fg on g.id_globo = fg.id_globo
       left outer join cfg_t_cat_funz f on  fg.id_funz = f.id_funz
order by f.id_funz, g.id_globo
;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_GLOBO" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_WIDG
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_WIDG" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_ABILITAZIONE_WIDG", "FLAG_DEFAULT") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  w.ORDINAMENTO ORDINAMENTO_W,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.flag_abilitazione flag_abilitazione_funz,
  f.flag_residente,
  f.flag_non_residente,
  ss.ID_FUNZ_SOSP,
  ss.DATA_INIZIO_SOSP,
  ss.DATA_FINE_SOSP,
  ss.TIPO_SOSP,
  ss.flag_abilitazione flag_abilitazione_FUNZ_SOSP,
  w.ID_WIDG,
  w.DENOMINAZIONE_WIDG,
  w.DESCRIZIONE_WIDG,
  w.URI_WIDG,
  w.DATA_CATALOGAZIONE_WIDG,
  w.flag_abilitazione flag_abilitazione_WIDG,
  w.FLAG_DEFAULT
FROM CFG_T_CAT_SEZ s
     left outer join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     left outer join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     left outer join CFG_T_CAT_WIDG w on f.ID_FUNZ = w.ID_FUNZ
     left outer join CFG_T_CAT_FUNZ_SOSP ss on f.ID_FUNZ = ss.ID_FUNZ
order by s.ORDINAMENTO, c.ordinamento, w.ordinamento

;
  GRANT SELECT ON "LDFDCADM"."V_CFG_T_CAT_WIDG" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_CFG_T_CAT_WIDG_DELEGHE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_CFG_T_CAT_WIDG_DELEGHE" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_ABILITAZIONE_WIDG", "FLAG_DEFAULT", "ID_DELEGA") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT
  s.ID_SEZ,
  s.ORDINAMENTO ORDINAMENTO_S,
  c.ORDINAMENTO ORDINAMENTO_C,
  w.ORDINAMENTO ORDINAMENTO_W,
  s.DENOMINAZIONE_SEZ,
  s.DESCRIZIONE_SEZ,
  s.DATA_CATALOGAZIONE_SEZ,
  s.URI_SEZ,
  s.flag_abilitazione flag_abilitazione_sez,
  c.ID_COMP,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.DATA_INVIO_MSG,
  c.flag_abilitazione flag_abilitazione_comp,
  f.ID_FUNZ,
  f.DENOMINAZIONE_FUNZ,
  f.DESCRIZIONE_FUNZ,
  f.WICKET_LABEL_ID_STD,
  f.WICKET_LABEL_ID_ALT,
  f.CLASSE_PAGINA_STD,
  f.CLASSE_PAGINA_ALT,
  f.WICKET_TITLE_STD,
  f.WICKET_TITLE_ALT,
  f.flag_abilitazione flag_abilitazione_funz,
  f.flag_residente,
  f.flag_non_residente,
  ss.ID_FUNZ_SOSP,
  ss.DATA_INIZIO_SOSP,
  ss.DATA_FINE_SOSP,
  ss.TIPO_SOSP,
  ss.flag_abilitazione flag_abilitazione_FUNZ_SOSP,
  w.ID_WIDG,
  w.DENOMINAZIONE_WIDG,
  w.DESCRIZIONE_WIDG,
  w.URI_WIDG,
  w.DATA_CATALOGAZIONE_WIDG,
  w.flag_abilitazione flag_abilitazione_WIDG,
  w.FLAG_DEFAULT,
  f.id_delega
FROM CFG_T_CAT_SEZ s
     left outer join CFG_T_CAT_COMP c on s.ID_SEZ = c.ID_SEZ
     left outer join CFG_T_CAT_FUNZ f on c.ID_COMP = f.ID_COMP
     left outer join CFG_T_CAT_WIDG w on f.ID_FUNZ = w.ID_FUNZ
     left outer join CFG_T_CAT_FUNZ_SOSP ss on f.ID_FUNZ = ss.ID_FUNZ
order by s.ORDINAMENTO, c.ordinamento, w.ordinamento

;
--------------------------------------------------------
--  DDL for View V_FDC_AUDIT
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_FDC_AUDIT" ("ID_FCITT", "NOME_PAGINA", "AMBIENTE", "SESSION_ID", "AUTORIZZATO", "TIME_STAMP", "TIPO_UTENTE", "DESCRIZIONE_TIPO_UTENTE", "DESCRIZIONE_FUNZ", "ID_FUNZ", "DENOMINAZIONE_COMP", "ID_COMP", "DESCRIZIONE_SEZ", "ID_SEZ") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  select
  a.ID_FCITT,
  a.NOME_PAGINA,
  a.AMBIENTE,
  a.SESSION_ID,
  a.AUTORIZZATO,
  a.TIME_STAMP,
  a.TIPO_UTENTE,
  t.descrizione_tipo_utente,
  f.descrizione_funz,
  nvl(f.id_funz,-1) id_funz,
  c.denominazione_comp,
  nvl(c.id_comp,-1) id_comp,
  s.descrizione_sez,
  nvl(s.id_sez,-1) id_sez
FROM FDC_AUDIT a

  INNER JOIN FDC_T_UTENTI t on a.tipo_utente = t.id_tipo_utente
  left outer join cfg_t_cat_funz f on a.NOME_PAGINA = f.classe_pagina_std
  left outer join cfg_t_cat_comp c on f.id_comp = c.id_comp
  left outer join cfg_t_cat_sez s on c.id_sez = s.id_sez
  where trunc(a.time_stamp) > add_months (sysdate, -1)
UNION ALL
select
  p.ID_ANAGRAFICA ID_FCITT,
  'Home Page' NOME_PAGINA,
  'ESERCIZIO' AMBIENTE,
  to_char(p.DATA_PRESA_VISIONE,'DD/MM/YYYY HH24:MI:SS')||' '||id_anagrafica SESSION_ID,
  1 AUTORIZZATO,
  p.DATA_PRESA_VISIONE TIME_STAMP,
  1 TIPO_UTENTE,
  'Residente' descrizione_tipo_utente,
  null descrizione_funz,
  -1 id_funz,
  null denominazione_comp,
  -1 id_comp,
  null descrizione_sez,
  -1 id_sez
FROM PR_PRIVACY_PRESA_VISIONE p
where p.DATA_PRESA_VISIONE < to_date('15/05/2020 12:38','DD/MM/YYYY HH24:MI')
;
  GRANT SELECT ON "LDFDCADM"."V_FDC_AUDIT" TO "LDFDC";
  GRANT SELECT ON "LDFDCADM"."V_FDC_AUDIT" TO "LDPERS";
  GRANT SELECT ON "LDFDCADM"."V_FDC_AUDIT" TO "LDPRIV";
--------------------------------------------------------
--  DDL for View V_FDC_UTENTI
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_FDC_UTENTI" ("ID_FCITT", "DATA_INS") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  select
  c.ID_FCITT,
  c.data_ins
FROM CFG_T_FCITT c
     inner join pr_privacy_presa_visione pv on c.ID_FCITT = pv.id_anagrafica
;
--------------------------------------------------------
--  DDL for View V_FDC_UTENTI_PRIVACY
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_FDC_UTENTI_PRIVACY" ("ID_FCITT", "CODICE_FISCALE", "TIPO", "ID_PRIVACY_VER", "ID_SERVIZIO", "DATA_INS", "DATA_PRESA_VISIONE") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  select
  c.ID_FCITT,
  u.codice_fiscale,
  u.tipo,
  pv.id_privacy_ver,
  pv.id_servizio,
  to_char(c.data_ins,'DD/MM/YYYY') data_ins,
  to_char(max(pv.data_ins),'DD/MM/YYYY') data_presa_visione
FROM CFG_T_FCITT c
     inner join (select p.person_id, p.codice_fiscale, 'residente' tipo from cpv_person p
                 union
                 select a.person_id, a.codice_fiscale, 'non residente' tipo from cpv_anonim a) u
                 on c.person_id = u.person_id
     left outer join pr_privacy_presa_visione pv on c.ID_FCITT = pv.id_anagrafica
group by 
  c.ID_FCITT,
  u.codice_fiscale,
  u.tipo,
  c.data_ins,
  pv.id_privacy_ver,
  pv.id_servizio
;
--------------------------------------------------------
--  DDL for View V_HOME_WIDG_DELEGHE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_HOME_WIDG_DELEGHE" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_DEFAULT", "FLAG_ABILITAZIONE_WIDG", "ID_CFG_R_FCITT_WIDG", "ID_FCITT", "DATA_ASSOCIAZIONE_FCITT_WIDG", "DATA_DEASSOCIAZIONE_FCITT_WIDG", "FLAG_ABILITAZIONE_FCITT_WIDG", "PERSON_ID", "FLAG_RESIDENTE", "FLAG_NON_RESIDENTE", "ID_DELEGA") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT DISTINCT CAT_W.ID_SEZ,
                CAT_W.ORDINAMENTO_S,
                CAT_W.ORDINAMENTO_C,
                CAT_W.ORDINAMENTO_W,
                CAT_W.DENOMINAZIONE_SEZ,
                CAT_W.DESCRIZIONE_SEZ,
                CAT_W.DATA_CATALOGAZIONE_SEZ,
                CAT_W.URI_SEZ,
                CAT_W.FLAG_ABILITAZIONE_SEZ,
                CAT_W.ID_COMP,
                CAT_W.DENOMINAZIONE_COMP,
                CAT_W.DESCRIZIONE_COMP,
                CAT_W.URI_COMP,
                CAT_W.DATA_CATALOGAZIONE_COMP,
                CAT_W.DATA_INVIO_MSG,
                CAT_W.FLAG_ABILITAZIONE_COMP,
                CAT_W.ID_FUNZ,
                CAT_W.DENOMINAZIONE_FUNZ,
                CAT_W.DESCRIZIONE_FUNZ,
                CAT_W.WICKET_LABEL_ID_STD,
                CAT_W.WICKET_LABEL_ID_ALT,
                CAT_W.CLASSE_PAGINA_STD,
                CAT_W.CLASSE_PAGINA_ALT,
                CAT_W.WICKET_TITLE_STD,
                CAT_W.WICKET_TITLE_ALT,
                CAT_W.FLAG_ABILITAZIONE_FUNZ,
                CAT_W.ID_FUNZ_SOSP,
                CAT_W.DATA_INIZIO_SOSP,
                CAT_W.DATA_FINE_SOSP,
                CAT_W.TIPO_SOSP,
                CAT_W.FLAG_ABILITAZIONE_FUNZ_SOSP,
                CAT_W.ID_WIDG,
                CAT_W.DENOMINAZIONE_WIDG,
                CAT_W.DESCRIZIONE_WIDG,
                CAT_W.URI_WIDG,
                CAT_W.DATA_CATALOGAZIONE_WIDG,
                CAT_W.FLAG_DEFAULT,
                CAT_W.FLAG_ABILITAZIONE_WIDG,
                NULL AS ID_CFG_R_FCITT_WIDG,
                NULL AS ID_FCITT,
                NULL AS DATA_ASSOCIAZIONE_FCITT_WIDG,
                NULL AS DATA_DEASSOCIAZIONE_FCITT_WIDG,
                NULL AS FLAG_ABILITAZIONE_FCITT_WIDG,
                NULL AS PERSON_ID,
                CAT_W.FLAG_RESIDENTE,
                CAT_W.FLAG_NON_RESIDENTE,
                CAT_W.id_delega
  FROM V_CFG_T_CAT_WIDG_DELEGHE CAT_W
 WHERE 1 = 1
 AND id_delega <>0

;
  GRANT SELECT ON "LDFDCADM"."V_HOME_WIDG_DELEGHE" TO "LDFDC";
  GRANT SELECT ON "LDFDCADM"."V_HOME_WIDG_DELEGHE" TO "LDPERS";
  GRANT SELECT ON "LDFDCADM"."V_HOME_WIDG_DELEGHE" TO "LDPRIV";
--------------------------------------------------------
--  DDL for View V_PL_T_ISTANZE_DOCUMENTI
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_PL_T_ISTANZE_DOCUMENTI" ("CODICE_HERMES", "TIPOLOGIA", "RIFERIMENTO_LEGGE", "MIN_DOC", "MAX_DOC", "CODICE", "DOCUMENTO", "FLG_AUTODICHIARAZIONE", "ID_DOC_ALTERNATIVO", "DOCUMENTO_ALTERNATIVO", "OBBLIGATORIO") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT i.CODICE_HERMES,
  i.TIPOLOGIA,
  i.RIFERIMENTO_LEGGE,
  i.MIN_DOC,
  i.MAX_DOC,
  d.codice,
  d.documento,
  d.FLG_AUTODICHIARAZIONE,
  d.id_doc_alternativo,
  da.documento documento_alternativo,
  id.obbligatorio
FROM PL_T_ISTANZE i
     left outer join PL_T_ISTANZE_DOCUMENTI id on i.CODICE_HERMES = id.CODICE_ISTANZA
     left outer join PL_T_DOCUMENTI d on id.CODICE_DOCUMENTO = d.CODICE
     left outer join PL_T_DOCUMENTI da on d.ID_DOC_ALTERNATIVO = da.CODICE
order by  i.CODICE_HERMES
;
  GRANT SELECT ON "LDFDCADM"."V_PL_T_ISTANZE_DOCUMENTI" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_PL_T_ISTANZE_SERIE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_PL_T_ISTANZE_SERIE" ("CODICE_HERMES", "TIPOLOGIA", "RIFERIMENTO_LEGGE", "MIN_DOC", "MAX_DOC", "FLG_INTEGRAZIONI", "TIPO_RELAZIONE_SERIE", "CODICE", "DESCRIZIONE_SERIE", "TIPO", "TIPO_DESCRIZIONE", "ARTICOLO", "SERIE", "COMMA") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT i.CODICE_HERMES,
  i.TIPOLOGIA,
  i.RIFERIMENTO_LEGGE,
  i.MIN_DOC,
  i.MAX_DOC,
  i.FLG_INTEGRAZIONI,
  i.TIPO_RELAZIONE_SERIE,
  d.codice,
  d.descrizione_serie,
  d.tipo,
  case d.tipo
   when 1 then 'serie'
   when 2 then 'articolo'
   when 3 then 'entrambi'
   else 'non previsto'
  end tipo_descrizione,
  d.articolo,
  d.serie,
  d.comma
FROM PL_T_ISTANZE i
     left outer join PL_T_ISTANZE_SERIE id on i.CODICE_HERMES = id.CODICE_ISTANZA
     left outer join PL_T_SERIE d on id.CODICE_SERIE = d.CODICE
order by  i.CODICE_HERMES
;
  GRANT SELECT ON "LDFDCADM"."V_PL_T_ISTANZE_SERIE" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_R_FCITT_WIDG_X_HOME
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_R_FCITT_WIDG_X_HOME" ("ID_SEZ", "ORDINAMENTO_S", "ORDINAMENTO_C", "ORDINAMENTO_W", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DATA_CATALOGAZIONE_SEZ", "URI_SEZ", "FLAG_ABILITAZIONE_SEZ", "ID_COMP", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "DATA_INVIO_MSG", "FLAG_ABILITAZIONE_COMP", "ID_FUNZ", "DENOMINAZIONE_FUNZ", "DESCRIZIONE_FUNZ", "WICKET_LABEL_ID_STD", "WICKET_LABEL_ID_ALT", "CLASSE_PAGINA_STD", "CLASSE_PAGINA_ALT", "WICKET_TITLE_STD", "WICKET_TITLE_ALT", "FLAG_ABILITAZIONE_FUNZ", "ID_FUNZ_SOSP", "DATA_INIZIO_SOSP", "DATA_FINE_SOSP", "TIPO_SOSP", "FLAG_ABILITAZIONE_FUNZ_SOSP", "ID_WIDG", "DENOMINAZIONE_WIDG", "DESCRIZIONE_WIDG", "URI_WIDG", "DATA_CATALOGAZIONE_WIDG", "FLAG_ABILITAZIONE_WIDG", "PRIORITA", "ID_CFG_R_FCITT_WIDG", "ID_FCITT", "DATA_ASSOCIAZIONE_FCITT_WIDG", "FLAG_ABILITAZIONE_FCITT_WIDG", "PERSON_ID") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  ID_SEZ,
  ORDINAMENTO_S,
  ORDINAMENTO_C,
  ORDINAMENTO_W,
  DENOMINAZIONE_SEZ,
  DESCRIZIONE_SEZ,
  DATA_CATALOGAZIONE_SEZ,
  URI_SEZ,
  FLAG_ABILITAZIONE_SEZ,
  ID_COMP,
  DENOMINAZIONE_COMP,
  DESCRIZIONE_COMP,
  URI_COMP,
  DATA_CATALOGAZIONE_COMP,
  DATA_INVIO_MSG,
  FLAG_ABILITAZIONE_COMP,
  ID_FUNZ,
  DENOMINAZIONE_FUNZ,
  DESCRIZIONE_FUNZ,
  WICKET_LABEL_ID_STD,
  WICKET_LABEL_ID_ALT,
  CLASSE_PAGINA_STD,
  CLASSE_PAGINA_ALT,
  WICKET_TITLE_STD,
  WICKET_TITLE_ALT,
  FLAG_ABILITAZIONE_FUNZ,
  ID_FUNZ_SOSP,
  DATA_INIZIO_SOSP,
  DATA_FINE_SOSP,
  TIPO_SOSP,
  FLAG_ABILITAZIONE_FUNZ_SOSP,
  ID_WIDG,
  DENOMINAZIONE_WIDG,
  DESCRIZIONE_WIDG,
  URI_WIDG,
  DATA_CATALOGAZIONE_WIDG,
  FLAG_ABILITAZIONE_WIDG,
  0 PRIORITA,
  ID_CFG_R_FCITT_WIDG,
  ID_FCITT,
  DATA_ASSOCIAZIONE_FCITT_WIDG,
  FLAG_ABILITAZIONE_FCITT_WIDG,
  PERSON_ID
FROM V_CFG_R_FCITT_WIDG 
UNION ALL
SELECT 
  d.ID_SEZ,
  d.ORDINAMENTO_S,
  d.ORDINAMENTO_C,
  d.ORDINAMENTO_W,
  d.DENOMINAZIONE_SEZ,
  d.DESCRIZIONE_SEZ,
  d.DATA_CATALOGAZIONE_SEZ,
  d.URI_SEZ,
  d.FLAG_ABILITAZIONE_SEZ,
  d.ID_COMP,
  d.DENOMINAZIONE_COMP,
  d.DESCRIZIONE_COMP,
  d.URI_COMP,
  d.DATA_CATALOGAZIONE_COMP,
  d.DATA_INVIO_MSG,
  d.FLAG_ABILITAZIONE_COMP,
  d.ID_FUNZ,
  d.DENOMINAZIONE_FUNZ,
  d.DESCRIZIONE_FUNZ,
  d.WICKET_LABEL_ID_STD,
  d.WICKET_LABEL_ID_ALT,
  d.CLASSE_PAGINA_STD,
  d.CLASSE_PAGINA_ALT,
  d.WICKET_TITLE_STD,
  d.WICKET_TITLE_ALT,
  d.FLAG_ABILITAZIONE_FUNZ,
  d.ID_FUNZ_SOSP,
  d.DATA_INIZIO_SOSP,
  d.DATA_FINE_SOSP,
  d.TIPO_SOSP,
  d.FLAG_ABILITAZIONE_FUNZ_SOSP,
  d.ID_WIDG,
  d.DENOMINAZIONE_WIDG,
  d.DESCRIZIONE_WIDG,
  d.URI_WIDG,
  d.DATA_CATALOGAZIONE_WIDG,
  d.FLAG_ABILITAZIONE_WIDG,
  d.FLAG_DEFAULT PRIORITA,
  NULL,
  u.ID_FCITT,
  NULL,
  NULL,
  u.PERSON_ID
FROM V_CFG_T_CAT_WIDG d, CFG_T_FCITT u
 where d.flag_abilitazione_sez = 1 
   and d.flag_abilitazione_comp = 1 
   and d.flag_abilitazione_funz = 1
   and d.flag_abilitazione_WIDG = 1 
   and d.FLAG_DEFAULT = 1
   and (d.ID_SEZ, d.ID_COMP, d.ID_WIDG, u.ID_FCITT) not in (select ID_SEZ, ID_COMP, ID_WIDG, ID_FCITT FROM V_CFG_R_FCITT_WIDG) 
order by  
  PERSON_ID,
  ORDINAMENTO_S,
  ORDINAMENTO_C,
  PRIORITA,
  ORDINAMENTO_W
;
--------------------------------------------------------
--  DDL for View V_SC_SCADENZE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_SC_SCADENZE" ("ID_SCADENZA", "ID_STATO", "STATO_SCADENZA", "ID_COMP", "ID_SEZ", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "ORDINAMENTO", "FLAG_ABILITAZIONE", "SCADENZA", "DATA_SCADENZA", "DATA_INVIO_MSG", "OP_NOME", "OP_COGNOME", "OP_CF", "OP_LOGIN", "ID_OPERATORE") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  s.ID_SCADENZA,
  s.ID_STATO,
  st.STATO_SCADENZA,
  s.ID_COMP,
  c.ID_SEZ,
  sz.DENOMINAZIONE_SEZ,
  sz.DESCRIZIONE_SEZ,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.ORDINAMENTO,
  c.FLAG_ABILITAZIONE,
  s.SCADENZA,
  s.DATA_SCADENZA,
  s.DATA_INVIO_MSG,
  o.OP_NOME,
  o.OP_COGNOME,
  o.OP_CF,
  o.OP_LOGIN,
  o.ID_OPERATORE
FROM SC_SCADENZE s 
     inner join SC_STATI_SCADENZE st on s.ID_STATO = st.ID_STATO
     inner join CFG_T_CAT_COMP c on s.ID_COMP = c.ID_COMP
     inner join CFG_T_CAT_SEZ sz on c.ID_SEZ = sz.ID_SEZ
     inner join SC_ABILITAZIONI a on c.ID_COMP = a.ID_COMP
     inner join SC_OPERATORI o on a.ID_OPERATORE = o.ID_OPERATORE
;
  GRANT SELECT ON "LDFDCADM"."V_SC_SCADENZE" TO "LDFDC";
--------------------------------------------------------
--  DDL for View V_SC_SCADENZE_UT
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "LDFDCADM"."V_SC_SCADENZE_UT" ("ID_SCADENZA", "ID_STATO", "STATO_SCADENZA", "ID_COMP", "ID_SEZ", "DENOMINAZIONE_SEZ", "DESCRIZIONE_SEZ", "URI_SEZ", "DENOMINAZIONE_COMP", "DESCRIZIONE_COMP", "URI_COMP", "DATA_CATALOGAZIONE_COMP", "ORDINAMENTO", "FLAG_ABILITAZIONE", "SCADENZA", "DATA_SCADENZA", "DATA_INVIO_MSG", "ID_FCITT", "PERSON_ID", "ID_T_EVENTO", "OGGETTO") DEFAULT COLLATION "USING_NLS_COMP"  AS 
  SELECT 
  s.ID_SCADENZA,
  s.ID_STATO,
  st.STATO_SCADENZA,
  s.ID_COMP,
  c.ID_SEZ,
  sz.DENOMINAZIONE_SEZ,
  sz.DESCRIZIONE_SEZ,
  sz.URI_SEZ,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.ORDINAMENTO,
  c.FLAG_ABILITAZIONE,
  s.SCADENZA,
  s.DATA_SCADENZA,
  s.DATA_INVIO_MSG,
  NULL ID_FCITT,
  NULL PERSON_ID,
  NULL ID_T_EVENTO,
  NULL OGGETTO
FROM SC_SCADENZE s 
     inner join SC_STATI_SCADENZE st on s.ID_STATO = st.ID_STATO
     inner join CFG_T_CAT_COMP c on s.ID_COMP = c.ID_COMP
     inner join CFG_T_CAT_SEZ sz on c.ID_SEZ = sz.ID_SEZ
where s.id_stato = 2
  and c.flag_abilitazione = 1
UNION ALL
  SELECT 
  s.ID_SCADENZA,
  s.ID_STATO,
  st.STATO_SCADENZA,
  e.ID_COMP,
  c.ID_SEZ,
  sz.DENOMINAZIONE_SEZ,
  sz.DESCRIZIONE_SEZ,
  sz.URI_SEZ,
  c.DENOMINAZIONE_COMP,
  c.DESCRIZIONE_COMP,
  c.URI_COMP,
  c.DATA_CATALOGAZIONE_COMP,
  c.ORDINAMENTO,
  c.FLAG_ABILITAZIONE,
  s.SCADENZA,
  s.DATA_SCADENZA,
  s.DATA_INVIO_MSG,
  s.ID_FCITT,
  u.PERSON_ID,
  s.ID_T_EVENTO,
  s.OGGETTO
FROM CFG_R_FCITT_SCADENZE_PERSONALI s 
     inner join SC_STATI_SCADENZE st on s.ID_STATO = st.ID_STATO
     inner join CFG_T_EVENTI e on e.ID_T_EVENTO = s.ID_T_EVENTO
     inner join CFG_T_CAT_COMP c on e.ID_COMP = c.ID_COMP
     inner join CFG_T_CAT_SEZ sz on c.ID_SEZ = sz.ID_SEZ
     inner join CFG_T_FCITT u on s.ID_FCITT = u.ID_FCITT
where s.id_stato = 2
  and c.flag_abilitazione = 1
;
  GRANT SELECT ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT DELETE ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT INSERT ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT UPDATE ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT REFERENCES ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT READ ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT ON COMMIT REFRESH ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT QUERY REWRITE ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT DEBUG ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT FLASHBACK ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
  GRANT MERGE VIEW ON "LDFDCADM"."V_SC_SCADENZE_UT" TO "LDFDC";
