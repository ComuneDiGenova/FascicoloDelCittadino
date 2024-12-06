package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatFunz;

public class VCfgTCatFunzBuilder {

  private VCfgTCatFunz vCfgTCatFunz;
  private CfgTCatSez sezione;
  private CfgTCatComp comparto;
  private CfgTCatFunz funzione;

  public VCfgTCatFunzBuilder() {
    super();
  }

  public VCfgTCatFunzBuilder(VCfgTCatFunz vCfgTCatFunz) {
    super();
    this.vCfgTCatFunz = vCfgTCatFunz;
  }

  public VCfgTCatFunzBuilder buildSezione() {
    sezione = new CfgTCatSez();
    sezione.setIdSez(vCfgTCatFunz.getIdSez());
    sezione.setOrdinamento(vCfgTCatFunz.getOrdinamentoS());
    sezione.setDenominazioneSez(vCfgTCatFunz.getDenominazioneSez());
    sezione.setDescrizioneSez(vCfgTCatFunz.getDescrizioneSez());
    sezione.setDataCatalogazioneSez(vCfgTCatFunz.getDataCatalogazioneSez());
    sezione.setUriSez(vCfgTCatFunz.getUriSez());
    sezione.setFlagAbilitazione(vCfgTCatFunz.getFlagAbilitazioneSez());
    sezione.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatFunzBuilder buildComparto() {
    comparto = new CfgTCatComp();
    comparto.setIdSez(vCfgTCatFunz.getIdSez());
    comparto.setIdComp(vCfgTCatFunz.getIdComp());
    comparto.setOrdinamento(vCfgTCatFunz.getOrdinamentoC());
    comparto.setDenominazioneComp(vCfgTCatFunz.getDenominazioneComp());
    comparto.setDescrizioneComp(vCfgTCatFunz.getDescrizioneComp());
    comparto.setUriComp(vCfgTCatFunz.getUriComp());
    comparto.setDataCatalogazioneComp(vCfgTCatFunz.getDataCatalogazioneComp());
    comparto.setDataInvioMsg(vCfgTCatFunz.getDataInvioMsg());
    comparto.setFlagAbilitazione(vCfgTCatFunz.getFlagAbilitazioneComp());
    comparto.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatFunzBuilder buildFunzione() {
    funzione = new CfgTCatFunz();
    funzione.setIdComp(vCfgTCatFunz.getIdComp());
    funzione.setIdFunz(vCfgTCatFunz.getIdFunz());
    funzione.setDenominazioneFunz(vCfgTCatFunz.getDenominazioneFunz());
    funzione.setDescrizioneFunz(vCfgTCatFunz.getDescrizioneFunz());
    funzione.setWicketLabelIdAlt(vCfgTCatFunz.getWicketLabelIdAlt());
    funzione.setWicketLabelIdStd(vCfgTCatFunz.getWicketLabelIdStd());
    funzione.setClassePaginaAlt(vCfgTCatFunz.getClassePaginaAlt());
    funzione.setClassePaginaStd(vCfgTCatFunz.getClassePaginaStd());
    funzione.setWicketTitleAlt(vCfgTCatFunz.getWicketTitleAlt());
    funzione.setWicketTitleStd(vCfgTCatFunz.getWicketTitleStd());
    funzione.setFlagAbilitazione(vCfgTCatFunz.getFlagAbilitazioneFunz());
    funzione.setIdStatoRec(vCfgTCatFunz.getIdStatoRec());
    funzione.setIsResidente(vCfgTCatFunz.getFlagResidente());
    funzione.setIsNonResidente(vCfgTCatFunz.getFlagNonResidente());
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

  public CfgTCatFunz getFunzione() {
    return funzione;
  }

  public void setFunzione(CfgTCatFunz funzione) {
    this.funzione = funzione;
  }

  public VCfgTCatFunz getvCfgTCatFunz() {
    return vCfgTCatFunz;
  }

  public void setvCfgTCatFunz(VCfgTCatFunz vCfgTCatFunz) {
    this.vCfgTCatFunz = vCfgTCatFunz;
  }
}
