package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.noprintable;

import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;

public class BolloPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -7319009828739115221L;

  public BolloPage() {
    super();
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    // soluzione trovata qui:
    // https://stackoverflow.com/questions/6647392/how-to-stop-user-from-printing-webpages-using-javascript-or-jquery
    response.render(
        CssContentHeaderItem.forUrl(
            "/" + Constants.Componente.GESTORE.getWebContext() + "/css/" + "noPrint.css"));
  }
}
