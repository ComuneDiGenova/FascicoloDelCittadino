package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.anagrafe;

import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;

public class CertificatoAnagraficoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  public CertificatoAnagraficoPage() {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);

    ElencoCertificatiRichiestiPanel elenco = new ElencoCertificatiRichiestiPanel("elenco");
    addOrReplace(elenco);

    LinkDinamicoLaddaFunzione<Object> btnNuovaDomanda =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnNuovaDomanda",
            new LinkDinamicoFunzioneData(
                "CertificatoAnagraficoPage.nuovaDomanda",
                "RichiestaCertificatoAnagraficoPage",
                "CertificatoAnagraficoPage.nuovaDomanda"),
            null,
            elenco,
            "color-yellow col-auto icon-certificato-anagrafico");
    addOrReplace(btnNuovaDomanda);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }
}
