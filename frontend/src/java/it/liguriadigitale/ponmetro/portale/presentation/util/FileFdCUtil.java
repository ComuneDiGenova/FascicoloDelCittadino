package it.liguriadigitale.ponmetro.portale.presentation.util;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileFdCUtil {

  private static Log log = LogFactory.getLog(FileFdCUtil.class);

  public static String getSizeFile(long size) {
    long n = 1000;
    String s = "";
    double kb = size / n;
    double mb = kb / n;
    double gb = mb / n;
    double tb = gb / n;
    if (size < n) {
      s = size + " Bytes";
    } else if (size >= n && size < (n * n)) {
      s = String.format("%.2f", kb) + " KB";
    } else if (size >= (n * n) && size < (n * n * n)) {
      s = String.format("%.2f", mb) + " MB";
    } else if (size >= (n * n * n) && size < (n * n * n * n)) {
      s = String.format("%.2f", gb) + " GB";
    } else if (size >= (n * n * n * n)) {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }

  private static MagicMatch checkByteArray(final byte[] byteArray)
      throws BusinessException, MagicMatchNotFoundException {
    try {
      return Magic.getMagicMatch(byteArray, false);
    } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
      log.error("FileFdCUtil -- checkByteArray: " + e.getMessage());
      // throw new BusinessException(e);
      throw new MagicMatchNotFoundException(e);
    }
  }

  public static String getMimeTypeFileUploadAllegato(byte[] byteArray)
      throws BusinessException, MagicMatchNotFoundException {
    MagicMatch magicMatch = checkByteArray(byteArray);
    String mimeType = "";
    if (magicMatch != null) {
      mimeType = magicMatch.getMimeType();
    }
    log.debug("CP mimetype = " + mimeType);
    return mimeType;
  }

  public static String getEstensionFileUploadAllegato(byte[] byteArray)
      throws BusinessException, MagicMatchNotFoundException {
    MagicMatch magicMatch = checkByteArray(byteArray);
    String estensione = "";
    if (magicMatch != null) {
      estensione = magicMatch.getExtension();
    }
    log.debug("CP estensione = " + estensione);
    return estensione;
  }
}
