package it.liguriadigitale.ponmetro.portale.presentation.pages.wizard.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.landing.RedirectBaseLandingPage;
import org.apache.wicket.markup.html.link.Link;

public class AutodichiarazioneGenitoreNonResidentePanel extends BasePanel {

  private static final long serialVersionUID = -6377093781357488746L;

  public AutodichiarazioneGenitoreNonResidentePanel(String id) {
    super(id);

    createFeedBackPanel();
    setOutputMarkupId(true);
    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    addOrReplace(creaBottoneHome());
  }

  private Link<Void> creaBottoneHome() {
    return new Link<Void>("home") {

      private static final long serialVersionUID = 1L;

      @Override
      public void onClick() {
        setResponsePage(RedirectBaseLandingPage.class);
      }
    };
  }
}
