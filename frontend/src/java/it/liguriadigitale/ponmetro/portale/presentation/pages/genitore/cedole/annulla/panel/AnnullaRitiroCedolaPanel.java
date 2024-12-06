package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.annulla.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.PrenotazioneCedolaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AnnullamentoCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.markup.html.link.Link;

public class AnnullaRitiroCedolaPanel extends BasePanel {

  private static final long serialVersionUID = 3630106380046949776L;

  public AnnullaRitiroCedolaPanel(String id, Cedola cedola) {
    super(id);
    fillDati(cedola);
  }

  @Override
  public void fillDati(Object dati) {
    Cedola cedola = (Cedola) dati;
    AnnullamentoCedola annullamento = new AnnullamentoCedola();
    annullamento.setCedolaCodice(cedola.getCedolaCodice());
    annullamento.setCodiceFiscaleMinore(cedola.getCodiceFiscaleMinore());
    @SuppressWarnings("unchecked")
    AbstracFrameworkForm<AnnullamentoCedola> form =
        new AbstracFrameworkForm<AnnullamentoCedola>("form", annullamento) {

          private static final long serialVersionUID = 7602157250344140043L;

          @Override
          public void addElementiForm() {
            // TODO Auto-generated method stub

          }

          @Override
          protected void onSubmit() {
            ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
            try {
              AnnullamentoCedola request = getModelObject();
              instance.getApiCedoleLibrarie().prenotazioneAnnullamentoPut(request);

              UtenteServiziRistorazione iscritto =
                  PrenotazioneCedolaPage.trovaIscrittoDaCedola(cedola, getUtente());
              setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
            } catch (BusinessException e) {
              log.error("Errore: ", e);
              error("Impossibile contattare il backend");
            } finally {
              instance.closeConnection();
            }
          }
        };

    Link<Void> linkannulla =
        new Link<Void>("annulla") {

          private static final long serialVersionUID = -3743805429895310539L;

          @Override
          public void onClick() {
            UtenteServiziRistorazione iscritto =
                PrenotazioneCedolaPage.trovaIscrittoDaCedola(cedola, getUtente());
            setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
          }
        };
    form.add(linkannulla);
    add(form);
    createFeedBackPanel();
  }
}
