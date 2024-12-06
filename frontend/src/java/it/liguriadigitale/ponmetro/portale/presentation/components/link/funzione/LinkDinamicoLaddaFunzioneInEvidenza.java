package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoLinkInEvidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

public class LinkDinamicoLaddaFunzioneInEvidenza<T> implements Serializable {

  private static final long serialVersionUID = 861990522210189588L;

  protected final Log log = LogFactory.getLog(getClass());

  private static final String ATTRIBUTE_TITLE = "title";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private Component object;

  private LaddaAjaxLink<Object> laddaLinkImg;
  private Link<Object> link;
  private ExternalLink externalLink;

  public LinkDinamicoLaddaFunzioneInEvidenza() {
    super();
  }

  public LinkDinamicoLaddaFunzioneInEvidenza(
      String id,
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String iconaCss,
      EnumTipoLinkInEvidenza tipoLinkInEvidenza,
      String url) {
    super();

    if (tipoLinkInEvidenza.equals(EnumTipoLinkInEvidenza.LADDAAJAXLINK)) {
      LaddaAjaxLink<Object> laddaAjaxLink = creaLaddaLink(id, linkF, t, component, iconaCss);
      setLaddaLinkImg(laddaAjaxLink);
    } else if (tipoLinkInEvidenza.equals(EnumTipoLinkInEvidenza.LINK)) {
      Link<Object> link = creaLink(id, linkF, t, component, iconaCss);
      setLink(link);
    } else if (tipoLinkInEvidenza.equals(EnumTipoLinkInEvidenza.EXTERNALLINK)) {
      ExternalLink externalLink = creaExternalLink(id, linkF, t, component, iconaCss, url);
      setExternalLink(externalLink);
    }
  }

  private Link<Object> creaLink(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {
    link =
        new Link<Object>(id) {

          private static final long serialVersionUID = -6146824677350972357L;

          Class<Component> clazz;

          @SuppressWarnings("unchecked")
          @Override
          public void onClick() {
            try {
              log.debug(
                  ":: LinkDinamicoLaddaFunzioneInEvidenza > creaLink > onClick : cerco classe "
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
                  ":: LinkDinamicoLaddaFunzioneInEvidenza > creaLink > onClick : class "
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
                  "[LinkDinamicoLaddaFunzioneInEvidenza1] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza2] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza3] onClick error on reflection: "
                      + e.getMessage(),
                  e);
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
                  "[LinkDinamicoLaddaFunzioneInEvidenza4] onClick error on reflection: "
                      + e1.getMessage(),
                  e);
              throw new RestartResponseException(new UtenteNonAutorizzatoPage());
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza5] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza6] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            }
          }

