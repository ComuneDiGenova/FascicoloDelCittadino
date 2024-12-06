package it.liguriadigitale.ponmetro.portale.pojo.mail;

import java.io.File;
import java.io.Serializable;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ContenutoMessaggioBuilder implements Serializable {

  private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
  private static final String MAIL_SMTP_PORT = "mail.smtp.port";
  private static final long serialVersionUID = -1976846285510427639L;
  protected static Log log = LogFactory.getLog(ContenutoMessaggioBuilder.class);

  private String from;
  private String[] to;
  private String[] cc;
  private String[] bcc;
  private String subject;
  private String text;
  private File[] attachments;
  private String smtpServer;
  private String port;

  private static final String MAIL_SMTP_HOST = "mail.smtp.host";

  public ContenutoMessaggioBuilder getInstance() {
    return new ContenutoMessaggioBuilder();
  }

  public ContenutoMessaggioBuilder setFrom(String from) {
    this.from = from;
    return this;
  }

  public ContenutoMessaggioBuilder setTo(String to) {
    String[] toArray = convertStringToArrayString(to);
    this.to = toArray;
    return this;
  }

  public ContenutoMessaggioBuilder setCc(String cc) {
    String[] ccArray = convertStringToArrayString(cc);
    this.cc = ccArray;
    return this;
  }

  public ContenutoMessaggioBuilder setBcc(String bcc) {
    String[] bccArray = convertStringToArrayString(bcc);
    this.bcc = bccArray;
    return this;
  }

  public ContenutoMessaggioBuilder setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public ContenutoMessaggioBuilder setText(String text) {
    this.text = text;
    return this;
  }

  public ContenutoMessaggioBuilder setAttachments(File[] attachments) {
    this.attachments = attachments;
    return this;
  }

  public ContenutoMessaggioBuilder setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
    return this;
  }

  public ContenutoMessaggioBuilder setPort(String port) {
    this.port = port;
    return this;
  }

  public ContenutoMessaggio build() {
    ContenutoMessaggio contenutoMessaggio = new ContenutoMessaggio();
    contenutoMessaggio.setAttachments(attachments);
    contenutoMessaggio.setBcc(bcc);
    contenutoMessaggio.setCc(cc);
    contenutoMessaggio.setFrom(from);
    contenutoMessaggio.setSubject(subject);
    contenutoMessaggio.setText(text);
    contenutoMessaggio.setTo(to);
    if (StringUtils.isNotEmpty(smtpServer)) {
      Properties mailSessionProperties = new Properties();
      mailSessionProperties.setProperty(MAIL_SMTP_HOST, smtpServer);
      mailSessionProperties.put(MAIL_SMTP_PORT, StringUtils.isNotBlank(port) ? port : "25");
      mailSessionProperties.put(MAIL_SMTP_AUTH, "false");
      contenutoMessaggio.setMailSessionProperties(mailSessionProperties);
      contenutoMessaggio.setSmtpServer(smtpServer);
    }
    log.debug("contenutoMessaggio:" + contenutoMessaggio);
    return contenutoMessaggio;
  }

  private String[] convertStringToArrayString(String to) {
    String[] array = null;
    if (StringUtils.isNotBlank(to)) {
      String temp = to + ";";
      array = temp.split(";");
    } else {
      array = new String[] {};
    }
    return array;
  }
}
