package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class DatiRichiestaRimborsoTariNetribeMapper
    extends PropertyMap<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> {

  @Override
  protected void configure() {

    map().getDatiRichiedenteRimborso().setCfPIva(source.getCodiceFiscaleIntestatario());
    map().getDatiRichiedenteRimborso().setNome(source.getNomeRichiedente());
    map().getDatiRichiedenteRimborso().setCognome(source.getCognomeRichiedente());
    map()
        .getDatiRichiedenteRimborso()
        .setTipologiaRichiedenteRimborso(source.getTipologiaRichiedenteRimborso());
    map().getDatiRichiedenteRimborso().setIdAvviso(source.getIdAvvisoBolletta());
    map().getDatiRichiedenteRimborso().setImportoRimborso(source.getImportoRimborso());
    map().getDatiRichiedenteRimborso().setEmailDiContatto(source.getEmailRichiedente());
    map().getDatiRichiedenteRimborso().setDataIstanza(source.getDataInvioRichiesta());
    map().getDatiRichiedenteRimborso().setNote(source.getNote());
    map().getDatiRichiedenteRimborso().setSaldi(source.getListaSaldi());

    map().getDatiIstanza().setModalitaPagamento(source.getModalitaDiPagamento());
    map().getDatiIstanza().setIban(source.getIban());
    map().getDatiIstanza().setSwift(source.getSwift());
    map().getDatiIstanza().setNominativoDelegato(source.getNominativoDelegato());
    map().getDatiIstanza().setCodiceFiscaleDelegato(source.getCodiceFiscaleDelegato());

    when(isRimborsoErede())
        .map()
        .getDatiIstanza()
        .setCodiceFiscaleErede(source.getCodiceFiscaleRichiedente());
    when(isRimborsoErede())
        .map()
        .getDatiIstanza()
        .setNominativoErede(source.getNominativoRichiedente());
    when(isRimborsoErede())
        .map()
        .getDatiIstanza()
        .setIndirizzoErede(source.getIndirizzoRichiedente());
    when(isRimborsoErede()).map().getDatiIstanza().setComuneErede(source.getComuneRichiedente());

    using(capFromStringToInt)
        .map(source.getCapRichiedente(), destination.getDatiIstanza().getCapErede());

    // when(isRimborsoErede()).map().getDatiIstanza().setDocumentiAllegati(source.getListaAllegati());

    map().getDatiIstanza().setDocumentiAllegati(source.getListaAllegati());
  }

  private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> isRimborsoErede() {
    return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {

      @Override
      public boolean applies(
          MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> context) {
        return !((DatiRichiestaRimborsoTariNetribe) context.getParent().getSource())
            .isIntestatario();
      }
    };
  }

  private Converter<String, Integer> capFromStringToInt =
      new Converter<String, Integer>() {

        @Override
        public Integer convert(MappingContext<String, Integer> context) {
          return Integer.parseInt(context.getSource());
        }
      };

  //	private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> eredeNominativoNotNull() {
  //		return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {
  //
  //			@Override
  //			public boolean applies(MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>
  // context) {
  //				return ((DatiRichiestaRimborsoTariNetribe)
  // context.getParent().getSource()).getNominativoRichiedente() != null;
  //			}
  //
  //		};
  //	}
  //
  //	private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> eredeCodiceFiscaleNotNull() {
  //		return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {
  //
  //			@Override
  //			public boolean applies(MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>
  // context) {
  //				return ((DatiRichiestaRimborsoTariNetribe)
  // context.getParent().getSource()).getCodiceFiscaleRichiedente() != null;
  //			}
  //
  //		};
  //	}
  //
  //	private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> eredeIndirizzoNotNull() {
  //		return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {
  //
  //			@Override
  //			public boolean applies(MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>
  // context) {
  //				return ((DatiRichiestaRimborsoTariNetribe)
  // context.getParent().getSource()).getIndirizzoRichiedente() != null;
  //			}
  //
  //		};
  //	}
  //
  //	private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> eredeComuneNotNull() {
  //		return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {
  //
  //			@Override
  //			public boolean applies(MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>
  // context) {
  //				return ((DatiRichiestaRimborsoTariNetribe)
  // context.getParent().getSource()).getComuneRichiedente() != null;
  //			}
  //
  //		};
  //	}
  //
  //	private Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi> eredeCapNotNull() {
  //		return new Condition<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>() {
  //
  //			@Override
  //			public boolean applies(MappingContext<DatiRichiestaRimborsoTariNetribe, TARIRimborsi>
  // context) {
  //				return ((DatiRichiestaRimborsoTariNetribe)
  // context.getParent().getSource()).getCapRichiedente() != null;
  //			}
  //
  //		};
  //	}

}
