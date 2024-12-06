package it.liguriadigitale.ponmetro.portale.presentation.components.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class PortalePanel extends Panel {

  private static final long serialVersionUID = -1731941606270437535L;
  protected Log log = LogFactory.getLog(this.getClass());

  protected WebMarkupContainer praticheTable;
  protected PagingNavigator unNavigator;
  protected Label labelNrecord;
  protected String idPanel;

  public PortalePanel(String id) {
    super(id);
  }

  protected String getNomeUtenteLoggato() {
    LoginInSession session = (LoginInSession) getSession();
    String username = session.getUtente().getLogin();
    return username;
  }

  protected Utente getUtente() {
    LoginInSession session = (LoginInSession) getSession();
    return session.getUtente();
  }

  protected boolean isNumeric(String anno) {
    try {
      Long.valueOf(anno);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public void mostraElementi(boolean findResult) {
    labelNrecord.setVisible(true);
    unNavigator.setVisible(findResult);
    praticheTable.setVisible(findResult);
  }

  public String getIdPanel() {
    return idPanel;
  }

  public void setIdPanel(String idPanel) {
    this.idPanel = idPanel;
  }

  protected void creaLabel(
      final String strIdWicket, final Object object, final String strNomeMetodo) {
    Label label = new Label(strIdWicket, new PropertyModel<String>(object, strNomeMetodo));
    label.setOutputMarkupId(true);
    this.add(label);
  }

  protected void creaLabelBooleana(final String strIdWicket, final Long valore) {

    Label label = new Label(strIdWicket, valore == 0 ? "NO" : "SI");
    label.setOutputMarkupId(true);
    this.add(label);
  }

  protected void createFeedBackPanel() {
    // NotificationPanel feedback = new NotificationPanel("feedback");
    NotificationPanel feedback =
        new NotificationPanel("feedback", new ContainerFeedbackMessageFilter(this));
    feedback.setOutputMarkupId(true);
    this.add(feedback);
  }
}
