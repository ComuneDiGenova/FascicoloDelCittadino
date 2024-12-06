package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.autocomplete.ProvinciaBorseStudioAutocompleteConverter;
import it.liguriadigitale.ponmetro.serviziristorazione.model.Provincia;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ProvinciaIscrizioneMensaConverter implements IConverter<Provincia> {

  private static final long serialVersionUID = -4591067535452728526L;

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
