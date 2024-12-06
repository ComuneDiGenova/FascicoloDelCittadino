package it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo;

import java.io.Serializable;

public class FileDaScaricare implements Serializable {

  private static final long serialVersionUID = 8061986608319323370L;

  private boolean esitoOK;
  private String fileName;
  private byte[] fileBytes;
  private String messaggioErrore;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public byte[] getFileBytes() {
    return fileBytes;
  }

  public void setFileBytes(byte[] fileBytes) {
    this.fileBytes = fileBytes;
  }

  public boolean isEsitoOK() {
    return esitoOK;
  }

  public void setEsitoOK(boolean esitoOK) {
    this.esitoOK = esitoOK;
  }

  public String getMessaggioErrore() {
    return messaggioErrore;
  }

  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
  }
}
