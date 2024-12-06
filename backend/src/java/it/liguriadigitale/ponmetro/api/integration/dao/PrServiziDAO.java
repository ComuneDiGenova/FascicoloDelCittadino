package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * PrServizi
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.privacy.db.PrServizi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrServiziDAO extends AbstractTableDAO {

  private PrServizi prservizi;

  public PrServiziDAO(PrServizi prservizi) {
    super();
    this.prservizi = prservizi;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from PR_SERVIZI where ID_SERVIZIO=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prservizi.getIdServizio());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from PR_SERVIZI where 1=1 ";
    if (prservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (prservizi.getCodServizio() != null) sql += " and COD_SERVIZIO  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prservizi.getIdServizio() != null) st.setLong(index++, prservizi.getIdServizio());
    if (prservizi.getCodServizio() != null) st.setString(index++, prservizi.getCodServizio());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    PrServizi obj = new PrServizi();

    obj.setIdServizio(r.getLong("ID_SERVIZIO"));

    obj.setCodServizio(r.getString("COD_SERVIZIO"));
    obj.setTitolo(r.getString("TITOLO"));
    obj.setDescrizione(r.getString("DESCRIZIONE"));
    obj.setAnnote(r.getString("ANNOTE"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    obj.setIdPrivacy(r.getLong("ID_PRIVACY"));

    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from PR_SERVIZI where ID_SERVIZIO=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, prservizi.getIdServizio());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from PR_SERVIZI where 1=1 ";
    if (prservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (prservizi.getCodServizio() != null) sql += " and COD_SERVIZIO  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (prservizi.getIdServizio() != null) st.setLong(index++, prservizi.getIdServizio());
    if (prservizi.getCodServizio() != null) st.setString(index++, prservizi.getCodServizio());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_SERVIZIO=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, prservizi.getIdServizio());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (prservizi.getIdServizio() != null) sql += " and ID_SERVIZIO  = ?";
    if (prservizi.getCodServizio() != null) sql += " and COD_SERVIZIO  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (prservizi.getIdServizio() != null) st.setLong(index++, prservizi.getIdServizio());
    if (prservizi.getCodServizio() != null) st.setString(index++, prservizi.getCodServizio());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (prservizi.getIdServizio() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prservizi.getIdServizio());
    }
    pst.setString(index++, prservizi.getCodServizio());
    pst.setString(index++, prservizi.getTitolo());
    pst.setString(index++, prservizi.getDescrizione());
    pst.setString(index++, prservizi.getAnnote());
    if (prservizi.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prservizi.getIdStatoRec());
    }
    pst.setString(index++, prservizi.getUtenteIns());
    if (prservizi.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prservizi.getDataIns()));
    }
    pst.setString(index++, prservizi.getUtenteAgg());
    if (prservizi.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prservizi.getDataAgg()));
    }
    if (prservizi.getIdPrivacy() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prservizi.getIdPrivacy());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE PR_SERVIZI set ID_SERVIZIO= ?  , COD_SERVIZIO= ?  , TITOLO= ?  , DESCRIZIONE= ?  , ANNOTE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  , ID_PRIVACY= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO PR_SERVIZI ( ID_SERVIZIO,COD_SERVIZIO,TITOLO,DESCRIZIONE,ANNOTE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,ID_PRIVACY ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO PR_SERVIZI ( COD_SERVIZIO,TITOLO,DESCRIZIONE,ANNOTE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,ID_PRIVACY ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, prservizi.getCodServizio());
    pst.setString(index++, prservizi.getTitolo());
    pst.setString(index++, prservizi.getDescrizione());
    pst.setString(index++, prservizi.getAnnote());
    if (prservizi.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prservizi.getIdStatoRec());
    }
    pst.setString(index++, prservizi.getUtenteIns());
    if (prservizi.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prservizi.getDataIns()));
    }
    pst.setString(index++, prservizi.getUtenteAgg());
    if (prservizi.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(prservizi.getDataAgg()));
    }
    if (prservizi.getIdPrivacy() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, prservizi.getIdPrivacy());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_SERVIZIO"};
  }
}
