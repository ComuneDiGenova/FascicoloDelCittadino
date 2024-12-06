package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * PrPrivacyVer
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrPrivacyVer;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PrPrivacyVerDAO extends AbstractTableDAO {

  private PrPrivacyVer prprivacyver;

  public PrPrivacyVerDAO(PrPrivacyVer prprivacyver) {
    super();
    this.prprivacyver = prprivacyver;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from PR_PRIVACY_VER where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from PR_PRIVACY_VER where 1=1 ";
    if (prprivacyver.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacyver.getIdPrivacyVer() != null) st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    PrPrivacyVer obj = new PrPrivacyVer();

    obj.setIdPrivacyVer(r.getLong("ID_PRIVACY_VER"));

    obj.setIdPrivacy(r.getLong("ID_PRIVACY"));
    if (r.getTimestamp("DATA_CREAZIONE") != null)
      obj.setDataCreazione(r.getTimestamp("DATA_CREAZIONE").toLocalDateTime());
    if (r.getTimestamp("DATA_PUBBLICAZIONE") != null)
      obj.setDataPubblicazione(r.getTimestamp("DATA_PUBBLICAZIONE").toLocalDateTime());
    if (r.getTimestamp("DATA_FINE") != null)
      obj.setDataFine(r.getTimestamp("DATA_FINE").toLocalDateTime());
    obj.setNomeFile(r.getString("NOME_FILE"));
    Blob tmpPDF_FILE = r.getBlob("PDF_FILE");
    if (tmpPDF_FILE != null) {
      obj.setPdfFile(new javax.sql.rowset.serial.SerialBlob(tmpPDF_FILE));
    } else {
      obj.setPdfFile(null);
    }

    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    if (r.getTimestamp("DATA_INS") != null)
      obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    if (r.getTimestamp("DATA_AGG") != null)
      obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from PR_PRIVACY_VER where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from PR_PRIVACY_VER where 1=1 ";
    if (prprivacyver.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prprivacyver.getIdPrivacyVer() != null) st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_PRIVACY_VER=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (prprivacyver.getIdPrivacyVer() != null) sql += " and ID_PRIVACY_VER  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (prprivacyver.getIdPrivacyVer() != null) st.setLong(index++, prprivacyver.getIdPrivacyVer());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prprivacyver.getIdPrivacyVer() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacyver.getIdPrivacyVer());
    }
    if (prprivacyver.getIdPrivacy() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacyver.getIdPrivacy());
    }
    if (prprivacyver.getDataCreazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataCreazione()));
    }
    if (prprivacyver.getDataPubblicazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataPubblicazione()));
    }
    if (prprivacyver.getDataFine() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataFine()));
    }
    pst.setString(index++, prprivacyver.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (prprivacyver.getPdfFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, prprivacyver.getPdfFile().getBinaryStream());
    }
    if (prprivacyver.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacyver.getIdStatoRec());
    }
    pst.setString(index++, prprivacyver.getUtenteIns());
    if (prprivacyver.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataIns()));
    }
    pst.setString(index++, prprivacyver.getUtenteAgg());
    if (prprivacyver.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE PR_PRIVACY_VER set ID_PRIVACY_VER= ?  , ID_PRIVACY= ?  , DATA_CREAZIONE= ?  , DATA_PUBBLICAZIONE= ?  , DATA_FINE= ?  , NOME_FILE= ?  , PDF_FILE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO PR_PRIVACY_VER ( ID_PRIVACY_VER,ID_PRIVACY,DATA_CREAZIONE,DATA_PUBBLICAZIONE,DATA_FINE,NOME_FILE,PDF_FILE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO PR_PRIVACY_VER ( ID_PRIVACY,DATA_CREAZIONE,DATA_PUBBLICAZIONE,DATA_FINE,NOME_FILE,PDF_FILE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prprivacyver.getIdPrivacy() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacyver.getIdPrivacy());
    }
    if (prprivacyver.getDataCreazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataCreazione()));
    }
    if (prprivacyver.getDataPubblicazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataPubblicazione()));
    }
    if (prprivacyver.getDataFine() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataFine()));
    }
    pst.setString(index++, prprivacyver.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (prprivacyver.getPdfFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, prprivacyver.getPdfFile().getBinaryStream());
    }
    if (prprivacyver.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prprivacyver.getIdStatoRec());
    }
    pst.setString(index++, prprivacyver.getUtenteIns());
    if (prprivacyver.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataIns()));
    }
    pst.setString(index++, prprivacyver.getUtenteAgg());
    if (prprivacyver.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prprivacyver.getDataAgg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_PRIVACY_VER"};
  }
}
