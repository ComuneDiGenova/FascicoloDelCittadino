package it.liguriadigitale.ponmetro.api.business.livello1.rest.api;

import it.liguriadigitale.ponmetro.demografico.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.ListaCarteInScadenza;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.demografico.model.SchedaAnagrafica;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaFigli;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaGenitori;
import it.liguriadigitale.ponmetro.demografico.model.TesseraElettorale;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApiServiziDemograficoImpl implements PortaleApi {

  private static Log log = LogFactory.getLog(ApiServiziDemograficoImpl.class);

  /* https://www.baeldung.com/resteasy-client-tutorial */

  PortaleApi istance;

  public ApiServiziDemograficoImpl(PortaleApi createDemograficoApi) {
    istance = createDemograficoApi;
  }

  @Override
  public Residente demograficoResidenteCodiceFiscaleGet(String arg0) {
    return istance.demograficoResidenteCodiceFiscaleGet(arg0);
  }

  @Override
  public SchedaAnagrafica demograficoSchedaAnagraficaCodiceFiscaleGet(String arg0) {
    return istance.demograficoSchedaAnagraficaCodiceFiscaleGet(arg0);
  }

  @Override
  public TesseraElettorale demograficoTesseraElettoraleCodiceFiscaleGet(String arg0) {
    return istance.demograficoTesseraElettoraleCodiceFiscaleGet(arg0);
  }

  @Override
  public DatiCatastali oggettiDatiCatastaliCodiceIndirizzoGet(String arg0) {
    return istance.oggettiDatiCatastaliCodiceIndirizzoGet(arg0);
  }

  @Override
  public StrutturaGenitori oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(String arg0) {
    log.debug(
        "[ApiServiziDemograficoImpl] oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet-- inizio");
    return istance.oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(arg0);
  }

  @Override
  public StrutturaFigli oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(String arg0) {
    log.debug(
        "[ApiServiziDemograficoImpl] oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet-- inizio");
    return istance.oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(arg0);
  }

  @Override
  public ListaCarteInScadenza getScadenze(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<ResidenteTari> tariResidentiCodiceFiscaleGet(String arg0) {
    // Non utilizzato nel backend
    throw new NotImplementedException();
  }
}
