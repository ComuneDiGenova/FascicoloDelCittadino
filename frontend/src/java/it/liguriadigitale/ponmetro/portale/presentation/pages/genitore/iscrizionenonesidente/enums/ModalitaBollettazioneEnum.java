package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums;

public enum ModalitaBollettazioneEnum {
  CARTACEA("Cartacea"),
  DEMATERIALIZZATA("Dematerializzata");

  private String modalita;

  private ModalitaBollettazioneEnum(String modalita) {
    this.modalita = modalita;
  }

  public String getModalita() {
    return modalita;
  }
}
