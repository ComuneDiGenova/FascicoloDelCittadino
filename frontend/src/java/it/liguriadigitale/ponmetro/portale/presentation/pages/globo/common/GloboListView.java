package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.LinkDinamicoGloboConLink;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class GloboListView extends Panel {
  private static final Log log = LogFactory.getLog(GloboListView.class);

  private static final String GLOBO_PAGE = "GloboPage";
  private static final String WICKET_ID_LINK_DINAMICO = "linkDinamico";
  private static final long serialVersionUID = 5829686778298899617L;
  private Long idSezione;
  private Boolean visualizzaTutteFunzioni;

  private List<FunzioniDisponibili> listaFunzioniDisponibili;

  private boolean isListaFunioniPreparata;

  private boolean checkBackBrowser = true;

  public GloboListView(String id, Long idSezione, Boolean visualizzaTutteFunzioni) {
    super(id);
    log.debug("GloboListView costruttore 1");
    this.checkBackBrowser = false;
    this.idSezione = idSezione;
    this.isListaFunioniPreparata = false;
    this.visualizzaTutteFunzioni = visualizzaTutteFunzioni;
    this.listaFunzioniDisponibili = getListaGLobo();
  }

  public GloboListView(String id, Long idSezione) {
    this(id, idSezione, false);
  }

  public GloboListView(String id, List<FunzioniDisponibili> listaFunzioniDisponibili) {
    super(id);
    log.debug("GloboListView costruttore 2");
    log.debug("listaFunzioniDisponibili =\n" + listaFunzioniDisponibili);
    this.checkBackBrowser = false;
    this.visualizzaTutteFunzioni = true;
    this.isListaFunioniPreparata = true;
    this.idSezione = listaFunzioniDisponibili.get(0).getIdSez();
    this.listaFunzioniDisponibili = listaFunzioniDisponibili;
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug(
        "onConfigure GloboListView\nidSezione = "
            + idSezione
            + "\nisListaFunioniPreparata = "
            + isListaFunioniPreparata
            + "\nvisualizzaTutteFunzioni = "
            + visualizzaTutteFunzioni
            + "\nlistaFunzioniDisponibili = "
            + listaFunzioniDisponibili
            + "\ncheckBackBrowser = "
            + checkBackBrowser);
    boolean isPulsantiPresenti = creaPulsantiGlobo();
    setVisible(isPulsantiPresenti);
  }

  private boolean creaPulsantiGlobo() {

    log.debug("Crea Pulsanti Globo Lista =" + listaFunzioniDisponibili);

    List<CfgTCatFunzLink> listaLinkEsterni = getListaLinkPerSezione();

    ListView<FunzioniDisponibili> listview =
        new ListView<FunzioniDisponibili>("lista", listaFunzioniDisponibili) {

          private static final long serialVersionUID = -3917872072845773346L;

          @Override
          protected void populateItem(ListItem<FunzioniDisponibili> item) {

            item.setRenderBodyOnly(true);
            FunzioniDisponibili funzione = item.getModelObject();
            log.debug("FUNZIONE=" + funzione.getClassePaginaStd());

            aggiungiListaLinkEsterni(listaLinkEsterni, funzione);

            boolean condizioneStampaLinkDinamico =
                ((GLOBO_PAGE.equalsIgnoreCase(funzione.getClassePaginaStd()))
                    || (visualizzaTutteFunzioni && nonFunzioneFiglio(funzione)));

            log.debug(
                "FUNZIONE 1:\nClasse pagina std= "
                    + funzione.getClassePaginaStd()
                    + "\nIcona "
                    + funzione.getIconaCss()
                    + "\nIdFunz "
                    + funzione.getIdFunz()
                    + "\nTitle "
                    + funzione.getWicketTitleStd()
                    + "\nLink Esterni "
                    + funzione.getListaLinkEsterni()
                    + "\nDenominazione "
                    + funzione.getDenominazioneFunz()
                    + "\nRisultato if = "
                    + condizioneStampaLinkDinamico
                    + "\nvisualizzaTutteFunzioni = "
                    + visualizzaTutteFunzioni
                    + "\nnonFunzioneFiglio(funzione) = "
                    + nonFunzioneFiglio(funzione)
                    + "\n(GLOBO_PAGE.equalsIgnoreCase(funzione.getClassePaginaStd())) = "
                    + (GLOBO_PAGE.equalsIgnoreCase(funzione.getClassePaginaStd()))
                    + "\n(visualizzaTutteFunzioni && nonFunzioneFiglio(funzione)) = "
                    + (visualizzaTutteFunzioni && nonFunzioneFiglio(funzione))
                    + "\nisListaFunioniPreparata = "
                    + isListaFunioniPreparata);

            if (condizioneStampaLinkDinamico) {
              log.debug(
                  "\nidFUNZ= "
                      + funzione.getIdFunz()
                      + "\nclassePaginaALT= "
                      + funzione.getClassePaginaAlt()
                      + "\nclassePaginaSTD= "
                      + funzione.getClassePaginaStd());

              aggiungiOggettoLinkEsterno(funzione);

              LinkGloboMetadata linkDinamicoFunzioneData =
                  LinkGloboMetadataBuilder.getInstance().build(funzione);

              /*log.debug("LINKDINAMICOFUNZIONEDATA :\nClassName std= "+ linkDinamicoFunzioneData.getWicketClassName()
              + "\nIcona " + linkDinamicoFunzioneData.getCssIcona()
              + "\nLabelKeyText " + linkDinamicoFunzioneData.getWicketLabelKeyText()
              + "\nLabelKetTitle " + linkDinamicoFunzioneData.getWicketLabelKeyTitle()
              + "\nLink Esterni " + linkDinamicoFunzioneData.getListaLinkEsterni()
              + "\nPageParameters " + linkDinamicoFunzioneData.getPageParameters()
              + "\nParametro " + linkDinamicoFunzioneData.getParametro());*/

              boolean isVisibileSeDelegato = isVisibileSeDelegato(funzione);
              log.debug("isVisibileSeDelegato=" + isVisibileSeDelegato);

              LinkDinamicoGloboConLink<Object> link =
                  new LinkDinamicoGloboConLink<>(
                      WICKET_ID_LINK_DINAMICO,
                      linkDinamicoFunzioneData,
                      null,
                      GloboListView.this,
                      isVisibileSeDelegato);
              item.add(link);
            } else {
              log.debug(
                  "condizioneStampaLinkDinamico = false per funzione con id = "
                      + funzione.getIdFunz());

              EmptyPanel panelVuoto = new EmptyPanel(WICKET_ID_LINK_DINAMICO);
              panelVuoto.setVisible(false);
              panelVuoto.setRenderBodyOnly(true);
              item.add(panelVuoto);
            }
          }

          private void aggiungiOggettoLinkEsterno(FunzioniDisponibili funzione) {
            if (funzione.getClassePaginaAlt() != null
                && funzione.getClassePaginaStd() != null
                && !funzione.getClassePaginaStd().equalsIgnoreCase("GloboPage")
                && funzione.getClassePaginaAlt().contains("https:")) {

              creaOggettoPerLinkEsternoTipoFunzione(funzione);
            }
          }

          private void aggiungiListaLinkEsterni(
              List<CfgTCatFunzLink> listaLinkEsterni, FunzioniDisponibili funzione) {
            if (isFunzioneGloboPadreOInvalida(funzione)) {
              log.debug("Link esterno NON aggiunto per ID_FUNZ = [ " + funzione.getIdFunz() + " ]");
              funzione.setListaLinkEsterni(new ArrayList<CfgTCatFunzLink>());
            } else {
              log.debug("Link esterno aggiunto per ID_FUNZ = [ " + funzione.getIdFunz() + " ]");
              funzione.setListaLinkEsterni(
                  getListaLinkPerFunzione(
                      listaLinkEsterni, funzione.getIdFunz(), idSezione == null));
            }
          }

          private void creaOggettoPerLinkEsternoTipoFunzione(FunzioniDisponibili funzione) {
            log.debug(
                "ClassPaginaSTD = "
                    + funzione.getClassePaginaStd()
                    + " ha un url in ClassePaginaAlt = "
                    + funzione.getClassePaginaAlt());
            CfgTCatFunzLink link = new CfgTCatFunzLink();
            link.setDescrizioniTooltip(funzione.getWicketTitleStd());
            link.setFlagAbilitazione(funzione.getFlagAbilitazioneFunz());
            link.setFlagResidente(funzione.getFlagResidente());
            link.setFlagNonresidente(funzione.getFlagNonResidente());
            link.setIconaCss(funzione.getIconaCss());
            link.setIdFunz(new BigDecimal(funzione.getIdFunz()));
            link.setIdLink(new BigDecimal(0));
            link.setIdStatoRec(funzione.getIdStatoRec());
            link.setTipoLink("funzione");
            link.setUrl(funzione.getClassePaginaAlt());
            List<CfgTCatFunzLink> listaConLink = new ArrayList<>();
            if (!funzione.getListaLinkEsterni().isEmpty()) {
              listaConLink = funzione.getListaLinkEsterni();
            }
            listaConLink.add(link);
            funzione.setListaLinkEsterni(listaConLink);
          }
        };
    addOrReplace(listview);
    listview.setRenderBodyOnly(true);
    return (!listaFunzioniDisponibili.isEmpty());
  }

  protected boolean isVisibileSeDelegato(FunzioniDisponibili funzione) {
    if (getUtente() != null && getUtente().isUtenteDelegato()) {
      return funzione.getIdDelega() > 0;
    } else {
      return true;
    }
  }

  private Utente getUtente() {
    LoginInSession loginSession = (LoginInSession) getSession();
    Utente utente =
        loginSession != null && loginSession.getUtente() != null ? loginSession.getUtente() : null;
    return utente;
  }

  private boolean nonFunzioneFiglio(FunzioniDisponibili funzione) {
    if (funzione.getClassePaginaAlt() != null && funzione.getClassePaginaAlt().contains("https:")) {
      return true;
    } else {
      return funzione.getIdFunzPadre() == null;
    }
  }

  private List<FunzioniDisponibili> getListaGLobo() {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    return ServiceLocator.getInstance()
        .getServiziHomePage()
        .getFunzioniBySezione(pagineAbilitate, idSezione);
  }

  private List<CfgTCatFunzLink> getListaLinkPerFunzione(
      List<CfgTCatFunzLink> lista, Long idFunzione, boolean isPaginaGlobo) {

    log.debug("idFunz = " + idFunzione + " " + "lista = " + lista);
    BigDecimal idFunzioneBig = BigDecimal.valueOf(idFunzione);
    List<CfgTCatFunzLink> listaFiltrata = new ArrayList<CfgTCatFunzLink>();
    if (lista.isEmpty()) {
      log.debug("lista in getListaLinkPerFunzione is empty");
      return listaFiltrata;
    } else {
      log.debug("idFunzioneBig = " + idFunzioneBig);
      if (isPaginaGlobo) {
        for (CfgTCatFunzLink link : lista) {
          if (link.getCodiceMaggioli().equals(idFunzioneBig)) {
            link.setIdFunz(link.getCodiceMaggioli());
            listaFiltrata.add(link);
          }
        }
      } else {
        listaFiltrata =
            lista.stream()
                .filter(c -> idFunzioneBig.equals(c.getIdFunz()))
                .collect(Collectors.toList());
      }
      log.debug("lista is not empty e listaFiltrara e " + listaFiltrata);
    }
    log.debug(
        "listaFiltrata dopo if con idFunzioneBig ="
            + idFunzioneBig
            + " e listaFiltrata = "
            + listaFiltrata);
    return listaFiltrata;
  }

  private List<CfgTCatFunzLink> getListaLinkPerSezione() {
    List<CfgTCatFunzLink> lista = new ArrayList<>();
    try {
      if (idSezione != null) {
        lista =
            ServiceLocatorLivelloUno.getInstance()
                .getApiHomePage()
                .getLinkEsterni(String.valueOf(idSezione));
      }
      log.debug("Link Esterni Trovati = " + lista);
    } catch (BusinessException e) {
      log.error("Errore durante la chiamata ad APi: ", e);
    }
    return lista;
  }

  private boolean isFunzioneGloboPadreOInvalida(FunzioniDisponibili funzione) {
    try {
      if (funzione.getIdFunz() == null) {
        return true;
      }

      if (PageUtil.isStringValid(funzione.getClassePaginaStd())
          && funzione.getClassePaginaStd().equalsIgnoreCase(GLOBO_PAGE)) {

        List<VCfgTCatGlobo> listaFunzioni = new ArrayList<VCfgTCatGlobo>();
        listaFunzioni =
            ServiceLocator.getInstance().getGlobo().getFunzioni().stream()
                .filter(elem -> elem.getIdFunz().equals(funzione.getIdFunz()))
                .collect(Collectors.toList());

        if (listaFunzioni != null && listaFunzioni.size() > 1) {
          log.debug(
              "FUNZIONE ID [ "
                  + funzione.getIdFunz()
                  + " ] ha piu di un riferimento globo in VCfgTCatFunzGlobo");
          return true;
        }

        if (listaFunzioni.get(0) != null
            && !listaFunzioni
                .get(0)
                .getDenominazioneFunz()
                .equalsIgnoreCase(funzione.getDescrizioneFunz())) {
          return true;
        }

        List<CfgTCatFunzLink> lista =
            ServiceLocatorLivelloUno.getInstance()
                .getApiHomePage()
                .getLinkEsterniPerIdFunz(String.valueOf(funzione.getIdFunz()))
                .stream()
                .filter(elem -> elem.getFlagAbilitazione())
                .collect(Collectors.toList());

        if (lista != null && lista.size() > 1) {
          log.debug(
              "FUNZIONE ID [ " + funzione.getIdFunz() + " ] ha piu di un link in CfgTCatFunzLink");
          return true;
        }
      }

      return false;
    } catch (BusinessException e) {
      log.error("ERRORE in GloboListView > moreThanOneIdfunz = " + e);
      return true;
    }
  }
}
