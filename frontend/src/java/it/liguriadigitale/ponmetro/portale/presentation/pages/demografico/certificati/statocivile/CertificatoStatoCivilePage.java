package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;

public class CertificatoStatoCivilePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5975503781218096405L;

  public CertificatoStatoCivilePage() {
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
                "CertificatoStatoCivilePage.nuovaDomanda",
                "RichiestaCertificatoStatoCivilePage",
                "CertificatoStatoCivilePage.nuovaDomanda"),
            null,
            elenco,
            "color-yellow col-auto icon-certificato-stato-civile");
    addOrReplace(btnNuovaDomanda);
  }
}
