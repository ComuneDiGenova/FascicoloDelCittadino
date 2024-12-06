package it.liguriadigitale.ponmetro.portale.presentation.pages.error;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

public class ErroreServiziConStatusPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 6824050563624219155L;

  public ErroreServiziConStatusPage(String servizio, String title, String details) {
    log.debug("ErroreServiziConStatusPage: " + servizio + " \n " + title + " \n" + details);

    String messaggioDaMostrare = "";

    String descrizioneServizio = "Servizio ".concat(servizio).concat(" ");
    if (PageUtil.isStringValid(title)) {
      messaggioDaMostrare = descrizioneServizio.concat("\n").concat(title);
    }
    if (PageUtil.isStringValid(details)) {
      messaggioDaMostrare = messaggioDaMostrare.concat("\n").concat(details);
    }

    MultiLineLabel messaggioDettaglioErrore =
        new MultiLineLabel("messaggioDettaglioErrore", messaggioDaMostrare);

    addOrReplace(messaggioDettaglioErrore);
  }

  @Override
  public void renderPage() {
    if (hasBeenRendered()) {
      setResponsePage(getPageClass(), getPageParameters());
    } else {
      super.renderPage();
    }
  }
}
