package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTAvatar
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-07-24T09:56:10.867
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.avatar.CfgTAvatar;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CfgTAvatarDAO extends AbstractTableDAO {

  private CfgTAvatar cfgtavatar;

  public CfgTAvatarDAO(CfgTAvatar cfgtavatar) {
    super();
    this.cfgtavatar = cfgtavatar;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_AVATAR where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtavatar.getIdAvatar());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_AVATAR where 1=1 ";
    if (cfgtavatar.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    if (cfgtavatar.getIdFcitt() != null) sql += " and ID_FCITT  = ?";

    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtavatar.getIdAvatar() != null) st.setLong(index++, cfgtavatar.getIdAvatar());
    if (cfgtavatar.getIdFcitt() != null) st.setLong(index++, cfgtavatar.getIdFcitt());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTAvatar obj = new CfgTAvatar();

    obj.setIdAvatar(r.getLong("ID_AVATAR"));

    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setNomeFile(r.getString("NOME_FILE"));
    Blob tmpAVATAR_FILE = r.getBlob("AVATAR_FILE");
    if (tmpAVATAR_FILE != null) {
      obj.setAvatarFile(new javax.sql.rowset.serial.SerialBlob(tmpAVATAR_FILE));
    } else {
      obj.setAvatarFile(null);
    }

    obj.setUtenteIns(r.getString("UTENTE_INS"));
    obj.setDataIns(
        r.getTimestamp("DATA_INS") != null ? r.getTimestamp("DATA_INS").toLocalDateTime() : null);
    obj.setUtenteAgg(r.getString("UTENTE_AGG"));
    obj.setDataAgg(
        r.getTimestamp("DATA_AGG") != null ? r.getTimestamp("DATA_AGG").toLocalDateTime() : null);
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_AVATAR where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtavatar.getIdAvatar());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_AVATAR where 1=1 ";
    if (cfgtavatar.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtavatar.getIdAvatar() != null) st.setLong(index++, cfgtavatar.getIdAvatar());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtavatar.getIdAvatar());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtavatar.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtavatar.getIdAvatar() != null) st.setLong(index++, cfgtavatar.getIdAvatar());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtavatar.getIdAvatar() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtavatar.getIdAvatar());
    }
    if (cfgtavatar.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtavatar.getIdFcitt());
    }
    pst.setString(index++, cfgtavatar.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (cfgtavatar.getAvatarFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, cfgtavatar.getAvatarFile().getBinaryStream());
    }
    pst.setString(index++, cfgtavatar.getUtenteIns());
    if (cfgtavatar.getDataIns() == null) {
      pst.setNull(index++, Types.DATE);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatar.getDataIns()));
    }
    pst.setString(index++, cfgtavatar.getUtenteAgg());
    if (cfgtavatar.getDataAgg() == null) {
      pst.setNull(index++, Types.DATE);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatar.getDataAgg()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_AVATAR set ID_AVATAR= ?  , ID_FCITT= ?  , NOME_FILE= ?  , AVATAR_FILE= ?  , UTENTE_INS= ?  , DATA_INS= ?  , UTENTE_AGG= ?  , DATA_AGG= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_AVATAR ( ID_AVATAR,ID_FCITT,NOME_FILE,AVATAR_FILE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_AVATAR ( ID_FCITT,NOME_FILE,AVATAR_FILE,UTENTE_INS,DATA_INS,UTENTE_AGG,DATA_AGG ) VALUES (? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtavatar.getIdFcitt() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtavatar.getIdFcitt());
    }
    pst.setString(index++, cfgtavatar.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (cfgtavatar.getAvatarFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, cfgtavatar.getAvatarFile().getBinaryStream());
    }
    pst.setString(index++, cfgtavatar.getUtenteIns());
    if (cfgtavatar.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatar.getDataIns()));
    }
    pst.setString(index++, cfgtavatar.getUtenteAgg());
    if (cfgtavatar.getDataAgg() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatar.getDataAgg()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_AVATAR"};
  }
}
