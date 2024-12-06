package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.enums.StatoIstanza;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanelGenericContent;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.MieIstanzePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.form.UtilIstanze;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieistanze.pl.GestisciIstanzaPLPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.DettaglioVerbaliPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.utilverbali.UtilVerbaliDataNotifica;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.verbalirate.RichiestaRateizzazionePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.AnagraficaNotifica;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.IdDocumentiIstanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Istanza;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Motivazione;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Verbale;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class MieIstanzePanel extends BasePanel {

  private static final long serialVersionUID = -5996851877419565818L;

  private boolean cercaIstanza;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;
  public Istanza istanza;

  private String codice;

  public MieIstanzePanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public MieIstanzePanel(String id, boolean cercaIstanza) {
    super(id);

    this.cercaIstanza = cercaIstanza;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public MieIstanzePanel(String id, String codice) {
    super(id);

    this.codice = codice;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Istanza> listaIstanze = (List<Istanza>) dati;
    if (listaIstanze != null && !listaIstanze.isEmpty()) {
      // ordina per data
      listaIstanze =
          listaIstanze.stream()
              .sorted(Comparator.comparing(Istanza::getId).reversed())
              .sorted(Comparator.comparing(Istanza::getAnno).reversed())
              .collect(Collectors.toList());
    }
    List<Istanza> listaIstanzeSenzaNull =
        listaIstanze.stream().filter(elem -> elem != null).collect(Collectors.toList());

    String messaggioInfoCercaIstanze = "";
    if (PageUtil.isStringValid(codice)) {
      messaggioInfoCercaIstanze =
          getString("MieIstanzePanel.listaIstanzeVuotaDaBadge").concat(" ").concat(codice);
    } else {
      messaggioInfoCercaIstanze = getString("MieIstanzePanel.listaIstanzeVuota");
    }

    // Label listaIstanzeVuota = new Label("listaIstanzeVuota",
    // getString("MieIstanzePanel.listaIstanzeVuota"));
    Label listaIstanzeVuota = new Label("listaIstanzeVuota", messaggioInfoCercaIstanze);
    listaIstanzeVuota.setVisible(listaIstanze.isEmpty() && !cercaIstanza);
    add(listaIstanzeVuota);

    Label istanzaNonTrovata =
        new Label("istanzaNonTrovata", getString("MieIstanzePanel.istanzaNonTrovata"));
    istanzaNonTrovata.setVisible(cercaIstanza && listaIstanze.isEmpty());
    add(istanzaNonTrovata);

    PageableListView<Istanza> listView =
        new PageableListView<Istanza>("box", listaIstanzeSenzaNull, 4) {

          private static final long serialVersionUID = -4185564655257906953L;

          @Override
          protected void populateItem(ListItem<Istanza> item) {
            istanza = item.getModelObject();

            List<Verbale> listaTuttiVerbali = istanza.getVerbali();
            List<Verbale> listaVerbaliAccettati = new ArrayList<Verbale>();
            List<Verbale> listaVerbaliRespinti = new ArrayList<Verbale>();
            List<Verbale> listaVerbali = new ArrayList<Verbale>();
            List<IdDocumentiIstanza> listaDocumenti = istanza.getDocumenti();

            for (Verbale verbale : listaTuttiVerbali) {
              if (verbale.getIsAccettatoInIstanza() == null) {
                listaVerbali.add(verbale);
              } else if (verbale.getIsAccettatoInIstanza()) {
                listaVerbaliAccettati.add(verbale);
              } else {
                listaVerbaliRespinti.add(verbale);
              }
            }

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaIstanza(istanza.getStatoCodice()));
            item.add(icona);

            Label identificativo = new Label("identificativo", istanza.getId());
            identificativo.setVisible(istanza.getId() != null);
            item.add(identificativo);

            Label anno = new Label("anno", istanza.getAnno());
            anno.setVisible(istanza.getAnno() != null);
            item.add(anno);

            Label descrizione = new Label("descrizione", istanza.getDescrizione());
            descrizione.setVisible(PageUtil.isStringValid(istanza.getDescrizione()));
            item.add(descrizione);

            Label soggetto = new Label("soggetto", istanza.getSoggetto().getRdfsLabel());
            soggetto.setVisible(PageUtil.isStringValid(istanza.getSoggetto().getRdfsLabel()));
            item.add(soggetto);

            Label data = new Label("data", LocalDateUtil.getDataFormatoEuropeo(istanza.getData()));
            data.setVisible(istanza.getData() != null);
            item.add(data);

            Label stato = new Label("stato", getDescrizioneFDCIstanza(istanza));
            stato.setVisible(PageUtil.isStringValid(getDescrizioneFDCIstanza(istanza)));
            item.add(stato);

            Label statoDal =
                new Label("statoDal", LocalDateUtil.getDataFormatoEuropeo(istanza.getStatoData()));
            statoDal.setVisible(istanza.getStatoData() != null);
            item.add(statoDal);

            Label esito = new Label("esito", istanza.getEsito());
            esito.setVisible(PageUtil.isStringValid(istanza.getEsito()));
            item.add(esito);

            Label note = new Label("note", istanza.getNoteIntegrazioneBackOffice());
            note.setVisible(PageUtil.isStringValid(istanza.getNoteIntegrazioneBackOffice()));
            item.add(note);

            Label ufficioIncaricato =
                new Label("ufficioIncaricato", istanza.getUfficioIncaricato());
            ufficioIncaricato.setVisible(PageUtil.isStringValid(istanza.getUfficioIncaricato()));
            item.add(ufficioIncaricato);

            item.add(creaLinkDettaglioIstanza(istanza));
            item.add(creaLinkConcludiIstanza(istanza));
            item.add(creaLinkIntegraIstanza(istanza));
            item.add(creaButtonCompletaIstanzaRateizzazione(istanza));

            String motivazioniValue = "";
            List<Motivazione> listaMotivazioni = istanza.getMotivazioni();
            if (listaMotivazioni != null && !listaMotivazioni.isEmpty()) {
              for (Motivazione motivazioneElem : listaMotivazioni) {
                String codice = "";
                String descrizioneMotivazione = "";

                if (motivazioneElem != null) {
                  if (motivazioneElem.getCodice() != null) {
                    codice =
                        getString("MieIstanzePanel.codice")
                            .concat(" ")
                            .concat(motivazioneElem.getCodice());
                  }

                  if (motivazioneElem.getDescrizione() != null) {
                    descrizioneMotivazione =
                        codice.concat(" - ").concat(motivazioneElem.getDescrizione());
                  }

                  motivazioniValue = motivazioniValue.concat(descrizioneMotivazione).concat("\n");
                }
              }
            }
            MultiLineLabel motivazioni = new MultiLineLabel("motivazioni", motivazioniValue);
            motivazioni.setVisible(istanza.getMotivazioni() != null);
            motivazioni.setEscapeModelStrings(false);
            item.add(motivazioni);

            // non mi differenzio, copia e incolla e vai
            ListView<Verbale> listViewDatiVerbaleAccettati =
                new ListView<Verbale>("listViewDatiVerbaleAccettati", listaVerbaliAccettati) {

                  private static final long serialVersionUID = 6871311467106930303L;

                  @Override
                  protected void populateItem(ListItem<Verbale> item) {
                    Verbale verbale = item.getModelObject();

                    item.setOutputMarkupId(true);

                    String dettaglio = "";
                    if (verbale != null) {
                      String numeroAvviso = "";
                      String numeroProtocollo = "";

                      if (verbale.getNumeroAvviso() != null) {
                        numeroAvviso =
                            getString("MieIstanzePanel.numeroAvviso")
                                .concat(" ")
                                .concat(verbale.getNumeroAvviso());
                      }

                      if (verbale.getNumeroProtocollo() != null) {
                        numeroProtocollo =
                            numeroAvviso
                                .concat(getString("MieIstanzePanel.info1"))
                                .concat(getString("MieIstanzePanel.numeroProtocollo"))
                                .concat(verbale.getNumeroProtocollo())
                                .concat(getString("MieIstanzePanel.info2"));
                      }

                      dettaglio = dettaglio.concat(numeroProtocollo);
                    }

                    Label datiVerbale = new Label("datiVerbale", dettaglio);
                    datiVerbale.setVisible(verbale != null);
                    item.add(datiVerbale);

                    item.add(creaLinkDettagliVerbale(verbale));
                  }

                  private LaddaAjaxLink<Object> creaLinkDettagliVerbale(Verbale verbale) {
                    LaddaAjaxLink<Object> linkDettagliVerbale =
                        new LaddaAjaxLink<Object>("btnDettagliVerbale", Type.Primary) {

                          private static final long serialVersionUID = -6560827124701057928L;

                          @Override
                          public void onClick(AjaxRequestTarget target) {
                            target.add(MieIstanzePanel.this);

                            controlloSeVerbalePresenteInAnagraficaOBolloDelLoggato(verbale);
                          }
                        };
                    linkDettagliVerbale.setLabel(
                        Model.of(
                            Application.get()
                                .getResourceSettings()
                                .getLocalizer()
                                .getString("MieIstanzePanel.dettagli", MieIstanzePanel.this)));

                    return linkDettagliVerbale;
                  }
                };

            listViewDatiVerbaleAccettati.setVisible(
                listaVerbaliAccettati != null && !listaVerbaliAccettati.isEmpty());

            ListView<Verbale> listViewDatiVerbale =
                new ListView<Verbale>("listViewDatiVerbale", listaVerbali) {

                  private static final long serialVersionUID = 6871311467106930303L;

                  @Override
                  protected void populateItem(ListItem<Verbale> item) {
                    Verbale verbale = item.getModelObject();

                    item.setOutputMarkupId(true);

                    String dettaglio = "";
                    if (verbale != null) {
                      String numeroAvviso = "";
                      String numeroProtocollo = "";

                      if (verbale.getNumeroAvviso() != null) {
                        numeroAvviso =
                            getString("MieIstanzePanel.numeroAvviso")
                                .concat(" ")
                                .concat(verbale.getNumeroAvviso());
                      }

                      if (verbale.getNumeroProtocollo() != null) {
                        numeroProtocollo =
                            numeroAvviso
                                .concat(getString("MieIstanzePanel.info1"))
                                .concat(getString("MieIstanzePanel.numeroProtocollo"))
                                .concat(verbale.getNumeroProtocollo())
                                .concat(getString("MieIstanzePanel.info2"));
                      }

                      dettaglio = dettaglio.concat(numeroProtocollo);
                    }

                    Label datiVerbale = new Label("datiVerbale", dettaglio);
                    datiVerbale.setVisible(verbale != null);
                    item.add(datiVerbale);

                    item.add(creaLinkDettagliVerbale(verbale));
                  }

                  private LaddaAjaxLink<Object> creaLinkDettagliVerbale(Verbale verbale) {
                    LaddaAjaxLink<Object> linkDettagliVerbale =
                        new LaddaAjaxLink<Object>("btnDettagliVerbale", Type.Primary) {

                          private static final long serialVersionUID = -6560827124701057928L;

                          @Override
                          public void onClick(AjaxRequestTarget target) {
                            target.add(MieIstanzePanel.this);

                            controlloSeVerbalePresenteInAnagraficaOBolloDelLoggato(verbale);
                          }
                        };
                    linkDettagliVerbale.setLabel(
                        Model.of(
                            Application.get()
                                .getResourceSettings()
                                .getLocalizer()
                                .getString("MieIstanzePanel.dettagli", MieIstanzePanel.this)));

                    return linkDettagliVerbale;
                  }
                };

            listViewDatiVerbale.setVisible(listaVerbali != null && !listaVerbali.isEmpty());

            ListView<Verbale> listViewDatiVerbaleRespinti =
                new ListView<Verbale>("listViewDatiVerbaleRespinti", listaVerbaliRespinti) {

                  private static final long serialVersionUID = 6871311467106930303L;

                  @Override
                  protected void populateItem(ListItem<Verbale> item) {
                    Verbale verbale = item.getModelObject();

                    item.setOutputMarkupId(true);

                    String dettaglio = "";
                    if (verbale != null) {
                      String numeroAvviso = "";
                      String numeroProtocollo = "";

                      if (verbale.getNumeroAvviso() != null) {
                        numeroAvviso =
                            getString("MieIstanzePanel.numeroAvviso")
                                .concat(" ")
                                .concat(verbale.getNumeroAvviso());
                      }

                      if (verbale.getNumeroProtocollo() != null) {
                        numeroProtocollo =
                            numeroAvviso
                                .concat(getString("MieIstanzePanel.info1"))
                                .concat(getString("MieIstanzePanel.numeroProtocollo"))
                                .concat(verbale.getNumeroProtocollo())
                                .concat(getString("MieIstanzePanel.info2"));
                      }

                      dettaglio = dettaglio.concat(numeroProtocollo);
                    }

                    Label datiVerbale = new Label("datiVerbale", dettaglio);
                    datiVerbale.setVisible(verbale != null);
                    item.add(datiVerbale);

                    item.add(creaLinkDettagliVerbale(verbale));
                  }

                  private LaddaAjaxLink<Object> creaLinkDettagliVerbale(Verbale dettagliVerbale) {
                    LaddaAjaxLink<Object> linkDettagliVerbale =
                        new LaddaAjaxLink<Object>("btnDettagliVerbale", Type.Primary) {

                          private static final long serialVersionUID = -6560827124701057928L;

                          @Override
                          public void onClick(AjaxRequestTarget target) {
                            target.add(MieIstanzePanel.this);

                            setResponsePage(new DettaglioVerbaliPage(dettagliVerbale));
                          }
                        };
                    linkDettagliVerbale.setLabel(
                        Model.of(
                            Application.get()
                                .getResourceSettings()
                                .getLocalizer()
                                .getString("MieIstanzePanel.dettagli", MieIstanzePanel.this)));

                    return linkDettagliVerbale;
                  }
                };

            listViewDatiVerbaleRespinti.setVisible(
                listaVerbaliRespinti != null && !listaVerbaliRespinti.isEmpty());

            // documenti
            ListView<IdDocumentiIstanza> listViewDocumenti =
                new ListView<IdDocumentiIstanza>("listViewDocumenti", listaDocumenti) {

                  private static final long serialVersionUID = 6871311467106930303L;

                  @Override
                  protected void populateItem(ListItem<IdDocumentiIstanza> item) {
                    IdDocumentiIstanza idDocumentiIstanza = item.getModelObject();

                    item.setOutputMarkupId(true);

                    Label nomeDocumento = new Label("nomeDocumento", idDocumentiIstanza.getNome());
                    nomeDocumento.setVisible(idDocumentiIstanza.getNome() != null);
                    item.add(nomeDocumento);

                    // item.add(creaLinkDownloadDocumento(idDocumentiIstanza));
                    try {
                      if (LabelFdCUtil.checkIfNotNull(idDocumentiIstanza)
                          && LabelFdCUtil.checkIfNotNull(idDocumentiIstanza.getNome())) {
                        // chiama servizio qui per i byte
                        FileAllegato fileAllegato =
                            ServiceLocator.getInstance()
                                .getServiziMieiVerbali()
                                .getAllegatoIstanza(
                                    null,
                                    "" + istanza.getId(),
                                    istanza.getAnno().intValue(),
                                    "" + idDocumentiIstanza.getId());
                        // String estensione =
                        // FileFdCUtil.getEstensionFileUploadAllegato(fileAllegato.getFile());
                        // String mimeType =
                        // FileFdCUtil.getMimeTypeFileUploadAllegato(fileAllegato.getFile());
                        item.addOrReplace(
                            VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                                "btnDownloadDocumento",
                                idDocumentiIstanza.getNome(),
                                fileAllegato.getFile()));
                      } else {
                        WebMarkupContainer btnDownloadDocumento =
                            new WebMarkupContainer("btnDownloadDocumento");
                        btnDownloadDocumento.setVisible(false);
                        item.addOrReplace(btnDownloadDocumento);
                      }

                    } catch (BusinessException
                        | MagicMatchNotFoundException
                        | ApiException
                        | IOException e) {
                      log.error("Errore scarica documento apk: " + e.getMessage());
                    }
                  }

                  private LaddaAjaxLink<Object> creaLinkDownloadDocumento(
                      IdDocumentiIstanza idDocumentiIstanza) {
                    LaddaAjaxLink<Object> linkDownloadDocumento =
                        new LaddaAjaxLink<Object>("btnDownloadDocumento", Type.Primary) {

                          private static final long serialVersionUID = -6560827124701057928L;

                          // public IdDocumentiIstanza aa =
                          // idDocumentiIstanza;
                          @Override
                          public void onClick(AjaxRequestTarget target) {
                            target.add(MieIstanzePanel.this);

                            // chiama servizio qui per i byte

                            FileAllegato fileAllegato;
                            try {
                              fileAllegato =
                                  ServiceLocator.getInstance()
                                      .getServiziMieiVerbali()
                                      .getAllegatoIstanza(
                                          null,
                                          "" + istanza.getId(),
                                          istanza.getAnno().intValue(),
                                          "" + idDocumentiIstanza.getId());
                              String estensione =
                                  FileFdCUtil.getEstensionFileUploadAllegato(
                                      fileAllegato.getFile());
                              String mimeType =
                                  FileFdCUtil.getMimeTypeFileUploadAllegato(fileAllegato.getFile());
                              item.addOrReplace(
                                  createLinkDocumentoCaricato(
                                      "btnDownloadDocumento",
                                      idDocumentiIstanza.getNome(),
                                      fileAllegato.getFile(),
                                      estensione,
                                      mimeType));
                            } catch (BusinessException
                                | ApiException
                                | IOException
                                | MagicMatchNotFoundException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                            }
                          }
                        };
                    linkDownloadDocumento.setLabel(
                        Model.of(
                            Application.get()
                                .getResourceSettings()
                                .getLocalizer()
                                .getString("MieIstanzePanel.visualizza", MieIstanzePanel.this)));

                    return linkDownloadDocumento;
                  }
                };

            listViewDatiVerbaleAccettati.setVisible(
                listaVerbaliAccettati != null && !listaVerbaliAccettati.isEmpty());
            listViewDocumenti.setVisible(listaDocumenti != null && !listaDocumenti.isEmpty());
            setOutputMarkupId(true);
            item.add(listViewDatiVerbaleRespinti);
            item.add(listViewDatiVerbale);
            item.add(listViewDatiVerbaleAccettati);
            item.add(listViewDocumenti);
          }
        };
    setOutputMarkupId(true);
    listView.setRenderBodyOnly(true);
    listView.setVisible(!listaIstanze.isEmpty());
    add(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationMieIstanze", listView);
    paginazioneFascicolo.setVisible(listaIstanze.size() > 4);
    add(paginazioneFascicolo);
  }

  public Component createLinkDocumentoCaricato(
      String idWicket, String nomeFile, byte[] file, String estensione, String mimeType)
      throws BusinessException {
    ResourceLink<?> linkFile =
        VariazioniResidenzaUtil.downloadPdfImage(idWicket, nomeFile, file, estensione, mimeType);
    boolean visibile = LabelFdCUtil.checkIfNotNull(file);

    if (LabelFdCUtil.checkIfNotNull(linkFile)) {
      linkFile.setVisible(visibile);
      return linkFile;
    } else {
      WebMarkupContainer btnWicketId = new WebMarkupContainer(idWicket);
      btnWicketId.setVisible(false);

      return btnWicketId;
    }
  }

  public String getDescrizioneFDCIstanza(Istanza istanza) {
    String[][] stati = MieIstanzePage.getStati();
    int i = 0;
    if ("CMP".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 0;
    } else if ("IVT".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 1;
    } else if ("PCR".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 2;
    } else if ("ATT".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 3;
    } else if ("EVA".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 4;
    } else if ("EVP".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 5;
    } else if ("EVR".equalsIgnoreCase(istanza.getStatoCodice())) {
      i = 6;
    }
    if (i == 0) {
      return istanza.getStatoDescrizione();
    }
    return stati[i][1];
  }

  // da fe come tributi ma qui mi adeguo (copy and paste)^n
  private AttributeAppender getCssIconaIstanza(String anotherString) {
    String[][] stati = MieIstanzePage.getStati();
    String aaa = " icon-foglio ";
    String bbb = " col-3 ";
    String ccc = BasePanelGenericContent.CSS_COLOR_FDC_LIGHT;
    int i = 0;

    if ("CMP".equalsIgnoreCase(anotherString)) {
      i = 0;
    } else if ("IVT".equalsIgnoreCase(anotherString)) {
      i = 1;
    } else if ("PCR".equalsIgnoreCase(anotherString)) {
      i = 2;
    } else if ("ATT".equalsIgnoreCase(anotherString)) {
      i = 3;
    } else if ("EVA".equalsIgnoreCase(anotherString)) {
      i = 4;
    } else if ("EVP".equalsIgnoreCase(anotherString)) {
      i = 5;
    } else if ("EVR".equalsIgnoreCase(anotherString)) {
      i = 6;
    }
    ccc = stati[i][3];
    String css = bbb + ccc + aaa;

    return new AttributeAppender("class", " " + css);
  }

  public LaddaAjaxLink<Object> creaLinkDettaglioIstanza(Istanza istanza) {
    LaddaAjaxLink<Object> btnDettaglioIstanza =
        new LaddaAjaxLink<Object>("btnDettaglioIstanza", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieIstanzePanel.this);
            // build istanzepage
            // setResponsePage(new DettaglioIstanzaPage(istanza));

          }
        };
    btnDettaglioIstanza.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieIstanzePanel.concludiIstanza", MieIstanzePanel.this)));
    btnDettaglioIstanza.setVisible(false);
    return btnDettaglioIstanza;
  }

  public LaddaAjaxLink<Object> creaLinkConcludiIstanza(Istanza istanza) {
    LaddaAjaxLink<Object> linkConcludiIstanza =
        new LaddaAjaxLink<Object>("btnConcludiIstanza", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(MieIstanzePanel.this);
            setResponsePage(new GestisciIstanzaPLPage(istanza));
          }
        };
    linkConcludiIstanza.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieIstanzePanel.concludiIstanza", MieIstanzePanel.this)));

    linkConcludiIstanza.setVisible(
        !UtilIstanze.isIstanzaRateizzazione(istanza)
            && istanza.getStatoCodice().equalsIgnoreCase(StatoIstanza.IN_COMPILAZIONE.toString()));

    return linkConcludiIstanza;
  }

  private LaddaAjaxLink<Object> creaLinkIntegraIstanza(Istanza istanza) {
    LaddaAjaxLink<Object> linkIntegraIstanza =
        new LaddaAjaxLink<Object>("btnIntegraIstanza", Type.Primary) {

          private static final long serialVersionUID = -1L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new GestisciIstanzaPLPage(istanza));
          }
        };
    linkIntegraIstanza.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieIstanzePanel.integraIstanza", MieIstanzePanel.this)));

    linkIntegraIstanza.setVisible(
        !UtilIstanze.isIstanzaRateizzazione(istanza)
            && istanza.getStatoCodice().equals(StatoIstanza.ATTESA_DI_INTEGRAZIONE.toString()));

    return linkIntegraIstanza;
  }

  private LaddaAjaxLink<Object> creaButtonCompletaIstanzaRateizzazione(Istanza istanza) {

    log.debug("[creaButtonCompletaIstanzaRateizzazione] Istanza da Completare" + istanza);

    LaddaAjaxLink<Object> btnCompletaIstanzaRateizzazione =
        (LaddaAjaxLink<Object>)
            new LaddaAjaxLink<Object>("btnCompletaIstanzaRateizzazione", Type.Primary) {

              /** */
              private static final long serialVersionUID = 1L;

              @Override
              public void onClick(AjaxRequestTarget arg0) {
                setResponsePage(new RichiestaRateizzazionePage(2, istanza));
              }
            };

    log.debug(
        "[creaButtonCompletaIstanzaRateizzazione] Codice Stato Istanza: "
            + istanza.getStatoCodice());
    boolean isIstanzaIncompleted =
        istanza.getStatoCodice().equalsIgnoreCase(StatoIstanza.ATTESA_DI_INTEGRAZIONE.toString())
            && UtilIstanze.isIstanzaRateizzazione(istanza);

    log.debug(
        "[creaButtonCompletaIstanzaRateizzazione] Istanza da Completare:  " + isIstanzaIncompleted);

    btnCompletaIstanzaRateizzazione.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("MieIstanzePanel.completaIstanzaRatezzazione", MieIstanzePanel.this)));
    // forse meglio brasare false
    btnCompletaIstanzaRateizzazione.setVisible(isIstanzaIncompleted);

    return btnCompletaIstanzaRateizzazione;
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadRicevuta(FileAllegato ricevuta) {
    final AbstractResource fileResourceByte;

    String nomeFile = ricevuta.getNomeFile();

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = 9104653345391693746L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {
              r.setFileName(nomeFile);
              r.setContentType("application/pdf");
              r.setContentDisposition(ContentDisposition.INLINE);
              r.setContentLength(ricevuta.getFile().length);
              r.setWriteCallback(
                  new WriteCallback() {
                    @Override
                    public void writeData(final Attributes attributes) {
                      attributes.getResponse().write(ricevuta.getFile());
                    }
                  });

              r.disableCaching();
            } catch (final Exception e) {
              log.error("Errore durante scarico pdf ricevuta dpp conducente");
            }

            return r;
          }
        };

    final ResourceLink linkFile =
        new ResourceLink("btnDownload", fileResourceByte) {

          private static final long serialVersionUID = 5259836759618558252L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };

    linkFile.setVisible(LabelFdCUtil.checkIfNotNull(ricevuta));

    return linkFile;
  }

  private void controlloSeVerbalePresenteInAnagraficaOBolloDelLoggato(Verbale verbale) {
    try {
      DettaglioVerbale dettagliVerbale =
          ServiceLocator.getInstance()
              .getServiziMieiVerbali()
              .getDettaglioVerbale(verbale.getNumeroProtocollo(), getUtente());

      AnagraficaNotifica anagraficaDelLoggato =
          UtilVerbaliDataNotifica.getAnagraficaUtente(dettagliVerbale, getUtente());

      String targa = verbale.getTarga();
      LocalDate dataAccertamento = verbale.getDataAccertamento();

      Veicolo veicoloDiProprieta = null;
      if (LabelFdCUtil.checkIfNotNull(getUtente().getListaVeicoliAttivi())) {
        veicoloDiProprieta =
            getUtente().getListaVeicoliAttivi().stream()
                .filter(
                    elem ->
                        PageUtil.isStringValid(elem.getTarga())
                            && elem.getTarga().equalsIgnoreCase(targa)
                            && PageUtil.isStringValid(elem.getDataInizioProprieta())
                            && (LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(
                                        elem.getDataInizioProprieta())
                                    .isAfter(dataAccertamento)
                                || LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(
                                        elem.getDataInizioProprieta())
                                    .isEqual(dataAccertamento)))
                .findAny()
                .orElse(null);
      }

      log.debug("CP anagraficaDelLoggato = " + anagraficaDelLoggato);
      log.debug("CP veicoloDiProprieta = " + veicoloDiProprieta);

      if (LabelFdCUtil.checkIfNotNull(anagraficaDelLoggato)
          || LabelFdCUtil.checkIfNotNull(veicoloDiProprieta)) {
        setResponsePage(new DettaglioVerbaliPage(dettagliVerbale));
      } else {
        error(
            "Attenzione a questo verbale possono accedere solo le persone autorizzate dalla Polizia Locale.");
      }

    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("le Mie Istanze"));
    }
  }
}
