package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.ordinario;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.OrdinarioScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DatiScadenzaOrdinarioPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiScadenzaOrdinarioPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public DatiScadenzaOrdinarioPanel(String id) {
    super(id);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    /*
     *
     * superFillDati( (List<OrdinarioScadenzeExt>) dati, getBaseLabelKey(),
     * new ArrayList<String>( Arrays.asList(new String[] { "rata",
     * "dataScadenzaRata" })), false, true, 6L, 6);
     */
    @SuppressWarnings("unchecked")
    List<OrdinarioScadenzeExt> listItem = (List<OrdinarioScadenzeExt>) dati;

    Label listaVuota = new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaVuota.setVisible(listItem == null || listItem.isEmpty());
    add(listaVuota);

    Label titolo = new Label("titolo", getString(getBaseLabelKey() + "titolo"));
    titolo.setVisible(false);
    addOrReplace(titolo);

    setLarghezzaPrimaColonna(6);

    PageableListView<OrdinarioScadenzeExt> listView =
        new PageableListView<OrdinarioScadenzeExt>("box", listItem, 2) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<OrdinarioScadenzeExt> item) {
            final OrdinarioScadenzeExt ithitem = item.getModelObject();

            List<String> fields =
                new ArrayList<String>(Arrays.asList(new String[] {"rata", "dataScadenzaRata"}));

            item.add(createContentPanel(ithitem, fields));
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    paginazioneAjaxFascicolo = new PaginazioneAjaxFascicoloPanel("pagination", listView);
    paginazioneAjaxFascicolo.setComponentToRender(this);
    paginazioneAjaxFascicolo.setVisible(listItem != null && listItem.size() > 6);
    add(paginazioneAjaxFascicolo);
  }
}
