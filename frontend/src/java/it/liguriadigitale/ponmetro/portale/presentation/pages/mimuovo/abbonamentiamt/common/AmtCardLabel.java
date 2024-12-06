package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import org.apache.wicket.Component;

/**
 * The Class AmtCardLabel.
 *
 * <p>questa classe usa un HTML diverso rispetto alla classe madre
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class AmtCardLabel<T extends Component> extends CardLabel {

  private static final long serialVersionUID = -8143563574622388443L;

  public AmtCardLabel(String id, Object value, Component pannello, String resourceId) {
    super(id, value, pannello, resourceId);
  }

  public AmtCardLabel(String id, Object value, Component istanza) {
    super(id, value, istanza);
  }
}
