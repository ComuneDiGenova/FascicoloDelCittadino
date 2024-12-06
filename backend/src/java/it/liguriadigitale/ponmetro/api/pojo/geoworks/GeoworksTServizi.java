package it.liguriadigitale.ponmetro.api.pojo.geoworks;

/**
 * GeoworksTServizi
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-24T12:30:36.814
 */
import java.io.Serializable;

public class GeoworksTServizi implements Serializable {
  private static final long serialVersionUID = 1L;

  public GeoworksTServizi() {
    super();
  }

  /** Type : BIGINT Name : ID_SERVIZIO */
  public java.math.BigDecimal idServizio;

  /** Type : BIGINT Name : ID_FUNZ */
  public java.math.BigDecimal idFunz;

  /** Type : VARCHAR Name : DESCRIZIONE_SERVIZIO */
  public String descrizioneServizio;

  /** Type : VARCHAR Name : TIPO_FUNZ */
  public String tipoFunz;

  /** Sets the value for idServizio */
  public void setIdServizio(java.math.BigDecimal idServizio) {
    this.idServizio = idServizio;
  }

  /** Gets the value for idServizio */
  public java.math.BigDecimal getIdServizio() {
    return idServizio;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public java.math.BigDecimal getIdFunz() {
    return idFunz;
  }

  /** Sets the value for descrizioneServizio */
  public void setDescrizioneServizio(String descrizioneServizio) {
    this.descrizioneServizio = descrizioneServizio;
  }

  /** Gets the value for descrizioneServizio */
  public String getDescrizioneServizio() {
    return descrizioneServizio;
  }

  /** Sets the value for tipoFunz */
  public void setTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
  }

  /** Gets the value for tipoFunz */
  public String getTipoFunz() {
    return tipoFunz;
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
