package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ServerPoc {

  public static List<RimborsoImu> GetListaRimborsiImu() {
    List<RimborsoImu> rimborsi = new ArrayList<RimborsoImu>();

    RimborsoImu rimborso = new RimborsoImu();
    rimborso.setNumeroDocumento(123456789);
    rimborso.setAnnoDocumento(2022);
    rimborso.setStato(StatoRimborsoEnum.C);
    rimborso.setDataPresentazione(LocalDate.now());
    rimborso.setVersamenti(getVersamenti());

    rimborsi.add(rimborso);

    RimborsoImu rimborso3 = new RimborsoImu();
    rimborso3.setNumeroDocumento(123456789);
    rimborso3.setAnnoDocumento(2022);
    rimborso3.setStato(StatoRimborsoEnum.P);
    rimborso3.setDataPresentazione(LocalDate.now().minusDays(2));

    RimborsoImu rimborso4 = new RimborsoImu();
    rimborso4.setNumeroDocumento(123456789);
    rimborso4.setAnnoDocumento(2022);
    rimborso4.setStato(StatoRimborsoEnum.E);
    rimborso4.setDataPresentazione(LocalDate.now().plusDays(2));

    RimborsoImu rimborso5 = new RimborsoImu();
    rimborso5.setNumeroDocumento(123456789);
    rimborso5.setAnnoDocumento(2022);
    rimborso5.setStato(StatoRimborsoEnum.S);
    rimborso5.setDataPresentazione(LocalDate.now().minusDays(1));

    rimborsi.add(rimborso);
    rimborsi.add(rimborso5);
    rimborsi.add(rimborso4);
    rimborsi.add(rimborso3);

    return rimborsi;
  }

  private static List<Versamento> getVersamenti() {
    List<Versamento> lvs = new ArrayList<Versamento>();
    for (int i = 2017; i < 2023; i++) {
      Versamento v = new Versamento();
      v.setAnno(i);
      v.setQuotaComune(BigDecimal.valueOf(index(25, 65)));
      v.setQuotaStato(BigDecimal.ZERO);
      v.setTotaleImporto(v.getTotaleImporto().add(v.getQuotaComune()));
      v.setMotivazioneVersamento(MotivazioneVersamentoEnum.values()[index(0, 3)]);
      v.setStato(StatoAnnualitaEnum.values()[index(0, 6)]);

      if (v.getMotivazioneVersamento().equals(MotivazioneVersamentoEnum.M15)) {
        v.setAltro("Altra Motivazione");
      }

      lvs.add(v);
    }

    return lvs;
  }

  private static int index(int min, int max) {
    Random r = new Random();
    return r.ints(min, max).findAny().getAsInt();
  }

  public static List<RimborsoImuAllegato> getListaRimborsiImuAllegati() {
    List<RimborsoImuAllegato> allegati = new ArrayList<RimborsoImuAllegato>();
    RimborsoImuAllegato allegato1 = new RimborsoImuAllegato();
    allegato1.setAllegato(TipoAllegatoEnum.AUTOCERTIFICAZIONE_EREDI);
    allegato1.setNomeFile("autocertifcazione_eredi.pdf");

    allegati.add(allegato1);

    RimborsoImuAllegato allegato3 = new RimborsoImuAllegato();
    allegato3.setAllegato(TipoAllegatoEnum.VERSAMENTO_TRIBUTARIO);
    allegato3.setNomeFile("ricevuta_versamento_1.pdf");

    allegati.add(allegato3);

    RimborsoImuAllegato allegato4 = new RimborsoImuAllegato();
    allegato4.setAllegato(TipoAllegatoEnum.VERSAMENTO_TRIBUTARIO);
    allegato4.setNomeFile("ricevuta_versamento_2.pdf");

    allegati.add(allegato4);

    return allegati;
  }
}
