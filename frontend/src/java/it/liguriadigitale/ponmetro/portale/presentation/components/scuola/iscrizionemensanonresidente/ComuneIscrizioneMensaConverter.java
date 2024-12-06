package it.liguriadigitale.ponmetro.portale.presentation.components.scuola.iscrizionemensanonresidente;

import it.liguriadigitale.ponmetro.serviziristorazione.model.Comune;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ComuneIscrizioneMensaConverter implements IConverter<Comune> {

  private static final long serialVersionUID = 8446994780155385443L;

  protected static Log log = LogFactory.getLog(ComuneIscrizioneMensaConverter.class);

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
    return obj.getDescrizione();
  }
}
