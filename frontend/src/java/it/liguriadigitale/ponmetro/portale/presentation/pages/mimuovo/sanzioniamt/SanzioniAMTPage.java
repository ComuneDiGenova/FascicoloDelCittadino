package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.sanzioniamt;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Informazioni;
import it.liguriadigitale.ponmetro.abbonamentiamt.model.Messaggio;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.amt.AbbonamentoSanzioneAmt;
import it.liguriadigitale.ponmetro.portale.pojo.amt.SanzioniAmtEsteso;
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
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.sanzioniamt.panel.SanzioniAMTPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class SanzioniAMTPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3507082466650962114L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SanzioniAMTPage() {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    try {
      String tipo = "S";
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
              new AlertBoxPanel<Component>("messaggiAMT", listaMessaggi).setRenderBodyOnly(true);
      addOrReplace(messaggi);

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore messaggi AMT sanzioni: " + e.getMessage(), e);
    }

    NucleoFamiglia nucleoFamiglia = inizializzaNucleoFamigliaAMT();

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<MessaggiInformativi> listaMessaggiInfo = popolaListaMessaggiInfo();

    AlertBoxPanel<Component> messaggiInfo =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggiInfo", listaMessaggiInfo).setRenderBodyOnly(true);
    addOrReplace(messaggiInfo);

    NucleoAnagraficoComponentePanel nucleoAnagraficoPanel =
        (NucleoAnagraficoComponentePanel)
            new NucleoAnagraficoComponentePanel(
                    "nucleoAnagraficoPanel", AbbonamentoSanzioneAmt.SANZIONE)
                .setRenderBodyOnly(true);
    nucleoAnagraficoPanel.fillDati(nucleoFamiglia);
    nucleoAnagraficoPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(nucleoFamiglia)
            && LabelFdCUtil.checkIfNotNull(nucleoFamiglia.getListaCfNucleo())
            && !LabelFdCUtil.checkEmptyList(nucleoFamiglia.getListaCfNucleo())
            && nucleoFamiglia.getListaCfNucleo().size() > 1);
    addOrReplace(nucleoAnagraficoPanel);

    creaPannelloSanzioniAMT(nucleoFamiglia);

    setOutputMarkupId(true);
  }

  private NucleoFamiglia inizializzaNucleoFamigliaAMT() {
    NucleoFamiglia nucleo = null;
    try {
      nucleo =
          ServiceLocator.getInstance()
              .getServiziAbbonamentiAmt()
              .inizializzaNucleoFamigliaAMT(getUtente());
    } catch (BusinessException | ApiException e) {
      log.error("Errore nucleo amt: " + e.getMessage());
    }
    return nucleo;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public SanzioniAMTPage(NucleoFamiglia nucleoFamiglia) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi = popolaListaMessaggi(nucleoFamiglia);

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggiAMT", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    NucleoFamiglia nucleoFamigliaPerMultiSelect = inizializzaNucleoFamigliaAMT();
    NucleoAnagraficoComponentePanel nucleoAnagraficoPanel =
        (NucleoAnagraficoComponentePanel)
            new NucleoAnagraficoComponentePanel(
                    "nucleoAnagraficoPanel", AbbonamentoSanzioneAmt.SANZIONE)
                .setRenderBodyOnly(true);
    nucleoAnagraficoPanel.fillDati(nucleoFamigliaPerMultiSelect);
    nucleoAnagraficoPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(nucleoFamigliaPerMultiSelect)
            && LabelFdCUtil.checkIfNotNull(nucleoFamigliaPerMultiSelect.getListaCfNucleo())
            && !LabelFdCUtil.checkEmptyList(nucleoFamigliaPerMultiSelect.getListaCfNucleo())
            && nucleoFamigliaPerMultiSelect.getListaCfNucleo().size() > 1);
    addOrReplace(nucleoAnagraficoPanel);

    creaPannelloSanzioniAMT(nucleoFamiglia);
    setOutputMarkupId(true);
  }

  public SanzioniAMTPanel creaPannelloSanzioniAMT(NucleoFamiglia nucleoFamiglia) {
    log.debug("nucleoFamiglia:" + nucleoFamiglia);
    SanzioniAMTPanel sanzioniAmtPanel = new SanzioniAMTPanel("sanzioniAmtPanel");
    sanzioniAmtPanel.fillDati(popolaListaSanzioni(nucleoFamiglia));
    addOrReplace(sanzioniAmtPanel);
    return sanzioniAmtPanel;
  }

  private List<SanzioniAmtEsteso> popolaListaSanzioni(NucleoFamiglia sanzioniAmt) {
    log.debug("CP popolaListaSanzioni = " + sanzioniAmt.getListaCfNucleo());

    try {
      return ServiceLocator.getInstance()
          .getServiziAbbonamentiAmt()
          .listaTutteSanzioniAmt(sanzioniAmt);

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("sanzioni AMT"));
    }
  }

  private List<MessaggiInformativi> popolaListaMessaggi(NucleoFamiglia abbonamentiAmt) {
    log.debug("CP popolaListaMessaggi sanzioni = " + abbonamentiAmt.getListaCfNucleo());

    try {
      String tipo = "S";
      return ServiceLocator.getInstance()
          .getServiziAbbonamentiAmt()
          .popolaListaMessaggi(abbonamentiAmt, tipo);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("sanzioni AMT"));
    }
  }

  private List<MessaggiInformativi> popolaListaMessaggiInfo() {
    return ServiceLocator.getInstance()
        .getServiziAbbonamentiAmt()
        .popolaListaMessaggiInfoSanzioni();
  }
}
