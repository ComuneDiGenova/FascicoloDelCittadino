package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzioneInEvidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoLinkInEvidenza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.RiassuntoDichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.form.RicercaVerbaleConducenteForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.DateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

public class RicercaVerbaleConducenteFormPanel extends BasePanel {

  private static final long serialVersionUID = 5695013920986161890L;

  private RicercaVerbaleConducenteForm form = null;

  private RicercaVerbaleConducente ricercaVerbaleConducente;

  private FeedbackPanel feedbackPanel;

  private WebMarkupContainer alertToggle = new WebMarkupContainer("alertToggle");

  private WebMarkupContainer alertDate = new WebMarkupContainer("alertDate");

  private WebMarkupContainer alertPeriodoDate = new WebMarkupContainer("alertPeriodoDate");

  private WebMarkupContainer alertEstensioneFile = new WebMarkupContainer("alertEstensioneFile");

  private WebMarkupContainer alertFileObbligatorio =
      new WebMarkupContainer("alertFileObbligatorio");

  private WebMarkupContainer alertCategoriaPatente =
      new WebMarkupContainer("alertCategoriaPatente");

  private WebMarkupContainer alertDataRilascioNelFuturo =
      new WebMarkupContainer("alertDataRilascioNelFuturo");

  private WebMarkupContainer alertDataVerbaleNelFuturo =
      new WebMarkupContainer("alertDataVerbaleNelFuturo");

  private WebMarkupContainer alertDataRilascioPrimaDiNascita =
      new WebMarkupContainer("alertDataRilascioPrimaDiNascita");

  private WebMarkupContainer alert150gg = new WebMarkupContainer("alert150gg");

  private WebMarkupContainer alertLunghezzaNomeFile =
      new WebMarkupContainer("alertLunghezzaNomeFile");

  private WebMarkupContainer alertCf = new WebMarkupContainer("alertCf");

  public RicercaVerbaleConducenteFormPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    this.feedbackPanel = createFeedBackDppPanel();

