package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.DettaglioProprietaUtenzaExt;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;

public class DatiGenericiProprietaPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiGenericiProprietaPanel.";
  private AttributeAppender atIcon;

  public DatiGenericiProprietaPanel(String id, AttributeAppender atIcon) {
    super(id);
    setOutputMarkupId(true);
    this.atIcon = atIcon;
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    DettaglioProprietaUtenzaExt dettaglioProprietaUtenzaExt = (DettaglioProprietaUtenzaExt) dati;
    List<String> fields =
        new ArrayList<String>(
            Arrays.asList(
                new String[] {
                  // "indirizzoCompleto",
                  "via", "numeroCivico", "esponenteCivico", "piano", "scala", "interno"
                }));
    add(getIcona(dettaglioProprietaUtenzaExt));
    add(getLabelTitolo(dettaglioProprietaUtenzaExt));
    add(createContentPanel(dettaglioProprietaUtenzaExt, fields));

    // addOrReplace(new
    // SoggettoAPanel<DettaglioProprietaUtenzaExt>("soggettoAPanel",
    // dettaglioProprietaUtenzaExt,
    // dettaglioProprietaUtenzaExt.getTributi(), null));
    this.setVisible(isAtLeastOneShowed());
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    return this.atIcon;
  }
}
