package it.liguriadigitale.ponmetro.portale.business.imu.impl;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.business.imu.service.ServiziImu;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiziImuImpl implements ServiziImu {

  @SuppressWarnings("unused")
  private Log log = LogFactory.getLog(getClass());

  @SuppressWarnings("unused")
  private static final String ERRORE_API_IMU = "Errore di connessione alle API IMU";

  @Override
  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io Abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("rimborsoImu", "Rimborso IMU"));

    return listaBreadcrumb;
  }

  @Override
  public List<MessaggiInformativi> popolaListaMessaggi() {
    List<MessaggiInformativi> listaMessaggi = new ArrayList<>();

    MessaggiInformativi messaggio1 = new MessaggiInformativi();
    messaggio1.setMessaggio("Messaggio ...");
    messaggio1.setType("info");
    listaMessaggi.add(messaggio1);

    return listaMessaggi;
  }
}
