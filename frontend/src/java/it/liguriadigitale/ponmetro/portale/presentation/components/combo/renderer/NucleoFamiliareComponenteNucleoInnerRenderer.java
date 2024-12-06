package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class NucleoFamiliareComponenteNucleoInnerRenderer
    extends ChoiceRenderer<NucleoFamiliareComponenteNucleoInner> {

  private static final long serialVersionUID = 2714332705214102998L;

  @Override
  public Object getDisplayValue(final NucleoFamiliareComponenteNucleoInner obj) {
    NucleoFamiliareComponenteNucleoInner c = obj;
    return c.getCognome() + " " + c.getNome();
  }

  @Override
  public String getIdValue(final NucleoFamiliareComponenteNucleoInner obj, final int index) {
    NucleoFamiliareComponenteNucleoInner c = obj;
    if (c != null) return String.valueOf(c.getCodiceFiscale());
    else return "";
  }
}
