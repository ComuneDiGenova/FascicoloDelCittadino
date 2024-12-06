package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.bolloAuto.BolloAutoErroriPage;
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

public class LinkDinamicoLaddaFunzioneBolloAuto<T> extends Panel {

  private static final long serialVersionUID = -354452919778342340L;

  protected final Log log = LogFactory.getLog(getClass());

  private static final String LINK_ID = "linkImg";
  private static final String ATTRIBUTE_TITLE = "title";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private Component object;
  private LaddaAjaxLink<Object> linkImg;

  public LinkDinamicoLaddaFunzioneBolloAuto(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String cssIcona) {
    super(id);
    creaLinkBolloAuto(linkF, t, component, cssIcona);
  }

  public LinkDinamicoLaddaFunzioneBolloAuto(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null);
  }

  public LinkDinamicoLaddaFunzioneBolloAuto(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null);
  }

  public LinkDinamicoLaddaFunzioneBolloAuto(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null);
  }

  @Override
  public boolean isVisible() {
    if (linkImg != null) {
      return linkImg.isVisible();
    } else {
      return super.isVisible();
    }
  }

  private void creaLinkBolloAuto(
      LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {

    linkImg =
        new LaddaAjaxLink<Object>(LINK_ID, Type.Link) {

          private static final long serialVersionUID = -4259431720916559039L;

          Class<Component> clazz;

          @Override
          public void onClick(AjaxRequestTarget target) {
            try {
              log.debug(
                  ":: LinkDinamicoLaddaFunzioneBolloAuto > creaLink > onClick : cerco classe "
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
                  ":: LinkDinamicoLaddaFunzioneBolloAuto > creaLink > onClick : class "
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
                  "[LinkDinamicoLaddaFunzioneBolloAuto1] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneBolloAuto2] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneBolloAuto3] onClick error on reflection: "
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
                  "[LinkDinamicoLaddaFunzioneBolloAuto4] onClick error on reflection: "
                      + e1.getMessage(),
                  e);
              throw new RestartResponseException(new BolloAutoErroriPage(e1.getMessage()));
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneBolloAuto5] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoLaddaFunzioneBolloAuto6] onClick error on reflection: "
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
    add(linkImg);

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -6743720270129474340L;

          @Override
          public String cssClassName() {
            return iconaCss;
          }
        };
    linkImg.setIconType(iconType);

    String[] iconaCssSplitted = iconaCss.split(" ");
    int indexOf = iconaCssSplitted[0].indexOf("-");
    String spinnerColor = iconaCssSplitted[0].substring(indexOf + 1, iconaCssSplitted[0].length());
    linkImg.setSpinnerColor(spinnerColor);

    String sottoFascicoloLabel = getSottoFascicoloLabel(linkF, component);
    linkImg.setLabel(Model.of(sottoFascicoloLabel));

    AttributeModifier attributeModifier =
        new AttributeModifier("style", "padding-left: 10px; padding-right:10 px;");
    linkImg.add(attributeModifier);

    linkImg.setOutputMarkupId(true);
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
