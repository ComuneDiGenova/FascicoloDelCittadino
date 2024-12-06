package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici.widget;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.link.BaseLink;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.PresenzeInMensaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class PresenzeWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -9182203433450880176L;

  private List<UtenteServiziRistorazione> lista;

  public PresenzeWidget(POSIZIONE posizione) {
    super(posizione);
    lista = getUtente().getListaFigli();
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    String strPresenzeDi = getPresenzeDi(lista);
    Label presenzeDi = new Label("presenzeDi", new Model<>(strPresenzeDi));
    presenzeDi.setVisible(strPresenzeDi.trim().length() > 0);

    add(presenzeDi);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  protected void mostraIcona() {

    Link<Void> linkPresenze = new BaseLink("presenze", PresenzeInMensaPage.class);
    Long numeroDiPresenzeInMensa = getNumeroDiPresenzeInMensa(lista);
    Label numeroPresenze = new Label("presenzeLabel", numeroDiPresenzeInMensa);
    linkPresenze.add(numeroPresenze);
    // linkPresenze.setEnabled(numeroDiPresenzeInMensa > 0);

    add(linkPresenze);
  }

  private String getPresenzeDi(List<UtenteServiziRistorazione> lista) {
    StringBuilder messaggio = new StringBuilder("");
    int nFigli = 0;

    for (UtenteServiziRistorazione iscritto : lista) {
      if (ServiceLocator.getInstance().getServiziRistorazione().isIscrizioneAccettata(iscritto)) {
        if (nFigli > 0) {
          messaggio.append(" e ");
        }

        messaggio.append(" " + iscritto.getNome());

        nFigli = nFigli + 1;
      }
    }

    if (nFigli < 1) {
      messaggio = new StringBuilder(" ");
    }

    return messaggio.toString();
  }

  private Long getNumeroDiPresenzeInMensa(List<UtenteServiziRistorazione> lista) {
    try {
      return ServiceLocator.getInstance().getServiziRistorazione().contaNumeroPresenze(lista);
    } catch (BusinessException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    } catch (ApiException e) {
      log.error("Errore nella Response:" + e.getMessage());
      return (0L);
    }
  }

  //	private List<UtenteServiziRistorazione> getListaFigliIscrittiServiziDiRistorazione() {
  //		try {
  //			if (getUtente().getListaFigli() == null) {
  //				getUtente().setListaFigli(
  //						ServiceLocator.getInstance().getServiziRistorazione().trovaIscritti(getUtente()));
  //			}
  //			return getUtente().getListaFigli();3
  //
  //		} catch (BusinessException e) {
  //			log.debug("Errore durante la chiamata delle API", e);
  //			throw new RestartResponseAtInterceptPageException(ErrorPage.class);
  //		} catch (ApiException e) {
  //			log.error("Errore nella Response:" + e.getMessage());
  //			if (e.getResponse().getStatus() > 500) {
  //				log.error("Nessun figlio iscritto");
  //				return new ArrayList<>();
  //			} else {
  //				throw new RestartResponseAtInterceptPageException(ErrorPage.class);
  //			}
  //		}
  //	}

}
