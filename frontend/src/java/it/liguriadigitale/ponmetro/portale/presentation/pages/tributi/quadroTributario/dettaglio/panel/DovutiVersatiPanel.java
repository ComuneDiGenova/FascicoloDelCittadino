package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.SchedaTributoDovutiVersatiExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DovutiVersatiPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DovutiVersatiPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public DovutiVersatiPanel(String id) {
    super(id, 5);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<SchedaTributoDovutiVersatiExt> listaOggetti = (List<SchedaTributoDovutiVersatiExt>) dati;

    Label listaVuota = new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaVuota.setVisible(listaOggetti == null || listaOggetti.isEmpty());
    add(listaVuota);

    Label titolo = new Label("titolo", getString(getBaseLabelKey() + "titolo"));
    addOrReplace(titolo);
    PageableListView<SchedaTributoDovutiVersatiExt> listView =
        new PageableListView<SchedaTributoDovutiVersatiExt>("box", listaOggetti, 6) {

          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<SchedaTributoDovutiVersatiExt> item) {
            final SchedaTributoDovutiVersatiExt oggetto = item.getModelObject();

            // addIconaETitolo(comproprietario, item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "rata",
                          "dataScadenza",
                          // "dovutol",
                          "versatoL",
                          // "residuo",
                          // "versatoRavvedimentoOpe"
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
