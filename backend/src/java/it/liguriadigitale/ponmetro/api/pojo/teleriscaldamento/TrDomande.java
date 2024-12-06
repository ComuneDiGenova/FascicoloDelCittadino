package it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento;

/**
 * TrDomande
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-11-08T11:44:54.667
 */
import java.io.Serializable;

public class TrDomande implements Serializable {
  private static final long serialVersionUID = 1L;

  public TrDomande() {
    super();
  }

  /** Type : BIGINT Name : ID */
  public java.math.BigDecimal id;

  /** Type : BIGINT Name : ID_STATO_DOMANDA */
  public Long idStatoDomanda;

  /** Type : VARCHAR Name : STATO_DOMANDA */
  public String statoDomanda;

  /** Type : TIMESTAMP Name : DATA_CONSEGNA_IREN */
  public java.time.LocalDateTime dataConsegnaIren;

  /** Type : VARCHAR Name : NUM_PROTOCOLLO */
  public String numProtocollo;

  /** Type : VARCHAR Name : DATA_PROTOCOLLO */
  public String dataProtocollo;

  /** Type : VARCHAR Name : COGNOME_RICHIEDENTE */
  public String cognomeRichiedente;

  /** Type : VARCHAR Name : NOME_RICHIEDENTE */
  public String nomeRichiedente;

  /** Type : VARCHAR Name : CF_RICHIEDENTE */
  public String cfRichiedente;

  /** Type : VARCHAR Name : TELEFONO */
  public String telefono;

  /** Type : VARCHAR Name : CELLULARE */
  public String cellulare;

  /** Type : VARCHAR Name : EMAIL */
  public String email;

  /** Type : VARCHAR Name : NUM_NUCLEO_FAMILIARE */
  public String numNucleoFamiliare;

  /** Type : VARCHAR Name : INDICATORE_ISEE_12 */
  public String indicatoreIsee12;

  /** Type : VARCHAR Name : INDICATORE_ISEE_20 */
  public String indicatoreIsee20;

  /** Type : VARCHAR Name : TIPO_UTENZA */
  public String tipoUtenza;

  /** Type : VARCHAR Name : NUMERO_CLIENTE */
  public String numeroCliente;

  /** Type : VARCHAR Name : NUM_CONTRATTO */
  public String numContratto;

  /** Type : VARCHAR Name : VIA_FORNITURA */
  public String viaFornitura;

  /** Type : VARCHAR Name : COMUNE_FORNITURA */
  public String comuneFornitura;

  /** Type : VARCHAR Name : NUM_CIVICO_FORNITURA */
  public String numCivicoFornitura;

  /** Type : VARCHAR Name : PROVINCIA_FORNITURA */
  public String provinciaFornitura;

  /** Type : VARCHAR Name : NOMINATIVO_CONTRATTO */
  public String nominativoContratto;

  /** Type : VARCHAR Name : CF_INTESTATARIO_CONTRATTO */
  public String cfIntestatarioContratto;

  /** Type : VARCHAR Name : PIVA_CONTRATTO */
  public String pivaContratto;

  /** Type : VARCHAR Name : NOMINATIVO_APT */
  public String nominativoApt;

  /** Type : VARCHAR Name : NOMINATIVO_AAMM_COND */
  public String nominativoAammCond;

  /** Type : VARCHAR Name : VIA_AMM_CONDOMINIO */
  public String viaAmmCondominio;

  /** Type : VARCHAR Name : COMUNE_AMM_CONDOMINIO */
  public String comuneAmmCondominio;

  /** Type : VARCHAR Name : CIVICO_AMM_CONDOMINIO */
  public String civicoAmmCondominio;

  /** Type : VARCHAR Name : CAP_AMM_CONDOMINIO */
  public String capAmmCondominio;

  /** Type : VARCHAR Name : PROV_AMM_CONDOMINIO */
  public String provAmmCondominio;

  /** Type : VARCHAR Name : TEL_AMM_CONDOMINIO */
  public String telAmmCondominio;

  /** Type : VARCHAR Name : CEL_AMM_CONDOMINIO */
  public String celAmmCondominio;

  /** Type : VARCHAR Name : EMAIL_AMM_CONDOMINIO */
  public String emailAmmCondominio;

