package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio;

import it.liguriadigitale.ponmetro.demografico.model.Residente;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ComponenteNucleoAnagrafeRender implements IChoiceRenderer<Residente> {

  @Override
  public Object getDisplayValue(final Residente obj) {
    Residente c = obj;
    return c.getRdfsLabel();
  }

  @Override
  public String getIdValue(final Residente obj, final int index) {
    Residente c = obj;
    if ((c != null) && (c.getCpvTaxCode() != null)) return c.getCpvTaxCode();
    else return "";
  }

  @Override
  public Residente getObject(String id, IModel<? extends List<? extends Residente>> lista) {
    for (Residente dati : lista.getObject()) {
      if (dati != null && (id.equalsIgnoreCase(dati.getCpvTaxCode()))) {
        return dati;
      }
    }
    return null;
  }
}
