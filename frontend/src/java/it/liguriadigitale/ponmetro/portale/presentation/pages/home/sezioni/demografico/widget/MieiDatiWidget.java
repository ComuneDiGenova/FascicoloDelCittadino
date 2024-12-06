package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.MieiDatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MieiDatiWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -5374599160635706150L;

  public MieiDatiWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -2653046138399996738L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieiDatiWidget.this);
            setResponsePage(new MieiDatiPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 4409740115069702689L;

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
                .getString("MieiDatiWidget.mieiDati", MieiDatiWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
