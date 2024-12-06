package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.portale.pojo.mieiverbali.RicercaVerbaleConducente;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.LocalDateLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.AnteprimaRicevutaDppCondCondPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.dppconducente.DichiarazionePuntiPatenteConducentePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class RiassuntoDichiarazionePuntiPatentePanel extends BasePanel {

  private static final long serialVersionUID = 1498848313870361614L;

  private RicercaVerbaleConducente ricercaVerbaleConducente;

  public RiassuntoDichiarazionePuntiPatentePanel(String id) {
    super(id);
  }

  public RiassuntoDichiarazionePuntiPatentePanel(
      RicercaVerbaleConducente ricercaVerbaleConducente) {
    super("ricercaVerbaleConducente");

    createFeedBackPanel();

    this.ricercaVerbaleConducente = ricercaVerbaleConducente;
    fillDati(ricercaVerbaleConducente);
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  public void fillDati(Object dati) {
    RicercaVerbaleConducente ricercaVerbaleConducente = (RicercaVerbaleConducente) dati;

    Label numeroAvviso = new Label("numeroAvviso", ricercaVerbaleConducente.getNumeroAvviso());
    numeroAvviso.setVisible(PageUtil.isStringValid(ricercaVerbaleConducente.getNumeroAvviso()));
    addOrReplace(numeroAvviso);

    LocalDateLabel dataVerbale =
        new LocalDateLabel("dataVerbale", ricercaVerbaleConducente.getDataVerbale());
    dataVerbale.setVisible(ricercaVerbaleConducente.getDataVerbale() != null);
    addOrReplace(dataVerbale);

    Label targa = new Label("targa", ricercaVerbaleConducente.getTarga());
    targa.setVisible(PageUtil.isStringValid(ricercaVerbaleConducente.getTarga()));
    addOrReplace(targa);

    Label nomeCognomeConducente =
        new Label(
            "nomeCognomeConducente",
            ricercaVerbaleConducente
                .getNomeConducente()
                .concat(" ")
                .concat(ricercaVerbaleConducente.getCognomeConducente()));
    nomeCognomeConducente.setVisible(
        PageUtil.isStringValid(
            ricercaVerbaleConducente
                .getNomeConducente()
                .concat(" ")
                .concat(ricercaVerbaleConducente.getCognomeConducente())));
    addOrReplace(nomeCognomeConducente);

    Label cfConducente =
        new Label("cfConducente", ricercaVerbaleConducente.getCodiceFiscaleConducente());
    cfConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getCodiceFiscaleConducente()));
    addOrReplace(cfConducente);

    Label natoaConducente =
        new Label(
            "natoaConducente",
            ricercaVerbaleConducente
                .getCittaNascitaConducente()
                .concat("(")
                .concat(ricercaVerbaleConducente.getProvinciaNascitaConducente())
                .concat(")"));
    natoaConducente.setVisible(
        PageUtil.isStringValid(
            ricercaVerbaleConducente
                .getCittaNascitaConducente()
                .concat("(")
                .concat(ricercaVerbaleConducente.getProvinciaNascitaConducente())
                .concat(")")));
    addOrReplace(natoaConducente);

    LocalDateLabel natoIlConducente =
        new LocalDateLabel("natoIlConducente", ricercaVerbaleConducente.getDataNascitaConducente());
    natoIlConducente.setVisible(ricercaVerbaleConducente.getDataNascitaConducente() != null);
    addOrReplace(natoIlConducente);

    Label cittaResidenzaConducente =
        new Label("cittaResidenzaConducente", ricercaVerbaleConducente.getCittaConducente());
    cittaResidenzaConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getCittaConducente()));
    addOrReplace(cittaResidenzaConducente);

    Label residenzaConducente =
        new Label("residenzaConducente", ricercaVerbaleConducente.getIndirizzoConducente());
    residenzaConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getIndirizzoConducente()));
    addOrReplace(residenzaConducente);

    Label emailConducente =
        new Label("emailConducente", ricercaVerbaleConducente.getEmailDichiarante());
    emailConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getEmailDichiarante()));
    addOrReplace(emailConducente);

    Label numeroPatenteConducente =
        new Label("numeroPatenteConducente", ricercaVerbaleConducente.getNumeroPatente());
    numeroPatenteConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getNumeroPatente()));
    addOrReplace(numeroPatenteConducente);

    Label categoriaPatenteConducente =
        new Label("categoriaPatenteConducente", ricercaVerbaleConducente.getCategoriaPatente());
    categoriaPatenteConducente.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getCategoriaPatente()));
    addOrReplace(categoriaPatenteConducente);

    Label patenteConducenteRilasciataDa =
        new Label(
            "patenteConducenteRilasciataDa", ricercaVerbaleConducente.getPatenteRilasciataDa());
    patenteConducenteRilasciataDa.setVisible(
        PageUtil.isStringValid(ricercaVerbaleConducente.getPatenteRilasciataDa()));
    addOrReplace(patenteConducenteRilasciataDa);

    LocalDateLabel patenteConducenteRilasciataIl =
        new LocalDateLabel(
            "patenteConducenteRilasciataIl", ricercaVerbaleConducente.getPatenteRilasciataIl());
    patenteConducenteRilasciataIl.setVisible(
        ricercaVerbaleConducente.getPatenteRilasciataIl() != null);
    addOrReplace(patenteConducenteRilasciataIl);

    LocalDateLabel patenteConducenteValidaFinoAl =
        new LocalDateLabel(
            "patenteConducenteValidaFinoAl", ricercaVerbaleConducente.getPatenteValidaFinoAl());
    patenteConducenteValidaFinoAl.setVisible(
        ricercaVerbaleConducente.getPatenteValidaFinoAl() != null);
    addOrReplace(patenteConducenteValidaFinoAl);

    /*
     * String nomeCognomeProprietarioValue = ""; if(ricercaVerbaleConducente
     * .getNomeProprietario() != null &&
     * ricercaVerbaleConducente.getCognomeProprietario() != null) {
     * nomeCognomeProprietarioValue = ricercaVerbaleConducente
     * .getNomeProprietario().concat(" ").concat(ricercaVerbaleConducente.
     * getCognomeProprietario()); } Label nomeCognomeProprietario = new
     * Label("nomeCognomeProprietario", nomeCognomeProprietarioValue);
     * nomeCognomeProprietario.setVisible(PageUtil.isStringValid(
     * ricercaVerbaleConducente.getNomeProprietario()) &&
     * PageUtil.isStringValid(ricercaVerbaleConducente.
     * getCognomeProprietario())); addOrReplace(nomeCognomeProprietario);
     *
     * String cfProprietarioValue = "";
     * if(ricercaVerbaleConducente.getCodiceFiscaleProprietario() != null) {
     * cfProprietarioValue =
     * ricercaVerbaleConducente.getCodiceFiscaleProprietario(); } Label
     * cfProprietario = new Label("cfProprietario", cfProprietarioValue);
     * cfProprietario.setVisible(PageUtil.isStringValid(
     * ricercaVerbaleConducente.getCodiceFiscaleProprietario()));
     * addOrReplace(cfProprietario);
     *
     * String pivaValue = ""; if(ricercaVerbaleConducente.getPiva() != null)
     * { pivaValue = ricercaVerbaleConducente.getPiva(); } Label piva = new
     * Label("piva", pivaValue);
     * piva.setVisible(PageUtil.isStringValid(ricercaVerbaleConducente.
     * getPiva())); addOrReplace(piva);
     */

    CheckBox checkBox =
        new CheckBox(
            "toggleDichiarazione", Model.of(ricercaVerbaleConducente.isToggleDichiarazione()));
    checkBox.setVisible(true);
    addOrReplace(checkBox);

    Label uploadPatente = new Label("uploadPatente", ricercaVerbaleConducente.getNomeFilePatente());
    uploadPatente.setVisible(PageUtil.isStringValid(ricercaVerbaleConducente.getNomeFilePatente()));
    addOrReplace(uploadPatente);

    Link<?> linkAnnulla =
        new Link("annulla") {

          private static final long serialVersionUID = 2770089981514340426L;

          @Override
          public void onClick() {
            setResponsePage(new DichiarazionePuntiPatenteConducentePage());
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };

    addOrReplace(linkAnnulla);

    Link linkIndietro =
        new Link("indietro") {

          private static final long serialVersionUID = 7821634722741982980L;

          @Override
          public void onClick() {
            setResponsePage(new DichiarazionePuntiPatenteConducentePage(ricercaVerbaleConducente));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    addOrReplace(linkIndietro);

    LaddaAjaxLink<?> linkConfermaDPP =
        new LaddaAjaxLink("confermaDpp", Type.Primary) {

          private static final long serialVersionUID = 2466060127718420908L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(getPage());

            setResponsePage(new AnteprimaRicevutaDppCondCondPage(ricercaVerbaleConducente));
          }

          @Override
          public MarkupContainer setDefaultModel(IModel model) {
            return setDefaultModel(model);
          }
        };
    linkConfermaDPP.setLabel(
        Model.of(getString("RiassuntoDichiarazionePuntiPatentePanel.prosegui")));
    addOrReplace(linkConfermaDPP);
  }

  public RicercaVerbaleConducente getRicercaVerbaleConducente() {
    return ricercaVerbaleConducente;
  }

  public void setRicercaVerbaleConducente(RicercaVerbaleConducente ricercaVerbaleConducente) {
    this.ricercaVerbaleConducente = ricercaVerbaleConducente;
  }
}
