package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.link.GloboLaddaLink;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

public class LinkDinamicoGlobo<T> extends Panel {

  private static final long serialVersionUID = -8224186602289670660L;
  private static final Log log = LogFactory.getLog(LinkDinamicoGlobo.class);

  private static final String WICKET_ID_LINK = "link";
  private GloboLaddaLink linkImg;
  private Boolean visibilita;

  public LinkDinamicoGlobo(
      String id, LinkGloboMetadata linkF, T t, Component component, Boolean visibilita) {
    super(id);
    creaLink(linkF, t, component);
    this.visibilita = visibilita;
  }

  public LinkDinamicoGlobo(
      String id, LinkGloboMetadata linkF, T t, Component component, String cssIcona) {
    this(id, linkF, t, component, true);
  }

  public LinkDinamicoGlobo(String id, LinkGloboMetadata linkF, T t, Component component) {
    this(id, linkF, t, component, true);
  }

  public LinkDinamicoGlobo(String id, LinkGloboMetadata linkF, T t) {
    this(id, linkF, t, null, true);
  }

  public LinkDinamicoGlobo(String id, LinkGloboMetadata linkF) {
    this(id, linkF, null, null, true);
  }

  @Override
  public boolean isVisible() {
    if (linkImg != null) {
      return visibilita && linkImg.isVisible();
    } else {
      return visibilita && super.isVisible();
    }
  }

  private void creaLink(LinkGloboMetadata linkMetadata, T t, Component component) {
    log.debug(
        "LinkDinamicoGlobo > creaLink =\nComponent: " + component.getClass().getCanonicalName());
    linkImg = new GloboLaddaLink(linkMetadata, component);
    aggiungiIconaDaCss(linkMetadata, component, linkImg);
    aggiungiLabel(linkMetadata, component);
    add(linkImg);
  }

  private void aggiungiLabel(LinkGloboMetadata linkMetadata, Component component) {
    String sottoFascicoloLabel = getSottoFascicoloLabel(linkMetadata, component);
    linkImg.setLabel(Model.of(sottoFascicoloLabel));
  }

  private void aggiungiIconaDaCss(
      LinkGloboMetadata linkMetadata, Component component, GloboLaddaLink linkImg) {
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
    linkImg.setSpinnerColor(spinnerColor);
    linkImg.setIconType(iconType);
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
    if (resourceId.contains("Panel.") || resourceId.contains("Page.")) {
      String resourceMessage =
          Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
      return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
    } else {
      return resourceId;
    }
  }

  private String getSottoFascicoloLabel(LinkGloboMetadata linkF, Component component) {
    String label = getResourceMessage(linkF.getWicketLabelKeyText(), component);
    if (StringUtils.isBlank(label)) {
      label = linkF.getWicketLabelKeyText();
    }
    return label;
  }
}
