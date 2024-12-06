package it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BollettiniMensa implements Serializable {

  private static final long serialVersionUID = -1615704336403043384L;

  private Integer annoSelezionatoDallaCombo;
  private Integer annoDaCercare;
  private Utente impegnato;
  private List<DatiPagamentiBollettiniRiepilogativiEstesi> listDatiPagamentiBollettini;

  public BollettiniMensa(final Utente impegnato) {
    annoSelezionatoDallaCombo = -1;
    annoDaCercare = -1;
  }

  public Utente getImpegnato() {
    return impegnato;
  }

  public void setImpegnato(Utente impegnato) {
    this.impegnato = impegnato;
  }

  public List<DatiPagamentiBollettiniRiepilogativiEstesi>
      getListDatiBollettiniAnnoDaVisualizzareNeiRisultati() {
    return listDatiPagamentiBollettini == null || (!annoSelezionatoDallaCombo.equals(annoDaCercare))
        ? new ArrayList<>()
        : listDatiPagamentiBollettini.stream()
            .filter(item -> item.getAnnoRiferimento().equals(BigDecimal.valueOf(annoDaCercare)))
            .collect(Collectors.toList());
  }

  public List<DatiPagamentiBollettiniRiepilogativiEstesi> getListDatiBollettini() {
    return listDatiPagamentiBollettini;
  }

  public void setListDatiPagamentiPartitario(
      List<DatiPagamentiBollettiniRiepilogativiEstesi> listDatiPagamentiBollettini) {
    this.listDatiPagamentiBollettini = listDatiPagamentiBollettini;
  }

  public void addAllToListDatiPagamentiBollettini(
      List<DatiPagamentiBollettiniRiepilogativiEstesi> listDatiPagamentiBollettiniToAdd) {
    if (listDatiPagamentiBollettini == null) {
      listDatiPagamentiBollettini = new ArrayList<>();
    }
    listDatiPagamentiBollettini.addAll(new ArrayList<>(listDatiPagamentiBollettiniToAdd));
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

  @Override
  public String toString() {
    return "PartitarioMensa [annoSelezionatoDallaCombo="
        + annoSelezionatoDallaCombo
        + ", annoDaCercare="
        + annoDaCercare
        + ", impegnato="
        + impegnato
        + ", listDatiPagamentiBollettini="
        + listDatiPagamentiBollettini
        + "]";
  }
}
