package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi.tarieng;

import it.liguriadigitale.ponmetro.tarieng.apiclient.FascicoloDelContribuenteTariRestControllerApi;
import it.liguriadigitale.ponmetro.tarieng.model.DettaglioDocumentoEmesso;
import it.liguriadigitale.ponmetro.tarieng.model.DocumentiPDF;
import it.liguriadigitale.ponmetro.tarieng.model.RicevutaTelematica;
import it.liguriadigitale.ponmetro.tarieng.model.SchedaTributoTari;
import it.liguriadigitale.ponmetro.tarieng.model.Tributi;

public class ApiTariEngImpl implements FascicoloDelContribuenteTariRestControllerApi {

  private FascicoloDelContribuenteTariRestControllerApi instance;

  public ApiTariEngImpl(FascicoloDelContribuenteTariRestControllerApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Tributi quadroTributarioTARI(String arg0) {
    return instance.quadroTributarioTARI(arg0);
  }

  @Override
  public SchedaTributoTari schedaTributoTARI(String arg0) {
    return instance.schedaTributoTARI(arg0);
  }

  @Override
  public DocumentiPDF stampaDocumentoPDF(Integer arg0, Integer arg1) {
    return instance.stampaDocumentoPDF(arg0, arg1);
  }

  @Override
  public RicevutaTelematica stampaRicevutaTelematica(Integer arg0, Integer arg1) {
    return instance.stampaRicevutaTelematica(arg0, arg1);
  }

  @Override
  public DettaglioDocumentoEmesso dettaglioDocumentoTARI(String arg0) {
    return instance.dettaglioDocumentoTARI(arg0);
  }
}
