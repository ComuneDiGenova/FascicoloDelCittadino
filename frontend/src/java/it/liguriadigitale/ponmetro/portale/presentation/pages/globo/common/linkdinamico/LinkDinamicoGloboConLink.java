package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.link.GloboLaddaLink;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LinkDinamicoGloboConLink<T> extends Panel {

  private static final long serialVersionUID = -8224186602289670660L;
  private static final Log log = LogFactory.getLog(LinkDinamicoGloboConLink.class);

  private static final String WICKET_ID_LINK = "link";
  private Boolean visibilita;
  private String sottoFascicolo = " ";
  private GloboLaddaLink linkImg;
  private static final String WICKET_ID_LINK_ESTERNO = "linkEsterno";

  public LinkDinamicoGloboConLink(
      String id, LinkGloboMetadata linkF, T t, Component component, Boolean visibilita) {
    super(id);
    this.visibilita = visibilita;
    if (linkF.getListaLinkEsterni() != null && !linkF.getListaLinkEsterni().isEmpty()) {
      log.debug("linkF.getListaLinkEsterni = " + linkF.getListaLinkEsterni());
    }
    log.debug(
        "\nLinkDinamicoGloboConLink \nid = "
            + id
            + "\nLinkGloboMetadata = "
            + linkF.getCssIcona()
            + " "
            + linkF.getParametro()
            + "\ncomponent id = "
            + component.getId()
            + "\nlinkF.getDescrizione = "
            + linkF.getWicketLabelKeyText());

    creaLink(linkF, component);
    creaLinkEsterni(linkF, component);
  }

  public LinkDinamicoGloboConLink(
      String id, LinkGloboMetadata linkF, T t, Component component, String cssIcona) {
    this(id, linkF, t, component, true);
  }

  public LinkDinamicoGloboConLink(String id, LinkGloboMetadata linkF, T t, Component component) {
    this(id, linkF, t, component, true);
  }

  public LinkDinamicoGloboConLink(String id, LinkGloboMetadata linkF, T t) {
    this(id, linkF, t, null, true);
  }

  public LinkDinamicoGloboConLink(String id, LinkGloboMetadata linkF) {
    this(id, linkF, null, null, true);
  }

  private void creaLinkEsterni(LinkGloboMetadata linkF, Component component) {

    log.debug(
        "lista link esterni="
            + linkF.getListaLinkEsterni()
            + "  id funzione = "
            + linkF.getWicketClassName());

    List<CfgTCatFunzLink> lista = linkF.getListaLinkEsterni();
    if (lista == null || lista.isEmpty()) {

      log.debug("lista vuota > crea empty panel");
      EmptyPanel panelVuoto = new EmptyPanel("listaLinkEsterni");
      panelVuoto.setVisible(false);
      panelVuoto.setRenderBodyOnly(true);
      add(panelVuoto);

    } else {

      ListView<CfgTCatFunzLink> listaLinkEsterni =
          new ListView<CfgTCatFunzLink>("listaLinkEsterni", lista) {

            private static final long serialVersionUID = -3917872072845773346L;

            @Override
            protected void populateItem(ListItem<CfgTCatFunzLink> item) {

              CfgTCatFunzLink linkEsterno = item.getModelObject();

              log.debug(
                  "crea oggetto con elemento esterno di tipo = "
                      + linkEsterno.getTipoLink()
                      + " con tooltip: "
                      + linkEsterno.getDescrizioniTooltip());

              LinkGloboMetadata linkEsternoRipopolato =
                  LinkGloboMetadataBuilder.getInstance().build(linkEsterno);
              LinkDinamicoFunzioneLinkEsterni<Object> link =
                  new LinkDinamicoFunzioneLinkEsterni<>(
                      WICKET_ID_LINK_ESTERNO,
                      linkEsternoRipopolato,
                      null,
                      LinkDinamicoGloboConLink.this,
                      linkEsternoRipopolato.getCssIcona(),
                      sottoFascicolo,
                      true,
                      getVisibilitaSingoloItem(linkEsterno),
                      false);

              item.add(link);
            }
          };

      add(listaLinkEsterni);
      listaLinkEsterni.setRenderBodyOnly(true);
    }
  }

  private boolean getVisibilitaSingoloItem(CfgTCatFunzLink linkEsterno) {
    if (linkEsterno.getFlagAbilitazione()) {

      log.debug(
          "\nL'icona ["
              + linkEsterno.getIconaCss()
              + "] di tipo ["
              + linkEsterno.getTipoLink()
              + "] con descrizione ["
              + linkEsterno.getDescrizioniTooltip()
              + "] ha il FlagAbilitazione a ["
              + linkEsterno.getFlagAbilitazione()
              + "]");

      if ((getUtente().isResidente() && linkEsterno.getFlagResidente())
          || (!getUtente().isResidente() && linkEsterno.getFlagNonresidente())) {
        log.debug(
            "\n["
                + getUtente().isResidente()
                + "] = Utente isResidente"
                + "\n["
                + linkEsterno.getFlagResidente()
                + "] = LinkEsterno isAbilitatoPerResidente"
                + "\n["
                + !getUtente().isResidente()
                + "] = Utente isNonResidente"
                + "\n["
                + linkEsterno.getFlagNonresidente()
                + "] = LinkEsterno isAbilitatoPerNonResidente");
        return true;
      } else {
        log.debug("Nessun match tra la residenzialita dell'utente e l'abilitazione del link");
        return false;
      }

    } else {
      log.debug("FlagAbilitazione = false");
      return false;
    }
  }

  private Utente getUtente() {
    LoginInSession loginSession = (LoginInSession) getSession();
    Utente utente =
        loginSession != null && loginSession.getUtente() != null ? loginSession.getUtente() : null;
    return utente;
  }

  @Override
  public boolean isVisible() {
    if (linkImg != null) {
      return visibilita && linkImg.isVisible();
    } else {
      return visibilita && super.isVisible();
    }
  }

  private void creaLink(LinkGloboMetadata linkMetadata, Component component) {
    log.debug(
        "Component: "
            + component.getClass().getCanonicalName()
            + "\nlinkMetadata: "
            + linkMetadata.getWicketClassName());

    LinkGloboMetadata linkEsterno = new LinkGloboMetadata();

    if (linkMetadata.getListaLinkEsterni() != null
        && !linkMetadata.getListaLinkEsterni().isEmpty()) {

      linkEsterno = creaOggettoLinkFunzioneEsterna(linkMetadata, linkEsterno);
    }

    if (linkEsterno.getWicketClassName() != null && !linkEsterno.getWicketClassName().isEmpty()) {
      log.debug(
          "LINK ESTERNO : "
              + "\ngetCssIcona "
              + linkEsterno.getCssIcona()
              + "\ngetWicketClassName "
              + linkEsterno.getWicketClassName()
              + "\ngetWicketLabelKeyText "
              + linkEsterno.getWicketLabelKeyText()
              + "\ngetWicketLabelKeyTitle "
              + linkEsterno.getWicketLabelKeyTitle()
              + "\ngetListaLinkEsterni "
              + linkEsterno.getListaLinkEsterni()
              + "\ngetPageParameters "
              + linkEsterno.getPageParameters()
              + "\ngetParametro "
              + linkEsterno.getParametro());
      @SuppressWarnings("rawtypes")
      LinkDinamicoFunzioneLinkEsterni sostitutoPerFunzioniLinkEsterni =
          new LinkDinamicoFunzioneLinkEsterni<>(
              WICKET_ID_LINK,
              linkEsterno,
              null,
              LinkDinamicoGloboConLink.this,
              linkEsterno.getCssIcona(),
              sottoFascicolo,
              true,
              true,
              true);

      add(sostitutoPerFunzioniLinkEsterni);

      List<CfgTCatFunzLink> listaSenzaLinkEsternoTipoFunzione = new ArrayList<>();

      for (CfgTCatFunzLink elemento : linkMetadata.getListaLinkEsterni()) {
        if (!elemento.getTipoLink().equalsIgnoreCase("funzione")) {
          listaSenzaLinkEsternoTipoFunzione.add(elemento);
        }
      }
      linkMetadata.setListaLinkEsterni(listaSenzaLinkEsternoTipoFunzione);

    } else {

      log.debug(
          "LinkDinamicoGloboConLink > creaLink =\nComponent: "
              + component.getClass().getCanonicalName());
      linkImg = new GloboLaddaLink(linkMetadata, component);
      aggiungiIconaDaCss(linkMetadata, component, linkImg);
      aggiungiLabel(linkMetadata, component);

      add(linkImg);
    }
  }

  private LinkGloboMetadata creaOggettoLinkFunzioneEsterna(
      LinkGloboMetadata linkMetadata, LinkGloboMetadata linkEsterno) {

    log.debug(
        "getListaLinkEsterni di " + linkMetadata.getWicketClassName() + " is not null || empty");
    for (CfgTCatFunzLink elementoEsterno : linkMetadata.getListaLinkEsterni()) {
      log.debug(
          "elemento esterno da analizzare "
              + elementoEsterno.getIdFunz()
              + " ha tipo link = "
              + elementoEsterno.getTipoLink());
      if (elementoEsterno.getTipoLink().equalsIgnoreCase("funzione")) {
        log.debug(
            "ELEMENTO ESTERNO:"
                + "\ngetDescrizioniTooltip "
                + elementoEsterno.getDescrizioniTooltip()
                + "\ngetIconaCss "
                + elementoEsterno.getIconaCss()
                + "\ngetTipoLink "
                + elementoEsterno.getTipoLink()
                + "\ngetUrl "
                + elementoEsterno.getUrl()
                + "\ngetFlagAbilitazione "
                + elementoEsterno.getFlagAbilitazione()
                + "\ngetFlagNonresidente "
                + elementoEsterno.getFlagNonresidente()
                + "\ngetFlagResidente "
                + elementoEsterno.getFlagResidente()
                + "\ngetIdFunz "
                + elementoEsterno.getIdFunz()
                + "\ngetIdLink "
                + elementoEsterno.getIdLink()
                + "\ngetIdStatoRec "
                + elementoEsterno.getIdStatoRec());

        linkEsterno.setCssIcona(elementoEsterno.getIconaCss());
        linkEsterno.setLinkEsterno(true);
        List<CfgTCatFunzLink> lista = new ArrayList<>();
        lista.add(
            elementoEsterno); // l'elemento potrebbe avere piu dati nella lista (video + funzione
        // es)
        linkEsterno.setListaLinkEsterni(lista);
        PageParameters parameters = new PageParameters();
        parameters
            .set("url", elementoEsterno.getUrl())
            .set("idLink", elementoEsterno.getIdLink().toString())
            .set("idFunz", elementoEsterno.getIdFunz().toString())
            .set("tipoLink", elementoEsterno.getTipoLink())
            .set("descrizioniTooltip", elementoEsterno.getDescrizioniTooltip())
            .set("iconaCss", elementoEsterno.getIconaCss())
            .set("flagAbilitazione", elementoEsterno.getFlagAbilitazione().toString())
            .set("idStatoRec", elementoEsterno.getIdStatoRec().toString())
            .set("flagResidente", elementoEsterno.getFlagResidente().toString())
            .set("flagNonresidente", elementoEsterno.getFlagNonresidente().toString());
        linkEsterno.setPageParameters(parameters);
        linkEsterno.setParametro(elementoEsterno);
        linkEsterno.setTargetInANewWindow(true);
        linkEsterno.setWicketClassName(linkMetadata.getWicketClassName());
        linkEsterno.setWicketLabelKeyText(linkMetadata.getWicketLabelKeyText());
        linkEsterno.setWicketLabelKeyTitle(linkMetadata.getWicketLabelKeyTitle());
      }
    }
    return linkEsterno;
  }

  private void aggiungiLabel(LinkGloboMetadata linkMetadata, Component component) {
    log.debug("linkmetadata getWicketClassName = " + linkMetadata.getWicketClassName());
    String sottoFascicoloLabel = getSottoFascicoloLabel(linkMetadata, component);
    linkImg.setLabel(Model.of(sottoFascicoloLabel));
  }

  private void aggiungiIconaDaCss(
      LinkGloboMetadata linkMetadata, Component component, GloboLaddaLink linkImg) {

    log.debug("LinkDInamicoGloboCoLink > aggiungiIconaDaCss ");

    String iconaCss = linkMetadata.getCssIcona();
    String spinnerColor = "#fcb342";
    IconType iconType = aggiungiIcona(iconaCss);
    if (StringUtils.isEmpty(iconaCss)) {
      AttributeModifier attributeModifier =
          new AttributeModifier("class", "link-icon btn-link ladda-button");
      linkImg.add(attributeModifier);
    } else {
      if (iconaCss.contains("blue-spid")) {
        spinnerColor = "#0066cb";
      } else if (iconaCss.contains("blue-sebina")) {
        spinnerColor = "#0e5aa8";
      } else if (iconaCss.contains("green")) {
        spinnerColor = "#008d8b";
      }
    }
    if (sottoFascicolo.equalsIgnoreCase(" ")) {
      linkImg.setIconType(iconType);
    }
    linkImg.setSpinnerColor(spinnerColor);
    linkImg.setOutputMarkupId(true);
  }

  private IconType aggiungiIcona(String iconaCss) {
    return new IconType(WICKET_ID_LINK) {

      private static final long serialVersionUID = 8535411269404222206L;

      @Override
      public String cssClassName() {
        return iconaCss;
      }
    };
  }

  private String getResourceMessage(String resourceId, Component component) {
    if (resourceId != null) {
      log.debug("REASOURCEID: " + resourceId);
      if (resourceId.contains("Panel.") || resourceId.contains("Page.")) {
        String resourceMessage =
            Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
        return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
      } else {
        return resourceId;
      }
    } else return " ";
  }

  private String getSottoFascicoloLabel(LinkGloboMetadata linkF, Component component) {
    String label = getResourceMessage(linkF.getWicketLabelKeyText(), component);
    if (StringUtils.isBlank(label)) {
      label = linkF.getWicketLabelKeyText();
    }
    return label;
  }
}
