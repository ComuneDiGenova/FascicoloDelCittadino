package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.ConfermaIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.RichiestaIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoConfermaIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  public RiassuntoConfermaIscrizionePanel(Iscrizione iscrizione) {
    super("iscrizionePanel");
    createFeedBackPanel();
    fillDati(iscrizione);
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  public void fillDati(Object dati) {
    Iscrizione iscrizione = (Iscrizione) dati;
    UtenteServiziRistorazione iscritto = iscrizione.getUtente();

    log.debug("CP iscritto in riassunto iscrizione mensa = " + iscritto);

    add(new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
    add(new Label("sesso", iscritto.getSesso()));
    add(new Label("codiceFiscale", iscritto.getCodiceFiscale()));

    String indirizzoResidenza = "";
    if (iscritto.getIndirizzoResidenza() != null && !iscritto.getIndirizzoResidenza().isEmpty()) {
      indirizzoResidenza = iscritto.getIndirizzoResidenza().toLowerCase();
    }
    add(
        new Label("residente", indirizzoResidenza)
            .setVisible(indirizzoResidenza != null && !indirizzoResidenza.isEmpty()));

    // add(new NotEmptyLabel("nato",
    // getStringTextNato(iscritto).toLowerCase()));
    add(new LocalDateLabel("dataNascita", iscritto.getDataNascita()));

    String luogoNascita = "";
    if (PageUtil.isStringValid(iscritto.getLuogoNascita())) {
      luogoNascita = iscritto.getLuogoNascita().toLowerCase();
    }
    add(
        new NotEmptyLabel("luogoNascita", luogoNascita)
            .setVisible(PageUtil.isStringValid(iscritto.getLuogoNascita())));

    // TODO da togliere 1.3.4
    /*
     * Label dietaEpisodiAllergici = new Label("dietaEpisodiAllergici",
     * getString("radioDietaAllergici.si"));
     * dietaEpisodiAllergici.setVisible(iscrizione.isDietaEpisodiAllergici() );
     *
     * Label dietaMotiviReligiosi = new Label("dietaMotiviReligiosi",
     * getString("radioDietaReligiosi.si"));
     * dietaMotiviReligiosi.setVisible(iscrizione.isDietaMotiviReligiosi());
     *
     * Label dietaMotiviSanitari = new Label("dietaMotiviSanitari",
     * getString("radioDietaSanitari.si"));
     * dietaMotiviSanitari.setVisible(iscrizione.isDietaMotiviSanitari());
     *
     * Label nessunaDieta = new Label("nessunaDieta",
     * getString("radioDietaSanitari.no"));
     * nessunaDieta.setVisible(!iscrizione.isDietaMotiviSanitari() &&
     * !iscrizione.isDietaMotiviReligiosi() &&
     * !iscrizione.isDietaEpisodiAllergici());
     */

    String indirizzo = "";
    String comune = "";
    String cap = "";
    if (iscrizione.getIndirizzo() != null) {
      indirizzo = iscrizione.getIndirizzo();
    }
    if (iscrizione.getComune() != null) {
      comune = iscrizione.getComune();
    }
    if (iscrizione.getCap() != null) {
      cap = iscrizione.getCap();
    }
    // + " (" + comune + ")";
    String domiciliatoIn = indirizzo + " " + cap + " " + comune;
    Label domicilio = new Label("domicilio", domiciliatoIn);
    if (iscrizione.getIndirizzo() != null
        || iscrizione.getComune() != null
        || iscrizione.getCap() != null) {
      domicilio.setVisible(true);
    } else {
      domicilio.setVisible(false);
    }
    add(domicilio);

    Label email = new Label("email", iscrizione.getEmailContatto());
    add(email);

    // TODO da togliere 1.3.4
    /*
     * add(dietaEpisodiAllergici); add(dietaMotiviReligiosi);
     * add(dietaMotiviSanitari); add(nessunaDieta);
     */

    LaddaAjaxLink<?> linkConfermaIscrizione =
        new LaddaAjaxLink("confermaIscrizione", Type.Primary) {
          private static final long serialVersionUID = -8714430499348259886L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new ConfermaIscrizionePage(iscrizione));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };

    linkConfermaIscrizione.setLabel(
        Model.of(getString("RiassuntoConfermaIscrizionePanel.prosegui")));
    add(linkConfermaIscrizione);

    Link<?> linkAnnullaIscrizione =
        new Link("annullaIscrizione") {

          private static final long serialVersionUID = 2425663096920143739L;

          @Override
          public void onClick() {
            setResponsePage(new RiepilogoIscrizioniPage());
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };

    add(linkAnnullaIscrizione);

    Link linkIndietroIscrizione =
        new Link("indietroIscrizione") {

          private static final long serialVersionUID = -7121005129675952515L;

          @Override
          public void onClick() {
            UtenteServiziRistorazione utenteServiziRistorazione = iscrizione.getUtente();

            setResponsePage(new RichiestaIscrizionePage(utenteServiziRistorazione, iscrizione));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    add(linkIndietroIscrizione);
  }
}
