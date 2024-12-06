package it.liguriadigitale.ponmetro.portale.business.graphql.impl.demografico;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApiServiziDemograficoImpl implements PortaleApi {

  @SuppressWarnings("unused")
  private static Log log = LogFactory.getLog(ApiServiziDemograficoImpl.class);

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
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public TesseraElettorale demograficoTesseraElettoraleCodiceFiscaleGet(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public DatiCatastali oggettiDatiCatastaliCodiceIndirizzoGet(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public StrutturaGenitori oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public StrutturaFigli oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public ListaCarteInScadenza getScadenze(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<ResidenteTari> tariResidentiCodiceFiscaleGet(String arg0) {
    throw new java.lang.UnsupportedOperationException("Not supported yet.");
  }
}
