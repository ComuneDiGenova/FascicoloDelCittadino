package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * PrPrivacy
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrPrivacyDAO extends AbstractTableDAO {

  private PrPrivacy prprivacy;

  public PrPrivacyDAO(PrPrivacy prprivacy) {
    super();
    this.prprivacy = prprivacy;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from PR_PRIVACY where ID_PRIVACY=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacy.getIdPrivacy());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from PR_PRIVACY where 1=1 ";
    if (prprivacy.getIdPrivacy() != null) sql += " and ID_PRIVACY  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacy.getIdPrivacy() != null) st.setLong(index++, prprivacy.getIdPrivacy());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    PrPrivacy obj = new PrPrivacy();

    obj.setIdPrivacy(r.getLong("ID_PRIVACY"));

    obj.setNomePrivacy(r.getString("NOME_PRIVACY"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from PR_PRIVACY where ID_PRIVACY=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacy.getIdPrivacy());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from PR_PRIVACY where 1=1 ";
    if (prprivacy.getIdPrivacy() != null) sql += " and ID_PRIVACY  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacy.getIdPrivacy() != null) st.setLong(index++, prprivacy.getIdPrivacy());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_PRIVACY=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, prprivacy.getIdPrivacy());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (prprivacy.getIdPrivacy() != null) sql += " and ID_PRIVACY  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (prprivacy.getIdPrivacy() != null) st.setLong(index++, prprivacy.getIdPrivacy());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prprivacy.getIdPrivacy() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacy.getIdPrivacy());
    }
    pst.setString(index++, prprivacy.getNomePrivacy());
    if (prprivacy.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacy.getIdStatoRec());
    }
    pst.setString(index++, prprivacy.getUtenteIns());
    if (prprivacy.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacy.getDataIns()));
    }
    pst.setString(index++, prprivacy.getUtenteAgg());
    if (prprivacy.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacy.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE PR_PRIVACY set ID_PRIVACY= ?  , NOME_PRIVACY= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO PR_PRIVACY ( ID_PRIVACY,NOME_PRIVACY,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO PR_PRIVACY ( NOME_PRIVACY,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, prprivacy.getNomePrivacy());
    if (prprivacy.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacy.getIdStatoRec());
    }
    pst.setString(index++, prprivacy.getUtenteIns());
    if (prprivacy.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacy.getDataIns()));
    }
    pst.setString(index++, prprivacy.getUtenteAgg());
    if (prprivacy.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacy.getDataAgg()));
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_PRIVACY"};
  }
}
