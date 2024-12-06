package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete;

import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ComuneBorseStudioAutocompleteConverter implements IConverter<Comune> {

  protected static Log log = LogFactory.getLog(ComuneBorseStudioAutocompleteConverter.class);

  private List<Comune> listaComuni;

  public void setListaComuni(List<Comune> listaComuni) {
    this.listaComuni = listaComuni;
  }

  @Override
  public Comune convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaComuni.stream()
        .filter(x -> x.getDescrizione().contains(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(Comune obj, Locale arg1) {
    // TODO  -generated method stub
    return obj.getDescrizione();
  }
}
