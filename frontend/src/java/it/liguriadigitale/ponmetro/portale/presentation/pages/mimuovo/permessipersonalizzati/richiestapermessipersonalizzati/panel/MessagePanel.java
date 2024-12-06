package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class MessagePanel extends BasePanel {

  private static final long serialVersionUID = -3021289773196275267L;

  private List<String> listaMessaggi;
  ListView<String> listView;

  public MessagePanel(String id, List<String> listaMessaggi) {
    super(id);
    this.listaMessaggi = listaMessaggi;
    fillDati(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    add(listView);
  }

  @Override
  public void fillDati(Object dati) {

    listView =
        new ListView<String>("listaMessaggi") {

          private static final long serialVersionUID = 1848681084780295530L;

          @Override
          protected void populateItem(ListItem<String> item) {

            Label messaggio = new Label("messaggio", item.getModelObject());
            messaggio.setEscapeModelStrings(false);
            item.addOrReplace(messaggio);
          }
        };

    listView.setList(listaMessaggi);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    fillDati(listView);
  }

  public List<String> getListaMessaggi() {
    return listaMessaggi;
  }

  public void setListaMessaggi(List<String> listaMessaggi) {
    this.listaMessaggi = listaMessaggi;
  }
}
