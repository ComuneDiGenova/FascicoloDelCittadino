package it.liguriadigitale.ponmetro.portale.pojo.iscrizione;

import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.pojo.MembroNucleoChePercepivaReddito;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffariaDisoccupato;
import java.io.Serializable;
import java.util.List;

public class AgevolazioneStep2 implements Serializable {

  private static final long serialVersionUID = 3065642535770896941L;

  private AgevolazioneStep1 step1;
  private String emailContatto;
  private String telefonoContatto;

  private Integer numeroMinoriACarico;

  List<MembroNucleoChePercepivaReddito> listaMembriNucleo;

  List<DatiAgevolazioneTariffariaDisoccupato> listaMembriConDataDisoccupazione;

  private String comboSiNoPuntoA;

  public String getEmailContatto() {
    return emailContatto;
  }

  public void setEmailContatto(String emailContatto) {
    this.emailContatto = emailContatto;
  }

  public String getTelefonoContatto() {
    return telefonoContatto;
  }

  public void setTelefonoContatto(String telefonoContatto) {
    this.telefonoContatto = telefonoContatto;
  }

  public AgevolazioneStep1 getStep1() {
    return step1;
  }

  public void setStep1(AgevolazioneStep1 step1) {
    this.step1 = step1;
  }

  public Integer getNumeroMinoriACarico() {
    return numeroMinoriACarico;
  }

  public void setNumeroMinoriACarico(Integer numeroMinoriACarico) {
    this.numeroMinoriACarico = numeroMinoriACarico;
  }

  public List<MembroNucleoChePercepivaReddito> getListaMembriNucleo() {
    return listaMembriNucleo;
  }

  public void setListaMembriNucleo(List<MembroNucleoChePercepivaReddito> listaMembriNucleo) {
    this.listaMembriNucleo = listaMembriNucleo;
  }

  public List<DatiAgevolazioneTariffariaDisoccupato> getListaMembriConDataDisoccupazione() {
    return listaMembriConDataDisoccupazione;
  }

  public void setListaMembriConDataDisoccupazione(
      List<DatiAgevolazioneTariffariaDisoccupato> listaMembriConDataDisoccupazione) {
    this.listaMembriConDataDisoccupazione = listaMembriConDataDisoccupazione;
  }

  public String getComboSiNoPuntoA() {
    return comboSiNoPuntoA;
  }

  public void setComboSiNoPuntoA(String comboSiNoPuntoA) {
    this.comboSiNoPuntoA = comboSiNoPuntoA;
  }

  @Override
  public String toString() {
    return "AgevolazioneStep2 [step1="
        + step1
        + ", emailContatto="
        + emailContatto
        + ", telefonoContatto="
        + telefonoContatto
        + ", numeroMinoriACarico="
        + numeroMinoriACarico
        + ", listaMembriNucleo="
        + listaMembriNucleo
        + ", listaMembriConDataDisoccupazione="
        + listaMembriConDataDisoccupazione
        + ", comboSiNoPuntoA="
        + comboSiNoPuntoA
        + "]";
  }
}
