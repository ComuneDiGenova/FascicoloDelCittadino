package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tari;

import it.liguriadigitale.ponmetro.tarinetribe.apiclient.AgevolazioneTariffariaTariApi;
import it.liguriadigitale.ponmetro.tarinetribe.model.AgevolazioneTariffariaTariInformazioni;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiestaAgevolazioneTariffariaTari;
import java.util.List;

public class TariAgevolazioneTariffariaImpl implements AgevolazioneTariffariaTariApi {

  private AgevolazioneTariffariaTariApi instance;

  public TariAgevolazioneTariffariaImpl(AgevolazioneTariffariaTariApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public AgevolazioneTariffariaTariInformazioni getAgevolazioneTariffariaTariInformazioni(
      Long arg0) {
    return instance.getAgevolazioneTariffariaTariInformazioni(arg0);
  }

  @Override
  public DatiAgevolazioneTariffariaTari insertAgevolazioneTariffariaTari(
      DatiRichiestaAgevolazioneTariffariaTari arg0) {
    return instance.insertAgevolazioneTariffariaTari(arg0);
  }

  @Override
  public List<DatiAgevolazioneTariffariaTari> getAgevolazioneTariffariaTari(
      String arg0, Integer arg1) {
    return instance.getAgevolazioneTariffariaTari(arg0, arg1);
  }
}
