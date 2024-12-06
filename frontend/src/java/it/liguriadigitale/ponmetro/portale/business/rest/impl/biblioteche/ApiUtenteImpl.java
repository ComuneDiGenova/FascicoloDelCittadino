package it.liguriadigitale.ponmetro.portale.business.rest.impl.biblioteche;

import it.liguriadigitale.ponmetro.sebinaBiblioteche.apiclient.UtenteApi;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Indirizzo;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.InlineObject1;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteAbil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull;
import java.util.List;
import org.apache.commons.lang.NotImplementedException;

public class ApiUtenteImpl implements UtenteApi {

  private UtenteApi instance;

  public ApiUtenteImpl(UtenteApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public List<Indirizzo> findIndirizziUtenteById(Long arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Utente findUtenteByCdutente(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<Utente> findUtenteByDocIdentita(String arg0, String arg1) {
    return instance.findUtenteByDocIdentita(arg0, arg1);
  }

  @Override
  public Utente findUtenteById(Long arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public List<Utente> findUtenteByRecapito(String arg0, String arg1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Utente findUtenteByUsername(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public void patchUtenteById(Long arg0, InlineObject arg1) {
    instance.patchUtenteById(arg0, arg1);
  }

  @Override
  public void postUtente(UtenteFull arg0, String arg1) {
    instance.postUtente(arg0, arg1);
  }

  @Override
  public List<UtenteAbil> findUtenteAbil(Long arg0, String arg1, String arg2) {
    return instance.findUtenteAbil(arg0, arg1, arg2);
  }

  @Override
  public void patchUtenteAbil(Long arg0, String arg1, InlineObject1 arg2, List<String> arg3) {
    instance.patchUtenteAbil(arg0, arg1, arg2, arg3);
  }
}
