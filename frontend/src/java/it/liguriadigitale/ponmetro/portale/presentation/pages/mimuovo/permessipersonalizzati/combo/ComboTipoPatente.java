package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.combo;

import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCDropDownChoice;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.model.IModel;

public class ComboTipoPatente extends FdCDropDownChoice<String> {

  private static final long serialVersionUID = -6765798125295641058L;

  boolean isResidente;

  public ComboTipoPatente(String id, IModel<String> model) {
    super(id);

    List<String> listaCategoriePatente =
        new ArrayList<String>(
            Arrays.asList(
                "A", "A1", "AB", "AC", "AD", "AE", "AM", "B", "BE", "C", "CE", "D", "DE", "E", "KB",
                "KD", "M", "P"));

    setChoices(listaCategoriePatente);

    setModel(model);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }
}
