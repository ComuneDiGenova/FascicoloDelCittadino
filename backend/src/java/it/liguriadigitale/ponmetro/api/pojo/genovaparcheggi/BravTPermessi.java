package it.liguriadigitale.ponmetro.api.pojo.genovaparcheggi;

/**
 * BravTPermessi
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-11-14T12:14:51.262
 */
import java.io.Serializable;

public class BravTPermessi implements Serializable {
  private static final long serialVersionUID = 1L;

  public BravTPermessi() {
    super();
  }

  /** Type : BIGINT Name : ID_PERMESSO */
  public java.math.BigDecimal idPermesso;

  /** Type : BIGINT Name : ID_FUNZ */
  public java.math.BigDecimal idFunz;

  /** Type : VARCHAR Name : TIPO_FUNZ */
  public String tipoFunz;

  /** Type : VARCHAR Name : DESCRIZIONE_FUNZ */
  public String descrizioneFunz;

  /** Sets the value for idPermesso */
  public void setIdPermesso(java.math.BigDecimal idPermesso) {
    this.idPermesso = idPermesso;
  }

  /** Gets the value for idPermesso */
  public java.math.BigDecimal getIdPermesso() {
    return idPermesso;
  }

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
