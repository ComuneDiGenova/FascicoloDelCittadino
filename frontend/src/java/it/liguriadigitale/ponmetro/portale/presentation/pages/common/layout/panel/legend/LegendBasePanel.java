package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.legend;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class LegendBasePanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 4234527383068466160L;
  private static final String BASE_PANEL_ID = "legendPanel";
  private static final String BASE_LABEL_KEY = "LegendBasePanel.";
  private String idTitoloCustom;

  public LegendBasePanel(final String[][] stati, int numeroElementi) {
    this(BASE_PANEL_ID, stati, numeroElementi);
  }

  public LegendBasePanel(final String id, final String[][] stati, int numeroElementi) {
    this(id, null, stati, numeroElementi);
  }

  public LegendBasePanel(
      final String id, final String idTitoloCustom, final String[][] stati, int numeroElementi) {
    super(id);
    this.idTitoloCustom = idTitoloCustom;
    initContent(stati, numeroElementi);
  }

  protected void initContent(final String[][] stati, int numeroElementi) {
    List<LegendBaseElement> baseElements = new ArrayList<LegendBaseElement>();
    for (int i = 0; i < numeroElementi; i++) {
      baseElements.add(new LegendBaseElement(stati[i][0], stati[i][1], stati[i][2]));
    }
    fillDati(baseElements);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  // to-do
  // legenda con icona read
  // legenda "bottone" che filtra result
  // legenda "bottone" con icona che filtra result
  @Override
  public void fillDati(Object dati) {
    String titolo = getString(getBaseLabelKey() + "titolo");
    if (idTitoloCustom != null) {
      titolo = getString(getBaseLabelKey() + idTitoloCustom);
    }
    Label lTitolo = new Label("titolo", titolo);
    add(lTitolo);

    @SuppressWarnings("unchecked")
    ListView<LegendBaseElement> loopingOnPages =
        new ListView<LegendBaseElement>("elementi", (List<LegendBaseElement>) dati) {

          private static final long serialVersionUID = -8035233904559627868L;

          @Override
          protected void populateItem(ListItem<LegendBaseElement> item) {
            LegendBaseElement element = item.getModelObject();
            item.add(element.getEntireLabel(true));
          }
        };
    add(loopingOnPages);
  }
}
