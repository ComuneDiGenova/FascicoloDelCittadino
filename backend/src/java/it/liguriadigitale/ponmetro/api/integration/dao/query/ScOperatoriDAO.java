package it.liguriadigitale.ponmetro.api.integration.dao.query;

/**
 * ScOperatori
 *
 * <p>WARNING! Automatically generated file with TableGen 2.0.7! Do not edit! created:
 * 2022-04-01T11:49:27.497
 */
import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.operatori.ScOperatori;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScOperatoriDAO extends AbstractSearchDAO {

  private ScOperatori scoperatori;

  public ScOperatoriDAO(ScOperatori scoperatori) {
    super();
    this.scoperatori = scoperatori;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from SC_OPERATORI where 1=1 and ID_STATO_REC=1 ";
    if (scoperatori.getIdOperatore() != null) sql += " and ID_OPERATORE  = ?";
    if (scoperatori.getOpCf() != null) sql += " and OP_CF  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (scoperatori.getIdOperatore() != null) st.setLong(index++, scoperatori.getIdOperatore());
    if (scoperatori.getOpCf() != null) st.setString(index++, scoperatori.getOpCf());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    ScOperatori obj = new ScOperatori();

    obj.setIdOperatore(r.getLong("ID_OPERATORE"));
    if (r.wasNull()) {
      obj.setIdOperatore(null);
    }

    obj.setOpLogin(r.getString("OP_LOGIN"));
    obj.setOpCf(r.getString("OP_CF"));
    obj.setOpNome(r.getString("OP_NOME"));
    obj.setOpCognome(r.getString("OP_COGNOME"));
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));
    if (r.wasNull()) {
      obj.setIdStatoRec(null);
    }

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    obj.setIdRuolo(r.getLong("ID_RUOLO"));
    if (r.wasNull()) {
      obj.setIdRuolo(null);
    }

    return obj;
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from SC_OPERATORI where 1=1 ";
    if (scoperatori.getIdOperatore() != null) sql += " and ID_OPERATORE  = ?";
    if (scoperatori.getOpCf() != null) sql += " and OP_CF  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (scoperatori.getIdOperatore() != null) st.setLong(index++, scoperatori.getIdOperatore());
    if (scoperatori.getOpCf() != null) st.setString(index++, scoperatori.getOpCf());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (scoperatori.getIdOperatore() != null) sql += " and ID_OPERATORE  = ?";
    if (scoperatori.getOpCf() != null) sql += " and OP_CF  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (scoperatori.getIdOperatore() != null) st.setLong(index++, scoperatori.getIdOperatore());
    if (scoperatori.getOpCf() != null) st.setString(index++, scoperatori.getOpCf());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (scoperatori.getIdOperatore() != null) {
      pst.setLong(index++, scoperatori.getIdOperatore());
    }
    if (scoperatori.getOpLogin() != null) {
      pst.setString(index++, scoperatori.getOpLogin());
    }
    if (scoperatori.getOpCf() != null) {
      pst.setString(index++, scoperatori.getOpCf());
    }
    if (scoperatori.getOpNome() != null) {
      pst.setString(index++, scoperatori.getOpNome());
    }
    if (scoperatori.getOpCognome() != null) {
      pst.setString(index++, scoperatori.getOpCognome());
    }
    if (scoperatori.getIdStatoRec() != null) {
      pst.setLong(index++, scoperatori.getIdStatoRec());
    }
    if (scoperatori.getUtenteIns() != null) {
      pst.setString(index++, scoperatori.getUtenteIns());
    }
    if (scoperatori.getDataIns() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataIns()));
    }
    if (scoperatori.getUtenteAgg() != null) {
      pst.setString(index++, scoperatori.getUtenteAgg());
    }
    if (scoperatori.getDataAgg() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataAgg()));
    }
    if (scoperatori.getIdRuolo() != null) {
      pst.setLong(index++, scoperatori.getIdRuolo());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE SC_OPERATORI set ";

    if (scoperatori.getIdOperatore() != null) {
      sql += " ID_OPERATORE= ? ";
      sql += " ,";
    }
    if (scoperatori.getOpLogin() != null) {
      sql += " OP_LOGIN= ? ";
      sql += " ,";
    }
    if (scoperatori.getOpCf() != null) {
      sql += " OP_CF= ? ";
      sql += " ,";
    }
    if (scoperatori.getOpNome() != null) {
      sql += " OP_NOME= ? ";
      sql += " ,";
    }
    if (scoperatori.getOpCognome() != null) {
      sql += " OP_COGNOME= ? ";
      sql += " ,";
    }
    if (scoperatori.getIdStatoRec() != null) {
      sql += " ID_STATO_REC= ? ";
      sql += " ,";
    }
    if (scoperatori.getUtenteIns() != null) {
      sql += " UTENTE_INS= ? ";
      sql += " ,";
    }
    if (scoperatori.getDataIns() != null) {
      sql += " DATA_INS= ? ";
      sql += " ,";
    }
    if (scoperatori.getUtenteAgg() != null) {
      sql += " UTENTE_AGG= ? ";
      sql += " ,";
    }
    if (scoperatori.getDataAgg() != null) {
      sql += " DATA_AGG= ? ";
      sql += " ,";
    }
    if (scoperatori.getIdRuolo() != null) {
      sql += " ID_RUOLO= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO SC_OPERATORI ( ID_OPERATORE,OP_LOGIN,OP_CF,OP_NOME,OP_COGNOME,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,ID_RUOLO ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (scoperatori.getIdOperatore() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdOperatore());
    }
    pst.setString(index++, scoperatori.getOpLogin());
    pst.setString(index++, scoperatori.getOpCf());
    pst.setString(index++, scoperatori.getOpNome());
    pst.setString(index++, scoperatori.getOpCognome());
    if (scoperatori.getIdStatoRec() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdStatoRec());
    }
    pst.setString(index++, scoperatori.getUtenteIns());
    if (scoperatori.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataIns()));
    }
    pst.setString(index++, scoperatori.getUtenteAgg());
    if (scoperatori.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataAgg()));
    }
    if (scoperatori.getIdRuolo() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdRuolo());
    }
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO SC_OPERATORI ( ID_OPERATORE,OP_LOGIN,OP_CF,OP_NOME,OP_COGNOME,ID_STATO_REC,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG,ID_RUOLO ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (scoperatori.getIdOperatore() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdOperatore());
    }
    pst.setString(index++, scoperatori.getOpLogin());
    pst.setString(index++, scoperatori.getOpCf());
    pst.setString(index++, scoperatori.getOpNome());
    pst.setString(index++, scoperatori.getOpCognome());
    if (scoperatori.getIdStatoRec() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdStatoRec());
    }
    pst.setString(index++, scoperatori.getUtenteIns());
    if (scoperatori.getDataIns() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataIns()));
    }
    pst.setString(index++, scoperatori.getUtenteAgg());
    if (scoperatori.getDataAgg() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(scoperatori.getDataAgg()));
    }
    if (scoperatori.getIdRuolo() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, scoperatori.getIdRuolo());
    }
    return index;
  }
}
