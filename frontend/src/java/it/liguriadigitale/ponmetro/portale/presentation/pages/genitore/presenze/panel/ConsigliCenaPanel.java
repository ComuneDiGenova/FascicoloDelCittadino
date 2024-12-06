package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.presenze.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;

public class ConsigliCenaPanel extends BasePanel {

  private static final long serialVersionUID = -7175366811492221721L;

  public ConsigliCenaPanel(UtenteServiziRistorazione iscrizione) {
    super("riepilogoMensa");
    createFeedBackPanel();
    fillDati(iscrizione);

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {
    UtenteServiziRistorazione iscritto = (UtenteServiziRistorazione) dati;
    /*
     *
     * add(new Label("nomeCognome", iscritto.getNome() + " " +
     * iscritto.getCognome())); add(new Label("statoIscrizione",
     * iscritto.getStatoIscrizioneServiziRistorazione())); add(new
     * Label("sesso", iscritto.getSesso())); add(new Label("cf",
     * iscritto.getCodiceFiscale())); add(new Label("residente",
     * iscritto.getIndirizzoResidenza())); add(new Label("iscritto",
     * statoText)); add(new LocalDateLabel("iscrittoDal",
     * iscritto.getDataIscrizioneServiziRistorazione())); add(new
     * Label("scuola", iscritto.getStrutturaScolastica())); add(new
     * Label("sezione", iscritto.getSezione()));
     */

  }
}
