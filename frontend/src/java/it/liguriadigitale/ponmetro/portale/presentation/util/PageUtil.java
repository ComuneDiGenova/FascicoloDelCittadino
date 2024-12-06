package it.liguriadigitale.ponmetro.portale.presentation.util;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.GloboPage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

public class PageUtil {

  private static final String URL_LOGOUT_SHIBBOLET =
      "/Shibboleth.sso/Logout?return=http://servizionline.comune.genova.it/";

  protected static final Log log = LogFactory.getLog(PageUtil.class);

  public static String firstUpperCase(final String p) {
    return (p.substring(0, 1).toUpperCase() + p.substring(1, p.length()).toLowerCase());
  }

  public static String firstDownCase(final String p) {
    return (p.substring(0, 1).toLowerCase() + p.substring(1, p.length()));
  }

  public static ArrayList<String> elencoCampi(final Class<?> classe) {
    ArrayList<String> lista = new ArrayList<>();

    for (Method method : classe.getMethods()) {
      String nomeMetodo = method.getName();
      if (nomeMetodo.substring(0, 3).contains("get")) {
        String text = nomeMetodo.substring(3, nomeMetodo.length());
        text = PageUtil.firstDownCase(text);
        if (!text.equals("class")) lista.add(text);
      }
    }
    return lista;
  }

  public static String decodeDigitBoolean(final int valore) {
    String risposta = "n.d.";
    if (valore == 1) risposta = "Si";
    else if (valore == 2) risposta = "No";
    return risposta;
  }

  public static String decodeStringBoolean(final String valore) {
    int valoreInt;
    if ((valore != null) && (valore.length() > 0)) {
      valoreInt = Integer.parseInt(valore);
      return PageUtil.decodeDigitBoolean(valoreInt);
    }
    return "n.d.";
  }

  public static String escapeNullString(String value) {
    return value == null ? "" : value;
  }

  public static String escapeNullEmptyString(String value) {
    if ((value == null) || value.equalsIgnoreCase("")) return "0";
    else return value;
  }

  public static String getShibboletLogoutUrl() {
    return getServerUrl() + URL_LOGOUT_SHIBBOLET;
  }

  public static String getServerUrl() {

    String baseUrl =
        RequestCycle.get().getUrlRenderer().getBaseUrl().getProtocol()
            + "://"
            + RequestCycle.get().getUrlRenderer().getBaseUrl().getHost();
    if (RequestCycle.get().getUrlRenderer().getBaseUrl().getPort() != 80)
      baseUrl = baseUrl + ":" + RequestCycle.get().getUrlRenderer().getBaseUrl().getPort();
    return baseUrl;
  }

  public static AbstractResourceStreamWriter createResourceStream(final byte[] oggettoBinario) {
    return oggettoBinario != null
        ? createResourceStreamFromInputStream(new ByteArrayInputStream(oggettoBinario))
        : null;
  }

  public static AbstractResourceStreamWriter createResourceStreamFromInputStream(
      final ByteArrayInputStream htmlIn) {
    return new AbstractResourceStreamWriter() {

      private static final long serialVersionUID = 5262438267644350346L;

      @Override
      public void write(OutputStream out) throws IOException {
        final byte[] outputByte = new byte[htmlIn.available()];
        while (htmlIn.read(outputByte, 0, htmlIn.available()) != -1) {
          out.write(outputByte, 0, outputByte.length);
        }
        out.flush();
        htmlIn.close();
      }
    };
  }

  public static boolean isPaginaInElencoFornito(
      String simpleNamePaginaCorrente, List<DatiCompletiFunzione> elencoPagine) {
    log.debug("simpleNamePaginaCorrente= " + simpleNamePaginaCorrente);
    if (elencoPagine != null) {
      for (DatiCompletiFunzione funzione : elencoPagine) {
        String nomePagina = funzione.getDatiFunzione().getClassePagina();
        if (nomePagina != null
            && nomePagina.equalsIgnoreCase(GloboPage.class.getName())
            && nomePagina.equalsIgnoreCase(simpleNamePaginaCorrente)) {
          log.debug("nomePagina= " + nomePagina + " trovata in elenco");
          return true;
        }
      }
    }

    return false;
  }

