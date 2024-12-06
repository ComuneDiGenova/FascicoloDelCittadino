package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * VCfgRFcittComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:40:04.453
 */
import java.io.Serializable;

public class VCfgRFcittComp implements Serializable {
  private static final long serialVersionUID = 1L;

  public VCfgRFcittComp() {
    super();
  }

  /** Type : BIGINT Name : ID_SEZ */
  public Long idSez;

  /** Type : BIGINT Name : ORDINAMENTO_S */
  public Long ordinamentoS;

  /** Type : BIGINT Name : ORDINAMENTO_C */
  public Long ordinamentoC;

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

  /** Type : BIGINT Name : ORDINAMENTO */
  public java.time.LocalDateTime dataInvioMsg;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_COMP */
  public Boolean flagAbilitazioneComp;

  /** Type : BIGINT Name : ID_CFG_R_FCITT_COMP */
  public Long idCfgRFcittComp;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : TIMESTAMP Name : DATA_REGISTRAZ_FCITT_COMP */
  public java.time.LocalDateTime dataRegistrazFcittComp;

  /** Type : TIMESTAMP Name : DATA_DEREGISTRAZ_FCITT_COMP */
  public java.time.LocalDateTime dataDeregistrazFcittComp;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_CITT_COMP */
  public Boolean flagAbilitazioneCittComp;

  /** Type : BIGINT Name : PERSON_ID */
  public Long personId;

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

  /** Gets the value for idComp */
  public Long getOrdinamentoC() {
    return ordinamentoC;
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

  /** Sets the value for ordinamento */
  public void setDataInvioMsg(java.time.LocalDateTime dataInvioMsg) {
    this.dataInvioMsg = dataInvioMsg;
  }

  /** Gets the value for ordinamento */
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

  /** Sets the value for idCfgRFcittComp */
  public void setIdCfgRFcittComp(Long idCfgRFcittComp) {
    this.idCfgRFcittComp = idCfgRFcittComp;
  }

  /** Gets the value for idCfgRFcittComp */
  public Long getIdCfgRFcittComp() {
    return idCfgRFcittComp;
  }

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for dataRegistrazFcittComp */
  public void setDataRegistrazFcittComp(java.time.LocalDateTime dataRegistrazFcittComp) {
    this.dataRegistrazFcittComp = dataRegistrazFcittComp;
  }

  /** Gets the value for dataRegistrazFcittComp */
  public java.time.LocalDateTime getDataRegistrazFcittComp() {
    return dataRegistrazFcittComp;
  }

  /** Sets the value for dataDeregistrazFcittComp */
  public void setDataDeregistrazFcittComp(java.time.LocalDateTime dataDeregistrazFcittComp) {
    this.dataDeregistrazFcittComp = dataDeregistrazFcittComp;
  }

  /** Gets the value for dataDeregistrazFcittComp */
  public java.time.LocalDateTime getDataDeregistrazFcittComp() {
    return dataDeregistrazFcittComp;
  }

  /** Sets the value for flagAbilitazioneCittComp */
  public void setFlagAbilitazioneCittComp(Boolean flagAbilitazioneCittComp) {
    this.flagAbilitazioneCittComp = flagAbilitazioneCittComp;
  }

  /** Gets the value for flagAbilitazioneCittComp */
  public Boolean getFlagAbilitazioneCittComp() {
    return flagAbilitazioneCittComp;
  }

  /** Sets the value for personId */
  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  /** Gets the value for personId */
  public Long getPersonId() {
    return personId;
  }

  @Override
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
