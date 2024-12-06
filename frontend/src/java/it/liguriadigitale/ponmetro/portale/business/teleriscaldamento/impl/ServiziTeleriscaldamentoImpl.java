package it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.teleriscaldamento.service.ServiziTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.DatiVerificatiEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.EsitoVerificaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee25Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.StatoPraticaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandeTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Esito;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Protocollo;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.RicercaDato;
import it.liguriadigitale.ponmetro.teleriscaldamentoiren.model.FileRichiestaBonusResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziTeleriscaldamentoImpl implements ServiziTeleriscaldamento {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TELERISCALDAMENTO =
      "Errore di connessione alle API Teleriscaldamento";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("teleriscaldamento", "Teleriscaldamento"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRegistrazione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("teleriscaldamento", "Teleriscaldamento"));
    listaBreadcrumb.add(new BreadcrumbFdC("domandaTeleriscaldamento", "Domanda"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    String chiaveInizio = "TELERISCALDAMENTO_INIZIO";
    String dataInizio = getValoreDaDbByChiave(chiaveInizio);
    log.debug("CP dataInizio = " + dataInizio);

    String chiaveFine = "TELERISCALDAMENTO_FINE";
    String dataFine = getValoreDaDbByChiave(chiaveFine);
    log.debug("CP dataFine = " + dataFine);

    MessaggiInformativi messaggio4 = new MessaggiInformativi();
    messaggio4.setMessaggio(
        "<b>QUANDO FARE DOMANDA</b><br></br>Le domande per il Bonus Teleriscaldamento possono essere effettuate dai cittadini residenti nel Comune di Genova che dispongono delle caratteristiche di cui sopra entro tra il "
            + dataInizio
            + " e il "
            + dataFine
            + " attraverso il Fascicolo del Cittadino.");
    messaggio4.setType("info");
    listaMessaggi.add(messaggio4);

    MessaggiInformativi messaggio5 = new MessaggiInformativi();
    messaggio5.setMessaggio(
        "<b>ULTERIORI INFORMAZIONI</b><br></br>Per maggiori informazioni vai sul <a href=\"https://www.gruppoiren.it/\" target=\"_blank\">sito di IREN</a>");
    messaggio5.setType("info");
    listaMessaggi.add(messaggio5);

    return listaMessaggi;
  }

  @Override
  public List<DomandaTeleriscaldamento> getDomande(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDomande: " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DomandeTeleriscaldamento domande =
          instance.getApiTeleriscaldamento().getDatiCittadino(codiceFiscale);

      List<DomandaTeleriscaldamento> listaDomande = new ArrayList<>();

      if (LabelFdCUtil.checkIfNotNull(domande)
          && !LabelFdCUtil.checkEmptyList(domande.getDomande())) {
        listaDomande =
            domande.getDomande().stream()
                .sorted(
                    Comparator.comparing(DomandaTeleriscaldamento::getIdentificativo).reversed())
                .collect(Collectors.toList());
      }

      return listaDomande;

    } catch (BusinessException e) {
      log.error("ServiziTeleriscaldamentoImpl -- getDomande: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getDomande: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getDomande: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getDomande: errore durante la chiamata delle API Teleriscaldamento ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Teleriscaldamento"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("ISEE", 1));
    listaStep.add(new StepFdC("Dati richiedente", 2));
    listaStep.add(new StepFdC("Dati utenza", 3));
    listaStep.add(new StepFdC("Dati contratto", 4));
    listaStep.add(new StepFdC("Dichiarazioni", 5));
    listaStep.add(new StepFdC("Riepilogo", 6));
    listaStep.add(new StepFdC("Esito", 7));

    return listaStep;
  }

  // @Override
  // public void getAttestazioniISEE(Utente utente, DatiDomandaTeleriscaldamento
  // datiDomanda) {
  // AttestazioniISEE attestazioniISEE = null;
  //
  // LocalDate oggi = LocalDate.now();
  //
  // try {
  // attestazioniISEE =
  // ServiceLocator.getInstance().getServiziINPS().verificaPresentazioneISEE(utente,
  // oggi);
  // } catch (BusinessException e) {
  // log.error("CP errore attestazione ISEE per teleriscaldamento: " +
  // e.getMessage(), e);
  // }
  //
  // if (LabelFdCUtil.checkIfNotNull(attestazioniISEE)) {
  // datiDomanda.setIseePresentato(true);
  //
  // int numeroFigliACarico = 0;
  //
  // if (LabelFdCUtil.checkIfNotNull(attestazioniISEE.getEsitoAttestazione())
  // &&
  // LabelFdCUtil.checkIfNotNull(attestazioniISEE.getEsitoAttestazione().getNucleoFamiliare())
  // && LabelFdCUtil.checkIfNotNull(
  // attestazioniISEE.getEsitoAttestazione().getNucleoFamiliare().getComponenteNucleo())
  // && !LabelFdCUtil.checkEmptyList(
  // attestazioniISEE.getEsitoAttestazione().getNucleoFamiliare().getComponenteNucleo()))
  // {
  //
  // numeroFigliACarico = (int)
  // attestazioniISEE.getEsitoAttestazione().getNucleoFamiliare()
  // .getComponenteNucleo().stream()
  // .filter(elem ->
  // "F".equalsIgnoreCase(elem.getRapportoConDichiarante())).count();
  //
  // datiDomanda.setNumeroFigliACarico(numeroFigliACarico);
  //
  // datiDomanda.setNumeroComponenti(
  // attestazioniISEE.getEsitoAttestazione().getNucleoFamiliare().getComponenteNucleo().size());
  //
  // }
  //
  // if (LabelFdCUtil.checkIfNotNull(attestazioniISEE.getEsitoAttestazione())) {
  // Double importoIsee =
  // attestazioniISEE.getEsitoAttestazione().getIseeOrdinarioISEE();
  // datiDomanda.setImportoIsee(importoIsee);
  //
  // Double iseeMassimo = new Double(12000);
  // if (numeroFigliACarico >= 4) {
  // iseeMassimo = new Double(20000);
  // }
  //
  // if (LabelFdCUtil.checkIfNotNull(importoIsee) &&
  // importoIsee.compareTo(iseeMassimo) < 0) {
  // datiDomanda.setImportoIseeNelRange(true);
  // datiDomanda.setDomandaPresentabile(true);
  //
  // if (iseeMassimo.compareTo(new Double(12000)) == 0) {
  // datiDomanda.setIndicatoreIsee12(IndicatoreIsee12Enum.SI);
  // } else {
  // datiDomanda.setIndicatoreIsee12(IndicatoreIsee12Enum.NO);
  // }
  //
  // if (iseeMassimo.compareTo(new Double(20000)) == 0) {
  // datiDomanda.setIndicatoreIsee20(IndicatoreIsee20Enum.SI);
  // } else {
  // datiDomanda.setIndicatoreIsee20(IndicatoreIsee20Enum.NO);
  // }
  //
  // } else {
  // datiDomanda.setImportoIseeNelRange(false);
  // datiDomanda.setIndicatoreIsee12(IndicatoreIsee12Enum.NO);
  // datiDomanda.setIndicatoreIsee20(IndicatoreIsee20Enum.NO);
  // }
  // }
  //
  // } else {
  // datiDomanda.setIseePresentato(false);
  // datiDomanda.setImportoIseeNelRange(false);
  // datiDomanda.setDomandaPresentabile(false);
  // }
  //
  // datiDomanda.setAttestazioneIsee(attestazioniISEE);
  //
  // }

  @Override
  public Protocollo getProtocollo() throws BusinessException, ApiException, IOException {
    log.debug("CP getProtocollo: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiTeleriscaldamento().getProtocollo();
    } catch (BusinessException e) {
      log.error("ServiziTeleriscaldamentoImpl -- getProtocollo: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getProtocollo: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getProtocollo: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- getProtocollo: errore durante la chiamata delle API Teleriscaldamento ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Teleriscaldamento"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public Esito inviaDomandaTeleriscaldamento(DatiDomandaTeleriscaldamento datiDomanda)
      throws BusinessException, ApiException, IOException {
    log.debug("CP inviaDomandaTeleriscaldamento: ");

    // get protocollo

    Protocollo protocollo = getProtocollo();

    if (LabelFdCUtil.checkIfNotNull(protocollo)
        && LabelFdCUtil.checkIfNotNull(protocollo.getProtocollo())) {
      datiDomanda.setNumeroProtocollo(String.valueOf(protocollo.getProtocollo()));
      datiDomanda.setDataProtocollo(LocalDate.now());

      datiDomanda.setIdentificativo(protocollo.getProtocollo());
    }
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DomandaTeleriscaldamento domanda = new DomandaTeleriscaldamento();

      if (LabelFdCUtil.checkIfNotNull(datiDomanda)) {

        String annoDomanda = getValoreDaDbByChiave("TELERISCALDAMENTO_ANNO");
        domanda.setAnnoDomanda(annoDomanda);

        domanda.setCapAmmCondominio(datiDomanda.getCapAmministratore());
        domanda.setCellAmmCondominio(datiDomanda.getCellulareAmministratore());
        domanda.setCellulare(datiDomanda.getCellulare());
        domanda.setCfIntestarioContratto(datiDomanda.getCfIntestatarioContratto());
        domanda.setCfRichiedente(datiDomanda.getCodiceFiscale());
        domanda.setCivicoAmmCondominio(datiDomanda.getNumeroCivicoAmministratore());
        domanda.setCognomeRichiedente(datiDomanda.getCognome());
        domanda.setComuneAmmCondominio(datiDomanda.getComuneAmministratore());

        String si = "SI";
        String no = "NO";

        if (PageUtil.isStringValid(datiDomanda.getArt43())) {
          if (datiDomanda.getArt43().equalsIgnoreCase("Sì")) {
            domanda.setConsensoInf(si);
          }
          if (datiDomanda.getArt43().equalsIgnoreCase("No")) {
            domanda.setConsensoInf(no);
          }
        }

        if (PageUtil.isStringValid(datiDomanda.getPrivacy())) {
          if (datiDomanda.getPrivacy().equalsIgnoreCase("Sì")) {
            domanda.setConsensoPrivacy(si);
          }
          if (datiDomanda.getPrivacy().equalsIgnoreCase("No")) {
            domanda.setConsensoPrivacy(no);
          }
        }

        datiDomanda.setDataInvioIren(LocalDate.now());
        domanda.setDataInvioIREN(datiDomanda.getDataInvioIren());

        if (LabelFdCUtil.checkIfNotNull(datiDomanda.getDataProtocollo())) {
          String dataProtocollo =
              LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(datiDomanda.getDataProtocollo());
          domanda.setDataProtocollo(dataProtocollo);
        }

        datiDomanda.setDatiVerificati(DatiVerificatiEnum.SI);
        domanda.setDatiVerificati(datiDomanda.getDatiVerificati());

        domanda.setEmail(datiDomanda.getEmail());
        domanda.setEmailAmmCondominio(datiDomanda.getEmailAmministratore());

        datiDomanda.setEsitoVerifica(EsitoVerificaEnum.OK);
        domanda.setEsitoVerifica(datiDomanda.getEsitoVerifica());

        domanda.setIdentificativo(datiDomanda.getIdentificativo());

        // domanda.setIndicatoreIsee12(datiDomanda.getIndicatoreIsee12());
        //
        // domanda.setIndicatoreIsee20(datiDomanda.getIndicatoreIsee20());

        domanda.setIndicatoreIsee25(IndicatoreIsee25Enum.SI);

        domanda.setNomeRichiedente(datiDomanda.getNome());

        String nominativoAmministratore = "";
        if (PageUtil.isStringValid(datiDomanda.getNomeAmministratore())
            && PageUtil.isStringValid(datiDomanda.getCognomeAmministratore())) {
          nominativoAmministratore =
              datiDomanda
                  .getCognomeAmministratore()
                  .concat(" ")
                  .concat(datiDomanda.getNomeAmministratore());
          datiDomanda.setNominativoAmministratore(nominativoAmministratore);
        }
        domanda.setNominativoAmmCond(datiDomanda.getNominativoAmministratore());

        String nominativoApt = "";
        if (PageUtil.isStringValid(datiDomanda.getNomeProprietarioAppartamento())
            && PageUtil.isStringValid(datiDomanda.getCognomeProprietarioAppartamento())) {
          nominativoApt =
              datiDomanda
                  .getCognomeProprietarioAppartamento()
                  .concat(" ")
                  .concat(datiDomanda.getNomeProprietarioAppartamento());
          datiDomanda.setNominativoProprietarioAppartamento(nominativoApt);
        }
        domanda.setNominativoApt(datiDomanda.getNominativoProprietarioAppartamento());

        String nominativoContratto = "";
        if (PageUtil.isStringValid(datiDomanda.getNomeIntestatarioContratto())
            && PageUtil.isStringValid(datiDomanda.getCognomeIntestatarioContratto())) {
          nominativoContratto =
              datiDomanda
                  .getCognomeIntestatarioContratto()
                  .concat(" ")
                  .concat(datiDomanda.getNomeIntestatarioContratto());
          datiDomanda.setNominativoIntestatarioContratto(nominativoContratto);
        }
        domanda.setNominativoContratto(datiDomanda.getNominativoIntestatarioContratto());

        domanda.setNumCivicoFornitura(datiDomanda.getNumeroCivico());
        domanda.setComuneFornitura(datiDomanda.getComune());
        domanda.setCapFornitura(datiDomanda.getCap());

        domanda.setNumContratto(datiDomanda.getNumeroContratto());

        String numeroClienteConEventualiZeri = "";
        log.debug("CP datiDomanda.getNumeroCliente() = " + datiDomanda.getNumeroCliente());

        if (PageUtil.isStringValid(datiDomanda.getNumeroCliente())) {
          Integer numeroClienteInInt = Integer.parseInt(datiDomanda.getNumeroCliente());

          log.debug("CP num cliente = " + numeroClienteInInt);
          numeroClienteConEventualiZeri = String.format("%08d", numeroClienteInInt);

          log.debug("CP numeroClienteConEventualiZeri = " + numeroClienteConEventualiZeri);
        }
        domanda.setNumeroCliente(numeroClienteConEventualiZeri);

        domanda.setNumNucleoFamiliare(datiDomanda.getNumeroComponenti());

        domanda.setNumProtocollo(datiDomanda.getNumeroProtocollo());

        domanda.setpIvaContratto(datiDomanda.getpIvaIntestatarioContratto());
        domanda.setProvAmmCondominio(datiDomanda.getProvinciaAmministratore());
        domanda.setProvinciaFornitura(datiDomanda.getProvincia());

        domanda.setTelAmmCondominio(datiDomanda.getTelefonoAmministratore());
        domanda.setTelefono(datiDomanda.getTelefono());

        domanda.setTipoUtenza(datiDomanda.getTipoUtenza().getDescrizione()); // va bene cosi?

        domanda.setViaAmmCondominio(datiDomanda.getViaAmministratore());
        domanda.setViaFornitura(datiDomanda.getVia());

        // idStato: null 1 se errore su IREN  2 se ok su IREN
        // statoPratica:  Consegnata al Comune se errore su IREN  Inviata dal Comune
        // ad IREN se ok su IREN

        FileRichiestaBonusResponse servizioIren = null;

        String teleriscaldamentoIrenPostDaChiamare =
            getValoreDaDbByChiave("TELERISCALDAMENTO_POST_IREN");

        if (teleriscaldamentoIrenPostDaChiamare.equalsIgnoreCase("SI")) {
          // chiamo servizio iren vero

          servizioIren =
              ServiceLocator.getInstance()
                  .getServiziTeleriscaldamentoIren()
                  .serviceRichiestaBonusPost(datiDomanda);
        }

        log.debug("CP servizioIren = " + servizioIren);

        if (LabelFdCUtil.checkIfNotNull(servizioIren)
            && servizioIren.getEsito().equalsIgnoreCase("OK")) {

          log.debug("CP entro in OK");

          datiDomanda.setIdStato(new BigDecimal(2));
          domanda.setIdStato(datiDomanda.getIdStato());

          datiDomanda.setStatoPratica(StatoPraticaEnum.INVIATA_DAL_COMUNE_AD_IREN);
          domanda.setStatoPratica(datiDomanda.getStatoPratica());

          datiDomanda.setEsitoDiIren(servizioIren.getEsito());
        } else {

          log.debug("CP entro in != OK");

          datiDomanda.setIdStato(new BigDecimal(1));
          domanda.setIdStato(datiDomanda.getIdStato());

          datiDomanda.setStatoPratica(StatoPraticaEnum.CONSEGNATA_AL_COMUNE);
          domanda.setStatoPratica(datiDomanda.getStatoPratica());

          datiDomanda.setEsitoDiIren("KO");
        }
      }

      // invio a backend

      log.debug("CP prima di POST a nostro backend teleriscaldamento = " + domanda);

      return instance.getApiTeleriscaldamento().setDatiCittadino(domanda);

    } catch (BusinessException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- inviaDomandaTeleriscaldamento: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- inviaDomandaTeleriscaldamento: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- inviaDomandaTeleriscaldamento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- inviaDomandaTeleriscaldamento: errore durante la chiamata delle API Teleriscaldamento ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Teleriscaldamento"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(Utente utente) {
    log.debug("CP getAttestazioneISEEModi");

    try {
      String tipoIndicatore = "Ordinario";
      ConsultazioneAttestazioneCF200 attestazione =
          InpsModiHelper.getAttestazioneISEE(
              utente,
              PrestazioneDaErogareEnum.A2_05,
              StatodomandaPrestazioneEnum.EROGATA,
              tipoIndicatore);

      return attestazione;
    } catch (BusinessException e) {
      log.error("Errore getAttestazioneISEEModi Teleriscaldamento = " + e.getMessage(), e);
      return null;
    }
  }

  @Override
  public Ordinario getIseeOrdinario(Utente utente) {
    log.debug("CP getIseeOrdinario");

    ConsultazioneAttestazioneCF200 attestazione = getAttestazioneISEEModi(utente);

    if (LabelFdCUtil.checkIfNull(attestazione)) {
      return null;
    }

    return InpsModiHelper.getOrdinarioByAttestazioneIsee(attestazione);
  }

  @Override
  public void setDatiIseeModiInDomanda(Utente utente, DatiDomandaTeleriscaldamento datiDomanda) {
    Ordinario iseeOrdinario = getIseeOrdinario(utente);

    if (LabelFdCUtil.checkIfNotNull(iseeOrdinario)) {
      datiDomanda.setIseePresentato(true);

      if (LabelFdCUtil.checkIfNotNull(iseeOrdinario.getNucleoFamiliare())
          && LabelFdCUtil.checkIfNotNull(
              iseeOrdinario.getNucleoFamiliare().getComponenteNucleo())) {
        datiDomanda.setNumeroComponenti(
            iseeOrdinario.getNucleoFamiliare().getComponenteNucleo().size());
      }

      if (LabelFdCUtil.checkIfNotNull(iseeOrdinario.getIsEEOrdinario())
          && LabelFdCUtil.checkIfNotNull(iseeOrdinario.getIsEEOrdinario().getValori())
          && LabelFdCUtil.checkIfNotNull(iseeOrdinario.getIsEEOrdinario().getValori().getISEE())) {

        Double importoIseeDouble =
            Double.valueOf(iseeOrdinario.getIsEEOrdinario().getValori().getISEE());
        datiDomanda.setImportoIsee(importoIseeDouble);

        Double iseeMassimo = new Double(25000);

        if (LabelFdCUtil.checkIfNotNull(importoIseeDouble)
            && importoIseeDouble.compareTo(iseeMassimo) < 0) {
          datiDomanda.setImportoIseeNelRange(true);
          datiDomanda.setDomandaPresentabile(true);
          datiDomanda.setIndicatoreIsee25(IndicatoreIsee25Enum.SI);
        } else {
          datiDomanda.setImportoIseeNelRange(false);
          datiDomanda.setDomandaPresentabile(false);
          datiDomanda.setIndicatoreIsee25(IndicatoreIsee25Enum.NO);
        }
      }

    } else {
      datiDomanda.setIseePresentato(false);
      datiDomanda.setImportoIseeNelRange(false);
      datiDomanda.setDomandaPresentabile(false);
    }
  }

  @Override
  public RicercaDato isClientePresenteInLista(String numeroCliente)
      throws BusinessException, ApiException, IOException {
    log.debug("CP isClientePresenteInLista");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiTeleriscaldamento().isClientePresenteInLista(numeroCliente);
    } catch (BusinessException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isClientePresenteInLista: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isClientePresenteInLista: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isClientePresenteInLista: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isClientePresenteInLista: errore durante la chiamata delle API Teleriscaldamento ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Teleriscaldamento"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public RicercaDato isContrattoPresenteInLista(String numeroContratto)
      throws BusinessException, ApiException, IOException {
    log.debug("CP isContrattoPresenteInLista");

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiTeleriscaldamento().isContrattoPresenteInLista(numeroContratto);
    } catch (BusinessException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isContrattoPresenteInLista: errore API Teleriscaldamento:");
      throw new BusinessException(ERRORE_API_TELERISCALDAMENTO);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isContrattoPresenteInLista: errore durante la chiamata delle API Teleriscaldamento"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(),
          ERRORE_API_TELERISCALDAMENTO);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isContrattoPresenteInLista: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTeleriscaldamentoImpl -- isContrattoPresenteInLista: errore durante la chiamata delle API Teleriscaldamento ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Teleriscaldamento"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public String getValoreDaDbByChiave(String chiave) {
    log.debug("CP getValoreDaDbByChiave");

    String massimo = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        massimo = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante getValoreDaDbByChiave = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return massimo;
  }

  @Override
  public byte[] getHelpTeleriscaldamentoPDF(Utente utente) throws BusinessException {
    log.debug("getHelpTeleriscaldamentoPDF: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(
                BaseServiceImpl.COD_HELP_TELERISCALDAMENTO, utente.getIdAnonimoComuneGenova(), -3L);
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF HELP teleriscaldamento: ", e);
      throw new BusinessException("Impossibile recuperare PDF HELP teleriscaldamento");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }
}
