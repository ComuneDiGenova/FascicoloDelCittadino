package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.DietaSpeciale;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.GiorniRientroScuola;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.RiassuntoRichiestaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.RichiestaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.RichiestaDietaSpecialeForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.pojo.UtenteServiziRistorazioneDieteSpeciali;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class RichiestaDietaSpecialePanel extends BasePanel {

  private static final long serialVersionUID = 5278050424742161904L;

  private RichiestaDietaSpecialeForm form = null;

  private UtenteServiziRistorazione iscrizione;

  private DietaSpeciale dietaSpeciale;

  private FeedbackPanel feedbackPanel;

  public RichiestaDietaSpecialePanel(UtenteServiziRistorazione iscrizione) {
    super("richiestaDietaSpecialePanel");

    setOutputMarkupId(true);

    this.feedbackPanel = createFeedBackDietePanel();

    this.iscrizione = iscrizione;

    this.dietaSpeciale = new DietaSpeciale();

    List<RientroEnum> listaGiorniRientro = Arrays.asList(RientroEnum.values());
    List<GiorniRientroScuola> lista = new ArrayList<GiorniRientroScuola>();
    for (RientroEnum elem : listaGiorniRientro) {
      GiorniRientroScuola gg = new GiorniRientroScuola();
      gg.setGiornoRientro(elem);
      lista.add(gg);
    }
    dietaSpeciale.setListaGiorniRientri(lista);

    fillDati(iscrizione);
  }

  public RichiestaDietaSpecialePanel(
      UtenteServiziRistorazione iscrizione, DietaSpeciale dietaSpeciale) {
    super("richiestaDietaSpecialePanel");

    setOutputMarkupId(true);

    this.feedbackPanel = createFeedBackDietePanel();

    this.iscrizione = iscrizione;
    this.dietaSpeciale = dietaSpeciale;

    fillDati(iscrizione);
  }

  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscrizione = (UtenteServiziRistorazione) dati;

    WebMarkupContainer alertFile = new WebMarkupContainer("alertFile");
    alertFile.setVisible(false);

    WebMarkupContainer alertScuola = new WebMarkupContainer("alertScuola");
    alertScuola.setVisible(false);

    form =
        new RichiestaDietaSpecialeForm(
            "form", dietaSpeciale, getUtente(), iscrizione, feedbackPanel) {

          private static final long serialVersionUID = -1653101739782060620L;

          @Override
          protected void onSubmit() {
            dietaSpeciale.setNomeRichiedente(getUtente().getNome());
            dietaSpeciale.setCognomeRichiedente(getUtente().getCognome());
            dietaSpeciale.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());

            dietaSpeciale.setNomeIscritto(iscrizione.getNome());
            dietaSpeciale.setCognomeIscritto(iscrizione.getCognome());
            dietaSpeciale.setCfIscritto(iscrizione.getCodiceFiscale());

            dietaSpeciale.setDataRichiesta(LocalDate.now());

            log.debug("CP on submit dieta = " + dietaSpeciale);

            if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.DI_SALUTE)) {
              if (LabelFdCUtil.checkIfNotNull(getFileAttestazioneMedicaUpload())) {
                getModelObject().setByteFileAttestazioneMedica(getByteFileAttestazioneMedica());
                getModelObject().setNomeFileAttestazioneMedica(getNomeFileAttestazioneMedica());
                getModelObject()
                    .setDimensioneFileAttestazioneMedica(getDimensioneFileAttestazioneMedica());
                if (LabelFdCUtil.checkIfNotNull(getByteFileAttestazioneMedica())) {
                  try {
                    getModelObject()
                        .setEstensioneFileAttestazioneMedica(
                            FileFdCUtil.getEstensionFileUploadAllegato(
                                getByteFileAttestazioneMedica()));

                    getModelObject()
                        .setMimeTypeFileAttestazioneMedica(
                            FileFdCUtil.getMimeTypeFileUploadAllegato(
                                getByteFileAttestazioneMedica()));

                  } catch (BusinessException | MagicMatchNotFoundException e) {
                    log.error(
                        "Errore durante mimetype o estensione attestazione medica = "
                            + e.getMessage());
                  }
                }
              }
            }

            if (dietaSpeciale.getTipoDieta().equals(TipoDietaSpecialeEnum.ETICO_RELIGIOSI)) {
              if (checkScuola()) {
                setResponsePage(new RiassuntoRichiestaDietaSpecialePage(iscrizione, dietaSpeciale));
              } else {
                alertScuola.setVisible(!checkScuola());

                onError();
              }
            } else {
              if (checkIfAllegatoAlmenoUnFile() && checkScuola()) {
                setResponsePage(new RiassuntoRichiestaDietaSpecialePage(iscrizione, dietaSpeciale));
              } else {
                alertFile.setVisible(!checkIfAllegatoAlmenoUnFile());
                alertScuola.setVisible(!checkScuola());

                onError();
              }
            }
          }

          @Override
          protected void onError() {
            log.debug("CP on error dieta" + dietaSpeciale);
          }

          private boolean checkIfAllegatoAlmenoUnFile() {
            boolean isFileAllegato =
                LabelFdCUtil.checkIfNotNull(getModelObject().getByteFileAttestazioneMedica());
            return isFileAllegato;
          }

          private boolean checkScuola() {
            boolean isScuolaOk = true;
            String infoAutoComplete = "Non esiste una scuola corrispondente ai caratteri inseriti";
            if (LabelFdCUtil.checkIfNotNull(getModelObject().getScuola())) {
              if (getModelObject().getScuola().equalsIgnoreCase(infoAutoComplete)) {
                isScuolaOk = false;
              }
            }
            return isScuolaOk;
          }
        };

    form.addOrReplace(creaBottoneAnnulla());
    form.setMultiPart(true);

    form.addOrReplace(alertFile);
    form.addOrReplace(alertScuola);

    form.setOutputMarkupId(true);
    addOrReplace(form);
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = -8111420991149641988L;

      @Override
      public void onClick() {
        form.clearInput();
        UtenteServiziRistorazioneDieteSpeciali iscrizioneDiete =
            new UtenteServiziRistorazioneDieteSpeciali();
        iscrizioneDiete.setIscritto(iscrizione);
        setResponsePage(new RichiestaDietaSpecialePage(iscrizioneDiete));
      }
    });
  }

  protected FeedbackPanel createFeedBackDietePanel() {
    NotificationPanel feedback =
        new NotificationPanel("feedback") {

          private static final long serialVersionUID = 8808546061319159503L;

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
