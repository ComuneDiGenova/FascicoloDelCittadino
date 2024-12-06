package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * CfgRFcittWidg
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:08.205
 */
import java.io.Serializable;

public class CfgRFcittWidg implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgRFcittWidg() {
    super();
  }

  /** Type : BIGINT Name : ID_CFG_R_FCITT_WIDG */
  public Long idCfgRFcittWidg;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : BIGINT Name : ID_WIDG */
  public Long idWidg;

  /** Type : TIMESTAMP Name : DATA_ASSOCIAZIONE_FCITT_WIDG */
  public java.time.LocalDateTime dataAssociazioneFcittWidg;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

  /** Type : TIMESTAMP Name : DATA_DEASSOCIAZIONE_FCITT_WIDG */
  public java.time.LocalDateTime dataDeassociazioneFcittWidg;

  /** Sets the value for idCfgRFcittWidg */
  public void setIdCfgRFcittWidg(Long idCfgRFcittWidg) {
    this.idCfgRFcittWidg = idCfgRFcittWidg;
  }

  /** Gets the value for idCfgRFcittWidg */
  public Long getIdCfgRFcittWidg() {
    return idCfgRFcittWidg;
  }

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for idWidg */
  public void setIdWidg(Long idWidg) {
    this.idWidg = idWidg;
  }

  /** Gets the value for idWidg */
  public Long getIdWidg() {
    return idWidg;
  }

  /** Sets the value for dataAssociazioneFcittWidg */
  public void setDataAssociazioneFcittWidg(java.time.LocalDateTime dataAssociazioneFcittWidg) {
    this.dataAssociazioneFcittWidg = dataAssociazioneFcittWidg;
  }

  /** Gets the value for dataAssociazioneFcittWidg */
  public java.time.LocalDateTime getDataAssociazioneFcittWidg() {
    return dataAssociazioneFcittWidg;
  }

  /** Sets the value for flagAbilitazione */
  public void setFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
  }

  /** Gets the value for flagAbilitazione */
  public Boolean getFlagAbilitazione() {
    return flagAbilitazione;
  }

  /** Sets the value for dataDeassociazioneFcittWidg */
  public void setDataDeassociazioneFcittWidg(java.time.LocalDateTime dataDeassociazioneFcittWidg) {
    this.dataDeassociazioneFcittWidg = dataDeassociazioneFcittWidg;
  }

  /** Gets the value for dataDeassociazioneFcittWidg */
  public java.time.LocalDateTime getDataDeassociazioneFcittWidg() {
    return dataDeassociazioneFcittWidg;
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
