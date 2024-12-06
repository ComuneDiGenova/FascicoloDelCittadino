package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.serviziristorazione.model.Cittadinanza;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class CittadinanzeIscrizioneMensaConverter implements IConverter<Cittadinanza> {

  protected static Log log = LogFactory.getLog(CittadinanzeIscrizioneMensaConverter.class);

  private List<Cittadinanza> listaCittadinanze;

  public void setListaCittadinanze(List<Cittadinanza> listaCittadinanze) {
    this.listaCittadinanze = listaCittadinanze;
  }

  @Override
  public Cittadinanza convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaCittadinanze.stream()
        .filter(x -> x.getDescrizione().contains(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(Cittadinanza obj, Locale arg1) {
    return obj.getDescrizione();
  }
}
