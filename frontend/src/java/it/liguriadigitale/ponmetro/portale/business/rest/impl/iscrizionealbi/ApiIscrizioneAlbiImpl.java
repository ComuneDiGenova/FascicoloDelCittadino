package it.liguriadigitale.ponmetro.portale.business.rest.impl.iscrizionealbi;

import it.liguriadigitale.ponmetro.iscrizioni.albi.apiclient.IscrizioniAlbiElettoraliApi;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DatiAccount;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DatiDomanda;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DatiRicevuta;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandaProtocollata;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.DomandeInviate;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.RichiestaInviata;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.RisultatoDescribe;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.RisultatoTornata;
import it.liguriadigitale.ponmetro.iscrizioni.albi.model.Tornate;

public class ApiIscrizioneAlbiImpl implements IscrizioniAlbiElettoraliApi {

  private IscrizioniAlbiElettoraliApi instance;

  public ApiIscrizioneAlbiImpl(IscrizioniAlbiElettoraliApi instance) {
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
  public DomandeInviate richiesteInviate(String arg0) {

    return instance.richiesteInviate(arg0);
  }

  @Override
  public DatiRicevuta datiRicevuta(String arg0) {
    return instance.datiRicevuta(arg0);
  }

  @Override
  public RichiestaInviata inviaRichiesta(DatiDomanda arg0) {

    return instance.inviaRichiesta(arg0);
  }

  @Override
  public RisultatoDescribe describeById(String arg0) {
    return instance.describeById(arg0);
  }

  @Override
  public Tornate listaTipiDiTornate() {
    return instance.listaTipiDiTornate();
  }

  @Override
  public RisultatoTornata tornateByIdTipoTornata(String arg0) {
    return instance.tornateByIdTipoTornata(arg0);
  }
}
