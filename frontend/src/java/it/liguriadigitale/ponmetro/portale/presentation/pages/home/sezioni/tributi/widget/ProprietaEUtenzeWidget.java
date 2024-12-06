package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.ProprietaEUtenzePage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class ProprietaEUtenzeWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1667377557891623676L;

  public ProprietaEUtenzeWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 7902490827642347702L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ProprietaEUtenzeWidget.this);
            setResponsePage(new ProprietaEUtenzePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 8271338554270400600L;

          @Override
          public String cssClassName() {
            return "icon-case color-green displayblock";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ProprietaEUtenzeWidget.titolo", ProprietaEUtenzeWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
