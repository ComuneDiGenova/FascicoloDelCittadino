package it.liguriadigitale.ponmetro.api.integration.dao.update;

import it.liguriadigitale.framework.integration.dao.AbstractUpdateTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.CfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumAzione;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public class CfgRFcittNotificheUpdateDAO extends AbstractUpdateTableDAO {

  private CfgRFcittNotifiche cfgRFcittNotifiche;

  private EnumAzione azione;

  private List<Long> listaMessaggi = null;

  public CfgRFcittNotificheUpdateDAO(CfgRFcittNotifiche cfgRFcittNotifiche) {
    super();
    this.cfgRFcittNotifiche = cfgRFcittNotifiche;
  }

  public void setAzione(EnumAzione azione) {
    this.azione = azione;
  }

  public void setListaIdFcittNotifiche(List<Long> listaMessaggi) {
    this.listaMessaggi = listaMessaggi;
  }

  @Override
  protected String getSqlUpdateByWhere() {
    String sql = getUpdateFields() + " WHERE 1=1 ";
    if (cfgRFcittNotifiche.getIdFcitt() != null) {
      sql += " AND ID_FCITT = ? ";
    }
    if (listaMessaggi != null && !listaMessaggi.isEmpty()) {
      sql += " AND ID_FCITT_NOTIFICHE IN ( ";
      for (Long l : listaMessaggi) {
        sql += " ? ,";
        log.trace("itera listaMessaggi l" + l);
      }
      sql = org.apache.commons.lang.StringUtils.chop(sql);
      sql += " ) ";
    }
    log.debug("getSqlUpdateByWhere: " + sql);
    return sql;
  }

  private String getUpdateFields() {
    String sql =
        " UPDATE CFG_R_FCITT_NOTIFICHE "
            + " SET "
            + "  UTENTE_AGG = ? , "
            + "  DATA_AGG = sysdate ";
    if (azione.equals(EnumAzione.SEGNA_COME_DA_LEGGERE)) {
      sql += " , DATA_PRESA_VISIONE = null ";
    } else if (azione.equals(EnumAzione.SEGNA_COME_LETTO)) {
      sql += " , DATA_PRESA_VISIONE = sysdate ";
    } else if (azione.equals(EnumAzione.SEGNA_COME_IMPORTANTE)) {
      sql += " , ID_STATO_REC = 1 ";
    } else if (azione.equals(EnumAzione.SEGNA_COME_NON_IMPORTANTE)) {
      sql += " , ID_STATO_REC = 0 ";
    }
    log.debug("getUpdateFields: " + sql);
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, new BigDecimal(cfgRFcittNotifiche.getIdFcitt()));
    if (listaMessaggi != null && !listaMessaggi.isEmpty()) {
      for (Long l : listaMessaggi) {
        st.setLong(index++, l);
      }
    }
    // alternativa stream:
    // int[] aIndex = {index};
    // listaMessaggi.stream()
    // .filter(ithitem -> ithitem != null)
    // .forEach(ithitem->{
    // try {
    // st.setLong( aIndex[0]++, ithitem );
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // });
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setString(index++, cfgRFcittNotifiche.getUtenteAgg());
    return index;
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
