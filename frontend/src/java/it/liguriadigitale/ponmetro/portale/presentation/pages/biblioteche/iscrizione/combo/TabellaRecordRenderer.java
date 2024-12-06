package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.combo;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TabellaRecordRenderer extends ChoiceRenderer<TabellaPaeseRecord> {

  private static final long serialVersionUID = 4844500144689266196L;

  @Override
  public Object getDisplayValue(final TabellaPaeseRecord tabellaRecord) {
    return tabellaRecord.getDscr();
  }

  @Override
  public String getIdValue(final TabellaPaeseRecord obj, final int index) {
    TabellaPaeseRecord c = obj;
    return "" + c.getCd();
  }
}
