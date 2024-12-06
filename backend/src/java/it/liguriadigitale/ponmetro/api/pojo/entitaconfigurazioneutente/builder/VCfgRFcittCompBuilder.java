package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittComp;

public class VCfgRFcittCompBuilder {

  private static final long serialVersionUID = -2967655746587193197L;

  private VCfgRFcittComp vCfgRFcittComp;
  private CfgTCatSez sezione;
  private CfgTCatComp comparto;

  public VCfgRFcittCompBuilder() {
    super();
  }

  public VCfgRFcittCompBuilder(VCfgRFcittComp vCfgRFcittComp) {
    super();
    this.vCfgRFcittComp = vCfgRFcittComp;
  }

  public VCfgRFcittCompBuilder(
      VCfgRFcittComp vCfgRFcittComp,
      CfgTCatSez sezione,
      CfgTCatComp comparto,
      CfgTCatFunz funzione,
      CfgTCatWidg widget) {
    super();
    this.vCfgRFcittComp = vCfgRFcittComp;
    this.sezione = sezione;
    this.comparto = comparto;
  }

  public VCfgRFcittCompBuilder buildSezione() {
    sezione = new CfgTCatSez();
    sezione.setIdSez(vCfgRFcittComp.getIdSez());
    sezione.setOrdinamento(vCfgRFcittComp.getOrdinamentoS());
    sezione.setDenominazioneSez(vCfgRFcittComp.getDenominazioneSez());
    sezione.setDescrizioneSez(vCfgRFcittComp.getDescrizioneSez());
    sezione.setDataCatalogazioneSez(vCfgRFcittComp.getDataCatalogazioneSez());
    sezione.setUriSez(vCfgRFcittComp.getUriSez());
    sezione.setFlagAbilitazione(vCfgRFcittComp.getFlagAbilitazioneSez());
    sezione.setIdStatoRec(null);
    return this;
  }

  public VCfgRFcittCompBuilder buildComparto() {
    comparto = new CfgTCatComp();
    comparto.setIdSez(vCfgRFcittComp.getIdSez());
    comparto.setIdComp(vCfgRFcittComp.getIdComp());
    comparto.setOrdinamento(vCfgRFcittComp.getOrdinamentoC());
    comparto.setDenominazioneComp(vCfgRFcittComp.getDenominazioneComp());
    comparto.setDescrizioneComp(vCfgRFcittComp.getDescrizioneComp());
    comparto.setUriComp(vCfgRFcittComp.getUriComp());
    comparto.setDataCatalogazioneComp(vCfgRFcittComp.getDataCatalogazioneComp());
    comparto.setDataInvioMsg(vCfgRFcittComp.getDataInvioMsg());
    comparto.setFlagAbilitazione(vCfgRFcittComp.getFlagAbilitazioneComp());
    comparto.setIdStatoRec(null);
    return this;
  }

  public CfgTCatSez getSezione() {
    return sezione;
  }

  public void setSezione(CfgTCatSez sezione) {
    this.sezione = sezione;
  }

  public CfgTCatComp getComparto() {
    return comparto;
  }

  public void setComparto(CfgTCatComp comparto) {
    this.comparto = comparto;
  }

  public VCfgRFcittComp getvCfgRFcittComp() {
    return vCfgRFcittComp;
  }

  public void setvCfgRFcittComp(VCfgRFcittComp vCfgRFcittComp) {
    this.vCfgRFcittComp = vCfgRFcittComp;
  }
}
