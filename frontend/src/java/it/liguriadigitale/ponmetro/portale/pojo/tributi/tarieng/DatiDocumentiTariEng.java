package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng;

import it.liguriadigitale.ponmetro.tarieng.model.DettaglioDocumentoEmesso;
import it.liguriadigitale.ponmetro.tarieng.model.Rate;
import java.io.Serializable;
import java.util.List;

public class DatiDocumentiTariEng implements Serializable {

  private static final long serialVersionUID = 6862240984844978847L;

  private String esito;

  private Long idDeb;

  private Long idDoc;

  private String tipoDoc;

  private Double importo;

  private String desc;

  private String tipoUtz;

  private String ruolo;

  private String numDoc;

  private Double eccTari;

  private Double eccTefa;

  private Double eccTariR;

  private Double totaleEccedenze;

  private String messaggioPerEccedenze;

  private boolean eccTariRichiedibile;

  private boolean eccTefaRichiedibile;

  private boolean eccTariRealeRichiedibile;

  private boolean rimborsoRichiedibile;

  private boolean isCardRimborsiPosizioneVisibile;

  private String annoEmissione;

  private Long anno;

  private Double incassato;

  private String denominazione;

  private String nome;

  private String cognome;

  private String codiceFiscale;

  private String email;

  private String indirizzo;

  private List<Rate> listaRate;

  private Double idAllegato;

  private String frqAgg;

  private Double dov;

  private Double pag;

  private String messaggioPosizione;

  private Long idUltimoDoc;

  private String messaggioDocumento;

  private boolean isBtnPagaConPagoPaVisibile;

  private String flagMigrazioneEsperta;

  private String uriDocumento;

  private DettaglioDocumentoEmesso dettagliDocumento;

  private boolean listaDocEmessiPiena;

  public String getEsito() {
    return esito;
  }

  public void setEsito(String esito) {
    this.esito = esito;
  }

  public Long getIdDeb() {
    return idDeb;
  }

  public void setIdDeb(Long idDeb) {
    this.idDeb = idDeb;
  }

  public Long getIdDoc() {
    return idDoc;
  }

  public void setIdDoc(Long idDoc) {
    this.idDoc = idDoc;
  }

  public String getTipoDoc() {
    return tipoDoc;
  }

  public void setTipoDoc(String tipoDoc) {
    this.tipoDoc = tipoDoc;
  }

  public Double getImporto() {
    return importo;
  }

