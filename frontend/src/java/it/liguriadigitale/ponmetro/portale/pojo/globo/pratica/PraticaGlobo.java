package it.liguriadigitale.ponmetro.portale.pojo.globo.pratica;

import java.io.Serializable;
import java.time.LocalDate;

public class PraticaGlobo implements Serializable {

  private static final long serialVersionUID = 7770636723257588072L;

  private Long id;
  private String codiceFiscale;
  private String codiceModulo;
  private String URNModulo;
  private String CodiceUODestinataria;
  private String codicePratica;
  private String oggetto;
  private String descrizioneModulo;
  private String url;
  private String urlDownload;
  private LocalDate dataInserimento;
  private LocalDate dataModifica;
  private String stato;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public String getCodiceModulo() {
    return codiceModulo;
  }

  public void setCodiceModulo(String codiceModulo) {
    this.codiceModulo = codiceModulo;
  }

  public String getURNModulo() {
    return URNModulo;
  }

  public void setURNModulo(String uRNModulo) {
    URNModulo = uRNModulo;
  }

  public String getCodiceUODestinataria() {
    return CodiceUODestinataria;
  }

  public void setCodiceUODestinataria(String codiceUODestinataria) {
    CodiceUODestinataria = codiceUODestinataria;
  }

  public String getCodicePratica() {
    return codicePratica;
  }

  public void setCodicePratica(String codicePratica) {
    this.codicePratica = codicePratica;
  }

  public String getOggetto() {
    return oggetto;
  }

  public void setOggetto(String oggetto) {
    this.oggetto = oggetto;
  }

  public String getDescrizioneModulo() {
    return descrizioneModulo;
  }

  public void setDescrizioneModulo(String descrizioneModulo) {
    this.descrizioneModulo = descrizioneModulo;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrlDownload() {
    return urlDownload;
  }

  public void setUrlDownload(String urlDownload) {
    this.urlDownload = urlDownload;
  }

  public LocalDate getDataInserimento() {
    return dataInserimento;
  }

  public void setDataInserimento(LocalDate dataInserimento) {
    this.dataInserimento = dataInserimento;
  }

  public LocalDate getDataModifica() {
    return dataModifica;
  }

  public void setDataModifica(LocalDate dataModifica) {
    this.dataModifica = dataModifica;
  }

  public String getStato() {
    return stato;
  }

  public void setStato(String stato) {
    this.stato = stato;
  }
}
