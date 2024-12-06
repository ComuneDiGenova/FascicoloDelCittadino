package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiStradeAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.preferenze.AggiungiPreferenzaServizioAllerteCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import java.util.List;
import org.apache.wicket.Component;

public class AggiungiPreferenzaServizioAllerteCortesiaPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1884420713373698622L;

  protected int index = 1;

  private DatiStradeAllerteCortesia datiStrada;

  private int numeroOccorenze;

  private int startIndex;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public AggiungiPreferenzaServizioAllerteCortesiaPage(DatiStradeAllerteCortesia datiStrada) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance()
            .getServiziAllerteCortesia()
            .popolaListaMessaggiAggiungiPreferenza();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    int index = 1;

    AggiungiPreferenzaServizioAllerteCortesiaPanel aggiungiPreferenzaPanel =
        new AggiungiPreferenzaServizioAllerteCortesiaPanel(
            "aggiungiPreferenzaPanel", datiStrada, index);
    addOrReplace(aggiungiPreferenzaPanel);

    setOutputMarkupId(true);
  }

  public void refreshListaRisultati(
      DatiStradeAllerteCortesia datiStrada, int index, int numeroOccorenze, int startIndex) {
    this.index = index;

    this.setDatiStrada(datiStrada);
    this.remove("aggiungiPreferenzaPanel");

    this.setNumeroOccorenze(numeroOccorenze);
    this.setStartIndex(startIndex);

    AggiungiPreferenzaServizioAllerteCortesiaPanel aggiungiPreferenzaPanel =
        new AggiungiPreferenzaServizioAllerteCortesiaPanel(
            "aggiungiPreferenzaPanel", datiStrada, index, numeroOccorenze, startIndex);
    addOrReplace(aggiungiPreferenzaPanel);
  }

  public DatiStradeAllerteCortesia getDatiStrada() {
    return datiStrada;
  }

  public void setDatiStrada(DatiStradeAllerteCortesia datiStrada) {
    this.datiStrada = datiStrada;
  }

  public int getNumeroOccorenze() {
    return numeroOccorenze;
  }

  public void setNumeroOccorenze(int numeroOccorenze) {
    this.numeroOccorenze = numeroOccorenze;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }
}
