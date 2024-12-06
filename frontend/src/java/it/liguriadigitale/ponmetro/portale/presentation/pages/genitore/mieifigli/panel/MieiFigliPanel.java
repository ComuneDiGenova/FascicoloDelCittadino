package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.mieifigli.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.link.AutocertificazioneLink;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class MieiFigliPanel extends BasePanel {

  private static final long serialVersionUID = -1642481424336076636L;

  private List<ComponenteNucleo> listaMinori;

  public MieiFigliPanel(String id) {
    super(id);
    Residente residente = LoadData.caricaMieiDatiResidente(getSession());
    fillDati(residente);
    createFeedBackPanel();
    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {

    listaMinori = new ArrayList<>();
    List<ComponenteNucleo> sortedListaMinori = new ArrayList<>();

    try {
      listaMinori =
          ServiceLocator.getInstance().getServizioDemografico().getFigliMinorenni(getUtente());
      sortedListaMinori =
          listaMinori.stream()
              .sorted(Comparator.comparing(ComponenteNucleo::getDataNascita))
              .collect(Collectors.toList());
    } catch (BusinessException | ApiException e) {
      log.error("Errore API:", e);
    }

    ListView<ComponenteNucleo> listView =
        new ListView<ComponenteNucleo>("listaConviventi", sortedListaMinori) {

          private static final long serialVersionUID = -675270110514582192L;

          @Override
          protected void populateItem(ListItem<ComponenteNucleo> item) {
            ComponenteNucleo minore = item.getModelObject();
            String parentelaFiglio = "mio figlio";
            String tipoParentelaFiglio = minore.getRelazione().getCpvParentType();
            if (tipoParentelaFiglio.equalsIgnoreCase("FG")
                && minore.getDatiCittadino().getCpvHasSex().equalsIgnoreCase("F")) {
              parentelaFiglio = parentelaFiglio.replace("o", "a");
            } else {
              it.liguriadigitale.ponmetro.portale.pojo.enums.CpvParentTypeEnum tipoEnum =
                  it.liguriadigitale.ponmetro.portale.pojo.enums.CpvParentTypeEnum.valueOf(
                      tipoParentelaFiglio);
              parentelaFiglio = tipoEnum.getDescription();
            }
            Label tipoParentela = new Label("parentela", parentelaFiglio);

            Label nomeCognome =
                new Label(
                    "nome",
                    minore.getDatiCittadino().getCpvGivenName()
                        + " "
                        + minore.getDatiCittadino().getCpvFamilyName());
            Label codiceFiscale = new Label("cf", minore.getDatiCittadino().getCpvTaxCode());
            Label residente = new Label("residente", getStatoResidente(minore));
            Label fonteDati = new Label("fonteDati", getFonteDati(minore));
            item.add(tipoParentela);
            item.add(nomeCognome);
            item.add(codiceFiscale);
            item.add(residente);
            item.add(fonteDati);

            // item.setVisible(isVisibile(minore));
          }

          @SuppressWarnings("unused")
          private boolean isVisibile(MinoreConvivente minore) {
            if (minore.isFiglioStatoCivile()) return true;
            if (minore.isAutodichiarazioneFiglio()) return true;
            else return false;
          }

          private String getFonteDati(ComponenteNucleo minore) {

            String fonteDati = getString("Portale.fonteDati.schedaAnagrafica");

            switch (minore.getSorgente()) {
              case SCHEDA_ANAGRAFICA:
                fonteDati = getString("Portale.fonteDati.schedaAnagrafica");
                break;
              case AUTODICHIARAZIONE:
                fonteDati = getString("Portale.fonteDati.autodichiarazione");
                break;
              case STATO_CIVILE:
                fonteDati = getString("Portale.fonteDati.statoCivile");
                break;
            }

            return fonteDati;
          }

          private String getStatoResidente(ComponenteNucleo minore) {
            log.debug("ComponenteNucleo: " + minore);
            String statoResidente = getString("Portale.no");
            if (minore.isCoResidente() || !minore.isResidenteComuneGenova()) {
              statoResidente = getString("Portale.si");
            }
            return statoResidente;
          }
        };
    listView.setRenderBodyOnly(true);
    add(listView);
    Link<Void> linkInfo = new AutocertificazioneLink("infoLink");

    add(linkInfo);
  }

  public boolean isListaVisibile() {
    return !listaMinori.isEmpty();
  }
}
