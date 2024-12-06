package it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.panel.MenuRiepilogoDinamicoPanel;
import org.apache.cxf.common.util.StringUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MenuRiepilogoDinamicoPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -8404439516337839392L;

  private String idSezione;

  public MenuRiepilogoDinamicoPage(PageParameters parameters) {
    super(parameters);

    idSezione = String.valueOf(parameters.get(GloboPathParametersName.ID_SEZIONE.name()));
    String nomeSezione =
        String.valueOf(parameters.get(GloboPathParametersName.NOME_SEZIONE.name()));
    if (StringUtils.isEmpty(nomeSezione)) {
      nomeSezione = idSezione;
      idSezione = null;
    }
    idSezione = ricavaIdSezioneDaNomeSezione(idSezione, nomeSezione);
    generaRiepilogo(idSezione);
  }

  public MenuRiepilogoDinamicoPage(String idSezione) {
    super();
    log.debug("MenuRiepilogoDinamicoPage1");
    this.idSezione = idSezione;
    generaRiepilogo(idSezione);
  }

  @SuppressWarnings("rawtypes")
  private void generaRiepilogo(String idSezione) {
    DatiSezione datiSezione = ricavaDatiSezioneDaIdSezione(idSezione);
    @SuppressWarnings("unchecked")
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbPerPaginaRiepilogo(datiSezione, getUtente()));
    addOrReplace(breadcrumbPanel);

    MenuRiepilogoDinamicoPanel bibliotechePanel =
        new MenuRiepilogoDinamicoPanel("panel", datiSezione);
    addOrReplace(bibliotechePanel);
  }

  private DatiSezione ricavaDatiSezioneDaIdSezione(String idSezione) {
    log.debug("idSezione:" + idSezione);
    if (!StringUtils.isEmpty(idSezione)) {
      for (DatiSezione sezione : getUtente().getWidgetConfigurati().keySet()) {
        if (idSezione.equalsIgnoreCase(sezione.getIdSezione())) {
          log.debug("return idSezione:" + idSezione);
          return sezione;
        }
      }
      log.debug("idSezione non trovata in keySet");
      return new DatiSezione();
    } else {
      log.debug("idSezione vuoto:" + idSezione);
      return new DatiSezione();
    }
  }

  private String ricavaIdSezioneDaNomeSezione(String idSezione, String nomeSezione) {
    log.debug("idSezione:" + idSezione);
    log.debug("nomeSezione:" + nomeSezione);
    if (StringUtils.isEmpty(idSezione)) {
      idSezione = "0";
      if (!StringUtils.isEmpty(nomeSezione)) {
        for (DatiSezione sezione : getUtente().getWidgetConfigurati().keySet()) {
          if (nomeSezione.equalsIgnoreCase(sezione.getDenominazioneSez())) {
            idSezione = sezione.getIdSezione();
          }
        }
        log.debug("return idSezione:" + idSezione);
        return idSezione;
      } else {
        log.debug("return idSezione:" + idSezione);
        return idSezione;
      }
    } else {
      log.debug("return idSezione:" + idSezione);
      return idSezione;
    }
  }
}
