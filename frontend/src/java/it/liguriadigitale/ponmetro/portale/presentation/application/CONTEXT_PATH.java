package it.liguriadigitale.ponmetro.portale.presentation.application;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;

public enum CONTEXT_PATH {
  NORMALE("/portale/web"),
  DELEGHE("/portale/deleghe");

  private String path;

  private CONTEXT_PATH(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  public static String getPath(Utente utente) {
    if (utente.isUtenteDelegato()) {
      return DELEGHE.getPath();
    } else {
      return NORMALE.getPath();
    }
  }
}
