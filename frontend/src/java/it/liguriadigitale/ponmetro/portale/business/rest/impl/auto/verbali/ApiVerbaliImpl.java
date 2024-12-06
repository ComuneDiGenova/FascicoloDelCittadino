package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.VerbaliApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraAvviso;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiGeneraListaDettagliVerbali;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbaleCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.GeneraAvvisoResult;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.VerbaleCollection;
import java.time.LocalDate;
import org.apache.commons.lang.NotImplementedException;

public class ApiVerbaliImpl implements VerbaliApi {

  private VerbaliApi instance;

  public ApiVerbaliImpl(VerbaliApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DettaglioVerbale getFineDetail(String arg0, String arg1) {
    return instance.getFineDetail(arg0, arg1);
  }

  @Override
  public VerbaleCollection onGetFines(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      LocalDate arg4,
      String arg5,
      LocalDate arg6,
      LocalDate arg7) {
    return instance.onGetFines(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
  }

  @Override
  public Verbale onVerifyFine(String arg0, String arg1, String arg2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public GeneraAvvisoResult setGeneraAvviso(DatiGeneraAvviso arg0) {
    return instance.setGeneraAvviso(arg0);
  }

  @Override
  public DettaglioVerbaleCollection listaDettagliVerbali(DatiGeneraListaDettagliVerbali arg0) {
    return instance.listaDettagliVerbali(arg0);
  }

  @Override
  public DettaglioVerbale getFineDetailByNumeroAvvisoPag(String arg0, String arg1) {
    return instance.getFineDetail(arg0, arg1);
  }
}
