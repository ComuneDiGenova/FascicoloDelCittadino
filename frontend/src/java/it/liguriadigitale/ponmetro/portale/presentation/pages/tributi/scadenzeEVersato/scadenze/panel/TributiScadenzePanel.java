package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.ElencoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.OrdinarioScadenzeExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.TributiScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.SummaryScadenzaAccertatoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.ordinario.DatiScadenzaOrdinarioPanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class TributiScadenzePanel extends BasePanelGenericContent {

  private static final long serialVersionUID = -4276599417139686147L;
  protected Log log = LogFactory.getLog(getClass());

  private Integer annoIesimo;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public TributiScadenzePanel(String id, Integer annoIesimo) {
    super(id);
    this.setAnnoIesimo(annoIesimo);
    log.error("TributiScadenzePanel TributiScadenzePanel " + id);
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  protected String getBaseLabelKey() {
    return StringUtils.capitalize(
            HomeWebApplication.ID_TRIBSCADENZE_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME)
        + HomeWebApplication.ID_SUFFIX_PANEL_WITH_DOT;
  }

  @Override
  public void fillDati(Object dati) {
    TributiScadenzeExt tDati = (TributiScadenzeExt) dati;
    log.error("ScadenzePanel fillDati " + tDati);

    List<ElencoScadenzeExt> list =
        tDati == null ? new ArrayList<ElencoScadenzeExt>() : tDati.getElencoScadenzeExt();

    log.error("ScadenzePanel list " + list);

    Label listaVuota = new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaVuota.setVisible(list.isEmpty());
    add(listaVuota);

    PageableListView<ElencoScadenzeExt> listView =
        new PageableListView<ElencoScadenzeExt>("box", list, 8) {

          private static final long serialVersionUID = 1927027489230651444L;

          @Override
          protected void populateItem(ListItem<ElencoScadenzeExt> item) {
            final ElencoScadenzeExt itemModel = item.getModelObject();

            log.error("ScadenzePanel ElencoScadenzeExt " + itemModel);

            addFieldString(itemModel, "titolo", item);

            // non ci sono i campi, capire cosa cfare vedere qui e cosa fare
            // vedere nela pagina di dettaglio
            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(new String[] {"tributo", "delibera", "accertato"}));

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIcona(itemModel));
            item.add(icona);

            item.addOrReplace(createContentPanel(itemModel, fields));

            // aggiungiFigli();

            DatiScadenzaOrdinarioPanel datiScadenzaOrdinarioPanel =
                new DatiScadenzaOrdinarioPanel("listaUnoFigli");
            List<OrdinarioScadenzeExt> listOrdinarioScadenzeExt =
                itemModel.getOrdinarioScadenzeExt();
            datiScadenzaOrdinarioPanel.fillDati(listOrdinarioScadenzeExt);
            datiScadenzaOrdinarioPanel.setVisible(listOrdinarioScadenzeExt != null);
            item.add(datiScadenzaOrdinarioPanel);

            SummaryScadenzaAccertatoPanel summaryScadenzaAccertatoPanel =
                new SummaryScadenzaAccertatoPanel("listaDueFigli");
            List<AccertatoScadenzeExt> listAccertatoScadenzeExt =
                itemModel.getAccertatoScadenzeExt();
            summaryScadenzaAccertatoPanel.fillDati(listAccertatoScadenzeExt);
            summaryScadenzaAccertatoPanel.setVisible(listAccertatoScadenzeExt != null);
            item.add(summaryScadenzaAccertatoPanel);
          }
        };

    add(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(list.size() > 4);
    add(paginazioneFascicolo);
  }

  @Override
  protected String getBaseIcon() {
    return BasePanelGenericContent.ALL_ICON_IOCON_SCADENZE;
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    ElencoScadenzeExt elemento = (ElencoScadenzeExt) t;
    String css = elemento.getColorForIcon();
    css += getBaseIcon();
    return new AttributeAppender("class", " " + css);
  }

  public Integer getAnnoIesimo() {
    return annoIesimo;
  }

  public void setAnnoIesimo(Integer annoIesimo) {
    this.annoIesimo = annoIesimo;
  }
}
