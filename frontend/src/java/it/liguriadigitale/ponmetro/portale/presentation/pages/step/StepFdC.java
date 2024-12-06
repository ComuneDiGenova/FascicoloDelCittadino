package it.liguriadigitale.ponmetro.portale.presentation.pages.step;

import java.io.Serializable;

public class StepFdC implements Serializable {

  private static final long serialVersionUID = -8863764184926997863L;

  private String descrizione;
  private Integer indice;
  private Boolean show;

  public StepFdC(String descrizione, Integer indice, Boolean show) {
    super();
    this.descrizione = descrizione;
    this.indice = indice;
    this.show = show;
  }

  public StepFdC(String descrizione, Integer indice) {
    this(descrizione, indice, true);
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public Integer getIndice() {
    return indice;
  }

  public void setIndice(Integer indice) {
    this.indice = indice;
  }

  public Boolean getShow() {
    return show;
  }

  public void setShow(Boolean show) {
    this.show = show;
  }
}
