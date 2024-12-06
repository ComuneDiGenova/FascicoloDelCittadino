package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;

public class SoggettiCoinvolti implements Serializable {

  private static final long serialVersionUID = -1615706336403043384L;

  Disabile disabile;
  Tutore tutore;
  Accompagnatore accompagnatore;
  boolean tutoreCoincideConAccompagnatore;

  public SoggettiCoinvolti() {

    disabile = new Disabile();
    tutore = new Tutore();
    accompagnatore = new Accompagnatore();
  }

  public Disabile getDisabile() {
    return disabile;
  }

  public void setDisabile(Disabile disabile) {
    this.disabile = disabile;
  }

  public Tutore getTutore() {
    return tutore;
  }

  public void setTutore(Tutore tutore) {
    this.tutore = tutore;
  }

  public Accompagnatore getAccompagnatore() {
    return accompagnatore;
  }

  public void setAccompagnatore(Accompagnatore accompagnatore) {
    this.accompagnatore = accompagnatore;
  }

  public boolean isTutoreCoincideConAccompagnatore() {
    return tutoreCoincideConAccompagnatore;
  }

  public void setTutoreCoincideConAccompagnatore(boolean tutoreCoincideConAccompagnatore) {
    this.tutoreCoincideConAccompagnatore = tutoreCoincideConAccompagnatore;
  }
}
