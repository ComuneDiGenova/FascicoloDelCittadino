package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComponenteNucleoIseeRender
    implements IChoiceRenderer<NucleoFamiliareComponenteNucleoInner> {

  private static final long serialVersionUID = -473364639777510653L;

  @Override
  public Object getDisplayValue(final NucleoFamiliareComponenteNucleoInner obj) {
    NucleoFamiliareComponenteNucleoInner c = obj;
    return c.getCognome().concat(" ").concat(c.getNome());
  }

  @Override
  public String getIdValue(final NucleoFamiliareComponenteNucleoInner obj, final int index) {
    NucleoFamiliareComponenteNucleoInner c = obj;
    if ((c != null) && (c.getCodiceFiscale() != null)) return c.getCodiceFiscale();
    else return "";
  }

  @Override
  public NucleoFamiliareComponenteNucleoInner getObject(
      String id, IModel<? extends List<? extends NucleoFamiliareComponenteNucleoInner>> lista) {
    for (NucleoFamiliareComponenteNucleoInner dati : lista.getObject()) {
      if (dati != null && (id.equalsIgnoreCase(dati.getCodiceFiscale()))) {
        return dati;
      }
    }
    return null;
  }
}
