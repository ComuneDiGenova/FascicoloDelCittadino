package it.liguriadigitale.ponmetro.portale.business.cedole;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.scuola.cedole.model.AllegatoPDF;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Classe;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.RitiroCedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Scuola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.util.List;

public interface GestioneCedoleLibrarieService {

  public Cedola situazioneCorrenteCedola(UtenteServiziRistorazione minore, Integer anno)
      throws BusinessException;

  public AllegatoPDF getCedolaPDF(Cedola cedola) throws BusinessException, ApiException;

  public void ritiro(RitiroCedola ritiro) throws BusinessException, ApiException;

  public List<Cartolibreria> elencoCartoliberie() throws BusinessException, ApiException;

  public String verificaTipoCedola(UtenteServiziRistorazione iscritto)
      throws BusinessException, ApiException;

  public Cedola presentaDomanda(DomandaCedola domanda, Utente utente)
      throws BusinessException, ApiException;

  public List<Scuola> getScuole();

  public List<Classe> getClassi(String idScuola);

  public List<BreadcrumbFdC> getListaBreadcrumbPrivacy();
}
