package it.liguriadigitale.ponmetro.portale.pojo.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.IndPrincEnum;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.RecPrefEnum;
import java.io.Serializable;
import java.time.LocalDate;

public class BibliotecheIscrizione implements Serializable {

  private static final long serialVersionUID = 4666364199117421937L;

  private String nome;

  private String cognome;

  private String sesso;

  private LocalDate dataNascita;

  private String luogoNascita;

  private String codiceFiscale;

  private String numeroCI;

  private String tipoCI;

  private String enteCI;

  private String luogoCI;

  private LocalDate dataRilascioCI;

  private LocalDate dataScadenzaCI;

  private String nomeGenitore;

  private String cognomeGenitore;

  private String numeroCIGenitore;

  private String tipoCIGenitore;

  private String enteCIGenitore;

  private String luogoCIGenitore;

  private LocalDate dataRilascioCIGenitore;

  private LocalDate dataScadenzaCIGenitore;

  private String indirizzoResidenza;

  private String capResidenza;

  private String cittaResidenza;

  private String provinciaResidenza;

  private String statoResidenza;

  private IndPrincEnum indirizzoPrincipale;

  private String email;

  private String cellulare;

  private RecPrefEnum recapitoPreferenziale;

  private String codiceTipoUtente;

  private boolean autorizzazioneTrattamentoDati;

  private boolean autorizzazioneMail;

  private Long idSebina;

  // private TabellaPaeseRecord country;

  private TabellaPaeseRecord autocompleteCittadinanza;

  //	public TabellaPaeseRecord getCountry() {
  //		return country;
  //	}
  //
  //	public void setCountry(TabellaPaeseRecord country) {
  //		this.country = country;
  //	}

  public String getNome() {
    return nome;
  }

  public TabellaPaeseRecord getAutocompleteCittadinanza() {
    return autocompleteCittadinanza;
  }

