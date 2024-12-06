package it.liguriadigitale.ponmetro.portale.pojo.menu;

import org.apache.wicket.util.io.IClusterable;

public class Dashboard implements IClusterable {

  /** */
  private static final long serialVersionUID = 1L;

  String nomeDashboard;
  String nomeMenu;
  String nomeGruppo;
  String codiceGruppo;
  Integer ordine;

  String nomeGruppoLabel;
  String nomeFileIconaGruppo;

  public String getNomeGruppoLabel() {
    return nomeGruppoLabel;
  }

  public void setNomeGruppoLabel(final String nomeGruppoLabel) {
    this.nomeGruppoLabel = nomeGruppo;
  }

  public String getNomeGruppo() {
    return nomeGruppo;
  }

  public void setNomeGruppo(final String nomeGruppo) {
    this.nomeGruppo = nomeGruppo;
  }

  public String getNomeDashboard() {
    return nomeDashboard;
  }

  public void setNomeDashboard(final String nomeDashboard) {
    this.nomeDashboard = nomeDashboard;
  }

  public Integer getOrdine() {
    return ordine;
  }

  public void setOrdine(final Integer ordine) {
    this.ordine = ordine;
  }

  public String getCodiceGruppo() {
    return codiceGruppo;
  }

  public void setCodiceGruppo(final String codiceGruppo) {
    this.codiceGruppo = codiceGruppo;
  }

  @Override
  public String toString() {
    return "Dashboard [nomeDashboard="
        + nomeDashboard
        + ", nomeGruppo="
        + nomeGruppo
        + ", codiceGruppo="
        + codiceGruppo
        + ", ordine="
        + ordine
        + ", nomeGruppoLabel="
        + nomeGruppoLabel
        + "]";
  }

  public String getNomeMenu() {
    return nomeMenu;
  }

  public void setNomeMenu(String nomeMenu) {
    this.nomeMenu = nomeMenu;
  }

  public String getNomeFileIconaGruppo() {
    return nomeFileIconaGruppo;
  }

  public void setNomeFileIconaGruppo(String nomeFileIconaGruppo) {
    this.nomeFileIconaGruppo = nomeFileIconaGruppo;
  }
}
