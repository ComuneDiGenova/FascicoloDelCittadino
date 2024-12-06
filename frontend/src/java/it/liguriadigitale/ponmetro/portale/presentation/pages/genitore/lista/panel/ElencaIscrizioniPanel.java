package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.lista.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.RichiestaIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.StatoIscrizionePage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIscrizioneServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class ElencaIscrizioniPanel extends BasePanel {

  private static final long serialVersionUID = 3086721082227556048L;

  public ElencaIscrizioniPanel(List<UtenteServiziRistorazione> lista) {
    super("listaPanel");
    log.debug("[ElencaIscrizioniPanel] -- lista=" + lista.size());

    String annoLabel = "n/d";
    add(new Label("anno", annoLabel));

    ListDataProvider<UtenteServiziRistorazione> dataProvider =
        new ListDataProvider<UtenteServiziRistorazione>(lista);
    DataView<UtenteServiziRistorazione> dataView = creaDataTable(dataProvider);
    BootstrapPagingNavigator unNavigator = new BootstrapPagingNavigator("navigator", dataView);
    if (dataView.getDataProvider().size() < 10) {
      unNavigator.setVisible(false);
      if (dataView.getDataProvider().size() < 1) {
        error("Non sono presenti iscrizioni.");
        log.debug("[ElencaIscrizioniPanel] Non sono presenti iscrizioni: lista=" + lista.size());
      }
    }
    add(unNavigator);
    add(dataView);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    // non utilizzato
  }

  private DataView<UtenteServiziRistorazione> creaDataTable(
      ListDataProvider<UtenteServiziRistorazione> dataProvider) {
    log.debug("[ElencaIscrizioniPanel]-- creaDataTable INIZIO=" + dataProvider.size());
    return new DataView<UtenteServiziRistorazione>("resultlist", dataProvider) {

      private static final long serialVersionUID = 2699892435561227586L;

      @Override
      protected void populateItem(Item<UtenteServiziRistorazione> item) {
        final UtenteServiziRistorazione iscrizione = item.getModelObject();
        Label labelNome = new Label("nome", iscrizione.getNome());
        Label labelCognome = new Label("cognome", iscrizione.getCognome());
        LocalDateLabel labelDataNascita =
            new LocalDateLabel("dataNascita", iscrizione.getDataNascita());
        LocalDateLabel labelDataIscrizione =
            new LocalDateLabel(
                "dataIscrizione",
                iscrizione.getDataIscrizioneServiziRistorazione() == null
                    ? null
                    : iscrizione.getDataIscrizioneServiziRistorazione());

        Label labelValidita =
            new Label("stato", iscrizione.getStatoIscrizioneServiziRistorazione());

        Link<Void> link =
            new Link<Void>("visualizza") {

              private static final long serialVersionUID = 4552994830193559102L;

              @Override
              public void onClick() {
                StatoIscrizionePage page = new StatoIscrizionePage(iscrizione);
                setResponsePage(page);
              }
            };

        Link<Void> linkIscrivi =
            new Link<Void>("iscrivi") {

              private static final long serialVersionUID = 4552994830193559103L;

              @Override
              public void onClick() {
                RichiestaIscrizionePage page = new RichiestaIscrizionePage(iscrizione);
                setResponsePage(page);
              }
            };

        link.setVisible(isIscritto(iscrizione));
        linkIscrivi.setVisible(!isIscritto(iscrizione));

        item.add(labelNome);
        item.add(labelCognome);
        item.add(labelDataNascita);
        item.add(labelDataIscrizione);
        item.add(labelValidita);
        item.add(link);
        item.add(linkIscrivi);
      }
    };
  }

  private boolean isIscritto(UtenteServiziRistorazione iscritto) {
    return (!StatoIscrizioneServiziRistorazioneEnum.NON_ISCRITTO
        .value()
        .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione()));
  }
}
