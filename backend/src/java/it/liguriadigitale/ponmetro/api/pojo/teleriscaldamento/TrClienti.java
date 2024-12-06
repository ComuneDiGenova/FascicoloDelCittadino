package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

/**
 * TrClienti
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-11-07T15:35:43.453
 */
import java.io.Serializable;

public class TrClienti implements Serializable {
  private static final long serialVersionUID = 1L;

  public TrClienti() {
    super();
  }

  /** Type : VARCHAR Name : NUMERO_CLIENTE */
  public String numeroCliente;

  /** Sets the value for numeroCliente */
  public void setNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
  }

  /** Gets the value for numeroCliente */
  public String getNumeroCliente() {
    return numeroCliente;
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
