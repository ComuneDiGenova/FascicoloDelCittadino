package it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento;

import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.DatiVerificatiEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.EsitoVerificaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee25Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.StatoPraticaEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DatiDomandaTeleriscaldamento implements Serializable {

  private static final long serialVersionUID = -5740523039150923583L;

  // STEP 1 ISEE

  // private AttestazioniISEE attestazioneIsee;

  private boolean iseePresentato;

  private int numeroComponenti;

  private Double importoIsee;

  private boolean importoIseeNelRange;

  private boolean domandaPresentabile;

  // STEP 2 dati richiedente

  private String cognome;

  private String nome;

  private String codiceFiscale;

  private String telefono;

  private String cellulare;

  private String email;

  // STEP 3 utenza

  private TipoUtenzaEnum tipoUtenza;

  private String numeroCliente;

  private String numeroContratto;

  private String indirizzoCompleto;

  private String via;

  private String numeroCivico;

  private String comune;

  private String provincia;

  private String cap;

  // STEP 4 dati contratto

  private String nomeIntestatarioContratto;

  private String cognomeIntestatarioContratto;

  private String nominativoIntestatarioContratto;

  private String cfIntestatarioContratto;

  private String pIvaIntestatarioContratto;

  private String nomeProprietarioAppartamento;

  private String cognomeProprietarioAppartamento;

  private String nominativoProprietarioAppartamento;

  private String nomeAmministratore;

  private String cognomeAmministratore;

  private String nominativoAmministratore;

  private String viaAmministratore;

  private String comuneAmministratore;

  private String numeroCivicoAmministratore;

  private String capAmministratore;

  private String provinciaAmministratore;

  private String telefonoAmministratore;

  private String cellulareAmministratore;

  private String emailAmministratore;

  // STEP 5 consensi

  private String art43;

  private String privacy;

  // altri dati

  private LocalDate dataInvioIren;

  private String numeroProtocollo;

  private LocalDate dataProtocollo;

  private DatiVerificatiEnum datiVerificati;

  private EsitoVerificaEnum esitoVerifica;

  private BigDecimal identificativo;

  private BigDecimal idStato;

  //	private IndicatoreIsee12Enum indicatoreIsee12;
  //
  //	private IndicatoreIsee20Enum indicatoreIsee20;

  private IndicatoreIsee25Enum indicatoreIsee25;

  private StatoPraticaEnum statoPratica;

  private String esitoDiIren;

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public TipoUtenzaEnum getTipoUtenza() {
    return tipoUtenza;
  }

  public void setTipoUtenza(TipoUtenzaEnum tipoUtenza) {
    this.tipoUtenza = tipoUtenza;
  }

  public String getNumeroCliente() {
    return numeroCliente;
  }

  public void setNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
  }

  public String getNumeroContratto() {
    return numeroContratto;
  }

  public void setNumeroContratto(String numeroContratto) {
    this.numeroContratto = numeroContratto;
  }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getIndirizzoCompleto() {
    return indirizzoCompleto;
  }

  public void setIndirizzoCompleto(String indirizzoCompleto) {
    this.indirizzoCompleto = indirizzoCompleto;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public String getNumeroCivico() {
    return numeroCivico;
  }

  public void setNumeroCivico(String numeroCivico) {
    this.numeroCivico = numeroCivico;
  }

  public String getArt43() {
    return art43;
  }

  public void setArt43(String art43) {
    this.art43 = art43;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public String getNominativoIntestatarioContratto() {
    return nominativoIntestatarioContratto;
  }

  public void setNominativoIntestatarioContratto(String nominativoIntestatarioContratto) {
    this.nominativoIntestatarioContratto = nominativoIntestatarioContratto;
  }

  public String getCfIntestatarioContratto() {
    return cfIntestatarioContratto;
  }

  public void setCfIntestatarioContratto(String cfIntestatarioContratto) {
    this.cfIntestatarioContratto = cfIntestatarioContratto;
  }

  public String getpIvaIntestatarioContratto() {
    return pIvaIntestatarioContratto;
  }

  public void setpIvaIntestatarioContratto(String pIvaIntestatarioContratto) {
    this.pIvaIntestatarioContratto = pIvaIntestatarioContratto;
  }

  public String getNominativoProprietarioAppartamento() {
    return nominativoProprietarioAppartamento;
  }

  public void setNominativoProprietarioAppartamento(String nominativoProprietarioAppartamento) {
    this.nominativoProprietarioAppartamento = nominativoProprietarioAppartamento;
  }

  public String getNominativoAmministratore() {
    return nominativoAmministratore;
  }

  public void setNominativoAmministratore(String nominativoAmministratore) {
    this.nominativoAmministratore = nominativoAmministratore;
  }

  public String getViaAmministratore() {
    return viaAmministratore;
  }

  public void setViaAmministratore(String viaAmministratore) {
    this.viaAmministratore = viaAmministratore;
  }

  public String getComuneAmministratore() {
    return comuneAmministratore;
  }

  public void setComuneAmministratore(String comuneAmministratore) {
    this.comuneAmministratore = comuneAmministratore;
  }

  public String getNumeroCivicoAmministratore() {
    return numeroCivicoAmministratore;
  }

  public void setNumeroCivicoAmministratore(String numeroCivicoAmministratore) {
    this.numeroCivicoAmministratore = numeroCivicoAmministratore;
  }

  public String getCapAmministratore() {
    return capAmministratore;
  }

  public void setCapAmministratore(String capAmministratore) {
    this.capAmministratore = capAmministratore;
  }

  public String getProvinciaAmministratore() {
    return provinciaAmministratore;
  }

  public void setProvinciaAmministratore(String provinciaAmministratore) {
    this.provinciaAmministratore = provinciaAmministratore;
  }

  public String getTelefonoAmministratore() {
    return telefonoAmministratore;
  }

  public void setTelefonoAmministratore(String telefonoAmministratore) {
    this.telefonoAmministratore = telefonoAmministratore;
  }

  public String getCellulareAmministratore() {
    return cellulareAmministratore;
  }

  public void setCellulareAmministratore(String cellulareAmministratore) {
    this.cellulareAmministratore = cellulareAmministratore;
  }

  public String getEmailAmministratore() {
    return emailAmministratore;
  }

  public void setEmailAmministratore(String emailAmministratore) {
    this.emailAmministratore = emailAmministratore;
  }

  public String getNomeIntestatarioContratto() {
    return nomeIntestatarioContratto;
  }

  public void setNomeIntestatarioContratto(String nomeIntestatarioContratto) {
    this.nomeIntestatarioContratto = nomeIntestatarioContratto;
  }

  public String getCognomeIntestatarioContratto() {
    return cognomeIntestatarioContratto;
  }

  public void setCognomeIntestatarioContratto(String cognomeIntestatarioContratto) {
    this.cognomeIntestatarioContratto = cognomeIntestatarioContratto;
  }

  public String getNomeProprietarioAppartamento() {
    return nomeProprietarioAppartamento;
  }

  public void setNomeProprietarioAppartamento(String nomeProprietarioAppartamento) {
    this.nomeProprietarioAppartamento = nomeProprietarioAppartamento;
  }

  public String getCognomeProprietarioAppartamento() {
    return cognomeProprietarioAppartamento;
  }

  public void setCognomeProprietarioAppartamento(String cognomeProprietarioAppartamento) {
    this.cognomeProprietarioAppartamento = cognomeProprietarioAppartamento;
  }

  public String getNomeAmministratore() {
    return nomeAmministratore;
  }

  public void setNomeAmministratore(String nomeAmministratore) {
    this.nomeAmministratore = nomeAmministratore;
  }

  public String getCognomeAmministratore() {
    return cognomeAmministratore;
  }

  public void setCognomeAmministratore(String cognomeAmministratore) {
    this.cognomeAmministratore = cognomeAmministratore;
  }

  //	public AttestazioniISEE getAttestazioneIsee() {
  //		return attestazioneIsee;
  //	}
  //
  //	public void setAttestazioneIsee(AttestazioniISEE attestazioneIsee) {
  //		this.attestazioneIsee = attestazioneIsee;
  //	}

  public boolean isIseePresentato() {
    return iseePresentato;
  }

  public void setIseePresentato(boolean iseePresentato) {
    this.iseePresentato = iseePresentato;
  }

  public Double getImportoIsee() {
    return importoIsee;
  }

  public void setImportoIsee(Double importoIsee) {
    this.importoIsee = importoIsee;
  }

  public boolean isDomandaPresentabile() {
    return domandaPresentabile;
  }

  public void setDomandaPresentabile(boolean domandaPresentabile) {
    this.domandaPresentabile = domandaPresentabile;
  }

  public boolean isImportoIseeNelRange() {
    return importoIseeNelRange;
  }

  public void setImportoIseeNelRange(boolean importoIseeNelRange) {
    this.importoIseeNelRange = importoIseeNelRange;
  }

  public int getNumeroComponenti() {
    return numeroComponenti;
  }

  public void setNumeroComponenti(int numeroComponenti) {
    this.numeroComponenti = numeroComponenti;
  }

  public LocalDate getDataInvioIren() {
    return dataInvioIren;
  }

  public void setDataInvioIren(LocalDate dataInvioIren) {
    this.dataInvioIren = dataInvioIren;
  }

  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public LocalDate getDataProtocollo() {
    return dataProtocollo;
  }

  public void setDataProtocollo(LocalDate dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
  }

  public DatiVerificatiEnum getDatiVerificati() {
    return datiVerificati;
  }

  public void setDatiVerificati(DatiVerificatiEnum datiVerificati) {
    this.datiVerificati = datiVerificati;
  }

  public EsitoVerificaEnum getEsitoVerifica() {
    return esitoVerifica;
  }

  public void setEsitoVerifica(EsitoVerificaEnum esitoVerifica) {
    this.esitoVerifica = esitoVerifica;
  }

  public BigDecimal getIdentificativo() {
    return identificativo;
  }

  public void setIdentificativo(BigDecimal identificativo) {
    this.identificativo = identificativo;
  }

  public BigDecimal getIdStato() {
    return idStato;
  }

  public void setIdStato(BigDecimal idStato) {
    this.idStato = idStato;
  }

  //	public IndicatoreIsee12Enum getIndicatoreIsee12() {
  //		return indicatoreIsee12;
  //	}
  //
  //	public void setIndicatoreIsee12(IndicatoreIsee12Enum indicatoreIsee12) {
  //		this.indicatoreIsee12 = indicatoreIsee12;
  //	}
  //
  //	public IndicatoreIsee20Enum getIndicatoreIsee20() {
  //		return indicatoreIsee20;
  //	}
  //
  //	public void setIndicatoreIsee20(IndicatoreIsee20Enum indicatoreIsee20) {
  //		this.indicatoreIsee20 = indicatoreIsee20;
  //	}

  public StatoPraticaEnum getStatoPratica() {
    return statoPratica;
  }

  public void setStatoPratica(StatoPraticaEnum statoPratica) {
    this.statoPratica = statoPratica;
  }

  public String getEsitoDiIren() {
    return esitoDiIren;
  }

  public void setEsitoDiIren(String esitoDiIren) {
    this.esitoDiIren = esitoDiIren;
  }

  public IndicatoreIsee25Enum getIndicatoreIsee25() {
    return indicatoreIsee25;
  }

  public void setIndicatoreIsee25(IndicatoreIsee25Enum indicatoreIsee25) {
    this.indicatoreIsee25 = indicatoreIsee25;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatiDomandaTeleriscaldamento [iseePresentato=");
    builder.append(iseePresentato);
    builder.append(", numeroComponenti=");
    builder.append(numeroComponenti);
    builder.append(", importoIsee=");
    builder.append(importoIsee);
    builder.append(", importoIseeNelRange=");
    builder.append(importoIseeNelRange);
    builder.append(", domandaPresentabile=");
    builder.append(domandaPresentabile);
    builder.append(", cognome=");
    builder.append(cognome);
    builder.append(", nome=");
    builder.append(nome);
    builder.append(", codiceFiscale=");
    builder.append(codiceFiscale);
    builder.append(", telefono=");
    builder.append(telefono);
    builder.append(", cellulare=");
    builder.append(cellulare);
    builder.append(", email=");
    builder.append(email);
    builder.append(", tipoUtenza=");
    builder.append(tipoUtenza);
    builder.append(", numeroCliente=");
    builder.append(numeroCliente);
    builder.append(", numeroContratto=");
    builder.append(numeroContratto);
    builder.append(", indirizzoCompleto=");
    builder.append(indirizzoCompleto);
    builder.append(", via=");
    builder.append(via);
    builder.append(", numeroCivico=");
    builder.append(numeroCivico);
    builder.append(", comune=");
    builder.append(comune);
    builder.append(", provincia=");
    builder.append(provincia);
    builder.append(", cap=");
    builder.append(cap);
    builder.append(", nomeIntestatarioContratto=");
    builder.append(nomeIntestatarioContratto);
    builder.append(", cognomeIntestatarioContratto=");
    builder.append(cognomeIntestatarioContratto);
    builder.append(", nominativoIntestatarioContratto=");
    builder.append(nominativoIntestatarioContratto);
    builder.append(", cfIntestatarioContratto=");
    builder.append(cfIntestatarioContratto);
    builder.append(", pIvaIntestatarioContratto=");
    builder.append(pIvaIntestatarioContratto);
    builder.append(", nomeProprietarioAppartamento=");
    builder.append(nomeProprietarioAppartamento);
    builder.append(", cognomeProprietarioAppartamento=");
    builder.append(cognomeProprietarioAppartamento);
    builder.append(", nominativoProprietarioAppartamento=");
    builder.append(nominativoProprietarioAppartamento);
    builder.append(", nomeAmministratore=");
    builder.append(nomeAmministratore);
    builder.append(", cognomeAmministratore=");
    builder.append(cognomeAmministratore);
    builder.append(", nominativoAmministratore=");
    builder.append(nominativoAmministratore);
    builder.append(", viaAmministratore=");
    builder.append(viaAmministratore);
    builder.append(", comuneAmministratore=");
    builder.append(comuneAmministratore);
    builder.append(", numeroCivicoAmministratore=");
    builder.append(numeroCivicoAmministratore);
    builder.append(", capAmministratore=");
    builder.append(capAmministratore);
    builder.append(", provinciaAmministratore=");
    builder.append(provinciaAmministratore);
    builder.append(", telefonoAmministratore=");
    builder.append(telefonoAmministratore);
    builder.append(", cellulareAmministratore=");
    builder.append(cellulareAmministratore);
    builder.append(", emailAmministratore=");
    builder.append(emailAmministratore);
    builder.append(", art43=");
    builder.append(art43);
    builder.append(", privacy=");
    builder.append(privacy);
    builder.append(", dataInvioIren=");
    builder.append(dataInvioIren);
    builder.append(", numeroProtocollo=");
    builder.append(numeroProtocollo);
    builder.append(", dataProtocollo=");
    builder.append(dataProtocollo);
    builder.append(", datiVerificati=");
    builder.append(datiVerificati);
    builder.append(", esitoVerifica=");
    builder.append(esitoVerifica);
    builder.append(", identificativo=");
    builder.append(identificativo);
    builder.append(", idStato=");
    builder.append(idStato);
    builder.append(", indicatoreIsee25=");
    builder.append(indicatoreIsee25);
    builder.append(", statoPratica=");
    builder.append(statoPratica);
    builder.append(", esitoDiIren=");
    builder.append(esitoDiIren);
    builder.append("]");
    return builder.toString();
  }
}