  /** Type : VARCHAR Name : CONSENSO_PRIVACY */
  public String consensoPrivacy;

  /** Type : VARCHAR Name : CONSENSO_INF */
  public String consensoInf;

  /** Type : VARCHAR Name : DATI_VERIFICATI */
  public String datiVerificati;

  /** Type : VARCHAR Name : ESITO_VERIFICA */
  public String esitoVerifica;

  /** Type : VARCHAR Name : ANNO_DOMANDA */
  public String annoDomanda;

  /** Type : VARCHAR Name : CAP_FORNITURA */
  public String capFornitura;

  /** Type : VARCHAR Name : INDICATORE_ISEE_25 */
  public String indicatoreIsee25;

  /** Sets the value for id */
  public void setId(java.math.BigDecimal id) {
    this.id = id;
  }

  /** Gets the value for id */
  public java.math.BigDecimal getId() {
    return id;
  }

  /** Sets the value for idStatoDomanda */
  public void setIdStatoDomanda(Long idStatoDomanda) {
    this.idStatoDomanda = idStatoDomanda;
  }

  /** Gets the value for idStatoDomanda */
  public Long getIdStatoDomanda() {
    return idStatoDomanda;
  }

  /** Sets the value for statoDomanda */
  public void setStatoDomanda(String statoDomanda) {
    this.statoDomanda = statoDomanda;
  }

  /** Gets the value for statoDomanda */
  public String getStatoDomanda() {
    return statoDomanda;
  }

  /** Sets the value for dataConsegnaIren */
  public void setDataConsegnaIren(java.time.LocalDateTime dataConsegnaIren) {
    this.dataConsegnaIren = dataConsegnaIren;
  }

  /** Gets the value for dataConsegnaIren */
  public java.time.LocalDateTime getDataConsegnaIren() {
    return dataConsegnaIren;
  }

  /** Sets the value for numProtocollo */
  public void setNumProtocollo(String numProtocollo) {
    this.numProtocollo = numProtocollo;
  }

  /** Gets the value for numProtocollo */
  public String getNumProtocollo() {
    return numProtocollo;
  }

  /** Sets the value for dataProtocollo */
  public void setDataProtocollo(String dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
  }

  /** Gets the value for dataProtocollo */
  public String getDataProtocollo() {
    return dataProtocollo;
  }

  /** Sets the value for cognomeRichiedente */
  public void setCognomeRichiedente(String cognomeRichiedente) {
    this.cognomeRichiedente = cognomeRichiedente;
  }

  /** Gets the value for cognomeRichiedente */
  public String getCognomeRichiedente() {
    return cognomeRichiedente;
  }

  /** Sets the value for nomeRichiedente */
  public void setNomeRichiedente(String nomeRichiedente) {
    this.nomeRichiedente = nomeRichiedente;
  }

  /** Gets the value for nomeRichiedente */
  public String getNomeRichiedente() {
    return nomeRichiedente;
  }

  /** Sets the value for cfRichiedente */
  public void setCfRichiedente(String cfRichiedente) {
    this.cfRichiedente = cfRichiedente;
  }

  /** Gets the value for cfRichiedente */
  public String getCfRichiedente() {
    return cfRichiedente;
  }

  /** Sets the value for telefono */
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /** Gets the value for telefono */
  public String getTelefono() {
    return telefono;
  }

  /** Sets the value for cellulare */
  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  /** Gets the value for cellulare */
  public String getCellulare() {
    return cellulare;
  }

  /** Sets the value for email */
  public void setEmail(String email) {
    this.email = email;
  }

  /** Gets the value for email */
  public String getEmail() {
    return email;
  }

  /** Sets the value for numNucleoFamiliare */
  public void setNumNucleoFamiliare(String numNucleoFamiliare) {
    this.numNucleoFamiliare = numNucleoFamiliare;
  }

  /** Gets the value for numNucleoFamiliare */
  public String getNumNucleoFamiliare() {
    return numNucleoFamiliare;
  }

  /** Sets the value for indicatoreIsee12 */
  public void setIndicatoreIsee12(String indicatoreIsee12) {
    this.indicatoreIsee12 = indicatoreIsee12;
  }

  /** Gets the value for indicatoreIsee12 */
  public String getIndicatoreIsee12() {
    return indicatoreIsee12;
  }

