package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * TCfgCatTipiNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit!
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.config.db.TCfgCatTipiNotifiche;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TCfgCatTipiNotificheDAO extends AbstractTableDAO {

  private TCfgCatTipiNotifiche tcfgcattipinotifiche;

  public TCfgCatTipiNotificheDAO(TCfgCatTipiNotifiche tcfgcattipinotifiche) {
    super();
    this.tcfgcattipinotifiche = tcfgcattipinotifiche;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from T_CFG_CAT_TIPI_NOTIFICHE where ID_T_NOTIFICA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from T_CFG_CAT_TIPI_NOTIFICHE where 1=1 ";
    if (tcfgcattipinotifiche.getIdTNotifica() != null) sql += " and ID_T_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcattipinotifiche.getIdTNotifica() != null)
      st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    TCfgCatTipiNotifiche obj = new TCfgCatTipiNotifiche();

    obj.setIdTNotifica(r.getLong("ID_T_NOTIFICA"));

    obj.setDenominazioneTNotifica(r.getString("DENOMINAZIONE_T_NOTIFICA"));
    obj.setDescrizioneTNotifica(r.getString("DESCRIZIONE_T_NOTIFICA"));
    obj.setDataCatalogazioneTNotifica(
        r.getTimestamp("DATA_CATALOGAZIONE_T_NOTIFICA").toLocalDateTime());
    obj.setImgTNotifica(r.getString("IMG_T_NOTIFICA"));
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from T_CFG_CAT_TIPI_NOTIFICHE where ID_T_NOTIFICA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from T_CFG_CAT_TIPI_NOTIFICHE where 1=1 ";
    if (tcfgcattipinotifiche.getIdTNotifica() != null) sql += " and ID_T_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (tcfgcattipinotifiche.getIdTNotifica() != null)
      st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_T_NOTIFICA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (tcfgcattipinotifiche.getIdTNotifica() != null) sql += " and ID_T_NOTIFICA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (tcfgcattipinotifiche.getIdTNotifica() != null)
      st.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (tcfgcattipinotifiche.getIdTNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcattipinotifiche.getIdTNotifica());
    }
    pst.setString(index++, tcfgcattipinotifiche.getDenominazioneTNotifica());
    pst.setString(index++, tcfgcattipinotifiche.getDescrizioneTNotifica());
    if (tcfgcattipinotifiche.getDataCatalogazioneTNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++,
          java.sql.Timestamp.valueOf(tcfgcattipinotifiche.getDataCatalogazioneTNotifica()));
    }
    pst.setString(index++, tcfgcattipinotifiche.getImgTNotifica());
    if (tcfgcattipinotifiche.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcattipinotifiche.getOrdinamento());
    }
    if (tcfgcattipinotifiche.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcattipinotifiche.getFlagAbilitazione());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE T_CFG_CAT_TIPI_NOTIFICHE set ID_T_NOTIFICA= ?  , DENOMINAZIONE_T_NOTIFICA= ?  , DESCRIZIONE_T_NOTIFICA= ?  , DATA_CATALOGAZIONE_T_NOTIFICA= ?  , IMG_T_NOTIFICA= ?  , ORDINAMENTO= ?  , FLAG_ABILITAZIONE= ?  ";
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO T_CFG_CAT_TIPI_NOTIFICHE ( ID_T_NOTIFICA,DENOMINAZIONE_T_NOTIFICA,DESCRIZIONE_T_NOTIFICA,DATA_CATALOGAZIONE_T_NOTIFICA,IMG_T_NOTIFICA,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO T_CFG_CAT_TIPI_NOTIFICHE ( DENOMINAZIONE_T_NOTIFICA,DESCRIZIONE_T_NOTIFICA,DATA_CATALOGAZIONE_T_NOTIFICA,IMG_T_NOTIFICA,ORDINAMENTO,FLAG_ABILITAZIONE ) VALUES (? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, tcfgcattipinotifiche.getDenominazioneTNotifica());
    pst.setString(index++, tcfgcattipinotifiche.getDescrizioneTNotifica());
    if (tcfgcattipinotifiche.getDataCatalogazioneTNotifica() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(
          index++,
          java.sql.Timestamp.valueOf(tcfgcattipinotifiche.getDataCatalogazioneTNotifica()));
    }
    pst.setString(index++, tcfgcattipinotifiche.getImgTNotifica());
    if (tcfgcattipinotifiche.getOrdinamento() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, tcfgcattipinotifiche.getOrdinamento());
    }
    if (tcfgcattipinotifiche.getFlagAbilitazione() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setBoolean(index++, tcfgcattipinotifiche.getFlagAbilitazione());
    }
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_T_NOTIFICA"};
  }
}
