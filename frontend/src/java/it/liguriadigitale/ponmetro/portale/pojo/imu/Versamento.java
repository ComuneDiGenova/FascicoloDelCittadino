package it.liguriadigitale.ponmetro.portale.pojo.imu;

import it.liguriadigitale.ponmetro.tributi.model.DatiAnnualitaRimborso;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Versamento implements Serializable {
  /** */
  private static final long serialVersionUID = 1L;

  private UUID id;
  private int anno;
  private BigDecimal quotaComune;
  private BigDecimal quotaStato;
  private BigDecimal totaleImporto;
  private StatoAnnualitaEnum stato;
  private MotivazioneVersamentoEnum motivazioneVersamento;
  private TipoQuotaEnum tipoQuota;
  private BigDecimal importoRichiedente;
  private BigDecimal importoApprovato;
  private String altro;
  private String motivoSospensione;

  public Versamento() {
    // TODO Auto-generated constructor stub
  }

  public Versamento(DatiAnnualitaRimborso dataAnnualitaRimborso) {
    // TODO Auto-generated constructor stub

    this.anno = dataAnnualitaRimborso.getAnn().intValue();
    this.importoRichiedente = dataAnnualitaRimborso.getImpRich();
    this.importoApprovato = dataAnnualitaRimborso.getImpApp();
    this.totaleImporto = dataAnnualitaRimborso.getImpTot();
    this.id = UUID.randomUUID();
    this.stato = StatoAnnualitaEnum.valueOf(dataAnnualitaRimborso.getStat());
    this.motivazioneVersamento =
        MotivazioneVersamentoEnum.getById(dataAnnualitaRimborso.getMotiv());
    this.motivoSospensione = dataAnnualitaRimborso.getMotivSosp();
    this.altro = dataAnnualitaRimborso.getAltroMotiv();
    this.tipoQuota = decodeTipoQuota(dataAnnualitaRimborso.getQuot());
  }

  public Versamento(
      int anno,
      String tipoQuota,
      BigDecimal impTot,
      BigDecimal impApp,
      BigDecimal impRich,
      StatoAnnualitaEnum statoAnnualita,
      MotivazioneVersamentoEnum motivazione,
      String motivoSospensione,
      String altro) {
    // TODO Auto-generated constructor stub
    this.anno = anno;
    this.importoRichiedente = impRich;
    this.importoApprovato = impApp;
    this.totaleImporto = impTot;
    this.id = UUID.randomUUID();
    this.stato = statoAnnualita;
    this.motivazioneVersamento = motivazione;
    this.motivoSospensione = motivoSospensione;
    this.tipoQuota = decodeTipoQuota(tipoQuota);
    this.altro = altro;
  }

  private TipoQuotaEnum decodeTipoQuota(String tipoQuota) {
    // TODO Auto-generated method stub
    TipoQuotaEnum t = TipoQuotaEnum.Q1;

    switch (tipoQuota) {
      case "2":
        t = TipoQuotaEnum.Q2;
        break;
      case "3":
        t = TipoQuotaEnum.Q3;
        break;
      case "0":
        t = null;
        break;
      default:
        t = TipoQuotaEnum.Q1;
        break;
    }

    return t;
  }

  public String getMotivoSospensione() {
    return motivoSospensione;
  }

  public void setMotivoSospensione(String motivoSospensione) {
    this.motivoSospensione = motivoSospensione;
  }

  public TipoQuotaEnum getTipoQuota() {
    return tipoQuota;
  }

  public void setTipoQuota(TipoQuotaEnum tipoQuota) {
    this.tipoQuota = tipoQuota;
  }

  public BigDecimal getImportoRichiedente() {
    return importoRichiedente;
  }

  public void setImportoRichiedente(BigDecimal importoRichiedente) {
    this.importoRichiedente = importoRichiedente;
  }

  public BigDecimal getImportoApprovato() {
    return importoApprovato;
  }

  public void setImportoApprovato(BigDecimal importoApprovato) {
    this.importoApprovato = importoApprovato;
  }

  public StatoAnnualitaEnum getStato() {
    return stato;
  }

  public void setStato(StatoAnnualitaEnum stato) {
    this.stato = stato;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getAnno() {
    return anno;
  }

  public BigDecimal getQuotaComune() {
    return quotaComune;
  }

  public BigDecimal getQuotaStato() {
    return quotaStato;
  }

  public BigDecimal getTotaleImporto() {
    return totaleImporto;
  }

  public MotivazioneVersamentoEnum getMotivazioneVersamento() {
    return motivazioneVersamento;
  }

  public void setMotivazioneVersamento(MotivazioneVersamentoEnum motivazioneVersamento) {
    this.motivazioneVersamento = motivazioneVersamento;
  }

  public void setAnno(int anno) {
    this.anno = anno;
  }

  public void setQuotaComune(BigDecimal quotaComune) {
    this.quotaComune = quotaComune;
  }

  public void setQuotaStato(BigDecimal quotaStato) {
    this.quotaStato = quotaStato;
  }

  public void setTotaleImporto(BigDecimal totaleImporto) {
    this.totaleImporto = totaleImporto;
  }

  public String getAltro() {
    return altro;
  }

  public void setAltro(String altro) {
    this.altro = altro;
  }
}
