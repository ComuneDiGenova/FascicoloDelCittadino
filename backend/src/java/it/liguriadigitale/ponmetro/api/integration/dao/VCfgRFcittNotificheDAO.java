package it.liguriadigitale.ponmetro.api.integration.dao;

import it.liguriadigitale.framework.integration.dao.AbstractSearchDAO;
import it.liguriadigitale.ponmetro.api.pojo.messaggiutente.VCfgRFcittNotifiche;
import it.liguriadigitale.ponmetro.messaggi.utente.model.EnumFiltroTipologia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VCfgRFcittNotificheDAO extends AbstractSearchDAO {

  private static Log log = LogFactory.getLog(VCfgRFcittNotificheDAO.class);

  private static final String ALIAS_COUNT_NUMERO = "COUNT_NUMERO";

  private VCfgRFcittNotifiche vcfgrfcittnotifiche;
  private boolean voglioNumero;
  private EnumFiltroTipologia tipoMessaggi;

  public VCfgRFcittNotificheDAO(VCfgRFcittNotifiche vCfgRFcittNotifiche) {
    super();
    this.vcfgrfcittnotifiche = vCfgRFcittNotifiche;
  }

  public boolean isVoglioNumero() {
    return voglioNumero;
  }

  public void setVoglioNumero(boolean voglioNumero) {
    this.voglioNumero = voglioNumero;
  }

  public void setTipoMessaggi(EnumFiltroTipologia tipoMessaggi) {
    this.tipoMessaggi = tipoMessaggi;
  }

  private String getCommonFilter() {
    String sql = "";

    if (vcfgrfcittnotifiche.getIdFcitt() != null) {
      sql += " AND ID_FCITT = ? ";
    }

    if (vcfgrfcittnotifiche.getIdComp() != null) {
      sql += " AND ID_COMP = ? ";
    }

    if (tipoMessaggi != null && tipoMessaggi.equals(EnumFiltroTipologia.DA_LEGGERE)) {
      sql += " AND DATA_PRESA_VISIONE IS NULL ";
    } else if (tipoMessaggi != null && tipoMessaggi.equals(EnumFiltroTipologia.LETTI)) {
      sql += " AND DATA_PRESA_VISIONE IS NOT NULL ";
    }
    return sql;
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    log.debug("getSqlRetrieveObjectByWhere: ");
    String sql = "SELECT ";
    if (!voglioNumero) {
      sql += " * ";
    } else {
      sql += " COUNT (*) AS " + ALIAS_COUNT_NUMERO + " ";
    }
    sql += " FROM V_CFG_R_FCITT_NOTIFICHE " + " WHERE 1=1 ";
    sql += getCommonFilter();
    if (!voglioNumero) {
      sql += " ORDER BY ID_NOTIFICA DESC ";
    }
    log.debug("Query generata: " + sql);
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (vcfgrfcittnotifiche.getIdFcitt() != null) {
      st.setLong(index++, vcfgrfcittnotifiche.getIdFcitt());
    }
    if (vcfgrfcittnotifiche.getIdComp() != null) {
      st.setLong(index++, vcfgrfcittnotifiche.getIdComp());
    }
  }

  private Object getFromResultSetNumero(ResultSet r) throws SQLException {
    return r.getLong(ALIAS_COUNT_NUMERO);
  }

  private Object getFromResultSetLista(ResultSet r) throws SQLException {
    VCfgRFcittNotifiche obj = new VCfgRFcittNotifiche();

    obj.setIdNotifica(r.getLong("ID_NOTIFICA"));

    obj.setIdComp(r.getLong("ID_COMP"));

    obj.setIdSez(r.getLong("ID_SEZ"));

    obj.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
    obj.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
    obj.setUriSez(r.getString("URI_SEZ"));
    obj.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
    obj.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
    obj.setUriComp(r.getString("URI_COMP"));
    obj.setDataCatalogazioneComp(
        r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
            ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
            : null);
    obj.setOrdinamento(r.getLong("ORDINAMENTO"));

    obj.setFlagAbilitazioneComp(r.getBoolean("FLAG_ABILITAZIONE_COMP"));
    obj.setTestoNotifica(r.getString("TESTO_NOTIFICA"));
    obj.setUriNotifica(r.getString("URI_NOTIFICA"));
    obj.setDataNotifica(
        r.getTimestamp("DATA_NOTIFICA") != null
            ? r.getTimestamp("DATA_NOTIFICA").toLocalDateTime()
            : null);
    obj.setFlagAbilitazioneNotif(r.getBoolean("FLAG_ABILITAZIONE_NOTIF"));
    obj.setIdFcittNotifiche(r.getBigDecimal("ID_FCITT_NOTIFICHE"));
    obj.setIdFcitt(r.getLong("ID_FCITT"));

    obj.setPersonId(r.getLong("PERSON_ID"));

    obj.setDataPresaVisione(
        r.getTimestamp("DATA_PRESA_VISIONE") != null
            ? r.getTimestamp("DATA_PRESA_VISIONE").toLocalDateTime()
            : null);
    obj.setIdStatoRec(r.getLong("ID_STATO_REC"));
    log.debug("obj: " + obj);
    return obj;
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    log.debug("getFromResultSet: ResultSet " + r != null ? r.toString() : "null");
    if (!voglioNumero) {
      return getFromResultSetLista(r);
    }
    return getFromResultSetNumero(r);
  }
}
