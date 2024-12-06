package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi;

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

public class GenovaParcheggiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -2154041154692490286L;

  private TipoFunzEnum tipoFunzioneAreaBlu = TipoFunzEnum.AREABLU;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public GenovaParcheggiPage() {
    super();

    if (!getUtente().isServiziBravPrivacyNonAccettata()) {

      PrivacyServizio privacyServizio = new PrivacyServizio();

      privacyServizio.setUtente(getUtente());
      privacyServizio.setCodicePrivacy(BaseServiceImpl.COD_BRAV);
      privacyServizio.setPaginaSuCuiAtterrare(getPage());
      privacyServizio.setMessaggioErrore("privacy servizi AREABLU");
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

      LinkDinamicoLaddaFunzione<Object> btnDomandaAreaBlu =
          new LinkDinamicoLaddaFunzione<Object>(
              "btnDomandaAreaBlu",
              new LinkDinamicoFunzioneData(
                  "GenovaParcheggiPage.btnDomandaAreaBlu",
                  "RichiestaPermessiAreaBluPage",
                  "GenovaParcheggiPage.btnDomandaAreaBlu"),
              null,
              GenovaParcheggiPage.this,
              "color-cyan col-auto icon-genova-pass");
      addOrReplace(btnDomandaAreaBlu);

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

      //			PermitsListResult permitsListResult =
      // ServiceLocator.getInstance().getServiziGenovaParcheggi()
      //					.getPermessiGePark(getUtente().getCodiceFiscaleOperatore());

      List<BravPermessi> listaPermessiBrav = BravUtils.getBravPermessi(tipoFunzioneAreaBlu);
      log.debug("CP listaPermessiBrav = " + listaPermessiBrav);

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
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Genova Parcheggi"));
    }
  }

  private String getDescrizioneServizio() {
    BravFunzioni funzione = BravUtils.getFunzioneBrav(tipoFunzioneAreaBlu);
    return PageUtil.isStringValid(funzione.getDescrizioneFunz())
        ? funzione.getDescrizioneFunz()
        : "";
  }
}
