package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.autocomplete;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.TabellaPaeseRecord;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class CittadinanzeAutocompleteConverter implements IConverter<TabellaPaeseRecord> {

  protected static Log log = LogFactory.getLog(CittadinanzeAutocompleteConverter.class);

  private List<TabellaPaeseRecord> listaCittadinanze;

  public void setListaCittadinanze(List<TabellaPaeseRecord> listaCittadinanze) {
    this.listaCittadinanze = listaCittadinanze;
  }

  @Override
  public TabellaPaeseRecord convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaCittadinanze.stream()
        .filter(x -> x.getDscr().contains(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(TabellaPaeseRecord obj, Locale arg1) {
    // TODO  -generated method stub
    return obj.getDscr();
  }
}
