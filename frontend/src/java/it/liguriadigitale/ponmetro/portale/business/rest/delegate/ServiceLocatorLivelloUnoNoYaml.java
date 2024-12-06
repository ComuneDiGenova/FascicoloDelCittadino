package it.liguriadigitale.ponmetro.portale.business.rest.delegate;

import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.InpsNoYamlImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.InpsNoYamlService;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.globo.GloboNoYamlImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.noyaml.globo.GloboNoYamlService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceLocatorLivelloUnoNoYaml {

  protected Log log = LogFactory.getLog(getClass());

  public static ServiceLocatorLivelloUnoNoYaml getInstance() {
    return new ServiceLocatorLivelloUnoNoYaml();
  }

  public InpsNoYamlService getApiInpsSenzaYaml() {
    return new InpsNoYamlImpl();
  }

  public GloboNoYamlService getApiGloboSenzaYaml() {
    return new GloboNoYamlImpl();
  }
}
