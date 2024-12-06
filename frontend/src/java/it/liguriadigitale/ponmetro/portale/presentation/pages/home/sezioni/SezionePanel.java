package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni;

import it.liguriadigitale.framework.presentation.components.link.BaseLink;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class SezionePanel extends BasePanel {

  private static final long serialVersionUID = 3852523522896889707L;

  protected static final String WIDGET = "Widget";

  protected abstract void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop);

  public SezionePanel(String id) {
    super(id);
    setRenderBodyOnly(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DatiVisualizzazioneSezioneWidget> listaWidget =
        (List<DatiVisualizzazioneSezioneWidget>) dati;
    // for (DatiVisualizzazioneSezioneWidget prova : listaWidget) {
    // log.debug("fillDati: widget:" + prova);
    // }
    mostraWidgetTop(listaWidget);
    mostraWidgetBottom(listaWidget);
  }

  protected void mostraTitolo(
      Class<? extends Page> paginaRiepilogoDaSezione,
      Model<String> testoLabel,
      boolean isNecessarioMenu,
      String nomeSezione) {

    Link<Void> link = creaLinkSezione(paginaRiepilogoDaSezione, "linkTitoloCard", nomeSezione);
    Link<Void> linkVaiTuttiServizi =
        creaLinkSezione(paginaRiepilogoDaSezione, "linkTitoloCard2", nomeSezione);
    Label label = new Label("testoTitoloCard", testoLabel);
    link.add(label);
    linkVaiTuttiServizi.setVisible(isNecessarioMenu);
    add(link);
    add(linkVaiTuttiServizi);

    // per accesibilita
    Label vai = new Label("vai", getString("SezionePanel.vai"));
    AttributeAppender attributeAppenderTitle = new AttributeAppender("title", testoLabel);
    AttributeAppender attributeAppenderAriaLabel = new AttributeAppender("aria-label", testoLabel);
    vai.add(attributeAppenderTitle);
    vai.add(attributeAppenderAriaLabel);
    linkVaiTuttiServizi.addOrReplace(vai);
    label.add(attributeAppenderAriaLabel);
  }

  protected void mostraTitolo(
      Class<? extends Page> pageClass, Model<String> testoLabel, boolean isNecessarioMenu) {
    mostraTitolo(pageClass, testoLabel, isNecessarioMenu, "");
  }

  @SuppressWarnings("unchecked")
  public Link<Void> creaLinkSezione(
      Class<? extends Page> pageClass, String wicketID, String nomeSezione) {

    if (pageClass.getName().equalsIgnoreCase(MenuRiepilogoDinamicoPage.class.getName())) {
      log.debug("creo link per " + pageClass.getName());
      return new Link<Void>(wicketID) {

        private static final long serialVersionUID = -452032494217345467L;

        @Override
        public void onClick() {
          PageParameters parameters = new PageParameters();
          parameters.set(GloboPathParametersName.NOME_SEZIONE.name(), nomeSezione);
          log.debug("nomeSezione=" + nomeSezione);
          setResponsePage(new MenuRiepilogoDinamicoPage(parameters));
        }
      };
    } else {
      return new BaseLink<>(wicketID, (Class<Page>) pageClass);
    }
  }

  protected DatiSezione estraiSezioneCorrente(String denominazioneSezione) {
    Set<DatiSezione> sezioni = getUtente().getWidgetConfigurati().keySet();
    for (DatiSezione sezione : sezioni) {
      if (sezione.getDenominazioneSez().equalsIgnoreCase(denominazioneSezione)) {
        return sezione;
      }
    }
    return new DatiSezione();
  }

  protected List<DatiVisualizzazioneSezioneWidget> estraiWidgetSezione(DatiSezione sezione) {
    return getUtente().getWidgetConfigurati().get(sezione);
  }

  protected String getDenominazioneWidgetByPosizione(
      int posizione, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    if (listaWidget.size() > posizione) {
      DatiVisualizzazioneSezioneWidget widget = listaWidget.get(posizione);
      return widget.getWidget().getUriWidg();
    } else {
      return "";
    }
  }

  protected DatiVisualizzazioneSezioneWidget getWidgetByPosizione(
      int posizione, List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    if (listaWidget.size() > posizione) {
      DatiVisualizzazioneSezioneWidget widget = listaWidget.get(posizione);
      return widget;
    } else {
      return null;
    }
  }

  protected String getDenominazioneWidget(DatiVisualizzazioneSezioneWidget widget) {
    if (widget != null) {
      return widget.getWidget().getUriWidg();
    } else {
      return "";
    }
  }

  protected void mostraWidgetTop(List<DatiVisualizzazioneSezioneWidget> listaWidget) {

    DatiVisualizzazioneSezioneWidget widgetTop = getWidgetByPosizione(0, listaWidget);
    String denominazione = getDenominazioneWidget(widgetTop);
    log.debug("TOP denominazione:" + denominazione);
    addWidget(denominazione, POSIZIONE.TOP, widgetTop);
  }

  protected void mostraWidgetBottom(List<DatiVisualizzazioneSezioneWidget> listaWidget) {

    DatiVisualizzazioneSezioneWidget widgetBottom = getWidgetByPosizione(1, listaWidget);
    String denominazione = getDenominazioneWidget(widgetBottom);
    log.debug("BOTTOM denominazione:" + denominazione);
    addWidget(denominazione, POSIZIONE.BOTTOM, widgetBottom);
  }

  protected Component addWidgetDynamico(StringBuilder className, POSIZIONE posizione) {

    try {
      log.debug("addWidgetDynamico: INIZIO");
      // Class<?> c = Class.forName(className.toString());
      Class<?> c = PageClassFinder.findClassByNameWidget(className.toString());
      Constructor<?> cons = c.getConstructor(POSIZIONE.class);
      Component object = (Component) cons.newInstance(posizione);
      log.debug("addWidgetDynamico:" + object.getClass().getCanonicalName());
      return object;
    } catch (NoSuchMethodException
        | SecurityException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      log.debug("addWidgetDynamico:", e);
    }
    log.debug("addWidgetDynamico: ritorna EmptyPanel");
    return new EmptyPanel(posizione.getWicketId());
  }

  protected Component addComponenteWidget(String className, POSIZIONE posizione) {

    try {
      log.debug("addComponenteWidget: INIZIO");
      Class<?> c = PageClassFinder.findClassByNameWidget(className + WIDGET);
      Constructor<?> cons = c.getConstructor(POSIZIONE.class);
      Component object = (Component) cons.newInstance(posizione);
      log.debug("addComponenteWidget:" + object.getClass().getCanonicalName());
      return object;
    } catch (NoSuchMethodException
        | SecurityException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      log.debug("addComponenteWidget:", e);
    }
    log.debug("addComponenteWidget: ritorna EmptyPanel");
    return new EmptyPanel(posizione.getWicketId());
  }

  protected StringBuilder getWidgetClasseNameStringBuilder(String denominazione) {
    StringBuilder builder =
        new StringBuilder(this.getClass().getPackage().getName())
            .append("." + WIDGET.toLowerCase() + ".");
    builder.append(denominazione).append(WIDGET);
    return builder;
  }
}