  public void setAutocompleteCittadinanza(TabellaPaeseRecord autocompleteCittadinanza) {
    this.autocompleteCittadinanza = autocompleteCittadinanza;
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

  public String getSesso() {
    return sesso;
  }

  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getLuogoNascita() {
    return luogoNascita;
  }

  public void setLuogoNascita(String luogoNascita) {
    this.luogoNascita = luogoNascita;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getIndirizzoResidenza() {
    return indirizzoResidenza;
  }

  public void setIndirizzoResidenza(String indirizzoResidenza) {
    this.indirizzoResidenza = indirizzoResidenza;
  }

  public String getCapResidenza() {
    return capResidenza;
  }

  public void setCapResidenza(String capResidenza) {
    this.capResidenza = capResidenza;
  }

  public String getCittaResidenza() {
    return cittaResidenza;
  }

  public void setCittaResidenza(String cittaResidenza) {
    this.cittaResidenza = cittaResidenza;
  }

  public String getProvinciaResidenza() {
    return provinciaResidenza;
  }

  public void setProvinciaResidenza(String provinciaResidenza) {
    this.provinciaResidenza = provinciaResidenza;
  }

  public String getStatoResidenza() {
    return statoResidenza;
  }

  public void setStatoResidenza(String statoResidenza) {
    this.statoResidenza = statoResidenza;
  }

  public IndPrincEnum getIndirizzoPrincipale() {
    return indirizzoPrincipale;
  }

  public void setIndirizzoPrincipale(IndPrincEnum indirizzoPrincipale) {
    this.indirizzoPrincipale = indirizzoPrincipale;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  public RecPrefEnum getRecapitoPreferenziale() {
    return recapitoPreferenziale;
  }

  public void setRecapitoPreferenziale(RecPrefEnum recapitoPreferenziale) {
    this.recapitoPreferenziale = recapitoPreferenziale;
  }

  public String getCodiceTipoUtente() {
    return codiceTipoUtente;
  }

  public void setCodiceTipoUtente(String codiceTipoUtente) {
    this.codiceTipoUtente = codiceTipoUtente;
  }

  public boolean isAutorizzazioneTrattamentoDati() {
    return autorizzazioneTrattamentoDati;
  }

  public void setAutorizzazioneTrattamentoDati(boolean autorizzazioneTrattamentoDati) {
    this.autorizzazioneTrattamentoDati = autorizzazioneTrattamentoDati;
  }

  public String getNumeroCI() {
    return numeroCI;
  }

  public void setNumeroCI(String numeroCI) {
    this.numeroCI = numeroCI;
  }

  public String getTipoCI() {
    return tipoCI;
  }

  public void setTipoCI(String tipoCI) {
    this.tipoCI = tipoCI;
  }

  public String getEnteCI() {
    return enteCI;
  }

  public void setEnteCI(String enteCI) {
    this.enteCI = enteCI;
  }

  public String getLuogoCI() {
    return luogoCI;
  }

  public void setLuogoCI(String luogoCI) {
    this.luogoCI = luogoCI;
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

  public boolean isAutorizzazioneMail() {
    return autorizzazioneMail;
  }

  public void setAutorizzazioneMail(boolean autorizzazioneMail) {
    this.autorizzazioneMail = autorizzazioneMail;
  }

  public String getNumeroCIGenitore() {
    return numeroCIGenitore;
  }

  public void setNumeroCIGenitore(String numeroCIGenitore) {
    this.numeroCIGenitore = numeroCIGenitore;
  }

  public String getTipoCIGenitore() {
    return tipoCIGenitore;
  }

  public void setTipoCIGenitore(String tipoCIGenitore) {
    this.tipoCIGenitore = tipoCIGenitore;
  }

  public String getEnteCIGenitore() {
    return enteCIGenitore;
  }

  public void setEnteCIGenitore(String enteCIGenitore) {
    this.enteCIGenitore = enteCIGenitore;
  }

  public String getLuogoCIGenitore() {
    return luogoCIGenitore;
  }

  public void setLuogoCIGenitore(String luogoCIGenitore) {
    this.luogoCIGenitore = luogoCIGenitore;
  }

  public LocalDate getDataRilascioCIGenitore() {
    return dataRilascioCIGenitore;
  }

  public void setDataRilascioCIGenitore(LocalDate dataRilascioCIGenitore) {
    this.dataRilascioCIGenitore = dataRilascioCIGenitore;
  }

  public LocalDate getDataScadenzaCIGenitore() {
    return dataScadenzaCIGenitore;
  }

  public void setDataScadenzaCIGenitore(LocalDate dataScadenzaCIGenitore) {
    this.dataScadenzaCIGenitore = dataScadenzaCIGenitore;
  }

  public String getNomeGenitore() {
    return nomeGenitore;
  }

  public void setNomeGenitore(String nomeGenitore) {
    this.nomeGenitore = nomeGenitore;
  }

  public String getCognomeGenitore() {
    return cognomeGenitore;
  }

  public void setCognomeGenitore(String cognomeGenitore) {
    this.cognomeGenitore = cognomeGenitore;
  }

  public Long getIdSebina() {
    return idSebina;
  }

  public void setIdSebina(Long idSebina) {
    this.idSebina = idSebina;
  }

  @Override
  public String toString() {
    return "BibliotecheIscrizione [nome="
        + nome
        + ", cognome="
        + cognome
        + ", sesso="
        + sesso
        + ", dataNascita="
        + dataNascita
        + ", luogoNascita="
        + luogoNascita
        + ", codiceFiscale="
        + codiceFiscale
        + ", numeroCI="
        + numeroCI
        + ", tipoCI="
        + tipoCI
        + ", enteCI="
        + enteCI
        + ", luogoCI="
        + luogoCI
        + ", dataRilascioCI="
        + dataRilascioCI
        + ", dataScadenzaCI="
        + dataScadenzaCI
        + ", nomeGenitore="
        + nomeGenitore
        + ", cognomeGenitore="
        + cognomeGenitore
        + ", numeroCIGenitore="
        + numeroCIGenitore
        + ", tipoCIGenitore="
        + tipoCIGenitore
        + ", enteCIGenitore="
        + enteCIGenitore
        + ", luogoCIGenitore="
        + luogoCIGenitore
        + ", dataRilascioCIGenitore="
        + dataRilascioCIGenitore
        + ", dataScadenzaCIGenitore="
        + dataScadenzaCIGenitore
        + ", indirizzoResidenza="
        + indirizzoResidenza
        + ", capResidenza="
        + capResidenza
        + ", cittaResidenza="
        + cittaResidenza
        + ", provinciaResidenza="
        + provinciaResidenza
        + ", statoResidenza="
        + statoResidenza
        + ", indirizzoPrincipale="
        + indirizzoPrincipale
        + ", email="
        + email
        + ", cellulare="
        + cellulare
        + ", recapitoPreferenziale="
        + recapitoPreferenziale
        + ", codiceTipoUtente="
        + codiceTipoUtente
        + ", autorizzazioneTrattamentoDati="
        + autorizzazioneTrattamentoDati
        + ", autorizzazioneMail="
        + autorizzazioneMail
        + ", idSebina="
        + idSebina
        + ", autocompleteCittadinanza="
        + autocompleteCittadinanza
        + "]";
  }
}
