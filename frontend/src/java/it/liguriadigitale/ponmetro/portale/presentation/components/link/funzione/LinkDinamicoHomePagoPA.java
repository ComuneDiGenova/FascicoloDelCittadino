package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.mipGlobali.MipGlobaliErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
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
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class LinkDinamicoHomePagoPA<T> extends Panel {

  private static final long serialVersionUID = -5961359632280639402L;

  protected final Log log = LogFactory.getLog(getClass());

  private static final String LINK_ID = "link";
  private static final String LABEL_ID = "label";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private Component object;
  private Link<Void> link;

  public LinkDinamicoHomePagoPA(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String cssIcona) {
    super(id);
    creaLink(linkF, t, component, cssIcona);
  }

  public LinkDinamicoHomePagoPA(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null);
  }

  public LinkDinamicoHomePagoPA(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null);
  }

  public LinkDinamicoHomePagoPA(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null);
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    setVisible(ServiceLocator.getInstance().getServiziHomePage().isVisibilePagoPa());
  }

  private void creaLink(LinkDinamicoFunzioneData linkF, T t, Component component, String iconaCss) {

    link =
        new Link<Void>(LINK_ID) {

          private static final long serialVersionUID = 8681385092494150041L;
          Class<Component> clazz;

          @SuppressWarnings("unchecked")
          @Override
          public void onClick() {
            try {
              log.debug(
                  ":: LinkDinamicoHomePagoPA > creaLink > onClick : cerco classe "
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
                  ":: LinkDinamicoHomePagoPA > creaLink > onClick : class "
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
                  "[LinkDinamicoFunzioneMipGlobali1] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoFunzioneMipGlobali2] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoFunzioneMipGlobali3] onClick error on reflection: "
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
                  "[LinkDinamicoFunzioneMipGlobali4] onClick error on reflection: "
                      + e1.getMessage(),
                  e);
              throw new RestartResponseException(new MipGlobaliErrorPage(e1.getMessage()));
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoFunzioneMipGlobali5] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoFunzioneMipGlobali6] onClick error on reflection: "
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
            setVisible(isLinkVisibile);
          }
        };

    log.debug("Component: " + component.getClass().getCanonicalName());
    add(link);
    link.add(creaLabel(linkF, component));

    WebMarkupContainer iconaMarkup = new WebMarkupContainer("icona");
    Image image = new Image("image", new ContextRelativeResource("/images/pagopa-logo.png"));
    iconaMarkup.add(image);
    link.add(iconaMarkup);

    if (iconaCss != null) {
      iconaMarkup.add(new AttributeAppender("class", new Model<String>(iconaCss)));
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
