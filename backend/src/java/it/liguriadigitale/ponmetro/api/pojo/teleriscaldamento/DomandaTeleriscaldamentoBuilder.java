package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.DatiVerificatiEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.EsitoVerificaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee12Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee20Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee25Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.StatoPraticaEnum;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DomandaTeleriscaldamentoBuilder {

  private BigDecimal identificativo;

  private BigDecimal idStato;

  private StatoPraticaEnum statoPratica;

  private LocalDate dataInvioIREN;

  private String numProtocollo;

  private String dataProtocollo;

  private String cognomeRichiedente;

  private String nomeRichiedente;

  private String cfRichiedente;

  private String telefono;

  private String cellulare;

  private String email;

  private Integer numNucleoFamiliare;

  private IndicatoreIsee12Enum indicatoreIsee12;

  private IndicatoreIsee20Enum indicatoreIsee20;

  private IndicatoreIsee25Enum indicatoreIsee25;

  private String tipoUtenza;

  private String numeroCliente;

  private String numContratto;

  private String viaFornitura;

  private String comuneFornitura;

  private String numCivicoFornitura;

  private String provinciaFornitura;

  private String capFornitura;

  private String nominativoContratto;

  private String cfIntestarioContratto;

  private String pIvaContratto;

  private String nominativoApt;

  private String nominativoAmmCond;

  private String viaAmmCondominio;

  private String comuneAmmCondominio;

  private String civicoAmmCondominio;

  private String capAmmCondominio;

  private String provAmmCondominio;

  private String telAmmCondominio;

  private String cellAmmCondominio;

  private String emailAmmCondominio;

  private String consensoPrivacy;

  private String consensoInf;

  private DatiVerificatiEnum datiVerificati;

  private EsitoVerificaEnum esitoVerifica;

  private String annoDomanda;

  public static DomandaTeleriscaldamentoBuilder getInstance() {
    return new DomandaTeleriscaldamentoBuilder();
  }

  public DomandaTeleriscaldamentoBuilder addIdentificativo(BigDecimal identificativo) {
    this.identificativo = identificativo;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addIdStato(BigDecimal idStato) {
    this.idStato = idStato;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addStatoPratica(StatoPraticaEnum statoPratica) {
    this.statoPratica = statoPratica;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addDataInvioIREN(LocalDate dataInvioIREN) {
    this.dataInvioIREN = dataInvioIREN;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNumProtocollo(String numProtocollo) {
    this.numProtocollo = numProtocollo;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addDataProtocollo(String dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCognomeRichiedente(String cognomeRichiedente) {
    this.cognomeRichiedente = cognomeRichiedente;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNomeRichiedente(String nomeRichiedente) {
    this.nomeRichiedente = nomeRichiedente;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCfRichiedente(String cfRichiedente) {
    this.cfRichiedente = cfRichiedente;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addTelefono(String telefono) {
    this.telefono = telefono;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCellulare(String cellulare) {
    this.cellulare = cellulare;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addEmail(String email) {
    this.email = email;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNumNucleoFamiliare(Integer numNucleoFamiliare) {
    this.numNucleoFamiliare = numNucleoFamiliare;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addIndicatoreIsee12(
      IndicatoreIsee12Enum indicatoreIsee12) {
    this.indicatoreIsee12 = indicatoreIsee12;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addIndicatoreIsee20(
      IndicatoreIsee20Enum indicatoreIsee20) {
    this.indicatoreIsee20 = indicatoreIsee20;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addIndicatoreIsee20(
      IndicatoreIsee25Enum indicatoreIsee25) {
    this.indicatoreIsee25 = indicatoreIsee25;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addTipoUtenza(String tipoUtenza) {
    this.tipoUtenza = tipoUtenza;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNumContratto(String numContratto) {
    this.numContratto = numContratto;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addViaFornitura(String viaFornitura) {
    this.viaFornitura = viaFornitura;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCapFornitura(String capFornitura) {
    this.capFornitura = capFornitura;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addComuneFornitura(String comuneFornitura) {
    this.comuneFornitura = comuneFornitura;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNumCivicoFornitura(String numCivicoFornitura) {
    this.numCivicoFornitura = numCivicoFornitura;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addProvinciaFornitura(String provinciaFornitura) {
    this.provinciaFornitura = provinciaFornitura;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNominativoContratto(String nominativoContratto) {
    this.nominativoContratto = nominativoContratto;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCfIntestarioContratto(String cfIntestarioContratto) {
    this.cfIntestarioContratto = cfIntestarioContratto;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addpIvaContratto(String pIvaContratto) {
    this.pIvaContratto = pIvaContratto;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNominativoApt(String nominativoApt) {
    this.nominativoApt = nominativoApt;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addNominativoAmmCond(String nominativoAmmCond) {
    this.nominativoAmmCond = nominativoAmmCond;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addViaAmmCondominio(String viaAmmCondominio) {
    this.viaAmmCondominio = viaAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addComuneAmmCondominio(String comuneAmmCondominio) {
    this.comuneAmmCondominio = comuneAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCivicoAmmCondominio(String civicoAmmCondominio) {
    this.civicoAmmCondominio = civicoAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCapAmmCondominio(String capAmmCondominio) {
    this.capAmmCondominio = capAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addProvAmmCondominio(String provAmmCondominio) {
    this.provAmmCondominio = provAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addTelAmmCondominio(String telAmmCondominio) {
    this.telAmmCondominio = telAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addCellAmmCondominio(String cellAmmCondominio) {
    this.cellAmmCondominio = cellAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addEmailAmmCondominio(String emailAmmCondominio) {
    this.emailAmmCondominio = emailAmmCondominio;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addConsensoPrivacy(String consensoPrivacy) {
    this.consensoPrivacy = consensoPrivacy;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addConsensoInf(String consensoInf) {
    this.consensoInf = consensoInf;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addDatiVerificati(DatiVerificatiEnum datiVerificati) {
    this.datiVerificati = datiVerificati;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addEsitoVerifica(EsitoVerificaEnum esitoVerifica) {
    this.esitoVerifica = esitoVerifica;
    return this;
  }

  public DomandaTeleriscaldamentoBuilder addAnnoDomanda(String annoDomanda) {
    this.annoDomanda = annoDomanda;
    return this;
  }

  public DomandaTeleriscaldamento build() {
    DomandaTeleriscaldamento domanda = new DomandaTeleriscaldamento();
    domanda.setCapAmmCondominio(capAmmCondominio);
    domanda.setCellAmmCondominio(cellAmmCondominio);
    domanda.setCellulare(cellulare);
    domanda.setCfIntestarioContratto(cfIntestarioContratto);
    domanda.setCfRichiedente(cfRichiedente);
    domanda.setCivicoAmmCondominio(civicoAmmCondominio);
    domanda.setCognomeRichiedente(cognomeRichiedente);
    domanda.setComuneAmmCondominio(comuneAmmCondominio);
    domanda.setComuneAmmCondominio(comuneAmmCondominio);
    domanda.setComuneFornitura(comuneFornitura);
    domanda.setConsensoInf(consensoInf);
    domanda.setConsensoPrivacy(consensoPrivacy);
    domanda.setDataInvioIREN(dataInvioIREN);
    domanda.setDataProtocollo(dataProtocollo);
    domanda.setDatiVerificati(datiVerificati);
    domanda.setEmail(email);
    domanda.setEmailAmmCondominio(emailAmmCondominio);
    domanda.setEsitoVerifica(esitoVerifica);
    domanda.setIdentificativo(identificativo);
    domanda.setIdStato(idStato);
    domanda.setIndicatoreIsee12(indicatoreIsee12);
    domanda.setIndicatoreIsee20(indicatoreIsee20);
    domanda.setIndicatoreIsee25(indicatoreIsee25);
    domanda.setNomeRichiedente(nomeRichiedente);
    domanda.setNominativoAmmCond(nominativoAmmCond);
    domanda.setNominativoApt(nominativoApt);
    domanda.setNominativoContratto(nominativoContratto);
    domanda.setNumCivicoFornitura(numCivicoFornitura);
    domanda.setNumContratto(numContratto);
    domanda.setNumeroCliente(numeroCliente);
    domanda.setNumNucleoFamiliare(numNucleoFamiliare);
    domanda.setNumProtocollo(numProtocollo);
    domanda.setpIvaContratto(pIvaContratto);
    domanda.setProvAmmCondominio(provAmmCondominio);
    domanda.setProvinciaFornitura(provinciaFornitura);
    domanda.setStatoPratica(statoPratica);
    domanda.setTelAmmCondominio(telAmmCondominio);
    domanda.setTelefono(telefono);
    domanda.setTipoUtenza(tipoUtenza);
    domanda.setViaAmmCondominio(viaAmmCondominio);
    domanda.setViaFornitura(viaFornitura);
    domanda.setCapFornitura(capFornitura);
    domanda.setAnnoDomanda(annoDomanda);
    return domanda;
  }
}
