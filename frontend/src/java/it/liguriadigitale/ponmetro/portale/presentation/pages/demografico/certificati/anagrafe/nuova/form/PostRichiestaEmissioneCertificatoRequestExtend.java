package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe.nuova.form;

import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import java.time.LocalDate;

public class PostRichiestaEmissioneCertificatoRequestExtend
    extends PostRichiestaEmissioneCertificatoRequest {

  private LocalDate dataA;
  private LocalDate dataFine;
  private LocalDate dataInizio;

  private String telefonoContatto;

  public LocalDate getDataA() {
    return dataA;
  }

  public void setDataA(LocalDate dataA) {
    this.dataA = dataA;
  }

  public String getTelefonoContatto() {
    return telefonoContatto;
  }

  public void setTelefonoContatto(String telefonoContatto) {
    this.telefonoContatto = telefonoContatto;
  }

  public LocalDate getDataFine() {
    return dataFine;
  }

  public void setDataFine(LocalDate dataFine) {
    this.dataFine = dataFine;
  }

  public LocalDate getDataInizio() {
    return dataInizio;
  }

  public void setDataInizio(LocalDate dataInizio) {
    this.dataInizio = dataInizio;
  }

  @Override
  public String toString() {
    return "PostRichiestaEmissioneCertificatoRequestExtend [dataA="
        + dataA
        + ", dataFine="
        + dataFine
        + ", dataInizio="
        + dataInizio
        + ", telefonoContatto="
        + telefonoContatto
        + ", getDataRichiesta()="
        + getDataRichiesta()
        + ", getNumeroCopie()="
        + getNumeroCopie()
        + ", getCodiceCertificato()="
        + getCodiceCertificato()
        + ", getTipoCertificato()="
        + getTipoCertificato()
        + ", getCodiceUtilizzo()="
        + getCodiceUtilizzo()
        + ", getEstremiRichiedente()="
        + getEstremiRichiedente()
        + ", getEstremiIntestatario()="
        + getEstremiIntestatario()
        + ", getImportoBollo()="
        + getImportoBollo()
        + ", getImportoDirittiSegreteria()="
        + getImportoDirittiSegreteria()
        + ", getTipoConsegnaScelta()="
        + getTipoConsegnaScelta()
        + ", getTelefono()="
        + getTelefono()
        + ", getEmail()="
        + getEmail()
        + ", getCodiceComuneSpedizione()="
        + getCodiceComuneSpedizione()
        + ", getCapComuneSpedizione()="
        + getCapComuneSpedizione()
        + ", getIndirizzoSpedizione()="
        + getIndirizzoSpedizione()
        + ", getNominativoSpedizione()="
        + getNominativoSpedizione()
        + ", getNumeroMarcaBollo()="
        + getNumeroMarcaBollo()
        + ", getDataMarcaBollo()="
        + getDataMarcaBollo()
        + ", getDataCertificatoStorico()="
        + getDataCertificatoStorico()
        + ", toString()="
        + super.toString()
        + ", getClass()="
        + getClass()
        + ", hashCode()="
        + hashCode()
        + "]";
  }
}
