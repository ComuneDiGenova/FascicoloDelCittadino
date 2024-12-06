package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.RevocaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.pojo.UtenteServiziRistorazioneDieteSpeciali;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.AnnoScolastico;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale.StatoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpecialeValida.TipoMenuEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.FileAllegato;
import it.liguriadigitale.ponmetro.serviziristorazione.model.GiornoRientro;
import it.liguriadigitale.ponmetro.serviziristorazione.model.IdentificativoPdf;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class DieteSpecialiPanel extends BasePanel {

  private static final long serialVersionUID = 5088795241310282734L;

  private static final String ICON_DIETA = "color-orange col-3 icon-menu";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  protected UtenteServiziRistorazione iscritto;

  private List<DatiDietaSpeciale> listaDieteSpeciali;

  public DieteSpecialiPanel(String id) {
    super(id);
  }

  public DieteSpecialiPanel(UtenteServiziRistorazione iscritto) {
    super("dieteSpecialiPanel");
    this.iscritto = iscritto;
    createFeedBackPanel();
    fillDati(iscritto);

    setOutputMarkupId(true);
  }

  public DieteSpecialiPanel(
      UtenteServiziRistorazione iscritto, List<DatiDietaSpeciale> listaDieteSpeciali) {
    super("dieteSpecialiPanel");
    this.iscritto = iscritto;
    createFeedBackPanel();
    fillDati(listaDieteSpeciali);

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DatiDietaSpeciale> listaDiete = (List<DatiDietaSpeciale>) dati;

    setListaDieteSpeciali(listaDiete);

    Label listaVuota =
        new Label(
            "listaVuota",
            new StringResourceModel("DieteSpecialiPanel.listaVuota", this)
                .setParameters(iscritto.getNome()));
    listaVuota.setVisible(listaDiete.isEmpty());
    addOrReplace(listaVuota);

    PageableListView<DatiDietaSpeciale> listView =
        new PageableListView<DatiDietaSpeciale>("box", listaDiete, 4) {

          private static final long serialVersionUID = -2517699305863903256L;

          @Override
          protected void populateItem(ListItem<DatiDietaSpeciale> item) {

            final DatiDietaSpeciale dieta = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaDieta());
            icona.add(getColoreIconaDieta(dieta));
            item.addOrReplace(icona);

            Label identificativo = new Label("identificativo", dieta.getIdentificativo());
            identificativo.setVisible(PageUtil.isStringValid(dieta.getIdentificativo()));
            item.addOrReplace(identificativo);

            Label cfIscritto = new Label("cfIscritto", dieta.getCodiceFiscaleIscritto());
            cfIscritto.setVisible(PageUtil.isStringValid(dieta.getCodiceFiscaleIscritto()));
            item.addOrReplace(cfIscritto);

            Label nomeIscritto = new Label("nomeIscritto", dieta.getNomeIscritto());
            nomeIscritto.setVisible(PageUtil.isStringValid(dieta.getNomeIscritto()));
            item.addOrReplace(nomeIscritto);

            Label cognomeIscritto = new Label("cognomeIscritto", dieta.getCognomeIscritto());
            cognomeIscritto.setVisible(PageUtil.isStringValid(dieta.getCognomeIscritto()));
            item.addOrReplace(cognomeIscritto);

            Label nomeRichiedente = new Label("nomeRichiedente", dieta.getNomeRichiedente());
            nomeRichiedente.setVisible(PageUtil.isStringValid(dieta.getNomeRichiedente()));
            item.addOrReplace(nomeRichiedente);

            Label cognomeRichiedente =
                new Label("cognomeRichiedente", dieta.getCognomeRichiedente());
            cognomeRichiedente.setVisible(PageUtil.isStringValid(dieta.getCognomeRichiedente()));
            item.addOrReplace(cognomeRichiedente);

            Label cfRichiedente = new Label("cfRichiedente", dieta.getCodiceFiscaleRichiedente());
            cfRichiedente.setVisible(PageUtil.isStringValid(dieta.getCodiceFiscaleRichiedente()));
            item.addOrReplace(cfRichiedente);

            Label nomeRichiedenteRevoca =
                new Label("nomeRichiedenteRevoca", dieta.getNomeRichiedenteRevoca());
            nomeRichiedenteRevoca.setVisible(
                PageUtil.isStringValid(dieta.getNomeRichiedenteRevoca()));
            item.addOrReplace(nomeRichiedenteRevoca);

            Label cognomeRichiedenteRevoca =
                new Label("cognomeRichiedenteRevoca", dieta.getCodiceFiscaleRichiedenteRevoca());
            cognomeRichiedenteRevoca.setVisible(
                PageUtil.isStringValid(dieta.getCognomeRichiedenteRevoca()));
            item.addOrReplace(cognomeRichiedenteRevoca);

            Label cfRichiedenteRevoca =
                new Label("cfRichiedenteRevoca", dieta.getCodiceFiscaleRichiedenteRevoca());
            cfRichiedenteRevoca.setVisible(
                PageUtil.isStringValid(dieta.getCodiceFiscaleRichiedenteRevoca()));
            item.addOrReplace(cfRichiedenteRevoca);

            Label tipoDieta = new Label("tipoDieta", dieta.getTipoDieta());
            tipoDieta.setVisible(PageUtil.isStringValid(dieta.getTipoDieta()));
            item.addOrReplace(tipoDieta);

            String descrizioneDietaMotiviReligiosi = "";
            if (LabelFdCUtil.checkIfNotNull(dieta.getDietaMotiviReligiosi())) {
              descrizioneDietaMotiviReligiosi = dieta.getDietaMotiviReligiosi().getDescrizione();
            }
            Label dietaMotiviReligiosi =
                new Label("dietaMotiviReligiosi", descrizioneDietaMotiviReligiosi);
            dietaMotiviReligiosi.setVisible(
                LabelFdCUtil.checkIfNotNull(dieta.getDietaMotiviReligiosi())
                    && PageUtil.isStringValid(dieta.getDietaMotiviReligiosi().getDescrizione()));
            item.addOrReplace(dietaMotiviReligiosi);

            String valoreAnafilassi =
                LabelFdCUtil.checkIfNotNull(dieta.getAnafilassi()) && dieta.getAnafilassi()
                    ? "Sì"
                    : "No";
            Label anafilassi = new Label("anafilassi", valoreAnafilassi);
            anafilassi.setVisible(
                LabelFdCUtil.checkIfNotNull(dieta.getAnafilassi()) && dieta.getAnafilassi());
            item.addOrReplace(anafilassi);

            Label codiceScuola = new Label("codiceScuola", dieta.getCodiceScuola());
            codiceScuola.setVisible(PageUtil.isStringValid(dieta.getCodiceScuola()));
            item.addOrReplace(codiceScuola);

            Label descrizioneScuola =
                new Label("descrizioneScuola", getDescrizioneScuola(dieta.getCodiceScuola()));
            descrizioneScuola.setVisibilityAllowed(
                PageUtil.isStringValid(getDescrizioneScuola(dieta.getCodiceScuola())));
            item.addOrReplace(descrizioneScuola);

            Label annoScolastico =
                new Label(
                    "annoScolastico", getDescrizioneAnnoScolastico(dieta.getAnnoScolastico()));
            annoScolastico.setVisibilityAllowed(
                PageUtil.isStringValid(getDescrizioneAnnoScolastico(dieta.getAnnoScolastico())));
            item.addOrReplace(annoScolastico);

            Label classe = new Label("classe", dieta.getClasse());
            classe.setVisible(
                LabelFdCUtil.checkIfNotNull(dieta.getClasse()) && dieta.getClasse() != 0);
            item.addOrReplace(classe);

            Label sezione = new Label("sezione", dieta.getSezione());
            sezione.setVisible(PageUtil.isStringValid(dieta.getSezione()));
            item.addOrReplace(sezione);

            String rientro = "";
            List<GiornoRientro> listaRientri = dieta.getGiorniRientro();
            if (LabelFdCUtil.checkIfNotNull(listaRientri)
                && !LabelFdCUtil.checkEmptyList(listaRientri)) {
              for (GiornoRientro elem : listaRientri) {
                rientro = rientro.concat(elem.getRientro()).concat("\n");
              }
            }
            MultiLineLabel rientri = new MultiLineLabel("rientri", rientro);
            rientri.setVisible(
                LabelFdCUtil.checkIfNotNull(dieta.getGiorniRientro())
                    && !LabelFdCUtil.checkEmptyList(listaRientri));
            rientri.setEscapeModelStrings(false);
            item.addOrReplace(rientri);

            Label noteIntegrative = new Label("noteIntegrative", dieta.getNoteIntegrative());
            noteIntegrative.setVisible(PageUtil.isStringValid(dieta.getNoteIntegrative()));
            item.addOrReplace(noteIntegrative);

            Label noteBackoffice = new Label("noteBackoffice", dieta.getNoteBackoffice());
            noteBackoffice.setVisible(PageUtil.isStringValid(dieta.getNoteBackoffice()));
            item.addOrReplace(noteBackoffice);

            LocalDateLabel dataRichiesta =
                new LocalDateLabel("dataRichiesta", dieta.getDataRichiesta());
            dataRichiesta.setVisible(LabelFdCUtil.checkIfNotNull(dieta.getDataRichiesta()));
            item.addOrReplace(dataRichiesta);

            Label statoRichiesta = new Label("statoRichiesta", dieta.getStato());
            noteBackoffice.setVisible(PageUtil.isStringValid(dieta.getStato()));
            item.addOrReplace(statoRichiesta);

            LocalDateLabel dataInizio = new LocalDateLabel("dataInizio", dieta.getDataInizio());
            dataInizio.setVisible(LabelFdCUtil.checkIfNotNull(dieta.getDataInizio()));
            item.addOrReplace(dataInizio);

            LocalDateLabel dataFine = new LocalDateLabel("dataFine", dieta.getDataFine());
            dataFine.setVisible(LabelFdCUtil.checkIfNotNull(dieta.getDataFine()));
            item.addOrReplace(dataFine);

            item.addOrReplace(scaricaFile("btnPdfDomanda", dieta.getDomanda()));

            item.addOrReplace(scaricaFile("btnPdfDomandaRevoca", dieta.getDomandaRevoca()));

            item.addOrReplace(scaricaFile("btnPdfDieta", dieta.getDieta()));

            item.addOrReplace(
                scaricaFile("btnPdfDichiarazioneSanitaria", dieta.getDichiarazioneSanitaria()));

            item.addOrReplace(creaBtnRevocaDieta(dieta));
          }

          private AttributeAppender getCssIconaDieta() {
            String css = ICON_DIETA;
            return new AttributeAppender("class", " " + css);
          }

          private AttributeModifier getColoreIconaDieta(DatiDietaSpeciale dietaSpeciale) {
            AttributeModifier coloreIcona = new AttributeModifier("style", "color: #000000");

            if (dietaSpeciale.getStato().equalsIgnoreCase(StatoEnum.IN_ELABORAZIONE.value())
                || dietaSpeciale
                    .getStato()
                    .equalsIgnoreCase(StatoEnum.REVOCA_IN_ELABORAZIONE.value())) {
              coloreIcona = new AttributeModifier("style", "color: #a66300");
            } else if (dietaSpeciale.getStato().equalsIgnoreCase(StatoEnum.ACCETTATA.value())) {
              coloreIcona = new AttributeModifier("style", "color: #008758");
            } else if (dietaSpeciale.getStato().equalsIgnoreCase(StatoEnum.RESPINTA.value())) {
              coloreIcona = new AttributeModifier("style", "color: #d9364f");
            } else if (dietaSpeciale.getStato().equalsIgnoreCase(StatoEnum.REVOCATA.value())) {
              coloreIcona = new AttributeModifier("style", "color: #5c6f82");
            }
            return coloreIcona;
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(!listaDiete.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(listaDiete.size() > 4);
    addOrReplace(paginazioneFascicolo);

    UtenteServiziRistorazioneDieteSpeciali iscrittoDiete =
        new UtenteServiziRistorazioneDieteSpeciali();
    iscrittoDiete.setIscritto(iscritto);

    DatiDietaSpecialeValida dietaSpecialeValida =
        getDietaSpecialeValida(iscritto.getCodiceFiscale());
    log.debug("CP dietaSpecialeValida = " + dietaSpecialeValida);
    iscrittoDiete.setDietaValida(dietaSpecialeValida);

    LinkDinamicoLaddaFunzione<Object> btnRichiestaDieta =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnRichiestaDieta",
            new LinkDinamicoFunzioneData(
                "DieteSpecialiPanel.btnRichiestaDieta",
                "RichiestaDietaSpecialePage",
                "DieteSpecialiPanel.btnRichiestaDieta"),
            iscrittoDiete,
            DieteSpecialiPanel.this,
            "color-orange col-auto icon-menu");
    addOrReplace(btnRichiestaDieta);

    WebMarkupContainer containerDietaValida = new WebMarkupContainer("containerDietaValida");
    if (LabelFdCUtil.checkIfNotNull(iscrittoDiete.getDietaValida())) {

      WebMarkupContainer containerInfoDietaValida =
          new WebMarkupContainer("containerInfoDietaValida");
      containerInfoDietaValida.setOutputMarkupId(true);
      containerInfoDietaValida.setOutputMarkupPlaceholderTag(true);
      containerInfoDietaValida.setVisible(
          iscrittoDiete
              .getDietaValida()
              .getTipoMenu()
              .equalsIgnoreCase(TipoMenuEnum.MEN_ETICO_RELIGIOSO.value()));
      containerDietaValida.addOrReplace(containerInfoDietaValida);

      Label idDietaValida = new Label("idDietaValida", iscrittoDiete.getDietaValida().getIdDieta());
      idDietaValida.setVisible(PageUtil.isStringValid(iscrittoDiete.getDietaValida().getIdDieta()));
      containerDietaValida.addOrReplace(idDietaValida);

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "codiceFiscaleValida",
              iscrittoDiete.getDietaValida().getCodiceFiscale(),
              DieteSpecialiPanel.this));

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "nomeValida", iscrittoDiete.getDietaValida().getNome(), DieteSpecialiPanel.this));

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "cognomeValida",
              iscrittoDiete.getDietaValida().getCognome(),
              DieteSpecialiPanel.this));

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "tipoMenuValida",
              iscrittoDiete.getDietaValida().getTipoMenu(),
              DieteSpecialiPanel.this));

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "tipoDietaValida",
              iscrittoDiete.getDietaValida().getTipoDieta(),
              DieteSpecialiPanel.this));

      containerDietaValida.addOrReplace(
          new AmtCardLabel<>(
              "annoScolasticoValida",
              iscrittoDiete.getDietaValida().getAnnoScolastico(),
              DieteSpecialiPanel.this));
    }
    containerDietaValida.setOutputMarkupId(true);
    containerDietaValida.setOutputMarkupPlaceholderTag(true);
    containerDietaValida.setVisible(LabelFdCUtil.checkIfNotNull(iscrittoDiete.getDietaValida()));
    addOrReplace(containerDietaValida);
  }

  private Component scaricaFile(String wicketId, final IdentificativoPdf identificativoPdf) {

    ResourceLink<?> linkFile = downloadPdfImage(wicketId, identificativoPdf);

    boolean isBtnVisibile = LabelFdCUtil.checkIfNotNull(identificativoPdf);
    if (LabelFdCUtil.checkIfNotNull(linkFile)) {
      linkFile.setVisible(isBtnVisibile);
      return linkFile;
    } else {
      WebMarkupContainer btnWicketId = new WebMarkupContainer(wicketId);
      btnWicketId.setVisible(false);
      addOrReplace(btnWicketId);
      return btnWicketId;
    }
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadPdfImage(
      String wicketId, final IdentificativoPdf identificativoPdf) {

    final AbstractResource fileResourceByte;
    ResourceLink linkFile = null;

    try {
      if (LabelFdCUtil.checkIfNotNull(identificativoPdf)
          && LabelFdCUtil.checkIfNotNull(identificativoPdf.getId())
          && LabelFdCUtil.checkIfNotNull(identificativoPdf.getTipoDocumento())) {

        FileAllegato fileAllegato =
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getFilePdfDieteSpeciali(identificativoPdf);

        fileResourceByte =
            new AbstractResource() {

              private static final long serialVersionUID = 4259027535467070956L;

              @Override
              protected ResourceResponse newResourceResponse(final Attributes attributes) {
                final ResourceResponse r = new ResourceResponse();
                try {

                  if (fileAllegato.getEstensioneFile().equalsIgnoreCase("pdf")) {
                    r.setFileName(fileAllegato.getNomeFile());
                    r.setContentType(fileAllegato.getMimeType());
                    r.setContentDisposition(ContentDisposition.INLINE);
                    r.setContentLength(fileAllegato.getFile().length);
                    r.setWriteCallback(
                        new WriteCallback() {
                          @Override
                          public void writeData(final Attributes attributes) {
                            attributes.getResponse().write(fileAllegato.getFile());
                          }
                        });
                  } else {
                    r.setFileName(fileAllegato.getNomeFile());
                    r.setContentType(fileAllegato.getMimeType());
                    r.setContentDisposition(ContentDisposition.INLINE);
                    r.setContentLength(fileAllegato.getFile().length);
                    r.setWriteCallback(
                        new WriteCallback() {
                          @Override
                          public void writeData(final Attributes attributes) {
                            attributes.getResponse().write(fileAllegato.getFile());
                          }
                        });
                  }

                  r.disableCaching();
                } catch (final Exception e) {
                  log.error("Errore durante scarico pdf allegato");
                }

                return r;
              }
            };

        linkFile =
            new ResourceLink(wicketId, fileResourceByte) {

              private static final long serialVersionUID = 136933642391833376L;

              @Override
              protected void onComponentTag(final ComponentTag tag) {
                super.onComponentTag(tag);
                tag.put("target", "_blank");
                tag.put("title", "scarica FILE: " + fileAllegato.getNomeFile());
              }
            };
      }
    } catch (ApiException | BusinessException e) {
      log.error("Errore durante download allegato diete: " + e.getMessage());
    }

    return linkFile;
  }

  private LaddaAjaxLink<Object> creaBtnRevocaDieta(DatiDietaSpeciale dieta) {
    LaddaAjaxLink<Object> btnRevocaDieta =
        new LaddaAjaxLink<Object>("btnRevocaDieta", Type.Primary) {

          private static final long serialVersionUID = 8325307411204295593L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DieteSpecialiPanel.this);

            Integer index = 1;
            setResponsePage(new RevocaDietaSpecialePage(dieta, iscritto, index));
          }
        };
    btnRevocaDieta.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("DieteSpecialiPanel.revocaDieta", DieteSpecialiPanel.this)));

    btnRevocaDieta.setVisible(
        dieta.getStato().equalsIgnoreCase(StatoEnum.IN_ELABORAZIONE.value())
            || dieta.getStato().equalsIgnoreCase(StatoEnum.ACCETTATA.value()));

    IconType iconType =
        new IconType("btnRevocaDieta") {

          private static final long serialVersionUID = -7180107646697060974L;

          @Override
          public String cssClassName() {
            return "icon-no";
          }
        };

    btnRevocaDieta.setIconType(iconType);

    return btnRevocaDieta;
  }

  private String getDescrizioneScuola(String codiceScuola) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getDescrizioneScuolaDaCodiceScuola(codiceScuola);
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private String getDescrizioneAnnoScolastico(AnnoScolastico as) {
    String annoScolastico = "";
    if (LabelFdCUtil.checkIfNotNull(as)) {
      annoScolastico = as.getDescrizione();
    }
    return annoScolastico;
  }

  public List<DatiDietaSpeciale> getListaDieteSpeciali() {
    return listaDieteSpeciali;
  }

  public void setListaDieteSpeciali(List<DatiDietaSpeciale> listaDieteSpeciali) {
    this.listaDieteSpeciali = listaDieteSpeciali;
  }

  private DatiDietaSpecialeValida getDietaSpecialeValida(String codiceFiscaleIscritto) {
    try {
      return ServiceLocator.getInstance()
          .getServiziRistorazione()
          .getDietaSpecialeValida(codiceFiscaleIscritto);
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API dieta speciale valida: ");
      return null;
    }
  }
}
