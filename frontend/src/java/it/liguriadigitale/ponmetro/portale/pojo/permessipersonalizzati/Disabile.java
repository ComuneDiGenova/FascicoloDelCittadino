package it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati;

import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.EnumTipoDomandaPermessoPersonalizzato;
import java.io.Serializable;
import java.time.LocalDate;

public class Disabile implements Serializable {

  private static final long serialVersionUID = -485625038560513660L;

  boolean faParteDelNucleoFamigliare;
  ComponenteNucleo componenteNucleoFamigliare;

  boolean isResidenteInGenova;

  boolean richiedente;
  String codiceFiscalePerRicercaDisabile;
  String cartaIdentitaPerRicercaDisabile;

  String codiceFiscaleDisabile;
  String codiceIndividuoDisabile;
  String cognomeDisabile;
  String nomeDisabile;
  LocalDate dataNascitaDisabile;
  String luogoNascitaDisabile;
  String provinciaNascitaDisabile;

  String indirizzoResidenzaDisabile;
  FeaturesGeoserver indirizzoNonResidente;

  int codCivicoResidenzaDisabile;
  int codInternoResidenzaDisabile;
  String luogoResidenzaDisabile;

  String capResidenzaDisabile;
  String cittaResidenzaDisabile;
  String provinciaResidenzaDisabile;

  String telefonoDisabile;
  String cellulareDisabile;
  String emailDisabile;
  String pecDisabile;
  String tipoPatenteDisabile;
  String numeroPantenteDisabile;
  LocalDate dataRilascioPatenteDisabile;
  LocalDate dataScadenzaPatenteDisabile;

  String numeroContrassegnoH;
  LocalDate dataRilascioContrassegnoH;
  LocalDate dataScadenzaContrassegnoH;
  PermitVerificationResult datiPermessoDisabile;

  public Disabile() {
    faParteDelNucleoFamigliare = false;
  }

  public boolean isRichiedente() {
    return richiedente;
  }

  public void setRichiedente(boolean richiedente) {
    this.richiedente = richiedente;
  }

  public int getCodCivicoResidenzaDisabile() {
    return codCivicoResidenzaDisabile;
  }

  public void setCodCivicoResidenzaDisabile(int codCivicoResidenzaDisabile) {
    this.codCivicoResidenzaDisabile = codCivicoResidenzaDisabile;
  }

  public int getCodInternoResidenzaDisabile() {
    return codInternoResidenzaDisabile;
  }

  public void setCodInternoResidenzaDisabile(int codInternoResidenzaDisabile) {
    this.codInternoResidenzaDisabile = codInternoResidenzaDisabile;
  }

  public String getLuogoResidenzaDisabile() {
    return luogoResidenzaDisabile;
  }

  public void setLuogoResidenzaDisabile(String luogoResidenzaDisabile) {
    this.luogoResidenzaDisabile = luogoResidenzaDisabile;
  }

  public boolean isFaParteDelNucleoFamigliare() {
    return faParteDelNucleoFamigliare;
  }

  public void setFaParteDelNucleoFamigliare(boolean faParteDelNucleoFamigliare) {
    this.faParteDelNucleoFamigliare = faParteDelNucleoFamigliare;
  }

  public ComponenteNucleo getComponenteNucleoFamigliare() {
    return componenteNucleoFamigliare;
  }

  public void setComponenteNucleoFamigliare(ComponenteNucleo componenteNucleoFamigliare) {
    this.componenteNucleoFamigliare = componenteNucleoFamigliare;
  }

  public String getCodiceFiscalePerRicercaDisabile() {
    return codiceFiscalePerRicercaDisabile;
  }

  public void setCodiceFiscalePerRicercaDisabile(String codiceFiscalePerRicercaDisabile) {
    this.codiceFiscalePerRicercaDisabile = codiceFiscalePerRicercaDisabile;
  }

  public String getCartaIdentitaPerRicercaDisabile() {
    return cartaIdentitaPerRicercaDisabile;
  }

  public void setCartaIdentitaPerRicercaDisabile(String cartaIdentitaPerRicercaDisabile) {
    this.cartaIdentitaPerRicercaDisabile = cartaIdentitaPerRicercaDisabile;
  }

  public String getCodiceFiscaleDisabile() {
    return codiceFiscaleDisabile;
  }

  public void setCodiceFiscaleDisabile(String codiceFiscaleDisabile) {
    this.codiceFiscaleDisabile = codiceFiscaleDisabile;
  }

  public String getCognomeDisabile() {
    return cognomeDisabile;
  }

  public void setCognomeDisabile(String cognomeDisabile) {
    this.cognomeDisabile = cognomeDisabile;
  }

  public String getNomeDisabile() {
    return nomeDisabile;
  }

  public void setNomeDisabile(String nomeDisabile) {
    this.nomeDisabile = nomeDisabile;
  }

  public LocalDate getDataNascitaDisabile() {
    return dataNascitaDisabile;
  }

  public void setDataNascitaDisabile(LocalDate dataNascitaDisabile) {
    this.dataNascitaDisabile = dataNascitaDisabile;
  }

  public String getLuogoNascitaDisabile() {
    return luogoNascitaDisabile;
  }

  public void setLuogoNascitaDisabile(String luogoNascitaDisabile) {
    this.luogoNascitaDisabile = luogoNascitaDisabile;
  }

  public String getProvinciaNascitaDisabile() {
    return provinciaNascitaDisabile;
  }

