package it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.ristorazione;

import it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafici;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazioneNonResidente;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiInfoBollettazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiMenuMensa;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiBollettiniRiepilogativi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitarioEstesi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiSintetico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPresenzaServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiResidenziali;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiRevocaRichiestaDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiSimulazioneTariffaria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoAgevolazioneTariffaria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DomandaInviata;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DomandaProtocollata;
import it.liguriadigitale.ponmetro.serviziristorazione.model.FileAllegato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApiServiziRistorazionePortaleImpl
    implements it.liguriadigitale.ponmetro.serviziristorazione.apiclient.PortaleApi {

  private Log log = LogFactory.getLog(getClass());

  /* https://www.baeldung.com/resteasy-client-tutorial */

  PortaleApi instance;

  public ApiServiziRistorazionePortaleImpl(PortaleApi createRistorazioneApi) {
    instance = createRistorazioneApi;
    log.debug("ApiServiziRistorazionePortaleImpl ");
  }

  @Override
  public DatiAnagrafici getAnagraficaRistorazione(String arg0) {
    return instance.getAnagraficaRistorazione(arg0);
  }

  @Override
  public List<AnnoScolastico> getAnniScolastici() {
    return instance.getAnniScolastici();
  }

  @Override
  public List<AttestazioniDiPagamento> getAttestazioniDiPagamento(String arg0) {
    return instance.getAttestazioniDiPagamento(arg0);
  }

  @Override
  public FileAllegato getAttestazioniDiPagamentoPdf(String arg0, Integer arg1) {
    return instance.getAttestazioniDiPagamentoPdf(arg0, arg1);
  }

  @Override
  public String getBolletinoPdf(String arg0) {
    return instance.getBolletinoPdf(arg0);
  }

  @Override
  public List<DatiPagamentiBollettiniRiepilogativi> getBollettini(String arg0) {
    return instance.getBollettini(arg0);
  }

  @Override
  public DatiPagamentiSintetico getBollettiniSintetico(String arg0) {
    return instance.getBollettiniSintetico(arg0);
  }

  @Override
  public List<Cittadinanza> getCittadinanze() {
    return instance.getCittadinanze();
  }

  @Override
  public List<Comune> getComuni(String arg0) {
    return instance.getComuni(arg0);
  }

  @Override
  public DatiDipartimentaliBambino getDatiAnagraficiFiglio(String arg0) {
    return instance.getDatiAnagraficiFiglio(arg0);
  }

  @Override
  public DatiAnagrafeGenitore getDatiAnagraficiGenitore(String arg0) {
    return instance.getDatiAnagraficiGenitore(arg0);
  }

  @Override
  public List<DatiDietaSpeciale> getDieteSpeciali(String arg0) {
    return instance.getDieteSpeciali(arg0);
  }

  @Override
  public FileAllegato getDieteSpecialiPdf(String arg0, String arg1) {
    return instance.getDieteSpecialiPdf(arg0, arg1);
  }

  @Override
  public List<DatiDirezioneTerritoriale> getDirezioniTerritoriali() {
    return instance.getDirezioniTerritoriali();
  }

  @Override
  public DatiStatoDomandaChiusuraServiziRistorazione getDomandaChiusuraIscrizioneStato(
      String arg0) {
    return instance.getDomandaChiusuraIscrizioneStato(arg0);
  }

  @Override
  public String getDomandaChiusuraIscrizioneStatoPdf(String arg0) {
    return instance.getDomandaChiusuraIscrizioneStatoPdf(arg0);
  }

  @Override
  public DatiAgevolazioneTariffaria getDomandeAgevolazioneTariffaria(
      String arg0, String arg1, String arg2) {
    return instance.getDomandeAgevolazioneTariffaria(arg0, arg1, arg2);
  }

  @Override
  public DatiStatoAgevolazioneTariffaria getDomandeAgevolazioneTariffariaStato(
      String arg0, String arg1) {
    return instance.getDomandeAgevolazioneTariffariaStato(arg0, arg1);
  }

  @Override
  public String getDomandeAgevolazioneTariffariaStatoPdf(String arg0) {
    return instance.getDomandeAgevolazioneTariffariaStatoPdf(arg0);
  }

  @Override
  public String getDomandeIscrizioneServiziRistorazioneStatoPdf(String arg0) {
    return instance.getDomandeIscrizioneServiziRistorazioneStatoPdf(arg0);
  }

  @Override
  public DatiIscrizioneServiziRistorazione getIscrizioneRistorazione(String arg0) {
    return instance.getIscrizioneRistorazione(arg0);
  }

  @Override
  public List<DatiIstituto> getIstitutiScolastici(String arg0) {
    return instance.getIstitutiScolastici(arg0);
  }

  @Override
  public List<DietaMotiviEticoReligiosi> getMenuMotiviEticoReligiosi() {
    return instance.getMenuMotiviEticoReligiosi();
  }

  @Override
  public List<DatiMenuMensa> getMenuServiziRistorazione(
      String arg0, LocalDate arg1, LocalDate arg2, String arg3) {
    // FRR JIRA FASCITT-129, embed "-"
    return instance.getMenuServiziRistorazione("-", arg1, arg2, arg3);
  }

  @Override
  public DatiPagamentiPartitarioEstesi getPartitario(String arg0) {
    return instance.getPartitario(arg0);
  }

  @Override
  public List<DatiPresenzaServiziRistorazione> getPresenzaMensa(
      String arg0, LocalDate arg1, LocalDate arg2) {
    return instance.getPresenzaMensa(arg0, arg1, arg2);
  }

  @Override
  public List<Provincia> getProvince() {
    return instance.getProvince();
  }

  @Override
  public DatiResidenziali getResidenzaRistorazione(String arg0) {
    return instance.getResidenzaRistorazione(arg0);
  }

  @Override
  public DatiSimulazioneTariffaria getRichiestaSimulazioneTariffaria(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      String arg4,
      String arg5,
      String arg6,
      String arg7) {
    return instance.getRichiestaSimulazioneTariffaria(
        arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
  }

  @Override
  public UtenteServiziRistorazione getUtenteRistorazione(String arg0) {
    return instance.getUtenteRistorazione(arg0);
  }

  @Override
  public void postDomandaChiusuraIscrizione(DatiDomandaChiusuraServiziRistorazione arg0) {
    instance.postDomandaChiusuraIscrizione(arg0);
  }

  @Override
  public void postRevocaDieta(DatiRevocaRichiestaDietaSpeciale arg0) {
    instance.postRevocaDieta(arg0);
  }

  @Override
  public void postRichiestaDietaSpeciale(DatiDomandaRichiestaDietaSpeciale arg0) {
    instance.postRichiestaDietaSpeciale(arg0);
  }

  @Override
  public DatiDietaSpecialeValida getDietaSpecialeValida(String arg0) {
    return instance.getDietaSpecialeValida(arg0);
  }

  @Override
  public DomandaProtocollata getDatiProtocollo(String arg0, BigDecimal arg1) {
    return instance.getDatiProtocollo(arg0, arg1);
  }

  @Override
  public FileAllegato getDocumentoProtocollo(BigDecimal arg0) {
    return instance.getDocumentoProtocollo(arg0);
  }

  @Override
  public List<DatiIstituto> getIstitutiScolasticiManutenzion(String arg0) {
    return instance.getIstitutiScolasticiManutenzion(arg0);
  }

  @Override
  public DomandaInviata postDomandaIscrizione(DatiDomandaIscrizioneServiziRistorazione arg0) {
    return instance.postDomandaIscrizione(arg0);
  }

  @Override
  public DomandaInviata postDomandaIscrizioneNonResidenti(
      DatiDomandaIscrizioneServiziRistorazioneNonResidente arg0) {
    return instance.postDomandaIscrizioneNonResidenti(arg0);
  }

  @Override
  public DomandaInviata postDomandeAgevolazioneTariffaria(DatiAgevolazioneTariffaria arg0) {
    return instance.postDomandeAgevolazioneTariffaria(arg0);
  }

  @Override
  public DatiInfoBollettazione getDatiInfoBollettazione(String arg0) {
    return instance.getDatiInfoBollettazione(arg0);
  }

  @Override
  public void putDatiInfoBollettazione(String arg0, DatiInfoBollettazione arg1) {
    instance.putDatiInfoBollettazione(arg0, arg1);
  }
}
