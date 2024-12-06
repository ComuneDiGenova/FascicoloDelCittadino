package it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiPartitario;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoPagamenti;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartitarioMensa implements Serializable {

  private static final long serialVersionUID = 4474872583870378493L;

  private Integer annoSelezionatoDallaCombo;
  private Integer annoDaCercare;
  private UtenteServiziRistorazione iscritto;
  private List<DatiPagamentiPartitario> listDatiPagamentiPartitario;
  private List<DatiStatoPagamenti> listaStatoPagamentiPartitario;

  public PartitarioMensa(final UtenteServiziRistorazione iscritto) {
    this.iscritto = iscritto;
    annoSelezionatoDallaCombo = -1;
    annoDaCercare = -1;
  }

  public UtenteServiziRistorazione getIscritto() {
    return iscritto;
  }

  public void setIscritto(UtenteServiziRistorazione iscritto) {
    this.iscritto = iscritto;
  }

  public List<DatiPagamentiPartitario>
      getListDatiPagamentiPartitarioAnnoDaVisualizzareNeiRisultati() {
    return !annoSelezionatoDallaCombo.equals(annoDaCercare)
        ? new ArrayList<>()
        : listDatiPagamentiPartitario.stream()
            .filter(item -> item.getAnno().equals(annoDaCercare.toString()))
            .collect(Collectors.toList());
  }

  public List<DatiPagamentiPartitario> getListDatiPagamentiPartitario() {
    return listDatiPagamentiPartitario;
  }

  public void setListDatiPagamentiPartitario(
      List<DatiPagamentiPartitario> listDatiPagamentiPartitario) {
    this.listDatiPagamentiPartitario = listDatiPagamentiPartitario;
  }

  public void addAllToListDatiPagamentiPartitario(
      List<DatiPagamentiPartitario> listDatiPagamentiPartitarioToAdd) {
    if (listDatiPagamentiPartitario == null) {
      listDatiPagamentiPartitario = new ArrayList<>();
    }
    listDatiPagamentiPartitario.addAll(new ArrayList<>(listDatiPagamentiPartitarioToAdd));
  }

  public void addAllToListaDatiStatoPagamentiPartitario(
      List<DatiStatoPagamenti> listaStatoPagamentiToAdd) {
    if (listaStatoPagamentiPartitario == null) {
      listaStatoPagamentiPartitario = new ArrayList<>();
    }
    listaStatoPagamentiPartitario.addAll(new ArrayList<>(listaStatoPagamentiToAdd));
  }

  public Integer getAnnoSelezionatoDallaCombo() {
    return annoSelezionatoDallaCombo;
  }

  public void setAnnoSelezionatoDallaCombo(Integer annoSelezionatoDallaCombo) {
    this.annoSelezionatoDallaCombo =
        annoSelezionatoDallaCombo != null ? annoSelezionatoDallaCombo : -1;
  }

  public Integer getAnnoDaCercare() {
    return annoDaCercare;
  }

  public void setAnnoDaCercare(Integer annoDaCercare) {
    this.annoDaCercare = annoDaCercare != null ? annoDaCercare : -1;
  }

  public List<DatiStatoPagamenti> getListaStatoPagamentiPartitario() {
    return listaStatoPagamentiPartitario;
  }

  public void setListaStatoPagamentiPartitario(
      List<DatiStatoPagamenti> listaStatoPagamentiPartitario) {
    this.listaStatoPagamentiPartitario = listaStatoPagamentiPartitario;
  }

  @Override
  public String toString() {
    return "PartitarioMensa [annoSelezionatoDallaCombo="
        + annoSelezionatoDallaCombo
        + ", annoDaCercare="
        + annoDaCercare
        + ", iscritto="
        + iscritto
        + ", listDatiPagamentiPartitario="
        + listDatiPagamentiPartitario
        + ", listaStatoPagamentiPartitario="
        + listaStatoPagamentiPartitario
        + "]";
  }
}
