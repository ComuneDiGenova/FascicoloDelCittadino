package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.proprietaEdUtenze.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.tributi.model.Tributi.TributoEnum;
import it.liguriadigitale.ponmetro.tributi.model.Tributo;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public class SoggettoAPanel<T> extends BasePanelGenericContent {

  private static final long serialVersionUID = 1L;

  public static final String TRIBUTO_IMU = TributoEnum.IMU.value();
  public static final String TRIBUTO_TARI = TributoEnum.TARI.value();

  private static final String BASE_LABEL_KEY = "SoggettoAPanel.";

  private List<Tributo> tributi;
  private List<Tributo> tributiResidenza;

  public <T> SoggettoAPanel(
      String id, T dati, List<Tributo> tributi, List<Tributo> tributiResidenza) {
    super(id);
    setOutputMarkupId(true);
    this.tributi = tributi;
    this.tributiResidenza = tributiResidenza;
    fillDati(dati);
  }

  @Override
  protected String getBaseLabelKey() {
    return BASE_LABEL_KEY;
  }

  @Override
  public void fillDati(Object dati) {
    Boolean hasTributi = this.tributi != null && !this.tributi.isEmpty();
    Boolean hasTributiResidenza = this.tributiResidenza != null && !this.tributiResidenza.isEmpty();

    Label soggettoA = new Label("soggettoA", getString(BASE_LABEL_KEY + "soggettoA"));
    soggettoA.setVisible(false); // rimuovere false per ripristinare i
    // bottoni e scommentare riga sotto
    // soggettoA.setVisible(hasTributi || hasTributiResidenza);
    add(soggettoA);

    Boolean hasImu =
        (hasTributi
                && this.tributi.stream()
                    .anyMatch(elem -> TRIBUTO_IMU.equalsIgnoreCase(elem.getTributo())))
            || (hasTributiResidenza
                && this.tributiResidenza.stream()
                    .anyMatch(elem -> TRIBUTO_IMU.equalsIgnoreCase(elem.getTributo())));

    Boolean hasTari =
        (hasTributi
                && this.tributi.stream()
                    .anyMatch(elem -> TRIBUTO_TARI.equalsIgnoreCase(elem.getTributo())))
            || (hasTributiResidenza
                && this.tributiResidenza.stream()
                    .anyMatch(elem -> TRIBUTO_TARI.equalsIgnoreCase(elem.getTributo())));

    aggiungiSoggettoATestuale(hasImu, hasTari);

    addOrReplace(new WebMarkupContainer("buttonImu").setVisible(hasImu));
    addOrReplace(new WebMarkupContainer("buttonTari").setVisible(hasTari));
  }

  private void aggiungiSoggettoATestuale(Boolean hasImu, Boolean hasTari) {
    Label soggettoA = new Label("soggettoATitolo");
    add(soggettoA);

    Label soggettoACosa =
        new Label(
            "soggettoACosa",
            (hasImu ? TRIBUTO_IMU : "") + (hasTari ? (hasImu ? " e " : "") + TRIBUTO_TARI : ""));
    soggettoACosa.setVisible(hasImu || hasTari);
    add(soggettoACosa);
  }
}
