package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.VersamentiAttoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class DatiVersamentiAttoPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiVersamentiAttoPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public DatiVersamentiAttoPanel(String id) {
    super(id, 7);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    AccertatoScadenzeExt accertatoScadenzeExt = (AccertatoScadenzeExt) dati;

    List<VersamentiAttoScadenzeExt> listVersamentiAttoScadenzeExt =
        accertatoScadenzeExt.getVersamentiAttoScadenzeExt();

    Label listaComproprietariVuota =
        new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaComproprietariVuota.setVisible(
        listVersamentiAttoScadenzeExt == null || listVersamentiAttoScadenzeExt.isEmpty());
    add(listaComproprietariVuota);

    Label titoloComproprietari =
        new Label("titolo", getString(getBaseLabelKey() + "titoloVersamentiAtto"));
    addOrReplace(titoloComproprietari);
    PageableListView<VersamentiAttoScadenzeExt> listView =
        new PageableListView<VersamentiAttoScadenzeExt>("box", listVersamentiAttoScadenzeExt, 6) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<VersamentiAttoScadenzeExt> item) {
            final VersamentiAttoScadenzeExt itemModel = item.getModelObject();

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "rataVersamento",
                          "annoVersamento",
                          "numeroVersamento",
                          "dataVersamento",
                          "importoVersatoL"
                        }));

            item.add(createContentPanel(itemModel, fields));
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    paginazioneAjaxFascicolo = new PaginazioneAjaxFascicoloPanel("pagination", listView);
    paginazioneAjaxFascicolo.setComponentToRender(this);
    paginazioneAjaxFascicolo.setVisible(
        listVersamentiAttoScadenzeExt != null && listVersamentiAttoScadenzeExt.size() > 6);
    add(paginazioneAjaxFascicolo);
  }
}
