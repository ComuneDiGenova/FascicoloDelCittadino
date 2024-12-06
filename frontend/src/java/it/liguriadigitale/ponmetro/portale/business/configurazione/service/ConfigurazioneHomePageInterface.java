package it.liguriadigitale.ponmetro.portale.business.configurazione.service;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioneChiaveValore;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.SottopagineDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import java.util.List;

public interface ConfigurazioneHomePageInterface {

  public boolean isVisibilePagoPa();

  public boolean isVisibileSegnalazioniCzrm();

  public boolean isVisibileMessaggi();

  public boolean isVisibileScadenze();

  public boolean isVisibileRicerca();

  public boolean isVisibilePrivacy();

  public String getUrlCovid();

  public String getUrlFascicoloSanitarioElettronico();

  public List<FunzioniDisponibili> getFunzioniBySezione(
      List<FunzioniDisponibili> listaFunzioni, Long idSezione);

  public List<FunzioneChiaveValore> getListaFunzioniInEvidenza(String chiave);

  public List<ChiaveValore> getListaFunzioniInRealizzazione(String chiave);

  public String getStringaRisorsaDalDb(String chiave);

  public List<SottopagineDisponibili> getElencoSottoPagine();
}
