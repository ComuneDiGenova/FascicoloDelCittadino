package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.abito.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.ContrattiArtePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class ContrattiArteWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -2040886463093803604L;

  public ContrattiArteWidget(POSIZIONE posizione) {
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

  private LaddaAjaxLink<Object> creaLinkImg() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("linkImg", Type.Link) {

          private static final long serialVersionUID = 4248108629365284025L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ContrattiArteWidget.this);
            setResponsePage(new ContrattiArtePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 5337912353178913642L;

          @Override
          public String cssClassName() {
            return "icon-contratti-immobili col-auto color-fc-secondary";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ContrattiArteWidget.titolo", ContrattiArteWidget.this)));
    linkImg.setOutputMarkupId(true);
    return linkImg;
  }
}
