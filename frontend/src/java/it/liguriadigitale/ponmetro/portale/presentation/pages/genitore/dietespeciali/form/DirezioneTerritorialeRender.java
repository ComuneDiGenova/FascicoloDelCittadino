package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class DirezioneTerritorialeRender implements IChoiceRenderer<DatiDirezioneTerritoriale> {

  /** */
  private static final long serialVersionUID = 1L;

  @Override
  public Object getDisplayValue(DatiDirezioneTerritoriale obj) {
    DatiDirezioneTerritoriale item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getNome())) {
      return item.getNome();
    } else {
      return "";
    }
  }

  @Override
  public String getIdValue(DatiDirezioneTerritoriale obj, int index) {
    DatiDirezioneTerritoriale item = obj;
    if (LabelFdCUtil.checkIfNotNull(item) && LabelFdCUtil.checkIfNotNull(item.getCodDirezTerr())) {
      return String.valueOf(item.getNome());
    } else {
      return "";
    }
  }

  @Override
  public DatiDirezioneTerritoriale getObject(
      String id, IModel<? extends List<? extends DatiDirezioneTerritoriale>> lista) {
    for (DatiDirezioneTerritoriale dati : lista.getObject()) {
      if (LabelFdCUtil.checkIfNotNull(dati)) if (dati.getNome().equalsIgnoreCase(id)) return dati;
    }

    return null;
  }
}
