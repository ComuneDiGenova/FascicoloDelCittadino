package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.comboautocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Stato;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;

public class StatoProvider extends TextChoiceProvider<Stato> {

  private static final long serialVersionUID = -3162071977822459227L;

  private Log log = LogFactory.getLog(getClass());

  // HashMap<String, Stato> mappa;

  Session session;

  public StatoProvider(Session session) {
    super();
    this.session = session;
  }

  @Override
  protected String getDisplayText(Stato choice) {
    return choice.getDescrizione();
  }

  @Override
  protected Object getId(Stato choice) {
    return choice.getCodice();
  }

  @Override
  public void query(String term, int page, Response<Stato> response) {
    response.addAll(getStati(term));
    response.setHasMore(response.size() == 10);
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public Collection<Stato> toChoices(Collection<String> ids) {
    //		ArrayList<Stato> listaStati = new ArrayList<Stato>();
    //		listaStati.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    //		return listaStati;

    HashMap<String, Stato> mappa;

    mappa = (HashMap<String, Stato>) session.getAttribute("mappaStato");
    ArrayList<Stato> lista = new ArrayList<Stato>();
    lista.add(mappa.get(ids.iterator().next()));
    return lista;
  }

  private List<Stato> getStati(String input) {
    List<Stato> lista = new ArrayList<Stato>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getStati(null, input)
              .getResult()
              .getStati();

      HashMap<String, Stato> mappa;
      mappa = new HashMap<String, Stato>();

      for (Stato stato : lista) {
        mappa.put(stato.getCodice(), stato);
      }

      session.setAttribute("mappaStato", mappa);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore get stato APK: " + e.getMessage(), e);
    }
    return lista;
  }
}
