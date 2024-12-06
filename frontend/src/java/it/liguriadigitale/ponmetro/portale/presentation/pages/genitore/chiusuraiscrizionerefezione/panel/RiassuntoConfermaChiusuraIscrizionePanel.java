package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.ConfermaChiusuraIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.RichiestaChiusuraIscrizioneRefezionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoConfermaChiusuraIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = 6053212080610158792L;

  public Chiusuraiscrizionerefezione chiusuraiscrizionerefezione;
  public UtenteServiziRistorazione iscritto;

  public RiassuntoConfermaChiusuraIscrizionePanel(
      String id,
      Chiusuraiscrizionerefezione chiusuraiscrizionerefezione,
      UtenteServiziRistorazione iscritto) {
    super(id);
    createFeedBackPanel();
    this.chiusuraiscrizionerefezione = chiusuraiscrizionerefezione;
    this.iscritto = iscritto;

    fillDati(this.iscritto);
  }

  @SuppressWarnings({"rawtypes", "serial"})
  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    add(new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
    add(new Label("sesso", iscritto.getSesso()));
    add(new Label("codiceFiscale", iscritto.getCodiceFiscale()));
    add(new Label("residente", iscritto.getIndirizzoResidenza().toLowerCase()));
    add(new LocalDateLabel("dataNascita", iscritto.getDataNascita()));
    add(new NotEmptyLabel("luogoNascita", iscritto.getLuogoNascita().toLowerCase()));

    Label email = new Label("email", this.chiusuraiscrizionerefezione.getEmailContatto());
    add(email);
    Label telefono = new Label("telefono", this.chiusuraiscrizionerefezione.getTelefonoContatto());
    add(telefono);
    Label note = new Label("note", this.chiusuraiscrizionerefezione.getNote());
    add(note);

    LaddaAjaxLink<?> linkConfermaChiusuraIscrizione =
        new LaddaAjaxLink("confermaChiusuraIscrizione", Type.Primary) {

          @Override
          public void onClick(AjaxRequestTarget target) {
            log.debug("confermaChiusuraIscrizione ONCLICK ");
            target.add(getPage());
            setResponsePage(
                new ConfermaChiusuraIscrizionePage(chiusuraiscrizionerefezione, iscritto));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaChiusuraIscrizione.setLabel(
        Model.of(getString("RiassuntoConfermaChiusuraIscrizionePanel.prosegui")));
    add(linkConfermaChiusuraIscrizione);

    Link linkAnnullaChiusuraIscrizione =
        new Link("annullaChiusuraIscrizione") {
          @Override
          public void onClick() {
            setResponsePage(new RiepilogoIscrizioniPage());
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    add(linkAnnullaChiusuraIscrizione);

    Link linkIndietroChiusuraIscrizione =
        new Link("indietroChiusuraIscrizione") {

          @Override
          public void onClick() {
            setResponsePage(
                new RichiestaChiusuraIscrizioneRefezionePage(
                    iscritto, chiusuraiscrizionerefezione));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    add(linkIndietroChiusuraIscrizione);
  }
}
