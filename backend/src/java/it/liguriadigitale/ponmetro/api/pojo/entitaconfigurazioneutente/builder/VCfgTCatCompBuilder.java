package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatComp;

public class VCfgTCatCompBuilder {

  private VCfgTCatComp vCfgTCatComp;
  private CfgTCatSez sezione;
  private CfgTCatComp comparto;

  public VCfgTCatCompBuilder() {
    super();
  }

  public VCfgTCatCompBuilder(VCfgTCatComp vCfgTCatComp) {
    super();
    this.vCfgTCatComp = vCfgTCatComp;
  }

  public VCfgTCatCompBuilder buildSezione() {
    sezione = new CfgTCatSez();
    sezione.setIdSez(vCfgTCatComp.getIdSez());
    sezione.setOrdinamento(vCfgTCatComp.getOrdinamentoS());
    sezione.setDenominazioneSez(vCfgTCatComp.getDenominazioneSez());
    sezione.setDescrizioneSez(vCfgTCatComp.getDescrizioneSez());
    sezione.setDataCatalogazioneSez(vCfgTCatComp.getDataCatalogazioneSez());
    sezione.setUriSez(vCfgTCatComp.getUriSez());
    sezione.setFlagAbilitazione(vCfgTCatComp.getFlagAbilitazioneSez());
    sezione.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatCompBuilder buildComparto() {
    comparto = new CfgTCatComp();
    comparto.setIdSez(vCfgTCatComp.getIdSez());
    comparto.setIdComp(vCfgTCatComp.getIdComp());
    comparto.setOrdinamento(vCfgTCatComp.getOrdinamentoC());
    comparto.setDenominazioneComp(vCfgTCatComp.getDenominazioneComp());
    comparto.setDescrizioneComp(vCfgTCatComp.getDescrizioneComp());
    comparto.setUriComp(vCfgTCatComp.getUriComp());
    comparto.setDataCatalogazioneComp(vCfgTCatComp.getDataCatalogazioneComp());
    comparto.setDataInvioMsg(vCfgTCatComp.getDataInvioMsg());
    comparto.setFlagAbilitazione(vCfgTCatComp.getFlagAbilitazioneComp());
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

  public VCfgTCatComp getvCfgTCatComp() {
    return vCfgTCatComp;
  }

  public void setvCfgTCatComp(VCfgTCatComp vCfgTCatComp) {
    this.vCfgTCatComp = vCfgTCatComp;
  }
}
