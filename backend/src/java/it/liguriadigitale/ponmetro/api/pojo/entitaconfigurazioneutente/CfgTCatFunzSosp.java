package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * CfgTCatFunzSosp
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:09.665
 */
import java.io.Serializable;

public class CfgTCatFunzSosp implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTCatFunzSosp() {
    super();
  }

  /** Type : BIGINT Name : ID_FUNZ_SOSP */
  public Long idFunzSosp;

  /** Type : BIGINT Name : ID_FUNZ */
  public Long idFunz;

  /** Type : TIMESTAMP Name : DATA_INIZIO_SOSP */
  public java.time.LocalDateTime dataInizioSosp;

  /** Type : TIMESTAMP Name : DATA_FINE_SOSP */
  public java.time.LocalDateTime dataFineSosp;

  /** Type : BIGINT Name : TIPO_SOSP */
  public Long tipoSosp;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

  /** Sets the value for idFunzSosp */
  public void setIdFunzSosp(Long idFunzSosp) {
    this.idFunzSosp = idFunzSosp;
  }

  /** Gets the value for idFunzSosp */
  public Long getIdFunzSosp() {
    return idFunzSosp;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(Long idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public Long getIdFunz() {
    return idFunz;
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
