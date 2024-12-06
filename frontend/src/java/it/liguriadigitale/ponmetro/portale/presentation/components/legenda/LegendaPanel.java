package it.liguriadigitale.ponmetro.portale.presentation.components.legenda;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class LegendaPanel<T extends Component> extends BasePanel {

  private static final long serialVersionUID = 7797733322338606726L;

  public LegendaPanel(String id, List<Legenda> listaLegenda) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(listaLegenda);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Legenda> lista = (List<Legenda>) dati;

    ListView<Legenda> listView =
        new ListView<Legenda>("lista", lista) {

          private static final long serialVersionUID = 3578424913234019571L;

          @Override
          protected void populateItem(ListItem<Legenda> item) {
            final Legenda legenda = item.getModelObject();

            String testo = "";
            if (PageUtil.isStringValid(legenda.getTesto())) {
              testo = legenda.getTesto().toUpperCase();
            }
            Label elemento = new Label("elemento", testo);
            AttributeModifier attributeModifier =
                new AttributeModifier("class", legenda.getStile());
            elemento.add(attributeModifier);
            item.addOrReplace(elemento);
          }
        };
    addOrReplace(listView);
  }
}
