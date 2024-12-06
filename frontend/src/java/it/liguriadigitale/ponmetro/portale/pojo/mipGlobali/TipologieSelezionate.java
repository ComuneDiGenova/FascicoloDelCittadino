package it.liguriadigitale.ponmetro.portale.pojo.mipGlobali;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import java.io.Serializable;

public class TipologieSelezionate implements Serializable {

  private static final long serialVersionUID = 2985487749864331034L;

  private TipologiaEntrata tipologiaEntrata;
  private TipologiaEntrata comboServizi;

  public TipologieSelezionate(TipologiaEntrata tipologiaEntrata) {
    super();
    this.tipologiaEntrata = tipologiaEntrata;
  }

  public TipologiaEntrata getTipologiaEntrata() {
    return tipologiaEntrata;
  }

  public void setTipologiaEntrata(TipologiaEntrata tipologiaEntrata) {
    this.tipologiaEntrata = tipologiaEntrata;
  }

  public TipologiaEntrata getComboServizi() {
    return comboServizi;
  }

  public void setComboServizi(TipologiaEntrata comboServizi) {
    this.comboServizi = comboServizi;
  }

  @Override
  public String toString() {
    return "TipologieSelezionate [tipologiaEntrata="
        + tipologiaEntrata
        + ", comboServizi="
        + comboServizi
        + "]";
  }
}
