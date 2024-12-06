package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.panel.QuadroTributarioTariPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.panel.RimborsiTariEngPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tarieng.model.QuadroTributario;
import it.liguriadigitale.ponmetro.tarieng.model.Tributi;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoGETResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class QuadroTributarioTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 5057225363650124678L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public QuadroTributarioTariPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<Legenda> listaLegenda = ServiceLocator.getInstance().getServiziTariEng().getListaLegenda();
    LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
    legendaPanel.setVisible(false);
    addOrReplace(legendaPanel);

    Map<Long, QuadroTributario> mappaTari = popolaTari();

    // TODO non buttare
    // Map<Long, List<DatiDocumentiTariEng>> mappaDettaglioTari =
    // popolaDettagliTari(mappaTari);
    // tabsDettagliTari(mappaDettaglioTari);

    tabsDettagliTari(mappaTari);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
    List<CfgTCatFunzLink> listaPerId = new ArrayList<>();

    String simpleName = this.getClass().getSimpleName();
    log.debug("simplename = " + simpleName);
    String idFunz = getIdFunzPerNomeClasse(simpleName);

    try {
      if (!idFunz.isEmpty()) {
        List<CfgTCatFunzLink> lista =
            ServiceLocatorLivelloUno.getInstance()
                .getApiHomePage()
                .getLinkEsterniPerIdFunz(String.valueOf(idFunz));
        if (!lista.isEmpty()) {
          for (CfgTCatFunzLink link : lista) {
            if (link.getFlagAbilitazione()) {
              listaPerId.add(link);
            }
          }
        }
      }
    } catch (BusinessException e) {
      log.error("QuadroTributarioTariPage ERROR = " + e);
    }

    if (listaPerId == null || listaPerId.isEmpty()) {
      CfgTCatFunzLink pojo = new CfgTCatFunzLink();
      pojo.setDescrizioniTooltip("errato");
      listaPerId.add(pojo);
    }

    log.debug("listaPerId =\n" + listaPerId);
    ListView<CfgTCatFunzLink> listviewTutorial =
        new ListView<CfgTCatFunzLink>("listaTutorialTari", listaPerId) {

          private static final long serialVersionUID = -3917872072845773346L;

          @Override
          protected void populateItem(ListItem<CfgTCatFunzLink> item) {
            item.setRenderBodyOnly(true);
            CfgTCatFunzLink funzione = item.getModelObject();
            if (funzione.getDescrizioniTooltip() != null
                && funzione.getDescrizioniTooltip().equalsIgnoreCase("errato")) {

              LinkDinamicoFunzioneLinkEsterni<Object> tutorialTariStd =
                  new LinkDinamicoFunzioneLinkEsterni<Object>(
                      "tutorialTari",
                      LinkDinamicoFunzioneDataBuilder.getInstance()
                          .setWicketLabelKeyText("QuadroTributarioTariPage.tari2022")
                          .setWicketClassName("VideoTutorialLaMiaTari2022Page")
                          .build(),
                      null,
                      QuadroTributarioTariPage.this,
                      "icon-video-tutorial",
                      "ioContribuente",
                      true);
              item.add(tutorialTariStd);

            } else {

              LinkGloboMetadata linkEsternoRipopolato =
                  LinkGloboMetadataBuilder.getInstance().build(funzione);
              log.debug(
                  "linkEsternoRipopolato"
                      + "\ngetWicketClassName = "
                      + linkEsternoRipopolato.getWicketClassName()
                      + "\ngetCssIcona = "
                      + linkEsternoRipopolato.getCssIcona()
                      + "\ngetWicketLabelKeyText = "
                      + linkEsternoRipopolato.getWicketLabelKeyText()
                      + "\ngetListaLinkEsterni = "
                      + linkEsternoRipopolato.getListaLinkEsterni()
                      + "\ngetPageParameters = "
                      + linkEsternoRipopolato.getPageParameters()
                      + "\ngetParametro = "
                      + linkEsternoRipopolato.getParametro()
                      + "\nisLinkEsterno = "
                      + linkEsternoRipopolato.isLinkEsterno()
                      + "\nisTargetInANewWindow = "
                      + linkEsternoRipopolato.isTargetInANewWindow());
              LinkDinamicoFunzioneLinkEsterni<Object> link =
                  new LinkDinamicoFunzioneLinkEsterni<Object>(
                      "tutorialTari",
                      linkEsternoRipopolato,
                      null,
                      QuadroTributarioTariPage.this,
                      linkEsternoRipopolato.getCssIcona(),
                      "ioContribuente",
                      true,
                      true,
                      true);

              item.add(link);
            }
          }
        };
    addOrReplace(listviewTutorial);
    listviewTutorial.setRenderBodyOnly(true);
  }

  private String getIdFunzPerNomeClasse(String nomeClasse) {
    log.debug("QuadroTributariotariPage > getIdFunzPerNomeClasse");
    String idFunz = "all";
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    if (pagineAbilitate.size() != 0) {
      log.debug("QuadroTributariotariPage > getIdFunzPerNomeClasse > lista =\n" + pagineAbilitate);
      for (FunzioniDisponibili funzione : pagineAbilitate) {
        if (funzione.getClassePaginaStd() != null
            && funzione.getFlagAbilitazioneFunz() != null
            && funzione.getClassePaginaStd().equalsIgnoreCase(nomeClasse)
            && funzione.getFlagAbilitazioneFunz()) {
          idFunz = funzione.getIdFunz().toString();
          log.debug("idfunz in getIdFunzPerNomeClasse = " + idFunz);
        }
      }
    }
    return idFunz;
  }

  private Map<Long, QuadroTributario> popolaTari() {

    Map<Long, QuadroTributario> mappa = new HashMap<Long, QuadroTributario>();

    try {
      Tributi tari =
          ServiceLocator.getInstance()
              .getServiziTariEng()
              .getQuadroTributarioTARI(getUtente().getCodiceFiscaleOperatore());

      if (LabelFdCUtil.checkIfNotNull(tari)) {
        mappa = ServiceLocator.getInstance().getServiziTariEng().getMappaAnniTributiTari(tari);
      }
    } catch (BusinessException | ApiException e) {
      log.error("Errore quadro tributario tari eng: " + e.getMessage(), e);
    }
    return mappa;
  }

  private void tabsDettagliTari(Map<Long, QuadroTributario> mappa) {
    List<ITab> listaTabsTari = new ArrayList<>();

    List<Long> listKeys = new ArrayList<>(mappa.keySet());

    for (Long anno : listKeys) {
      QuadroTributario quadroTributario = mappa.get(anno);
      AbstractTab tab =
          new AbstractTab(new Model<String>(anno.toString())) {

            private static final long serialVersionUID = -7479712868662323985L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {

              QuadroTributarioTariPanel obj =
                  new QuadroTributarioTariPanel(panelId, quadroTributario);
              return obj;
            }
          };
      listaTabsTari.add(tab);
    }

    AbstractTab tabRimborsi =
        new AbstractTab(new Model<String>("Rimborsi")) {

          private static final long serialVersionUID = 6475377076494988032L;

          @Override
          public WebMarkupContainer getPanel(String panelId) {

            List<DatiRimborso> lista = getRimborsiOrdinati();
            return new RimborsiTariEngPanel(panelId, lista);
          }
        };

    listaTabsTari.add(tabRimborsi);

    FascicoloTabPanel<ITab> fascicoloTabPanel =
        new FascicoloTabPanel<ITab>("tabsPanel", listaTabsTari);
    fascicoloTabPanel.setOutputMarkupId(true);
    addOrReplace(fascicoloTabPanel);
  }

  private IstanzaRimborsoGETResponse popolaRimborsi(String codiceFiscale) {
    try {
      return ServiceLocator.getInstance().getServiziTariEng().getRimborsi(codiceFiscale);
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("la Mia TARI"));
    }
  }

  private List<DatiRimborso> getRimborsiOrdinati() {
    return ServiceLocator.getInstance()
        .getServiziTariEng()
        .getRimborsiOrdinati(
            popolaRimborsi(getUtente().getCodiceFiscaleOperatore()),
            getUtente().getCodiceFiscaleOperatore());
  }
}
