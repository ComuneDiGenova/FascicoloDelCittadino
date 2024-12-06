package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.AbbonamentiAmtPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class AbbonamentiAmtWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1028529236018037512L;

  public AbbonamentiAmtWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -5312899936950158083L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(AbbonamentiAmtWidget.this);
            setResponsePage(new AbbonamentiAmtPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 4561210969254369060L;

          @Override
          public String cssClassName() {
            return "icon-abbonamenti color-cyan color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AbbonamentiAmtWidget.abbonamenti", AbbonamentiAmtWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
