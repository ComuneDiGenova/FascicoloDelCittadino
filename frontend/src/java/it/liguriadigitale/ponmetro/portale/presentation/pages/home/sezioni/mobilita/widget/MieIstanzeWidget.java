package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MieIstanzeWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -5603715576455866704L;

  public MieIstanzeWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 3157156918119356687L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieIstanzeWidget.this);
            setResponsePage(new MieIstanzePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 2998904042496892847L;

          @Override
          public String cssClassName() {
            return "icon-fogli color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieIstanzeWidget.mieIstanze", MieIstanzeWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
