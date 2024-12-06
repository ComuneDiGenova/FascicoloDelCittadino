package it.liguriadigitale.ponmetro.portale.pojo.portale;

import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;

public class UtenteServiziRistorazioneHandler implements Serializable {

  private static final long serialVersionUID = -1050657223409985843L;

  private UtenteServiziRistorazione iscritto;

  public UtenteServiziRistorazioneHandler(UtenteServiziRistorazione iscritto) {
    super();
    this.iscritto = iscritto;
  }

  public boolean isVisibileLinkMensa() {
    return (iscritto
            .getStatoIscrizioneServiziRistorazione()
            .equalsIgnoreCase(
                UtenteServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum.ACCETTATA.value())
        && iscritto.getScuolaConMensa());
  }
}