    fillDati(new RicercaVerbaleConducente());
  }

  public RicercaVerbaleConducenteFormPanel(
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

    alertToggle.setVisible(false);

    alertDate.setVisible(false);

    alertPeriodoDate.setVisible(false);

    alertEstensioneFile.setVisible(false);

    alertFileObbligatorio.setVisible(false);

    alertCategoriaPatente.setVisible(false);

    alertDataRilascioNelFuturo.setVisible(false);

    alertDataVerbaleNelFuturo.setVisible(false);

    alertDataRilascioPrimaDiNascita.setVisible(false);

    alert150gg.setVisible(false);

    alertLunghezzaNomeFile.setVisible(false);

    alertCf.setVisible(false);

    //		form = new RicercaVerbaleConducenteForm("form", ricercaVerbaleConducente, feedbackPanel,
    // getUtente()) {
    //
    //			private static final long serialVersionUID = 977649006105456242L;
    //
    //			@Override
    //			protected void onSubmit() {
    //				ricercaVerbaleConducente.setNomeConducente(getUtente().getNome());
    //				ricercaVerbaleConducente.setCognomeConducente(getUtente().getCognome());
    //
    //	ricercaVerbaleConducente.setSessoConducente(getUtente().getDatiCittadinoResidente().getCpvHasSex());
    //
    //	ricercaVerbaleConducente.setCodiceFiscaleConducente(getUtente().getCodiceFiscaleOperatore());
    //				ricercaVerbaleConducente
    //				.setDataNascitaConducente(getUtente().getDatiCittadinoResidente().getCpvDateOfBirth());
    //
    //				ricercaVerbaleConducente.setCittaNascitaConducente(
    //						getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvCity());
    //				ricercaVerbaleConducente.setProvinciaNascitaConducente(
    //						getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvProvince());
    //				ricercaVerbaleConducente.setIndirizzoConducente(
    //						getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
    //				ricercaVerbaleConducente
    //
    //	.setCapConducente(getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
    //				ricercaVerbaleConducente
    //
    //	.setCittaConducente(getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
    //
    //				if (getUploadPatente() != null) {
    //					ricercaVerbaleConducente.setNomeFilePatente(getNomeFilePatente());
    //					ricercaVerbaleConducente.setByteFilePatente(getByteFilePatente());
    //				}
    //
    //				try {
    //					if (checkToggle() && checkDatePatente()
    //							&& !checkDataRilascioPrimaDiNascita() && checkFilePatenteCaricato()
    //							&& checkEstensioneFilePatente() && checkCategoriaPatente() && checkEntro150gg()
    //							&& checkLunghezzaNomeFile() && checkAlertCf()) {
    //						setResponsePage(
    //								new RiassuntoDichiarazionePuntiPatenteConducentePage(ricercaVerbaleConducente));
    //					} else {
    //						alertPeriodoDate.setVisible(!checkDatePatente());
    //						alertFileObbligatorio.setVisible(!checkFilePatenteCaricato());
    //						alertEstensioneFile.setVisible(!checkEstensioneFilePatente());
    //						alertToggle.setVisible(!checkToggle());
    //						alertCategoriaPatente.setVisible(!checkCategoriaPatente());
    //
    //						alertDataRilascioPrimaDiNascita.setVisible(checkDataRilascioPrimaDiNascita());
    //						alert150gg.setVisible(!checkEntro150gg());
    //
    //						alertLunghezzaNomeFile.setVisible(!checkLunghezzaNomeFile());
    //
    //						alertCf.setVisible(!checkAlertCf());
    //
    //						onError();
    //					}
    //				} catch (BusinessException | ApiException | IOException e) {
    //					log.error("Errore durante get verbali in dpp conducente: " + e.getMessage());
    //				}
    //
    //			}
    //
    //			private boolean checkDatePatente() {
    //				return DateUtil.checkDateRilascioPatente(getModelObject().getPatenteRilasciataIl(),
    //						getModelObject().getPatenteValidaFinoAl());
    //			}
    //
    //			private boolean checkEstensioneFilePatente() {
    //				boolean estensioneOk = false;
    //				try {
    //					if (checkFilePatenteCaricato()) {
    //						String mimeType = FileFdCUtil
    //								.getMimeTypeFileUploadAllegato(getModelObject().getByteFilePatente());
    //						if (mimeType.equalsIgnoreCase("application/pdf")
    //								|| mimeType.substring(0, 5).equalsIgnoreCase("image")) {
    //							estensioneOk = true;
    //						}
    //					}
    //				} catch (BusinessException | MagicMatchNotFoundException e) {
    //					log.error("Errore durante mime type patente: " + e.getMessage());
    //				}
    //				return estensioneOk;
    //			}
    //
    //			private boolean checkFilePatenteCaricato() {
    //				boolean fileCaricato = false;
    //				if (getUploadPatente() != null) {
    //					if (getNomeFilePatente() != null) {
    //						fileCaricato = true;
    //					}
    //				}
    //				return fileCaricato;
    //			}
    //
    //			private boolean checkToggle() {
    //				return getModelObject().isToggleDichiarazione();
    //			}
    //
    //			private boolean checkCategoriaPatente() {
    //				ArrayList<String> listaCategoriePatente = new ArrayList<String>(
    //						Arrays.asList("P", "M", "KB", "KD", "AE", "A", "B", "C", "D", "E", "AB", "A1", "BE",
    // "CE", "DE",
    //								"AC", "AD", "AM"));
    //				boolean categoriaPatente = listaCategoriePatente
    //						.contains(getModelObject().getCategoriaPatente().toUpperCase());
    //				return categoriaPatente;
    //			}
    //
    //			private boolean checkDataRilascioPrimaDiNascita() {
    //				return
    // DateUtil.checkDataRilascioPrimaDiDataNascita(getModelObject().getPatenteRilasciataIl(),
    //						getModelObject().getDataNascitaConducente());
    //			}
    //
    //			private boolean checkEntro150gg() {
    //				return DateUtil.checkDataVerbaleEntro150ggDaOggi(getModelObject().getDataVerbale());
    //			}
    //
    //			private boolean checkLunghezzaNomeFile() {
    //				boolean lunghezzaOk = true;
    //				if (LabelFdCUtil.checkIfNotNull(getModelObject().getNomeFilePatente())
    //						&& getModelObject().getNomeFilePatente().length() > 50) {
    //					lunghezzaOk = false;
    //				}
    //				return lunghezzaOk;
    //			}
    //
    //			private boolean checkAlertCf() throws BusinessException, ApiException, IOException {
    //				boolean cf = true;
    //				if(LabelFdCUtil.checkIfNotNull(cf)) {
    //					List<Verbale> listaVerbali =
    // ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());
    //					if(listaVerbali.stream().anyMatch(elem ->
    // elem.getNumeroAvviso().equalsIgnoreCase(getModelObject().getNumeroAvviso()))) {
    //						cf = false;
    //					}
    //				}
    //				return cf;
    //			}
    //
    //			@Override
    //			protected void onError() {
    //
    //			}
    //
    //		};

    form =
        new RicercaVerbaleConducenteForm(
            "form", ricercaVerbaleConducente, feedbackPanel, getUtente());

    form.addOrReplace(creaBottoneProsegui(ricercaVerbaleConducente));

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

    alertCf.addOrReplace(creaLinkVerbali());
    form.addOrReplace(alertCf);

    form.setMultiPart(true);
    form.setOutputMarkupId(true);

    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = -3115940051385436840L;

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

          private static final long serialVersionUID = -4144216848667276421L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    feedback.setOutputMarkupId(true);
    this.add(feedback);
    return feedback;
  }

  private LaddaAjaxLink<Object> creaLinkVerbali() {
    LinkDinamicoLaddaFunzioneInEvidenza<Object> linkDinamico =
        new LinkDinamicoLaddaFunzioneInEvidenza<Object>(
            "mieiVerbali",
            new LinkDinamicoFunzioneData(
                "RicercaVerbaleConducenteFormPanel.verbali",
                "MieiVerbaliPage",
                "RicercaVerbaleConducenteFormPanel.verbali"),
            null,
            RicercaVerbaleConducenteFormPanel.this,
            "color-cyan col-auto icon-vigile-verbale",
            EnumTipoLinkInEvidenza.LADDAAJAXLINK,
            null);

    LaddaAjaxLink<Object> mieiVerbali = linkDinamico.getLaddaLinkImg();

    IconType iconType =
        new IconType("mieiVerbali") {

          private static final long serialVersionUID = 6488221540019581483L;

          @Override
          public String cssClassName() {
            return "icon-vigile-verbale color-cyan";
          }
        };

    mieiVerbali.setIconType(iconType);
    mieiVerbali.setOutputMarkupId(true);
    mieiVerbali.setOutputMarkupPlaceholderTag(true);

    return mieiVerbali;
  }

  private LaddaAjaxButton creaBottoneProsegui(RicercaVerbaleConducente ricercaVerbaleConducente) {
    LaddaAjaxButton prosegui =
        new LaddaAjaxButton("prosegui", Type.Primary) {

          private static final long serialVersionUID = -5183868473975674981L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(RicercaVerbaleConducenteFormPanel.this);

            ricercaVerbaleConducente.setNomeConducente(getUtente().getNome());
            ricercaVerbaleConducente.setCognomeConducente(getUtente().getCognome());
            ricercaVerbaleConducente.setSessoConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasSex());
            ricercaVerbaleConducente.setCodiceFiscaleConducente(
                getUtente().getCodiceFiscaleOperatore());
            ricercaVerbaleConducente.setDataNascitaConducente(
                getUtente().getDatiCittadinoResidente().getCpvDateOfBirth());

            ricercaVerbaleConducente.setCittaNascitaConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvCity());
            ricercaVerbaleConducente.setProvinciaNascitaConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasBirthPlace().getClvProvince());
            ricercaVerbaleConducente.setIndirizzoConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
            ricercaVerbaleConducente.setCapConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
            ricercaVerbaleConducente.setCittaConducente(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());

            if (form.getUploadPatente() != null) {
              ricercaVerbaleConducente.setNomeFilePatente(form.getNomeFilePatente());
              ricercaVerbaleConducente.setByteFilePatente(form.getByteFilePatente());
            }

            try {
              if (checkToggle()
                  && checkDatePatente()
                  && !checkDataRilascioPrimaDiNascita()
                  && checkFilePatenteCaricato()
                  && checkEstensioneFilePatente()
                  && checkCategoriaPatente()
                  && checkEntro150gg()
                  && checkLunghezzaNomeFile()
                  && checkAlertCf()) {
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

                alertCf.setVisible(!checkAlertCf());

                onError();
              }
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante get verbali in dpp conducente: " + e.getMessage());
            }
          }

          private boolean checkDatePatente() {
            return DateUtil.checkDateRilascioPatente(
                ricercaVerbaleConducente.getPatenteRilasciataIl(),
                ricercaVerbaleConducente.getPatenteValidaFinoAl());
          }

          private boolean checkEstensioneFilePatente() {
            boolean estensioneOk = false;
            try {
              if (checkFilePatenteCaricato()) {
                String mimeType =
                    FileFdCUtil.getMimeTypeFileUploadAllegato(
                        ricercaVerbaleConducente.getByteFilePatente());
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
            if (form.getUploadPatente() != null) {
              if (form.getNomeFilePatente() != null) {
                fileCaricato = true;
              }
            }
            return fileCaricato;
          }

          private boolean checkToggle() {
            return ricercaVerbaleConducente.isToggleDichiarazione();
          }

          private boolean checkCategoriaPatente() {
            ArrayList<String> listaCategoriePatente =
                new ArrayList<String>(
                    Arrays.asList(
                        "P", "M", "KB", "KD", "AE", "A", "B", "C", "D", "E", "AB", "A1", "BE", "CE",
                        "DE", "AC", "AD", "AM", "A2"));
            boolean categoriaPatente =
                listaCategoriePatente.contains(
                    ricercaVerbaleConducente.getCategoriaPatente().toUpperCase());
            return categoriaPatente;
          }

          private boolean checkDataRilascioPrimaDiNascita() {
            return DateUtil.checkDataRilascioPrimaDiDataNascita(
                ricercaVerbaleConducente.getPatenteRilasciataIl(),
                ricercaVerbaleConducente.getDataNascitaConducente());
          }

          private boolean checkEntro150gg() {
            return DateUtil.checkDataVerbaleEntro150ggDaOggi(
                ricercaVerbaleConducente.getDataVerbale());
          }

          private boolean checkLunghezzaNomeFile() {
            boolean lunghezzaOk = true;
            if (LabelFdCUtil.checkIfNotNull(ricercaVerbaleConducente.getNomeFilePatente())
                && ricercaVerbaleConducente.getNomeFilePatente().length() > 50) {
              lunghezzaOk = false;
            }
            return lunghezzaOk;
          }

          private boolean checkAlertCf() throws BusinessException, ApiException, IOException {
            boolean cf = true;
            if (LabelFdCUtil.checkIfNotNull(cf)) {
              List<Verbale> listaVerbali =
                  ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());
              if (listaVerbali.stream()
                  .anyMatch(
                      elem ->
                          elem.getNumeroAvviso()
                              .equalsIgnoreCase(ricercaVerbaleConducente.getNumeroAvviso()))) {
                cf = false;
              }
            }
            return cf;
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(RicercaVerbaleConducenteFormPanel.this);

            log.error("CP errore step 1 dpp conducente");
          }
        };
    prosegui.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RicercaVerbaleConducenteFormPanel.prosegui",
                    RicercaVerbaleConducenteFormPanel.this)));

    prosegui.setOutputMarkupId(true);

    return prosegui;
  }
}
