package it.liguriadigitale.ponmetro.api.business.livello1.rest.api;

import it.liguriadigitale.ponmetro.tassaauto.apiclient.TassaAutoApi;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiAvvioPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.DatiPagamentoBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettagliBolloMezzi;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioVeicolo;
import it.liguriadigitale.ponmetro.tassaauto.model.Errore;
import it.liguriadigitale.ponmetro.tassaauto.model.EsitoAnnullamentoPagamento;
import it.liguriadigitale.ponmetro.tassaauto.model.Pagamenti;
import it.liguriadigitale.ponmetro.tassaauto.model.VeicoliAttivi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApiVeicoloImpl implements TassaAutoApi {

  private Log log = LogFactory.getLog(getClass());

  private TassaAutoApi instance;

  public ApiVeicoloImpl(TassaAutoApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public DettaglioCalcoloBollo getCalcola(String arg0, String arg1, String arg2) {
    return instance.getCalcola(arg0, arg1, arg2);
  }

  @Override
  public DettagliBolloMezzi getCalcolaSituazione(String arg0, String arg1, String arg2) {
    return instance.getCalcolaSituazione(arg0, arg1, arg2);
  }

  @Override
  public DettaglioVeicolo getDettagliVeicoloAttivo(String arg0) {
    return instance.getDettagliVeicoloAttivo(arg0);
  }

  @Override
  public Pagamenti getPagamento(String arg0, String arg1) {
    return instance.getPagamento(arg0, arg1);
  }

  @Override
  public VeicoliAttivi getVeicoliAttivi(String arg0) {
    return instance.getVeicoliAttivi(arg0);
  }

  @Override
  public EsitoAnnullamentoPagamento postAnnullaPrenotazionePagoPa(String arg0) {
    return instance.postAnnullaPrenotazionePagoPa(arg0);
  }

  @Override
  public DatiAvvioPagamento postPagoTassaPagoPa(DatiPagamentoBollo arg0) {
    return instance.postPagoTassaPagoPa(arg0);
  }

  @Override
  public Errore status() {
    return instance.status();
  }
}
