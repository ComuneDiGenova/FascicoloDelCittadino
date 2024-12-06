package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile.CertificatoStatoCivilePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class CertificatoStatoCivileWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 2285532730936402488L;

  public CertificatoStatoCivileWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -7120075326737553989L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(CertificatoStatoCivileWidget.this);
            setResponsePage(new CertificatoStatoCivilePage());
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 8159329557313074130L;

          @Override
          public String cssClassName() {
            return "icon-certificato-stato-civile color-yellow";
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
                    "CertificatoStatoCivileWidget.certificato",
                    CertificatoStatoCivileWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
