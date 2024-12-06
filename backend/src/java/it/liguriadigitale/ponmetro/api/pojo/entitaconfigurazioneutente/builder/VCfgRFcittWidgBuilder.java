package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.builder;

import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatComp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunz;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatFunzSosp;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatSez;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.CfgTCatWidg;
import it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente.VCfgRFcittWidg;

public class VCfgRFcittWidgBuilder {

  private static final long serialVersionUID = -2967655746587993197L;

  private VCfgRFcittWidg vCfgRFcittWidg;
  private CfgTCatSez sezione;
  private CfgTCatComp comparto;
  private CfgTCatFunz funzione;
  private CfgTCatFunzSosp sospensioneFunzione;
  private CfgTCatWidg widget;

  public VCfgRFcittWidgBuilder() {
    super();
  }

  public VCfgRFcittWidgBuilder(VCfgRFcittWidg vCfgRFcittWidg) {
    super();
    this.vCfgRFcittWidg = vCfgRFcittWidg;
  }

  public VCfgRFcittWidgBuilder buildSezione() {
    sezione = new CfgTCatSez();
    sezione.setIdSez(vCfgRFcittWidg.getIdSez());
    sezione.setOrdinamento(vCfgRFcittWidg.getOrdinamentoS());
    sezione.setDenominazioneSez(vCfgRFcittWidg.getDenominazioneSez());
    sezione.setDescrizioneSez(vCfgRFcittWidg.getDescrizioneSez());
    sezione.setDataCatalogazioneSez(vCfgRFcittWidg.getDataCatalogazioneSez());
    sezione.setUriSez(vCfgRFcittWidg.getUriSez());
    sezione.setFlagAbilitazione(vCfgRFcittWidg.getFlagAbilitazioneSez());
    sezione.setIdStatoRec(null);
    return this;
  }

  public VCfgRFcittWidgBuilder buildComparto() {
    comparto = new CfgTCatComp();
    comparto.setIdSez(vCfgRFcittWidg.getIdSez());
    comparto.setIdComp(vCfgRFcittWidg.getIdComp());
    comparto.setOrdinamento(vCfgRFcittWidg.getOrdinamentoC());
    comparto.setDenominazioneComp(vCfgRFcittWidg.getDenominazioneComp());
    comparto.setDescrizioneComp(vCfgRFcittWidg.getDescrizioneComp());
    comparto.setUriComp(vCfgRFcittWidg.getUriComp());
    comparto.setDataCatalogazioneComp(vCfgRFcittWidg.getDataCatalogazioneComp());
    comparto.setDataInvioMsg(vCfgRFcittWidg.getDataInvioMsg());
    comparto.setFlagAbilitazione(vCfgRFcittWidg.getFlagAbilitazioneComp());
    comparto.setIdStatoRec(null);
    return this;
  }

  public VCfgRFcittWidgBuilder buildFunzione() {
    funzione = new CfgTCatFunz();
    funzione.setIdComp(vCfgRFcittWidg.getIdComp());
    funzione.setIdFunz(vCfgRFcittWidg.getIdFunz());
    funzione.setDenominazioneFunz(vCfgRFcittWidg.getDenominazioneFunz());
    funzione.setDescrizioneFunz(vCfgRFcittWidg.getDescrizioneFunz());
    funzione.setWicketLabelIdAlt(vCfgRFcittWidg.getWicketLabelIdAlt());
    funzione.setWicketLabelIdStd(vCfgRFcittWidg.getWicketLabelIdStd());
    funzione.setClassePaginaAlt(vCfgRFcittWidg.getClassePaginaAlt());
    funzione.setClassePaginaStd(vCfgRFcittWidg.getClassePaginaStd());
    funzione.setWicketTitleAlt(vCfgRFcittWidg.getWicketTitleAlt());
    funzione.setWicketTitleStd(vCfgRFcittWidg.getWicketTitleStd());
    funzione.setFlagAbilitazione(vCfgRFcittWidg.getFlagAbilitazioneFunz());
    funzione.setIdStatoRec(null);
    return this;
  }

  public VCfgRFcittWidgBuilder buildSospensioneFunzione() {
    sospensioneFunzione = new CfgTCatFunzSosp();
    sospensioneFunzione.setIdFunz(vCfgRFcittWidg.getIdFunz());
    sospensioneFunzione.setIdFunzSosp(vCfgRFcittWidg.getIdFunzSosp());
    sospensioneFunzione.setTipoSosp(vCfgRFcittWidg.getTipoSosp());
    sospensioneFunzione.setDataInizioSosp(vCfgRFcittWidg.getDataInizioSosp());
    sospensioneFunzione.setDataFineSosp(vCfgRFcittWidg.getDataFineSosp());
    sospensioneFunzione.setFlagAbilitazione(vCfgRFcittWidg.getFlagAbilitazioneFunzSosp());
    return this;
  }

  public VCfgRFcittWidgBuilder buildWidget() {
    widget = new CfgTCatWidg();
    widget.setIdFunz(vCfgRFcittWidg.getIdFunz());
    widget.setIdWidg(vCfgRFcittWidg.getIdWidg());
    widget.setOrdinamento(vCfgRFcittWidg.getOrdinamentoW());
    widget.setDenominazioneWidg(vCfgRFcittWidg.getDenominazioneWidg());
    widget.setDescrizioneWidg(vCfgRFcittWidg.getDescrizioneWidg());
    widget.setUriWidg(vCfgRFcittWidg.getUriWidg());
    widget.setDataCatalogazioneWidg(vCfgRFcittWidg.getDataCatalogazioneWidg());
    widget.setFlagDefault(vCfgRFcittWidg.getFlagDefault());
    widget.setFlagAbilitazione(vCfgRFcittWidg.getFlagAbilitazioneWidg());
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

  public CfgTCatFunzSosp getFunzioneSospensione() {
    return sospensioneFunzione;
  }

  public void setFunzioneSospensione(CfgTCatFunzSosp sospensioneFunzione) {
    this.sospensioneFunzione = sospensioneFunzione;
  }

  public CfgTCatWidg getWidget() {
    return widget;
  }

  public void setWidget(CfgTCatWidg widget) {
    this.widget = widget;
  }

  public VCfgRFcittWidg getvCfgRFcittWidg() {
    return vCfgRFcittWidg;
  }

  public void setvCfgRFcittWidg(VCfgRFcittWidg vCfgRFcittWidg) {
    this.vCfgRFcittWidg = vCfgRFcittWidg;
  }
}