  public static boolean isNomePaginaInElencoFornito(
      String simpleNamePaginaCorrente, List<String> elencoPagine) {
    log.debug("simpleNamePaginaCorrente= " + simpleNamePaginaCorrente);
    if (elencoPagine != null) {
      for (String nomePagina : elencoPagine) {
        log.trace("nomePagina= " + nomePagina);
        if (nomePagina != null && nomePagina.equalsIgnoreCase(simpleNamePaginaCorrente)) {
          return true;
        }
      }
    }

    return false;
  }

  public static int isNumeroFunzioniPerSezione(
      List<FunzioniDisponibili> lista, String nomeSezione) {
    int numeroFunzioniTrovate = 0;
    for (FunzioniDisponibili funzioni : lista) {
      if (funzioni.getDenominazioneSez().equalsIgnoreCase(nomeSezione)) {
        numeroFunzioniTrovate = numeroFunzioniTrovate + 1;
      }
    }
    return numeroFunzioniTrovate;
  }

  public static boolean isStringValid(String string) {
    return string != null && !string.trim().isEmpty();
  }

  /**
   * Metodo che restituisce il primo anno della coppia anno scolastico in base al mese in cui siamo.
   * Con "primo anno della coppia" della coppia as xx/yy s'intende l'anno xx
   *
   * @return primo anno della coppia anno scolastico
   * @example
   */
  public static Long getLongAnnoPartenzaAnnoScolastico() {
    int anno = LocalDate.now().getYear();
    int mese = LocalDate.now().getMonthValue();
    int meseLimite = Month.MARCH.getValue();
    log.debug("CP anno = " + anno + " mese " + mese + " mese limite " + meseLimite);
    return new Long(mese > meseLimite ? anno : anno - 1);
  }

  public static String getStringYYYYAnnoScolastico(Long primoAnno) {
    return getStringAnnoScolasticoAsIs(primoAnno < 70 ? primoAnno + 2000L : primoAnno);
  }

  public static String getStringYYAnnoScolastico(Long primoAnno) {
    return getStringAnnoScolasticoAsIs(primoAnno % 100L);
  }

  public static String getStringAnnoScolasticoAsIs(Long primoAnno) {
    return (new StringBuilder()).append(primoAnno).append(" / ").append(primoAnno + 1).toString();
  }

  public static String convertiAAAAMMDDFormatoData(String inputDate) {
    if (inputDate == null) {
      return null;
    }
    try {
      return ""
          + inputDate.substring(6, 8)
          + "/"
          + inputDate.substring(4, 6)
          + "/"
          + inputDate.substring(0, 4);
    } catch (Exception e) {
      return null;
    }
  }

  public static String convertiAAAAminusMMminusDDFormatoData(String inputDate) {
    if (inputDate == null) {
      return null;
    }
    try {
      String[] elementi = inputDate.split("-");
      return elementi[2] + "/" + elementi[1] + "/" + elementi[0];
    } catch (Exception e) {
      return null;
    }
  }

  public static Integer getYearFromAAAAminusMMminusDDOrActualYear(String inputDate) {
    return inputDate == null ? LocalDate.now().getYear() : Integer.valueOf(inputDate.split("-")[0]);
  }

  public static String convertiImportoToEuroZeroWantNull(Double inputValue) {
    return convertiImportoToEuro(inputValue, true);
  }

  public static String convertiImportoToEuro(Double inputValue, Boolean zeroToNull) {
    if (inputValue != 0.0 || !zeroToNull) {
      NumberFormat numberFormatEuro = NumberFormat.getCurrencyInstance(Locale.GERMANY);
      return numberFormatEuro.format(inputValue);
    }
    return null;
  }
}
