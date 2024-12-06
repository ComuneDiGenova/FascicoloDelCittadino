package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.infotraffico.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class MappaInfoTrafficoPanel extends BasePanel {

  private static final long serialVersionUID = -5517127924822833296L;

  WebMarkupContainer container = new WebMarkupContainer("mappa");

  public MappaInfoTrafficoPanel(String id) {
    super(id);

    setOutputMarkupId(true);

    createFeedBackPanel();
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
  }

  @Override
  public void fillDati(Object dati) {
    try {
      List<DatiSegnalazioneTraffico> listaDatiSegnalazioneTraffico =
          ServiceLocator.getInstance()
              .getServiziSegnalazioneTraffico()
              .getListSegnalazioniTraffico();

      container.setOutputMarkupId(true);
      add(container);

      for (DatiSegnalazioneTraffico segnalazioneTraffico : listaDatiSegnalazioneTraffico) {
        if (segnalazioneTraffico.getLat() != 0.0 && segnalazioneTraffico.getLng() != 0.0) {

          Double lat = segnalazioneTraffico.getLat();
          Double lng = segnalazioneTraffico.getLng();
          String descrizione = segnalazioneTraffico.getDescrizione();

          container.add(
              new AbstractAjaxBehavior() {

                private static final long serialVersionUID = 8596050468290509546L;

                @Override
                public void renderHead(Component component, IHeaderResponse response) {
                  String aggiungiPunto =
                      "L.marker(["
                          + lat
                          + ","
                          + lng
                          + "]).addTo(map).bindPopup('"
                          + descrizione
                          + "')";
                  response.render(OnDomReadyHeaderItem.forScript(aggiungiPunto));
                  super.renderHead(component, response);
                }

                @Override
                public void onRequest() {}
              });
        }
      }

    } catch (BusinessException | ApiException e) {
      log.debug("errore");
    }
  }
}
