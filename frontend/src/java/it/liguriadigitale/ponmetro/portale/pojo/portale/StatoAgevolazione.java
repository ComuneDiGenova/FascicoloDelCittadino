package it.liguriadigitale.ponmetro.portale.pojo.portale;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import java.io.Serializable;
import java.time.LocalDate;

public class StatoAgevolazione implements Serializable {

  private static final long serialVersionUID = -1844290949314829845L;
  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private String annoScolastico;
  private DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione;
  private String codicePDF;

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  public DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum getStatoRichiestaAgevolazione() {
    return statoRichiestaAgevolazione;
  }

  public void setStatoRichiestaAgevolazione(
      DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione) {
    this.statoRichiestaAgevolazione = statoRichiestaAgevolazione;
  }

  public String getCodicePDF() {
    return codicePDF;
  }

  public void setCodicePDF(String codicePDF) {
    this.codicePDF = codicePDF;
  }
}
