package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.SchedaTributoOggettiExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class OggettiPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "OggettiPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public OggettiPanel(String id) {
    super(id, 6);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<SchedaTributoOggettiExt> listaOggetti = (List<SchedaTributoOggettiExt>) dati;

    Label listaVuota = new Label("listaVuota", getString(BASE_LABEL_KEY + "listaVuota"));
    listaVuota.setVisible(listaOggetti == null || listaOggetti.isEmpty());
    add(listaVuota);

    Label titolo = new Label("titolo", getString(BASE_LABEL_KEY + "titolo"));
    addOrReplace(titolo);
    PageableListView<SchedaTributoOggettiExt> listView =
        new PageableListView<SchedaTributoOggettiExt>("box", listaOggetti, 6) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<SchedaTributoOggettiExt> item) {
            final SchedaTributoOggettiExt oggetto = item.getModelObject();

            // addIconaETitolo(comproprietario, item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          // "codiceOggettoAnagrafico",
                          "indirizzoCompleto",
                          "sezione",
                          "foglio",
                          "particella",
                          "subalterno",
                          "zonaCensuaria",
                          "categoria",
                          "classe",
                          "consistenza",
                          // "codiceVia",
                          // "codiceAccesso",
                          // "codiceInterno",
                          "dataInizioLegame",
                          "dataFineLegame"
                        }));

            item.add(createContentPanel(oggetto, fields));
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    paginazioneAjaxFascicolo = new PaginazioneAjaxFascicoloPanel("pagination", listView);
    paginazioneAjaxFascicolo.setComponentToRender(this);
    paginazioneAjaxFascicolo.setVisible(listaOggetti != null && listaOggetti.size() > 6);
    add(paginazioneAjaxFascicolo);
  }
}
