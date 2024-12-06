package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * VPlTIstanzeDocumenti
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-08-10T12:52:52.267
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeDocumenti;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VPlTIstanzeDocumentiDAO extends AbstractSearchDAO {

  private VPlTIstanzeDocumenti vpltistanzedocumenti;

  public VPlTIstanzeDocumentiDAO(VPlTIstanzeDocumenti vpltistanzedocumenti) {
    super();
    this.vpltistanzedocumenti = vpltistanzedocumenti;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    log.error("VPlTIstanzeDocumentiDAO getSqlRetrieveObjectByWhere: ");
    String sql = "SELECT * ";

    sql += " FROM LDFDCADM.V_PL_T_ISTANZE_DOCUMENTI " + " WHERE 1=1 ";
    sql += getCommonFilter();
    log.error("Query generata: " + sql);
    return sql;
  }

  private String getCommonFilter() {
    String sql = "";

    if (vpltistanzedocumenti.getCodiceHermes() != null) {
      sql += " AND CODICE_HERMES = ? ";
    }
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;

    if (vpltistanzedocumenti.getCodiceHermes() != null) {
      st.setString(index++, vpltistanzedocumenti.getCodiceHermes());
    }
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VPlTIstanzeDocumenti obj = new VPlTIstanzeDocumenti();

    obj.setCodiceHermes(r.getString("CODICE_HERMES"));
    obj.setTipologia(r.getString("TIPOLOGIA"));
    obj.setRiferimentoLegge(r.getString("RIFERIMENTO_LEGGE"));
    obj.setMinDoc(r.getBigDecimal("MIN_DOC"));
    obj.setMaxDoc(r.getBigDecimal("MAX_DOC"));
    obj.setCodice(r.getBigDecimal("CODICE"));
    obj.setDocumento(r.getString("DOCUMENTO"));
    obj.setFlgAutodichiarazione(r.getString("FLG_AUTODICHIARAZIONE"));
    obj.setIdDocAlternativo(r.getBigDecimal("ID_DOC_ALTERNATIVO"));
    obj.setDocumentoAlternativo(r.getString("DOCUMENTO_ALTERNATIVO"));
    obj.setObbligatorio(r.getString("OBBLIGATORIO"));
    return obj;
  }
}
