package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

/**
 * TrDomande
 *
 * <p>ATTENZIONE! Builder generato automaticamente con TableGen null! Non modificare! Creato il:
 * 2022-11-08T11:44:54.698
 */
public class TrDomandeBuilder {

  public java.math.BigDecimal id;

  public Long idStatoDomanda;

  public String statoDomanda;

  public java.time.LocalDateTime dataConsegnaIren;

  public String numProtocollo;

  public String dataProtocollo;

  public String cognomeRichiedente;

  public String nomeRichiedente;

  public String cfRichiedente;

  public String telefono;

  public String cellulare;

  public String email;

  public String numNucleoFamiliare;

  public String indicatoreIsee12;

  public String indicatoreIsee20;

  public String tipoUtenza;

  public String numeroCliente;

  public String numContratto;

  public String viaFornitura;

  public String comuneFornitura;

  public String numCivicoFornitura;

  public String provinciaFornitura;

  public String nominativoContratto;

  public String cfIntestatarioContratto;

  public String pivaContratto;

  public String nominativoApt;

  public String nominativoAammCond;

  public String viaAmmCondominio;

  public String comuneAmmCondominio;

  public String civicoAmmCondominio;

  public String capAmmCondominio;

  public String provAmmCondominio;

  public String telAmmCondominio;

  public String celAmmCondominio;

  public String emailAmmCondominio;

  public String consensoPrivacy;

  public String consensoInf;

  public String datiVerificati;

  public String esitoVerifica;

  public String annoDomanda;

  public String capFornitura;

  public String indicatoreIsee25;

  public static TrDomandeBuilder getInstance() {
    return new TrDomandeBuilder();
  }

  public TrDomandeBuilder addId(java.math.BigDecimal id) {
    this.id = id;
    return this;
  }

  public TrDomandeBuilder addIdStatoDomanda(Long idStatoDomanda) {
    this.idStatoDomanda = idStatoDomanda;
    return this;
  }

  public TrDomandeBuilder addStatoDomanda(String statoDomanda) {
    this.statoDomanda = statoDomanda;
    return this;
  }

  public TrDomandeBuilder addDataConsegnaIren(java.time.LocalDateTime dataConsegnaIren) {
    this.dataConsegnaIren = dataConsegnaIren;
    return this;
  }

  public TrDomandeBuilder addNumProtocollo(String numProtocollo) {
    this.numProtocollo = numProtocollo;
    return this;
  }

  public TrDomandeBuilder addDataProtocollo(String dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
    return this;
  }

  public TrDomandeBuilder addCognomeRichiedente(String cognomeRichiedente) {
    this.cognomeRichiedente = cognomeRichiedente;
    return this;
  }

  public TrDomandeBuilder addNomeRichiedente(String nomeRichiedente) {
    this.nomeRichiedente = nomeRichiedente;
    return this;
  }

  public TrDomandeBuilder addCfRichiedente(String cfRichiedente) {
    this.cfRichiedente = cfRichiedente;
    return this;
  }

  public TrDomandeBuilder addTelefono(String telefono) {
    this.telefono = telefono;
    return this;
  }

  public TrDomandeBuilder addCellulare(String cellulare) {
    this.cellulare = cellulare;
    return this;
  }

  public TrDomandeBuilder addEmail(String email) {
    this.email = email;
    return this;
  }

  public TrDomandeBuilder addNumNucleoFamiliare(String numNucleoFamiliare) {
    this.numNucleoFamiliare = numNucleoFamiliare;
    return this;
  }

  public TrDomandeBuilder addIndicatoreIsee12(String indicatoreIsee12) {
    this.indicatoreIsee12 = indicatoreIsee12;
    return this;
  }

  public TrDomandeBuilder addIndicatoreIsee20(String indicatoreIsee20) {
    this.indicatoreIsee20 = indicatoreIsee20;
    return this;
  }

  public TrDomandeBuilder addTipoUtenza(String tipoUtenza) {
    this.tipoUtenza = tipoUtenza;
    return this;
  }

  public TrDomandeBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public TrDomandeBuilder addNumContratto(String numContratto) {
    this.numContratto = numContratto;
    return this;
  }

  public TrDomandeBuilder addViaFornitura(String viaFornitura) {
    this.viaFornitura = viaFornitura;
    return this;
  }

  public TrDomandeBuilder addComuneFornitura(String comuneFornitura) {
    this.comuneFornitura = comuneFornitura;
    return this;
  }

  public TrDomandeBuilder addNumCivicoFornitura(String numCivicoFornitura) {
    this.numCivicoFornitura = numCivicoFornitura;
    return this;
  }

  public TrDomandeBuilder addProvinciaFornitura(String provinciaFornitura) {
    this.provinciaFornitura = provinciaFornitura;
    return this;
  }

  public TrDomandeBuilder addNominativoContratto(String nominativoContratto) {
    this.nominativoContratto = nominativoContratto;
    return this;
  }

  public TrDomandeBuilder addCfIntestatarioContratto(String cfIntestatarioContratto) {
    this.cfIntestatarioContratto = cfIntestatarioContratto;
    return this;
  }

