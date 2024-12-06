package it.liguriadigitale.ponmetro.portale.presentation.components.tarieng;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TipoEccedenzaRimborsoTariEngDropDownChoice
    extends FdCDropDownChoice<TipoEccedenzaRimborsoTariEng> {

  private static final long serialVersionUID = -992336347301800690L;

  public TipoEccedenzaRimborsoTariEngDropDownChoice(
      String id, IModel<TipoEccedenzaRimborsoTariEng> model) {
    super(id);

    setChoices(getListaTipoEccedenze());
    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    setLabel(Model.of("Tipologia eccedenza rimborso"));

    setRequired(true);

    setChoiceRenderer(new TipoEccedenzaRimborsoTariEngRender());
  }

  private static List<TipoEccedenzaRimborsoTariEng> getListaTipoEccedenze() {
    return Arrays.asList(TipoEccedenzaRimborsoTariEng.values());
  }
}
