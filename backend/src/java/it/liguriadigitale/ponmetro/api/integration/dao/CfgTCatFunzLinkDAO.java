package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * CfgTCatFunzLink
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-01-09T10:42:11.675
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CfgTCatFunzLinkDAO extends AbstractTableDAO {

  private CfgTCatFunzLink cfgtcatfunzlink;

  public CfgTCatFunzLinkDAO(CfgTCatFunzLink cfgtcatfunzlink) {
    super();
    this.cfgtcatfunzlink = cfgtcatfunzlink;
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ_LINK where ID_LINK=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from CFG_T_CAT_FUNZ_LINK where 1=1 ";
    if (cfgtcatfunzlink.getIdLink() != null) sql += " and ID_LINK  = ?";
    if (cfgtcatfunzlink.getIdFunz() != null) sql += " and ID_FUNZ  = ?";
    if (cfgtcatfunzlink.getUrl() != null) sql += " and URL  = ?";
    if (cfgtcatfunzlink.getCodiceMaggioli() != null) sql += " and CODICE_MAGGIOLI = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunzlink.getIdLink() != null) st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
    if (cfgtcatfunzlink.getIdFunz() != null) st.setBigDecimal(index++, cfgtcatfunzlink.getIdFunz());
    if (cfgtcatfunzlink.getUrl() != null) st.setString(index++, cfgtcatfunzlink.getUrl());
    if (cfgtcatfunzlink.getCodiceMaggioli() != null)
      st.setBigDecimal(index++, cfgtcatfunzlink.getCodiceMaggioli());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    CfgTCatFunzLink obj = new CfgTCatFunzLink();

    obj.setIdLink(r.getBigDecimal("ID_LINK"));
    obj.setIdFunz(r.getBigDecimal("ID_FUNZ"));
    obj.setTipoLink(r.getString("TIPO_LINK"));
    obj.setDescrizioniTooltip(r.getString("DESCRIZIONI_TOOLTIP"));
    obj.setUrl(r.getString("URL"));
    obj.setIconaCss(r.getString("ICONA_CSS"));
    obj.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE"));
    obj.setIdStatoRec(r.getBoolean("ID_STATO_REC"));
    obj.setFlagResidente(r.getBoolean("FLAG_RESIDENTE"));
    obj.setFlagNonresidente(r.getBoolean("FLAG_NONRESIDENTE"));
    obj.setCodiceMaggioli(r.getBigDecimal("CODICE_MAGGIOLI"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ_LINK where ID_LINK=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from CFG_T_CAT_FUNZ_LINK where 1=1 ";
    if (cfgtcatfunzlink.getIdLink() != null) sql += " and ID_LINK  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (cfgtcatfunzlink.getIdLink() != null) st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID_LINK=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (cfgtcatfunzlink.getIdLink() != null) sql += " and ID_LINK  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (cfgtcatfunzlink.getIdLink() != null) st.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (cfgtcatfunzlink.getIdLink() != null) {
      pst.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
    }
    if (cfgtcatfunzlink.getIdFunz() != null) {
      pst.setBigDecimal(index++, cfgtcatfunzlink.getIdFunz());
    }
    if (cfgtcatfunzlink.getTipoLink() != null) {
      pst.setString(index++, cfgtcatfunzlink.getTipoLink());
    }
    if (cfgtcatfunzlink.getDescrizioniTooltip() != null) {
      pst.setString(index++, cfgtcatfunzlink.getDescrizioniTooltip());
    }
    if (cfgtcatfunzlink.getUrl() != null) {
      pst.setString(index++, cfgtcatfunzlink.getUrl());
    }
    if (cfgtcatfunzlink.getIconaCss() != null) {
      pst.setString(index++, cfgtcatfunzlink.getIconaCss());
    }
    if (cfgtcatfunzlink.getFlagAbilitazione() != null) {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagAbilitazione());
    }
    if (cfgtcatfunzlink.getIdStatoRec() != null) {
      pst.setBoolean(index++, cfgtcatfunzlink.getIdStatoRec());
    }
    if (cfgtcatfunzlink.getFlagResidente() != null) {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagResidente());
    }
    if (cfgtcatfunzlink.getFlagNonresidente() != null) {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagNonresidente());
    }
    if (cfgtcatfunzlink.getCodiceMaggioli() != null) {
      pst.setBigDecimal(index++, cfgtcatfunzlink.getCodiceMaggioli());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE CFG_T_CAT_FUNZ_LINK set ";

    if (cfgtcatfunzlink.getIdLink() != null) {
      sql += " ID_LINK= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getIdFunz() != null) {
      sql += " ID_FUNZ= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getTipoLink() != null) {
      sql += " TIPO_LINK= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getDescrizioniTooltip() != null) {
      sql += " DESCRIZIONI_TOOLTIP= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getUrl() != null) {
      sql += " URL= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getIconaCss() != null) {
      sql += " ICONA_CSS= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getFlagAbilitazione() != null) {
      sql += " FLAG_ABILITAZIONE= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getIdStatoRec() != null) {
      sql += " ID_STATO_REC= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getFlagResidente() != null) {
      sql += " FLAG_RESIDENTE= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getFlagNonresidente() != null) {
      sql += " FLAG_NONRESIDENTE= ? ";
      sql += " ,";
    }
    if (cfgtcatfunzlink.getCodiceMaggioli() != null) {
      sql += " CODICE_MAGGIOLI= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ_LINK ( ID_LINK,ID_FUNZ,TIPO_LINK,DESCRIZIONI_TOOLTIP,URL,ICONA_CSS,FLAG_ABILITAZIONE,ID_STATO_REC,FLAG_RESIDENTE,FLAG_NONRESIDENTE,CODICE_MAGGIOLI ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, cfgtcatfunzlink.getIdLink());
    pst.setBigDecimal(index++, cfgtcatfunzlink.getIdFunz());
    pst.setString(index++, cfgtcatfunzlink.getTipoLink());
    pst.setString(index++, cfgtcatfunzlink.getDescrizioniTooltip());
    pst.setString(index++, cfgtcatfunzlink.getUrl());
    pst.setString(index++, cfgtcatfunzlink.getIconaCss());
    if (cfgtcatfunzlink.getFlagAbilitazione() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagAbilitazione());
    }
    if (cfgtcatfunzlink.getIdStatoRec() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getIdStatoRec());
    }
    if (cfgtcatfunzlink.getFlagResidente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagResidente());
    }
    if (cfgtcatfunzlink.getFlagNonresidente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagNonresidente());
    }
    pst.setBigDecimal(index++, cfgtcatfunzlink.getCodiceMaggioli());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO CFG_T_CAT_FUNZ_LINK ( ID_FUNZ,TIPO_LINK,DESCRIZIONI_TOOLTIP,URL,ICONA_CSS,FLAG_ABILITAZIONE,ID_STATO_REC,FLAG_RESIDENTE,FLAG_NONRESIDENTE,CODICE_MAGGIOLI ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, cfgtcatfunzlink.getIdFunz());
    pst.setString(index++, cfgtcatfunzlink.getTipoLink());
    pst.setString(index++, cfgtcatfunzlink.getDescrizioniTooltip());
    pst.setString(index++, cfgtcatfunzlink.getUrl());
    pst.setString(index++, cfgtcatfunzlink.getIconaCss());
    if (cfgtcatfunzlink.getFlagAbilitazione() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagAbilitazione());
    }
    if (cfgtcatfunzlink.getIdStatoRec() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getIdStatoRec());
    }
    if (cfgtcatfunzlink.getFlagResidente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagResidente());
    }
    if (cfgtcatfunzlink.getFlagNonresidente() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setBoolean(index++, cfgtcatfunzlink.getFlagNonresidente());
    }
    pst.setBigDecimal(index++, cfgtcatfunzlink.getCodiceMaggioli());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"ID_LINK"};
  }
}
