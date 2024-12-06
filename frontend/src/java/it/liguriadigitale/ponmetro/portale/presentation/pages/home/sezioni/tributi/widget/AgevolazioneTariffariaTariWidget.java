package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.ContributoTariPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class AgevolazioneTariffariaTariWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1667377557891623676L;

  public AgevolazioneTariffariaTariWidget(POSIZIONE posizione) {
    super(posizione);

    setOutputMarkupId(true);
    fillDati("");

    //		LocalDate dataScadenzaContributoTari = LocalDate.of(2022, 1, 1);
    //		boolean visibilitaFunzioneContributoTari =
    // LocalDate.now().isBefore(dataScadenzaContributoTari);
    //		log.debug("CP visibilitaFunzioneContributoTari in widget = " +
    // visibilitaFunzioneContributoTari);
    //		setVisible(visibilitaFunzioneContributoTari);
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
            target.add(AgevolazioneTariffariaTariWidget.this);
            setResponsePage(new ContributoTariPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 8271338554270400600L;

          @Override
          public String cssClassName() {
            return "icon-tari-porcellino color-green displayblock";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AgevolazioneTariffariaTariWidget.titolo",
                    AgevolazioneTariffariaTariWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
