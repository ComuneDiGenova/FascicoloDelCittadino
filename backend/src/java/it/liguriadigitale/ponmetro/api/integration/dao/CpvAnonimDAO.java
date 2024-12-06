package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CpvAnonim
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-11-17T18:03:13.470
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvAnonim;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CpvAnonimDAO extends AbstractTableDAO {

  private CpvAnonim cpvanonim;

  public CpvAnonimDAO(CpvAnonim cpvanonim) {
    super();
    this.cpvanonim = cpvanonim;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CPV_ANONIM where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cpvanonim.getPersonId());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CPV_ANONIM where 1=1 AND ID_STATO_REC=1";
    if (cpvanonim.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvanonim.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cpvanonim.getPersonId() != null) st.setLong(index++, cpvanonim.getPersonId());
    if (cpvanonim.getCodiceFiscale() != null) st.setString(index++, cpvanonim.getCodiceFiscale());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CpvAnonim obj = new CpvAnonim();

    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setCodiceFiscale(r.getString("CODICE_FISCALE"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    obj.setNome(r.getString("NOME"));
    obj.setCognome(r.getString("COGNOME"));
    obj.setDataNascita(
        r.getTimestamp("DATA_NASCITA") != null
            ? r.getTimestamp("DATA_NASCITA").toLocalDateTime()
            : null);
    obj.setSesso(r.getString("SESSO"));
    obj.setCodiceBelfioreNascita(r.getString("CODICE_BELFIORE_NASCITA"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CPV_ANONIM where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cpvanonim.getPersonId());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CPV_ANONIM where 1=1 ";
    if (cpvanonim.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvanonim.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cpvanonim.getPersonId() != null) st.setLong(index++, cpvanonim.getPersonId());
    if (cpvanonim.getCodiceFiscale() != null) st.setString(index++, cpvanonim.getCodiceFiscale());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cpvanonim.getPersonId());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cpvanonim.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvanonim.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cpvanonim.getPersonId() != null) st.setLong(index++, cpvanonim.getPersonId());
    if (cpvanonim.getCodiceFiscale() != null) st.setString(index++, cpvanonim.getCodiceFiscale());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cpvanonim.getPersonId() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvanonim.getPersonId());
    }
    pst.setString(index++, cpvanonim.getCodiceFiscale());
    if (cpvanonim.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvanonim.getIdStatoRec());
    }
    pst.setString(index++, cpvanonim.getUtenteIns());
    if (cpvanonim.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataIns()));
    }
    pst.setString(index++, cpvanonim.getUtenteAgg());
    if (cpvanonim.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataAgg()));
    }
    pst.setString(index++, cpvanonim.getNome());
    pst.setString(index++, cpvanonim.getCognome());
    if (cpvanonim.getDataNascita() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataNascita()));
    }
    pst.setString(index++, cpvanonim.getSesso());
    pst.setString(index++, cpvanonim.getCodiceBelfioreNascita());
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CPV_ANONIM set PERSON_ID= ?  , CODICE_FISCALE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  , NOME= ?  , COGNOME= ?  , DATA_NASCITA= ?  , SESSO= ?  , CODICE_BELFIORE_NASCITA= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CPV_ANONIM ( PERSON_ID,CODICE_FISCALE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,NOME,COGNOME,DATA_NASCITA,SESSO,CODICE_BELFIORE_NASCITA ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CPV_ANONIM ( CODICE_FISCALE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,NOME,COGNOME,DATA_NASCITA,SESSO,CODICE_BELFIORE_NASCITA ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cpvanonim.getCodiceFiscale());
    if (cpvanonim.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvanonim.getIdStatoRec());
    }
    pst.setString(index++, cpvanonim.getUtenteIns());
    if (cpvanonim.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataIns()));
    }
    pst.setString(index++, cpvanonim.getUtenteAgg());
    if (cpvanonim.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataAgg()));
    }
    pst.setString(index++, cpvanonim.getNome());
    pst.setString(index++, cpvanonim.getCognome());
    if (cpvanonim.getDataNascita() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvanonim.getDataNascita()));
    }
    pst.setString(index++, cpvanonim.getSesso());
    pst.setString(index++, cpvanonim.getCodiceBelfioreNascita());
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"PERSON_ID"};
  }
}
