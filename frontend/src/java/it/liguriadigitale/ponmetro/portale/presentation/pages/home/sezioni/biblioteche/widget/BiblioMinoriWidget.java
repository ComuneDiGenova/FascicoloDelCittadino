package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.biblioteche.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori.BiblioMinoriPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BiblioMinoriWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1014385844754795286L;

  public BiblioMinoriWidget(POSIZIONE posizione) {
    super(posizione);

    fillDati("");
    setOutputMarkupId(true);

    Utente.inizializzaPrivacySebina(getUtente());
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

          private static final long serialVersionUID = 8889112445204938352L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(BiblioMinoriWidget.this);

            if (getUtente().isBibliotecheNonRaggiungibile()) {
              setResponsePage(new ErroreServiziPage("Biblioteche "));
            } else {
              setResponsePage(new BiblioMinoriPage());
            }
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = -7127494034765991638L;

          @Override
          public String cssClassName() {
            return "icon-studente-libro color-cyan color-blue-sebina";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("BiblioMinoriWidget.biblioMinori", BiblioMinoriWidget.this)));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
