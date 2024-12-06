package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento;

import it.liguriadigitale.ponmetro.portale.pojo.teleriscaldamento.DatiDomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel.domanda.TeleriscaldamentoDomandaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;

public class TeleriscaldamentoDomandaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6721833752726809494L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public TeleriscaldamentoDomandaPage() {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    DatiDomandaTeleriscaldamento datiDomanda = new DatiDomandaTeleriscaldamento();

    // vecchio
    // ServiceLocator.getInstance().getServiziTeleriscaldamento().getAttestazioniISEE(getUtente(),
    // datiDomanda);

    // nuovo
    ServiceLocator.getInstance()
        .getServiziTeleriscaldamento()
        .setDatiIseeModiInDomanda(getUtente(), datiDomanda);

    datiDomanda.setNome(getUtente().getNome());
    datiDomanda.setCognome(getUtente().getCognome());
    datiDomanda.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    datiDomanda.setEmail(getUtente().getMail());
    datiDomanda.setCellulare(getUtente().getMobile());

    if (getUtente().isResidente()
        && LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
        && LabelFdCUtil.checkIfNotNull(
            getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
      datiDomanda.setIndirizzoCompleto(
          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());

      datiDomanda.setVia(
          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvOfficialStreetName());

      datiDomanda.setNumeroCivico(
          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvStreetNumber());

      datiDomanda.setCap(
          getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
    }

    datiDomanda.setComune("Genova");

    datiDomanda.setProvincia("Ge");

    TeleriscaldamentoDomandaPanel teleriscaldamentoPanel =
        new TeleriscaldamentoDomandaPanel("teleriscaldamentoPanel", datiDomanda);
    addOrReplace(teleriscaldamentoPanel);

    setOutputMarkupId(true);
  }
}
