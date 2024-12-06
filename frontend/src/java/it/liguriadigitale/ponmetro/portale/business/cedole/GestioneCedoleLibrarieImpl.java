package it.liguriadigitale.ponmetro.portale.business.cedole;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoCedole;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AllegatoPDF;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Classe;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ClasseScuola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoCartolibrerie;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoClassi;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoScuole;
import it.liguriadigitale.ponmetro.scuola.cedole.model.RitiroCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Scuola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto.TipoSoggettoEnum;
import it.liguriadigitale.ponmetro.scuola.cedole.model.TipoCedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class GestioneCedoleLibrarieImpl implements GestioneCedoleLibrarieService {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public Cedola situazioneCorrenteCedola(UtenteServiziRistorazione minore, Integer anno)
      throws BusinessException {

    String codiceFiscale = minore.getCodiceFiscale();
    try {
      String annoScolastico = calcolaAnnoScolastico(anno);
      return ServiceLocatorLivelloUnoCedole.getInstance()
          .getApiCedoleLibrarie()
          .domandaGet(codiceFiscale, annoScolastico);
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata API Cedole: ", e);
      throw new BusinessException("Servizio Cedole Librarie non disponibile");
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- situazioneCorrenteCedola: errore durante la chiamata delle API Cedole ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    }
  }

  private String calcolaAnnoScolastico(Integer anno) {

    Integer annoProssimo = anno + 1;
    return anno.toString() + "-" + annoProssimo;
  }

  @Override
  public AllegatoPDF getCedolaPDF(Cedola cedola) throws BusinessException {
    try {
      return ServiceLocatorLivelloUnoCedole.getInstance()
          .getApiCedoleLibrarie()
          .prenotazioneCedolaGet(cedola.getCedolaCodice());
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata API AllegatoPDF: ", e);
      throw new BusinessException("PDF Cedola non disponibile");
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- getCedolaPDF: errore durante la chiamata delle API Cedole ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    }
  }

  @Override
  public void ritiro(RitiroCedola ritiro) throws BusinessException, ApiException {
    log.debug("effetuato ritiro: " + ritiro);
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      instance.getApiCedoleLibrarie().prenotazioneRitiroPut(ritiro);
    } catch (WebApplicationException e) {
      log.error("Errore API ritiro: ", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- ritiro: errore durante la chiamata delle API Cedole ", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<Cartolibreria> elencoCartoliberie() throws BusinessException, ApiException {
    List<Cartolibreria> lista = new ArrayList<>();
    ElencoCartolibrerie elenco =
        ServiceLocatorLivelloUnoCedole.getInstance()
            .getApiUtilitaCedoleLibrarie()
            .cartolibrerieGet();
    if (elenco != null) {
      lista =
          elenco.getCartolibrerie().stream()
              .sorted(Comparator.comparing(Cartolibreria::getDenominazioneCartolibreria))
              .collect(Collectors.toList());
    }
    return lista;
  }

  @Override
  public String verificaTipoCedola(UtenteServiziRistorazione iscritto)
      throws BusinessException, ApiException {
    ClasseScuola paramClasse = new ClasseScuola();
    String classe = iscritto.getClasse();
    String sezione = iscritto.getSezione();
    if (classe.isEmpty() || checkIfNull(classe)) {
      throw new BusinessException("Classe non valorizzata");
    }
    if (sezione.isEmpty() || checkIfNull(sezione)) {
      throw new BusinessException("Sezione non valorizzata");
    }
    paramClasse.setClasse(classe);
    paramClasse.setSezione(sezione);
    TipoCedola tipo;
    try {
      tipo =
          ServiceLocatorLivelloUnoCedole.getInstance()
              .getApiCedoleLibrarie()
              .tipoCedolaPerClassePost(paramClasse);
      if (tipo != null) {
        return tipo.getTipoCedola();
      } else {
        return "";
      }
    } catch (WebApplicationException e) {
      log.error("Errore verificaTipoCedola: ", e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- verificaTipoCedola: errore durante la chiamata delle API Cedole ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    }
  }

  @Override
  public Cedola presentaDomanda(DomandaCedola domanda, Utente utente)
      throws BusinessException, ApiException {

    SoggettoAdulto soggettoPrenotazione = new SoggettoAdulto();
    soggettoPrenotazione.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
    soggettoPrenotazione.setCognome(utente.getCognome());
    soggettoPrenotazione.setNome(utente.getNome());
    soggettoPrenotazione.setTipoSoggetto(TipoSoggettoEnum.GENITORE);
    soggettoPrenotazione.setEmail(domanda.getSoggettoPrenotazione().getEmail());
    soggettoPrenotazione.setTelefono(domanda.getSoggettoPrenotazione().getTelefono());

    domanda.setDataDomanda(LocalDate.now());
    domanda.setSoggettoPrenotazione(soggettoPrenotazione);
    try {
      Cedola cedola =
          ServiceLocatorLivelloUnoCedole.getInstance().getApiCedoleLibrarie().domandaPost(domanda);
      return cedola;
    } catch (BusinessException e) {
      log.error("Errore presentaDomanda: ", e);
      throw new BusinessException("Errore durante la chiamata al backend");
    } catch (ResponseProcessingException | WebApplicationException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- presentaDomanda : errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(
          Response.serverError().status(400).tag(e.getMessage()).build(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- presentaDomanda: errore durante la chiamata delle API Cedole ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    }
  }

  private static boolean checkIfNull(Object obj) {
    return !Optional.ofNullable(obj).isPresent();
  }

  @Override
  public List<Scuola> getScuole() {
    List<Scuola> lista = new ArrayList<>();
    try {
      ElencoScuole elenco =
          ServiceLocatorLivelloUnoCedole.getInstance().getApiUtilitaCedoleLibrarie().scuoleGet();
      if (elenco != null) {
        lista =
            elenco.getScuole().stream()
                .sorted(Comparator.comparing(Scuola::getDenominazioneScuola))
                .collect(Collectors.toList());
      }
    } catch (BusinessException e) {
      log.debug("Errore: ", e);
    }
    return lista;
  }

  @Override
  public List<Classe> getClassi(String idScuola) {
    List<Classe> lista = new ArrayList<>();
    try {
      ElencoClassi elenco =
          ServiceLocatorLivelloUnoCedole.getInstance()
              .getApiUtilitaCedoleLibrarie()
              .classiGet(idScuola);
      if (elenco != null) {
        lista =
            elenco.getClassi().stream()
                .sorted(Comparator.comparing(Classe::getClasse))
                .collect(Collectors.toList());
      }
    } catch (BusinessException e) {
      log.debug("Errore getClassi: ", e);
    } catch (RuntimeException e) {
      log.error(
          "GestioneCedoleLibrarieImpl -- getClassi: errore durante la chiamata delle API Cedole ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("cedole librarie"));
    }
    return lista;
  }

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumbPrivacy() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioGenitore", "io Genitore"));
    listaBreadcrumb.add(new BreadcrumbFdC("cedole", "Privacy Cedole Librarie"));

    return listaBreadcrumb;
  }
}
