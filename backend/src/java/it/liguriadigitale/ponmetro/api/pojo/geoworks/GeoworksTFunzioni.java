package it.liguriadigitale.ponmetro.api.pojo.geoworks;

/**
 * GeoworksTFunzioni
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-10-24T10:39:21.261
 */
import java.io.Serializable;

public class GeoworksTFunzioni implements Serializable {
  private static final long serialVersionUID = 1L;

  public GeoworksTFunzioni() {
    super();
  }

  /** Type : BIGINT Name : ID_FUNZ */
  public java.math.BigDecimal idFunz;

  /** Type : VARCHAR Name : TIPO_FUNZ */
  public String tipoFunz;

  /** Type : VARCHAR Name : DESCRIZIONE_FUNZ */
  public String descrizioneFunz;

  /** Sets the value for idFunz */
  public void setIdFunz(java.math.BigDecimal idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public java.math.BigDecimal getIdFunz() {
    return idFunz;
  }

  /** Sets the value for tipoFunz */
  public void setTipoFunz(String tipoFunz) {
    this.tipoFunz = tipoFunz;
  }

  /** Gets the value for tipoFunz */
  public String getTipoFunz() {
    return tipoFunz;
  }

  /** Sets the value for descrizioneFunz */
  public void setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
  }

  /** Gets the value for descrizioneFunz */
  public String getDescrizioneFunz() {
    return descrizioneFunz;
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
