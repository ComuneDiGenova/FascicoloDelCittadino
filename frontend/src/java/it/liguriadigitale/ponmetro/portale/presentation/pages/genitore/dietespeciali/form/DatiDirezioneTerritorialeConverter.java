package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form;

import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDirezioneTerritoriale;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.convert.IConverter;

public class DatiDirezioneTerritorialeConverter implements IConverter<DatiDirezioneTerritoriale> {

  private static final long serialVersionUID = 3251433094703013493L;

  protected static Log log = LogFactory.getLog(DatiDirezioneTerritorialeConverter.class);

  private List<DatiDirezioneTerritoriale> listaDatiDirezioneTerritoriale;

  /*
   * (non-Javadoc)
   *
   * @see
   * org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String,
   * java.util.Locale)
   */
  @Override
  public DatiDirezioneTerritoriale convertToObject(String value, Locale locale) {
    log.debug("[autocomplete - convertToObject] Convert String to Object: " + value);
    DatiDirezioneTerritoriale dati = new DatiDirezioneTerritoriale();
    dati.setNome(value);

    return listaDatiDirezioneTerritoriale.stream()
        .filter(x -> x.getNome().equals(value))
        .findAny()
        .orElse(dati);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.apache.wicket.util.convert.IConverter#convertToString(java.lang.Object,
   * java.util.Locale)
   */
  @Override
  public String convertToString(DatiDirezioneTerritoriale value, Locale locale) {
    return value.getNome();
  }

  public void setListaDatiDirezioneTerritoriale(List<DatiDirezioneTerritoriale> values) {
    listaDatiDirezioneTerritoriale = values;
  }
}
