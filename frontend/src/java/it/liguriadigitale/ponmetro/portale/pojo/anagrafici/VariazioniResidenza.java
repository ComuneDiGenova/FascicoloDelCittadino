package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioEnum;
import it.liguriadigitale.ponmetro.servizianagrafici.model.DocumentoPratica;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetDatiGeneraliPraticaResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Toponomastica;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VariazioniResidenza implements Serializable {

  private static final long serialVersionUID = 761848952330426178L;

  private FeaturesGeoserver autocompleteViario;

  private VariazioniDiResidenzaEnum tipoVariazioneDiResidenza;

  // private String geoserver;

  private String nomeRichiedente;

  private String cognomeRichiedente;

  private String nominativoRichiedente;

  private String cfRichiedente;

  private String emailRichiedente;

  private String cellulareRichiedente;

  private String pecRichiedente;

  private String telefonoRichiedente;

  private String secondoTelefonoRichiedente;

  private ListItem comboTipologiaIscrizione;

  private LocalDate dataDecorrenza;

  private Integer personIdDemografico;

  private Integer registeredFamilyId;

  private String comuneProvenienza;

  private String codiceComuneProvenienza;

  private String descrizioneComuneProvenienza;

  private String statoProvenienza;

  private String codiceStatoProvenienza;

  private ListItem comboProvenienza;

  private String viario;

  private Integer idVia;

  private String tipoVia;

  private Integer civico;

  private String esponente;

  private String colore;

  private String scala;

  private String sezione;

  private String foglio;

  private String numero;

  private String subalterno;

  private String toponomastica;

  private String nomeCoabitante;

  private String cognomeCoabitante;

  private String cfCoabitante;

  private LocalDate dataNascitaCoabitante;

  private String codiceCoabitante;

  private ListItem comboTipoOccupazione;

  private String descrizioneTitolo;

  private String numeroContratto;

  private LocalDate dataContratto;

  private String comuneAgEntrate;

  private ListItem comboTipologiaFamiglia;

  private String nomeProprietario;

  private String cognomeProprietario;

  private String ragioneSocialeProprietario;

  private String cfProprietario;

  private String comuneNascitaProprietario;

  private String countryNascitaProprietario;

  private LocalDate dataNascitaProprietario;

  private String comuneResidenzaProprietario;

  private String codiceComuneNascitaProprietario;

  private String emailProprietario;

  private String telefonoProprietario;

  private String codiceComuneAgEntrate;

  private String codiceComuneResidenzaProprietario;

  private String indirizzoProprietario;

  private String numeroPratica;

  private boolean vincoliAffettivi;

  private List<IndividuiCollegati> listaIndividuiCollegati;

  private List<IndividuiCollegatiImmigrazione> listaIndividuiCollegatiImmigrazione;

  private Integer idPratica;

  private String documentoUpload;

  private List<DocumentoCaricato> listaAllegati;

  private GetDatiGeneraliPraticaResponse datiGenerali;

  private boolean aggiungi;

  private List<DocumentoPratica> listaDocumentiCaricati;

  private Integer ultimoIdPerNonResidente;

  private String idTipoPratica;

  private String stato;

  private NazionalitaProprietarioEnum nazionalita;

  private String interno;

  private String internoEsponente;

  private String piano;

  private Integer idToponomastica;

  private Toponomastica selectToponomastica;

  private Map<String, List<Toponomastica>> mappaToponomastiche;

  public Toponomastica getSelectToponomastica() {
    return selectToponomastica;
  }

  public void setSelectToponomastica(Toponomastica selectToponomastica) {
    this.selectToponomastica = selectToponomastica;
  }

  public VariazioniDiResidenzaEnum getTipoVariazioneDiResidenza() {
    return tipoVariazioneDiResidenza;
  }

  public void setTipoVariazioneDiResidenza(VariazioniDiResidenzaEnum tipoVariazioneDiResidenza) {
    this.tipoVariazioneDiResidenza = tipoVariazioneDiResidenza;
  }

  public String getNomeRichiedente() {
    return nomeRichiedente;
  }

  public void setNomeRichiedente(String nomeRichiedente) {
    this.nomeRichiedente = nomeRichiedente;
  }

  public String getCognomeRichiedente() {
    return cognomeRichiedente;
  }

  public void setCognomeRichiedente(String cognomeRichiedente) {
    this.cognomeRichiedente = cognomeRichiedente;
  }

  public String getNominativoRichiedente() {
    return nominativoRichiedente;
  }

  public void setNominativoRichiedente(String nominativoRichiedente) {
    this.nominativoRichiedente = nominativoRichiedente;
  }

  public String getCfRichiedente() {
    return cfRichiedente;
  }

  public void setCfRichiedente(String cfRichiedente) {
    this.cfRichiedente = cfRichiedente;
  }

  public String getEmailRichiedente() {
    return emailRichiedente;
  }

  public void setEmailRichiedente(String emailRichiedente) {
    this.emailRichiedente = emailRichiedente;
  }

  public String getCellulareRichiedente() {
    return cellulareRichiedente;
  }

  public void setCellulareRichiedente(String cellulareRichiedente) {
    this.cellulareRichiedente = cellulareRichiedente;
  }

  public String getPecRichiedente() {
    return pecRichiedente;
  }

  public void setPecRichiedente(String pecRichiedente) {
    this.pecRichiedente = pecRichiedente;
  }

  public String getTelefonoRichiedente() {
    return telefonoRichiedente;
  }

  public void setTelefonoRichiedente(String telefonoRichiedente) {
    this.telefonoRichiedente = telefonoRichiedente;
  }

  public String getSecondoTelefonoRichiedente() {
    return secondoTelefonoRichiedente;
  }

  public void setSecondoTelefonoRichiedente(String secondoTelefonoRichiedente) {
    this.secondoTelefonoRichiedente = secondoTelefonoRichiedente;
  }

  public ListItem getComboTipologiaIscrizione() {
    return comboTipologiaIscrizione;
  }

  public void setComboTipologiaIscrizione(ListItem comboTipologiaIscrizione) {
    this.comboTipologiaIscrizione = comboTipologiaIscrizione;
  }

  public LocalDate getDataDecorrenza() {
    return dataDecorrenza;
  }

  public void setDataDecorrenza(LocalDate dataDecorrenza) {
    this.dataDecorrenza = dataDecorrenza;
  }

  public Integer getPersonIdDemografico() {
    return personIdDemografico;
  }

  public void setPersonIdDemografico(Integer personIdDemografico) {
    this.personIdDemografico = personIdDemografico;
  }

  public Integer getRegisteredFamilyId() {
    return registeredFamilyId;
  }

  public void setRegisteredFamilyId(Integer registeredFamilyId) {
    this.registeredFamilyId = registeredFamilyId;
  }

  public String getComuneProvenienza() {
    return comuneProvenienza;
  }

  public void setComuneProvenienza(String comuneProvenienza) {
    this.comuneProvenienza = comuneProvenienza;
  }

  public String getCodiceComuneProvenienza() {
    return codiceComuneProvenienza;
  }

  public void setCodiceComuneProvenienza(String codiceComuneProvenienza) {
    this.codiceComuneProvenienza = codiceComuneProvenienza;
  }

  public String getDescrizioneComuneProvenienza() {
    return descrizioneComuneProvenienza;
  }

  public void setDescrizioneComuneProvenienza(String descrizioneComuneProvenienza) {
    this.descrizioneComuneProvenienza = descrizioneComuneProvenienza;
  }

  public String getStatoProvenienza() {
    return statoProvenienza;
  }

  public void setStatoProvenienza(String statoProvenienza) {
    this.statoProvenienza = statoProvenienza;
  }

  public String getCodiceStatoProvenienza() {
    return codiceStatoProvenienza;
  }

  public void setCodiceStatoProvenienza(String codiceStatoProvenienza) {
    this.codiceStatoProvenienza = codiceStatoProvenienza;
  }

  public ListItem getComboProvenienza() {
    return comboProvenienza;
  }

  public void setComboProvenienza(ListItem comboProvenienza) {
    this.comboProvenienza = comboProvenienza;
  }

  public String getViario() {
    return viario;
  }

  public void setViario(String viario) {
    this.viario = viario;
  }

  public Integer getIdVia() {
    return idVia;
  }

  public void setIdVia(Integer idVia) {
    this.idVia = idVia;
  }

  public Integer getCivico() {
    return civico;
  }

  public void setCivico(Integer civico) {
    this.civico = civico;
  }

  public String getEsponente() {
    return esponente;
  }

  public void setEsponente(String esponente) {
    this.esponente = esponente;
  }

  public String getColore() {
    return colore;
  }

  public void setColore(String colore) {
    this.colore = colore;
  }

  public String getSezione() {
    return sezione;
  }

  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  public String getFoglio() {
    return foglio;
  }

  public void setFoglio(String foglio) {
    this.foglio = foglio;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getSubalterno() {
    return subalterno;
  }

  public void setSubalterno(String subalterno) {
    this.subalterno = subalterno;
  }

  public String getToponomastica() {
    return toponomastica;
  }

  public void setToponomastica(String toponomastica) {
    this.toponomastica = toponomastica;
  }

  public String getNomeCoabitante() {
    return nomeCoabitante;
  }

  public void setNomeCoabitante(String nomeCoabitante) {
    this.nomeCoabitante = nomeCoabitante;
  }

  public String getCognomeCoabitante() {
    return cognomeCoabitante;
  }

  public void setCognomeCoabitante(String cognomeCoabitante) {
    this.cognomeCoabitante = cognomeCoabitante;
  }

  public String getCfCoabitante() {
    return cfCoabitante;
  }

  public void setCfCoabitante(String cfCoabitante) {
    this.cfCoabitante = cfCoabitante;
  }

  public LocalDate getDataNascitaCoabitante() {
    return dataNascitaCoabitante;
  }

  public void setDataNascitaCoabitante(LocalDate dataNascitaCoabitante) {
    this.dataNascitaCoabitante = dataNascitaCoabitante;
  }

  public String getCodiceCoabitante() {
    return codiceCoabitante;
  }

  public void setCodiceCoabitante(String codiceCoabitante) {
    this.codiceCoabitante = codiceCoabitante;
  }

  public ListItem getComboTipoOccupazione() {
    return comboTipoOccupazione;
  }

  public void setComboTipoOccupazione(ListItem comboTipoOccupazione) {
    this.comboTipoOccupazione = comboTipoOccupazione;
  }

  public String getDescrizioneTitolo() {
    return descrizioneTitolo;
  }

  public void setDescrizioneTitolo(String descrizioneTitolo) {
    this.descrizioneTitolo = descrizioneTitolo;
  }

  public String getNumeroContratto() {
    return numeroContratto;
  }

  public void setNumeroContratto(String numeroContratto) {
    this.numeroContratto = numeroContratto;
  }

  public LocalDate getDataContratto() {
    return dataContratto;
  }

  public void setDataContratto(LocalDate dataContratto) {
    this.dataContratto = dataContratto;
  }

  public String getComuneAgEntrate() {
    return comuneAgEntrate;
  }

  public void setComuneAgEntrate(String comuneAgEntrate) {
    this.comuneAgEntrate = comuneAgEntrate;
  }

  public ListItem getComboTipologiaFamiglia() {
    return comboTipologiaFamiglia;
  }

  public void setComboTipologiaFamiglia(ListItem comboTipologiaFamiglia) {
    this.comboTipologiaFamiglia = comboTipologiaFamiglia;
  }

  public String getNomeProprietario() {
    return nomeProprietario;
  }

  public void setNomeProprietario(String nomeProprietario) {
    this.nomeProprietario = nomeProprietario;
  }

  public String getCognomeProprietario() {
    return cognomeProprietario;
  }

  public void setCognomeProprietario(String cognomeProprietario) {
    this.cognomeProprietario = cognomeProprietario;
  }

  public String getRagioneSocialeProprietario() {
    return ragioneSocialeProprietario;
  }

  public void setRagioneSocialeProprietario(String ragioneSocialeProprietario) {
    this.ragioneSocialeProprietario = ragioneSocialeProprietario;
  }

  public String getCfProprietario() {
    return cfProprietario;
  }

  public void setCfProprietario(String cfProprietario) {
    this.cfProprietario = cfProprietario;
  }

  public String getComuneNascitaProprietario() {
    return comuneNascitaProprietario;
  }

  public void setComuneNascitaProprietario(String comuneNascitaProprietario) {
    this.comuneNascitaProprietario = comuneNascitaProprietario;
  }

  public LocalDate getDataNascitaProprietario() {
    return dataNascitaProprietario;
  }

  public void setDataNascitaProprietario(LocalDate dataNascitaProprietario) {
    this.dataNascitaProprietario = dataNascitaProprietario;
  }

  public String getComuneResidenzaProprietario() {
    return comuneResidenzaProprietario;
  }

  public void setComuneResidenzaProprietario(String comuneResidenzaProprietario) {
    this.comuneResidenzaProprietario = comuneResidenzaProprietario;
  }

  public String getCodiceComuneNascitaProprietario() {
    return codiceComuneNascitaProprietario;
  }

  public void setCodiceComuneNascitaProprietario(String codiceComuneNascitaProprietario) {
    this.codiceComuneNascitaProprietario = codiceComuneNascitaProprietario;
  }

  public String getCodiceComuneAgEntrate() {
    return codiceComuneAgEntrate;
  }

  public void setCodiceComuneAgEntrate(String codiceComuneAgEntrate) {
    this.codiceComuneAgEntrate = codiceComuneAgEntrate;
  }

  public String getCodiceComuneResidenzaProprietario() {
    return codiceComuneResidenzaProprietario;
  }

  public void setCodiceComuneResidenzaProprietario(String codiceComuneResidenzaProprietario) {
    this.codiceComuneResidenzaProprietario = codiceComuneResidenzaProprietario;
  }

  public String getIndirizzoProprietario() {
    return indirizzoProprietario;
  }

  public void setIndirizzoProprietario(String indirizzoProprietario) {
    this.indirizzoProprietario = indirizzoProprietario;
  }

  public String getNumeroPratica() {
    return numeroPratica;
  }

  public void setNumeroPratica(String numeroPratica) {
    this.numeroPratica = numeroPratica;
  }

  public boolean isVincoliAffettivi() {
    return vincoliAffettivi;
  }

  public void setVincoliAffettivi(boolean vincoliAffettivi) {
    this.vincoliAffettivi = vincoliAffettivi;
  }

  public List<IndividuiCollegati> getListaIndividuiCollegati() {
    return listaIndividuiCollegati;
  }

  public void setListaIndividuiCollegati(List<IndividuiCollegati> listaIndividuiCollegati) {
    this.listaIndividuiCollegati = listaIndividuiCollegati;
  }

  public List<IndividuiCollegatiImmigrazione> getListaIndividuiCollegatiImmigrazione() {
    return listaIndividuiCollegatiImmigrazione;
  }

  public void setListaIndividuiCollegatiImmigrazione(
      List<IndividuiCollegatiImmigrazione> listaIndividuiCollegatiImmigrazione) {
    this.listaIndividuiCollegatiImmigrazione = listaIndividuiCollegatiImmigrazione;
  }

  public Integer getIdPratica() {
    return idPratica;
  }

  public void setIdPratica(Integer idPratica) {
    this.idPratica = idPratica;
  }

  public String getDocumentoUpload() {
    return documentoUpload;
  }

  public void setDocumentoUpload(String documentoUpload) {
    this.documentoUpload = documentoUpload;
  }

  public List<DocumentoCaricato> getListaAllegati() {
    return listaAllegati;
  }

  public void setListaAllegati(List<DocumentoCaricato> listaAllegati) {
    this.listaAllegati = listaAllegati;
  }

  public GetDatiGeneraliPraticaResponse getDatiGenerali() {
    return datiGenerali;
  }

  public void setDatiGenerali(GetDatiGeneraliPraticaResponse datiGenerali) {
    this.datiGenerali = datiGenerali;
  }

  public boolean isAggiungi() {
    return aggiungi;
  }

  public void setAggiungi(boolean aggiungi) {
    this.aggiungi = aggiungi;
  }

  public List<DocumentoPratica> getListaDocumentiCaricati() {
    return listaDocumentiCaricati;
  }

  public void setListaDocumentiCaricati(List<DocumentoPratica> listaDocumentiCaricati) {
    this.listaDocumentiCaricati = listaDocumentiCaricati;
  }

  public Integer getUltimoIdPerNonResidente() {
    return ultimoIdPerNonResidente;
  }

  public void setUltimoIdPerNonResidente(Integer ultimoIdPerNonResidente) {
    this.ultimoIdPerNonResidente = ultimoIdPerNonResidente;
  }

  public String getIdTipoPratica() {
    return idTipoPratica;
  }

  public void setIdTipoPratica(String idTipoPratica) {
    this.idTipoPratica = idTipoPratica;
  }

  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }

  public NazionalitaProprietarioEnum getNazionalita() {
    return nazionalita;
  }

  public void setNazionalita(NazionalitaProprietarioEnum nazionalita) {
    this.nazionalita = nazionalita;
  }

  public String getEmailProprietario() {
    return emailProprietario;
  }

  public void setEmailProprietario(String emailProprietario) {
    this.emailProprietario = emailProprietario;
  }

  public String getTelefonoProprietario() {
    return telefonoProprietario;
  }

  public void setTelefonoProprietario(String telefonoProprietario) {
    this.telefonoProprietario = telefonoProprietario;
  }

  public String getInterno() {
    return interno;
  }

  public void setInterno(String interno) {
    this.interno = interno;
  }

  public String getScala() {
    return scala;
  }

  public void setScala(String scala) {
    this.scala = scala;
  }

  public String getInternoEsponente() {
    return internoEsponente;
  }

  public void setInternoEsponente(String internoEsponente) {
    this.internoEsponente = internoEsponente;
  }

  public String getPiano() {
    return piano;
  }

  public void setPiano(String piano) {
    this.piano = piano;
  }

  public Integer getIdToponomastica() {
    return idToponomastica;
  }

  public void setIdToponomastica(Integer idToponomastica) {
    this.idToponomastica = idToponomastica;
  }

  public String getTipoVia() {
    return tipoVia;
  }

  public void setTipoVia(String tipoVia) {
    this.tipoVia = tipoVia;
  }

  public Map<String, List<Toponomastica>> getMappaToponomastiche() {
    return mappaToponomastiche;
  }

  public void setMappaToponomastiche(Map<String, List<Toponomastica>> mappaToponomastiche) {
    this.mappaToponomastiche = mappaToponomastiche;
  }

  public String getCountryNascitaProprietario() {
    return countryNascitaProprietario;
  }

  public void setCountryNascitaProprietario(String countryNascitaProprietario) {
    this.countryNascitaProprietario = countryNascitaProprietario;
  }

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  @Override
  public String toString() {
    return "VariazioniResidenza [autocompleteViario="
        + autocompleteViario
        + ", tipoVariazioneDiResidenza="
        + tipoVariazioneDiResidenza
        + ", nomeRichiedente="
        + nomeRichiedente
        + ", cognomeRichiedente="
        + cognomeRichiedente
        + ", nominativoRichiedente="
        + nominativoRichiedente
        + ", cfRichiedente="
        + cfRichiedente
        + ", emailRichiedente="
        + emailRichiedente
        + ", cellulareRichiedente="
        + cellulareRichiedente
        + ", pecRichiedente="
        + pecRichiedente
        + ", telefonoRichiedente="
        + telefonoRichiedente
        + ", secondoTelefonoRichiedente="
        + secondoTelefonoRichiedente
        + ", comboTipologiaIscrizione="
        + comboTipologiaIscrizione
        + ", dataDecorrenza="
        + dataDecorrenza
        + ", personIdDemografico="
        + personIdDemografico
        + ", registeredFamilyId="
        + registeredFamilyId
        + ", comuneProvenienza="
        + comuneProvenienza
        + ", codiceComuneProvenienza="
        + codiceComuneProvenienza
        + ", descrizioneComuneProvenienza="
        + descrizioneComuneProvenienza
        + ", statoProvenienza="
        + statoProvenienza
        + ", codiceStatoProvenienza="
        + codiceStatoProvenienza
        + ", comboProvenienza="
        + comboProvenienza
        + ", viario="
        + viario
        + ", idVia="
        + idVia
        + ", tipoVia="
        + tipoVia
        + ", civico="
        + civico
        + ", esponente="
        + esponente
        + ", colore="
        + colore
        + ", scala="
        + scala
        + ", sezione="
        + sezione
        + ", foglio="
        + foglio
        + ", numero="
        + numero
        + ", subalterno="
        + subalterno
        + ", toponomastica="
        + toponomastica
        + ", nomeCoabitante="
        + nomeCoabitante
        + ", cognomeCoabitante="
        + cognomeCoabitante
        + ", cfCoabitante="
        + cfCoabitante
        + ", dataNascitaCoabitante="
        + dataNascitaCoabitante
        + ", codiceCoabitante="
        + codiceCoabitante
        + ", comboTipoOccupazione="
        + comboTipoOccupazione
        + ", descrizioneTitolo="
        + descrizioneTitolo
        + ", numeroContratto="
        + numeroContratto
        + ", dataContratto="
        + dataContratto
        + ", comuneAgEntrate="
        + comuneAgEntrate
        + ", comboTipologiaFamiglia="
        + comboTipologiaFamiglia
        + ", nomeProprietario="
        + nomeProprietario
        + ", cognomeProprietario="
        + cognomeProprietario
        + ", ragioneSocialeProprietario="
        + ragioneSocialeProprietario
        + ", cfProprietario="
        + cfProprietario
        + ", comuneNascitaProprietario="
        + comuneNascitaProprietario
        + ", countryNascitaProprietario="
        + countryNascitaProprietario
        + ", dataNascitaProprietario="
        + dataNascitaProprietario
        + ", comuneResidenzaProprietario="
        + comuneResidenzaProprietario
        + ", codiceComuneNascitaProprietario="
        + codiceComuneNascitaProprietario
        + ", emailProprietario="
        + emailProprietario
        + ", telefonoProprietario="
        + telefonoProprietario
        + ", codiceComuneAgEntrate="
        + codiceComuneAgEntrate
        + ", codiceComuneResidenzaProprietario="
        + codiceComuneResidenzaProprietario
        + ", indirizzoProprietario="
        + indirizzoProprietario
        + ", numeroPratica="
        + numeroPratica
        + ", vincoliAffettivi="
        + vincoliAffettivi
        + ", listaIndividuiCollegati="
        + listaIndividuiCollegati
        + ", listaIndividuiCollegatiImmigrazione="
        + listaIndividuiCollegatiImmigrazione
        + ", idPratica="
        + idPratica
        + ", datiGenerali="
        + datiGenerali
        + ", aggiungi="
        + aggiungi
        + ", ultimoIdPerNonResidente="
        + ultimoIdPerNonResidente
        + ", idTipoPratica="
        + idTipoPratica
        + ", stato="
        + stato
        + ", nazionalita="
        + nazionalita
        + ", interno="
        + interno
        + ", internoEsponente="
        + internoEsponente
        + ", piano="
        + piano
        + ", idToponomastica="
        + idToponomastica
        + ", selectToponomastica="
        + selectToponomastica
        + ", mappaToponomastiche="
        + mappaToponomastiche
        + "]";
  }
}
