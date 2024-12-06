package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.DettaglioPermessiPersonalizzatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.DomandaResponseComparator;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoPersona;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class ElencoPermessiPersonalizzatiPanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  private static final String ICON_STATO_ROSSO = "color-fc-danger col-auto icon-accessibilita";

  private static final String ICON_STATO_VERDE = "color-fc-success col-auto icon-accessibilita";

  private static final String ICON_STATO_GIALLO = "color-fc-warning col-auto icon-accessibilita";

  private static final String ICON_STATO_NERO = "color-black col-auto icon-accessibilita";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  HashMap<String, TipologiaProcedimento> hsmTipologieProcedimento;

  public ElencoPermessiPersonalizzatiPanel(String id) {
    super(id);

    hsmTipologieProcedimento = new HashMap<String, TipologiaProcedimento>();

    List<TipologiaProcedimento> listaTipologieProcedimento;
    try {

      listaTipologieProcedimento =
          ServiceLocator.getInstance()
              .getServiziPermessiPersonalizzati()
              .getTipologieProcedimento();
      setOutputMarkupId(true);

      for (Iterator<TipologiaProcedimento> iterator = listaTipologieProcedimento.iterator();
          iterator.hasNext(); ) {
        TipologiaProcedimento tipologiaProcedimento = iterator.next();
        hsmTipologieProcedimento.put(tipologiaProcedimento.getCodice(), tipologiaProcedimento);
      }

    } catch (BusinessException | ApiException | IOException e) {
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco tipologie procedimento"));
    }

    try {
      List<DomandaResponse> lista =
          ServiceLocator.getInstance()
              .getServiziPermessiPersonalizzati()
              .getDomande(getUtente().getCodiceFiscaleOperatore());

      Collections.sort(lista, new DomandaResponseComparator());

      setOutputMarkupId(true);
      fillDati(lista);
      createFeedBackPanel();

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco permessi personalizzati"));
    }
  }

  public ElencoPermessiPersonalizzatiPanel(String id, List<CertificatoPersona> lista) {
    this(id);
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DomandaResponse> lista = (List<DomandaResponse>) dati;

    mostraLista(lista);
  }

  private void mostraLista(List<DomandaResponse> lista) {

    PageableListView<DomandaResponse> listView =
        new PageableListView<DomandaResponse>("lista", lista, 4) {

          private static final long serialVersionUID = -593847571381978380L;

          @Override
          protected void populateItem(ListItem<DomandaResponse> item) {
            DomandaResponse domandaResponse = item.getModelObject();
            log.debug("idDomanda= " + domandaResponse.getIdDomanda());

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaDomanda(domandaResponse));
            item.addOrReplace(icona);

            NotEmptyLabel stato =
                new NotEmptyLabel("stato", domandaResponse.getStatoProcedimento());
            stato.setVisible(false);
            item.addOrReplace(stato);
            item.add(
                new CardLabel<>(
                    "idDomanda",
                    domandaResponse.getIdDomanda(),
                    ElencoPermessiPersonalizzatiPanel.this));
            item.add(
                new CardLabel<>(
                    "descrizioneProcedimento",
                    hsmTipologieProcedimento
                        .get(domandaResponse.getProcedimento())
                        .getDescrizione(),
                    ElencoPermessiPersonalizzatiPanel.this));

            String statoDomanda = "";
            if (domandaResponse.getStatoProcedimento() != null) {
              statoDomanda = domandaResponse.getStatoProcedimento().getDescrizione();
            }
            item.add(
                new CardLabel<>(
                    "descrizioneStato", statoDomanda, ElencoPermessiPersonalizzatiPanel.this));

            item.add(
                creaBtnDettaglio(
                    domandaResponse.getIdDomanda(),
                    hsmTipologieProcedimento
                        .get(domandaResponse.getProcedimento())
                        .getDescrizione()));
          }
        };
    listView.setRenderBodyOnly(true);
    listView.setVisible(!lista.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(lista.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (lista.isEmpty()) {
      warn("Nessun permesso personalizzato trovato");
    }
  }

  private LaddaAjaxLink<Object> creaBtnDettaglio(int idDomanda, String descrizioneProcedimento) {
    LaddaAjaxLink<Object> btnDettaglio =
        new LaddaAjaxLink<Object>("btnDettaglio", Type.Primary) {

          private static final long serialVersionUID = 5354813348751268136L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(
                new DettaglioPermessiPersonalizzatiPage(idDomanda, descrizioneProcedimento));
          }
        };

    btnDettaglio.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "ElencoPermessiPersonalizzatiPanel.btnDettaglio",
                    ElencoPermessiPersonalizzatiPanel.this)));

    //		IconType iconType = new IconType("btnNuovaRichiestaPermesso") {
    //
    //			private static final long serialVersionUID = 8404184190966468972L;
    //
    //			@Override
    //			public String cssClassName() {
    //				return "icon-accessibilita";
    //			}
    //		};
    //		btnDettaglio.setIconType(iconType);

    return btnDettaglio;
  }

  // 1 per in compilazione, 2 per presentato, 3 per accettato, 4 per sospeso, 5
  // per rifiutato

  private AttributeAppender getCssIconaDomanda(DomandaResponse domandaResponse) {

    /*
     * for (StatoProcedimento statoProcedimento : listaStatiProcedimento) {
     *
     *
     * switch (statoProcedimento.getId()) { case 1: {
     *
     * yield type; } default: throw new
     * IllegalArgumentException("Unexpected value: " + key); }
     *
     *
     * }
     */

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
