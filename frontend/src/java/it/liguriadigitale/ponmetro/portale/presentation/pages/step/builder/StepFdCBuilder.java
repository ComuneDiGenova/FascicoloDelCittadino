package it.liguriadigitale.ponmetro.portale.presentation.pages.step.builder;

import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;

public class StepFdCBuilder {

  private String descrizione;
  private Integer indice;

  public static StepFdCBuilder getInstance() {
    return new StepFdCBuilder();
  }

  public StepFdCBuilder addDescrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  public StepFdCBuilder addIndice(Integer indice) {
    this.indice = indice;
    return this;
  }

  public StepFdC build() {
    StepFdC stepFdc = new StepFdC(descrizione, indice);
    return stepFdc;
  }
}
