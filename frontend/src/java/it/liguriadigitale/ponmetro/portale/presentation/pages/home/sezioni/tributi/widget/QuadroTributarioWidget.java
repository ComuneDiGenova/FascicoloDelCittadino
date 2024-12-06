package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.QuadroTributarioPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class QuadroTributarioWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -8122917549057317982L;

  public QuadroTributarioWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 7828846915352391294L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(QuadroTributarioWidget.this);
            setResponsePage(new QuadroTributarioPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 2784666775685010011L;

          @Override
          public String cssClassName() {
            return "icon-foglio-penna color-green";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("QuadroTributarioWidget.titolo", QuadroTributarioWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
