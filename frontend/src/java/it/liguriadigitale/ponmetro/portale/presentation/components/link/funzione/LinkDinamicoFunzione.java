package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
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
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;

public class LinkDinamicoFunzione<T> extends Panel {

  protected final Log log = LogFactory.getLog(getClass());

  private static final String LINK_ID = "link";
  private static final String LABEL_ID = "label";
  private static final String ATTRIBUTE_TITLE = "title";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private static final long serialVersionUID = 6822067991233658190L;
  private Component object;
  private Link<Void> link;

  public LinkDinamicoFunzione(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String cssIcona) {
    super(id);
    creaLink(linkF, t, component, cssIcona);
  }

  public LinkDinamicoFunzione(String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null);
  }

  public LinkDinamicoFunzione(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null);
  }

  public LinkDinamicoFunzione(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null);
  }

  // public public LinkDinamicoFunzione(String id, DatiCompletiFunzione
  // funzioneDb) {
  // super(id);
  // // TODO da cambiare e implementare!!!!!
  // }

  @Override
  public boolean isVisible() {
    if (link != null) {
      return link.isVisible();
    } else {
      return super.isVisible();
    }
  }

  private void creaLink(LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {

    link =
        new Link<Void>(LINK_ID) {

          private static final long serialVersionUID = -8714430499348259886L;
          Class<Component> clazz;

          @SuppressWarnings("unchecked")
          @Override
          public void onClick() {
            try {
              log.debug(
                  ":: LinkDinamicoFunzione > creaLink > onClick : cerco classe "
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
                  ":: LinkDinamicoFunzione > creaLink > onClick : class "
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
                  "[LinkDinamicoFunzione1] onClick error on reflection: " + e.getMessage(), e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoFunzione2] onClick error on reflection: " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoFunzione3] onClick error on reflection: " + e.getMessage(), e);
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
                  "[LinkDinamicoFunzione4] onClick error on reflection: " + e1.getMessage(), e);
              throw new RestartResponseException(new UtenteNonAutorizzatoPage());
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoFunzione5] onClick error on reflection: " + e.getMessage(), e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoFunzione6] onClick error on reflection: " + e.getMessage(), e);
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

          // @Override
          // public boolean isVisible() {
          //
          // List<String> pagineDisabilitate = ((LoginInSession)
          // getSession()).getPagineDisabilitate();
          // boolean isLinkVisibile =
          // !(PageUtil.isPaginaInElencoFornito(linkF.getWicketClassName(),
          // pagineDisabilitate));
          // log.debug("[LinkDinamicoFunzione] link a pagina " +
          // linkF.getWicketClassName()
          // + " isVisible: " + isLinkVisibile);
          // return isLinkVisibile;
          // }

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
    add(link);
    link.add(creaLabel(linkF, component));

    WebMarkupContainer iconaMarkup = new WebMarkupContainer("icona");
    link.add(iconaMarkup);
    if (iconaCss != null) {
      iconaMarkup.add(new AttributeAppender("class", new Model<String>(iconaCss), " "));
      link.add(new AttributeAppender("class", new Model<String>("d-table mx-auto link-icon"), " "));
      iconaMarkup.setVisible(true);
    } else {
      iconaMarkup.setVisible(false);
    }
  }

  private NotEmptyLabel creaLabel(LinkDinamicoFunzioneData linkF, Component component) {
    return new NotEmptyLabel(
        LABEL_ID, getResourceMessage(linkF.getWicketLabelKeyText(), component));
  }

  private String getResourceMessage(String resourceId, Component component) {
    String resourceMessage =
        Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
    return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
  }
}
