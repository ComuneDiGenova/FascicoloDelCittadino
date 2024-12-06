package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTAvatarDef
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-07-24T09:56:11.237
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.avatar.CfgTAvatarDef;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CfgTAvatarDefDAO extends AbstractTableDAO {

  private CfgTAvatarDef cfgtavatardef;

  public CfgTAvatarDefDAO(CfgTAvatarDef cfgtavatardef) {
    super();
    this.cfgtavatardef = cfgtavatardef;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_AVATAR_DEF where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_AVATAR_DEF where 1=1 ";
    if (cfgtavatardef.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtavatardef.getIdAvatar() != null) st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTAvatarDef obj = new CfgTAvatarDef();

    obj.setIdAvatar(r.getLong("ID_AVATAR"));

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
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_AVATAR_DEF where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_AVATAR_DEF where 1=1 ";
    if (cfgtavatardef.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtavatardef.getIdAvatar() != null) st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_AVATAR=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtavatardef.getIdAvatar() != null) sql += " and ID_AVATAR  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtavatardef.getIdAvatar() != null) st.setLong(index++, cfgtavatardef.getIdAvatar());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtavatardef.getIdAvatar() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setLong(index++, cfgtavatardef.getIdAvatar());
    }
    pst.setString(index++, cfgtavatardef.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (cfgtavatardef.getAvatarFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, cfgtavatardef.getAvatarFile().getBinaryStream());
    }
    pst.setString(index++, cfgtavatardef.getUtenteIns());
    if (cfgtavatardef.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatardef.getDataIns()));
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql =
        "UPDATE CFG_T_AVATAR_DEF set ID_AVATAR= ?  , NOME_FILE= ?  , AVATAR_FILE= ?  , UTENTE_INS= ?  , DATA_INS= ?  ";
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_AVATAR_DEF ( ID_AVATAR,NOME_FILE,AVATAR_FILE,UTENTE_INS,DATA_INS ) VALUES (? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsert(PreparedStatement st) throws SQLException {
    setFieldStatement(st);
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_AVATAR_DEF ( NOME_FILE,AVATAR_FILE,UTENTE_INS,DATA_INS ) VALUES (? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgtavatardef.getNomeFile());
    /* La colonna CLOB viene valorizzata con la stringa di appoggio */
    if (cfgtavatardef.getAvatarFile() == null) {
      pst.setNull(index++, Types.CLOB);
    } else {
      pst.setBinaryStream(index++, cfgtavatardef.getAvatarFile().getBinaryStream());
    }
    pst.setString(index++, cfgtavatardef.getUtenteIns());
    if (cfgtavatardef.getDataIns() == null) {
      pst.setNull(index++, 3);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(cfgtavatardef.getDataIns()));
    }
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_AVATAR"};
  }
}
