package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.portale.UtenteServiziRistorazioneHandler;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.PresenzeInMensaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MensaWidget extends MyWidgetPanel {
  private static final long serialVersionUID = -5935059538192330842L;

  public MensaWidget(POSIZIONE posizione) {
    super(posizione);

    setOutputMarkupId(true);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    add(creaLinkImg());
  }

  @Override
  protected void mostraIcona() {}

  @Override
  public boolean isVisible() {
    return hasListaFigliConLinkVisibile();
  }

  private boolean hasListaFigliConLinkVisibile() {
    List<UtenteServiziRistorazione> listaFigli = getListaFigli();
    if (listaFigli != null) {
      return !(listaFigli.stream()
          .filter(
              iscritto -> (new UtenteServiziRistorazioneHandler(iscritto)).isVisibileLinkMensa())
          .collect(Collectors.toList())
          .isEmpty());
    }
    return false;
  }

  private List<UtenteServiziRistorazione> getListaFigli() {
    try {
      if (getUtente().getListaFigli() == null) {
        getUtente()
            .setListaFigli(
                ServiceLocator.getInstance().getServiziRistorazione().trovaIscritti(getUtente()));
      }
      return getUtente().getListaFigli();
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private LaddaAjaxLink<Object> creaLinkImg() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("linkImg", Type.Link) {

          private static final long serialVersionUID = -3927275642358599838L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MensaWidget.this);
            setResponsePage(new PresenzeInMensaPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -141858586050695849L;

          @Override
          public String cssClassName() {
            return "icon-ristorazione color-orange";
          }
        };

    linkImg.setSpinnerColor("#f95e01");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MensaWidget.link", MensaWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
