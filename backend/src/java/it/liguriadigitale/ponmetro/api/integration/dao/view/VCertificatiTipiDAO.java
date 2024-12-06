package it.liguriadigitale.ponmetro.api.integration.dao.view;

/**
 * VCertificatiTipi
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2021-09-06T15:51:39.657
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.certificati.VCertificatiTipi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VCertificatiTipiDAO extends AbstractSearchDAO {

  private VCertificatiTipi vcertificatitipi;

  public VCertificatiTipiDAO(VCertificatiTipi vcertificatitipi) {
    super();
    this.vcertificatitipi = vcertificatitipi;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from V_CERTIFICATI_TIPI";
    sql = sql + " WHERE ID_STATO_REC_TIPO=0";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    VCertificatiTipi obj = new VCertificatiTipi();

    obj.setIdTCertificato(r.getLong("ID_T_CERTIFICATO"));

    obj.setCodiceAnpr(r.getString("CODICE_ANPR"));
    obj.setTipo(r.getString("TIPO"));
    obj.setEvento(r.getString("EVENTO"));
    obj.setCertificato(r.getString("CERTIFICATO"));
    obj.setInvio(r.getString("INVIO"));
    obj.setAnnoDa(r.getLong("ANNO_DA"));

    obj.setAnnoA(r.getLong("ANNO_A"));

    obj.setMarcaDaBollo(r.getString("MARCA_DA_BOLLO"));
    obj.setRimborsoSpese(r.getString("RIMBORSO_SPESE"));
    obj.setDirittiDiSegreteria(r.getString("DIRITTI_DI_SEGRETERIA"));
    obj.setRestrizioni(r.getString("RESTRIZIONI"));
    obj.setInfoDaChiedere(r.getString("INFO_DA_CHIEDERE"));
    obj.setIdStatoRecTipo(r.getLong("ID_STATO_REC_TIPO"));

    obj.setIdStatoRecInvio(r.getLong("ID_STATO_REC_INVIO"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }
}
