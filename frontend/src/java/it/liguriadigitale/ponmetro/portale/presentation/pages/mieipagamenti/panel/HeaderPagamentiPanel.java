package it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.pagamenti.mip.verticali.model.TipologiaEntrata;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.mip.MipErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mieipagamenti.PagamentiFormPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.MieiVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.QuadroTributarioTariPage;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class HeaderPagamentiPanel extends BasePanel {

  private static final long serialVersionUID = -6767901217649853607L;

  public HeaderPagamentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {

    WebMarkupContainer containerRicerca = new WebMarkupContainer("containerRicerca");
    containerRicerca.addOrReplace(creaBtnRicercaDebito());
    containerRicerca.setVisible(!listaServiziSenzaVerbaliSenzaTariEng().isEmpty());
    addOrReplace(containerRicerca);

    WebMarkupContainer containerMessaggiInfo = new WebMarkupContainer("containerMessaggiInfo");
    // containerMessaggiInfo.addOrReplace(creaLinkVerbali());
    containerMessaggiInfo.addOrReplace(creaLinkDinamicoVerbali());
    // containerMessaggiInfo.addOrReplace(creaLinkMiaTariEng());
    containerMessaggiInfo.addOrReplace(creaLinkDinamicoLaMiaTariEng());

    addOrReplace(containerMessaggiInfo);
  }

  private List<TipologiaEntrata> listaServizi() {
    try {
      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getListaTipologieEntrateServiziFiltrata(getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }

  private List<TipologiaEntrata> listaServiziSenzaVerbaliSenzaTariEng() {
    try {
      return ServiceLocator.getInstance()
          .getServiziMipGlobali()
          .getListaTipologieEntrateServiziFiltrataSenzaVerbaliSenzaTariEngSenzaTariNetribe(
              getUtente());
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API MIP GLOBALI", e);
      throw new RestartResponseAtInterceptPageException(MipErrorPage.class);
    }
  }

  private LaddaAjaxLink<Object> creaBtnRicercaDebito() {
    LaddaAjaxLink<Object> btnRicercaDebito =
        new LaddaAjaxLink<Object>("btnRicercaDebito", Type.Primary) {

          private static final long serialVersionUID = 3273904788904550962L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderPagamentiPanel.this);
            setResponsePage(new PagamentiFormPage(listaServiziSenzaVerbaliSenzaTariEng()));
          }
        };
    btnRicercaDebito.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("HeaderPagamentiPanel.cerca", HeaderPagamentiPanel.this)));

    return btnRicercaDebito;
  }

  private Component creaLinkDinamicoVerbali() {
    LinkDinamicoLaddaFunzione<Object> mieiVerbali =
        new LinkDinamicoLaddaFunzione<Object>(
            "mieiVerbali",
            new LinkDinamicoFunzioneData(
                "HeaderPagamentiPanel.verbali", "MieiVerbaliPage", "HeaderPagamentiPanel.verbali"),
            null,
            HeaderPagamentiPanel.this,
            "color-cyan col-auto icon-vigile-verbale");
    return mieiVerbali;
  }

  @SuppressWarnings("unused")
  private LaddaAjaxLink<Object> creaLinkVerbali() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("mieiVerbali", Type.Link) {

          private static final long serialVersionUID = -6850732240072422472L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderPagamentiPanel.this);
            setResponsePage(new MieiVerbaliPage());
          }
        };

    IconType iconType =
        new IconType("mieiVerbali") {

          private static final long serialVersionUID = 3646330326216350408L;

          @Override
          public String cssClassName() {
            return "icon-vigile-verbale color-cyan";
          }
        };

    linkImg.setSpinnerColor("#02c3c0");
    linkImg.setIconType(iconType);
    linkImg.setLabel(Model.of(getString("HeaderPagamentiPanel.verbali")));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }

  @SuppressWarnings("unused")
  private LaddaAjaxLink<Object> creaLinkMiaTariEng() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("tariEng", Type.Link) {

          private static final long serialVersionUID = -5578899442577142945L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(HeaderPagamentiPanel.this);
            setResponsePage(new QuadroTributarioTariPage());
          }
        };

    IconType iconType =
        new IconType("tariEng") {

          private static final long serialVersionUID = 2541807995122926136L;

          @Override
          public String cssClassName() {
            return "icon-cassonetto color-green";
          }
        };

    linkImg.setSpinnerColor("#008d8b");
    linkImg.setIconType(iconType);
    linkImg.setLabel(Model.of(getString("HeaderPagamentiPanel.tariEng")));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }

  private Component creaLinkDinamicoLaMiaTariEng() {
    LinkDinamicoLaddaFunzione<Object> tariEng =
        new LinkDinamicoLaddaFunzione<Object>(
            "tariEng",
            new LinkDinamicoFunzioneData(
                "HeaderPagamentiPanel.tariEng",
                "QuadroTributarioTariPage",
                "HeaderPagamentiPanel.tariEng"),
            null,
            HeaderPagamentiPanel.this,
            "color-green col-auto icon-cassonetto");
    return tariEng;
  }
}
