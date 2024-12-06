package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.tarieng.TipoEccedenzaRimborsoTariEng;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.EnteBeneficiarioRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipoRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import it.liguriadigitale.ponmetro.taririmborsieng.model.FileAllegato;
import it.liguriadigitale.ponmetro.taririmborsieng.model.IstanzaRimborsoPOSTResponse;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class DatiRichiestaRimborsoTariEng implements Serializable {

  private static final long serialVersionUID = -8687389573228599896L;

  private String codiceBelfiore;

  private TipologiaRichiedenteRimborsoEnum tipologiaRichiedente;

  private String nomeRichiedente;

  private String cognomeRichiedente;

  private String codiceFiscaleRichiedente;

  private String emailRichiedente;

  private Long idDeb;

  private String numeroDocumento;

  private TipoRimborsoEnum tipoRimborso;

  private EnteBeneficiarioRimborsoEnum enteBeneficiarioTari;

  private EnteBeneficiarioRimborsoEnum enteBeneficiarioTefa;

  private Double eccTari;

  private Double eccTefa;

  private Double eccTariR;

  private Double sommaEccedenze;

  private LocalDate dataIstanza;

  private String note;

  private ModalitaPagamentoEnum modalitaDiPagamento;

  private String iban;

  private String swift;

  private String nomeBeneficiario;

  private String cognomeBeneficiario;

  private String codiceFiscaleBeneficiario;

  private String indirizzoBeneficiario;

  private String comuneBeneficiario;

  private String capBeneficiario;

  private String nomeDelegato;

  private String cognomeDelegato;

  private String codiceFiscaleDelegato;

  private String allegatiRimborsoUpload;

  private String nomeAllegatoRimborsoUpload;

  private byte[] byteAllegatoRimborsoUpload;

  private List<FileAllegato> listaAllegati;

  private IstanzaRimborsoPOSTResponse responsePostRimborsoTARI;

  private IstanzaRimborsoPOSTResponse responsePostRimborsoTEFA;

  private IstanzaRimborsoPOSTResponse responsePostRimborsoTARIREALE;

  private boolean eccTariRichiedibile;

  private boolean eccTefaRichiedibile;

  private boolean eccTariRealeRichiedibile;

  private String infoEccTari;

  private String infoEccTefa;

  private String infoEccTariR;

  private TipoEccedenzaRimborsoTariEng tipoEccedenzaRimborso;

  private DatiDocumentiTariEng datiDocumentiTariEng;

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

  public String getEmailRichiedente() {
    return emailRichiedente;
  }

  public void setEmailRichiedente(String emailRichiedente) {
    this.emailRichiedente = emailRichiedente;
  }

  public Long getIdDeb() {
    return idDeb;
  }

  public void setIdDeb(Long idDeb) {
    this.idDeb = idDeb;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public EnteBeneficiarioRimborsoEnum getEnteBeneficiarioTari() {
    return enteBeneficiarioTari;
  }

  public void setEnteBeneficiarioTari(EnteBeneficiarioRimborsoEnum enteBeneficiarioTari) {
    this.enteBeneficiarioTari = enteBeneficiarioTari;
  }

  public EnteBeneficiarioRimborsoEnum getEnteBeneficiarioTefa() {
    return enteBeneficiarioTefa;
  }

  public void setEnteBeneficiarioTefa(EnteBeneficiarioRimborsoEnum enteBeneficiarioTefa) {
    this.enteBeneficiarioTefa = enteBeneficiarioTefa;
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

  public String getNomeBeneficiario() {
    return nomeBeneficiario;
  }

  public void setNomeBeneficiario(String nomeBeneficiario) {
    this.nomeBeneficiario = nomeBeneficiario;
  }

  public String getCognomeBeneficiario() {
    return cognomeBeneficiario;
  }

  public void setCognomeBeneficiario(String cognomeBeneficiario) {
    this.cognomeBeneficiario = cognomeBeneficiario;
  }

  public String getIndirizzoBeneficiario() {
    return indirizzoBeneficiario;
  }

  public void setIndirizzoBeneficiario(String indirizzoBeneficiario) {
    this.indirizzoBeneficiario = indirizzoBeneficiario;
  }

  public String getComuneBeneficiario() {
    return comuneBeneficiario;
  }

  public void setComuneBeneficiario(String comuneBeneficiario) {
    this.comuneBeneficiario = comuneBeneficiario;
  }

  public String getCodiceBelfiore() {
    return codiceBelfiore;
  }

  public void setCodiceBelfiore(String codiceBelfiore) {
    this.codiceBelfiore = codiceBelfiore;
  }

  public LocalDate getDataIstanza() {
    return dataIstanza;
  }

  public void setDataIstanza(LocalDate dataIstanza) {
    this.dataIstanza = dataIstanza;
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

  public List<FileAllegato> getListaAllegati() {
    return listaAllegati;
  }

  public void setListaAllegati(List<FileAllegato> listaAllegati) {
    this.listaAllegati = listaAllegati;
  }

  public TipologiaRichiedenteRimborsoEnum getTipologiaRichiedente() {
    return tipologiaRichiedente;
  }

  public void setTipologiaRichiedente(TipologiaRichiedenteRimborsoEnum tipologiaRichiedente) {
    this.tipologiaRichiedente = tipologiaRichiedente;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public TipoRimborsoEnum getTipoRimborso() {
    return tipoRimborso;
  }

  public void setTipoRimborso(TipoRimborsoEnum tipoRimborso) {
    this.tipoRimborso = tipoRimborso;
  }

  public ModalitaPagamentoEnum getModalitaDiPagamento() {
    return modalitaDiPagamento;
  }

  public void setModalitaDiPagamento(ModalitaPagamentoEnum modalitaDiPagamento) {
    this.modalitaDiPagamento = modalitaDiPagamento;
  }

  public String getCodiceFiscaleBeneficiario() {
    return codiceFiscaleBeneficiario;
  }

  public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
    this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
  }

  public String getCapBeneficiario() {
    return capBeneficiario;
  }

  public void setCapBeneficiario(String capBeneficiario) {
    this.capBeneficiario = capBeneficiario;
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

  public IstanzaRimborsoPOSTResponse getResponsePostRimborsoTARI() {
    return responsePostRimborsoTARI;
  }

  public void setResponsePostRimborsoTARI(IstanzaRimborsoPOSTResponse responsePostRimborsoTARI) {
    this.responsePostRimborsoTARI = responsePostRimborsoTARI;
  }

  public IstanzaRimborsoPOSTResponse getResponsePostRimborsoTEFA() {
    return responsePostRimborsoTEFA;
  }

  public void setResponsePostRimborsoTEFA(IstanzaRimborsoPOSTResponse responsePostRimborsoTEFA) {
    this.responsePostRimborsoTEFA = responsePostRimborsoTEFA;
  }

  public Double getEccTariR() {
    return eccTariR;
  }

  public void setEccTariR(Double eccTariR) {
    this.eccTariR = eccTariR;
  }

  public Double getSommaEccedenze() {
    return sommaEccedenze;
  }

  public void setSommaEccedenze(Double sommaEccedenze) {
    this.sommaEccedenze = sommaEccedenze;
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

  public IstanzaRimborsoPOSTResponse getResponsePostRimborsoTARIREALE() {
    return responsePostRimborsoTARIREALE;
  }

  public void setResponsePostRimborsoTARIREALE(
      IstanzaRimborsoPOSTResponse responsePostRimborsoTARIREALE) {
    this.responsePostRimborsoTARIREALE = responsePostRimborsoTARIREALE;
  }

  public String getInfoEccTari() {
    return infoEccTari;
  }

  public void setInfoEccTari(String infoEccTari) {
    this.infoEccTari = infoEccTari;
  }

  public String getInfoEccTefa() {
    return infoEccTefa;
  }

  public void setInfoEccTefa(String infoEccTefa) {
    this.infoEccTefa = infoEccTefa;
  }

  public String getInfoEccTariR() {
    return infoEccTariR;
  }

  public void setInfoEccTariR(String infoEccTariR) {
    this.infoEccTariR = infoEccTariR;
  }

  public TipoEccedenzaRimborsoTariEng getTipoEccedenzaRimborso() {
    return tipoEccedenzaRimborso;
  }

  public void setTipoEccedenzaRimborso(TipoEccedenzaRimborsoTariEng tipoEccedenzaRimborso) {
    this.tipoEccedenzaRimborso = tipoEccedenzaRimborso;
  }

  public DatiDocumentiTariEng getDatiDocumentiTariEng() {
    return datiDocumentiTariEng;
  }

  public void setDatiDocumentiTariEng(DatiDocumentiTariEng datiDocumentiTariEng) {
    this.datiDocumentiTariEng = datiDocumentiTariEng;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DatiRichiestaRimborsoTariEng [codiceBelfiore=");
    builder.append(codiceBelfiore);
    builder.append(", tipologiaRichiedente=");
    builder.append(tipologiaRichiedente);
    builder.append(", nomeRichiedente=");
    builder.append(nomeRichiedente);
    builder.append(", cognomeRichiedente=");
    builder.append(cognomeRichiedente);
    builder.append(", codiceFiscaleRichiedente=");
    builder.append(codiceFiscaleRichiedente);
    builder.append(", emailRichiedente=");
    builder.append(emailRichiedente);
    builder.append(", idDeb=");
    builder.append(idDeb);
    builder.append(", numeroDocumento=");
    builder.append(numeroDocumento);
    builder.append(", tipoRimborso=");
    builder.append(tipoRimborso);
    builder.append(", enteBeneficiarioTari=");
    builder.append(enteBeneficiarioTari);
    builder.append(", enteBeneficiarioTefa=");
    builder.append(enteBeneficiarioTefa);
    builder.append(", eccTari=");
    builder.append(eccTari);
    builder.append(", eccTefa=");
    builder.append(eccTefa);
    builder.append(", eccTariR=");
    builder.append(eccTariR);
    builder.append(", sommaEccedenze=");
    builder.append(sommaEccedenze);
    builder.append(", dataIstanza=");
    builder.append(dataIstanza);
    builder.append(", note=");
    builder.append(note);
    builder.append(", modalitaDiPagamento=");
    builder.append(modalitaDiPagamento);
    builder.append(", iban=");
    builder.append(iban);
    builder.append(", swift=");
    builder.append(swift);
    builder.append(", nomeBeneficiario=");
    builder.append(nomeBeneficiario);
    builder.append(", cognomeBeneficiario=");
    builder.append(cognomeBeneficiario);
    builder.append(", codiceFiscaleBeneficiario=");
    builder.append(codiceFiscaleBeneficiario);
    builder.append(", indirizzoBeneficiario=");
    builder.append(indirizzoBeneficiario);
    builder.append(", comuneBeneficiario=");
    builder.append(comuneBeneficiario);
    builder.append(", capBeneficiario=");
    builder.append(capBeneficiario);
    builder.append(", nomeDelegato=");
    builder.append(nomeDelegato);
    builder.append(", cognomeDelegato=");
    builder.append(cognomeDelegato);
    builder.append(", codiceFiscaleDelegato=");
    builder.append(codiceFiscaleDelegato);
    builder.append(", nomeAllegatoRimborsoUpload=");
    builder.append(nomeAllegatoRimborsoUpload);
    builder.append(", responsePostRimborsoTARI=");
    builder.append(responsePostRimborsoTARI);
    builder.append(", responsePostRimborsoTEFA=");
    builder.append(responsePostRimborsoTEFA);
    builder.append(", responsePostRimborsoTARIREALE=");
    builder.append(responsePostRimborsoTARIREALE);
    builder.append(", eccTariRichiedibile=");
    builder.append(eccTariRichiedibile);
    builder.append(", eccTefaRichiedibile=");
    builder.append(eccTefaRichiedibile);
    builder.append(", eccTariRealeRichiedibile=");
    builder.append(eccTariRealeRichiedibile);
    builder.append(", infoEccTari=");
    builder.append(infoEccTari);
    builder.append(", infoEccTefa=");
    builder.append(infoEccTefa);
    builder.append(", infoEccTariR=");
    builder.append(infoEccTariR);
    builder.append(", tipoEccedenzaRimborso=");
    builder.append(tipoEccedenzaRimborso);
    builder.append("]");
    return builder.toString();
  }
}
