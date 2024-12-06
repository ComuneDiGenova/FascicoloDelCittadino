package it.liguriadigitale.ponmetro.portale.business.tributi.imu.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoRimborsoImu;
import it.liguriadigitale.ponmetro.portale.business.tributi.imu.service.ServiziImuEngService;
import it.liguriadigitale.ponmetro.portale.pojo.imu.CategoriaCatastale;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImuAllegato;
import it.liguriadigitale.ponmetro.portale.pojo.imu.StatoRimborsoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tributi.model.Allegato;
import it.liguriadigitale.ponmetro.tributi.model.DatiAnnualitaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.DatiAnnualitaRimborsoPost;
import it.liguriadigitale.ponmetro.tributi.model.DatiIstanzaPost;
import it.liguriadigitale.ponmetro.tributi.model.DatiPagamentoPost;
import it.liguriadigitale.ponmetro.tributi.model.DatiRichiedentePost;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioPraticaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.ListaImmobiliPost;
import it.liguriadigitale.ponmetro.tributi.model.PraticaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.PraticaRimborsoPost;
import it.liguriadigitale.ponmetro.tributi.model.ProtocollazioneIstanzaRimborso;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziImuEngImpl implements ServiziImuEngService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_IMU_ENG = "Errore di connessione alle API IMU ENG";

  @Override
  public List<RimborsoImu> getPraticheRimborsoIMU(String codiceFiscale)
      throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      Comparator<RimborsoImu> comparator = Comparator.comparingLong(RimborsoImu::getAnnoDocumento);
      comparator = comparator.thenComparingInt(RimborsoImu::getNumeroDocumento);
      comparator = comparator.reversed();

      List<PraticaRimborso> pratiche =
          instance.getApiTributi().getPraticheRimborsoIMU(codiceFiscale);

      return pratiche.stream()
          .map(
              x -> {
                log.debug("[MapRimborso] " + x.getStatoOpe());

                RimborsoImu imu = new RimborsoImu();
                imu.setAnnoDocumento(x.getAnnDoc());
                imu.setDataPresentazione(
                    LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(x.getDatPre()));
                imu.setNumeroDocumento(x.getNumDoc());
                imu.setStato(StatoRimborsoEnum.valueOf(x.getStatoOpe())); // getStatoPratica()
                imu.setUriPratica(x.getUriPratica());

                return imu;
              })
          .sorted(comparator)
          .collect(Collectors.toList());

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getPraticheRimborsoIMU: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getPraticheRimborsoIMU: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- getPraticheRimborsoIMU: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- getPraticheRimborsoIMU: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public IstanzaRimborso inserisciAllegato(
      String fileName,
      Long praticaRimborsoId,
      String codiceFiscale,
      String tipoAllegato,
      String fileBase64)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {

      log.debug("[inserisciAllegato] Invio il file ad Eng: " + fileName);

      return instance
          .getApiTributi()
          .inserisciAllegato(fileName, praticaRimborsoId, codiceFiscale, tipoAllegato, fileBase64);
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- getPraticheRimborsoIMU: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getPraticheRimborsoIMU: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- getPraticheRimborsoIMU: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- getPraticheRimborsoIMU: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public IstanzaRimborso praticaRimborsoImu(RimborsoImu praticaRimborso)
      throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {

      PraticaRimborsoPost praticaRimborsoPost = new PraticaRimborsoPost();

      ServiceLocator.getInstance();

      praticaRimborsoPost.setDatiIstanza(createDatiIstanza(praticaRimborso));
      praticaRimborsoPost.setDatiRichiedente(createDatiRichiedente(praticaRimborso));
      praticaRimborsoPost.setDatiPagamento(createDatiPagamento(praticaRimborso));
      praticaRimborsoPost.setDatiAnnualitaRimborso(
          createListaDatiAnnualitaRimborso(praticaRimborso));
      praticaRimborsoPost.setListaImmobili(createListaImmobiliPost(praticaRimborso));

      return instance.getApiTributi().praticaRimborsoImu(praticaRimborsoPost);

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- praticaRimborsoImu: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (NotFoundException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoImu: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoImu: errore WebApplicationException:"
              + e.getCause().getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(),
          e.getCause().getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoImu: errore durante la chiamata delle API IMU ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public IstanzaRimborso cancellaAllegato(Long idAllegato) throws ApiException, BusinessException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {
      return instance.getApiTributi().cancellaAllegato(idAllegato);
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- cancellaAllegato: errore API TARI ENG: ");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- cancellaAllegato: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- cancellaAllegato: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- cancellaAllegato: errore durante la chiamata delle API IMU ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  private DatiIstanzaPost createDatiIstanza(RimborsoImu praticaRimborso) {
    DatiIstanzaPost datiIstanzaPost = new DatiIstanzaPost();
    datiIstanzaPost.setTipologiaRichiedenteRimborso(praticaRimborso.getTipologiaRichiedenteEng());
    datiIstanzaPost.setDataIstanza(
        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    datiIstanzaPost.setCfTutelato(praticaRimborso.getCfTutelato());

    if (LabelFdCUtil.checkIfNotNull(praticaRimborso.getDelegato())) {
      datiIstanzaPost.setCfDel(praticaRimborso.getDelegato().getCodiceFiscaleDelegato());
    }

    datiIstanzaPost.setNote(praticaRimborso.getAnnotazioni());

    return datiIstanzaPost;
  }

  private DatiRichiedentePost createDatiRichiedente(RimborsoImu praticaRimborso) {
    DatiRichiedentePost datiRichiedente = new DatiRichiedentePost();
    datiRichiedente.setCf(praticaRimborso.getCf());
    datiRichiedente.setProvincia(praticaRimborso.getProvincia());
    datiRichiedente.setComune(praticaRimborso.getComune());
    datiRichiedente.setIndirizzo(praticaRimborso.getIndirizzo());
    datiRichiedente.setCivico(
        LabelFdCUtil.checkIfNotNull(praticaRimborso.getCivico())
            ? Long.valueOf(praticaRimborso.getCivico())
            : null);

    datiRichiedente.setEsponente(praticaRimborso.getEsponente());
    datiRichiedente.setScala(praticaRimborso.getScala());
    datiRichiedente.setInterno(praticaRimborso.getInterno());
    datiRichiedente.setColore(praticaRimborso.getColore());
    datiRichiedente.setCap(praticaRimborso.getCap());
    datiRichiedente.setCellulare(praticaRimborso.getCellulare());
    datiRichiedente.setTel(praticaRimborso.getTelefono());
    datiRichiedente.setEmail(praticaRimborso.getEmail());
    datiRichiedente.setPec(praticaRimborso.getPec());

    return datiRichiedente;
  }

  private DatiPagamentoPost createDatiPagamento(RimborsoImu praticaRimborso) {
    DatiPagamentoPost datiPagamentoPost = new DatiPagamentoPost();
    datiPagamentoPost.setModPag(praticaRimborso.getDecodificaModalitaPagamentoEng());

    if (praticaRimborso.getModalitaPagamento().equals(ModalitaPagamentoEnum.CAB)) {
      datiPagamentoPost.setIban(praticaRimborso.getBonificoBancario().getIban());
      datiPagamentoPost.setDescBanca(praticaRimborso.getBonificoBancario().getIstituto());
      datiPagamentoPost.setSwift(praticaRimborso.getBonificoBancario().getSwift());
      datiPagamentoPost.setCfIntConto(
          praticaRimborso.getBonificoBancario().getCodiceFiscaleIntestatario());
    }

    return datiPagamentoPost;
  }

  private List<DatiAnnualitaRimborsoPost> createListaDatiAnnualitaRimborso(
      RimborsoImu praticaRimborso) {
    // TODO Auto-generated method stub

    if (LabelFdCUtil.checkIfNull(praticaRimborso.getVersamenti())) {
      return new ArrayList<DatiAnnualitaRimborsoPost>();
    }

    return praticaRimborso.getVersamenti().stream()
        .map(
            x -> {
              DatiAnnualitaRimborsoPost annualita = new DatiAnnualitaRimborsoPost();
              annualita.setAnno(new Long(x.getAnno()));
              annualita.setQuotaCom(x.getQuotaComune());
              annualita.setQuotaStato(x.getQuotaStato());
              annualita.setTextAltro(x.getAltro());
              annualita.setMotivazione(x.getMotivazioneVersamento().name());

              return annualita;
            })
        .collect(Collectors.toList());
  }

  private List<ListaImmobiliPost> createListaImmobiliPost(RimborsoImu praticaRimborso) {
    if (LabelFdCUtil.checkIfNull(praticaRimborso.getImmobili())) {
      return new ArrayList<ListaImmobiliPost>();
    }

    return praticaRimborso.getImmobili().stream()
        .map(
            x -> {
              return mapImmobilePost(x);
            })
        .collect(Collectors.toList());
  }

  private ListaImmobiliPost mapImmobilePost(Immobile immobile) {
    ListaImmobiliPost immobilePost = new ListaImmobiliPost();
    immobilePost.setAltro(immobile.getAltro());
    immobilePost.setCap(immobile.getCap());
    immobilePost.setCategoria(immobile.getCategoria().getCodice());
    immobilePost.setCiv(immobile.getCivico());
    immobilePost.setClasse(immobile.getClasse());
    immobilePost.setColore(immobile.getColore());
    immobilePost.setComune(immobile.getComune());
    immobilePost.setEsp(immobile.getEsponente());
    immobilePost.setFoglio(immobile.getFoglio());
    immobilePost.setInterno(immobile.getInterno());
    immobilePost.setNumero(immobile.getParticella());
    immobilePost.setPercPoss(immobile.getPercentualePossesso().toString());
    immobilePost.setPiano(immobile.getPiano());
    immobilePost.setProvincia(immobile.getProvincia());
    immobilePost.setSc(immobile.getScala());
    immobilePost.setSubalterno(immobile.getSubalterno());
    immobilePost.setTipo(immobile.getTipo().name());
    immobilePost.setUtilizzo(immobile.getUtilizzo().name());
    immobilePost.setVia(immobile.getIndirizzo());
    immobilePost.setSezione(immobile.getSezione());

    return immobilePost;
  }

  @Override
  public RimborsoImu dettaglioPraticaRimborsoImu(Integer praticaRimborsoImuId)
      throws ApiException, BusinessException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {

      DettaglioPraticaRimborso praticaRimborso =
          instance.getApiTributi().getDettaglioPraticaRimborsoIMU(praticaRimborsoImuId);

      RimborsoImu rimborso = new RimborsoImu(praticaRimborso, praticaRimborsoImuId);
      rimborso.setVersamenti(mapAnnualitaDaEng(praticaRimborso.getDatiAnnualitaRimborso()));
      return rimborso;

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- dettaglioPraticaRimborsoImu: errore API TARI ENG: ");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- dettaglioPraticaRimborsoImu: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- dettaglioPraticaRimborsoImu: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- dettaglioPraticaRimborsoImu: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  private List<Versamento> mapAnnualitaDaEng(List<DatiAnnualitaRimborso> datiAnnualitaRimborso) {
    if (LabelFdCUtil.checkIfNull(datiAnnualitaRimborso)) {
      return new ArrayList<Versamento>();
    }

    return datiAnnualitaRimborso.stream()
        .map(
            x -> {
              return new Versamento(x);
            })
        .collect(Collectors.toList());
  }

  @Override
  public ProtocollazioneIstanzaRimborso praticaRimborsoIMUProtocollazione(
      String nome,
      String cognome,
      String personaFisicaOGiuridica,
      String codiceFiscale,
      String codiceContribuente,
      String descrizioneDocumento,
      Long progressivoIstanzaRimborso)
      throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {

      return instance
          .getApiTributi()
          .praticaRimborsoIMUProtocollazione(
              nome,
              cognome,
              personaFisicaOGiuridica,
              codiceFiscale,
              codiceContribuente,
              descrizioneDocumento,
              progressivoIstanzaRimborso);

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- praticaRimborsoIMUProtocollazione: errore API TARI ENG: ");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- praticaRimborsoIMUProtocollazione: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoIMUProtocollazione: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoIMUProtocollazione: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public IstanzaRimborso praticaRimborsoImuAnnullamento(Long progressivoIstanzaRimborso)
      throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {

      return instance.getApiTributi().praticaRimborsoImuAnnullamento(progressivoIstanzaRimborso);

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- praticaRimborsoImuAnnullamento: errore API TARI ENG: ");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- praticaRimborsoImuAnnullamento: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoImuAnnullamento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- praticaRimborsoImuAnnullamento: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<RimborsoImuAllegato> getElencoAllegatiPraticaRimborso(
      String codiceFiscale, Long progressivoIstanzaRimborso)
      throws BusinessException, ApiException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {

      List<Allegato> allegati =
          instance
              .getApiTributi()
              .getElencoAllegatiPraticaRimborso(codiceFiscale, progressivoIstanzaRimborso);

      return allegati.stream()
          .map(
              x -> {
                return new RimborsoImuAllegato(
                    x.getIdAllegato(), x.getNomeFile(), x.getTipoDocumento(), x.getContenutoFile());
              })
          .collect(Collectors.toList());

    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("ServiziTariEngImpl -- getElencoAllegatiPraticaRimborso: errore API TARI ENG: ");
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getElencoAllegatiPraticaRimborso: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_IMU_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziImuEngImpl -- getElencoAllegatiPraticaRimborso: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- getElencoAllegatiPraticaRimborso: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public String getLinkFromResourceDB(String chiave) {
    log.debug("CP getLinkFromResourceDB");

    String massimo = "";
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();
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
  public List<CategoriaCatastale> getCategorieCatastali() throws BusinessException {
    // TODO Auto-generated method stub
    ServiceLocatorLivelloUnoRimborsoImu instance =
        ServiceLocatorLivelloUnoRimborsoImu.getInstance();

    try {
      return instance.getApiCategorieCatastali().getCategorieCatastali().stream()
          .map(
              x -> {
                CategoriaCatastale cat = new CategoriaCatastale();
                cat.setCodice(x.getCt46CodCategoria());
                cat.setDescrizione(x.getCt46Descriz());
                return cat;
              })
          .collect(Collectors.toList());
    } catch (BusinessException e) {
      log.error("Errore durante getCategorieCatastali = " + e.getMessage(), e);
      throw new BusinessException(ERRORE_API_IMU_ENG);
    } catch (RuntimeException e) {
      log.error(
          "ServiziImuEngImpl -- getCategorieCatastali: errore durante la chiamata delle API IMU ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("IMU"));
    } finally {
      instance.closeConnection();
    }
  }
}
