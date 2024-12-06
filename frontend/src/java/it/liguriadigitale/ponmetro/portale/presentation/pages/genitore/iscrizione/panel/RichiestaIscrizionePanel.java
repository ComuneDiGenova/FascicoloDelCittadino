package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.panel;

import it.liguriadigitale.framework.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.Iscrizione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.RiassuntoConfermaIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizione.form.RichiestaIscrizioneForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.Optional;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class RichiestaIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  private Iscrizione datiIscrizione;

  public RichiestaIscrizionePanel(UtenteServiziRistorazione iscrizione) {
    super("iscrizionePanel");
    createFeedBackPanel();
    fillDati(iscrizione);
  }

  public RichiestaIscrizionePanel(
      UtenteServiziRistorazione utenteServiziRistorazione, Iscrizione iscrizione) {
    super("iscrizionePanel");
    createFeedBackPanel();
    this.datiIscrizione = iscrizione;
    fillDati(utenteServiziRistorazione);
  }

  @Override
  public void fillDati(Object dati) {

    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;

    if (iscritto.getStatoIscrizioneServiziRistorazione().equalsIgnoreCase("ACCETTATA")) {
      add(new Label("titleRistorazione", getString("RichiestaIscrizionePanel.titleCambia")));
    } else {
      add(new Label("titleRistorazione", getString("RichiestaIscrizionePanel.titleIscrivi")));
    }

    add(new Label("nomeCognome", iscritto.getNome() + " " + iscritto.getCognome()));
    add(new Label("sesso", iscritto.getSesso()));
    add(new Label("codiceFiscale", iscritto.getCodiceFiscale()));
    String indirizzoResidenza = PageUtil.escapeNullString(iscritto.getIndirizzoResidenza());
    add(new Label("residente", indirizzoResidenza.toLowerCase()));
    add(new LocalDateLabel("dataNascita", iscritto.getDataNascita()));
    String luogoNascita = PageUtil.escapeNullString(iscritto.getLuogoNascita());
    add(new NotEmptyLabel("luogoNascita", luogoNascita.toLowerCase()));

    Iscrizione iscrizione = new Iscrizione();
    iscrizione.setUtente(iscritto);
    iscrizione.setEmailContatto(getUtente().getMail());

    RichiestaIscrizioneForm form = null;

    WebMarkupContainer alertIndirizzo = new WebMarkupContainer("alertIndirizzo");
    alertIndirizzo.setVisible(false);

    WebMarkupContainer alertComune = new WebMarkupContainer("alertComune");
    alertComune.setVisible(false);

    WebMarkupContainer alertCap = new WebMarkupContainer("alertCap");
    alertCap.setVisible(false);

    if (datiIscrizione != null) {
      form =
          new RichiestaIscrizioneForm("form", datiIscrizione, this) {

            private static final long serialVersionUID = -6226358143103976564L;

            @Override
            protected void onSubmit() {

              if ((datiIscrizione.getIndirizzo() == null
                      && datiIscrizione.getComune() == null
                      && datiIscrizione.getCap() == null)
                  || (datiIscrizione.getIndirizzo() != null
                      && datiIscrizione.getComune() != null
                      && datiIscrizione.getCap() != null)) {
                alertIndirizzo.setVisible(false);
                alertComune.setVisible(false);
                alertCap.setVisible(false);

                setResponsePage(new RiassuntoConfermaIscrizionePage(datiIscrizione));
              } else {
                alertIndirizzo.setVisible(visibilitaAlertIndirizzo(datiIscrizione));
                alertComune.setVisible(visibilitaAlertComune(datiIscrizione));
                alertCap.setVisible(visibilitaAlertCap(datiIscrizione));
                onError();
              }
            }

            @Override
            protected void onError() {
              super.onError();
            }
          };
    } else {
      form =
          new RichiestaIscrizioneForm("form", iscrizione, this) {

            private static final long serialVersionUID = 1822345136597385660L;

            @Override
            protected void onSubmit() {

              if ((iscrizione.getIndirizzo() == null
                      && iscrizione.getComune() == null
                      && iscrizione.getCap() == null)
                  || ((iscrizione.getIndirizzo() != null
                      && iscrizione.getComune() != null
                      && iscrizione.getCap() != null))) {
                alertIndirizzo.setVisible(false);
                alertComune.setVisible(false);
                alertCap.setVisible(false);

                setResponsePage(new RiassuntoConfermaIscrizionePage(getModelObject()));
              } else {
                alertIndirizzo.setVisible(visibilitaAlertIndirizzo(iscrizione));
                alertComune.setVisible(visibilitaAlertComune(iscrizione));
                alertCap.setVisible(visibilitaAlertCap(iscrizione));
                onError();
              }
            }

            @Override
            protected void onError() {
              super.onError();
            }
          };
    }

    form.add(creaBottoneAnnulla());
    add(form);

    add(alertIndirizzo);
    add(alertComune);
    add(alertCap);
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

  private boolean checkIfNull(Object obj) {
    return Optional.ofNullable(obj).isPresent();
  }

  private boolean visibilitaAlertIndirizzo(Iscrizione elementoIscrizione) {
    boolean alertIndirizzoVisibile = false;
    if (checkIfNull(elementoIscrizione.getComune()) || checkIfNull(elementoIscrizione.getCap())) {
      if (!checkIfNull(elementoIscrizione.getIndirizzo())) {
        alertIndirizzoVisibile = true;
      }
    }
    return alertIndirizzoVisibile;
  }

  private boolean visibilitaAlertComune(Iscrizione elementoIscrizione) {
    boolean alertComuneVisibile = false;
    if (checkIfNull(elementoIscrizione.getIndirizzo())
        || checkIfNull(elementoIscrizione.getCap())) {
      if (!checkIfNull(elementoIscrizione.getComune())) {
        alertComuneVisibile = true;
      }
    }
    return alertComuneVisibile;
  }

  private boolean visibilitaAlertCap(Iscrizione elementoIscrizione) {
    boolean alertCapVisibile = false;
    if (checkIfNull(elementoIscrizione.getIndirizzo())
        || checkIfNull(elementoIscrizione.getComune())) {
      if (!checkIfNull(elementoIscrizione.getCap())) {
        alertCapVisibile = true;
      }
    }
    return alertCapVisibile;
  }
}
