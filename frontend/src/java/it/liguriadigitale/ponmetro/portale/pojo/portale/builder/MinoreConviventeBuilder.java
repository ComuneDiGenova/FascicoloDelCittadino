package it.liguriadigitale.ponmetro.portale.pojo.portale.builder;

import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import java.time.LocalDate;

public class MinoreConviventeBuilder {

  private Residente datiDemografico;

  private String codiceFiscale;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;
  private Long idPerson;
  private AutocertificazioneTipoMinoreEnum tipoParentela;
  private boolean figlioStatoCivile;
  private boolean minoreResidente;
  private boolean autodichiarazioneFiglio;
  private boolean bloccoAutodichiarazione;

  public MinoreConviventeBuilder setDatiDemografico(Residente datiDemografico) {
    this.datiDemografico = datiDemografico;
    return this;
  }

  public MinoreConviventeBuilder setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
    return this;
  }

  public MinoreConviventeBuilder setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public MinoreConviventeBuilder setCognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

  public MinoreConviventeBuilder setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
    return this;
  }

  public MinoreConviventeBuilder setIdPerson(Long idPerson) {
    this.idPerson = idPerson;
    return this;
  }

  public MinoreConviventeBuilder setTipoParentela(AutocertificazioneTipoMinoreEnum tipoParentela) {
    this.tipoParentela = tipoParentela;
    return this;
  }

  public MinoreConviventeBuilder setFiglioStatoCivile(boolean figlioStatoCivile) {
    this.figlioStatoCivile = figlioStatoCivile;
    return this;
  }

  public MinoreConviventeBuilder setMinoreResidente(boolean minoreResidente) {
    this.minoreResidente = minoreResidente;
    return this;
  }

  public MinoreConviventeBuilder setAutodichiarazioneFiglio(boolean autodichiarazioneFiglio) {
    this.autodichiarazioneFiglio = autodichiarazioneFiglio;
    return this;
  }

  public MinoreConviventeBuilder setBloccoAutodichiarazione(boolean bloccoAutodichiarazione) {
    this.bloccoAutodichiarazione = bloccoAutodichiarazione;
    return this;
  }

  public MinoreConvivente build() {
    MinoreConvivente minore = new MinoreConvivente();
    minore.setTipoParentela(tipoParentela);
    minore.setCodiceFiscale(codiceFiscale);
    minore.setCognome(cognome);
    minore.setDataNascita(dataNascita);
    minore.setDatiDemografico(datiDemografico);
    minore.setIdPerson(idPerson);
    minore.setNome(nome);
    minore.setAutodichiarazioneFiglio(autodichiarazioneFiglio);
    minore.setBloccoAutodichiarazione(bloccoAutodichiarazione);
    minore.setMinoreResidente(minoreResidente);
    minore.setFiglioStatoCivile(figlioStatoCivile);
    return minore;
  }
}
