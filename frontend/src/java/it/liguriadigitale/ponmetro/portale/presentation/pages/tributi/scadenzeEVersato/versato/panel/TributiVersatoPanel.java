package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.versato.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.ElencoVersamentiExt;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.TributiVersamentiExt;
import it.liguriadigitale.ponmetro.portale.presentation.application.HomeWebApplication;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.quadroTributario.dettaglio.DettaglioQuadroTributarioPage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class TributiVersatoPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = -4276599417139686147L;

  private Integer annoIesimo;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public TributiVersatoPanel(String id, Integer annoIesimo) {
    super(id);
    this.annoIesimo = annoIesimo;
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  protected String getBaseLabelKey() {
    return StringUtils.capitalize(
            HomeWebApplication.ID_TRIBVERSATO_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME)
        + HomeWebApplication.ID_SUFFIX_PANEL_WITH_DOT;
  }

  @Override
  public void fillDati(Object dati) {
    ElencoVersamentiExt tDati = (ElencoVersamentiExt) dati;

    List<TributiVersamentiExt> list =
        tDati == null ? new ArrayList<TributiVersamentiExt>() : tDati.getVersamentiExt();

    Label listaVuota = new Label("listaVuota", getString(getBaseLabelKey() + "listaVuota"));
    listaVuota.setVisible(list.isEmpty());
    add(listaVuota);

    setLarghezzaPrimaColonna(6);

    PageableListView<TributiVersamentiExt> listView =
        new PageableListView<TributiVersamentiExt>("box", list, 8) {

          private static final long serialVersionUID = 1937027489230651444L;

          @Override
          protected void populateItem(ListItem<TributiVersamentiExt> item) {
            final TributiVersamentiExt itemModel = item.getModelObject();

            addFieldString(itemModel, "titolo", item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          "annoRiferimento",
                          "dataVersamento",
                          "importoVersatoL",
                          "rata",
                          "dataScadenzaRata",
                          "modalitaPagamento",
                          "documentoEmesso",
                          "verificato"
                          // "uriUtenza"
                        }));

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIcona(itemModel));
            item.add(icona);
            item.addOrReplace(createContentPanel(itemModel, fields));

            // fare tutta la cosa dentro il metodo, idd and visibility
            LaddaAjaxLink<Object> bottoneDettagli = creaBottoneDettagli(itemModel);
            item.addOrReplace(bottoneDettagli);
            bottoneDettagli.setVisible(itemModel.getFlagIsVerificato());
          }
        };

    add(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(list.size() > 4);
    add(paginazioneFascicolo);
  }

  @Override
  protected String getBaseIcon() {
    return BasePanelGenericContent.ALL_ICON_IOCON_VERSATO;
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    TributiVersamentiExt elemento = (TributiVersamentiExt) t;
    String css = elemento.getColorForIcon();
    css += getBaseIcon();
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
                    HomeWebApplication
                        .ID_DETTAGLIO_OFTRIBVERSATO_OFSCADENZEEVERSATO_OFTRIBUTI_OFHOME,
                    ((TributiVersamentiExt) oggetto).getUriUtenza(),
                    getCssIcona(oggetto),
                    annoIesimo);
            setResponsePage(page);
          }
        };

    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("Common.bottoneDettagli", this)));

    btnDettagli.setOutputMarkupId(true);

    return btnDettagli;
  }
}
