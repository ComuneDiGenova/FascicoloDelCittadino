package it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.service;

import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeDocumenti;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeSerie;
import java.util.List;

public interface SupportoIstanzeVerbaliPLInterface {

  public List<VPlTIstanzeSerie> getListaDatiMotivi(
      Boolean mettiliInAnd, Boolean wantEqual, String serie, String articolo, String codiceHermes);

  public List<VPlTIstanzeDocumenti> getListaDatiDocumenti(String codiceHermes);
}
