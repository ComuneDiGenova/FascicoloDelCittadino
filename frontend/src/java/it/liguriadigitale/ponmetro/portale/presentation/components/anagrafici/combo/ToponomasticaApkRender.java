package it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo;

import it.liguriadigitale.ponmetro.servizianagrafici.model.Toponomastica;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ToponomasticaApkRender implements IOptionRenderer<Toponomastica> {

  private static final long serialVersionUID = -7063326542406089289L;

  @Override
  public String getDisplayValue(Toponomastica topo) {
    String interno = topo.getInterno() == null ? "" : "Interno ".concat(topo.getInterno());
    String internoEsponente =
        topo.getInternoEsponente() == null ? "" : " - ".concat(topo.getInternoEsponente());

    return interno.concat(internoEsponente);
  }

  @Override
  public IModel<Toponomastica> getModel(Toponomastica value) {
    return new Model<Toponomastica>(value);
  }
}
