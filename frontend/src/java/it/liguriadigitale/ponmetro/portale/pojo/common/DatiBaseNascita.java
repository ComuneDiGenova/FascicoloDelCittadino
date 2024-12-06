package it.liguriadigitale.ponmetro.portale.pojo.common;

import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.LocalDate;

public class DatiBaseNascita extends DatiBaseLuogo {

  private LocalDate dataNascita;

  public DatiBaseNascita() {
    super();
  }

  public DatiBaseNascita(LocalDate dataNascita) {
    this();
    this.dataNascita = dataNascita;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public int getAnniCompiuti() {
    return LocalDateUtil.calcolaEta(dataNascita);
  }

  @Override
  public String toString() {
    return "DatiBaseNascita [dataNascita="
        + dataNascita
        + ", getAnniCompiuti()="
        + getAnniCompiuti()
        + ", toString()="
        + super.toString()
        + "]";
  }
}
