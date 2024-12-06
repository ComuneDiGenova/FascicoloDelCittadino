package it.liguriadigitale.ponmetro.portale.pojo.pagamentiristorazione;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiPagamentiBollettiniRiepilogativi;
import it.liguriadigitale.ponmetro.serviziristorazione.model.ItemBeneficiarioBollettino;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatiPagamentiBollettiniRiepilogativiEstesi implements Serializable {

  private static final long serialVersionUID = 2439050314082899123L;

  private Boolean isPagato;
  private byte[] pdfBollettino;
  private byte[] pdfRicevuta;

  DatiPagamentiBollettiniRiepilogativi datiRiepilogativi;

  private String iuv;
  private String codiceAvviso;
  private String attualizzato;
  private Double importoMIP;

  public DatiPagamentiBollettiniRiepilogativiEstesi(
      DatiPagamentiBollettiniRiepilogativi datiRiepilogativi) {
    super();

    this.datiRiepilogativi = datiRiepilogativi;
  }

  public DatiPagamentiBollettiniRiepilogativiEstesi(
      byte[] pdfBollettino, DatiPagamentiBollettiniRiepilogativi datiRiepilogativi) {
    super();

    this.pdfBollettino = pdfBollettino;
    this.datiRiepilogativi = datiRiepilogativi;
  }

  public Boolean getIsPagato() {
    return isPagato;
  }

  public void setIsPagato(Boolean isPagato) {
    this.isPagato = isPagato;
  }

  public byte[] getPdfBollettino() {
    return pdfBollettino;
  }

  public void setPdfBollettino(byte[] pdfBollettino) {
    this.pdfBollettino = pdfBollettino;
  }

  public byte[] getPdfRicevuta() {
    return pdfRicevuta;
  }

  public void setPdfRicevuta(byte[] pdfRicevuta) {
    this.pdfRicevuta = pdfRicevuta;
  }

  public String getIdBollettino() {
    return datiRiepilogativi.getIdBollettino();
  }

  public String getIdentificativoDebito() {
    return datiRiepilogativi.getIdentificativoDebito();
  }

  public String getCfImpegnato() {
    return datiRiepilogativi.getCfImpegnato();
  }

  public String getNominativoImpegnato() {
    return datiRiepilogativi.getNominativoImpegnato();
  }

  public String getIdImpegnato() {
    return datiRiepilogativi.getIdImpegnato();
  }

  public BigDecimal getAnnoRiferimento() {
    return datiRiepilogativi.getAnnoRiferimento();
  }

  public BigDecimal getBimestreEmissioneRiferimento() {
    return datiRiepilogativi.getBimestreEmissioneRiferimento();
  }

  public String getAnnoScolasticoTestuale() {
    return datiRiepilogativi.getAnnoScolasticoTestuale();
  }

  public String getPeriodoPagamentoTestuale() {
    return datiRiepilogativi.getPeriodoPagamentoTestuale();
  }

  public LocalDate getDataProtocolloEmissione() {
    return datiRiepilogativi.getDataProtocolloEmissione();
  }

  public LocalDate getDataScadenza() {
    return datiRiepilogativi.getDataScadenza();
  }

  public BigDecimal getTotalePagareEuro() {
    return datiRiepilogativi.getTotalePagareEuro();
  }

  public List<ItemBeneficiarioBollettino> getBeneficiariBollettino() {
    return datiRiepilogativi.getBeneficiariBollettino();
  }

  public final DatiPagamentiBollettiniRiepilogativi getDatiRiepilogativi() {
    return datiRiepilogativi;
  }

  public final void setDatiRiepilogativi(DatiPagamentiBollettiniRiepilogativi datiRiepilogativi) {
    this.datiRiepilogativi = datiRiepilogativi;
  }

  public final String getIuv() {
    return iuv;
  }

  public final void setIuv(String iuv) {
    this.iuv = iuv;
  }

  public final String getCodiceAvviso() {
    return codiceAvviso;
  }

  public final void setCodiceAvviso(String codiceAvviso) {
    this.codiceAvviso = codiceAvviso;
  }

  public final String getAttualizzato() {
    return attualizzato;
  }

  public final void setAttualizzato(String attualizzato) {
    this.attualizzato = attualizzato;
  }

  public final Double getImportoMIP() {
    return importoMIP;
  }

  public final void setImportoMIP(Double importoMIP) {
    this.importoMIP = importoMIP;
  }

  @Override
  public String toString() {
    return "DatiPagamentiBollettiniRiepilogativiEstesi [isPagato="
        + isPagato
        + ", pdfBollettino="
        + Arrays.toString(pdfBollettino)
        + ", pdfRicevuta="
        + Arrays.toString(pdfRicevuta)
        + ", datiRiepilogativi="
        + datiRiepilogativi
        + ", iuv="
        + iuv
        + ", codiceAvviso="
        + codiceAvviso
        + ", attualizzato="
        + attualizzato
        + ", importoMIP="
        + importoMIP
        + "]";
  }
}
