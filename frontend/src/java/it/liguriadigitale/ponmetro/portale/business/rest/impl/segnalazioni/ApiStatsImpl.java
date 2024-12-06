package it.liguriadigitale.ponmetro.portale.business.rest.impl.segnalazioni;

import it.liguriadigitale.ponmetro.segnalazioni.apiclient.StatisticsApi;
import it.liguriadigitale.ponmetro.segnalazioni.model.Stat;
import it.liguriadigitale.ponmetro.segnalazioni.model.StatCollection;
import org.apache.commons.lang.NotImplementedException;

public class ApiStatsImpl implements StatisticsApi {

  private StatisticsApi instance;

  public ApiStatsImpl(StatisticsApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public Stat getStatByIdentifier(String var1, String var2, Integer var3, Integer var4) {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }

  @Override
  public Stat getUserStatByIdentifier(
      String var1, String var2, String var3, Integer var4, Integer var5) {
    return instance.getUserStatByIdentifier(var1, var2, var3, var4, var5);
  }

  @Override
  public StatCollection loadStats() {
    // Non utilizzato nel FdC
    throw new NotImplementedException();
  }
}
