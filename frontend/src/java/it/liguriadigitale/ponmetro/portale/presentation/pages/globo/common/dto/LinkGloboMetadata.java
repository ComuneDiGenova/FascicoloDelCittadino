package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto;

import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import java.io.Serializable;
import java.util.List;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LinkGloboMetadata implements Serializable {

  private static final long serialVersionUID = 3502395028169677756L;

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

  public LinkGloboMetadata(
      String wicketLabelKeyText,
      String wicketClassName,
      String wicketLabelKeyTitle,
      boolean targetInANewWindow) {
    super();
    this.wicketLabelKeyTitle = wicketLabelKeyTitle;
    this.wicketLabelKeyText = wicketLabelKeyText;
    this.wicketClassName = wicketClassName;
    this.targetInANewWindow = targetInANewWindow;
  }

  public LinkGloboMetadata(
      String wicketLabelKeyText, String wicketClassName, String wicketLabelKeyTitle) {
    this(wicketLabelKeyText, wicketClassName, wicketLabelKeyTitle, false);
  }

  public LinkGloboMetadata() {
    super();
  }

  public String getWicketLabelKeyTitle() {
    return wicketLabelKeyTitle;
  }

  public void setWicketLabelKeyTitle(String wicketLabelKeyTitle) {
    this.wicketLabelKeyTitle = wicketLabelKeyTitle;
  }

  public String getWicketLabelKeyText() {
    return wicketLabelKeyText;
  }

  public void setWicketLabelKeyText(String wicketLabelKeyText) {
    this.wicketLabelKeyText = wicketLabelKeyText;
  }

  public String getWicketClassName() {
    return wicketClassName;
  }

  public void setWicketClassName(String wicketClassName) {
    this.wicketClassName = wicketClassName;
  }

  public boolean isTargetInANewWindow() {
    return targetInANewWindow;
  }

  public void setTargetInANewWindow(boolean targetInANewWindow) {
    this.targetInANewWindow = targetInANewWindow;
  }

  public String getCssIcona() {
    return cssIcona;
  }

  public void setCssIcona(String cssIcona) {
    this.cssIcona = cssIcona;
  }

  public PageParameters getPageParameters() {
    return pageParameters;
  }

  public void setPageParameters(PageParameters pageParameters) {
    this.pageParameters = pageParameters;
  }

  public boolean isLinkEsterno() {
    return isLinkEsterno;
  }

  public void setLinkEsterno(boolean isLinkEsterno) {
    this.isLinkEsterno = isLinkEsterno;
  }

  public Object getParametro() {
    return parametro;
  }

  public void setParametro(Object parametro) {
    this.parametro = parametro;
  }
}
