package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.CertificatoAnagraficoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class CertificatoAnagraficoWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 6917216178023390585L;

  public CertificatoAnagraficoWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -2716814672722651649L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(CertificatoAnagraficoWidget.this);
            setResponsePage(new CertificatoAnagraficoPage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 4159784110929117421L;

          @Override
          public String cssClassName() {
            return "icon-certificato-anagrafico color-yellow";
          }
        };

    linkImg.setSpinnerColor("#fcb342");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "CertificatoAnagraficoWidget.certificato", CertificatoAnagraficoWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
