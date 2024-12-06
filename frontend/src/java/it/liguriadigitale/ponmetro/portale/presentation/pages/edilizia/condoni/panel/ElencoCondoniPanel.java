package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.edilizia.condono.model.CondonoResponse;
import it.liguriadigitale.ponmetro.edilizia.condono.model.Indirizzo;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.FdCMultiLineLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.condoni.dettaglio.DettaglioCondoniPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class ElencoCondoniPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  private static final String ICON_STATO_ROSSO = "color-fc-danger col-auto icon-accessibilita";

  private static final String ICON_STATO_VERDE = "color-fc-success col-auto icon-accessibilita";

  private static final String ICON_STATO_GIALLO = "color-fc-warning col-auto icon-accessibilita";

  private static final String ICON_STATO_NERO = "color-black col-auto icon-accessibilita";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  //	HashMap<String, TipologiaProcedimento> hsmTipologieProcedimento;

  /*
   * public ElencoCondoniPanel(String id) { super(id);
   *
   *
   * hsmTipologieProcedimento = new HashMap<String, TipologiaProcedimento>();
   *
   * List<TipologiaProcedimento> listaTipologieProcedimento; try {
   *
   *
   * listaTipologieProcedimento =
   * ServiceLocator.getInstance().getServiziPermessiPersonalizzati()
   * .getTipologieProcedimento(); setOutputMarkupId(true);
   *
   * for (Iterator<TipologiaProcedimento> iterator =
   * listaTipologieProcedimento.iterator(); iterator .hasNext();) {
   * TipologiaProcedimento tipologiaProcedimento = (TipologiaProcedimento)
   * iterator.next();
   * hsmTipologieProcedimento.put(tipologiaProcedimento.getCodice(),
   * tipologiaProcedimento); }
   *
   * } catch (BusinessException | ApiException | IOException e) { throw new
   * RestartResponseAtInterceptPageException(new
   * ErroreServiziPage("elenco tipologie procedimento")); }
   *
   * try { List<DomandaResponse> lista =
   * ServiceLocator.getInstance().getServiziPermessiPersonalizzati()
   * .getDomande(getUtente().getCodiceFiscaleOperatore());
   *
   * Collections.sort(lista, new DomandaResponseComparator());
   *
   * setOutputMarkupId(true); fillDati(lista); createFeedBackPanel();
   *
   * } catch (BusinessException | ApiException | IOException e) {
   * log.debug("Errore durante la chiamata delle API", e); throw new
   * RestartResponseAtInterceptPageException(new
   * ErroreServiziPage("elenco permessi personalizzati")); }
   *
   *
   * }
   */

  public ElencoCondoniPanel(String id, List<CondonoResponse> lista) {
    super(id);
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<CondonoResponse> lista = (List<CondonoResponse>) dati;

    mostraLista(lista);
  }

  private void mostraLista(List<CondonoResponse> lista) {

    PageableListView<CondonoResponse> listView =
        new PageableListView<CondonoResponse>("lista", lista, 4) {

          private static final long serialVersionUID = -593847571381978380L;

          @Override
          protected void populateItem(ListItem<CondonoResponse> item) {
            CondonoResponse domandaResponse = item.getModelObject();
            log.debug("numero pratica = " + domandaResponse.getNumeroPratica());

            item.addOrReplace(
                new CardLabel<>(
                    "statoPratica", domandaResponse.getStatoPratica(), ElencoCondoniPanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "numeroPratica", domandaResponse.getNumeroPratica(), ElencoCondoniPanel.this));

            item.addOrReplace(
                new CardLabel<>(
                    "annoPratica", domandaResponse.getAnnoPratica(), ElencoCondoniPanel.this));

            item.addOrReplace(
                new CardLabel<>("recId", domandaResponse.getRecId(), ElencoCondoniPanel.this)
                    .setVisible(false));

            List<Indirizzo> indirizzi = domandaResponse.getIndirizzi();
            List<String> listaIndirizzi = new ArrayList<>();
            for (Indirizzo indirizzo : indirizzi) {
              String strIndirizzo = "";
              if (indirizzo != null) {
                if (indirizzo.getVia() != null) {
                  strIndirizzo = strIndirizzo.concat(indirizzo.getVia() + " ");
                }

                if (indirizzo.getCivico() != null) {
                  strIndirizzo = strIndirizzo.concat(indirizzo.getCivico() + " ");
                }

                if (indirizzo.getInterno() != null) {
                  strIndirizzo = strIndirizzo.concat(indirizzo.getInterno() + " ");
                }
              }

              listaIndirizzi.add(strIndirizzo);
            }

            @SuppressWarnings("unchecked")
            FdCMultiLineLabel fdCMultiLineLabel =
                new FdCMultiLineLabel("listaIndirizzi", listaIndirizzi, ElencoCondoniPanel.this);
            item.add(fdCMultiLineLabel);

            item.addOrReplace(
                creaBtnDettaglio(
                    domandaResponse.getNumeroPratica(), domandaResponse.getAnnoPratica()));

            /*
             *
             * WebMarkupContainer icona = new WebMarkupContainer("icona");
             * icona.add(getCssIconaDomanda(domandaResponse)); item.addOrReplace(icona);
             *
             * NotEmptyLabel stato = new NotEmptyLabel("stato",
             * domandaResponse.getStatoProcedimento()); stato.setVisible(false);
             * item.addOrReplace(stato); item.add(new CardLabel<>("idDomanda",
             * domandaResponse.getIdDomanda(), ElencoCondoniPanel.this));
             *
             *
             * String statoDomanda = ""; if (domandaResponse.getStatoProcedimento() != null)
             * { statoDomanda = domandaResponse.getStatoProcedimento().getDescrizione(); }
             * item.add(new CardLabel<>("descrizioneStato", statoDomanda,
             * ElencoCondoniPanel.this));
             */

          }
        };
    listView.setRenderBodyOnly(true);
    listView.setVisible(checkListaCondoniPiena(lista));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkListaCondoniPiena(lista) && lista.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (!checkListaCondoniPiena(lista)) {
      warn("Nessun condono trovato");
    }
  }

  private boolean checkListaCondoniPiena(List<CondonoResponse> lista) {
    return LabelFdCUtil.checkIfNotNull(lista) && !LabelFdCUtil.checkEmptyList(lista);
  }

  private LaddaAjaxLink<Object> creaBtnDettaglio(int numeroPratica, int annoPratica) {
    LaddaAjaxLink<Object> btnDettaglio =
        new LaddaAjaxLink<Object>("btnDettaglio", Type.Primary) {

          private static final long serialVersionUID = 5354813348751268136L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new DettaglioCondoniPage(numeroPratica, annoPratica));
          }
        };

    btnDettaglio.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("ElencoCondoniPanel.btnDettaglio", ElencoCondoniPanel.this)));

    return btnDettaglio;
  }

  // 1 per in compilazione, 2 per presentato, 3 per accettato, 4 per sospeso, 5
  // per rifiutato

  private AttributeAppender getCssIconaDomanda(DomandaResponse domandaResponse) {

    String css = "";
    if (domandaResponse != null
        && domandaResponse.getStatoProcedimento() != null
        && domandaResponse.getStatoProcedimento().getCodice() != null) {
      if (domandaResponse.getStatoProcedimento().getCodice() == 3
          || domandaResponse.getStatoProcedimento().getCodice() == 4
          || domandaResponse.getStatoProcedimento().getCodice() == 5) {
        css = ICON_STATO_GIALLO;
      } else if (domandaResponse.getStatoProcedimento().getCodice() == 1
          || domandaResponse.getStatoProcedimento().getCodice() == 2
          || domandaResponse.getStatoProcedimento().getCodice() == 9) {
        css = ICON_STATO_VERDE;
      } else if (domandaResponse.getStatoProcedimento().getCodice() == 0
          || domandaResponse.getStatoProcedimento().getCodice() == 6
          || domandaResponse.getStatoProcedimento().getCodice() == 7
          || domandaResponse.getStatoProcedimento().getCodice() == 8) {
        css = ICON_STATO_ROSSO;
      } else {
        css = ICON_STATO_NERO;
      }
    } else {
      css = ICON_STATO_NERO;
    }

    return new AttributeAppender("class", " " + css);
  }
}
