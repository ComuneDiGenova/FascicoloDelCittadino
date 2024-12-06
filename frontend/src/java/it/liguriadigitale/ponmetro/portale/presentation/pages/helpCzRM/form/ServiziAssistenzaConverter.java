package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.form;

import it.liguriadigitale.ponmetro.portale.pojo.richiestaassistenza.SottoCategoria;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

public class ServiziAssistenzaConverter implements IConverter<SottoCategoria> {

  protected static Log log = LogFactory.getLog(ServiziAssistenzaConverter.class);

  private List<SottoCategoria> listaServizi;

  public void setListaServizi(List<SottoCategoria> servizi) {
    this.listaServizi = servizi;
  }

  @Override
  public SottoCategoria convertToObject(String value, Locale arg1) throws ConversionException {

    return listaServizi.stream().filter(x -> x.getServizio().equals(value)).findAny().orElse(null);
  }

  @Override
  public String convertToString(SottoCategoria obj, Locale arg1) {
    return obj.getServizio();
  }
}