  /** Sets the value for indicatoreIsee20 */
  public void setIndicatoreIsee20(String indicatoreIsee20) {
    this.indicatoreIsee20 = indicatoreIsee20;
  }

  /** Gets the value for indicatoreIsee20 */
  public String getIndicatoreIsee20() {
    return indicatoreIsee20;
  }

  /** Sets the value for tipoUtenza */
  public void setTipoUtenza(String tipoUtenza) {
    this.tipoUtenza = tipoUtenza;
  }

  /** Gets the value for tipoUtenza */
  public String getTipoUtenza() {
    return tipoUtenza;
  }

  /** Sets the value for numeroCliente */
  public void setNumeroCliente(String numeroCliente) {
    this.numeroCliente = numeroCliente;
  }

  /** Gets the value for numeroCliente */
  public String getNumeroCliente() {
    return numeroCliente;
  }

  /** Sets the value for numContratto */
  public void setNumContratto(String numContratto) {
    this.numContratto = numContratto;
  }

  /** Gets the value for numContratto */
  public String getNumContratto() {
    return numContratto;
  }

  /** Sets the value for viaFornitura */
  public void setViaFornitura(String viaFornitura) {
    this.viaFornitura = viaFornitura;
  }

  /** Gets the value for viaFornitura */
  public String getViaFornitura() {
    return viaFornitura;
  }

  /** Sets the value for comuneFornitura */
  public void setComuneFornitura(String comuneFornitura) {
    this.comuneFornitura = comuneFornitura;
  }

  /** Gets the value for comuneFornitura */
  public String getComuneFornitura() {
    return comuneFornitura;
  }

  /** Sets the value for numCivicoFornitura */
  public void setNumCivicoFornitura(String numCivicoFornitura) {
    this.numCivicoFornitura = numCivicoFornitura;
  }

  /** Gets the value for numCivicoFornitura */
  public String getNumCivicoFornitura() {
    return numCivicoFornitura;
  }

  /** Sets the value for provinciaFornitura */
  public void setProvinciaFornitura(String provinciaFornitura) {
    this.provinciaFornitura = provinciaFornitura;
  }

  /** Gets the value for provinciaFornitura */
  public String getProvinciaFornitura() {
    return provinciaFornitura;
  }

  /** Sets the value for nominativoContratto */
  public void setNominativoContratto(String nominativoContratto) {
    this.nominativoContratto = nominativoContratto;
  }

  /** Gets the value for nominativoContratto */
  public String getNominativoContratto() {
    return nominativoContratto;
  }

  /** Sets the value for cfIntestatarioContratto */
  public void setCfIntestatarioContratto(String cfIntestatarioContratto) {
    this.cfIntestatarioContratto = cfIntestatarioContratto;
  }

  /** Gets the value for cfIntestatarioContratto */
  public String getCfIntestatarioContratto() {
    return cfIntestatarioContratto;
  }

  /** Sets the value for pivaContratto */
  public void setPivaContratto(String pivaContratto) {
    this.pivaContratto = pivaContratto;
  }

  /** Gets the value for pivaContratto */
  public String getPivaContratto() {
    return pivaContratto;
  }

  /** Sets the value for nominativoApt */
  public void setNominativoApt(String nominativoApt) {
    this.nominativoApt = nominativoApt;
  }

  /** Gets the value for nominativoApt */
  public String getNominativoApt() {
    return nominativoApt;
  }

  /** Sets the value for nominativoAammCond */
  public void setNominativoAammCond(String nominativoAammCond) {
    this.nominativoAammCond = nominativoAammCond;
  }

  /** Gets the value for nominativoAammCond */
  public String getNominativoAammCond() {
    return nominativoAammCond;
  }

  /** Sets the value for viaAmmCondominio */
  public void setViaAmmCondominio(String viaAmmCondominio) {
    this.viaAmmCondominio = viaAmmCondominio;
  }

  /** Gets the value for viaAmmCondominio */
  public String getViaAmmCondominio() {
    return viaAmmCondominio;
  }

  /** Sets the value for comuneAmmCondominio */
  public void setComuneAmmCondominio(String comuneAmmCondominio) {
    this.comuneAmmCondominio = comuneAmmCondominio;
  }

