package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.riepilogo.panel;

import it.liguriadigitale.ponmetro.portale.pojo.enums.ScaricaPrivacyEnum;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzionePrivacySebina;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class RiepilogoPersonalizzazionePanel extends BasePanel {

  private static final long serialVersionUID = 6643296689162191688L;

  public RiepilogoPersonalizzazionePanel(String id) {
    super(id);
    setOutputMarkupId(true);
    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    LinkDinamicoLaddaFunzione<Object> linkConfiguraAvatar =
        new LinkDinamicoLaddaFunzione<>(
            "configuraAvatar",
            new LinkDinamicoFunzioneData(
                "RiepilogoPersonalizzazionePanel.avatar",
                "ConfigurazioneAvatarPage",
                "RiepilogoPersonalizzazionePanel.avatar"),
            null,
            RiepilogoPersonalizzazionePanel.this,
            "color-blue-spid col-auto icon-omino");

    LinkDinamicoLaddaFunzione<Object> linkConfiguraNotifiche =
        new LinkDinamicoLaddaFunzione<>(
            "configuraNotifiche",
            new LinkDinamicoFunzioneData(
                "RiepilogoPersonalizzazionePanel.notifiche",
                "ConfigurazioneNotifichePage",
                "RiepilogoPersonalizzazionePanel.notifiche"),
            null,
            RiepilogoPersonalizzazionePanel.this,
            "color-blue-spid col-auto icon-salva");

    LinkDinamicoLaddaFunzione<Object> linkConfiguraWidget =
        new LinkDinamicoLaddaFunzione<>(
            "configuraWidget",
            new LinkDinamicoFunzioneData(
                "RiepilogoPersonalizzazionePanel.widget",
                "ConfigurazioneWidgetPage",
                "RiepilogoPersonalizzazionePanel.widget"),
            null,
            RiepilogoPersonalizzazionePanel.this,
            "color-blue-spid col-auto icon-albo-pretorio");

    LinkDinamicoLaddaFunzione<Object> linkIlMioProfilo =
        new LinkDinamicoLaddaFunzione<>(
            "ilMioProfilo",
            new LinkDinamicoFunzioneData(
                "RiepilogoPersonalizzazionePanel.ilMioProfilo",
                "DatiSpidPage",
                "RiepilogoPersonalizzazionePanel.ilMioProfilo"),
            null,
            RiepilogoPersonalizzazionePanel.this,
            "color-blue-spid col-auto icon-omino");

    LinkDinamicoLaddaFunzionePrivacySebina<Object> linkPrivacyFdC =
        new LinkDinamicoLaddaFunzionePrivacySebina<Object>(
            "privacyFdC",
            new LinkDinamicoFunzioneData(
                "RiepilogoPersonalizzazionePanel.privacyFdC",
                "BibliotecheSebinaPrivacyPage",
                "RiepilogoPersonalizzazionePanel.privacyFdC"),
            null,
            RiepilogoPersonalizzazionePanel.this,
            "color-blue-spid col-auto icon-salva",
            true,
            ScaricaPrivacyEnum.PRIVACY_FDC);

    addOrReplace(linkConfiguraWidget);
    addOrReplace(linkConfiguraNotifiche);
    addOrReplace(linkConfiguraAvatar);
    addOrReplace(linkIlMioProfilo);
    addOrReplace(linkPrivacyFdC);
  }
}
