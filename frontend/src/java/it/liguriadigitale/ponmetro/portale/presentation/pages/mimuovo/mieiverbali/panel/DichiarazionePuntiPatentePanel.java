package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.PuntiPatenteProprietario;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DichiarazionePuntiPatentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.RiassuntoDichiarazionePuntiPatenteProprietarioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.form.DichiarazionePuntiPatenteProprietarioForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
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

public class DichiarazionePuntiPatentePanel extends BasePanel {

  private static final long serialVersionUID = -3188421079650836444L;

  private DichiarazionePuntiPatenteProprietarioForm form = null;

  private DettaglioVerbale dettaglioVerbale;

  private PuntiPatenteProprietario puntiPatenteProprietario;

  private Verbale verbale;

  private FeedbackPanel feedbackPanel;

  public DichiarazionePuntiPatentePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatentePanel(String id, DettaglioVerbale dettaglioVerbale) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatentePanel(
      String id, PuntiPatenteProprietario puntiPatenteProprietario) {
    super(id);

    this.puntiPatenteProprietario = puntiPatenteProprietario;
    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatentePanel(
      String id,
      PuntiPatenteProprietario puntiPatenteProprietario,
      DettaglioVerbale dettaglioVerbale) {
    super(id);

    this.puntiPatenteProprietario = puntiPatenteProprietario;
    this.dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  public DichiarazionePuntiPatentePanel(
      String id, DettaglioVerbale dettaglioVerbale, Verbale verbale) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;
    this.verbale = verbale;

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();
  }

  @Override
  public void fillDati(Object dati) {

    DettaglioVerbale dettaglioVerbale = (DettaglioVerbale) dati;

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
    add(numeroAvviso);

    Label numeroProtocollo = new Label("numeroProtocollo", dettaglioVerbale.getNumeroProtocollo());
    add(numeroProtocollo);

    Label dataVerbale =
        new Label(
            "dataVerbale",
            LocalDateUtil.getDataFormatoEuropeo(dettaglioVerbale.getDataAccertamento()));
    add(dataVerbale);

    Label targa = new Label("targa", dettaglioVerbale.getTarga());
    add(targa);

    Label nomeCognomeProprietario =
        new Label(
            "nomeCognomeProprietario",
            getUtente().getNome().concat(" ").concat(getUtente().getCognome()));
    add(nomeCognomeProprietario);

    Label cfProprietario = new Label("cfProprietario", getUtente().getCodiceFiscaleOperatore());
    add(cfProprietario);

    Label proprietarioNatoA =
        new Label(
            "proprietarioNatoA",
            getUtente()
                .getDatiCittadinoResidente()
                .getCpvHasBirthPlace()
                .getClvCity()
                .concat(" ")
                .concat("(")
                .concat(
                    getUtente()
                        .getDatiCittadinoResidente()
                        .getCpvHasBirthPlace()
                        .getClvProvince()
                        .concat(")")));
    add(proprietarioNatoA);

    Label proprietarioNatoIl =
        new Label(
            "proprietarioNatoIl",
            LocalDateUtil.getDataFormatoEuropeo(getUtente().getDataDiNascita()));
    add(proprietarioNatoIl);

    Label proprietarioResidenteIn =
        new Label(
            "proprietarioResidenteIn",
            getUtente()
                .getDatiCittadinoResidente()
                .getCpvHasAddress()
                .getClvFullAddress()
                .concat(" ")
                .concat(
                    getUtente()
                        .getDatiCittadinoResidente()
                        .getCpvHasAddress()
                        .getClvPostCode()
                        .concat(" ")
                        .concat(
                            getUtente()
                                .getDatiCittadinoResidente()
                                .getCpvHasAddress()
                                .getClvCity())));
    add(proprietarioResidenteIn);

    if (puntiPatenteProprietario == null) {
      puntiPatenteProprietario = new PuntiPatenteProprietario();
    }

    form =
        new DichiarazionePuntiPatenteProprietarioForm(
            "form", puntiPatenteProprietario, feedbackPanel, getUtente()) {

          private static final long serialVersionUID = 7220477670216786036L;

          @Override
          protected void onSubmit() {
            puntiPatenteProprietario.setNumeroAvviso(dettaglioVerbale.getNumeroAvviso());
            puntiPatenteProprietario.setNumeroProtocollo(dettaglioVerbale.getNumeroProtocollo());
            puntiPatenteProprietario.setDataVerbale(dettaglioVerbale.getDataAccertamento());
            puntiPatenteProprietario.setTarga(dettaglioVerbale.getTarga());
            puntiPatenteProprietario.setNomeProprietario(getUtente().getNome());
            puntiPatenteProprietario.setCognomeProprietario(getUtente().getCognome());
            puntiPatenteProprietario.setCfProprietario(getUtente().getCodiceFiscaleOperatore());
            puntiPatenteProprietario.setProprietarioNatoA(
                getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvCity());
            puntiPatenteProprietario.setProprietarioNatoAProvincia(
                getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvProvince());
            puntiPatenteProprietario.setProprietarioNatoIl(getUtente().getDataDiNascita());

            puntiPatenteProprietario.setProprietarioResidenteIn(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
            puntiPatenteProprietario.setProprietarioResidenteCitta(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
            puntiPatenteProprietario.setProprietarioResidenteCap(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());

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
                alertLunghezzaNomeFile.setVisible(!checkLunghezzaNomeFileConducente());

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
                getModelObject().getPatenteRilasciataIl(), getUtente().getDataDiNascita());
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

      private static final long serialVersionUID = -4296547604859933619L;

      @Override
      public void onClick() {
        form.clearInput();
        form.getFormUploadConducente().clearInput();
        form.getFormUploadProprietario().clearInput();
        form.getContainerFileUploadConducente().setVisible(false);
        form.getContainerFileUploadProprietario().setVisible(false);

        setResponsePage(new DichiarazionePuntiPatentePage(dettaglioVerbale, verbale));
      }
    });
  }

  protected FeedbackPanel createFeedBackDppPanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedback") {

          private static final long serialVersionUID = -6796700967503575438L;

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
