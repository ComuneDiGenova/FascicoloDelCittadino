package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.DichiarazionePuntiPatenteApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DatiComune;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.PuntiPatente;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.RichiestaDPP;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public class ApiDichiarazionePuntiPatenteImpl implements DichiarazionePuntiPatenteApi {

  private DichiarazionePuntiPatenteApi instance;

  public ApiDichiarazionePuntiPatenteImpl(DichiarazionePuntiPatenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public void deleteFineDPP(Integer arg0, Integer arg1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PuntiPatente getFineDPP(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PuntiPatente setDPPToVerbaleId(String arg0, RichiestaDPP arg1) {
    return instance.setDPPToVerbaleId(arg0, arg1);
  }

  @Override
  public PuntiPatente updateDPPToVerbaleId(String arg0, String arg1, RichiestaDPP arg2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PuntiPatente getFineDPPConducente(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public PuntiPatente setDPPConducenteToVerbaleId(String arg0, RichiestaDPP arg1) {
    return instance.setDPPConducenteToVerbaleId(arg0, arg1);
  }

  @Override
  public List<DatiComune> getListaComuni(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }
}
