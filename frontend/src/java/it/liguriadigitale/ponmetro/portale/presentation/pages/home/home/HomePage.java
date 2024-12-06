package it.liguriadigitale.ponmetro.portale.presentation.pages.home.home;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzioneInEvidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoLinkInEvidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PanelClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.widget.util.WidgetUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class HomePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4022446466212362738L;
  private static final String SUFFIX = "Panel";
  private static final String WICKET_ID_PANEL = "miaSezione";

  public HomePage() {
    super();
    addContent();
  }

  @Override
  public void renderPage() {
    log.debug("RENDER PAGE");
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }

  private void addContent() {
    log.debug("addContent");
    Map<DatiSezione, List<DatiVisualizzazioneSezioneWidget>> widgetConfigurati =
        getUtente().getWidgetConfigurati();
    List<DatiSezione> listaSezioni = WidgetUtil.getListaSezioniOrdinate(widgetConfigurati);
    ListView<DatiSezione> listView =
        new ListView<DatiSezione>("lista", listaSezioni) {

          private static final long serialVersionUID = -1949303234023054323L;

          @Override
          protected void populateItem(ListItem<DatiSezione> item) {
            DatiSezione sezioneCorrente = item.getModelObject();
            String denominazioneSezione = sezioneCorrente.getDenominazioneSez();
            log.debug("denominazioneSezione:" + denominazioneSezione);
            if (!denominazioneSezione.isEmpty()
                && !denominazioneSezione.equalsIgnoreCase("content")) {
              if (Constants.LAZY_PANEL) {
                item.add(getLazyPanel(sezioneCorrente));
              } else {
                item.add(
                    getPanelSezioneDinamico(sezioneCorrente, WICKET_ID_PANEL)
                        .setRenderBodyOnly(false));
              }
            } else {
              item.add(new EmptyPanel(WICKET_ID_PANEL));
            }
          }
        };
    listView.setRenderBodyOnly(true);
    add(listView);

    log.debug("Inizio icona");
    LinkDinamicoFunzioneLinkEsterni<Object> tutorialBenvenuto =
        new LinkDinamicoFunzioneLinkEsterni<Object>(
            "tutorialBenvenuto",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("HomePage.tutorialBenvunaCard")
                .setWicketClassName("VideoTutorialBenvenutoHomePage")
                .build(),
            null,
            HomePage.this,
            "icon-video-tutorial",
            "",
            true);
    addOrReplace(tutorialBenvenuto);
    log.debug("fine icona");

    LinkDinamicoLaddaFunzione<Object> verificaStrada =
        new LinkDinamicoLaddaFunzione<Object>(
            "verificaStrada",
            new LinkDinamicoFunzioneData(
                "HomePage.verificaStrada",
                "VerificaPericolositaStradaPage",
                "HomePage.verificaStrada"),
            null,
            HomePage.this,
            "");
    addOrReplace(verificaStrada);

    creaBoxBenvenuto();
    creaFunzioniInEvidenza();
    creaFunzioniInRealizzazione();

    String testoDaDbPerAttenzione =
        ServiceLocator.getInstance().getServiziTariEng().getValoreDaDb("ATTENZIONE_HOME_MESSAGGIO");
    Label attenzioneHomeMessaggio = new Label("attenzioneHomeMessaggio", testoDaDbPerAttenzione);
    attenzioneHomeMessaggio.setOutputMarkupId(true);
    attenzioneHomeMessaggio.setOutputMarkupPlaceholderTag(true);
    attenzioneHomeMessaggio.setVisible(
        "SI"
            .equalsIgnoreCase(
                ServiceLocator.getInstance()
                    .getServiziTariEng()
                    .getValoreDaDb("ATTENZIONE_HOME_MESSAGGIO_VISIBILE")));
    addOrReplace(attenzioneHomeMessaggio);
  }

  @SuppressWarnings("unused")
  private Link<Void> generaLink(String id) {
    return new Link<Void>(id) {

      private static final long serialVersionUID = 3758915427292204454L;

      @Override
      public void onClick() {
        setResponsePage(new UtenteNonAutorizzatoPage());
      }
    };
  }

  private Component getPanelSezioneDinamico(DatiSezione sezioneCorrente, String id) {
    log.debug("UriSez:" + sezioneCorrente.getUriSez());
    Panel sezione = new SezioneDinamicaPanel(id, sezioneCorrente.getDenominazioneSez());
    return sezione;
  }

  @SuppressWarnings("rawtypes")
  private AjaxLazyLoadPanel getLazyPanel(DatiSezione sezioneCorrente) {
    return new AjaxLazyLoadPanel(WICKET_ID_PANEL) {

      private static final long serialVersionUID = 192877466303109020L;

      @Override
      public Component getLazyLoadComponent(String id) {
        Component panel = getPanelSezioneDinamico(sezioneCorrente, id);
        this.setVisible(panel.isVisible());
        return panel;
      }
    };
  }

  @SuppressWarnings("unused")
  private Panel getPanelSezione(DatiSezione sezioneCorrente, String id) {

    try {
      log.debug("UriSez:" + sezioneCorrente.getUriSez());
      Class<?> c = PanelClassFinder.findClassByNameSezioni(sezioneCorrente.getUriSez() + SUFFIX);
      Constructor<?> cons = c.getConstructor(String.class);
      Panel sezione = (Panel) cons.newInstance(id);

      return sezione;
    } catch (NoSuchMethodException
        | InvocationTargetException
        | SecurityException
        | InstantiationException
        | IllegalAccessException
        | IllegalArgumentException e) {
      log.debug("ERRORE getPanelSezione: ", e);
      return (new EmptyPanel(id));
    }
  }

  private void creaBoxBenvenuto() {
    String genere =
        CodiceFiscaleValidatorUtil.getSessoFromCf(getUtente().getCodiceFiscaleOperatore());
    StringBuilder builder = new StringBuilder();
    if (genere.equalsIgnoreCase("F")) {
      builder.append(getString("HomePage.benvenuta"));
    } else {
      builder.append(getString("HomePage.benvenuto"));
    }
    builder.append(" ").append(StringUtils.upperCase(getUtente().getNome()));
    Label benvenuto = new Label("benvenuto", builder.toString());
    addOrReplace(benvenuto);
  }

  public void creaFunzioniInEvidenza() {
    log.debug("CP creaFunzioniInEvidenza");

    List<FunzioneChiaveValore> lista =
        ServiceLocator.getInstance()
            .getServiziHomePage()
            .getListaFunzioniInEvidenza("IN_EVIDENZA_");

    ListView<FunzioneChiaveValore> listView =
        new ListView<FunzioneChiaveValore>("listaInEvidenza", lista) {

          private static final long serialVersionUID = -3707898763473375236L;

          @Override
          protected void populateItem(ListItem<FunzioneChiaveValore> item) {
            final FunzioneChiaveValore elem = item.getModelObject();
            item.addOrReplace(getValore("descrizione", elem));
          }
        };
    addOrReplace(listView);
  }

  private LaddaAjaxLink<Object> getValore(
      String wicketId, FunzioneChiaveValore funzioneChiaveValore) {

    String descrizioneFunz = "";
    String classePaginaStd = "";
    String iconaCss = "";
    if (LabelFdCUtil.checkIfNotNull(funzioneChiaveValore)
        && LabelFdCUtil.checkIfNotNull(funzioneChiaveValore.getFunzioniDisponibili())) {
      if (PageUtil.isStringValid(
          funzioneChiaveValore.getFunzioniDisponibili().getDescrizioneFunz())) {
        descrizioneFunz = funzioneChiaveValore.getFunzioniDisponibili().getDescrizioneFunz();
      }
      if (PageUtil.isStringValid(
          funzioneChiaveValore.getFunzioniDisponibili().getClassePaginaStd())) {
        classePaginaStd = funzioneChiaveValore.getFunzioniDisponibili().getClassePaginaStd();
      }
      if (PageUtil.isStringValid(funzioneChiaveValore.getFunzioniDisponibili().getIconaCss())) {
        iconaCss = funzioneChiaveValore.getFunzioniDisponibili().getIconaCss();
      }
    }

    LinkDinamicoLaddaFunzioneInEvidenza<Object> linkDinamico =
        new LinkDinamicoLaddaFunzioneInEvidenza<Object>(
            wicketId,
            new LinkDinamicoFunzioneData(descrizioneFunz, classePaginaStd, descrizioneFunz),
            null,
            HomePage.this,
            iconaCss,
            EnumTipoLinkInEvidenza.LADDAAJAXLINK,
            null);

    LaddaAjaxLink<Object> laddaAjaxLink = linkDinamico.getLaddaLinkImg();

    IconType iconType =
        new IconType(wicketId) {

          private static final long serialVersionUID = 1491171845930063248L;

          @Override
          public String cssClassName() {
            String iconaCss = "";
            if (LabelFdCUtil.checkIfNotNull(funzioneChiaveValore)
                && LabelFdCUtil.checkIfNotNull(funzioneChiaveValore.getFunzioniDisponibili())) {
              if (PageUtil.isStringValid(
                  funzioneChiaveValore.getFunzioniDisponibili().getIconaCss())) {
                iconaCss = funzioneChiaveValore.getFunzioniDisponibili().getIconaCss();
              }
            }
            return iconaCss;
          }
        };
    laddaAjaxLink.setIconType(iconType);
    laddaAjaxLink.setOutputMarkupId(true);
    laddaAjaxLink.setOutputMarkupPlaceholderTag(true);
    return laddaAjaxLink;
  }

  public void creaFunzioniInRealizzazione() {
    log.debug("CP creaFunzioniInRealizzazione");

    List<ChiaveValore> lista =
        ServiceLocator.getInstance()
            .getServiziHomePage()
            .getListaFunzioniInRealizzazione("IN_REALIZZAZIONE_");
    log.debug("mia_lista : " + lista);

    ListView<ChiaveValore> listView =
        new ListView<ChiaveValore>("listaInRealizzazione", lista) {

          private static final long serialVersionUID = 6582034268592365050L;

          @Override
          protected void populateItem(ListItem<ChiaveValore> item) {
            final ChiaveValore elem = item.getModelObject();
            log.debug("mio_elem: " + elem);
            Label valore = new Label("valore", elem.getValore());
            log.debug("mio_label: " + elem.getValore());
            valore.setVisible(
                LabelFdCUtil.checkIfNotNull(elem) && PageUtil.isStringValid(elem.getValore()));
            item.addOrReplace(valore);
          }
        };
    addOrReplace(listView);
  }
}
