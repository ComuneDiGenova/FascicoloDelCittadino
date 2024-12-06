package it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tributi.model.Versamenti;

public class TributiVersamentiExt extends Versamenti {

  private static final long serialVersionUID = 1L;

  public TributiVersamentiExt() {
    super();
  }

  // 2021/08/01 - Per ora i versamenti riguardano SOLO IMU
  public String getTitolo() {
    return "VERSAMENTO IMU";
  }

  // In attesa di istruzioni / legende
  public String getColorForIcon() {
    return BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS;
  }

  @Override
  public String getDataVersamento() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataVersamento());
  }

  @Override
  public String getDataScadenzaRata() {
    return PageUtil.convertiAAAAminusMMminusDDFormatoData(super.getDataScadenzaRata());
  }

  public String getImportoVersatoL() {
    return PageUtil.convertiImportoToEuroZeroWantNull(super.getImportoVersato());
  }

  public String getVerificato() {
    return super.getFlagIsVerificato() ? "SI" : "NO";
  }
}
