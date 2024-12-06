package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.common;

public enum StepVariazioniEnum {
  DATI_GENERALI("Dati generali"),
  INDIRIZZO_RESIDENZA("Indirizzo residenza"),
  INDIVIDUI_COLLEGATI("Individui collegati"),
  CARICA_DOCUMENTI("Carica documenti"),
  CONFERMA_DATI("Conferma dati");

  private String step;

  private StepVariazioniEnum(String step) {
    this.step = step;
  }

  public String getStep() {
    return step;
  }
}
