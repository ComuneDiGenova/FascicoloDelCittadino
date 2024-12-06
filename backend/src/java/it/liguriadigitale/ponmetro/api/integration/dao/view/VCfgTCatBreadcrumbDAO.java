package it.liguriadigitale.ponmetro.api.integration.dao.view;

/**
 * VCfgTCatBreadcrumb
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-11-24T13:57:37.587
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.breadcrumb.VCfgTCatBreadcrumb;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCfgTCatBreadcrumbDAO extends AbstractSearchDAO {

  private VCfgTCatBreadcrumb vcfgtcatbreadcrumb;

  public VCfgTCatBreadcrumbDAO(VCfgTCatBreadcrumb vcfgtcatbreadcrumb) {
    super();
    this.vcfgtcatbreadcrumb = vcfgtcatbreadcrumb;
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CFG_T_CAT_BREADCRUMB";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCfgTCatBreadcrumb obj = new VCfgTCatBreadcrumb();

    obj.setFigliofunz(r.getLong("FIGLIOFUNZ"));
    if (r.wasNull()) {
      obj.setFigliofunz(null);
    }

    obj.setFunz(r.getBigDecimal("FUNZ"));
    obj.setDescrfunz(r.getString("DESCRFUNZ"));
    obj.setIdpagina(r.getString("IDPAGINA"));
    obj.setIsfiglio(r.getBigDecimal("ISFIGLIO"));
    obj.setIssezione(r.getBigDecimal("ISSEZIONE"));
    obj.setIsdelegabile(r.getBigDecimal("ISDELEGABILE"));
    obj.setIdSez(r.getBigDecimal("ID_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setUriSez(r.getString("URI_SEZ"));
    return obj;
  }
}
