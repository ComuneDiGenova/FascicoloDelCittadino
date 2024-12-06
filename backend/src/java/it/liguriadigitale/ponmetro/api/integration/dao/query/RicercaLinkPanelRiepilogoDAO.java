package it.liguriadigitale.ponmetro.api.integration.dao.query;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RicercaLinkPanelRiepilogoDAO extends AbstractSearchDAO {

  private VCfgTCatFunz vcfgtcatfunz;

  public RicercaLinkPanelRiepilogoDAO(VCfgTCatFunz vcfgtcatfunz) {
    this.vcfgtcatfunz = vcfgtcatfunz;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql =
        "select * from CFG_T_CAT_FUNZ_LINK t "
            + "inner join V_CFG_T_CAT_FUNZ f on f.id_funz=t.id_funz ";
    if (vcfgtcatfunz.getIdSez() != null) {
      sql += "where f.ID_SEZ = ?";
    }
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, vcfgtcatfunz.getIdSez());
  }

  @Override
  protected Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatFunzLink obj = new CfgTCatFunzLink();

    obj.setIdLink(r.getBigDecimal("ID_LINK"));
    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setTipoLink(r.getString("TIPO_LINK"));
    obj.setDescrizioniTooltip(r.getString("DESCRIZIONI_TOOLTIP"));
    obj.setUrl(r.getString("URL"));
    obj.setIconaCss(r.getString("ICONA_CSS"));
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    obj.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    obj.setFlagNonresidente(r.getBoolean("FLAG_NONRESIDENTE"));
    obj.setCodiceMaggioli(r.getBigDecimal("CODICE_MAGGIOLI"));
    return obj;
  }
}
