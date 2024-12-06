package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.GenovaParcheggiPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class PagoPAGeparkKOPanel extends BasePanel {

  private static final long serialVersionUID = -4857339621043730557L;

  public PagoPAGeparkKOPanel(String id) {
    super(id);

    addOrReplace(creaBtnTornaAllaHome());

    createFeedBackPanel();
  }

  private LaddaAjaxLink<Object> creaBtnTornaAllaHome() {

    LaddaAjaxLink<Object> btnAzione =
        new LaddaAjaxLink<Object>("btnTornaAllaHome", Type.Primary) {

          private static final long serialVersionUID = 5695062771060141898L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new GenovaParcheggiPage());
          }
        };
    btnAzione.setLabel(Model.of("Ritorna alla pagina Iniziale"));

    return btnAzione;
  }

  @Override
  public void fillDati(Object dati) {}
}
