package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.registrazione;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Indirizzo;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Utente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 7459229689313486674L;

  private int index;

  private StepPanel stepPanel;

  private RegistrazioneAllertePanel registrazionePanel;

  private IndirizzoRegistrazioneAllertePanel indirizzoRegistrazionePanel;

  private PericolositaCivicoRegistrazioneAllertePanel pericolositaCivicoRegistrazioneAllertePanel;

  private EsitoInvioDatiAllerteRossePanel esitoRegistrazionePanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  private boolean isDaVerificaPericolositaStrada;

  private FeaturesGeoserver featuresGeoserverDaVerificaPericolosita;

  public DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    super(id, datiCompleti);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(datiCompleti);
  }

  public DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel(
      String id,
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti,
      boolean isDaVerificaPericolositaStrada,
      FeaturesGeoserver featuresGeoserverDaVerificaPericolosita) {
    super(id, datiCompleti);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;
    this.isDaVerificaPericolositaStrada = isDaVerificaPericolositaStrada;

    this.featuresGeoserverDaVerificaPericolosita = featuresGeoserverDaVerificaPericolosita;

    fillDati(datiCompleti);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance().getServiziAllerteZonaRossa().getListaStep());
    form.addOrReplace(stepPanel);

    CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> dettagliUtente =
        (CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa>) dati;

    avanti = creaBottoneAvanti(dettagliUtente);

    indirizzoRegistrazionePanel =
        new IndirizzoRegistrazioneAllertePanel(
            "indirizzoRegistrazionePanel",
            dettagliUtente,
            index,
            avanti,
            isDaVerificaPericolositaStrada);
    indirizzoRegistrazionePanel.setVisible(index == 1);
    form.addOrReplace(indirizzoRegistrazionePanel);

    registrazionePanel = new RegistrazioneAllertePanel("registrazionePanel", dettagliUtente, index);
    registrazionePanel.setVisible(index == 2);
    form.addOrReplace(registrazionePanel);

    pericolositaCivicoRegistrazioneAllertePanel =
        new PericolositaCivicoRegistrazioneAllertePanel(
            "pericolositaCivicoRegistrazioneAllertePanel", dettagliUtente, index, avanti);
    pericolositaCivicoRegistrazioneAllertePanel.setVisible(index == 3);
    form.addOrReplace(pericolositaCivicoRegistrazioneAllertePanel);

    esitoRegistrazionePanel = new EsitoInvioDatiAllerteRossePanel("esitoRegistrazionePanel", index);
    esitoRegistrazionePanel.setVisible(index == 5);
    form.addOrReplace(esitoRegistrazionePanel);

    form.addOrReplace(avanti);
    form.addOrReplace(creaBottoneIndietro(dettagliUtente));
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = -3443191063041159375L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            index = index + 1;

            if (isDaVerificaPericolositaStrada
                && LabelFdCUtil.checkIfNotNull(datiCompleti.getObject())
                && LabelFdCUtil.checkIfNotNull(datiCompleti.getObject().getCivicoZonaRossa())
                && LabelFdCUtil.checkIfNull(
                    datiCompleti.getObject().getCivicoZonaRossa().getAutocompleteViario())) {
              datiCompleti
                  .getObject()
                  .getCivicoZonaRossa()
                  .setAutocompleteViario(featuresGeoserverDaVerificaPericolosita);
            }

            if (index == 5) {
              try {
                // registro utente
                Utente utenteZonaRossa =
                    ServiceLocator.getInstance()
                        .getServiziAllerteZonaRossa()
                        .registraUtente(datiCompleti.getObject());

                if (LabelFdCUtil.checkIfNotNull(utenteZonaRossa)) {
                  BigDecimal idUtenteZonaRossa = utenteZonaRossa.getId();

                  log.debug("CP idUtenteZonaRossa = " + idUtenteZonaRossa);

                  // aggiungo civico
                  datiCompleti
                      .getObject()
                      .getCivicoZonaRossa()
                      .setIdUtente(idUtenteZonaRossa.intValue());
                  Indirizzo civicoAggiunto =
                      ServiceLocator.getInstance()
                          .getServiziAllerteZonaRossa()
                          .aggiungiCivico(datiCompleti.getObject().getCivicoZonaRossa());

                  // aggiungo telefono
                  ContattoTelefonicoZonaRossa contattoTelefonicoZonaRossa =
                      new ContattoTelefonicoZonaRossa();
                  contattoTelefonicoZonaRossa.setIdUtente(idUtenteZonaRossa.intValue());
                  contattoTelefonicoZonaRossa.setNumero(
                      datiCompleti.getObject().getDettagliUtenteZonaRossa().getNumero());
                  contattoTelefonicoZonaRossa.setTipo(
                      datiCompleti.getObject().getDettagliUtenteZonaRossa().getTipo());
                  contattoTelefonicoZonaRossa.setLingua(
                      datiCompleti.getObject().getDettagliUtenteZonaRossa().getLingua());
                  contattoTelefonicoZonaRossa.setLinguaNoItalia(
                      datiCompleti.getObject().getDettagliUtenteZonaRossa().getLinguaNoItalia());
                  ServiceLocator.getInstance()
                      .getServiziAllerteZonaRossa()
                      .aggiungiTelefono(contattoTelefonicoZonaRossa);

                  // aggiungo componente
                  ComponenteNucleoZonaRossa componenteNucleoZonaRossa =
                      new ComponenteNucleoZonaRossa();
                  componenteNucleoZonaRossa.setIdUtente(idUtenteZonaRossa.intValue());
                  componenteNucleoZonaRossa.setIdCivico(civicoAggiunto.getId().intValue());
                  componenteNucleoZonaRossa.setTipo(
                      datiCompleti.getObject().getDettagliUtenteZonaRossa().getTipoPersona());

                  ServiceLocator.getInstance()
                      .getServiziAllerteZonaRossa()
                      .aggiungiComponenteCivico(componenteNucleoZonaRossa);
                }

              } catch (ApiException e) {

                index = index - 1;

                String myMessage = e.getMyMessage();
                String eccezione = "javax.ws.rs.WebApplicationException:";
                String messaggioRicevuto = myMessage;
                if (myMessage.contains(eccezione)) {
                  messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
                } else {
                  messaggioRicevuto =
                      "Servizio momentaneamente non disponibile, riprovare più tardi";
                }
                log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

                Integer indexOf = messaggioRicevuto.indexOf(":");
                String messaggioDaVisualizzare =
                    messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

                log.error("CP errore registrazione utente zona rossa: " + messaggioDaVisualizzare);

                error(messaggioDaVisualizzare);

              } catch (IOException | BusinessException e) {

                log.error(
                    "BusinessException errore registrazione utente zona rossa:" + e.getMessage(),
                    e);

                index = index - 1;

                error("Errore durante registrazione.");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this);

            log.error("CP errore registrazione allerta cortesia ");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.avanti",
                    DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 9009168084240184131L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this);

            form.clearInput();

            setResponsePage(new AllertePage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.annulla",
                    DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro(
      CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti) {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 5431183464484099520L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.indietro",
                    DatiCompletiRegistrazioneUtenteAllerteZonaRossaPanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {
      indirizzoRegistrazionePanel.setVisible(true);
      registrazionePanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(false);

      indietro.setVisible(false);
      avanti.setVisible(true);

      indirizzoRegistrazionePanel.setEnabled(true);

      target.add(this);

    } else if (index == 2) {
      indirizzoRegistrazionePanel.setVisible(false);
      registrazionePanel.setVisible(true);
      esitoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(false);

      indietro.setVisible(true);

      registrazionePanel.setEnabled(true);

      target.add(this);

    } else if (index == 3) {
      registrazionePanel.setVisible(false);
      indirizzoRegistrazionePanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(true);

      indietro.setVisible(true);

      pericolositaCivicoRegistrazioneAllertePanel.setEnabled(true);

      target.add(this);

    } else if (index == 4) {
      registrazionePanel.setVisible(true);
      registrazionePanel.setEnabled(false);

      indirizzoRegistrazionePanel.setVisible(true);
      indirizzoRegistrazionePanel.setEnabled(false);

      pericolositaCivicoRegistrazioneAllertePanel.setVisible(true);
      pericolositaCivicoRegistrazioneAllertePanel.setEnabled(false);

      esitoRegistrazionePanel.setVisible(false);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 5) {
      registrazionePanel.setVisible(false);
      indirizzoRegistrazionePanel.setVisible(false);
      pericolositaCivicoRegistrazioneAllertePanel.setVisible(false);
      esitoRegistrazionePanel.setVisible(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(this);
    }

    return target;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
