package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * VCfgTCatWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:06.204
 */
import java.io.Serializable;

public class VCfgTCatWidg implements Serializable {
  private static final long serialVersionUID = 1L;

  public VCfgTCatWidg() {
    super();
  }

  /** Type : BIGINT Name : ID_SEZ */
  public Long idSez;

  /** Type : BIGINT Name : ORDINAMENTO_S */
  public Long ordinamentoS;

  /** Type : BIGINT Name : ORDINAMENTO_C */
  public Long ordinamentoC;

  /** Type : BIGINT Name : ORDINAMENTO_W */
  public Long ordinamentoW;

  /** Type : VARCHAR Name : DENOMINAZIONE_SEZ */
  public String denominazioneSez;

  /** Type : VARCHAR Name : DESCRIZIONE_SEZ */
  public String descrizioneSez;

  /** Type : TIMESTAMP Name : DATA_CATALOGAZIONE_SEZ */
  public java.time.LocalDateTime dataCatalogazioneSez;

  /** Type : VARCHAR Name : URI_SEZ */
  public String uriSez;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_SEZ */
  public Boolean flagAbilitazioneSez;

  /** Type : BIGINT Name : ID_COMP */
  public Long idComp;

  /** Type : VARCHAR Name : DENOMINAZIONE_COMP */
  public String denominazioneComp;

  /** Type : VARCHAR Name : DESCRIZIONE_COMP */
  public String descrizioneComp;

  /** Type : VARCHAR Name : URI_COMP */
  public String uriComp;

  /** Type : TIMESTAMP Name : DATA_CATALOGAZIONE_COMP */
  public java.time.LocalDateTime dataCatalogazioneComp;

  /** Type : TIMESTAMP Name : DATA_INVIO_MSG */
  public java.time.LocalDateTime dataInvioMsg;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_COMP */
  public Boolean flagAbilitazioneComp;

  /** Type : BIGINT Name : ID_FUNZ */
  public Long idFunz;

  /** Type : VARCHAR Name : DENOMINAZIONE_FUNZ */
  public String denominazioneFunz;

  /** Type : VARCHAR Name : DESCRIZIONE_FUNZ */
  public String descrizioneFunz;

  /** Type : VARCHAR Name : WICKET_LABEL_ID_STD */
  public String wicketLabelIdStd;

  /** Type : VARCHAR Name : WICKET_LABEL_ID_ALT */
  public String wicketLabelIdAlt;

  /** Type : VARCHAR Name : CLASSE_PAGINA_STD */
  public String classePaginaStd;

  /** Type : VARCHAR Name : CLASSE_PAGINA_ALT */
  public String classePaginaAlt;

  /** Type : VARCHAR Name : WICKET_TITLE_STD */
  public String wicketTitleStd;

  /** Type : VARCHAR Name : WICKET_TITLE_ALT */
  public String wicketTitleAlt;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_FUNZ */
  public Boolean flagAbilitazioneFunz;

  /** Type : BIGINT Name : ID_FUNZ_SOSP */
  public Long idFunzSosp;

  /** Type : TIMESTAMP Name : DATA_INIZIO_SOSP */
  public java.time.LocalDateTime dataInizioSosp;

  /** Type : TIMESTAMP Name : DATA_FINE_SOSP */
  public java.time.LocalDateTime dataFineSosp;

  /** Type : BIGINT Name : TIPO_SOSP */
  public Long tipoSosp;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_FUNZ_SOSP */
  public Boolean flagAbilitazioneFunzSosp;

  /** Type : BIGINT Name : ID_WIDG */
  public Long idWidg;

  /** Type : VARCHAR Name : DENOMINAZIONE_WIDG */
  public String denominazioneWidg;

  /** Type : VARCHAR Name : DESCRIZIONE_WIDG */
  public String descrizioneWidg;

  /** Type : VARCHAR Name : URI_WIDG */
  public String uriWidg;

