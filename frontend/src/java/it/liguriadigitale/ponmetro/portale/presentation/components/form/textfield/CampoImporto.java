package it.liguriadigitale.ponmetro.portale.presentation.components.form.textfield;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;
import org.apache.wicket.validation.validator.RangeValidator;

public class CampoImporto extends TextField<BigDecimal> {

  private static final long serialVersionUID = 2640713321121637721L;

  protected Log log = LogFactory.getLog(this.getClass());

  public CampoImporto(String id) {
    super(id);
    setType(BigDecimal.class);
  }

  public CampoImporto(String id, Model<BigDecimal> model) {
    super(id, model);
  }

  public CampoImporto(String id, BigDecimal minimoImporto, BigDecimal massimoImporto) {
    this(id);
    this.add(new RangeValidator<>(minimoImporto, massimoImporto));
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public IConverter getConverter(Class type) {

    return new BigDecimalConverter() {

      private static final long serialVersionUID = -6040401160867382807L;

      @Override
      public BigDecimal convertToObject(String value, Locale locale) {

        final String EURO_PATTERN =
            "^[+-]?(([0-9]*([,][0-9]{1,2})?)|[0-9]{1,3}(([.][0-9]{3})*([,][0-9]{1,2})?))$";

        Pattern patternImporto = Pattern.compile(EURO_PATTERN);

        // IMPORTO IN ITALIANO
        if (patternImporto.matcher(value).matches())
          try {
            return super.convertToObject(value, locale);
          } catch (ConversionException e) {
            log.error("[CampoImporto] convertToObject - Formato importo non corretto");
            throw (new ConversionException("Formato importo non corretto"));
          }
        else {
          log.error("[CampoImporto] convertToObject - Formato importo non corretto");
          throw (new ConversionException("Formato importo non corretto"));
        }
      }
    };
  }
}
