package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.puntitari.panel;

import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.puntitari.model.Conferimento;
import it.liguriadigitale.ponmetro.puntitari.model.PuntiTari;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class PuntiTariPanel extends BasePanel {

  private static final long serialVersionUID = -4552382552894933519L;

  private Integer annoScelto;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public PuntiTariPanel(String panelId, PuntiTari puntiTari, Integer anno) {
    super(panelId);
    setAnnoScelto(anno);
    fillDati(puntiTari);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    PuntiTari puntiTari = (PuntiTari) dati;
    List<Conferimento> listaConferimenti = new ArrayList<Conferimento>();

    WebMarkupContainer vuoto = new WebMarkupContainer("vuoto");
    vuoto.setVisible(LabelFdCUtil.checkIfNull(puntiTari));
    addOrReplace(vuoto);

    WebMarkupContainer pieno = new WebMarkupContainer("pieno");
    pieno.setVisible(LabelFdCUtil.checkIfNotNull(puntiTari));
    addOrReplace(pieno);

    if (LabelFdCUtil.checkIfNotNull(puntiTari)
        && LabelFdCUtil.checkIfNotNull(puntiTari.getConferimenti())) {
      listaConferimenti = puntiTari.getConferimenti();
    }

    String cf = "";
    Integer codTia = 0;
    Double totPunti = 0.0;

    if (LabelFdCUtil.checkIfNotNull(puntiTari)) {
      cf = puntiTari.getCodiceFiscale();
      codTia = puntiTari.getCodiceTIA();
      totPunti = puntiTari.getPunti();
    }

    Label codiceFiscale = new Label("codiceFiscale", cf);
    codiceFiscale.setVisible(
        LabelFdCUtil.checkIfNotNull(puntiTari)
            && LabelFdCUtil.checkIfNotNull(puntiTari.getCodiceFiscale()));
    pieno.addOrReplace(codiceFiscale);

    Label codiceTia = new Label("codiceTia", codTia);
    codiceTia.setVisible(
        LabelFdCUtil.checkIfNotNull(puntiTari)
            && LabelFdCUtil.checkIfNotNull(puntiTari.getCodiceTIA()));
    pieno.addOrReplace(codiceTia);

    Label punti = new Label("punti", totPunti);
    punti.setVisible(
        LabelFdCUtil.checkIfNotNull(puntiTari)
            && LabelFdCUtil.checkIfNotNull(puntiTari.getPunti()));
    pieno.addOrReplace(punti);

    PageableListView<Conferimento> listView =
        new PageableListView<Conferimento>("box", listaConferimenti, 6) {

          private static final long serialVersionUID = 8047272629912048374L;

          @Override
          protected void populateItem(ListItem<Conferimento> item) {
            final Conferimento conferimento = item.getModelObject();

            item.addOrReplace(new CardLabel<>("data", conferimento.getData(), PuntiTariPanel.this));

            item.addOrReplace(
                new CardLabel<>("conferitore", conferimento.getConferitore(), PuntiTariPanel.this));

            item.addOrReplace(
                new CardLabel<>("luogo", conferimento.getLuogo(), PuntiTariPanel.this));

            String descrizioneSenzaUnderscore = "";
            if (PageUtil.isStringValid(conferimento.getDescrizione())) {
              descrizioneSenzaUnderscore = conferimento.getDescrizione().replaceAll("_", " ");
            }
            Label descrizione = new Label("descrizione", descrizioneSenzaUnderscore);
            descrizione.setVisible(PageUtil.isStringValid(descrizioneSenzaUnderscore));
            item.addOrReplace(descrizione);

            String quantitaEUnitaMisura = "";
            if (LabelFdCUtil.checkIfNotNull(conferimento.getPesoQuantita())) {
              quantitaEUnitaMisura = String.valueOf(conferimento.getPesoQuantita()).concat(" ");
            }
            if (PageUtil.isStringValid(conferimento.getUnitaMisura())
                && !conferimento.getUnitaMisura().equalsIgnoreCase("Nr.")) {
              quantitaEUnitaMisura = quantitaEUnitaMisura.concat(conferimento.getUnitaMisura());
            }

            item.addOrReplace(
                new CardLabel<>("quantita", quantitaEUnitaMisura, PuntiTariPanel.this));

            Label punti = new Label("punti", conferimento.getPunti());
            punti.setVisible(LabelFdCUtil.checkIfNotNull(conferimento.getPunti()));
            item.addOrReplace(punti);
          }
        };

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationPuntiTari", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(puntiTari)
            && LabelFdCUtil.checkIfNotNull(listaConferimenti)
            && !LabelFdCUtil.checkEmptyList(listaConferimenti)
            && listaConferimenti.size() > 6);
    addOrReplace(paginazioneFascicolo);
  }

  public Integer getAnnoScelto() {
    return annoScelto;
  }

  public void setAnnoScelto(Integer annoScelto) {
    this.annoScelto = annoScelto;
  }
}
