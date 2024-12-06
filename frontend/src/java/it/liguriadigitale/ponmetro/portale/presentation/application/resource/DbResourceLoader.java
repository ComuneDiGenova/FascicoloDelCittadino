package it.liguriadigitale.ponmetro.portale.presentation.application.resource;

import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Component;
import org.apache.wicket.resource.loader.IStringResourceLoader;

public class DbResourceLoader implements IStringResourceLoader {

  private static Log log = LogFactory.getLog(DbResourceLoader.class);

  @Override
  public String loadStringResource(
      Class<?> clazz, String key, Locale locale, String style, String variation) {
    log.trace("clazz:" + clazz.getName());
    log.trace("key:" + key);
    log.trace("locale:" + locale);
    log.trace("style:" + style);
    log.trace("variation" + variation);
    return getStringaDalDB(key);
  }

  @Override
  public String loadStringResource(
      Component component, String key, Locale locale, String style, String variation) {
    log.trace("key:" + key);
    log.trace("locale:" + locale);
    log.trace("style:" + style);
    log.trace("variation" + variation);
    return getStringaDalDB(key);
  }

  private String getStringaDalDB(String key) {
    if (StringUtils.isNotBlank(key)) {
      return ServiceLocator.getInstance().getServiziHomePage().getStringaRisorsaDalDb(key);
    } else {
      return null;
    }
  }
}
