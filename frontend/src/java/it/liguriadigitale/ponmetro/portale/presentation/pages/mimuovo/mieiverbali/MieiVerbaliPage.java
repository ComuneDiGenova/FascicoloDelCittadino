package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.HeaderMieiVerbaliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel.MieiVerbaliPanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class MieiVerbaliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2925368097070985581L;

  public MieiVerbaliPage() {
    this(null, null);
  }

  public MieiVerbaliPage(List<Verbale> listaVerbali) {
    this(listaVerbali, null);
  }

  public MieiVerbaliPage(List<Verbale> listaVerbali, String stato) {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HeaderMieiVerbaliPanel headerMieiVerbaliPanel =
        (HeaderMieiVerbaliPanel)
            new HeaderMieiVerbaliPanel("headerMieiVerbaliPanel").setRenderBodyOnly(true);
    headerMieiVerbaliPanel.fillDati("");
    addOrReplace(headerMieiVerbaliPanel);

    LinkDinamicoLaddaFunzione<Object> btnPagamentoVerbale =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnPagamentoVerbale",
            new LinkDinamicoFunzioneData(
                "MieiVerbaliPage.btnPagamentoVerbale",
                "PagamentoMultaPLPage",
                "MieiVerbaliPage.btnPagamentoVerbale"),
            null,
            MieiVerbaliPage.this,
            "color-cyan col-auto icon-vigile-verbale");
    addOrReplace(btnPagamentoVerbale);

    MieiVerbaliPanel mieiVerbaliPanel =
        (MieiVerbaliPanel)
            new MieiVerbaliPanel("mieiVerbaliPanel", listaVerbali, stato).setRenderBodyOnly(true);

    mieiVerbaliPanel.fillDati(listaVerbali == null ? popolaListaVerbali() : listaVerbali);
    add(mieiVerbaliPanel);
    setOutputMarkupId(true);
  }

  private List<Verbale> popolaListaVerbali() {
    try {
      return ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }
}
