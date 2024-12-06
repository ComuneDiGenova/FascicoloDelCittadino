package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel.DomandaBorsaStudioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.List;

public class DomandaBorseStudioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7592375506749698578L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DomandaBorseStudioPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    PraticaBorseStudioExtend praticaEsteso = new PraticaBorseStudioExtend();

    praticaEsteso.setNomeRichiedente(getUtente().getNome());
    praticaEsteso.setCognomeRichiedente(getUtente().getCognome());
    praticaEsteso.setCodiceFiscaleRichiedente(getUtente().getCodiceFiscaleOperatore());
    praticaEsteso.setEmail(getUtente().getMail());
    praticaEsteso.setCellulare(getUtente().getMobile());

    praticaEsteso.setNumeroFigliACarico(1);

    if (getUtente().isResidente()) {

      praticaEsteso.setProvinciaResidenza("010");
      praticaEsteso.setComuneResidenza("025");

      if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
          && LabelFdCUtil.checkIfNotNull(
              getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {

        praticaEsteso.setIndirizzoResidenza(
            getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());

        try {
          FeaturesGeoserver toponomasticaGeoserver =
              ServiceLocator.getInstance()
                  .getServiziGeoserver()
                  .getToponomasticaResidenzaUtenteLoggato(getUtente());

          praticaEsteso.setToponomasticaUtenteLoggato(toponomasticaGeoserver);

          if (LabelFdCUtil.checkIfNotNull(toponomasticaGeoserver)) {
            praticaEsteso.setViaResidenza(toponomasticaGeoserver.getDESVIA());
            praticaEsteso.setCivicoResidenza(toponomasticaGeoserver.getTESTO());
            praticaEsteso.setCodiceViaResidenza(toponomasticaGeoserver.getCOD_STRADA());

            praticaEsteso.setInternoResidenza(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFlatNumber());
            praticaEsteso.setCapResidenza(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
          }

        } catch (BusinessException | ApiException e) {
          log.error("Errore toponomatica loggato in borse studio: " + e.getMessage(), e);
        }
      }
    }

    try {
      List<Residente> listaPersoneInAnagrafeDelDemografico =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .listaPersoneInAnagrafeDelDemografico(getUtente());
      praticaEsteso.setListaComponentiAnagrafe(listaPersoneInAnagrafeDelDemografico);
    } catch (BusinessException | ApiException e) {
      log.error("Errore durante nucleo anagrafico = " + e.getMessage());
    }

    DomandaBorsaStudioPanel domandaPanel =
        new DomandaBorsaStudioPanel("domandaPanel", praticaEsteso);
    addOrReplace(domandaPanel);

    setOutputMarkupId(true);
  }

  //	public void refreshDomandaBorsaPanel(PraticaBorseStudioExtend praticaEsteso) {
  //
  //		this.praticaEsteso = praticaEsteso;
  //		this.remove("domandaPanel");
  //
  //		DomandaBorsaStudioPanel domandaPanel = new DomandaBorsaStudioPanel("domandaPanel",
  // praticaEsteso);
  //		addOrReplace(domandaPanel);
  //	}

}
