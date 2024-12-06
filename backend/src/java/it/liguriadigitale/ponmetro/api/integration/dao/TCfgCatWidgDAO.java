package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgCatWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgCatWidg;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class TCfgCatWidgDAO extends AbstractTableDAO {

  private TCfgCatWidg tcfgcatwidg;

  public TCfgCatWidgDAO(TCfgCatWidg tcfgcatwidg) {
    super();
    this.tcfgcatwidg = tcfgcatwidg;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_CAT_WIDG where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_CAT_WIDG where 1=1 ";
    if (tcfgcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatwidg.getIdWidg() != null) st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgCatWidg obj = new TCfgCatWidg();

    obj.setIdWidg(r.getLong("ID_WIDG"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setDenominazioneWidg(r.getString("DENOMINAZIONE_WIDG"));
    obj.setDescrizioneWidg(r.getString("DESCRIZIONE_WIDG"));
    obj.setUriWidg(r.getString("URI_WIDG"));
    Blob tmpIMG_WIDG = r.getBlob("IMG_WIDG");
    if (tmpIMG_WIDG != null) {
      obj.setImgWidg(new javax.sql.rowset.serial.SerialBlob(tmpIMG_WIDG));
    } else {
      obj.setImgWidg(null);
    }

    obj.setDataCatalogazioneWidg(r.getTimestamp("DATA_CATALOGAZIONE_WIDG").toLocalDateTime());
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_CAT_WIDG where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_CAT_WIDG where 1=1 ";
    if (tcfgcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcatwidg.getIdWidg() != null) st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_WIDG=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgcatwidg.getIdWidg() != null) sql += " and ID_WIDG  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgcatwidg.getIdWidg() != null) st.setLong(index++, tcfgcatwidg.getIdWidg());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcatwidg.getIdWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatwidg.getIdWidg());
    }
    if (tcfgcatwidg.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatwidg.getIdComp());
    }
    pst.setString(index++, tcfgcatwidg.getDenominazioneWidg());
    pst.setString(index++, tcfgcatwidg.getDescrizioneWidg());
    pst.setString(index++, tcfgcatwidg.getUriWidg());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (tcfgcatwidg.getImgWidg() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, tcfgcatwidg.getImgWidg().getBinaryStream());
    }
    if (tcfgcatwidg.getDataCatalogazioneWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatwidg.getDataCatalogazioneWidg()));
    }
    if (tcfgcatwidg.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatwidg.getOrdinamento());
    }
    if (tcfgcatwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatwidg.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_CAT_WIDG set ID_WIDG= ?  , ID_COMP= ?  , DENOMINAZIONE_WIDG= ?  , DESCRIZIONE_WIDG= ?  , URI_WIDG= ?  , IMG_WIDG= ?  , DATA_CATALOGAZIONE_WIDG= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_CAT_WIDG ( ID_WIDG,ID_COMP,DENOMINAZIONE_WIDG,DESCRIZIONE_WIDG,URI_WIDG,IMG_WIDG,DATA_CATALOGAZIONE_WIDG,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_CAT_WIDG ( ID_COMP,DENOMINAZIONE_WIDG,DESCRIZIONE_WIDG,URI_WIDG,IMG_WIDG,DATA_CATALOGAZIONE_WIDG,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcatwidg.getIdComp() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatwidg.getIdComp());
    }
    pst.setString(index++, tcfgcatwidg.getDenominazioneWidg());
    pst.setString(index++, tcfgcatwidg.getDescrizioneWidg());
    pst.setString(index++, tcfgcatwidg.getUriWidg());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (tcfgcatwidg.getImgWidg() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, tcfgcatwidg.getImgWidg().getBinaryStream());
    }
    if (tcfgcatwidg.getDataCatalogazioneWidg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(tcfgcatwidg.getDataCatalogazioneWidg()));
    }
    if (tcfgcatwidg.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcatwidg.getOrdinamento());
    }
    if (tcfgcatwidg.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcatwidg.getFlagAbilitazione());
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_WIDG"};
  }
}
