package it.liguriadigitale.ponmetro.portale.business.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnlineSettings;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.ricerca.RicercaGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.util.List;

public interface GloboFrontendInterface {

  public List<VCfgTCatGlobo> getFunzioni() throws BusinessException;

  public List<VCfgTCatGlobo> getFunzioniGloboPerComparto(Long idComparto) throws BusinessException;

  public List<BreadcrumbFdC> getListaBreadcrumb(
      List<VCfgTCatGlobo> listaFiltrataProcedimenti, List<FunzioniDisponibili> list, Utente utente);

  // Pagina di ricerca
  public List<Node> getServiziOnlineGlobo() throws BusinessException;

  public GloboServiziOnlineSettings getServiziOnlineGloboSettings() throws BusinessException;

  public List<Node> filtraLista(List<Node> nodi, RicercaGlobo ricerca, Utente utente);

  // pratiche
  public List<PraticaGlobo> getPraticheGlobo(Utente utente) throws BusinessException;
}