  public void setImporto(Double importo) {
    this.importo = importo;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getTipoUtz() {
    return tipoUtz;
  }

  public void setTipoUtz(String tipoUtz) {
    this.tipoUtz = tipoUtz;
  }

  public String getRuolo() {
    return ruolo;
  }

  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  public String getNumDoc() {
    return numDoc;
  }

  public void setNumDoc(String numDoc) {
    this.numDoc = numDoc;
  }

  public Double getEccTari() {
    return eccTari;
  }

  public void setEccTari(Double eccTari) {
    this.eccTari = eccTari;
  }

  public Double getEccTefa() {
    return eccTefa;
  }

  public void setEccTefa(Double eccTefa) {
    this.eccTefa = eccTefa;
  }

  public String getAnnoEmissione() {
    return annoEmissione;
  }

  public void setAnnoEmissione(String annoEmissione) {
    this.annoEmissione = annoEmissione;
  }

  public Double getIncassato() {
    return incassato;
  }

  public void setIncassato(Double incassato) {
    this.incassato = incassato;
  }

  public String getDenominazione() {
    return denominazione;
  }

  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public List<Rate> getListaRate() {
    return listaRate;
  }

  public void setListaRate(List<Rate> listaRate) {
    this.listaRate = listaRate;
  }

  public Long getAnno() {
    return anno;
  }

  public void setAnno(Long anno) {
    this.anno = anno;
  }

  public Double getIdAllegato() {
    return idAllegato;
  }

  public void setIdAllegato(Double idAllegato) {
    this.idAllegato = idAllegato;
  }

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

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Double getEccTariR() {
    return eccTariR;
  }

  public void setEccTariR(Double eccTariR) {
    this.eccTariR = eccTariR;
  }

  public Double getTotaleEccedenze() {
    return totaleEccedenze;
  }

  public void setTotaleEccedenze(Double totaleEccedenze) {
    this.totaleEccedenze = totaleEccedenze;
  }

  public String getMessaggioPerEccedenze() {
    return messaggioPerEccedenze;
  }

  public void setMessaggioPerEccedenze(String messaggioPerEccedenze) {
    this.messaggioPerEccedenze = messaggioPerEccedenze;
  }

  public boolean isEccTariRichiedibile() {
    return eccTariRichiedibile;
  }

  public void setEccTariRichiedibile(boolean eccTariRichiedibile) {
    this.eccTariRichiedibile = eccTariRichiedibile;
  }

  public boolean isEccTefaRichiedibile() {
    return eccTefaRichiedibile;
  }

  public void setEccTefaRichiedibile(boolean eccTefaRichiedibile) {
    this.eccTefaRichiedibile = eccTefaRichiedibile;
  }

  public boolean isEccTariRealeRichiedibile() {
    return eccTariRealeRichiedibile;
  }

  public void setEccTariRealeRichiedibile(boolean eccTariRealeRichiedibile) {
    this.eccTariRealeRichiedibile = eccTariRealeRichiedibile;
  }

  public boolean isRimborsoRichiedibile() {
    return rimborsoRichiedibile;
  }

  public void setRimborsoRichiedibile(boolean rimborsoRichiedibile) {
    this.rimborsoRichiedibile = rimborsoRichiedibile;
  }

  public String getFrqAgg() {
    return frqAgg;
  }

  public void setFrqAgg(String frqAgg) {
    this.frqAgg = frqAgg;
  }

  public Double getDov() {
    return dov;
  }

  public void setDov(Double dov) {
    this.dov = dov;
  }

  public Double getPag() {
    return pag;
  }

  public void setPag(Double pag) {
    this.pag = pag;
  }

  public String getMessaggioPosizione() {
    return messaggioPosizione;
  }

  public void setMessaggioPosizione(String messaggioPosizione) {
    this.messaggioPosizione = messaggioPosizione;
  }

  public Long getIdUltimoDoc() {
    return idUltimoDoc;
  }

  public void setIdUltimoDoc(Long idUltimoDoc) {
    this.idUltimoDoc = idUltimoDoc;
  }

  public boolean isBtnPagaConPagoPaVisibile() {
    return isBtnPagaConPagoPaVisibile;
  }

  public void setBtnPagaConPagoPaVisibile(boolean isBtnPagaConPagoPaVisibile) {
    this.isBtnPagaConPagoPaVisibile = isBtnPagaConPagoPaVisibile;
  }

  public String getMessaggioDocumento() {
    return messaggioDocumento;
  }

  public void setMessaggioDocumento(String messaggioDocumento) {
    this.messaggioDocumento = messaggioDocumento;
  }

  public boolean isCardRimborsiPosizioneVisibile() {
    return isCardRimborsiPosizioneVisibile;
  }

  public void setCardRimborsiPosizioneVisibile(boolean isCardRimborsiPosizioneVisibile) {
    this.isCardRimborsiPosizioneVisibile = isCardRimborsiPosizioneVisibile;
  }

  public String getUriDocumento() {
    return uriDocumento;
  }

  public void setUriDocumento(String uriDocumento) {
    this.uriDocumento = uriDocumento;
  }

  public DettaglioDocumentoEmesso getDettagliDocumento() {
    return dettagliDocumento;
  }

  public void setDettagliDocumento(DettaglioDocumentoEmesso dettagliDocumento) {
    this.dettagliDocumento = dettagliDocumento;
  }

  public String getFlagMigrazioneEsperta() {
    return flagMigrazioneEsperta;
  }

  public void setFlagMigrazioneEsperta(String flagMigrazioneEsperta) {
    this.flagMigrazioneEsperta = flagMigrazioneEsperta;
  }

  public boolean isListaDocEmessiPiena() {
    return listaDocEmessiPiena;
  }

  public void setListaDocEmessiPiena(boolean listaDocEmessiPiena) {
    this.listaDocEmessiPiena = listaDocEmessiPiena;
  }

  @Override
  public String toString() {
    return "DatiDocumentiTariEng [esito="
        + esito
        + ", idDeb="
        + idDeb
        + ", idDoc="
        + idDoc
        + ", tipoDoc="
        + tipoDoc
        + ", importo="
        + importo
        + ", desc="
        + desc
        + ", tipoUtz="
        + tipoUtz
        + ", ruolo="
        + ruolo
        + ", numDoc="
        + numDoc
        + ", eccTari="
        + eccTari
        + ", eccTefa="
        + eccTefa
        + ", eccTariR="
        + eccTariR
        + ", totaleEccedenze="
        + totaleEccedenze
        + ", messaggioPerEccedenze="
        + messaggioPerEccedenze
        + ", eccTariRichiedibile="
        + eccTariRichiedibile
        + ", eccTefaRichiedibile="
        + eccTefaRichiedibile
        + ", eccTariRealeRichiedibile="
        + eccTariRealeRichiedibile
        + ", rimborsoRichiedibile="
        + rimborsoRichiedibile
        + ", isCardRimborsiPosizioneVisibile="
        + isCardRimborsiPosizioneVisibile
        + ", annoEmissione="
        + annoEmissione
        + ", anno="
        + anno
        + ", incassato="
        + incassato
        + ", denominazione="
        + denominazione
        + ", nome="
        + nome
        + ", cognome="
        + cognome
        + ", codiceFiscale="
        + codiceFiscale
        + ", email="
        + email
        + ", indirizzo="
        + indirizzo
        + ", listaRate="
        + listaRate
        + ", idAllegato="
        + idAllegato
        + ", frqAgg="
        + frqAgg
        + ", dov="
        + dov
        + ", pag="
        + pag
        + ", messaggioPosizione="
        + messaggioPosizione
        + ", idUltimoDoc="
        + idUltimoDoc
        + ", messaggioDocumento="
        + messaggioDocumento
        + ", isBtnPagaConPagoPaVisibile="
        + isBtnPagaConPagoPaVisibile
        + ", flagMigrazioneEsperta="
        + flagMigrazioneEsperta
        + ", uriDocumento="
        + uriDocumento
        + ", dettagliDocumento="
        + dettagliDocumento
        + ", listaDocEmessiPiena="
        + listaDocEmessiPiena
        + "]";
  }
}
