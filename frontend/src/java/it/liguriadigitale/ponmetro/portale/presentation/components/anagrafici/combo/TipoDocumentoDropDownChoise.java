package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TipoDocumentoDropDownChoise<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = 3235299780015791781L;

  private IChoiceRenderer render;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public TipoDocumentoDropDownChoise(String id, IModel modello) {
    super(id, new ComboLoadableDetachableModel(getListaTipiDocumenti()));

    setModel(modello);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona tipo documento"));
  }

  private static List<TipiDocumentiDaCaricare> getListaTipiDocumenti() {
    List<TipiDocumentiDaCaricare> listaTipiDocumenti =
        Arrays.asList(TipiDocumentiDaCaricare.values());
    return listaTipiDocumenti;
  }
}
