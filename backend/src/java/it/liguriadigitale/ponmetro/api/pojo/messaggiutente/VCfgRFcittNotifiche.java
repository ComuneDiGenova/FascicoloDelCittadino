package it.liguriadigitale.ponmetro.api.pojo.messaggiutente;

/**
 * VCfgRFcittNotifiche
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-11-08T18:35:54.859
 */
import java.io.Serializable;

public class VCfgRFcittNotifiche implements Serializable {
  private static final long serialVersionUID = 1L;

  public VCfgRFcittNotifiche() {
    super();
  }

  /** Type : BIGINT Name : ID_NOTIFICA */
  public Long idNotifica;

  /** Type : BIGINT Name : ID_COMP */
  public Long idComp;

  /** Type : BIGINT Name : ID_SEZ */
  public Long idSez;

  /** Type : VARCHAR Name : DENOMINAZIONE_SEZ */
  public String denominazioneSez;

  /** Type : VARCHAR Name : DESCRIZIONE_SEZ */
  public String descrizioneSez;

  /** Type : VARCHAR Name : URI_SEZ */
  public String uriSez;

  /** Type : VARCHAR Name : DENOMINAZIONE_COMP */
  public String denominazioneComp;

  /** Type : VARCHAR Name : DESCRIZIONE_COMP */
  public String descrizioneComp;

  /** Type : VARCHAR Name : URI_COMP */
  public String uriComp;

  /** Type : TIMESTAMP Name : DATA_CATALOGAZIONE_COMP */
  public java.time.LocalDateTime dataCatalogazioneComp;

  /** Type : BIGINT Name : ORDINAMENTO */
  public Long ordinamento;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_COMP */
  public Boolean flagAbilitazioneComp;

  /** Type : VARCHAR Name : TESTO_NOTIFICA */
  public String testoNotifica;

  /** Type : VARCHAR Name : URI_NOTIFICA */
  public String uriNotifica;

  /** Type : TIMESTAMP Name : DATA_NOTIFICA */
  public java.time.LocalDateTime dataNotifica;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE_NOTIF */
  public Boolean flagAbilitazioneNotif;

  /** Type : BIGINT Name : ID_FCITT_NOTIFICHE */
  public java.math.BigDecimal idFcittNotifiche;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : BIGINT Name : PERSON_ID */
  public Long personId;

  /** Type : TIMESTAMP Name : DATA_PRESA_VISIONE */
  public java.time.LocalDateTime dataPresaVisione;

  /** Type : BIGINT Name : ID_STATO_REC */
  public Long idStatoRec;

  /** Sets the value for idNotifica */
  public void setIdNotifica(Long idNotifica) {
    this.idNotifica = idNotifica;
  }

  /** Gets the value for idNotifica */
  public Long getIdNotifica() {
    return idNotifica;
  }

  /** Sets the value for idComp */
  public void setIdComp(Long idComp) {
    this.idComp = idComp;
  }

  /** Gets the value for idComp */
  public Long getIdComp() {
    return idComp;
  }

  /** Sets the value for idSez */
  public void setIdSez(Long idSez) {
    this.idSez = idSez;
  }

  /** Gets the value for idSez */
  public Long getIdSez() {
    return idSez;
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

  /** Sets the value for uriSez */
  public void setUriSez(String uriSez) {
    this.uriSez = uriSez;
  }

  /** Gets the value for uriSez */
  public String getUriSez() {
    return uriSez;
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
  public void setOrdinamento(Long ordinamento) {
    this.ordinamento = ordinamento;
  }

  /** Gets the value for ordinamento */
  public Long getOrdinamento() {
    return ordinamento;
  }

  /** Sets the value for flagAbilitazioneComp */
  public void setFlagAbilitazioneComp(Boolean flagAbilitazioneComp) {
    this.flagAbilitazioneComp = flagAbilitazioneComp;
  }

  /** Gets the value for flagAbilitazioneComp */
  public Boolean getFlagAbilitazioneComp() {
    return flagAbilitazioneComp;
  }

  /** Sets the value for testoNotifica */
  public void setTestoNotifica(String testoNotifica) {
    this.testoNotifica = testoNotifica;
  }

  /** Gets the value for testoNotifica */
  public String getTestoNotifica() {
    return testoNotifica;
  }

  /** Sets the value for uriNotifica */
  public void setUriNotifica(String uriNotifica) {
    this.uriNotifica = uriNotifica;
  }

  /** Gets the value for uriNotifica */
  public String getUriNotifica() {
    return uriNotifica;
  }

  /** Sets the value for dataNotifica */
  public void setDataNotifica(java.time.LocalDateTime dataNotifica) {
    this.dataNotifica = dataNotifica;
  }

  /** Gets the value for dataNotifica */
  public java.time.LocalDateTime getDataNotifica() {
    return dataNotifica;
  }

  /** Sets the value for flagAbilitazioneNotif */
  public void setFlagAbilitazioneNotif(Boolean flagAbilitazioneNotif) {
    this.flagAbilitazioneNotif = flagAbilitazioneNotif;
  }

  /** Gets the value for flagAbilitazioneNotif */
  public Boolean getFlagAbilitazioneNotif() {
    return flagAbilitazioneNotif;
  }

  /** Sets the value for idFcittNotifiche */
  public void setIdFcittNotifiche(java.math.BigDecimal idFcittNotifiche) {
    this.idFcittNotifiche = idFcittNotifiche;
  }

  /** Gets the value for idFcittNotifiche */
  public java.math.BigDecimal getIdFcittNotifiche() {
    return idFcittNotifiche;
  }

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for personId */
  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  /** Gets the value for personId */
  public Long getPersonId() {
    return personId;
  }

  /** Sets the value for dataPresaVisione */
  public void setDataPresaVisione(java.time.LocalDateTime dataPresaVisione) {
    this.dataPresaVisione = dataPresaVisione;
  }

  /** Gets the value for dataPresaVisione */
  public java.time.LocalDateTime getDataPresaVisione() {
    return dataPresaVisione;
  }

  /** Sets the value for idStatoRec */
  public void setIdStatoRec(Long idStatoRec) {
    this.idStatoRec = idStatoRec;
  }

  /** Gets the value for idStatoRec */
  public Long getIdStatoRec() {
    return idStatoRec;
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
