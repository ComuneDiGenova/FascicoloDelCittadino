package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class UtenteServiziRistorazioneChoiceRenderer
    extends ChoiceRenderer<UtenteServiziRistorazione> {

  private static final long serialVersionUID = -1338892533380317579L;

  @Override
  public Object getDisplayValue(final UtenteServiziRistorazione obj) {
    UtenteServiziRistorazione c = obj;
    return c.getNome();
  }

  @Override
  public String getIdValue(final UtenteServiziRistorazione obj, final int index) {
    UtenteServiziRistorazione c = obj;
    if ((c != null) && (c.getCodiceFiscale() != null)) return c.getCodiceFiscale();
    else return "";
  }
}
