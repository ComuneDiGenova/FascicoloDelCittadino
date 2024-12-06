package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.datigenerali;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertecortesia.model.SERVIZIO;
import it.liguriadigitale.ponmetro.allertecortesia.model.Utente;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServizi;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.CambiaCellulareAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.CambiaEmailAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ModificaUtenteServiziCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.VerificaCellulareAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.VerificaEmailAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.liservizi.ServizioCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util.AllerteCortesiaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class DatiUtenteServiziCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -3239271662505732267L;

  private VerificaServizi verificaServizi = null;

  private ListView<SERVIZIO> listaServiziCortesia;

  private String email;

  private String mailVerificata;
  private String telefonoCellulareVerificato;

  public DatiUtenteServiziCortesiaPanel(String id, DettagliUtente dettagliUtente) {
    super(id, dettagliUtente);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(dettagliUtente);
  }

  @Override
  public void fillDati(Object dati) {

    super.fillDati(dati);

    DettagliUtente dettagliUtente = (DettagliUtente) dati;

    Utente datiUtente = new Utente();
    if (LabelFdCUtil.checkIfNotNull(dettagliUtente)) {
      datiUtente = dettagliUtente.getUTENTE();

      email = datiUtente.getEMAIL();
    }

    form.add(new FdCTitoloPanel("titolo", getString("DatiUtenteServiziCortesiaPanel.titolo")));

    WebMarkupContainer iscritto = new WebMarkupContainer("iscritto");

    FdCButtonBootstrapAjaxLink<Object> btnModificaUtente = creaBtnModificaUtente(datiUtente);
    btnModificaUtente.setVisibilityAllowed(false);
    btnModificaUtente.setLabel(
        Model.of(getString("DatiUtenteServiziCortesiaPanel.btnModificaUtente")));

    iscritto.addOrReplace(btnModificaUtente);
    iscritto.add(
        new FdCTitoloPanel(
            "titoloIscritto", getString("DatiUtenteServiziCortesiaPanel.titoloIscritto")));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "cognome", datiUtente.getCOGNOMERAGIONESOCIALE(), DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>("nome", datiUtente.getNOME(), DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>("email", datiUtente.getEMAIL(), DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "emailVerificata",
            AllerteCortesiaUtil.decodificaBooleanoComeString(datiUtente.getEMAILVERIFICATA()),
            DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(creaBtnVerificaEmail(dettagliUtente));
    iscritto.addOrReplace(creaBtnCambiaEmail(dettagliUtente));

    iscritto.addOrReplace(
        new AmtCardLabel<>("nazione", datiUtente.getNAZIONE(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "provincia", datiUtente.getPROVINCIA(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>("comune", datiUtente.getCOMUNE(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>("cap", datiUtente.getCAP(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "indirizzo", datiUtente.getINDIRIZZO(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "telefonoFisso", datiUtente.getTELEFONOFISSO(), DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "telefonoCellulare",
            datiUtente.getTELEFONOCELLULARE(),
            DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "telefonoCellulareVerificato",
            AllerteCortesiaUtil.decodificaBooleanoComeString(
                datiUtente.getTELEFONOCELLULAREVERIFICATO()),
            DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(creaBtnVerificaCellulare(datiUtente));
    iscritto.addOrReplace(creaBtnCambiaCellulare(datiUtente));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "verificaAnagrafica",
                AllerteCortesiaUtil.decodificaBooleanoComeString(
                    datiUtente.getVERIFICAANAGRAFICA()),
                DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "flagInserimentoEventi",
                AllerteCortesiaUtil.decodificaBooleanoComeString(
                    datiUtente.getFLAGINSERIMENTOEVENTI()),
                DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "flagImportazioneEventi",
                AllerteCortesiaUtil.decodificaBooleanoComeString(
                    datiUtente.getFLAGIMPORTAZIONEFILEEVENTI()),
                DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "flagImmediato",
                AllerteCortesiaUtil.decodificaBooleanoComeString(
                    datiUtente.getFLAGINSERIMENTOEVENTOIMMEDIATO()),
                DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
            "emailSpid",
            AllerteCortesiaUtil.decodificaBooleanoComeString(datiUtente.getEMAILSPID()),
            DatiUtenteServiziCortesiaPanel.this));

    iscritto.addOrReplace(
        new AmtCardLabel<>(
                "dataAggiornamento", datiUtente.getULTAGGPWD(), DatiUtenteServiziCortesiaPanel.this)
            .setVisible(false));

    if (LabelFdCUtil.checkIfNotNull(datiUtente) && PageUtil.isStringValid(datiUtente.getEMAIL())) {
      verificaServizi = popolaListaServizi(datiUtente.getEMAIL());
    }

    List<SERVIZIO> listaServizi = new ArrayList<SERVIZIO>();
    if (LabelFdCUtil.checkIfNotNull(verificaServizi)
        && LabelFdCUtil.checkIfNotNull(verificaServizi.getSERVIZI())
        && LabelFdCUtil.checkIfNotNull(verificaServizi.getSERVIZI().getSERVIZIO())) {
      listaServizi = verificaServizi.getSERVIZI().getSERVIZIO();

      telefonoCellulareVerificato = verificaServizi.getTELEFONOCELLULAREVERIFICATO();
      mailVerificata = verificaServizi.getMAILVERIFICATA();
    }

    listaServiziCortesia =
        new ListView<SERVIZIO>("listaServiziCortesia", listaServizi) {

          private static final long serialVersionUID = -5439900154072189384L;

          @Override
          protected void populateItem(ListItem<SERVIZIO> itemServizio) {
            final SERVIZIO servizio = itemServizio.getModelObject();

            itemServizio.addOrReplace(
                new ServizioCortesiaPanel(
                    "servizioCortesiaPanel",
                    servizio,
                    itemServizio.getIndex(),
                    email,
                    servizio.getID(),
                    telefonoCellulareVerificato,
                    mailVerificata));
          }
        };

    listaServiziCortesia.setOutputMarkupId(true);
    listaServiziCortesia.setOutputMarkupPlaceholderTag(true);
    listaServiziCortesia.setVisible(
        LabelFdCUtil.checkIfNotNull(listaServizi) && !LabelFdCUtil.checkEmptyList(listaServizi));
    iscritto.addOrReplace(listaServiziCortesia);

    iscritto.setVisible(checkUtenteIscritto(dettagliUtente));

    iscritto.setOutputMarkupId(true);
    iscritto.setOutputMarkupPlaceholderTag(true);
    form.addOrReplace(iscritto);
  }

  VerificaServizi popolaListaServizi(String email) {
    VerificaServizi verificaServizi = null;
    try {
      VerificaServiziResponse response =
          ServiceLocator.getInstance().getServiziAllerteCortesia().getWsGetListaServizi(email);

      if (LabelFdCUtil.checkIfNotNull(response)) {
        verificaServizi = response.getFRONTOFFICEPUTUTENTE();
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("Allerte Servizi Cortesia"));
    }
    return verificaServizi;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnVerificaEmail(DettagliUtente dettagliUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnVerificaEmail =
        new FdCButtonBootstrapAjaxLink<Object>("btnVerificaEmail", Type.Primary) {

          private static final long serialVersionUID = 5979717340051075728L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic verifica email = " + dettagliUtente);

            setResponsePage(new VerificaEmailAllerteCortesiaPage(dettagliUtente));
          }
        };

    btnVerificaEmail.setLabel(
        Model.of(getString("DatiUtenteServiziCortesiaPanel.btnVerificaEmail")));

    btnVerificaEmail.setVisible(
        LabelFdCUtil.checkIfNotNull(dettagliUtente)
            && LabelFdCUtil.checkIfNotNull(dettagliUtente.getUTENTE())
            && PageUtil.isStringValid(dettagliUtente.getUTENTE().getEMAILVERIFICATA())
            && dettagliUtente.getUTENTE().getEMAILVERIFICATA().equalsIgnoreCase("N"));

    return btnVerificaEmail;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnCambiaEmail(DettagliUtente dettagliUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnCambiaEmail =
        new FdCButtonBootstrapAjaxLink<Object>("btnCambiaEmail", Type.Primary) {

          private static final long serialVersionUID = 5979717340051075728L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic cambia email = " + dettagliUtente);

            setResponsePage(new CambiaEmailAllerteCortesiaPage(dettagliUtente));
          }
        };

    btnCambiaEmail.setLabel(Model.of(getString("DatiUtenteServiziCortesiaPanel.btnCambiaEmail")));

    return btnCambiaEmail;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnCambiaCellulare(Utente datiUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnCambiaCellulare =
        new FdCButtonBootstrapAjaxLink<Object>("btnCambiaCellulare", Type.Primary) {

          private static final long serialVersionUID = 6995306306472785329L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic cambia cellulare = " + datiUtente);

            setResponsePage(new CambiaCellulareAllerteCortesiaPage(datiUtente));
          }
        };

    btnCambiaCellulare.setLabel(
        Model.of(getString("DatiUtenteServiziCortesiaPanel.btnCambiaCellulare")));

    if (LabelFdCUtil.checkIfNotNull(datiUtente)
        && PageUtil.isStringValid(datiUtente.getTELEFONOCELLULAREVERIFICATO())) {

      btnCambiaCellulare.setVisible(true);
    }

    return btnCambiaCellulare;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnModificaUtente(Utente datiUtente) {
    return new FdCButtonBootstrapAjaxLink<Object>("btnVModificaUtente", Type.Primary) {

      private static final long serialVersionUID = 2176562558198913165L;

      @Override
      public void onClick(AjaxRequestTarget target) {

        log.debug("CP clic creaBtnModificaUtente = " + datiUtente);
        int index = 1;

        DatiCompletiRegistrazioneUtenteAllerteCortesia
            datiCompletiRegistrazioneUtenteAllerteCortesia =
                new DatiCompletiRegistrazioneUtenteAllerteCortesia();

        DatiRegistrazioneAllerteCortesia datiRegistrazioneAllerteCortesia =
            new DatiRegistrazioneAllerteCortesia();
        datiRegistrazioneAllerteCortesia.setNome(datiUtente.getNOME());
        datiRegistrazioneAllerteCortesia.setCognome(datiUtente.getCOGNOMERAGIONESOCIALE());
        datiRegistrazioneAllerteCortesia.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
        datiRegistrazioneAllerteCortesia.setEmail(datiUtente.getEMAIL());
        datiRegistrazioneAllerteCortesia.setTelefonoCellulare(datiUtente.getTELEFONOCELLULARE());
        datiRegistrazioneAllerteCortesia.setTelefonoFisso(datiUtente.getTELEFONOFISSO());
        datiRegistrazioneAllerteCortesia.setIndirizzo(datiUtente.getINDIRIZZO());
        datiRegistrazioneAllerteCortesia.setNazione(datiUtente.getNAZIONE());
        datiRegistrazioneAllerteCortesia.setComune(datiUtente.getCOMUNE());
        datiRegistrazioneAllerteCortesia.setProvincia(datiUtente.getPROVINCIA());
        if (LabelFdCUtil.checkIfNotNull(datiUtente.getCAP())) {
          datiRegistrazioneAllerteCortesia.setCap(String.valueOf(datiUtente.getCAP()));
        }

        datiCompletiRegistrazioneUtenteAllerteCortesia.setDatiRegistrazioneAllerteCortesia(
            datiRegistrazioneAllerteCortesia);

        log.debug("CP creaBtnModificaUtente = " + datiCompletiRegistrazioneUtenteAllerteCortesia);

        CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti =
            new CompoundPropertyModel<>(datiCompletiRegistrazioneUtenteAllerteCortesia);

        setResponsePage(new ModificaUtenteServiziCortesiaPage(index, datiCompleti));
      }
    };
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnVerificaCellulare(Utente datiUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnVerificaCellulare =
        new FdCButtonBootstrapAjaxLink<Object>("btnVerificaCellulare", Type.Primary) {

          private static final long serialVersionUID = 2176562558198913165L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic verifica cellulare = " + datiUtente);

            DatiVerificaCellulareAllerteCortesia datiVerificaCel =
                new DatiVerificaCellulareAllerteCortesia();

            datiVerificaCel.setCellulare(datiUtente.getTELEFONOCELLULARE());
            datiVerificaCel.setEmail(datiUtente.getEMAIL());
            // datiVerificaCel.setReinvia("N");

            setResponsePage(new VerificaCellulareAllerteCortesiaPage(datiVerificaCel));
          }
        };

    if (LabelFdCUtil.checkIfNotNull(datiUtente)
        && PageUtil.isStringValid(datiUtente.getTELEFONOCELLULAREVERIFICATO())) {
      if (datiUtente.getTELEFONOCELLULAREVERIFICATO().equalsIgnoreCase("N")) {
        btnVerificaCellulare.setLabel(
            Model.of(getString("DatiUtenteServiziCortesiaPanel.btnVerificaCellulare")));
        btnVerificaCellulare.setVisible(true);
      } else {
        btnVerificaCellulare.setVisible(false);
      }
    }

    return btnVerificaCellulare;
  }

  private boolean checkUtenteIscritto(DettagliUtente dettagliUtente) {
    return (LabelFdCUtil.checkIfNotNull(dettagliUtente));
  }
}
