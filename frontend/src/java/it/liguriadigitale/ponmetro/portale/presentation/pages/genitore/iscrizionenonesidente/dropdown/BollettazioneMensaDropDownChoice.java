package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.dropdown;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.enums.ModalitaBollettazioneEnum;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

public class BollettazioneMensaDropDownChoice extends FdCDropDownChoice<ModalitaBollettazioneEnum> {

  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("rawtypes")
  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BollettazioneMensaDropDownChoice(String id, PropertyModel modalita) {
    super(id);

    setChoices(getListaModalita());
    setModel(modalita);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.render = new BollettazioneMensaRender();
    setChoiceRenderer(render);
  }

  private static List<ModalitaBollettazioneEnum> getListaModalita() {
    List<ModalitaBollettazioneEnum> lista = Arrays.asList(ModalitaBollettazioneEnum.values());
    return lista;
  }
}
