package it.liguriadigitale.ponmetro.portale.pojo.common;

import java.time.LocalDate;

public class DatiPersonaPercettoreAmmortizSociale {
  private DatiBasePersona persona;
  private String ammortizzatoreSociale;
  private LocalDate percettoreDal;
  private LocalDate percettoreAl;
  private String noteAmmortizzatoreSociale;

  public DatiPersonaPercettoreAmmortizSociale() {
    super();
  }

  public DatiBasePersona getPersona() {
    return persona;
  }

  public void setPersona(DatiBasePersona persona) {
    this.persona = persona;
  }

  public String getAmmortizzatoreSociale() {
    return ammortizzatoreSociale;
  }

  public void setAmmortizzatoreSociale(String ammortizzatoreSociale) {
    this.ammortizzatoreSociale = ammortizzatoreSociale;
  }

  public LocalDate getPercettoreDal() {
    return percettoreDal;
  }

  public void setPercettoreDal(LocalDate percettoreDal) {
    this.percettoreDal = percettoreDal;
  }

  public LocalDate getPercettoreAl() {
    return percettoreAl;
  }

  public void setPercettoreAl(LocalDate percettoreAl) {
    this.percettoreAl = percettoreAl;
  }

  public String getNoteAmmortizzatoreSociale() {
    return noteAmmortizzatoreSociale;
  }

  public void setNoteAmmortizzatoreSociale(String noteAmmortizzatoreSociale) {
    this.noteAmmortizzatoreSociale = noteAmmortizzatoreSociale;
  }

  @Override
  public String toString() {
    return "DatiPersonaPercettoreAmmortizSociale [persona="
        + persona
        + ", ammortizzatoreSociale="
        + ammortizzatoreSociale
        + ", percettoreDal="
        + percettoreDal
        + ", percettoreAl="
        + percettoreAl
        + ", noteAmmortizzatoreSociale="
        + noteAmmortizzatoreSociale
        + "]";
  }
}
