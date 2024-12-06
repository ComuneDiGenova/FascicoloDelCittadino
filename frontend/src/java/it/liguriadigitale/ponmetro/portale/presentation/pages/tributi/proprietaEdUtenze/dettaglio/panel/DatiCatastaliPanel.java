package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.dettaglio.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.proprieta.DettaglioProprietaUtenzaExt;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.panel.ProprietaEUtenzePanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;

public class DatiCatastaliPanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  private static final String BASE_LABEL_KEY = "DatiCatastaliPanel.";

  public DatiCatastaliPanel(String id) {
    super(id);
    setOutputMarkupId(true);
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
                  "sezione",
                  "foglio",
                  "particella",
                  "subalterno",
                  "zonaCensuaria",
                  "categoria",
                  "classe",
                  "consistenza",
                  "superficieL",
                  "renditaL"
                }));
    add(getIcona(dettaglioProprietaUtenzaExt));
    add(new Label("titolo", getString(getBaseLabelKey() + "titolo")));
    add(createContentPanel(dettaglioProprietaUtenzaExt, fields));
  }

  @Override
  protected AttributeAppender getCssIcona(Object t) {
    return new AttributeAppender("class", " " + ProprietaEUtenzePanel.ICON_CATASTALI);
  }
}
