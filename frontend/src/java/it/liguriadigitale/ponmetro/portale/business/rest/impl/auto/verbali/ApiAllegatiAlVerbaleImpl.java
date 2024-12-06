package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.AllegatiAlVerbaleApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Allegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import org.apache.commons.lang.NotImplementedException;

public class ApiAllegatiAlVerbaleImpl implements AllegatiAlVerbaleApi {

  private AllegatiAlVerbaleApi instance;

  public ApiAllegatiAlVerbaleImpl(AllegatiAlVerbaleApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Allegato addAllegatoToVerbaleId(String arg0, String arg1, FileAllegato arg2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public void deleteFileInDocument(Integer arg0, Integer arg1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public FileAllegato getFineAttachment(String arg0, String arg1, String arg2) {
    return instance.getFineAttachment(arg0, arg1, arg2);
  }

  @Override
  public AllegatiCollection getFineAttachments(String arg0) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Allegato setAllegatoToVerbaleId(String arg0, Allegato arg1) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }
}
