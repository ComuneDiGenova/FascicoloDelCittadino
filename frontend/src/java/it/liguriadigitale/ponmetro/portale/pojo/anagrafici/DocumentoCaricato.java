package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class DocumentoCaricato implements Serializable {

  private static final long serialVersionUID = 53250873281035080L;

  private Integer idPratica;

  private String nomeFile;

  private byte[] byteDocumentoUpload;

  public FileUploadField documentoUpload;

  public Integer getIdPratica() {
    return idPratica;
  }

  public void setIdPratica(Integer idPratica) {
    this.idPratica = idPratica;
  }

  public String getNomeFile() {
    return nomeFile;
  }

  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  public byte[] getByteDocumentoUpload() {
    return byteDocumentoUpload;
  }

  public void setByteDocumentoUpload(byte[] byteDocumentoUpload) {
    this.byteDocumentoUpload = byteDocumentoUpload;
  }

  public FileUploadField getDocumentoUpload() {
    return documentoUpload;
  }

  public void setDocumentoUpload(FileUploadField documentoUpload) {
    this.documentoUpload = documentoUpload;
  }

  @Override
  public String toString() {
    return "DocumentoCaricato [idPratica="
        + idPratica
        + ", nomeFile="
        + nomeFile
        + ", byteDocumentoUpload="
        + Arrays.toString(byteDocumentoUpload)
        + ", documentoUpload="
        + documentoUpload
        + "]";
  }
}
