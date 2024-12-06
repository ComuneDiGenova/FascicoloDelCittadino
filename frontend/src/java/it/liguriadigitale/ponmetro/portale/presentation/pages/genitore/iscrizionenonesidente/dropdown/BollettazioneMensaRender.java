package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.dropdown;

import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class BollettazioneMensaRender implements IChoiceRenderer<ModalitaBollettazioneEnum> {

  private static final long serialVersionUID = 493612788266812087L;

  @Override
  public Object getDisplayValue(final ModalitaBollettazioneEnum obj) {
    ModalitaBollettazioneEnum c = obj;
    return c.name();
  }

  @Override
  public String getIdValue(final ModalitaBollettazioneEnum obj, final int index) {
    ModalitaBollettazioneEnum c = obj;
    if (c != null) return c.name();
    else return "";
  }

  @Override
  public ModalitaBollettazioneEnum getObject(
      String id, IModel<? extends List<? extends ModalitaBollettazioneEnum>> lista) {
    for (ModalitaBollettazioneEnum dati : lista.getObject()) {
      if (dati != null && id.equalsIgnoreCase(dati.name())) {
        return dati;
      }
    }
    return null;
  }
}