  public void setProvinciaNascitaDisabile(String provinciaNascitaDisabile) {
    this.provinciaNascitaDisabile = provinciaNascitaDisabile;
  }

  public String getIndirizzoResidenzaDisabile() {
    return indirizzoResidenzaDisabile;
  }

  public void setIndirizzoResidenzaDisabile(String indirizzoResidenzaDisabile) {
    this.indirizzoResidenzaDisabile = indirizzoResidenzaDisabile;
  }

  public String getCapResidenzaDisabile() {
    return capResidenzaDisabile;
  }

  public void setCapResidenzaDisabile(String capResidenzaDisabile) {
    this.capResidenzaDisabile = capResidenzaDisabile;
  }

  public String getCittaResidenzaDisabile() {
    return cittaResidenzaDisabile;
  }

  public void setCittaResidenzaDisabile(String cittaResidenzaDisabile) {
    this.cittaResidenzaDisabile = cittaResidenzaDisabile;
  }

  public String getProvinciaResidenzaDisabile() {
    return provinciaResidenzaDisabile;
  }

  public void setProvinciaResidenzaDisabile(String provinciaResidenzaDisabile) {
    this.provinciaResidenzaDisabile = provinciaResidenzaDisabile;
  }

  public String getNumeroContrassegnoH() {
    return numeroContrassegnoH;
  }

  public void setNumeroContrassegnoH(String numeroContrassegnoH) {
    this.numeroContrassegnoH = numeroContrassegnoH;
  }

  public LocalDate getDataRilascioContrassegnoH() {
    return dataRilascioContrassegnoH;
  }

  public void setDataRilascioContrassegnoH(LocalDate dataRilascioContrassegnoH) {
    this.dataRilascioContrassegnoH = dataRilascioContrassegnoH;
  }

  public LocalDate getDataScadenzaContrassegnoH() {
    return dataScadenzaContrassegnoH;
  }

  public void setDataScadenzaContrassegnoH(LocalDate dataScadenzaContrassegnoH) {
    this.dataScadenzaContrassegnoH = dataScadenzaContrassegnoH;
  }

  public String getTelefonoDisabile() {
    return telefonoDisabile;
  }

  public void setTelefonoDisabile(String telefonoDisabile) {
    this.telefonoDisabile = telefonoDisabile;
  }

  public String getCellulareDisabile() {
    return cellulareDisabile;
  }

  public void setCellulareDisabile(String cellulareDisabile) {
    this.cellulareDisabile = cellulareDisabile;
  }

  public String getEmailDisabile() {
    return emailDisabile;
  }

  public void setEmailDisabile(String emailDisabile) {
    this.emailDisabile = emailDisabile;
  }

  public String getPecDisabile() {
    return pecDisabile;
  }

  public void setPecDisabile(String pecDisabile) {
    this.pecDisabile = pecDisabile;
  }

  public String getTipoPatenteDisabile() {
    return tipoPatenteDisabile;
  }

  public void setTipoPatenteDisabile(String tipoPatenteDisabile) {
    this.tipoPatenteDisabile = tipoPatenteDisabile;
  }

  public String getNumeroPantenteDisabile() {
    return numeroPantenteDisabile;
  }

  public void setNumeroPantenteDisabile(String numeroPantenteDisabile) {
    this.numeroPantenteDisabile = numeroPantenteDisabile;
  }

  public LocalDate getDataRilascioPatenteDisabile() {
    return dataRilascioPatenteDisabile;
  }

  public void setDataRilascioPatenteDisabile(LocalDate dataRilascioPatenteDisabile) {
    this.dataRilascioPatenteDisabile = dataRilascioPatenteDisabile;
  }

  public LocalDate getDataScadenzaPatenteDisabile() {
    return dataScadenzaPatenteDisabile;
  }

  public void setDataScadenzaPatenteDisabile(LocalDate dataScadenzaPatenteDisabile) {
    this.dataScadenzaPatenteDisabile = dataScadenzaPatenteDisabile;
  }

  public boolean isRichiedente(RichiestaPermessoPersonalizzato richiestaPermessoPersonalizzato) {
    return !(EnumTipoDomandaPermessoPersonalizzato.getById(
                richiestaPermessoPersonalizzato.getTipoDomanda().getCodice())
            .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_MINORE)
        || EnumTipoDomandaPermessoPersonalizzato.getById(
                richiestaPermessoPersonalizzato.getTipoDomanda().getCodice())
            .equals(EnumTipoDomandaPermessoPersonalizzato.DISABILE_TUTORE));
  }

  public String getCodiceIndividuoDisabile() {
    return codiceIndividuoDisabile;
  }

  public void setCodiceIndividuoDisabile(String codiceIndividuoDisabile) {
    this.codiceIndividuoDisabile = codiceIndividuoDisabile;
  }

  public FeaturesGeoserver getIndirizzoNonResidente() {
    return indirizzoNonResidente;
  }

  public void setIndirizzoNonResidente(FeaturesGeoserver indirizzoNonResidente) {
    this.indirizzoNonResidente = indirizzoNonResidente;
  }

  public PermitVerificationResult getDatiPermessoDisabile() {
    return datiPermessoDisabile;
  }

  public void setDatiPermessoDisabile(PermitVerificationResult datiPermessoDisabile) {
    this.datiPermessoDisabile = datiPermessoDisabile;
  }

  public boolean isResidenteInGenova() {
    return isResidenteInGenova;
  }

  public void setResidenteInGenova(boolean isResidenteInGenova) {
    this.isResidenteInGenova = isResidenteInGenova;
  }
}
