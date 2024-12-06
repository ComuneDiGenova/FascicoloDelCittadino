package it.liguriadigitale.ponmetro.api.integration.dao;

/**
 * S3Ct46tCodCategorie
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-12-12T16:24:21.565
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.categoriecatastali.S3Ct46tCodCategorie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class S3Ct46tCodCategorieDAO extends AbstractTableDAO {

  private S3Ct46tCodCategorie s3ct46tcodcategorie;

  public S3Ct46tCodCategorieDAO(S3Ct46tCodCategorie s3ct46tcodcategorie) {
    super();
    this.s3ct46tcodcategorie = s3ct46tcodcategorie;
  }

  public S3Ct46tCodCategorieDAO() {
    // TODO Auto-generated constructor stub
  }

  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from S3_CT46T_COD_CATEGORIE where CT46_COD_CATEGORIA=?";
    return sql;
  }

  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from S3_CT46T_COD_CATEGORIE where 1=1 ";
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null) sql += " and CT46_COD_CATEGORIA  = ?";
    return sql;
  }

  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null)
      st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  public Object getFromResultSet(ResultSet r) throws SQLException {
    S3Ct46tCodCategorie obj = new S3Ct46tCodCategorie();

    obj.setCt46CodCategoria(r.getString("CT46_COD_CATEGORIA"));
    obj.setCt46Descriz(r.getString("CT46_DESCRIZ"));
    return obj;
  }

  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from S3_CT46T_COD_CATEGORIE where CT46_COD_CATEGORIA=?";
    return sql;
  }

  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from S3_CT46T_COD_CATEGORIE where 1=1 ";
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null) sql += " and CT46_COD_CATEGORIA  = ?";
    return sql;
  }

  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null)
      st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where CT46_COD_CATEGORIA=?";
    return sql;
  }

  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null) sql += " and CT46_COD_CATEGORIA  = ?";
    return sql;
  }

  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null)
      st.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (s3ct46tcodcategorie.getCt46CodCategoria() != null) {
      pst.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
    }
    if (s3ct46tcodcategorie.getCt46Descriz() != null) {
      pst.setString(index++, s3ct46tcodcategorie.getCt46Descriz());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE S3_CT46T_COD_CATEGORIE set ";

    if (s3ct46tcodcategorie.getCt46CodCategoria() != null) {
      sql += " CT46_COD_CATEGORIA= ? ";
      sql += " ,";
    }
    if (s3ct46tcodcategorie.getCt46Descriz() != null) {
      sql += " CT46_DESCRIZ= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO S3_CT46T_COD_CATEGORIE ( CT46_COD_CATEGORIA,CT46_DESCRIZ ) VALUES (? ,?   )";
    return sql;
  }

  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, s3ct46tcodcategorie.getCt46CodCategoria());
    pst.setString(index++, s3ct46tcodcategorie.getCt46Descriz());
  }

  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql = "INSERT INTO S3_CT46T_COD_CATEGORIE ( CT46_DESCRIZ ) VALUES (?   )";
    return sql;
  }

  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, s3ct46tcodcategorie.getCt46Descriz());
    return index;
  }

  protected String[] getKeyColumnsNames() {
    return new String[] {"CT46_COD_CATEGORIA"};
  }
}