  /** Type : TIMESTAMP Name : DATA_CATALOGAZIONE_WIDG */
  public java.time.LocalDateTime dataCatalogazioneWidg;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_WIDG */
  public Boolean flagAbilitazioneWidg;

  /** Type : BIGINT Name : FLAG_DEFAULT */
  public Boolean flagDefault;

  /** Sets the value for idSez */
  public void setIdSez(Long idSez) {
    this.idSez = idSez;
  }

  /** Gets the value for idSez */
  public Long getIdSez() {
    return idSez;
  }

  /** Sets the value for ordinamentoS */
  public void setOrdinamentoS(Long ordinamentoS) {
    this.ordinamentoS = ordinamentoS;
  }

  /** Gets the value for ordinamentoS */
  public Long getOrdinamentoS() {
    return ordinamentoS;
  }

  /** Sets the value for ordinamentoC */
  public void setOrdinamentoC(Long ordinamentoC) {
    this.ordinamentoC = ordinamentoC;
  }

  /** Gets the value for ordinamentoC */
  public Long getOrdinamentoC() {
    return ordinamentoC;
  }

  /** Sets the value for ordinamentoW */
  public void setOrdinamentoW(Long ordinamentoW) {
    this.ordinamentoW = ordinamentoW;
  }

  /** Gets the value for ordinamentoW */
  public Long getOrdinamentoW() {
    return ordinamentoW;
  }

  /** Sets the value for denominazioneSez */
  public void setDenominazioneSez(String denominazioneSez) {
    this.denominazioneSez = denominazioneSez;
  }

  /** Gets the value for denominazioneSez */
  public String getDenominazioneSez() {
    return denominazioneSez;
  }

  /** Sets the value for descrizioneSez */
  public void setDescrizioneSez(String descrizioneSez) {
    this.descrizioneSez = descrizioneSez;
  }

  /** Gets the value for descrizioneSez */
  public String getDescrizioneSez() {
    return descrizioneSez;
  }

  /** Sets the value for dataCatalogazioneSez */
  public void setDataCatalogazioneSez(java.time.LocalDateTime dataCatalogazioneSez) {
    this.dataCatalogazioneSez = dataCatalogazioneSez;
  }

  /** Gets the value for dataCatalogazioneSez */
  public java.time.LocalDateTime getDataCatalogazioneSez() {
    return dataCatalogazioneSez;
  }

  /** Sets the value for uriSez */
  public void setUriSez(String uriSez) {
    this.uriSez = uriSez;
  }

  /** Gets the value for uriSez */
  public String getUriSez() {
    return uriSez;
  }

  /** Sets the value for flagAbilitazioneSez */
  public void setFlagAbilitazioneSez(Boolean flagAbilitazioneSez) {
    this.flagAbilitazioneSez = flagAbilitazioneSez;
  }

  /** Gets the value for flagAbilitazioneSez */
  public Boolean getFlagAbilitazioneSez() {
    return flagAbilitazioneSez;
  }

  /** Sets the value for idComp */
  public void setIdComp(Long idComp) {
    this.idComp = idComp;
  }

  /** Gets the value for idComp */
  public Long getIdComp() {
    return idComp;
  }

  /** Sets the value for denominazioneComp */
  public void setDenominazioneComp(String denominazioneComp) {
    this.denominazioneComp = denominazioneComp;
  }

  /** Gets the value for denominazioneComp */
  public String getDenominazioneComp() {
    return denominazioneComp;
  }

  /** Sets the value for descrizioneComp */
  public void setDescrizioneComp(String descrizioneComp) {
    this.descrizioneComp = descrizioneComp;
  }

  /** Gets the value for descrizioneComp */
  public String getDescrizioneComp() {
    return descrizioneComp;
  }

  /** Sets the value for uriComp */
  public void setUriComp(String uriComp) {
    this.uriComp = uriComp;
  }

  /** Gets the value for uriComp */
  public String getUriComp() {
    return uriComp;
  }

  /** Sets the value for dataCatalogazioneComp */
  public void setDataCatalogazioneComp(java.time.LocalDateTime dataCatalogazioneComp) {
    this.dataCatalogazioneComp = dataCatalogazioneComp;
  }

