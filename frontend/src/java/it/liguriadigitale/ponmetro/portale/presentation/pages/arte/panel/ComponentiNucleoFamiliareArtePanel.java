package it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel;

import it.liguriadigitale.ponmetro.arte.model.Componente;
import it.liguriadigitale.ponmetro.arte.model.Componenti;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class ComponentiNucleoFamiliareArtePanel extends BasePanel {

  private static final long serialVersionUID = 3528603330683536008L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ComponentiNucleoFamiliareArtePanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    Componenti componenti = (Componenti) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkComponenti(componenti));
    addOrReplace(listaVuota);

    List<Componente> listaComponenti = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(componenti)
        && LabelFdCUtil.checkIfNotNull(componenti.getComponentiNucleo())) {
      listaComponenti = componenti.getComponentiNucleo();
    }
    PageableListView<Componente> listView =
        new PageableListView<Componente>("componenti", listaComponenti, 4) {

          private static final long serialVersionUID = 8293162684760336256L;

          @Override
          protected void populateItem(ListItem<Componente> itemComponente) {
            final Componente componente = itemComponente.getModelObject();

            NotEmptyLabel id = new NotEmptyLabel("id", componente.getIDCOMPONENTE());
            itemComponente.addOrReplace(id);

            itemComponente.addOrReplace(
                new AmtCardLabel<>(
                    "nome", componente.getNOME(), ComponentiNucleoFamiliareArtePanel.this));

            itemComponente.addOrReplace(
                new AmtCardLabel<>(
                    "cognome", componente.getCOGNOME(), ComponentiNucleoFamiliareArtePanel.this));

            itemComponente.addOrReplace(
                new AmtCardLabel<>(
                    "cf", componente.getCFISC(), ComponentiNucleoFamiliareArtePanel.this));

            itemComponente.addOrReplace(
                new AmtCardLabel<>(
                    "dataInizio", componente.getDATA(), ComponentiNucleoFamiliareArtePanel.this));
          }
        };

    listView.setVisible(checkComponenti(componenti));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkComponenti(componenti) && listaComponenti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkComponenti(Componenti componenti) {
    return LabelFdCUtil.checkIfNotNull(componenti)
        && LabelFdCUtil.checkIfNotNull(componenti.getComponentiNucleo())
        && !LabelFdCUtil.checkEmptyList(componenti.getComponentiNucleo());
  }
}
