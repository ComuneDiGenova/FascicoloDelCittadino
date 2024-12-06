package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import java.io.Serializable;

public class AllegatoPermessiPersonalizzati implements Serializable {

  private static final long serialVersionUID = -4113780750640882442L;

  private String descrizioneAllegato;
  private String uploadFile;
  private byte[] byteFile;
  private String nomeFile;
  // private FileUpload fileUploadField;
  private String codiceAllegato;
  private int dimensioneFile;

  public String getDescrizioneAllegato() {
    return descrizioneAllegato;
  }

  public void setDescrizioneAllegato(String descrizioneAllegato) {
    this.descrizioneAllegato = descrizioneAllegato;
  }

  public String getCodiceAllegato() {
    return codiceAllegato;
  }

  public void setCodiceAllegato(String codiceAllegato) {
    this.codiceAllegato = codiceAllegato;
  }

  public byte[] getByteFile() {
    return byteFile;
  }

  public void setByteFile(byte[] byteFile) {
    this.byteFile = byteFile;
  }

  public String getNomeFile() {
    return nomeFile;
  }

  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  //	public FileUpload getFileUploadField() {
  //		return fileUploadField;
  //	}
  //
  //	public void setFileUploadField(FileUpload fileUploadField) {
  //		this.fileUploadField = fileUploadField;
  //	}

  public String getUploadFile() {
    return uploadFile;
  }

  public void setUploadFile(String uploadFile) {
    this.uploadFile = uploadFile;
  }

  public int getDimensioneFile() {
    return dimensioneFile;
  }

  public void setDimensioneFile(int dimensioneFile) {
    this.dimensioneFile = dimensioneFile;
  }
}
