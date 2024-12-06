package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.riuso.mip.PaymentRequest.ServiceData;

public class ServiceDataBuilder {

  protected String idServizio;
  protected String urlcss;
  protected String numeroDocumento;
  protected String annoDocumento;
  protected String descrizioneDocumento;
  protected String datiSpecifici;

  public static ServiceDataBuilder getInstance() {
    return new ServiceDataBuilder();
  }

  public ServiceDataBuilder setIdServizio(String idServizio) {
    this.idServizio = idServizio;
    return this;
  }

  public ServiceDataBuilder setUrlcss(String urlcss) {
    this.urlcss = urlcss;
    return this;
  }

  public ServiceDataBuilder setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
    return this;
  }

  public ServiceDataBuilder setAnnoDocumento(String annoDocumento) {
    this.annoDocumento = annoDocumento;
    return this;
  }

  public ServiceDataBuilder setDescrizioneDocumento(String descrizioneDocumento) {
    this.descrizioneDocumento = descrizioneDocumento;
    return this;
  }

  public ServiceDataBuilder setDatiSpecifici(String datiSpecifici) {
    this.datiSpecifici = datiSpecifici;
    return this;
  }

  public ServiceData build() {

    ServiceData data = new ServiceData();
    data.setAnnoDocumento(annoDocumento);
    data.setDatiSpecifici(datiSpecifici);
    data.setDescrizioneDocumento(descrizioneDocumento);
    data.setIDServizio(idServizio);
    data.setNumeroDocumento(numeroDocumento);
    data.setURLCSS(urlcss);
    return data;
  }
}
