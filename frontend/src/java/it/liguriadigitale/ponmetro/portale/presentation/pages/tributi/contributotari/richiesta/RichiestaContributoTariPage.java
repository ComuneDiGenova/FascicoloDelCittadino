package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta;

import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasCitizenship;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel.RichiestaContributoTariPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;

public class RichiestaContributoTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2870339785734964558L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaContributoTariPage() {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiDomandaContributoTari datiDomanda = new DatiDomandaContributoTari();

    datiDomanda.setNome(getUtente().getNome());
    datiDomanda.setCognome(getUtente().getCognome());
    datiDomanda.setNominativo(getUtente().getDatiCittadinoResidente().getRdfsLabel());
    datiDomanda.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    datiDomanda.setDataNascita(getUtente().getDataDiNascita());
    datiDomanda.setEmail(getUtente().getMail());
    datiDomanda.setCellulare(getUtente().getMobile());

    if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())) {
      ResidenteCpvHasCitizenship cittadinanza =
          getUtente().getDatiCittadinoResidente().getCpvHasCitizenship();

      datiDomanda.setCittadinanza(cittadinanza.getClvCountry());
      datiDomanda.setCodiceCittadinanza(cittadinanza.getClvHasIdentifier());
    }

    ServiceLocator.getInstance()
        .getServiziContributoTari()
        .setDatiDomanda(getUtente(), datiDomanda);

    RichiestaContributoTariPanel richiestaContributoPanel =
        new RichiestaContributoTariPanel("richiestaContributoPanel", datiDomanda);
    addOrReplace(richiestaContributoPanel);

    setOutputMarkupId(true);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RichiestaContributoTariPage(DatiDomandaContributoTari datiDomanda) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    datiDomanda.setNome(getUtente().getNome());
    datiDomanda.setCognome(getUtente().getCognome());
    datiDomanda.setNominativo(getUtente().getDatiCittadinoResidente().getRdfsLabel());
    datiDomanda.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    datiDomanda.setDataNascita(getUtente().getDataDiNascita());
    datiDomanda.setEmail(getUtente().getMail());
    datiDomanda.setCellulare(getUtente().getMobile());

    if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())) {
      ResidenteCpvHasCitizenship cittadinanza =
          getUtente().getDatiCittadinoResidente().getCpvHasCitizenship();

      datiDomanda.setCittadinanza(cittadinanza.getClvCountry());
      datiDomanda.setCodiceCittadinanza(cittadinanza.getClvHasIdentifier());
    }

    ServiceLocator.getInstance()
        .getServiziContributoTari()
        .setDatiDomanda(getUtente(), datiDomanda);

    RichiestaContributoTariPanel richiestaContributoPanel =
        new RichiestaContributoTariPanel("richiestaContributoPanel", datiDomanda);
    addOrReplace(richiestaContributoPanel);

    log.debug("CP prova uffa = " + datiDomanda);

    setOutputMarkupId(true);
  }
}
