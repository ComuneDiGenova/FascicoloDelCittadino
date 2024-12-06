package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.VerificaEmailAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class CambiaEmailAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -3376600919023980663L;

  private FdCEmailTextField email;
  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public CambiaEmailAllerteCortesiaPanel(String id, Object dettagliUtente) {
    super(id, dettagliUtente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(dettagliUtente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DettagliUtente dettagliUtente = (DettagliUtente) dati;

    form.add(new FdCTitoloPanel("titolo", getString("CambiaEmailAllerteCortesiaPanel.titolo")));

    email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(dettagliUtente.getUTENTE(), "EMAIL"),
            CambiaEmailAllerteCortesiaPanel.this);
    email.getTextField().setRequired(true);
    form.addOrReplace(email);

    form.addOrReplace(creaBtnAvanti(dettagliUtente, email.getTextField().getValue()));
    form.addOrReplace(creaBtnAnnulla());
  }

  private FdcAjaxButton creaBtnAvanti(DettagliUtente dettagliUtente, String emailVecchia) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -3483102254767218691L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti su cambia email = ");

            try {

              if (LabelFdCUtil.checkIfNotNull(dettagliUtente)
                  && LabelFdCUtil.checkIfNotNull(dettagliUtente.getUTENTE())) {

                String emailNuova = dettagliUtente.getUTENTE().getEMAIL();

                log.debug("CP email vecchia = " + emailVecchia + " email nuova = " + emailNuova);

                if (PageUtil.isStringValid(dettagliUtente.getUTENTE().getEMAILVERIFICATA())) {

                  if (emailNuova.equalsIgnoreCase(emailVecchia)) {
                    error("Impossibile aggiornare la mail perchè le mail coincidono");
                  } else {

                    ServiceLocator.getInstance()
                        .getServiziAllerteCortesia()
                        .putWsUpdateUtenteMail(emailVecchia, emailNuova);

                    avanti.setVisible(false);
                    annulla.setVisible(false);
                    email.setEnabled(false);

                    setResponsePage(new VerificaEmailAllerteCortesiaPage(dettagliUtente));
                    // success("Email aggiornata ma devi accettare il link sulla mail");

                  }
                }
              }
            } catch (ApiException e) {
              log.error("Errore put aggiorna email allerte cortesia: " + e.getMessage(), e);

              // e.getLocalizedMessage()
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");

              log.debug("CP indexOf = " + indexOf);

              //					String messaggioDaVisualizzare = messaggioRicevuto.substring(indexOf + 1,
              //							messaggioRicevuto.length());

              if (messaggioRicevuto.contains(": null")) {
                messaggioRicevuto = messaggioRicevuto.replace(": null", "");
              }

              log.debug("CP messaggioRicevuto = " + messaggioRicevuto);

              //					log.debug("CP messaggioDaVisualizzare " + messaggioDaVisualizzare);

              error(messaggioRicevuto);

            } catch (BusinessException | IOException e) {
              log.error("BusinessException gestito durante la chiamata delle API:", e);
              error("Impossibile aggiornare la mail.");
            }

            target.add(CambiaEmailAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(CambiaEmailAllerteCortesiaPanel.this);
          }
        };

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 7273460191811614442L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    annulla.setLabel(Model.of(getString("CambiaEmailAllerteCortesiaPanel.annulla")));

    return annulla;
  }
}
