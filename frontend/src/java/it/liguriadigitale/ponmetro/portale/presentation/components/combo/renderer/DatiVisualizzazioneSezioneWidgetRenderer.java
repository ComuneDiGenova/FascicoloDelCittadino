package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class DatiVisualizzazioneSezioneWidgetRenderer
    implements IChoiceRenderer<DatiVisualizzazioneSezioneWidget> {

  private static final long serialVersionUID = -4934768945592923819L;

  @Override
  public Object getDisplayValue(final DatiVisualizzazioneSezioneWidget obj) {
    DatiVisualizzazioneSezioneWidget c = obj;
    if (c != null && c.getWidget() != null) {
      if (!obj.getSezione().getFlagAbilitazione()
          || !obj.getComparto().getFlagAbilitazione()
          || !obj.getFunzione().getFlagAbilitazione()
          || !obj.getWidget().getFlagAbilitazione()) {
        return c.getWidget().getDescrizioneWidg() + " DISABILITATO";
      } else return c.getWidget().getDescrizioneWidg();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(final DatiVisualizzazioneSezioneWidget obj, final int index) {
    DatiVisualizzazioneSezioneWidget c = obj;
    if (c != null && c.getWidget() != null) return String.valueOf(c.getWidget().getUriWidg());
    else return "";
  }

  @Override
  public DatiVisualizzazioneSezioneWidget getObject(
      String id, IModel<? extends List<? extends DatiVisualizzazioneSezioneWidget>> lista) {

    for (DatiVisualizzazioneSezioneWidget dati : lista.getObject()) {
      if (dati.getWidget() != null)
        if (dati.getWidget().getUriWidg().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
