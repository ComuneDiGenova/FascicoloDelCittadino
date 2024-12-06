package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.pojo.TipoPagamento;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TipoPagamentoRenderer extends ChoiceRenderer<TipoPagamento> {

  private static final long serialVersionUID = 4844500144689266196L;

  @Override
  public Object getDisplayValue(final TipoPagamento tipoPagamento) {
    return tipoPagamento.getDescTipoPagamento();
  }

  @Override
  public String getIdValue(final TipoPagamento obj, final int index) {
    TipoPagamento c = obj;
    return "" + c.getIdTipoPagamento();
  }
}
