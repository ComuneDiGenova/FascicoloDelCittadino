package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.presentation.components.application.session.AbstractLoginInSession;
import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIscrizioneServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public abstract class BasePanel extends FrameworkWebPanel {

  private static final long serialVersionUID = -6833857256064971422L;

  public abstract void fillDati(final Object dati);

  protected NotificationPanel feedbackPanel;

  public BasePanel(final String id) {
    super(id);
  }

  @Override
  public Utente getUtente() {
    AbstractLoginInSession<?> session = (AbstractLoginInSession<?>) this.getSession();
    return (Utente) session.getUtente();
  }

  @Override
  protected void createFeedBackPanel() {
    feedbackPanel =
        new NotificationPanel("feedback") {

          private static final long serialVersionUID = -883302032153540620L;

          /* PANEL senza X per la chiusura */

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }

          //			@Override
          //			public void renderHead(IHeaderResponse response) {
          //				super.renderHead(response);
          //
          //
          //				StringBuilder sb = new StringBuilder();
          //				sb.append("$(document).ready(function() { \r\n"
          //
          //						+ "if ($( \"#notificationDismTopFix\" ).find( \"div\" ).hasClass('alert-danger'))
          // {"
          //						+ "$(\"#notificationDismTopFix\").removeClass(\"success\").addClass(\"error\");"
          //
          //						+ "$( \"#notificationDismTopFix\" ).append( \"<button type='button' class='btn
          // notification-close'><svg class='icon'><use
          // href='/portale/bootstrap-italia/svg/sprites.svg#it-close'
          // xlink:href='/portale/bootstrap-italia/svg/sprites.svg#it-close'></use></svg><span
          // class='sr-only'>Chiudi notifica: Dismissable Top Fix</span></button>\" );"
          //
          //						+ "} else {"
          //						+ "$(\"#notificationDismTopFix\").removeClass(\"dismissable\");"
          //						//+ "$( \"#notificationDismTopFix\" ).append( \"<button type='button' class='btn
          // notification-close'><svg class='icon'><use
          // href='/portale/bootstrap-italia/svg/sprites.svg#it-close'
          // xlink:href='/portale/bootstrap-italia/svg/sprites.svg#it-close'></use></svg><span
          // class='sr-only'>Chiudi notifica: Dismissable Top Fix</span></button>\" );"
          //
          //						+ "}"
          //						+ "$( \"#notificationDismTopFix\" ).find( \"div\" ).css(\"border\", \"hidden\");"
          //
          //
          //
          //						+ "if ($( \"#notificationDismTopFix\" ).find( \"div\" ).hasClass('alert-danger')
          // || $( \"#notificationDismTopFix\" ).find( \"div\" ).hasClass('alert-success') ) {"
          //						+ " notificationShow('notificationDismTopFix');"
          //						+ "}"
          //						+ "} );");
          //
          //
          //
          //				response.render(OnLoadHeaderItem.forScript(sb.toString()));
          //
          //			}
        };
    feedbackPanel.setOutputMarkupId(true);
    this.addOrReplace(feedbackPanel);
  }

  /// Utility getString

  protected String getStringTextNato(Residente residente) {
    String testo = "";
    if (residente != null
        && residente.getCpvHasSex().equalsIgnoreCase(Residente.CpvHasSexEnum.F.value())) {
      testo = getString("Portale.natoA.f");
    } else {
      testo = getString("Portale.natoA.m");
    }
    return testo;
  }

  protected String getStringTextNato(UtenteServiziRistorazione iscritto) {
    String testo = "";
    if (iscritto != null
        && iscritto.getSesso().equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.F.value())) {
      testo = getString("Portale.natoA.f");
    } else {
      testo = getString("Portale.natoA.m");
    }
    return testo;
  }

  protected String getStringTextIscrizione(UtenteServiziRistorazione iscritto) {
    String testo = "";

    if (iscritto != null) {
      if (StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
          .value()
          .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) {
        if (iscritto.getSesso().equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
          testo = getString("Portale.iscritto.m");
        } else {
          testo = getString("Portale.iscritto.f");
        }
      } else {
        if (iscritto.getSesso().equalsIgnoreCase(UtenteServiziRistorazione.SessoEnum.M.value())) {
          testo = getString("Portale.nonIscritto.m");
        } else {
          testo = getString("Portale.nonIscritto.f");
        }
      }
    }

    return testo;
  }
}
