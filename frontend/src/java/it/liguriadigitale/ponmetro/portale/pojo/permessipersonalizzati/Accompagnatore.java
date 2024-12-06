package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import java.io.Serializable;
import java.time.LocalDate;

public class Accompagnatore implements Serializable {

  private static final long serialVersionUID = -8877231732827039287L;

  boolean faParteDelNucleoFamigliare;
  ComponenteNucleo componenteNucleoFamigliare;

  String codiceFiscalePerRicercaAccompagnatore;
  String codiceIndividuoAccompagnatore;
  String cartaIdentitaPerRicercaAccompagnatore;

  String codiceFiscaleAccompagnatore;
  String cognomeAccompagnatore;
  String nomeAccompagnatore;
  LocalDate dataNascitaAccompagnatore;
  String luogoNascitaAccompagnatore;
  String provinciaNascitaAccompagnatore;

  String indirizzoResidenzaAccompagnatore;

  int codCivicoResidenzaAccompagnatore;
  int codInternoResidenzaAccompagnatore;
  String luogoResidenzaAccompagnatore;

  String capResidenzaAccompagnatore;
  String cittaResidenzaAccompagnatore;
  String provinciaResidenzaAccompagnatore;

  String telefonoAccompagnatore;
  String cellulareAccompagnatore;
  String emailAccompagnatore;
  String pecAccompagnatore;
  String tipoPatenteAccompagnatore;
  String numeroPantenteAccompagnatore;
  LocalDate dataRilascioPatenteAccompagnatore;
  LocalDate dataScadenzaPatenteAccompagnatore;

  public Accompagnatore() {
    faParteDelNucleoFamigliare = false;
  }

  public String getCodiceFiscalePerRicercaAccompagnatore() {
    return codiceFiscalePerRicercaAccompagnatore;
  }

  public void setCodiceFiscalePerRicercaAccompagnatore(
      String codiceFiscalePerRicercaAccompagnatore) {
    this.codiceFiscalePerRicercaAccompagnatore = codiceFiscalePerRicercaAccompagnatore;
  }

  public String getCartaIdentitaPerRicercaAccompagnatore() {
    return cartaIdentitaPerRicercaAccompagnatore;
  }

  public void setCartaIdentitaPerRicercaAccompagnatore(
      String cartaIdentitaPerRicercaAccompagnatore) {
    this.cartaIdentitaPerRicercaAccompagnatore = cartaIdentitaPerRicercaAccompagnatore;
  }

  public boolean isFaParteDelNucleoFamigliare() {
    return faParteDelNucleoFamigliare;
  }

  public void setFaParteDelNucleoFamigliare(boolean faParteDelNucleoFamigliare) {
    this.faParteDelNucleoFamigliare = faParteDelNucleoFamigliare;
  }

  public String getCodiceFiscaleAccompagnatore() {
    return codiceFiscaleAccompagnatore;
  }

  public void setCodiceFiscaleAccompagnatore(String codiceFiscaleAccompagnatore) {
    this.codiceFiscaleAccompagnatore = codiceFiscaleAccompagnatore;
  }

  public String getCognomeAccompagnatore() {
    return cognomeAccompagnatore;
  }

  public void setCognomeAccompagnatore(String cognomeAccompagnatore) {
    this.cognomeAccompagnatore = cognomeAccompagnatore;
  }

  public String getNomeAccompagnatore() {
    return nomeAccompagnatore;
  }

  public void setNomeAccompagnatore(String nomeAccompagnatore) {
    this.nomeAccompagnatore = nomeAccompagnatore;
  }

  public LocalDate getDataNascitaAccompagnatore() {
    return dataNascitaAccompagnatore;
  }

  public void setDataNascitaAccompagnatore(LocalDate dataNascitaAccompagnatore) {
    this.dataNascitaAccompagnatore = dataNascitaAccompagnatore;
  }

  public String getLuogoNascitaAccompagnatore() {
    return luogoNascitaAccompagnatore;
  }

  public void setLuogoNascitaAccompagnatore(String luogoNascitaAccompagnatore) {
    this.luogoNascitaAccompagnatore = luogoNascitaAccompagnatore;
  }

  public String getProvinciaNascitaAccompagnatore() {
    return provinciaNascitaAccompagnatore;
  }

  public void setProvinciaNascitaAccompagnatore(String provinciaNascitaAccompagnatore) {
    this.provinciaNascitaAccompagnatore = provinciaNascitaAccompagnatore;
  }

  public String getIndirizzoResidenzaAccompagnatore() {
    return indirizzoResidenzaAccompagnatore;
  }

  public void setIndirizzoResidenzaAccompagnatore(String indirizzoResidenzaAccompagnatore) {
    this.indirizzoResidenzaAccompagnatore = indirizzoResidenzaAccompagnatore;
  }

