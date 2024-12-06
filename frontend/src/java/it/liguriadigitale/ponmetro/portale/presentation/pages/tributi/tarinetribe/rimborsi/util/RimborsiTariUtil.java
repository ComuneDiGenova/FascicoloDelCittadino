package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.util;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaRimborsoTariNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.Saldi;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RimborsiTariUtil {

  public static DatiRichiestaRimborsoTariNetribe setDatiRichiedenteRimborso(
      Utente utente, boolean isIntestatario) {
    DatiRichiestaRimborsoTariNetribe datiRimborso = new DatiRichiestaRimborsoTariNetribe();

    datiRimborso.setIntestatario(isIntestatario);
    if (isIntestatario) {
      datiRimborso.setTipologiaRichiedenteRimborso(TipologiaRichiedenteRimborsoEnum.INTESTATARIO);

      datiRimborso.setNomeIntestatario(utente.getNome());
      datiRimborso.setCognomeIntestatario(utente.getCognome());
      datiRimborso.setCodiceFiscaleIntestatario(utente.getCodiceFiscaleOperatore());
    }

    if (!isIntestatario) {
      List<Saldi> listaSaldi = new ArrayList<>();

      Saldi saldoTefa = new Saldi();
      saldoTefa.setTributo("TEFA");
      saldoTefa.setImporto(null);
      listaSaldi.add(saldoTefa);

      Saldi saldoTari = new Saldi();
      saldoTari.setTributo("3944");
      saldoTari.setImporto(null);
      listaSaldi.add(saldoTari);

      datiRimborso.setListaSaldi(listaSaldi);
    }

    datiRimborso.setDataInvioRichiesta(LocalDate.now());

    datiRimborso.setCodiceFiscaleRichiedente(utente.getCodiceFiscaleOperatore());
    datiRimborso.setNomeRichiedente(utente.getNome());
    datiRimborso.setCognomeRichiedente(utente.getCognome());
    datiRimborso.setEmailRichiedente(utente.getMail());

    if (!datiRimborso.isIntestatario()
        && utente.isResidente()
        && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente())
        && LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente().getCpvHasAddress())) {
      datiRimborso.setIndirizzoRichiedente(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
      datiRimborso.setComuneRichiedente(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
      datiRimborso.setCapRichiedente(
          utente.getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
    }

    return datiRimborso;
  }
}
