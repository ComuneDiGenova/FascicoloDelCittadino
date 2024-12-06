package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Collegamento;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LinkGloboMetadataBuilder implements Serializable {

  private static final long serialVersionUID = 3502395028169677756L;
  private Log log = LogFactory.getLog(getClass());

  private String wicketLabelKeyTitle;
  private String wicketLabelKeyText;
  private String wicketClassName;
  private boolean targetInANewWindow;
  private String cssIcona;
  private PageParameters pageParameters;
  private boolean isLinkEsterno;
  private Object parametro;
  private List<CfgTCatFunzLink> listaLinkEsterni;

  public List<CfgTCatFunzLink> getListaLinkEsterni() {
    return listaLinkEsterni;
  }

  public void setListaLinkEsterni(List<CfgTCatFunzLink> listaLinkEsterni) {
    this.listaLinkEsterni = listaLinkEsterni;
  }

  public LinkGloboMetadataBuilder setLinkEsterno(boolean isLinkEsterno) {
    this.isLinkEsterno = isLinkEsterno;
    return this;
  }

  public LinkGloboMetadataBuilder setParametro(Object parametro) {
    this.parametro = parametro;
    return this;
  }

  public LinkGloboMetadataBuilder setLinkTitleAdditionalText(String wicketLabelKeyTitle) {
    this.wicketLabelKeyTitle = wicketLabelKeyTitle;
    return this;
  }

  public LinkGloboMetadataBuilder setWicketLabelKeyText(String wicketLabelKeyText) {
    this.wicketLabelKeyText = wicketLabelKeyText;
    return this;
  }

  public LinkGloboMetadataBuilder setWicketClassName(String wicketClassName) {
    this.wicketClassName = wicketClassName;
    return this;
  }

  public LinkGloboMetadataBuilder setTargetInANewWindow(boolean targetInANewWindow) {
    this.targetInANewWindow = targetInANewWindow;
    return this;
  }

  public LinkGloboMetadataBuilder setCssIcona(String cssIcona) {
    this.cssIcona = cssIcona;
    return this;
  }

  public LinkGloboMetadataBuilder setPageParameters(PageParameters pageParameters) {
    this.pageParameters = pageParameters;
    return this;
  }

  public static LinkGloboMetadataBuilder getInstance() {
    return new LinkGloboMetadataBuilder();
  }

  public LinkGloboMetadata build() {

    LinkGloboMetadata dto = new LinkGloboMetadata();
    dto.setTargetInANewWindow(targetInANewWindow);
    dto.setWicketClassName(wicketClassName);
    dto.setWicketLabelKeyText(wicketLabelKeyText);
    dto.setWicketLabelKeyTitle(wicketLabelKeyTitle);
    dto.setCssIcona(cssIcona);
    dto.setPageParameters(pageParameters);
    dto.setParametro(parametro);
    dto.setLinkEsterno(isLinkEsterno);
    dto.setListaLinkEsterni(listaLinkEsterni);
    return dto;
  }

  public LinkGloboMetadata build(Node record, String iconaCss) {
    log.debug("Record=" + record);
    String titolo = record.getTitolo();
    log.debug("titolo=" + titolo);
    titolo = StringUtils.replace(titolo, "/", "-");
    if (isFunzioneGlobo(record)) {
      log.debug("isFunzioneGlobo=" + titolo);
      this.setWicketClassName("GloboPage");
      this.setLinkTitleAdditionalText(titolo);
      this.setWicketLabelKeyText(titolo);
      this.setCssIcona(iconaCss);
      this.setPageParameters(record);
    } else {
      this.setLinkTitleAdditionalText(titolo);
      this.setWicketLabelKeyText(titolo);
      this.setCssIcona(iconaCss);
      this.setWicketClassName("GloboRedirectPage");
      this.setLinkEsterno(true);
      if (!record.getUrl_servizio().isEmpty()) {
        Collegamento collegamento = record.getUrl_servizio().get(0);
        log.debug("URL:" + collegamento);
        String path = getPathApplicazioneCorretto(collegamento);
        this.setParametro(path);
      }
    }
    return build();
  }

  private boolean isFunzioneGlobo(Node record) {
    return record.getCodice_maggioli() != 0L;
  }

  private String getPathApplicazioneCorretto(Collegamento collegamento) {
    String path = collegamento.getPath();
    if (path.contains("fascicolo-test.comune.genova.it")) {
      path = path.replace("https://fascicolo-test.comune.genova.it/portale/web/", "");
      path = "../" + path;
    } else if (path.contains("fascicolo.comune.genova.it")) {
      path = path.replace("https://fascicolo.comune.genova.it/portale/web/", "");
      path = "../" + path;
    }
    log.debug("PROVA: " + path);
    return path;
  }

  public void setPageParameters(Node record) {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_FUNZIONE.name(), "na");
    parameters.set(GloboPathParametersName.ID_PROCEDIMENTO.name(), record.getCodice_maggioli());
    log.debug(" record.getCodice_maggioli()=" + record.getCodice_maggioli());
    this.setPageParameters(parameters);
  }

  public LinkGloboMetadata build(FunzioniDisponibili funzione) {
    this.setWicketClassName(funzione.getClassePaginaStd());
    this.setWicketLabelKeyTitle(funzione);
    this.setWicketLabelKeyText(funzione);
    this.setCssIcona(funzione.getIconaCss());
    this.setPageParameters(funzione);
    this.setListaLinkEsterni(funzione.getListaLinkEsterni());
    return build();
  }

  public LinkGloboMetadata build(VCfgTCatGlobo funzioneGlobo) {
    String denominazioneProcedimento = funzioneGlobo.getDenominazioneProcedimento();
    denominazioneProcedimento = StringUtils.replace(denominazioneProcedimento, "/", "-");
    this.setWicketClassName(funzioneGlobo.getClassePaginaStd());
    this.setWicketLabelKeyText(denominazioneProcedimento);
    this.setLinkTitleAdditionalText(denominazioneProcedimento);
    this.setCssIcona(funzioneGlobo.getIconaCss());
    this.setPageParameters(funzioneGlobo);
    return build();
  }

  public LinkGloboMetadata build(CfgTCatFunzLink funzioneEsterna) {
    this.setWicketClassName("CreaLinkConUrlPage");
    this.setWicketLabelKeyText(funzioneEsterna.getDescrizioniTooltip());
    this.setLinkTitleAdditionalText(funzioneEsterna.getDescrizioniTooltip());
    this.setCssIcona(funzioneEsterna.getIconaCss());
    this.setParametro(funzioneEsterna);
    List<CfgTCatFunzLink> list = new ArrayList<CfgTCatFunzLink>();
    list.add(funzioneEsterna);
    this.setListaLinkEsterni(list);
    this.setPageParameters(funzioneEsterna);
    this.setTargetInANewWindow(true);
    this.setLinkEsterno(true);
    return build();
  }

  public LinkGloboMetadata build(BreadcrumbFdC breadcrumb) {
    log.debug("breadcrumb.getFunzione()!=null:" + breadcrumb.getFunzione() != null);
    if (breadcrumb.getFunzione() != null) {
      log.debug(
          "breadcrumb.getFunzione().getClassePaginaStd():"
              + breadcrumb.getFunzione().getClassePaginaStd());
      this.setWicketClassName(breadcrumb.getFunzione().getClassePaginaStd());
    }

    this.setWicketLabelKeyText(breadcrumb.getDescrizione());

    return build();
  }

  private void setPageParameters(VCfgTCatGlobo funzioneGlobo) {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_FUNZIONE.name(), funzioneGlobo.getIdFunz());
    parameters.set(
        GloboPathParametersName.ID_PROCEDIMENTO.name(), funzioneGlobo.getIdProcedimento());
    this.setPageParameters(parameters);
  }

  private void setPageParameters(FunzioniDisponibili funzione) {
    PageParameters parameters = new PageParameters();
    parameters.set(GloboPathParametersName.ID_FUNZIONE.name(), funzione.getIdFunz());
    this.setPageParameters(parameters);
  }

  private void setPageParameters(CfgTCatFunzLink funzione) {
    PageParameters parameters = new PageParameters();
    parameters
        .set("url", funzione.getUrl())
        .set("idLink", funzione.getIdLink().toString())
        .set("idFunz", funzione.getIdFunz().toString())
        .set("tipoLink", funzione.getTipoLink())
        .set("descrizioniTooltip", funzione.getDescrizioniTooltip())
        .set("iconaCss", funzione.getIconaCss())
        .set("flagAbilitazione", funzione.getFlagAbilitazione().toString())
        .set("idStatoRec", funzione.getIdStatoRec().toString())
        .set("flagResidente", funzione.getFlagResidente().toString())
        .set("flagNonresidente", funzione.getFlagNonresidente().toString());
    this.setPageParameters(parameters);
  }

  private void setWicketLabelKeyTitle(FunzioniDisponibili funzione) {
    String wicketTitleStd = funzione.getWicketTitleStd();
    if (StringUtils.isEmpty(wicketLabelKeyText)) {
      wicketLabelKeyText = funzione.getDescrizioneFunz();
    }
    this.setLinkTitleAdditionalText(wicketTitleStd);
  }

  private void setWicketLabelKeyText(FunzioniDisponibili funzione) {
    String wicketLabelIdStd = funzione.getWicketLabelIdStd();
    if (StringUtils.isEmpty(wicketLabelIdStd)) {
      wicketLabelIdStd = funzione.getDescrizioneFunz();
    }
    this.setWicketLabelKeyText(wicketLabelIdStd);
  }

  public LinkGloboMetadata build(PraticaGlobo record, String iconaCss) {
    this.setLinkTitleAdditionalText(record.getDescrizioneModulo());
    this.setWicketLabelKeyText(record.getDescrizioneModulo());
    this.setCssIcona(iconaCss);
    this.setWicketClassName("GloboRedirectPage");
    this.setLinkEsterno(true);
    if (!StringUtils.isEmpty(record.getUrl())) {
      String collegamento = BaseServiceImpl.URL_SSO_GLOBO + record.getUrl();
      log.debug("URL:" + collegamento);
      this.setParametro(collegamento);
    }
    this.setTargetInANewWindow(true);
    return build();
  }
}
