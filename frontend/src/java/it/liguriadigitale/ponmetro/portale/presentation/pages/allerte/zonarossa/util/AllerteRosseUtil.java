package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.util;

import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util.AllerteCortesiaUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.behavior.AttributeAppender;

@SuppressWarnings("unused")
public class AllerteRosseUtil {

  private static Log log = LogFactory.getLog(AllerteCortesiaUtil.class);

  private static final String ICON_TELEFONO = "color-fc-pink col-3 icon-telefono";
  private static final String ICON_CELLULARE = "color-fc-pink col-3 icon-celulare";

  private static final String ICON_UOMO = "color-fc-pink col-3 icon-uomo";
  private static final String ICON_DONNA = "color-fc-pink col-3 icon-donna";
  private static final String ICON_BIMBA = "color-fc-pink col-3 icon-bimba";
  private static final String ICON_BIMBO = "color-fc-pink col-3 icon-bimbo";
  private static final String ICON_OMINO = "color-fc-pink col-3 icon-omino";

  public static AttributeAppender getCssIconaTelefono(String tipo) {
    String css = "";

    if (tipo.equalsIgnoreCase("FISSO")) {
      css = ICON_TELEFONO;
    } else {
      css = ICON_CELLULARE;
    }

    return new AttributeAppender("class", " " + css);
  }

  public static AttributeAppender getCssIconaComponenteNucleo(String codiceFiscale) {
    String css = "";

    //		log.debug("CP getCssIconaComponenteNucleo = " + codiceFiscale);
    //
    //		if (PageUtil.isStringValid(codiceFiscale)) {
    //
    //			String genere = CodiceFiscaleValidatorUtil.getSessoFromCf(codiceFiscale);
    //			LocalDate dataNascita = CodiceFiscaleValidatorUtil.getDataNascitaFromCf(codiceFiscale);
    //
    //			log.debug("CP 	dataNascita =" + dataNascita);
    //
    //			if (PageUtil.isStringValid(genere)) {
    //				if (genere.equalsIgnoreCase("F")) {
    //					if (LocalDateUtil.isMaggiorenne(dataNascita)) {
    //						css = ICON_DONNA;
    //					} else {
    //						css = ICON_BIMBA;
    //					}
    //				} else if (genere.equalsIgnoreCase("M")) {
    //					if (LocalDateUtil.isMaggiorenne(dataNascita)) {
    //						css = ICON_UOMO;
    //					} else {
    //						css = ICON_BIMBO;
    //					}
    //				}
    //			} else {
    //				css = ICON_OMINO;
    //			}
    //		} else {
    //			css = ICON_OMINO;
    //		}

    css = ICON_OMINO;

    return new AttributeAppender("class", " " + css);
  }
}
