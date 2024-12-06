package it.liguriadigitale.ponmetro.portale.presentation.util;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Created on 29-apr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author demartino
 *     <p>TODO To change the template for this generated type comment go to Window - Preferences -
 *     Java - Code Style - Code Templates
 */
public class CodiceFiscaleUtil {
  private char[] mesi = {'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};
  private char[] sostituti = {'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'};
  private char[] caratteriControllo = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
    'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
  };
  private HashMap<String, Integer> tabellaPari = null;
  private HashMap<String, Integer> tabellaDispari = null;

  /**
   * Costruttore che riempe due HashMap per la verifica del carattere di controllo del codice
   * fiscale.
   */
  @SuppressWarnings({})
  public CodiceFiscaleUtil() {
    tabellaPari = new HashMap<String, Integer>();
    tabellaDispari = new HashMap<String, Integer>();

    tabellaPari.put("0", new Integer(0));
    tabellaPari.put("1", new Integer(1));
    tabellaPari.put("2", new Integer(2));
    tabellaPari.put("3", new Integer(3));
    tabellaPari.put("4", new Integer(4));
    tabellaPari.put("5", new Integer(5));
    tabellaPari.put("6", new Integer(6));
    tabellaPari.put("7", new Integer(7));
    tabellaPari.put("8", new Integer(8));
    tabellaPari.put("9", new Integer(9));
    tabellaPari.put("A", new Integer(0));
    tabellaPari.put("B", new Integer(1));
    tabellaPari.put("C", new Integer(2));
    tabellaPari.put("D", new Integer(3));
    tabellaPari.put("E", new Integer(4));
    tabellaPari.put("F", new Integer(5));
    tabellaPari.put("G", new Integer(6));
    tabellaPari.put("H", new Integer(7));
    tabellaPari.put("I", new Integer(8));
    tabellaPari.put("J", new Integer(9));
    tabellaPari.put("K", new Integer(10));
    tabellaPari.put("L", new Integer(11));
    tabellaPari.put("M", new Integer(12));
    tabellaPari.put("N", new Integer(13));
    tabellaPari.put("O", new Integer(14));
    tabellaPari.put("P", new Integer(15));
    tabellaPari.put("Q", new Integer(16));
    tabellaPari.put("R", new Integer(17));
    tabellaPari.put("S", new Integer(18));
    tabellaPari.put("T", new Integer(19));
    tabellaPari.put("U", new Integer(20));
    tabellaPari.put("V", new Integer(21));
    tabellaPari.put("W", new Integer(22));
    tabellaPari.put("X", new Integer(23));
    tabellaPari.put("Y", new Integer(24));
    tabellaPari.put("Z", new Integer(25));

    tabellaDispari.put("0", new Integer(1));
    tabellaDispari.put("1", new Integer(0));
    tabellaDispari.put("2", new Integer(5));
    tabellaDispari.put("3", new Integer(7));
    tabellaDispari.put("4", new Integer(9));
    tabellaDispari.put("5", new Integer(13));
    tabellaDispari.put("6", new Integer(15));
    tabellaDispari.put("7", new Integer(17));
    tabellaDispari.put("8", new Integer(19));
    tabellaDispari.put("9", new Integer(21));
    tabellaDispari.put("A", new Integer(1));
    tabellaDispari.put("B", new Integer(0));
    tabellaDispari.put("C", new Integer(5));
    tabellaDispari.put("D", new Integer(7));
    tabellaDispari.put("E", new Integer(9));
    tabellaDispari.put("F", new Integer(13));
    tabellaDispari.put("G", new Integer(15));
    tabellaDispari.put("H", new Integer(17));
    tabellaDispari.put("I", new Integer(19));
    tabellaDispari.put("J", new Integer(21));
    tabellaDispari.put("K", new Integer(2));
    tabellaDispari.put("L", new Integer(4));
    tabellaDispari.put("M", new Integer(18));
    tabellaDispari.put("N", new Integer(20));
    tabellaDispari.put("O", new Integer(11));
    tabellaDispari.put("P", new Integer(3));
    tabellaDispari.put("Q", new Integer(6));
    tabellaDispari.put("R", new Integer(8));
    tabellaDispari.put("S", new Integer(12));
    tabellaDispari.put("T", new Integer(14));
    tabellaDispari.put("U", new Integer(16));
    tabellaDispari.put("V", new Integer(10));
    tabellaDispari.put("W", new Integer(22));
    tabellaDispari.put("X", new Integer(25));
    tabellaDispari.put("Y", new Integer(24));
    tabellaDispari.put("Z", new Integer(23));
  }

