package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.ajax.PaginazioneAjaxFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli.DettagliScadenzaAccertatoPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class SummaryScadenzaAccertatoPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "SummaryScadenzaAccertatoPanel.";

  protected PaginazioneAjaxFascicoloPanel paginazioneAjaxFascicolo;

  public SummaryScadenzaAccertatoPanel(String id) {
    super(id, 7);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    @SuppressWarnings("unchecked")
    List<AccertatoScadenzeExt> listItem = (List<AccertatoScadenzeExt>) dati;

    Label listaVuota = new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaVuota.setVisible(listItem == null || listItem.isEmpty());
    add(listaVuota);

    Label titolo = new Label("titolo", getString(getBaseLabelKey() + "titolo"));
    addOrReplace(titolo);

    setLarghezzaPrimaColonna(6);

    PageableListView<AccertatoScadenzeExt> listView =
        new PageableListView<AccertatoScadenzeExt>("box", listItem, 2) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<AccertatoScadenzeExt> item) {
            final AccertatoScadenzeExt itemModel = item.getModelObject();

            addIconaETitolo(itemModel, item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "statoPagamento",
                          "documentoEmesso",
                          "annoRiferimentoAtto",
                          "dovutoAttoRidottoL",
                          "dovutoAttoPienoL",
                        }));
            item.add(createContentPanel(itemModel, fields));

            LaddaAjaxLink<Object> bottoneDettagli =
                creaBottoneDettagli(itemModel, new DettagliScadenzaAccertatoPage(itemModel));
            item.addOrReplace(bottoneDettagli);
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
    paginazioneAjaxFascicolo = new PaginazioneAjaxFascicoloPanel("pagination", listView);
    paginazioneAjaxFascicolo.setComponentToRender(this);
    paginazioneAjaxFascicolo.setVisible(listItem != null && listItem.size() > 2);
    add(paginazioneAjaxFascicolo);
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    AccertatoScadenzeExt elemento = (AccertatoScadenzeExt) t;
    String css = elemento.getColorForIcon();
    css += elemento.getBaseIcon();
    return new AttributeAppender("class", " " + css);
  }
}