  /** Gets the value for comuneAmmCondominio */
  public String getComuneAmmCondominio() {
    return comuneAmmCondominio;
  }

  /** Sets the value for civicoAmmCondominio */
  public void setCivicoAmmCondominio(String civicoAmmCondominio) {
    this.civicoAmmCondominio = civicoAmmCondominio;
  }

  /** Gets the value for civicoAmmCondominio */
  public String getCivicoAmmCondominio() {
    return civicoAmmCondominio;
  }

  /** Sets the value for capAmmCondominio */
  public void setCapAmmCondominio(String capAmmCondominio) {
    this.capAmmCondominio = capAmmCondominio;
  }

  /** Gets the value for capAmmCondominio */
  public String getCapAmmCondominio() {
    return capAmmCondominio;
  }

  /** Sets the value for provAmmCondominio */
  public void setProvAmmCondominio(String provAmmCondominio) {
    this.provAmmCondominio = provAmmCondominio;
  }

  /** Gets the value for provAmmCondominio */
  public String getProvAmmCondominio() {
    return provAmmCondominio;
  }

  /** Sets the value for telAmmCondominio */
  public void setTelAmmCondominio(String telAmmCondominio) {
    this.telAmmCondominio = telAmmCondominio;
  }

  /** Gets the value for telAmmCondominio */
  public String getTelAmmCondominio() {
    return telAmmCondominio;
  }

  /** Sets the value for celAmmCondominio */
  public void setCelAmmCondominio(String celAmmCondominio) {
    this.celAmmCondominio = celAmmCondominio;
  }

  /** Gets the value for celAmmCondominio */
  public String getCelAmmCondominio() {
    return celAmmCondominio;
  }

  /** Sets the value for emailAmmCondominio */
  public void setEmailAmmCondominio(String emailAmmCondominio) {
    this.emailAmmCondominio = emailAmmCondominio;
  }

  /** Gets the value for emailAmmCondominio */
  public String getEmailAmmCondominio() {
    return emailAmmCondominio;
  }

  /** Sets the value for consensoPrivacy */
  public void setConsensoPrivacy(String consensoPrivacy) {
    this.consensoPrivacy = consensoPrivacy;
  }

  /** Gets the value for consensoPrivacy */
  public String getConsensoPrivacy() {
    return consensoPrivacy;
  }

  /** Sets the value for consensoInf */
  public void setConsensoInf(String consensoInf) {
    this.consensoInf = consensoInf;
  }

  /** Gets the value for consensoInf */
  public String getConsensoInf() {
    return consensoInf;
  }

  /** Sets the value for datiVerificati */
  public void setDatiVerificati(String datiVerificati) {
    this.datiVerificati = datiVerificati;
  }

  /** Gets the value for datiVerificati */
  public String getDatiVerificati() {
    return datiVerificati;
  }

  /** Sets the value for esitoVerifica */
  public void setEsitoVerifica(String esitoVerifica) {
    this.esitoVerifica = esitoVerifica;
  }

  /** Gets the value for esitoVerifica */
  public String getEsitoVerifica() {
    return esitoVerifica;
  }

  /** Sets the value for annoDomanda */
  public void setAnnoDomanda(String annoDomanda) {
    this.annoDomanda = annoDomanda;
  }

  /** Gets the value for annoDomanda */
  public String getAnnoDomanda() {
    return annoDomanda;
  }

  /** Sets the value for capFornitura */
  public void setCapFornitura(String capFornitura) {
    this.capFornitura = capFornitura;
  }

  /** Gets the value for capFornitura */
  public String getCapFornitura() {
    return capFornitura;
  }

  /** Sets the value for indicatoreIsee25 */
  public void setIndicatoreIsee25(String indicatoreIsee25) {
    this.indicatoreIsee25 = indicatoreIsee25;
  }

  /** Gets the value for indicatoreIsee25 */
  public String getIndicatoreIsee25() {
    return indicatoreIsee25;
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append(super.toString());
    try {
      java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].getName();
        Object fieldValue = fields[i].get(this);
        String line = "\n" + fieldName + ": " + fieldValue;
        str.append(line);
      }
      return str.toString();
    } catch (Exception e) {
      return str.toString();
    }
  }
}
