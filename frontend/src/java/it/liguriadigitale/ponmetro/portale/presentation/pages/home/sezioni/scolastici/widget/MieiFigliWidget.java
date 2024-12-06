package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.mieifigli.MieiFigliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MieiFigliWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -5374599160635706150L;

  public MieiFigliWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -7803933186965214722L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieiFigliWidget.this);
            setResponsePage(new MieiFigliPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -1979553366295479166L;

          @Override
          public String cssClassName() {
            return "icon-famiglia color-orange";
          }
        };

    linkImg.setSpinnerColor("#f95e01");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieiFigliWidget.link", MieiFigliWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
