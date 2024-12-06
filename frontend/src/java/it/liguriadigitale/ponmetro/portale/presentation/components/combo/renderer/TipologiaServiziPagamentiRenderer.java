package it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer;

import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class TipologiaServiziPagamentiRenderer implements IChoiceRenderer<TipologiaEntrata> {

  private static final long serialVersionUID = -4571300224948976143L;

  @Override
  public Object getDisplayValue(TipologiaEntrata obj) {
    TipologiaEntrata tipologiaEntrata = obj;
    if (tipologiaEntrata != null && tipologiaEntrata.getDescrizioneServizio() != null) {
      return tipologiaEntrata.getDescrizioneServizio();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(TipologiaEntrata obj, final int index) {
    TipologiaEntrata tipologiaEntrata = obj;
    if (tipologiaEntrata != null && tipologiaEntrata.getNomeServizio() != null) {
      return String.valueOf(tipologiaEntrata.getNomeServizio());
    } else {
      return "";
    }
  }

  @Override
  public TipologiaEntrata getObject(
      String id, IModel<? extends List<? extends TipologiaEntrata>> lista) {
    for (TipologiaEntrata dati : lista.getObject()) {
      if (dati != null) if (dati.getNomeServizio().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
