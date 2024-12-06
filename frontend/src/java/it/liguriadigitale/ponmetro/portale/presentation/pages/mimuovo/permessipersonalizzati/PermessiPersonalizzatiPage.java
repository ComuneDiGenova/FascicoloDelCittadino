package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.panel.ElencoPermessiPersonalizzatiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.RichiestaPermessiPersonalizzatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class PermessiPersonalizzatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7660309620639141558L;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PermessiPersonalizzatiPage() {
    super();

    addOrReplace(creaBtnNuovaRichiestaPermesso());

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<Legenda> listaLegenda =
        ServiceLocator.getInstance().getServiziPermessiPersonalizzati().getListaLegenda();

    LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
    legendaPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(popolaListaDomande())
            && !LabelFdCUtil.checkEmptyList(popolaListaDomande()));

    addOrReplace(legendaPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    ElencoPermessiPersonalizzatiPanel elenco =
        new ElencoPermessiPersonalizzatiPanel("elencoPermessiPersonalizzati");
    addOrReplace(elenco);
  }

  private List<DomandaResponse> popolaListaDomande() {

    List<DomandaResponse> lista = new ArrayList<>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziPermessiPersonalizzati()
              .getDomande(getUtente().getCodiceFiscaleOperatore());

      Collections.sort(lista, new DomandaResponseComparator());

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco permessi personalizzati"));
    }

    return lista;
  }

  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioMiMuovo", "io Mi Muovo"));
    listaBreadcrumb.add(new BreadcrumbFdC("permessiPersonalizzati", "Permessi personalizzati"));

    return listaBreadcrumb;
  }

  private LaddaAjaxLink<Object> creaBtnNuovaRichiestaPermesso() {
    LaddaAjaxLink<Object> btnNuovaRichiestaPermesso =
        new LaddaAjaxLink<Object>("btnNuovaRichiestaPermesso", Type.Primary) {

          private static final long serialVersionUID = 5354813348751268136L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            // target.add(RimborsoTariProprietarioPanel.this);

            setResponsePage(new RichiestaPermessiPersonalizzatiPage());
          }
        };

    btnNuovaRichiestaPermesso.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "PermessiPersonalizzatiPage.btnNuovaRichiestaPermesso",
                    PermessiPersonalizzatiPage.this)));

    IconType iconType =
        new IconType("btnNuovaRichiestaPermesso") {

          private static final long serialVersionUID = 8404184190966468972L;

          @Override
          public String cssClassName() {
            return "icon-accessibilita";
          }
        };
    btnNuovaRichiestaPermesso.setIconType(iconType);

    return btnNuovaRichiestaPermesso;
  }

  private List<MessaggiInformativi> popolaListaMessaggi() {
    try {
      return ServiceLocator.getInstance().getServiziPermessiPersonalizzati().popolaListaMessaggi();
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("permessi personalizzati"));
    }
  }
}
