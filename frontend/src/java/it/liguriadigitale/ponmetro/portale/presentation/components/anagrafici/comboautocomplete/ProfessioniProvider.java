package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.comboautocomplete;

import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Response;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.TextChoiceProvider;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Professione;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;

public class ProfessioniProvider extends TextChoiceProvider<Professione> {

  private static final long serialVersionUID = 171066051138237639L;

  @SuppressWarnings("unused")
  private Log log = LogFactory.getLog(getClass());

  //	HashMap<Integer, Professione> mappa;

  Session session;

  public ProfessioniProvider(Session session) {
    super();

    this.session = session;
  }

  @Override
  protected String getDisplayText(Professione choice) {
    return choice.getDescrizioneM();
  }

  @Override
  protected Object getId(Professione choice) {
    return choice.getCodice();
  }

  @Override
  public void query(String term, int page, Response<Professione> response) {
    response.addAll(getProfessioni(term));
    response.setHasMore(response.size() == 10);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Professione> toChoices(Collection<String> ids) {
    //		ArrayList<Professione> listaProfessione = new ArrayList<Professione>();
    //		listaProfessione.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    //		return listaProfessione;

    HashMap<Integer, Professione> mappa;

    mappa = (HashMap<Integer, Professione>) session.getAttribute("mappaProfessioni");
    ArrayList<Professione> lista = new ArrayList<Professione>();
    lista.add(mappa.get(Integer.parseInt(ids.iterator().next())));
    return lista;
  }

  private List<Professione> getProfessioni(String input) {
    List<Professione> listaProfessione =
        ServiceLocator.getInstance().getServiziAnagrafici().getProfessioniFiltrate(input);

    HashMap<Integer, Professione> mappa;
    mappa = new HashMap<Integer, Professione>();

    for (Professione professione : listaProfessione) {
      mappa.put(professione.getCodice(), professione);
    }

    session.setAttribute("mappaProfessioni", mappa);

    return listaProfessione;
  }
}
