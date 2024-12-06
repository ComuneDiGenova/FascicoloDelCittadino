package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati.form.ModificaDatiPersonaliBibliotecheForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiDettaglioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.RiepilogoBibliotechePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class ModificaDatiPersonaliBibliotechePanel extends BasePanel {

  private static final long serialVersionUID = 1990261841324735536L;

  private ModificaDatiPersonaliBibliotecheForm form = null;

  private boolean isAggiornamentoDatiTutore;

  public ModificaDatiPersonaliBibliotechePanel(
      String id, Utente utenteSebina, boolean isAggiornamentoDatiTutore) {
    super(id);
    this.isAggiornamentoDatiTutore = isAggiornamentoDatiTutore;
    fillDati(utenteSebina);

    setOutputMarkupId(true);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Utente utenteSebina = (Utente) dati;

    form = new ModificaDatiPersonaliBibliotecheForm("form", utenteSebina);
    form.addOrReplace(creaBottoneAvanti(utenteSebina));
    form.addOrReplace(creaBottoneAnnulla());
    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    addOrReplace(form);
  }

  private LaddaAjaxButton creaBottoneAvanti(Utente utenteSebina) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 5720856056870653966L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(ModificaDatiPersonaliBibliotechePanel.this);

            log.debug("CP click aggiorna dati = " + utenteSebina);

            try {
              InlineObject inlineObject = new InlineObject();
              inlineObject.setMail(utenteSebina.getMail());
              inlineObject.setMobile(utenteSebina.getMobile());
              if (isAggiornamentoDatiTutore) {
                inlineObject.setDocIdentTutore("");
              }

              ServiceLocator.getInstance()
                  .getServiziBiblioteche()
                  .patchUtenteById(utenteSebina.getId(), inlineObject);

              // success("Aggiornamento eseguito correttamente.");

              getUtente()
                  .setListaUtenteBiblioteche(
                      ServiceLocator.getInstance()
                          .getServiziBiblioteche()
                          .getUtenteByDocIdentita(getUtente().getCodiceFiscaleOperatore()));

              setResponsePage(new BibliotecheMovimentiDettaglioPage());

            } catch (ApiException | IOException | BusinessException e) {
              error("Errore durante aggiornamento dati");
              log.error("BusinessException gestito durante la chiamata delle API:", e);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(ModificaDatiPersonaliBibliotechePanel.this);

            log.error("CP errore update dati sebina");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "ModificaDatiPersonaliBibliotechePanel.avanti",
                    ModificaDatiPersonaliBibliotechePanel.this)));

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 5163906925273576049L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ModificaDatiPersonaliBibliotechePanel.this);

            form.clearInput();

            setResponsePage(new RiepilogoBibliotechePage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "ModificaDatiPersonaliBibliotechePanel.annulla",
                    ModificaDatiPersonaliBibliotechePanel.this)));

    return annulla;
  }
}
