package it.liguriadigitale.ponmetro.portale.presentation.application;

public enum FUNZIONI_PATH {
  DELEGABILI("delegabili"),
  NON_DELEGABILI("nondelegabili");

  private String path;

  private FUNZIONI_PATH(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
