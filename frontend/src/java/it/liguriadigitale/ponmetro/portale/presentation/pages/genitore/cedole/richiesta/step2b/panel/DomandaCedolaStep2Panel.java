package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step2b.form.DomandaCedolaStep2Form;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import org.apache.wicket.markup.html.link.Link;

public class DomandaCedolaStep2Panel extends BasePanel {

  private static final long serialVersionUID = 3630106380046949776L;

  private CedoleMinore cedolaMinore;

  public DomandaCedolaStep2Panel(
      String id, CedoleMinore cedolaMinore, DomandaCedola domandaCedola) {
    super(id);
    this.cedolaMinore = cedolaMinore;
    fillDati(domandaCedola);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    DomandaCedola domanda = (DomandaCedola) dati;
    DomandaCedolaStep2Form form =
        new DomandaCedolaStep2Form("form", domanda) {

          private static final long serialVersionUID = 7602157250344140043L;

          @Override
          protected void onSubmit() {
            try {
              ServiceLocator.getInstance()
                  .getCedoleLibrarie()
                  .presentaDomanda(domanda, getUtente());
              setResponsePage(new CedoleLibrarieConPrivacyPage(cedolaMinore.getMinore()));
            } catch (BusinessException e) {
              log.error("Errore: ", e);
              error("Impossibile contattare il backend");
            } catch (ApiException e) {
              log.error("Errore: ", e);
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              error("Errore: " + messaggioRicevuto);
            }
          }
        };

    Link<Void> linkannulla =
        new Link<Void>("annulla") {

          private static final long serialVersionUID = -3743805429895310539L;

          @Override
          public void onClick() {
            setResponsePage(new CedoleLibrarieConPrivacyPage(cedolaMinore.getMinore()));
          }
        };
    form.add(linkannulla);
    add(form);
  }
}
