package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

public class ImportoEuroLabel extends Label {

  private static final long serialVersionUID = 7759040936517270200L;

  private static NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.GERMANY);

  public ImportoEuroLabel(String id, IModel<Long> importo) {
    super(id, formattaImporto(importo));
  }

  public ImportoEuroLabel(String id, Double importo) {
    super(id, formattaImporto(importo));
  }

  public ImportoEuroLabel(String id, Float importo) {
    super(id, formattaImporto(importo));
  }

  public ImportoEuroLabel(String id, Long importo) {
    super(id, formattaImporto(importo));
  }

  public ImportoEuroLabel(String id, BigDecimal importo) {
    super(id, formattaImporto(importo));
  }

  private static String formattaImporto(BigDecimal importo) {
    if (null != importo) return numberFormatEuro.format(importo);
    else return null;
  }

  private static String formattaImporto(Float importo) {
    if (null != importo) return numberFormatEuro.format(importo);
    else return null;
  }

  private static String formattaImporto(Long importo) {
    if (null != importo) return numberFormatEuro.format(importo.doubleValue());
    else return null;
  }

  private static String formattaImporto(IModel<?> importo) {
    return numberFormatEuro.format(((Double) importo.getObject()).doubleValue());
  }

  private static String formattaImporto(Double importo) {
    if (null != importo) return numberFormatEuro.format(importo);
    else return null;
  }
}
