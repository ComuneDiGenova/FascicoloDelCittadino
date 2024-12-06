package it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.Debito;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.EsitoPagamento;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.RicevutaPagamento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoTariEng;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarieng.service.ServiziTariEngService;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mip.MipData;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiDocumentiTariEng;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.pagopa.PagamentoOnLinePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarieng.model.Debitore;
import it.liguriadigitale.ponmetro.tarieng.model.DettaglioDocumentoEmesso;
import it.liguriadigitale.ponmetro.tarieng.model.DocumentiEmessi;
import it.liguriadigitale.ponmetro.tarieng.model.DocumentiPDF;
import it.liguriadigitale.ponmetro.tarieng.model.QuadroTributario;
import it.liguriadigitale.ponmetro.tarieng.model.Rate;
import it.liguriadigitale.ponmetro.tarieng.model.RicevutaTelematica;
import it.liguriadigitale.ponmetro.tarieng.model.SchedaTributoTari;
import it.liguriadigitale.ponmetro.tarieng.model.Tributi;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanzaRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.EnteBeneficiarioRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatoRimborso.StatoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoGETResponse;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import it.liguriadigitale.ponmetro.taririmborsieng.model.NuovoRimborso;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

public class ServiziTariEngImpl implements ServiziTariEngService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TARI_ENG = "Errore di connessione alle API TARI ENG";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioContribuente", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("tariEng", "La mia TARI"));

    return listaBreadcrumb;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbDettagli() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioContribuente", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("tariEng", "La mia TARI"));
    listaBreadcrumb.add(new BreadcrumbFdC("tariEngDettagli", "Dettagli"));

    return listaBreadcrumb;
  }

  @Override
  public List<Legenda> getListaLegenda() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("Situazione corretta");
    legenda1.setStile("badge badge-success");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("Pagamento non pervenuto");
    legenda2.setStile("badge badge-primary");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Riscontrate anomalie");
    legenda3.setStile("badge badge-danger");
    listaLegenda.add(legenda3);

    Legenda legenda4 = new Legenda();
    legenda4.setTesto("Verifica non effettuata");
    legenda4.setStile("badge badge-secondary");
    listaLegenda.add(legenda4);

    return listaLegenda;
  }

  @Override
  public List<Legenda> getListaLegendaRimborsi() {
    List<Legenda> listaLegenda = new ArrayList<>();

    Legenda legenda1 = new Legenda();
    legenda1.setTesto("Inserito");
    legenda1.setStile("badge bg-primary");
    listaLegenda.add(legenda1);

    Legenda legenda2 = new Legenda();
    legenda2.setTesto("In elaborazione");
    legenda2.setStile("badge bg-primary");
    listaLegenda.add(legenda2);

    Legenda legenda3 = new Legenda();
    legenda3.setTesto("Emesso");
    legenda3.setStile("badge bg-primary");
    listaLegenda.add(legenda3);

    Legenda legenda4 = new Legenda();
    legenda4.setTesto("Liquidato");
    legenda4.setStile("badge bg-primary");
    listaLegenda.add(legenda4);

    Legenda legenda5 = new Legenda();
    legenda5.setTesto("Respinta");
    legenda5.setStile("badge bg-primary");
    listaLegenda.add(legenda5);

    return listaLegenda;
  }

  @Override
  public Tributi getQuadroTributarioTARI(String codiceFiscale)
      throws BusinessException, ApiException {
    log.debug("CP getQuadroTributarioTARI = " + codiceFiscale);

    ServiceLocatorLivelloUnoTariEng instance = ServiceLocatorLivelloUnoTariEng.getInstance();

    try {

      return instance.getApiTariEng().quadroTributarioTARI(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getQuadroTributarioTARI: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getQuadroTributarioTARI: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getQuadroTributarioTARI: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- getQuadroTributarioTARI: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public SchedaTributoTari getSchedaTributoTARI(String uri) throws BusinessException, ApiException {
    log.debug("CP schedaTributoTARI = " + uri);

    ServiceLocatorLivelloUnoTariEng instance = ServiceLocatorLivelloUnoTariEng.getInstance();

    try {
      return ServiceLocatorLivelloUnoTariEng.getInstance().getApiTariEng().schedaTributoTARI(uri);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- schedaTributoTARI: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- schedaTributoTARI: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- schedaTributoTARI: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- schedaTributoTARI: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public Map<Long, QuadroTributario> getMappaAnniTributiTari(Tributi tari) {
    log.debug("CP getMappaAnniTributi");

    Map<Long, QuadroTributario> mappa = new HashMap<Long, QuadroTributario>();

    Map<Long, QuadroTributario> treeMap = new HashMap<Long, QuadroTributario>();

    if (LabelFdCUtil.checkIfNotNull(tari) && LabelFdCUtil.checkIfNotNull(tari.getDett())) {

      mappa =
          tari.getDett().parallelStream()
              .collect(Collectors.toMap(quadro -> quadro.getAnno(), quadro -> quadro));

      treeMap =
          new TreeMap<Long, QuadroTributario>(
              new Comparator<Long>() {

                @Override
                public int compare(Long o1, Long o2) {
                  return o2.compareTo(o1);
                }
              });

      treeMap.putAll(mappa);
    }

    return treeMap;
  }

  @Override
  public List<DatiDocumentiTariEng> getDatiTariCompletiDellAnno(QuadroTributario elem)
      throws BusinessException, ApiException {
    log.debug("CP getDatiTariCompletiDellAnno ");

    List<DatiDocumentiTariEng> listaDatiTariCompletiDellAnno =
        new ArrayList<DatiDocumentiTariEng>();

    if (LabelFdCUtil.checkIfNotNull(elem)) {

      if (LabelFdCUtil.checkIfNotNull(elem.getDeb())) {

        for (Debitore debitore : elem.getDeb()) {

          log.debug("CP idDeb = " + debitore.getIdDeb());

          SchedaTributoTari schedaTributo = getSchedaTributoTARI(debitore.getUri());

          if (LabelFdCUtil.checkIfNotNull(schedaTributo)
              && LabelFdCUtil.checkIfNotNull(schedaTributo.getDocEmessi())
              && !LabelFdCUtil.checkEmptyList(schedaTributo.getDocEmessi())) {

            for (DocumentiEmessi documentoEmesso : schedaTributo.getDocEmessi()) {

              DatiDocumentiTariEng dati = new DatiDocumentiTariEng();

              dati.setEsito(elem.getEsitoV());
              dati.setAnno(elem.getAnno());

              dati.setIdDeb(debitore.getIdDeb());
              dati.setTipoUtz(debitore.getTipoUtz());
              dati.setDesc(debitore.getDesc());

              dati.setEccTari(schedaTributo.getEccTari());
              dati.setEccTefa(schedaTributo.getEccTefa());
              dati.setEccTariR(schedaTributo.getEccTariR());

              dati.setFrqAgg(schedaTributo.getFrqAgg());

              dati.setIdDoc(documentoEmesso.getIdDoc());
              dati.setTipoDoc(documentoEmesso.getTipoDoc());
              dati.setImporto(documentoEmesso.getImp());
              dati.setNumDoc(documentoEmesso.getNumDoc());
              dati.setRuolo(documentoEmesso.getRuolo());
              dati.setAnnoEmissione(documentoEmesso.getAnnoEmi());

              dati.setIncassato(documentoEmesso.getIncas());
              dati.setFlagMigrazioneEsperta(documentoEmesso.getFlgMigr());

              dati.setDenominazione(documentoEmesso.getDenom());
              dati.setIndirizzo(documentoEmesso.getIndir());
              dati.setIdAllegato(documentoEmesso.getIdAlleg());

              dati.setDov(schedaTributo.getDov());
              dati.setPag(schedaTributo.getPag());
              dati.setIdUltimoDoc(schedaTributo.getIdUltimoDoc());
              dati.setUriDocumento(documentoEmesso.getUriDoc());

              dati.setListaDocEmessiPiena(true);

              listaDatiTariCompletiDellAnno.add(dati);
            }

          } else {
            DatiDocumentiTariEng dati = new DatiDocumentiTariEng();
            dati.setEsito(elem.getEsitoV());
            dati.setAnno(elem.getAnno());
            dati.setIdDeb(debitore.getIdDeb());
            dati.setTipoUtz(debitore.getTipoUtz());
            dati.setDesc(debitore.getDesc());

            dati.setEccTari(schedaTributo.getEccTari());
            dati.setEccTefa(schedaTributo.getEccTefa());
            dati.setEccTariR(schedaTributo.getEccTariR());

            dati.setFrqAgg(schedaTributo.getFrqAgg());

            dati.setDov(schedaTributo.getDov());
            dati.setPag(schedaTributo.getPag());
            dati.setIdUltimoDoc(schedaTributo.getIdUltimoDoc());

            dati.setListaDocEmessiPiena(false);

            listaDatiTariCompletiDellAnno.add(dati);
          }
        }
      }
    }
    return listaDatiTariCompletiDellAnno;
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("Dati", 1));
    listaStep.add(new StepFdC("Beneficiario", 2));
    listaStep.add(new StepFdC("Riepilogo dati", 3));
    listaStep.add(new StepFdC("Esito", 4));

    return listaStep;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborso() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioContribuente", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("tariEng", "La mia TARI"));
    listaBreadcrumb.add(new BreadcrumbFdC("richiestaRimborsotariEng", "Richiesta rimborso"));

    return listaBreadcrumb;
  }

  @Override
  public AbstractLink creaBtnPaga(Rate rate, Utente utente) {
    String wicketID = "btnPaga";
    MipData data = new MipData();
    if (LabelFdCUtil.checkIfNotNull(rate)) {
      data = creaMipData(rate, utente);
    }
    return creaBottoneMipPagoPA(rate, wicketID, data);
  }

  @Override
  public MipData creaMipData(Rate rata, Utente utente) {
    MipData data = new MipData();

    try {
      Debito debitoMIP = getDebitoMIPDaIUV(utente.getCodiceFiscaleOperatore(), rata.getIuv());

      BigDecimal importo = new BigDecimal(0);
      if (LabelFdCUtil.checkIfNotNull(debitoMIP)
          && LabelFdCUtil.checkIfNotNull(debitoMIP.getImportoDaPagare())) {
        importo = new BigDecimal(debitoMIP.getImportoDaPagare());
      }

      data.setImportoAvviso(importo);
      data.setNumeroDocumento(rata.getIuv());
      data.setIdServizio("TARI_GERI_AVV_NP");
      data.setUtente(utente);

    } catch (BusinessException | ApiException e) {
      log.error("Errore creaMipData TARI ENG: " + e.getMessage(), e);
    }

    return data;
  }

  @Override
  public Link<Void> creaBottoneMipPagoPA(Rate rata, String wicketID, MipData data) {
    @SuppressWarnings("rawtypes")
    Link<Void> link =
        new Link<Void>(wicketID) {

          private static final long serialVersionUID = -8794445862774081974L;

          @Override
          public void onClick() {
            PagamentoOnLinePage page = new PagamentoOnLinePage(data);
            setResponsePage(page);
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    return link;
  }

  @Override
  public Debito getDebitoMIPDaIUV(String cf, String iuv) throws BusinessException, ApiException {
    log.debug("CP getDebitoMIPDaIUV");

    Debito debitoMIP = null;

    try {

      debitoMIP =
          ServiceLocator.getInstance()
              .getServiziMipVerticali()
              .getListaTipologieEntrateNonAttualizzaByCfIuv(cf, iuv);

      return debitoMIP;

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getDebitoMIPDaIUV: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getDebitoMIPDaIUV: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getDebitoMIPDaIUV: errore WebApplicationException:"
              + e.getMessage());

      return debitoMIP;
    }
  }

  @Override
  public RicevutaPagamento getRicevutaMipDaIUV(Utente utente, String iuv)
      throws BusinessException, ApiException {
    log.debug("CP getRicevutaMipDaIUV");

    RicevutaPagamento ricevutaMIP = null;

    try {

      List<RicevutaPagamento> listaRicevute =
          ServiceLocator.getInstance().getServiziMipVerticali().getRicevutaDaIuv(utente, iuv, true);

      if (LabelFdCUtil.checkIfNotNull(listaRicevute)
          && !LabelFdCUtil.checkEmptyList(listaRicevute)) {
        ricevutaMIP =
            listaRicevute.stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem.getEsitoPagamento())
                            && elem.getEsitoPagamento().equals(EsitoPagamento.OK))
                .findAny()
                .orElse(null);
      }

      return ricevutaMIP;

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getRicevutaMipDaIUV: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getRicevutaMipDaIUV: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getRicevutaMipDaIUV: errore WebApplicationException:"
              + e.getMessage());

      return ricevutaMIP;
    }
  }

  @Override
  public RicevutaTelematica getStampaRicevutaTelematica(
      Integer idDebitoreGeri, Integer idRicevutaTelematicaGeri)
      throws BusinessException, ApiException {
    log.debug(
        "CP getStampaRicevutaTelematica = " + idDebitoreGeri + " - " + idRicevutaTelematicaGeri);
    try {
      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariEng()
          .stampaRicevutaTelematica(idDebitoreGeri, idRicevutaTelematicaGeri);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getStampaRicevutaTelematica: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaRicevutaTelematica: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaRicevutaTelematica: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaRicevutaTelematica: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    }
  }

  @Override
  public DocumentiPDF getStampaDocumentoPDF(Integer idDebitoreGeri, Integer idAllegatoGeri)
      throws BusinessException, ApiException {
    log.debug("CP getStampaDocumentoPDF = " + idDebitoreGeri + " - " + idAllegatoGeri);
    try {
      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariEng()
          .stampaDocumentoPDF(idDebitoreGeri, idAllegatoGeri);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getStampaDocumentoPDF: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaDocumentoPDF: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaDocumentoPDF: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- getStampaDocumentoPDF: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    }
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbRichiestaRimborsoTariErede() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("tributi", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("tariEng", "La mia TARI"));
    listaBreadcrumb.add(
        new BreadcrumbFdC(
            "richiestaRimborsoTariComeErede",
            "Richiesta rimborso TARI in qualità di erede/procuratore/amministratore di sostegno"));

    return listaBreadcrumb;
  }

  @Override
  public String getValoreDaDb(String chiave) {
    String data = "";
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);
      if (LabelFdCUtil.checkIfNotNull(valore)) {
        data = valore.getValore();
      }
    } catch (BusinessException e) {
      log.error("Errore durante get in Eng da DB = " + e.getMessage(), e);
    } finally {
      instance.closeConnection();
    }

    return data;
  }

  @Override
  public IstanzaRimborsoGETResponse getRimborsi(String codiceFiscale)
      throws BusinessException, ApiException {
    log.debug("CP getRimborsi = " + codiceFiscale);

    ServiceLocatorLivelloUnoTariEng instance = ServiceLocatorLivelloUnoTariEng.getInstance();

    try {

      return instance.getApiTariRimborsiEng().getRimborsi(codiceFiscale);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getRimborsi: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getRimborsi: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getRimborsi: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- getRimborsi: errore durante la chiamata delle API TARI ENG ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggiQuadroDettagli(
      DatiDocumentiTariEng documento) {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    String tipologiaEAnno = "";
    if (LabelFdCUtil.checkIfNotNull(documento.getTipoDoc())) {
      tipologiaEAnno = documento.getTipoDoc();
    }

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio(
        "Di seguito la sintesi dell'avviso di pagamento"
            .concat(" ")
            .concat(tipologiaEAnno)
            .concat(" ")
            .concat("a lei intestato"));
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }

  @Override
  public IstanzaRimborsoPOSTResponse setRichiestaRimborsoTARI(
      DatiRichiestaRimborsoTariEng datiRimborso) throws BusinessException, ApiException {
    log.debug("CP setRichiestaRimborsoTARI ");

    try {

      NuovoRimborso rimborso = new NuovoRimborso();

      DatiIstanzaRimborso istanzaRimborso = new DatiIstanzaRimborso();
      DatiRichiedenteRimborso datiRichiedenteRimborso = new DatiRichiedenteRimborso();
      DatiIstanza datiIstanza = new DatiIstanza();

      if (LabelFdCUtil.checkIfNotNull(datiRimborso)) {
        rimborso.setCodiceBelfiore(datiRimborso.getCodiceBelfiore());

        datiRichiedenteRimborso.setTipologiaRichiedenteRimborso(
            datiRimborso.getTipologiaRichiedente());
        datiRichiedenteRimborso.setNome(datiRimborso.getNomeRichiedente());
        datiRichiedenteRimborso.setCognome(datiRimborso.getCognomeRichiedente());

        if (LabelFdCUtil.checkIfNotNull(datiRimborso.getIdDeb())) {
          datiRichiedenteRimborso.setIdDebitore(new BigDecimal(datiRimborso.getIdDeb()));
        }

        datiRichiedenteRimborso.setNumeroDocumento(datiRimborso.getNumeroDocumento());
        datiRichiedenteRimborso.setTipoRimborso(datiRimborso.getTipoRimborso());

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleRichiedente())) {
          datiRichiedenteRimborso.setCfPIva(
              datiRimborso.getCodiceFiscaleRichiedente().toUpperCase());
        }

        datiRichiedenteRimborso.setEnteBeneficiarioRimborso(datiRimborso.getEnteBeneficiarioTari());
        datiRichiedenteRimborso.setImportoIstanza(datiRimborso.getEccTari());

        LocalDate oggi = LocalDate.now();
        String dataOggi = LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(oggi);
        datiRichiedenteRimborso.setDataIstanza(dataOggi);

        datiRichiedenteRimborso.setEmailDiContatto(datiRimborso.getEmailRichiedente());
        datiRichiedenteRimborso.setNote(datiRimborso.getNote());

        istanzaRimborso.setDatiRichiedenteRimborso(datiRichiedenteRimborso);

        datiIstanza.setModalitaPagamento(datiRimborso.getModalitaDiPagamento());

        String nominativoDelegato = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeDelegato())) {
          nominativoDelegato = datiRimborso.getCognomeDelegato().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeDelegato())) {
          nominativoDelegato =
              nominativoDelegato.concat(datiRimborso.getNomeDelegato().toUpperCase());
        }
        datiIstanza.setNominativoDelegato(nominativoDelegato);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())) {
          datiIstanza.setCodiceFiscaleDelegato(
              datiRimborso.getCodiceFiscaleDelegato().toUpperCase());
        }

        String nominativoBeneficiario = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeBeneficiario())) {
          nominativoBeneficiario = datiRimborso.getCognomeBeneficiario().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeBeneficiario())) {
          nominativoBeneficiario =
              nominativoBeneficiario.concat(datiRimborso.getNomeBeneficiario().toUpperCase());
        }
        datiIstanza.setNominativoBeneficiario(nominativoBeneficiario);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleBeneficiario())) {
          datiIstanza.setCodiceFiscaleBeneficiario(
              datiRimborso.getCodiceFiscaleBeneficiario().toUpperCase());
        }

        datiIstanza.setIndirizzoBeneficiario(datiRimborso.getIndirizzoBeneficiario());
        datiIstanza.setCapBeneficiario(datiRimborso.getCapBeneficiario());
        datiIstanza.setComuneBeneficiario(datiRimborso.getComuneBeneficiario());

        datiIstanza.setIban(datiRimborso.getIban());
        datiIstanza.setSwift(datiRimborso.getSwift());

        datiIstanza.setDocumentiAllegati(datiRimborso.getListaAllegati());

        istanzaRimborso.setDatiIstanza(datiIstanza);

        rimborso.setIstanzaRimborso(istanzaRimborso);
      }

      log.debug("CP rimborso prima di POST = " + rimborso);

      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariRimborsiEng()
          .setRichiestaRimborso(rimborso);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- setRichiestaRimborsoTARI: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARI: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARI: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARI: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    }
  }

  @Override
  public IstanzaRimborsoPOSTResponse setRichiestaRimborsoTEFA(
      DatiRichiestaRimborsoTariEng datiRimborso) throws BusinessException, ApiException {
    log.debug("CP setRichiestaRimborsoTEFA ");

    try {

      NuovoRimborso rimborso = new NuovoRimborso();

      DatiIstanzaRimborso istanzaRimborso = new DatiIstanzaRimborso();
      DatiRichiedenteRimborso datiRichiedenteRimborso = new DatiRichiedenteRimborso();
      DatiIstanza datiIstanza = new DatiIstanza();

      if (LabelFdCUtil.checkIfNotNull(datiRimborso)) {
        rimborso.setCodiceBelfiore(datiRimborso.getCodiceBelfiore());

        datiRichiedenteRimborso.setTipologiaRichiedenteRimborso(
            datiRimborso.getTipologiaRichiedente());
        datiRichiedenteRimborso.setNome(datiRimborso.getNomeRichiedente());
        datiRichiedenteRimborso.setCognome(datiRimborso.getCognomeRichiedente());

        if (LabelFdCUtil.checkIfNotNull(datiRimborso.getIdDeb())) {
          datiRichiedenteRimborso.setIdDebitore(new BigDecimal(datiRimborso.getIdDeb()));
        }

        datiRichiedenteRimborso.setNumeroDocumento(datiRimborso.getNumeroDocumento());
        datiRichiedenteRimborso.setTipoRimborso(datiRimborso.getTipoRimborso());

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleRichiedente())) {
          datiRichiedenteRimborso.setCfPIva(
              datiRimborso.getCodiceFiscaleRichiedente().toUpperCase());
        }

        datiRichiedenteRimborso.setEnteBeneficiarioRimborso(datiRimborso.getEnteBeneficiarioTefa());
        datiRichiedenteRimborso.setImportoIstanza(datiRimborso.getEccTefa());

        LocalDate oggi = LocalDate.now();
        String dataOggi = LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(oggi);
        datiRichiedenteRimborso.setDataIstanza(dataOggi);

        datiRichiedenteRimborso.setEmailDiContatto(datiRimborso.getEmailRichiedente());
        datiRichiedenteRimborso.setNote(datiRimborso.getNote());

        istanzaRimborso.setDatiRichiedenteRimborso(datiRichiedenteRimborso);

        datiIstanza.setModalitaPagamento(datiRimborso.getModalitaDiPagamento());

        String nominativoDelegato = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeDelegato())) {
          nominativoDelegato = datiRimborso.getCognomeDelegato().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeDelegato())) {
          nominativoDelegato =
              nominativoDelegato.concat(datiRimborso.getNomeDelegato().toUpperCase());
        }
        datiIstanza.setNominativoDelegato(nominativoDelegato);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())) {
          datiIstanza.setCodiceFiscaleDelegato(
              datiRimborso.getCodiceFiscaleDelegato().toUpperCase());
        }

        String nominativoBeneficiario = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeBeneficiario())) {
          nominativoBeneficiario = datiRimborso.getCognomeBeneficiario().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeBeneficiario())) {
          nominativoBeneficiario =
              nominativoBeneficiario.concat(datiRimborso.getNomeBeneficiario().toUpperCase());
        }
        datiIstanza.setNominativoBeneficiario(nominativoBeneficiario);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleBeneficiario())) {
          datiIstanza.setCodiceFiscaleBeneficiario(
              datiRimborso.getCodiceFiscaleBeneficiario().toUpperCase());
        }

        datiIstanza.setIndirizzoBeneficiario(datiRimborso.getIndirizzoBeneficiario());
        datiIstanza.setCapBeneficiario(datiRimborso.getCapBeneficiario());
        datiIstanza.setComuneBeneficiario(datiRimborso.getComuneBeneficiario());

        datiIstanza.setIban(datiRimborso.getIban());
        datiIstanza.setSwift(datiRimborso.getSwift());

        datiIstanza.setDocumentiAllegati(datiRimborso.getListaAllegati());

        istanzaRimborso.setDatiIstanza(datiIstanza);

        rimborso.setIstanzaRimborso(istanzaRimborso);
      }

      log.debug("CP rimborso prima di POST = " + rimborso);

      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariRimborsiEng()
          .setRichiestaRimborso(rimborso);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- setRichiestaRimborsoTEFA: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTEFA: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTEFA: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTEFA: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    }
  }

  @Override
  public IstanzaRimborsoPOSTResponse setRichiestaRimborsoTARIREALE(
      DatiRichiestaRimborsoTariEng datiRimborso) throws BusinessException, ApiException {
    log.debug("CP setRichiestaRimborsoTARIREALE ");

    try {

      NuovoRimborso rimborso = new NuovoRimborso();

      DatiIstanzaRimborso istanzaRimborso = new DatiIstanzaRimborso();
      DatiRichiedenteRimborso datiRichiedenteRimborso = new DatiRichiedenteRimborso();
      DatiIstanza datiIstanza = new DatiIstanza();

      if (LabelFdCUtil.checkIfNotNull(datiRimborso)) {
        rimborso.setCodiceBelfiore(datiRimborso.getCodiceBelfiore());

        datiRichiedenteRimborso.setTipologiaRichiedenteRimborso(
            datiRimborso.getTipologiaRichiedente());
        datiRichiedenteRimborso.setNome(datiRimborso.getNomeRichiedente());
        datiRichiedenteRimborso.setCognome(datiRimborso.getCognomeRichiedente());

        if (LabelFdCUtil.checkIfNotNull(datiRimborso.getIdDeb())) {
          datiRichiedenteRimborso.setIdDebitore(new BigDecimal(datiRimborso.getIdDeb()));
        }

        datiRichiedenteRimborso.setNumeroDocumento(datiRimborso.getNumeroDocumento());
        datiRichiedenteRimborso.setTipoRimborso(datiRimborso.getTipoRimborso());

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleRichiedente())) {
          datiRichiedenteRimborso.setCfPIva(
              datiRimborso.getCodiceFiscaleRichiedente().toUpperCase());
        }

        datiRichiedenteRimborso.setEnteBeneficiarioRimborso(datiRimborso.getEnteBeneficiarioTari());
        datiRichiedenteRimborso.setImportoIstanza(datiRimborso.getEccTariR());

        LocalDate oggi = LocalDate.now();
        String dataOggi = LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(oggi);
        datiRichiedenteRimborso.setDataIstanza(dataOggi);

        datiRichiedenteRimborso.setEmailDiContatto(datiRimborso.getEmailRichiedente());
        datiRichiedenteRimborso.setNote(datiRimborso.getNote());

        istanzaRimborso.setDatiRichiedenteRimborso(datiRichiedenteRimborso);

        datiIstanza.setModalitaPagamento(datiRimborso.getModalitaDiPagamento());

        String nominativoDelegato = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeDelegato())) {
          nominativoDelegato = datiRimborso.getCognomeDelegato().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeDelegato())) {
          nominativoDelegato =
              nominativoDelegato.concat(datiRimborso.getNomeDelegato().toUpperCase());
        }
        datiIstanza.setNominativoDelegato(nominativoDelegato);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleDelegato())) {
          datiIstanza.setCodiceFiscaleDelegato(
              datiRimborso.getCodiceFiscaleDelegato().toUpperCase());
        }

        String nominativoBeneficiario = "";
        if (PageUtil.isStringValid(datiRimborso.getCognomeBeneficiario())) {
          nominativoBeneficiario = datiRimborso.getCognomeBeneficiario().toUpperCase().concat(" ");
        }
        if (PageUtil.isStringValid(datiRimborso.getNomeBeneficiario())) {
          nominativoBeneficiario =
              nominativoBeneficiario.concat(datiRimborso.getNomeBeneficiario().toUpperCase());
        }
        datiIstanza.setNominativoBeneficiario(nominativoBeneficiario);

        if (PageUtil.isStringValid(datiRimborso.getCodiceFiscaleBeneficiario())) {
          datiIstanza.setCodiceFiscaleBeneficiario(
              datiRimborso.getCodiceFiscaleBeneficiario().toUpperCase());
        }

        datiIstanza.setIndirizzoBeneficiario(datiRimborso.getIndirizzoBeneficiario());
        datiIstanza.setCapBeneficiario(datiRimborso.getCapBeneficiario());
        datiIstanza.setComuneBeneficiario(datiRimborso.getComuneBeneficiario());

        datiIstanza.setIban(datiRimborso.getIban());
        datiIstanza.setSwift(datiRimborso.getSwift());

        datiIstanza.setDocumentiAllegati(datiRimborso.getListaAllegati());

        istanzaRimborso.setDatiIstanza(datiIstanza);

        rimborso.setIstanzaRimborso(istanzaRimborso);
      }

      log.debug("CP rimborso prima di POST = " + rimborso);

      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariRimborsiEng()
          .setRichiestaRimborso(rimborso);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- setRichiestaRimborsoTARIREALE: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARIREALE: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARIREALE: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());

    } catch (RuntimeException e) {
      log.error(
          "ServiziTariEngImpl -- setRichiestaRimborsoTARIREALE: errore durante la chiamata delle API TARI ENG ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("TARI"));
    }
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @Override
  public String decodicaTipoUtenza(String tipoUtz) {
    String tipoUtenza = "";
    if (PageUtil.isStringValid(tipoUtz)) {
      if (tipoUtz.equalsIgnoreCase("D")) {
        tipoUtenza = "DOMESTICA";
      } else if (tipoUtz.equalsIgnoreCase("N")) {
        tipoUtenza = "NON DOMESTICA";
      }
    }
    return tipoUtenza;
  }

  @Override
  public List<DatiDocumentiTariEng> getListaDistinctSuIdDeb(
      List<DatiDocumentiTariEng> listaDatiDocumentiTariEng) {
    log.debug("CP getListaDistinctSuIdDeb");

    List<DatiDocumentiTariEng> listaDistinctSuIdDeb = new ArrayList<DatiDocumentiTariEng>();

    if (LabelFdCUtil.checkIfNotNull(listaDatiDocumentiTariEng)) {

      listaDistinctSuIdDeb =
          listaDatiDocumentiTariEng.stream()
              .filter(distinctByKey(elem -> elem.getIdDeb()))
              .collect(Collectors.toList());
    }

    log.debug("CP listaDistinctSuIdDeb = " + listaDistinctSuIdDeb);

    log.debug("CP listaDatiDocumentiTariEng = " + listaDatiDocumentiTariEng);

    return listaDistinctSuIdDeb;
  }

  @Override
  public DatiDocumentiTariEng setInfoSuEccedenze(DatiDocumentiTariEng elementoTari) {
    log.debug("CP setInfoSuEccedenze ");

    if (LabelFdCUtil.checkIfNotNull(elementoTari)) {
      Double totaleEccedenzeRimborso = new Double(0.0);

      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTari())
          && Double.compare(elementoTari.getEccTari(), 0.0) > 0) {

        log.debug("CP elementoTari.getEccTari() != null");

        totaleEccedenzeRimborso = Double.sum(totaleEccedenzeRimborso, elementoTari.getEccTari());

        elementoTari.setEccTariRichiedibile(true);
      }
      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTariR())
          && Double.compare(elementoTari.getEccTariR(), 0.0) > 0) {

        log.debug("CP elementoTari.getEccTariR() != null");

        totaleEccedenzeRimborso = Double.sum(totaleEccedenzeRimborso, elementoTari.getEccTariR());

        elementoTari.setEccTariRealeRichiedibile(true);
      }

      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTefa())
          && Double.compare(elementoTari.getEccTefa(), 0.0) > 0) {

        log.debug("CP elementoTari.getEccTefa() != null");

        totaleEccedenzeRimborso = Double.sum(totaleEccedenzeRimborso, elementoTari.getEccTefa());

        elementoTari.setEccTefaRichiedibile(true);
      }

      elementoTari.setTotaleEccedenze(totaleEccedenzeRimborso);

      if (Double.compare(elementoTari.getTotaleEccedenze(), 0.0) > 0) {

        log.debug("CP totale eccedenze > 0");

        log.debug("CP cf = " + elementoTari.getCodiceFiscale());

        List<DatiRimborso> listaTuttiRimborsiDiQuellIdDeb = new ArrayList<>();

        try {
          IstanzaRimborsoGETResponse rimborsi = getRimborsi(elementoTari.getCodiceFiscale());

          if (LabelFdCUtil.checkIfNotNull(rimborsi)
              && LabelFdCUtil.checkIfNotNull(rimborsi.getListaDatiRimborsi())
              && !LabelFdCUtil.checkEmptyList(rimborsi.getListaDatiRimborsi())) {

            listaTuttiRimborsiDiQuellIdDeb =
                rimborsi.getListaDatiRimborsi().stream()
                    .filter(
                        elem ->
                            LabelFdCUtil.checkIfNotNull(elem.getDatiRimborso())
                                && LabelFdCUtil.checkIfNotNull(
                                    elem.getDatiRimborso().getIdDebitore())
                                && LabelFdCUtil.checkIfNotNull(elementoTari.getIdDeb())
                                && elem.getDatiRimborso()
                                    .getIdDebitore()
                                    .equals(elementoTari.getIdDeb().intValue()))
                    .collect(Collectors.toList());

            log.debug(
                "CP lista rimborsi dopo filtro su id deb = "
                    + listaTuttiRimborsiDiQuellIdDeb.size());

            if (!LabelFdCUtil.checkEmptyList(listaTuttiRimborsiDiQuellIdDeb)) {

              log.debug("CP listaRimborsi dopo filtro piena " + elementoTari.getIdDeb());

              for (DatiRimborso elemRimborso : listaTuttiRimborsiDiQuellIdDeb) {
                if (LabelFdCUtil.checkIfNotNull(elemRimborso)) {
                  if (LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso())) {

                    log.debug(
                        "CP checkIstanzaAperta " + checkIstanzaAperta(elementoTari, elemRimborso));

                    log.debug(
                        "CP checkIstanzaApertaNuovo "
                            + checkIstanzaApertaNuovo(elementoTari, elemRimborso));

                    if (checkIstanzaApertaNuovo(elementoTari, elemRimborso)) {

                      log.debug("CP if su stato e date");

                      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTari())
                          && Double.compare(elementoTari.getEccTari(), 0.0) > 0
                          && PageUtil.isStringValid(
                              elemRimborso.getDatiRimborso().getTipoRimborso())
                          && elemRimborso
                              .getDatiRimborso()
                              .getTipoRimborso()
                              .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO.value())
                      /*&& PageUtil.isStringValid(elemRimborso.getDatiRimborso()
                      		.getEnteBeneficiarioRimborso())
                      && elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso()
                      .equalsIgnoreCase(EnteBeneficiarioRimborsoEnum.ENTE.value())*/ ) {
                        elementoTari.setEccTariRichiedibile(false);
                      }

                      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTefa())
                          && Double.compare(elementoTari.getEccTefa(), 0.0) > 0
                          && PageUtil.isStringValid(
                              elemRimborso.getDatiRimborso().getTipoRimborso())
                          && elemRimborso
                              .getDatiRimborso()
                              .getTipoRimborso()
                              .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO.value())
                      /*&& PageUtil.isStringValid(elemRimborso.getDatiRimborso()
                      		.getEnteBeneficiarioRimborso())
                      && elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso()
                      .equalsIgnoreCase(
                      		EnteBeneficiarioRimborsoEnum.PROVINCIA.value())*/ ) {
                        elementoTari.setEccTefaRichiedibile(false);
                      }

                      if (LabelFdCUtil.checkIfNotNull(elementoTari.getEccTariR())
                          && Double.compare(elementoTari.getEccTariR(), 0.0) > 0
                          && PageUtil.isStringValid(
                              elemRimborso.getDatiRimborso().getTipoRimborso())
                          && elemRimborso
                              .getDatiRimborso()
                              .getTipoRimborso()
                              .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZAREALE.value())
                      /*&& PageUtil.isStringValid(elemRimborso.getDatiRimborso()
                      		.getEnteBeneficiarioRimborso())
                      && elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso()
                      .equalsIgnoreCase(
                      		EnteBeneficiarioRimborsoEnum.ENTE.value())*/ ) {
                        elementoTari.setEccTariRealeRichiedibile(false);
                      }

                    } else {
                      log.debug("CP non entro in if su date e stato");
                    }
                  }
                }
              }

            } else {
              log.debug("CP listaRimborsi dopo filtro vuota");
            }

          } else {

            log.debug("CP listaRimborsi vuota");

            elementoTari.setRimborsoRichiedibile(true);
          }

        } catch (BusinessException | ApiException e) {
          log.error("Errore rimborsi tari eng: " + e.getMessage(), e);
        }
      } else {
        log.debug("CP totale eccedenze < = 0");

        elementoTari.setRimborsoRichiedibile(false);

        elementoTari.setEccTariRichiedibile(false);
        elementoTari.setEccTefaRichiedibile(false);
        elementoTari.setEccTariRealeRichiedibile(false);
      }

      if (elementoTari.isEccTariRichiedibile()
          || elementoTari.isEccTefaRichiedibile()
          || elementoTari.isEccTariRealeRichiedibile()) {
        elementoTari.setRimborsoRichiedibile(true);
      } else {
        elementoTari.setRimborsoRichiedibile(false);
      }

      setMessaggioInfoRimborsi(elementoTari);
    }

    return elementoTari;
  }

  private void setMessaggioInfoRimborsi(DatiDocumentiTariEng elementoTari) {

    log.debug(
        "CP messaggio da settare: "
            + elementoTari.isEccTariRichiedibile()
            + " - "
            + elementoTari.isEccTefaRichiedibile()
            + " - "
            + elementoTari.isEccTariRealeRichiedibile());

    log.debug(
        "CP ecc = "
            + elementoTari.getEccTari()
            + " - "
            + elementoTari.getEccTariR()
            + " - "
            + elementoTari.getEccTefa());

    if (elementoTari.isEccTariRichiedibile()
        && elementoTari.isEccTefaRichiedibile()
        && elementoTari.isEccTariRealeRichiedibile()) {
      elementoTari.setMessaggioPerEccedenze(
          "E' possibile inserire la richiesta di rimborso. La richiesta sarà processata mediante la predisposizione di tre nuove pratiche di rimborso.");
    }

    if (!elementoTari.isEccTariRichiedibile()
        && !elementoTari.isEccTefaRichiedibile()
        && !elementoTari.isEccTariRealeRichiedibile()
        && Double.compare(elementoTari.getTotaleEccedenze(), 0) > 0) {
      elementoTari.setMessaggioPerEccedenze(
          "La richiesta di rimborso è in elaborazione presso gli Uffici competenti.");
    }

    if ((elementoTari.isEccTariRichiedibile()
            && elementoTari.isEccTefaRichiedibile()
            && !elementoTari.isEccTariRealeRichiedibile())
        || (elementoTari.isEccTariRichiedibile()
            && !elementoTari.isEccTefaRichiedibile()
            && elementoTari.isEccTariRealeRichiedibile())
        || (!elementoTari.isEccTariRichiedibile()
            && elementoTari.isEccTefaRichiedibile()
            && elementoTari.isEccTariRealeRichiedibile())) {

      elementoTari.setMessaggioPerEccedenze(
          "E' possibile inserire la richiesta di rimborso. La richiesta sarà processata mediante la predisposizione di due nuove pratiche di rimborso.");
    }

    if ((elementoTari.isEccTariRichiedibile()
            && !elementoTari.isEccTefaRichiedibile()
            && !elementoTari.isEccTariRealeRichiedibile())
        || (!elementoTari.isEccTariRichiedibile()
            && elementoTari.isEccTefaRichiedibile()
            && !elementoTari.isEccTariRealeRichiedibile())
        || (!elementoTari.isEccTariRichiedibile()
            && !elementoTari.isEccTefaRichiedibile()
            && elementoTari.isEccTariRealeRichiedibile())) {
      elementoTari.setMessaggioPerEccedenze(
          "E' possibile inserire la richiesta di rimborso. La richiesta sarà processata mediante la predisposizione di una nuova pratica di rimborso.");
    }
  }

  private boolean checkIstanzaAperta(DatiDocumentiTariEng elementoTari, DatiRimborso elemRimborso) {
    return PageUtil.isStringValid(elemRimborso.getDatiRimborso().getStato())
        && (elemRimborso.getDatiRimborso().getStato().equalsIgnoreCase(StatoEnum.INSERITO.value())
            || elemRimborso
                .getDatiRimborso()
                .getStato()
                .equalsIgnoreCase(StatoEnum.INELABORAZIONE.value())
            || (LabelFdCUtil.checkIfNotNull(LabelFdCUtil.checkIfNotNull(elementoTari.getFrqAgg()))
                && LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso())
                && LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso().getDataValidazione())
                && (LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(elementoTari.getFrqAgg())
                        .isEqual(
                            LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                                elemRimborso.getDatiRimborso().getDataValidazione()))
                    || LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                            elemRimborso.getDatiRimborso().getDataValidazione())
                        .isAfter(
                            LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                                elementoTari.getFrqAgg())))
                && (elemRimborso
                        .getDatiRimborso()
                        .getStato()
                        .equalsIgnoreCase(StatoEnum.VALIDATO.value())
                    || elemRimborso
                        .getDatiRimborso()
                        .getStato()
                        .equalsIgnoreCase(StatoEnum.KOGERI.value())
                    || elemRimborso
                        .getDatiRimborso()
                        .getStato()
                        .equalsIgnoreCase(StatoEnum.TRASMESSOSIBAK.value())
                    || elemRimborso
                        .getDatiRimborso()
                        .getStato()
                        .equalsIgnoreCase(StatoEnum.KOSIBAK.value())
                    || elemRimborso
                        .getDatiRimborso()
                        .getStato()
                        .equalsIgnoreCase(StatoEnum.LIQUIDATO.value())
                /*
                 * || elemRimborso.getDatiRimborso().getStato()
                 * .equalsIgnoreCase(StatoEnum.ANNULLATO.value())
                 */ )));
  }

  private boolean checkIstanzaApertaNuovo(
      DatiDocumentiTariEng elementoTari, DatiRimborso elemRimborso) {
    return PageUtil.isStringValid(elemRimborso.getDatiRimborso().getStato())
        && (elemRimborso.getDatiRimborso().getStato().equalsIgnoreCase(StatoEnum.INSERITO.value())
            || elemRimborso
                .getDatiRimborso()
                .getStato()
                .equalsIgnoreCase(StatoEnum.INELABORAZIONE.value())
            || elemRimborso
                .getDatiRimborso()
                .getStato()
                .equalsIgnoreCase(StatoEnum.TRASMESSOSIBAK.value())
            || elemRimborso
                .getDatiRimborso()
                .getStato()
                .equalsIgnoreCase(StatoEnum.VALIDATO.value())
            || elemRimborso.getDatiRimborso().getStato().equalsIgnoreCase(StatoEnum.KOGERI.value())
            || elemRimborso.getDatiRimborso().getStato().equalsIgnoreCase(StatoEnum.KOSIBAK.value())
            || (LabelFdCUtil.checkIfNotNull(LabelFdCUtil.checkIfNotNull(elementoTari.getFrqAgg()))
                && LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso())
                && LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso().getDataValidazione())
                && (LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(elementoTari.getFrqAgg())
                        .isEqual(
                            LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                                elemRimborso.getDatiRimborso().getDataValidazione()))
                    || LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                            elemRimborso.getDatiRimborso().getDataValidazione())
                        .isAfter(
                            LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                                elementoTari.getFrqAgg())))
                && (elemRimborso
                    .getDatiRimborso()
                    .getStato()
                    .equalsIgnoreCase(StatoEnum.LIQUIDATO.value())
                /*
                 * || elemRimborso.getDatiRimborso().getStato()
                 * .equalsIgnoreCase(StatoEnum.ANNULLATO.value())
                 */ )));
  }

  @Override
  public List<ModalitaPagamentoEnum> getListaModalitaPagamento() {
    return Arrays.asList(ModalitaPagamentoEnum.values());
  }

  @Override
  public byte[] getHelpRimborsiPDF(Utente utente, String codiceHelp, Long idHelp)
      throws BusinessException {
    log.debug("getHelRimborsiPDF: ");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    Response response =
        instance
            .getApiPrivacy()
            .getDocumentoPrivacy(codiceHelp, utente.getIdAnonimoComuneGenova(), idHelp);
    InputStream myinputstream = response.readEntity(InputStream.class);
    try {
      return IOUtils.toByteArray(myinputstream);
    } catch (IOException e) {
      log.error("Impossibile recuperare PDF HELP rimborsi tari: ", e);
      throw new BusinessException("Impossibile recuperare PDF HELP rimborsi tari");
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DatiDocumentiTariEng setInfoPosizione(DatiDocumentiTariEng elementoTari) {

    log.debug(
        "CP setInfoPosizione " + elementoTari.getIdDoc() + " - anno " + elementoTari.getAnno());

    if (LabelFdCUtil.checkIfNotNull(elementoTari)) {

      if (!checkTotaleEccedenzePositivo(elementoTari)) {
        log.debug("CP eccedenza < 0");

        if (checkIdUltimoDocGeriPresente(elementoTari)) {
          log.debug("CP idUltimoDoc presente");

          elementoTari.setCardRimborsiPosizioneVisibile(false);

          if (!checkDovutoUgualePagato(elementoTari)) {

            log.debug("CP dovuto != pagato");

            if (checkIdUltimoDocGeriPresenteUgualeIdDoc(elementoTari)) {

              log.debug("CP checkIdUltimoDocGeriPresenteUgualeIdDoc true ");

              elementoTari.setBtnPagaConPagoPaVisibile(true);
            } else {

              log.debug("CP checkIdUltimoDocGeriPresenteUgualeIdDoc else ");

              elementoTari.setBtnPagaConPagoPaVisibile(false);
            }
          }

        } else {
          log.debug("CP idUltimoDoc assente");

          elementoTari.setCardRimborsiPosizioneVisibile(true);

          if (checkDovutoUgualePagato(elementoTari)) {
            log.debug("CP dovuto = pagato");

            // elementoTari.setMessaggioPosizione("I pagamenti risultano regolari");

            String valorePagamentiRegolari = getValoreDaDb("TARI_PAGAMENTI_REGOLARI");
            elementoTari.setMessaggioPosizione(valorePagamentiRegolari);

          } else {
            log.debug("CP dovuto != pagato");

            if (checkDovutoMaggiorePagato(elementoTari)) {
              log.debug("CP dovuto > pagato");

              if (PageUtil.isStringValid(elementoTari.getRuolo())) {
                if (elementoTari.getRuolo().equalsIgnoreCase("S")) {

                  // elementoTari.setMessaggioPosizione(
                  // "Sarà possibile pagare solo la cartella di pagamento emessa da Agenzia delle
                  // Entrate - Riscossione. L'eventuale versamento totale o parziale della
                  // cartella di pagamento non sarà visualizzabile in questa sede.");

                  String valoreARuolo = getValoreDaDb("TARI_ISCRITTO_A_RUOLO");
                  elementoTari.setMessaggioPosizione(valoreARuolo);

                } else {

                  // elementoTari.setMessaggioPosizione(
                  // "Se non hai ricevuto successivi documenti di Sollecito o Accertamento, puoi
                  // regolarizzare la tua posizione scaricando gli avvisi presenti nella sezione
                  // Documenti. In caso contrario dovrai effettuare il pagamento dell'importo
                  // riportato nel documento che ti è stato notificato.");
                  //
                  String valoreNonARuolo = getValoreDaDb("TARI_REGOLARIZZA");
                  elementoTari.setMessaggioPosizione(valoreNonARuolo);
                }
              } else {
                String valoreNonARuolo = getValoreDaDb("TARI_REGOLARIZZA");
                elementoTari.setMessaggioPosizione(valoreNonARuolo);
              }

            } else {
              log.debug("CP dovuto < pagato");

              // elementoTari.setMessaggioPosizione(
              // "Se non hai ricevuto successivi documenti di Sollecito o Accertamento, puoi
              // regolarizzare la tua posizione scaricando gli avvisi presenti nella sezione
              // Documenti. In caso contrario dovrai effettuare il pagamento dell'importo
              // riportato nel documento che ti è stato notificato.");

              String valoreNoRegolarizza = getValoreDaDb("TARI_NO_REGOLARIZZA");
              elementoTari.setMessaggioPosizione(valoreNoRegolarizza);
            }
          }
        }

      } else {

        log.debug("CP totale eccendeze > 0");

        elementoTari.setCardRimborsiPosizioneVisibile(true);
      }
    }

    // TODO per richiesta di uffici tari fino a quando Eng sistema
    //		Long annoDaNonFareVederePosizioneDebitoria = 2023L;
    //		if(LabelFdCUtil.checkIfNotNull(elementoTari) &&
    // elementoTari.getAnno().equals(annoDaNonFareVederePosizioneDebitoria)) {
    //			log.debug("CP entro in controllo per anno 2023");
    //			elementoTari.setCardRimborsiPosizioneVisibile(false);
    //		}
    // fine TODO per richiesta uffici tari per anno 2023

    return elementoTari;
  }

  private boolean checkTotaleEccedenzePositivo(DatiDocumentiTariEng elementoTari) {
    Double zeroDouble = Double.valueOf(0);

    return elementoTari.getTotaleEccedenze().compareTo(zeroDouble) > 0;
  }

  private boolean checkDovutoUgualePagato(DatiDocumentiTariEng elementoTari) {
    return LabelFdCUtil.checkIfNotNull(elementoTari.getDov())
        && LabelFdCUtil.checkIfNotNull(elementoTari.getPag())
        && elementoTari.getDov().compareTo(elementoTari.getPag()) == 0;
  }

  private boolean checkDovutoMaggiorePagato(DatiDocumentiTariEng elementoTari) {
    return LabelFdCUtil.checkIfNotNull(elementoTari.getDov())
        && LabelFdCUtil.checkIfNotNull(elementoTari.getPag())
        && elementoTari.getDov().compareTo(elementoTari.getPag()) > 0;
  }

  private boolean checkIdUltimoDocGeriPresente(DatiDocumentiTariEng elementoTari) {
    return LabelFdCUtil.checkIfNotNull(elementoTari.getIdUltimoDoc())
        && elementoTari.getIdUltimoDoc().compareTo(0L) != 0;
  }

  private boolean checkIdUltimoDocGeriPresenteUgualeIdDoc(DatiDocumentiTariEng elementoTari) {
    return checkIdUltimoDocGeriPresente(elementoTari)
        && LabelFdCUtil.checkIfNotNull(elementoTari.getIdDoc())
        && elementoTari.getIdUltimoDoc().compareTo(elementoTari.getIdDoc()) == 0;
  }

  @Override
  public void setInfoSuEccedenzeEPosizione(DatiDocumentiTariEng elementoTari) {

    setInfoSuEccedenze(elementoTari);

    setInfoPosizione(elementoTari);
  }

  @Override
  public DettaglioDocumentoEmesso getDettagliDocumentoTARI(String uri)
      throws BusinessException, ApiException {
    log.debug("CP getDettagliDocumentoTARI ");

    ServiceLocatorLivelloUnoTariEng instance = ServiceLocatorLivelloUnoTariEng.getInstance();

    try {
      return ServiceLocatorLivelloUnoTariEng.getInstance()
          .getApiTariEng()
          .dettaglioDocumentoTARI(uri);

    } catch (BusinessException e) {
      log.error("ServiziTariEngImpl -- getDettagliDocumentoTARI: errore API TARI ENG:");
      throw new BusinessException(ERRORE_API_TARI_ENG);
    } catch (ServiceUnavailableException e) {
      log.error(
          "ServiziTariEngImpl -- getDettagliDocumentoTARI: errore durante la chiamata delle API TARI ENG"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(500).tag(e.getMessage()).build(), ERRORE_API_TARI_ENG);
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "ServiziTariEngImpl -- getDettagliDocumentoTARI: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<DatiRimborso> getRimborsiOrdinati(
      IstanzaRimborsoGETResponse rimborsi, String codiceFiscale) {
    List<DatiRimborso> listaOrdinata = new ArrayList<DatiRimborso>();

    if (LabelFdCUtil.checkIfNotNull(rimborsi)
        && LabelFdCUtil.checkIfNotNull(rimborsi.getListaDatiRimborsi())
        && !LabelFdCUtil.checkEmptyList(rimborsi.getListaDatiRimborsi())) {

      //			Comparator<DatiRimborso> comparator = Comparator.comparing(
      //					(DatiRimborso elem) -> elem.getDatiRimborso().getDataIstanza(),
      //					Comparator.nullsFirst(Comparator.reverseOrder()));

      Comparator<DatiRimborso> comparator =
          Comparator.comparing(
              (DatiRimborso elem) -> elem.getDatiRimborso().getIdIstanza(),
              Comparator.nullsFirst(Comparator.reverseOrder()));

      listaOrdinata =
          rimborsi.getListaDatiRimborsi().stream().sorted(comparator).collect(Collectors.toList());
    }

    return listaOrdinata;
  }

  @Override
  public boolean checkRimborsoIntestarioRichiedibile(DatiRichiestaRimborsoTariEng datiRimborso) {
    log.debug("checkRimborsoIntestarioRichiedibile");

    boolean rimborsoRichiedibile = true;

    try {
      IstanzaRimborsoGETResponse rimborsi = getRimborsi(datiRimborso.getCodiceFiscaleRichiedente());

      if (LabelFdCUtil.checkIfNotNull(rimborsi)
          && LabelFdCUtil.checkIfNotNull(rimborsi.getListaDatiRimborsi())
          && !LabelFdCUtil.checkEmptyList(rimborsi.getListaDatiRimborsi())) {

        List<DatiRimborso> listaTuttiRimborsiDiQuellIdDeb = new ArrayList<>();

        listaTuttiRimborsiDiQuellIdDeb =
            rimborsi.getListaDatiRimborsi().stream()
                .filter(
                    elem ->
                        LabelFdCUtil.checkIfNotNull(elem.getDatiRimborso())
                            && LabelFdCUtil.checkIfNotNull(elem.getDatiRimborso().getIdDebitore())
                            && LabelFdCUtil.checkIfNotNull(datiRimborso.getIdDeb())
                            && elem.getDatiRimborso()
                                .getIdDebitore()
                                .equals(datiRimborso.getIdDeb().intValue()))
                .collect(Collectors.toList());

        log.debug(
            "CP lista rimborsi dopo filtro su id deb = " + listaTuttiRimborsiDiQuellIdDeb.size());

        if (!LabelFdCUtil.checkEmptyList(listaTuttiRimborsiDiQuellIdDeb)) {

          log.debug("CP listaRimborsi dopo filtro piena " + datiRimborso.getIdDeb());

          for (DatiRimborso elemRimborso : listaTuttiRimborsiDiQuellIdDeb) {
            if (LabelFdCUtil.checkIfNotNull(elemRimborso)) {
              if (LabelFdCUtil.checkIfNotNull(elemRimborso.getDatiRimborso())) {

                if (checkIstanzaApertaNuovo(datiRimborso.getDatiDocumentiTariEng(), elemRimborso)) {

                  log.debug("CP if su stato e date");

                  if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTari())
                      && Double.compare(datiRimborso.getEccTari(), 0.0) > 0
                      && PageUtil.isStringValid(elemRimborso.getDatiRimborso().getTipoRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getTipoRimborso()
                          .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO.value())
                      && PageUtil.isStringValid(
                          elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getEnteBeneficiarioRimborso()
                          .equalsIgnoreCase(EnteBeneficiarioRimborsoEnum.ENTE.value())) {

                    datiRimborso.setEccTariRichiedibile(false);
                  }

                  if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTefa())
                      && Double.compare(datiRimborso.getEccTefa(), 0.0) > 0
                      && PageUtil.isStringValid(elemRimborso.getDatiRimborso().getTipoRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getTipoRimborso()
                          .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZADARESIDUONEGATIVO.value())
                      && PageUtil.isStringValid(
                          elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getEnteBeneficiarioRimborso()
                          .equalsIgnoreCase(EnteBeneficiarioRimborsoEnum.PROVINCIA.value())) {
                    datiRimborso.setEccTefaRichiedibile(false);
                  }

                  if (LabelFdCUtil.checkIfNotNull(datiRimborso.getEccTariR())
                      && Double.compare(datiRimborso.getEccTariR(), 0.0) > 0
                      && PageUtil.isStringValid(elemRimborso.getDatiRimborso().getTipoRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getTipoRimborso()
                          .equalsIgnoreCase(TipoRimborsoEnum.ECCEDENZAREALE.value())
                      && PageUtil.isStringValid(
                          elemRimborso.getDatiRimborso().getEnteBeneficiarioRimborso())
                      && elemRimborso
                          .getDatiRimborso()
                          .getEnteBeneficiarioRimborso()
                          .equalsIgnoreCase(EnteBeneficiarioRimborsoEnum.ENTE.value())) {
                    datiRimborso.setEccTariRealeRichiedibile(false);
                  }

                } else {
                  log.debug("CP non entro in if su date e stato");
                }

                if (!datiRimborso.isEccTariRichiedibile()
                    && !datiRimborso.isEccTefaRichiedibile()
                    && !datiRimborso.isEccTariRealeRichiedibile()) {
                  rimborsoRichiedibile = false;
                } else {
                  rimborsoRichiedibile = true;
                }
              }
            }
          }

        } else {
          log.debug("CP listaRimborsi dopo filtro vuota");
        }

      } else {
        log.debug("CP listaRimborsi vuota");

        rimborsoRichiedibile = true;
      }

    } catch (BusinessException | ApiException e) {
      log.error("Errore checkRimborsoIntestarioRichiedibile tari eng: " + e.getMessage(), e);
    }

    return rimborsoRichiedibile;
  }

  @Override
  public String decodificaStatoIstanzaDiRimborso(String statoEng) {
    String statoDecodificato = "";

    if (PageUtil.isStringValid(statoEng)) {

      if (statoEng.equalsIgnoreCase(StatoEnum.INSERITO.value())) {
        statoDecodificato = "PRESENTATA";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.INELABORAZIONE.value())) {
        statoDecodificato = "IN ELABORAZIONE";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.VALIDATO.value())) {
        statoDecodificato = "IN ELABORAZIONE";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.KOGERI.value())) {
        statoDecodificato = "IN ELABORAZIONE";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.TRASMESSOSIBAK.value())) {
        statoDecodificato = "IN ELABORAZIONE";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.KOSIBAK.value())) {
        statoDecodificato = "IN ELABORAZIONE";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.LIQUIDATO.value())) {
        statoDecodificato = "LIQUIDATO";
      } else if (statoEng.equalsIgnoreCase(StatoEnum.ANNULLATO.value())) {
        statoDecodificato = "ANNULLATA";
      }
    }

    return statoDecodificato;
  }

  @Override
  public boolean messaggioAccontoPagatoParzialmenteVisibile(DatiDocumentiTariEng documento) {
    return !checkIncassatoUgualePagato(documento)
        && checkTipoDocAcconto(documento)
        && checkAnnoEmissioneDopo2021(documento)
        && !checkIdUltimoDocGeriPresenteUgualeIdDoc(documento);
  }

  private boolean checkIncassatoUgualePagato(DatiDocumentiTariEng elementoTari) {
    return LabelFdCUtil.checkIfNotNull(elementoTari.getImporto())
        && LabelFdCUtil.checkIfNotNull(elementoTari.getIncassato())
        && elementoTari.getImporto().compareTo(elementoTari.getIncassato()) == 0;
  }

  private boolean checkTipoDocAcconto(DatiDocumentiTariEng elementoTari) {
    return PageUtil.isStringValid(elementoTari.getTipoDoc())
        && elementoTari.getTipoDoc().contains("ACCONTO");
  }

  private boolean checkAnnoEmissioneDopo2021(DatiDocumentiTariEng elementoTari) {
    boolean annoEmissioneDopo2021 = false;

    if (PageUtil.isStringValid(elementoTari.getAnnoEmissione())) {
      Long annoEmissione = Long.parseLong(elementoTari.getAnnoEmissione());
      Long anno2021 = 2021L;
      if (annoEmissione.compareTo(anno2021) > 0) {
        annoEmissioneDopo2021 = true;
      }
    }

    return annoEmissioneDopo2021;
  }

  @Override
  public boolean dataScadenzaRataConguaglioVisible(DatiDocumentiTariEng documento) {
    boolean scadenzaRataVisibile = true;

    if (PageUtil.isStringValid(documento.getTipoDoc())
        && documento.getTipoDoc().contains("CONGUAGLIO")
        && PageUtil.isStringValid(documento.getAnnoEmissione())
        && documento.getAnnoEmissione().equalsIgnoreCase("2022")) {
      scadenzaRataVisibile = false;
    }

    return scadenzaRataVisibile;
  }
}
