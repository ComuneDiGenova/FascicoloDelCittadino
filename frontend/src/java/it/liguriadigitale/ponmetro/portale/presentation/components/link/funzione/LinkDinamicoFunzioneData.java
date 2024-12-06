package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import java.io.Serializable;

public class LinkDinamicoFunzioneData implements Serializable {

  private static final long serialVersionUID = 3502395028169677756L;

  private String wicketLabelKeyTitle;
  private String wicketLabelKeyText;
  private String wicketClassName;
  private boolean targetInANewWindow;

  public LinkDinamicoFunzioneData(
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

  public LinkDinamicoFunzioneData(
      String wicketLabelKeyText, String wicketClassName, String wicketLabelKeyTitle) {
    this(wicketLabelKeyText, wicketClassName, wicketLabelKeyTitle, false);
  }

  public LinkDinamicoFunzioneData() {
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
}
