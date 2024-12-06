package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.certificati;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Atto;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class AttoRenderer extends ChoiceRenderer<Atto> {

  private static final long serialVersionUID = 6867701326111507362L;

  @Override
  public Object getDisplayValue(Atto obj) {
    Atto atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return ripulisciDescrizioneDaCodice(atto).getDescrizione();
    } else {
      return "";
    }
  }

  private Atto ripulisciDescrizioneDaCodice(Atto atto) {
    if (atto.getDescrizione().contains(atto.getCodice())) {
      String nuovaDescrizione = atto.getDescrizione().replace(atto.getCodice(), "");
      String nuovaDescrizione2 = nuovaDescrizione.replace("-", "");
      atto.setDescrizione(nuovaDescrizione2);
    }
    return atto;
  }

  @Override
  public String getIdValue(Atto obj, final int index) {
    Atto atto = obj;
    if (LabelFdCUtil.checkIfNotNull(atto)) {
      return atto.getCodice();
    } else {
      return "";
    }
  }
}
