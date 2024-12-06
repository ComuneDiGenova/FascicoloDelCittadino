package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.common.autorizzazione.UtenteNonAutorizzatoPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.icon.IconaInfo;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class LinkDinamicoFunzioneLinkEsterni<T> extends Panel {

  protected final Log log = LogFactory.getLog(getClass());

  private static final String LINK_ID = "linkImg";
  private static final String LABEL_ID = "label";
  private static final String ATTRIBUTE_TITLE = "title";
  private static final String ATTRIBUTE_TARGET = "target";
  private static final String ATTRIBUTE_TARGET_VALUE = "_blank";

  private static final long serialVersionUID = 6822067991233658190L;
  private Component object;
  private Link<Void> link;

  private LinkGloboMetadata linkGloboMetadata;

  private boolean visibilita;

  private WebMarkupContainer div;

  private boolean labelObbligatoria = false;

  private boolean isGeoworks = false;

  public LinkDinamicoFunzioneLinkEsterni(
      String id,
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String cssIcona,
      String sottoFascicolo,
      boolean isTutorial,
      boolean isGeoworks) {
    super(id);
    linkF.setTargetInANewWindow(true);
    log.debug(
        "\nLinkDinamicoFunzioneLinkEsterni - LinkDinamicoFunzioneData"
            + "\nLinkDinamicoFunzioneLinkEsterni:\nID = "
            + id
            + "\nLinkDinamicoFunzioneData:\nClassName = "
            + linkF.getWicketClassName()
            + "\nTitle = "
            + linkF.getWicketLabelKeyTitle()
            + "\nKeyText = "
            + linkF.getWicketLabelKeyText()
            + "\nisTargetInANewWindow = "
            + linkF.isTargetInANewWindow()
            + "\nIconaCss = "
            + cssIcona
            + "\nComponent ="
            + component);

    this.isGeoworks = isGeoworks;
    log.debug("this.isGeoworks = " + this.isGeoworks);

    creaComponente(t, component, cssIcona, sottoFascicolo, isTutorial, linkF, false);
  }

  public LinkDinamicoFunzioneLinkEsterni(
      String id,
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String cssIcona,
      String sottoFascicolo,
      boolean isTutorial) {
    super(id);
    linkF.setTargetInANewWindow(true);
    log.debug(
        "\nLinkDinamicoFunzioneLinkEsterni - LinkDinamicoFunzioneData"
            + "\nLinkDinamicoFunzioneLinkEsterni:\nID = "
            + id
            + "\nLinkDinamicoFunzioneData:\nClassName = "
            + linkF.getWicketClassName()
            + "\nTitle = "
            + linkF.getWicketLabelKeyTitle()
            + "\nKeyText = "
            + linkF.getWicketLabelKeyText()
            + "\nisTargetInANewWindow = "
            + linkF.isTargetInANewWindow()
            + "\nIconaCss = "
            + cssIcona
            + "\nComponent ="
            + component);
    creaComponente(t, component, cssIcona, sottoFascicolo, isTutorial, linkF, false);
  }

  public LinkDinamicoFunzioneLinkEsterni(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null, null, false);
  }

  public LinkDinamicoFunzioneLinkEsterni(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null);
  }

  public LinkDinamicoFunzioneLinkEsterni(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null);
  }

  public LinkDinamicoFunzioneLinkEsterni(
      String id,
      LinkGloboMetadata linkF,
      T t,
      Component component,
      String cssIcona,
      String sottoFascicolo,
      boolean isTutorial,
      boolean visibilita,
      boolean labelObbligatoria) {
    super(id);
    this.visibilita = visibilita;
    this.linkGloboMetadata = linkF;
    linkF.setTargetInANewWindow(true);
    LinkDinamicoFunzioneData link = LinkDinamicoFunzioneDataBuilder.getInstance().build(linkF);
    log.debug(
        "\nLinkDinamicoFunzioneLinkEsterni - LinkGloboMetadata"
            + "\nLinkDinamicoFunzioneLinkEsterni:\nID = "
            + id
            + "\nLinkDinamicoFunzioneData:\nClassName = "
            + link.getWicketClassName()
            + "\nTitle = "
            + link.getWicketLabelKeyTitle()
            + "\nisTargetInANewWindow = "
            + link.isTargetInANewWindow()
            + "\nIconaCss = "
            + cssIcona
            + "\nVisibilita = "
            + visibilita);
    this.labelObbligatoria = labelObbligatoria;
    creaComponente(t, component, cssIcona, sottoFascicolo, isTutorial, link, true);
  }

  private void creaComponente(
      T t,
      Component component,
      String cssIcona,
      String sottoFascicolo,
      boolean isTutorial,
      LinkDinamicoFunzioneData link,
      boolean isFromLinkGloboMetadata) {
    div = new WebMarkupContainer("div");
    addOrReplace(div);

    creaLink(link, t, component, cssIcona, sottoFascicolo, isTutorial, isFromLinkGloboMetadata);
    aggiungiLabel(link, component, isFromLinkGloboMetadata);
    aggiungiIcona(cssIcona, sottoFascicolo, isTutorial);
  }

  @Override
  public boolean isVisible() {
    if (link != null) {
      return link.isVisible();
    } else {
      return super.isVisible();
    }
  }

  private void creaLink(
      LinkDinamicoFunzioneData linkF,
      T t,
      Component component,
      String iconaCss,
      String sottoFascicolo,
      boolean isTutorial,
      boolean isFromLinkGloboMetadata) {

    log.debug("LinkDinamicoFunzioneLinkEsterni > CreaComponente > creaLink ");
    link =
        new Link<Void>(LINK_ID) {

          private static final long serialVersionUID = 5264458956283480785L;

          Class<Component> clazz;

          @SuppressWarnings("unchecked")
          @Override
          public void onClick() {
            try {
              if (checkUtilizzoLinkGloboMetadata(linkF, isFromLinkGloboMetadata)) {
                Class<IRequestablePage> clazz =
                    (Class<IRequestablePage>)
                        PageClassFinder.findClassByNameFunction(
                            linkGloboMetadata.getWicketClassName());
                if (clazz.equals(Page404.class)) {
                  setVisible(false);
                  return;
                }
                Component object =
                    (Component)
                        clazz
                            .getConstructor(linkGloboMetadata.getPageParameters().getClass())
                            .newInstance(linkGloboMetadata.getPageParameters());
                log.debug(
                    "\nParametro passato: "
                        + linkGloboMetadata.getParametro()
                        + "\nPageParameters = \n"
                        + linkGloboMetadata.getPageParameters());
                setResponsePage((IRequestablePage) object);
              }
              log.debug(
                  ":: LinkDinamicoFunzioneLinkEsterni > creaLink > onClick : cerco classe "
                      + linkF.getWicketClassName()
                      + " .... ");
              clazz =
                  (Class<Component>)
                      PageClassFinder.findClassByNameFunction(linkF.getWicketClassName());
              if (clazz.equals(Page404.class)) {
                log.debug("\nclazz.equals(Page404.class) = true" + clazz.getCanonicalName());
                setVisible(false);
                return;
              }
              log.debug(
                  ":: LinkDinamicoFunzioneLinkEsterni > creaLink > onClick : class "
                      + linkF.getWicketClassName()
                      + " trovata ");
              if (t != null) {
                object = clazz.getConstructor(t.getClass()).newInstance(t);
              } else {
                object = clazz.getConstructor().newInstance();
              }
              setResponsePage((IRequestablePage) object);

            } catch (RestartResponseAtInterceptPageException e) {
              log.debug("RestartResponseAtInterceptPageException = " + e);
              throw e;
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
                  "[LinkDinamicoFunzioneLinkEsterni - InvocationTargetException] onClick error on reflection: "
                      + e1.getMessage(),
                  e);
              throw new RestartResponseException(new UtenteNonAutorizzatoPage());
            } catch (NoSuchMethodException e) {
              log.error(
                  "[LinkDinamicoFunzioneLinkEsterni -NoSuchMethodException] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (SecurityException e) {
              log.error(
                  "[LinkDinamicoFunzioneLinkEsterni - SecurityException] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (InstantiationException e) {
              log.error(
                  "[LinkDinamicoFunzioneLinkEsterni - InstantiationException] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalAccessException e) {
              log.error(
                  "[LinkDinamicoFunzioneLinkEsterni - IllegalAccessException] onClick error on reflection: "
                      + e.getMessage(),
                  e);
            } catch (IllegalArgumentException e) {
              log.error(
                  "[LinkDinamicoFunzioneLinkEsterni -IllegalArgumentException] onClick error on reflection: "
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
            if (isFromLinkGloboMetadata && StringUtils.isNotEmpty(linkF.getWicketLabelKeyText())) {
              tooltip = linkF.getWicketLabelKeyText();
            } else {
              try {
                log.debug(
                    "\n!linkF.getWicketLabelKeyTitle().isEmpty() = "
                            + !linkF.getWicketLabelKeyTitle().isEmpty()
                            + "\nlinkF.getWicketLabelKeyTitle()!=null = "
                            + linkF.getWicketLabelKeyTitle()
                        != null);
                if (!linkF.getWicketLabelKeyTitle().isEmpty()
                    && linkF.getWicketLabelKeyTitle() != null) {
                  tooltip = getResourceMessage(linkF.getWicketLabelKeyTitle(), component);
                }
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
            }
            arg0.put(ATTRIBUTE_TITLE, tooltip);
          }

          @Override
          protected void onConfigure() {
            super.onConfigure();
            setVisible(setVisibility(linkF));
          }
        };

    log.debug("Component: " + component.getClass().getCanonicalName());

    div.addOrReplace(link);
  }

  private void aggiungiIcona(String iconaCss, String sottoFascicolo, boolean isTutorial) {
    log.debug(
        "LinkDinamicoFunzioneLinkEsterni > CreaComponente > aggiungiIcona " + this.isGeoworks);

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    link.add(icona);

    Image image = new Image("image", "");
    image.setEnabled(false);
    image.setVisible(false);

    WebMarkupContainer tagBr = new WebMarkupContainer("tagBr");
    tagBr.setVisible(this.isGeoworks);
    link.addOrReplace(tagBr);

    if (linkGloboMetadata != null
        && getListaImmaginiAltNecessarie().contains(linkGloboMetadata.getWicketClassName())) {
      image.setImageResource(new ContextRelativeResource("/images/bigmet.jpg"));
      image.setEnabled(true);
      image.setVisible(true);

      disattivaIconaInfo();
    } else {
      if (iconaCss != null) {
        if (iconaCss.contains("info")) {
          log.debug("icona info = " + iconaCss);
          IconaInfo iconaInfo = new IconaInfo("iconaInfo");
          link.addOrReplace(iconaInfo);
        } else {
          disattivaIconaInfo();

          icona.add(new AttributeAppender("class", new Model<String>(iconaCss), " "));
          icona.setVisible(true);
        }
        link.add(
            new AttributeAppender("class", new Model<String>("d-table mx-auto link-icon"), " "));

      } else {
        icona.setVisible(false);
      }
      if (isTutorial) {
        log.debug("iconaCss is iconLinkEsterni= " + iconaCss);
        icona.add(new AttributeAppender("class", new Model<String>("containerTutorial"), " "));
      }
    }

    icona.add(image);

    if (labelObbligatoria) {
      icona.add(new AttributeAppender("style", "display: block;"));
    }
  }

  private void disattivaIconaInfo() {
    EmptyPanel iconaInfo = new EmptyPanel("iconaInfo");
    iconaInfo.setEnabled(false);
    iconaInfo.setVisible(false);
    link.addOrReplace(iconaInfo);
  }

  private List<String> getListaImmaginiAltNecessarie() {
    List<String> lista = new ArrayList<>();
    lista.add("BibliotecheSebinaLinkPage");
    return lista;
  }

  private void aggiungiLabel(
      LinkDinamicoFunzioneData linkF, Component component, boolean isFromLinkGloboMetadata) {
    log.debug("LinkDinamicoFunzioneLinkEsterni > CreaComponente > aggiungiLabel ");
    link.add(creaLabel(linkF, component));
  }

  public boolean setVisibility(LinkDinamicoFunzioneData linkF) {

    List<String> pagineAbilitate = ((LoginInSession) getSession()).getNomiPagineAbilitate();
    boolean isLinkVisibile =
        (PageUtil.isNomePaginaInElencoFornito(linkF.getWicketClassName(), pagineAbilitate));
    log.debug(
        "[LinkDinamicoFunzioneLinkEsterni] link a pagina "
            + linkF.getWicketClassName()
            + " isVisible: "
            + isLinkVisibile);
    if (linkF.getWicketClassName().equalsIgnoreCase("CreaLinkConUrlPage")
        && linkGloboMetadata.getParametro() != null) {
      log.debug(
          " linkF.getWicketClassName() [ "
              + linkF.getWicketClassName()
              + " ] == CreaLinkConUrlPage");
      isLinkVisibile = visibilita;
    }
    return isLinkVisibile;
  }

  private boolean checkUtilizzoLinkGloboMetadata(
      LinkDinamicoFunzioneData linkF, boolean isFromLinkGloboMetadata) {
    return isFromLinkGloboMetadata
        && linkF.getWicketClassName().equalsIgnoreCase("CreaLinkConUrlPage");
  }

  private NotEmptyLabel creaLabel(LinkDinamicoFunzioneData linkF, Component component) {
    log.debug(
        "LinkDinamicoFunzioneLinkEsterni > CreaComponente > creaLabel \n isGeoworks = "
            + this.isGeoworks);

    if (this.isGeoworks) {
      return new NotEmptyLabel(LABEL_ID, linkF.getWicketLabelKeyText());
    } else {
      return new NotEmptyLabel(
          LABEL_ID, getResourceMessage(linkF.getWicketLabelKeyText(), component));
    }
  }

  private String getResourceMessage(String resourceId, Component component) {
    log.debug("LinkDinamicoFunzioneLinkEsterni > CreaComponente > getResourceMessage ");
    log.debug(
        "\ngetResourceMessage\nresourceId = "
            + resourceId
            + "\ncomponent.id = "
            + component.getId());
    String resourceMessage = "";
    if (StringUtils.contains(resourceId, ".")) {
      resourceMessage =
          Application.get().getResourceSettings().getLocalizer().getString(resourceId, component);
      log.debug("getResourceMessage: " + resourceMessage);
    }
    if (labelObbligatoria) {
      resourceMessage = resourceId;
    }
    return StringUtils.isBlank(resourceMessage) ? " " : resourceMessage;
  }
}
