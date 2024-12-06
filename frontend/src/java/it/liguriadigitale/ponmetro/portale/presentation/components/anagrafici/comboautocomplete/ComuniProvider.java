package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.comboautocomplete;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Comune;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetCodiceComuneResponseGenericResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;

public class ComuniProvider extends TextChoiceProvider<Comune> {

  private static final long serialVersionUID = -5996370034423096263L;

  private Log log = LogFactory.getLog(getClass());

  // HashMap<Integer, Comune> mappa;

  Session session;

  public ComuniProvider(Session session) {
    super();

    this.session = session;
  }

  @Override
  protected String getDisplayText(Comune choice) {
    return choice.getDescrizione();
  }

  @Override
  protected Object getId(Comune choice) {
    return choice.getCodice();
  }

  @Override
  public void query(String term, int page, Response<Comune> response) {
    response.addAll(getComuni(term));
    response.setHasMore(response.size() == 10);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Comune> toChoices(Collection<String> ids) {
    //		ArrayList<Comune> listaComuni = new ArrayList<Comune>();
    //		listaComuni.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    //		return listaComuni;

    HashMap<Integer, Comune> mappa;

    mappa = (HashMap<Integer, Comune>) session.getAttribute("mappaComuni");
    ArrayList<Comune> lista = new ArrayList<Comune>();
    lista.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    return lista;
  }

  private List<Comune> getComuni(String input) {
    List<Comune> lista = new ArrayList<Comune>();
    try {
      GetCodiceComuneResponseGenericResponse comuni =
          ServiceLocator.getInstance().getServiziAnagrafici().getTuttiComuniApkappa(null, input);
      if (LabelFdCUtil.checkIfNotNull(comuni)
          && LabelFdCUtil.checkIfNotNull(comuni.getResult())
          && LabelFdCUtil.checkIfNotNull(comuni.getResult().getComuni())) {
        lista = comuni.getResult().getComuni();

        HashMap<Integer, Comune> mappa;
        mappa = new HashMap<Integer, Comune>();

        for (Comune comune : lista) {
          mappa.put(comune.getCodice(), comune);
        }

        session.setAttribute("mappaComuni", mappa);
      }

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore get comune APK: " + e.getMessage(), e);
    }

    return lista;
  }
}
