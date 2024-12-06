package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ComproprietariExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DatiComproprietariPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiComproprietariPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public DatiComproprietariPanel(String id) {
    super(id);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    @SuppressWarnings("unchecked")
    List<ComproprietariExt> comproprietari = (List<ComproprietariExt>) dati;

    Label listaComproprietariVuota =
        new Label("listaComproprietariVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaComproprietariVuota.setVisible(comproprietari == null || comproprietari.isEmpty());
    add(listaComproprietariVuota);

    Label titoloComproprietari =
        new Label("titolo", getString(getBaseLabelKey() + "titoloComproprietari"));
    addOrReplace(titoloComproprietari);
    PageableListView<ComproprietariExt> listView =
        new PageableListView<ComproprietariExt>("box", comproprietari, 6) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<ComproprietariExt> item) {
            final ComproprietariExt comproprietario = item.getModelObject();

            // addIconaETitolo(comproprietario, item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "denominazione", "codiceFiscale",
                          // "tipoSoggetto",
                          // "dataInizioPossesso",
                          // "dataFinePossesso",
                          // "percentualePossesso"
                        }));

            item.add(createContentPanel(comproprietario, fields));
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    paginazioneAjaxFascicolo =
        new PaginazioneAjaxFascicoloPanel("paginationComproprietari", listView);
    paginazioneAjaxFascicolo.setComponentToRender(this);
    paginazioneAjaxFascicolo.setVisible(comproprietari != null && comproprietari.size() > 6);
    add(paginazioneAjaxFascicolo);
  }
}
