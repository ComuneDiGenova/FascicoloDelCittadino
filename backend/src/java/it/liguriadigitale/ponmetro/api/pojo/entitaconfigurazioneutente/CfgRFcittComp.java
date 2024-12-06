package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * CfgRFcittComp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:06.998
 */
import java.io.Serializable;

public class CfgRFcittComp implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgRFcittComp() {
    super();
  }

  /** Type : BIGINT Name : ID_CFG_R_FCITT_COMP */
  public Long idCfgRFcittComp;

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : BIGINT Name : ID_COMP */
  public Long idComp;

  /** Type : TIMESTAMP Name : DATA_REGISTRAZ_FCITT_COMP */
  public java.time.LocalDateTime dataRegistrazFcittComp;

  /** Type : TIMESTAMP Name : DATA_DEREGISTRAZ_FCITT_COMP */
  public java.time.LocalDateTime dataDeregistrazFcittComp;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

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

  /** Sets the value for idComp */
  public void setIdComp(Long idComp) {
    this.idComp = idComp;
  }

  /** Gets the value for idComp */
  public Long getIdComp() {
    return idComp;
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

  /** Sets the value for flagAbilitazione */
  public void setFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
  }

  /** Gets the value for flagAbilitazione */
  public Boolean getFlagAbilitazione() {
    return flagAbilitazione;
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
