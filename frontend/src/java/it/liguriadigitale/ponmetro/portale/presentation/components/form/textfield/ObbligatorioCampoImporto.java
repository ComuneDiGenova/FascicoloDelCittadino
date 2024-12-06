package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.CampoObbligatorioBehavior;
import java.math.BigDecimal;

public class ObbligatorioCampoImporto extends CampoImporto {

  private static final long serialVersionUID = -7035022612627520280L;
  private boolean asteriskRendered;

  public ObbligatorioCampoImporto(String id) {
    super(id);
    this.add(new CampoObbligatorioBehavior());
    asteriskRendered = false;
  }

  public ObbligatorioCampoImporto(String id, BigDecimal minimoImporto, BigDecimal massimoImporto) {
    super(id, minimoImporto, massimoImporto);
    this.add(new CampoObbligatorioBehavior());
    asteriskRendered = false;
  }

  public boolean isAsteriskRendered() {
    return asteriskRendered;
  }

  public void setAsteriskRendered(boolean asteriskRendered) {
    this.asteriskRendered = asteriskRendered;
  }
}
