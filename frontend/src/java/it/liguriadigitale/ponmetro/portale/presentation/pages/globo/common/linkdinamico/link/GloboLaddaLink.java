package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.link;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.request.component.IRequestablePage;

public class GloboLaddaLink extends LaddaAjaxLink<Void> {

  private static final long serialVersionUID = -7328593343413631180L;
  private static final Log log = LogFactory.getLog(GloboLaddaLink.class);

  /** COSTANTI * */
  private static final String WICKET_ID_LINK = "link";

  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_BLANK = "_blank";
  private static final String ATTRIBUTE_TITLE = "title";

  /*** PARAMETRI ***/
  private LinkGloboMetadata linkMetadata;

  private Component pannello;

  public GloboLaddaLink(LinkGloboMetadata linkMetadata, Component pannello) {
    super(WICKET_ID_LINK, Type.Link);
    log.debug(
        "\nlinkMetadata icona = "
            + linkMetadata.getCssIcona()
            + "\n wicketclassname ="
            + linkMetadata.getWicketClassName()
            + "\n parametro= "
            + linkMetadata.getParametro()
            + "\n labelkeytitle = "
            + linkMetadata.getWicketLabelKeyTitle()
            + "\nPannello id"
            + pannello.getId());
    this.linkMetadata = linkMetadata;
    this.pannello = pannello;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onClick(AjaxRequestTarget target) {

    log.debug(
        ":: LinkDinamicoGlobo > creaLink > onClick : cerco classe "
            + linkMetadata.getWicketClassName()
            + " .... ");
    Class<IRequestablePage> clazz =
        (Class<IRequestablePage>)
            PageClassFinder.findClassByNameFunction(linkMetadata.getWicketClassName());
    if (clazz.equals(Page404.class)) {
      setVisible(false);
      return;
    }
    if (linkMetadata.isLinkEsterno()) {
      try {
        log.debug("Parametro passato: " + linkMetadata.getParametro());
        Component object =
            (Component)
                clazz
                    .getConstructor(linkMetadata.getParametro().getClass())
                    .newInstance(linkMetadata.getParametro());
        setResponsePage((IRequestablePage) object);
      } catch (InstantiationException
          | IllegalAccessException
          | IllegalArgumentException
          | InvocationTargetException
          | NoSuchMethodException
          | SecurityException e) {
        log.error(
            "[GloboLaddaLink1] onClick error on reflection con parametro: " + e.getMessage(), e);
      }

    } else {
      log.debug(
          ":: GloboLaddaLink > creaLink > onClick : class "
              + linkMetadata.getWicketClassName()
              + " trovata ");
      try {
        setResponsePage(clazz, linkMetadata.getPageParameters());
      } catch (IllegalArgumentException | SecurityException e) {
        log.error("[GloboLaddaLink1] onClick error on reflection: " + e.getMessage(), e);
      }
    }
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
    boolean isLinkVisibile =
        (PageUtil.isNomePaginaInElencoFornito(linkMetadata.getWicketClassName(), pagineAbilitate));
    log.debug(
        "[GloboLaddaLink] link a pagina "
            + linkMetadata.getWicketClassName()
            + " isVisible: "
            + isLinkVisibile);
    setVisible(isLinkVisibile && super.isVisible());
  }

  @Override
  protected void onComponentTag(ComponentTag tagHtml) {
    super.onComponentTag(tagHtml);
    linkApreNuovaFinestraBrowser(tagHtml);
    creaTooltip(tagHtml);
  }

  private void creaTooltip(ComponentTag tagHtml) {
    String tooltip = "";
    try {
      tooltip = getResourceMessage(linkMetadata.getWicketLabelKeyTitle(), pannello);
    } catch (Exception e) {
      log.trace(
          "[GloboLaddaLink] La stringa non e' la key di un file di properties, linkF.getWicketLabelKeyTitle():"
              + e.getMessage());
      if (StringUtils.isBlank(tooltip)) {
        tooltip = getResourceMessage(linkMetadata.getWicketLabelKeyText(), pannello);
        if (StringUtils.isNotEmpty(linkMetadata.getWicketLabelKeyTitle())
            && !StringUtils.equals(
                linkMetadata.getWicketLabelKeyText(), linkMetadata.getWicketLabelKeyTitle())) {
          tooltip = tooltip + " " + linkMetadata.getWicketLabelKeyTitle();
        }
      }
    }
    tagHtml.put(ATTRIBUTE_TITLE, tooltip);
  }

  private void linkApreNuovaFinestraBrowser(ComponentTag tagHtml) {
    if (linkMetadata.isTargetInANewWindow()) {
      tagHtml.put(ATTRIBUTE_TARGET, ATTRIBUTE_TARGET_BLANK);
    }
  }

  private String getResourceMessage(String resourceId, Component component) {
    if (resourceId.contains("Panel.") || resourceId.contains("Page.")) {
      String resourceMessage =
          Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
      return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
    } else {
      return resourceId;
    }
  }

  @SuppressWarnings("unused")
  private Throwable findCauseUsingPlainJava(Throwable throwable) {
    Objects.requireNonNull(throwable);
    Throwable rootCause = throwable;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
      rootCause = rootCause.getCause();
    }
    return rootCause;
  }
}