  /**
   * Genera il codice fiscale a partire dai dati anagrafici
   *
   * @param nome nome
   * @param cognome cognome
   * @param giornoNascita giorno di nascita
   * @param meseNascita mese di nascita
   * @param annoNascita anno di nascita
   * @param sesso sesso
   * @param codiceLuogoNascita codice Istat del comune di nascita
   * @return il codice fiscale generato a partire dai dati anagrafici
   * @throws Exception se c'� un errore nella generazione del codice fiscale
   */
  public String generaCodiceFiscale(
      String nome,
      String cognome,
      int giornoNascita,
      int meseNascita,
      int annoNascita,
      char sesso,
      String codiceLuogoNascita)
      throws Exception {
    ArrayList<Character> vocaliNome = new ArrayList<Character>();
    ArrayList<Character> vocaliCognome = new ArrayList<Character>();
    ArrayList<Character> consonantiNome = new ArrayList<Character>();
    ArrayList<Character> consonantiCognome = new ArrayList<Character>();

    meseNascita = meseNascita - 1;

    String codicegenerato = "";

    nome = removeCaratteriSpeciali(nome);
    cognome = removeCaratteriSpeciali(cognome);

    String tmp = "";
    for (int i = 0; i < nome.length(); i++) {
      char c = nome.charAt(i);
      if (!Character.isWhitespace(c) && c != '\'') {
        if (c == 'A') tmp = tmp + 'A';
        else if (c == 'E') tmp = tmp + 'E';
        else if (c == 'I') tmp = tmp + 'I';
        else if (c == 'O') tmp = tmp + 'O';
        else if (c == 'U') tmp = tmp + 'U';
        else tmp = tmp + c;
      }
    }
    nome = tmp.toUpperCase();
    tmp = "";
    for (int i = 0; i < cognome.length(); i++) {
      char c = cognome.charAt(i);
      if (!Character.isWhitespace(c) && c != '\'') {
        if (c == 'A') tmp = tmp + 'A';
        else if (c == 'E') tmp = tmp + 'E';
        else if (c == 'I') tmp = tmp + 'I';
        else if (c == 'O') tmp = tmp + 'O';
        else if (c == 'U') tmp = tmp + 'U';
        else tmp = tmp + c;
      }
    }
    cognome = tmp.toUpperCase();

    // SEPARO LE VOCALI DALLE CONSONANTI DEL NOME
    for (int i = 0; i < nome.length(); i++) {
      int c = nome.charAt(i);
      if (c >= 65 && c <= 90) {
        if (c == 65 || c == 69 || c == 73 || c == 79 || c == 85)
          vocaliNome.add(new Character(nome.charAt(i)));
        else consonantiNome.add(new Character(nome.charAt(i)));
      } else {
        throw new Exception("impossibile generare il codice fiscale");
      }
    }

    // SEPARO LE VOCALI DALLE CONSONANTI DEL COGNOME
    for (int i = 0; i < cognome.length(); i++) {
      int c = cognome.charAt(i);
      if (c >= 65 && c <= 90) {
        if (c == 65 || c == 69 || c == 73 || c == 79 || c == 85)
          vocaliCognome.add(new Character(cognome.charAt(i)));
        else consonantiCognome.add(new Character(cognome.charAt(i)));
      } else {
        throw new Exception("impossibile generare il codice fiscale");
      }
    }

    int max = 0;
    if (consonantiCognome.size() >= 3) max = 3;
    else max = consonantiCognome.size();
    for (int i = 0; i < max; i++)
      codicegenerato = codicegenerato + consonantiCognome.get(i).toString();

    if (max < 3 && vocaliCognome.size() >= (3 - max))
      for (int i = 0; i < 3 - max; i++)
        codicegenerato = codicegenerato + vocaliCognome.get(i).toString();
    else if (max < 3 && vocaliCognome.size() < (3 - max))
      for (int i = 0; i < 3 - max; i++)
        if (i < vocaliCognome.size())
          codicegenerato = codicegenerato + vocaliCognome.get(i).toString();
        else codicegenerato = codicegenerato + "X";

    max = 0;
    if (consonantiNome.size() > 3) max = 4;
    else if (consonantiNome.size() == 3) max = 3;
    else max = consonantiNome.size();

    for (int i = 0; i < max; i++) {
      if (max == 4 && i == 1)
        ;
      else codicegenerato = codicegenerato + consonantiNome.get(i).toString();
    }

    if (max < 3 && vocaliNome.size() >= (3 - max))
      for (int i = 0; i < 3 - max; i++)
        codicegenerato = codicegenerato + vocaliNome.get(i).toString();
    else if (max < 3 && vocaliNome.size() < (3 - max))
      for (int i = 0; i < 3 - max; i++)
        if (i < vocaliNome.size()) codicegenerato = codicegenerato + vocaliNome.get(i).toString();
        else codicegenerato = codicegenerato + "X";

    // Data sesso e luogo di nascita
    if (annoNascita <= 1899) annoNascita = annoNascita - 1800;
    else if (annoNascita >= 1900 && annoNascita <= 1999) annoNascita = annoNascita - 1900;
    else if (annoNascita >= 2000) annoNascita = annoNascita - 2000;

    if (annoNascita == 0) codicegenerato = codicegenerato + "00";
    else if (annoNascita < 10) codicegenerato = codicegenerato + "0" + String.valueOf(annoNascita);
    else codicegenerato = codicegenerato + String.valueOf(annoNascita);

    codicegenerato = codicegenerato + mesi[meseNascita];

    if ((sesso == 'M' || sesso == 'm') && giornoNascita <= 9)
      codicegenerato = codicegenerato + "0" + String.valueOf(giornoNascita);
    else if ((sesso == 'M' || sesso == 'm') && giornoNascita >= 10)
      codicegenerato = codicegenerato + String.valueOf(giornoNascita);
    else if ((sesso == 'F' || sesso == 'f'))
      codicegenerato = codicegenerato + String.valueOf(giornoNascita + 40);

    codicegenerato = codicegenerato + codiceLuogoNascita.toUpperCase();

    return codicegenerato + this.generaCarattereDiControllo(codicegenerato);
  }

