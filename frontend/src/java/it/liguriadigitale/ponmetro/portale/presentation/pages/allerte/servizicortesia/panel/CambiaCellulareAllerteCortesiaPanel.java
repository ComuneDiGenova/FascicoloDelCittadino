package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.Utente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.VerificaCellulareAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class CambiaCellulareAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 7259647474670246404L;

  private FdCTextField cellulare;
  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public CambiaCellulareAllerteCortesiaPanel(String id, Object datiUtente) {
    super(id, datiUtente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiUtente);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    Utente datiUtente = (Utente) dati;

    form.add(new FdCTitoloPanel("titolo", getString("CambiaCellulareAllerteCortesiaPanel.titolo")));

    cellulare =
        new FdCTextField(
            "cellulare",
            new PropertyModel(datiUtente, "TELEFONO_CELLULARE"),
            CambiaCellulareAllerteCortesiaPanel.this);
    cellulare.getTextField().setRequired(true);
    form.addOrReplace(cellulare);

    form.addOrReplace(creaBtnAvanti(datiUtente, cellulare.getTextField().getValue()));
    form.addOrReplace(creaBtnAnnulla());
  }

  private FdcAjaxButton creaBtnAvanti(Utente datiUtente, String cellulareVecchio) {

    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 6554612856870013039L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti su cambia cel = ");

            try {

              if (LabelFdCUtil.checkIfNotNull(datiUtente)) {

                String cellulareNuovo = datiUtente.getTELEFONOCELLULARE();
                String email = datiUtente.getEMAIL();

                log.debug(
                    "CP cel vecchio = "
                        + cellulareVecchio
                        + " cel nuovo = "
                        + cellulareNuovo
                        + " email = "
                        + email);

                if (cellulareNuovo.equalsIgnoreCase(cellulareVecchio)) {
                  error(
                      "Impossibile aggiornare il numero di cellulare  perchè i numeri coincidono");
                } else {
                  ServiceLocator.getInstance()
                      .getServiziAllerteCortesia()
                      .putWsUpdateUtenteTelefonoCellulare(email, cellulareNuovo);

                  avanti.setVisible(false);
                  annulla.setVisible(false);
                  cellulare.setEnabled(false);

                  DatiVerificaCellulareAllerteCortesia datiVerificaCel =
                      new DatiVerificaCellulareAllerteCortesia();

                  datiVerificaCel.setCellulare(datiUtente.getTELEFONOCELLULARE());
                  datiVerificaCel.setEmail(datiUtente.getEMAIL());

                  setResponsePage(new VerificaCellulareAllerteCortesiaPage(datiVerificaCel));
                }
              }
            } catch (ApiException e) {

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
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              log.error("CP errore cambia cellilare cortesia: " + messaggioDaVisualizzare);

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {

              log.error("BusinessException errore cambia cellilare cortesia:" + e.getMessage(), e);

              error("Impossibile aggiornare il numero di cellulare.");
            }

            target.add(CambiaCellulareAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(CambiaCellulareAllerteCortesiaPanel.this);
          }
        };

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    annulla.setLabel(Model.of(getString("CambiaCellulareAllerteCortesiaPanel.annulla")));

    return annulla;
  }
}
