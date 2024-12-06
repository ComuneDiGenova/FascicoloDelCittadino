package it.liguriadigitale.ponmetro.portale.pojo.common;

public class DatiPersonaIndicazioneCarico {
  private DatiBasePersona persona;
  private Boolean isACaricoAltraPersona;

  public DatiPersonaIndicazioneCarico() {
    super();
  }

  public DatiBasePersona getPersona() {
    return persona;
  }

  public void setPersona(DatiBasePersona persona) {
    this.persona = persona;
  }

  public Boolean getIsACaricoAltraPersona() {
    return isACaricoAltraPersona;
  }

  public void setIsACaricoAltraPersona(Boolean isACaricoAltraPersona) {
    this.isACaricoAltraPersona = isACaricoAltraPersona;
  }

  @Override
  public String toString() {
    return "DatiPersonaIndicazioneCarico [persona="
        + persona
        + ", isACaricoAltraPersona="
        + isACaricoAltraPersona
        + "]";
  }
}
