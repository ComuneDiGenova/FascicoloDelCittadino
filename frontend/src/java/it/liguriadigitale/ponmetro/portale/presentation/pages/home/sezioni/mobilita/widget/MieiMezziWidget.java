package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.IMieiMezziPage;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class MieiMezziWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -3639622854343494514L;

  public MieiMezziWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
    setOutputMarkupId(true);
    setVisible(!getUtente().getListaVeicoliAttivi().isEmpty());
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

          private static final long serialVersionUID = -8599152623242839706L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieiMezziWidget.this);
            if (getUtente().isBolloNonRaggiungibile()) {
              setResponsePage(new ErroreServiziPage("i miei mezzi "));
            } else {
              setResponsePage(new IMieiMezziPage());
            }
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -4701551211142123030L;

          @Override
          public String cssClassName() {
            return "icon-mezzi color-cyan color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieiMezziWidget.mieiMezzi", MieiMezziWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
