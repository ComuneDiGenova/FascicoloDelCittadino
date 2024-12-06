package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.IndividuiCollegati;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel.DettagliVariazioniPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.GloboRicercaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoIndividuiResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetViarioResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.IndividuoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Pratica;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class DettaglioVariazioniPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5576888133819386713L;

  public DettaglioVariazioniPage(Pratica pratica) {

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    log.debug("nome pagina: " + GloboRicercaPage.class.getSimpleName());
    addOrReplace(breadcrumbPanel);

    DettagliVariazioniPanel dettaglioPanel = new DettagliVariazioniPanel("dettaglioPanel");
    VariazioniResidenza variazioniResidenza = getVariazioniDiResidenza(pratica);
    dettaglioPanel.fillDati(variazioniResidenza);
    addOrReplace(dettaglioPanel);
  }

  private GetDatiGeneraliPraticaResponse getDatiGeneraliPratica(Integer idPratica) {
    try {
      GetDatiGeneraliPraticaResponse datiGenerali = new GetDatiGeneraliPraticaResponse();

      GetDatiGeneraliPraticaResponseGenericResponse result =
          ServiceLocator.getInstance().getServiziAnagrafici().getDatiGeneraliPratica(idPratica);
      if (LabelFdCUtil.checkIfNotNull(result)
          && PageUtil.isStringValid(result.getStatus())
          && !result.getStatus().equalsIgnoreCase("KO")) {
        datiGenerali = result.getResult();
      }
      return datiGenerali;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private List<IndividuoPratica> getIndividuiCollegati(Integer idPratica) {
    try {
      List<IndividuoPratica> listaIndividui = new ArrayList<IndividuoPratica>();
      GetElencoIndividuiResponseGenericResponse result =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getElencoIndividuiCollegati(idPratica);

      if (LabelFdCUtil.checkIfNotNull(result)
          && PageUtil.isStringValid(result.getStatus())
          && !result.getStatus().equalsIgnoreCase("KO")) {
        listaIndividui = result.getResult().getIndividui();
      }
      return listaIndividui;
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private VariazioniResidenza getVariazioniDiResidenza(Pratica pratica) {

    Integer idPratica = pratica.getId();

    VariazioniResidenza variazioniResidenza = new VariazioniResidenza();

    variazioniResidenza.setIdPratica(pratica.getId());

    List<IndividuiCollegati> listaIndividuiCollegati = new ArrayList<IndividuiCollegati>();
    List<IndividuoPratica> listaElencoIndividui = getIndividuiCollegati(idPratica);

    for (IndividuoPratica elem : listaElencoIndividui) {
      IndividuiCollegati individuo = new IndividuiCollegati();

      individuo.setIdDemografico(String.valueOf(elem.getCodiceIndividuo()));
      individuo.setNominativo(elem.getNominativo());
      individuo.setParentela(elem.getParentela());
      individuo.setDataNascita(elem.getDataNascita());
      individuo.setSelezionato(true);
      individuo.setEta(LocalDateUtil.calcolaEta(elem.getDataNascita()));
      individuo.setCf(elem.getCodiceFiscaleIndividuo());

      if (LabelFdCUtil.checkIfNotNull(elem.getCodiceFiscaleIndividuo())) {
        individuo.setGenere(
            CodiceFiscaleValidatorUtil.getSessoFromCf(elem.getCodiceFiscaleIndividuo()));
      }

      listaIndividuiCollegati.add(individuo);
    }

    variazioniResidenza.setListaIndividuiCollegati(listaIndividuiCollegati);

    GetDatiGeneraliPraticaResponse datiGenerali = getDatiGeneraliPratica(idPratica);
    variazioniResidenza.setDatiGenerali(datiGenerali);

    variazioniResidenza.setNomeCoabitante(datiGenerali.getNomeIs());
    variazioniResidenza.setCognomeCoabitante(datiGenerali.getCognomeIs());
    variazioniResidenza.setCodiceCoabitante(String.valueOf(datiGenerali.getCodiceIndividuoIs()));

    variazioniResidenza.setNomeProprietario(datiGenerali.getNomeProprietario());
    variazioniResidenza.setCognomeProprietario(datiGenerali.getCognomeProprietario());

    String nominativoRichiedente = "";
    if (PageUtil.isStringValid(getUtente().getNome())
        && PageUtil.isStringValid(getUtente().getCognome())) {
      nominativoRichiedente = getUtente().getCognome().concat(" ").concat(getUtente().getNome());
    }
    variazioniResidenza.setNominativoRichiedente(nominativoRichiedente);

    variazioniResidenza.setIdVia(datiGenerali.getIdVia());
    variazioniResidenza.setViario(getDescrizioneVia(datiGenerali.getIdVia()));

    if (LabelFdCUtil.checkIfNotNull(datiGenerali.getNumero())) {
      variazioniResidenza.setNumero(String.valueOf(datiGenerali.getNumero()));
    }

    if (LabelFdCUtil.checkIfNotNull(datiGenerali.getNumeroCivico())) {
      variazioniResidenza.setCivico(Integer.parseInt(datiGenerali.getNumeroCivico()));
    }

    variazioniResidenza.setDataDecorrenza(datiGenerali.getDataInizio());

    List<DocumentoPratica> listaDocumentiCaricati =
        VariazioniResidenzaUtil.getDocumentiCaricati(idPratica);
    variazioniResidenza.setListaDocumentiCaricati(listaDocumentiCaricati);

    variazioniResidenza.setIdTipoPratica(pratica.getIdTipoPratica());
    variazioniResidenza.setStato(pratica.getStato());

    variazioniResidenza.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());

    return variazioniResidenza;
  }

  private String getDescrizioneVia(Integer idVia) {
    String descrizione = null;
    try {
      GetViarioResponseGenericResponse response =
          ServiceLocator.getInstance().getServiziAnagrafici().getViario(idVia, null);
      if (LabelFdCUtil.checkIfNotNull(response)
          && LabelFdCUtil.checkIfNotNull(response.getResult())
          && LabelFdCUtil.checkIfNotNull(response.getResult().getViario())
          && !LabelFdCUtil.checkEmptyList(response.getResult().getViario())
          && response.getResult().getViario().size() == 1) {
        descrizione =
            response
                .getResult()
                .getViario()
                .get(0)
                .getTipoVia()
                .concat(" ")
                .concat(response.getResult().getViario().get(0).getDescrizioneVia());
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore viario " + e.getMessage());
    }
    return descrizione;
  }
}
