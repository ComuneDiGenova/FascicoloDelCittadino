package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessiZTL;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitsListResult;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravFunzioni.TipoFunzEnum;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.util.BravUtils;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.bravprivacy.BravPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.panel.GenovaParcheggiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.privacy.pojo.PrivacyServizio;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class PermessiZTLPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6880480585341405498L;

  private TipoFunzEnum tipoFunzioneZtl = TipoFunzEnum.ZTL;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PermessiZTLPage() {

    super();

    if (!getUtente().isServiziBravPrivacyNonAccettata()) {
      PrivacyServizio privacyServizio = new PrivacyServizio();

      privacyServizio.setUtente(getUtente());
      privacyServizio.setCodicePrivacy(BaseServiceImpl.COD_BRAV);
      privacyServizio.setPaginaSuCuiAtterrare(getPage());
      privacyServizio.setMessaggioErrore("privacy servizi ZTL");
      privacyServizio.setNomePartecipata("Genova Parcheggi");

      throw new RestartResponseAtInterceptPageException(new BravPrivacyPage(privacyServizio));

    } else {

      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel(
              "breadcrumbPanel",
              MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
      addOrReplace(breadcrumbPanel);

      List<Legenda> listaLegenda =
          ServiceLocator.getInstance().getServiziGenovaParcheggi().getListaLegenda();
      LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
      legendaPanel.setVisible(
          LabelFdCUtil.checkIfNotNull(popolaListaPermessi())
              && !LabelFdCUtil.checkEmptyList(popolaListaPermessi()));
      addOrReplace(legendaPanel);

      AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
          AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
      addOrReplace(boxMessaggi);

      LinkDinamicoLaddaFunzione<Object> btnDomandaZTL =
          new LinkDinamicoLaddaFunzione<Object>(
              "btnDomandaZTL",
              new LinkDinamicoFunzioneData(
                  "PermessiZTLPage.btnDomandaZTL",
                  "RichiestaPermessiZTLPage",
                  "PermessiZTLPage.btnDomandaZTL"),
              null,
              PermessiZTLPage.this,
              "color-cyan col-auto icon-genova-pass");
      addOrReplace(btnDomandaZTL);

      String descrizione = getDescrizioneServizio();

      GenovaParcheggiPanel genovaParcheggiPanel =
          new GenovaParcheggiPanel("genovaParcheggiPanel", descrizione);
      genovaParcheggiPanel.fillDati(popolaListaPermessi());
      addOrReplace(genovaParcheggiPanel);
    }

    setOutputMarkupId(true);
  }

  private List<Permit> popolaListaPermessi() {
    log.debug("CP popolaListaPermessi");

    try {
      List<Permit> listaPermessi = new ArrayList<>();

      List<BravPermessi> listaPermessiBrav = BravUtils.getBravPermessi(tipoFunzioneZtl);

      PermitsListResult permitsListResult =
          ServiceLocator.getInstance()
              .getServiziGenovaParcheggi()
              .getPermessiGeParkConFiltroDaFdCBackend(
                  getUtente().getCodiceFiscaleOperatore(), listaPermessiBrav);

      if (LabelFdCUtil.checkIfNotNull(permitsListResult)) {
        listaPermessi = permitsListResult.getPermitsList();
      }

      return listaPermessi;
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Permessi ZTL"));
    }
  }

  private String getDescrizioneServizio() {
    BravFunzioni funzione = BravUtils.getFunzioneBrav(tipoFunzioneZtl);
    return PageUtil.isStringValid(funzione.getDescrizioneFunz())
        ? funzione.getDescrizioneFunz()
        : "";
  }
}