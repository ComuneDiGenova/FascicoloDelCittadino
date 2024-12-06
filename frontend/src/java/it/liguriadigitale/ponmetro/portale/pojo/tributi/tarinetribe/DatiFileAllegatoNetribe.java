package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.Serializable;

public class DatiFileAllegatoNetribe implements Serializable {

  private boolean isScaricato;

  private FileAllegato fileAllegatoNetribe;

  private String messaggioErrore;

  public boolean isScaricato() {
    return isScaricato;
  }

  public void setScaricato(boolean isScaricato) {
    this.isScaricato = isScaricato;
  }

  public FileAllegato getFileAllegatoNetribe() {
    return fileAllegatoNetribe;
  }

  public void setFileAllegatoNetribe(FileAllegato fileAllegatoNetribe) {
    this.fileAllegatoNetribe = fileAllegatoNetribe;
  }

  public String getMessaggioErrore() {
    return messaggioErrore;
  }

  public void setMessaggioErrore(String messaggioErrore) {
    this.messaggioErrore = messaggioErrore;
  }

  @Override
  public String toString() {
    return "DatiFileAllegatoNetribe [isScaricato="
        + isScaricato
        + ", fileAllegatoNetribe="
        + fileAllegatoNetribe
        + ", messaggioErrore="
        + messaggioErrore
        + "]";
  }
}
