package it.liguriadigitale.ponmetro.portale.business.arpal.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.arpal.model.ItemA;
import it.liguriadigitale.ponmetro.arpal.model.ItemS;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ServiziArpal {

  public List<BreadcrumbFdC> getListaBreadcrumb();

  public List<MessaggiInformativi> popolaListaMessaggi();

  public List<ItemA> elencoProveLaboratorioAlims(Utente utente)
      throws ApiException, BusinessException;

  public List<ItemS> elencoProveLaboratorioSimpa(Utente utente)
      throws ApiException, BusinessException;

  public File getAllegatoPdfSimpa(String nomeFile)
      throws BusinessException, ApiException, IOException;

  public File getAllegatoPdfAlims(String nomeFile, String nomeDirectory)
      throws BusinessException, ApiException, IOException;

  List<MessaggiInformativi> popolaListaMessaggiArpal();
}
