package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * PrPrivacyPresaVisione
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacyPresaVisione;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrPrivacyPresaVisioneDAO extends AbstractTableDAO {

  private PrPrivacyPresaVisione prprivacypresavisione;

  public PrPrivacyPresaVisioneDAO(PrPrivacyPresaVisione prprivacypresavisione) {
    super();
    this.prprivacypresavisione = prprivacypresavisione;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from PR_PRIVACY_PRESA_VISIONE where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from PR_PRIVACY_PRESA_VISIONE where 1=1 ";
    if (prprivacypresavisione.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    if (prprivacypresavisione.getIdAnagrafica() != null) sql += " and ID_ANAGRAFICA  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacypresavisione.getIdPrivacyVer() != null)
      st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
    if (prprivacypresavisione.getIdAnagrafica() != null)
      st.setLong(index++, prprivacypresavisione.getIdAnagrafica());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    PrPrivacyPresaVisione obj = new PrPrivacyPresaVisione();

    obj.setIdPrivacyVer(r.getLong("ID_PRIVACY_VER"));

    obj.setIdAnagrafica(r.getLong("ID_ANAGRAFICA"));

    obj.setIdServizio(r.getLong("ID_SERVIZIO"));

    obj.setDataPresaVisione(r.getTimestamp("DATA_PRESA_VISIONE").toLocalDateTime());
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from PR_PRIVACY_PRESA_VISIONE where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from PR_PRIVACY_PRESA_VISIONE where 1=1 ";
    if (prprivacypresavisione.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacypresavisione.getIdPrivacyVer() != null)
      st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (prprivacypresavisione.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (prprivacypresavisione.getIdPrivacyVer() != null)
      st.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prprivacypresavisione.getIdPrivacyVer() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdPrivacyVer());
    }
    if (prprivacypresavisione.getIdAnagrafica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdAnagrafica());
    }
    if (prprivacypresavisione.getIdServizio() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdServizio());
    }
    if (prprivacypresavisione.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataPresaVisione()));
    }
    if (prprivacypresavisione.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdStatoRec());
    }
    pst.setString(index++, prprivacypresavisione.getUtenteIns());
    if (prprivacypresavisione.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataIns()));
    }
    pst.setString(index++, prprivacypresavisione.getUtenteAgg());
    if (prprivacypresavisione.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE PR_PRIVACY_PRESA_VISIONE set ID_PRIVACY_VER= ?  , ID_ANAGRAFICA= ?  , ID_SERVIZIO= ?  , DATA_PRESA_VISIONE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO PR_PRIVACY_PRESA_VISIONE ( ID_PRIVACY_VER,ID_ANAGRAFICA,ID_SERVIZIO,DATA_PRESA_VISIONE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO PR_PRIVACY_PRESA_VISIONE ( ID_ANAGRAFICA,ID_SERVIZIO,DATA_PRESA_VISIONE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prprivacypresavisione.getIdAnagrafica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdAnagrafica());
    }
    if (prprivacypresavisione.getIdServizio() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdServizio());
    }
    if (prprivacypresavisione.getDataPresaVisione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataPresaVisione()));
    }
    if (prprivacypresavisione.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacypresavisione.getIdStatoRec());
    }
    pst.setString(index++, prprivacypresavisione.getUtenteIns());
    if (prprivacypresavisione.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataIns()));
    }
    pst.setString(index++, prprivacypresavisione.getUtenteAgg());
    if (prprivacypresavisione.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacypresavisione.getDataAgg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_PRIVACY_VER"};
  }
}
