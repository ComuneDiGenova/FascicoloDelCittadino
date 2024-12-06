package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * FdcAudit
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-17T15:18:49.451921
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableWithoutKeysDAO;
import it.liguriadigitale.ponmetro.api.pojo.audit.FdcAudit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FdcAuditDAO extends AbstractTableWithoutKeysDAO {

  private FdcAudit fdcaudit;

  public FdcAuditDAO(FdcAudit fdcaudit) {
    super();
    this.fdcaudit = fdcaudit;
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from FDC_AUDIT";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {}

  public Object getFromResultSet(ResultSet r) throws SQLException {
    FdcAudit obj = new FdcAudit();

    obj.setIdFcitt(r.getBigDecimal("ID_FCITT"));
    obj.setNomePagina(r.getString("NOME_PAGINA"));
    obj.setAmbiente(r.getString("AMBIENTE"));
    obj.setSessionId(r.getString("SESSION_ID"));
    obj.setAutorizzato(r.getBoolean("AUTORIZZATO"));
    obj.setTimeStamp(
        r.getTimestamp("TIME_STAMP") != null
            ? r.getTimestamp("TIME_STAMP").toLocalDateTime()
            : null);
    obj.setTipoUtente(r.getLong("TIPO_UTENTE"));
    if (r.wasNull()) {
      obj.setTipoUtente(null);
    }

    obj.setAnnoNascita(r.getString("ANNO_NASCITA"));
    obj.setSesso(r.getString("SESSO"));
    obj.setServizioEsterno(r.getString("SERVIZIO_ESTERNO"));
    return obj;
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from FDC_AUDIT";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {}

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + "";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (fdcaudit.getIdFcitt() != null) {
      pst.setBigDecimal(index++, fdcaudit.getIdFcitt());
    }
    if (fdcaudit.getNomePagina() != null) {
      pst.setString(index++, fdcaudit.getNomePagina());
    }
    if (fdcaudit.getAmbiente() != null) {
      pst.setString(index++, fdcaudit.getAmbiente());
    }
    if (fdcaudit.getSessionId() != null) {
      pst.setString(index++, fdcaudit.getSessionId());
    }
    if (fdcaudit.getAutorizzato() != null) {
      pst.setBoolean(index++, fdcaudit.getAutorizzato());
    }
    if (fdcaudit.getTimeStamp() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(fdcaudit.getTimeStamp()));
    }
    if (fdcaudit.getTipoUtente() != null) {
      pst.setLong(index++, fdcaudit.getTipoUtente());
    }
    if (fdcaudit.getAnnoNascita() != null) {
      pst.setString(index++, fdcaudit.getAnnoNascita());
    }
    if (fdcaudit.getSesso() != null) {
      pst.setString(index++, fdcaudit.getSesso());
    }
    if (fdcaudit.getServizioEsterno() != null) {
      pst.setString(index++, fdcaudit.getServizioEsterno());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE FDC_AUDIT set ";

    if (fdcaudit.getIdFcitt() != null) {
      sql += " ID_FCITT= ? ";
      sql += " ,";
    }
    if (fdcaudit.getNomePagina() != null) {
      sql += " NOME_PAGINA= ? ";
      sql += " ,";
    }
    if (fdcaudit.getAmbiente() != null) {
      sql += " AMBIENTE= ? ";
      sql += " ,";
    }
    if (fdcaudit.getSessionId() != null) {
      sql += " SESSION_ID= ? ";
      sql += " ,";
    }
    if (fdcaudit.getAutorizzato() != null) {
      sql += " AUTORIZZATO= ? ";
      sql += " ,";
    }
    if (fdcaudit.getTimeStamp() != null) {
      sql += " TIME_STAMP= ? ";
      sql += " ,";
    }
    if (fdcaudit.getTipoUtente() != null) {
      sql += " TIPO_UTENTE= ? ";
      sql += " ,";
    }
    if (fdcaudit.getAnnoNascita() != null) {
      sql += " ANNO_NASCITA= ? ";
      sql += " ,";
    }
    if (fdcaudit.getSesso() != null) {
      sql += " SESSO= ? ";
      sql += " ,";
    }
    if (fdcaudit.getServizioEsterno() != null) {
      sql += " SERVIZIO_ESTERNO= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO FDC_AUDIT ( ID_FCITT,NOME_PAGINA,AMBIENTE,SESSION_ID,AUTORIZZATO,TIME_STAMP,TIPO_UTENTE,ANNO_NASCITA,SESSO,SERVIZIO_ESTERNO ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, fdcaudit.getIdFcitt());
    pst.setString(index++, fdcaudit.getNomePagina());
    pst.setString(index++, fdcaudit.getAmbiente());
    pst.setString(index++, fdcaudit.getSessionId());
    if (fdcaudit.getAutorizzato() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, fdcaudit.getAutorizzato());
    }
    if (fdcaudit.getTimeStamp() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(fdcaudit.getTimeStamp()));
    }
    if (fdcaudit.getTipoUtente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, fdcaudit.getTipoUtente());
    }
    pst.setString(index++, fdcaudit.getAnnoNascita());
    pst.setString(index++, fdcaudit.getSesso());
    pst.setString(index++, fdcaudit.getServizioEsterno());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO FDC_AUDIT ( ID_FCITT,NOME_PAGINA,AMBIENTE,SESSION_ID,AUTORIZZATO,TIME_STAMP,TIPO_UTENTE,ANNO_NASCITA,SESSO,SERVIZIO_ESTERNO ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, fdcaudit.getIdFcitt());
    pst.setString(index++, fdcaudit.getNomePagina());
    pst.setString(index++, fdcaudit.getAmbiente());
    pst.setString(index++, fdcaudit.getSessionId());
    if (fdcaudit.getAutorizzato() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, fdcaudit.getAutorizzato());
    }
    if (fdcaudit.getTimeStamp() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(fdcaudit.getTimeStamp()));
    }
    if (fdcaudit.getTipoUtente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, fdcaudit.getTipoUtente());
    }
    pst.setString(index++, fdcaudit.getAnnoNascita());
    pst.setString(index++, fdcaudit.getSesso());
    pst.setString(index++, fdcaudit.getServizioEsterno());
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    // TODO Auto-generated method stub
    return null;
  }
}
