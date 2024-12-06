package it.liguriadigitale.ponmetro.portale.business.ristorazione.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.commissionimensa.model.Istituto;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.ComponenteMinorenne;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Attualizzato;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.AvvisoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.portale.business.demografico.impl.DemograficoImpl;
import it.liguriadigitale.ponmetro.portale.business.demografico.service.DemograficoInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoBollettazioneMensa;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoGoadevBollettino;
import it.liguriadigitale.ponmetro.portale.business.ristorazione.service.ServiziRistorazioneService;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiFiglioExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiGenitoreExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiNascitaResidenzaDomicilio;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione.DatiPagamentiBollettiniRiepilogativiEstesi;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.pojo.portale.StatoAgevolazione;
import it.liguriadigitale.ponmetro.portale.pojo.portale.builder.AgevolazioneTariffariaRistorazioneBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.portale.builder.StatoAgevolazioneBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.pojo.MembroNucleoChePercepivaReddito;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AttestazioniDiPagamento;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffariaAgevolando;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffariaDisoccupato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaIscrizioneServiziRistorazioneNonResidente;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiInfoBollettazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiMenuMensa;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiBollettiniRiepilogativi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitarioEstesi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiSintetico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPresenzaServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiRevocaRichiestaDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoAgevolazioneTariffaria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoPagamenti;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviSanitari;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DomandaInviata;
import it.liguriadigitale.ponmetro.serviziristorazione.model.FileAllegato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro;
import it.liguriadigitale.ponmetro.serviziristorazione.model.IdentificativoPdf;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione.SessoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziRistorazioneImpl implements ServiziRistorazioneService {

  private static final String ERRORE_CHIAMATA_API = "Errore di chiamata alle API";
  private static final String ERRORE_POOL_THREAD = "Errore nel recupero dei dati Demografici";

  private Log log = LogFactory.getLog(getClass());

  @Override
  public DatiMenuMensa getMenuMensa(LocalDate oggi, UtenteServiziRistorazione iscrizione)
      throws BusinessException, ApiException {
    if (iscrizione == null) {
      throw new ApiException(null, "iscrizione non valorizzata");
    }
    String strutturaScolastica = iscrizione.getStrutturaScolastica();
    log.debug("[ServiziRistorazioneImpl] getMenuMensa");
    log.debug(
        "[ServiziRistorazioneImpl] -- iscrizione.getStrutturaScolastica()=" + strutturaScolastica);
    log.debug("[ServiziRistorazioneImpl] -- giorno=" + oggi);
    log.debug(
        "[ServiziRistorazioneImpl] -- iscrizione.getCodiceFiscale()="
            + iscrizione.getCodiceFiscale());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<DatiMenuMensa> lista =
          instance
              .getApiRistorazionePortale()
              .getMenuServiziRistorazione(
                  strutturaScolastica, oggi, oggi, iscrizione.getCodiceFiscale());
      log.debug("[ServiziRistorazioneImpl] -- lista=" + lista.size());
      if (!lista.isEmpty()) {
        return lista.get(0);
      } else {
        log.error("Errore: nessun menu restituito");
        return new DatiMenuMensa();
      }
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getMenuMensa: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("menù mensa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public UtenteServiziRistorazione trovaIscritto(UtenteServiziRistorazione figlio)
      throws BusinessException, ApiException {
    log.debug("UtenteServiziRistorazione");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      // PRIMA DI MODIFICA CHIESTA DA SILVESTRI PER WSO2
      figlio =
          instance.getApiRistorazionePortale().getUtenteRistorazione(figlio.getCodiceFiscale());
      return figlio;

    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- trovaIscritto: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public Long contaNumeroPresenze(List<UtenteServiziRistorazione> lista)
      throws BusinessException, ApiException {

    Long numeroPresenze = 0L;

    try {
      for (UtenteServiziRistorazione figlio : lista) {
        List<DatiPresenzaServiziRistorazione> listaGiorniDiPresenza =
            getGiorniDiPresenzaPerFiglioPerMese(figlio, LocalDateUtil.today());
        for (DatiPresenzaServiziRistorazione presenza : listaGiorniDiPresenza) {
          if (presenza
              .getPresenza()
              .equalsIgnoreCase(
                  DatiPresenzaServiziRistorazione.PresenzaEnum.PRESENTE_SIA_A_SCUOLA_CHE_IN_MENSA
                      .value())) numeroPresenze = numeroPresenze + 1;
        }
      }
    } catch (Exception e) {
      log.debug("[ServiziRistorazioneImpl] contaNumeroPresenze", e);
    }

    return numeroPresenze;
  }

  @Override
  public List<DatiPresenzaServiziRistorazione> getGiorniDiPresenzaPerFiglioPerMese(
      UtenteServiziRistorazione figlio, LocalDate day) throws BusinessException, ApiException {
    log.debug("[ServiziRistorazioneImpl] getGiorniDiPresenzaPerFiglioDaInizioMese ");

    ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti instance =
        ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti.getInstance();
    return closeConnectionInGetGiorniDiPresenzaPerFiglioPerMese(instance, figlio, day);
  }

  private List<DatiPresenzaServiziRistorazione>
      closeConnectionInGetGiorniDiPresenzaPerFiglioPerMese(
          ServiceLocatorLivelloUno instance, UtenteServiziRistorazione figlio, LocalDate day) {
    log.debug("[ServiziRistorazioneImpl] getGiorniDiPresenzaPerFiglioDaInizioMese ");
    try {
      LocalDate inizio = LocalDateUtil.firstDayOfMonth(day);
      LocalDate fine = LocalDateUtil.lastDayOfMonth(day);
      List<DatiPresenzaServiziRistorazione> lista =
          instance
              .getApiRistorazionePortale()
              .getPresenzaMensa(figlio.getCodiceFiscale(), inizio, fine);
      log.debug(
          "[ServiziRistorazioneImpl] getGiorniDiPresenzaPerFiglioDaInizioMese("
              + figlio.getNome()
              + ","
              + day
              + ")="
              + lista.size());
      return lista;
    } catch (BusinessException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getGiorniDiPresenzaPerFiglioDaInizioMese: " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("presenze mensa"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private List<UtenteServiziRistorazione> trovaIscrittiAccettati(List<ComponenteNucleo> lista)
      throws BusinessException, ApiException {

    List<UtenteServiziRistorazione> listaIscritti = new ArrayList<>();

    for (ComponenteNucleo utente : lista) {
      UtenteServiziRistorazione iscritto = this.trovaIscritto(utente.getDatiCittadino());

      if (isIscrizioneAccettata(iscritto)) {
        listaIscritti.add(iscritto);
      }
    }

    return listaIscritti;
  }

  @Override
  public boolean isIscrizioneAccettata(UtenteServiziRistorazione iscritto) {
    return iscritto
        .getStatoIscrizioneServiziRistorazione()
        .equalsIgnoreCase(
            UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA.value());
  }

  private List<UtenteServiziRistorazione> trovaIscritti(List<ComponenteNucleo> listaNucleo)
      throws BusinessException {
    log.debug("ServiziRistorazioneImpl -- trovaIscritti(lista) inizio");

    List<UtenteServiziRistorazione> listaIscritti = new ArrayList<>();

    ExecutorService ex = Executors.newWorkStealingPool();

    List<Callable<UtenteServiziRistorazione>> tasks = new ArrayList<>();

    for (final ComponenteNucleo componente : listaNucleo) {
      tasks.add(
          new Callable<UtenteServiziRistorazione>() {

            @Override
            public UtenteServiziRistorazione call() throws BusinessException, ApiException {
              ServiziRistorazioneImpl sr = new ServiziRistorazioneImpl();

              UtenteServiziRistorazione res = new UtenteServiziRistorazione();
              try {
                log.debug(
                    "ServiziRistorazioneImpl -- trovaIscritti(lista):call inizio: "
                        + componente.getRelazione().getCpvComponentTaxCode());
                res = sr.trovaIscritto(componente.getDatiCittadino());
                log.debug(
                    "ServiziRistorazioneImpl -- trovaIscritti(lista):call fine: "
                        + componente.getRelazione().getCpvComponentTaxCode());
              } catch (Exception e) {
                log.debug(
                    "ServiziRistorazioneImpl -- trovaIscritti(lista):call errore: "
                        + componente.getRelazione().getCpvComponentTaxCode(),
                    e);
                res.setCodiceFiscale(componente.getRelazione().getCpvComponentTaxCode());
                res.setCognome(componente.getDatiCittadino().getCpvFamilyName());
                res.setDataNascita(componente.getDatiCittadino().getCpvDateOfBirth());
                res.setIndirizzoResidenza(
                    componente.getDatiCittadino().getCpvHasAddress() == null
                        ? ""
                        : componente.getDatiCittadino().getCpvHasAddress().getClvFullAddress());
                res.setLuogoNascita(
                    componente.getDatiCittadino().getCpvHasBirthPlace() == null
                        ? ""
                        : componente.getDatiCittadino().getCpvHasBirthPlace().getClvCity());
                res.setNome(componente.getDatiCittadino().getCpvGivenName());
                res.setSesso(
                    (SessoEnum.F.name()
                            .equalsIgnoreCase(componente.getDatiCittadino().getCpvHasSex())
                        ? SessoEnum.F
                        : SessoEnum.M));
                res.setStatoIscrizioneServiziRistorazione(
                    StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO);
                res.setStrutturaScolastica("");
                log.debug("ServiziRistorazioneImpl -- trovaIscritti: " + res);
              }
              return res;
            }
          });
    }

    try {
      for (final Future<UtenteServiziRistorazione> future : ex.invokeAll(tasks)) {
        UtenteServiziRistorazione res = future.get();

        listaIscritti.add(res);
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("ServiziRistorazioneImpl -- trovaIscritti(lista) errore nei thread", e);
      ex.shutdown();

      throw new BusinessException(ERRORE_POOL_THREAD);
    }

    log.debug("ServiziRistorazioneImpl -- trovaIscritti(lista) fine");

    return listaIscritti;
  }

  @Override
  public List<UtenteServiziRistorazione> trovaIscrittiAccettati(Utente capoFamiglia)
      throws BusinessException, ApiException {

    DemograficoInterface service = getDemograficoService();
    List<ComponenteNucleo> lista = service.getFigli(capoFamiglia);
    return this.trovaIscrittiAccettati(lista);
  }

  private DemograficoInterface getDemograficoService() {
    return new DemograficoImpl();
  }

  @Override
  public List<UtenteServiziRistorazione> trovaIscritti(Utente capoFamiglia)
      throws BusinessException, ApiException {
    log.debug("trovaIscritti(utente: " + capoFamiglia.getCodiceFiscaleOperatore() + ")");

    DemograficoInterface service = getDemograficoService();

    List<ComponenteNucleo> lista = service.getFigliMinori16anni(capoFamiglia);
    log.debug(
        "trovaIscritti(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + "): getFigliMinori16anni.size= "
            + lista.size());
    List<ComponenteNucleo> listaFigliCoResidenti =
        lista.stream().filter(p -> p.isCoResidente()).collect(Collectors.toList());
    log.debug(
        "trovaIscritti(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + "): listaFigliCoResidenti.size= "
            + listaFigliCoResidenti.size());
    List<UtenteServiziRistorazione> listaFigli = this.trovaIscritti(listaFigliCoResidenti);
    log.debug(
        "trovaIscritti(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + "): listaFigli.size= "
            + listaFigli.size());
    return listaFigli;
  }

  public UtenteServiziRistorazione trovaIscritto(String cf) throws BusinessException, ApiException {
    log.debug("trovaIscritto(cf: " + cf + ")");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      // PRIMA DI MODIFICA CHIESTA DA SILVESTRI PER WSO2
      /*
       * return ServiceLocatorLivelloUno.getInstance().getApiRistorazionePortale( )
       * .utentiServiziRistorazioneCodiceFiscaleGet(cf);
       */

      return instance.getApiRistorazionePortale().getUtenteRistorazione(cf);
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- trovaIscritto: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private UtenteServiziRistorazione trovaIscritto(Residente residente)
      throws BusinessException, ApiException {
    return trovaIscritto(residente.getCpvTaxCode());
  }

  @Override
  public Boolean statoPagamenti(String cf, String annoScolastico) {
    log.debug(
        "ServiziRistorazioneImpl -- statoPagamenti(cf: " + cf + ", as: " + annoScolastico + ")");

    boolean pagato = true;

    return pagato;
  }

  @Override
  public List<AgevolazioneTariffariaRistorazione> elencoRichiesteAgevolazioni(
      Utente utente, Long annoScolastico) {
    List<AgevolazioneTariffariaRistorazione> listaAgevolazioni = new ArrayList<>();
    Map<String, AgevolazioneTariffariaRistorazione> mappa = new HashMap<>();
    DemograficoInterface demografico = new DemograficoImpl();

    for (UtenteServiziRistorazione figlio : utente.getListaFigli()) {
      DatiAgevolazioneTariffaria res = new DatiAgevolazioneTariffaria();
      ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
      try {
        DatiStatoAgevolazioneTariffaria agevolazione =
            instance
                .getApiRistorazionePortale()
                .getDomandeAgevolazioneTariffariaStato(
                    figlio.getCodiceFiscale(), annoScolastico.toString());

        log.debug(
            "elencoRichiesteAgevolazion cf "
                + figlio.getCodiceFiscale()
                + " e as: "
                + annoScolastico.toString()
                + " stato: "
                + agevolazione);

        Residente r = demografico.getDatiResidenteDaSessione(figlio.getCodiceFiscale(), utente);

        mappa.put(
            annoScolastico.toString().concat("#").concat(figlio.getCodiceFiscale()),
            new AgevolazioneTariffariaRistorazioneBuilder()
                .setAnnoScolastico(annoScolastico.toString())
                .setCodiceFiscale(figlio.getCodiceFiscale())
                .setCognome(r.getCpvFamilyName())
                .setDataFineAgevolazione(null)
                .setDataInizioAgevolazione(null)
                .setDataNascita(r.getCpvDateOfBirth())
                .setDataPresentazioneAgevolazione(null)
                .setDataRichiestaAgevolazione(null)
                .setDataValiditaAgevolazione(null)
                .setNome(r.getCpvGivenName())
                .setNumeroProtocolloDsu(res.getNumeroProtocolloDsu())
                .setOmissioneDifformita(res.getOmissioneDifformita())
                .setStatoRichiestaAgevolazione(getValueEnumStatoAgevolazione(agevolazione))
                .build());

      } catch (Exception e) {
        log.debug("ServiziRistorazioneImpl -- elencoRichiesteAgevolazioni -- errore", e);
      } finally {
        log.debug("close connection");
        instance.closeConnection();
      }
      log.debug(
          "ServiziRistorazioneImpl -- elencoRichiesteAgevolazioni -- trovato richiesta:"
              + res.getStatoRichiestaAgevolazione());
    }
    mappa.forEach((key, value) -> listaAgevolazioni.add(value));
    log.debug("ServiziRistorazioneImpl -- listaAgevolazioni -- size:" + listaAgevolazioni.size());
    return listaAgevolazioni;
  }

  private StatoRichiestaAgevolazioneEnum getValueEnumStatoAgevolazione(
      DatiStatoAgevolazioneTariffaria stato) {

    DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum enumStato =
        StatoRichiestaAgevolazioneEnum.NON_PRESENTATA;
    try {
      enumStato =
          DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum.fromValue(
              stato.getStatoRichiestaAgevolazione());
    } catch (Exception e) {
      log.error("Valore ENUM non trovato: " + stato.getStatoRichiestaAgevolazione());
      log.error("errore: ", e);
    }
    return enumStato;
  }

  /**
   * Metodo di supporto, dato l'agevolando restituisci i dati dell'agevolazione per l'anno in input
   *
   * @param iscrittoAgevolandoFiglio
   * @param annoScolastico
   * @return oggetto contenente i dati dell'agevolazione, null altrimenti
   */
  @SuppressWarnings("unused")
  private AgevolazioneTariffariaRistorazione elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici(
      UtenteServiziRistorazione iscrittoAgevolandoFiglio, Long annoScolastico) {
    if (iscrittoAgevolandoFiglio == null) {
      log.error(
          "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici ISCRITTO NULL");
    }

    log.debug("elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici -- ricerca agevolazione... ");

    DatiAgevolazioneTariffaria datiAgevolazioneTariffaria = new DatiAgevolazioneTariffaria();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      datiAgevolazioneTariffaria =
          instance
              .getApiRistorazionePortale()
              .getDomandeAgevolazioneTariffaria(
                  iscrittoAgevolandoFiglio.getCodiceFiscaleImpegnato(),
                  iscrittoAgevolandoFiglio.getCodiceFiscale(),
                  annoScolastico.toString());
      if (datiAgevolazioneTariffaria != null) {
        log.debug("elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici -- TROVATA! ");
        return (new AgevolazioneTariffariaRistorazioneBuilder()
            .setAnnoScolastico(datiAgevolazioneTariffaria.getAnnoScolasticoAgevolazione())
            .setCodiceFiscale(iscrittoAgevolandoFiglio.getCodiceFiscale())
            .setCognome(iscrittoAgevolandoFiglio.getCognome())
            .setDataFineAgevolazione(
                datiAgevolazioneTariffaria.getDataFineAgevolazione() == null
                    ? null
                    : datiAgevolazioneTariffaria.getDataFineAgevolazione())
            .setDataInizioAgevolazione(
                datiAgevolazioneTariffaria.getDataInizioAgevolazione() == null
                    ? null
                    : datiAgevolazioneTariffaria.getDataInizioAgevolazione())
            .setDataNascita(iscrittoAgevolandoFiglio.getDataNascita())
            .setDataPresentazioneAgevolazione(
                datiAgevolazioneTariffaria.getDataPresentazioneAgevolazione() == null
                    ? null
                    : datiAgevolazioneTariffaria.getDataPresentazioneAgevolazione())
            .setDataRichiestaAgevolazione(datiAgevolazioneTariffaria.getDataRichiestaAgevolazione())
            .setDataValiditaAgevolazione(
                datiAgevolazioneTariffaria.getDataValiditaAgevolazione() == null
                    ? null
                    : datiAgevolazioneTariffaria.getDataValiditaAgevolazione())
            .setNome(iscrittoAgevolandoFiglio.getNome())
            .setNumeroProtocolloDsu(datiAgevolazioneTariffaria.getNumeroProtocolloDsu())
            .setOmissioneDifformita(datiAgevolazioneTariffaria.getOmissioneDifformita())
            .setStatoRichiestaAgevolazione(
                StatoRichiestaAgevolazioneEnum.fromValue(
                    datiAgevolazioneTariffaria.getStatoRichiestaAgevolazione()))
            .build());
      }
    } catch (BusinessException e) {
      log.debug(
          "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici -- errore BUSINESS",
          e);
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
    log.debug(
        "elencoRichiestaAgevolazioneUtenteFiglioAnnoSpecifici -- AGEVOLAZIONE NON PRESENTE NON TROVATA ");
    return null;
  }

  /**
   * Metodo di supporto, dato l'agevolando e l'anno restituisci i dati minimali dell'agevolazione
   * per l'anno in input
   *
   * @param iscrittoAgevolandoFiglio
   * @param annoScolastico
   * @return oggetto contenente i dati dello stato della agevolazione, null altrimenti
   */
  private StatoAgevolazione elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici(
      UtenteServiziRistorazione iscrittoAgevolandoFiglio, Long annoScolastico) {
    if (iscrittoAgevolandoFiglio == null) {
      log.error(
          "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici ISCRITTO NULL");
    } else {
      log.debug(
          "elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici -- ricerca stato agevolazione... ");

      ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
      try {
        DatiStatoAgevolazioneTariffaria stato =
            instance
                .getApiRistorazionePortale()
                .getDomandeAgevolazioneTariffariaStato(
                    iscrittoAgevolandoFiglio.getCodiceFiscale(), annoScolastico.toString());

        if (stato == null) {
          throw new BusinessException("stato nullo");
        }

        return (new StatoAgevolazioneBuilder()
            .setAnnoScolastico(annoScolastico.toString())
            .setCodiceFiscale(iscrittoAgevolandoFiglio.getCodiceFiscale())
            .setCognome(iscrittoAgevolandoFiglio.getCognome())
            .setDataNascita(iscrittoAgevolandoFiglio.getDataNascita())
            .setNome(iscrittoAgevolandoFiglio.getNome())
            .setStatoRichiestaAgevolazione(
                StatoRichiestaAgevolazioneEnum.fromValue(stato.getStatoRichiestaAgevolazione()))
            .setCodicePDF(stato.getCodicePdfDomanda())
            .build());
      } catch (BusinessException e) {
        log.debug(
            "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici -- errore BUSINESS",
            e);
      } catch (RuntimeException e) {
        log.error(
            "ServiziRistorazioneImpl -- elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici: errore durante la chiamata delle API Goadev ",
            e);
        throw new RestartResponseAtInterceptPageException(
            new ErroreServiziPage("ristorazione scolastica"));
      } finally {
        log.debug("close connection");
        instance.closeConnection();
      }
    }
    log.debug(
        "elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici -- AGEVOLAZIONE NON PRESENTE NON TROVATA ");
    return null;
  }

  /**
   * Dato l'agevolando restituisci la lista di tutte le ageolazioni fino all'anno corrente a partire
   * dall'anno in input
   *
   * @param iscritto
   * @param annoScolasticoPartenza
   * @return lista oggetti contenente i dati delle agevolazione, potrebbe essere vuota
   */
  @Override
  public List<StatoAgevolazione> elencoRichiesteAgevolazioniConFiglioAnnoPartenza(
      UtenteServiziRistorazione iscritto, Long annoScolasticoPartenza) {
    List<StatoAgevolazione> listaAgevolazioni = new ArrayList<>();
    if (iscritto == null || iscritto.getDataNascita() == null) {
      log.error(
          "ServiziRistorazioneImpl -- elencoRichiesteAgevolazioniConFiglioAnnoPartenza ISCRITTO NULL");
    } else {
      log.debug(
          "ServiziRistorazioneImpl -- elencoRichiesteAgevolazioniConFiglioAnnoPartenza,"
              + "iscritto "
              + iscritto
              + "parti da "
              + annoScolasticoPartenza);

      Long annoScolasticoMinimoPartenzaNascita =
          Long.max(annoScolasticoPartenza, new Long(iscritto.getDataNascita().getYear()));
      Long annoScolasticoMassimo = PageUtil.getLongAnnoPartenzaAnnoScolastico();
      Long numeroAnniVoluti = 5L;
      Long annoScolasticoMinimo =
          Long.max(annoScolasticoMinimoPartenzaNascita, annoScolasticoMassimo - numeroAnniVoluti);

      while (annoScolasticoMassimo > annoScolasticoMinimo) {
        StatoAgevolazione statoAgevolazioneTariffaria =
            elencoRichiestaAgevolazioneMinimaleUtenteFiglioAnnoSpecifici(
                iscritto, annoScolasticoMassimo);
        if (statoAgevolazioneTariffaria != null) {
          log.debug(
              "elencoRichiesteAgevolazioniConFiglioAnnoPartenza --  per l'anno "
                  + annoScolasticoMassimo
                  + " trovata agevolazioneTariffaria: "
                  + statoAgevolazioneTariffaria);
          listaAgevolazioni.add(statoAgevolazioneTariffaria);
        }
        annoScolasticoMassimo--;
      }
      log.debug(
          "elencoRichiesteAgevolazioniConFiglioAnnoPartenza -- "
              + " lista contentente "
              + listaAgevolazioni.size()
              + " oggetti AgevolazioneTariffariaRistorazione ");
    }

    log.debug("CP lista agevolazioni " + listaAgevolazioni);

    return listaAgevolazioni;
  }

  @Override
  public List<AgevolazioneTariffariaRistorazione> elencoRichiesteAgevolazioniThread(
      Utente utente, Long anno) {
    List<AgevolazioneTariffariaRistorazione> ret = new ArrayList<>();

    ExecutorService ex = Executors.newWorkStealingPool();

    Calendar now = Calendar.getInstance();

    List<String> anniScolastici = new ArrayList<>();
    anniScolastici.add(String.valueOf(now.get(Calendar.YEAR) - 1));
    anniScolastici.add(String.valueOf(now.get(Calendar.YEAR)));

    List<Callable<DatiAgevolazioneTariffaria>> tasks = new ArrayList<>();

    for (final String annoScolastico : anniScolastici) {
      for (UtenteServiziRistorazione figlio : utente.getListaFigli()) {
        tasks.add(
            new Callable<DatiAgevolazioneTariffaria>() {

              @Override
              public DatiAgevolazioneTariffaria call() throws Exception {
                DatiAgevolazioneTariffaria res = new DatiAgevolazioneTariffaria();
                ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
                try {
                  DatiStatoAgevolazioneTariffaria stato =
                      instance
                          .getApiRistorazionePortale()
                          .getDomandeAgevolazioneTariffariaStato(
                              figlio.getCodiceFiscale(), annoScolastico);

                  if ((stato != null)
                      && !StatoRichiestaAgevolazioneEnum.NON_PRESENTATA
                          .value()
                          .equalsIgnoreCase(stato.getStatoRichiestaAgevolazione())) {
                    res =
                        instance
                            .getApiRistorazionePortale()
                            .getDomandeAgevolazioneTariffaria(
                                utente.getCodiceFiscaleOperatore(),
                                figlio.getCodiceFiscale(),
                                annoScolastico);
                  }
                } catch (Exception e) {
                  log.debug("ServiziRistorazioneImpl -- elencoRichiesteAgevolazioni -- errore", e);
                  return null;
                } finally {
                  instance.closeConnection();
                }
                log.debug(
                    "ServiziRistorazioneImpl -- elencoRichiesteAgevolazioni -- inserito:"
                        + res.getStatoRichiestaAgevolazione());
                return res;
              }
            });
      }
    }

    try {
      Map<String, AgevolazioneTariffariaRistorazione> mappa = new HashMap<>();
      DemograficoInterface demografico = new DemograficoImpl();
      for (final Future<DatiAgevolazioneTariffaria> future : ex.invokeAll(tasks)) {
        DatiAgevolazioneTariffaria res = future.get();
        if (res != null) {
          for (DatiAgevolazioneTariffariaAgevolando cfa : res.getCodiciFiscaliAgevolandi()) {
            Residente r =
                demografico.getDatiResidenteDaSessione(cfa.getCodiceFiscaleAgevolando(), utente);
            mappa.put(
                res.getAnnoScolasticoAgevolazione()
                    .concat("#")
                    .concat(cfa.getCodiceFiscaleAgevolando()),
                new AgevolazioneTariffariaRistorazioneBuilder()
                    .setAnnoScolastico(res.getAnnoScolasticoAgevolazione())
                    .setCodiceFiscale(cfa.getCodiceFiscaleAgevolando())
                    .setCognome(r.getCpvFamilyName())
                    .setDataFineAgevolazione(
                        res.getDataFineAgevolazione() == null
                            ? null
                            : res.getDataFineAgevolazione())
                    .setDataInizioAgevolazione(
                        res.getDataInizioAgevolazione() == null
                            ? null
                            : res.getDataInizioAgevolazione())
                    .setDataNascita(r.getCpvDateOfBirth())
                    .setDataPresentazioneAgevolazione(
                        res.getDataPresentazioneAgevolazione() == null
                            ? null
                            : res.getDataPresentazioneAgevolazione())
                    .setDataRichiestaAgevolazione(res.getDataRichiestaAgevolazione())
                    .setDataValiditaAgevolazione(
                        res.getDataValiditaAgevolazione() == null
                            ? null
                            : res.getDataValiditaAgevolazione())
                    .setNome(r.getCpvGivenName())
                    .setNumeroProtocolloDsu(res.getNumeroProtocolloDsu())
                    .setOmissioneDifformita(res.getOmissioneDifformita())
                    .setStatoRichiestaAgevolazione(
                        StatoRichiestaAgevolazioneEnum.fromValue(
                            res.getStatoRichiestaAgevolazione()))
                    .build());
          }
        }
      }
      mappa.forEach((key, value) -> ret.add(value));
    } catch (InterruptedException | ExecutionException e) {
      log.error(
          "ServiziRistorazioneImpl -- elencoRichiesteAgevolazioni(Utente) errore nei thread", e);
      ex.shutdown();
    }
    return ret;
  }

  @Override
  public boolean richiediAgevolazione(AgevolazioneStep2 richiesta, Utente utente)
      throws BusinessException, ApiException {

    DatiAgevolazioneTariffaria domandaApi = convertiFormInDomanda(richiesta, utente);
    try {
      DomandaInviata response =
          ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti.getInstance()
              .getApiRistorazionePortale()
              .postDomandeAgevolazioneTariffaria(domandaApi);

      log.debug("CP richiediAgevolazione response = " + response);
      if (LabelFdCUtil.checkIfNotNull(response)) {
        log.debug("Risposta: " + response);
        bloccaAutoDichiarazionePerFiglio(utente, richiesta.getStep1().getListaFigliPerRichiesta());
        return true;
      } else {
        log.error("Errore durante la presentazione della domanda:" + response);
        throw new BusinessException("Errore durante la presentazione della domanda");
      }

      //			if (response.getId().equalsIgnoreCase("OK")) {
      //				log.debug("Risposta: " + response);
      //				bloccaAutoDichiarazionePerFiglio(utente,
      // richiesta.getStep1().getListaFigliPerRichiesta());
      //				return true;
      //			} else {
      //				log.error("Errore durante la presentazione della domanda:" + response);
      //				throw new BusinessException("Errore durante la presentazione della domanda");
      //			}

    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- richiediAgevolazione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("richiesta agevolazione mensa"));
    }
  }

  private DatiAgevolazioneTariffaria convertiFormInDomanda(
      AgevolazioneStep2 richiesta, Utente utente) {

    DatiAgevolazioneTariffaria dati = new DatiAgevolazioneTariffaria();

    Integer annoScolastico = Integer.sum(richiesta.getStep1().getAnnoScolastico(), 1);
    log.debug(
        "CP anno scolastico "
            + richiesta
                .getStep1()
                .getAnnoScolastico()
                .toString()
                .concat("/")
                .concat(annoScolastico.toString().substring(2, 4)));

    dati.setAnnoScolasticoAgevolazione(richiesta.getStep1().getAnnoScolastico().toString());
    dati.setCodiceFiscaleRichiedente(utente.getCodiceFiscaleOperatore());

    dati.setCodiciFiscaliAgevolandi(creaListaCodiciFiscaliAgevolandiDaIseeModi(richiesta));

    dati.setNumeroProtocolloDsu(estraiNumeroProtocolloDsuIseeModi(richiesta));
    dati.setDisoccupatiNucleoFamiliare(creaListaDisoccupatiIseeModi(richiesta));

    dati.setNumTelefonicoRichiedente(richiesta.getTelefonoContatto());
    dati.setEmailRichiedente(richiesta.getEmailContatto());

    if (LabelFdCUtil.checkIfNotNull(richiesta.getNumeroMinoriACarico())) {
      dati.setNumeroMinoriCaricoNucleoFamiliare(
          BigDecimal.valueOf(richiesta.getNumeroMinoriACarico()));
    }

    dati.setDataPresentazioneAgevolazione(estraiDataPresentazioneConIseeModi(richiesta));

    boolean omissione = false;
    if (LabelFdCUtil.checkIfNotNull(
        richiesta
            .getStep1()
            .getAttestazioneIseeModi()
            .getConsultazioneAttestazioneResponse()
            .getConsultazioneAttestazioneResult()
            .getXmlEsitoAttestazioneDecoded()
            .getEsitoAttestazione()
            .getAttestazione()
            .getOmissioneDifformita())) {
      omissione = true;
    }
    dati.setOmissioneDifformita(omissione);

    dati.setAccettazioneNucleoIseeAnagrafico(richiesta.getStep1().getAccettazioneNuclei());

    boolean iseeNonCalcolabile = false;

    if (LabelFdCUtil.checkIfNotNull(richiesta.getStep1().getListaFigliPerRichiesta())
        && richiesta.getStep1().getListaFigliPerRichiesta().stream()
            .filter(
                elem ->
                    elem.getIseeNonCalcolabile() != null && elem.getIseeNonCalcolabile() == true)
            .findAny()
            .isPresent()) {
      iseeNonCalcolabile = true;
    }
    dati.setIseeNonCalcolabile(iseeNonCalcolabile);

    log.debug("CP dati convertiFormInDomanda = " + dati);

    return dati;
  }

  private String estraiNumeroProtocolloDsuIseeModi(AgevolazioneStep2 richiesta) {
    if (LabelFdCUtil.checkIfNotNull(richiesta)
        && LabelFdCUtil.checkIfNotNull(richiesta.getStep1())
        && LabelFdCUtil.checkIfNotNull(richiesta.getStep1().getAttestazioneIseeModi())
        && LabelFdCUtil.checkIfNotNull(
            richiesta.getStep1().getAttestazioneIseeModi().getConsultazioneAttestazioneResponse())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione())) {
      return richiesta
          .getStep1()
          .getAttestazioneIseeModi()
          .getConsultazioneAttestazioneResponse()
          .getConsultazioneAttestazioneResult()
          .getXmlEsitoAttestazioneDecoded()
          .getEsitoAttestazione()
          .getAttestazione()
          .getNumeroProtocolloDSU();
    } else {
      return null;
    }
  }

  private LocalDate estraiDataPresentazioneConIseeModi(AgevolazioneStep2 richiesta) {
    if (LabelFdCUtil.checkIfNotNull(richiesta)
        && LabelFdCUtil.checkIfNotNull(richiesta.getStep1())
        && LabelFdCUtil.checkIfNotNull(richiesta.getStep1().getAttestazioneIseeModi())
        && LabelFdCUtil.checkIfNotNull(
            richiesta.getStep1().getAttestazioneIseeModi().getConsultazioneAttestazioneResponse())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione())
        && LabelFdCUtil.checkIfNotNull(
            richiesta
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione())) {

      return richiesta
          .getStep1()
          .getAttestazioneIseeModi()
          .getConsultazioneAttestazioneResponse()
          .getConsultazioneAttestazioneResult()
          .getXmlEsitoAttestazioneDecoded()
          .getEsitoAttestazione()
          .getAttestazione()
          .getDataPresentazione();

    } else {
      return null;
    }
  }

  private List<DatiAgevolazioneTariffariaDisoccupato> creaListaDisoccupatiIseeModi(
      AgevolazioneStep2 richiesta) {
    List<DatiAgevolazioneTariffariaDisoccupato> lista = new ArrayList<>();

    // TODO tapullo per Goadev in attesa di ritorno del programmatore

    // DatiAgevolazioneTariffariaDisoccupato disoccupato1 = new
    // DatiAgevolazioneTariffariaDisoccupato();
    // DatiAgevolazioneTariffariaDisoccupato disoccupato2 = new
    // DatiAgevolazioneTariffariaDisoccupato();

    // if(LabelFdCUtil.checkIfNotNull(richiesta.getDataDisoccupazioneGenitore1())) {
    // if (richiesta.getComboDisoccupazioneIseeModiGenitore1() != null
    // && richiesta.getComboDisoccupazioneIseeModiGenitore1() != null) {
    // disoccupato1.setCodiceFiscaleDisoccupato(
    // richiesta.getComboDisoccupazioneIseeModiGenitore1().getCodiceFiscale());
    //
    // if(LabelFdCUtil.checkIfNotNull(richiesta.getDataDisoccupazioneGenitore1())) {
    // disoccupato1
    // .setDataInizioDisoccupazione(convertiInOffSetDateTime(richiesta.getDataDisoccupazioneGenitore1()));
    // }
    //
    // lista.add(disoccupato1);
    // }
    // }

    // if(LabelFdCUtil.checkIfNotNull(richiesta.getDataDisoccupazioneGenitore2())){
    // if (richiesta.getComboDisoccupazioneIseeModiGenitore2() != null
    // && richiesta.getComboDisoccupazioneIseeModiGenitore2() != null) {
    // disoccupato2.setCodiceFiscaleDisoccupato(
    // richiesta.getComboDisoccupazioneIseeModiGenitore2().getCodiceFiscale());
    //
    // if(LabelFdCUtil.checkIfNotNull(richiesta.getDataDisoccupazioneGenitore2())){
    // disoccupato2
    // .setDataInizioDisoccupazione(convertiInOffSetDateTime(richiesta.getDataDisoccupazioneGenitore2()));
    // }
    //
    // lista.add(disoccupato2);
    // }
    // }

    log.debug("CP richiesta.getListaMembriNucleo() = " + richiesta.getListaMembriNucleo());

    for (MembroNucleoChePercepivaReddito elem : richiesta.getListaMembriNucleo()) {

      // TODO con tapullo per Goadev

      if (LabelFdCUtil.checkIfNotNull(elem.getDataDisoccupazioneMembroNucleo())) {
        DatiAgevolazioneTariffariaDisoccupato disoccupato =
            new DatiAgevolazioneTariffariaDisoccupato();

        disoccupato.setCodiceFiscaleDisoccupato(elem.getDatiMembroNucleo().getCodiceFiscale());
        disoccupato.setDataInizioDisoccupazione(elem.getDataDisoccupazioneMembroNucleo());

        lista.add(disoccupato);
      }
    }

    richiesta.setListaMembriConDataDisoccupazione(lista);

    log.debug("CP lista disoccupati = " + lista);

    return lista;
  }

  private OffsetDateTime convertiInOffSetDateTime(LocalDate dataDisoccupazione1) {
    return LocalDateUtil.getOffsetDateTime(dataDisoccupazione1);
  }

  @SuppressWarnings("unused")
  private OffsetDateTime convertiInOffSetDateTime(Date data) {
    Date newDate = DateUtils.addHours(data, 3);
    if (data != null) {
      return newDate.toInstant().atOffset(ZoneOffset.UTC);
    } else {
      return null;
    }
  }

  private List<DatiAgevolazioneTariffariaAgevolando> creaListaCodiciFiscaliAgevolandiDaIseeModi(
      AgevolazioneStep2 richiesta) {

    List<DatiAgevolazioneTariffariaAgevolando> lista = new ArrayList<>();

    for (AgevolazioneTariffariaRistorazione agevolando :
        richiesta.getStep1().getListaFigliPerRichiesta()) {

      DatiAgevolazioneTariffariaAgevolando datiAgevolando =
          new DatiAgevolazioneTariffariaAgevolando();

      if (agevolando.isSelezionato()) {

        String codiceFiscale = agevolando.getCodiceFiscale();
        datiAgevolando.setCodiceFiscaleAgevolando(codiceFiscale);

        String isee = agevolando.getImportoIndicatoreIseeBambino();
        if (PageUtil.isStringValid(isee)) {
          Double iseeMinorenne = Double.parseDouble(isee);
          BigDecimal iseeMinorenneBigDecimal = BigDecimal.valueOf(iseeMinorenne);
          datiAgevolando.setIseeAgevolando(iseeMinorenneBigDecimal);
        }

        lista.add(datiAgevolando);
      }
    }
    return lista;
  }

  @Override
  public List<AgevolazioneTariffariaRistorazione> incrociaConIseeModi(
      List<AgevolazioneTariffariaRistorazione> lista,
      List<NucleoFamiliareComponenteNucleoInner> listaFigliComponenteNucleoIseeModi) {

    log.debug("incrociaConIseeModi nuovo");

    List<AgevolazioneTariffariaRistorazione> listaIncrocioConIsee = new ArrayList<>();

    for (AgevolazioneTariffariaRistorazione agevolazione : lista) {
      Boolean found = false;

      for (NucleoFamiliareComponenteNucleoInner elemIsee : listaFigliComponenteNucleoIseeModi) {
        if (agevolazione.getCodiceFiscale().equalsIgnoreCase(elemIsee.getCodiceFiscale()))
          found = true;
      }
      if (found) {
        listaIncrocioConIsee.add(agevolazione);
      }
    }

    return listaIncrocioConIsee;
  }

  @Override
  public byte[] getPdfIscrizione(String codicePDF) throws BusinessException, ApiException {
    log.debug("getPdfIscrizione:" + codicePDF);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String sResponseBase64 =
          instance
              .getApiRistorazionePortale()
              .getDomandeIscrizioneServiziRistorazioneStatoPdf(codicePDF);
      return DatatypeConverter.parseBase64Binary(sResponseBase64);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfIscrizione Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfIscrizione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } catch (Exception e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfIscrizione Errore nella Response:" + e.getMessage(), e);
      throw new BusinessException(e.getMessage());
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getPdfRichiestaAgevolazione(String codicePDF)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      String sResponseBase64 =
          instance.getApiRistorazionePortale().getDomandeAgevolazioneTariffariaStatoPdf(codicePDF);
      if (sResponseBase64.startsWith("<jsonValue>")) {
        log.debug("trovato jsonValue");
        sResponseBase64 = sResponseBase64.substring("<jsonValue>".length() - 1);
      }
      return DatatypeConverter.parseBase64Binary(sResponseBase64);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getPdfRichiestaAgevolazione:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfRichiestaAgevolazione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  private void bloccaAutoDichiarazionePerFiglio(
      Utente utente, List<AgevolazioneTariffariaRistorazione> listaFigliPerRichiesta) {

    for (AgevolazioneTariffariaRistorazione agevolazione : listaFigliPerRichiesta) {
      String cfFiglioInAgevolazione = agevolazione.getCodiceFiscale();
      ComponenteNucleo componenteNucleo =
          trovaFiglioAutodichiaratoByCodiceFiscale(utente, cfFiglioInAgevolazione);
      if (componenteNucleo != null) {
        String cfFiglioInAutodichiarazione = componenteNucleo.getDatiCittadino().getCpvTaxCode();
        if (cfFiglioInAgevolazione.equalsIgnoreCase(cfFiglioInAutodichiarazione)) {
          bloccoAutodichiarazione(utente, componenteNucleo);
        }
      }
    }
  }

  private void bloccoAutodichiarazione(Utente utente, ComponenteNucleo nucleo) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {

      CitizenResponse response =
          instance
              .getApiFamily()
              .updateBloccoAutodichiarazione(
                  nucleo.getIdPerson(), utente.getIdAnonimoComuneGenova(), true);
      if (response.getEsito() != null && !response.getEsito().isEsito()) {
        log.debug("Update autodichiarzione fallita:" + response.getEsito().getDescrizione());
        log.debug("Update autodichiarzione fallita:" + response.getEsito().getEccezione());
      }
    } catch (BusinessException e) {
      log.error("Errore durante l'update: ", e);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public boolean presentaDomandaIscrizione(
      DatiDomandaIscrizioneServiziRistorazione domanda, Utente utente)
      throws BusinessException, ApiException {

    ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti instance =
        ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti.getInstance();
    try {
      instance.getApiRistorazionePortale().postDomandaIscrizione(domanda);
      ComponenteNucleo componenteNucleo =
          trovaFiglioAutodichiaratoByCodiceFiscale(utente, domanda.getCodiceFiscaleIscrivendo());
      if (componenteNucleo != null) {
        bloccoAutodichiarazione(utente, componenteNucleo);
      }
    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- presentaDomandaIscrizione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("iscrizione mensa"));
    } finally {
      instance.closeConnection();
    }
    return true;
  }

  @Override
  public boolean presentaDomandaChiusuraIscrizione(DatiDomandaChiusuraServiziRistorazione domanda)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      instance.getApiRistorazionePortale().postDomandaChiusuraIscrizione(domanda);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- presentaDomandaChiusuraIscrizione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("chiusura iscrizione mensa"));
    } finally {
      instance.closeConnection();
    }
    return true;
  }

  private ComponenteNucleo trovaFiglioAutodichiaratoByCodiceFiscale(
      Utente utente, String codiceFiscaleIscrivendo) {

    List<ComponenteNucleo> lista = utente.getNucleoFamiliareAllargato();
    for (ComponenteNucleo componenteNucleo : lista) {
      log.debug(
          "trovaFiglioAutodichiaratoByCodiceFiscale --- componenteNucleo=" + componenteNucleo);
      if (componenteNucleo != null)
        if (componenteNucleo.getAutodichiarazioneFiglio() != null
            && componenteNucleo.getAutodichiarazioneFiglio() > 0L) {
          String cfFiglioInAutodichiarazione = componenteNucleo.getDatiCittadino().getCpvTaxCode();
          if (cfFiglioInAutodichiarazione.equalsIgnoreCase(codiceFiscaleIscrivendo))
            return componenteNucleo;
        }
    }
    return null;
  }

  @Override
  public String getSituazioneSintenticoPagamenti(String codiceFiscale)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DatiPagamentiSintetico dati =
          instance.getApiRistorazionePortale().getBollettiniSintetico(codiceFiscale);
      if (dati != null) {
        return dati.getSituazionePagamenti();
      }
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getSituazioneSintenticoPagamenti:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getSituazioneSintenticoPagamenti: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("bollettini ristorazione scolastica"));
    } catch (Exception e) {
      log.error("Errore generico:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }

    return null;
  }

  @Override
  public List<DatiPagamentiBollettiniRiepilogativiEstesi> getBollettiniImpegnato(Utente utente)
      throws ApiException, BusinessException {
    log.debug("call getBollettiniImpegnato, cf = " + utente.getCodiceFiscaleOperatore());
    return getListaBollettiniByCfImpegnato(utente);
  }

  /*
   * @Override public List<DatiPagamentiBollettiniRiepilogativiEstesi>
   * getBollettiniImpegnatoByAnno(Utente utente, Long anno) throws ApiException,
   * BusinessException { List<DatiPagamentiBollettiniRiepilogativiEstesi>
   * listDatiPagamentiBollettini = getListaBollettiniByCfImpegnato( utente);
   * return listDatiPagamentiBollettini.stream() .filter(dato ->
   * dato.getAnnoRiferimento().equals(anno)) .collect(Collectors.toList()); }
   */

  @Override
  public DatiStatoDomandaChiusuraServiziRistorazione getDatiChiusuraIscritto(String codiceFiscale)
      throws ApiException, BusinessException {
    log.debug("DatiStatoDomandaChiusuraServiziRistorazione:" + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDomandaChiusuraIscrizioneStato(codiceFiscale);
    } catch (WebApplicationException e) {
      log.error("getPdfBollettinoPagamentoMensa Errore nella Response:" + e.getMessage(), e);
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfBollettinoPagamentoMensa: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      instance.closeConnection();
    }
    return null;
  }

  @SuppressWarnings("unused")
  private byte[] getPdfBollettinoPagamentoMensa(String idBollettino)
      throws BusinessException, ApiException {
    log.debug("getPdfBollettinoPagamentoMensa:" + idBollettino);

    byte[] pdfBollettino = null;
    ServiceLocatorLivelloUnoGoadevBollettino instance =
        ServiceLocatorLivelloUnoGoadevBollettino.getInstance();
    try {
      String sResponseBase64 = instance.getApiRistorazionePortale().getBolletinoPdf(idBollettino);
      pdfBollettino = DatatypeConverter.parseBase64Binary(sResponseBase64);
      return pdfBollettino;

    } catch (WebApplicationException e) {
      log.error("getPdfBollettinoPagamentoMensa Errore nella Response 1:" + e.getMessage(), e);
      return pdfBollettino;
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfBollettinoPagamentoMensa: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } catch (Exception e) {
      log.error("getPdfBollettinoPagamentoMensa Errore nella Response 2:" + e.getMessage(), e);
      return pdfBollettino;
    } finally {
      instance.closeConnection();
    }
  }

  /**
   * Metodo di supporto per get lista
   *
   * @param codiceFiscale
   * @return
   * @throws BusinessException
   */
  private List<DatiPagamentiBollettiniRiepilogativiEstesi> getListaBollettiniByCfImpegnato(
      Utente utente) throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug(
          "enter getListaBollettiniByCfImpegnato, cf = " + utente.getCodiceFiscaleOperatore());

      List<DatiPagamentiBollettiniRiepilogativi> listaDatiPagamentiBollettinoRiepilogativi =
          instance.getApiRistorazionePortale().getBollettini(utente.getCodiceFiscaleOperatore());

      List<DatiPagamentiBollettiniRiepilogativiEstesi> listaDatiPagamentiBollettino =
          new ArrayList<DatiPagamentiBollettiniRiepilogativiEstesi>();

      for (DatiPagamentiBollettiniRiepilogativi bollettino :
          listaDatiPagamentiBollettinoRiepilogativi) {
        DatiPagamentiBollettiniRiepilogativiEstesi datiPagamentiBollettiniRiepilogativiEstesi =
            new DatiPagamentiBollettiniRiepilogativiEstesi(bollettino);

        String idenficativoDebitoConNumeroRata =
            bollettino.getIdentificativoDebito().trim().concat("_0");
        Thread.sleep(50);
        boolean attualizzaPagamento = true;
        Debito debitoMIP =
            ServiceLocator.getInstance()
                .getServiziMipVerticali()
                .getDebitoByCfServizioAnnoNumeroDocumento(
                    bollettino.getCfImpegnato(),
                    "SCUOLA_RISTO",
                    bollettino.getAnnoRiferimento().longValue(),
                    idenficativoDebitoConNumeroRata,
                    attualizzaPagamento);
        Thread.sleep(50);
        boolean ottieniPdf = false;
        AvvisoPagamento avvisoMip =
            ServiceLocator.getInstance()
                .getServiziMipVerticali()
                .getAvvisoPagamento(
                    bollettino.getCfImpegnato(),
                    "SCUOLA_RISTO",
                    bollettino.getAnnoRiferimento().longValue(),
                    idenficativoDebitoConNumeroRata,
                    ottieniPdf,
                    attualizzaPagamento);
        Thread.sleep(50);
        if (debitoMIP != null) {
          datiPagamentiBollettiniRiepilogativiEstesi.setCodiceAvviso(debitoMIP.getCodiceAvviso());

          if (avvisoMip.getCodiceAvviso() != null && !avvisoMip.getCodiceAvviso().isEmpty()) {
            String iuvCalcolatoDaCodiceAvviso =
                avvisoMip.getCodiceAvviso().substring(3, avvisoMip.getCodiceAvviso().length());
            log.debug("CP iuvCalcolatoDaCodiceAvviso = " + iuvCalcolatoDaCodiceAvviso);
            datiPagamentiBollettiniRiepilogativiEstesi.setIuv(iuvCalcolatoDaCodiceAvviso);
          }
          // PER RID
          if (LabelFdCUtil.checkIfNotNull(debitoMIP)
              && LabelFdCUtil.checkIfNotNull(debitoMIP.getIuv())
              && debitoMIP.getIuv().equalsIgnoreCase("-")) {
            datiPagamentiBollettiniRiepilogativiEstesi.setIuv(debitoMIP.getIuv());
          }

          boolean isBollettinoPagato = false;
          if (debitoMIP.getEsitoPagamento() != null
              && (debitoMIP
                      .getEsitoPagamento()
                      .toString()
                      .equalsIgnoreCase(EsitoPagamento.OK.toString())
                  || debitoMIP
                      .getEsitoPagamento()
                      .toString()
                      .equalsIgnoreCase(EsitoPagamento.CODE9.toString()))) {
            isBollettinoPagato = true;
          }
          if (debitoMIP.getEsitoPagamento() == null
              && (debitoMIP.getImportoDaPagare() != null
                  && debitoMIP.getImportoDaPagare() == 0.0)) {
            isBollettinoPagato = true;
          }
          datiPagamentiBollettiniRiepilogativiEstesi.setIsPagato(isBollettinoPagato);
          Attualizzato attualizzato = debitoMIP.getAttualizzato();
          if (attualizzato != null) {
            datiPagamentiBollettiniRiepilogativiEstesi.setAttualizzato(attualizzato.toString());
          }
          Double importoMIP = 0.0;
          if (debitoMIP.getImportoDaPagare() != null) {
            importoMIP = debitoMIP.getImportoDaPagare();
          }
          datiPagamentiBollettiniRiepilogativiEstesi.setImportoMIP(importoMIP);
        }

        listaDatiPagamentiBollettino.add(datiPagamentiBollettiniRiepilogativiEstesi);
      }

      if (listaDatiPagamentiBollettino != null) {
        listaDatiPagamentiBollettino =
            listaDatiPagamentiBollettino.stream()
                .filter(dato -> dato.getDataProtocolloEmissione() != null)
                .sorted(
                    Comparator.comparing(
                            DatiPagamentiBollettiniRiepilogativiEstesi::getDataProtocolloEmissione)
                        .reversed())
                .collect(Collectors.toList());
      }

      return listaDatiPagamentiBollettino;
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getListaBollettiniByCfImpegnato:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaBollettiniByCfImpegnato: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("bollettini ristorazione scolastica"));
    } catch (Exception e) {
      log.error("Errore generico:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiPagamentiPartitario> getPartitarioIscritto(String codiceFiscale)
      throws ApiException, BusinessException {
    return getListaPartitarioByCfIscritto(codiceFiscale);
  }

  @Override
  public List<DatiStatoPagamenti> getListaSituazioneAnniPartitario(String codiceFiscale)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("getListaSituazioneAnniPartitario: " + codiceFiscale);
      DatiPagamentiPartitarioEstesi datiPagamentiPartitarioEstesi =
          instance.getApiRistorazionePortale().getPartitario(codiceFiscale);

      List<DatiStatoPagamenti> listaStatoPagamenti = new ArrayList<>();

      if (datiPagamentiPartitarioEstesi.getListaStatoPagamenti() != null) {
        listaStatoPagamenti = datiPagamentiPartitarioEstesi.getListaStatoPagamenti();
      }

      return listaStatoPagamenti;
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getListaSituazioneAnniPartitario:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaSituazioneAnniPartitario: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getListaSituazioneAnniPartitario:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiPagamentiPartitario> getPartitarioIscrittoByAnno(String codiceFiscale, Long anno)
      throws ApiException, BusinessException {
    List<DatiPagamentiPartitario> listDatiPagamentiPartitario =
        getListaPartitarioByCfIscritto(codiceFiscale);
    String sAnno = anno.toString();
    return listDatiPagamentiPartitario.stream()
        .filter(dato -> dato.getAnno().equalsIgnoreCase(sAnno))
        .collect(Collectors.toList());
  }

  /**
   * Metodo di supporto per get lista
   *
   * @param codiceFiscale
   * @return
   * @throws BusinessException
   */
  private List<DatiPagamentiPartitario> getListaPartitarioByCfIscritto(String codiceFiscale)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      log.debug("getListaPartitarioByCfIscritto:" + codiceFiscale);
      DatiPagamentiPartitarioEstesi datiPagamentiPartitarioEstesi =
          instance.getApiRistorazionePortale().getPartitario(codiceFiscale);

      List<DatiPagamentiPartitario> listaDatiPagamentiPartitario =
          datiPagamentiPartitarioEstesi.getListaDatiPartitario();

      if (listaDatiPagamentiPartitario != null) {
        listaDatiPagamentiPartitario =
            listaDatiPagamentiPartitario.stream()
                .filter(dato -> dato.getDataOperazione() != null)
                .sorted(Comparator.comparing(DatiPagamentiPartitario::getDataOperazione).reversed())
                .collect(Collectors.toList());
      }
      return listaDatiPagamentiPartitario;
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getListaPartitarioByCfIscritto:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaPartitarioByCfIscritto: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("partitario"));
    } catch (Exception e) {
      log.error("Errore generico:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public byte[] getPdfBollettino(String idBollettino) throws BusinessException, ApiException {
    log.debug("getPdfBollettino, id:" + idBollettino);

    byte[] pdfBollettino = null;

    try {
      String pdfStringBase64 =
          ServiceLocatorLivelloUnoGoadevBollettino.getInstance()
              .getApiRistorazionePortale()
              .getBolletinoPdf(idBollettino);
      pdfBollettino = DatatypeConverter.parseBase64Binary(pdfStringBase64);

    } catch (WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl getPdfBollettino Errore nella Response:" + e.getMessage(), e);
      // throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfBollettino: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("bollettini mensa scolastica"));
    } catch (Exception e) {
      log.error(
          "ServiziRistorazioneImpl getPdfBollettino Errore nella Response:" + e.getMessage(), e);
      // throw new BusinessException(e.getMessage());
    }

    return pdfBollettino;
  }

  @Override
  public byte[] getPdfRicevuta(String idDebito) throws BusinessException, ApiException {
    return null;
  }

  @Override
  public DatiStatoDomandaChiusuraServiziRistorazione getDatiStatoDomandaChiusuraServiziRistorazione(
      String codiceFiscale) throws ApiException, BusinessException {
    log.debug("CP getDatiStatoDomandaChiusuraServiziRistorazione " + codiceFiscale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDomandaChiusuraIscrizioneStato(codiceFiscale);
    } catch (BusinessException e) {
      log.error("Errore getDatiStatoDomandaChiusuraServiziRistorazione:", e);
      throw new BusinessException("Errore di connessione alle API");
    } catch (WebApplicationException e) {
      log.error(
          "Errore nella Response getDatiStatoDomandaChiusuraServiziRistorazione:" + e.getMessage(),
          e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiStatoDomandaChiusuraServiziRistorazione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiDietaSpeciale> getDieteSpeciali(String codiceFiscaleIscritto)
      throws ApiException, BusinessException {
    log.debug("CP getDieteSpeciali = " + codiceFiscaleIscritto);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiDieteSpeciali().getDieteSpeciali(codiceFiscaleIscritto);
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDieteSpeciali: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDieteSpeciali: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("diete speciali"));
    } catch (Exception e) {
      log.error("Errore generico getDieteSpeciali:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getFilePdfDieteSpeciali(IdentificativoPdf identificativoPdf)
      throws ApiException, BusinessException {
    log.debug(
        "CP getFilePdfDieteSpeciali = "
            + identificativoPdf.getId()
            + " "
            + identificativoPdf.getTipoDocumento());
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiDieteSpeciali()
          .getDieteSpecialiPdf(identificativoPdf.getTipoDocumento(), identificativoPdf.getId());
    } catch (BusinessException e) {
      log.error("Errore getFilePdfDieteSpeciali:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getFilePdfDieteSpeciali:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getFilePdfDieteSpeciali: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("diete speciali"));
    } catch (Exception e) {
      log.error("Errore generico getFilePdfDieteSpeciali:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DietaMotiviEticoReligiosi> getListaMenuMotiviReligiosi()
      throws ApiException, BusinessException {
    log.debug("CP getListaMenuMotiviReligiosi");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiDieteSpeciali().getMenuMotiviEticoReligiosi();
    } catch (BusinessException e) {
      log.error("Errore getListaMenuMotiviReligiosi:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response getListaMenuMotiviReligiosi:" + e.getMessage(), e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaMenuMotiviReligiosi: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("diete speciali"));
    } catch (Exception e) {
      log.error("Errore generico getListaMenuMotiviReligiosi:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public void richiediDietaSpeciale(DietaSpeciale dietaSpeciale)
      throws ApiException, BusinessException {
    log.debug("CP richiediDietaSpeciale " + dietaSpeciale);

    try {
      DatiDomandaRichiestaDietaSpeciale datiDomanda = new DatiDomandaRichiestaDietaSpeciale();

      datiDomanda.setCodiceFiscaleIscritto(dietaSpeciale.getCfIscritto());
      datiDomanda.setNomeIscritto(dietaSpeciale.getNomeIscritto());
      datiDomanda.setCognomeIscritto(dietaSpeciale.getCognomeIscritto());

      datiDomanda.setCodiceFiscaleRichiedente(dietaSpeciale.getCfRichiedente());
      datiDomanda.setNomeRichiedente(dietaSpeciale.getNomeRichiedente());
      datiDomanda.setCognomeRichiedente(dietaSpeciale.getCognomeRichiedente());
      datiDomanda.setEmailRichiedente(dietaSpeciale.getEmailRichiedente());

      datiDomanda.setDataRichiesta(dietaSpeciale.getDataRichiesta());

      datiDomanda.setTipoDietaSpeciale(dietaSpeciale.getTipoDieta());

      datiDomanda.setNoteIntegrative(dietaSpeciale.getNoteIntegrative());

      if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE)) {
        DietaMotiviSanitari dietaMotiviSanitari = new DietaMotiviSanitari();

        dietaMotiviSanitari.setEpisodiAnafilassi(dietaSpeciale.isAnafilassi());

        FileAllegato fileAllegato = new FileAllegato();
        fileAllegato.setFile(dietaSpeciale.getByteFileAttestazioneMedica());
        fileAllegato.setNomeFile(dietaSpeciale.getNomeFileAttestazioneMedica());
        fileAllegato.setEstensioneFile(dietaSpeciale.getEstensioneFileAttestazioneMedica());
        fileAllegato.setMimeType(dietaSpeciale.getMimeTypeFileAttestazioneMedica());

        dietaMotiviSanitari.setPdfAttestazioneMedica(fileAllegato);

        datiDomanda.setDietaMotiviSanitari(dietaMotiviSanitari);
      }

      if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI)) {
        datiDomanda.setDietaMotiviReligiosi(dietaSpeciale.getComboMenu().getCodice());
      }

      /*
       * String scuola = dietaSpeciale.getScuola(); String[] scuolaSplitted = null;
       * String codiceScuola = ""; if (LabelFdCUtil.checkIfNotNull(scuola)) {
       * scuolaSplitted = scuola.split("-"); } if
       * (LabelFdCUtil.checkIfNotNull(scuolaSplitted)) { codiceScuola =
       * scuolaSplitted[scuolaSplitted.length - 1]; }
       * datiDomanda.setCodiceScuola(codiceScuola);
       */

      // datiDomanda.setCodiceDirezioneTerritoriale(dietaSpeciale.getDatiDirezioneTerritoriale().getCodDirezTerr());
      datiDomanda.setCodiceScuola(dietaSpeciale.getDatiIstituto().getCodScuola());
      datiDomanda.setClasse(dietaSpeciale.getClasse());
      datiDomanda.setSezione(dietaSpeciale.getSezione());

      datiDomanda.setAnnoScolastico(dietaSpeciale.getAnnoScolastico());

      /*
       * if (LabelFdCUtil.checkIfNotNull(dietaSpeciale.getListaGiorniRientri())) {
       * List<GiorniRientroScuola> listaGiorniSelezionati =
       * dietaSpeciale.getListaGiorniRientri().stream() .filter(elem ->
       * elem.isSelezionato()).collect(Collectors.toList()); for (GiorniRientroScuola
       * elem : listaGiorniSelezionati) { GiornoRientro giornoRientro = new
       * GiornoRientro(); giornoRientro.setRientro(elem.getGiornoRientro());
       * listaRientri.add(giornoRientro); } }
       */

      List<GiornoRientro> listaRientri = new ArrayList<GiornoRientro>();

      if (LabelFdCUtil.checkIfNotNull(dietaSpeciale.getListaGiorniRientriSelezionati())
          && dietaSpeciale.getListaGiorniRientriSelezionati().size() > 0) {

        dietaSpeciale
            .getListaGiorniRientriSelezionati()
            .forEach(
                giorno -> {
                  GiornoRientro giornoRientro = new GiornoRientro();
                  giornoRientro.setRientro(giorno);
                  listaRientri.add(giornoRientro);
                });
      }

      datiDomanda.setGiorniRientro(listaRientri);

      log.debug("CP datiDomanda prima di post = " + datiDomanda);

      ServiceLocatorLivelloUnoGoadevBollettino.getInstance()
          .getApiRistorazionePortale()
          .postRichiestaDietaSpeciale(datiDomanda);

    } catch (BusinessException e) {
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziRistorazioneImpl -- richiediDietaSpeciale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- richiediDietaSpeciale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- richiediDietaSpeciale: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("dieta speciale"));
    }
  }

  @Override
  public void revocaDietaSpeciale(
      String codiceFiscaleIscritto,
      String identificativoDomanda,
      String codiceFiscaleRichiedenteRevoca,
      String nomeRichiedenteRevoca,
      String cognomeRichiedenteRevoca,
      String emailRichiedenteRevoca)
      throws ApiException, BusinessException {
    log.debug("CP revocaDietaSpeciale " + identificativoDomanda);

    try {
      DatiRevocaRichiestaDietaSpeciale revoca = new DatiRevocaRichiestaDietaSpeciale();
      revoca.setCodiceFiscaleIscritto(codiceFiscaleIscritto);
      revoca.setIdentificativo(identificativoDomanda);
      revoca.setCodiceFiscaleRichiedenteRevoca(codiceFiscaleRichiedenteRevoca);
      revoca.setNomeRichiedenteRevoca(nomeRichiedenteRevoca);
      revoca.setCognomeRichiedenteRevoca(cognomeRichiedenteRevoca);
      revoca.setEmailRichiedenteRevoca(emailRichiedenteRevoca);

      ServiceLocatorLivelloUnoGoadevBollettino.getInstance()
          .getApiRistorazionePortale()
          .postRevocaDieta(revoca);
    } catch (BusinessException e) {
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziRistorazioneImpl -- revocaDietaSpeciale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- revocaDietaSpeciale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- revocaDietaSpeciale: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("revoca dieta speciale"));
    }
  }

  @Override
  public String getDescrizioneScuolaDaCodiceScuola(String codiceScuola)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDescrizioneScuolaDaCodiceScuola " + codiceScuola);

    try {
      String descrizioneScuola = "";
      Istituto elementoCommissioniMensa = new Istituto();

      List<Istituto> listaIstituiti =
          ServiceLocator.getInstance().getServiziCommissioniMensa().getIstituti();

      if (LabelFdCUtil.checkIfNotNull(listaIstituiti)
          && !LabelFdCUtil.checkEmptyList(listaIstituiti)) {
        elementoCommissioniMensa =
            listaIstituiti.stream()
                .filter(elem -> elem.getCodScuola().equalsIgnoreCase(codiceScuola))
                .findAny()
                .orElse(null);
      }
      if (LabelFdCUtil.checkIfNotNull(elementoCommissioniMensa)) {
        descrizioneScuola = elementoCommissioniMensa.getDescScuola();
      }
      return descrizioneScuola;

    } catch (BusinessException e) {
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDescrizioneScuolaDaCodiceScuola: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDescrizioneScuolaDaCodiceScuola: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDescrizioneScuolaDaCodiceScuola: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    }
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRiepilogo() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));

    return listaBreadcrumb;
  }

  private void addHomeBreadcrumb(List<BreadcrumbFdC> listaBreadcrumb) {
    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbBambiniAScuola() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAttestazioniPagamento() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("attestazioniDiPagamento", "Attestazioni di pagamento"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbMieiFigli() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("imieifigli", "I miei figli"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPresenzeMensa() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("mensa", "Presenze in mensa e menù"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPagamenti() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("pagamentoiscrittomensa", "Pagamenti ristorazione e servizi comunali"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbMovimenti() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("partitarioiscrittomensa", "Movimenti pagamenti"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbVerificaIscrizione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("statoiscrizione", "Verifica iscrizione ristorazione"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbDieteSpeciali() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("dieteSpeciali", "Diete speciali"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRichiestaDieteSpeciali() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    // listaBreadcrumb.add(new BreadcrumbFdC("dieteSpeciali", "Diete speciali"));
    listaBreadcrumb.add(new BreadcrumbFdC("richiestaDietaSpeciale", "Richiesta"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbVerbaliCommissioniMensa() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("commissioneMensa", "Verbali di commissione mensa"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbStorico() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("statoagevolazione", "Storico richieste agevolazione tariffaria"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio(
        "Le attestazioni di pagamento sono utilizzabili ai fini della dichiarazione dei redditi.");
    messaggio.setType("info");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbAgevolazione() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("bambiniascuola", "Bambini a scuola"));
    listaBreadcrumb.add(new BreadcrumbFdC("agevolazione", "Richiesta di agevolazione tariffaria"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbHome() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    addHomeBreadcrumb(listaBreadcrumb);
    return listaBreadcrumb;
  }

  @Override
  public List<AttestazioniDiPagamento> getAttestazioniPagamento(String codiceFiscaleIscritto)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAttestazioniPagamento = " + codiceFiscaleIscritto);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      List<AttestazioniDiPagamento> listaAttestazioni =
          instance.getApiRistorazionePortale().getAttestazioniDiPagamento(codiceFiscaleIscritto);

      List<AttestazioniDiPagamento> listaAttestazioniOrdinatePerData =
          new ArrayList<AttestazioniDiPagamento>();

      if (LabelFdCUtil.checkIfNotNull(listaAttestazioni)) {
        listaAttestazioniOrdinatePerData =
            listaAttestazioni.stream()
                .sorted(Comparator.comparing(AttestazioniDiPagamento::getAnnoSolare).reversed())
                .collect(Collectors.toList());
      }

      return listaAttestazioniOrdinatePerData;

    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getAttestazioniPagamento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getAttestazioniPagamento: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("attestati pagamento mensa scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getAttestazioniPagamento:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getPdfAttestazionePagamento(String codiceFiscaleIscritto, Integer anno)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getPdfAttestazionePagamento = " + codiceFiscaleIscritto);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiRistorazionePortale()
          .getAttestazioniDiPagamentoPdf(codiceFiscaleIscritto, anno);
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfAttestazionePagamento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getPdfAttestazionePagamento: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("attestati pagamento mensa scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getPdfAttestazionePagamento:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiDirezioneTerritoriale> getListaDatiDirezioneTerritoriali()
      throws BusinessException, ApiException, IOException {

    log.debug("CP getListaDatiDirezioneTerritoriali ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDirezioniTerritoriali();
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- getListaDatiDirezioneTerritoriali:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaDatiDirezioneTerritoriali: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("diete speciali"));
    } catch (Exception e) {
      log.error("Errore generico getListaDatiDirezioneTerritoriali:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiIstituto> getListaDatiIstituto(String codiceDirezioneTerritoriale)
      throws BusinessException, ApiException, IOException {

    log.debug("CP getListaDatiIstituto = " + codiceDirezioneTerritoriale);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance
          .getApiRistorazionePortale()
          .getIstitutiScolastici(codiceDirezioneTerritoriale);
    } catch (BusinessException e) {
      log.error("Errore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaDatiIstituto: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getListaDatiIstituto: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("istituiti scolastici"));
    } catch (Exception e) {
      log.error("Errore generico getListaDatiIstituto:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(
      Utente utente, String tipoIndicatore) {
    log.debug("CP getAttestazioneISEEModi Mensa");

    try {
      ConsultazioneAttestazioneCF200 attestazione =
          InpsModiHelper.getAttestazioneISEE(
              utente,
              PrestazioneDaErogareEnum.A2_12,
              StatodomandaPrestazioneEnum.EROGATA,
              tipoIndicatore);

      return attestazione;
    } catch (BusinessException e) {
      log.error("Errore getAttestazioneISEEModi Mensa = " + e.getMessage(), e);
      return null;
    }
  }

  @Override
  public ConsultazioneDichiarazioneCF200 getDichiarazioneISEEModi(
      String codiceFiscale, String tipoIndicatore) {
    log.debug("CP getDichiarazioneISEEModi Mensa " + codiceFiscale);

    try {
      ConsultazioneDichiarazioneCF200 dichiarazione =
          InpsModiHelper.getDichiarazioneISEE(
              codiceFiscale,
              PrestazioneDaErogareEnum.A2_12,
              StatodomandaPrestazioneEnum.EROGATA,
              tipoIndicatore);

      return dichiarazione;
    } catch (BusinessException e) {
      log.error("Errore getDichiarazioneISEEModi Mensa = " + e.getMessage(), e);
      return null;
    }
  }

  @Override
  public List<NucleoFamiliareComponenteNucleoInner> getComponenteNucleoAttestazioneIseeModi(
      AgevolazioneStep1 agevolazioneStep1) {

    List<NucleoFamiliareComponenteNucleoInner> listaComponenteInAgevolazioneIseeModi =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    if (LabelFdCUtil.checkIfNotNull(agevolazioneStep1)) {

      if (checkNotNullFinoComponenteNucleoInAttestazioneIseeModi(agevolazioneStep1)) {

        listaComponenteInAgevolazioneIseeModi =
            agevolazioneStep1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getOrdinario()
                .getNucleoFamiliare()
                .getComponenteNucleo();
      }
    }

    return listaComponenteInAgevolazioneIseeModi;
  }

  @Override
  public List<NucleoFamiliareComponenteNucleoInner> getFigliComponenteNucleoAttestazioneIseeModi(
      AgevolazioneStep1 agevolazioneStep1) {

    List<NucleoFamiliareComponenteNucleoInner> listaFigliComponenteNucleoAttestazioneIseeModi =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    List<NucleoFamiliareComponenteNucleoInner> listaComponenteNucleo =
        getComponenteNucleoAttestazioneIseeModi(agevolazioneStep1);

    if (LabelFdCUtil.checkIfNotNull(listaComponenteNucleo)
        && !LabelFdCUtil.checkEmptyList(listaComponenteNucleo)) {
      // listaFigliComponenteNucleoAttestazioneIseeModi =
      // listaComponenteNucleo.stream()
      // .filter(elem -> PageUtil.isStringValid(elem.getRapportoConDichiarante())
      // && (elem.getRapportoConDichiarante().equalsIgnoreCase("F") ||
      // elem.getRapportoConDichiarante().equalsIgnoreCase("P") ||
      // elem.getRapportoConDichiarante().equalsIgnoreCase("MA")))
      // .collect(Collectors.toList());

      listaFigliComponenteNucleoAttestazioneIseeModi =
          listaComponenteNucleo.stream().collect(Collectors.toList());
    }

    return listaFigliComponenteNucleoAttestazioneIseeModi;
  }

  @Override
  public List<NucleoFamiliareComponenteNucleoInner> getListaComponenteNucleoDaDichirazioneIseeModi(
      ConsultazioneDichiarazioneCF200 dichirazioneIseeModi) {
    log.debug("CP getListaComponenteNucleoDaDichirazioneIseeModi");

    List<NucleoFamiliareComponenteNucleoInner> listaComponenteNucleoDaDichirazione =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    if (checkNotNullComponenteNucleoInDichiarazioneIseeModi(dichirazioneIseeModi)) {

      listaComponenteNucleoDaDichirazione =
          dichirazioneIseeModi
              .getConsultazioneDichiarazioneResponse()
              .getConsultazioneDichiarazioneResult()
              .getXmlEsitoDichiarazioneDecoded()
              .getEsitoDichiarazione()
              .getDichiarazione()
              .getNucleoFamiliare()
              .getComponenteNucleo();
    }

    return listaComponenteNucleoDaDichirazione;
  }

  private boolean checkNotNullComponenteNucleoInDichiarazioneIseeModi(
      ConsultazioneDichiarazioneCF200 dichirazioneIseeModi) {
    return LabelFdCUtil.checkIfNotNull(dichirazioneIseeModi)
        && LabelFdCUtil.checkIfNotNull(dichirazioneIseeModi.getConsultazioneDichiarazioneResponse())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult()
                .getXmlEsitoDichiarazioneDecoded())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult()
                .getXmlEsitoDichiarazioneDecoded()
                .getEsitoDichiarazione())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult()
                .getXmlEsitoDichiarazioneDecoded()
                .getEsitoDichiarazione()
                .getDichiarazione())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult()
                .getXmlEsitoDichiarazioneDecoded()
                .getEsitoDichiarazione()
                .getDichiarazione()
                .getNucleoFamiliare())
        && LabelFdCUtil.checkIfNotNull(
            dichirazioneIseeModi
                .getConsultazioneDichiarazioneResponse()
                .getConsultazioneDichiarazioneResult()
                .getXmlEsitoDichiarazioneDecoded()
                .getEsitoDichiarazione()
                .getDichiarazione()
                .getNucleoFamiliare()
                .getComponenteNucleo());
  }

  private ConsultazioneIndicatoreCF200 indicatoreIseeDelBambinoMaggiorenneOrdinario(
      AgevolazioneTariffariaRistorazione elemAgevolazione, String tipoIndicatore)
      throws BusinessException {
    ConsultazioneIndicatoreCFBody bodyIndicatoreIseeModiBambino =
        InpsModiHelper.createConsultazioneIndicatoreCFBody(
            elemAgevolazione.getCodiceFiscale(),
            PrestazioneDaErogareEnum.A2_12,
            StatodomandaPrestazioneEnum.EROGATA,
            tipoIndicatore);

    ConsultazioneIndicatoreCF200 indicatoreIseeModiBambino =
        ServiceLocator.getInstance()
            .getServiziINPSModi()
            .consultazioneIndicatoreCF(bodyIndicatoreIseeModiBambino);

    return indicatoreIseeModiBambino;
  }

  @Override
  public List<AgevolazioneTariffariaRistorazione> setImportoIndicatoreIseeBambino(
      List<AgevolazioneTariffariaRistorazione> listaIncrioConIseeModi) {
    log.debug("setImportoIndicatoreIseeBambino");

    if (LabelFdCUtil.checkIfNotNull(listaIncrioConIseeModi)) {
      for (AgevolazioneTariffariaRistorazione elemAgevolazione : listaIncrioConIseeModi) {

        try {
          String tipoIndicatoreMinorenne = "Minorenne";

          String protocolloDSU = "";
          LocalDate dataPresentazione = null;

          ConsultazioneIndicatoreCF200 indicatoreIseeBambinoMinorenne =
              indicatoreIseeDelBambinoMaggiorenneOrdinario(
                  elemAgevolazione, tipoIndicatoreMinorenne);

          if (checkValoreIseeInIndicatoreIseeModi(indicatoreIseeBambinoMinorenne)) {

            String iseeMinore =
                indicatoreIseeBambinoMinorenne
                    .getConsultazioneIndicatoreResponse()
                    .getConsultazioneIndicatoreResult()
                    .getXmlEsitoIndicatoreDecoded()
                    .getIndicatore()
                    .getISEE();

            elemAgevolazione.setImportoIndicatoreIseeBambino(iseeMinore);

            protocolloDSU =
                indicatoreIseeBambinoMinorenne
                    .getConsultazioneIndicatoreResponse()
                    .getConsultazioneIndicatoreResult()
                    .getXmlEsitoIndicatoreDecoded()
                    .getIndicatore()
                    .getProtocolloDSU();

            dataPresentazione =
                indicatoreIseeBambinoMinorenne
                    .getConsultazioneIndicatoreResponse()
                    .getConsultazioneIndicatoreResult()
                    .getXmlEsitoIndicatoreDecoded()
                    .getIndicatore()
                    .getDataPresentazione();

          } else {

            String tipoIndicatoreOrdinario = "Ordinario";

            ConsultazioneIndicatoreCF200 indicatoreIseeBambinoOrdinario =
                indicatoreIseeDelBambinoMaggiorenneOrdinario(
                    elemAgevolazione, tipoIndicatoreOrdinario);

            if (checkValoreIseeInIndicatoreIseeModi(indicatoreIseeBambinoOrdinario)) {

              String iseeMinore =
                  indicatoreIseeBambinoOrdinario
                      .getConsultazioneIndicatoreResponse()
                      .getConsultazioneIndicatoreResult()
                      .getXmlEsitoIndicatoreDecoded()
                      .getIndicatore()
                      .getISEE();

              elemAgevolazione.setImportoIndicatoreIseeBambino(iseeMinore);

              protocolloDSU =
                  indicatoreIseeBambinoOrdinario
                      .getConsultazioneIndicatoreResponse()
                      .getConsultazioneIndicatoreResult()
                      .getXmlEsitoIndicatoreDecoded()
                      .getIndicatore()
                      .getProtocolloDSU();

              dataPresentazione =
                  indicatoreIseeBambinoOrdinario
                      .getConsultazioneIndicatoreResponse()
                      .getConsultazioneIndicatoreResult()
                      .getXmlEsitoIndicatoreDecoded()
                      .getIndicatore()
                      .getDataPresentazione();
            }
          }

          ConsultazioneAttestazioneCF200 attestazioneMinore =
              InpsModiHelper.getAttestazioneISEEConCF(
                  elemAgevolazione.getCodiceFiscale(),
                  PrestazioneDaErogareEnum.A2_12,
                  StatodomandaPrestazioneEnum.EROGATA,
                  tipoIndicatoreMinorenne,
                  protocolloDSU,
                  dataPresentazione);

          log.debug("CP attestazioneMinore = " + attestazioneMinore);

          if (LabelFdCUtil.checkIfNotNull(attestazioneMinore)) {
            Ordinario ordinarioMinore =
                InpsModiHelper.getOrdinarioByAttestazioneIsee(attestazioneMinore);

            log.debug("CP ordinarioMinore = " + ordinarioMinore);

            if (LabelFdCUtil.checkIfNotNull(ordinarioMinore)) {

              List<ComponenteMinorenne> listaComponenteMinorenne =
                  ordinarioMinore.getIsEEMin().getComponenteMinorenne();
              if (LabelFdCUtil.checkIfNotNull(listaComponenteMinorenne)) {

                log.debug(
                    "CP elemAgevolazione.getCodiceFiscale() "
                        + elemAgevolazione.getCodiceFiscale());

                ComponenteMinorenne minoreInLista =
                    listaComponenteMinorenne.stream()
                        .filter(
                            elemCM ->
                                elemCM
                                    .getCodiceFiscale()
                                    .equalsIgnoreCase(elemAgevolazione.getCodiceFiscale()))
                        .findFirst()
                        .orElse(null);

                log.debug("CP minoreInLista = " + minoreInLista);

                if (LabelFdCUtil.checkIfNotNull(minoreInLista)) {

                  elemAgevolazione.setIseeNonCalcolabile(minoreInLista.getIsEENonCalcolabile());
                }
              }
            }
          }

        } catch (BusinessException e) {
          log.error("Errore inps modi mensa = " + e.getMessage(), e);
        }
      }
    }

    return listaIncrioConIseeModi;
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

  @Override
  public boolean checkCampiNotNullFinoAttestazioneIseeModi(AgevolazioneStep1 step1) {
    log.debug("checkCampiNotNullInOggettoneAttestazione");

    return LabelFdCUtil.checkIfNotNull(step1)
        && LabelFdCUtil.checkIfNotNull(step1.getAttestazioneIseeModi())
        && LabelFdCUtil.checkIfNotNull(
            step1.getAttestazioneIseeModi().getConsultazioneAttestazioneResponse())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione());
  }

  @Override
  public boolean checkNotNullFinoISEEInOrdinarioIseeModi(AgevolazioneStep1 step1) {
    log.debug("checkNotNullFinoISEEInOrdinario");
    try {
      return LabelFdCUtil.checkIfNotNull(
              step1
                  .getAttestazioneIseeModi()
                  .getConsultazioneAttestazioneResponse()
                  .getConsultazioneAttestazioneResult()
                  .getXmlEsitoAttestazioneDecoded()
                  .getEsitoAttestazione()
                  .getAttestazione()
                  .getOrdinario())
          && LabelFdCUtil.checkIfNotNull(
              step1
                  .getAttestazioneIseeModi()
                  .getConsultazioneAttestazioneResponse()
                  .getConsultazioneAttestazioneResult()
                  .getXmlEsitoAttestazioneDecoded()
                  .getEsitoAttestazione()
                  .getAttestazione()
                  .getOrdinario()
                  .getIsEEOrdinario())
          && LabelFdCUtil.checkIfNotNull(
              step1
                  .getAttestazioneIseeModi()
                  .getConsultazioneAttestazioneResponse()
                  .getConsultazioneAttestazioneResult()
                  .getXmlEsitoAttestazioneDecoded()
                  .getEsitoAttestazione()
                  .getAttestazione()
                  .getOrdinario()
                  .getIsEEOrdinario()
                  .getValori())
          && LabelFdCUtil.checkIfNotNull(
              step1
                  .getAttestazioneIseeModi()
                  .getConsultazioneAttestazioneResponse()
                  .getConsultazioneAttestazioneResult()
                  .getXmlEsitoAttestazioneDecoded()
                  .getEsitoAttestazione()
                  .getAttestazione()
                  .getOrdinario()
                  .getIsEEOrdinario()
                  .getValori()
                  .getISEE());
    } catch (Exception e) {
      log.error(
          "MP ServiziRistorazioneImpl - errore in checkNotNullFinoISEEInOrdinarioIseeModi:"
              + e.getMessage(),
          e);
      return false;
    }
  }

  @Override
  public List<NucleoFamiliareComponenteNucleoInner> getListaPortatoriDiRedditoConIseeModi(
      AgevolazioneStep2 step2) {

    log.debug("getListaPortatoriDiRedditoConIseeModi");

    List<NucleoFamiliareComponenteNucleoInner> listaPortatoriReddito =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    List<NucleoFamiliareComponenteNucleoInner> listaComponenteInAgevolazioneIseeModi =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    List<NucleoFamiliareComponenteNucleoInner> listaComponenteInDichiarazioneIseeModi =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();

    if (LabelFdCUtil.checkIfNotNull(step2) && LabelFdCUtil.checkIfNotNull(step2.getStep1())) {

      if (checkNotNullFinoComponenteNucleoInAttestazioneIseeModi(step2.getStep1())) {
        listaComponenteInAgevolazioneIseeModi =
            step2
                .getStep1()
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getOrdinario()
                .getNucleoFamiliare()
                .getComponenteNucleo();
      }

      listaComponenteInDichiarazioneIseeModi =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getListaComponenteNucleoDaDichirazioneIseeModi(
                  step2.getStep1().getDichiarazioneIseeModi());

      if (LabelFdCUtil.checkIfNotNull(listaComponenteInDichiarazioneIseeModi)
          && !LabelFdCUtil.checkEmptyList(listaComponenteInDichiarazioneIseeModi)) {

        log.debug(
            "CP listaComponenteInDichiarazioneIseeModi size = "
                + listaComponenteInDichiarazioneIseeModi.size());

        for (NucleoFamiliareComponenteNucleoInner elemComponenteDichiarazione :
            listaComponenteInDichiarazioneIseeModi) {

          // con persone eta >= 16

          if (LabelFdCUtil.checkIfNotNull(elemComponenteDichiarazione)
              && LabelFdCUtil.checkIfNotNull(
                  elemComponenteDichiarazione.getFoglioComponenteNucleo())
              && LabelFdCUtil.checkIfNotNull(
                  elemComponenteDichiarazione.getFoglioComponenteNucleo().getDatiComponente())
              && LabelFdCUtil.checkIfNotNull(
                  elemComponenteDichiarazione
                      .getFoglioComponenteNucleo()
                      .getDatiComponente()
                      .getAnagrafica())
              && LabelFdCUtil.checkIfNotNull(
                  elemComponenteDichiarazione
                      .getFoglioComponenteNucleo()
                      .getDatiComponente()
                      .getAnagrafica()
                      .getDataNascita())) {

            if (LocalDateUtil.isMaggiore15anni(
                elemComponenteDichiarazione
                    .getFoglioComponenteNucleo()
                    .getDatiComponente()
                    .getAnagrafica()
                    .getDataNascita())) {

              String codiceFiscaleInFoglio =
                  elemComponenteDichiarazione.getFoglioComponenteNucleo().getCodiceFiscale();

              for (NucleoFamiliareComponenteNucleoInner elemComponenteAttestazione :
                  listaComponenteInAgevolazioneIseeModi) {

                if (LabelFdCUtil.checkIfNotNull(elemComponenteAttestazione)
                    && elemComponenteAttestazione
                        .getCodiceFiscale()
                        .equalsIgnoreCase(codiceFiscaleInFoglio)) {
                  listaPortatoriReddito.add(elemComponenteAttestazione);
                }
              }
            }
          }

          // fine con persone eta >= 16

          // con filtro su attivita lavoro
          // ArrayList<String> listaAttivitaSoggettoCheLavora = new
          // ArrayList<String>(Arrays.asList("LAVIND", "LAVDET", "LAVINT", "LAVPAR",
          // "LAVAUT"));
          // log.debug("CP listaAttivitaSoggettoCheLavora = " +
          // listaAttivitaSoggettoCheLavora);
          //
          // if (LabelFdCUtil.checkIfNotNull(elemComponenteDichiarazione) &&
          // LabelFdCUtil.checkIfNotNull(elemComponenteDichiarazione.getFoglioComponenteNucleo())
          // &&
          // LabelFdCUtil.checkIfNotNull(elemComponenteDichiarazione.getFoglioComponenteNucleo().getDatiComponente())
          // &&
          // PageUtil.isStringValid(elemComponenteDichiarazione.getFoglioComponenteNucleo().getDatiComponente().getAttivitaSoggetto())
          // ) {
          //
          // log.debug("CP attivita soggetto = " +
          // elemComponenteDichiarazione.getFoglioComponenteNucleo().getDatiComponente().getAttivitaSoggetto());
          //
          // if(listaAttivitaSoggettoCheLavora.contains(elemComponenteDichiarazione.getFoglioComponenteNucleo().getDatiComponente().getAttivitaSoggetto()))
          // {
          //
          // String codiceFiscaleInFoglio =
          // elemComponenteDichiarazione.getFoglioComponenteNucleo().getCodiceFiscale();
          //
          // log.debug("CP codiceFiscaleInFoglio = " + codiceFiscaleInFoglio);
          //
          // for (NucleoFamiliareComponenteNucleoInner elemComponenteAttestazione :
          // listaComponenteInAgevolazioneIseeModi) {
          //
          // log.debug("CP elemComponenteAttestazione getCodiceFiscale() = " +
          // elemComponenteAttestazione
          // .getCodiceFiscale());
          //
          // if (LabelFdCUtil.checkIfNotNull(elemComponenteAttestazione) &&
          // elemComponenteAttestazione
          // .getCodiceFiscale().equalsIgnoreCase(codiceFiscaleInFoglio)) {
          // listaPortatoriReddito.add(elemComponenteAttestazione);
          // }
          //
          // }
          // }
          //
          // }
          // fine con filtro su attivita lavoro
        }
      }
    }

    return listaPortatoriReddito;
  }

  @Override
  public boolean checkNotNullFinoComponenteNucleoInAttestazioneIseeModi(AgevolazioneStep1 step1) {
    return checkCampiNotNullFinoAttestazioneIseeModi(step1)
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getOrdinario())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getOrdinario()
                .getNucleoFamiliare())
        && LabelFdCUtil.checkIfNotNull(
            step1
                .getAttestazioneIseeModi()
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione()
                .getOrdinario()
                .getNucleoFamiliare()
                .getComponenteNucleo());
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("Pagamento effettuato");
    legenda1.setStile("badge bg-success");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("Pagamento da effettuare");
    legenda2.setStile("badge bg-danger");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Pagamento annullato");
    legenda3.setStile("badge bg-secondary");
    listaLegenda.add(legenda3);

    return listaLegenda;
  }

  @Override
  public List<StepFdC> getListaStepIscrizioneMensaNonResidenti() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati genitore", 1));
    listaStep.add(new StepFdC("Dati bambino", 2));
    listaStep.add(new StepFdC("Riepilogo", 3));
    listaStep.add(new StepFdC("Esito", 4));

    return listaStep;
  }

  @Override
  public List<AnnoScolastico> getAnniScolastici()
      throws BusinessException, ApiException, IOException {
    log.debug("CP getAnniScolastici ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getAnniScolastici();
    } catch (BusinessException e) {
      log.error("Errore anni scolastici mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- getAnniScolastici:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getAnniScolastici: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("anni scolastici"));
    } catch (Exception e) {
      log.error("Errore generico getAnniScolastici:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Comune> getComuni(String codiceProvincia)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getComuni " + codiceProvincia);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getComuni(codiceProvincia);
    } catch (BusinessException e) {
      log.error("Errore comuni mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- getComuni:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getComuni: errore durante la chiamata delle API Goadev ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Comuni"));
    } catch (Exception e) {
      log.error("Errore generico getComuni:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Provincia> getProvince() throws BusinessException, ApiException, IOException {
    log.debug("CP getProvince ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getProvince();
    } catch (BusinessException e) {
      log.error("Errore province mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- getProvince:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getProvince: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Province"));
    } catch (Exception e) {
      log.error("Errore generico getProvince:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Cittadinanza> getCittadinanze() throws BusinessException, ApiException, IOException {
    log.debug("CP getCittadinanze ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getCittadinanze();
    } catch (BusinessException e) {
      log.error("Errore cittadinanze mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- getCittadinanze:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getCittadinanze: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Cittadinanze"));
    } catch (Exception e) {
      log.error("Errore generico getCittadinanze:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public DatiAnagrafeGenitore getDatiAnagraficiGenitore(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDatiAnagraficiGenitore ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDatiAnagraficiGenitore(codiceFiscale);
    } catch (BusinessException e) {
      log.error("Errore dati genitore mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiGenitore: ResponseProcessingException "
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiGenitore: WebApplicationException"
              + e.getMessage()
              + " - e.getResponse().getStatus() = "
              + e.getResponse().getStatus());

      if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
        return null;
      } else {
        throw new ApiException(
            Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
      }
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiGenitore: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getDatiAnagraficiGenitore:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public DatiDipartimentaliBambino getDatiAnagraficiFiglio(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDatiAnagraficiFiglio ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDatiAnagraficiFiglio(codiceFiscale);
    } catch (BusinessException e) {
      log.error("Errore dati figlio mensa:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiFiglio: ResponseProcessingException "
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiFiglio: WebApplicationException "
              + e.getMessage()
              + " -  "
              + e.getResponse().getStatus());
      if (e.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
        return null;
      } else {
        throw new ApiException(
            Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
      }
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiAnagraficiFiglio: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("ristorazione scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getDatiAnagraficiFiglio:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Comune> getComuniAutocomplete(String codiceProvincia, String input)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getComuniAutocomplete = " + codiceProvincia + " - " + input);

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
  public List<Provincia> getProvinceAutocomplete(String input)
      throws BusinessException, ApiException, IOException {

    String inputToUpper = input.toUpperCase();

    List<Provincia> listaTutteProvince = getProvince();

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
  public void iscriviBambinoNonResidente(DatiIscrizioneMensaNonResidente datiIscrizioneBambino)
      throws BusinessException, ApiException, IOException {
    log.debug("CP iscriviBambinoNonResidente ");
    ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti instance =
        ServiceLocatorLicelloUnoGoadevIscrizioneNonResidenti.getInstance();
    try {

      DatiDomandaIscrizioneServiziRistorazioneNonResidente datiIscrizione =
          new DatiDomandaIscrizioneServiziRistorazioneNonResidente();

      if (LabelFdCUtil.checkIfNotNull(datiIscrizioneBambino.getDatiGenitore())) {
        DatiAnagrafeGenitore datiGenitore = new DatiAnagrafeGenitore();

        DatiGeneraliAnagrafe datiGeneraliGenitore = new DatiGeneraliAnagrafe();
        datiGeneraliGenitore.setCodiceFiscale(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getCodiceFiscale());
        datiGeneraliGenitore.setDataNascita(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getDataNascita());
        datiGeneraliGenitore.setCognome(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getCognome());
        datiGeneraliGenitore.setNome(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getNome());

        datiGeneraliGenitore.setViaResidenza(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getViaResidenza());
        datiGeneraliGenitore.setCivicoResidenza(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getCivicoResidenza());
        datiGeneraliGenitore.setInternoResidenza(
            datiIscrizioneBambino
                .getDatiGenitore()
                .getDatiGeneraliAnagrafe()
                .getInternoResidenza());
        datiGeneraliGenitore.setCapResidenza(
            datiIscrizioneBambino.getDatiGenitore().getDatiGeneraliAnagrafe().getCapResidenza());

        if (LabelFdCUtil.checkIfNotNull(
            datiIscrizioneBambino.getDatiGenitore().getAutocompleteStatoNascita())) {
          datiGeneraliGenitore.setCodiceStatoNascita(
              datiIscrizioneBambino.getDatiGenitore().getAutocompleteStatoNascita().getCodice());
        }

        String sesso =
            CodiceFiscaleValidatorUtil.getSessoFromCf(
                datiIscrizioneBambino
                    .getDatiGenitore()
                    .getDatiGeneraliAnagrafe()
                    .getCodiceFiscale());

        if (PageUtil.isStringValid(sesso)) {
          if (sesso.equalsIgnoreCase("F")) {
            datiGeneraliGenitore.setSesso(
                it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe.SessoEnum
                    .F);
          } else {
            datiGeneraliGenitore.setSesso(
                it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe.SessoEnum
                    .M);
          }
        }

        if (LabelFdCUtil.checkIfNotNull(
            datiIscrizioneBambino.getDatiGenitore().getDatiNascitaResidenzaDomicilio())) {

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino.getDatiGenitore().getAutocompleteStatoNascita())) {
            if (datiIscrizioneBambino
                .getDatiGenitore()
                .getAutocompleteStatoNascita()
                .getCodice()
                .equalsIgnoreCase("100")) {
              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaNascita())) {
                datiGeneraliGenitore.setCodiceProvinciaNascita(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteProvinciaNascita()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneNascita())) {
                datiGeneraliGenitore.setCodiceComuneNascita(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteComuneNascita()
                        .getCodice());
              }
            } else {
              datiGeneraliGenitore.setCodiceProvinciaNascita("999");

              datiGeneraliGenitore.setCodiceComuneNascita("999");
            }
          }

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino
                  .getDatiGenitore()
                  .getDatiNascitaResidenzaDomicilio()
                  .getAutocompleteProvinciaResidenza())) {
            datiGeneraliGenitore.setCodiceProvinciaResidenza(
                datiIscrizioneBambino
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .getAutocompleteProvinciaResidenza()
                    .getCodice());
          }

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino
                  .getDatiGenitore()
                  .getDatiNascitaResidenzaDomicilio()
                  .getAutocompleteComuneResidenza())) {
            datiGeneraliGenitore.setCodiceComuneResidenza(
                datiIscrizioneBambino
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .getAutocompleteComuneResidenza()
                    .getCodice());
          }

          if (PageUtil.isStringValid(
              datiIscrizioneBambino.getDatiGenitore().getDatiResidenzaDomicilioCoincidono())) {
            if (datiIscrizioneBambino
                .getDatiGenitore()
                .getDatiResidenzaDomicilioCoincidono()
                .equalsIgnoreCase("Si")) {

              datiGeneraliGenitore.setCodiceProvinciaDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaResidenza()
                      .getCodice());

              datiGeneraliGenitore.setCodiceComuneDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneResidenza()
                      .getCodice());

              datiGeneraliGenitore.setViaDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getViaResidenza());

              datiGeneraliGenitore.setCivicoDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getCivicoResidenza());
              datiGeneraliGenitore.setInternoDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getInternoResidenza());
              datiGeneraliGenitore.setCapDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getCapResidenza());

            } else {
              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaDomicilio())) {
                datiGeneraliGenitore.setCodiceProvinciaDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteProvinciaDomicilio()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneDomicilio())) {
                datiGeneraliGenitore.setCodiceComuneDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteComuneDomicilio()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteViario())) {
                datiGeneraliGenitore.setCodiceViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getCOD_STRADA());
                datiGeneraliGenitore.setViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getDESVIA());
                datiGeneraliGenitore.setCivicoDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getNUMERO());
              } else {
                datiGeneraliGenitore.setViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiGeneraliAnagrafe()
                        .getViaDomicilio());
                datiGeneraliGenitore.setCivicoDomicilio(
                    datiIscrizioneBambino
                        .getDatiGenitore()
                        .getDatiGeneraliAnagrafe()
                        .getCivicoDomicilio());
              }

              datiGeneraliGenitore.setInternoDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getInternoDomicilio());
              datiGeneraliGenitore.setCapDomicilio(
                  datiIscrizioneBambino
                      .getDatiGenitore()
                      .getDatiGeneraliAnagrafe()
                      .getCapDomicilio());
            }
          }
        }

        datiGenitore.setDatiGeneraliAnagrafe(datiGeneraliGenitore);
        datiGenitore.setEmail(datiIscrizioneBambino.getDatiGenitore().getEmail());
        datiGenitore.setTelefono(datiIscrizioneBambino.getDatiGenitore().getTelefono());

        DatiInfoBollettazione datiBollettazione = new DatiInfoBollettazione();

        ModalitaBollettazioneEnum modalitaBollettazione =
            datiIscrizioneBambino.getDatiGenitore().getModalitaBollettazione();
        if (modalitaBollettazione.equals(ModalitaBollettazioneEnum.CARTACEA)) {
          datiBollettazione.setIsCartaceo(true);
        } else {
          datiBollettazione.setIsCartaceo(false);
        }
        datiBollettazione.setEmailBollettazione(
            datiIscrizioneBambino.getDatiGenitore().getEmailBollettazione());

        datiGenitore.setInfoBollettazione(datiBollettazione);

        datiIscrizione.setDatiAnagrafeGenitore(datiGenitore);
      }

      // bimbo

      if (LabelFdCUtil.checkIfNotNull(datiIscrizioneBambino.getDatiBambino())) {
        DatiDipartimentaliBambino datiBimbo = new DatiDipartimentaliBambino();

        DatiGeneraliAnagrafe datiGeneraliBambino = new DatiGeneraliAnagrafe();
        datiGeneraliBambino.setCodiceFiscale(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getCodiceFiscale());
        datiGeneraliBambino.setDataNascita(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getDataNascita());
        datiGeneraliBambino.setCognome(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getCognome());
        datiGeneraliBambino.setNome(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getNome());

        datiGeneraliBambino.setViaResidenza(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getViaResidenza());
        datiGeneraliBambino.setCivicoResidenza(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getCivicoResidenza());
        datiGeneraliBambino.setInternoResidenza(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getInternoResidenza());
        datiGeneraliBambino.setCapResidenza(
            datiIscrizioneBambino.getDatiBambino().getDatiGeneraliAnagrafe().getCapResidenza());

        String sesso =
            CodiceFiscaleValidatorUtil.getSessoFromCf(
                datiIscrizioneBambino
                    .getDatiBambino()
                    .getDatiGeneraliAnagrafe()
                    .getCodiceFiscale());

        if (PageUtil.isStringValid(sesso)) {
          if (sesso.equalsIgnoreCase("F")) {
            datiGeneraliBambino.setSesso(
                it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe.SessoEnum
                    .F);
          } else {
            datiGeneraliBambino.setSesso(
                it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe.SessoEnum
                    .M);
          }
        }

        if (LabelFdCUtil.checkIfNotNull(
            datiIscrizioneBambino.getDatiBambino().getDatiNascitaResidenzaDomicilio())) {

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino.getDatiBambino().getAutocompleteCittadinanze())) {

            if (datiIscrizioneBambino
                .getDatiBambino()
                .getAutocompleteCittadinanze()
                .getCodice()
                .equalsIgnoreCase("100")) {
              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaNascita())) {
                datiGeneraliBambino.setCodiceProvinciaNascita(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteProvinciaNascita()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneNascita())) {
                datiGeneraliBambino.setCodiceComuneNascita(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteComuneNascita()
                        .getCodice());
              }
            } else {
              datiGeneraliBambino.setCodiceProvinciaNascita("999");

              datiGeneraliBambino.setCodiceComuneNascita("999");
            }
          }

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino
                  .getDatiBambino()
                  .getDatiNascitaResidenzaDomicilio()
                  .getAutocompleteProvinciaResidenza())) {
            datiGeneraliBambino.setCodiceProvinciaResidenza(
                datiIscrizioneBambino
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .getAutocompleteProvinciaResidenza()
                    .getCodice());
          }

          if (LabelFdCUtil.checkIfNotNull(
              datiIscrizioneBambino
                  .getDatiBambino()
                  .getDatiNascitaResidenzaDomicilio()
                  .getAutocompleteComuneResidenza())) {
            datiGeneraliBambino.setCodiceComuneResidenza(
                datiIscrizioneBambino
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .getAutocompleteComuneResidenza()
                    .getCodice());
          }

          if (PageUtil.isStringValid(
              datiIscrizioneBambino.getDatiBambino().getDatiResidenzaDomicilioCoincidono())) {
            if (datiIscrizioneBambino
                .getDatiBambino()
                .getDatiResidenzaDomicilioCoincidono()
                .equalsIgnoreCase("Si")) {
              datiGeneraliBambino.setCodiceProvinciaDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaResidenza()
                      .getCodice());

              datiGeneraliBambino.setCodiceComuneDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneResidenza()
                      .getCodice());

              datiGeneraliBambino.setCivicoDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiGeneraliAnagrafe()
                      .getCivicoResidenza());
              datiGeneraliBambino.setInternoDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiGeneraliAnagrafe()
                      .getInternoResidenza());
              datiGeneraliBambino.setCapDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiGeneraliAnagrafe()
                      .getCapResidenza());

            } else {
              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaDomicilio())) {
                datiGeneraliBambino.setCodiceProvinciaDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteProvinciaDomicilio()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneDomicilio())) {
                datiGeneraliBambino.setCodiceComuneDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteComuneDomicilio()
                        .getCodice());
              }

              if (LabelFdCUtil.checkIfNotNull(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteViario())) {
                datiGeneraliBambino.setCodiceViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getCOD_STRADA());
                datiGeneraliBambino.setViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getDESVIA());
                datiGeneraliBambino.setCivicoDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiNascitaResidenzaDomicilio()
                        .getAutocompleteViario()
                        .getNUMERO());

              } else {
                datiGeneraliBambino.setViaDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiGeneraliAnagrafe()
                        .getViaDomicilio());
                datiGeneraliBambino.setCivicoDomicilio(
                    datiIscrizioneBambino
                        .getDatiBambino()
                        .getDatiGeneraliAnagrafe()
                        .getCivicoDomicilio());
              }

              datiGeneraliBambino.setInternoDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiGeneraliAnagrafe()
                      .getInternoDomicilio());
              datiGeneraliBambino.setCapDomicilio(
                  datiIscrizioneBambino
                      .getDatiBambino()
                      .getDatiGeneraliAnagrafe()
                      .getCapDomicilio());
            }
          }
        }

        datiBimbo.setDatiGeneraliAnagrafe(datiGeneraliBambino);

        if (LabelFdCUtil.checkIfNotNull(
            datiIscrizioneBambino.getDatiBambino().getAutocompleteCittadinanze())) {
          datiBimbo.setCodiceCittadinanza(
              datiIscrizioneBambino.getDatiBambino().getAutocompleteCittadinanze().getCodice());
        }
        datiIscrizione.setDatiDipartimentaliBambino(datiBimbo);
      }

      log.debug("CP datiIscrizione prima di POST = " + datiIscrizione);

      instance.getApiRistorazionePortale().postDomandaIscrizioneNonResidenti(datiIscrizione);

    } catch (BusinessException e) {
      log.error("Errore iscrivi figlio non residente mensa:" + e.getMessage(), e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error("ServiziRistorazioneImpl -- iscriviBambinoNonResidente:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- iscriviBambinoNonResidente: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("iscrizione mensa"));
    } catch (Exception e) {
      log.error("Errore generico iscriviBambinoNonResidente:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Cittadinanza> getCittadinanzeAutocomplete(String input)
      throws BusinessException, ApiException, IOException {
    String inputToUpper = input.toUpperCase();

    List<Cittadinanza> listaTutteCittadinanze = getCittadinanze();

    List<Cittadinanza> listaCittadinanzeFiltrate = new ArrayList<Cittadinanza>();

    listaCittadinanzeFiltrate =
        listaTutteCittadinanze.stream()
            .filter(
                elem ->
                    LabelFdCUtil.checkIfNotNull(elem)
                        && LabelFdCUtil.checkIfNotNull(elem.getDescrizione())
                        && elem.getDescrizione().contains(inputToUpper))
            .collect(Collectors.toList());

    return listaCittadinanzeFiltrate;
  }

  @Override
  public Provincia getProvinciaDaCodice(String codiceProvincia) {
    Provincia provincia = null;
    try {
      provincia =
          getProvince().stream()
              .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceProvincia))
              .findAny()
              .orElse(null);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getProvinciaDaCodice = " + e.getMessage());
    }
    return provincia;
  }

  @Override
  public Comune getComuneDaCodice(String codiceComune, String codiceProvincia) {
    Comune comune = null;
    try {
      comune =
          getComuni(codiceProvincia).stream()
              .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceComune))
              .findAny()
              .orElse(null);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getComuneDaCodice = " + e.getMessage());
    }
    return comune;
  }

  @Override
  public Cittadinanza getCittadinanzaDaCodice(String codiceCittadinanza) {
    Cittadinanza cittadinanza = null;
    try {
      cittadinanza =
          getCittadinanze().stream()
              .filter(elem -> elem.getCodice().equalsIgnoreCase(codiceCittadinanza))
              .findAny()
              .orElse(null);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore getCittadinanzaDaCodice = " + e.getMessage());
    }
    return cittadinanza;
  }

  @Override
  public DatiGenitoreExtend getDatiGenitoreExted(Utente utente, DatiAnagrafeGenitore datiGenitore) {
    DatiGenitoreExtend datiGenitoreExtend = null;

    if (LabelFdCUtil.checkIfNotNull(datiGenitore)) {
      datiGenitoreExtend = new DatiGenitoreExtend();

      if (PageUtil.isStringValid(datiGenitore.getEmail())) {
        datiGenitoreExtend.setEmail(datiGenitore.getEmail());
      } else {
        datiGenitoreExtend.setEmail(utente.getMail());
      }

      datiGenitoreExtend.setTelefono(datiGenitore.getTelefono());
      datiGenitoreExtend.setDatiGeneraliAnagrafe(datiGenitore.getDatiGeneraliAnagrafe());

      if (LabelFdCUtil.checkIfNotNull(datiGenitore.getDatiGeneraliAnagrafe())) {

        datiGenitoreExtend
            .getDatiGeneraliAnagrafe()
            .setCodiceStatoNascita(datiGenitore.getDatiGeneraliAnagrafe().getCodiceStatoNascita());
        Cittadinanza statoNascita =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getCittadinanzaDaCodice(
                    datiGenitoreExtend.getDatiGeneraliAnagrafe().getCodiceStatoNascita());
        if (LabelFdCUtil.checkIfNotNull(statoNascita)) {
          datiGenitoreExtend.setAutocompleteStatoNascita(statoNascita);
        }

        DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio =
            new DatiNascitaResidenzaDomicilio();

        Provincia provinciaNascita =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaNascita());
        if (LabelFdCUtil.checkIfNotNull(provinciaNascita)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaNascita(provinciaNascita);
        }

        Comune comuneNascita =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceComuneNascita(),
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaNascita());
        if (LabelFdCUtil.checkIfNotNull(comuneNascita)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneNascita(comuneNascita);
        }

        Provincia provinciaResidenza =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaResidenza());
        if (LabelFdCUtil.checkIfNotNull(provinciaResidenza)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaResidenza(provinciaResidenza);
        }

        Comune comuneResidenza =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceComuneResidenza(),
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaResidenza());
        if (LabelFdCUtil.checkIfNotNull(comuneResidenza)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneResidenza(comuneResidenza);
        }

        Provincia provinciaDomicilio =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaDomicilio());
        if (LabelFdCUtil.checkIfNotNull(provinciaDomicilio)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaDomicilio(provinciaDomicilio);
        }

        Comune comuneDomicilio =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio(),
                    datiGenitore.getDatiGeneraliAnagrafe().getCodiceProvinciaDomicilio());
        if (LabelFdCUtil.checkIfNotNull(comuneDomicilio)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneDomicilio(comuneDomicilio);
        }

        if (PageUtil.isStringValid(
                datiGenitore.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio())
            && datiGenitore
                .getDatiGeneraliAnagrafe()
                .getCodiceComuneDomicilio()
                .equalsIgnoreCase("025")
            && PageUtil.isStringValid(datiGenitore.getDatiGeneraliAnagrafe().getViaDomicilio())
            && LabelFdCUtil.checkIfNotNull(
                datiGenitore.getDatiGeneraliAnagrafe().getCodiceViaDomicilio())) {

          String viaDomicilio = "";

          viaDomicilio = datiGenitore.getDatiGeneraliAnagrafe().getViaDomicilio();

          String codiceDomicilio = datiGenitore.getDatiGeneraliAnagrafe().getCodiceViaDomicilio();
          String numeroCivicoDomicilio =
              datiGenitore.getDatiGeneraliAnagrafe().getCivicoDomicilio();

          if (PageUtil.isStringValid(viaDomicilio)) {

            try {
              List<FeaturesGeoserver> listaGeoserver =
                  ServiceLocator.getInstance().getServiziGeoserver().getGeoserver(viaDomicilio);

              FeaturesGeoserver geoserver =
                  listaGeoserver.stream()
                      .filter(
                          elem ->
                              LabelFdCUtil.checkIfNotNull(elem.getCOD_STRADA())
                                  && elem.getCOD_STRADA().equalsIgnoreCase(codiceDomicilio)
                                  && LabelFdCUtil.checkIfNotNull(elem.getNUMERO())
                                  && elem.getNUMERO().equals(numeroCivicoDomicilio))
                      .findAny()
                      .orElse(null);

              datiNascitaResidenzaDomicilio.setAutocompleteViario(geoserver);

            } catch (BusinessException | ApiException e) {
              log.error("Errore geoserver da comune goadev mensa " + e.getMessage());
            }
          }
        }

        datiGenitoreExtend.setDatiNascitaResidenzaDomicilio(datiNascitaResidenzaDomicilio);
      }

    } else {
      datiGenitore = new DatiAnagrafeGenitore();
      datiGenitoreExtend = new DatiGenitoreExtend();

      DatiGeneraliAnagrafe datiGeneraliAnagrafe = new DatiGeneraliAnagrafe();
      datiGeneraliAnagrafe.setNome(utente.getNome());
      datiGeneraliAnagrafe.setCognome(utente.getCognome());
      datiGeneraliAnagrafe.setDataNascita(utente.getDataDiNascita());
      datiGeneraliAnagrafe.setCodiceFiscale(utente.getCodiceFiscaleOperatore());

      datiGenitoreExtend.setDatiGeneraliAnagrafe(datiGeneraliAnagrafe);

      datiGenitoreExtend.setEmail(utente.getMail());

      datiGenitoreExtend.setDatiNascitaResidenzaDomicilio(new DatiNascitaResidenzaDomicilio());
    }

    log.debug("CP datiGenitoreExtend = " + datiGenitoreExtend);

    return datiGenitoreExtend;
  }

  @Override
  public DatiFiglioExtend getDatiFiglioExted(
      String codiceFiscaleIscritto,
      String nomeIscritto,
      String cognomeIscritto,
      LocalDate dataNascitaIscritto,
      DatiDipartimentaliBambino datiFiglio) {
    DatiFiglioExtend datiFiglioExtend = null;

    if (LabelFdCUtil.checkIfNotNull(datiFiglio)) {
      datiFiglioExtend = new DatiFiglioExtend();

      datiFiglioExtend.setCodiceCittadinanza(datiFiglio.getCodiceCittadinanza());
      Cittadinanza cittadinanza =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getCittadinanzaDaCodice(datiFiglio.getCodiceCittadinanza());

      if (LabelFdCUtil.checkIfNotNull(cittadinanza)) {
        datiFiglioExtend.setAutocompleteCittadinanze(cittadinanza);
      }

      datiFiglioExtend.setDatiGeneraliAnagrafe(datiFiglio.getDatiGeneraliAnagrafe());

      if (LabelFdCUtil.checkIfNotNull(datiFiglio.getDatiGeneraliAnagrafe())) {
        DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio =
            new DatiNascitaResidenzaDomicilio();

        Provincia provinciaNascita =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaNascita());
        if (LabelFdCUtil.checkIfNotNull(provinciaNascita)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaNascita(provinciaNascita);
        }

        Comune comuneNascita =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceComuneNascita(),
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaNascita());
        if (LabelFdCUtil.checkIfNotNull(comuneNascita)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneNascita(comuneNascita);
        }

        Provincia provinciaResidenza =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaResidenza());
        if (LabelFdCUtil.checkIfNotNull(provinciaResidenza)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaResidenza(provinciaResidenza);
        }

        Comune comuneResidenza =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceComuneResidenza(),
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaResidenza());
        if (LabelFdCUtil.checkIfNotNull(comuneResidenza)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneResidenza(comuneResidenza);
        }

        Provincia provinciaDomicilio =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getProvinciaDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaDomicilio());
        if (LabelFdCUtil.checkIfNotNull(provinciaDomicilio)) {
          datiNascitaResidenzaDomicilio.setAutocompleteProvinciaDomicilio(provinciaDomicilio);
        }

        Comune comuneDomicilio =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getComuneDaCodice(
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio(),
                    datiFiglio.getDatiGeneraliAnagrafe().getCodiceProvinciaDomicilio());
        if (LabelFdCUtil.checkIfNotNull(comuneDomicilio)) {
          datiNascitaResidenzaDomicilio.setAutocompleteComuneDomicilio(comuneDomicilio);
        }

        if (PageUtil.isStringValid(datiFiglio.getDatiGeneraliAnagrafe().getCodiceComuneDomicilio())
            && datiFiglio
                .getDatiGeneraliAnagrafe()
                .getCodiceComuneDomicilio()
                .equalsIgnoreCase("025")
            && PageUtil.isStringValid(datiFiglio.getDatiGeneraliAnagrafe().getViaDomicilio())
            && LabelFdCUtil.checkIfNotNull(
                datiFiglio.getDatiGeneraliAnagrafe().getCodiceViaDomicilio())) {

          String viaDomicilio = datiFiglio.getDatiGeneraliAnagrafe().getViaDomicilio();
          String codiceViaDomicilio = datiFiglio.getDatiGeneraliAnagrafe().getCodiceViaDomicilio();
          String numeroCivicoDomicilio = datiFiglio.getDatiGeneraliAnagrafe().getCivicoDomicilio();

          if (PageUtil.isStringValid(viaDomicilio)) {

            try {
              FeaturesGeoserver geoserver =
                  ServiceLocator.getInstance()
                      .getServiziGeoserver()
                      .getGeoserverByCodiceStradaNumeroCivico(
                          codiceViaDomicilio, numeroCivicoDomicilio, viaDomicilio);

              datiNascitaResidenzaDomicilio.setAutocompleteViario(geoserver);

            } catch (BusinessException | ApiException e) {
              log.error("Errore geoserver da comune goadev mensa " + e.getMessage());
            }
          }
        }

        datiFiglioExtend.setDatiNascitaResidenzaDomicilio(datiNascitaResidenzaDomicilio);
      }
    } else {
      datiFiglio = new DatiDipartimentaliBambino();
      datiFiglioExtend = new DatiFiglioExtend();

      DatiGeneraliAnagrafe datiGeneraliAnagrafe = new DatiGeneraliAnagrafe();
      datiGeneraliAnagrafe.setNome(nomeIscritto);
      datiGeneraliAnagrafe.setCognome(cognomeIscritto);
      datiGeneraliAnagrafe.setDataNascita(dataNascitaIscritto);
      datiGeneraliAnagrafe.setCodiceFiscale(codiceFiscaleIscritto);

      datiFiglioExtend.setDatiGeneraliAnagrafe(datiGeneraliAnagrafe);

      datiFiglioExtend.setDatiNascitaResidenzaDomicilio(new DatiNascitaResidenzaDomicilio());
    }

    log.debug("CP datiFiglioExtend = " + datiFiglioExtend);

    return datiFiglioExtend;
  }

  @Override
  public DatiDietaSpecialeValida getDietaSpecialeValida(String codiceFiscaleIscritto)
      throws ApiException, BusinessException {
    log.debug("CP getDietaSpecialeValida = " + codiceFiscaleIscritto);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiDieteSpeciali().getDietaSpecialeValida(codiceFiscaleIscritto);
    } catch (BusinessException e) {
      log.error("Errore getDietaSpecialeValida:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDietaSpecialeValida: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDietaSpecialeValida: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("dieta speciale"));
    } catch (Exception e) {
      log.error("Errore generico getDietaSpecialeValida:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public DatiInfoBollettazione getDatiInfoBollettazione(String codiceFiscaleGenitore)
      throws BusinessException, ApiException, IOException {
    log.debug("CP getDatiInfoBollettazione = " + codiceFiscaleGenitore);
    ServiceLocatorLivelloUnoBollettazioneMensa instance =
        ServiceLocatorLivelloUnoBollettazioneMensa.getInstance();
    try {
      return instance.getApiRistorazionePortale().getDatiInfoBollettazione(codiceFiscaleGenitore);
    } catch (BusinessException e) {
      log.error("Errore getDietaSpecialeValida:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiInfoBollettazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziRistorazioneImpl -- getDatiInfoBollettazione: errore durante la chiamata delle API Goadev ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("bollettazione mensa scolastica"));
    } catch (Exception e) {
      log.error("Errore generico getDatiInfoBollettazione:", e);
      throw new BusinessException(ERRORE_CHIAMATA_API);
    } finally {
      instance.closeConnection();
    }
  }
}
