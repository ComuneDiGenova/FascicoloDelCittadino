package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CpvPerson
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvPerson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CpvPersonDAO extends AbstractTableDAO {

  private CpvPerson cpvperson;

  public CpvPersonDAO(CpvPerson cpvperson) {
    super();
    this.cpvperson = cpvperson;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CPV_PERSON where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cpvperson.getPersonId());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CPV_PERSON where 1=1 ";
    if (cpvperson.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvperson.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cpvperson.getPersonId() != null) st.setLong(index++, cpvperson.getPersonId());
    if (cpvperson.getCodiceFiscale() != null) st.setString(index++, cpvperson.getCodiceFiscale());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CpvPerson obj = new CpvPerson();

    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setIdAnagrafica(r.getString("ID_ANAGRAFICA"));
    obj.setCodiceFiscale(r.getString("CODICE_FISCALE"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(r.getTimestamp("DATA_INS").toLocalDateTime());
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(r.getTimestamp("DATA_AGG").toLocalDateTime());
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CPV_PERSON where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cpvperson.getPersonId());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CPV_PERSON where 1=1 ";
    if (cpvperson.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvperson.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cpvperson.getPersonId() != null) st.setLong(index++, cpvperson.getPersonId());
    if (cpvperson.getCodiceFiscale() != null) st.setString(index++, cpvperson.getCodiceFiscale());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where PERSON_ID=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cpvperson.getPersonId());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cpvperson.getPersonId() != null) sql += " and PERSON_ID  = ?";
    if (cpvperson.getCodiceFiscale() != null) sql += " and CODICE_FISCALE  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cpvperson.getPersonId() != null) st.setLong(index++, cpvperson.getPersonId());
    if (cpvperson.getCodiceFiscale() != null) st.setString(index++, cpvperson.getCodiceFiscale());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cpvperson.getPersonId() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvperson.getPersonId());
    }
    pst.setString(index++, cpvperson.getIdAnagrafica());
    pst.setString(index++, cpvperson.getCodiceFiscale());
    if (cpvperson.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvperson.getIdStatoRec());
    }
    pst.setString(index++, cpvperson.getUtenteIns());
    if (cpvperson.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvperson.getDataIns()));
    }
    pst.setString(index++, cpvperson.getUtenteAgg());
    if (cpvperson.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvperson.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CPV_PERSON set PERSON_ID= ?  , ID_ANAGRAFICA= ?  , CODICE_FISCALE= ?  , ID_STATO_REC= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CPV_PERSON ( PERSON_ID,ID_ANAGRAFICA,CODICE_FISCALE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CPV_PERSON ( ID_ANAGRAFICA,CODICE_FISCALE,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cpvperson.getIdAnagrafica());
    pst.setString(index++, cpvperson.getCodiceFiscale());
    if (cpvperson.getIdStatoRec() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cpvperson.getIdStatoRec());
    }
    pst.setString(index++, cpvperson.getUtenteIns());
    if (cpvperson.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvperson.getDataIns()));
    }
    pst.setString(index++, cpvperson.getUtenteAgg());
    if (cpvperson.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cpvperson.getDataAgg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"PERSON_ID"};
  }
}
