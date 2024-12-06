package it.liguriadigitale.ponmetro.portale.business.borsedistudio.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.borsestudio.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.borsestudio.model.Annualita;
import it.liguriadigitale.ponmetro.borsestudio.model.Bambino;
import it.liguriadigitale.ponmetro.borsestudio.model.BorsaStudioIBAN;
import it.liguriadigitale.ponmetro.borsestudio.model.Categoria;
import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import it.liguriadigitale.ponmetro.borsestudio.model.FileAllegato;
import it.liguriadigitale.ponmetro.borsestudio.model.Parentela;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.StatoPraticaEnum;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.borsestudio.model.Scuola;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.ComponenteMinorenne;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.borsedistudio.service.ServiziBorseDiStudioService;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoBorseStudio;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziBorseDiStudioImpl implements ServiziBorseDiStudioService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_BORSE_STUDIO =
      "Errore di connessione alle API Borse Di Studio";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbBorseDiStudio() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(new BreadcrumbFdC("borseDiStudio", "Borse di Studio"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbBorseDiStudioDomanda() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioRichiedo", "io Richiedo"));
    listaBreadcrumb.add(new BreadcrumbFdC("borseDiStudio", "Borse di Studio"));
    listaBreadcrumb.add(new BreadcrumbFdC("borseDiStudioDomanda", "Domanda"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiBorseDiStudio()
      throws BusinessException, ApiException, IOException {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    Annualita annualita = validitaDomandaBorseDiStudio();

    String dataInizio = "";
    String dataFine = "";
    String aperto = "";
    String stato = "";
    String riferimento = "";

    if (LabelFdCUtil.checkIfNotNull(annualita)) {

      if (LabelFdCUtil.checkIfNotNull(annualita.getDataInizio())) {
        // dataInizio = LocalDateUtil.getStringOffsetDateTime(annualita.getDataInizio());

        dataInizio = LocalDateUtil.getDataOraCorretteInItalia(annualita.getDataInizio());
      }

      if (LabelFdCUtil.checkIfNotNull(annualita.getDataFine())) {
        // dataFine = LocalDateUtil.getStringOffsetDateTime(annualita.getDataFine());

        dataFine = LocalDateUtil.getDataOraCorretteInItalia(annualita.getDataFine());
      }

      if (LabelFdCUtil.checkIfNotNull(annualita.getAperto())) {
        if (annualita.getAperto()) {
          aperto = "Sì";
        } else {
          aperto = "No";
        }
      }

      if (LabelFdCUtil.checkIfNotNull(annualita.getStato())) {
        stato = annualita.getStato();
      }

      if (LabelFdCUtil.checkIfNotNull(annualita.getRiferimentiProvvedimento())) {
        riferimento = annualita.getRiferimentiProvvedimento();
      }
    }

    MessaggiInformativi messaggio1 = new MessaggiInformativi();

    String messaggio =
        "La borsa di studio è concessa al nucleo familiare per ogni figlio frequentante le scuole statali e paritarie medie e superiori nell'anno scolastico di riferimento.<br>La borsa di studio viene erogata sulla base delle spese sostenute dalle famiglie per l’acquisto di libri di testo, dizionari ed atlanti. Le spese devono essere autocertificate e giustificate da idonea documentazione valida ai fini fiscali in possesso del richiedente, anche nel caso di acquisti effettuati on line. In quest’ultimo caso si ricorda che i modelli “ordine” o “consegna” non sono documenti validi ai fini fiscali.<br><ul>";
    messaggio = messaggio.concat("<li>");
    if (LabelFdCUtil.checkIfNotNull(dataInizio)) {
      messaggio = messaggio.concat("Le domande possono essere presentate dal ").concat(dataInizio);
    }
    if (LabelFdCUtil.checkIfNotNull(dataFine)) {
      messaggio = messaggio.concat(" al ").concat(dataFine);
    }
    messaggio = messaggio.concat("</li>");
    if (PageUtil.isStringValid(aperto)) {
      messaggio = messaggio.concat("<li>Domanda presentabile: ").concat(aperto).concat("</li>");
    }
    if (PageUtil.isStringValid(stato)) {
      messaggio = messaggio.concat("<li>Stato: ").concat(stato).concat("</li>");
    }
    if (PageUtil.isStringValid(riferimento)) {
      messaggio = messaggio.concat("<li>Riferimento: ").concat(riferimento).concat("</li>");
    }
    messaggio = messaggio.concat("</ul>");
    messaggio = messaggio.concat("<br>");

    String urlSito = getUrlSitoBorseDiStudio();

    messaggio =
        messaggio.concat(
            "Per maggiori informazioni vai sul <a href="
                + urlSito
                + " target=\"_blank\">sito del Comune di Genova</a>");

    messaggio1.setMessaggio(messaggio);

    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<Pratica> listaPraticheBorseDiStudio(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP listaPraticheBorseDiStudio: " + codiceFiscale);
    ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti instance =
        ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti.getInstance();
    try {
      List<Pratica> listaPratiche = instance.getApiBorseDiStudio().getPratiche(codiceFiscale);

      List<Pratica> listaPraticheOrdinataPerIdentificativoDomandaOnline = new ArrayList<>();

      if (LabelFdCUtil.checkIfNotNull(listaPratiche)
          && !LabelFdCUtil.checkEmptyList(listaPratiche)) {
        listaPraticheOrdinataPerIdentificativoDomandaOnline =
            listaPratiche.stream()
                .sorted(Comparator.comparing(Pratica::getIdentificativoDomandaOnline).reversed())
                .collect(Collectors.toList());
      }

      return listaPraticheOrdinataPerIdentificativoDomandaOnline;

    } catch (BusinessException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- listaPraticheBorseDiStudio: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- listaPraticheBorseDiStudio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- listaPraticheBorseDiStudio: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Annualita validitaDomandaBorseDiStudio()
      throws BusinessException, ApiException, IOException {
    log.debug("CP validitaDomandaBorseDiStudio");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getAnnualita();
    } catch (BusinessException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- validitaDomandaBorseDiStudio: errore API BORSE DI STUDIO:",
          e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- validitaDomandaBorseDiStudio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- validitaDomandaBorseDiStudio: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Beneficiario", 1));
    listaStep.add(new StepFdC("Richiedente", 2));
    listaStep.add(new StepFdC("Dati", 3));
    listaStep.add(new StepFdC("Dichiarazioni", 4));
    listaStep.add(new StepFdC("Riepilogo", 5));
    listaStep.add(new StepFdC("Esito", 6));

    return listaStep;
  }

  @Override
  public List<AnnoScolastico> getAnniScolastici()
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAnniScolastici: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getAnniScolastici();
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getAnniScolastici: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getAnniScolastici: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getAnniScolastici: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Parentela> getParentele() throws BusinessException, ApiException, IOException {
    log.debug("CP getParentele: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getParentele();
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getParentele: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getParentele: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getParentele: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Categoria> getCategorie() throws BusinessException, ApiException, IOException {
    log.debug("CP getCategorie: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getCategorie();
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getCategorie: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getCategorie: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getCategorie: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Scuola> getScuole(String codiceCategoria)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getScuole: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getScuole(codiceCategoria);
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getCategorie: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getCategorie: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getCategorie: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Bambino getBambino(BigDecimal codiceAnnoScolastico, String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getBambino: " + codiceAnnoScolastico + " - " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getBambino(codiceAnnoScolastico, codiceFiscale);
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getBambino: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getBambino: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getBambino: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void richiediBorsaStudio(PraticaBorseStudioExtend praticaEstesa)
      throws BusinessException, ApiException, IOException {
    log.debug("CP richiediBorsaStudio: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      Pratica pratica = new Pratica();

      if (LabelFdCUtil.checkIfNotNull(praticaEstesa)) {

        log.debug("CP praticaEstesa = " + praticaEstesa);

        pratica.setNomeRichiedente(praticaEstesa.getNomeRichiedente());
        pratica.setCognomeRichiedente(praticaEstesa.getCognomeRichiedente());
        pratica.setCodiceFiscaleRichiedente(praticaEstesa.getCodiceFiscaleRichiedente());
        pratica.setNomeIntestatarioBorsa(praticaEstesa.getNomeIntestatarioBorsa());
        pratica.setCognomeIntestatarioBorsa(praticaEstesa.getCognomeIntestatarioBorsa());
        pratica.setCodiceIntestatarioBorsa(praticaEstesa.getCodiceIntestatarioBorsa());
        pratica.setScuolaModificata(praticaEstesa.getScuolaModificata());
        pratica.setDataRichiesta(LocalDate.now());
        pratica.setAnnoScolastico(praticaEstesa.getAnnoScolastico());

        Float importoRichiesto = praticaEstesa.getImportoEuro().floatValue();
        pratica.setImportoRichiesto(importoRichiesto);

        pratica.setDataPresentazioneIsee(praticaEstesa.getDataPresentazioneIsee());
        pratica.setDataValiditaIsee(praticaEstesa.getDataValiditaIsee());
        pratica.setImportoIsee(praticaEstesa.getImportoIsee());
        pratica.setNumeroProtocolloIsee(praticaEstesa.getNumeroProtocolloIsee());
        pratica.setAnnoProtocolloIsee(praticaEstesa.getAnnoProtocolloIsee());
        pratica.setEmail(praticaEstesa.getEmail());
        pratica.setCellulare(praticaEstesa.getCellulare());
        pratica.setParentela(praticaEstesa.getParentela());
        pratica.setNumeroFigliACarico(praticaEstesa.getNumeroFigliACarico());
        pratica.setNumeroPersoneDisabili(praticaEstesa.getNumeroPersoneDisabili());
        pratica.setFileAllegatoVittimaIn(praticaEstesa.getFileAllegatoVittimaIn());
        pratica.setFileScontriniIn(praticaEstesa.getFileScontriniIn());
        pratica.setCategoria(praticaEstesa.getCategoria());
        pratica.setScuola(praticaEstesa.getScuola());

        if (LabelFdCUtil.checkIfNotNull(praticaEstesa.getClasseNumero())) {
          pratica.setClasse(String.valueOf(praticaEstesa.getClasseNumero()));
        }

        pratica.setSezione(praticaEstesa.getSezione());
        pratica.setIban(praticaEstesa.getIban());
        pratica.setImporto(praticaEstesa.getImporto());
        pratica.setFiglioVittimaLavoro(praticaEstesa.getFiglioVittimaLavoro());
        pratica.setIseeDifforme(praticaEstesa.getIseeDifforme());
        pratica.setDichiarazioneSpesaFiscale(praticaEstesa.getDichiarazioneSpesaFiscale());
        pratica.setDichiarazioneDiEssereAConoscenza(
            praticaEstesa.getDichiarazioneDiEssereAConoscenza());
        pratica.setAccettazioneNucleoIseeAnagrafico(praticaEstesa.getAccettazioneNuclei());

        pratica.setProvinciaResidenza(praticaEstesa.getProvinciaResidenza());
        pratica.setComuneResidenza(praticaEstesa.getComuneResidenza());

        pratica.setViaResidenza(praticaEstesa.getViaResidenza());
        pratica.setCivicoResidenza(praticaEstesa.getCivicoResidenza());
        pratica.setInternoResidenza(praticaEstesa.getInternoResidenza());
        pratica.setCapResidenza(praticaEstesa.getCapResidenza());
        pratica.setCodiceViaResidenza(praticaEstesa.getCodiceViaResidenza());

        pratica.setViaDomicilioNoGenova(praticaEstesa.getViaDomicilioNoGenova());
        pratica.setCivicoDomicilioNoGenova(praticaEstesa.getCivicoDomicilioNoGenova());
        pratica.setInternoDomicilioNoGenova(praticaEstesa.getInternoDomicilioNoGenova());
        pratica.setCapDomicilioNoGenova(praticaEstesa.getCapDomicilioNoGenova());

        pratica.setProvinciaDomicilio(praticaEstesa.getProvinciaDomicilio());
        pratica.setComuneDomicilio(praticaEstesa.getComuneDomicilio());

        //				if (LabelFdCUtil.checkIfNotNull(praticaEstesa.getGeoserver())) {
        //					pratica.setViaDomicilioGenova(praticaEstesa.getGeoserver().getDESVIA());
        //					pratica.setCivicoDomicilioGenova(praticaEstesa.getGeoserver().getNUMERO());
        //					pratica.setCodiceViaDomicilioGenova(praticaEstesa.getGeoserver().getCOD_STRADA());
        //				} else {
        //					pratica.setViaDomicilioGenova(praticaEstesa.getViaDomicilioGenova());
        //					pratica.setCivicoDomicilioGenova(praticaEstesa.getCivicoDomicilioGenova());
        //					pratica.setCodiceViaDomicilioGenova(praticaEstesa.getCodiceViaDomicilioGenova());
        //				}

        if (LabelFdCUtil.checkIfNotNull(praticaEstesa.getAutocompleteViario())) {
          pratica.setViaDomicilioGenova(praticaEstesa.getAutocompleteViario().getDESVIA());
          pratica.setCivicoDomicilioGenova(praticaEstesa.getAutocompleteViario().getNUMERO());
          pratica.setCodiceViaDomicilioGenova(
              praticaEstesa.getAutocompleteViario().getCOD_STRADA());
        } else {
          pratica.setViaDomicilioGenova(praticaEstesa.getViaDomicilioGenova());
          pratica.setCivicoDomicilioGenova(praticaEstesa.getCivicoDomicilioGenova());
          pratica.setCodiceViaDomicilioGenova(praticaEstesa.getCodiceViaDomicilioGenova());
        }

        pratica.setCapDomicilioGenova(praticaEstesa.getCapDomicilioGenova());
        pratica.setInternoDomicilioGenova(praticaEstesa.getInternoDomicilioGenova());
      }

      log.debug("CP prima di richiedi borsa = " + pratica);

      instance
          .getApiBorseDiStudio()
          .setPraticaBorseDiStudio(praticaEstesa.getCodiceFiscaleRichiedente(), pratica);

    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- richiediBorsaStudio: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- richiediBorsaStudio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- richiediBorsaStudio: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    }
  }

  @Override
  public List<Provincia> getPronvice() throws BusinessException, ApiException, IOException {
    log.debug("CP getPronvice: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getProvince();
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getPronvice: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getPronvice: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getPronvice: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Comune> getComuni(String codiceProvincia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getComuni: " + codiceProvincia);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiBorseDiStudio().getComuni(codiceProvincia);
    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getComuni: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getComuni: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getComuni: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Provincia> getProvinciaSelect2(String input)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getProvinciaSelect2 = " + input);

    String inputToUpper = input.toUpperCase();

    List<Provincia> listaTutteProvince = getPronvice();

    List<Provincia> listaProvinceFiltrate = new ArrayList<Provincia>();

    listaProvinceFiltrate =
        listaTutteProvince.stream()
            .filter(
                elem ->
                    LabelFdCUtil.checkIfNotNull(elem)
                        && LabelFdCUtil.checkIfNotNull(elem.getDescrizione())
                        && elem.getDescrizione().contains(inputToUpper))
            .collect(Collectors.toList());

    return listaProvinceFiltrate;
  }

  @Override
  public List<Comune> getComuniSelect2(String codiceProvincia, String input)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getComuniSelect2 = " + codiceProvincia + " - " + input);

    String inputToUpper = input.toUpperCase();

    List<Comune> listaTuttiComuniDellaProvincia = getComuni(codiceProvincia);

    List<Comune> listaComuniFiltrati = new ArrayList<Comune>();

    listaComuniFiltrati =
        listaTuttiComuniDellaProvincia.stream()
            .filter(
                elem ->
                    LabelFdCUtil.checkIfNotNull(elem)
                        && LabelFdCUtil.checkIfNotNull(elem.getDescrizione())
                        && elem.getDescrizione().contains(inputToUpper))
            .collect(Collectors.toList());

    return listaComuniFiltrati;
  }

  @Override
  public Categoria getCategoria(String codice) throws BusinessException, ApiException, IOException {
    log.debug("CP getCategoria");

    Categoria categoria = new Categoria();

    List<Categoria> listaCategoria = getCategorie();

    categoria =
        listaCategoria.stream()
            .filter(elem -> elem.getCodice().equalsIgnoreCase(codice))
            .findAny()
            .orElse(new Categoria());

    return categoria;
  }

  @Override
  public Scuola getScuola(String codiceScuola, String codiceCategoria)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getScuola");

    Scuola scuola = new Scuola();

    List<Scuola> listaScuole = getScuole(codiceCategoria);

    scuola =
        listaScuole.stream()
            .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceScuola))
            .findAny()
            .orElse(new Scuola());

    return scuola;
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto(StatoPraticaEnum.ACCETTATA.value());
    legenda1.setStile("badge bg-success");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto(StatoPraticaEnum.IN_ELABORAZIONE.value());
    legenda2.setStile("badge bg-warning");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto(StatoPraticaEnum.RIFIUTATA.value());
    legenda3.setStile("badge bg-secondary");
    listaLegenda.add(legenda3);

    Legenda legenda4 = new Legenda();
    legenda4.setTesto(StatoPraticaEnum.INCOMPLETA.value());
    legenda4.setStile("badge bg-secondary");
    listaLegenda.add(legenda4);

    return listaLegenda;
  }

  @Override
  public String getDescrizioneProvinciaDaCodice(String codiceProvincia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDescrizioneProvinciaDaCodice: " + codiceProvincia);

    String descrizione = "";

    List<Provincia> listaProvince = getPronvice();

    if (LabelFdCUtil.checkIfNotNull(listaProvince) && !LabelFdCUtil.checkEmptyList(listaProvince)) {
      Provincia provincia =
          listaProvince.stream()
              .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceProvincia))
              .findAny()
              .orElse(new Provincia());

      if (LabelFdCUtil.checkIfNotNull(provincia)) {
        descrizione = provincia.getDescrizione();
      }
    }

    return descrizione;
  }

  @Override
  public String getDescrizioneComuneDaCodice(String codiceProvincia, String codiceComune)
      throws BusinessException, ApiException, IOException {
    log.debug(
        "CP getDescrizioneComuneDaCodice: provincia = "
            + codiceProvincia
            + " - comune = "
            + codiceComune);

    String descrizione = "";

    if (PageUtil.isStringValid(codiceProvincia)) {
      List<Comune> listaComune = getComuni(codiceProvincia);

      if (LabelFdCUtil.checkIfNotNull(listaComune) && !LabelFdCUtil.checkEmptyList(listaComune)) {
        Comune comune =
            listaComune.stream()
                .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceComune))
                .findAny()
                .orElse(new Comune());

        if (LabelFdCUtil.checkIfNotNull(comune)) {
          descrizione = comune.getDescrizione();
        }
      }
    }

    return descrizione;
  }

  @Override
  public FileAllegato getDocumentoBorse(String tipoFile, BigDecimal identificativoDomanda)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDocumentoBorse: " + tipoFile);
    ServiceLocatorLivelloUnoBorseStudio instance =
        ServiceLocatorLivelloUnoBorseStudio.getInstance();
    try {

      return instance
          .getApiBorseDiStudio()
          .getDocumentoBorseDiStudio(tipoFile, identificativoDomanda);

    } catch (BusinessException e) {
      log.error("ServiziBorseDiStudioImpl -- getDocumentoBorse: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getDocumentoBorse: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } /*catch (RuntimeException e) {
      	log.error(
      			"ServiziBorseDiStudioImpl -- getDocumentoBorse: errore durante la chiamata delle API BORSE DI STUDIO ",
      			e);
      	throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
      }*/ finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private String getUrlBandoBorseDiStudio() {
    log.debug("CP getUrlBandoBorseDiStudio");

    String chiave = "URL_BORSE_STUDIO_BANDO";
    String urlBando = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        urlBando = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante getUrlBandoBorseDiStudio da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return urlBando;
  }

  private String getUrlSitoBorseDiStudio() {
    log.debug("CP getUrlSitoBorseDiStudio");

    String chiave = "URL_BORSE_STUDIO_SITO";
    String urlBando = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        urlBando = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante getUrlSitoBorseDiStudio da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return urlBando;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiDichiarazioniBorseDiStudio() {

    List<MessaggiInformativi> listaMessaggi = new ArrayList<MessaggiInformativi>();

    String urlBando = getUrlBandoBorseDiStudio();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();

    String messaggio =
        "Dichiaro di aver preso conoscenza del bando a questo <a href="
            + urlBando
            + " target=\"_blank\">link</a>.";

    messaggio1.setMessaggio(messaggio);

    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiDichiarazioniBorseDiStudioConLink() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<MessaggiInformativi>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();

    String messaggio = "Dichiaro di aver preso conoscenza del bando a questo link";
    messaggio1.setMessaggio(messaggio);

    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public List<BorsaStudioIBAN> getListaBorseStudioIban(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getListaBorseStudioIban: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      List<BorsaStudioIBAN> listaPraticheIban =
          instance.getApiBorseDiStudio().getBorseStudioIban(codiceFiscale);

      List<BorsaStudioIBAN> listaPraticheIbanOrdinataPerIdentificativoDomandaOnline =
          new ArrayList<>();

      if (LabelFdCUtil.checkIfNotNull(listaPraticheIban)
          && !LabelFdCUtil.checkEmptyList(listaPraticheIban)) {
        listaPraticheIbanOrdinataPerIdentificativoDomandaOnline =
            listaPraticheIban.stream()
                .sorted(Comparator.comparing(BorsaStudioIBAN::getIdDomandaOnline).reversed())
                .collect(Collectors.toList());
      }

      return listaPraticheIbanOrdinataPerIdentificativoDomandaOnline;

    } catch (BusinessException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getListaBorseStudioIban: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getListaBorseStudioIban: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- getListaBorseStudioIban: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void putModificaIbanBorsaStudio(String codiceFiscale, BorsaStudioIBAN datiBorsaStudio)
      throws BusinessException, ApiException, IOException {
    log.debug("CP putModificaIbanBorsaStudio: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      instance.getApiBorseDiStudio().setBorseStudioIban(codiceFiscale, datiBorsaStudio);

    } catch (BusinessException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- putModificaIbanBorsaStudio: errore API BORSE DI STUDIO:", e);
      throw new BusinessException(ERRORE_API_BORSE_STUDIO);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- putModificaIbanBorsaStudio: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziBorseDiStudioImpl -- putModificaIbanBorsaStudio: errore durante la chiamata delle API BORSE DI STUDIO ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Borse Di Studio"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public void setDatiIseePerIntestatarioBorsa(
      Residente intestatarioBorsa, PraticaBorseStudioExtend datiDomanda)
      throws BusinessException, ApiException, IOException {
    log.debug("CP setDatiIseePerIntestatarioBorsa: ");

    try {

      String tipoIndicatore = "";
      String tipoIndicatoreOrdinario = "Ordinario";
      String tipoIndicatoreMinorenne = "Minorenne";

      int etaIntestatario = LocalDateUtil.calcolaEta(intestatarioBorsa.getCpvDateOfBirth());
      log.debug("CP etaIntestatario = " + etaIntestatario);
      tipoIndicatore = etaIntestatario < 18 ? tipoIndicatoreMinorenne : tipoIndicatoreOrdinario;

      ConsultazioneIndicatoreCFBody bodyIndicatoreIseeModi =
          InpsModiHelper.createConsultazioneIndicatoreCFBody(
              datiDomanda.getCodiceIntestatarioBorsa(),
              PrestazioneDaErogareEnum.A1_19,
              StatodomandaPrestazioneEnum.EROGATA,
              tipoIndicatore);

      ConsultazioneIndicatoreCF200 indicatoreIseeModi =
          ServiceLocator.getInstance()
              .getServiziINPSModi()
              .consultazioneIndicatoreCF(bodyIndicatoreIseeModi);

      if (checkValoreIseeInIndicatoreIseeModi(indicatoreIseeModi)) {

        setDatiIndicatoreIsee(datiDomanda, indicatoreIseeModi);

        setDatiAgevolazioneIsee(datiDomanda, tipoIndicatoreMinorenne);

      } else if (etaIntestatario < 18) {

        ConsultazioneIndicatoreCFBody bodyIndicatoreIseeModiBambinoOrdinario =
            InpsModiHelper.createConsultazioneIndicatoreCFBody(
                datiDomanda.getCodiceIntestatarioBorsa(),
                PrestazioneDaErogareEnum.A1_19,
                StatodomandaPrestazioneEnum.EROGATA,
                tipoIndicatoreOrdinario);

        ConsultazioneIndicatoreCF200 indicatoreIseeModiBambinoOrdinario =
            ServiceLocator.getInstance()
                .getServiziINPSModi()
                .consultazioneIndicatoreCF(bodyIndicatoreIseeModiBambinoOrdinario);

        if (checkValoreIseeInIndicatoreIseeModi(indicatoreIseeModiBambinoOrdinario)) {

          setDatiIndicatoreIsee(datiDomanda, indicatoreIseeModiBambinoOrdinario);

          setDatiAgevolazioneIsee(datiDomanda, tipoIndicatoreOrdinario);
        }
      }

    } catch (BusinessException e) {
      log.error("Errore inps modi mensa = " + e.getMessage(), e);
    }
  }

  private void setDatiIndicatoreIsee(
      PraticaBorseStudioExtend datiDomanda, ConsultazioneIndicatoreCF200 indicatoreIseeModi) {
    String isee =
        indicatoreIseeModi
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult()
            .getXmlEsitoIndicatoreDecoded()
            .getIndicatore()
            .getISEE();
    datiDomanda.setImportoIsee(new BigDecimal(isee));

    String protocolloDSU =
        indicatoreIseeModi
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult()
            .getXmlEsitoIndicatoreDecoded()
            .getIndicatore()
            .getProtocolloDSU();
    datiDomanda.setNumeroProtocolloIsee(protocolloDSU);

    LocalDate dataPresentazioneIsee =
        indicatoreIseeModi
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult()
            .getXmlEsitoIndicatoreDecoded()
            .getIndicatore()
            .getDataPresentazione();
    datiDomanda.setDataPresentazioneIsee(dataPresentazioneIsee);

    LocalDate dataValiditaIsee =
        indicatoreIseeModi
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult()
            .getXmlEsitoIndicatoreDecoded()
            .getIndicatore()
            .getRicercaCF()
            .getDataValidita();
    datiDomanda.setDataValiditaIsee(dataValiditaIsee);
  }

  private void setDatiAgevolazioneIsee(PraticaBorseStudioExtend datiDomanda, String tipoIndicatore)
      throws BusinessException {
    ConsultazioneAttestazioneCF200 attestazioneIsee =
        InpsModiHelper.getAttestazioneISEEConCF(
            datiDomanda.getCodiceIntestatarioBorsa(),
            PrestazioneDaErogareEnum.A1_19,
            StatodomandaPrestazioneEnum.EROGATA,
            tipoIndicatore,
            datiDomanda.getNumeroProtocolloIsee(),
            datiDomanda.getDataPresentazioneIsee());

    log.debug("CP attestazioneIsee = " + attestazioneIsee);

    if (LabelFdCUtil.checkIfNotNull(attestazioneIsee)) {

      if (LabelFdCUtil.checkIfNotNull(
          attestazioneIsee
              .getConsultazioneAttestazioneResponse()
              .getConsultazioneAttestazioneResult()
              .getXmlEsitoAttestazioneDecoded()
              .getEsitoAttestazione()
              .getAttestazione()
              .getOmissioneDifformita())) {
        datiDomanda.setIseeDifforme(true);
        datiDomanda.setIseeDifformeValue("Si");
      } else {
        datiDomanda.setIseeDifforme(false);
        datiDomanda.setIseeDifformeValue("No");
      }

      Ordinario ordinario = InpsModiHelper.getOrdinarioByAttestazioneIsee(attestazioneIsee);

      log.debug("CP ordinario = " + ordinario);

      if (LabelFdCUtil.checkIfNotNull(ordinario)) {

        List<NucleoFamiliareComponenteNucleoInner> listaComponenteNucleoIsee =
            new ArrayList<NucleoFamiliareComponenteNucleoInner>();

        if (LabelFdCUtil.checkIfNotNull(ordinario.getNucleoFamiliare())
            && LabelFdCUtil.checkIfNotNull(ordinario.getNucleoFamiliare().getComponenteNucleo())) {
          listaComponenteNucleoIsee = ordinario.getNucleoFamiliare().getComponenteNucleo();
        }

        if (LabelFdCUtil.checkIfNotNull(listaComponenteNucleoIsee)
            && !LabelFdCUtil.checkEmptyList(listaComponenteNucleoIsee)) {

          // List<Residente> listaPersoneInAnagrafeDelDemografico =
          // ServiceLocator.getInstance().getServizioDemografico().listaPersoneInAnagrafeDelDemografico(getUtente());
          List<Residente> listaPersoneInAnagrafeDelDemografico =
              datiDomanda.getListaComponentiAnagrafe();

          boolean checkNuclei =
              InpsModiHelper.checkNucleoIseeUgualeNucleoAnagrafico(
                  listaComponenteNucleoIsee, listaPersoneInAnagrafeDelDemografico);

          log.debug("CP checkNuclei borse = " + checkNuclei);
          datiDomanda.setNucleoIseeUgualeNucleoAnagrafico(checkNuclei);

          if (checkNuclei) {
            datiDomanda.setAccettazioneNuclei(
                AccettazioneNucleoIseeAnagraficoEnum.NUCLEI_COINCIDONO);
          } else {

            Comparator<NucleoFamiliareComponenteNucleoInner> comparator =
                Comparator.comparing(
                    NucleoFamiliareComponenteNucleoInner::getCodiceFiscale,
                    Comparator.nullsLast(Comparator.naturalOrder()));

            List<NucleoFamiliareComponenteNucleoInner> listaIseeOrdinata =
                listaComponenteNucleoIsee.stream().sorted(comparator).collect(Collectors.toList());

            datiDomanda.setListaComponentiIsee(listaIseeOrdinata);
            datiDomanda.setListaComponentiAnagrafe(listaPersoneInAnagrafeDelDemografico);
          }
        }

        if (LabelFdCUtil.checkIfNotNull(ordinario.getIsEEMin())) {
          List<ComponenteMinorenne> listaComponenteMinorenne =
              ordinario.getIsEEMin().getComponenteMinorenne();
          if (LabelFdCUtil.checkIfNotNull(listaComponenteMinorenne)) {

            log.debug("CP intestatario " + datiDomanda.getCodiceIntestatarioBorsa());

            ComponenteMinorenne personaInLista =
                listaComponenteMinorenne.stream()
                    .filter(
                        elemCM ->
                            elemCM
                                .getCodiceFiscale()
                                .equalsIgnoreCase(datiDomanda.getCodiceIntestatarioBorsa()))
                    .findFirst()
                    .orElse(null);

            log.debug("CP personaInLista = " + personaInLista);

            if (LabelFdCUtil.checkIfNotNull(personaInLista)) {
              datiDomanda.setIseeNonCalcolabile(personaInLista.getIsEENonCalcolabile());
            }
          }
        }
      }
    }
  }

  private boolean checkValoreIseeInIndicatoreIseeModi(
      ConsultazioneIndicatoreCF200 indicatoreIseeModiBambino) {
    return LabelFdCUtil.checkIfNotNull(indicatoreIseeModiBambino)
        && LabelFdCUtil.checkIfNotNull(
            indicatoreIseeModiBambino.getConsultazioneIndicatoreResponse())
        && LabelFdCUtil.checkIfNotNull(
            indicatoreIseeModiBambino
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult())
        && LabelFdCUtil.checkIfNotNull(
            indicatoreIseeModiBambino
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded())
        && LabelFdCUtil.checkIfNotNull(
            indicatoreIseeModiBambino
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore())
        && PageUtil.isStringValid(
            indicatoreIseeModiBambino
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getISEE());
  }
}
