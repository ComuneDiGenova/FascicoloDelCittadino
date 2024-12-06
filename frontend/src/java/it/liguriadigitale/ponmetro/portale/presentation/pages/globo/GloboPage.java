package it.liguriadigitale.ponmetro.portale.presentation.pages.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel.GloboListaPraticheApertePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel.GloboListaProcedimentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel.GloboListaTipoProcedimentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboParam;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.builder.GloboParamBuilder;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class GloboPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7091955629679432126L;
  private Log log = LogFactory.getLog(getClass());

  private static final String WICKET_ID_PANNELLO = "pannello";
  private static final String WICKET_ID_PRATICHE = "pratiche";
  private GloboParam globoParam;

  public GloboPage(PageParameters parameters) {
    super(parameters);
    log.debug("GloboParam=" + globoParam);
    globoParam = estraiGloboParameters(parameters);
    log.debug("GloboParam=" + globoParam);
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug("onConfigure");

    try {
      List<VCfgTCatGlobo> listaFunzioni = ServiceLocator.getInstance().getGlobo().getFunzioni();
      List<VCfgTCatGlobo> listaFiltrata = filtroPerIdFunzione(listaFunzioni);
      List<VCfgTCatGlobo> listaFiltrataProcedimenti = filtroPerIdProcedimento(listaFiltrata);

      if (listaFiltrataProcedimenti.isEmpty()) {
        log.error("Lista vuota");
        error("Parametri Globo non corretti, nessun procedimento trovato");
        addOrReplace(new FeedbackPanel("breadcrumbPanel"));
        addOrReplace(new EmptyPanel(WICKET_ID_PANNELLO));
        addOrReplace(new EmptyPanel(WICKET_ID_PRATICHE));
      } else if (listaFiltrataProcedimenti.size() > 1) {
        /*
        GloboListView panel = new GloboListView(WICKET_ID_PANNELLO, getListaFiltrataRiorganizzata(listaFiltrataProcedimenti));
        panel.add(new AttributeAppender("class", "row content my-4"));
        */
        log.debug("listaFiltrataProcedimenti =\n" + listaFiltrataProcedimenti);
        creaBreadcrumb(listaFiltrataProcedimenti);
        GloboListaTipoProcedimentiPanel panel =
            new GloboListaTipoProcedimentiPanel(WICKET_ID_PANNELLO, listaFiltrataProcedimenti);
        addOrReplace(panel);
        addOrReplace(new EmptyPanel(WICKET_ID_PRATICHE));

      } else {
        log.debug("listaFiltrataProcedimenti =\n" + listaFiltrataProcedimenti);
        creaBreadcrumb(listaFiltrataProcedimenti);
        VCfgTCatGlobo tipoProcedimento = listaFiltrataProcedimenti.get(0);
        GloboListaProcedimentiPanel panel =
            new GloboListaProcedimentiPanel(WICKET_ID_PANNELLO, tipoProcedimento);
        addOrReplace(panel);
        addOrReplace(new GloboListaPraticheApertePanel(WICKET_ID_PRATICHE, tipoProcedimento));
      }
    } catch (BusinessException e) {
      log.error("Errore: ", e);
      error("Impossibile contattare il backend del Fascicolo");
    }
  }

  private List<VCfgTCatGlobo> filtroPerIdProcedimento(List<VCfgTCatGlobo> listaFunzioni) {
    log.debug("filtroPerIdProcedimento:" + globoParam.getIdProcedimento());
    if (!StringUtils.isEmpty(globoParam.getIdProcedimento())) {
      log.debug("filtroPerIdProcedimento:" + globoParam.getIdProcedimento());
      log.debug("listaFunzioni.size=" + listaFunzioni.size());
      List<VCfgTCatGlobo> listaFiltrata =
          listaFunzioni.stream()
              .filter(
                  c ->
                      c.getIdProcedimento() != null
                          && c.getIdProcedimento().equalsIgnoreCase(globoParam.getIdProcedimento()))
              .collect(Collectors.toList());
      log.debug("listaFiltrata.size=" + listaFiltrata.size());
      return listaFiltrata;
    } else {
      return listaFunzioni;
    }
  }

  private List<VCfgTCatGlobo> filtroPerIdFunzione(List<VCfgTCatGlobo> listaFunzioni) {
    if (!StringUtils.isEmpty(globoParam.getIdFunzione())) {
      List<VCfgTCatGlobo> listaFiltrata =
          listaFunzioni.stream()
              .filter(
                  c ->
                      c.getIdFunz() != null
                          && c.getIdFunz().toString().equals(globoParam.getIdFunzione()))
              .collect(Collectors.toList());
      log.debug("listaFiltrata.size=" + listaFiltrata.size());
      return listaFiltrata;
    } else {
      return listaFunzioni;
    }
  }

  private GloboParam estraiGloboParameters(PageParameters parameters) {
    String idFunzione = String.valueOf(parameters.get(GloboPathParametersName.ID_FUNZIONE.name()));
    String idProcedimento =
        String.valueOf(parameters.get(GloboPathParametersName.ID_PROCEDIMENTO.name()));

    if (!StringUtils.isEmpty(idFunzione)) {
      log.debug("ID_FUNZIONE: " + idFunzione);
    } else {
      log.debug("NO ID_FUNZIONE: " + idFunzione);
    }

    if (!StringUtils.isEmpty(idProcedimento)) {
      log.debug("ID_PROCEDIMENTO: " + idProcedimento);
    } else {
      log.debug("NO ID_PROCEDIMENTO: " + idProcedimento);
    }
    return GloboParamBuilder.getInstance()
        .addIdFunzione(idFunzione)
        .addIdProcedimento(idProcedimento)
        .build();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void creaBreadcrumb(List<VCfgTCatGlobo> listaFiltrataProcedimenti) {
    LoginInSession session = (LoginInSession) Session.get();
    List<BreadcrumbFdC> listaBreadcrumb =
        ServiceLocator.getInstance()
            .getGlobo()
            .getListaBreadcrumb(
                listaFiltrataProcedimenti, session.getPagineAbilitate(), getUtente());
    FdCBreadcrumbPanel breadcrumbPanel = new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
    addOrReplace(breadcrumbPanel);
  }
}
