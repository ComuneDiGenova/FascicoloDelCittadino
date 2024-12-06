package it.liguriadigitale.ponmetro.api.integration.dao.extend;

import it.liguriadigitale.ponmetro.api.integration.dao.VCfgRFcittCompDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VCfgRFcittCompDAOExtend extends VCfgRFcittCompDAO {

  public VCfgRFcittCompDAOExtend(VCfgRFcittComp vcfgrfcittcomp) {
    super(vcfgrfcittcomp);
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (vcfgrfcittcomp.getIdFcitt() != null) st.setLong(index++, vcfgrfcittcomp.getIdFcitt());
    if (vcfgrfcittcomp.getIdSez() != null) st.setLong(index++, vcfgrfcittcomp.getIdSez());
    if (vcfgrfcittcomp.getIdFcitt() != null) st.setLong(index++, vcfgrfcittcomp.getIdFcitt());
    if (vcfgrfcittcomp.getIdSez() != null) st.setLong(index++, vcfgrfcittcomp.getIdSez());
    if (vcfgrfcittcomp.getIdSez() != null) st.setLong(index++, vcfgrfcittcomp.getIdSez());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        " WITH C_CATALOGO_ABILITATI AS (  "
            + " SELECT DISTINCT "
            + "  CAT_C.ID_SEZ, "
            + "  CAT_C.ORDINAMENTO_S, "
            + "  CAT_C.ORDINAMENTO_C, "
            + "  CAT_C.DENOMINAZIONE_SEZ, "
            + "  CAT_C.DESCRIZIONE_SEZ, "
            + "  CAT_C.DATA_CATALOGAZIONE_SEZ, "
            + "  CAT_C.URI_SEZ, "
            + "  CAT_C.FLAG_ABILITAZIONE_SEZ, "
            + "  CAT_C.ID_COMP, "
            + "  CAT_C.DENOMINAZIONE_COMP, "
            + "  CAT_C.DESCRIZIONE_COMP, "
            + "  CAT_C.URI_COMP, "
            + "  CAT_C.DATA_CATALOGAZIONE_COMP, "
            + "  CAT_C.DATA_INVIO_MSG, "
            + "  CAT_C.FLAG_ABILITAZIONE_COMP,     "
            + "  CITT_C.ID_CFG_R_FCITT_COMP,  "
            + "  CITT_C.ID_FCITT,     "
            + "  CITT_C.DATA_REGISTRAZ_FCITT_COMP,   "
            + "  CITT_C.DATA_DEREGISTRAZ_FCITT_COMP,    "
            + "  CITT_C.FLAG_ABILITAZIONE_CITT_COMP,    "
            + "  CITT_C.PERSON_ID     "
            + " FROM V_CFG_T_CAT_COMP CAT_C INNER JOIN V_CFG_R_FCITT_COMP CITT_C    "
            + " ON CAT_C.ID_COMP = CITT_C.ID_COMP  "
            + " WHERE 1 = 1 ";
    if (vcfgrfcittcomp.getIdFcitt() != null) {
      sql += "     AND CITT_C.ID_FCITT  = ? ";
    }
    if (vcfgrfcittcomp.getIdSez() != null) {
      sql += "     AND CITT_C.ID_SEZ  = ? ";
    }
    sql += // " AND CITT_C.FLAG_ABILITAZIONE_CITT_COMP = 1 "
        // + " AND CAT_C.FLAG_ABILITAZIONE_SEZ = 1 "
        // + " AND CAT_C.FLAG_ABILITAZIONE_COMP = 1 "
        "  AND SYSDATE > CITT_C.DATA_REGISTRAZ_FCITT_COMP    "
            + "  AND "
            + "  ( "
            + "    CITT_C.DATA_DEREGISTRAZ_FCITT_COMP IS NULL "
            + "      OR "
            + "    SYSDATE < CITT_C.DATA_DEREGISTRAZ_FCITT_COMP "
            + "  ) "
            + " ), "
            + " C_CATALOGO_DISABILITATI AS (  "
            + " SELECT DISTINCT "
            + "  CAT_C.ID_SEZ, "
            + "  CAT_C.ORDINAMENTO_S, "
            + "  CAT_C.ORDINAMENTO_C, "
            + "  CAT_C.DENOMINAZIONE_SEZ, "
            + "  CAT_C.DESCRIZIONE_SEZ, "
            + "  CAT_C.DATA_CATALOGAZIONE_SEZ, "
            + "  CAT_C.URI_SEZ, "
            + "  CAT_C.FLAG_ABILITAZIONE_SEZ, "
            + "  CAT_C.ID_COMP, "
            + "  CAT_C.DENOMINAZIONE_COMP, "
            + "  CAT_C.DESCRIZIONE_COMP, "
            + "  CAT_C.URI_COMP, "
            + "  CAT_C.DATA_CATALOGAZIONE_COMP, "
            + "  CAT_C.DATA_INVIO_MSG, "
            + "  CAT_C.FLAG_ABILITAZIONE_COMP,     "
            + "  CITT_C.ID_CFG_R_FCITT_COMP,  "
            + "  CITT_C.ID_FCITT,     "
            + "  CITT_C.DATA_REGISTRAZ_FCITT_COMP,   "
            + "  CITT_C.DATA_DEREGISTRAZ_FCITT_COMP,    "
            + "  CITT_C.FLAG_ABILITAZIONE_CITT_COMP,    "
            + "  CITT_C.PERSON_ID     "
            + " FROM V_CFG_T_CAT_COMP CAT_C INNER JOIN V_CFG_R_FCITT_COMP CITT_C    "
            + " ON CAT_C.ID_COMP = CITT_C.ID_COMP  "
            + " WHERE 1 = 1 ";
    if (vcfgrfcittcomp.getIdFcitt() != null) {
      sql += "     AND CITT_C.ID_FCITT  = ? ";
    }
    if (vcfgrfcittcomp.getIdSez() != null) {
      sql += "     AND CITT_C.ID_SEZ  = ? ";
    }
    sql += // " AND CITT_C.FLAG_ABILITAZIONE_CITT_COMP = 1 "
        // + " AND CAT_C.FLAG_ABILITAZIONE_SEZ = 1 "
        // + " AND CAT_C.FLAG_ABILITAZIONE_COMP = 1 "
        "  AND SYSDATE >= CITT_C.DATA_DEREGISTRAZ_FCITT_COMP "
            + "  AND CAT_C.ID_COMP NOT IN ( "
            + "    SELECT C_CATALOGO_ABILITATI.ID_COMP "
            + "    FROM C_CATALOGO_ABILITATI ) "
            + " order by DATA_DEREGISTRAZ_FCITT_COMP "
            + " ), "
            + " UNIONE_SOLO_ID AS ( "
            + " SELECT DISTINCT C_CATALOGO_ABILITATI.ID_COMP AS ID_COMP FROM C_CATALOGO_ABILITATI "
            + " UNION ALL  "
            + " SELECT DISTINCT C_CATALOGO_DISABILITATI.ID_COMP AS ID_COMP FROM C_CATALOGO_DISABILITATI), "
            + " ALTRI AS ( "
            + " SELECT DISTINCT "
            + "  CAT_C.ID_SEZ, "
            + "  CAT_C.ORDINAMENTO_S, "
            + "  CAT_C.ORDINAMENTO_C, "
            + "  CAT_C.DENOMINAZIONE_SEZ, "
            + "  CAT_C.DESCRIZIONE_SEZ, "
            + "  CAT_C.DATA_CATALOGAZIONE_SEZ, "
            + "  CAT_C.URI_SEZ, "
            + "  CAT_C.FLAG_ABILITAZIONE_SEZ, "
            + "  CAT_C.ID_COMP, "
            + "  CAT_C.DENOMINAZIONE_COMP, "
            + "  CAT_C.DESCRIZIONE_COMP, "
            + "  CAT_C.URI_COMP, "
            + "  CAT_C.DATA_CATALOGAZIONE_COMP, "
            + "  CAT_C.DATA_INVIO_MSG, "
            + "  CAT_C.FLAG_ABILITAZIONE_COMP,     "
            + "  NULL AS ID_CFG_R_FCITT_COMP,  "
            + "  NULL AS ID_FCITT,     "
            + "  NULL AS DATA_REGISTRAZ_FCITT_COMP,   "
            + "  NULL AS DATA_DEREGISTRAZ_FCITT_COMP,    "
            + "  NULL AS FLAG_ABILITAZIONE_CITT_COMP,    "
            + "  NULL AS PERSON_ID     "
            + " FROM V_CFG_T_CAT_COMP CAT_C "
            + " WHERE 1 = 1 ";
    if (vcfgrfcittcomp.getIdSez() != null) {
      sql += "     AND CITT_C.ID_SEZ  = ? ";
    }
    sql +=
        " AND CAT_C.ID_COMP NOT IN (SELECT ID_COMP FROM UNIONE_SOLO_ID) ), "
            + " UNIONE AS( "
            + " SELECT DISTINCT C_CATALOGO_ABILITATI.* FROM C_CATALOGO_ABILITATI "
            + " UNION ALL  "
            + " SELECT DISTINCT C_CATALOGO_DISABILITATI.* FROM C_CATALOGO_DISABILITATI "
            + " UNION ALL  "
            + " SELECT DISTINCT ALTRI.* FROM ALTRI "
            + " ) "
            + " "
            + " SELECT DISTINCT UNIONE.*    "
            + " FROM UNIONE   "
            + " WHERE 1 = 1 "
            + " ORDER BY UNIONE.ID_SEZ, UNIONE.ID_COMP ";

    return sql;
  }
}
