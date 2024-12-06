package it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;

public class DatiGenitoreExtend extends DatiAnagrafeGenitore {

  private static final long serialVersionUID = 4981500112812448717L;

  private Cittadinanza autocompleteStatoNascita;

  private DatiNascitaResidenzaDomicilio datiNascitaResidenzaDomicilio;

  private String datiResidenzaDomicilioCoincidono;

  private String emailBollettazione;

  private ModalitaBollettazioneEnum modalitaBollettazione;

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

  public Cittadinanza getAutocompleteStatoNascita() {
    return autocompleteStatoNascita;
  }

  public void setAutocompleteStatoNascita(Cittadinanza autocompleteStatoNascita) {
    this.autocompleteStatoNascita = autocompleteStatoNascita;
  }

  public String getEmailBollettazione() {
    return emailBollettazione;
  }

  public void setEmailBollettazione(String emailBollettazione) {
    this.emailBollettazione = emailBollettazione;
  }

  public ModalitaBollettazioneEnum getModalitaBollettazione() {
    return modalitaBollettazione;
  }

  public void setModalitaBollettazione(ModalitaBollettazioneEnum modalitaBollettazione) {
    this.modalitaBollettazione = modalitaBollettazione;
  }

  @Override
  public String toString() {
    return "DatiGenitoreExtend [autocompleteStatoNascita="
        + autocompleteStatoNascita
        + ", datiNascitaResidenzaDomicilio="
        + datiNascitaResidenzaDomicilio
        + ", datiResidenzaDomicilioCoincidono="
        + datiResidenzaDomicilioCoincidono
        + ", emailBollettazione="
        + emailBollettazione
        + ", modalitaBollettazione="
        + modalitaBollettazione
        + "]";
  }
}
