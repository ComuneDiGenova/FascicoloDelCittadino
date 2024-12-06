package it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete;

import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ProvinciaBorseStudioAutocompleteConverter implements IConverter<Provincia> {

  private static final long serialVersionUID = 7840149186134250266L;

  protected static Log log = LogFactory.getLog(ProvinciaBorseStudioAutocompleteConverter.class);

  private List<Provincia> listaProvince;

  public void setListaProvince(List<Provincia> listaProvince) {
    this.listaProvince = listaProvince;
  }

  @Override
  public Provincia convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaProvince.stream()
        .filter(x -> x.getDescrizione().contains(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(Provincia obj, Locale arg1) {
    // TODO  -generated method stub
    return obj.getDescrizione();
  }
}
