package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.panel;

import it.liguriadigitale.ponmetro.portale.pojo.chiusuraiscrizionerefezione.Chiusuraiscrizionerefezione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.RiassuntoConfermaChiusuraIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.chiusuraiscrizionerefezione.form.RichiestaChiusuraIscrizioneRefezioneForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiStatoDomandaChiusuraServiziRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class RichiestaChiusuraIscrizioneRefezionePanel extends BasePanel {

  private static final long serialVersionUID = -6171815322396815634L;

  private DatiStatoDomandaChiusuraServiziRistorazione datiChiusura;
  private UtenteServiziRistorazione iscritto;
  private Chiusuraiscrizionerefezione chiusura;

  public RichiestaChiusuraIscrizioneRefezionePanel(
      String id,
      final UtenteServiziRistorazione iscritto,
      final DatiStatoDomandaChiusuraServiziRistorazione datiChiusura) {
    super(id);
    setOutputMarkupId(true);
    this.iscritto = iscritto;
    this.datiChiusura = datiChiusura;
    createFeedBackPanel();
    initPanel();
  }

  public RichiestaChiusuraIscrizioneRefezionePanel(
      String id,
      final UtenteServiziRistorazione iscritto,
      final DatiStatoDomandaChiusuraServiziRistorazione datiChiusura,
      Chiusuraiscrizionerefezione chiusura) {
    super(id);
    setOutputMarkupId(true);
    this.iscritto = iscritto;
    this.datiChiusura = datiChiusura;
    this.chiusura = chiusura;
    createFeedBackPanel();
    initPanel();
  }

  private void initPanel() {
    fillDati(this.iscritto);
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    add(
        new Label(
            "titleChiusuraRistorazione",
            getString("RichiestaChiusuraIscrizioneRefezionePanel.titleDisiscrivi")));

    add(new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
    add(new Label("sesso", iscritto.getSesso()));
    add(new Label("codiceFiscale", iscritto.getCodiceFiscale()));
    add(new Label("residente", iscritto.getIndirizzoResidenza().toLowerCase()));
    // add(new NotEmptyLabel("nato",
    // getStringTextNato(iscritto).toLowerCase()));
    add(new LocalDateLabel("dataNascita", iscritto.getDataNascita()));
    add(new NotEmptyLabel("luogoNascita", iscritto.getLuogoNascita().toLowerCase()));

    Chiusuraiscrizionerefezione chiusuraiscrizionerefezione = new Chiusuraiscrizionerefezione();
    chiusuraiscrizionerefezione.setUtente(iscritto);
    chiusuraiscrizionerefezione.setDatiChiusura(this.datiChiusura);

    if (chiusura != null) {
      chiusuraiscrizionerefezione.setEmailContatto(chiusura.getEmailContatto());
      chiusuraiscrizionerefezione.setNote(chiusura.getNote());
      chiusuraiscrizionerefezione.setTelefonoContatto(chiusura.getTelefonoContatto());
    } else {
      chiusuraiscrizionerefezione.setEmailContatto(getUtente().getMail());
      chiusuraiscrizionerefezione.setNote("");
      chiusuraiscrizionerefezione.setTelefonoContatto("");
    }

    RichiestaChiusuraIscrizioneRefezioneForm form = null;

    if (chiusuraiscrizionerefezione != null) {
      form =
          new RichiestaChiusuraIscrizioneRefezioneForm(
              "form", chiusuraiscrizionerefezione, this, getUtente()) {

            private static final long serialVersionUID = 8358733454696272214L;

            @Override
            protected void onSubmit() {
              setResponsePage(
                  new RiassuntoConfermaChiusuraIscrizionePage(
                      chiusuraiscrizionerefezione, iscritto));
            }
          };
    } else {
      form =
          new RichiestaChiusuraIscrizioneRefezioneForm(
              "form", chiusuraiscrizionerefezione, this, getUtente()) {

            private static final long serialVersionUID = -6757761219336702707L;

            @Override
            protected void onSubmit() {
              setResponsePage(
                  new RiassuntoConfermaChiusuraIscrizionePage(getModelObject(), iscritto));
            }
          };
    }

    // Label mailUtente;
    // if (getUtente().getMail() == null &&
    // LayoutBasePage.ambienteInternoDiTest()) {
    // mailUtente = new Label("mailUtente",
    // "f.ferrando2@liguriadigitale.it");
    // } else {
    // mailUtente = new Label("mailUtente", getUtente().getMail());
    // }
    // form.add(mailUtente);

    form.add(creaBottoneAnnulla());

    add(form);
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 3263848436379631971L;

      @Override
      public void onClick() {
        setResponsePage(new RiepilogoIscrizioniPage());
      }
    });
  }
}