  /** Gets the value for dataCatalogazioneComp */
  public java.time.LocalDateTime getDataCatalogazioneComp() {
    return dataCatalogazioneComp;
  }

  /** Sets the value for dataInvioMsg */
  public void setDataInvioMsg(java.time.LocalDateTime dataInvioMsg) {
    this.dataInvioMsg = dataInvioMsg;
  }

  /** Gets the value for dataInvioMsg */
  public java.time.LocalDateTime getDataInvioMsg() {
    return dataInvioMsg;
  }

  /** Sets the value for flagAbilitazioneComp */
  public void setFlagAbilitazioneComp(Boolean flagAbilitazioneComp) {
    this.flagAbilitazioneComp = flagAbilitazioneComp;
  }

  /** Gets the value for flagAbilitazioneComp */
  public Boolean getFlagAbilitazioneComp() {
    return flagAbilitazioneComp;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(Long idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public Long getIdFunz() {
    return idFunz;
  }

  /** Sets the value for denominazioneFunz */
  public void setDenominazioneFunz(String denominazioneFunz) {
    this.denominazioneFunz = denominazioneFunz;
  }

  /** Gets the value for denominazioneFunz */
  public String getDenominazioneFunz() {
    return denominazioneFunz;
  }

  /** Sets the value for descrizioneFunz */
  public void setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
  }

  /** Gets the value for descrizioneFunz */
  public String getDescrizioneFunz() {
    return descrizioneFunz;
  }

  /** Sets the value for wicketLabelIdStd */
  public void setWicketLabelIdStd(String wicketLabelIdStd) {
    this.wicketLabelIdStd = wicketLabelIdStd;
  }

  /** Gets the value for wicketLabelIdStd */
  public String getWicketLabelIdStd() {
    return wicketLabelIdStd;
  }

  /** Sets the value for wicketLabelIdAlt */
  public void setWicketLabelIdAlt(String wicketLabelIdAlt) {
    this.wicketLabelIdAlt = wicketLabelIdAlt;
  }

  /** Gets the value for wicketLabelIdAlt */
  public String getWicketLabelIdAlt() {
    return wicketLabelIdAlt;
  }

  /** Sets the value for classePaginaStd */
  public void setClassePaginaStd(String classePaginaStd) {
    this.classePaginaStd = classePaginaStd;
  }

  /** Gets the value for classePaginaStd */
  public String getClassePaginaStd() {
    return classePaginaStd;
  }

  /** Sets the value for classePaginaAlt */
  public void setClassePaginaAlt(String classePaginaAlt) {
    this.classePaginaAlt = classePaginaAlt;
  }

  /** Gets the value for classePaginaAlt */
  public String getClassePaginaAlt() {
    return classePaginaAlt;
  }

  /** Sets the value for wicketTitleStd */
  public void setWicketTitleStd(String wicketTitleStd) {
    this.wicketTitleStd = wicketTitleStd;
  }

  /** Gets the value for wicketTitleStd */
  public String getWicketTitleStd() {
    return wicketTitleStd;
  }

  /** Sets the value for wicketTitleAlt */
  public void setWicketTitleAlt(String wicketTitleAlt) {
    this.wicketTitleAlt = wicketTitleAlt;
  }

  /** Gets the value for wicketTitleAlt */
  public String getWicketTitleAlt() {
    return wicketTitleAlt;
  }

  /** Sets the value for flagAbilitazioneFunz */
  public void setFlagAbilitazioneFunz(Boolean flagAbilitazioneFunz) {
    this.flagAbilitazioneFunz = flagAbilitazioneFunz;
  }

  /** Gets the value for flagAbilitazioneFunz */
  public Boolean getFlagAbilitazioneFunz() {
    return flagAbilitazioneFunz;
  }

  /** Sets the value for idFunzSosp */
  public void setIdFunzSosp(Long idFunzSosp) {
    this.idFunzSosp = idFunzSosp;
  }

  /** Gets the value for idFunzSosp */
  public Long getIdFunzSosp() {
    return idFunzSosp;
  }

  /** Sets the value for dataInizioSosp */
  public void setDataInizioSosp(java.time.LocalDateTime dataInizioSosp) {
    this.dataInizioSosp = dataInizioSosp;
  }

  /** Gets the value for dataInizioSosp */
  public java.time.LocalDateTime getDataInizioSosp() {
    return dataInizioSosp;
  }

  /** Sets the value for dataFineSosp */
  public void setDataFineSosp(java.time.LocalDateTime dataFineSosp) {
    this.dataFineSosp = dataFineSosp;
  }

  /** Gets the value for dataFineSosp */
  public java.time.LocalDateTime getDataFineSosp() {
    return dataFineSosp;
  }

  /** Sets the value for tipoSosp */
  public void setTipoSosp(Long tipoSosp) {
    this.tipoSosp = tipoSosp;
  }

  /** Gets the value for tipoSosp */
  public Long getTipoSosp() {
    return tipoSosp;
  }

  /** Sets the value for flagAbilitazioneFunzSosp */
  public void setFlagAbilitazioneFunzSosp(Boolean flagAbilitazioneFunzSosp) {
    this.flagAbilitazioneFunzSosp = flagAbilitazioneFunzSosp;
  }

  /** Gets the value for flagAbilitazioneFunzSosp */
  public Boolean getFlagAbilitazioneFunzSosp() {
    return flagAbilitazioneFunzSosp;
  }

  /** Sets the value for idWidg */
  public void setIdWidg(Long idWidg) {
    this.idWidg = idWidg;
  }

  /** Gets the value for idWidg */
  public Long getIdWidg() {
    return idWidg;
  }

  /** Sets the value for denominazioneWidg */
  public void setDenominazioneWidg(String denominazioneWidg) {
    this.denominazioneWidg = denominazioneWidg;
  }

  /** Gets the value for denominazioneWidg */
  public String getDenominazioneWidg() {
    return denominazioneWidg;
  }

  /** Sets the value for descrizioneWidg */
  public void setDescrizioneWidg(String descrizioneWidg) {
    this.descrizioneWidg = descrizioneWidg;
  }

  /** Gets the value for descrizioneWidg */
  public String getDescrizioneWidg() {
    return descrizioneWidg;
  }

  /** Sets the value for uriWidg */
  public void setUriWidg(String uriWidg) {
    this.uriWidg = uriWidg;
  }

  /** Gets the value for uriWidg */
  public String getUriWidg() {
    return uriWidg;
  }

  /** Sets the value for dataCatalogazioneWidg */
  public void setDataCatalogazioneWidg(java.time.LocalDateTime dataCatalogazioneWidg) {
    this.dataCatalogazioneWidg = dataCatalogazioneWidg;
  }

  /** Gets the value for dataCatalogazioneWidg */
  public java.time.LocalDateTime getDataCatalogazioneWidg() {
    return dataCatalogazioneWidg;
  }

  /** Sets the value for flagAbilitazioneWidg */
  public void setFlagAbilitazioneWidg(Boolean flagAbilitazioneWidg) {
    this.flagAbilitazioneWidg = flagAbilitazioneWidg;
  }

  /** Gets the value for flagAbilitazioneWidg */
  public Boolean getFlagAbilitazioneWidg() {
    return flagAbilitazioneWidg;
  }

  /** Sets the value for flagDefault */
  public void setFlagDefault(Boolean flagDefault) {
    this.flagDefault = flagDefault;
  }

  /** Gets the value for flagDefault */
  public Boolean getFlagDefault() {
    return flagDefault;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append(super.toString());
    try {
      java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].getName();
        Object fieldValue = fields[i].get(this);
        String line = "\n" + fieldName + ": " + fieldValue;
        str.append(line);
      }
      return str.toString();
    } catch (Exception e) {
      return str.toString();
    }
  }
}
