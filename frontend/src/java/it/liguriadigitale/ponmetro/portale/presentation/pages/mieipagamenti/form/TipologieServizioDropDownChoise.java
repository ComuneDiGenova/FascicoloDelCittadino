package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.form;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

public class TipologieServizioDropDownChoise extends DropDownChoice<TipologiaEntrata> {

  private static final long serialVersionUID = -3451442644482684027L;

  private Log log = LogFactory.getLog(getClass());

  private static final String STYLE_COLOR = " style='background-color: #cccccc' ";

  public TipologieServizioDropDownChoise(String id, IModel<List<TipologiaEntrata>> choices) {
    super(id, choices);
  }

  @Override
  protected void setOptionAttributes(
      AppendingStringBuffer buffer, TipologiaEntrata choice, int index, String selected) {
    if (choice != null) {
      buffer.append(STYLE_COLOR);
      log.debug("buffer.nome =" + choice.getDescrizioneServizio());
      log.debug("buffer.append =" + choice.getNomeServizio());
    }

    super.setOptionAttributes(buffer, choice, index, selected);
  }
}
