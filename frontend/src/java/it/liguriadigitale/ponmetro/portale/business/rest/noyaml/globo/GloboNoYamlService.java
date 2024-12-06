package it.liguriadigitale.ponmetro.portale.business.rest.noyaml.globo;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnline;
import it.liguriadigitale.ponmetro.portale.pojo.globo.GloboServiziOnlineSettings;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import java.util.List;

public interface GloboNoYamlService {

  public GloboServiziOnlineSettings serviziOnlineSettimgs() throws BusinessException;

  public GloboServiziOnline serviziOnline() throws BusinessException;

  public List<PraticaGlobo> istanzeGlobo(String codiceFiscale) throws BusinessException;
}