  public TrDomandeBuilder addPivaContratto(String pivaContratto) {
    this.pivaContratto = pivaContratto;
    return this;
  }

  public TrDomandeBuilder addNominativoApt(String nominativoApt) {
    this.nominativoApt = nominativoApt;
    return this;
  }

  public TrDomandeBuilder addNominativoAammCond(String nominativoAammCond) {
    this.nominativoAammCond = nominativoAammCond;
    return this;
  }

  public TrDomandeBuilder addViaAmmCondominio(String viaAmmCondominio) {
    this.viaAmmCondominio = viaAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addComuneAmmCondominio(String comuneAmmCondominio) {
    this.comuneAmmCondominio = comuneAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addCivicoAmmCondominio(String civicoAmmCondominio) {
    this.civicoAmmCondominio = civicoAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addCapAmmCondominio(String capAmmCondominio) {
    this.capAmmCondominio = capAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addProvAmmCondominio(String provAmmCondominio) {
    this.provAmmCondominio = provAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addTelAmmCondominio(String telAmmCondominio) {
    this.telAmmCondominio = telAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addCelAmmCondominio(String celAmmCondominio) {
    this.celAmmCondominio = celAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addEmailAmmCondominio(String emailAmmCondominio) {
    this.emailAmmCondominio = emailAmmCondominio;
    return this;
  }

  public TrDomandeBuilder addConsensoPrivacy(String consensoPrivacy) {
    this.consensoPrivacy = consensoPrivacy;
    return this;
  }

  public TrDomandeBuilder addConsensoInf(String consensoInf) {
    this.consensoInf = consensoInf;
    return this;
  }

  public TrDomandeBuilder addDatiVerificati(String datiVerificati) {
    this.datiVerificati = datiVerificati;
    return this;
  }

  public TrDomandeBuilder addEsitoVerifica(String esitoVerifica) {
    this.esitoVerifica = esitoVerifica;
    return this;
  }

  public TrDomandeBuilder addAnnoDomanda(String annoDomanda) {
    this.annoDomanda = annoDomanda;
    return this;
  }

  public TrDomandeBuilder addCapFornitura(String capFornitura) {
    this.capFornitura = capFornitura;
    return this;
  }

  public TrDomandeBuilder addIndicatoreIsee25(String indicatoreIsee25) {
    this.indicatoreIsee25 = indicatoreIsee25;
    return this;
  }

  public TrDomande build() {
    TrDomande obj = new TrDomande();
    obj.setId(this.id);
    obj.setIdStatoDomanda(this.idStatoDomanda);
    obj.setStatoDomanda(this.statoDomanda);
    obj.setDataConsegnaIren(this.dataConsegnaIren);
    obj.setNumProtocollo(this.numProtocollo);
    obj.setDataProtocollo(this.dataProtocollo);
    obj.setCognomeRichiedente(this.cognomeRichiedente);
    obj.setNomeRichiedente(this.nomeRichiedente);
    obj.setCfRichiedente(this.cfRichiedente);
    obj.setTelefono(this.telefono);
    obj.setCellulare(this.cellulare);
    obj.setEmail(this.email);
    obj.setNumNucleoFamiliare(this.numNucleoFamiliare);
    obj.setIndicatoreIsee12(this.indicatoreIsee12);
    obj.setIndicatoreIsee20(this.indicatoreIsee20);
    obj.setTipoUtenza(this.tipoUtenza);
    obj.setNumeroCliente(this.numeroCliente);
    obj.setNumContratto(this.numContratto);
    obj.setViaFornitura(this.viaFornitura);
    obj.setComuneFornitura(this.comuneFornitura);
    obj.setNumCivicoFornitura(this.numCivicoFornitura);
    obj.setProvinciaFornitura(this.provinciaFornitura);
    obj.setNominativoContratto(this.nominativoContratto);
    obj.setCfIntestatarioContratto(this.cfIntestatarioContratto);
    obj.setPivaContratto(this.pivaContratto);
    obj.setNominativoApt(this.nominativoApt);
    obj.setNominativoAammCond(this.nominativoAammCond);
    obj.setViaAmmCondominio(this.viaAmmCondominio);
    obj.setComuneAmmCondominio(this.comuneAmmCondominio);
    obj.setCivicoAmmCondominio(this.civicoAmmCondominio);
    obj.setCapAmmCondominio(this.capAmmCondominio);
    obj.setProvAmmCondominio(this.provAmmCondominio);
    obj.setTelAmmCondominio(this.telAmmCondominio);
    obj.setCelAmmCondominio(this.celAmmCondominio);
    obj.setEmailAmmCondominio(this.emailAmmCondominio);
    obj.setConsensoPrivacy(this.consensoPrivacy);
    obj.setConsensoInf(this.consensoInf);
    obj.setDatiVerificati(this.datiVerificati);
    obj.setEsitoVerifica(this.esitoVerifica);
    obj.setAnnoDomanda(this.annoDomanda);
    obj.setCapFornitura(this.capFornitura);
    obj.setIndicatoreIsee25(this.indicatoreIsee25);
    return obj;
  }
}
