package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.dettaglio.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.math.BigDecimal;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class RimborsoImuDettaglioPanel extends BasePanel {

  private static final long serialVersionUID = 1879798765464651321L;

  private static final String ICON_ACCETTATA = "color-fc-success col-3 icon-rimborsi-imu";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public RimborsoImuDettaglioPanel(String id) {
    super(id);
    // TODO Auto-generated constructor stub
  }

  public RimborsoImuDettaglioPanel(String id, Integer praticaRimborsoId) {
    // TODO Auto-generated constructor stub
    this(id);

    fillDati(praticaRimborsoId);
  }

  @Override
  public void fillDati(Object dati) {

    Integer praticaRimborsoId = (Integer) dati;
    RimborsoImu rimborsoImu = getDettaglioPraticaRimborso(praticaRimborsoId);

    // TODO Auto-generated method stub
    addOrReplace(
        creaFilter("flagVerbaleInCompilazione", "RimborsoImuDettaglioPanel.inCompilazione"));
    addOrReplace(creaFilter("flagVerbalePresentato", "RimborsoImuDettaglioPanel.presentato"));
    addOrReplace(creaFilter("flagVerbaleSospeso", "RimborsoImuDettaglioPanel.sospeso"));
    addOrReplace(creaFilter("flagVerbaleApprovato", "RimborsoImuDettaglioPanel.approvato"));
    addOrReplace(creaFilter("flagVerbaleDiniegato", "RimborsoImuDettaglioPanel.diniegato"));
    addOrReplace(creaFilter("flagVerbaleLiquidato", "RimborsoImuDettaglioPanel.liquidato"));

    WebMarkupContainer containerProtocollo = new WebMarkupContainer("containerProtocollo");

    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "annoProtocollo", rimborsoImu.getAnnoProtocollo(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "numeroProtocollo", rimborsoImu.getNumeroProtocollo(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "dataProtocollo", rimborsoImu.getDataProtocollo(), RimborsoImuDettaglioPanel.this));

    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "annoDocumento", rimborsoImu.getAnnoDocumento(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "numeroDocumento", rimborsoImu.getNumeroDocumento(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "dataPresentazione",
            rimborsoImu.getDataPresentazione(),
            RimborsoImuDettaglioPanel.this));

    containerProtocollo.addOrReplace(
        new AmtCardLabel<>("nome", getUtente().getNome(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>("cognome", getUtente().getCognome(), RimborsoImuDettaglioPanel.this));
    containerProtocollo.addOrReplace(
        new AmtCardLabel<>(
            "codicefiscale", rimborsoImu.getCodiceFiscale(), RimborsoImuDettaglioPanel.this));

    WebMarkupContainer datiIntestatario = new WebMarkupContainer("datiIntestatario");

    datiIntestatario.addOrReplace(
        new AmtCardLabel<>(
            "cfIntestatario",
            rimborsoImu.getCodiceFiscaleDefunto(),
            RimborsoImuDettaglioPanel.this));
    datiIntestatario.addOrReplace(
        new AmtCardLabel<>(
            "nomeIntestatario", rimborsoImu.getNomeDefunto(), RimborsoImuDettaglioPanel.this));
    datiIntestatario.addOrReplace(
        new AmtCardLabel<>(
            "cognomeIntestatario",
            rimborsoImu.getCognomeDefunto(),
            RimborsoImuDettaglioPanel.this));

    datiIntestatario.setVisible(
        LabelFdCUtil.checkIfNotNull(rimborsoImu.getCodiceFiscaleDefunto())
            && !rimborsoImu.getCodiceFiscaleDefunto().isEmpty());

    containerProtocollo.addOrReplace(datiIntestatario);

    addOrReplace(containerProtocollo);

    PageableListView<Versamento> listView =
        new PageableListView<Versamento>("box", rimborsoImu.getVersamenti(), 4) {

          private static final long serialVersionUID = 1986546311465487985L;

          @Override
          protected void populateItem(ListItem<Versamento> row) {
            // TODO Auto-generated method stub
            Versamento item = row.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            AttributeAppender attribute = new AttributeAppender("class", " " + ICON_ACCETTATA);
            icona.add(attribute);
            icona.add(getColoreIconaRimborso(item));
            row.addOrReplace(icona);

            String motiv =
                LabelFdCUtil.checkIfNotNull(item.getMotivazioneVersamento())
                    ? item.getMotivazioneVersamento().toString()
                    : "";
            String tipoQuota =
                LabelFdCUtil.checkIfNull(item.getTipoQuota())
                    ? null
                    : item.getTipoQuota().toString();

            row.addOrReplace(
                new AmtCardLabel<>("annoV", item.getAnno(), RimborsoImuDettaglioPanel.this));

            row.addOrReplace(
                new AmtCardLabel<>("tipoQuota", tipoQuota, RimborsoImuDettaglioPanel.this));

            row.addOrReplace(
                new AmtCardLabel<>(
                    "totaleRichiedenteV",
                    formatWithCurrency(item.getImportoRichiedente()),
                    RimborsoImuDettaglioPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "totaleApprovatoV",
                    formatWithCurrency(item.getImportoApprovato()),
                    RimborsoImuDettaglioPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "totaleImportoV",
                    formatWithCurrency(item.getTotaleImporto()),
                    RimborsoImuDettaglioPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("motivazioneV", motiv, RimborsoImuDettaglioPanel.this));
            row.addOrReplace(
                new AmtCardLabel<>("altroV", item.getAltro(), RimborsoImuDettaglioPanel.this));
            row.addOrReplace(new Label("statoV", item.getStato().toString()));
            row.addOrReplace(
                new AmtCardLabel<>(
                    "motivazioneSospensione",
                    item.getMotivoSospensione(),
                    RimborsoImuDettaglioPanel.this));
          }

          private AttributeModifier getColoreIconaRimborso(Versamento item) {
            AttributeModifier coloreIcona = new AttributeModifier("style", "color: #000000");

            switch (item.getStato()) {
              case P: // Presentato
                coloreIcona = new AttributeModifier("style", "color: #008758");
                break;
              case S: // Sospeso
                coloreIcona = new AttributeModifier("style", "color: #d9364f");
                break;
              case R: // Diniegato
                coloreIcona = new AttributeModifier("style", "color: #d9364f");
                break;
              case T: // Approvato
                coloreIcona = new AttributeModifier("style", "color: #008758");
                break;
              case L: // Liquidato
                coloreIcona = new AttributeModifier("style", "color: #008758");
                break;
              case A: // Annulato
                coloreIcona = new AttributeModifier("style", "color: #d9364f");
                break;
              default: // IN_COMPILAZIONE
                coloreIcona = new AttributeModifier("style", "color: #a66300");
                break;
            }
            return coloreIcona;
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(rimborsoImu.getVersamenti())
            && rimborsoImu.getVersamenti().size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private RimborsoImu getDettaglioPraticaRimborso(Integer praticaRimborsoId) {
    // TODO Auto-generated method stub
    try {
      return ServiceLocator.getInstance()
          .getServiziImuEng()
          .dettaglioPraticaRimborsoImu(praticaRimborsoId);

    } catch (ApiException | BusinessException e) {
      // TODO Auto-generated catch block
      log.debug("[getDettaglioPraticaRimborso] ");
      return new RimborsoImu();
    }
  }

  private String formatWithCurrency(BigDecimal value) {

    if (LabelFdCUtil.checkIfNull(value) || value.equals(BigDecimal.ZERO)) {
      return null;
    }

    return value.toString() + " €";
  }

  private LaddaAjaxLink<Object> creaFilter(String wicketId, String label) {
    LaddaAjaxLink<Object> linkFlagVerbaleAperto =
        new LaddaAjaxLink<Object>(wicketId, Type.Link) {

          private static final long serialVersionUID = 1448579012199876827L;

          @Override
          public void onClick(AjaxRequestTarget target) {}
        };

    linkFlagVerbaleAperto.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(label, RimborsoImuDettaglioPanel.this)));
    return linkFlagVerbaleAperto;
  }
}
