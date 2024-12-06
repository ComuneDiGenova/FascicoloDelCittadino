package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.ConfermaBibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoBibliotecheIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = 5553172470578126061L;

  private BibliotecheIscrizione bibliotecheIscrizione;

  private ComponenteNucleo componenteNucleo;

  private WebMarkupContainer containerDatiGenitore;

  public RiassuntoBibliotecheIscrizionePanel(String id) {
    super(id);
  }

  public RiassuntoBibliotecheIscrizionePanel(
      BibliotecheIscrizione bibliotecheIscrizione, ComponenteNucleo componenteNucleo) {
    super("riassuntoBibliotecheIscrizionePanel");

    this.bibliotecheIscrizione = bibliotecheIscrizione;

    setComponenteNucleo(componenteNucleo);

    createFeedBackPanel();
    fillDati(bibliotecheIscrizione);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(Object dati) {
    BibliotecheIscrizione bibliotecheIscrizione = (BibliotecheIscrizione) dati;

    containerDatiGenitore = new WebMarkupContainer("containerDatiGenitore");
    containerDatiGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(getComponenteNucleo())
            && !getComponenteNucleo()
                .getCodiceFiscale()
                .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore()));
    containerDatiGenitore.setOutputMarkupId(true);
    addOrReplace(containerDatiGenitore);

    NotEmptyLabel nome = new NotEmptyLabel("nome", bibliotecheIscrizione.getNome());
    addOrReplace(nome);

    NotEmptyLabel cognome = new NotEmptyLabel("cognome", bibliotecheIscrizione.getCognome());
    addOrReplace(cognome);

    NotEmptyLabel sesso = new NotEmptyLabel("sesso", bibliotecheIscrizione.getSesso());
    addOrReplace(sesso);

    NotEmptyLabel dataDiNascita =
        new NotEmptyLabel(
            "dataDiNascita",
            LocalDateUtil.getDataFormatoEuropeo(bibliotecheIscrizione.getDataNascita()));
    addOrReplace(dataDiNascita);

    NotEmptyLabel luogoDiNascita =
        new NotEmptyLabel("luogoDiNascita", bibliotecheIscrizione.getLuogoNascita());
    addOrReplace(luogoDiNascita);

    NotEmptyLabel indirizzoResidenza =
        new NotEmptyLabel("indirizzoResidenza", bibliotecheIscrizione.getIndirizzoResidenza());
    addOrReplace(indirizzoResidenza);

    NotEmptyLabel capResidenza =
        new NotEmptyLabel("capResidenza", bibliotecheIscrizione.getCapResidenza());
    addOrReplace(capResidenza);

    NotEmptyLabel cittaResidenza =
        new NotEmptyLabel("cittaResidenza", bibliotecheIscrizione.getCittaResidenza());
    addOrReplace(cittaResidenza);

    NotEmptyLabel provinciaResidenza =
        new NotEmptyLabel("provinciaResidenza", bibliotecheIscrizione.getProvinciaResidenza());
    addOrReplace(provinciaResidenza);

    NotEmptyLabel statoResidenza =
        new NotEmptyLabel("statoResidenza", bibliotecheIscrizione.getStatoResidenza());
    addOrReplace(statoResidenza);

    NotEmptyLabel numeroCI = new NotEmptyLabel("numeroCI", bibliotecheIscrizione.getNumeroCI());
    addOrReplace(numeroCI);

    NotEmptyLabel ciRilasciataDa =
        new NotEmptyLabel("ciRilasciataDa", bibliotecheIscrizione.getLuogoCI());
    addOrReplace(ciRilasciataDa);

    LocalDateLabel ciValidaDal =
        new LocalDateLabel("ciValidaDal", bibliotecheIscrizione.getDataRilascioCI());
    ciValidaDal.setVisible(bibliotecheIscrizione.getDataRilascioCI() != null);
    addOrReplace(ciValidaDal);

    LocalDateLabel ciValidaFinoAl =
        new LocalDateLabel("ciValidaFinoAl", bibliotecheIscrizione.getDataScadenzaCI());
    ciValidaFinoAl.setVisible(bibliotecheIscrizione.getDataScadenzaCI() != null);
    addOrReplace(ciValidaFinoAl);

    NotEmptyLabel nomeGenitore =
        new NotEmptyLabel("nomeGenitore", bibliotecheIscrizione.getNomeGenitore());
    containerDatiGenitore.addOrReplace(nomeGenitore);

    NotEmptyLabel cognomeGenitore =
        new NotEmptyLabel("cognomeGenitore", bibliotecheIscrizione.getCognomeGenitore());
    containerDatiGenitore.addOrReplace(cognomeGenitore);

    NotEmptyLabel numeroCIGenitore =
        new NotEmptyLabel("numeroCIGenitore", bibliotecheIscrizione.getNumeroCIGenitore());
    containerDatiGenitore.addOrReplace(numeroCIGenitore);

    NotEmptyLabel ciRilasciataDaGenitore =
        new NotEmptyLabel("ciRilasciataDaGenitore", bibliotecheIscrizione.getLuogoCIGenitore());
    containerDatiGenitore.addOrReplace(ciRilasciataDaGenitore);

    LocalDateLabel ciValidaDalGenitore =
        new LocalDateLabel(
            "ciValidaDalGenitore", bibliotecheIscrizione.getDataRilascioCIGenitore());
    ciValidaDalGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(bibliotecheIscrizione.getDataRilascioCIGenitore()));
    containerDatiGenitore.addOrReplace(ciValidaDalGenitore);

    LocalDateLabel ciValidaFinoAlGenitore =
        new LocalDateLabel(
            "ciValidaFinoAlGenitore", bibliotecheIscrizione.getDataScadenzaCIGenitore());
    ciValidaFinoAlGenitore.setVisible(
        LabelFdCUtil.checkIfNotNull(bibliotecheIscrizione.getDataScadenzaCIGenitore()));
    containerDatiGenitore.addOrReplace(ciValidaFinoAlGenitore);

    String emailToUpper = "";
    if (PageUtil.isStringValid(bibliotecheIscrizione.getEmail())) {
      emailToUpper = bibliotecheIscrizione.getEmail().toUpperCase();
    }
    NotEmptyLabel email = new NotEmptyLabel("email", emailToUpper);
    addOrReplace(email);

    NotEmptyLabel cellulare = new NotEmptyLabel("cellulare", bibliotecheIscrizione.getCellulare());
    addOrReplace(cellulare);

    CheckBox checkBoxPrivacy = new CheckBox("toggleDichiarazione", Model.of(true));
    checkBoxPrivacy.setVisible(true);
    addOrReplace(checkBoxPrivacy);
    NotEmptyLabel labelToggleDichiarazione =
        new NotEmptyLabel(
            "labelToggleDichiarazione",
            getString("RiassuntoBibliotecheIscrizionePanel.autorizzazioneDati"));
    addOrReplace(labelToggleDichiarazione);

    CheckBox checkBoxMail =
        new CheckBox("toggleMail", Model.of(bibliotecheIscrizione.isAutorizzazioneMail()));
    checkBoxMail.setVisible(true);
    addOrReplace(checkBoxMail);
    NotEmptyLabel labelToggleMail =
        new NotEmptyLabel(
            "labelToggleMail", getString("RiassuntoBibliotecheIscrizionePanel.autorizzazioneMail"));
    addOrReplace(labelToggleMail);

    String descrizioneCountry = "";
    if (LabelFdCUtil.checkIfNotNull(bibliotecheIscrizione.getAutocompleteCittadinanza())) {
      descrizioneCountry = bibliotecheIscrizione.getAutocompleteCittadinanza().getDscr();
    }
    NotEmptyLabel country = new NotEmptyLabel("country", descrizioneCountry);
    addOrReplace(country);

    LaddaAjaxLink<?> linkConfermaIscrizioneBiblioteche =
        new LaddaAjaxLink("confermaIscrizione", Type.Primary) {

          private static final long serialVersionUID = -238554867900147223L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());
            setResponsePage(new ConfermaBibliotecheIscrizionePage(bibliotecheIscrizione));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaIscrizioneBiblioteche.setLabel(
        Model.of(getString("RiassuntoBibliotecheIscrizionePanel.prosegui")));
    addOrReplace(linkConfermaIscrizioneBiblioteche);

    Link linkAnnulla =
        new Link("annulla") {

          private static final long serialVersionUID = -5304929801448771443L;

          @Override
          public void onClick() {
            setResponsePage(new BibliotecheIscrizionePage(getComponenteNucleo()));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    addOrReplace(linkAnnulla);

    Link linkIndietro =
        new Link("indietro") {

          private static final long serialVersionUID = -3507439977738214184L;

          @Override
          public void onClick() {
            setResponsePage(
                new BibliotecheIscrizionePage(bibliotecheIscrizione, getComponenteNucleo()));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    addOrReplace(linkIndietro);
  }

  public BibliotecheIscrizione getBibliotecheIscrizione() {
    return bibliotecheIscrizione;
  }

  public void setBibliotecheIscrizione(BibliotecheIscrizione bibliotecheIscrizione) {
    this.bibliotecheIscrizione = bibliotecheIscrizione;
  }

  public ComponenteNucleo getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(ComponenteNucleo componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }
}
