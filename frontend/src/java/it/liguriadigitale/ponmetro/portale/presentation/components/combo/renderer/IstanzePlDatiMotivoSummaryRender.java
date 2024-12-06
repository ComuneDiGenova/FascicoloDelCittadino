package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class IstanzePlDatiMotivoSummaryRender extends ChoiceRenderer<DatiMotivoSummary> {

  private static final long serialVersionUID = -1L;

  @Override
  public Object getDisplayValue(DatiMotivoSummary obj) {
    DatiMotivoSummary catiMotivoSummary = obj;
    if (LabelFdCUtil.checkIfNotNull(catiMotivoSummary)) {
      return catiMotivoSummary.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(DatiMotivoSummary obj, final int index) {
    DatiMotivoSummary datiMotivoSummary = obj;
    if (LabelFdCUtil.checkIfNotNull(datiMotivoSummary)) {
      return datiMotivoSummary.getCodice();
    } else {
      return "";
    }
  }
}
