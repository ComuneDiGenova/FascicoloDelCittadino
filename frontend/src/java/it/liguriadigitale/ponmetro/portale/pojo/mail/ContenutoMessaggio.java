package it.liguriadigitale.ponmetro.portale.pojo.mail;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Properties;

public class ContenutoMessaggio implements Serializable {

  private static final long serialVersionUID = -4024780306109170159L;

  private String from;
  private String[] to;
  private String[] cc;
  private String[] bcc;
  private String subject;
  private String text;
  private File[] attachments;
  private String port;

  private String smtpServer;
  private Properties mailSessionProperties;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String[] getTo() {
    return to;
  }

  public void setTo(String[] to) {
    this.to = to;
  }

  public String[] getCc() {
    return cc;
  }

  public void setCc(String[] cc) {
    this.cc = cc;
  }

  public String[] getBcc() {
    return bcc;
  }

  public void setBcc(String[] bcc) {
    this.bcc = bcc;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public File[] getAttachments() {
    return attachments;
  }

  public void setAttachments(File[] attachments) {
    this.attachments = attachments;
  }

  public String getSmtpServer() {
    return smtpServer;
  }

  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }

  public Properties getMailSessionProperties() {
    return mailSessionProperties;
  }

  public void setMailSessionProperties(Properties mailSessionProperties) {
    this.mailSessionProperties = mailSessionProperties;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return "ContenutoMessaggio [from="
        + from
        + ", to="
        + Arrays.toString(to)
        + ", cc="
        + Arrays.toString(cc)
        + ", bcc="
        + Arrays.toString(bcc)
        + ", subject="
        + subject
        + ", text="
        + text
        + ", attachments="
        + Arrays.toString(attachments)
        + ", port="
        + port
        + ", smtpServer="
        + smtpServer
        + ", mailSessionProperties="
        + mailSessionProperties
        + "]";
  }
}
