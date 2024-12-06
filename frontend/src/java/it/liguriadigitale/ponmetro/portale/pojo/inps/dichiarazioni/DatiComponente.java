package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class DatiComponente implements Serializable {

  private static final long serialVersionUID = 437938892564392775L;

  private boolean FlagConvivenzaAnagrafica;
  private String AttivitaSoggetto;
  private Anagrafica Anagrafica;
  private Residenza Residenza;

  public boolean isFlagConvivenzaAnagrafica() {
    return FlagConvivenzaAnagrafica;
  }

  public void setFlagConvivenzaAnagrafica(boolean flagConvivenzaAnagrafica) {
    FlagConvivenzaAnagrafica = flagConvivenzaAnagrafica;
  }

  public String getAttivitaSoggetto() {
    return AttivitaSoggetto;
  }

  public void setAttivitaSoggetto(String attivitaSoggetto) {
    AttivitaSoggetto = attivitaSoggetto;
  }

  public Anagrafica getAnagrafica() {
    return Anagrafica;
  }

  public void setAnagrafica(Anagrafica anagrafica) {
    Anagrafica = anagrafica;
  }

  public Residenza getResidenza() {
    return Residenza;
  }

  public void setResidenza(Residenza residenza) {
    Residenza = residenza;
  }

  @Override
  public String toString() {
    return "DatiComponente [FlagConvivenzaAnagrafica="
        + FlagConvivenzaAnagrafica
        + ", AttivitaSoggetto="
        + AttivitaSoggetto
        + ", Anagrafica="
        + Anagrafica
        + ", Residenza="
        + Residenza
        + "]";
  }
}
