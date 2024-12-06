package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Parentela;
import java.io.Serializable;
import java.time.LocalDate;

public class IndividuiCollegatiImmigrazione implements Serializable {

  private static final long serialVersionUID = -4892131256255973538L;

  private String nome;

  private String cognome;

  private String nominativo;

  private String cf;

  private String sesso;

  private String cittadinanza;

  private LocalDate dataNascita;

  private String comboComuneNascita;

  private String comuneNascita;

  private String comuneNascitaEstero;

  private String codiceComuneNascita;

  private ListItem statoCivile;

  private Parentela parentela;

  private String patenti;

  private String veicoli;

  private String numeroCI;

  private LocalDate dataRilascioCI;

  private LocalDate dataScadenzaCI;

  private String comuneCi;

  private boolean espatrio;

  private String comuneRilascioCI;

  private String codiceComuneRilascioCI;

  private String statoRilascioCI;

  private String cognomeConiuge;

  private String nomeConiuge;

  private LocalDate dataMatrimonio;

  private String professione;

  private String codiceProfessione;

  private ListItem titoloDiStudio;

  private String comuneMatrimonio;

  private String codiceComuneMatrimonio;

  private String codiceIndividuo;

  private int eta;

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

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getSesso() {
    return sesso;
  }

  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public String getCittadinanza() {
    return cittadinanza;
  }

