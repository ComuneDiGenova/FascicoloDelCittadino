package it.liguriadigitale.ponmetro.portale.business.biblioteche.connector;

import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class FdCConnector {
  private static Log log = LogFactory.getLog(FdCConnector.class);

  public static String sendPatch(String urlAddress, String json, String cdBib)
      throws IOException, URISyntaxException {

    HttpClient client = new DefaultHttpClient();
    URI uri = new URIBuilder(urlAddress).addParameter("cdBib", cdBib).build();
    HttpPatch httpPatch = new HttpPatch(uri);
    httpPatch.setHeader("Authorization", "Bearer " + BaseServiceImpl.TOKEN_COMGE);
    StringEntity stringData = new StringEntity(json, ContentType.APPLICATION_JSON);
    httpPatch.setEntity(stringData);
    HttpResponse response = client.execute(httpPatch);

    if (response.getStatusLine().getStatusCode() >= 200
        && response.getStatusLine().getStatusCode() <= 300) {
      return "OK";
    } else {
      log.error("Errore " + response.getStatusLine().getStatusCode());
      String result = EntityUtils.toString(response.getEntity());
      log.error(result);
      throw new IOException(
          "Errore chiamata al servizio " + response.getStatusLine().getStatusCode());
    }
  }
}
