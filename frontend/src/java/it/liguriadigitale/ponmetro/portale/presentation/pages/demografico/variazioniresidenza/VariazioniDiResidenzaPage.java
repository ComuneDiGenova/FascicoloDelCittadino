package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.panel.VariazioniDiResidenzaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.GloboRicercaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetElencoPraticheResponseGenericResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Pratica;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class VariazioniDiResidenzaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  public VariazioniDiResidenzaPage(PageParameters parameters) {
    super(parameters);

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    log.debug("nome pagina: " + GloboRicercaPage.class.getSimpleName());
    addOrReplace(breadcrumbPanel);

    List<Pratica> listaIterPratiche = popolaListaIterPratiche();
    VariazioniDiResidenzaPanel variazioniResidenzaPanel =
        new VariazioniDiResidenzaPanel("variazioniResidenzaPanel");
    variazioniResidenzaPanel.fillDati(listaIterPratiche);
    addOrReplace(variazioniResidenzaPanel);
  }

  private List<Pratica> popolaListaIterPratiche() {
    try {
      GetElencoPraticheResponseGenericResponse response =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getElencoPratiche(getUtente().getCodiceFiscaleOperatore());
      List<Pratica> listaPratiche = new ArrayList<Pratica>();
      List<Pratica> listaPraticheOrdinatePerData = new ArrayList<Pratica>();

      if (LabelFdCUtil.checkIfNotNull(response.getResult())
          && LabelFdCUtil.checkIfNotNull(response.getResult().getPratiche())) {
        listaPratiche = response.getResult().getPratiche();

        listaPraticheOrdinatePerData =
            listaPratiche.stream()
                .sorted(Comparator.comparing(Pratica::getDataInizio).reversed())
                .collect(Collectors.toList());
      }

      return listaPraticheOrdinatePerData;

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("variazioni di residenza"));
    }
  }
}
