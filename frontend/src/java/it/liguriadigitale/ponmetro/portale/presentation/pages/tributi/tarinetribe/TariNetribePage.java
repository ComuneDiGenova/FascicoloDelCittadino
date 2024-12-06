package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants.TIPO_DEPLOY;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.panel.TariNetribePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.panel.RimborsiTariNetribePanel;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIResult;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class TariNetribePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 296476768808768175L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public TariNetribePage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    tabsAnniTari(LocalDate.now().getYear());

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private List<TARIResult> popolaSintesiTariAnno(Integer anno) {
    List<TARIResult> lista = new ArrayList<TARIResult>();

    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziTariNetribe()
              .getSintesiTariAnnoCorrente(getUtente().getCodiceFiscaleOperatore(), anno);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaSintesiTariAnno: " + e.getMessage());
    }

    return lista;
  }

  private void tabsAnniTari(Integer annoCorrente) {

    final Integer anniDaMostrare = 6;
    Integer ultimoAnnoDaMostrare = annoCorrente - anniDaMostrare;
    List<ITab> listaTabsAnniTari = new ArrayList<>();

    while (annoCorrente > ultimoAnnoDaMostrare) {

      Integer annoTari = annoCorrente;

      AbstractTab tab =
          new AbstractTab(new Model<>(String.valueOf(annoCorrente))) {

            private static final long serialVersionUID = -8412279930029720337L;

            @Override
            public Panel getPanel(String panelId) {

              List<TARIResult> listaTariAnno = popolaSintesiTariAnno(annoTari);

              return new TariNetribePanel(panelId, listaTariAnno);
            }
          };

      listaTabsAnniTari.add(tab);

      annoCorrente = annoCorrente - 1;
    }

    AbstractTab tabRimborsi =
        new AbstractTab(new Model<>(String.valueOf(getString("TariNetribePage.rimborsi")))) {

          private static final long serialVersionUID = -5868676327214629067L;

          @Override
          public WebMarkupContainer getPanel(String panelId) {

            List<TARIRimborsi> listaRimborsi = getListaRimborsi();
            return new RimborsiTariNetribePanel(panelId, listaRimborsi);
          }
        };

    listaTabsAnniTari.add(tabRimborsi);

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsAnniTari);
    fascicoloTabPanel.setOutputMarkupId(true);

    String btnAnnulitaDB =
        ServiceLocator.getInstance()
            .getServiziTariNetribe()
            .getValoreDaDb("TARI_NETRIBE_ANNUALITA");
    boolean btnAnnualitaVisibile = btnAnnulitaDB.equalsIgnoreCase("ENABLE") ? true : false;

    // per far fare i test a serena
    if (Constants.DEPLOY != TIPO_DEPLOY.ESERCIZIO) {
      btnAnnualitaVisibile = true;
    }
    //

    log.debug("CP btnAnnualitaVisibile = " + btnAnnualitaVisibile);

    fascicoloTabPanel.setVisible(btnAnnualitaVisibile);

    addOrReplace(fascicoloTabPanel);
  }

  private List<TARIRimborsi> getListaRimborsi() {
    List<TARIRimborsi> listaRimborsi = new ArrayList<TARIRimborsi>();
    try {
      listaRimborsi =
          ServiceLocator.getInstance()
              .getServiziTariRimborsiNetribe()
              .getRimborsiTARIAnnoCorrente(getUtente().getCodiceFiscaleOperatore());
    } catch (ApiException | BusinessException e) {
      log.error("Errore getRimborsiOrdinati: " + e.getMessage());
    }
    return listaRimborsi;
  }
}
