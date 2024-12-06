package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.HeaderMieIstanzePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel.MieIstanzePanel;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class MieIstanzePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 3132515640663813274L;

  public MieIstanzePage() {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HeaderMieIstanzePanel headerMieIstanzePanel =
        (HeaderMieIstanzePanel)
            new HeaderMieIstanzePanel("headerMieIstanzePanel").setRenderBodyOnly(true);
    headerMieIstanzePanel.fillDati("");
    add(headerMieIstanzePanel);

    MieIstanzePanel mieIstanzePanel = new MieIstanzePanel("mieIstanzePanel");
    List<Istanza> listaIstanze = popolaListaIstanze();

    mieIstanzePanel.fillDati(listaIstanze);
    add(mieIstanzePanel);

    setOutputMarkupId(true);
  }

  /*
   *
   * { "CMP", "IN COMPILAZIONE", BasePanelGenericContent.CSS_BADGE_FDC_SECONDARY,
   * BasePanelGenericContent.CSS_COLOR_FDC_SECONDARY }, { "IVT", "INVIATA",
   * BasePanelGenericContent.CSS_BADGE_FDC_SUCCESS,
   * BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS }, { "PCR", "IN ELABORAZIONE",
   * BasePanelGenericContent.CSS_BADGE_FDC_WARNING,
   * BasePanelGenericContent.CSS_COLOR_FDC_WARNING }, { "ATT",
   * "IN ATTESA DI DOCUMENTAZIONE", BasePanelGenericContent.CSS_BADGE_FDC_DANGER,
   * BasePanelGenericContent.CSS_COLOR_FDC_DANGER }, { "EVA", "ACCOLTA",
   * BasePanelGenericContent.CSS_BADGE_FDC_DARK,
   * BasePanelGenericContent.CSS_COLOR_FDC_DARK }, { "EVP",
   * "ACCOLTA PARZIALMENTE", BasePanelGenericContent.CSS_BADGE_FDC_PRIMARY,
   * BasePanelGenericContent.CSS_COLOR_FDC_PRIMARY }, { "EVR", "RESPINTA",
   * BasePanelGenericContent.CSS_BADGE_FDC_LIGHT,
   * BasePanelGenericContent.CSS_COLOR_FDC_LIGHT }
   */
  public static String[][] getStati() {
    String[][] stati = {
      {
        "CMP",
        "IN COMPILAZIONE",
        BasePanelGenericContent.CSS_BADGE_FDC_SECONDARY,
        BasePanelGenericContent.CSS_COLOR_FDC_SECONDARY
      },
      {
        "IVT",
        "INVIATA",
        BasePanelGenericContent.CSS_BADGE_FDC_SUN,
        BasePanelGenericContent.CSS_COLOR_FDC_SUN
      },
      {
        "PCR",
        "IN ELABORAZIONE",
        BasePanelGenericContent.CSS_BADGE_FDC_WARNING,
        BasePanelGenericContent.CSS_COLOR_FDC_WARNING
      },
      {
        "ATT",
        "IN ATTESA DI DOCUMENTAZIONE",
        BasePanelGenericContent.CSS_BADGE_FDC_DARK,
        BasePanelGenericContent.CSS_COLOR_FDC_DARK
      },
      {
        "EVA",
        "ACCOLTA",
        BasePanelGenericContent.CSS_BADGE_FDC_SUCCESS,
        BasePanelGenericContent.CSS_COLOR_FDC_SUCCESS
      },
      {
        "EVP",
        "ACCOLTA PARZIALMENTE",
        BasePanelGenericContent.CSS_BADGE_FDC_PRIMARY,
        BasePanelGenericContent.CSS_COLOR_FDC_PRIMARY
      },
      {
        "EVR",
        "RESPINTA",
        BasePanelGenericContent.CSS_BADGE_FDC_DANGER,
        BasePanelGenericContent.CSS_COLOR_FDC_DANGER
      }
    };
    return stati;
  }

  public MieIstanzePage(List<Istanza> listaIstanze) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    // 3 - legend > eventuale legenda in base agli stati degli elementi del
    // contesto
    // add(new LegendBasePanel(getStati(), 7));

    HeaderMieIstanzePanel headerMieIstanzePanel =
        (HeaderMieIstanzePanel)
            new HeaderMieIstanzePanel("headerMieIstanzePanel").setRenderBodyOnly(true);
    headerMieIstanzePanel.fillDati("");
    add(headerMieIstanzePanel);

    MieIstanzePanel mieIstanzePanel =
        (MieIstanzePanel) new MieIstanzePanel("mieIstanzePanel").setRenderBodyOnly(true);
    mieIstanzePanel.fillDati(listaIstanze);
    add(mieIstanzePanel);
    setOutputMarkupId(true);
  }

  public MieIstanzePage(List<Istanza> listaIstanze, String codice) {
    super();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    HeaderMieIstanzePanel headerMieIstanzePanel =
        (HeaderMieIstanzePanel)
            new HeaderMieIstanzePanel("headerMieIstanzePanel").setRenderBodyOnly(true);
    headerMieIstanzePanel.fillDati("");
    add(headerMieIstanzePanel);

    MieIstanzePanel mieIstanzePanel =
        (MieIstanzePanel) new MieIstanzePanel("mieIstanzePanel", codice).setRenderBodyOnly(true);
    mieIstanzePanel.fillDati(listaIstanze);
    add(mieIstanzePanel);
    setOutputMarkupId(true);
  }

  private List<Istanza> popolaListaIstanze() {
    try {
      log.debug("CP popola lista istanze");
      return ServiceLocator.getInstance().getServiziMieIstanze().getIstanze(getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
    }
  }

  @SuppressWarnings("unused")
  private List<Istanza> popolaListaIstanzeyVerbali() {

    try {
      List<Verbale> listaVerbaliFiltratiDaBadge =
          ServiceLocator.getInstance().getServiziMieiVerbali().getTuttiVerbali(getUtente());

      HashMap<Long, Istanza> mappazza = new HashMap<Long, Istanza>();

      for (Verbale verbale : listaVerbaliFiltratiDaBadge) {
        // preleva dettagli per ogni verbale
        DettaglioVerbale dettaglio =
            ServiceLocator.getInstance()
                .getServiziMieiVerbali()
                .getDettaglioVerbale(verbale.getNumeroProtocollo(), getUtente());
        if (dettaglio == null) {
          continue;
        }
        for (Istanza istanzaVerbale : dettaglio.getIstanze()) {
          if (mappazza.get(istanzaVerbale.getId()) == null) {
            mappazza.put(istanzaVerbale.getId(), istanzaVerbale);
          }
        }
      }
      List<Istanza> istanzeUnivoche = new ArrayList<Istanza>();
      for (Istanza value : mappazza.values()) {
        istanzeUnivoche.add(value);
      }
      return istanzeUnivoche;
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le mie istanze"));
    }
  }
}
