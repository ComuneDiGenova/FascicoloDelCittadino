package it.liguriadigitale.ponmetro.portale.business.ristorazione.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiFiglioExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiGenitoreExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.pojo.portale.StatoAgevolazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiInfoBollettazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiMenuMensa;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPresenzaServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoPagamenti;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.FileAllegato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.IdentificativoPdf;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ServiziRistorazioneService {

  public List<UtenteServiziRistorazione> trovaIscrittiAccettati(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public List<UtenteServiziRistorazione> trovaIscritti(Utente capoFamiglia)
      throws BusinessException, ApiException;

  public UtenteServiziRistorazione trovaIscritto(UtenteServiziRistorazione iscritto)
      throws BusinessException, ApiException;

  public Long contaNumeroPresenze(List<UtenteServiziRistorazione> lista)
      throws BusinessException, ApiException;

  public boolean isIscrizioneAccettata(UtenteServiziRistorazione iscritto);

  public List<DatiPresenzaServiziRistorazione> getGiorniDiPresenzaPerFiglioPerMese(
      UtenteServiziRistorazione figlio, LocalDate day) throws BusinessException, ApiException;

  public DatiMenuMensa getMenuMensa(LocalDate oggi, UtenteServiziRistorazione iscrizione)
      throws BusinessException, ApiException;

  public Boolean statoPagamenti(String cf, String annoScolastico);

  public List<AgevolazioneTariffariaRistorazione> elencoRichiesteAgevolazioni(
      Utente utente, Long anno);

  public List<AgevolazioneTariffariaRistorazione> elencoRichiesteAgevolazioniThread(
      Utente utente, Long anno);

  public List<StatoAgevolazione> elencoRichiesteAgevolazioniConFiglioAnnoPartenza(
      UtenteServiziRistorazione iscritto, Long annoScolasticoPartenza);

  public boolean richiediAgevolazione(AgevolazioneStep2 richiesta, Utente utente)
      throws BusinessException, ApiException;

  public boolean presentaDomandaIscrizione(
      DatiDomandaIscrizioneServiziRistorazione domanda, Utente utente)
      throws BusinessException, ApiException;

  public byte[] getPdfIscrizione(String codicePDF) throws BusinessException, ApiException;

  public byte[] getPdfRichiestaAgevolazione(String codicePDF)
      throws BusinessException, ApiException;

  public String getSituazioneSintenticoPagamenti(String codiceFiscale)
      throws ApiException, BusinessException;

  public List<DatiPagamentiPartitario> getPartitarioIscritto(String codiceFiscale)
      throws ApiException, BusinessException;

  public List<DatiPagamentiPartitario> getPartitarioIscrittoByAnno(String codiceFiscale, Long anno)
      throws ApiException, BusinessException;

  public List<DatiStatoPagamenti> getListaSituazioneAnniPartitario(String codiceFiscale)
      throws ApiException, BusinessException;

  public List<DatiPagamentiBollettiniRiepilogativiEstesi> getBollettiniImpegnato(Utente utente)
      throws ApiException, BusinessException;

  public byte[] getPdfBollettino(String idBollettino) throws BusinessException, ApiException;

  public byte[] getPdfRicevuta(String idDebito) throws BusinessException, ApiException;

  public DatiStatoDomandaChiusuraServiziRistorazione getDatiChiusuraIscritto(String codiceFiscale)
      throws ApiException, BusinessException;

  public boolean presentaDomandaChiusuraIscrizione(DatiDomandaChiusuraServiziRistorazione domanda)
      throws BusinessException, ApiException;

  public DatiStatoDomandaChiusuraServiziRistorazione getDatiStatoDomandaChiusuraServiziRistorazione(
      String codiceFiscale) throws ApiException, BusinessException;

  public List<DatiDietaSpeciale> getDieteSpeciali(String codiceFiscaleIscritto)
      throws ApiException, BusinessException;

  public DatiDietaSpecialeValida getDietaSpecialeValida(String codiceFiscaleIscritto)
      throws ApiException, BusinessException;

  public FileAllegato getFilePdfDieteSpeciali(IdentificativoPdf identificativoPdf)
      throws ApiException, BusinessException;

  public List<DietaMotiviEticoReligiosi> getListaMenuMotiviReligiosi()
      throws ApiException, BusinessException;

  public void richiediDietaSpeciale(DietaSpeciale dietaSpeciale)
      throws ApiException, BusinessException;

  public void revocaDietaSpeciale(
      String codiceFiscaleIscritto,
      String identificativoDomanda,
      String codiceFiscaleRichiedenteRevoca,
      String nomeRichiedenteRevoca,
      String cognomeRichiedenteRevoca,
      String emailRichiedenteRevoca)
      throws BusinessException, ApiException;

  public String getDescrizioneScuolaDaCodiceScuola(String codiceScuola)
      throws BusinessException, ApiException, IOException;

  List<BreadcrumbFdC> getListaBreadcrumbRiepilogo();

  List<BreadcrumbFdC> getListaBreadcrumbBambiniAScuola();

  List<BreadcrumbFdC> getListaBreadcrumbAttestazioniPagamento();

  List<BreadcrumbFdC> getListaBreadcrumbMieiFigli();

  List<BreadcrumbFdC> getListaBreadcrumbPresenzeMensa();

  List<BreadcrumbFdC> getListaBreadcrumbPagamenti();

  List<BreadcrumbFdC> getListaBreadcrumbMovimenti();

  List<BreadcrumbFdC> getListaBreadcrumbVerificaIscrizione();

  List<BreadcrumbFdC> getListaBreadcrumbDieteSpeciali();

  List<BreadcrumbFdC> getListaBreadcrumbRichiestaDieteSpeciali();

  List<BreadcrumbFdC> getListaBreadcrumbVerbaliCommissioniMensa();

  List<BreadcrumbFdC> getListaBreadcrumbStorico();

  List<BreadcrumbFdC> getListaBreadcrumbAgevolazione();

  List<BreadcrumbFdC> getListaBreadcrumbHome();

  List<MessaggiInformativi> popolaListaMessaggi();

  List<AttestazioniDiPagamento> getAttestazioniPagamento(String codiceFiscaleIscritto)
      throws BusinessException, ApiException, IOException;

  FileAllegato getPdfAttestazionePagamento(String codiceFiscaleIscritto, Integer anno)
      throws BusinessException, ApiException, IOException;

  List<DatiDirezioneTerritoriale> getListaDatiDirezioneTerritoriali()
      throws BusinessException, ApiException, IOException;

  List<DatiIstituto> getListaDatiIstituto(String codiceDirezioneTerritoriale)
      throws BusinessException, ApiException, IOException;

  ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(Utente utente, String tipoIndicatore);

  ConsultazioneDichiarazioneCF200 getDichiarazioneISEEModi(
      String codiceFiscale, String tipoIndicatore);

  List<AgevolazioneTariffariaRistorazione> incrociaConIseeModi(
      List<AgevolazioneTariffariaRistorazione> lista,
      List<NucleoFamiliareComponenteNucleoInner> listaFigliComponenteNucleoIseeModi);

  List<AgevolazioneTariffariaRistorazione> setImportoIndicatoreIseeBambino(
      List<AgevolazioneTariffariaRistorazione> listaIncrioConIseeModi);

  List<NucleoFamiliareComponenteNucleoInner> getComponenteNucleoAttestazioneIseeModi(
      AgevolazioneStep1 agevolazioneStep1);

  List<NucleoFamiliareComponenteNucleoInner> getFigliComponenteNucleoAttestazioneIseeModi(
      AgevolazioneStep1 agevolazioneStep1);

  List<NucleoFamiliareComponenteNucleoInner> getListaComponenteNucleoDaDichirazioneIseeModi(
      ConsultazioneDichiarazioneCF200 dichirazioneIseeModi);

  boolean checkCampiNotNullFinoAttestazioneIseeModi(AgevolazioneStep1 step1);

  boolean checkNotNullFinoISEEInOrdinarioIseeModi(AgevolazioneStep1 step1);

  boolean checkNotNullFinoComponenteNucleoInAttestazioneIseeModi(AgevolazioneStep1 step1);

  public List<NucleoFamiliareComponenteNucleoInner> getListaPortatoriDiRedditoConIseeModi(
      AgevolazioneStep2 step2);

  List<Legenda> getListaLegenda();

  List<StepFdC> getListaStepIscrizioneMensaNonResidenti();

  List<AnnoScolastico> getAnniScolastici() throws BusinessException, ApiException, IOException;

  List<Comune> getComuni(String codiceProvincia)
      throws BusinessException, ApiException, IOException;

  List<Provincia> getProvince() throws BusinessException, ApiException, IOException;

  List<Cittadinanza> getCittadinanze() throws BusinessException, ApiException, IOException;

  DatiAnagrafeGenitore getDatiAnagraficiGenitore(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  DatiDipartimentaliBambino getDatiAnagraficiFiglio(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  List<Comune> getComuniAutocomplete(String codiceProvincia, String input)
      throws BusinessException, ApiException, IOException;

  List<Provincia> getProvinceAutocomplete(String input)
      throws BusinessException, ApiException, IOException;

  public List<Cittadinanza> getCittadinanzeAutocomplete(String input)
      throws BusinessException, ApiException, IOException;

  void iscriviBambinoNonResidente(DatiIscrizioneMensaNonResidente datiIscrizioneBambino)
      throws BusinessException, ApiException, IOException;

  Provincia getProvinciaDaCodice(String codiceProvincia);

  Comune getComuneDaCodice(String codiceComune, String codiceProvincia);

  Cittadinanza getCittadinanzaDaCodice(String codiceCittadinanza);

  DatiGenitoreExtend getDatiGenitoreExted(Utente utente, DatiAnagrafeGenitore datiGenitore);

  DatiFiglioExtend getDatiFiglioExted(
      String codiceFiscaleIscritto,
      String nomeIscritto,
      String cognomeIscritto,
      LocalDate dataNascitaIscritto,
      DatiDipartimentaliBambino datiFiglio);

  DatiInfoBollettazione getDatiInfoBollettazione(String codiceFiscaleGenitore)
      throws BusinessException, ApiException, IOException;
}
