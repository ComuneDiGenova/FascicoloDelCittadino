package it.liguriadigitale.ponmetro.portale.pojo.portale.builder;

import it.liguriadigitale.ponmetro.portale.pojo.portale.StatoAgevolazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria;
import java.time.LocalDate;

public class StatoAgevolazioneBuilder {
  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private String annoScolastico;
  private DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione;
  private String codicePDF;

  public StatoAgevolazioneBuilder setAnnoScolastico(String annoScolastico) {
    this.annoScolastico = annoScolastico;
    return this;
  }

  public StatoAgevolazioneBuilder setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
    return this;
  }

  public StatoAgevolazioneBuilder setCodicePDF(String codicePDF) {
    this.codicePDF = codicePDF;
    return this;
  }

  public StatoAgevolazioneBuilder setStatoRichiestaAgevolazione(
      DatiAgevolazioneTariffaria.StatoRichiestaAgevolazioneEnum statoRichiestaAgevolazione) {
    this.statoRichiestaAgevolazione = statoRichiestaAgevolazione;
    return this;
  }

  public StatoAgevolazioneBuilder setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

  public StatoAgevolazioneBuilder setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public StatoAgevolazioneBuilder setCognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

  public StatoAgevolazione build() {
    StatoAgevolazione atr = new StatoAgevolazione();
    atr.setAnnoScolastico(annoScolastico);
    atr.setCodiceFiscale(codiceFiscale);
    atr.setCognome(cognome);
    atr.setDataNascita(dataNascita);
    atr.setNome(nome);
    atr.setCodicePDF(codicePDF);
    atr.setStatoRichiestaAgevolazione(statoRichiestaAgevolazione);
    return atr;
  }
}
