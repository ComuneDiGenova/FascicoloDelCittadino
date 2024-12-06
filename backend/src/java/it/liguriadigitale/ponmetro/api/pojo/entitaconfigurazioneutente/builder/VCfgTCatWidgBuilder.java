package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgTCatWidg;

public class VCfgTCatWidgBuilder {

  private VCfgTCatWidg vCfgTCatWidg;
  private CfgTCatSez sezione;
  private CfgTCatComp comparto;
  private CfgTCatFunz funzione;
  private CfgTCatWidg widget;

  public VCfgTCatWidgBuilder() {
    super();
  }

  public VCfgTCatWidgBuilder(VCfgTCatWidg vCfgTCatWidg) {
    super();
    this.vCfgTCatWidg = vCfgTCatWidg;
  }

  public VCfgTCatWidgBuilder buildSezione() {
    sezione = new CfgTCatSez();
    sezione.setIdSez(vCfgTCatWidg.getIdSez());
    sezione.setOrdinamento(vCfgTCatWidg.getOrdinamentoS());
    sezione.setDenominazioneSez(vCfgTCatWidg.getDenominazioneSez());
    sezione.setDescrizioneSez(vCfgTCatWidg.getDescrizioneSez());
    sezione.setDataCatalogazioneSez(vCfgTCatWidg.getDataCatalogazioneSez());
    sezione.setUriSez(vCfgTCatWidg.getUriSez());
    sezione.setFlagAbilitazione(vCfgTCatWidg.getFlagAbilitazioneSez());
    sezione.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatWidgBuilder buildComparto() {
    comparto = new CfgTCatComp();
    comparto.setIdSez(vCfgTCatWidg.getIdSez());
    comparto.setIdComp(vCfgTCatWidg.getIdComp());
    comparto.setOrdinamento(vCfgTCatWidg.getOrdinamentoC());
    comparto.setDenominazioneComp(vCfgTCatWidg.getDenominazioneComp());
    comparto.setDescrizioneComp(vCfgTCatWidg.getDescrizioneComp());
    comparto.setUriComp(vCfgTCatWidg.getUriComp());
    comparto.setDataCatalogazioneComp(vCfgTCatWidg.getDataCatalogazioneComp());
    comparto.setDataInvioMsg(vCfgTCatWidg.getDataInvioMsg());
    comparto.setFlagAbilitazione(vCfgTCatWidg.getFlagAbilitazioneComp());
    comparto.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatWidgBuilder buildFunzione() {
    funzione = new CfgTCatFunz();
    funzione.setIdComp(vCfgTCatWidg.getIdComp());
    funzione.setIdFunz(vCfgTCatWidg.getIdFunz());
    funzione.setDenominazioneFunz(vCfgTCatWidg.getDenominazioneFunz());
    funzione.setDescrizioneFunz(vCfgTCatWidg.getDescrizioneFunz());
    funzione.setWicketLabelIdAlt(vCfgTCatWidg.getWicketLabelIdAlt());
    funzione.setWicketLabelIdStd(vCfgTCatWidg.getWicketLabelIdStd());
    funzione.setClassePaginaAlt(vCfgTCatWidg.getClassePaginaAlt());
    funzione.setClassePaginaStd(vCfgTCatWidg.getClassePaginaStd());
    funzione.setWicketTitleAlt(vCfgTCatWidg.getWicketTitleAlt());
    funzione.setWicketTitleStd(vCfgTCatWidg.getWicketTitleStd());
    funzione.setFlagAbilitazione(vCfgTCatWidg.getFlagAbilitazioneFunz());
    funzione.setIdStatoRec(null);
    return this;
  }

  public VCfgTCatWidgBuilder buildWidget() {
    widget = new CfgTCatWidg();
    widget.setIdFunz(vCfgTCatWidg.getIdFunz());
    widget.setIdWidg(vCfgTCatWidg.getIdWidg());
    widget.setOrdinamento(vCfgTCatWidg.getOrdinamentoW());
    widget.setDenominazioneWidg(vCfgTCatWidg.getDenominazioneWidg());
    widget.setDescrizioneWidg(vCfgTCatWidg.getDescrizioneWidg());
    widget.setUriWidg(vCfgTCatWidg.getUriWidg());
    widget.setDataCatalogazioneWidg(vCfgTCatWidg.getDataCatalogazioneWidg());
    widget.setFlagDefault(vCfgTCatWidg.getFlagDefault());
    widget.setFlagAbilitazione(vCfgTCatWidg.getFlagAbilitazioneWidg());
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

  public CfgTCatWidg getWidget() {
    return widget;
  }

  public void setWidget(CfgTCatWidg widget) {
    this.widget = widget;
  }

  public VCfgTCatWidg CfgTCatWidg() {
    return vCfgTCatWidg;
  }

  public void CfgTCatWidg(VCfgTCatWidg vCfgTCatWidg) {
    this.vCfgTCatWidg = vCfgTCatWidg;
  }
}
