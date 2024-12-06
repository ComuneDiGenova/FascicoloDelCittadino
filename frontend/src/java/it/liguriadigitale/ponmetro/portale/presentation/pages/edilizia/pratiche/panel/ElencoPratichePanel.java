package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.pratiche.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.PraticaSingola;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.pratiche.dettaglio.DettaglioPraticheEdiliziePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class ElencoPratichePanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ElencoPratichePanel(String id, List<PraticaSingola> elencoPratiche) {
    super(id);
    setOutputMarkupId(true);
    fillDati(elencoPratiche);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<PraticaSingola> lista = (List<PraticaSingola>) dati;

    mostraLista(lista);
  }

  private void mostraLista(List<PraticaSingola> lista) {

    PageableListView<PraticaSingola> listView =
        new PageableListView<PraticaSingola>("lista", lista, 4) {

          private static final long serialVersionUID = -593847571381978380L;

          @Override
          protected void populateItem(ListItem<PraticaSingola> item) {
            PraticaSingola domandaResponse = item.getModelObject();

            log.debug("numero pratica = " + domandaResponse.getPRANPROTNUMERO());

            item.addOrReplace(
                new CardLabel<>(
                    "PRAVAPROTCODTIPOPROTOCOLLO",
                    domandaResponse.getPRAVAPROTCODTIPOPROTOCOLLO(),
                    ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRANPROTNUMERO",
                    domandaResponse.getPRANPROTNUMERO(),
                    ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRANPROTANNO", domandaResponse.getPRANPROTANNO(), ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "TPAADESCRPRATICA",
                    domandaResponse.getTPAADESCRPRATICA(),
                    ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRAVAOGGETTO", domandaResponse.getPRAVAOGGETTO(), ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRADPROTRICHIESTA",
                    domandaResponse.getPRADPROTRICHIESTA(),
                    ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRADPROTINGRESSOCOMUNE",
                    domandaResponse.getPRADPROTINGRESSOCOMUNE(),
                    ElencoPratichePanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "PRADPROTIMMISSIONE",
                    domandaResponse.getPRADPROTIMMISSIONE(),
                    ElencoPratichePanel.this));

            String pravaPraticaChiusaValue = "";
            if (PageUtil.isStringValid(domandaResponse.getPRAVAPRATICACHIUSA())) {
              if (domandaResponse.getPRAVAPRATICACHIUSA().equalsIgnoreCase("S")) {
                pravaPraticaChiusaValue = "Chiusa";
              }
              if (domandaResponse.getPRAVAPRATICACHIUSA().equalsIgnoreCase("N")) {
                pravaPraticaChiusaValue = "In lavorazione";
              }
            }

            item.addOrReplace(
                new CardLabel<>(
                    "PRAVAPRATICACHIUSA", pravaPraticaChiusaValue, ElencoPratichePanel.this));

            item.addOrReplace(creaBtnDettaglio(domandaResponse));
          }
        };
    listView.setRenderBodyOnly(true);
    listView.setVisible(checkListaPratichePiena(lista));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkListaPratichePiena(lista) && lista.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (!checkListaPratichePiena(lista)) {
      warn("Nessun condono trovato");
    }
  }

  private boolean checkListaPratichePiena(List<PraticaSingola> lista) {
    return LabelFdCUtil.checkIfNotNull(lista) && !LabelFdCUtil.checkEmptyList(lista);
  }

  private LaddaAjaxLink<Object> creaBtnDettaglio(PraticaSingola praticaSingola) {
    LaddaAjaxLink<Object> btnDettaglio =
        new LaddaAjaxLink<Object>("btnDettaglio", Type.Primary) {

          private static final long serialVersionUID = 5354813348751268136L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new DettaglioPraticheEdiliziePage(praticaSingola));
          }
        };

    btnDettaglio.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ElencoPratichePanel.btnDettaglio", ElencoPratichePanel.this)));

    return btnDettaglio;
  }

  // 1 per in compilazione, 2 per presentato, 3 per accettato, 4 per sospeso, 5
  // per rifiutato

  // private AttributeAppender getCssIconaDomanda(DomandaResponse domandaResponse)
  // {
  //
  // String css = "";
  // if (domandaResponse != null && domandaResponse.getStatoProcedimento() != null
  // && domandaResponse.getStatoProcedimento().getCodice() != null) {
  // if (domandaResponse.getStatoProcedimento().getCodice() == 3
  // || domandaResponse.getStatoProcedimento().getCodice() == 4
  // || domandaResponse.getStatoProcedimento().getCodice() == 5) {
  // css = ICON_STATO_GIALLO;
  // } else if (domandaResponse.getStatoProcedimento().getCodice() == 1
  // || domandaResponse.getStatoProcedimento().getCodice() == 2
  // || domandaResponse.getStatoProcedimento().getCodice() == 9) {
  // css = ICON_STATO_VERDE;
  // } else if (domandaResponse.getStatoProcedimento().getCodice() == 0
  // || domandaResponse.getStatoProcedimento().getCodice() == 6
  // || domandaResponse.getStatoProcedimento().getCodice() == 7
  // || domandaResponse.getStatoProcedimento().getCodice() == 8) {
  // css = ICON_STATO_ROSSO;
  // } else {
  // css = ICON_STATO_NERO;
  // }
  // } else {
  // css = ICON_STATO_NERO;
  // }
  //
  // return new AttributeAppender("class", " " + css);
  // }

}
