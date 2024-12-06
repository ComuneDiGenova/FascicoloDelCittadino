package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.virtuoso.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia.PlastiPremiaPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class PlastiPremiaWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 2499546588935551452L;

  public PlastiPremiaWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
    setOutputMarkupId(true);
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

          private static final long serialVersionUID = -3397318356688480355L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(PlastiPremiaWidget.this);
            setResponsePage(new PlastiPremiaPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -709004331656191070L;

          @Override
          public String cssClassName() {
            return "icon-plastipremia-logo color-green-light";
          }
        };

    linkImg.setSpinnerColor("#5cdba6");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("PlastiPuntiWidget.plastiPunti", PlastiPremiaWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
