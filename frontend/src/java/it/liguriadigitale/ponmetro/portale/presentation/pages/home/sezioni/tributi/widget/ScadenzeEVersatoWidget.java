package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.ScadenzeEVersatoPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class ScadenzeEVersatoWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 7102870724758603680L;

  public ScadenzeEVersatoWidget(POSIZIONE posizione) {
    super(posizione);

    setOutputMarkupId(true);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    add(creaLinkImg());
  }

  @Override
  protected void mostraIcona() {
    // TODO Auto-generated method stub

  }

  private LaddaAjaxLink<Object> creaLinkImg() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("linkImg", Type.Link) {

          private static final long serialVersionUID = 4248108629365284025L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ScadenzeEVersatoWidget.this);
            setResponsePage(new ScadenzeEVersatoPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 5337912353178913642L;

          @Override
          public String cssClassName() {
            return "icon-eventi-partecipazione color-green";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ScadenzeEVersatoWidget.titolo", ScadenzeEVersatoWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
