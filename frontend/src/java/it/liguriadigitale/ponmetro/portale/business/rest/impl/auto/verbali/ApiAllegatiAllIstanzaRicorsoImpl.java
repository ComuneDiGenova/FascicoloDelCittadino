package it.liguriadigitale.ponmetro.portale.business.rest.impl.auto.verbali;

import it.liguriadigitale.ponmetro.verbaliContravvenzioni.apiclient.AllegatiAllIstanzaRicorsoApi;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AllegatiCollection;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Allegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;

public class ApiAllegatiAllIstanzaRicorsoImpl implements AllegatiAllIstanzaRicorsoApi {

  private AllegatiAllIstanzaRicorsoApi instance;

  public ApiAllegatiAllIstanzaRicorsoImpl(AllegatiAllIstanzaRicorsoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Allegato addAttachmentToCancellationRequest(
      String arg0, String arg1, String arg2, Integer arg3, FileAllegato arg4) {
    return instance.addAttachmentToCancellationRequest(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public void deleteCancellationRequestAttachment(
      Integer arg0, String arg1, Integer arg2, Integer arg3) {
    instance.deleteCancellationRequestAttachment(arg0, arg1, arg2, arg3);
  }

  @Override
  public FileAllegato getCancellationRequestAttachment(
      String arg0, String arg1, String arg2, Integer arg3) {
    return instance.getCancellationRequestAttachment(arg0, arg1, arg2, arg3);
  }

  @Override
  public AllegatiCollection getCancellationRequestAttachments(
      String arg0, String arg1, Integer arg2) {
    return instance.getCancellationRequestAttachments(arg0, arg1, arg2);
  }

  @Override
  public Allegato setAllegatoToCancellationRequest(
      String arg0, String arg1, Integer arg2, Allegato arg3) {
    return instance.setAllegatoToCancellationRequest(arg0, arg1, arg2, arg3);
  }
}
