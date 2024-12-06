package it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;

public class DatiFiglioExtend extends DatiDipartimentaliBambino {

  private static final long serialVersionUID = -2267602181916731186L;

  private Cittadinanza autocompleteCittadinanze;

  private DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio;

  private String datiResidenzaDomicilioCoincidono;

  public Cittadinanza getAutocompleteCittadinanze() {
    return autocompleteCittadinanze;
  }

  public void setAutocompleteCittadinanze(Cittadinanza autocompleteCittadinanze) {
    this.autocompleteCittadinanze = autocompleteCittadinanze;
  }

  public DatiNascitaResidenzaDomicilio getDatiNascitaResidenzaDomicilio() {
    return datiNascitaResidenzaDomicilio;
  }

  public void setDatiNascitaResidenzaDomicilio(
      DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio) {
    this.datiNascitaResidenzaDomicilio = datiNascitaResidenzaDomicilio;
  }

  public String getDatiResidenzaDomicilioCoincidono() {
    return datiResidenzaDomicilioCoincidono;
  }

  public void setDatiResidenzaDomicilioCoincidono(String datiResidenzaDomicilioCoincidono) {
    this.datiResidenzaDomicilioCoincidono = datiResidenzaDomicilioCoincidono;
  }

  @Override
  public String toString() {
    return "DatiFiglioExtend [autocompleteCittadinanze="
        + autocompleteCittadinanze
        + ", datiNascitaResidenzaDomicilio="
        + datiNascitaResidenzaDomicilio
        + ", datiResidenzaDomicilioCoincidono="
        + datiResidenzaDomicilioCoincidono
        + "]";
  }
}