  /**
   * Verifica se il codice fiscale inserito � congruente con i dati anagrafici di riferimento
   *
   * @param codiceInserito codice fiscale inserito
   * @param nome nome
   * @param cognome cognome
   * @param giornoNascita giorno di nascita
   * @param meseNascita mese di nascita
   * @param annoNascita anno di nascita
   * @param sesso sesso
   * @param codiceLuogoNascita codice Istat del comune di nascita
   * @return true se il codice fiscale inserito � congruente con i dati anagrafici di riferimento,
   *     altrimenti false
   * @throws Exception se si verifica un errore nella generazione del codice fiscale di confronto a
   *     partire dai dati angrafici
   */
  public boolean controlloCodiceFiscale(
      String codiceInserito,
      String nome,
      String cognome,
      int giornoNascita,
      int meseNascita,
      int annoNascita,
      char sesso,
      String codiceLuogoNascita)
      throws Exception {
    String codiceGenerato =
        generaCodiceFiscale(
            nome, cognome, giornoNascita, meseNascita, annoNascita, sesso, codiceLuogoNascita);
    return controlloCodiceFiscale(codiceGenerato, codiceInserito);
  }

  /**
   * Verifica la congruenza di due codici fiscali
   *
   * @param codiceGenerato codice fiscale di riferimento
   * @param codiceInserito codice fiscale da controllare
   * @return true se i due codici ficali sono coerenti, altrimenti false
   * @throws Exception
   */
  public boolean controlloCodiceFiscale(String codiceGenerato, String codiceInserito)
      throws Exception {
    codiceGenerato = codiceGenerato.trim().toUpperCase();
    codiceInserito = codiceInserito.trim().toUpperCase();
    for (int i = 0; i < 15; i++) {
      if (codiceInserito.charAt(i) != codiceGenerato.charAt(i)) {
        if (i == 6 || i == 7 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14) {
          int c = Character.getNumericValue(codiceGenerato.charAt(i));
          if (codiceInserito.charAt(i) != sostituti[c]) return false;
        } else return false;
      }
    }

    return verificaCarattereDiControllo(codiceInserito);
  }

