package it.liguriadigitale.ponmetro.api.pojo.contatti;

/**
 * CfgTContatti
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2023-08-31T18:12:06.556
 */
import java.io.Serializable;

public class CfgTContatti implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTContatti() {
    super();
  }

  /** Type : BIGINT Name : ID_FCITT */
  public Long idFcitt;

  /** Type : VARCHAR Name : CONTATTO */
  public String contatto;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Type : TIMESTAMP Name : DATA_AGG */
  public java.time.LocalDateTime dataAgg;

  /** Type : VARCHAR Name : TIPO */
  public String tipo;

  /** Sets the value for idFcitt */
  public void setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
  }

  /** Gets the value for idFcitt */
  public Long getIdFcitt() {
    return idFcitt;
  }

  /** Sets the value for contatto */
  public void setContatto(String contatto) {
    this.contatto = contatto;
  }

  /** Gets the value for contatto */
  public String getContatto() {
    return contatto;
  }

  /** Sets the value for dataIns */
  public void setDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
  }

  /** Gets the value for dataIns */
  public java.time.LocalDateTime getDataIns() {
    return dataIns;
  }

  /** Sets the value for dataAgg */
  public void setDataAgg(java.time.LocalDateTime dataAgg) {
    this.dataAgg = dataAgg;
  }

  /** Gets the value for dataAgg */
  public java.time.LocalDateTime getDataAgg() {
    return dataAgg;
  }

  /** Sets the value for tipo */
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  /** Gets the value for tipo */
  public String getTipo() {
    return tipo;
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
