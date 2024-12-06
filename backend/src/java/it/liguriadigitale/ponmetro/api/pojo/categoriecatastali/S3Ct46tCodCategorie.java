package it.liguriadigitale.ponmetro.api.pojo.categoriecatastali;

/**
 * S3Ct46tCodCategorie
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-12-12T16:24:21.211
 */
import java.io.Serializable;

public class S3Ct46tCodCategorie implements Serializable {
  private static final long serialVersionUID = 1L;

  public S3Ct46tCodCategorie() {
    super();
  }

  /** Type : VARCHAR Name : CT46_COD_CATEGORIA */
  public String ct46CodCategoria;

  /** Type : VARCHAR Name : CT46_DESCRIZ */
  public String ct46Descriz;

  /** Sets the value for ct46CodCategoria */
  public void setCt46CodCategoria(String ct46CodCategoria) {
    this.ct46CodCategoria = ct46CodCategoria;
  }

  /** Gets the value for ct46CodCategoria */
  public String getCt46CodCategoria() {
    return ct46CodCategoria;
  }

  /** Sets the value for ct46Descriz */
  public void setCt46Descriz(String ct46Descriz) {
    this.ct46Descriz = ct46Descriz;
  }

  /** Gets the value for ct46Descriz */
  public String getCt46Descriz() {
    return ct46Descriz;
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
