package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;

public class FdCMultiLineLabel<T extends Component> extends Panel {

  private static final long serialVersionUID = -5197932571565619850L;

  Label label;
  String wicketId;

  public FdCMultiLineLabel(String wicketId, List<String> listaValoriStringa, T pannello) {
    super(wicketId);
    this.wicketId = wicketId;

    StringBuilder sb = new StringBuilder();
    for (String valoreStringa : listaValoriStringa) {
      sb.append(valoreStringa + "\n");
    }

    MultiLineLabel indirizzi = new MultiLineLabel("valoriStringa", sb.toString());
    addOrReplace(indirizzi);

    creaLabelEtichettaGenerandoResourceId(pannello, wicketId);

    addOrReplace(label);
  }

  private void creaLabelEtichettaGenerandoResourceId(T pannello, String wicketId) {
    String nomePannello = pannello.getClass().getSimpleName();
    String resourceId = nomePannello + "." + wicketId;
    creaLabelEtichetta(pannello, resourceId, wicketId);
  }

  private void creaLabelEtichetta(T pannello, String resourceId, String wicketId) {
    String etichetta = getLocalizer().getString(resourceId, pannello);

    label = new NotEmptyLabel("label", etichetta);
    label.setOutputMarkupId(true);
    label.setOutputMarkupPlaceholderTag(true);

    label.add(new AttributeModifier("for", "textField" + wicketId));

    addOrReplace(label);
  }
}
