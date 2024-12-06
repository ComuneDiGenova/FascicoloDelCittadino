package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ModalitaPagametoTariEngRender implements IChoiceRenderer<ModalitaPagamentoEnum> {

  private static final long serialVersionUID = 4051589981444472273L;

  @Override
  public Object getDisplayValue(ModalitaPagamentoEnum obj) {
    ModalitaPagamentoEnum item = obj;
    String descrizione = "";
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(descrizione)) {

      if (item.equals(ModalitaPagamentoEnum.CAB)) {
        descrizione = "Accredito su conto corrente";
      }

      if (item.equals(ModalitaPagamentoEnum.CAS)) {
        descrizione = "Ritiro presso tesoreria";
      }

      return descrizione;
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(ModalitaPagamentoEnum obj, final int index) {
    ModalitaPagamentoEnum item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.value())) {
      return String.valueOf(item.value());
    } else {
      return "";
    }
  }

  @Override
  public ModalitaPagamentoEnum getObject(
      String id, IModel<? extends List<? extends ModalitaPagamentoEnum>> lista) {
    for (ModalitaPagamentoEnum dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.value().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