  public void setCittadinanza(String cittadinanza) {
    this.cittadinanza = cittadinanza;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getComboComuneNascita() {
    return comboComuneNascita;
  }

  public void setComboComuneNascita(String comboComuneNascita) {
    this.comboComuneNascita = comboComuneNascita;
  }

  public String getComuneNascita() {
    return comuneNascita;
  }

  public void setComuneNascita(String comuneNascita) {
    this.comuneNascita = comuneNascita;
  }

  public String getComuneNascitaEstero() {
    return comuneNascitaEstero;
  }

  public void setComuneNascitaEstero(String comuneNascitaEstero) {
    this.comuneNascitaEstero = comuneNascitaEstero;
  }

  public String getCodiceComuneNascita() {
    return codiceComuneNascita;
  }

  public void setCodiceComuneNascita(String codiceComuneNascita) {
    this.codiceComuneNascita = codiceComuneNascita;
  }

  public ListItem getStatoCivile() {
    return statoCivile;
  }

  public void setStatoCivile(ListItem statoCivile) {
    this.statoCivile = statoCivile;
  }

  public Parentela getParentela() {
    return parentela;
  }

  public void setParentela(Parentela parentela) {
    this.parentela = parentela;
  }

  public String getPatenti() {
    return patenti;
  }

  public void setPatenti(String patenti) {
    this.patenti = patenti;
  }

  public String getVeicoli() {
    return veicoli;
  }

  public void setVeicoli(String veicoli) {
    this.veicoli = veicoli;
  }

  public String getNumeroCI() {
    return numeroCI;
  }

  public void setNumeroCI(String numeroCI) {
    this.numeroCI = numeroCI;
  }

  public LocalDate getDataRilascioCI() {
    return dataRilascioCI;
  }

  public void setDataRilascioCI(LocalDate dataRilascioCI) {
    this.dataRilascioCI = dataRilascioCI;
  }

  public LocalDate getDataScadenzaCI() {
    return dataScadenzaCI;
  }

  public void setDataScadenzaCI(LocalDate dataScadenzaCI) {
    this.dataScadenzaCI = dataScadenzaCI;
  }

  public String getComuneCi() {
    return comuneCi;
  }

  public void setComuneCi(String comuneCi) {
    this.comuneCi = comuneCi;
  }

  public boolean isEspatrio() {
    return espatrio;
  }

  public void setEspatrio(boolean espatrio) {
    this.espatrio = espatrio;
  }

  public String getComuneRilascioCI() {
    return comuneRilascioCI;
  }

  public void setComuneRilascioCI(String comuneRilascioCI) {
    this.comuneRilascioCI = comuneRilascioCI;
  }

  public String getCodiceComuneRilascioCI() {
    return codiceComuneRilascioCI;
  }

  public void setCodiceComuneRilascioCI(String codiceComuneRilascioCI) {
    this.codiceComuneRilascioCI = codiceComuneRilascioCI;
  }

  public String getStatoRilascioCI() {
    return statoRilascioCI;
  }

  public void setStatoRilascioCI(String statoRilascioCI) {
    this.statoRilascioCI = statoRilascioCI;
  }

  public String getCognomeConiuge() {
    return cognomeConiuge;
  }

  public void setCognomeConiuge(String cognomeConiuge) {
    this.cognomeConiuge = cognomeConiuge;
  }

  public String getNomeConiuge() {
    return nomeConiuge;
  }

  public void setNomeConiuge(String nomeConiuge) {
    this.nomeConiuge = nomeConiuge;
  }

  public LocalDate getDataMatrimonio() {
    return dataMatrimonio;
  }

  public void setDataMatrimonio(LocalDate dataMatrimonio) {
    this.dataMatrimonio = dataMatrimonio;
  }

  public String getProfessione() {
    return professione;
  }

  public void setProfessione(String professione) {
    this.professione = professione;
  }

  public String getCodiceProfessione() {
    return codiceProfessione;
  }

  public void setCodiceProfessione(String codiceProfessione) {
    this.codiceProfessione = codiceProfessione;
  }

  public ListItem getTitoloDiStudio() {
    return titoloDiStudio;
  }

  public void setTitoloDiStudio(ListItem titoloDiStudio) {
    this.titoloDiStudio = titoloDiStudio;
  }

  public String getComuneMatrimonio() {
    return comuneMatrimonio;
  }

  public void setComuneMatrimonio(String comuneMatrimonio) {
    this.comuneMatrimonio = comuneMatrimonio;
  }

  public String getCodiceComuneMatrimonio() {
    return codiceComuneMatrimonio;
  }

  public void setCodiceComuneMatrimonio(String codiceComuneMatrimonio) {
    this.codiceComuneMatrimonio = codiceComuneMatrimonio;
  }

  public String getCodiceIndividuo() {
    return codiceIndividuo;
  }

  public void setCodiceIndividuo(String codiceIndividuo) {
    this.codiceIndividuo = codiceIndividuo;
  }

  public int getEta() {
    return eta;
  }

  public void setEta(int eta) {
    this.eta = eta;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("IndividuiCollegatiImmigrazione [nome=");
    builder.append(nome);
    builder.append(", cognome=");
    builder.append(cognome);
    builder.append(", nominativo=");
    builder.append(nominativo);
    builder.append(", cf=");
    builder.append(cf);
    builder.append(", sesso=");
    builder.append(sesso);
    builder.append(", cittadinanza=");
    builder.append(cittadinanza);
    builder.append(", dataNascita=");
    builder.append(dataNascita);
    builder.append(", comboComuneNascita=");
    builder.append(comboComuneNascita);
    builder.append(", comuneNascita=");
    builder.append(comuneNascita);
    builder.append(", comuneNascitaEstero=");
    builder.append(comuneNascitaEstero);
    builder.append(", codiceComuneNascita=");
    builder.append(codiceComuneNascita);
    builder.append(", statoCivile=");
    builder.append(statoCivile);
    builder.append(", parentela=");
    builder.append(parentela);
    builder.append(", patenti=");
    builder.append(patenti);
    builder.append(", veicoli=");
    builder.append(veicoli);
    builder.append(", numeroCI=");
    builder.append(numeroCI);
    builder.append(", dataRilascioCI=");
    builder.append(dataRilascioCI);
    builder.append(", dataScadenzaCI=");
    builder.append(dataScadenzaCI);
    builder.append(", comuneCi=");
    builder.append(comuneCi);
    builder.append(", espatrio=");
    builder.append(espatrio);
    builder.append(", comuneRilascioCI=");
    builder.append(comuneRilascioCI);
    builder.append(", codiceComuneRilascioCI=");
    builder.append(codiceComuneRilascioCI);
    builder.append(", statoRilascioCI=");
    builder.append(statoRilascioCI);
    builder.append(", cognomeConiuge=");
    builder.append(cognomeConiuge);
    builder.append(", nomeConiuge=");
    builder.append(nomeConiuge);
    builder.append(", dataMatrimonio=");
    builder.append(dataMatrimonio);
    builder.append(", professione=");
    builder.append(professione);
    builder.append(", codiceProfessione=");
    builder.append(codiceProfessione);
    builder.append(", titoloDiStudio=");
    builder.append(titoloDiStudio);
    builder.append(", comuneMatrimonio=");
    builder.append(comuneMatrimonio);
    builder.append(", codiceComuneMatrimonio=");
    builder.append(codiceComuneMatrimonio);
    builder.append(", codiceIndividuo=");
    builder.append(codiceIndividuo);
    builder.append(", eta=");
    builder.append(eta);
    builder.append("]");
    return builder.toString();
  }
}
