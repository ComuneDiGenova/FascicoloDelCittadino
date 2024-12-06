package it.liguriadigitale.ponmetro.api.pojo.scadenze;

import java.io.Serializable;

public class ScadenzaPassata implements Serializable {

  /** */
  private static final long serialVersionUID = 1792650120073088266L;

  private Long idFCitt;
  private Long idScadenza;

  public Long getIdFCitt() {
    return idFCitt;
  }

  public void setIdFCitt(Long idFCitt) {
    this.idFCitt = idFCitt;
  }

  public Long getIdScadenza() {
    return idScadenza;
  }

  public void setIdScadenza(Long idScadenza) {
    this.idScadenza = idScadenza;
  }
}
