package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.util.NazionalitaProprietarioRender;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class NazionalitaProprietarioDropDownChoice<T> extends FdCDropDownChoice<T> {

  private static final long serialVersionUID = -8956055066684237498L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public NazionalitaProprietarioDropDownChoice(String id, IModel modello) {
    super(id);

    this.render = new NazionalitaProprietarioRender();

    setModel(modello);
    setChoiceRenderer(render);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona nazionalità proprietario"));

    setChoices(new ComboLoadableDetachableModel(getListaNazionalita()));
  }

  private static List<NazionalitaProprietarioEnum> getListaNazionalita() {
    return Arrays.asList(NazionalitaProprietarioEnum.values());
  }
}
