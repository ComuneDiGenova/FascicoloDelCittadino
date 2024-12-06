package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Documento;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class DocumentiPanel extends BasePanel {

  private static final long serialVersionUID = -5453543164979509962L;

  private DettaglioVerbale dettaglioVerbale;

  public DocumentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public DocumentiPanel(String id, DettaglioVerbale dettaglioVerbale) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    List<Documento> listaDocumenti = dettaglioVerbale.getDocumenti();

    Label titoloDocumenti =
        new Label("titoloDocumenti", getString("DocumentiPanel.titoloDocumenti"));
    add(titoloDocumenti);

    ListView<Documento> listViewDocumenti =
        new ListView<Documento>("box", listaDocumenti) {

          private static final long serialVersionUID = -7513254149897062685L;

          @Override
          protected void populateItem(ListItem<Documento> item) {
            Documento documentoItem = item.getModelObject();

            item.setOutputMarkupId(true);

            Label descrizione = new Label("descrizione", documentoItem.getDescrizione());
            descrizione.setVisible(documentoItem.getDescrizione() != null);
            item.add(descrizione);

            Label tipo = new Label("tipo", documentoItem.getTipo());
            tipo.setVisible(documentoItem.getTipo() != null);
            item.add(tipo);

            String dataPresentazioneValue = "";
            if (documentoItem.getDataPresentazione() != null) {
              dataPresentazioneValue =
                  LocalDateUtil.getDataFormatoEuropeo(documentoItem.getDataPresentazione());
            }
            Label dataPresentazione = new Label("dataPresentazione", dataPresentazioneValue);
            dataPresentazione.setVisible(documentoItem.getDataPresentazione() != null);
            item.add(dataPresentazione);

            String dataProrogaValue = "";
            if (documentoItem.getDataProroga() != null) {
              dataProrogaValue =
                  LocalDateUtil.getDataFormatoEuropeo(documentoItem.getDataProroga());
            }
            Label dataProroga = new Label("dataProroga", dataProrogaValue);
            dataProroga.setVisible(documentoItem.getDataProroga() != null);
            item.add(dataProroga);
          }
        };

    listViewDocumenti.setVisible(listaDocumenti != null && !listaDocumenti.isEmpty());
    add(listViewDocumenti);
  }
}
