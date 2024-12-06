package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasAddress;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.geoserver.impl.GeoserverImpl;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Immobile;
import it.liguriadigitale.ponmetro.portale.pojo.imu.RimborsoImu;
import it.liguriadigitale.ponmetro.portale.pojo.imu.SessoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.imu.Versamento;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.imu.rimborso.panel.RichiestaRimborsoImuPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;

public class RichiestaRimborsoImuPage extends LayoutNoFeedBackPage {
  private static final long serialVersionUID = 86544798765413156L;

  public RichiestaRimborsoImuPage() {
    super();

    log.debug("[RichiestaRateizzazionePage] Recupero il breadcrump per RichiestaRateizzazionePage");
    List<BreadcrumbFdC> listaBreadcrumb = getBreadcrumb();

    @SuppressWarnings({"unchecked", "rawtypes"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    RichiestaRimborsoImuPanel richiestaRimborsoPanel =
        new RichiestaRimborsoImuPanel("richiestaRimborsoImuPanel", creaRichiestaRimborsoImu());
    addOrReplace(richiestaRimborsoPanel);
  }

  public List<BreadcrumbFdC> getBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioContribuente", "io Contribuente"));
    listaBreadcrumb.add(new BreadcrumbFdC("rimborsiIMU", "Rimborsi IMU"));
    listaBreadcrumb.add(new BreadcrumbFdC("richiestaRimborsoImu", "Richiesta"));

    return listaBreadcrumb;
  }

  private RimborsoImu creaRichiestaRimborsoImu() {
    RimborsoImu rimborso = new RimborsoImu();
    rimborso.setNome(getUtente().getNome());
    rimborso.setCognome(getUtente().getCognome());
    rimborso.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    rimborso.setDataNascita(getUtente().getDataDiNascita());
    rimborso.setSesso(SessoEnum.valueOf(getUtente().getSesso()).toString());
    rimborso.setLuogoNascita(getUtente().getLuogoNascita());

    if (LabelFdCUtil.checkIfNotNull(
        getUtente().getDatiCittadinoResidente().getCpvHasCitizenship())) {
      rimborso.setCittadinanza(
          getUtente().getDatiCittadinoResidente().getCpvHasCitizenship().getClvCountry());
    }

    rimborso.setPec(getUtente().getPec());
    rimborso.setCellulare(getUtente().getMobile());
    rimborso.setEmail(getUtente().getMail());

    if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
      ResidenteCpvHasAddress residenza = getUtente().getDatiCittadinoResidente().getCpvHasAddress();
      rimborso.setProvincia("GE"); // ?
      rimborso.setComune(residenza.getClvCity());
      rimborso.setIndirizzo(
          residenza.getClvToponymQualifier() + " " + residenza.getClvOfficialStreetName());
      rimborso.setCivico(Integer.valueOf(residenza.getClvStreenNumberOnly()));
      rimborso.setCap(residenza.getClvPostCode());
      rimborso.setEsponente(residenza.getClvFlatExponent());
      rimborso.setColore(residenza.getClvStreetNumberColor());
      rimborso.setScala(residenza.getClvStair());
      rimborso.setInterno(residenza.getClvFlatNumberOnly());
    }

    rimborso.setVersamenti(new ArrayList<Versamento>());
    rimborso.setImmobili(new ArrayList<Immobile>());

    return rimborso;
  }

  private FeaturesGeoserver getResidenza() {
    GeoserverImpl geoServer = new GeoserverImpl();
    try {
      return geoServer.getToponomasticaResidenzaUtenteLoggato(getUtente());
    } catch (BusinessException | ApiException e) {
      // TODO Auto-generated catch block
      log.debug("[RichiestaRimborsoIMU - GetResidenza] Errore : " + e);
      return null;
    }
  }
}
