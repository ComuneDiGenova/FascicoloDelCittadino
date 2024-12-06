package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.pojo;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;

public class UtenteServiziRistorazioneDieteSpeciali implements Serializable {

  private static final long serialVersionUID = 5760862600981298448L;

  private UtenteServiziRistorazione iscritto;

  private DatiDietaSpecialeValida dietaValida;

  public UtenteServiziRistorazione getIscritto() {
    return iscritto;
  }

  public void setIscritto(UtenteServiziRistorazione iscritto) {
    this.iscritto = iscritto;
  }

  public DatiDietaSpecialeValida getDietaValida() {
    return dietaValida;
  }

  public void setDietaValida(DatiDietaSpecialeValida dietaValida) {
    this.dietaValida = dietaValida;
  }

  @Override
  public String toString() {
    return "UtenteServiziRistorazioneDieteSpeciali [iscritto="
        + iscritto
        + ", dietaValida="
        + dietaValida
        + "]";
  }
}
