package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiCoupon;
import it.liguriadigitale.ponmetro.plastipremia.model.PlastiPunti;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia.panel.coupon.CouponPlasticaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia.panel.punti.PuntiPlasticaPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class PlastiPremiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1117453753569859862L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public PlastiPremiaPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi = popolaListaMessaggi();

    AlertBoxPanel messaggi =
        (AlertBoxPanel) new AlertBoxPanel("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    tabsPlasTiPremia();
  }

  @SuppressWarnings("unused")
  private List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("homepage/home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioSostenibile", "io Sostenibile"));
    listaBreadcrumb.add(new BreadcrumbFdC("plasTiPremia", "PlasTiPremia"));

    return listaBreadcrumb;
  }

  private PlastiPunti popolaPuntiPlatica() {
    try {
      return ServiceLocator.getInstance()
          .getServiziPlasTiPremia()
          .getPunti(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    }
  }

  private PlastiCoupon popolaCouponPlatica() {
    try {
      return ServiceLocator.getInstance()
          .getServiziPlasTiPremia()
          .getCoupon(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("PlasTiPremia"));
    }
  }

  private void tabsPlasTiPremia() {

    List<ITab> listaTabsPlasTiPunti = new ArrayList<>();

    AbstractTab tabPuntiPlastica =
        new AbstractTab(new Model<>(String.valueOf("PlasTiPunti"))) {

          private static final long serialVersionUID = -1796179699552532982L;

          @Override
          public WebMarkupContainer getPanel(String panelId) {
            Panel panelRisultato = null;

            panelRisultato = new PuntiPlasticaPanel(panelId, popolaPuntiPlatica());

            return panelRisultato;
          }
        };

    listaTabsPlasTiPunti.add(tabPuntiPlastica);

    AbstractTab tabCouponPlastica =
        new AbstractTab(new Model<>(String.valueOf("PlasTiCoupon"))) {

          private static final long serialVersionUID = 6855332553564420888L;

          @Override
          public WebMarkupContainer getPanel(String panelId) {
            Panel panelRisultato = null;

            panelRisultato = new CouponPlasticaPanel(panelId, popolaCouponPlatica());

            return panelRisultato;
          }
        };

    listaTabsPlasTiPunti.add(tabCouponPlastica);

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsPlasTiPunti);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);
  }

  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
    MessaggiInformativi messaggio = new MessaggiInformativi();
    messaggio.setMessaggio(getString("PlastiPremiaPage.messaggio"));
    messaggio.setType("info");
    listaMessaggi.add(messaggio);
    return listaMessaggi;
  }
}
