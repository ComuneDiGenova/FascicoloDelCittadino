package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import java.io.Serializable;

public class UploadPatente implements Serializable {

  private static final long serialVersionUID = 8300156303560096099L;

  // private FileUploadField fileUploadField;

  private String uploadPatente;

  /*
   * public FileUploadField getFileUploadField() { return fileUploadField; }
   *
   * public void setFileUploadField(FileUploadField fileUploadField) {
   * this.fileUploadField = fileUploadField; }
   */

  public String getUploadPatente() {
    return uploadPatente;
  }

  public void setUploadPatente(String uploadPatente) {
    this.uploadPatente = uploadPatente;
  }

  @Override
  public String toString() {
    return "UploadPatente [uploadPatente=" + uploadPatente + "]";
  }
}
