package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.pojo.util.PojoUtils;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.tributi.model.ElencoScadenze;
import java.util.List;

public class ElencoScadenzeExt extends ElencoScadenze {

  private static final long serialVersionUID = 1L;

  public ElencoScadenzeExt() {
    super();
  }

  public String getTitolo() {
    return "Scadenza " + getTributo();
  }

  public String getDelibera() {
    return super.getDettaglio().getFlagDelibera() ? "SI" : "NO";
  }

  public String getAccertato() {
    return (super.getDettaglio().getAccertato() != null
            && !super.getDettaglio().getAccertato().isEmpty())
        ? "SI"
        : "NO";
  }

  public DettaglioScadenzeExt getDettaglioScadenzeExt() {
    if (super.getDettaglio() == null) return null;
    return PojoUtils.copyAndReturn(new DettaglioScadenzeExt(), super.getDettaglio());
  }

  public List<OrdinarioScadenzeExt> getOrdinarioScadenzeExt() {
    if (getDettaglioScadenzeExt() == null) return null;
    return getDettaglioScadenzeExt().getOrdinarioScadenzeExt();
  }

  public List<AccertatoScadenzeExt> getAccertatoScadenzeExt() {
    if (getDettaglioScadenzeExt() == null) return null;
    return getDettaglioScadenzeExt().getAccertatoScadenzeExt();
  }

  // In attesa di istruzioni / legende
  public String getColorForIcon() {
    return BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS;
  }

  public Integer getAnnoRiferimento() {
    if (getDettaglioScadenzeExt() == null) return null;
    return getDettaglioScadenzeExt().getAnnoRiferimento();
  }

  @Override
  public String toString() {
    return "ElencoScadenzeExt [getTitolo()="
        + getTitolo()
        + ", getDelibera()="
        + getDelibera()
        + ", getAccertato()="
        + getAccertato()
        + ", getDettaglioScadenzeExt()="
        + getDettaglioScadenzeExt()
        + ", getOrdinarioScadenzeExt()="
        + getOrdinarioScadenzeExt()
        + ", getAccertatoScadenzeExt()="
        + getAccertatoScadenzeExt()
        + ", getColorForIcon()="
        + getColorForIcon()
        + ", getAnnoRiferimento()="
        + getAnnoRiferimento()
        + ", toString()="
        + super.toString()
        + "]";
  }
}
