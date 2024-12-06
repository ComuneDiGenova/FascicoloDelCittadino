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

public class SpecchiParaboliciDossiArtificialiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6780495464239025030L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SpecchiParaboliciDossiArtificialiPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    GeoworksFunzioni funzione = GeoworksUtils.getFunzioneGeoworks(TipoFunzEnum.SPDA);
    List<GeoworksServizi> listaServizi = GeoworksUtils.getServiziGeoworks(TipoFunzEnum.SPDA);

    ListView<GeoworksServizi> boxBtnDomande =
        new ListView<GeoworksServizi>("boxBtnDomande", listaServizi) {

          @Override
          protected void populateItem(ListItem<GeoworksServizi> itemServizio) {

            final GeoworksServizi servizio = itemServizio.getModelObject();

            LinkDinamicoFunzioneData linkDinamicoFunzione =
                LinkDinamicoFunzioneDataBuilder.getInstance()
                    .setWicketLabelKeyText(servizio.getDescrizioneServizio())
                    .setWicketClassName("DomandaRottureSuoloPubblicoPage")
                    .setLinkTitleAdditionalText(servizio.getDescrizioneServizio())
                    .build();

            LinkDinamicoFunzioneLinkEsterni<GeoworksServizi>
                btnDomandaSpecchiParaboliciDossiArtificiali =
                    new LinkDinamicoFunzioneLinkEsterni<>(
                        "btnDomandaSpecchiParaboliciDossiArtificiali",
                        linkDinamicoFunzione,
                        servizio,
                        SpecchiParaboliciDossiArtificialiPage.this,
                        "color-fc-secondary col-auto icon-mappa",
                        "",
                        false,
                        true);

            itemServizio.addOrReplace(btnDomandaSpecchiParaboliciDossiArtificiali);
          }
        };
    boxBtnDomande.setRenderBodyOnly(true);
    boxBtnDomande.setVisible(
        LabelFdCUtil.checkIfNotNull(listaServizi) && !LabelFdCUtil.checkEmptyList(listaServizi));
    addOrReplace(boxBtnDomande);

    GeoworksPanel specchiParaboliciDossiArtificialiPanel =
        new GeoworksPanel("specchiParaboliciDossiArtificialiPanel", funzione, listaServizi);
    addOrReplace(specchiParaboliciDossiArtificialiPanel);

    setOutputMarkupId(true);
  }
}