          public Throwable findCauseUsingPlainJava(Throwable throwable) {
            Objects.requireNonNull(throwable);
            Throwable rootCause = throwable;
            while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
              rootCause = rootCause.getCause();
            }
            return rootCause;
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
                  "La stringa non è la key di un file di properties, linkF.getWicketLabelKeyTitle():"
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
          public boolean isVisible() {

            List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
            boolean isLinkVisibile =
                (PageUtil.isNomePaginaInElencoFornito(linkF.getWicketClassName(), pagineAbilitate));
            log.debug(
                "[LinkDinamicoFunzione] link a pagina "
                    + linkF.getWicketClassName()
                    + " isVisible: "
                    + isLinkVisibile);
            return isLinkVisibile;
          }
        };

    log.debug("Component: " + component.getClass().getCanonicalName());

    return link;
  }

  private ExternalLink creaExternalLink(
      String id,
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String iconaCss,
      String url) {
    externalLink =
        new ExternalLink(id, url) {

          private static final long serialVersionUID = -4310464373505908447L;

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
                  "La stringa non è la key di un file di properties, linkF.getWicketLabelKeyTitle():"
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
          public boolean isVisible() {

            List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
            boolean isLinkVisibile =
                (PageUtil.isNomePaginaInElencoFornito(linkF.getWicketClassName(), pagineAbilitate));
            log.debug(
                "[LinkDinamicoFunzione] link a pagina "
                    + linkF.getWicketClassName()
                    + " isVisible: "
                    + isLinkVisibile);
            return isLinkVisibile;
          }
        };

    return externalLink;
  }

  public LaddaAjaxLink<Object> creaLaddaLink(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {

    laddaLinkImg =
        new LaddaAjaxLink<Object>(id, Type.Link) {

          private static final long serialVersionUID = 8701503993497309875L;

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
                  "La stringa non è la key di un file di properties, linkF.getWicketLabelKeyTitle():"
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
          public boolean isVisible() {

            List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
            boolean isLinkVisibile =
                (PageUtil.isNomePaginaInElencoFornito(linkF.getWicketClassName(), pagineAbilitate));
            log.debug(
                "[LinkDinamicoFunzione] link a pagina "
                    + linkF.getWicketClassName()
                    + " isVisible: "
                    + isLinkVisibile);
            return isLinkVisibile;
          }

          @SuppressWarnings("unchecked")
          @Override
          public void onClick(AjaxRequestTarget target) {
            try {
              log.debug(
                  ":: LinkDinamicoLaddaFunzioneInEvidenza > creaLink > onClick : cerco classe "
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
                  ":: LinkDinamicoLaddaFunzioneInEvidenza > creaLink > onClick : class "
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
                  "[LinkDinamicoLaddaFunzioneInEvidenza1] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza2] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza3] onClick error on reflection: "
                      + e.getMessage(),
                  e);
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
                  "[LinkDinamicoLaddaFunzioneInEvidenza4] onClick error on reflection: "
                      + e1.getMessage(),
                  e);
              throw new RestartResponseException(new UtenteNonAutorizzatoPage());
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza5] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneInEvidenza6] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            }
          }
        };

    log.debug("Component: " + component.getClass().getCanonicalName());

    String[] iconaCssSplitted = iconaCss.split(" ");

    int indexOf = iconaCssSplitted[iconaCssSplitted.length - 1].indexOf('-');
    String spinnerColor =
        iconaCssSplitted[iconaCssSplitted.length - 1].substring(
            indexOf + 1, iconaCssSplitted[iconaCssSplitted.length - 1].length());
    if (spinnerColor.equalsIgnoreCase("blue-spid")) {
      spinnerColor = "#0066cb";
    }
    if (spinnerColor.equalsIgnoreCase("blue-sebina")) {
      spinnerColor = "#0e5aa8";
    }
    if (spinnerColor.equalsIgnoreCase("green")) {
      spinnerColor = "#008d8b";
    }
    laddaLinkImg.setSpinnerColor(spinnerColor);

    String sottoFascicoloLabel = getSottoFascicoloLabel(linkF, component);
    laddaLinkImg.setLabel(Model.of(sottoFascicoloLabel));

    if (iconaCssSplitted.length == 1) {
      AttributeModifier attributeModifier =
          new AttributeModifier("class", "link-icon btn-link ladda-button");
      laddaLinkImg.add(attributeModifier);
    }

    laddaLinkImg.setOutputMarkupId(true);

    return laddaLinkImg;
  }

  private String getResourceMessage(String resourceId, Component component) {
    return resourceId;
  }

  private String getSottoFascicoloLabel(LinkDinamicoFunzioneData linkF, Component component) {
    return getResourceMessage(linkF.getWicketLabelKeyText(), component);
  }

  public LaddaAjaxLink<Object> getLaddaLinkImg() {
    return laddaLinkImg;
  }

  public void setLaddaLinkImg(LaddaAjaxLink<Object> laddaLinkImg) {
    this.laddaLinkImg = laddaLinkImg;
  }

  public Link<Object> getLink() {
    return link;
  }

  public void setLink(Link<Object> link) {
    this.link = link;
  }

  public ExternalLink getExternalLink() {
    return externalLink;
  }

  public void setExternalLink(ExternalLink externalLink) {
    this.externalLink = externalLink;
  }
}
