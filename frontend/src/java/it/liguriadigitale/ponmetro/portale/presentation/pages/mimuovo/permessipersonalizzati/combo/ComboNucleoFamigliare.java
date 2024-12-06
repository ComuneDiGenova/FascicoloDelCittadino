package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.ComponenteNucleoFamigliareRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

public class ComboNucleoFamigliare extends DropDownChoice<ComponenteNucleo> {

  private static final long serialVersionUID = -6765798125295641058L;

  boolean isResidente;

  public ComboNucleoFamigliare(String id, Utente utente, IModel<ComponenteNucleo> model) {
    super(id);

    List<ComponenteNucleo> listaFamigliariResidenti = new ArrayList<>();
    List<ComponenteNucleo> listaFamigliari = utente.getNucleoFamiliareAllargato();

    for (Iterator<ComponenteNucleo> iterator = listaFamigliari.iterator(); iterator.hasNext(); ) {
      ComponenteNucleo componenteNucleo = iterator.next();
      if (!componenteNucleo.getCodiceFiscale().equalsIgnoreCase(utente.getCodiceFiscaleOperatore())
          && componenteNucleo.isResidenteComuneGenova())
        listaFamigliariResidenti.add(componenteNucleo);
    }

    setChoices(listaFamigliariResidenti);
    setChoiceRenderer(new ComponenteNucleoFamigliareRenderer());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }
}
