package it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import java.io.Serializable;

public class BreadcrumbFdC implements Serializable {

  private static final long serialVersionUID = -2507258326049975687L;

  private String mountedPath;
  private String descrizione;
  private String nomeClassePaginaPadre;
  private String contextPath;
  private FunzioniDisponibili funzione;

  public BreadcrumbFdC(String mountedPath, String descrizione) {
    super();
    this.setMountedPath(mountedPath);
    this.setDescrizione(descrizione);
  }

  public String getMountedPath() {
    return mountedPath;
  }

  public void setMountedPath(String mountedPath) {
    this.mountedPath = mountedPath;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public String getNomeClassePaginaPadre() {
    return nomeClassePaginaPadre;
  }

  public void setNomeClassePaginaPadre(String nomeClassePaginaPadre) {
    this.nomeClassePaginaPadre = nomeClassePaginaPadre;
  }

  public FunzioniDisponibili getFunzione() {
    return funzione;
  }

  public void setFunzione(FunzioniDisponibili funzione) {
    this.funzione = funzione;
  }

  public String getContextPath() {
    return contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  @Override
  public String toString() {
    return "BreadcrumbFdC [mountedPath="
        + mountedPath
        + ", descrizione="
        + descrizione
        + ", nomeClassePaginaPadre="
        + nomeClassePaginaPadre
        + ", contextPath="
        + contextPath
        + ", funzione="
        + funzione
        + "]";
  }
}
