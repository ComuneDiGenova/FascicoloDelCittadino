package it.liguriadigitale.ponmetro.portale.pojo.dietespeciali;

import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDomandaRichiestaDietaSpeciale.TipoDietaSpecialeEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIstituto;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DietaMotiviEticoReligiosi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro.RientroEnum;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class DietaSpeciale implements Serializable {

  private static final long serialVersionUID = 5371007830981926094L;

  private String cfIscritto;

  private String nomeIscritto;

  private String cognomeIscritto;

  private String cfRichiedente;

  private String nomeRichiedente;

  private String cognomeRichiedente;

  private String emailRichiedente;

  private LocalDate dataRichiesta;

  private TipoDietaSpecialeEnum tipoDieta;

  private String fileAttestazioneMedicaUpload;

  private String nomeFileAttestazioneMedica;

  private byte[] byteFileAttestazioneMedica;

  private String estensioneFileAttestazioneMedica;

  private String mimeTypeFileAttestazioneMedica;

  private String dimensioneFileAttestazioneMedica;

  private DietaMotiviEticoReligiosi comboMenu;

  private boolean anafilassi;

  private String noteIntegrative;

  private Integer classe;

  private String sezione;

  private List<GiorniRientroScuola> listaGiorniRientri;

  private List<RientroEnum> listaGiorniRientriSelezionati;

  private String scuola;

  private DatiIstituto datiIstituto;

  private DatiDirezioneTerritoriale direzioneTerritoriale;

  private AnnoScolastico annoScolastico;

  public DatiDirezioneTerritoriale getDatiDirezioneTerritoriale() {
    return direzioneTerritoriale;
  }

  public void setDatiDirezioneTerritoriale(DatiDirezioneTerritoriale value) {
    direzioneTerritoriale = value;
  }

  public DatiIstituto getDatiIstituto() {
    return datiIstituto;
  }

  public void setDatiIstituto(DatiIstituto value) {
    datiIstituto = value;
  }

  public String getScuola() {
    return scuola;
  }

  public void setScuola(String value) {
    this.scuola = value;
  }

  public String getCfIscritto() {
    return cfIscritto;
  }

  public void setCfIscritto(String cfIscritto) {
    this.cfIscritto = cfIscritto;
  }

  public String getNomeIscritto() {
    return nomeIscritto;
  }

  public void setNomeIscritto(String nomeIscritto) {
    this.nomeIscritto = nomeIscritto;
  }

  public String getCognomeIscritto() {
    return cognomeIscritto;
  }

  public void setCognomeIscritto(String cognomeIscritto) {
    this.cognomeIscritto = cognomeIscritto;
  }

  public String getCfRichiedente() {
    return cfRichiedente;
  }

  public void setCfRichiedente(String cfRichiedente) {
    this.cfRichiedente = cfRichiedente;
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

  public String getEmailRichiedente() {
    return emailRichiedente;
  }

  public void setEmailRichiedente(String emailRichiedente) {
    this.emailRichiedente = emailRichiedente;
  }

  public LocalDate getDataRichiesta() {
    return dataRichiesta;
  }

  public void setDataRichiesta(LocalDate dataRichiesta) {
    this.dataRichiesta = dataRichiesta;
  }

  public TipoDietaSpecialeEnum getTipoDieta() {
    return tipoDieta;
  }

  public void setTipoDieta(TipoDietaSpecialeEnum tipoDieta) {
    this.tipoDieta = tipoDieta;
  }

  public String getFileAttestazioneMedicaUpload() {
    return fileAttestazioneMedicaUpload;
  }

  public void setFileAttestazioneMedicaUpload(String fileAttestazioneMedicaUpload) {
    this.fileAttestazioneMedicaUpload = fileAttestazioneMedicaUpload;
  }

  public String getNomeFileAttestazioneMedica() {
    return nomeFileAttestazioneMedica;
  }

  public void setNomeFileAttestazioneMedica(String nomeFileAttestazioneMedica) {
    this.nomeFileAttestazioneMedica = nomeFileAttestazioneMedica;
  }

  public byte[] getByteFileAttestazioneMedica() {
    return byteFileAttestazioneMedica;
  }

  public void setByteFileAttestazioneMedica(byte[] byteFileAttestazioneMedica) {
    this.byteFileAttestazioneMedica = byteFileAttestazioneMedica;
  }

  public String getEstensioneFileAttestazioneMedica() {
    return estensioneFileAttestazioneMedica;
  }

  public void setEstensioneFileAttestazioneMedica(String estensioneFileAttestazioneMedica) {
    this.estensioneFileAttestazioneMedica = estensioneFileAttestazioneMedica;
  }

  public String getDimensioneFileAttestazioneMedica() {
    return dimensioneFileAttestazioneMedica;
  }

  public void setDimensioneFileAttestazioneMedica(String dimensioneFileAttestazioneMedica) {
    this.dimensioneFileAttestazioneMedica = dimensioneFileAttestazioneMedica;
  }

  public DietaMotiviEticoReligiosi getComboMenu() {
    return comboMenu;
  }

  public void setComboMenu(DietaMotiviEticoReligiosi comboMenu) {
    this.comboMenu = comboMenu;
  }

  public boolean isAnafilassi() {
    return anafilassi;
  }

  public void setAnafilassi(boolean anafilassi) {
    this.anafilassi = anafilassi;
  }

  public String getNoteIntegrative() {
    return noteIntegrative;
  }

  public void setNoteIntegrative(String noteIntegrative) {
    this.noteIntegrative = noteIntegrative;
  }

  public String getMimeTypeFileAttestazioneMedica() {
    return mimeTypeFileAttestazioneMedica;
  }

  public void setMimeTypeFileAttestazioneMedica(String mimeTypeFileAttestazioneMedica) {
    this.mimeTypeFileAttestazioneMedica = mimeTypeFileAttestazioneMedica;
  }

  public Integer getClasse() {
    return classe;
  }

  public void setClasse(Integer classe) {
    this.classe = classe;
  }

  public String getSezione() {
    return sezione;
  }

  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  public List<GiorniRientroScuola> getListaGiorniRientri() {
    return listaGiorniRientri;
  }

  public void setListaGiorniRientri(List<GiorniRientroScuola> listaGiorniRientri) {
    this.listaGiorniRientri = listaGiorniRientri;
  }

  public List<RientroEnum> getListaGiorniRientriSelezionati() {
    return listaGiorniRientriSelezionati;
  }

  public void setListaGiorniRientriSelezionati(List<RientroEnum> values) {
    this.listaGiorniRientriSelezionati = values;
  }

  public DatiDirezioneTerritoriale getDirezioneTerritoriale() {
    return direzioneTerritoriale;
  }

  public void setDirezioneTerritoriale(DatiDirezioneTerritoriale direzioneTerritoriale) {
    this.direzioneTerritoriale = direzioneTerritoriale;
  }

  public AnnoScolastico getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(AnnoScolastico annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  @Override
  public String toString() {
    return "DietaSpeciale [cfIscritto="
        + cfIscritto
        + ", nomeIscritto="
        + nomeIscritto
        + ", cognomeIscritto="
        + cognomeIscritto
        + ", cfRichiedente="
        + cfRichiedente
        + ", nomeRichiedente="
        + nomeRichiedente
        + ", cognomeRichiedente="
        + cognomeRichiedente
        + ", emailRichiedente="
        + emailRichiedente
        + ", dataRichiesta="
        + dataRichiesta
        + ", tipoDieta="
        + tipoDieta
        + ", comboMenu="
        + comboMenu
        + ", anafilassi="
        + anafilassi
        + ", noteIntegrative="
        + noteIntegrative
        + ", classe="
        + classe
        + ", sezione="
        + sezione
        + ", listaGiorniRientri="
        + listaGiorniRientri
        + ", listaGiorniRientriSelezionati="
        + listaGiorniRientriSelezionati
        + ", scuola="
        + scuola
        + ", datiIstituto="
        + datiIstituto
        + ", direzioneTerritoriale="
        + direzioneTerritoriale
        + ", annoScolastico="
        + annoScolastico
        + "]";
  }
}
