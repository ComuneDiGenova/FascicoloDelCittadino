package it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.geoworks.model.RequestFormsListDto;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class GeoworksPanel extends BasePanel {

  private static final long serialVersionUID = 420667632366892394L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private GeoworksFunzioni funzione;

  private List<GeoworksServizi> listaServizi;

  public GeoworksPanel(String id, GeoworksFunzioni funzione, List<GeoworksServizi> listaServizi) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    this.funzione = funzione;
    this.listaServizi = listaServizi;

    List<RequestFormsListDto> lista =
        popolaLista(getUtente().getCodiceFiscaleOperatore(), funzione, listaServizi);
    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    String titoloPratica = getTitolo(this.funzione);
    NotEmptyLabel titolo =
        new NotEmptyLabel(
            "titolo",
            new StringResourceModel("GeoworksPanel.titolo", this).setParameters(titoloPratica));
    titolo.setVisible(PageUtil.isStringValid(titoloPratica));
    addOrReplace(titolo);

    List<RequestFormsListDto> listaPratiche = (List<RequestFormsListDto>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setOutputMarkupId(true);
    listaVuota.setOutputMarkupPlaceholderTag(true);
    NotEmptyLabel messaggioListaVuota =
        new NotEmptyLabel(
            "messaggioListaVuota",
            new StringResourceModel("GeoworksPanel.listaVuota", this).setParameters(titoloPratica));
    titolo.setVisible(PageUtil.isStringValid(titoloPratica));
    listaVuota.addOrReplace(messaggioListaVuota);
    listaVuota.setVisible(!checkPratiche(listaPratiche));
    addOrReplace(listaVuota);

    PageableListView<RequestFormsListDto> listView =
        new PageableListView<RequestFormsListDto>("pratiche", listaPratiche, 4) {

          private static final long serialVersionUID = -8280581080836259284L;

          @Override
          protected void populateItem(ListItem<RequestFormsListDto> itemPratica) {
            final RequestFormsListDto pratica = itemPratica.getModelObject();

            itemPratica.addOrReplace(
                new AmtCardLabel<>("ownerName", pratica.getOwnerName(), GeoworksPanel.this));

            //				itemPratica.addOrReplace(
            //						new AmtCardLabel<>("requesterName", pratica.getRequesterName(),
            // GeoworksPanel.this));

            Label requesterName = new Label("requesterName", pratica.getRequesterName());
            requesterName.setVisible(PageUtil.isStringValid(pratica.getRequesterName()));
            itemPratica.addOrReplace(requesterName);

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "requestFormTrunks", pratica.getRequestFormTrunks(), GeoworksPanel.this));

            NotEmptyLabel requestFormCode =
                new NotEmptyLabel("requestFormCode", pratica.getRequestFormCode());
            requestFormCode.setVisible(LabelFdCUtil.checkIfNotNull(pratica.getRequestFormCode()));
            itemPratica.addOrReplace(requestFormCode);

            String currentStatusChangeStatusDate = pratica.getCurrentStatusChangeStatusDate();
            LocalDate currentStatusChangeStatusDateLocalDate = null;
            if (LabelFdCUtil.checkIfNotNull(currentStatusChangeStatusDate)) {
              OffsetDateTime currentStatusChangeStatusDateOffsetDateTime =
                  LocalDateUtil.getOffsetDateTimeByString(
                      currentStatusChangeStatusDate, "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
              currentStatusChangeStatusDateLocalDate =
                  LocalDateUtil.getLocalDateFromOffsetDateTime(
                      currentStatusChangeStatusDateOffsetDateTime);
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "currentStatusChangeStatusDate",
                    currentStatusChangeStatusDateLocalDate,
                    GeoworksPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "currentStatusTemplateExternalDescr",
                    pratica.getCurrentStatusTemplateExternalDescr(),
                    GeoworksPanel.this));

            String urlDettagli = "";
            Integer id = null;
            if (LabelFdCUtil.checkIfNotNull(pratica.getId())) {
              id = pratica.getId();
              log.debug("CP id geoworks = " + id);
              urlDettagli = BaseServiceImpl.API_GEOWORKS_DETTAGLI + id;
              log.debug("CP url dettagli geoworks = " + urlDettagli);
            }
            ExternalLink btnDettagli = new ExternalLink("btnDettagli", urlDettagli);
            btnDettagli.setVisible(LabelFdCUtil.checkIfNotNull(id));
            itemPratica.addOrReplace(btnDettagli);
          }
        };

    listView.setVisible(checkPratiche(listaPratiche));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkPratiche(listaPratiche) && listaPratiche.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private String getTitolo(GeoworksFunzioni funzione) {
    return LabelFdCUtil.checkIfNotNull(funzione) ? funzione.getDescrizioneFunz() : "";
  }

  private boolean checkPratiche(List<RequestFormsListDto> listaPratiche) {
    return LabelFdCUtil.checkIfNotNull(listaPratiche)
        && !LabelFdCUtil.checkEmptyList(listaPratiche);
  }

  private List<RequestFormsListDto> popolaLista(
      String codiceFiscaleOperatore,
      GeoworksFunzioni funzione,
      List<GeoworksServizi> listaServizi) {
    try {
      return ServiceLocator.getInstance()
          .getServiziGeoworks()
          .listaItemsFromSearch(codiceFiscaleOperatore, funzione, listaServizi);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Geoworks"));
    }
  }
}
