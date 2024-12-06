package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.inserimentodomanda;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo.ComboTipoDomanda;
import org.apache.wicket.Application;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class InserimentoDomandaPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  ComboTipoDomanda comboTipoDomanda;

  boolean isRedidente;

  CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel;

  public InserimentoDomandaPanel(String id, boolean isRedidente) {
    super(id);
    this.isRedidente = isRedidente;
    fillDati(id);
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    createFeedBackStep2Panel();
    comboTipoDomanda = new ComboTipoDomanda("tipoDomanda", isRedidente);
    comboTipoDomanda.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("InserimentoDomandaPanel.tipoDomanda", InserimentoDomandaPanel.this)));
    comboTipoDomanda.setRequired(true);
    addOrReplace(comboTipoDomanda);
  }

  protected FeedbackPanel createFeedBackStep2Panel() {

    NotificationPanel feedback =
        new NotificationPanel("feedback2") {

          private static final long serialVersionUID = -8510097378330901001L;

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
