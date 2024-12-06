package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.combo;

import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.TipoPagamentoRenderer;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.seggielettorali.pojo.TipoPagamento;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.IModel;

public class ComboTipoPagamento extends FdCDropDownChoice<TipoPagamento> {

  private static final long serialVersionUID = -6765798125295641058L;

  boolean isResidente;

  public ComboTipoPagamento(String id, IModel<TipoPagamento> model) {
    super(id);

    setChoices(getListaTipoPagamento());
    setChoiceRenderer(new TipoPagamentoRenderer());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private List<TipoPagamento> getListaTipoPagamento() {

    List<TipoPagamento> listaTipoPagamento = new ArrayList<>();

    TipoPagamento tipoPagamento = new TipoPagamento();
    tipoPagamento.setIdTipoPagamento(0);
    tipoPagamento.setDescTipoPagamento("Tramite ritiro presso tesoreria");
    listaTipoPagamento.add(tipoPagamento);

    tipoPagamento = new TipoPagamento();
    tipoPagamento.setIdTipoPagamento(1);
    tipoPagamento.setDescTipoPagamento("Tramite bonifico su IBAN");
    listaTipoPagamento.add(tipoPagamento);

    return listaTipoPagamento;
  }
}
