package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import java.io.Serializable;

public class LinkDinamicoFunzioneDataBuilder implements Serializable {

  private static final long serialVersionUID = 3502395028169677756L;

  private String wicketLabelKeyTitle;
  private String wicketLabelKeyText;
  private String wicketClassName;
  private boolean targetInANewWindow;

  public LinkDinamicoFunzioneDataBuilder setLinkTitleAdditionalText(String wicketLabelKeyTitle) {
    this.wicketLabelKeyTitle = wicketLabelKeyTitle;
    return this;
  }

  public LinkDinamicoFunzioneDataBuilder setWicketLabelKeyText(String wicketLabelKeyText) {
    this.wicketLabelKeyText = wicketLabelKeyText;
    return this;
  }

  public LinkDinamicoFunzioneDataBuilder setWicketClassName(String wicketClassName) {
    this.wicketClassName = wicketClassName;
    return this;
  }

  public LinkDinamicoFunzioneDataBuilder setTargetInANewWindow(boolean targetInANewWindow) {
    this.targetInANewWindow = targetInANewWindow;
    return this;
  }

  public static LinkDinamicoFunzioneDataBuilder getInstance() {
    return new LinkDinamicoFunzioneDataBuilder();
  }

  public LinkDinamicoFunzioneData build() {

    LinkDinamicoFunzioneData dto = new LinkDinamicoFunzioneData();
    dto.setTargetInANewWindow(targetInANewWindow);
    dto.setWicketClassName(wicketClassName);
    dto.setWicketLabelKeyText(wicketLabelKeyText);
    dto.setWicketLabelKeyTitle(wicketLabelKeyTitle);
    return dto;
  }

  public LinkDinamicoFunzioneData build(LinkGloboMetadata linkGloboMetadata) {
    LinkDinamicoFunzioneData dto = new LinkDinamicoFunzioneData();
    dto.setTargetInANewWindow(linkGloboMetadata.isTargetInANewWindow());
    dto.setWicketClassName(linkGloboMetadata.getWicketClassName());
    dto.setWicketLabelKeyText(linkGloboMetadata.getWicketLabelKeyText());
    dto.setWicketLabelKeyTitle(linkGloboMetadata.getWicketLabelKeyTitle());
    return dto;
  }
}
