package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.Saldi;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatiRichiestaRimborsoTariNetribe implements Serializable {

  private static final long serialVersionUID = 2809605909187663773L;

  private boolean isIntestatario;

  private LocalDate dataInvioRichiesta;

  private TipologiaRichiedenteRimborsoEnum tipologiaRichiedenteRimborso;

  private String nomeRichiedente;

  private String cognomeRichiedente;

  private String nominativoRichiedente;

  private String codiceFiscaleRichiedente;

  private String emailRichiedente;

  private String indirizzoRichiedente;

  private String comuneRichiedente;

  private String capRichiedente;

  private String nomeIntestatario;

  private String cognomeIntestatario;

  private String codiceFiscaleIntestatario;

  private String nomeDelegato;

  private String cognomeDelegato;

  private String nominativoDelegato;

  private String codiceFiscaleDelegato;

  private ModalitaPagamentoEnum modalitaDiPagamento;

  private String iban;

  private String swift;

  private String idAvvisoBolletta;

  private Double importoRimborso;

  private String note;

  private List<Saldi> listaSaldi;

  private List<FileAllegato> listaAllegati;

  private String allegatiRimborsoUpload;

  private String nomeAllegatoRimborsoUpload;

  private byte[] byteAllegatoRimborsoUpload;

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

  public String getCodiceFiscaleRichiedente() {
    return codiceFiscaleRichiedente;
  }

  public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
    this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
  }

  public TipologiaRichiedenteRimborsoEnum getTipologiaRichiedenteRimborso() {
    return tipologiaRichiedenteRimborso;
  }

  public void setTipologiaRichiedenteRimborso(
      TipologiaRichiedenteRimborsoEnum tipologiaRichiedenteRimborso) {
    this.tipologiaRichiedenteRimborso = tipologiaRichiedenteRimborso;
  }

  public boolean isIntestatario() {
    return isIntestatario;
  }

  public void setIntestatario(boolean isIntestatario) {
    this.isIntestatario = isIntestatario;
  }

  public String getIdAvvisoBolletta() {
    return idAvvisoBolletta;
  }

  public void setIdAvvisoBolletta(String idAvvisoBolletta) {
    this.idAvvisoBolletta = idAvvisoBolletta;
  }

  public Double getImportoRimborso() {
    return importoRimborso;
  }

  public void setImportoRimborso(Double importoRimborso) {
    this.importoRimborso = importoRimborso;
  }

  public String getEmailRichiedente() {
    return emailRichiedente;
  }

  public void setEmailRichiedente(String emailRichiedente) {
    this.emailRichiedente = emailRichiedente;
  }

  public LocalDate getDataInvioRichiesta() {
    return dataInvioRichiesta;
  }

  public void setDataInvioRichiesta(LocalDate dataInvioRichiesta) {
    this.dataInvioRichiesta = dataInvioRichiesta;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public List<Saldi> getListaSaldi() {
    return listaSaldi;
  }

  public void setListaSaldi(List<Saldi> listaSaldi) {
    this.listaSaldi = listaSaldi;
  }

  public String getIndirizzoRichiedente() {
    return indirizzoRichiedente;
  }

  public void setIndirizzoRichiedente(String indirizzoRichiedente) {
    this.indirizzoRichiedente = indirizzoRichiedente;
  }

  public String getComuneRichiedente() {
    return comuneRichiedente;
  }

  public void setComuneRichiedente(String comuneRichiedente) {
    this.comuneRichiedente = comuneRichiedente;
  }

  public String getCapRichiedente() {
    return capRichiedente;
  }

  public void setCapRichiedente(String capRichiedente) {
    this.capRichiedente = capRichiedente;
  }

  public String getNomeIntestatario() {
    return nomeIntestatario;
  }

  public void setNomeIntestatario(String nomeIntestatario) {
    this.nomeIntestatario = nomeIntestatario;
  }

  public String getCognomeIntestatario() {
    return cognomeIntestatario;
  }

  public void setCognomeIntestatario(String cognomeIntestatario) {
    this.cognomeIntestatario = cognomeIntestatario;
  }

  public String getCodiceFiscaleIntestatario() {
    return codiceFiscaleIntestatario;
  }

  public void setCodiceFiscaleIntestatario(String codiceFiscaleIntestatario) {
    this.codiceFiscaleIntestatario = codiceFiscaleIntestatario;
  }

  public String getNomeDelegato() {
    return nomeDelegato;
  }

  public void setNomeDelegato(String nomeDelegato) {
    this.nomeDelegato = nomeDelegato;
  }

  public String getCognomeDelegato() {
    return cognomeDelegato;
  }

  public void setCognomeDelegato(String cognomeDelegato) {
    this.cognomeDelegato = cognomeDelegato;
  }

  public String getCodiceFiscaleDelegato() {
    return codiceFiscaleDelegato;
  }

  public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
    this.codiceFiscaleDelegato = codiceFiscaleDelegato;
  }

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public String getSwift() {
    return swift;
  }

  public void setSwift(String swift) {
    this.swift = swift;
  }

  public ModalitaPagamentoEnum getModalitaDiPagamento() {
    return modalitaDiPagamento;
  }

  public void setModalitaDiPagamento(ModalitaPagamentoEnum modalitaDiPagamento) {
    this.modalitaDiPagamento = modalitaDiPagamento;
  }

  public List<FileAllegato> getListaAllegati() {
    return listaAllegati;
  }

  public void setListaAllegati(List<FileAllegato> listaAllegati) {
    this.listaAllegati = listaAllegati;
  }

  public String getAllegatiRimborsoUpload() {
    return allegatiRimborsoUpload;
  }

  public void setAllegatiRimborsoUpload(String allegatiRimborsoUpload) {
    this.allegatiRimborsoUpload = allegatiRimborsoUpload;
  }

  public String getNomeAllegatoRimborsoUpload() {
    return nomeAllegatoRimborsoUpload;
  }

  public void setNomeAllegatoRimborsoUpload(String nomeAllegatoRimborsoUpload) {
    this.nomeAllegatoRimborsoUpload = nomeAllegatoRimborsoUpload;
  }

  public byte[] getByteAllegatoRimborsoUpload() {
    return byteAllegatoRimborsoUpload;
  }

  public void setByteAllegatoRimborsoUpload(byte[] byteAllegatoRimborsoUpload) {
    this.byteAllegatoRimborsoUpload = byteAllegatoRimborsoUpload;
  }

  public String getNominativoDelegato() {
    return nominativoDelegato;
  }

  public void setNominativoDelegato(String nominativoDelegato) {
    this.nominativoDelegato = nominativoDelegato;
  }

  public String getNominativoRichiedente() {
    return nominativoRichiedente;
  }

  public void setNominativoRichiedente(String nominativoRichiedente) {
    this.nominativoRichiedente = nominativoRichiedente;
  }

  @Override
  public String toString() {
    return "DatiRichiestaRimborsoTariNetribe [isIntestatario="
        + isIntestatario
        + ", dataInvioRichiesta="
        + dataInvioRichiesta
        + ", tipologiaRichiedenteRimborso="
        + tipologiaRichiedenteRimborso
        + ", nomeRichiedente="
        + nomeRichiedente
        + ", cognomeRichiedente="
        + cognomeRichiedente
        + ", nominativoRichiedente="
        + nominativoRichiedente
        + ", codiceFiscaleRichiedente="
        + codiceFiscaleRichiedente
        + ", emailRichiedente="
        + emailRichiedente
        + ", indirizzoRichiedente="
        + indirizzoRichiedente
        + ", comuneRichiedente="
        + comuneRichiedente
        + ", capRichiedente="
        + capRichiedente
        + ", nomeIntestatario="
        + nomeIntestatario
        + ", cognomeIntestatario="
        + cognomeIntestatario
        + ", codiceFiscaleIntestatario="
        + codiceFiscaleIntestatario
        + ", nomeDelegato="
        + nomeDelegato
        + ", cognomeDelegato="
        + cognomeDelegato
        + ", nominativoDelegato="
        + nominativoDelegato
        + ", codiceFiscaleDelegato="
        + codiceFiscaleDelegato
        + ", modalitaDiPagamento="
        + modalitaDiPagamento
        + ", iban="
        + iban
        + ", swift="
        + swift
        + ", idAvvisoBolletta="
        + idAvvisoBolletta
        + ", importoRimborso="
        + importoRimborso
        + ", note="
        + note
        + ", listaSaldi="
        + listaSaldi
        + ", listaAllegati="
        + listaAllegati
        + ", allegatiRimborsoUpload="
        + allegatiRimborsoUpload
        + ", nomeAllegatoRimborsoUpload="
        + nomeAllegatoRimborsoUpload
        + ", byteAllegatoRimborsoUpload="
        + Arrays.toString(byteAllegatoRimborsoUpload)
        + "]";
  }
}
