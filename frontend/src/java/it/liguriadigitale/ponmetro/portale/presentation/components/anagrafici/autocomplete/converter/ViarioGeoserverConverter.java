package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.autocomplete.converter;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ViarioGeoserverConverter implements IConverter<FeaturesGeoserver> {

  private static final long serialVersionUID = 342243267139436976L;

  protected static Log log = LogFactory.getLog(ViarioGeoserverConverter.class);

  private List<FeaturesGeoserver> listaIndirizzi;

  public void setListaIndirizzi(List<FeaturesGeoserver> listaIndirizzi) {
    this.listaIndirizzi = listaIndirizzi;
  }

  @Override
  public FeaturesGeoserver convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaIndirizzi.stream()
        .filter(x -> x.getMACHINE_LAST_UPD().contains(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(FeaturesGeoserver obj, Locale arg1) {
    // TODO  -generated method stub
    return obj.getMACHINE_LAST_UPD();
  }
}
