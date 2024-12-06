package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.liservizi;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.FRUIBILITA;
import it.liguriadigitale.ponmetro.allertecortesia.model.SERVIZIO;
import it.liguriadigitale.ponmetro.allertecortesia.model.VerificaServiziResponse;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.EnumCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.ImportoEuroLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.SiNoLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.DettagliServizioAllerteCortesiaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.util.AllerteCortesiaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class ServizioCortesiaPanel extends BasePanel {

  private static final long serialVersionUID = -3239271662505732267L;

  private int index;

  private String email;

  private FdcAjaxButton btnRegistraServizio;
  private FdcAjaxButton btnCancellaServizio;

  private String idServizio;

  String idCollapsible;

  private SERVIZIO servizio;

  private String mailVerificata;
  private String telefonoCellulareVerificato;

  static final String URL_TELEGRAM = "https://t.me/ComGeGenovaAlert/";

  public ServizioCortesiaPanel(
      String id,
      SERVIZIO servizio,
      int index,
      String email,
      String idServizio,
      String telefonoCellulareVerificato,
      String mailVerificata) {
    super(id);
    this.index = index;
    this.email = email;
    this.setIdServizio(idServizio);
    this.setServizio(servizio);
    this.mailVerificata = mailVerificata;
    this.telefonoCellulareVerificato = telefonoCellulareVerificato;

    fillDati(servizio);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {

    SERVIZIO servizio = (SERVIZIO) dati;

    idCollapsible = "coll" + getMarkupId();

    AjaxButton btnServizio =
        new AjaxButton("btnServizio") {

          private static final long serialVersionUID = -78112760158967281L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            super.onSubmit(target);
          }
        };

    WebMarkupContainer wmkBtnServizio = new WebMarkupContainer("wmkBtnServizio");
    wmkBtnServizio.setMarkupId("coll" + idCollapsible);
    wmkBtnServizio.addOrReplace(btnServizio);

    add(wmkBtnServizio);

    btnServizio.setOutputMarkupId(true);
    btnServizio.setOutputMarkupPlaceholderTag(true);
    btnServizio.add(AttributeModifier.replace("data-bs-target", "#" + idCollapsible));
    btnServizio.add(AttributeModifier.replace("aria-controls", idCollapsible));

    WebMarkupContainer wmkServizio = new WebMarkupContainer("wmkServizio");

    wmkServizio.add(AttributeModifier.append("aria-labelledby", "coll" + idCollapsible));

    if (getSession().getAttribute("idCollapsible") != null
        && ((String) getSession().getAttribute("idCollapsible")).equalsIgnoreCase(index + "")) {
      wmkServizio.add(AttributeModifier.append("class", "show"));
    }

    wmkServizio.setOutputMarkupId(true);
    wmkServizio.setOutputMarkupPlaceholderTag(true);

    wmkServizio.setMarkupId(idCollapsible);
    addOrReplace(wmkServizio);

    NotEmptyLabel descrizioneBreve =
        new NotEmptyLabel("descrizioneBreve", servizio.getDESCRIZIONEBREVE());
    btnServizio.addOrReplace(descrizioneBreve);

    wmkServizio.addOrReplace(creaBtnDettagli(servizio.getID()));

    wmkServizio.addOrReplace(
        new AmtCardLabel<>("id", servizio.getID(), ServizioCortesiaPanel.this).setVisible(false));

    wmkServizio.addOrReplace(
        new AmtCardLabel(
            "descrizioneEstesa", servizio.getDESCRIZIONEESTESA(), ServizioCortesiaPanel.this) {

          private static final long serialVersionUID = -4685028141371755647L;

          @Override
          protected void creaLabelValore(Object value) {
            if (value instanceof String) {
              NotEmptyLabel nELbl = new NotEmptyLabel(WICKET_ID_LABEL, (String) value);
              nELbl.setEscapeModelStrings(false);
              addOrReplace(nELbl);
            } else if (value instanceof LocalDate) {
              addOrReplace(new NotEmptyLocalDateLabel(WICKET_ID_LABEL, (LocalDate) value));
            } else if (value instanceof Double) {
              addOrReplace(new ImportoEuroLabel(WICKET_ID_LABEL, (Double) value));
            } else if (value instanceof Long) {
              addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, (Long) value));
            } else if (value instanceof Integer) {
              addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, value.toString()));
            } else if (value instanceof Boolean) {
              addOrReplace(new SiNoLabel(WICKET_ID_LABEL, (Boolean) value));
            } else if (value instanceof BigDecimal) {
              addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, value.toString()));
            } else if (value instanceof Float) {
              addOrReplace(new ImportoEuroLabel(WICKET_ID_LABEL, (Float) value));
            } else {
              addOrReplace(new NotEmptyLabel(WICKET_ID_LABEL, ""));
            }
          }
        });

    List<FRUIBILITA> listaFruibilita = new ArrayList<FRUIBILITA>();
    if (LabelFdCUtil.checkIfNotNull(servizio)
        && LabelFdCUtil.checkIfNotNull(servizio.getFRUIBILITA())) {
      listaFruibilita = servizio.getFRUIBILITA();
    }
    ListView<FRUIBILITA> boxListaFruibilita =
        new ListView<FRUIBILITA>("boxListaFruibilita", listaFruibilita) {

          private static final long serialVersionUID = -8098168217832668847L;

          @Override
          protected void populateItem(ListItem<FRUIBILITA> itemFruibilita) {
            final FRUIBILITA fruibilita = itemFruibilita.getModelObject();

            WebMarkupContainer wmkBoxListaFruibilita =
                new WebMarkupContainer("wmkBoxListaFruibilita");
            wmkBoxListaFruibilita.setOutputMarkupId(true);
            wmkBoxListaFruibilita.setOutputMarkupPlaceholderTag(true);

            itemFruibilita.add(wmkBoxListaFruibilita);

            NotEmptyLabel descrizioneCanale =
                new NotEmptyLabel("descrizioneCanale", fruibilita.getDESCRIZIONECANALE());

            // descrizioneCanale.setVisible(false);
            wmkBoxListaFruibilita.addOrReplace(descrizioneCanale);

            AmtCardLabel idRelazioneServizioCanale =
                new AmtCardLabel(
                    "idRelazioneServizioCanale",
                    fruibilita.getDESCRIZIONECANALE(),
                    ServizioCortesiaPanel.this);
            idRelazioneServizioCanale.setVisible(false);
            wmkBoxListaFruibilita.addOrReplace(idRelazioneServizioCanale);

            AmtCardLabel idCanale =
                new AmtCardLabel<>(
                    "idCanale", fruibilita.getIDCANALE(), ServizioCortesiaPanel.this);
            idCanale.setVisible(false);
            wmkBoxListaFruibilita.addOrReplace(idCanale);

            AmtCardLabel verificaCanale =
                new AmtCardLabel<>(
                    "verificaCanale",
                    AllerteCortesiaUtil.decodificaBooleanoComeString(
                        fruibilita.getVERIFICACELLULARE()),
                    ServizioCortesiaPanel.this);

            // verificaCanale.setVisible(!fruibilita.getIDCANALE().equalsIgnoreCase("4"));
            verificaCanale.setVisible(false);

            wmkBoxListaFruibilita.addOrReplace(verificaCanale);

            wmkBoxListaFruibilita.addOrReplace(
                new AmtCardLabel<>(
                        "utenteIscritto",
                        AllerteCortesiaUtil.decodificaBooleanoComeString(
                            fruibilita.getUTENTEISCRITTO()),
                        ServizioCortesiaPanel.this)
                    .setVisible(!fruibilita.getIDCANALE().equalsIgnoreCase("5")));

            wmkBoxListaFruibilita.addOrReplace(
                creaBtnRegistraServizio(
                    fruibilita.getIDRELAZIONESERVIZIOCANALE(),
                    email,
                    fruibilita.getUTENTEISCRITTO(),
                    wmkBoxListaFruibilita,
                    fruibilita.getIDCANALE()));

            wmkBoxListaFruibilita.addOrReplace(
                creaBtnCancellaServizio(
                    fruibilita.getIDRELAZIONESERVIZIOCANALE(),
                    email,
                    fruibilita.getUTENTEISCRITTO(),
                    wmkBoxListaFruibilita,
                    fruibilita.getIDCANALE()));

            ExternalLink linkTelegram = new ExternalLink("linkTelegram", URL_TELEGRAM);
            linkTelegram.setVisible(fruibilita.getDESCRIZIONECANALE().equalsIgnoreCase("TELEGRAM"));
            wmkBoxListaFruibilita.addOrReplace(linkTelegram);

            wmkBoxListaFruibilita.addOrReplace(linkTelegram);

            WebMarkupContainer infoEmail = new WebMarkupContainer("infoEmail");
            infoEmail.setVisible(
                LabelFdCUtil.checkIfNotNull(fruibilita)
                    && LabelFdCUtil.checkIfNotNull(fruibilita.getIDCANALE())
                    && fruibilita.getIDCANALE().equalsIgnoreCase("2")
                    && PageUtil.isStringValid(mailVerificata)
                    && mailVerificata.equalsIgnoreCase("N"));
            wmkBoxListaFruibilita.addOrReplace(infoEmail);

            WebMarkupContainer infoCel = new WebMarkupContainer("infoCel");
            infoCel.setVisible(
                LabelFdCUtil.checkIfNotNull(fruibilita)
                    && LabelFdCUtil.checkIfNotNull(fruibilita.getIDCANALE())
                    && fruibilita.getIDCANALE().equalsIgnoreCase("1")
                    && PageUtil.isStringValid(telefonoCellulareVerificato)
                    && telefonoCellulareVerificato.equalsIgnoreCase("N"));
            wmkBoxListaFruibilita.addOrReplace(infoCel);
          }
        };
    boxListaFruibilita.setOutputMarkupId(true);
    boxListaFruibilita.setVisible(
        LabelFdCUtil.checkIfNotNull(listaFruibilita)
            && !LabelFdCUtil.checkEmptyList(listaFruibilita));
    wmkServizio.addOrReplace(boxListaFruibilita);
  }

  private FdcAjaxButton creaBtnRegistraServizio(
      String idRelazioneServizioCanale,
      String email,
      String utenteIscritto,
      WebMarkupContainer wmkBoxListaFruibilita,
      String idCanale) {

    btnRegistraServizio =
        new FdcAjaxButton("btnRegistraServizio") {

          private static final long serialVersionUID = -8708507161124403516L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "CP clic onSubmit btnRegistraServizio = "
                    + idRelazioneServizioCanale
                    + " - "
                    + email
                    + " - "
                    + utenteIscritto);
            getSession().setAttribute("idCollapsible", index + "");
            try {
              if (PageUtil.isStringValid(utenteIscritto)) {

                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putWsIscrizioneAdUnServizio(email, idRelazioneServizioCanale);

                success("Registrazione effettuata con successo");

                btnCancellaServizio.setVisible(true);
                btnRegistraServizio.setVisible(false);
              }
            } catch (ApiException e) {
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {
              log.error("BusinessException gestito durante la chiamata delle API:", e);

              error("Errore durante aggiornamento del servizio.");
            }

            target.add(getPage());
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(ServizioCortesiaPanel.this);

            log.error("CP errore registrazione a un servizio allerta cortesia");
          }
        };

    btnRegistraServizio.setMarkupId(index + idRelazioneServizioCanale + "registra");
    btnRegistraServizio.setOutputMarkupId(true);
    btnRegistraServizio.setOutputMarkupPlaceholderTag(true);

    btnRegistraServizio.setLabel(Model.of(getString("ServizioCortesiaPanel.registrati")));

    if (idCanale.equalsIgnoreCase("1")) {
      btnRegistraServizio.setVisible(
          PageUtil.isStringValid(telefonoCellulareVerificato)
              && telefonoCellulareVerificato.equalsIgnoreCase("S")
              && PageUtil.isStringValid(utenteIscritto)
              && utenteIscritto.equalsIgnoreCase("N"));
    } else if (idCanale.equalsIgnoreCase("2")) {
      btnRegistraServizio.setVisible(
          PageUtil.isStringValid(mailVerificata)
              && mailVerificata.equalsIgnoreCase("S")
              && PageUtil.isStringValid(utenteIscritto)
              && utenteIscritto.equalsIgnoreCase("N"));
    } else if (idCanale.equalsIgnoreCase("5")) {
      btnRegistraServizio.setVisible(false);
    } else {
      btnRegistraServizio.setVisible(
          PageUtil.isStringValid(utenteIscritto) && utenteIscritto.equalsIgnoreCase("N"));
    }

    return btnRegistraServizio;
  }

  private FdcAjaxButton creaBtnCancellaServizio(
      String idRelazioneServizioCanale,
      String email,
      String utenteIscritto,
      WebMarkupContainer wmkBoxListaFruibilita,
      String idCanale) {
    btnCancellaServizio =
        new FdcAjaxButton("btnCancellaServizio") {

          private static final long serialVersionUID = 1929353892132392160L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {

            log.debug(
                "CP clic onSubmit btnCancellaServizio = "
                    + idRelazioneServizioCanale
                    + " - "
                    + email
                    + " - "
                    + utenteIscritto);
            getSession().setAttribute("idCollapsible", index + "");
            try {
              if (PageUtil.isStringValid(utenteIscritto)) {
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putWsCancellazioneAdUnServizio(email, idRelazioneServizioCanale);

                btnCancellaServizio.setVisible(false);
                btnRegistraServizio.setVisible(true);

                success("Cancellazione effettuata con successo");
              }
            } catch (ApiException e) {
              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.error("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              error(messaggioDaVisualizzare);

            } catch (IOException | BusinessException e) {

              error("Errore durante aggiornamento del servizio.");
              log.error("BusinessException gestito durante la chiamata delle API:", e);
            }

            target.add(getPage());
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(ServizioCortesiaPanel.this);

            log.error("CP errore cancella servizio allerta cortesia");
          }
        };

    btnCancellaServizio.setMarkupId(index + idRelazioneServizioCanale + "cancella");
    btnCancellaServizio.setOutputMarkupId(true);
    btnCancellaServizio.setOutputMarkupPlaceholderTag(true);

    btnCancellaServizio.setLabel(Model.of(getString("ServizioCortesiaPanel.cancellati")));

    if (idCanale.equalsIgnoreCase("1")) {
      btnCancellaServizio.setVisible(
          PageUtil.isStringValid(telefonoCellulareVerificato)
              && telefonoCellulareVerificato.equalsIgnoreCase("S")
              && PageUtil.isStringValid(utenteIscritto)
              && utenteIscritto.equalsIgnoreCase("S"));
    } else if (idCanale.equalsIgnoreCase("2")) {
      btnCancellaServizio.setVisible(
          PageUtil.isStringValid(mailVerificata)
              && mailVerificata.equalsIgnoreCase("S")
              && PageUtil.isStringValid(utenteIscritto)
              && utenteIscritto.equalsIgnoreCase("S"));
    } else if (idCanale.equalsIgnoreCase("5")) {
      btnCancellaServizio.setVisible(false);
    } else {
      btnCancellaServizio.setVisible(
          PageUtil.isStringValid(utenteIscritto) && utenteIscritto.equalsIgnoreCase("S"));
    }

    return btnCancellaServizio;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnDettagli(String idServizio) {
    FdCButtonBootstrapAjaxLink<Object> btnDettagliServizio =
        new FdCButtonBootstrapAjaxLink<Object>("btnDettagliServizio", Type.Primary) {

          private static final long serialVersionUID = 4877323891634069658L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            target.add(ServizioCortesiaPanel.this);

            VerificaServiziResponse verificaServizi =
                AllerteCortesiaUtil.popolaDettagliServizio(idServizio, email);

            setResponsePage(new DettagliServizioAllerteCortesiaPage(verificaServizi, email));
          }
        };

    btnDettagliServizio.setVisible(
        EnumCortesia.getCortesiaDaId(Integer.parseInt(idServizio))
                .equals(EnumCortesia.ALLERTA_INTERRUZIONE_ACQUA)
            || EnumCortesia.getCortesiaDaId(Integer.parseInt(idServizio))
                .equals(EnumCortesia.ALLERTA_SOSTA));

    btnDettagliServizio.setLabel(Model.of(getString("ServizioCortesiaPanel.dettagli")));

    return btnDettagliServizio;
  }

  public String getIdServizio() {
    return idServizio;
  }

  public void setIdServizio(String idServizio) {
    this.idServizio = idServizio;
  }

  public SERVIZIO getServizio() {
    return servizio;
  }

  public void setServizio(SERVIZIO servizio) {
    this.servizio = servizio;
  }
}
