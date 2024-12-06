package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.form;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.tarieng.ModalitaPagametoTariEngRender;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ModalitaPagamentoDropDownChoice extends FdCDropDownChoice<ModalitaPagamentoEnum> {

  private static final long serialVersionUID = 8561552562915264384L;

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings("unchecked")
  public ModalitaPagamentoDropDownChoice(String id, IModel<ModalitaPagamentoEnum> model) {
    super(id);

    setChoices(getListaModalita());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new ModalitaPagametoTariEngRender();
    setChoiceRenderer(render);
  }

  private static List<ModalitaPagamentoEnum> getListaModalita() {
    return Arrays.asList(ModalitaPagamentoEnum.values());
  }
}