  public String getCapResidenzaAccompagnatore() {
    return capResidenzaAccompagnatore;
  }

  public void setCapResidenzaAccompagnatore(String capResidenzaAccompagnatore) {
    this.capResidenzaAccompagnatore = capResidenzaAccompagnatore;
  }

  public String getCittaResidenzaAccompagnatore() {
    return cittaResidenzaAccompagnatore;
  }

  public void setCittaResidenzaAccompagnatore(String cittaResidenzaAccompagnatore) {
    this.cittaResidenzaAccompagnatore = cittaResidenzaAccompagnatore;
  }

  public String getProvinciaResidenzaAccompagnatore() {
    return provinciaResidenzaAccompagnatore;
  }

  public void setProvinciaResidenzaAccompagnatore(String provinciaResidenzaAccompagnatore) {
    this.provinciaResidenzaAccompagnatore = provinciaResidenzaAccompagnatore;
  }

  public String getTelefonoAccompagnatore() {
    return telefonoAccompagnatore;
  }

  public void setTelefonoAccompagnatore(String telefonoAccompagnatore) {
    this.telefonoAccompagnatore = telefonoAccompagnatore;
  }

  public String getCellulareAccompagnatore() {
    return cellulareAccompagnatore;
  }

  public void setCellulareAccompagnatore(String cellulareAccompagnatore) {
    this.cellulareAccompagnatore = cellulareAccompagnatore;
  }

  public String getEmailAccompagnatore() {
    return emailAccompagnatore;
  }

  public void setEmailAccompagnatore(String emailAccompagnatore) {
    this.emailAccompagnatore = emailAccompagnatore;
  }

  public String getPecAccompagnatore() {
    return pecAccompagnatore;
  }

  public void setPecAccompagnatore(String pecAccompagnatore) {
    this.pecAccompagnatore = pecAccompagnatore;
  }

  public String getTipoPatenteAccompagnatore() {
    return tipoPatenteAccompagnatore;
  }

  public void setTipoPatenteAccompagnatore(String tipoPatenteAccompagnatore) {
    this.tipoPatenteAccompagnatore = tipoPatenteAccompagnatore;
  }

  public String getNumeroPantenteAccompagnatore() {
    return numeroPantenteAccompagnatore;
  }

  public void setNumeroPantenteAccompagnatore(String numeroPantenteAccompagnatore) {
    this.numeroPantenteAccompagnatore = numeroPantenteAccompagnatore;
  }

  public LocalDate getDataRilascioPatenteAccompagnatore() {
    return dataRilascioPatenteAccompagnatore;
  }

  public void setDataRilascioPatenteAccompagnatore(LocalDate dataRilascioPatenteAccompagnatore) {
    this.dataRilascioPatenteAccompagnatore = dataRilascioPatenteAccompagnatore;
  }

  public LocalDate getDataScadenzaPatenteAccompagnatore() {
    return dataScadenzaPatenteAccompagnatore;
  }

  public void setDataScadenzaPatenteAccompagnatore(LocalDate dataScadenzaPatenteAccompagnatore) {
    this.dataScadenzaPatenteAccompagnatore = dataScadenzaPatenteAccompagnatore;
  }

  public ComponenteNucleo getComponenteNucleoFamigliare() {
    return componenteNucleoFamigliare;
  }

  public void setComponenteNucleoFamigliare(ComponenteNucleo componenteNucleoFamigliare) {
    this.componenteNucleoFamigliare = componenteNucleoFamigliare;
  }

  public String getCodiceIndividuoAccompagnatore() {
    return codiceIndividuoAccompagnatore;
  }

  public void setCodiceIndividuoAccompagnatore(String codiceIndividuoAccompagnatore) {
    this.codiceIndividuoAccompagnatore = codiceIndividuoAccompagnatore;
  }

  public int getCodCivicoResidenzaAccompagnatore() {
    return codCivicoResidenzaAccompagnatore;
  }

  public void setCodCivicoResidenzaAccompagnatore(int codCivicoResidenzaAccompagnatore) {
    this.codCivicoResidenzaAccompagnatore = codCivicoResidenzaAccompagnatore;
  }

  public int getCodInternoResidenzaAccompagnatore() {
    return codInternoResidenzaAccompagnatore;
  }

  public void setCodInternoResidenzaAccompagnatore(int codInternoResidenzaAccompagnatore) {
    this.codInternoResidenzaAccompagnatore = codInternoResidenzaAccompagnatore;
  }

  public String getLuogoResidenzaAccompagnatore() {
    return luogoResidenzaAccompagnatore;
  }

  public void setLuogoResidenzaAccompagnatore(String luogoResidenzaAccompagnatore) {
    this.luogoResidenzaAccompagnatore = luogoResidenzaAccompagnatore;
  }
}
