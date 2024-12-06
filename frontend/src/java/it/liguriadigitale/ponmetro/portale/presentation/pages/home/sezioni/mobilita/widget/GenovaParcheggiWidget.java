package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.GenovaParcheggiPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class GenovaParcheggiWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 200774629175403903L;

  public GenovaParcheggiWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -228188022507482635L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(GenovaParcheggiWidget.this);
            setResponsePage(new GenovaParcheggiPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 930796544592966255L;

          @Override
          public String cssClassName() {
            return "icon-genova-pass color-cyan color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("GenovaParcheggiWidget.genovaParcheggi", GenovaParcheggiWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
