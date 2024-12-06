package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.aggiornadati.ModificaDatiPersonaliBibliotechePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.panel.BibliotecheMovimentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.pagamenti.panel.FascicoloTabPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Movimento;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class BibliotecheMovimentiDettaglioPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -6087253697750304263L;

  boolean isAggiornamentoDatiTutore;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BibliotecheMovimentiDettaglioPage() {
    super();
    isAggiornamentoDatiTutore = true;

    if (neomaggiorennePrecedentementeIscritto()) {
      throw new RestartResponseAtInterceptPageException(
          new ModificaDatiPersonaliBibliotechePage(
              getUtente().getListaUtenteBiblioteche().get(0), isAggiornamentoDatiTutore));
    } else {
      String nomeSpid = getUtente().getNome().trim();
      String cognomeSpid = getUtente().getCognome().trim();

      String nomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
      String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

      boolean datiSpidSebinaOk =
          nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);

      NotEmptyLabel nome =
          new NotEmptyLabel(
              "nome",
              new StringResourceModel("BibliotecheMovimentiDettaglioPage.nome", this)
                  .setParameters(getUtente().getNome()));
      addOrReplace(nome);

      String mail = getUtente().getListaUtenteBiblioteche().get(0).getMail();
      String mobile = getUtente().getListaUtenteBiblioteche().get(0).getMobile();

      NotEmptyLabel email = new NotEmptyLabel("email", mail);
      addOrReplace(email);

      NotEmptyLabel telefono = new NotEmptyLabel("telefono", mobile);
      addOrReplace(telefono);

      addOrReplace(creaBtnModificaDati(getUtente().getListaUtenteBiblioteche().get(0)));

      List<Legenda> listaLegenda =
          ServiceLocator.getInstance().getServiziBiblioteche().getListaLegenda();
      LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
      legendaPanel.setVisible(
          getUtente().getListaUtenteBiblioteche().size() == 1 && datiSpidSebinaOk);
      addOrReplace(legendaPanel);

      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel(
              "breadcrumbPanel",
              MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
      addOrReplace(breadcrumbPanel);

      if (getUtente().getListaUtenteBiblioteche().size() > 1) {

        String messageInfo = getString("BibliotecheMovimentiDettaglioPage.messageInfo");
        List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
        MessaggiInformativi messaggio = new MessaggiInformativi();
        messaggio.setMessaggio(messageInfo);
        messaggio.setType("danger");
        listaMessaggi.add(messaggio);

        AlertBoxPanel<Component> messaggi =
            (AlertBoxPanel<Component>)
                new AlertBoxPanel<Component>("tabsPanel", listaMessaggi).setRenderBodyOnly(true);

        addOrReplace(messaggi);

        List<Utente> listaUtenteSebina = getUtente().getListaUtenteBiblioteche();
        List<Long> listaIdSebina = new ArrayList<Long>();
        for (Utente utenteSebina : listaUtenteSebina) {
          listaIdSebina.add(utenteSebina.getId());
        }

        ServiceLocator.getInstance()
            .getServiziBiblioteche()
            .inviaMailSegnalazione(getUtente(), listaIdSebina);

      } else {
        if (!datiSpidSebinaOk) {
          //					NotificationPanel notificationPanel = new NotificationPanel("tabsPanel");
          String messageInfo = getString("BibliotecheMovimentiDettaglioPage.messageInfo");
          //					notificationPanel.info(messageInfo);
          //					addOrReplace(notificationPanel);

          List<MessaggiInformativi> listaMessaggi = new ArrayList<>();
          MessaggiInformativi messaggio = new MessaggiInformativi();
          messaggio.setMessaggio(messageInfo);
          messaggio.setType("danger");
          listaMessaggi.add(messaggio);

          AlertBoxPanel<Component> messaggi =
              (AlertBoxPanel<Component>)
                  new AlertBoxPanel<Component>("tabsPanel", listaMessaggi).setRenderBodyOnly(true);

          addOrReplace(messaggi);

          List<Utente> listaUtenteSebina = getUtente().getListaUtenteBiblioteche();
          List<Long> listaIdSebina = new ArrayList<Long>();
          for (Utente utenteSebina : listaUtenteSebina) {
            listaIdSebina.add(utenteSebina.getId());
          }

          ServiceLocator.getInstance()
              .getServiziBiblioteche()
              .inviaMailSegnalazione(getUtente(), listaIdSebina);
        } else {
          List<ITab> listaTabsBiblioteche = new ArrayList<>();
          AbstractTab tabPrestiti =
              new AbstractTab(
                  new Model<>(getString("BibliotecheMovimentiDettaglioPage.prestiti"))) {

                private static final long serialVersionUID = 811471131405817598L;

                @Override
                public Panel getPanel(String panelId) {
                  List<Movimento> listaPrestiti =
                      popolaListaPrestiti(getUtente().getCodiceFiscaleOperatore());
                  EnumMovimento prestiti = EnumMovimento.PRESTITI;
                  return new BibliotecheMovimentiPanel(panelId, listaPrestiti, prestiti);
                }
              };
          AbstractTab tabPrenotazioni =
              new AbstractTab(
                  new Model<>(getString("BibliotecheMovimentiDettaglioPage.prenotazioni"))) {

                private static final long serialVersionUID = -6510077337382522892L;

                @Override
                public Panel getPanel(String panelId) {
                  List<Movimento> listaPrenotazioni =
                      popolaListaPrenotazioni(getUtente().getCodiceFiscaleOperatore());
                  EnumMovimento prenotazioni = EnumMovimento.PRENOTAZIONI;
                  return new BibliotecheMovimentiPanel(panelId, listaPrenotazioni, prenotazioni);
                }
              };
          AbstractTab tabTutti =
              new AbstractTab(new Model<>(getString("BibliotecheMovimentiDettaglioPage.tutti"))) {

                private static final long serialVersionUID = -4393393179016095315L;

                @Override
                public Panel getPanel(String panelId) {
                  List<Movimento> listaMovimenti =
                      popolaListaTutti(getUtente().getCodiceFiscaleOperatore());
                  EnumMovimento tutti = EnumMovimento.TUTTI;
                  return new BibliotecheMovimentiPanel(panelId, listaMovimenti, tutti);
                }
              };

          listaTabsBiblioteche.add(tabPrestiti);
          listaTabsBiblioteche.add(tabPrenotazioni);
          listaTabsBiblioteche.add(tabTutti);

          FascicoloTabPanel<ITab> fascicoloTabPanel =
              new FascicoloTabPanel<>("tabsPanel", listaTabsBiblioteche);
          fascicoloTabPanel.setOutputMarkupId(true);
          addOrReplace(fascicoloTabPanel);
        }
      }
      setOutputMarkupId(true);
    }
  }

  private boolean neomaggiorennePrecedentementeIscritto() {

    boolean documentoIdentitaTutorePresente = false;

    if (getUtente().getListaUtenteBiblioteche() != null
        && getUtente().getListaUtenteBiblioteche().size() == 1
        && getUtente().getListaUtenteBiblioteche().get(0) != null) {
      Utente utente = getUtente().getListaUtenteBiblioteche().get(0);

      if (utente.getDocIdentTutore() == null) {
        documentoIdentitaTutorePresente = false;

      } else {
        documentoIdentitaTutorePresente = true;
      }
    }

    return documentoIdentitaTutorePresente;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BibliotecheMovimentiDettaglioPage(ComponenteNucleo bambino) {

    isAggiornamentoDatiTutore = false;

    try {

      Residente datiBambino = bambino.getDatiCittadino();

      NotEmptyLabel nome =
          new NotEmptyLabel(
              "nome",
              new StringResourceModel("BibliotecheMovimentiDettaglioPage.nome", this)
                  .setParameters(datiBambino.getCpvGivenName()));
      addOrReplace(nome);

      if (LabelFdCUtil.checkIfNotNull(datiBambino)) {
        String nomeSpid = datiBambino.getCpvGivenName().trim();
        String cognomeSpid = datiBambino.getCpvFamilyName().trim();

        List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaFindByDocIdentita =
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getUtenteByCf(bambino.getCodiceFiscale());

        if (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
            && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita)) {
          String nomeSebina = listaFindByDocIdentita.get(0).getGivenName().trim();
          String cognomeSebina = listaFindByDocIdentita.get(0).getSn().trim();

          boolean datiSpidSebinaOk =
              nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);

          String mail = listaFindByDocIdentita.get(0).getMail();
          String mobile = listaFindByDocIdentita.get(0).getMobile();

          NotEmptyLabel email = new NotEmptyLabel("email", mail);
          addOrReplace(email);

          NotEmptyLabel telefono = new NotEmptyLabel("telefono", mobile);
          addOrReplace(telefono);

          if (LabelFdCUtil.checkIfNotNull(datiBambino)
              && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita)) {
            addOrReplace(creaBtnModificaDati(listaFindByDocIdentita.get(0)));
          } else {
            addOrReplace(new WebMarkupContainer("btnModificaDati").setVisible(false));
          }

          FdCBreadcrumbPanel breadcrumbPanel =
              new FdCBreadcrumbPanel(
                  "breadcrumbPanel",
                  MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
          addOrReplace(breadcrumbPanel);

          List<Legenda> listaLegenda =
              ServiceLocator.getInstance().getServiziBiblioteche().getListaLegenda();
          LegendaPanel legendaPanel = new LegendaPanel<Component>("legendaPanel", listaLegenda);
          legendaPanel.setVisible(
              LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
                  && listaFindByDocIdentita.size() == 1
                  && datiSpidSebinaOk);
          addOrReplace(legendaPanel);

          if (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
              && listaFindByDocIdentita.size() > 1) {
            NotificationPanel notificationPanel = new NotificationPanel("tabsPanel");
            String messageInfo = getString("BibliotecheMovimentiDettaglioPage.messageInfo");
            notificationPanel.info(messageInfo);
            addOrReplace(notificationPanel);

            List<Utente> listaUtenteSebina = listaFindByDocIdentita;
            List<Long> listaIdSebina = new ArrayList<Long>();
            for (Utente utenteSebina : listaUtenteSebina) {
              listaIdSebina.add(utenteSebina.getId());
            }

            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .inviaMailSegnalazioneBambino(bambino, getUtente(), listaIdSebina);

          } else {
            if (!datiSpidSebinaOk) {
              NotificationPanel notificationPanel = new NotificationPanel("tabsPanel");
              String messageInfo = getString("BibliotecheMovimentiDettaglioPage.messageInfo");
              notificationPanel.info(messageInfo);
              addOrReplace(notificationPanel);

              List<Utente> listaUtenteSebina = listaFindByDocIdentita;
              List<Long> listaIdSebina = new ArrayList<Long>();
              for (Utente utenteSebina : listaUtenteSebina) {
                listaIdSebina.add(utenteSebina.getId());
              }

              ServiceLocator.getInstance()
                  .getServiziBiblioteche()
                  .inviaMailSegnalazioneBambino(bambino, getUtente(), listaIdSebina);
            } else {
              List<ITab> listaTabsBiblioteche = new ArrayList<>();
              AbstractTab tabPrestiti =
                  new AbstractTab(
                      new Model<>(getString("BibliotecheMovimentiDettaglioPage.prestiti"))) {

                    private static final long serialVersionUID = -8458170014170125802L;

                    @Override
                    public Panel getPanel(String panelId) {
                      List<Movimento> listaPrestiti =
                          popolaListaPrestiti(bambino.getCodiceFiscale());
                      EnumMovimento prestiti = EnumMovimento.PRESTITI;
                      return new BibliotecheMovimentiPanel(panelId, listaPrestiti, prestiti);
                    }
                  };
              AbstractTab tabPrenotazioni =
                  new AbstractTab(
                      new Model<>(getString("BibliotecheMovimentiDettaglioPage.prenotazioni"))) {

                    private static final long serialVersionUID = -330692853856422659L;

                    @Override
                    public Panel getPanel(String panelId) {
                      List<Movimento> listaPrenotazioni =
                          popolaListaPrenotazioni(bambino.getCodiceFiscale());
                      EnumMovimento prenotazioni = EnumMovimento.PRENOTAZIONI;
                      return new BibliotecheMovimentiPanel(
                          panelId, listaPrenotazioni, prenotazioni);
                    }
                  };
              AbstractTab tabTutti =
                  new AbstractTab(
                      new Model<>(getString("BibliotecheMovimentiDettaglioPage.tutti"))) {

                    private static final long serialVersionUID = -4105796951443965574L;

                    @Override
                    public Panel getPanel(String panelId) {
                      List<Movimento> listaMovimenti = popolaListaTutti(bambino.getCodiceFiscale());
                      EnumMovimento tutti = EnumMovimento.TUTTI;
                      return new BibliotecheMovimentiPanel(panelId, listaMovimenti, tutti);
                    }
                  };

              listaTabsBiblioteche.add(tabPrestiti);
              listaTabsBiblioteche.add(tabPrenotazioni);
              listaTabsBiblioteche.add(tabTutti);

              FascicoloTabPanel<ITab> fascicoloTabPanel =
                  new FascicoloTabPanel<>("tabsPanel", listaTabsBiblioteche);
              fascicoloTabPanel.setOutputMarkupId(true);
              addOrReplace(fascicoloTabPanel);
            }
          }
        }
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore durante movimenti Sebina bambino: " + e.getMessage());
    }

    setOutputMarkupId(true);
  }

  private List<Movimento> popolaListaPrestiti(String codiceFiscale) {
    try {
      return ServiceLocator.getInstance().getServiziBiblioteche().getListaPrestiti(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }

  private List<Movimento> popolaListaPrenotazioni(String codiceFiscale) {
    try {
      return ServiceLocator.getInstance()
          .getServiziBiblioteche()
          .getListaPrenotazioni(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }

  private List<Movimento> popolaListaTutti(String codiceFiscale) {
    try {
      return ServiceLocator.getInstance()
          .getServiziBiblioteche()
          .getMovimentiCorrentiUtenteById(codiceFiscale);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("io Leggo"));
    }
  }

  private LaddaAjaxLink<Object> creaBtnModificaDati(Utente utenteSebina) {
    LaddaAjaxLink<Object> btnModificaDati =
        new LaddaAjaxLink<Object>("btnModificaDati", Type.Primary) {

          private static final long serialVersionUID = -7739872507502450687L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(BibliotecheMovimentiDettaglioPage.this);
            setResponsePage(
                new ModificaDatiPersonaliBibliotechePage(utenteSebina, isAggiornamentoDatiTutore));
          }
        };
    btnModificaDati.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "BibliotecheMovimentiDettaglioPage.modificaDati",
                    BibliotecheMovimentiDettaglioPage.this)));

    btnModificaDati.setVisible(true);

    return btnModificaDati;
  }
}
