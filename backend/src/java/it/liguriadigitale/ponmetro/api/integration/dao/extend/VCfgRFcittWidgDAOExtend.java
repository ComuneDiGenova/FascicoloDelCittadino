package it.liguriadigitale.ponmetro.api.integration.dao.extend;

import it.liguriadigitale.ponmetro.api.integration.dao.VCfgRFcittWidgDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittWidg;

public class VCfgRFcittWidgDAOExtend extends VCfgRFcittWidgDAO {

  public VCfgRFcittWidgDAOExtend(VCfgRFcittWidg vcfgrfcittwidg) {
    super(vcfgrfcittwidg);
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        " WITH W_SELEZIONATI_ABILITATI AS ( "
            + "   SELECT DISTINCT "
            + " CITT_W.ID_SEZ, "
            + " CITT_W.ORDINAMENTO_S, "
            + " CITT_W.ORDINAMENTO_C, "
            + " CITT_W.ORDINAMENTO_W, "
            + " CITT_W.DENOMINAZIONE_SEZ, "
            + " CITT_W.DESCRIZIONE_SEZ, "
            + " CITT_W.DATA_CATALOGAZIONE_SEZ, "
            + " CITT_W.URI_SEZ, "
            + " CITT_W.FLAG_ABILITAZIONE_SEZ, "
            + " CITT_W.ID_COMP, "
            + " CITT_W.DENOMINAZIONE_COMP, "
            + " CITT_W.DESCRIZIONE_COMP, "
            + " CITT_W.URI_COMP, "
            + " CITT_W.DATA_CATALOGAZIONE_COMP, "
            + " CITT_W.DATA_INVIO_MSG, "
            + " CITT_W.FLAG_ABILITAZIONE_COMP, "
            + " CITT_W.ID_FUNZ, "
            + " CITT_W.DENOMINAZIONE_FUNZ, "
            + " CITT_W.DESCRIZIONE_FUNZ, "
            + " CITT_W.WICKET_LABEL_ID_STD, "
            + " CITT_W.WICKET_LABEL_ID_ALT, "
            + " CITT_W.CLASSE_PAGINA_STD, "
            + " CITT_W.CLASSE_PAGINA_ALT, "
            + " CITT_W.WICKET_TITLE_STD, "
            + " CITT_W.WICKET_TITLE_ALT, "
            + " CITT_W.FLAG_ABILITAZIONE_FUNZ, "
            + " CITT_W.ID_FUNZ_SOSP, "
            + " CITT_W.DATA_INIZIO_SOSP, "
            + " CITT_W.DATA_FINE_SOSP, "
            + " CITT_W.TIPO_SOSP, "
            + " CITT_W.FLAG_ABILITAZIONE_FUNZ_SOSP, "
            + " CITT_W.ID_WIDG, "
            + " CITT_W.DENOMINAZIONE_WIDG, "
            + " CITT_W.DESCRIZIONE_WIDG, "
            + " CITT_W.URI_WIDG, "
            + " CITT_W.DATA_CATALOGAZIONE_WIDG, "
            + " CITT_W.FLAG_DEFAULT, "
            + " CITT_W.FLAG_ABILITAZIONE_WIDG, "
            + " CITT_W.ID_CFG_R_FCITT_WIDG, "
            + " CITT_W.ID_FCITT, "
            + " CITT_W.DATA_ASSOCIAZIONE_FCITT_WIDG, "
            + " CITT_W.DATA_DEASSOCIAZIONE_FCITT_WIDG, "
            + " CITT_W.FLAG_ABILITAZIONE_FCITT_WIDG, "
            + " CITT_W.PERSON_ID, "
            + " CITT_W.FLAG_RESIDENTE, "
            + " CITT_W.FLAG_NON_RESIDENTE "
            + "   FROM V_CFG_R_FCITT_WIDG CITT_W "
            + "   WHERE 1 = 1 ";
    if (vcfgrfcittwidg.getIdFcitt() != null) {
      sql += " AND CITT_W.ID_FCITT  = ? ";
    }
    if (vcfgrfcittwidg.getFlagAbilitazioneFcittWidg()) {
      sql += " AND CITT_W.FLAG_ABILITAZIONE_FCITT_WIDG = 1 ";
    }
    sql +=
        "     AND SYSDATE > CITT_W.DATA_ASSOCIAZIONE_FCITT_WIDG "
            + "     AND (CITT_W.DATA_DEASSOCIAZIONE_FCITT_WIDG IS NULL OR SYSDATE < CITT_W.DATA_DEASSOCIAZIONE_FCITT_WIDG) "
            + " ), "
            + " W_DEFAULT_OR_OTHERS AS ( "
            + "   SELECT DISTINCT "
            + " CAT_W.ID_SEZ, "
            + " CAT_W.ORDINAMENTO_S, "
            + " CAT_W.ORDINAMENTO_C, "
            + " CAT_W.ORDINAMENTO_W, "
            + " CAT_W.DENOMINAZIONE_SEZ, "
            + " CAT_W.DESCRIZIONE_SEZ, "
            + " CAT_W.DATA_CATALOGAZIONE_SEZ, "
            + " CAT_W.URI_SEZ, "
            + " CAT_W.FLAG_ABILITAZIONE_SEZ, "
            + " CAT_W.ID_COMP, "
            + " CAT_W.DENOMINAZIONE_COMP, "
            + " CAT_W.DESCRIZIONE_COMP, "
            + " CAT_W.URI_COMP, "
            + " CAT_W.DATA_CATALOGAZIONE_COMP, "
            + " CAT_W.DATA_INVIO_MSG, "
            + " CAT_W.FLAG_ABILITAZIONE_COMP, "
            + " CAT_W.ID_FUNZ, "
            + " CAT_W.DENOMINAZIONE_FUNZ, "
            + " CAT_W.DESCRIZIONE_FUNZ, "
            + " CAT_W.WICKET_LABEL_ID_STD, "
            + " CAT_W.WICKET_LABEL_ID_ALT, "
            + " CAT_W.CLASSE_PAGINA_STD, "
            + " CAT_W.CLASSE_PAGINA_ALT, "
            + " CAT_W.WICKET_TITLE_STD, "
            + " CAT_W.WICKET_TITLE_ALT, "
            + " CAT_W.FLAG_ABILITAZIONE_FUNZ, "
            + " CAT_W.ID_FUNZ_SOSP, "
            + " CAT_W.DATA_INIZIO_SOSP, "
            + " CAT_W.DATA_FINE_SOSP, "
            + " CAT_W.TIPO_SOSP, "
            + " CAT_W.FLAG_ABILITAZIONE_FUNZ_SOSP, "
            + " CAT_W.ID_WIDG, "
            + " CAT_W.DENOMINAZIONE_WIDG, "
            + " CAT_W.DESCRIZIONE_WIDG, "
            + " CAT_W.URI_WIDG, "
            + " CAT_W.DATA_CATALOGAZIONE_WIDG, "
            + " CAT_W.FLAG_DEFAULT, "
            + " CAT_W.FLAG_ABILITAZIONE_WIDG, "
            + "     NULL AS ID_CFG_R_FCITT_WIDG, "
            + "     NULL AS ID_FCITT, "
            + "     NULL AS DATA_ASSOCIAZIONE_FCITT_WIDG, "
            + "     NULL AS DATA_DEASSOCIAZIONE_FCITT_WIDG, "
            + "     NULL AS FLAG_ABILITAZIONE_FCITT_WIDG, "
            + "     NULL AS PERSON_ID, "
            + " CAT_W.FLAG_RESIDENTE, "
            + " CAT_W.FLAG_NON_RESIDENTE "
            + "   FROM V_CFG_T_CAT_WIDG CAT_W  "
            + "   WHERE 1 = 1 " // AND FLAG_DEFAULT = 1 "
            + " ), "
            + " W_DA_VEDERE AS ( "
            + "     SELECT DISTINCT W_DEFAULT_OR_OTHERS.* "
            + "     FROM W_DEFAULT_OR_OTHERS "
            + "     WHERE W_DEFAULT_OR_OTHERS.ID_WIDG NOT IN ( "
            + "         SELECT W_SELEZIONATI_ABILITATI.ID_WIDG FROM W_SELEZIONATI_ABILITATI "
            + "       ) "
            + "   UNION "
            + "     SELECT W_SELEZIONATI_ABILITATI.* "
            + "     FROM W_SELEZIONATI_ABILITATI "
            + " ) "
            + "  "
            + " SELECT DISTINCT W_DA_VEDERE.*  "
            + " FROM W_DA_VEDERE "
            + " WHERE 1 = 1 ";
    if (vcfgrfcittwidg.getFlagAbilitazioneFcittWidg()) {
      sql +=
          "     AND W_DA_VEDERE.FLAG_ABILITAZIONE_SEZ = 1 "
              + "     AND W_DA_VEDERE.FLAG_ABILITAZIONE_COMP = 1 "
              + "     AND W_DA_VEDERE.FLAG_ABILITAZIONE_FUNZ = 1 "
              + "     AND W_DA_VEDERE.FLAG_ABILITAZIONE_WIDG = 1 "
              + "     AND (  "
              + "       W_DA_VEDERE.ID_FUNZ_SOSP IS NULL "
              + "       OR (  "
              + "           ( W_DA_VEDERE.DATA_INIZIO_SOSP IS NOT NULL AND SYSDATE < W_DA_VEDERE.DATA_INIZIO_SOSP ) "
              + "           AND  "
              + "           ( W_DA_VEDERE.DATA_FINE_SOSP IS NULL OR SYSDATE > W_DA_VEDERE.DATA_FINE_SOSP ) "
              + "          ) "
              + "       ) ";
    }
    if (vcfgrfcittwidg.getIdSez() != null) {
      sql += " AND W_DA_VEDERE.ID_SEZ  = ? ";
    }
    sql +=
        " ORDER BY W_DA_VEDERE.ID_SEZ ASC, W_DA_VEDERE.ID_CFG_R_FCITT_WIDG ASC,"
            + "W_DA_VEDERE.FLAG_DEFAULT DESC, W_DA_VEDERE.ORDINAMENTO_W ASC ";
    // Eventualmente volessimo avere le prime righe.. solo se la sezione e'
    // selezionata + " FETCH FIRST " + iNumberOfRows + " ROWS ONLY";

    return sql;
  }
}
