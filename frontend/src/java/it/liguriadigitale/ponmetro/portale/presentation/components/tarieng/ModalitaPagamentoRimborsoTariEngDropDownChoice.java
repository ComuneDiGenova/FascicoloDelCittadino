package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class ModalitaPagamentoRimborsoTariEngDropDownChoice
    extends FdCDropDownChoice<ModalitaPagamentoEnum> {

  private static final long serialVersionUID = 6306860241396552693L;

  public ModalitaPagamentoRimborsoTariEngDropDownChoice(
      String id, IModel<ModalitaPagamentoEnum> model) {
    super(id);

    setChoices(getListaModalita());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  private static List<ModalitaPagamentoEnum> getListaModalita() {
    return Arrays.asList(ModalitaPagamentoEnum.values());
  }
}
