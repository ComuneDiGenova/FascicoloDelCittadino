package it.liguriadigitale.ponmetro.portale.presentation.util;

public class EuroUtil {

  public static String covertiImporto(double importo) {
    // metodo wrapper
    int n = (int) Math.round(importo * 100);
    int parteIntera = n / 100;
    String parteDecimale = String.format("%02d", n % 100);

    if (parteIntera == 0) return "zero/" + parteDecimale;
    else return convertiParteIntera(parteIntera) + "/" + parteDecimale;
  }

  private static String convertiParteIntera(int n) {
    if (n < 0) return "meno " + convertiParteIntera(-n);
    else if (n == 0) return "";
    else if (n <= 19)
      return new String[] {
            "uno",
            "due",
            "tre",
            "quattro",
            "cinque",
            "sei",
            "sette",
            "otto",
            "nove",
            "dieci",
            "undici",
            "dodici",
            "tredici",
            "quattordici",
            "quindici",
            "sedici",
            "diciassette",
            "diciotto",
            "diciannove"
          }
          [n - 1];
    else if (n <= 99) {
      String[] vettore = {
        "venti", "trenta", "quaranta", "cinquanta", "sessanta", "settanta", "ottanta", "novanta"
      };
      String letter = vettore[(n / 10) - 2];
      int t = n % 10; // t e la prima cifra di n
      // se 1 o 8 va tolta la vocale finale di letter
      if ((t == 1) || (t == 8)) letter = letter.substring(0, letter.length() - 1);
      return letter + convertiParteIntera(n % 10);
    } else if (n <= 199) return "cento" + convertiParteIntera(n % 100);
    else if (n <= 999) {
      int m = n % 100;
      m /= 10; // divisione intera per 10 della variabile
      String letter = "cent";
      if (m != 8) letter = letter + "o";
      return convertiParteIntera(n / 100) + letter + convertiParteIntera(n % 100);
    } else if (n <= 1999) return "mille" + convertiParteIntera(n % 1000);
    else if (n <= 999999)
      return convertiParteIntera(n / 1000) + "mila" + convertiParteIntera(n % 1000);
    else if (n <= 1999999) return "unmilione" + convertiParteIntera(n % 1000000);
    else if (n <= 999999999)
      return convertiParteIntera(n / 1000000) + "milioni" + convertiParteIntera(n % 1000000);
    else if (n <= 1999999999) return "unmiliardo" + convertiParteIntera(n % 1000000000);
    else
      return convertiParteIntera(n / 1000000000) + "miliardi" + convertiParteIntera(n % 1000000000);
  }
}
