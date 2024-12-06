package it.liguriadigitale.ponmetro.portale.business.rest.impl.allerte.cortesia;

import it.liguriadigitale.ponmetro.allertecortesia.apiclient.RegistrazioneServiziCortesiaApi;
import it.liguriadigitale.ponmetro.allertecortesia.model.AggiornamentoRecapitiResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.allertecortesia.model.InserimentoResponse;
import it.liguriadigitale.ponmetro.allertecortesia.model.StradeCivici;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServizi;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;

public class ApiAllerteCortesiaImpl implements RegistrazioneServiziCortesiaApi {

  private RegistrazioneServiziCortesiaApi instance;

  public ApiAllerteCortesiaImpl(RegistrazioneServiziCortesiaApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public VerificaServiziResponse getWsGetListaServizi(String arg0) {
    return instance.getWsGetListaServizi(arg0);
  }

  @Override
  public VerificaServiziResponse getWsGetServizioById(String arg0, String arg1) {
    return instance.getWsGetServizioById(arg0, arg1);
  }

  @Override
  public DettagliUtente getWsLoginCF(String arg0) {
    return instance.getWsLoginCF(arg0);
  }

  //	@Override
  //	@Encoded
  //	public DettagliUtente getWsLoginEMAIL(String arg0, @Encoded String arg1) {
  //		return instance.getWsLoginEMAIL(arg0, arg1);
  //	}

  @Override
  public DettagliUtente getWsLoginEMAIL(String arg0, String arg1, String arg2) {
    return instance.getWsLoginEMAIL(arg0, arg1, arg2);
  }

  @Override
  public VerificaServizi putWsIscrizioneAdUnServizio(String arg0, String arg1) {
    return instance.putWsIscrizioneAdUnServizio(arg0, arg1);
  }

  @Override
  public AggiornamentoRecapitiResponse putWsUpdateUtente(
      String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
    return instance.putWsUpdateUtente(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
  }

  @Override
  public VerificaServizi putWsCancellazioneAdUnServizio(String arg0, String arg1) {
    return instance.putWsCancellazioneAdUnServizio(arg0, arg1);
  }

  @Override
  public AggiornamentoRecapitiResponse putWsUpdateUtenteMail(String arg0, String arg1) {
    return instance.putWsUpdateUtenteMail(arg0, arg1);
  }

  @Override
  public AggiornamentoRecapitiResponse putWsUpdateUtenteTelefonoCellulare(
      String arg0, String arg1) {
    return instance.putWsUpdateUtenteTelefonoCellulare(arg0, arg1);
  }

  @Override
  public InserimentoResponse putWsPutUtente(
      String arg0,
      String arg1,
      String arg2,
      String arg3,
      String arg4,
      String arg5,
      String arg6,
      String arg7,
      String arg8,
      String arg9,
      String arg10,
      String arg11) {
    return instance.putWsPutUtente(
        arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11);
  }

  @Override
  public AggiornamentoRecapitiResponse putWsPutCheckTelefonoSMS(String arg0, String arg1) {
    return instance.putWsPutCheckTelefonoSMS(arg0, arg1);
  }

  @Override
  public AggiornamentoRecapitiResponse getWsGetCheckTelefonoSMS(String arg0, String arg1) {
    return instance.getWsGetCheckTelefonoSMS(arg0, arg1);
  }

  @Override
  public StradeCivici getWsGetStradeCivici(
      String arg0, String arg1, String arg2, String arg3, String arg4) {
    return instance.getWsGetStradeCivici(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public InserimentoResponse putWsAggiuntaPreferenzaServizio(
      String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    return instance.putWsAggiuntaPreferenzaServizio(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  @Override
  public InserimentoResponse putWsCancellazionePreferenzaServizio(
      String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
    return instance.putWsCancellazionePreferenzaServizio(arg0, arg1, arg2, arg3, arg4, arg5);
  }

  @Override
  public AggiornamentoRecapitiResponse getWsGetCheckEmail(String arg0) {
    return instance.getWsGetCheckEmail(arg0);
  }
}
