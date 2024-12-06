package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Certificato;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class CertificatoRenderer extends ChoiceRenderer<Certificato> {

  private static final long serialVersionUID = 6867701326111507362L;

  @Override
  public Object getDisplayValue(Certificato obj) {
    Certificato atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return atto.getDescrizione();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(Certificato obj, final int index) {
    Certificato atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return atto.getCodice();
    } else {
      return "";
    }
  }
}
