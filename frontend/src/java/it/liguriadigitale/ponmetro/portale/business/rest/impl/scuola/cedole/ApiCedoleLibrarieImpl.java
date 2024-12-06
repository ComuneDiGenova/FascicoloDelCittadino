package it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.CedoleFdCApi;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AllegatoPDF;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AnnullamentoCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ClasseScuola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.RitiroCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SceltaCartolibreria;
import it.liguriadigitale.ponmetro.scuola.cedole.model.TipoCedola;

public class ApiCedoleLibrarieImpl implements CedoleFdCApi {

  private CedoleFdCApi instance;

  public ApiCedoleLibrarieImpl(CedoleFdCApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Cedola domandaGet(String var1, String var2) {
    return instance.domandaGet(var1, var2);
  }

  @Override
  public Cedola domandaPost(DomandaCedola var1) {
    return instance.domandaPost(var1);
  }

  @Override
  public Cedola prenotazioneAnnullamentoPut(AnnullamentoCedola var1) {
    return instance.prenotazioneAnnullamentoPut(var1);
  }

  @Override
  public Cedola prenotazioneCartolibreriaPut(SceltaCartolibreria var1) {
    return instance.prenotazioneCartolibreriaPut(var1);
  }

  @Override
  public AllegatoPDF prenotazioneCedolaGet(String arg0) {
    return instance.prenotazioneCedolaGet(arg0);
  }

  @Override
  public DomandaCedola prenotazioneRitiroPut(RitiroCedola arg0) {
    return instance.prenotazioneRitiroPut(arg0);
  }

  @Override
  public TipoCedola tipoCedolaPerClassePost(ClasseScuola arg0) {
    return instance.tipoCedolaPerClassePost(arg0);
  }
}
