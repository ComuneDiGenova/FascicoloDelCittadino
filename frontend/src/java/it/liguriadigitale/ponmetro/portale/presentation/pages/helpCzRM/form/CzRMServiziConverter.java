package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.api.pojo.helpczrm.CzrmServizi;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class CzRMServiziConverter implements IConverter<CzrmServizi> {

  private static final long serialVersionUID = -13121498751318L;

  protected static Log log = LogFactory.getLog(CzRMServiziConverter.class);

  private List<CzrmServizi> listaServizi;

  public void setListaServizi(List<CzrmServizi> servizi) {
    this.listaServizi = servizi;
  }

  @Override
  public CzrmServizi convertToObject(String value, Locale arg1) throws ConversionException {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);

    return listaServizi.stream()
        .filter(x -> x.getServizioValue().equals(value))
        .findAny()
        .orElse(null);
  }

  @Override
  public String convertToString(CzrmServizi obj, Locale arg1) {
    // TODO  -generated method stub
    return obj.getServizioText();
  }
}
