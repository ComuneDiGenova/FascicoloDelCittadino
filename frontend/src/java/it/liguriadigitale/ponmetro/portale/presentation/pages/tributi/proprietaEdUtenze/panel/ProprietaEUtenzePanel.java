package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.ProprietaUtenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.DettaglioProprietaPage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class ProprietaEUtenzePanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 6904564753259611327L;

  public static final String ICON_AUTORIMESSA = "color-green col-3 icon-palazzo_strada";
  public static final String ICON_ABITAZIONE = "color-green col-3 icon-casa";
  public static final String ICON_TERRENO = "color-green col-3 icon-natura";
  public static final String ICON_NEGOZIO = "color-green col-3 icon-porta";
  public static final String ICON_ALTRO = "color-green col-3 icon-case";
  public static final String ICON_CATASTALI = "color-green col-3 icon-foglio";

  private static final String BASE_LABEL_KEY = "ProprietaEUtenzePanel.";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  private Integer annoScelto;

  public ProprietaEUtenzePanel(String id, Integer annoScelto) {
    super(id);
    this.annoScelto = annoScelto;
    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    List<ProprietaUtenzeExt> listaProprietaUtenze = (List<ProprietaUtenzeExt>) dati;

    Label listaProprietaUtenzeVuota =
        new Label("listaProprietaUtenzeVuota", getString(BASE_LABEL_KEY + "listaVuota"));
    listaProprietaUtenzeVuota.setVisible(
        listaProprietaUtenze == null || listaProprietaUtenze.isEmpty());
    add(listaProprietaUtenzeVuota);

    PageableListView<ProprietaUtenzeExt> listView =
        new PageableListView<ProprietaUtenzeExt>("box", listaProprietaUtenze, 4) {

          private static final long serialVersionUID = 3042186948160882101L;

          @Override
          protected void populateItem(ListItem<ProprietaUtenzeExt> item) {
            final ProprietaUtenzeExt proprieta = item.getModelObject();

            addIconaETitolo(proprieta, item);

            List<String> fields =
                new ArrayList<String>(
                    Arrays.asList(
                        new String[] {
                          // "codiceOggettoAnagrafico",
                          // "informazioniCatastali",
                          "indirizzoCompleto",
                          "tipoOggetto",
                          "tipoOggettoGenerico",
                          "categoria",
                          "classe",
                          "consistenza",
                          "zonaCensuaria"
                          // "residenza"
                        }));
            Integer annoCorrente = LocalDate.now().getYear();
            if (annoCorrente.equals(annoScelto)) {
              fields.add("residenza");
            }
            item.addOrReplace(createContentPanel(proprieta, fields));

            SoggettoAPanel<ProprietaUtenzeExt> soggettoAPanel =
                new SoggettoAPanel<ProprietaUtenzeExt>(
                    "soggettoAPanel", proprieta, proprieta.getTributi(), null);
            // Vedi JIRA FDCOMGE-81
            soggettoAPanel.setVisible(false);
            item.addOrReplace(soggettoAPanel);

            LaddaAjaxLink<Object> bottoneDettagli = creaBottoneDettagli(proprieta);
            if (bottoneDettagli != null) {
              item.addOrReplace(bottoneDettagli);
            }
          }
        };

    add(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        listaProprietaUtenze != null && listaProprietaUtenze.size() > 4);
    add(paginazioneFascicolo);
  }

  @Override
  protected <T> LaddaAjaxLink<Object> creaBottoneDettagli(T oggetto) {
    LaddaAjaxLink<Object> btnDettagli =
        new LaddaAjaxLink<Object>("bottoneDettagli", Type.Primary) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(this);
            DettaglioProprietaPage page =
                new DettaglioProprietaPage(
                    getUtente(), (ProprietaUtenzeExt) oggetto, getCssIcona(oggetto), annoScelto);
            setResponsePage(page);
          }
        };

    btnDettagli.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ProprietaEUtenzePanel.bottoneDettagli", this)));

    btnDettagli.setOutputMarkupId(true);

    return btnDettagli;
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    ProprietaUtenzeExt proprietaUtenze = (ProprietaUtenzeExt) t;
    String css = "";

    if (proprietaUtenze.isTerreno()) {
      css = ICON_TERRENO;
    } else if (proprietaUtenze.isAbitazione()) {
      css = ICON_ABITAZIONE;
    } else if (proprietaUtenze.isGarage()) {
      css = ICON_AUTORIMESSA;
    } else if (proprietaUtenze.isNegozio()) {
      css = ICON_NEGOZIO;
    } else {
      css = ICON_ALTRO;
    }

    return new AttributeAppender("class", " " + css);
  }
}
