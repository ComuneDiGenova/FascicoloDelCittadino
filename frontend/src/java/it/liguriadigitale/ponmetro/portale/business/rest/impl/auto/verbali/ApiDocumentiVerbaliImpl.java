package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.DocumentiApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DocumentiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Documento;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import org.apache.commons.lang.NotImplementedException;

public class ApiDocumentiVerbaliImpl implements DocumentiApi {

  private DocumentiApi instance;

  public ApiDocumentiVerbaliImpl(DocumentiApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Documento addAllegatoToDocumentoId(String var1, String var2, FileAllegato var3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public void deleteDocumentFile(Integer var1, Integer var2, Integer var3) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public void deleteFineDocument(Integer var1, Integer var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Documento getFineDocument(String var1, String var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public DocumentiCollection getFineDocuments(String var1) {
    return instance.getFineDocuments(var1);
  }

  @Override
  public Documento setDocumentoToVerbaleId(String var1, Documento var2) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }
}
