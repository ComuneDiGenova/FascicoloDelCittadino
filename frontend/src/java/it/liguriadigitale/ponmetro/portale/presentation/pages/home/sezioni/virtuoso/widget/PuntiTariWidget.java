package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.virtuoso.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.puntitari.PuntiTariPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class PuntiTariWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 3684515143922658785L;

  public PuntiTariWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -4851433943716452980L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(PuntiTariWidget.this);
            setResponsePage(new PuntiTariPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 7360298959160812194L;

          @Override
          public String cssClassName() {
            return "icon-casa-riciclo color-green-light";
          }
        };

    linkImg.setSpinnerColor("#5cdba6");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("PuntiTariWidget.puntiTari", PuntiTariWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
