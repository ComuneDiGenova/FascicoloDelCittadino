package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.QuadroTributarioExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.quadrotributario.TributiExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.DettaglioQuadroTributarioPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class QuadroTributarioPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 4335375157068008441L;

  private static final String BASE_LABEL_KEY = "QuadroTributarioPanel.";
  static final String URL_PER_CALCOLARE =
      "https://calcolotributi.comune.genova.it/portale-tributi-tasi/";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  private Integer annoScelto;

  private ExternalLink createLinkEsternoCalcola(String idWicket) {
    ExternalLink btnCalcola = new ExternalLink(idWicket, URL_PER_CALCOLARE);
    btnCalcola.setVisible(false); // SPOSTATO IN SCADENZEtrue);
    return btnCalcola;
  }

  public QuadroTributarioPanel(String id, Integer annoScelto) {
    super(id);
    this.annoScelto = annoScelto;
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<TributiExt> listaTributi = (List<TributiExt>) dati;

    // tributi
    Label listaTributiVuota = new Label("listaVuota", getString(BASE_LABEL_KEY + "listaVuota"));
    listaTributiVuota.setVisible(listaTributi.isEmpty());
    add(listaTributiVuota);

    // listaTributi.forEach((Tributi tributo) -> {
    //
    // });

    PageableListView<TributiExt> listView =
        new PageableListView<TributiExt>("box", listaTributi, 8) {

          private static final long serialVersionUID = 1937027489230651444L;

          @Override
          protected void populateItem(ListItem<TributiExt> item) {
            final TributiExt itemModel = item.getModelObject();

            addFieldString(itemModel, "titolo", item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "anno", "stato" // ,
                          // "esitoVerifica",
                          // "uri"
                        }));

            String contentPanelString = "";
            QuadroTributarioExt dettaglioPerBottone = null;
            Boolean hasDettagli = false;
            // Get last, per ora va bene perche uno per anno
            for (QuadroTributarioExt dettaglio : itemModel.getDettaglioExt()) {
              contentPanelString +=
                  getStringContentPanel(
                      dettaglio, fields, dettaglio.getAnno() + itemModel.getTributo()); // " +
              // "<hr>";
              dettaglio.setNomeTributo(itemModel.getTributo());
              dettaglioPerBottone = dettaglio;
            }

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIcona(dettaglioPerBottone));
            item.add(icona);

            item.add(createContentPanel(contentPanelString));

            // fare tutta la cosa dentro il metodo, idd and visibility
            LaddaAjaxLink<Object> bottoneDettagli = creaBottoneDettagli(dettaglioPerBottone);
            hasDettagli = dettaglioPerBottone.hasValidUri();
            if (bottoneDettagli != null) {
              item.addOrReplace(bottoneDettagli);
              bottoneDettagli.setVisible(hasDettagli);
            }

            item.addOrReplace(createLinkEsternoCalcola("urlEsternoCalcolatrice"));
          }
        };

    add(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(listaTributi.size() > 4);
    add(paginazioneFascicolo);
  }

  @Override
  protected String getBaseIcon(Object t) {
    QuadroTributarioExt elemento = (QuadroTributarioExt) t;
    if (elemento.getNomeTributo() == "IMU")
      return BasePanelGenericContent.ALL_ICON_IOCON_QUA_TRIB_IMU;
    return BasePanelGenericContent.ALL_ICON_IOCON_QUA_TRIB_TARI;
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    QuadroTributarioExt elemento = (QuadroTributarioExt) t;
    String css = elemento.getColorForIcon();
    css += getBaseIcon(t);
    return new AttributeAppender("class", " " + css);
  }

  @Override
  protected <T> LaddaAjaxLink<Object> creaBottoneDettagli(T oggetto) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("bottoneDettagli", Type.Primary) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(this);
            DettaglioQuadroTributarioPage page =
                new DettaglioQuadroTributarioPage(
                    getUtente(),
                    ((QuadroTributarioExt) oggetto).getUri(),
                    getCssIcona(oggetto),
                    annoScelto);
            setResponsePage(page);
          }
        };

    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("QuadroTributarioPanel.bottoneDettagli", this)));

    btnDettagli.setOutputMarkupId(true);

    return btnDettagli;
  }
}
