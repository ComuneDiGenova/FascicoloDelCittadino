package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel.breadcrumb;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.markup.html.basic.Label;

public class BreadcrumbRichiestaIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = 5352016807189771593L;

  public BreadcrumbRichiestaIscrizionePanel(String id, UtenteServiziRistorazione iscrizione) {
    super(id);

    if (iscrizione.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("ACCETTATA")) {
      add(new Label("breadcrumb", getString("BreadcrumbRichiestaIscrizionePanel.cambia")));
    } else {
      add(new Label("breadcrumb", getString("BreadcrumbRichiestaIscrizionePanel.iscrivi")));
    }
  }

  public BreadcrumbRichiestaIscrizionePanel(
      String id, UtenteServiziRistorazione utenteServiziRistorazione, Iscrizione iscrizione) {
    super(id);

    if (utenteServiziRistorazione
        .getStatoIscrizioneServiziRistorazione()
        .equalsIgnoreCase("ACCETTATA")) {
      add(new Label("breadcrumb", getString("BreadcrumbRichiestaIscrizionePanel.cambia")));
    } else {
      add(new Label("breadcrumb", getString("BreadcrumbRichiestaIscrizionePanel.iscrivi")));
    }
  }

  @Override
  public void fillDati(Object dati) {}
}
