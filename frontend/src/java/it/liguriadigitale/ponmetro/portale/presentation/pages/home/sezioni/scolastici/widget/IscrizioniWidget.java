package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici.widget;

import it.liguriadigitale.framework.presentation.components.link.BaseLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.lista.ElencaIscrizioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiIscrizioneServiziRistorazione.StatoIscrizioneServiziRistorazioneEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class IscrizioniWidget extends MyWidgetPanel {

  private static final long serialVersionUID = -7196795778911336753L;

  public IscrizioniWidget(POSIZIONE posizione) {
    super(posizione);
    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {

    ListView<UtenteServiziRistorazione> listView =
        new ListView<UtenteServiziRistorazione>("listview", getUtente().getListaFigli()) {

          private static final long serialVersionUID = -1949303234023054323L;

          @Override
          protected void populateItem(ListItem<UtenteServiziRistorazione> item) {
            UtenteServiziRistorazione iscritto = item.getModelObject();

            Label nomeCognome = new Label("nome", iscritto.getNome() + " " + iscritto.getCognome());
            Label presso =
                new Label(
                    "presso",
                    getStringTextIscrizione(iscritto) + " " + getStringTextPresso(iscritto) + " ");
            Label scuola = new Label("scuola", iscritto.getStrutturaScolastica());
            item.add(nomeCognome);
            item.add(presso);
            item.add(scuola);
          }
        };
    add(listView);
  }

  @SuppressWarnings({"unused", "rawtypes", "unchecked"})
  private void creaLinkVerificaIscrizioni() {
    Link<Void> linkCf = new BaseLink("cfLink", ElencaIscrizioniPage.class);
    add(linkCf);
  }

  private String getStringTextPresso(UtenteServiziRistorazione iscritto) {
    String res = "";

    if (iscritto != null
        && StatoIscrizioneServiziRistorazioneEnum.ACCETTATA
            .value()
            .equalsIgnoreCase(iscritto.getStatoIscrizioneServiziRistorazione())) {
      res = getString("IscrizioniWidget.presso");
    }

    return res;
  }

  @Override
  protected void mostraIcona() {

    generaIconeFamiglia();
  }

  private void generaIconeFamiglia() {}
}
