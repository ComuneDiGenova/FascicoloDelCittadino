package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.dpp.nonresidenti;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.RiassuntoDichiarazionePuntiPatenteProprietarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.form.nonresidenti.DichiarazionePuntiPatenteProprietarioNonResidenteForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class DichiarazionePuntiPatenteNonResidentePanel extends BasePanel {

  private static final long serialVersionUID = -7727659756811374598L;

  private DichiarazionePuntiPatenteProprietarioNonResidenteForm form = null;

  private DettaglioVerbale dettaglioVerbale;

  private Verbale verbale;

  private PuntiPatenteProprietario puntiPatenteProprietario;

  private FeedbackPanel feedbackPanel;

  public DichiarazionePuntiPatenteNonResidentePanel(String id) {
    super(id);
    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatenteNonResidentePanel(
      String id, DettaglioVerbale dettaglioVerbale, Verbale verbale) {

    super(id);

    this.dettaglioVerbale = dettaglioVerbale;
    this.verbale = verbale;

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatenteNonResidentePanel(
      String id,
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale) {
    super(id);

    this.puntiPatenteProprietario = puntiPatenteProprietario;
    this.dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  @Override
  public void fillDati(Object dati) {

    WebMarkupContainer alertDate = new WebMarkupContainer("alertDate");
    alertDate.setVisible(false);

    WebMarkupContainer alertPeriodoDate = new WebMarkupContainer("alertPeriodoDate");
    alertPeriodoDate.setVisible(false);

    WebMarkupContainer alertEstensioneFile = new WebMarkupContainer("alertEstensioneFile");
    alertEstensioneFile.setVisible(false);

    WebMarkupContainer alertFileObbligatorio = new WebMarkupContainer("alertFileObbligatorio");
    alertFileObbligatorio.setVisible(false);

    WebMarkupContainer alertCategoriaPatente = new WebMarkupContainer("alertCategoriaPatente");
    alertCategoriaPatente.setVisible(false);

    WebMarkupContainer alertDataRilascioNelFuturo =
        new WebMarkupContainer("alertDataRilascioNelFuturo");
    alertDataRilascioNelFuturo.setVisible(false);

    WebMarkupContainer alertDataRilascioPrimaDiNascita =
        new WebMarkupContainer("alertDataRilascioPrimaDiNascita");
    alertDataRilascioPrimaDiNascita.setVisible(false);

    WebMarkupContainer alertLunghezzaNomeFile = new WebMarkupContainer("alertLunghezzaNomeFile");
    alertLunghezzaNomeFile.setVisible(false);

    Label numeroAvviso = new Label("numeroAvviso", dettaglioVerbale.getNumeroAvviso());
    addOrReplace(numeroAvviso);

    Label numeroProtocollo = new Label("numeroProtocollo", dettaglioVerbale.getNumeroProtocollo());
    addOrReplace(numeroProtocollo);

    LocalDateLabel dataVerbale =
        new LocalDateLabel("dataVerbale", dettaglioVerbale.getDataAccertamento());
    addOrReplace(dataVerbale);

    Label targa = new Label("targa", dettaglioVerbale.getTarga());
    addOrReplace(targa);

    Label nomeProprietario = new Label("nomeProprietario", getUtente().getNome());
    nomeProprietario.setVisible(PageUtil.isStringValid(getUtente().getNome()));
    addOrReplace(nomeProprietario);

    Label cognomeProprietario = new Label("cognomeProprietario", getUtente().getCognome());
    cognomeProprietario.setVisible(PageUtil.isStringValid(getUtente().getCognome()));
    addOrReplace(cognomeProprietario);

    Label cfProprietario = new Label("cfProprietario", getUtente().getCodiceFiscaleOperatore());
    cfProprietario.setVisible(PageUtil.isStringValid(getUtente().getCodiceFiscaleOperatore()));
    addOrReplace(cfProprietario);

    LocalDateLabel proprietarioNatoIl =
        new LocalDateLabel("proprietarioNatoIl", getUtente().getDataDiNascita());
    proprietarioNatoIl.setVisible(getUtente().getDataDiNascita() != null);
    addOrReplace(proprietarioNatoIl);

    if (puntiPatenteProprietario == null) {
      puntiPatenteProprietario = new PuntiPatenteProprietario();
    }

    form =
        new DichiarazionePuntiPatenteProprietarioNonResidenteForm(
            "form", puntiPatenteProprietario, feedbackPanel, getUtente()) {

          private static final long serialVersionUID = 2181522525467028900L;

          @Override
          protected void onSubmit() {
            puntiPatenteProprietario.setNumeroAvviso(dettaglioVerbale.getNumeroAvviso());
            puntiPatenteProprietario.setNumeroProtocollo(dettaglioVerbale.getNumeroProtocollo());
            puntiPatenteProprietario.setDataVerbale(dettaglioVerbale.getDataAccertamento());
            puntiPatenteProprietario.setTarga(dettaglioVerbale.getTarga());
            puntiPatenteProprietario.setNomeProprietario(getUtente().getNome());
            puntiPatenteProprietario.setCognomeProprietario(getUtente().getCognome());
            puntiPatenteProprietario.setCfProprietario(getUtente().getCodiceFiscaleOperatore());
            puntiPatenteProprietario.setProprietarioNatoIl(getUtente().getDataDiNascita());

            if (getPatenteConducenteUpload() != null) {
              getModelObject().setNomePatenteConducenteUpload(getNomePatenteConducenteUpload());
              getModelObject().setBytePatenteConducenteUpload(getBytePatenteConducenteUpload());
            }

            if (getPatenteProprietarioUpload() != null) {
              getModelObject().setNomePatenteProprietarioUpload(getNomePatenteProprietarioUpload());
              getModelObject().setBytePatenteProprietarioUpload(getBytePatenteProprietarioUpload());
            }

            if (getModelObject().isDichiarazioneProprietario()) {
              if (checkDatePatenteProprietario()
                  && checkFilePatenteProprietarioCaricato()
                  && checkEstensioneFilePatenteProprietario()
                  && checkCategoriaPatenteProprietario()
                  && !checkDataRilascioPrimaDiNascitaProprietario()
                  && checkLunghezzaNomeFileProprietario()) {
                setResponsePage(
                    new RiassuntoDichiarazionePuntiPatenteProprietarioPage(
                        puntiPatenteProprietario, dettaglioVerbale, verbale));
              } else {
                alertPeriodoDate.setVisible(!checkDatePatenteProprietario());
                alertFileObbligatorio.setVisible(!checkFilePatenteProprietarioCaricato());
                alertEstensioneFile.setVisible(!checkEstensioneFilePatenteProprietario());
                alertCategoriaPatente.setVisible(!checkCategoriaPatenteProprietario());
                alertDataRilascioPrimaDiNascita.setVisible(
                    checkDataRilascioPrimaDiNascitaProprietario());
                alertLunghezzaNomeFile.setVisible(!checkLunghezzaNomeFileProprietario());

                getContainerAlert().setVisible(true);

                onError();
              }
            } else {
              if (checkDatePatenteConducente()
                  && checkFilePatenteConducenteCaricato()
                  && checkEstensioneFilePatenteConducente()
                  && checkCategoriaPatenteConducente()
                  && !checkDataRilascioPrimaDiNascitaConducente()
                  && checkLunghezzaNomeFileConducente()) {
                setResponsePage(
                    new RiassuntoDichiarazionePuntiPatenteProprietarioPage(
                        puntiPatenteProprietario, dettaglioVerbale, verbale));
              } else {
                alertPeriodoDate.setVisible(!checkDatePatenteConducente());
                alertFileObbligatorio.setVisible(!checkFilePatenteConducenteCaricato());
                alertEstensioneFile.setVisible(!checkEstensioneFilePatenteConducente());
                alertCategoriaPatente.setVisible(!checkCategoriaPatenteConducente());
                alertDataRilascioPrimaDiNascita.setVisible(
                    checkDataRilascioPrimaDiNascitaConducente());
                alertLunghezzaNomeFile.setVisible(!checkLunghezzaNomeFileConducente());

                getContainerAlert().setVisible(true);

                onError();
              }
            }
          }

          private boolean checkDatePatenteConducente() {
            return DateUtil.checkDateRilascioPatente(
                getModelObject().getPatenteConducenteRilasciataIl(),
                getModelObject().getPatenteConducenteValidaFinoAl());
          }

          private boolean checkDatePatenteProprietario() {
            return DateUtil.checkDateRilascioPatente(
                getModelObject().getPatenteRilasciataIl(),
                getModelObject().getPatenteValidaFinoAl());
          }

          private boolean checkEstensioneFilePatenteConducente() {
            boolean estensioneOk = false;
            try {
              if (checkFilePatenteConducenteCaricato()) {
                String mimeType =
                    FileFdCUtil.getMimeTypeFileUploadAllegato(
                        getModelObject().getBytePatenteConducenteUpload());

                if (mimeType.equalsIgnoreCase("application/pdf")
                    || mimeType.substring(0, 5).equalsIgnoreCase("image")) {
                  estensioneOk = true;
                }
              }
            } catch (BusinessException | MagicMatchNotFoundException e) {
              log.error("Errore durante mime type patente: " + e.getMessage());
            }
            return estensioneOk;
          }

          private boolean checkEstensioneFilePatenteProprietario() {
            boolean estensioneOk = false;
            try {
              if (checkFilePatenteProprietarioCaricato()) {
                String mimeType =
                    FileFdCUtil.getMimeTypeFileUploadAllegato(
                        getModelObject().getBytePatenteProprietarioUpload());

                if (mimeType.equalsIgnoreCase("application/pdf")
                    || mimeType.substring(0, 5).equalsIgnoreCase("image")) {
                  estensioneOk = true;
                }
              }
            } catch (BusinessException | MagicMatchNotFoundException e) {
              log.error("Errore durante mime type patente: " + e.getMessage());
            }
            return estensioneOk;
          }

          private boolean checkFilePatenteConducenteCaricato() {
            boolean fileCaricato = false;
            if (getPatenteConducenteUpload() != null) {
              if (getNomePatenteConducenteUpload() != null) {
                fileCaricato = true;
              }
            }
            return fileCaricato;
          }

          private boolean checkFilePatenteProprietarioCaricato() {
            boolean fileCaricato = false;
            if (getPatenteProprietarioUpload() != null) {
              if (getNomePatenteProprietarioUpload() != null) {
                fileCaricato = true;
              }
            }
            return fileCaricato;
          }

          private boolean checkCategoriaPatenteProprietario() {
            ArrayList<String> listaCategoriePatente =
                new ArrayList<String>(
                    Arrays.asList(
                        "P", "M", "KB", "KD", "AE", "A", "B", "C", "D", "E", "AB", "A1", "BE", "CE",
                        "DE", "AC", "AD", "AM", "A2"));
            boolean categoriaPatente =
                listaCategoriePatente.contains(
                    getModelObject().getCategoriaPatente().toUpperCase());
            return categoriaPatente;
          }

          private boolean checkCategoriaPatenteConducente() {
            ArrayList<String> listaCategoriePatente =
                new ArrayList<String>(
                    Arrays.asList(
                        "P", "M", "KB", "KD", "AE", "A", "B", "C", "D", "E", "AB", "A1", "BE", "CE",
                        "DE", "AC", "AD", "AM", "A2"));
            boolean categoriaPatente =
                listaCategoriePatente.contains(
                    getModelObject().getCategoriaPatenteConducente().toUpperCase());
            return categoriaPatente;
          }

          private boolean checkDataRilascioPrimaDiNascitaProprietario() {
            return DateUtil.checkDataRilascioPrimaDiDataNascita(
                getModelObject().getPatenteRilasciataIl(),
                getModelObject().getProprietarioNatoIl());
          }

          private boolean checkDataRilascioPrimaDiNascitaConducente() {
            LocalDate dataNascitaConducente = getModelObject().getConducenteNatoIl();
            return DateUtil.checkDataRilascioPrimaDiDataNascita(
                getModelObject().getPatenteConducenteRilasciataIl(), dataNascitaConducente);
          }

          private boolean checkLunghezzaNomeFileProprietario() {
            boolean lunghezzaOk = true;
            if (LabelFdCUtil.checkIfNotNull(getModelObject().getNomePatenteProprietarioUpload())
                && getModelObject().getNomePatenteProprietarioUpload().length() > 50) {
              lunghezzaOk = false;
            }
            return lunghezzaOk;
          }

          private boolean checkLunghezzaNomeFileConducente() {
            boolean lunghezzaOk = true;
            if (LabelFdCUtil.checkIfNotNull(getModelObject().getNomePatenteConducenteUpload())
                && getModelObject().getNomePatenteConducenteUpload().length() > 50) {
              lunghezzaOk = false;
            }
            return lunghezzaOk;
          }

          @Override
          protected void onError() {}
        };

    form.getContainerAlert().addOrReplace(alertDate);
    form.getContainerAlert().addOrReplace(alertPeriodoDate);
    form.getContainerAlert().addOrReplace(alertEstensioneFile);
    form.getContainerAlert().addOrReplace(alertFileObbligatorio);
    form.getContainerAlert().addOrReplace(alertCategoriaPatente);
    form.getContainerAlert().addOrReplace(alertLunghezzaNomeFile);

    form.getContainerAlert().addOrReplace(alertDataRilascioNelFuturo);
    form.getContainerAlert().addOrReplace(alertDataRilascioPrimaDiNascita);

    form.setMultiPart(true);
    form.setOutputMarkupId(true);

    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 1441436194612550331L;

      @Override
      public void onClick() {
        form.clearInput();
        /*
         * form.getFormUploadConducente().clearInput();
         * form.getFormUploadProprietario().clearInput();
         * form.getContainerFileUploadConducente().setVisible(false);
         * form.getContainerFileUploadProprietario().setVisible(false);
         *
         * setResponsePage(new DichiarazionePuntiPatentePage(dettaglioVerbale,
         * verbale));
         */
      }
    });
  }

  protected FeedbackPanel createFeedBackDppPanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedback") {

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }
}
