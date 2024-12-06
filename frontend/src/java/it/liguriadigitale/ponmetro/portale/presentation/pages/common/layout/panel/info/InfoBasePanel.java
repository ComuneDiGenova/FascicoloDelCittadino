package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.info;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class InfoBasePanel extends BasePanelGenericContent {

  private static final long serialVersionUID = 4234527383068466160L;
  public static final String BASE_PANEL_ID = "infoPanel";
  private static final String BASE_LABEL_KEY = "InfoBasePanel.";

  private int iContatore;

  private ExternalLink bottone;
  private int iContatoreBottone;

  public InfoBasePanel(final String actualPage) {
    this(BASE_PANEL_ID, actualPage);
  }

  public InfoBasePanel(final String actualPage, ExternalLink bottone, int iContatoreBottone) {
    this(BASE_PANEL_ID, actualPage, bottone, iContatoreBottone);
  }

  public InfoBasePanel(
      final String id,
      final String actualPage,
      Boolean boTiDoLeStringheDaFuori,
      ExternalLink bottone,
      int iContatoreBottone) {
    super(id);
    setOutputMarkupId(true);
    if (!boTiDoLeStringheDaFuori) {
      initContent(actualPage);
    }
    iContatore = 0;
    this.iContatoreBottone = iContatoreBottone;
    this.bottone = bottone;
  }

  public InfoBasePanel(final String id, final String actualPage, Boolean boTiDoLeStringheDaFuori) {
    this(id, actualPage, boTiDoLeStringheDaFuori, new ExternalLink("bottone1", ""), -1);
  }

  public InfoBasePanel(final String id, final String actualPage) {
    this(id, actualPage, false);
  }

  public InfoBasePanel(
      final String id, final String actualPage, ExternalLink bottone, int iContatoreBottone) {
    this(id, actualPage, false, bottone, iContatoreBottone);
  }

  protected void initContent(final String actualPage) {
    List<String> baseElements = new ArrayList<String>();
    int i = 0;
    for (i = 0; ; i++) {
      try {
        baseElements.add(getString(BASE_LABEL_KEY + actualPage + "." + i));
      } catch (Exception e) {
        break;
      }
    }
    initContent(baseElements);
    if (i == 0) {
      setVisible(false);
    }
  }

  public void initContent(final List<String> stringhe) {
    fillDati(stringhe);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  // to-do
  // legenda con icona read
  // legenda "bottone" che filtra result
  // legenda "bottone" con icona che filtra result
  @Override
  public void fillDati(Object dati) {
    @SuppressWarnings("unchecked")
    ListView<String> listView =
        new ListView<String>("elements", (List<String>) dati) {

          private static final long serialVersionUID = -1L;

          @Override
          protected void populateItem(ListItem<String> item) {
            WebMarkupContainer elementContainer = new WebMarkupContainer("elementContainer");
            item.add(elementContainer);
            Label label = new Label("element", item.getModelObject());
            label.setEscapeModelStrings(false);
            label.setOutputMarkupId(true);
            elementContainer.add(label);
            if (bottone != null && iContatoreBottone != -1 && iContatore == iContatoreBottone) {
              bottone.setVisible(true);
            } else {
              bottone.setVisible(false);
            }
            elementContainer.add(bottone);
            iContatore++;
          }
        };
    listView.setOutputMarkupId(true);
    add(listView);
  }

  public void setBottonePerOraUnoSolo(ExternalLink createLinkEsternoCalcola, int i) {
    this.bottone = createLinkEsternoCalcola;
    this.iContatoreBottone = i;
  }
}
