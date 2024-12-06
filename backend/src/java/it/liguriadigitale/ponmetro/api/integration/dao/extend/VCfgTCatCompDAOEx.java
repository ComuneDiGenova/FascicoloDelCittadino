package it.liguriadigitale.ponmetro.api.integration.dao.extend;

import it.liguriadigitale.ponmetro.api.integration.dao.VCfgTCatCompDAO;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.extend.CfgTCatSezEx;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VCfgTCatCompDAOEx extends VCfgTCatCompDAO {

  private static Log log = LogFactory.getLog(VCfgTCatCompDAOEx.class);

  public VCfgTCatCompDAOEx(VCfgTCatComp vcfgtcatcomp) {
    super(vcfgtcatcomp);
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    Map<Long, CfgTCatSezEx> mappaSezioni = new HashMap<>();
    int rowCount = 0;
    if (r.last()) {
      rowCount = r.getRow();
      r.beforeFirst();
    }
    log.debug(" numero righe: " + rowCount);
    log.debug("resul set: " + r);
    while (r.next()) {

      CfgTCatComp objComparto = new CfgTCatComp();
      objComparto.setIdComp(r.getLong("ID_COMP"));
      objComparto.setOrdinamento(r.getLong("ORDINAMENTO_C"));
      objComparto.setDenominazioneComp(r.getString("DENOMINAZIONE_COMP"));
      objComparto.setDescrizioneComp(r.getString("DESCRIZIONE_COMP"));
      objComparto.setUriComp(r.getString("URI_COMP"));
      objComparto.setDataCatalogazioneComp(
          r.getTimestamp("DATA_CATALOGAZIONE_COMP") != null
              ? r.getTimestamp("DATA_CATALOGAZIONE_COMP").toLocalDateTime()
              : null);
      objComparto.setDataInvioMsg(
          r.getTimestamp("DATA_INVIO_MSG") != null
              ? r.getTimestamp("DATA_INVIO_MSG").toLocalDateTime()
              : null);
      objComparto.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE_COMP"));

      Long sezioneKey = r.getLong("ID_SEZ");

      log.debug("mappaSezioni: " + mappaSezioni);

      CfgTCatSezEx objSezione = mappaSezioni.get(sezioneKey);
      if (objSezione == null) {
        log.debug("sezione non trovata: " + r);
        objSezione = new CfgTCatSezEx();
        objSezione.setIdSez(sezioneKey);
        objSezione.setDenominazioneSez(r.getString("DENOMINAZIONE_SEZ"));
        objSezione.setDescrizioneSez(r.getString("DESCRIZIONE_SEZ"));
        objSezione.setDataCatalogazioneSez(
            r.getTimestamp("DATA_CATALOGAZIONE_SEZ") != null
                ? r.getTimestamp("DATA_CATALOGAZIONE_SEZ").toLocalDateTime()
                : null);
        objSezione.setUriSez(r.getString("URI_SEZ"));
        objSezione.setOrdinamento(r.getLong("ORDINAMENTO_S"));
        objSezione.setFlagAbilitazione(r.getBoolean("FLAG_ABILITAZIONE_SEZ"));
        mappaSezioni.put(sezioneKey, objSezione);
      }

      objSezione.addToListaComparti(objComparto);
    }

    return mappaSezioni;
  }
}
