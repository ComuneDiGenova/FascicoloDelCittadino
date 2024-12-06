package it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.iscrizioni.albi.apiclient.IscrizioniAlbiElettoraliApi;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DatiAccount;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviate;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviateRecordsInner;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.RisultatoTornata;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.Tornata;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.Tornate;
import it.liguriadigitale.ponmetro.portale.business.accenture.iscrizionealbi.service.ServiziIscrizioniAlbiService;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoAccenture;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class ServiziIscrizioniAlbiImpl implements ServiziIscrizioniAlbiService {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_ISCRIZIONI_ALBI =
      "Errore di connessione alle API Domande Iscrizioni Albi";

  @Override
  public DatiAccount getRecuperoUtenteByCodiceFiscale(String codiceFiscale)
      throws BusinessException, IOException, ApiException {

    log.debug("ServiziIscrizioniAlbiImpl getRecuperoUtenteByCodiceFiscale = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    DatiAccount response = null;

    try {
      response = instance.getApiIscrizioneAlbo().datiAccountByCF(codiceFiscale);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByCodiceFiscale: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_ISCRIZIONI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByCodiceFiscale: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByCodiceFiscale: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta trasporto alunni disabili"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DatiAccount getRecuperoUtenteByIdAccenture(String idAccenture)
      throws BusinessException, IOException, ApiException {

    log.debug("ServiziIscrizioniAlbiImpl getRecuperoUtenteByIdAccenture = " + idAccenture);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    DatiAccount response = null;

    try {
      response = instance.getApiIscrizioneAlbo().datiAccountById(idAccenture);

      return response;
    } catch (BusinessException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_ISCRIZIONI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getRecuperoUtenteByIdAccenture: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richiesta trasporto alunni disabili"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public DomandeInviate getListaDomandeIscrizioniAlbi(
      String codiceFiscale, TipologiaRichiestaEnum tipo)
      throws BusinessException, IOException, ApiException {
    log.debug("ServiziIscrizioniAlbiImpl getListaDomandeIscrizioniAlbi = " + codiceFiscale);

    ServiceLocatorLivelloUnoAccenture instance = ServiceLocatorLivelloUnoAccenture.getInstance();

    try {

      DatiAccount utenteAccentureByCf = getRecuperoUtenteByCodiceFiscale(codiceFiscale);

      if (LabelFdCUtil.checkIfNull(utenteAccentureByCf)) {
        return null;
      }

      String idUtenteAccentureByCf = utenteAccentureByCf.getId();

      log.debug("idUtenteAccentureByCf = " + idUtenteAccentureByCf);

      DatiAccount utemteById = getRecuperoUtenteByIdAccenture(idUtenteAccentureByCf);
      log.debug("utemteById = " + utemteById);

      DomandeInviate domande =
          instance.getApiIscrizioneAlbo().richiesteInviate(idUtenteAccentureByCf);

      if (domande != null && domande.getRecords() != null && !domande.getRecords().isEmpty()) {
        log.debug("lista Iscrizioni Albi = " + domande);

        List<DomandeInviateRecordsInner> listRecords =
            domande.getRecords().stream()
                .filter(
                    elem ->
                        elem != null
                            && elem.getTipoProcedimentoC() != null
                            && (isIscrizione(tipo, elem)
                                || isCancellazione(tipo, elem)
                                || isRinnovo(tipo, elem)))
                .collect(Collectors.toList());
        log.debug("lista Iscrizioni Albi RISULTATO= " + listRecords);
        domande.setRecords(listRecords);
        return domande;
      } else {
        DomandeInviate di = new DomandeInviate();
        List<DomandeInviateRecordsInner> l = new ArrayList<DomandeInviateRecordsInner>();
        di.setRecords(l);
        return new DomandeInviate();
      }

    } catch (BusinessException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getListaDomandeIscrizioniAlbi: errore API ACENTURE:", e);
      throw new BusinessException(ERRORE_API_ISCRIZIONI_ALBI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getListaDomandeIscrizioniAlbi: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziIscrizioniAlbiImpl -- getListaDomandeIscrizioniAlbi: errore durante la chiamata delle API ACCENTURE ",
          e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Richieste iscrizioni albi"));
    }
  }

  private boolean isCancellazione(TipologiaRichiestaEnum tipo, DomandeInviateRecordsInner elem) {
    return tipo.equals(TipologiaRichiestaEnum.SCRUTATORI)
        && elem.getTipoProcedimentoC().equalsIgnoreCase("Disiscrizione Albo Scrutatore di seggio");
  }

  private boolean isIscrizione(TipologiaRichiestaEnum tipo, DomandeInviateRecordsInner elem) {
    return elem.getTipoProcedimentoC()
        .equalsIgnoreCase("Iscrizione Albo " + getAlboByTipo(tipo) + " di seggio");
  }

  private boolean isRinnovo(TipologiaRichiestaEnum tipo, DomandeInviateRecordsInner elem) {
    return elem.getTipoProcedimentoC()
        .equalsIgnoreCase("Rinnovo Disponibilità " + getAlboByTipo(tipo));
  }

  private String getAlboByTipo(TipologiaRichiestaEnum tipo) {
    String albo = "";
    if (tipo.equals(TipologiaRichiestaEnum.SCRUTATORI)) {
      albo = "Scrutatore";
    } else if (tipo.equals(TipologiaRichiestaEnum.PRESIDENTI)) {
      albo = "Presidente";
    }
    return albo;
  }

  @Override
  public boolean isPresentTornataByTypo(String tipo) {

    try {
      IscrizioniAlbiElettoraliApi iscrAlbi =
          ServiceLocatorLivelloUnoAccenture.getInstance().getApiIscrizioneAlbo();
      Tornate tornate = iscrAlbi.listaTipiDiTornate();
      if (tornate != null && tornate.getListviews() != null) {
        Tornata singola =
            tornate.getListviews().stream()
                .filter(t -> t.getDeveloperName().contains(tipo.toLowerCase()))
                .collect(Collectors.toList())
                .get(0);
        RisultatoTornata tornatePerTipo = iscrAlbi.tornateByIdTipoTornata(singola.getId());
        return tornatePerTipo != null && tornatePerTipo.getSize() > 0;
      }

    } catch (Exception ex) {
      log.error("ERRORE in ServiziIscrizioniAlbiImpl > isPresentTornataByTypo = " + ex);
    }
    return false;
  }
}
