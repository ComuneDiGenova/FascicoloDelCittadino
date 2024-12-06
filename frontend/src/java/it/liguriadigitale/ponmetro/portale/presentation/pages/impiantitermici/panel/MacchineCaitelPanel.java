package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel;

import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Macchina;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class MacchineCaitelPanel extends BasePanel {

  private static final long serialVersionUID = 4033854687085750655L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public MacchineCaitelPanel(String id, Impianto impianto) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(impianto);

    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    Impianto impianto = (Impianto) dati;

    List<Macchina> listaMacchine = new ArrayList<Macchina>();
    if (checkMacchine(impianto)) {
      listaMacchine = impianto.getMacchine();
    } else {
      warn("Nessuna macchina presente");
    }

    PageableListView<Macchina> listView =
        new PageableListView<Macchina>("macchine", listaMacchine, 4) {

          private static final long serialVersionUID = 4301847188563905329L;

          @Override
          protected void populateItem(ListItem<Macchina> itemMacchina) {
            final Macchina macchina = itemMacchina.getModelObject();

            String dataRInstallazioneInFormatoGGMMAAAA = "";
            if (LabelFdCUtil.checkIfNotNull(macchina.getDataInstallazione())) {
              dataRInstallazioneInFormatoGGMMAAAA =
                  LocalDateUtil.getDataFormatoEuropeo(macchina.getDataInstallazione());
            }
            NotEmptyLabel dataInstallazione =
                new NotEmptyLabel("dataInstallazione", dataRInstallazioneInFormatoGGMMAAAA);
            dataInstallazione.setVisible(
                PageUtil.isStringValid(dataRInstallazioneInFormatoGGMMAAAA));
            itemMacchina.addOrReplace(dataInstallazione);

            itemMacchina.addOrReplace(
                new AmtCardLabel<>(
                    "fabbricante", macchina.getFabbricante(), MacchineCaitelPanel.this));

            itemMacchina.addOrReplace(
                new AmtCardLabel<>("modello", macchina.getModello(), MacchineCaitelPanel.this));

            itemMacchina.addOrReplace(
                new AmtCardLabel<>("matricola", macchina.getMatricola(), MacchineCaitelPanel.this));

            itemMacchina.addOrReplace(
                new AmtCardLabel<>(
                    "dataDismissione", macchina.getDataDismissione(), MacchineCaitelPanel.this));
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkMacchine(impianto) && listaMacchine.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkMacchine(Impianto impianto) {
    return LabelFdCUtil.checkIfNotNull(impianto)
        && LabelFdCUtil.checkIfNotNull(impianto.getMacchine())
        && !LabelFdCUtil.checkEmptyList(impianto.getMacchine());
  }
}
