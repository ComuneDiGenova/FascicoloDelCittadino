package it.liguriadigitale.ponmetro.portale.business.helpCzRm;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmSottoFascicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.segnalazioni.czrm.model.SegnalazioneCzrm;
import it.liguriadigitale.ponmetro.segnalazioni.czrm.model.SegnalazioneCzrm.StatoEnum;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class HelpCzRMImpl implements IHelpCzRMService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public List<CzrmSottoFascicoli> getSottofascicolo() throws ApiException, BusinessException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();

    try {
      return instance.getApiHelpCzRM().getCzRmSottoFascicoli();
    } catch (BusinessException e) {
      // TODO Auto-generated catch block
      log.error("HelpCzRMImpl -- getSottofascicolo: errore API helczrm:", e);
      throw new BusinessException("Errore recupero Sottofascicoli");
    } catch (WebApplicationException e) {
      log.error(
          "HelpCzRMImpl -- getSottofascicolo: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "HelpCzRMImpl -- getSottofascicolo: errore durante la chiamata delle API helczrm ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Serve Aiuto"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<CzrmServizi> getServizi() throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiHelpCzRM().getCzRmServizi();
    } catch (BusinessException e) {
      log.error("HelpCzRMImpl -- getServizi: errore API CZRM:", e);
      throw new BusinessException("Errore recupero Sottofascicoli");
    } catch (WebApplicationException e) {
      log.error("HelpCzRMImpl -- getServizi: errore WebApplicationException:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug("HelpCzRMImpl -- getServizi: errore durante la chiamata delle API czrm ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Serve aiuto"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<CzrmServizi> getServizi(String sottofascicolo) throws BusinessException {
    List<CzrmServizi> servizi;
    try {
      servizi = getServizi();

      log.debug("getServizi czrm = " + getServizi());

      List<CzrmServizi> listaFiltrata =
          servizi.stream()
              .filter(x -> x.getSottoFascicoloValue().equals(sottofascicolo))
              .collect(Collectors.toList());

      log.debug("listaFiltrata = " + listaFiltrata);

      return listaFiltrata;

    } catch (BusinessException | ApiException e) {
      log.error("HelpCzRMImpl -- getServizi: errore API czrm:", e);
      throw new BusinessException("Errore recupero Sottofascicoli");
    }
  }

  @Override
  public List<SegnalazioneCzrm> getListaSegnalazioniCzrm(String codiceFiscale)
      throws BusinessException, ApiException {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      return instance.getApiSegnalazioniCzRM().getSegnalazioniCzrm(codiceFiscale);
    } catch (BusinessException e) {
      log.error("HelpCzRMImpl -- getListaSegnalazioniCzrm: errore API helczrm:", e);
      throw new BusinessException("Errore recupero Sottofascicoli");
    } catch (WebApplicationException e) {
      log.error(
          "HelpCzRMImpl -- getListaSegnalazioniCzrm: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "HelpCzRMImpl -- getListaSegnalazioniCzrm: errore durante la chiamata delle API helczrm ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Serve Aiuto"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<SegnalazioneCzrm> getListaSegnalazioniCzrmFinto(String codiceFiscale)
      throws BusinessException, ApiException {
    List<SegnalazioneCzrm> lista = new ArrayList<SegnalazioneCzrm>();

    SegnalazioneCzrm c1 = new SegnalazioneCzrm();
    c1.setIdentificativo("123");
    c1.setCodiceFiscale(codiceFiscale);
    c1.setCognome("Rossi");
    c1.setNome("Mario");
    c1.setDataAggiornamento(LocalDate.now());
    c1.setDataInvio(LocalDate.of(2022, 12, 31));
    c1.setEmail("a.a@a.it");
    c1.setTelefono("3401234567");
    c1.setSottoFascicolo("Io Contribuente");
    c1.setServizio("La Mia TARI");
    c1.setOggetto("Non vedo la bolletta TARI");
    c1.setDescrizione("Non vedo la bolletta TARI dell'anno 2023");
    c1.setStato(StatoEnum.APERTO);

    SegnalazioneCzrm c2 = new SegnalazioneCzrm();
    c2.setIdentificativo("1234");
    c2.setCodiceFiscale(codiceFiscale);
    c2.setCognome("Rossi");
    c2.setNome("Mario");
    c2.setDataAggiornamento(LocalDate.now());
    c2.setDataInvio(LocalDate.of(2022, 12, 31));
    c2.setEmail("a.a@a.it");
    c2.setTelefono("3401234567");
    c2.setSottoFascicolo("Io Contribuente");
    c2.setServizio("La Mia TARI");
    c2.setOggetto("Non vedo la bolletta TARI");
    c2.setDescrizione("Non vedo la bolletta TARI dell'anno 2023");
    c2.setStato(StatoEnum.PRESO_IN_CARICO);

    lista.add(c1);
    lista.add(c2);
    lista.add(c1);
    lista.add(c2);
    lista.add(c1);

    return lista;
  }
}
