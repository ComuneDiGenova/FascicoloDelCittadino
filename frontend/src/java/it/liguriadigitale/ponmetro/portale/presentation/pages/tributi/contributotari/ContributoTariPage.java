package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.panel.ContributoTariPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.AgevolazioneTariffariaTariInformazioni;
import java.time.LocalDate;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.StringResourceModel;

public class ContributoTariPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4246967454171941171L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public ContributoTariPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    AgevolazioneTariffariaTariInformazioni informazioniContributoTari = getInformazioni();
    WebMarkupContainer containerInformazioniContributo =
        new WebMarkupContainer("containerInformazioniContributo");
    containerInformazioniContributo.setOutputMarkupId(true);
    containerInformazioniContributo.setOutputMarkupPlaceholderTag(true);
    containerInformazioniContributo.setVisible(
        LabelFdCUtil.checkIfNotNull(informazioniContributoTari));

    String presentabileDa = "";
    String presentabileFinoAl = "";

    String annoDeliberazione = "";
    String dataDeliberazioneComunale = "";
    String numDeliberazioneComunale = "";

    Double mqMassimi = null;
    Double iseeMassimo = null;
    LocalDate dataInizio = null;
    LocalDate dataFine = null;

    if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari)) {

      if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari.getPresentabileDa())) {
        presentabileDa =
            LocalDateUtil.getDataFormatoEuropeo(informazioniContributoTari.getPresentabileDa());
        dataInizio = informazioniContributoTari.getPresentabileDa();
      }

      if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari.getPresentabileFinoAl())) {
        presentabileFinoAl =
            LocalDateUtil.getDataFormatoEuropeo(informazioniContributoTari.getPresentabileFinoAl());
        dataFine = informazioniContributoTari.getPresentabileFinoAl();
      }

      if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari.getDelibera())) {

        if (LabelFdCUtil.checkIfNotNull(
            informazioniContributoTari.getDelibera().getAnnoDeliberazione())) {
          annoDeliberazione =
              String.valueOf(informazioniContributoTari.getDelibera().getAnnoDeliberazione());
        }

        if (LabelFdCUtil.checkIfNotNull(
            informazioniContributoTari.getDelibera().getDataDeliberazioneComunale())) {
          dataDeliberazioneComunale =
              LocalDateUtil.getDataFormatoEuropeo(
                  informazioniContributoTari.getDelibera().getDataDeliberazioneComunale());
        }

        if (LabelFdCUtil.checkIfNotNull(
            informazioniContributoTari.getDelibera().getNumDeliberazioneComunale())) {
          numDeliberazioneComunale =
              String.valueOf(
                  informazioniContributoTari.getDelibera().getNumDeliberazioneComunale());
        }

        if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari.getMqMassimi())) {
          mqMassimi = informazioniContributoTari.getMqMassimi();
        }

        if (LabelFdCUtil.checkIfNotNull(informazioniContributoTari.getFasce())
            && !LabelFdCUtil.checkEmptyList(informazioniContributoTari.getFasce())) {
          iseeMassimo = informazioniContributoTari.getFasce().get(0).getValoreIseeMassimo();
        }
      }
    }

    DatiDomandaContributoTari datiDomandaContributoTari = new DatiDomandaContributoTari();
    datiDomandaContributoTari.setMqMassimi(mqMassimi);
    datiDomandaContributoTari.setIseeMassimo(iseeMassimo);
    datiDomandaContributoTari.setPresentabileDa(dataInizio);
    datiDomandaContributoTari.setPresentabileFinoAl(dataFine);

    NotEmptyLabel informazioniContributo =
        new NotEmptyLabel(
            "informazioniContributo",
            new StringResourceModel("ContributoTariPage.informazioniContributo", this)
                .setParameters(
                    numDeliberazioneComunale,
                    dataDeliberazioneComunale,
                    presentabileDa,
                    presentabileFinoAl));

    informazioniContributo.setEscapeModelStrings(false);
    informazioniContributo.setOutputMarkupId(true);
    informazioniContributo.setOutputMarkupPlaceholderTag(true);
    containerInformazioniContributo.addOrReplace(informazioniContributo);
    addOrReplace(containerInformazioniContributo);

    ContributoTariPanel contributoTariPanel =
        new ContributoTariPanel("contributoTariPanel", datiDomandaContributoTari);
    addOrReplace(contributoTariPanel);

    setOutputMarkupId(true);
  }

  private AgevolazioneTariffariaTariInformazioni getInformazioni() {
    AgevolazioneTariffariaTariInformazioni informazioni = null;

    int anno = LocalDate.now().getYear();
    Long annoCorrente = Long.valueOf(anno);

    try {
      informazioni =
          ServiceLocator.getInstance().getServiziContributoTari().getInformazioni(annoCorrente);
    } catch (ApiException | BusinessException e) {
      log.error("Errore informazioni contributo TARI: " + e.getMessage(), e);
    }

    return informazioni;
  }
}
