package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiPersoneACaricoContributoTariNetribe;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class DatiPersoneACaricoContributTariNetribeMapper
    extends PropertyMap<
        DatiPersoneACaricoContributoTariNetribe,
        DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> {

  @Override
  protected void configure() {

    map().setCodiceFiscale(source.getCodiceFiscale());
    map().setCognome(source.getCognome());
    map().setNome(source.getNome());
    using(flagACaricoConverter).map(source.getFlagIsACarico(), destination.getFlagIsACarico());
    map().setCittadinanza(source.getCittadinanza());
  }

  private Converter<String, Integer> flagACaricoConverter =
      new Converter<String, Integer>() {

        @Override
        public Integer convert(MappingContext<String, Integer> context) {
          String valore = context.getSource().equalsIgnoreCase("No") ? "0" : "1";
          return Integer.parseInt(valore);
        }
      };
}