  /**
   * Genera il carattere di controllo a partire dai primi 15 caratteri del codice fiscale
   *
   * @param codiceFiscale
   * @return
   */
  public char generaCarattereDiControllo(String codiceFiscale) {
    int calcolo = 0;

    for (int i = 0; i < 15; i = i + 2) {
      String key = new Character(codiceFiscale.charAt(i)).toString();
      calcolo = calcolo + tabellaDispari.get(key).intValue();
    }
    for (int i = 1; i < 15; i = i + 2) {
      String key = new Character(codiceFiscale.charAt(i)).toString();
      calcolo = calcolo + tabellaPari.get(key).intValue();
    }

    int controllo = (calcolo % 26);

    return caratteriControllo[controllo];
  }

  /**
   * Verifica se il carattere di controllo del codice fiscale � corretto
   *
   * @param codiceInserito codice fiscale di cui calcolare il carattere di controllo
   * @return true se il carattere di controllo � corretto, altrimenti false
   */
  public boolean verificaCarattereDiControllo(String codiceInserito) {
    if (codiceInserito.trim().length() != 16) return false;

    char controllo = this.generaCarattereDiControllo(codiceInserito);
    return (controllo == codiceInserito.charAt(15));
  }

  public static String getBelfioreBase(String codFiscale) {
    String comuneNascitaDaCod = codFiscale.substring(11, 15);
    // Sostituisco le eventuali lettere con il numero corrispondente
    char[] numLett = {'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'};
    for (int g = 1; g < comuneNascitaDaCod.length(); g++) {
      int count = -1;
      char carattere = comuneNascitaDaCod.charAt(g);
      for (int f = 0; f < 10; f++) {
        if (carattere == String.valueOf(f).toCharArray()[0]) {
          count = 1;
          break;
        }
      }
      if (count == -1) {
        for (int y = 0; y < numLett.length; y++) {
          if (numLett[y] == carattere) {
            if (g == 1)
              comuneNascitaDaCod =
                  comuneNascitaDaCod.substring(0, g)
                      + String.valueOf(y)
                      + comuneNascitaDaCod.substring(g + 1);
            else if (g == 2)
              comuneNascitaDaCod =
                  comuneNascitaDaCod.substring(0, g)
                      + String.valueOf(y)
                      + String.valueOf(comuneNascitaDaCod.charAt(g + 1));
            else comuneNascitaDaCod = comuneNascitaDaCod.substring(0, g) + String.valueOf(y);
          }
        }
      }
    }
    return comuneNascitaDaCod;
  }

  private static String removeCaratteriSpeciali(String value) {
    return value
        .trim()
        .toLowerCase()
        .replace("ò", "o")
        .replace("à", "a")
        .replace("è", "e")
        .replace("é", "e")
        .replace("ù", "u")
        .replace("ì", "i")
        .replace("'", "")
        .replace("-", " ");
  }
}
