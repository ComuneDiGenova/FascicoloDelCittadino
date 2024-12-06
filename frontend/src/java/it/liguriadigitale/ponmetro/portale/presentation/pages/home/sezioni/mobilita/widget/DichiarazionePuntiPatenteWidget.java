package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class DichiarazionePuntiPatenteWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -7097435296748925430L;

  public DichiarazionePuntiPatenteWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 6042025832765573612L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DichiarazionePuntiPatenteWidget.this);
            setResponsePage(new DichiarazionePuntiPatenteConducentePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 255843216320658482L;

          @Override
          public String cssClassName() {
            return "icon-bandi color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DichiarazionePuntiPatenteWidget.dpp", DichiarazionePuntiPatenteWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
