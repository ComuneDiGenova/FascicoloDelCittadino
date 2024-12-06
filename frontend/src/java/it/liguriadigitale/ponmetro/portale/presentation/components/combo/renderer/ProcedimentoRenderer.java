package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ProcedimentoRenderer extends ChoiceRenderer<TipologiaProcedimento> {

  private static final long serialVersionUID = 1L;

  @Override
  public Object getDisplayValue(final TipologiaProcedimento tipoProcedimento) {
    return tipoProcedimento.getDescrizione();
  }

  @Override
  public String getIdValue(final TipologiaProcedimento obj, final int index) {
    TipologiaProcedimento c = obj;
    return c.getCodice().toString();
  }
}
