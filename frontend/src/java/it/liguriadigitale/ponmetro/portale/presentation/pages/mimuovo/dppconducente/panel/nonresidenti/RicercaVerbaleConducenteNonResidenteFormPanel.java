package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel.nonresidenti;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.RiassuntoDichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.form.nonresidenti.RicercaVerbaleConducenteNonResidenteForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.util.ArrayList;
import java.util.Arrays;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class RicercaVerbaleConducenteNonResidenteFormPanel extends BasePanel {

  private static final long serialVersionUID = -7736846838495834701L;

  private RicercaVerbaleConducenteNonResidenteForm form = null;

  private RicercaVerbaleConducente ricercaVerbaleConducente;

  private FeedbackPanel feedbackPanel;

  public RicercaVerbaleConducenteNonResidenteFormPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();

    fillDati(new RicercaVerbaleConducente());
  }

  public RicercaVerbaleConducenteNonResidenteFormPanel(
      String id, RicercaVerbaleConducente ricercaVerbaleConducente) {
    super(id);

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();

    this.ricercaVerbaleConducente = ricercaVerbaleConducente;

    fillDati(ricercaVerbaleConducente);
  }

  @Override
  public void fillDati(Object dati) {
    RicercaVerbaleConducente ricercaVerbaleConducente = (RicercaVerbaleConducente) dati;

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    WebMarkupContainer alertToggle = new WebMarkupContainer("alertToggle");
    alertToggle.setVisible(false);

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

    WebMarkupContainer alertDataVerbaleNelFuturo =
        new WebMarkupContainer("alertDataVerbaleNelFuturo");
    alertDataVerbaleNelFuturo.setVisible(false);

    WebMarkupContainer alertDataRilascioPrimaDiNascita =
        new WebMarkupContainer("alertDataRilascioPrimaDiNascita");
    alertDataRilascioPrimaDiNascita.setVisible(false);

    WebMarkupContainer alert150gg = new WebMarkupContainer("alert150gg");
    alert150gg.setVisible(false);

    WebMarkupContainer alertLunghezzaNomeFile = new WebMarkupContainer("alertLunghezzaNomeFile");
    alertLunghezzaNomeFile.setVisible(false);

    Label nomeConducente = new Label("nomeConducente", getUtente().getNome());
    nomeConducente.setVisible(PageUtil.isStringValid(getUtente().getNome()));

    Label cognomeConducente = new Label("cognomeConducente", getUtente().getCognome());
    nomeConducente.setVisible(PageUtil.isStringValid(getUtente().getCognome()));

    Label cfConducente = new Label("cfConducente", getUtente().getCodiceFiscaleOperatore());
    cfConducente.setVisible(PageUtil.isStringValid(getUtente().getCodiceFiscaleOperatore()));

    LocalDateLabel dataNascitaConducente =
        new LocalDateLabel("dataNascitaConducente", getUtente().getDataDiNascita());
    dataNascitaConducente.setVisible(getUtente().getDataDiNascita() != null);

    form =
        new RicercaVerbaleConducenteNonResidenteForm(
            "form", ricercaVerbaleConducente, feedbackPanel, getUtente()) {

          private static final long serialVersionUID = -2981738072915787187L;

          @Override
          protected void onSubmit() {
            ricercaVerbaleConducente.setNomeConducente(getUtente().getNome());
            ricercaVerbaleConducente.setCognomeConducente(getUtente().getCognome());
            ricercaVerbaleConducente.setCodiceFiscaleConducente(
                getUtente().getCodiceFiscaleOperatore());
            ricercaVerbaleConducente.setDataNascitaConducente(getUtente().getDataDiNascita());
            ricercaVerbaleConducente.setSessoConducente(
                CodiceFiscaleValidatorUtil.getSessoFromCf(
                    getModelObject().getCodiceFiscaleConducente()));

            if (getUploadPatente() != null) {
              ricercaVerbaleConducente.setNomeFilePatente(getNomeFilePatente());
              ricercaVerbaleConducente.setByteFilePatente(getByteFilePatente());
            }

            if (checkToggle()
                && checkDatePatente()
                && !checkDataRilascioPrimaDiNascita()
                && checkFilePatenteCaricato()
                && checkEstensioneFilePatente()
                && checkCategoriaPatente()
                && checkEntro150gg()
                && checkLunghezzaNomeFile()) {
              setResponsePage(
                  new RiassuntoDichiarazionePuntiPatenteConducentePage(ricercaVerbaleConducente));
            } else {
              alertPeriodoDate.setVisible(!checkDatePatente());
              alertFileObbligatorio.setVisible(!checkFilePatenteCaricato());
              alertEstensioneFile.setVisible(!checkEstensioneFilePatente());
              alertToggle.setVisible(!checkToggle());
              alertCategoriaPatente.setVisible(!checkCategoriaPatente());

              alertDataRilascioPrimaDiNascita.setVisible(checkDataRilascioPrimaDiNascita());
              alert150gg.setVisible(!checkEntro150gg());

              alertLunghezzaNomeFile.setVisible(!checkLunghezzaNomeFile());

              onError();
            }
          }

          private boolean checkDatePatente() {
            return DateUtil.checkDateRilascioPatente(
                getModelObject().getPatenteRilasciataIl(),
                getModelObject().getPatenteValidaFinoAl());
          }

          private boolean checkEstensioneFilePatente() {
            boolean estensioneOk = false;
            try {
              if (checkFilePatenteCaricato()) {
                String mimeType =
                    FileFdCUtil.getMimeTypeFileUploadAllegato(
                        getModelObject().getByteFilePatente());
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

          private boolean checkFilePatenteCaricato() {
            boolean fileCaricato = false;
            if (getUploadPatente() != null) {
              if (getNomeFilePatente() != null) {
                fileCaricato = true;
              }
            }
            return fileCaricato;
          }

          private boolean checkToggle() {
            return getModelObject().isToggleDichiarazione();
          }

          private boolean checkCategoriaPatente() {
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

          private boolean checkDataRilascioPrimaDiNascita() {
            return DateUtil.checkDataRilascioPrimaDiDataNascita(
                getModelObject().getPatenteRilasciataIl(),
                getModelObject().getDataNascitaConducente());
          }

          private boolean checkEntro150gg() {
            return DateUtil.checkDataVerbaleEntro150ggDaOggi(getModelObject().getDataVerbale());
          }

          private boolean checkLunghezzaNomeFile() {
            boolean lunghezzaOk = true;
            if (LabelFdCUtil.checkIfNotNull(getModelObject().getNomeFilePatente())
                && getModelObject().getNomeFilePatente().length() > 50) {
              lunghezzaOk = false;
            }
            return lunghezzaOk;
          }

          @Override
          protected void onError() {}
        };

    form.addOrReplace(alertToggle);
    form.addOrReplace(alertDate);
    form.addOrReplace(alertPeriodoDate);
    form.addOrReplace(alertDataRilascioNelFuturo);
    form.addOrReplace(alertDataVerbaleNelFuturo);
    form.addOrReplace(alertDataRilascioPrimaDiNascita);
    form.addOrReplace(alertEstensioneFile);
    form.addOrReplace(alertFileObbligatorio);
    form.addOrReplace(alertCategoriaPatente);
    form.addOrReplace(alert150gg);
    form.addOrReplace(alertLunghezzaNomeFile);

    form.addOrReplace(nomeConducente);
    form.addOrReplace(cognomeConducente);
    form.addOrReplace(cfConducente);
    form.addOrReplace(dataNascitaConducente);

    form.setMultiPart(true);
    form.setOutputMarkupId(true);

    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = -4463259816354706895L;

      @Override
      public void onClick() {
        form.clearInput();

        form.getFormInterno().clearInput();
        form.getContainerFileUpload().setVisible(false);

        setResponsePage(new DichiarazionePuntiPatenteConducentePage());
      }
    });
  }

  public RicercaVerbaleConducente getRicercaVerbaleConducente() {
    return ricercaVerbaleConducente;
  }

  public void setRicercaVerbaleConducente(RicercaVerbaleConducente ricercaVerbaleConducente) {
    this.ricercaVerbaleConducente = ricercaVerbaleConducente;
  }

  protected FeedbackPanel createFeedBackDppPanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedback") {

          private static final long serialVersionUID = -8120453622419169692L;

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
