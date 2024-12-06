package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico.InfoTrafficoPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class InfoTrafficoWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -7379894948566286917L;

  public InfoTrafficoWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = 3818402596802593480L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(InfoTrafficoWidget.this);
            setResponsePage(new InfoTrafficoPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -1927374524755853250L;

          @Override
          public String cssClassName() {
            return "icon-semaforo color-cyan";
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
                    "InfoTrafficoWidget.link.testo.infosultraffico", InfoTrafficoWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
