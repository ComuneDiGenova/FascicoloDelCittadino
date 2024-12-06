package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.MieiDatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MieiDatiNonResidentiWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -1805535551835028324L;

  public MieiDatiNonResidentiWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -1670061374834869410L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieiDatiNonResidentiWidget.this);
            setResponsePage(new MieiDatiPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -4083172454810886331L;

          @Override
          public String cssClassName() {
            return "icon-carta-id color-yellow";
          }
        };

    linkImg.setSpinnerColor("#fcb342");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "MieiDatiNonResidentiWidget.mieiDati", MieiDatiNonResidentiWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
