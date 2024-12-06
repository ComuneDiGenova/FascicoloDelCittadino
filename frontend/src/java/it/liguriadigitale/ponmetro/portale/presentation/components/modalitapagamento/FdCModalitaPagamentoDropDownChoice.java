package it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

@SuppressWarnings({"rawtypes", "unchecked"})
public class FdCModalitaPagamentoDropDownChoice<T extends Component>
    extends FdCDropDownChoice<Object> {

  private static final long serialVersionUID = 2272266396673950723L;

  private IChoiceRenderer render;

  public FdCModalitaPagamentoDropDownChoice(String id) {
    super(id);
  }

  public FdCModalitaPagamentoDropDownChoice(
      String id, IModel<Object> model, IChoiceRenderer render, List<Object> lista) {
    super(id);

    setChoices(lista);
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = render;

    setChoiceRenderer(render);
  }
}
