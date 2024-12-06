package it.liguriadigitale.ponmetro.portale.presentation.pages.configurazione.avatar.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UtilConfigurazioneAvatar {

  private static Log log = LogFactory.getLog(UtilConfigurazioneAvatar.class);

  private static MagicMatch checkByteArray(final byte[] byteArray) throws BusinessException {
    try {
      return Magic.getMagicMatch(byteArray, false);
    } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
      log.error("UtilConfigurazioneAvatar -- checkByteArray: " + e.getMessage());
      throw new BusinessException(e);
    }
  }

  public static String getMimeTypeFileAvatar(byte[] byteArray) throws BusinessException {
    MagicMatch magicMatch = checkByteArray(byteArray);
    String mimeType = "";
    if (magicMatch != null) {
      mimeType = magicMatch.getMimeType();
    }
    return mimeType;
  }
}
