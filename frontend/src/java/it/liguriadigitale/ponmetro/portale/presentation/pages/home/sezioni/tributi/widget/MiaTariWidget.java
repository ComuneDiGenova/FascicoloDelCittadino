package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.TariNetribePage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MiaTariWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 5036418217386037597L;

  public MiaTariWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 1440796797219118888L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MiaTariWidget.this);
            // setResponsePage(new QuadroTributarioTariPage());
            setResponsePage(new TariNetribePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 6490815897789960070L;

          @Override
          public String cssClassName() {
            return "icon-cassonetto color-green";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MiaTariWidget.titolo", MiaTariWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
