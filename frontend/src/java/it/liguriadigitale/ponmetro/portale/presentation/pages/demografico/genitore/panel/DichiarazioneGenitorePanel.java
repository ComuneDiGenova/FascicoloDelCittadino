package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.genitore.panel;

import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mionucleo.panel.MioFamigliarePanel;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class DichiarazioneGenitorePanel extends BasePanel {

  private static final long serialVersionUID = 4303332747241251854L;

  public DichiarazioneGenitorePanel(String id) {
    super(id);
    fillDati(getUtente().getDatiCittadinoResidente());
  }

  @Override
  public void fillDati(Object dati) {
    Residente residente = (Residente) dati;

    List<ItemRelazioneParentale> schedaAnagrafica = new ArrayList<>();
    if (residente.getCpvInRegisteredFamily() != null) {
      schedaAnagrafica = residente.getCpvInRegisteredFamily().getCpvBelongsToFamily();
    }

    ListView<ItemRelazioneParentale> listView =
        new ListView<ItemRelazioneParentale>("nucleo", schedaAnagrafica) {

          private static final long serialVersionUID = 7005458124768335776L;

          @Override
          protected void populateItem(ListItem<ItemRelazioneParentale> item) {
            ItemRelazioneParentale famigliare = item.getModelObject();

            MioFamigliarePanel panel = new MioFamigliarePanel(famigliare, false, null);
            item.add(panel);
          }
        };
    add(listView);
    String clvFullAddress = "";
    if (residente.getCpvHasAddress() != null)
      clvFullAddress = residente.getCpvHasAddress().getClvFullAddress();
    add(new Label("indirizzo", clvFullAddress));
  }
}
