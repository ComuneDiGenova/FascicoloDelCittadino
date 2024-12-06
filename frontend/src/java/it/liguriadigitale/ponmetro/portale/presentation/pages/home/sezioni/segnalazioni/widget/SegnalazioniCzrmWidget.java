package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.segnalazioni.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.HelpCzRMDomandePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class SegnalazioniCzrmWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 2759538700768442112L;

  public SegnalazioniCzrmWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 3947099556978382402L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(SegnalazioniCzrmWidget.this);
            setResponsePage(new HelpCzRMDomandePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          @Override
          public String cssClassName() {
            return "icon-suggerimenti color-orange-segnalaci";
          }
        };

    linkImg.setSpinnerColor("#f90f00");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("SegnalazioniCzrmWidget.segnalazioni", SegnalazioniCzrmWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
