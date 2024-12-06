package it.liguriadigitale.ponmetro.portale.business.rest.impl.tributi;

import it.liguriadigitale.ponmetro.tributi.apiclient.FascicoloDelContribuenteRestControllerApi;
import it.liguriadigitale.ponmetro.tributi.model.Allegato;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioPraticaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioProprietaUtenza;
import it.liguriadigitale.ponmetro.tributi.model.ElencoDocumenti;
import it.liguriadigitale.ponmetro.tributi.model.ElencoVersamenti;
import it.liguriadigitale.ponmetro.tributi.model.IstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.PraticaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.PraticaRimborsoPost;
import it.liguriadigitale.ponmetro.tributi.model.ProprietaUtenze;
import it.liguriadigitale.ponmetro.tributi.model.ProtocollazioneIstanzaRimborso;
import it.liguriadigitale.ponmetro.tributi.model.Risposta;
import it.liguriadigitale.ponmetro.tributi.model.Scadenze;
import it.liguriadigitale.ponmetro.tributi.model.SchedaTributo;
import it.liguriadigitale.ponmetro.tributi.model.Tributi;
import java.util.List;

public class TributiImpl implements FascicoloDelContribuenteRestControllerApi {

  private FascicoloDelContribuenteRestControllerApi instance;

  public TributiImpl(FascicoloDelContribuenteRestControllerApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DettaglioProprietaUtenza getDettaglioProprietaUtenze(Integer arg0) {
    return instance.getDettaglioProprietaUtenze(arg0);
  }

  @Override
  public ElencoVersamenti getElencoVersamenti(String arg0) {
    return instance.getElencoVersamenti(arg0);
  }

  @Override
  public List<ProprietaUtenze> getProprietaUtenze(String arg0, String arg1) {
    return instance.getProprietaUtenze(arg0, arg1);
  }

  @Override
  public List<Tributi> getQuadroTributario(String arg0) {
    return instance.getQuadroTributario(arg0);
  }

  @Override
  public List<SchedaTributo> getSchedaTributo(String arg0) {
    return instance.getSchedaTributo(arg0);
  }

  @Override
  public Scadenze getElencoScadenze(String arg0) {
    return instance.getElencoScadenze(arg0);
  }

  @Override
  public ElencoDocumenti stampaAccertamento(String arg0, String arg1) {
    return instance.stampaAccertamento(arg0, arg1);
  }

  @Override
  public String contributoTariffarioTari(String arg0) {
    // TODO Auto-generated method stub
    return instance.contributoTariffarioTari(arg0);
  }

  @Override
  public List<PraticaRimborso> getPraticheRimborsoIMU(String arg0) {
    // TODO Auto-generated method stub
    return instance.getPraticheRimborsoIMU(arg0);
  }

  @Override
  public Risposta richiestaHelloWorld(String arg0) {
    // TODO Auto-generated method stub
    return instance.richiestaHelloWorld(arg0);
  }

  @Override
  public IstanzaRimborso cancellaAllegato(Long arg0) {
    // TODO Auto-generated method stub
    return instance.cancellaAllegato(arg0);
  }

  @Override
  public DettaglioPraticaRimborso getDettaglioPraticaRimborsoIMU(Integer arg0) {
    // TODO Auto-generated method stub
    return instance.getDettaglioPraticaRimborsoIMU(arg0);
  }

  @Override
  public IstanzaRimborso praticaRimborsoImu(PraticaRimborsoPost arg0) {
    // TODO Auto-generated method stub
    return instance.praticaRimborsoImu(arg0);
  }

  @Override
  public IstanzaRimborso inserisciAllegato(
      String arg0, Long arg1, String arg2, String arg3, String arg4) {
    // TODO Auto-generated method stub
    return instance.inserisciAllegato(arg0, arg1, arg2, arg3, arg4);
  }

  @Override
  public ProtocollazioneIstanzaRimborso praticaRimborsoIMUProtocollazione(
      String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, Long arg6) {
    // TODO Auto-generated method stub
    return instance.praticaRimborsoIMUProtocollazione(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
  }

  @Override
  public IstanzaRimborso praticaRimborsoImuAnnullamento(Long arg0) {
    // TODO Auto-generated method stub
    return instance.praticaRimborsoImuAnnullamento(arg0);
  }

  @Override
  public List<Allegato> getElencoAllegatiPraticaRimborso(String arg0, Long arg1) {
    // TODO Auto-generated method stub
    return instance.getElencoAllegatiPraticaRimborso(arg0, arg1);
  }
}
