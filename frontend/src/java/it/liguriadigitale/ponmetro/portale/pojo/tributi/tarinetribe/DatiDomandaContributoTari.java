package it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe;

import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIseeRichiederenteECoresidenti;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiModalitaPagamentoAgevolazioneTariffariaTari.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiResidenti;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class DatiDomandaContributoTari implements Serializable {

  private static final long serialVersionUID = 248164688563785452L;

  // STEP 1

  private LocalDate presentabileDa;

  private LocalDate presentabileFinoAl;

  private Double mqMassimi;

  private Double iseeMassimo;

  private boolean iseePresentato;

  private Double importoIsee;

  private boolean importoIseeNelRange;

  private List<NucleoFamiliareComponenteNucleoInner> listaComponentiNucleoIsee;

  private List<Residente> listaComponentiNucleoAnagrafe;

  private boolean isNucleiAnagrafeIseeUguali;

  private boolean domandaPresentabile;

  private List<ResidenteTari> listaTuttiNucleiTari;

  private List<ResidenteTari> listaTuttiIS;

  private List<DatiResidenti> listaDatiResidenti;

  private Integer numeroComponenti;

  private List<DatiPersoneACaricoContributoTariNetribe> listaComponentiNucleoIseeACarico;

  private List<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> datiPersoneACarico;

  private List<DatiIseeExtend> datiIseeCompleti;

  private DatiIseeRichiederenteECoresidenti datiIseeTuttiNuclei;

  private boolean isOver70DaSolo;

  private boolean isOver70ConAltriIs;

  private boolean isOver70ConUnCoresidente;

  private boolean isNucleoFamiliareConDa1A4FigliUnder26;

  private boolean isRequisitiMinimiEtaENucleo;

  private boolean isUnder70;

  // STEP 2

  private String nome;

  private String cognome;

  private String nominativo;

  private String codiceFiscale;

  private LocalDate dataNascita;

  private String email;

  private String cellulare;

  private String cittadinanza;

  private String codiceCittadinanza;

  private ModalitaPagamentoEnum modalitaDiPagamento;

  private String iban;

  private String swift;

  private String nomeDelegato;

  private String cognomeDelegato;

  private String codiceFiscaleDelegato;

  private FileAllegato fileAllegato;

  private String allegatoContributoUpload;

  private String nomeAllegatoRimborsoUpload;

  private byte[] byteAllegatoRimborsoUpload;

  // STEP 3

  private String autodichiarazioneCategoriaImmobile;

  private Integer flagCategoriaImmobileDiversaDaA1A8A9;

  private String autodichiarazioneMqImmobile;

  private Integer flagSuperficieImmobileEntroMq;

  private Integer flagPensionatoSuperiore70anni;

  private String autodichiarazioneAltreAgevolazioni;

  private Integer flagBeneficarioNonFruitoreAltreAgevolazioni;

  private Integer flagPensionatoSuperiore70anni1FamiliareACarico;

  private Integer flagBeneficarioConFigliACaricoMax4;

  private String nominativoIseeACarico;

  private String autodichiarazioneInRegolaTari;

  private Integer flagInRegolaTari;

  //

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

  public boolean isImportoIseeNelRange() {
    return importoIseeNelRange;
  }

  public void setImportoIseeNelRange(boolean importoIseeNelRange) {
    this.importoIseeNelRange = importoIseeNelRange;
  }

  public boolean isDomandaPresentabile() {
    return domandaPresentabile;
  }

  public void setDomandaPresentabile(boolean domandaPresentabile) {
    this.domandaPresentabile = domandaPresentabile;
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

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  public ModalitaPagamentoEnum getModalitaDiPagamento() {
    return modalitaDiPagamento;
  }

  public void setModalitaDiPagamento(ModalitaPagamentoEnum modalitaDiPagamento) {
    this.modalitaDiPagamento = modalitaDiPagamento;
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

  public List<NucleoFamiliareComponenteNucleoInner> getListaComponentiNucleoIsee() {
    return listaComponentiNucleoIsee;
  }

  public void setListaComponentiNucleoIsee(
      List<NucleoFamiliareComponenteNucleoInner> listaComponentiNucleoIsee) {
    this.listaComponentiNucleoIsee = listaComponentiNucleoIsee;
  }

  public List<Residente> getListaComponentiNucleoAnagrafe() {
    return listaComponentiNucleoAnagrafe;
  }

  public void setListaComponentiNucleoAnagrafe(List<Residente> listaComponentiNucleoAnagrafe) {
    this.listaComponentiNucleoAnagrafe = listaComponentiNucleoAnagrafe;
  }

  public boolean isNucleiAnagrafeIseeUguali() {
    return isNucleiAnagrafeIseeUguali;
  }

  public void setNucleiAnagrafeIseeUguali(boolean isNucleiAnagrafeIseeUguali) {
    this.isNucleiAnagrafeIseeUguali = isNucleiAnagrafeIseeUguali;
  }

  public List<ResidenteTari> getListaTuttiNucleiTari() {
    return listaTuttiNucleiTari;
  }

  public void setListaTuttiNucleiTari(List<ResidenteTari> listaTuttiNucleiTari) {
    this.listaTuttiNucleiTari = listaTuttiNucleiTari;
  }

  public String getAutodichiarazioneCategoriaImmobile() {
    return autodichiarazioneCategoriaImmobile;
  }

  public void setAutodichiarazioneCategoriaImmobile(String autodichiarazioneCategoriaImmobile) {
    this.autodichiarazioneCategoriaImmobile = autodichiarazioneCategoriaImmobile;
  }

  public Integer getFlagCategoriaImmobileDiversaDaA1A8A9() {
    return flagCategoriaImmobileDiversaDaA1A8A9;
  }

  public void setFlagCategoriaImmobileDiversaDaA1A8A9(
      Integer flagCategoriaImmobileDiversaDaA1A8A9) {
    this.flagCategoriaImmobileDiversaDaA1A8A9 = flagCategoriaImmobileDiversaDaA1A8A9;
  }

  public String getAutodichiarazioneMqImmobile() {
    return autodichiarazioneMqImmobile;
  }

  public void setAutodichiarazioneMqImmobile(String autodichiarazioneMqImmobile) {
    this.autodichiarazioneMqImmobile = autodichiarazioneMqImmobile;
  }

  public Integer getFlagPensionatoSuperiore70anni() {
    return flagPensionatoSuperiore70anni;
  }

  public void setFlagPensionatoSuperiore70anni(Integer flagPensionatoSuperiore70anni) {
    this.flagPensionatoSuperiore70anni = flagPensionatoSuperiore70anni;
  }

  public String getAutodichiarazioneAltreAgevolazioni() {
    return autodichiarazioneAltreAgevolazioni;
  }

  public void setAutodichiarazioneAltreAgevolazioni(String autodichiarazioneAltreAgevolazioni) {
    this.autodichiarazioneAltreAgevolazioni = autodichiarazioneAltreAgevolazioni;
  }

  public Integer getFlagBeneficarioNonFruitoreAltreAgevolazioni() {
    return flagBeneficarioNonFruitoreAltreAgevolazioni;
  }

  public void setFlagBeneficarioNonFruitoreAltreAgevolazioni(
      Integer flagBeneficarioNonFruitoreAltreAgevolazioni) {
    this.flagBeneficarioNonFruitoreAltreAgevolazioni = flagBeneficarioNonFruitoreAltreAgevolazioni;
  }

  public Integer getNumeroComponenti() {
    return numeroComponenti;
  }

  public void setNumeroComponenti(Integer numeroComponenti) {
    this.numeroComponenti = numeroComponenti;
  }

  public String getNominativoIseeACarico() {
    return nominativoIseeACarico;
  }

  public void setNominativoIseeACarico(String nominativoIseeACarico) {
    this.nominativoIseeACarico = nominativoIseeACarico;
  }

  public List<DatiPersoneACaricoContributoTariNetribe> getListaComponentiNucleoIseeACarico() {
    return listaComponentiNucleoIseeACarico;
  }

  public void setListaComponentiNucleoIseeACarico(
      List<DatiPersoneACaricoContributoTariNetribe> listaComponentiNucleoIseeACarico) {
    this.listaComponentiNucleoIseeACarico = listaComponentiNucleoIseeACarico;
  }

  public FileAllegato getFileAllegato() {
    return fileAllegato;
  }

  public void setFileAllegato(FileAllegato fileAllegato) {
    this.fileAllegato = fileAllegato;
  }

  public String getAllegatoContributoUpload() {
    return allegatoContributoUpload;
  }

  public void setAllegatoContributoUpload(String allegatoContributoUpload) {
    this.allegatoContributoUpload = allegatoContributoUpload;
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

  public String getCittadinanza() {
    return cittadinanza;
  }

  public void setCittadinanza(String cittadinanza) {
    this.cittadinanza = cittadinanza;
  }

  public String getCodiceCittadinanza() {
    return codiceCittadinanza;
  }

  public void setCodiceCittadinanza(String codiceCittadinanza) {
    this.codiceCittadinanza = codiceCittadinanza;
  }

  public Integer getFlagSuperficieImmobileEntroMq() {
    return flagSuperficieImmobileEntroMq;
  }

  public void setFlagSuperficieImmobileEntroMq(Integer flagSuperficieImmobileEntroMq) {
    this.flagSuperficieImmobileEntroMq = flagSuperficieImmobileEntroMq;
  }

  public Integer getFlagPensionatoSuperiore70anni1FamiliareACarico() {
    return flagPensionatoSuperiore70anni1FamiliareACarico;
  }

  public void setFlagPensionatoSuperiore70anni1FamiliareACarico(
      Integer flagPensionatoSuperiore70anni1FamiliareACarico) {
    this.flagPensionatoSuperiore70anni1FamiliareACarico =
        flagPensionatoSuperiore70anni1FamiliareACarico;
  }

  public Integer getFlagBeneficarioConFigliACaricoMax4() {
    return flagBeneficarioConFigliACaricoMax4;
  }

  public void setFlagBeneficarioConFigliACaricoMax4(Integer flagBeneficarioConFigliACaricoMax4) {
    this.flagBeneficarioConFigliACaricoMax4 = flagBeneficarioConFigliACaricoMax4;
  }

  public List<DatiIseeExtend> getDatiIseeCompleti() {
    return datiIseeCompleti;
  }

  public void setDatiIseeCompleti(List<DatiIseeExtend> datiIseeCompleti) {
    this.datiIseeCompleti = datiIseeCompleti;
  }

  public DatiIseeRichiederenteECoresidenti getDatiIseeTuttiNuclei() {
    return datiIseeTuttiNuclei;
  }

  public void setDatiIseeTuttiNuclei(DatiIseeRichiederenteECoresidenti datiIseeTuttiNuclei) {
    this.datiIseeTuttiNuclei = datiIseeTuttiNuclei;
  }

  public String getNominativo() {
    return nominativo;
  }

  public void setNominativo(String nominativo) {
    this.nominativo = nominativo;
  }

  public List<ResidenteTari> getListaTuttiIS() {
    return listaTuttiIS;
  }

  public void setListaTuttiIS(List<ResidenteTari> listaTuttiIS) {
    this.listaTuttiIS = listaTuttiIS;
  }

  public boolean isOver70DaSolo() {
    return isOver70DaSolo;
  }

  public void setOver70DaSolo(boolean isOver70DaSolo) {
    this.isOver70DaSolo = isOver70DaSolo;
  }

  public boolean isOver70ConAltriIs() {
    return isOver70ConAltriIs;
  }

  public void setOver70ConAltriIs(boolean isOver70ConAltriIs) {
    this.isOver70ConAltriIs = isOver70ConAltriIs;
  }

  public boolean isOver70ConUnCoresidente() {
    return isOver70ConUnCoresidente;
  }

  public void setOver70ConUnCoresidente(boolean isOver70ConUnCoresidente) {
    this.isOver70ConUnCoresidente = isOver70ConUnCoresidente;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public boolean isRequisitiMinimiEtaENucleo() {
    return isRequisitiMinimiEtaENucleo;
  }

  public boolean isNucleoFamiliareConDa1A4FigliUnder26() {
    return isNucleoFamiliareConDa1A4FigliUnder26;
  }

  public void setNucleoFamiliareConDa1A4FigliUnder26(
      boolean isNucleoFamiliareConDa1A4FigliUnder26) {
    this.isNucleoFamiliareConDa1A4FigliUnder26 = isNucleoFamiliareConDa1A4FigliUnder26;
  }

  public void setRequisitiMinimiEtaENucleo(boolean isRequisitiMinimiEtaENucleo) {
    this.isRequisitiMinimiEtaENucleo = isRequisitiMinimiEtaENucleo;
  }

  public boolean isUnder70() {
    return isUnder70;
  }

  public void setUnder70(boolean isUnder70) {
    this.isUnder70 = isUnder70;
  }

  public List<DatiResidenti> getListaDatiResidenti() {
    return listaDatiResidenti;
  }

  public List<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> getDatiPersoneACarico() {
    return datiPersoneACarico;
  }

  public void setDatiPersoneACarico(
      List<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> datiPersoneACarico) {
    this.datiPersoneACarico = datiPersoneACarico;
  }

  public void setListaDatiResidenti(List<DatiResidenti> listaDatiResidenti) {
    this.listaDatiResidenti = listaDatiResidenti;
  }

  public Double getMqMassimi() {
    return mqMassimi;
  }

  public void setMqMassimi(Double mqMassimi) {
    this.mqMassimi = mqMassimi;
  }

  public Double getIseeMassimo() {
    return iseeMassimo;
  }

  public void setIseeMassimo(Double iseeMassimo) {
    this.iseeMassimo = iseeMassimo;
  }

  public LocalDate getPresentabileDa() {
    return presentabileDa;
  }

  public void setPresentabileDa(LocalDate presentabileDa) {
    this.presentabileDa = presentabileDa;
  }

  public LocalDate getPresentabileFinoAl() {
    return presentabileFinoAl;
  }

  public void setPresentabileFinoAl(LocalDate presentabileFinoAl) {
    this.presentabileFinoAl = presentabileFinoAl;
  }

  public String getAutodichiarazioneInRegolaTari() {
    return autodichiarazioneInRegolaTari;
  }

  public void setAutodichiarazioneInRegolaTari(String autodichiarazioneInRegolaTari) {
    this.autodichiarazioneInRegolaTari = autodichiarazioneInRegolaTari;
  }

  public Integer getFlagInRegolaTari() {
    return flagInRegolaTari;
  }

  public void setFlagInRegolaTari(Integer flagInRegolaTari) {
    this.flagInRegolaTari = flagInRegolaTari;
  }

  @Override
  public String toString() {
    return "DatiDomandaContributoTari [presentabileDa="
        + presentabileDa
        + ", presentabileFinoAl="
        + presentabileFinoAl
        + ", mqMassimi="
        + mqMassimi
        + ", iseeMassimo="
        + iseeMassimo
        + ", iseePresentato="
        + iseePresentato
        + ", importoIsee="
        + importoIsee
        + ", importoIseeNelRange="
        + importoIseeNelRange
        + ", listaComponentiNucleoIsee="
        + listaComponentiNucleoIsee
        + ", listaComponentiNucleoAnagrafe="
        + listaComponentiNucleoAnagrafe
        + ", isNucleiAnagrafeIseeUguali="
        + isNucleiAnagrafeIseeUguali
        + ", domandaPresentabile="
        + domandaPresentabile
        + ", listaTuttiNucleiTari="
        + listaTuttiNucleiTari
        + ", listaTuttiIS="
        + listaTuttiIS
        + ", listaDatiResidenti="
        + listaDatiResidenti
        + ", numeroComponenti="
        + numeroComponenti
        + ", listaComponentiNucleoIseeACarico="
        + listaComponentiNucleoIseeACarico
        + ", datiPersoneACarico="
        + datiPersoneACarico
        + ", datiIseeCompleti="
        + datiIseeCompleti
        + ", datiIseeTuttiNuclei="
        + datiIseeTuttiNuclei
        + ", isOver70DaSolo="
        + isOver70DaSolo
        + ", isOver70ConAltriIs="
        + isOver70ConAltriIs
        + ", isOver70ConUnCoresidente="
        + isOver70ConUnCoresidente
        + ", isNucleoFamiliareConDa1A4FigliUnder26="
        + isNucleoFamiliareConDa1A4FigliUnder26
        + ", isRequisitiMinimiEtaENucleo="
        + isRequisitiMinimiEtaENucleo
        + ", isUnder70="
        + isUnder70
        + ", nome="
        + nome
        + ", cognome="
        + cognome
        + ", nominativo="
        + nominativo
        + ", codiceFiscale="
        + codiceFiscale
        + ", dataNascita="
        + dataNascita
        + ", email="
        + email
        + ", cellulare="
        + cellulare
        + ", cittadinanza="
        + cittadinanza
        + ", codiceCittadinanza="
        + codiceCittadinanza
        + ", modalitaDiPagamento="
        + modalitaDiPagamento
        + ", iban="
        + iban
        + ", swift="
        + swift
        + ", nomeDelegato="
        + nomeDelegato
        + ", cognomeDelegato="
        + cognomeDelegato
        + ", codiceFiscaleDelegato="
        + codiceFiscaleDelegato
        + ", autodichiarazioneCategoriaImmobile="
        + autodichiarazioneCategoriaImmobile
        + ", flagCategoriaImmobileDiversaDaA1A8A9="
        + flagCategoriaImmobileDiversaDaA1A8A9
        + ", autodichiarazioneMqImmobile="
        + autodichiarazioneMqImmobile
        + ", flagSuperficieImmobileEntroMq="
        + flagSuperficieImmobileEntroMq
        + ", flagPensionatoSuperiore70anni="
        + flagPensionatoSuperiore70anni
        + ", autodichiarazioneAltreAgevolazioni="
        + autodichiarazioneAltreAgevolazioni
        + ", flagBeneficarioNonFruitoreAltreAgevolazioni="
        + flagBeneficarioNonFruitoreAltreAgevolazioni
        + ", flagPensionatoSuperiore70anni1FamiliareACarico="
        + flagPensionatoSuperiore70anni1FamiliareACarico
        + ", flagBeneficarioConFigliACaricoMax4="
        + flagBeneficarioConFigliACaricoMax4
        + ", nominativoIseeACarico="
        + nominativoIseeACarico
        + ", autodichiarazioneInRegolaTari="
        + autodichiarazioneInRegolaTari
        + ", flagInRegolaTari="
        + flagInRegolaTari
        + "]";
  }
}
