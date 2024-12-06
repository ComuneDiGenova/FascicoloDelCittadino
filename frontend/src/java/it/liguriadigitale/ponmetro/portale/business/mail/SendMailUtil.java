package it.liguriadigitale.ponmetro.portale.business.mail;

import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggio;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.util.string.Strings;

public class SendMailUtil {
  public static final String TEXT_HTML = "text/html; charset=UTF-8";
  public static final String TEXT_PLAIN = "text/plain";

  protected static Log log = LogFactory.getLog(SendMailUtil.class);

  public static SendMailUtil getInstance() {
    return new SendMailUtil();
  }

  public void sendHtmlMail(ContenutoMessaggio contenutoMessaggio) throws MessagingException {
    sendMail(contenutoMessaggio, TEXT_HTML);
  }

  public void sendTextMail(ContenutoMessaggio contenutoMessaggio) throws MessagingException {
    sendMail(contenutoMessaggio, TEXT_PLAIN);
  }

  protected void sendMail(ContenutoMessaggio contenutoMessaggio, String mimeType)
      throws MessagingException {
    Session mailSession = Session.getInstance(contenutoMessaggio.getMailSessionProperties());
    String[] cc = contenutoMessaggio.getCc();
    String[] bcc = contenutoMessaggio.getBcc();
    String[] to = contenutoMessaggio.getTo();
    InternetAddress[] toAddresses = new InternetAddress[to.length];
    InternetAddress[] ccAddresses = new InternetAddress[cc == null ? 0 : cc.length];
    InternetAddress[] bccAddresses = new InternetAddress[bcc == null ? 0 : bcc.length];
    for (int i = 0; i < to.length; i++) {
      toAddresses[i] = new InternetAddress(to[i]);
    }
    if (cc != null) {
      for (int i = 0; i < cc.length; i++) {
        ccAddresses[i] = new InternetAddress(cc[i]);
      }
    }
    if (bcc != null) {
      for (int i = 0; i < bcc.length; i++) {
        bccAddresses[i] = new InternetAddress(bcc[i]);
      }
    }
    Message message = new MimeMessage(mailSession);
    message.setFrom(new InternetAddress(contenutoMessaggio.getFrom()));
    message.setRecipients(Message.RecipientType.TO, toAddresses);
    message.setRecipients(Message.RecipientType.CC, ccAddresses);
    message.setRecipients(Message.RecipientType.BCC, bccAddresses);
    message.setSubject(contenutoMessaggio.getSubject());
    String text = contenutoMessaggio.getText();
    if (mimeType.equalsIgnoreCase(TEXT_HTML)) message.setContent(text, mimeType);
    else message.setText(text);
    Transport.send(message);
  }

  public static String getEscapedHTML(String text) {
    if (text != null) {
      String htmlText = Strings.escapeMarkup(text).toString();
      log.trace("getEscapedHTML=" + htmlText);
      return htmlText;
    } else {
      return "";
    }
  }
}
