package it.liguriadigitale.ponmetro.portale.pojo.mieiverbali;

import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import java.io.Serializable;
import java.util.Arrays;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class DatiRichiestaIstanzaPlDocumentoCaricato implements Serializable {

  private static final long serialVersionUID = 53250873281035080L;

  private Integer idIstanza;

  private String tipoFile;

  private String nomeFile;

  private byte[] byteDocumentoUpload;

  private FileUploadField documentoUpload;

  private DatiDocumento datiDocumento;

  public Integer getIdIstanza() {
    return idIstanza;
  }

  public void setIdIstanza(Integer idIstanza) {
    this.idIstanza = idIstanza;
  }

  public String getTipoFile() {
    return tipoFile;
  }

  public void setTipoFile(String tipoFile) {
    this.tipoFile = tipoFile;
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

  public DatiDocumento getDatiDocumento() {
    return datiDocumento;
  }

  public void setDatiDocumento(DatiDocumento datiDocumento) {
    this.datiDocumento = datiDocumento;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "DatiRichiestaIstanzaPlDocumentoCaricato [idIstanza="
        + idIstanza
        + ", tipoFile="
        + tipoFile
        + ", nomeFile="
        + nomeFile
        + ", byteDocumentoUpload="
        + Arrays.toString(byteDocumentoUpload)
        + ", documentoUpload="
        + documentoUpload
        + ", datiDocumento="
        + datiDocumento
        + "]";
  }
}
