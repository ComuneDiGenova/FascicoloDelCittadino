package it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.accenture.segnalazioniczrm.util.SegnalazioniCzrmUtil;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.HelpCzRMDettagliDomandePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo.CzrmMailCommenti;
import it.liguriadigitale.ponmetro.portale.presentation.pages.helpCzRM.pojo.EnumCzrmMailCommenti;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200Response;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner;
import it.liguriadigitale.ponmetro.richiesta.assistenza.model.ServicesDataV590SobjectsEmailMessageIDGet200Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class HelpCzRMDomandePanel extends BasePanel {

  private static final long serialVersionUID = -1199146446645343227L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public HelpCzRMDomandePanel(String id, SegnalazioniCzrmUtil lista) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(lista);
  }

  @Override
  public void fillDati(Object dati) {

    SegnalazioniCzrmUtil segnalazioni = (SegnalazioniCzrmUtil) dati;

    List<ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner> listaSegnalazioni =
        new ArrayList<>();

    if (LabelFdCUtil.checkIfNotNull(segnalazioni)
        && LabelFdCUtil.checkIfNotNull(segnalazioni.getSegnalazioni())) {
      listaSegnalazioni =
          segnalazioni.getSegnalazioni().getRecords().stream()
              .filter(elem -> !elem.getRecordTypeNameC().equalsIgnoreCase("EmailToCase"))
              .sorted(
                  Comparator.comparing(
                          ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner
                              ::getCreatedDate)
                      .reversed())
              .collect(Collectors.toList());
    }

    LinkDinamicoLaddaFunzione<Object> btnCreaSegnalazioneCzrm =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnCreaSegnalazioneCzrm",
            new LinkDinamicoFunzioneData(
                "HelpCzRMDomandePanel.btnCreaSegnalazioneCzrm",
                "HelpCzRMPage",
                "HelpCzRMDomandePanel.btnCreaSegnalazioneCzrm"),
            null,
            HelpCzRMDomandePanel.this,
            "color-orange-segnalaci col-auto icon-suggerimenti");
    addOrReplace(btnCreaSegnalazioneCzrm);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkSegnalazioni(listaSegnalazioni));
    addOrReplace(listaVuota);

    PageableListView<ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner> listView =
        new PageableListView<ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner>(
            "segnalazioni", listaSegnalazioni, 4) {

          private static final long serialVersionUID = 5670876069947623574L;

          @Override
          protected void populateItem(
              ListItem<ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner>
                  itemSegnalazione) {
            final ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner segnalazione =
                itemSegnalazione.getModelObject();

            log.debug("CP segnalazione czrm = " + segnalazione);

            Label caseNumber = new Label("caseNumber", segnalazione.getCaseNumber());
            caseNumber.setVisible(PageUtil.isStringValid(segnalazione.getCaseNumber()));
            itemSegnalazione.addOrReplace(caseNumber);

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "statoFrontEndC", segnalazione.getStatoFrontEndC(), HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "suppliedEmail", segnalazione.getSuppliedEmail(), HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "suppliedPhone", segnalazione.getSuppliedPhone(), HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "subject", segnalazione.getSubject(), HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "description", segnalazione.getDescription(), HelpCzRMDomandePanel.this));

            String dataCreazione = "";
            if (LabelFdCUtil.checkIfNotNull(segnalazione.getCreatedDate())) {
              dataCreazione =
                  LocalDateUtil.getDataOraCorretteInItalia(segnalazione.getCreatedDate());
            }
            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>("createdDate", dataCreazione, HelpCzRMDomandePanel.this));

            String dataUltimaModifica = "";
            if (LabelFdCUtil.checkIfNotNull(segnalazione.getLastModifiedDate())) {
              dataUltimaModifica =
                  LocalDateUtil.getDataOraCorretteInItalia(segnalazione.getLastModifiedDate());
            }
            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "lastModifiedDate", dataUltimaModifica, HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "sottofascicoloC",
                    segnalazione.getSottofascicoloC(),
                    HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "servizioC", segnalazione.getServizioC(), HelpCzRMDomandePanel.this));

            itemSegnalazione.addOrReplace(
                new AmtCardLabel<>(
                    "direzioneUfficioC",
                    segnalazione.getDirezioneUfficioC(),
                    HelpCzRMDomandePanel.this));

            List<CzrmMailCommenti> listaMailCommenti =
                popolaMailCommenti(
                    segnalazione.getId(), segnalazioni.getEmail(), segnalazioni.getNomeCognome());

            itemSegnalazione.addOrReplace(
                creaBtnDettagli(listaMailCommenti, segnalazione.getCaseNumber()));
          }
        };

    listView.setVisible(checkSegnalazioni(listaSegnalazioni));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkSegnalazioni(listaSegnalazioni) && listaSegnalazioni.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkSegnalazioni(
      List<ServicesDataV590SobjectsAccountIdCasesGet200ResponseRecordsInner> listaSegnalazioni) {
    return LabelFdCUtil.checkIfNotNull(listaSegnalazioni)
        && !LabelFdCUtil.checkEmptyList(listaSegnalazioni);
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnDettagli(
      List<CzrmMailCommenti> listaMailCommenti, String caseNumber) {
    FdCButtonBootstrapAjaxLink<Object> btnDettagli =
        new FdCButtonBootstrapAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = -2768196714510100927L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            target.add(HelpCzRMDomandePanel.this);

            setResponsePage(new HelpCzRMDettagliDomandePage(listaMailCommenti, caseNumber));
          }
        };

    btnDettagli.setVisible(
        LabelFdCUtil.checkIfNotNull(listaMailCommenti)
            && !LabelFdCUtil.checkEmptyList(listaMailCommenti));

    btnDettagli.setLabel(Model.of(getString("HelpCzRMDomandePanel.btnDettagli")));

    return btnDettagli;
  }

  private List<ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner> popolaMail(
      String idAccenture, String emailAccenture) {
    log.debug("HelpCzRMDomandePage popolaEmail");

    List<ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner> listaEmail =
        new ArrayList<>();

    try {
      ServicesDataV590SobjectsCaseIdEmailsGet200Response email =
          ServiceLocator.getInstance()
              .getSegnalazioniAccenture()
              .getListaMailSegnalazione(idAccenture, emailAccenture);

      if (LabelFdCUtil.checkIfNotNull(email)) {
        listaEmail = email.getRecords();

        Collections.sort(
            listaEmail,
            new Comparator<ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner>() {
              public int compare(
                  ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner o1,
                  ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner o2) {
                return o1.getMessageDate().compareTo(o2.getMessageDate());
              }
            });
      }

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaEmail: " + e.getMessage(), e);
    }
    return listaEmail;
  }

  private List<ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner> popolaCommenti(
      String idAccenture, String nomeCognome) {
    log.debug("HelpCzRMDomandePage popolaCommenti");

    List<ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner> listaCommenti =
        new ArrayList<>();

    try {
      ServicesDataV590SobjectsCaseIdCommentiRGet200Response commenti =
          ServiceLocator.getInstance()
              .getSegnalazioniAccenture()
              .getListaCommentiSegnalazione(idAccenture, nomeCognome);

      if (LabelFdCUtil.checkIfNotNull(commenti)) {
        listaCommenti = commenti.getRecords();

        Collections.sort(
            listaCommenti,
            new Comparator<ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner>() {
              public int compare(
                  ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner o1,
                  ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner o2) {
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
              }
            });
      }

    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaCommenti: " + e.getMessage(), e);
    }
    return listaCommenti;
  }

  private ServicesDataV590SobjectsEmailMessageIDGet200Response popolaMailDaCommento(
      String idEmailCommento) {
    log.debug("HelpCzRMDomandePage popolaMailDaCommento");

    ServicesDataV590SobjectsEmailMessageIDGet200Response emailCommento = null;

    try {
      emailCommento =
          ServiceLocator.getInstance()
              .getSegnalazioniAccenture()
              .getMailDaCommento(idEmailCommento);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popolaCommenti: " + e.getMessage(), e);
    }
    return emailCommento;
  }

  private List<CzrmMailCommenti> popolaMailCommenti(
      String idAccenture, String emailAccenture, String nomeCognome) {

    log.debug("CP popolaMailCommenti");

    List<CzrmMailCommenti> listaMailCommenti = new ArrayList<CzrmMailCommenti>();

    // List<ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner> listaMail =
    // popolaMail(idAccenture, emailAccenture);

    //		if(LabelFdCUtil.checkIfNotNull(listaMail) && !LabelFdCUtil.checkEmptyList(listaMail)) {
    //
    //			for(ServicesDataV590SobjectsCaseIdEmailsGet200ResponseRecordsInner elemMail : listaMail) {
    //				CzrmMailCommenti mail = new CzrmMailCommenti();
    //				mail.setTipologia(EnumCzrmMailCommenti.E);
    //				mail.setData(elemMail.getMessageDate());
    //				mail.setOggetto(elemMail.getSubject());
    //				mail.setBody(elemMail.getHtmlBody());
    //				listaMailCommenti.add(mail);
    //			}
    //
    //		}

    List<ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner> listaCommenti =
        popolaCommenti(idAccenture, nomeCognome);

    if (LabelFdCUtil.checkIfNotNull(listaCommenti) && !LabelFdCUtil.checkEmptyList(listaCommenti)) {

      for (ServicesDataV590SobjectsCaseIdCommentiRGet200ResponseRecordsInner elemCommenti :
          listaCommenti) {

        log.debug(
            "CP email message = "
                + elemCommenti.getEmailMessageC()
                + " - "
                + elemCommenti.getMailFuoriRigheC());

        if (elemCommenti.getMailFuoriRigheC()) {
          if (LabelFdCUtil.checkIfNotNull(elemCommenti.getEmailMessageC())) {

            CzrmMailCommenti emailMessage = new CzrmMailCommenti();

            emailMessage.setTipologia(EnumCzrmMailCommenti.E);
            emailMessage.setEmailMessage(elemCommenti.getEmailMessageC());

            ServicesDataV590SobjectsEmailMessageIDGet200Response mailDaCommento =
                popolaMailDaCommento(emailMessage.getEmailMessage());

            emailMessage.setData(elemCommenti.getCreatedDate());
            emailMessage.setOggetto(elemCommenti.getName());
            emailMessage.setBody(mailDaCommento.getTextBody());

            listaMailCommenti.add(emailMessage);
          }
        } else {
          CzrmMailCommenti commento = new CzrmMailCommenti();
          commento.setTipologia(EnumCzrmMailCommenti.C);
          commento.setData(elemCommenti.getCreatedDate());
          commento.setOggetto(elemCommenti.getName());
          commento.setBody(elemCommenti.getDescriptionC());

          listaMailCommenti.add(commento);
        }
      }
    }

    Collections.sort(
        listaMailCommenti,
        new Comparator<CzrmMailCommenti>() {
          public int compare(CzrmMailCommenti o1, CzrmMailCommenti o2) {
            return o1.getData().compareTo(o2.getData());
          }
        });

    return listaMailCommenti;
  }
}
