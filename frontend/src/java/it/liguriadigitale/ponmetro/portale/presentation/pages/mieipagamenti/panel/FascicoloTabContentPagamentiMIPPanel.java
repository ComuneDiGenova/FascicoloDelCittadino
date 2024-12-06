package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicolo;
import it.liguriadigitale.ponmetro.portale.pojo.mipDebito.DebitoMipFascicoloDatiGenerali;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.icon.LdIconType;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class FascicoloTabContentPagamentiMIPPanel extends BasePanel {

  private static final long serialVersionUID = 8537057305186590778L;

  private static final String ICON_CARTA = "color-red col-3 icon-card";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private boolean visibilitaDettaglio = false;

  private Map<String, Boolean> mapVisibleDettagli;

  public FascicoloTabContentPagamentiMIPPanel(String id) {
    super(id);

    mapVisibleDettagli = new HashMap<String, Boolean>();

    setOutputMarkupId(true);
  }

  public FascicoloTabContentPagamentiMIPPanel(
      List<DebitoMipFascicoloDatiGenerali> listaDebitiPerServizio) {
    super("tabsPanel");

    fillDati(listaDebitiPerServizio);

    mapVisibleDettagli = new HashMap<String, Boolean>();

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public FascicoloTabContentPagamentiMIPPanel(
      String id, List<DebitoMipFascicoloDatiGenerali> listaDebitiDatiGenerali) {
    super(id);

    fillDati(listaDebitiDatiGenerali);

    mapVisibleDettagli = new HashMap<String, Boolean>();

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DebitoMipFascicoloDatiGenerali> listaPagamentiFascicolo =
        (List<DebitoMipFascicoloDatiGenerali>) dati;

    WebMarkupContainer listaDebitiVuota = new WebMarkupContainer("listaDebitiVuota");
    listaDebitiVuota.setVisible(listaPagamentiFascicolo.isEmpty());
    addOrReplace(listaDebitiVuota);

    WebMarkupContainer containerMessaggiInfo = new WebMarkupContainer("containerMessaggiInfo");
    containerMessaggiInfo.setVisible(!listaPagamentiFascicolo.isEmpty());
    addOrReplace(containerMessaggiInfo);

    PageableListView<DebitoMipFascicoloDatiGenerali> listView =
        new PageableListView<DebitoMipFascicoloDatiGenerali>("box", listaPagamentiFascicolo, 4) {

          private static final long serialVersionUID = -8872377358303747373L;

          @Override
          protected void populateItem(ListItem<DebitoMipFascicoloDatiGenerali> item) {
            DebitoMipFascicoloDatiGenerali debitoMipFascicoloDatiGenerali = item.getModelObject();

            item.setOutputMarkupId(true);

            int indice = item.getIndex();
            item.setMarkupId("boxCard" + indice);

            AttributeAppender attributeAppender = new AttributeAppender("class", " " + ICON_CARTA);
            WebMarkupContainer icona = new WebMarkupContainer("icona");
            AttributeModifier coloreIconaEsitoOK = new AttributeModifier("style", "color: #008758");
            AttributeModifier coloreIconaEsitoKO = new AttributeModifier("style", "color: #d9364f");
            if (debitoMipFascicoloDatiGenerali != null
                && debitoMipFascicoloDatiGenerali.isEsitoDebito()) {
              icona.add(coloreIconaEsitoOK);
            } else if (debitoMipFascicoloDatiGenerali.getIuv() != null
                && debitoMipFascicoloDatiGenerali.getIuv().equalsIgnoreCase("-")) {
              if (debitoMipFascicoloDatiGenerali.getImportoDaPagare() != null
                  && debitoMipFascicoloDatiGenerali.getImportoDaPagare() == 0.0) {
                icona.add(coloreIconaEsitoOK);
              } else {
                if (debitoMipFascicoloDatiGenerali.isEsitoDebito()) {
                  icona.add(coloreIconaEsitoOK);
                } else {
                  icona.add(coloreIconaEsitoKO);
                }
              }
            } else {
              icona.add(coloreIconaEsitoKO);
            }
            icona.add(attributeAppender);
            item.addOrReplace(icona);

            Label servizio =
                new Label(
                    "servizio",
                    getDescrizioneServizio(debitoMipFascicoloDatiGenerali.getServizio()));
            servizio.setVisible(
                PageUtil.isStringValid(debitoMipFascicoloDatiGenerali.getServizio()));
            item.addOrReplace(servizio);

            Label numeroDocumento =
                new Label("numeroDocumento", debitoMipFascicoloDatiGenerali.getNumeroDebito());
            numeroDocumento.setVisible(
                PageUtil.isStringValid(debitoMipFascicoloDatiGenerali.getNumeroDebito()));
            item.addOrReplace(numeroDocumento);

            Label anno = new Label("anno", debitoMipFascicoloDatiGenerali.getAnno());
            anno.setVisible(
                debitoMipFascicoloDatiGenerali.getAnno() != null
                    && !debitoMipFascicoloDatiGenerali
                        .getServizio()
                        .equalsIgnoreCase("SCUOLA_RISTO"));
            item.addOrReplace(anno);

            Long annoScolasticoValue = Long.sum(debitoMipFascicoloDatiGenerali.getAnno(), 1);
            String as =
                String.valueOf(debitoMipFascicoloDatiGenerali.getAnno())
                    .concat("/")
                    .concat(String.valueOf(annoScolasticoValue));
            Label annoScolastico = new Label("annoScolastico", as);
            annoScolastico.setVisible(
                debitoMipFascicoloDatiGenerali.getAnno() != null
                    && debitoMipFascicoloDatiGenerali
                        .getServizio()
                        .equalsIgnoreCase("SCUOLA_RISTO"));
            item.addOrReplace(annoScolastico);

            Label debitore = new Label("debitore", debitoMipFascicoloDatiGenerali.getDebitore());
            debitore.setVisible(
                PageUtil.isStringValid(debitoMipFascicoloDatiGenerali.getDebitore()));
            item.addOrReplace(debitore);

            Label causale = new Label("causale", debitoMipFascicoloDatiGenerali.getCausale());
            causale.setVisible(PageUtil.isStringValid(debitoMipFascicoloDatiGenerali.getCausale()));
            item.addOrReplace(causale);

            LocalDate dataCreazioneLocalDate = null;
            if (debitoMipFascicoloDatiGenerali.getDataCreazione() != null) {
              dataCreazioneLocalDate =
                  LocalDateUtil.getLocalDateFromOffsetDateTime(
                      debitoMipFascicoloDatiGenerali.getDataCreazione());
            }
            String dataCreazioneValue = "";
            if (dataCreazioneLocalDate != null) {
              dataCreazioneValue = LocalDateUtil.getDataFormatoEuropeo(dataCreazioneLocalDate);
            }
            Label dataCreazione = new Label("dataCreazione", dataCreazioneValue);
            dataCreazione.setVisible(debitoMipFascicoloDatiGenerali.getDataCreazione() != null);
            item.addOrReplace(dataCreazione);

            RateMipPanel rateMipPanel = new RateMipPanel("rateMipPanel");
            rateMipPanel.setVisible(false);
            item.addOrReplace(rateMipPanel);

            mapVisibleDettagli.put(debitoMipFascicoloDatiGenerali.getNumeroDebito(), false);

            LaddaAjaxLink<String> btnDettagliDebito =
                new LaddaAjaxLink<String>("btnDettagliDebito", Type.Primary) {

                  private static final long serialVersionUID = -2068680195381528805L;

                  @Override
                  public void onClick(AjaxRequestTarget target) {

                    DebitoMipFascicolo debitoMipFascicolo =
                        getDebitoMipFascicolo(debitoMipFascicoloDatiGenerali);
                    debitoMipFascicolo.setIndex(indice);
                    rateMipPanel.fillDati(debitoMipFascicolo);
                    item.addOrReplace(rateMipPanel);

                    for (String key : mapVisibleDettagli.keySet()) {
                      if (key.equalsIgnoreCase(debitoMipFascicolo.getNumeroDebito())) {
                        if (mapVisibleDettagli.get(key)) {
                          mapVisibleDettagli.replace(debitoMipFascicolo.getNumeroDebito(), false);
                        } else {
                          mapVisibleDettagli.replace(debitoMipFascicolo.getNumeroDebito(), true);
                        }
                      }
                    }

                    visibilitaDettaglio =
                        mapVisibleDettagli.get(debitoMipFascicolo.getNumeroDebito());
                    setVisible(true);
                    rateMipPanel.setVisible(visibilitaDettaglio);
                    target.add(item);
                  }
                };
            btnDettagliDebito.setIconType(new LdIconType("icon-tributi mx-1"));
            btnDettagliDebito.setLabel(
                Model.of(
                    Application.get()
                        .getResourceSettings()
                        .getLocalizer()
                        .getString(
                            "FascicoloTabContentPagamentiMIPPanel.dettagli",
                            FascicoloTabContentPagamentiMIPPanel.this)));

            btnDettagliDebito.setOutputMarkupId(true);
            btnDettagliDebito.setOutputMarkupPlaceholderTag(true);
            btnDettagliDebito.setMarkupId("dettagli" + indice);

            btnDettagliDebito.setVisible(
                debitoMipFascicoloDatiGenerali.getIuv() != null
                    && !debitoMipFascicoloDatiGenerali.getIuv().equalsIgnoreCase("-"));

            item.addOrReplace(btnDettagliDebito);
          }
        };

    listView.setVisible(!listaPagamentiFascicolo.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationPagamenti", listView);
    paginazioneFascicolo.setVisible(listaPagamentiFascicolo.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  public String getDescrizioneServizio(String servizio) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getDescrizioneServizio(getUtente(), servizio);
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }

  private DebitoMipFascicolo getDebitoMipFascicolo(DebitoMipFascicoloDatiGenerali debitoGenerale) {
    try {
      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getDebitoMipFascicolo(getUtente(), debitoGenerale);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }
}
