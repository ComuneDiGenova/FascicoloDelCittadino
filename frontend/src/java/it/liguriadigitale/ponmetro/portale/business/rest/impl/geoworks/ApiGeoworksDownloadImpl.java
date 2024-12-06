package it.liguriadigitale.ponmetro.portale.business.rest.impl.geoworks;

import it.liguriadigitale.ponmetro.geoworks.apiclient.DownloadApi;
import java.io.File;

public class ApiGeoworksDownloadImpl implements DownloadApi {

  private DownloadApi instance;

  public ApiGeoworksDownloadImpl(DownloadApi instance) {
    super();
    this.instance = instance;
  }

  @Override
  public File getDownload(String arg0) {
    return instance.getDownload(arg0);
  }

  @Override
  public File postDownload(String arg0) {
    return instance.postDownload(arg0);
  }
}
