package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.util.List;

public class PatrimonioMobiliare implements Serializable {

  private static final long serialVersionUID = -739746143542473240L;

  private boolean FlagPossessoRapportoFinanziario;
  private List<Rapporto> Rapporto;

  public boolean isFlagPossessoRapportoFinanziario() {
    return FlagPossessoRapportoFinanziario;
  }

  public void setFlagPossessoRapportoFinanziario(boolean flagPossessoRapportoFinanziario) {
    FlagPossessoRapportoFinanziario = flagPossessoRapportoFinanziario;
  }

  public List<Rapporto> getRapporto() {
    return Rapporto;
  }

  public void setRapporto(List<Rapporto> rapporto) {
    Rapporto = rapporto;
  }

  @Override
  public String toString() {
    return "PatrimonioMobiliare [FlagPossessoRapportoFinanziario="
        + FlagPossessoRapportoFinanziario
        + ", Rapporto="
        + Rapporto
        + "]";
  }
}
