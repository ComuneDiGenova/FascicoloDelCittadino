package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;

public class ComponenteNucleo implements Serializable {

  private static final long serialVersionUID = -598110251656624127L;

  private String rapportoConDichiarante;
  private Boolean flagAssenzaReddito;
  private FoglioComponenteNucleo foglioComponenteNucleo;

  public String getRapportoConDichiarante() {
    return rapportoConDichiarante;
  }

  public void setRapportoConDichiarante(String rapportoConDichiarante) {
    this.rapportoConDichiarante = rapportoConDichiarante;
  }

  public Boolean getFlagAssenzaReddito() {
    return flagAssenzaReddito;
  }

  public void setFlagAssenzaReddito(Boolean flagAssenzaReddito) {
    this.flagAssenzaReddito = flagAssenzaReddito;
  }

  public FoglioComponenteNucleo getFoglioComponenteNucleo() {
    return foglioComponenteNucleo;
  }

  public void setFoglioComponenteNucleo(FoglioComponenteNucleo foglioComponenteNucleo) {
    this.foglioComponenteNucleo = foglioComponenteNucleo;
  }

  @Override
  public String toString() {
    return "ComponenteNucleo [rapportoConDichiarante="
        + rapportoConDichiarante
        + ", flagAssenzaReddito="
        + flagAssenzaReddito
        + ", foglioComponenteNucleo="
        + foglioComponenteNucleo
        + "]";
  }
}
