package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.form.RicercaVerbaleForm;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class MieiVerbaliFormPanel extends BasePanel {

  private static final long serialVersionUID = 7427334119644024461L;

  private RicercaVerbaleForm form = null;

  public MieiVerbaliFormPanel(String id) {
    super(id);
    log.debug("MieiVerbaliFormPanel INIZIO");
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Verbale verbaleCercato = (Verbale) dati;
    log.debug("MieiVerbaliFormPanel fillDati -- ");
    form = new RicercaVerbaleForm("form", verbaleCercato, this);
    form.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
    form.add(creaBottoneCerca(verbaleCercato));
    form.add(creaBottoneAnnulla());
    add(form);
  }

  private LaddaAjaxButton creaBottoneCerca(Verbale verbaleCercato) {
    return new LaddaAjaxButton(
        "button", Model.of(getString("MieiVerbaliFormPanel.cerca")), Type.Primary) {

      private static final long serialVersionUID = 3035908108701169567L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        target.add(MieiVerbaliFormPanel.this);
        try {

          List<Verbale> listaVerbaliByNumeroProtocollo =
              ServiceLocator.getInstance()
                  .getServiziMieiVerbali()
                  .getRicercaVerbaleByNumeroAvviso(getUtente(), form.getModelObject());

          setResponsePage(new MieiVerbaliFormPage(listaVerbaliByNumeroProtocollo, verbaleCercato));

        } catch (BusinessException | ApiException | IOException e) {
          log.error("Errore ");
        }
      }
    };
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = -7569709793994728772L;

      @Override
      public void onClick() {
        form.clearInput();
        setResponsePage(new MieiVerbaliFormPage());
      }
    });
  }
}
