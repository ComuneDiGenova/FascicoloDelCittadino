package it.liguriadigitale.ponmetro.api.integration.dao.update;

import it.liguriadigitale.framework.integration.dao.AbstractUpdateTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvAnonim;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.lang.NotImplementedException;

public class CpvAnonimUpdateDAO extends AbstractUpdateTableDAO {

  private CpvAnonim cpvAnonim;

  public CpvAnonimUpdateDAO(CpvAnonim cpvAnonim) {
    super();
    this.cpvAnonim = cpvAnonim;
  }

  @Override
  protected String getSqlUpdateByWhere() {
    String sql = "UPDATE cpv_anonim set id_stato_rec=0 WHERE 1=1 ";
    if (cpvAnonim.getPersonId() != null) {
      sql = sql + " and person_id=?";
    }
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    if (cpvAnonim.getPersonId() != null) {
      st.setLong(1, cpvAnonim.getPersonId());
    }
  }

  @Override
  protected String[] getKeyColumnsNames() {
    throw new NotImplementedException();
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {
    throw new NotImplementedException();
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement arg0) throws SQLException {
    throw new NotImplementedException();
  }
}
