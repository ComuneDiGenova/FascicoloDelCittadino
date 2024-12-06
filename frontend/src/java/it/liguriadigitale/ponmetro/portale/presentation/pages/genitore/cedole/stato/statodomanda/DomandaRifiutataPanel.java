package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1.CedoleLibrarieNuovaPage;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class DomandaRifiutataPanel extends Panel {

  private static final long serialVersionUID = 1922276247258571694L;

  public DomandaRifiutataPanel(String id, CedoleMinore cedolaMinore) {
    super(id);

    mostraElementi(cedolaMinore);
  }

  private void mostraElementi(CedoleMinore cedolaMinore) {
    //
    Cedola cedola = cedolaMinore.getCedola();
    add(new NotEmptyLabel("motivoRifiuto", cedola.getMotivazioneRifiuto()));
    add(new LocalDateLabel("data", cedola.getDataDomanda()));

    Link<Void> presentaDomanda =
        new Link<Void>("nuova") {

          private static final long serialVersionUID = -3889320225439443094L;

          @Override
          public void onClick() {
            DomandaCedola domanda =
                DomandaNonPresentataPanel.convertiIscrittoCedola(cedolaMinore.getMinore());
            setResponsePage(new CedoleLibrarieNuovaPage(cedolaMinore, domanda));
          }
        };
    add(presentaDomanda);
  }
}
