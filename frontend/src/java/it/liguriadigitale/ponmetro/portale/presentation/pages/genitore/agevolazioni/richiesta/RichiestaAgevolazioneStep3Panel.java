package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta;

import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffariaDisoccupato;
import java.time.LocalDate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;

public class RichiestaAgevolazioneStep3Panel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;

  public RichiestaAgevolazioneStep3Panel(AgevolazioneStep2 step2) {
    super("agevolazionePanel");
    createFeedBackPanel();
    fillDati(step2);
  }

  @Override
  public void fillDati(Object dati) {
    try {
      AgevolazioneStep2 step2 = (AgevolazioneStep2) dati;
      creaListaNomiSelezionati(step2.getStep1());
      creaLabelAnnoScolasticoSelezionato(step2.getStep1());

      //			NotEmptyLabel labelCf1 = new NotEmptyLabel("codiceFiscale1",
      //					step2.getComboDisoccupazioneIseeModiGenitore1() != null
      //							? step2.getComboDisoccupazioneIseeModiGenitore1().getCodiceFiscale()
      //							: "");
      //			NotEmptyLabel labelCf2 = new NotEmptyLabel("codiceFiscale2",
      //					step2.getComboDisoccupazioneIseeModiGenitore2() != null
      //							? step2.getComboDisoccupazioneIseeModiGenitore2().getCodiceFiscale()
      //							: "");

      //			LocalDateLabel data1 = new LocalDateLabel("data1", getData1(step2));
      //			LocalDateLabel data2 = new LocalDateLabel("data2", getData2(step2));

      //			add(labelCf1);
      //			add(labelCf2);
      //			add(data1);
      //			add(data2);

      ListView<DatiAgevolazioneTariffariaDisoccupato> boxDisoccupati =
          new ListView<DatiAgevolazioneTariffariaDisoccupato>(
              "boxDisoccupati", step2.getListaMembriConDataDisoccupazione()) {

            private static final long serialVersionUID = 5529675413637189114L;

            @Override
            protected void populateItem(
                ListItem<DatiAgevolazioneTariffariaDisoccupato> itemNucleo) {
              final DatiAgevolazioneTariffariaDisoccupato membroNucleo =
                  itemNucleo.getModelObject();

              NotEmptyLabel codiceFiscale =
                  new NotEmptyLabel("codiceFiscale", membroNucleo.getCodiceFiscaleDisoccupato());
              itemNucleo.addOrReplace(codiceFiscale);

              String dataDisoccupazione = "";
              if (LabelFdCUtil.checkIfNotNull(membroNucleo.getDataInizioDisoccupazione())) {
                LocalDate localdateDisoccupazione = membroNucleo.getDataInizioDisoccupazione();

                if (LabelFdCUtil.checkIfNotNull(localdateDisoccupazione)) {
                  dataDisoccupazione = LocalDateUtil.getDataFormatoEuropeo(localdateDisoccupazione);
                }
              }
              NotEmptyLabel data = new NotEmptyLabel("data", dataDisoccupazione);
              itemNucleo.addOrReplace(data);
            }
          };
      addOrReplace(boxDisoccupati);

      NotEmptyLabel email = new NotEmptyLabel("email", step2.getEmailContatto());
      NotEmptyLabel telefono = new NotEmptyLabel("telefono", step2.getTelefonoContatto());
      addOrReplace(email);
      addOrReplace(telefono);

      success("Richiesta di agevolazione inviata correttamente");
    } catch (Exception e) {
      log.error("Richiesta di agevolazione - Errore durante l'invio della domanda:", e);

      error("Errore durante l'invio della domanda di richiesta di agevolazione");
    }
  }

  //	private LocalDate getData1(AgevolazioneStep2 step2) {
  //		return step2.getDataDisoccupazioneGenitore1() != null ? step2.getDataDisoccupazioneGenitore1()
  // : null;
  //	}
  //
  //	private LocalDate getData2(AgevolazioneStep2 step2) {
  //		return step2.getDataDisoccupazioneGenitore2() != null ? step2.getDataDisoccupazioneGenitore2()
  // : null;
  //	}

  private void creaLabelAnnoScolasticoSelezionato(AgevolazioneStep1 step1) {
    Integer anno = step1.getAnnoScolastico();
    String testo = anno + "/" + (++anno);
    add(new Label("annoScolasticoSelezionato", testo));
  }

  private void creaListaNomiSelezionati(AgevolazioneStep1 step1) {

    RepeatingView listItems = new RepeatingView("listaNomiIscritti");
    String dummy = "vuoto";
    String lastChildId = dummy;
    for (AgevolazioneTariffariaRistorazione figlio : step1.getListaFigliPerRichiesta()) {
      if (figlio.isSelezionato()) {
        String nome =
            new StringBuilder()
                .append(figlio.getNome())
                .append(" ")
                .append(figlio.getCognome())
                .toString();
        listItems.add(new Label(listItems.newChildId(), nome));
        lastChildId = listItems.newChildId();
        listItems.add(new Label(lastChildId, ", "));
      }
    }
    if (!lastChildId.equalsIgnoreCase(dummy)) listItems.get(lastChildId).setVisible(false);
    add(listItems);
  }
}
