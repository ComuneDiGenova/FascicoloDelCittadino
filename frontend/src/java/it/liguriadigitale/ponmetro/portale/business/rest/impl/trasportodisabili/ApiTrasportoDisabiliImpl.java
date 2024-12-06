package it.liguriadigitale.ponmetro.portale.business.rest.impl.trasportodisabili;

import it.liguriadigitale.ponmetro.trasporto.disabili.apiclient.TrasportoBambiniDisabiliAScuolaApi;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DatiAccount;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DatiDomanda;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DatiRicevuta;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandaProtocollata;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviate;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.FileAllegato;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.FileAllegatoInviato;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.RichiestaInviata;

public class ApiTrasportoDisabiliImpl implements TrasportoBambiniDisabiliAScuolaApi {

  private TrasportoBambiniDisabiliAScuolaApi instance;

  public ApiTrasportoDisabiliImpl(TrasportoBambiniDisabiliAScuolaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DatiAccount datiAccountByCF(String arg0) {
    return instance.datiAccountByCF(arg0);
  }

  @Override
  public DatiAccount datiAccountById(String arg0) {

    return instance.datiAccountById(arg0);
  }

  @Override
  public DomandaProtocollata domandaProtocollata(String arg0) {

    return instance.domandaProtocollata(arg0);
  }

  @Override
  public FileAllegatoInviato inviaFileAllegato(FileAllegato arg0) {

    return instance.inviaFileAllegato(arg0);
  }

  @Override
  public RichiestaInviata inviaRichiesta(DatiDomanda arg0) {

    return instance.inviaRichiesta(arg0);
  }

  @Override
  public DomandeInviate richiesteInviate(String arg0) {

    return instance.richiesteInviate(arg0);
  }

  @Override
  public DatiRicevuta datiRicevuta(String arg0) {
    return instance.datiRicevuta(arg0);
  }
}
