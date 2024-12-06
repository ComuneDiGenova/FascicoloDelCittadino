package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.avatar;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.avatar.panel.ConfigurazioneAvatarPanel;

public class ConfigurazioneAvatarPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -3694243152805745219L;

  public ConfigurazioneAvatarPage() {
    super();

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    ConfigurazioneAvatarPanel avatarPanel =
        new ConfigurazioneAvatarPanel("configurazioneAvatarPanel");
    avatarPanel.fillDati("");
    add(avatarPanel);
  }
}
