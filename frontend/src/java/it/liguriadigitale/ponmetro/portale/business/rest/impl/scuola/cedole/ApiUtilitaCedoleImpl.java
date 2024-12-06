package it.liguriadigitale.ponmetro.portale.business.rest.impl.scuola.cedole;

import it.liguriadigitale.ponmetro.scuola.cedole.apiclient.PortaleFdcApi;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoCartolibrerie;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoClassi;
import it.liguriadigitale.ponmetro.scuola.cedole.model.ElencoScuole;

public class ApiUtilitaCedoleImpl implements PortaleFdcApi {

  private PortaleFdcApi instance;

  public ApiUtilitaCedoleImpl(PortaleFdcApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public ElencoCartolibrerie cartolibrerieGet() {
    return instance.cartolibrerieGet();
  }

  @Override
  public ElencoClassi classiGet(String arg0) {
    return instance.classiGet(arg0);
  }

  @Override
  public ElencoScuole scuoleGet() {
    return instance.scuoleGet();
  }
}
