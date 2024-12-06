package it.liguriadigitale.ponmetro.portale.business.rest.impl.backend;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaFunzioni;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ListaPagine;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.api.presentation.rest.home.service.HomeRestInterface;
import java.util.List;

public class ApiServiziHomePageImpl implements HomeRestInterface {

  private HomeRestInterface instance;

  public ApiServiziHomePageImpl(HomeRestInterface instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ListaFunzioni getListaFunzioni() {
    return instance.getListaFunzioni();
  }

  @Override
  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String arg0) {
    return instance.getListaFunzioniInEvidenza(arg0);
  }

  @Override
  public List<ChiaveValore> getListaFunzioniInRealizzazione(String arg0) {
    return instance.getListaFunzioniInRealizzazione(arg0);
  }

  @Override
  public ChiaveValore getValore(String arg0) {
    return instance.getValore(arg0);
  }

  @Override
  public Boolean getAbilitazioneIngresso(String arg0) {
    return instance.getAbilitazioneIngresso(arg0);
  }

  @Override
  public List<MessaggiInformativi> getMessaggiInformativi(String arg0) {
    return instance.getMessaggiInformativi(arg0);
  }

  @Override
  public ListaPagine getListaPagine() {
    return instance.getListaPagine();
  }

  @Override
  public List<CfgTCatFunzLink> getLinkEsterni(String arg0) {
    return instance.getLinkEsterni(arg0);
  }

  @Override
  public List<CfgTCatFunzLink> getLinkEsterniPerIdFunz(String arg0) {
    return instance.getLinkEsterniPerIdFunz(arg0);
  }
}
