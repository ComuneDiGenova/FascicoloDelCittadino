package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.VariazioniDiResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class VariazioniDiResidenzaWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 6197446431211760596L;

  public VariazioniDiResidenzaWidget(POSIZIONE posizione) {
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

          private static final long serialVersionUID = -8121593329856787048L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(VariazioniDiResidenzaWidget.this);
            setResponsePage(VariazioniDiResidenzaPage.class);
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 8592558996361688661L;

          @Override
          public String cssClassName() {
            return "icon-passeggiate color-yellow";
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
                    "VariazioniDiResidenzaWidget.variazioni", VariazioniDiResidenzaWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
