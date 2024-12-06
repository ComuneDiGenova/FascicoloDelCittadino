package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.scadenzeEVersato.scadenze.accertato.dettagli.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.scadenzeeversato.AccertatoScadenzeExt;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;

public class DatiAccertamentoPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiAccertamentoPanel.";

  public DatiAccertamentoPanel(String id) {
    super(id, 7);
    setOutputMarkupId(true);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    AccertatoScadenzeExt accertatoScadenzeExt = (AccertatoScadenzeExt) dati;

    addIcona(accertatoScadenzeExt);

    List<String> fields =
        new ArrayList<String>(
            Arrays.asList(
                new String[] {
                  "statoPagamento",
                  "documentoEmesso",
                  "annoRiferimentoAtto",
                  "dovutoAttoRidottoL",
                  "dovutoAttoPienoL",
                  "dataScadenza",
                  "dataScadenza90gg",
                  "dataNotificaAttoString"
                }));
    add(getLabelTitolo(accertatoScadenzeExt));
    add(createContentPanel(accertatoScadenzeExt, fields));

    this.setVisible(isAtLeastOneShowed());
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    AccertatoScadenzeExt elemento = (AccertatoScadenzeExt) t;
    String css = elemento.getColorForIcon();
    css += elemento.getBaseIcon();
    return new AttributeAppender("class", " " + css);
  }
}
