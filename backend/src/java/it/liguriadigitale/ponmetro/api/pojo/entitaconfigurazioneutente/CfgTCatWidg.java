package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * CfgTCatWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:10.401
 */
import java.io.Serializable;

public class CfgTCatWidg implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTCatWidg() {
    super();
  }

  /** Type : BIGINT Name : ID_WIDG */
  public Long idWidg;

  /** Type : BIGINT Name : ID_FUNZ */
  public Long idFunz;

  /** Type : VARCHAR Name : DENOMINAZIONE_WIDG */
  public String denominazioneWidg;

  /** Type : VARCHAR Name : DESCRIZIONE_WIDG */
  public String descrizioneWidg;

  /** Type : VARCHAR Name : URI_WIDG */
  public String uriWidg;

  /** Type : TIMESTAMP Name : DATA_CATALOGAZIONE_WIDG */
  public java.time.LocalDateTime dataCatalogazioneWidg;

  /** Type : BIGINT Name : ORDINAMENTO */
  public Long ordinamento;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

  /** Type : BIGINT Name : FLAG_DEFAULT */
  public Boolean flagDefault;

  /** Sets the value for idWidg */
  public void setIdWidg(Long idWidg) {
    this.idWidg = idWidg;
  }

  /** Gets the value for idWidg */
  public Long getIdWidg() {
    return idWidg;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(Long idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public Long getIdFunz() {
    return idFunz;
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

  /** Sets the value for ordinamento */
  public void setOrdinamento(Long ordinamento) {
    this.ordinamento = ordinamento;
  }

  /** Gets the value for ordinamento */
  public Long getOrdinamento() {
    return ordinamento;
  }

  /** Sets the value for flagAbilitazione */
  public void setFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
  }

  /** Gets the value for flagAbilitazione */
  public Boolean getFlagAbilitazione() {
    return flagAbilitazione;
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
