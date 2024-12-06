package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.HelpCzRMDomandePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form.Help;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form.HelpForm;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class HelpCzRMPanel extends BasePanel {

  String url_post_action;
  WebMarkupContainer containerForm;
  WebMarkupContainer containerSuccess;
  Help help;

  private FdcAjaxButton invia;

  public HelpCzRMPanel(String id) {
    super(id);

    createFeedBackPanel();
    fillDati(null);

    setOutputMarkupId(true);
  }

  private static final long serialVersionUID = 1L;

  @Override
  public void fillDati(Object dati) {
    containerForm = new WebMarkupContainer("containerForm");
    containerSuccess = new WebMarkupContainer("containerSuccess");
    containerSuccess.setOutputMarkupId(true);
    containerSuccess.setVisible(false);

    addOrReplace(containerSuccess);

    url_post_action = getUrl("HELP_CZ_RM_ACTION_POST");

    help = new Help();
    help.setCf(getUtente().getCodiceFiscaleOperatore());
    help.setCognome(getUtente().getCognome());
    help.setNome(getUtente().getNome());

    if (LabelFdCUtil.checkIfNotNull(getUtente().getMail())) {
      help.setEmail(getUtente().getMail());
    }

    if (LabelFdCUtil.checkIfNotNull(getUtente().getMobile())) {
      help.setTelefono(getUtente().getMobile());
    }

    //		HelpForm helpForm = new HelpForm("helpForm", help, feedbackPanel) {
    //			private static final long serialVersionUID = 1L;
    //
    //			@Override
    //			protected void onSubmit() {
    //
    //				log.debug("[HelpCzRMPanel] on submit " + help.getServizioAssistenza() + "\n " +
    // help.getSottofascicolo());
    //
    //				Help help = getModelObject();
    //
    //				if (LabelFdCUtil.checkIfNull(help.getServizioAssistenza())) {
    //					error("Il campo Servizio è obbligatorio");
    //					return;
    //				}
    //
    //				try {
    //
    //					help.setPrivacy("true");
    //
    //					ServiceLocator.getInstance().getRichiestaAssistenza().postAssistenza(help);
    //
    //					setResponsePage(new HelpCzRMDomandePage());
    //
    //				} catch (BusinessException | IOException | ApiException e) {
    //					log.error("Errore invia richiesta assistenza = " + e.getMessage());
    //				}
    //
    //			}
    //
    //			@Override
    //			protected void onError() {
    //				log.error("Errore HelpCzrm");
    //				super.onError();
    //			}
    //
    //		};

    HelpForm helpForm = new HelpForm("helpForm", help, feedbackPanel);
    helpForm.addOrReplace(creaBottoneInvia(help));

    containerForm.addOrReplace(helpForm);
    addOrReplace(containerForm);
  }

  //	private boolean sendToAccentureCzRM(Help help) {
  //		String url = getUrl("HELP_CZ_RM_ACTION_POST");
  //
  //		log.info(String.format(
  //				"[HelpCzRMPanel - sendToAccentureCzRM] : Nome: %s Cognome: %s Cf: %s Email: %s
  // Sottofascicolo: %s Servizio: %s "
  //						+ " Oggetto: %s Descrizione: %s Orgid: %s retURL: %s %s: HelpFascicoloCittadino",
  //						help.getNome(), help.getCognome(), help.getCf(), help.getEmail(),
  //						help.getSottofascicolo().getSottoFascicoloValue().toUpperCase(),
  // help.getServizio().getServizioValue(),
  //						help.getOggetto(), help.getDescrizione(), getUrl("HELP_CZ_RM_ORIGID_VALUE"),
  //						getUrl("HELP_CZ_RM_BASE_RET_URL") + "/portale/web/homepage/home",
  // getUrl("HELP_CZ_RM_ID")));
  //
  //		try (CloseableHttpClient http = HttpClients.createDefault()) {
  //			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
  //			qparams.add(new BasicNameValuePair("name", help.getCognome()));
  //			qparams.add(new BasicNameValuePair(getUrl("HELP_CZ_RM_NOME_ID"), help.getNome()));
  //			qparams.add(new BasicNameValuePair(getUrl("HELP_CZ_RM_CF_ID"), help.getCf()));
  //			qparams.add(new BasicNameValuePair("email", help.getEmail()));
  //			qparams.add(new BasicNameValuePair("phone", help.getTelefono()));
  //			qparams.add(new BasicNameValuePair(getUrl("HELP_CZ_RM_SOTTO_FASCICOLO_ID"),
  //					help.getSottofascicolo().getSottoFascicoloValue().toUpperCase()));
  //			qparams.add(
  //					new BasicNameValuePair(getUrl("HELP_CZ_RM_SERVIZIO_ID"),
  // help.getServizio().getServizioValue()));
  //			qparams.add(new BasicNameValuePair("subject", help.getOggetto()));
  //			qparams.add(new BasicNameValuePair("description", help.getDescrizione()));
  //			qparams.add(new BasicNameValuePair("orgid", getUrl("HELP_CZ_RM_ORIGID_VALUE")));
  //			qparams.add(
  //					new BasicNameValuePair("retURL", getUrl("HELP_CZ_RM_BASE_RET_URL") +
  // "/portale/web/homepage/home"));
  //			qparams.add(new BasicNameValuePair(getUrl("HELP_CZ_RM_ID"), "HelpFascicoloCittadino"));
  //
  //			HttpPost post = new HttpPost(url);
  //			post.setEntity(new UrlEncodedFormEntity(qparams, Consts.UTF_8));
  //
  //			try (CloseableHttpResponse response = http.execute(post)) {
  //				log.debug("[helpForm]: " + response.getStatusLine().getStatusCode());
  //				log.debug("[helpForm]: " + response);
  //
  //				return response.getStatusLine().getStatusCode() == 200;
  //			}
  //		} catch (Exception ex) {
  //			log.error(String.format("helpForm: %s", ex.getMessage()), ex);
  //			return false;
  //		}
  //	}

  //	private List<CzrmSottoFascicoli> getSottofascicoli() {
  //		try {
  //			return ServiceLocator.getInstance().getHelpCzRMService().getSottofascicolo();
  //		} catch (ApiException | BusinessException e) {
  //			// TODO Auto-generated catch block
  //			log.debug(String.format("[GetSottofascicoli] Errore Recupero Sotto fascicoli: %s",
  //					e.getCause().getMessage()));
  //			return new ArrayList<CzrmSottoFascicoli>();
  //		}
  //	}

  //	private List<CzrmServizi> getServizi(String sottofascicolo) {
  //		try {
  //			return ServiceLocator.getInstance().getHelpCzRMService().getServizi(sottofascicolo);
  //		} catch (BusinessException e) {
  //			// TODO Auto-generated catch block
  //			log.debug(
  //					String.format("[HelpCzRM - getServizi] Errore caricamento Servizi: %s",
  // e.getCause().getMessage()));
  //			return new ArrayList<CzrmServizi>();
  //		}
  //	}

  public static String getUrl(String chiave) {
    return ServiceLocator.getInstance().getServiziImuEng().getLinkFromResourceDB(chiave);
  }

  private FdcAjaxButton creaBottoneInvia(Help help) {
    invia =
        new FdcAjaxButton("invia") {

          private static final long serialVersionUID = 2574368383046135135L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "[HelpCzRMPanel] on submit "
                    + help.getServizioAssistenza()
                    + "\n "
                    + help.getSottofascicolo());

            if (LabelFdCUtil.checkIfNull(help.getServizioAssistenza())) {
              error("Il campo Servizio è obbligatorio");
              return;
            }

            try {

              help.setPrivacy("true");

              ServiceLocator.getInstance().getRichiestaAssistenza().postAssistenza(help);

              setResponsePage(new HelpCzRMDomandePage());

            } catch (BusinessException | IOException | ApiException e) {
              log.error("Errore invia richiesta assistenza = " + e.getMessage());
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            log.error("CP errore richiesta segnalazione");

            super.onError(target);

            target.add(HelpCzRMPanel.this);
          }
        };

    invia.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HelpCzRMPanel.invia", HelpCzRMPanel.this)));

    return invia;
  }
}
