package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaIstanza;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzeFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.form.RicercaIstanzaForm;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class MieIstanzeFormPanel extends BasePanel {

  private static final long serialVersionUID = -7717531308715004952L;

  private RicercaIstanzaForm form = null;

  public MieIstanzeFormPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    RicercaIstanza istanzaCercata = (RicercaIstanza) dati;

    form = new RicercaIstanzaForm("form", istanzaCercata, this);
    form.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
    form.add(creaBottoneCerca(istanzaCercata));
    form.add(creaBottoneAnnulla());
    add(form);
  }

  private LaddaAjaxButton creaBottoneCerca(RicercaIstanza istanzaCercata) {
    return new LaddaAjaxButton(
        "button", Model.of(getString("MieIstanzeFormPanel.cerca")), Type.Primary) {

      private static final long serialVersionUID = -6991893213393289607L;

      @Override
      protected void onSubmit(AjaxRequestTarget target) {
        target.add(MieIstanzeFormPanel.this);
        try {

          List<Istanza> listaIstanzeByNumeroAvviso =
              ServiceLocator.getInstance()
                  .getServiziMieIstanze()
                  .getIstanzeByNumeroProtocollo(getUtente(), form.getModelObject());

          boolean ricercaIstanza = true;

          setResponsePage(
              new MieIstanzeFormPage(listaIstanzeByNumeroAvviso, istanzaCercata, ricercaIstanza));

        } catch (BusinessException | ApiException | IOException e) {
          log.error("Errore ");
        }
      }
    };
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 5299548556513047749L;

      @Override
      public void onClick() {
        form.clearInput();
        setResponsePage(new MieIstanzeFormPage());
      }
    });
  }
}
