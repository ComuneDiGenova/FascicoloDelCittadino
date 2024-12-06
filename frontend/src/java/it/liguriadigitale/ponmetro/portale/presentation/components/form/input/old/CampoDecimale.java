package it.liguriadigitale.ponmetro.portale.presentation.components.form.input.old;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;
import org.apache.wicket.validation.validator.RangeValidator;

public class CampoDecimale extends CampoGenerico<BigDecimal> {

  private static final long serialVersionUID = 2567739758409958652L;

  public CampoDecimale(String nomeCampo) {
    super(nomeCampo);
  }

  public CampoDecimale(
      String nomeCampo, Integer lunghezzaFissa, BigDecimal minimoValore, BigDecimal massimoValore) {
    super(nomeCampo, lunghezzaFissa);
    this.add(new RangeValidator<BigDecimal>(minimoValore, massimoValore));
  }

  public CampoDecimale(
      String nomeCampo,
      Integer lunghezzaMinima,
      Integer lunghezzaMassima,
      BigDecimal minimoValore,
      BigDecimal massimoValore) {
    super(nomeCampo, lunghezzaMinima, lunghezzaMassima);
    this.add(new RangeValidator<BigDecimal>(minimoValore, massimoValore));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public IConverter getConverter(Class type) {

    return new BigDecimalConverter() {

      private static final long serialVersionUID = -7680698119541184588L;

      @Override
      public BigDecimal convertToObject(String value, Locale locale) {

        final String DECIMAL_PATTERN =
            "^[+-]?(([0-9]*([,][0-9]*)?)|[0-9]{1,3}(([.][0-9]{3})*([,][0-9]*)?))$";

        Pattern patternImporto = Pattern.compile(DECIMAL_PATTERN);

        // IMPORTO IN EURO
        if (patternImporto.matcher(value).matches())
          try {
            return super.convertToObject(value, locale);
          } catch (ConversionException e) {
            throw (new ConversionException("Formato importo non corretto"));
          }
        else {
          throw (new ConversionException("Formato importo non corretto"));
        }
      }
    };
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    StringBuilder sb = new StringBuilder();
    if (isRequired()) {
      sb.append(
          "$(document).ready(function() { \r\n"
              + "$('#"
              + getMarkupId()
              + "').rules( \"add\", { required: true}) ;});");
    }
    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }
}
