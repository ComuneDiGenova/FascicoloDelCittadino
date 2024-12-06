package it.liguriadigitale.ponmetro.portale.presentation.pages.oggettismarriti.panel;

import it.liguriadigitale.ponmetro.oggettismarriti.model.OggettiSmarriti;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class OggettiSmarritiPanel extends BasePanel {

  private static final long serialVersionUID = 1450524182454249842L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public OggettiSmarritiPanel(String id, List<OggettiSmarriti> listaOggettiSmarriti) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(listaOggettiSmarriti);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<OggettiSmarriti> listaOggettiSmarriti = (List<OggettiSmarriti>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkOggettiSmarriti(listaOggettiSmarriti));
    addOrReplace(listaVuota);

    PageableListView<OggettiSmarriti> listView =
        new PageableListView<OggettiSmarriti>("listaOggettiSmarriti", listaOggettiSmarriti, 4) {

          private static final long serialVersionUID = 8530201005349490127L;

          @Override
          protected void populateItem(ListItem<OggettiSmarriti> itemOggettoSmarrito) {
            final OggettiSmarriti oggettoSmarrito = itemOggettoSmarrito.getModelObject();

            NotEmptyLabel id = new NotEmptyLabel("id", oggettoSmarrito.getId());
            itemOggettoSmarrito.addOrReplace(id);

            String stato = "";
            if (PageUtil.isStringValid(oggettoSmarrito.getDatariconsegna())) {
              stato =
                  oggettoSmarrito.getDatariconsegna().equalsIgnoreCase("non disponibile")
                      ? "Non restituito"
                      : "Restituito";
            }
            itemOggettoSmarrito.addOrReplace(
                new AmtCardLabel<>("stato", stato, OggettiSmarritiPanel.this));

            itemOggettoSmarrito.addOrReplace(
                new AmtCardLabel<>(
                    "tipooggetto", oggettoSmarrito.getTipooggetto(), OggettiSmarritiPanel.this));

            itemOggettoSmarrito.addOrReplace(
                new AmtCardLabel<>(
                        "luogorinvenimento",
                        oggettoSmarrito.getLuogorinvenimento(),
                        OggettiSmarritiPanel.this)
                    .setVisible(PageUtil.isStringValid(oggettoSmarrito.getLuogorinvenimento())));

            itemOggettoSmarrito.addOrReplace(
                new AmtCardLabel<>(
                        "datariconsegna",
                        oggettoSmarrito.getDatariconsegna(),
                        OggettiSmarritiPanel.this)
                    .setVisible(
                        PageUtil.isStringValid(oggettoSmarrito.getDatariconsegna())
                            && !oggettoSmarrito
                                .getDatariconsegna()
                                .equalsIgnoreCase("non disponibile")));

            itemOggettoSmarrito.addOrReplace(
                new AmtCardLabel<>(
                    "datarinvenimento",
                    oggettoSmarrito.getDatarinvenimento(),
                    OggettiSmarritiPanel.this));
          }
        };

    listView.setVisible(checkOggettiSmarriti(listaOggettiSmarriti));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkOggettiSmarriti(listaOggettiSmarriti) && listaOggettiSmarriti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkOggettiSmarriti(List<OggettiSmarriti> listaOggettiSmarriti) {
    return LabelFdCUtil.checkIfNotNull(listaOggettiSmarriti)
        && !LabelFdCUtil.checkEmptyList(listaOggettiSmarriti);
  }
}
