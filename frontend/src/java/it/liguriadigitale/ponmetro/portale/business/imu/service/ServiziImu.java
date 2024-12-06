package it.liguriadigitale.ponmetro.portale.business.imu.service;

import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import java.util.List;

public interface ServiziImu {

  List<BreadcrumbFdC> getListaBreadcrumb();

  List<MessaggiInformativi> popolaListaMessaggi();
}
