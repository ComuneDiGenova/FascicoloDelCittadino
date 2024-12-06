package it.liguriadigitale.ponmetro.portale.presentation.pages.autodichiarazionefigli.panel;

import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DomandeAutodichiarazioneFigliPanel extends BasePanel {

  private static final long serialVersionUID = -4619890527864356889L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  // TODO per ora metto list string per mock
  public DomandeAutodichiarazioneFigliPanel(String id, List<String> lista) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<String> listaDomande = (List<String>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkDomande(listaDomande));
    addOrReplace(listaVuota);

    PageableListView<String> listView =
        new PageableListView<String>("domande", listaDomande, 4) {

          @Override
          protected void populateItem(ListItem<String> itemDomanda) {
            final String domanda = itemDomanda.getModelObject();
          }
        };

    listView.setVisible(checkDomande(listaDomande));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkDomande(listaDomande) && listaDomande.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkDomande(List<String> lista) {
    return LabelFdCUtil.checkIfNotNull(lista) && !LabelFdCUtil.checkEmptyList(lista);
  }
}
