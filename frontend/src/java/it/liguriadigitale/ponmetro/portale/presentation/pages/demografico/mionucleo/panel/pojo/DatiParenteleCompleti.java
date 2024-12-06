package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.pojo;

import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SorgenteDatiNucleoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import java.io.Serializable;

public class DatiParenteleCompleti implements Serializable {

  private static final long serialVersionUID = 2200361882640412401L;

  private ComponenteNucleo datiFamigliare;

  private ItemRelazioneParentale parentelaDaDemografico;

  private ItemRelazioneParentale relazioneConCittadinoLoggato;

  private SorgenteDatiNucleoEnum sorgente;

  private boolean figlioAutodichiarato;

  public ItemRelazioneParentale getParentelaDaDemografico() {
    return parentelaDaDemografico;
  }

  public void setParentelaDaDemografico(ItemRelazioneParentale parentelaDaDemografico) {
    this.parentelaDaDemografico = parentelaDaDemografico;
  }

  public ItemRelazioneParentale getRelazioneConCittadinoLoggato() {
    return relazioneConCittadinoLoggato;
  }

  public void setRelazioneConCittadinoLoggato(ItemRelazioneParentale relazioneConCittadinoLoggato) {
    this.relazioneConCittadinoLoggato = relazioneConCittadinoLoggato;
  }

  public SorgenteDatiNucleoEnum getSorgente() {
    return sorgente;
  }

  public void setSorgente(SorgenteDatiNucleoEnum sorgente) {
    this.sorgente = sorgente;
  }

  public boolean isFiglioAutodichiarato() {
    return figlioAutodichiarato;
  }

  public void setFiglioAutodichiarato(boolean figlioAutodichiarato) {
    this.figlioAutodichiarato = figlioAutodichiarato;
  }

  public ComponenteNucleo getDatiFamigliare() {
    return datiFamigliare;
  }

  public void setDatiFamigliare(ComponenteNucleo datiFamigliare) {
    this.datiFamigliare = datiFamigliare;
  }

  @Override
  public String toString() {
    return "DatiParenteleCompleti [datiFamigliare="
        + datiFamigliare
        + ", parentelaDaDemografico="
        + parentelaDaDemografico
        + ", relazioneConCittadinoLoggato="
        + relazioneConCittadinoLoggato
        + ", sorgente="
        + sorgente
        + ", figlioAutodichiarato="
        + figlioAutodichiarato
        + "]";
  }
}
