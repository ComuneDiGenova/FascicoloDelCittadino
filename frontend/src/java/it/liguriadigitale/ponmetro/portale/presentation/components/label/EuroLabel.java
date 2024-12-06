package it.liguriadigitale.ponmetro.portale.presentation.components.label;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class EuroLabel extends Label {

  private static final long serialVersionUID = 7759040936517270200L;

  private static NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.GERMANY);

  public EuroLabel(String id, IModel<Double> model) {
    super(id, model);
  }

  public EuroLabel(String id, Double label) {
    super(id, Model.of(label));
  }

  public EuroLabel(String id, BigDecimal importo) {
    super(id, formattaImporto(importo));
  }

  private static String formattaImporto(BigDecimal importo) {
    if (null != importo) return numberFormatEuro.format(importo);
    else return null;
  }

  @SuppressWarnings("unused")
  private static String formattaImporto(Long importo) {
    if (null != importo) return numberFormatEuro.format(importo.doubleValue());
    else return null;
  }

  @SuppressWarnings("unused")
  private static String formattaImporto(IModel<Double> importo) {
    return numberFormatEuro.format(importo.getObject().doubleValue());
  }

  private static String formattaImporto(Double importo) {
    if (null != importo) return numberFormatEuro.format(importo);
    else return null;
  }

  @Override
  public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
    Double doubleValore = (Double) getDefaultModelObject();
    replaceComponentTagBody(markupStream, openTag, formattaImporto(doubleValore));
  }
}
