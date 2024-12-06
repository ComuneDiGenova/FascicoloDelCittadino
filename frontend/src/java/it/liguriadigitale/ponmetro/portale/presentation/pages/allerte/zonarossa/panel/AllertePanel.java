package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Civico;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto;
import it.liguriadigitale.ponmetro.allertezonarossa.model.DettagliUtente;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.CivicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ContattoTelefonicoZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.DatiCompletiRegistrazioneUtenteAllerteZonaRossa;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AggiungiCivicoZonaRossaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AggiungiTelefonoZonaRossaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.AllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.DettagliCivicoZonaRossaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.RegistrazioneAllertePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.cancellatelefono.ModaleCancellaTelefonoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.util.AllerteRosseUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class AllertePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -8301533834811236566L;

  private ModaleCancellaTelefonoPanel<String> modaleCancellaTelefono;

  private boolean isDaVerificaPericolositaStrada;
  private FeaturesGeoserver featuresGeoserverDaVerificaPericolosita;

  public AllertePanel(String id, DettagliUtente dettagliUtente) {
    super(id, dettagliUtente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.isDaVerificaPericolositaStrada = false;

    fillDati(dettagliUtente);
  }

  public AllertePanel(
      String id,
      DettagliUtente dettagliUtente,
      FeaturesGeoserver featuresGeoserverDaVerificaPericolosita,
      boolean isDaVerificaPericolositaStrada) {
    super(id, dettagliUtente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.isDaVerificaPericolositaStrada = isDaVerificaPericolositaStrada;
    this.featuresGeoserverDaVerificaPericolosita = featuresGeoserverDaVerificaPericolosita;

    fillDati(dettagliUtente);
  }

  @Override
  public void fillDati(Object dati) {

    super.fillDati(dati);

    DettagliUtente dettagliUtente = (DettagliUtente) dati;

    WebMarkupContainer nonIscritto = new WebMarkupContainer("nonIscritto");
    nonIscritto.addOrReplace(creaBtnRegistrati(isDaVerificaPericolositaStrada));
    nonIscritto.setVisible(!checkUtenteIscritto(dettagliUtente));
    form.addOrReplace(nonIscritto);

    WebMarkupContainer iscritto = new WebMarkupContainer("iscritto");

    if (checkUtenteIscritto(dettagliUtente)) {

      iscritto.addOrReplace(
          new AmtCardLabel<>("nome", dettagliUtente.getNome(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>(
              "codiceFiscale", dettagliUtente.getCodiceFiscale(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>("cognome", dettagliUtente.getCognome(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>("iscrizione", dettagliUtente.getIscrizione(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>(
              "dataRegistrazione", dettagliUtente.getDataRegistrazione(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>("email", dettagliUtente.geteMail(), AllertePanel.this));

      iscritto.addOrReplace(
          new AmtCardLabel<>(
              "vulnerabilitaPersonale",
              dettagliUtente.getVulnerabilitaPersonale(),
              AllertePanel.this));

      iscritto.addOrReplace(creaBtnAggiungiTelefono(dettagliUtente.getId().intValue()));

      List<Contatto> contatti = new ArrayList<>();
      if (LabelFdCUtil.checkIfNotNull(dettagliUtente.getListaContattiTelefonici())) {
        contatti = dettagliUtente.getListaContattiTelefonici();
      }
      ListView<Contatto> listaContatti =
          new ListView<Contatto>("contatti", contatti) {

            private static final long serialVersionUID = -5106791967176514416L;

            @Override
            protected void populateItem(ListItem<Contatto> itemContatto) {
              final Contatto contatto = itemContatto.getModelObject();

              WebMarkupContainer icona = new WebMarkupContainer("iconaTelefono");
              icona.add(AllerteRosseUtil.getCssIconaTelefono(contatto.getTipo()));
              itemContatto.addOrReplace(icona);

              NotEmptyLabel numeroDiTelefono =
                  new NotEmptyLabel("numeroDiTelefono", contatto.getNumero());
              itemContatto.addOrReplace(numeroDiTelefono);

              itemContatto.addOrReplace(
                  new AmtCardLabel<>("idContatto", contatto.getId(), AllertePanel.this));

              itemContatto.addOrReplace(
                  new AmtCardLabel<>("tipoTelefono", contatto.getTipo(), AllertePanel.this));

              itemContatto.addOrReplace(
                  new AmtCardLabel<>("lingua", contatto.getLingua(), AllertePanel.this));

              itemContatto.addOrReplace(
                  new AmtCardLabel<>(
                      "linguaNoItalia", contatto.getLinguaNoItalia(), AllertePanel.this));

              modaleCancellaTelefono =
                  new ModaleCancellaTelefonoPanel<String>("modaleCancellaTelefono", contatto);
              String infoCancellazione = getString("AllertePanel.numeroDiTelefono").concat(" ");
              Label telefonoDaCancellare =
                  new Label("telefonoDaCancellare", infoCancellazione.concat(contatto.getNumero()));
              modaleCancellaTelefono.myAdd(telefonoDaCancellare);
              modaleCancellaTelefono.addOrReplace(creaBtnSi(modaleCancellaTelefono, contatto));
              modaleCancellaTelefono.addOrReplace(creaBtnNo(modaleCancellaTelefono));
              itemContatto.addOrReplace(modaleCancellaTelefono);
              itemContatto.addOrReplace(creaBtnCancellaTelefono(modaleCancellaTelefono, contatto));
            }
          };
      listaContatti.setVisible(
          LabelFdCUtil.checkIfNotNull(contatti) && !LabelFdCUtil.checkEmptyList(contatti));
      iscritto.addOrReplace(listaContatti);

      iscritto.addOrReplace(creaBtnAggiungiCivico(dettagliUtente.getId().intValue()));

      List<Civico> civici = new ArrayList<>();
      if (LabelFdCUtil.checkIfNotNull(dettagliUtente.getListaCiviciRegistrati())) {
        civici = dettagliUtente.getListaCiviciRegistrati();
      }
      ListView<Civico> listaCivici =
          new ListView<Civico>("civici", civici) {

            private static final long serialVersionUID = -5106791967176514416L;

            @Override
            protected void populateItem(ListItem<Civico> itemCivico) {
              final Civico civico = itemCivico.getModelObject();

              NotEmptyLabel indirizzoCompleto =
                  new NotEmptyLabel("indirizzoCompleto", civico.getIndirizzoCompleto());
              itemCivico.addOrReplace(indirizzoCompleto);

              itemCivico.addOrReplace(creaBtnDettagliCivico(civico));
            }
          };
      listaCivici.setVisible(
          LabelFdCUtil.checkIfNotNull(civici) && !LabelFdCUtil.checkEmptyList(civici));
      iscritto.addOrReplace(listaCivici);
    }

    iscritto.setVisible(checkUtenteIscritto(dettagliUtente));
    form.addOrReplace(iscritto);
  }

  private boolean checkUtenteIscritto(DettagliUtente dettagliUtente) {
    return (LabelFdCUtil.checkIfNotNull(dettagliUtente));
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnRegistrati(
      boolean isDaVerificaPericolositaStrada) {
    FdCButtonBootstrapAjaxLink<Object> btnRegistrati =
        new FdCButtonBootstrapAjaxLink<Object>("btnRegistrati", Type.Primary) {

          private static final long serialVersionUID = -102458521658082604L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(AllertePanel.this);

            Integer index = 1;
            DatiCompletiRegistrazioneUtenteAllerteZonaRossa datiCompletiUtenteZonaRossa =
                new DatiCompletiRegistrazioneUtenteAllerteZonaRossa();

            datiCompletiUtenteZonaRossa.getDettagliUtenteZonaRossa().setNome(getUtente().getNome());
            datiCompletiUtenteZonaRossa
                .getDettagliUtenteZonaRossa()
                .setCognome(getUtente().getCognome());
            datiCompletiUtenteZonaRossa
                .getDettagliUtenteZonaRossa()
                .setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
            datiCompletiUtenteZonaRossa
                .getDettagliUtenteZonaRossa()
                .seteMail(getUtente().getMail());
            datiCompletiUtenteZonaRossa
                .getDettagliUtenteZonaRossa()
                .setNumero(getUtente().getMobile());

            if (isDaVerificaPericolositaStrada) {
              datiCompletiUtenteZonaRossa
                  .getCivicoZonaRossa()
                  .setAutocompleteViario(featuresGeoserverDaVerificaPericolosita);
            } else {
              if (getUtente().isResidente()) {
                Residente datiResidenza = getUtente().getDatiCittadinoResidente();

                if (LabelFdCUtil.checkIfNotNull(datiResidenza)
                    && LabelFdCUtil.checkIfNotNull(datiResidenza.getCpvHasAddress())) {

                  FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();
                  featuresGeoserver.setMACHINE_LAST_UPD(
                      datiResidenza.getCpvHasAddress().getClvFullAddress());

                  if (LabelFdCUtil.checkIfNotNull(
                      datiResidenza.getCpvHasAddress().getGenovaOntoStreetNumberCode())) {
                    featuresGeoserver.setCODICE_INDIRIZZO(
                        Integer.parseInt(
                            datiResidenza.getCpvHasAddress().getGenovaOntoStreetNumberCode()));
                    featuresGeoserver.setCOD_STRADA(
                        datiResidenza.getCpvHasAddress().getGenovaOntoOfficialStreetNameCode());
                    featuresGeoserver.setLETTERA(
                        datiResidenza.getCpvHasAddress().getClvStreetNumberExponent());
                    featuresGeoserver.setNUMERO(
                        datiResidenza.getCpvHasAddress().getClvStreetNumber());
                    featuresGeoserver.setCOLORE(
                        datiResidenza.getCpvHasAddress().getClvStreetNumberColor());
                  }

                  datiCompletiUtenteZonaRossa
                      .getCivicoZonaRossa()
                      .setAutocompleteViario(featuresGeoserver);

                  log.debug("CP residente civico = " + datiCompletiUtenteZonaRossa);
                }
              }
            }

            CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti =
                new CompoundPropertyModel<>(datiCompletiUtenteZonaRossa);

            setResponsePage(
                new RegistrazioneAllertePage(
                    datiCompleti,
                    index,
                    isDaVerificaPericolositaStrada,
                    featuresGeoserverDaVerificaPericolosita));
          }
        };
    btnRegistrati.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllertePanel.registrati", AllertePanel.this)));

    IconType icon =
        new IconType("btnRegistrati") {

          private static final long serialVersionUID = -4364612481443783595L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnRegistrati.setIconType(icon);

    return btnRegistrati;
  }

  @SuppressWarnings("rawtypes")
  private FdcAjaxButton creaBtnCancellaTelefono(
      ModaleCancellaTelefonoPanel modale, Contatto contatto) {
    FdcAjaxButton btnCancellaTelefono =
        new FdcAjaxButton("btnCancellaTelefono") {

          private static final long serialVersionUID = 5473873333947426504L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug("CP clic elimina telefono = " + contatto);

            modale.show(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(AllertePanel.this);
          }
        };

    btnCancellaTelefono.setLabel(Model.of(getString("AllertePanel.btnCancellaTelefono")));

    return btnCancellaTelefono;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnDettagliCivico(Civico civico) {
    FdCButtonBootstrapAjaxLink<Object> btnDettagliCivico =
        new FdCButtonBootstrapAjaxLink<Object>("btnDettagliCivico", Type.Primary) {

          private static final long serialVersionUID = -5142564438167181490L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic detagli civico");

            setResponsePage(new DettagliCivicoZonaRossaPage(civico));
          }
        };

    btnDettagliCivico.setLabel(Model.of(getString("AllertePanel.btnDettagliCivico")));

    return btnDettagliCivico;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAggiungiTelefono(int idUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnAggiungiTelefono =
        new FdCButtonBootstrapAjaxLink<Object>("btnAggiungiTelefono", Type.Primary) {

          private static final long serialVersionUID = -1145112675235881116L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic aggiungi tel");

            ContattoTelefonicoZonaRossa contattoTelefonico = new ContattoTelefonicoZonaRossa();
            contattoTelefonico.setIdUtente(idUtente);
            setResponsePage(new AggiungiTelefonoZonaRossaPage(contattoTelefonico));
          }
        };

    btnAggiungiTelefono.setLabel(Model.of(getString("AllertePanel.btnAggiungiTelefono")));

    return btnAggiungiTelefono;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAggiungiCivico(int idUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnAggiungiCivico =
        new FdCButtonBootstrapAjaxLink<Object>("btnAggiungiCivico", Type.Primary) {

          private static final long serialVersionUID = 1055786842968863868L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic aggiungi civico");

            CivicoZonaRossa civicoNuovo = new CivicoZonaRossa();
            civicoNuovo.setIdUtente(idUtente);

            DatiCompletiRegistrazioneUtenteAllerteZonaRossa datiCompletiUtenteZonaRossa =
                new DatiCompletiRegistrazioneUtenteAllerteZonaRossa();
            datiCompletiUtenteZonaRossa.setCivicoZonaRossa(civicoNuovo);

            CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteZonaRossa> datiCompleti =
                new CompoundPropertyModel<>(datiCompletiUtenteZonaRossa);

            setResponsePage(new AggiungiCivicoZonaRossaPage(datiCompleti));
          }
        };

    btnAggiungiCivico.setLabel(Model.of(getString("AllertePanel.btnAggiungiCivico")));

    return btnAggiungiCivico;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnSi(Modal<String> modale, Contatto contatto) {
    FdCButtonBootstrapAjaxLink<Object> btnSi =
        new FdCButtonBootstrapAjaxLink<Object>("btnSi", Type.Primary) {

          /** */
          private static final long serialVersionUID = -3884353558987279394L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            log.debug("CP clic elimina telefono = " + contatto);

            try {
              if (LabelFdCUtil.checkIfNotNull(contatto)
                  && LabelFdCUtil.checkIfNotNull(contatto.getId())) {
                ServiceLocator.getInstance()
                    .getServiziAllerteZonaRossa()
                    .deleteTelefono(contatto.getId().intValue());
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

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {
              log.error("BusinessException gestito durante la chiamata delle API:", e);

              error("Errore cancellazione telefono");
            }

            setResponsePage(new AllertePage());
          }
        };

    btnSi.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllertePanel.si", AllertePanel.this)));

    btnSi.add(new AttributeAppender("style", "min-width: 100px;"));

    return btnSi;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnNo(Modal<String> modale) {
    FdCButtonBootstrapAjaxLink<Object> btnNo =
        new FdCButtonBootstrapAjaxLink<Object>("btnNo", Type.Primary) {

          /** */
          private static final long serialVersionUID = 2813407423322778578L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            modale.close(target);
          }
        };

    btnNo.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("AllertePanel.no", AllertePanel.this)));

    btnNo.add(new AttributeAppender("style", "min-width: 100px;"));

    return btnNo;
  }
}
