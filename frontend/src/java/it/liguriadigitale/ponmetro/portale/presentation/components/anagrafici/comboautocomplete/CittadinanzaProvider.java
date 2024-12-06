package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.comboautocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Cittadinanza;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;

public class CittadinanzaProvider extends TextChoiceProvider<Cittadinanza> {

  private static final long serialVersionUID = 171066051138237639L;

  private Log log = LogFactory.getLog(getClass());

  // HashMap<String, Cittadinanza> mappa;

  Session session;

  public CittadinanzaProvider(Session session) {
    super();

    this.session = session;
  }

  @Override
  protected String getDisplayText(Cittadinanza choice) {
    return choice.getDescrizione();
  }

  @Override
  protected Object getId(Cittadinanza choice) {
    return choice.getCodice();
  }

  @Override
  public void query(String term, int page, Response<Cittadinanza> response) {
    response.addAll(getCittadinanze(term));
    response.setHasMore(response.size() == 10);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Cittadinanza> toChoices(Collection<String> ids) {
    //		ArrayList<Cittadinanza> listaCittadinanza = new ArrayList<Cittadinanza>();
    //		listaCittadinanza.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    //		return listaCittadinanza;

    HashMap<String, Cittadinanza> mappa;

    mappa = (HashMap<String, Cittadinanza>) session.getAttribute("mappaCittadinanza");
    ArrayList<Cittadinanza> lista = new ArrayList<Cittadinanza>();
    lista.add(mappa.get(ids.iterator().next()));
    return lista;
  }

  private List<Cittadinanza> getCittadinanze(String input) {
    List<Cittadinanza> lista = new ArrayList<Cittadinanza>();
    try {
      lista =
          ServiceLocator.getInstance()
              .getServiziAnagrafici()
              .getCittadinanza(null, input)
              .getResult()
              .getCittadinanze();

      HashMap<String, Cittadinanza> mappa;
      mappa = new HashMap<String, Cittadinanza>();

      for (Cittadinanza cittadinanza : lista) {
        mappa.put(cittadinanza.getCodice(), cittadinanza);
      }

      session.setAttribute("mappaCittadinanza", mappa);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante get cittadinanza APK: " + e.getMessage(), e);
    }
    return lista;
  }
}
