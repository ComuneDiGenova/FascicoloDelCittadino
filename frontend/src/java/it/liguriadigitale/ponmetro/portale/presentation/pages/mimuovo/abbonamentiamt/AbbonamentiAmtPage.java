package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Informazioni;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Messaggio;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.amt.AbbonamentoSanzioneAmt;
import it.liguriadigitale.ponmetro.portale.pojo.amt.TesseraAmtEsteso;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.nucleo.NucleoAnagraficoComponentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.NucleoFamiglia;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.panel.AbbonamentiAmtPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class AbbonamentiAmtPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7660309620639141558L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AbbonamentiAmtPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    try {
      String tipo = "A";
      Informazioni info =
          ServiceLocator.getInstance()
              .getServiziAbbonamentiAmt()
              .getMessaggi(getUtente().getCodiceFiscaleOperatore(), tipo);

      List<MessaggiInformativi> listaMessaggi = new ArrayList<MessaggiInformativi>();
      if (LabelFdCUtil.checkIfNotNull(info)) {
        for (Messaggio elem : info.getMessaggi()) {
          MessaggiInformativi messaggio = new MessaggiInformativi();
          messaggio.setMessaggio(elem.getTitle());
          messaggio.setType(elem.getType());
          listaMessaggi.add(messaggio);
        }
      }
      AlertBoxPanel<Component> messaggi =
          (AlertBoxPanel<Component>)
              new AlertBoxPanel<Component>("messaggiAMT", listaMessaggi)
                  .setRenderBodyOnly(true)
                  .setVisible(false);
      addOrReplace(messaggi);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore messaggi AMT sanzioni: " + e.getMessage(), e);
    }

    NucleoFamiglia nucleoFamiglia = null;
    try {
      nucleoFamiglia = inizializzaNucleoFamigliaAMT();
    } catch (BusinessException | ApiException e) {
      log.error("Errore nucleo amt: " + e.getMessage());
    }

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    NucleoAnagraficoComponentePanel nucleoAnagraficoPanel =
        (NucleoAnagraficoComponentePanel)
            new NucleoAnagraficoComponentePanel(
                    "nucleoAnagraficoPanel", AbbonamentoSanzioneAmt.ABBONAMENTO)
                .setRenderBodyOnly(true);
    nucleoAnagraficoPanel.fillDati(nucleoFamiglia);
    nucleoAnagraficoPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(nucleoFamiglia)
            && LabelFdCUtil.checkIfNotNull(nucleoFamiglia.getListaCfNucleo())
            && !LabelFdCUtil.checkEmptyList(nucleoFamiglia.getListaCfNucleo())
            && nucleoFamiglia.getListaCfNucleo().size() > 1);
    addOrReplace(nucleoAnagraficoPanel);

    creaPannelloAbbonamentiAMT(nucleoFamiglia);

    setOutputMarkupId(true);
  }

  private NucleoFamiglia inizializzaNucleoFamigliaAMT() throws BusinessException, ApiException {
    return ServiceLocator.getInstance()
        .getServiziAbbonamentiAmt()
        .inizializzaNucleoFamigliaAMT(getUtente());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AbbonamentiAmtPage(NucleoFamiglia nucleoFamiglia) throws BusinessException, ApiException {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi = popolaListaMessaggi(nucleoFamiglia);

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    NucleoFamiglia nucleoFamigliaPerMultiSelect = null;
    try {
      nucleoFamigliaPerMultiSelect = inizializzaNucleoFamigliaAMT();
    } catch (BusinessException | ApiException e) {
      log.error("Errore inizializza nucleo amt: " + e.getMessage());
    }
    NucleoAnagraficoComponentePanel nucleoAnagraficoPanel =
        (NucleoAnagraficoComponentePanel)
            new NucleoAnagraficoComponentePanel(
                    "nucleoAnagraficoPanel", AbbonamentoSanzioneAmt.ABBONAMENTO)
                .setRenderBodyOnly(true);
    nucleoAnagraficoPanel.fillDati(nucleoFamigliaPerMultiSelect);
    nucleoAnagraficoPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(nucleoFamigliaPerMultiSelect)
            && LabelFdCUtil.checkIfNotNull(nucleoFamigliaPerMultiSelect.getListaCfNucleo())
            && !LabelFdCUtil.checkEmptyList(nucleoFamigliaPerMultiSelect.getListaCfNucleo())
            && nucleoFamigliaPerMultiSelect.getListaCfNucleo().size() > 1);
    addOrReplace(nucleoAnagraficoPanel);

    creaPannelloAbbonamentiAMT(nucleoFamiglia);
    setOutputMarkupId(true);
  }

  public AbbonamentiAmtPanel creaPannelloAbbonamentiAMT(NucleoFamiglia nucleoFamiglia) {
    log.debug("nucleoFamiglia:" + nucleoFamiglia);
    AbbonamentiAmtPanel abbonamentiAmtPanel = new AbbonamentiAmtPanel("abbonamentiAmtPanel");
    abbonamentiAmtPanel.fillDati(popolaListaAbbonamenti(nucleoFamiglia));
    addOrReplace(abbonamentiAmtPanel);
    return abbonamentiAmtPanel;
  }

  private List<TesseraAmtEsteso> popolaListaAbbonamenti(NucleoFamiglia abbonamentiAmt) {
    try {
      return ServiceLocator.getInstance()
          .getServiziAbbonamentiAmt()
          .listaTuttiAbbonamentiAmtOrdinatiPerDataDiscendente(abbonamentiAmt);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("abbonamenti AMT"));
    }
  }

  private List<MessaggiInformativi> popolaListaMessaggi(NucleoFamiglia abbonamentiAmt) {
    log.debug("CP popolaListaMessaggi abbonamenti = " + abbonamentiAmt.getListaCfNucleo());

    try {
      String tipo = "A";
      return ServiceLocator.getInstance()
          .getServiziAbbonamentiAmt()
          .popolaListaMessaggi(abbonamentiAmt, tipo);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("abbonamenti AMT"));
    }
  }
}
