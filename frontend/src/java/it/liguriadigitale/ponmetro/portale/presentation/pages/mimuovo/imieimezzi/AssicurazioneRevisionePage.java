package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.noprintable.BolloPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.AssicurazionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.RevisionePanel;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class AssicurazioneRevisionePage extends BolloPage {

  private static final long serialVersionUID = 6807351445331782317L;

  public AssicurazioneRevisionePage() {
    super();
  }

  @SuppressWarnings("rawtypes")
  public AssicurazioneRevisionePage(Veicolo veicolo, VerificaAssicurazioneVeicoli dati) {
    super();
    @SuppressWarnings({"unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    WebMarkupContainer emergenzaCovid = new WebMarkupContainer("emergenzaCovid");
    add(emergenzaCovid);

    // VerificaAssicurazioneVeicoli dati = getDatiAssicurazione(veicolo);

    add(
        new AjaxLazyLoadPanel("datiAssicurazionePanel") {

          private static final long serialVersionUID = 1L;

          @Override
          public Component getLazyLoadComponent(String id) {
            AssicurazionePanel assicurazionePanel = new AssicurazionePanel(id, veicolo);
            assicurazionePanel.setRenderBodyOnly(true);
            assicurazionePanel.setMarkupId("datiAssicurazionePanel");
            assicurazionePanel.fillDati(dati);
            return assicurazionePanel;
          }
        });

    add(
        new AjaxLazyLoadPanel("datiRevisionePanel") {

          private static final long serialVersionUID = 1L;

          @Override
          public Component getLazyLoadComponent(String id) {
            RevisionePanel revisionePanel = new RevisionePanel(id, veicolo);
            revisionePanel.setRenderBodyOnly(true);
            revisionePanel.setMarkupId("datiRevisionePanel");
            revisionePanel.fillDati(dati);
            return revisionePanel;
          }
        });
  }

  @SuppressWarnings("unused")
  private VerificaAssicurazioneVeicoli getDatiAssicurazione(Veicolo veicolo) {
    try {
      VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli =
          ServiceLocator.getInstance().getServiziMieiMezzi().getAssicurazione(veicolo);
      return verificaAssicurazioneVeicoli;
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
