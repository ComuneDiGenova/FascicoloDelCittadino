package it.liguriadigitale.ponmetro.portale.business.genovaparcheggi.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.AvvisoPagoPA;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitsListResult;
import it.liguriadigitale.ponmetro.genovaparcheggihelper.model.BravPermessi;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import java.io.IOException;
import java.util.List;

public interface ServiziGenovaParcheggi {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<Legenda> getListaLegenda();

  List<MessaggiInformativi> popolaListaMessaggi();

  PermitsListResult getPermessiGePark(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  PermitsListResult getPermessiGeParkConFiltroDaFdCBackend(
      String codiceFiscale, List<BravPermessi> listaPermessiBrav)
      throws BusinessException, ApiException, IOException;

  PermitsListResult getPermessiGeParkPerPreloadingPage(String codiceFiscale)
      throws BusinessException, ApiException, IOException;

  List<PermitAllowedAction> getAzioniSulPermesso(Permit permitId)
      throws BusinessException, ApiException, IOException;

  List<PermitVerificationResult> permitVerification(String customerIdCode, String filterKey)
      throws BusinessException, ApiException, IOException;

  byte[] getAllegatoPdfQuietanza(Permit permit) throws BusinessException, ApiException, IOException;

  byte[] getGeneraAvvisoPagoPa(Permit permit) throws BusinessException, ApiException, IOException;

  void rinnovaUnPermesso(Permit permit) throws BusinessException, ApiException, IOException;

  void annullaPermesso(Permit permit) throws BusinessException, ApiException, IOException;

  public AvvisoPagoPA getAvvisoPagoPaIuv(Permit permit)
      throws BusinessException, ApiException, IOException;

  public AvvisoPagoPA getAvvisoByPermit(Permit permit)
      throws BusinessException, ApiException, IOException;

  AvvisoPagoPA getAvvisoByPermitConBoolean(Permit permit, boolean downloadPdf)
      throws BusinessException, ApiException, IOException;

  List<MessaggiInformativi> popolaListaMessaggiOK();

  List<MessaggiInformativi> popolaListaMessaggiKO();
}
