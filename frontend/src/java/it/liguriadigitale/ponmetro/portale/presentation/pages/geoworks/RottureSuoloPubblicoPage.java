package it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks;

import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.geoworkshelper.model.GeoworksServizi;
import it.liguriadigitale.ponmetro.portale.business.geoworks.impl.util.GeoworksUtils;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.geoworks.panel.GeoworksPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class RottureSuoloPubblicoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2540254570012380386L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RottureSuoloPubblicoPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    GeoworksFunzioni funzione = GeoworksUtils.getFunzioneGeoworks(TipoFunzEnum.RSP);
    List<GeoworksServizi> listaServizi = GeoworksUtils.getServiziGeoworks(TipoFunzEnum.RSP);

    ListView<GeoworksServizi> boxBtnDomande =
        new ListView<GeoworksServizi>("boxBtnDomande", listaServizi) {

          private static final long serialVersionUID = -776821497240784932L;

          @Override
          protected void populateItem(ListItem<GeoworksServizi> itemServizio) {

            final GeoworksServizi servizio = itemServizio.getModelObject();

            LinkDinamicoFunzioneData linkDinamicoFunzione =
                LinkDinamicoFunzioneDataBuilder.getInstance()
                    .setWicketLabelKeyText(servizio.getDescrizioneServizio())
                    .setWicketClassName("DomandaRottureSuoloPubblicoPage")
                    .setLinkTitleAdditionalText(servizio.getDescrizioneServizio())
                    .build();

            LinkDinamicoFunzioneLinkEsterni<GeoworksServizi> btnDomandaRottureSuoloPubblico =
                new LinkDinamicoFunzioneLinkEsterni<>(
                    "btnDomandaRottureSuoloPubblico",
                    linkDinamicoFunzione,
                    servizio,
                    RottureSuoloPubblicoPage.this,
                    "color-fc-secondary col-auto icon-mappa",
                    "",
                    false,
                    true);

            itemServizio.addOrReplace(btnDomandaRottureSuoloPubblico);
          }
        };
    boxBtnDomande.setRenderBodyOnly(true);
    boxBtnDomande.setVisible(
        LabelFdCUtil.checkIfNotNull(listaServizi) && !LabelFdCUtil.checkEmptyList(listaServizi));
    addOrReplace(boxBtnDomande);

    GeoworksPanel rottureSuoloPubblicoPanel =
        new GeoworksPanel("rottureSuoloPubblicoPanel", funzione, listaServizi);
    addOrReplace(rottureSuoloPubblicoPanel);

    setOutputMarkupId(true);
  }
}
