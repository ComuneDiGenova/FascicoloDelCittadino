package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgTipiAnomalie
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgTipiAnomalie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TCfgTipiAnomalieDAO extends AbstractTableDAO {

  private TCfgTipiAnomalie tcfgtipianomalie;

  public TCfgTipiAnomalieDAO(TCfgTipiAnomalie tcfgtipianomalie) {
    super();
    this.tcfgtipianomalie = tcfgtipianomalie;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_TIPI_ANOMALIE where ID_TIPO_ANOMALIA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_TIPI_ANOMALIE where 1=1 ";
    if (tcfgtipianomalie.getIdTipoAnomalia() != null) sql += " and ID_TIPO_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgtipianomalie.getIdTipoAnomalia() != null)
      st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgTipiAnomalie obj = new TCfgTipiAnomalie();

    obj.setIdTipoAnomalia(r.getLong("ID_TIPO_ANOMALIA"));

    obj.setCodTipoAnomalia(r.getString("COD_TIPO_ANOMALIA"));
    obj.setDescrizioneTipoAnomalia(r.getString("DESCRIZIONE_TIPO_ANOMALIA"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_TIPI_ANOMALIE where ID_TIPO_ANOMALIA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_TIPI_ANOMALIE where 1=1 ";
    if (tcfgtipianomalie.getIdTipoAnomalia() != null) sql += " and ID_TIPO_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgtipianomalie.getIdTipoAnomalia() != null)
      st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_TIPO_ANOMALIA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgtipianomalie.getIdTipoAnomalia() != null) sql += " and ID_TIPO_ANOMALIA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgtipianomalie.getIdTipoAnomalia() != null)
      st.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgtipianomalie.getIdTipoAnomalia() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgtipianomalie.getIdTipoAnomalia());
    }
    pst.setString(index++, tcfgtipianomalie.getCodTipoAnomalia());
    pst.setString(index++, tcfgtipianomalie.getDescrizioneTipoAnomalia());
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_TIPI_ANOMALIE set ID_TIPO_ANOMALIA= ?  , COD_TIPO_ANOMALIA= ?  , DESCRIZIONE_TIPO_ANOMALIA= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_TIPI_ANOMALIE ( ID_TIPO_ANOMALIA,COD_TIPO_ANOMALIA,DESCRIZIONE_TIPO_ANOMALIA ) VALUES (? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_TIPI_ANOMALIE ( COD_TIPO_ANOMALIA,DESCRIZIONE_TIPO_ANOMALIA ) VALUES (? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, tcfgtipianomalie.getCodTipoAnomalia());
    pst.setString(index++, tcfgtipianomalie.getDescrizioneTipoAnomalia());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_TIPO_ANOMALIA"};
  }
}
