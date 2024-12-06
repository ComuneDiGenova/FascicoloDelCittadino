package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiModalitaPagamentoAgevolazioneTariffariaTari.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiestaAgevolazioneTariffariaTari;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.Condition;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class DatiRichiestaContributoTariNetribeMapper
    extends PropertyMap<DatiDomandaContributoTari, DatiRichiestaAgevolazioneTariffariaTari> {

  private Log log = LogFactory.getLog(getClass());

  @Override
  protected void configure() {

    map().getDatiRichiedente().setNome(source.getNome());
    map().getDatiRichiedente().setCognome(source.getCognome());
    map().getDatiRichiedente().setCodiceFiscale(source.getCodiceFiscale());
    map().getDatiRichiedente().getCittadinanza().setDescrizione(source.getCittadinanza());
    map().getDatiRichiedente().getCittadinanza().setCodice(source.getCodiceCittadinanza());
    map().getDatiRichiedente().setMail(source.getEmail());
    map().getDatiRichiedente().setTelefono(source.getCellulare());

    map()
        .getAutodichiarazioniRichiedente()
        .setFlagCategoriaImmobileDiversaDaA1A8A9(source.getFlagCategoriaImmobileDiversaDaA1A8A9());
    map()
        .getAutodichiarazioniRichiedente()
        .setFlagSuperficieImmobileEntroMq(source.getFlagSuperficieImmobileEntroMq());
    map()
        .getAutodichiarazioniRichiedente()
        .setFlagPensionatoSuperiore70anni(source.getFlagPensionatoSuperiore70anni());
    map()
        .getAutodichiarazioniRichiedente()
        .setFlagPensionatoSuperiore70anni1FamiliareACarico(
            source.getFlagPensionatoSuperiore70anni1FamiliareACarico());
    map()
        .getAutodichiarazioniRichiedente()
        .setFlagBeneficarioNonFruitoreAltreAgevolazioni(
            source.getFlagBeneficarioNonFruitoreAltreAgevolazioni());
    map()
        .getAutodichiarazioniRichiedente()
        .setFlagBeneficarioConFigliACaricoMax4(source.getFlagBeneficarioConFigliACaricoMax4());
    map().getAutodichiarazioniRichiedente().setFlagInRegolaTari(source.getFlagInRegolaTari());

    map().setDatiPersoneACaricoONoRichiedente(source.getDatiPersoneACarico());

    map().getModalitaPagamento().setModalitaPagamento(source.getModalitaDiPagamento());
    map()
        .getModalitaPagamento()
        .getDelegatoRitiro()
        .getDatiPersonali()
        .setNome(source.getNomeDelegato());
    map()
        .getModalitaPagamento()
        .getDelegatoRitiro()
        .getDatiPersonali()
        .setCognome(source.getCognomeDelegato());
    map()
        .getModalitaPagamento()
        .getDelegatoRitiro()
        .getDatiPersonali()
        .setCodiceFiscale(source.getCodiceFiscaleDelegato());
    map().getModalitaPagamento().getDelegatoRitiro().setFileAllegato(source.getFileAllegato());

    when(isModalitaPagamentoContoCorrente())
        .map()
        .getModalitaPagamento()
        .getCoordinateEIntestatarioContoBancario()
        .getDatiIntestatario()
        .setNome(source.getNomeDelegato());
    when(isModalitaPagamentoContoCorrente())
        .map()
        .getModalitaPagamento()
        .getCoordinateEIntestatarioContoBancario()
        .getDatiIntestatario()
        .setCognome(source.getCognomeDelegato());
    when(isModalitaPagamentoContoCorrente())
        .map()
        .getModalitaPagamento()
        .getCoordinateEIntestatarioContoBancario()
        .getDatiIntestatario()
        .setCodiceFiscale(source.getCodiceFiscaleDelegato());
    when(isModalitaPagamentoContoCorrente())
        .map()
        .getModalitaPagamento()
        .getCoordinateEIntestatarioContoBancario()
        .setIban(source.getIban());
    when(isModalitaPagamentoContoCorrente())
        .map()
        .getModalitaPagamento()
        .getCoordinateEIntestatarioContoBancario()
        .setSwift(source.getSwift());

    map().setIseeRichiederenteECoresidenti(source.getDatiIseeTuttiNuclei());

    map().setDatiResidenti(source.getListaDatiResidenti());
  }

  private Condition<DatiDomandaContributoTari, DatiRichiestaAgevolazioneTariffariaTari>
      isModalitaPagamentoContoCorrente() {
    return new Condition<DatiDomandaContributoTari, DatiRichiestaAgevolazioneTariffariaTari>() {

      @Override
      public boolean applies(
          MappingContext<DatiDomandaContributoTari, DatiRichiestaAgevolazioneTariffariaTari>
              context) {

        return ((DatiDomandaContributoTari) context.getParent().getSource())
            .getModalitaDiPagamento()
            .value()
            .equalsIgnoreCase(ModalitaPagamentoEnum.CAB.value());
      }
    };
  }
}
