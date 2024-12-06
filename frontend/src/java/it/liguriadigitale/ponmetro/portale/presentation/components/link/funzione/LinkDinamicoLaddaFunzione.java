package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

public class LinkDinamicoLaddaFunzione<T> extends Panel {

  private static final long serialVersionUID = -8224186602289670660L;

  protected final Log log = LogFactory.getLog(getClass());

  // private static final String LINK_ID = "linkImg";
  private static final String LINK_NOD_ID = "linkNoDImg";
  private static final String ATTRIBUTE_TITLE = "title";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private Component object;
  private LaddaAjaxLink<Object> linkImg;

  public LaddaAjaxLink<Object> getLinkImg() {
    return linkImg;
  }

  private Boolean visibilita;

  public LinkDinamicoLaddaFunzione(
      String id,
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String cssIcona,
      Boolean visibilita) {
    super(id);
    creaLink(linkF, t, component, cssIcona);
    this.visibilita = visibilita;
  }

  public LinkDinamicoLaddaFunzione(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String cssIcona) {
    this(id, linkF, t, component, cssIcona, true);
  }

  public LinkDinamicoLaddaFunzione(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null, true);
  }

  public LinkDinamicoLaddaFunzione(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null, null, true);
  }

  public LinkDinamicoLaddaFunzione(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null, null, true);
  }

  @Override
  public boolean isVisible() {
    if (linkImg != null) {
      return visibilita && linkImg.isVisible();
    } else {
      return visibilita && super.isVisible();
    }
  }

  private void creaLink(LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {

    linkImg =
        new LaddaAjaxLink<Object>(LINK_NOD_ID, Type.Link) {

          private static final long serialVersionUID = -6869436907596885000L;

          Class<Component> clazz;

          public Throwable findCauseUsingPlainJava(Throwable throwable) {
            Objects.requireNonNull(throwable);
            Throwable rootCause = throwable;
            while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
              rootCause = rootCause.getCause();
            }
            return rootCause;
          }

          @Override
          protected void onRender() {
            // TODO Auto-generated method stub -
            // https://g
            // ithub.com/apache/wicket/blob/master/wicket-core/src/main/java/org/apache/wicket/MarkupContainer.java
            super.onRender();
          }

          @Override
          protected void onComponentTag(ComponentTag arg0) {
            super.onComponentTag(arg0);
            if (linkF.isTargetInANewWindow()) {
              arg0.put(ATTRIBUTE_TARGET, ATTRIBUTE_TARGET_VALUE);
            }

            String tooltip = "";
            try {
              tooltip = getResourceMessage(linkF.getWicketLabelKeyTitle(), component);
            } catch (Exception e) {
              log.trace(
                  "La stringa non e' la key di un file di properties, linkF.getWicketLabelKeyTitle():"
                      + e.getMessage());
              if (StringUtils.isBlank(tooltip)) {
                tooltip = getResourceMessage(linkF.getWicketLabelKeyText(), component);
                if (StringUtils.isNotEmpty(linkF.getWicketLabelKeyTitle())
                    && !StringUtils.equals(
                        linkF.getWicketLabelKeyText(), linkF.getWicketLabelKeyTitle())) {
                  tooltip = tooltip + " " + linkF.getWicketLabelKeyTitle();
                }
              }
            }

            arg0.put(ATTRIBUTE_TITLE, tooltip);
          }

          @Override
          protected void onConfigure() {
            super.onConfigure();
            List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
            boolean isLinkVisibile =
                (PageUtil.isNomePaginaInElencoFornito(linkF.getWicketClassName(), pagineAbilitate));
            log.debug(
                "[LinkDinamicoFunzione] link a pagina "
                    + linkF.getWicketClassName()
                    + " isVisible: "
                    + isLinkVisibile);
            setVisible(isLinkVisibile && super.isVisible());
          }

          //
          // @Override
          // public boolean isVisible() {
          //
          // List<String> pagineAbilitate = ((LoginInSession)
          // getSession()).getNomiPagineAbilitate();
          // boolean isLinkVisibile =
          // (PageUtil.isPaginaInElencoFornito(linkF.getWicketClassName(),
          // pagineAbilitate));
          // log.debug("[LinkDinamicoFunzione] link a pagina " +
          // linkF.getWicketClassName()
          // + " isVisible: " + isLinkVisibile);
          // return isLinkVisibile && super.isVisible();
          // }

          @SuppressWarnings("unchecked")
          @Override
          public void onClick(AjaxRequestTarget target) {
            try {
              log.debug(
                  ":: LinkDinamicoLaddaFunzione > creaLink > onClick : cerco classe "
                      + linkF.getWicketClassName()
                      + " .... ");
              clazz =
                  (Class<Component>)
                      PageClassFinder.findClassByNameFunction(linkF.getWicketClassName());
              if (clazz.equals(Page404.class)) {
                setVisible(false);
                return;
              }
              log.debug(
                  ":: LinkDinamicoLaddaFunzione > creaLink > onClick : class "
                      + linkF.getWicketClassName()
                      + " trovata ");
              if (t != null) {
                object = clazz.getConstructor(t.getClass()).newInstance(t);
              } else {
                object = clazz.getConstructor().newInstance();
              }
              setResponsePage((IRequestablePage) object);

            } catch (RestartResponseAtInterceptPageException e) {
              throw e;
            } catch (InstantiationException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzione1] onClick error on reflection: " + e.getMessage(), e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzione2] onClick error on reflection: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzione3] onClick error on reflection: " + e.getMessage(), e);
            } catch (InvocationTargetException e) {
              log.debug("---era InvocationTargetException");
              if (e.getCause() instanceof RestartResponseAtInterceptPageException) {
                log.debug("---era una RestartResponseAtInterceptPageException");
                RestartResponseAtInterceptPageException e1 =
                    (RestartResponseAtInterceptPageException) e.getCause();
                throw e1;
              }
              Throwable e1 = findCauseUsingPlainJava(e);
              log.error(
                  "[LinkDinamicoLaddaFunzione4] onClick error on reflection: " + e1.getMessage(),
                  e);
              throw new RestartResponseException(new UtenteNonAutorizzatoPage());
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzione5] onClick error on reflection: " + e.getMessage(), e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzione6] onClick error on reflection: " + e.getMessage(), e);
            }
          }
        };

    log.debug("Component: " + component.getClass().getCanonicalName());

    IconType iconType =
        new IconType("linkNoDImg") {

          private static final long serialVersionUID = 8535411269404222206L;

          @Override
          public String cssClassName() {
            return iconaCss;
          }
        };

    linkImg.setOutputMarkupId(true);

    String[] iconaCssSplitted = iconaCss.split(" ");

    if (iconaCssSplitted.length > 1) {
      linkImg.setIconType(iconType);
    }

    int indexOf = iconaCssSplitted[0].indexOf('-');
    String spinnerColor = iconaCssSplitted[0].substring(indexOf + 1, iconaCssSplitted[0].length());
    if (spinnerColor.equalsIgnoreCase("blue-spid")) {
      spinnerColor = "#0066cb";
    } else if (spinnerColor.equalsIgnoreCase("blue-sebina")) {
      spinnerColor = "#0e5aa8";
    } else if (spinnerColor.equalsIgnoreCase("green")) {
      spinnerColor = "#008d8b";
    }
    linkImg.setSpinnerColor(spinnerColor);

    String sottoFascicoloLabel = getSottoFascicoloLabel(linkF, component);
    linkImg.setLabel(Model.of(sottoFascicoloLabel));
    // linkImg.add(new Label("testoLink", "I miei prestiti e prenotazioni"));//
    // Model.of(sottoFascicoloLabel));
    // linkImg.setBlock(true);

    if (iconaCssSplitted.length == 1) {
      AttributeModifier attributeModifier =
          new AttributeModifier("class", "link-icon btn-link ladda-button");
      linkImg.add(attributeModifier);
    }

    if (linkF.getWicketClassName().equalsIgnoreCase("PrenotazioneAppuntamentoPage")) {
      AttributeModifier attributeModifierGrassettoInNotifice =
          new AttributeModifier("class", "link-icon btn-link ladda-button bottom-nav-label");
      linkImg.add(attributeModifierGrassettoInNotifice);
    }

    add(linkImg);
  }

  private String getResourceMessage(String resourceId, Component component) {
    String resourceMessage =
        Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
    return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
  }

  private String getSottoFascicoloLabel(LinkDinamicoFunzioneData linkF, Component component) {
    return getResourceMessage(linkF.getWicketLabelKeyText(), component);
  }
}
