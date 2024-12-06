package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.ritiro.form.RitiroEffettuatoForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.RitiroCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SoggettoAdulto.TipoSoggettoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.markup.html.link.Link;

public class RitiroEffettuatoCedolaPanel extends BasePanel {

  private static final long serialVersionUID = 3630106380046949776L;
  private UtenteServiziRistorazione iscritto;

  public RitiroEffettuatoCedolaPanel(String id, Cedola cedola, UtenteServiziRistorazione iscritto) {
    super(id);
    this.iscritto = iscritto;
    fillDati(cedola);
  }

  @Override
  public void fillDati(Object dati) {
    Cedola cedola = (Cedola) dati;
    RitiroCedola ritiro = new RitiroCedola();
    ritiro.setCedolaCodice(cedola.getCedolaCodice());
    ritiro.setCodiceFiscaleMinore(cedola.getCodiceFiscaleMinore());
    ritiro.setSoggettoRitiro(ricavaSoggettoRitiroDaUtenteCollegato());
    RitiroEffettuatoForm form =
        new RitiroEffettuatoForm("form", ritiro) {

          private static final long serialVersionUID = 7602157250344140043L;

          @Override
          protected void onSubmit() {
            try {
              ServiceLocator.getInstance().getCedoleLibrarie().ritiro(ritiro);
              setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
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
            setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
          }
        };
    form.add(linkannulla);
    add(form);
    createFeedBackPanel();
  }

  private SoggettoAdulto ricavaSoggettoRitiroDaUtenteCollegato() {
    Utente utente = getUtente();
    SoggettoAdulto soggettoRitiro = new SoggettoAdulto();
    soggettoRitiro.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
    soggettoRitiro.setCognome(utente.getCognome());
    soggettoRitiro.setNome(utente.getNome());
    soggettoRitiro.setEmail(utente.getMail());
    soggettoRitiro.setTipoSoggetto(TipoSoggettoEnum.GENITORE);
    return soggettoRitiro;
  }
}
