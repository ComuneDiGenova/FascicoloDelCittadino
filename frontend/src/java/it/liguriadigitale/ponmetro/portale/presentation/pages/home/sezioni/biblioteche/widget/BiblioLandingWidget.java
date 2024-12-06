package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.biblioteche.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizioneNonResidentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class BiblioLandingWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 2944990912105349187L;

  public BiblioLandingWidget(POSIZIONE posizione) {
    super(posizione);

    setOutputMarkupId(true);
    fillDati("");

    Utente.inizializzaPrivacySebina(getUtente());
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

          private static final long serialVersionUID = 6851383408780062841L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(BiblioLandingWidget.this);

            if (getUtente().isBibliotecheNonRaggiungibile()) {
              setResponsePage(new ErroreServiziPage("Biblioteche "));
            } else {
              if (getUtente().getListaUtenteBiblioteche() != null) {

                String nomeSpid = getUtente().getNome().trim();
                String cognomeSpid = getUtente().getCognome().trim();

                String nomeSebina =
                    getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
                String cognomeSebina =
                    getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

                boolean datiSpidSebinaOk =
                    nomeSpid.equalsIgnoreCase(nomeSebina)
                        && cognomeSpid.equalsIgnoreCase(cognomeSebina);

                if (!datiSpidSebinaOk) {
                  setResponsePage(new BibliotecheIscrizionePage(datiSpidSebinaOk));
                } else {
                  setResponsePage(new BibliotecheMovimentiPage());
                }
              } else {
                if (getUtente().isResidente()) {
                  setResponsePage(
                      new BibliotecheIscrizionePage(
                          getUtente().getUtenteLoggatoComeComponenteNucleo()));
                } else {
                  setResponsePage(new BibliotecheIscrizioneNonResidentiPage());
                }
              }
            }
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 6654342664709887595L;

          @Override
          public String cssClassName() {
            if (getUtente().getListaUtenteBiblioteche() != null) {
              String nomeSpid = getUtente().getNome().trim();
              String cognomeSpid = getUtente().getCognome().trim();

              String nomeSebina =
                  getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
              String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

              boolean datiSpidSebinaOk =
                  nomeSpid.equalsIgnoreCase(nomeSebina)
                      && cognomeSpid.equalsIgnoreCase(cognomeSebina);

              if (!datiSpidSebinaOk) {
                return "icon-foglio-penna color-blue-sebina";
              } else {
                return "icon-libri color-blue-sebina";
              }
            } else {
              return "icon-foglio-penna color-blue-sebina";
            }
          }
        };

    linkImg.setSpinnerColor("#0e5aa8");
    linkImg.setIconType(iconType);

    if (getUtente().getListaUtenteBiblioteche() != null) {
      String nomeSpid = getUtente().getNome().trim();
      String cognomeSpid = getUtente().getCognome().trim();

      String nomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
      String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

      boolean datiSpidSebinaOk =
          nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);

      if (!datiSpidSebinaOk) {
        linkImg.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString("BiblioLandingWidget.iscrizione", BiblioLandingWidget.this)));
      } else {
        linkImg.setLabel(
            Model.of(
                Application.get()
                    .getResourceSettings()
                    .getLocalizer()
                    .getString("BiblioLandingWidget.movimenti", BiblioLandingWidget.this)));
      }
    } else {
      linkImg.setLabel(
          Model.of(
              Application.get()
                  .getResourceSettings()
                  .getLocalizer()
                  .getString("BiblioLandingWidget.iscrizione", BiblioLandingWidget.this)));
    }

    linkImg.setOutputMarkupId(true);

    return linkImg;
  }
}
