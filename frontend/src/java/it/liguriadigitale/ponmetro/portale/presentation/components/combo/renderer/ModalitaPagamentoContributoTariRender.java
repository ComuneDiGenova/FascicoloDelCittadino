package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class ModalitaPagamentoContributoTariRender extends ChoiceRenderer<ModalitaPagamentoEnum> {

  private Log log = LogFactory.getLog(getClass());

  @Override
  public Object getDisplayValue(final ModalitaPagamentoEnum obj) {
    ModalitaPagamentoEnum c = obj;

    String decodificaValore = "";
    if (c.value().equalsIgnoreCase("CAS")) {
      decodificaValore = "RITIRO PRESSO TESORERIA";
    }
    if (c.value().equalsIgnoreCase("CAB")) {
      decodificaValore = "ACCREDITO SU CONTO CORRENTE";
    }

    return decodificaValore;
  }

  @Override
  public String getIdValue(final ModalitaPagamentoEnum obj, final int index) {
    ModalitaPagamentoEnum c = obj;
    if (c != null) return String.valueOf(c.value());
    else return "";
  }
}
