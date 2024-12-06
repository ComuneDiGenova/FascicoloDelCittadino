package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.GenovaParcheggiPage;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class RinnovaPermessoGeParkPanel extends BasePanel {

  private static final long serialVersionUID = -2963930027900728369L;

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_GENOVA_PARCHEGGI =
      "Errore di connessione alle API Genova Parcheggi";

  private Permit permesso;
  private PermitAllowedAction azione;

  public RinnovaPermessoGeParkPanel(String id, Permit permesso, PermitAllowedAction azione) {
    super(id);

    this.permesso = permesso;
    this.azione = azione;

    fillDati("");

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void fillDati(Object dati) {
    addOrReplace(creaBtnAzioneRinnovaPermesso(permesso, azione));
  }

  private LaddaAjaxLink<Object> creaBtnAzioneRinnovaPermesso(
      Permit permesso, PermitAllowedAction azione) {

    LaddaAjaxLink<Object> btnAzione =
        new LaddaAjaxLink<Object>("btnAzioneLadda", Type.Primary) {

          private static final long serialVersionUID = 5695062771060141898L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            try {
              ServiceLocator.getInstance().getServiziGenovaParcheggi().rinnovaUnPermesso(permesso);
              setResponsePage(new GenovaParcheggiPage());

            } catch (BusinessException e) {
              log.error(ERRORE_API_GENOVA_PARCHEGGI, e);
            } catch (ApiException e) {
              log.error(ERRORE_API_GENOVA_PARCHEGGI, e);
            } catch (IOException e) {
              log.error(ERRORE_API_GENOVA_PARCHEGGI, e);
            }
          }
        };
    btnAzione.setLabel(Model.of(azione.getOperationDescription()));
    return btnAzione;
  }
}
