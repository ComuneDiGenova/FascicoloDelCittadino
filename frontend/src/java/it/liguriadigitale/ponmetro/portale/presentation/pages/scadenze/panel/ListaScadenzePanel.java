package it.liguriadigitale.ponmetro.portale.presentation.pages.scadenze.panel;

import it.liguriadigitale.ponmetro.api.pojo.scadenze.db.VScScadenzeUt;
import it.liguriadigitale.ponmetro.portale.presentation.components.util.PageClassFinder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.Page404;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.component.IRequestablePage;

public class ListaScadenzePanel extends BasePanel {

  private static final long serialVersionUID = -4930390410614798571L;

  private static Log log = LogFactory.getLog(ListaScadenzePanel.class);

  public ListaScadenzePanel(LocalDate date, List<VScScadenzeUt> lista) {
    super("listaScadenze");

    createFeedBackPanel();
    fillDati(lista);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<VScScadenzeUt> listaScadenze = (List<VScScadenzeUt>) dati;

    ListView<VScScadenzeUt> listViewScadenze =
        new ListView<VScScadenzeUt>("listaItemScadenze", listaScadenze) {

          private static final long serialVersionUID = 1L;

          @SuppressWarnings("rawtypes")
          @Override
          protected void populateItem(ListItem<VScScadenzeUt> item) {
            VScScadenzeUt scadenza = item.getModelObject();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataScadenzaFormatter = scadenza.getDataScadenza().format(formatter);
            Label giorno = new Label("giornoScadenza", dataScadenzaFormatter);

            Label comparto = new Label("comparto", scadenza.getDenominazioneComp());
            Label dataScadenza = new Label("dataScadenza", scadenza.getScadenza());

            item.add(giorno);
            item.add(comparto);
            item.add(dataScadenza);

            String uriComp = scadenza.getUriComp() + "Page";
            Link<?> linkInfo =
                new Link("azioniLink") {

                  private static final long serialVersionUID = -4751422526219613198L;

                  @Override
                  public void onClick() {
                    Class<IRequestablePage> clazz;
                    clazz = (Class<IRequestablePage>) PageClassFinder.findClassByName(uriComp);
                    if (clazz.equals(Page404.class)) {
                      setVisible(false);
                    }
                    setResponsePage(clazz);
                  }

                  @Override
                  public MarkupContainer setDefaultModel(IModel model) {
                    return setDefaultModel(model);
                  }
                };

            linkInfo.setVisible(scadenza.getUriComp() != null);
            item.add(linkInfo);
          }

          @Override
          public boolean isVisible() {
            return !listaScadenze.isEmpty();
          }
        };

    WebMarkupContainer cell = new WebMarkupContainer("cell");
    cell.setVisible(!listaScadenze.isEmpty());
    add(cell);

    Label messaggioListaVuota =
        new Label("messaggioListaVuota", getString("ListaScadenzePanel.listaVuota"));
    messaggioListaVuota.setVisible(listaScadenze.isEmpty());
    add(messaggioListaVuota);
    cell.add(listViewScadenze);
  }
}
