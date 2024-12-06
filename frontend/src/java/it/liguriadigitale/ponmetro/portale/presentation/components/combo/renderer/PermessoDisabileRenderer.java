package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class PermessoDisabileRenderer extends ChoiceRenderer<PermitVerificationResult> {

  private static final long serialVersionUID = 4844500144689266196L;

  @Override
  public Object getDisplayValue(final PermitVerificationResult permitVerificationResult) {
    return permitVerificationResult.getPermitAliasCode()
        + " valido dal "
        + DateUtil.toStringFromLocalDate(permitVerificationResult.getIssuingDate().toLocalDate())
        + " al "
        + DateUtil.toStringFromLocalDate(permitVerificationResult.getValidTo().toLocalDate());
  }

  @Override
  public String getIdValue(final PermitVerificationResult obj, final int index) {
    PermitVerificationResult c = obj;
    return c.getPermitCode();
  }
}
