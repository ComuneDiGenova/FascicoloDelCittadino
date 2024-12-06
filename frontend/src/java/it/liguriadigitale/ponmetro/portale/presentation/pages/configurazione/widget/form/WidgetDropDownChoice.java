package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.widget.form;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

public class WidgetDropDownChoice extends DropDownChoice<DatiVisualizzazioneSezioneWidget> {

  private static final long serialVersionUID = -3782915406811762906L;
  private Log log = LogFactory.getLog(getClass());

  private static final String STYLE_COLOR = " style='background-color: #cccccc' ";

  public WidgetDropDownChoice(String id, IModel<List<DatiVisualizzazioneSezioneWidget>> choices) {
    super(id, choices);
  }

  @Override
  protected void setOptionAttributes(
      AppendingStringBuffer buffer,
      DatiVisualizzazioneSezioneWidget choice,
      int index,
      String selected) {

    if (!choice.getSezione().getFlagAbilitazione()
        || !choice.getComparto().getFlagAbilitazione()
        || !choice.getFunzione().getFlagAbilitazione()
        || !choice.getWidget().getFlagAbilitazione()) {
      buffer.append(STYLE_COLOR);
      log.debug("buffer.nome =" + choice.getWidget().getDescrizioneWidg());
      log.debug(
          "buffer.append ="
              + choice.getSezione().getFlagAbilitazione()
              + choice.getComparto().getFlagAbilitazione()
              + choice.getWidget().getFlagAbilitazione());
    }
    super.setOptionAttributes(buffer, choice, index, selected);
  }
}
