package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.soggetticoinvolti;

import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.presentation.components.select2.Select2Choice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.step.ubicazioneparcheggio.FeaturesGeoserverProvider;
import org.apache.wicket.model.CompoundPropertyModel;

public class Select2Panel extends BasePanel {

  private static final long serialVersionUID = -6785317389778740703L;

  public Select2Panel(
      String id,
      CompoundPropertyModel<RichiestaPermessoPersonalizzato> richiestaPermessoPersonalizzatoModel) {
    super(id);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    fillDati(richiestaPermessoPersonalizzatoModel);
  }

  @Override
  public void fillDati(Object dati) {

    CompoundPropertyModel richiestaPermessoPersonalizzatoModel =
        (CompoundPropertyModel<RichiestaPermessoPersonalizzato>) dati;
    Select2Choice indirizzoNonResidente =
        new Select2Choice(
            "indirizzoNonResidente4",
            richiestaPermessoPersonalizzatoModel.bind(
                "soggettiCoinvolti.disabile.indirizzoNonResidente"),
            new FeaturesGeoserverProvider());

    add(indirizzoNonResidente);
  }
}
