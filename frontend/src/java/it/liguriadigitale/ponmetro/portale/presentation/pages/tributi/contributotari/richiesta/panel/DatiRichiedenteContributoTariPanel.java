package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.richiesta.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCModalitaPagamentoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoPressoTesoreriaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.FdCPagamentoSuContoCorrentePanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.modalitapagamento.ModalitaPagamentoTariNetribeContributoRender;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.FileUploadValidator;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiModalitaPagamentoAgevolazioneTariffariaTari.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.validation.validator.PatternValidator;

public class DatiRichiedenteContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = 6853290889161248784L;

  private int index;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoPressoTesoreriaPanel ritiroPressoTesoreriaPanel;

  @SuppressWarnings("rawtypes")
  private FdCPagamentoSuContoCorrentePanel accreditoSuContoCorrentePanel;

  private FileUploadField allegatoContributoUpload;

  private String nomeAllegatoUpload;

  private byte[] byteAllegatoUpload;

  private Form<?> formUploadAllegati;

  private WebMarkupContainer containerAllegatiUpload;

  private WebMarkupContainer containerUploadAllegati =
      new WebMarkupContainer("containerUploadAllegati");

  public DatiRichiedenteContributoTariPanel(
      String id, DatiDomandaContributoTari datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    setIndex(index);

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    DatiDomandaContributoTari datiDomanda = (DatiDomandaContributoTari) dati;

    addOrReplace(
        new FdCTitoloPanel("titolo", getString("DatiRichiedenteContributoTariPanel.titolo")));

    FdCTextField nome =
        new FdCTextField(
            "nome",
            new PropertyModel(datiDomanda, "nome"),
            DatiRichiedenteContributoTariPanel.this);
    nome.getTextField().setRequired(true);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome",
            new PropertyModel(datiDomanda, "cognome"),
            DatiRichiedenteContributoTariPanel.this);
    cognome.getTextField().setRequired(true);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale",
            new PropertyModel(datiDomanda, "codiceFiscale"),
            DatiRichiedenteContributoTariPanel.this);
    codiceFiscale.getTextField().setRequired(true);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(datiDomanda, "email"),
            DatiRichiedenteContributoTariPanel.this);
    email.getTextField().setRequired(true);
    addOrReplace(email);

    FdCTextField cellulare =
        new FdCTextField(
            "cellulare",
            new PropertyModel(datiDomanda, "cellulare"),
            DatiRichiedenteContributoTariPanel.this);
    cellulare.setMarkupId(cellulare.getTextField().getMarkupId());
    cellulare.getTextField().add(new PatternValidator(MetaPattern.DIGITS));
    cellulare.getTextField().setRequired(true);
    cellulare.setMarkupId("cellulare");
    addOrReplace(cellulare);

    accreditoSuContoCorrentePanel =
        new FdCPagamentoSuContoCorrentePanel<>(
            "accreditoSuContoCorrentePanel", datiDomanda, false, true);
    accreditoSuContoCorrentePanel.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getModalitaDiPagamento())
            && PageUtil.isStringValid(datiDomanda.getModalitaDiPagamento().value())
            && datiDomanda.getModalitaDiPagamento().value().equalsIgnoreCase("CAB"));

    addOrReplace(accreditoSuContoCorrentePanel);

    ritiroPressoTesoreriaPanel =
        new FdCPagamentoPressoTesoreriaPanel<>("ritiroPressoTesoreriaPanel", datiDomanda, false);
    ritiroPressoTesoreriaPanel.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda)
            && LabelFdCUtil.checkIfNotNull(datiDomanda.getModalitaDiPagamento())
            && PageUtil.isStringValid(datiDomanda.getModalitaDiPagamento().value())
            && datiDomanda.getModalitaDiPagamento().value().equalsIgnoreCase("CAS"));
    addOrReplace(ritiroPressoTesoreriaPanel);

    List<ModalitaPagamentoEnum> listaModalitaPagamento =
        ServiceLocator.getInstance().getServiziContributoTari().getListaModalitaPagamento();

    FdCModalitaPagamentoDropDownChoice modalitaDiPagamento =
        new FdCModalitaPagamentoDropDownChoice(
            "modalitaDiPagamento",
            new PropertyModel(datiDomanda, "modalitaDiPagamento"),
            new ModalitaPagamentoTariNetribeContributoRender(),
            listaModalitaPagamento);

    modalitaDiPagamento.setRequired(true);
    modalitaDiPagamento.setLabel(Model.of("Modalità di pagamento"));
    addOrReplace(modalitaDiPagamento);

    modalitaDiPagamento.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 7948549185815378484L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            if (LabelFdCUtil.checkIfNotNull(modalitaDiPagamento)
                && LabelFdCUtil.checkIfNotNull(modalitaDiPagamento.getValue())) {

              boolean checkModalitaPagamentoContoCorrente =
                  modalitaDiPagamento.getValue().equalsIgnoreCase("CAB");

              if (checkModalitaPagamentoContoCorrente) {

                datiDomanda.setNomeDelegato(null);
                datiDomanda.setCognomeDelegato(null);
                datiDomanda.setCodiceFiscaleDelegato(null);

                datiDomanda.setFileAllegato(null);

                ritiroPressoTesoreriaPanel.getNomeDelegato().getTextField().setRequired(false);
                ritiroPressoTesoreriaPanel.getCognomeDelegato().getTextField().setRequired(false);
                ritiroPressoTesoreriaPanel
                    .getCodiceFiscaleDelegato()
                    .getTextField()
                    .setRequired(false);

                ritiroPressoTesoreriaPanel =
                    new FdCPagamentoPressoTesoreriaPanel<>(
                        "ritiroPressoTesoreriaPanel", datiDomanda, false);
                addOrReplace(ritiroPressoTesoreriaPanel);

              } else {

                datiDomanda.setIban(null);
                datiDomanda.setSwift(null);
                datiDomanda.setNomeDelegato(null);
                datiDomanda.setCognomeDelegato(null);
                datiDomanda.setCodiceFiscaleDelegato(null);

                datiDomanda.setFileAllegato(null);

                accreditoSuContoCorrentePanel =
                    new FdCPagamentoSuContoCorrentePanel<>(
                        "accreditoSuContoCorrentePanel", datiDomanda, false, true);
                addOrReplace(accreditoSuContoCorrentePanel);
              }

              ritiroPressoTesoreriaPanel
                  .getCodiceFiscaleDelegato()
                  .getTextField()
                  .add(
                      new AjaxFormComponentUpdatingBehavior("change") {

                        @Override
                        protected void onError(AjaxRequestTarget target, RuntimeException e) {
                          super.onError(target, e);
                        }

                        @Override
                        protected void onUpdate(AjaxRequestTarget targetCfDelegato) {

                          if (PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())
                              && !datiDomanda
                                  .getCodiceFiscaleDelegato()
                                  .equalsIgnoreCase(datiDomanda.getCodiceFiscale())) {
                            containerUploadAllegati.setVisible(true);
                          } else {
                            containerUploadAllegati.setVisible(false);
                            datiDomanda.setFileAllegato(null);
                          }

                          formUploadAllegati.getRootForm().setMultiPart(true);

                          formUploadAllegati.setMultiPart(true);

                          targetCfDelegato.add(containerUploadAllegati, formUploadAllegati);
                        }
                      });

              accreditoSuContoCorrentePanel
                  .getCodiceFiscaleDelegato()
                  .getTextField()
                  .add(
                      new AjaxFormComponentUpdatingBehavior("change") {

                        @Override
                        protected void onError(AjaxRequestTarget target, RuntimeException e) {
                          super.onError(target, e);
                        }

                        @Override
                        protected void onUpdate(AjaxRequestTarget targetCfDelegato) {

                          if (PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())
                              && !datiDomanda
                                  .getCodiceFiscaleDelegato()
                                  .equalsIgnoreCase(datiDomanda.getCodiceFiscale())) {
                            containerUploadAllegati.setVisible(true);
                          } else {
                            containerUploadAllegati.setVisible(false);
                            datiDomanda.setFileAllegato(null);
                          }

                          formUploadAllegati.getRootForm().setMultiPart(true);

                          formUploadAllegati.setMultiPart(true);

                          targetCfDelegato.add(containerUploadAllegati, formUploadAllegati);
                        }
                      });

              accreditoSuContoCorrentePanel.setVisible(checkModalitaPagamentoContoCorrente);
              ritiroPressoTesoreriaPanel.setVisible(!checkModalitaPagamentoContoCorrente);

              containerUploadAllegati.setVisible(
                  PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())
                      && !datiDomanda
                          .getCodiceFiscaleDelegato()
                          .equalsIgnoreCase(datiDomanda.getCodiceFiscale()));
            }

            target.add(
                accreditoSuContoCorrentePanel, ritiroPressoTesoreriaPanel, containerUploadAllegati);
          }
        });

    containerAllegatiUpload = new WebMarkupContainer("containerAllegatiUpload");
    containerAllegatiUpload.setVisible(false);
    containerAllegatiUpload.setOutputMarkupId(true);

    formUploadAllegati =
        new Form<Void>("formUploadAllegati") {

          @Override
          protected void onSubmit() {}
        };

    formUploadAllegati.add(
        new AjaxFormSubmitBehavior("change") {

          @Override
          protected void onAfterSubmit(AjaxRequestTarget target) {

            log.debug("CP onAfterSubmit= ");

            final FileUpload uploadedFile = allegatoContributoUpload.getFileUpload();
            if (uploadedFile != null) {

              setAllegatoContributoUpload(allegatoContributoUpload);
              setNomeAllegatoUpload(uploadedFile.getClientFileName());
              setByteAllegatoUpload(uploadedFile.getBytes());

              containerAllegatiUpload.setVisible(true);

              FileAllegato fileAllegato = new FileAllegato();
              fileAllegato.setFile(getByteAllegatoUpload());
              fileAllegato.setNomeFile(getNomeAllegatoUpload());
              try {
                fileAllegato.setMimeType(
                    FileFdCUtil.getMimeTypeFileUploadAllegato(getByteAllegatoUpload()));
                fileAllegato.setEstensioneFile(
                    FileFdCUtil.getEstensionFileUploadAllegato(getByteAllegatoUpload()));
              } catch (BusinessException | MagicMatchNotFoundException e) {
                log.error("Errore durante mimetype allegato tari contributo " + e.getMessage());
              }

              datiDomanda.setFileAllegato(fileAllegato);

              Label nomeFileAllegato = new Label("nomeFileAllegato", getNomeAllegatoUpload());
              containerAllegatiUpload.addOrReplace(nomeFileAllegato);

              Label dimensioneFileAllegato =
                  new Label(
                      "dimensioneFileAllegato",
                      FileFdCUtil.getSizeFile(getByteAllegatoUpload().length));
              containerAllegatiUpload.addOrReplace(dimensioneFileAllegato);

              AjaxButton eliminaAllegato =
                  new AjaxButton("eliminaAllegato") {

                    @Override
                    protected void onSubmit(AjaxRequestTarget targetElimina) {

                      datiDomanda.setFileAllegato(null);
                      setNomeAllegatoUpload(null);
                      setByteAllegatoUpload(null);

                      containerAllegatiUpload.setVisible(false);

                      targetElimina.add(containerAllegatiUpload);
                    }
                  };
              eliminaAllegato.setDefaultFormProcessing(false);
              containerAllegatiUpload.addOrReplace(eliminaAllegato);

              target.add(formUploadAllegati, containerAllegatiUpload);
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            log.error("Errore upload file tari contributo");

            super.onError(target);

            target.add(getPage());
          }
        });

    formUploadAllegati.setMultiPart(true);

    formUploadAllegati.setOutputMarkupId(true);
    formUploadAllegati.setOutputMarkupPlaceholderTag(true);

    formUploadAllegati.addOrReplace(containerAllegatiUpload);

    allegatoContributoUpload = new FileUploadField("allegatoContributoUpload");
    allegatoContributoUpload.setOutputMarkupId(true);
    allegatoContributoUpload.setOutputMarkupPlaceholderTag(true);
    allegatoContributoUpload.setLabel(Model.of("Upload documenti"));
    allegatoContributoUpload.setVisible(isEnabled());
    allegatoContributoUpload.add(new FileUploadValidator());

    formUploadAllegati.addOrReplace(allegatoContributoUpload);

    containerUploadAllegati.setOutputMarkupId(true);
    containerUploadAllegati.setOutputMarkupPlaceholderTag(true);

    containerUploadAllegati.setVisible(
        LabelFdCUtil.checkIfNotNull(datiDomanda.getFileAllegato())
            && PageUtil.isStringValid(datiDomanda.getCodiceFiscaleDelegato())
            && !datiDomanda
                .getCodiceFiscaleDelegato()
                .equalsIgnoreCase(datiDomanda.getCodiceFiscale()));

    addOrReplace(containerUploadAllegati);

    containerUploadAllegati.addOrReplace(formUploadAllegati);

    containerAllegatiUpload.setEnabled(isEnabled());

    formUploadAllegati.addOrReplace(containerAllegatiUpload);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public FileUploadField getAllegatoContributoUpload() {
    return allegatoContributoUpload;
  }

  public void setAllegatoContributoUpload(FileUploadField allegatoContributoUpload) {
    this.allegatoContributoUpload = allegatoContributoUpload;
  }

  public String getNomeAllegatoUpload() {
    return nomeAllegatoUpload;
  }

  public void setNomeAllegatoUpload(String nomeAllegatoUpload) {
    this.nomeAllegatoUpload = nomeAllegatoUpload;
  }

  public byte[] getByteAllegatoUpload() {
    return byteAllegatoUpload;
  }

  public void setByteAllegatoUpload(byte[] byteAllegatoUpload) {
    this.byteAllegatoUpload = byteAllegatoUpload;
  }

  public Form<?> getFormUploadAllegati() {
    return formUploadAllegati;
  }

  public void setFormUploadAllegati(Form<?> formUploadAllegati) {
    this.formUploadAllegati = formUploadAllegati;
  }

  public WebMarkupContainer getContainerAllegatiUpload() {
    return containerAllegatiUpload;
  }

  public void setContainerAllegatiUpload(WebMarkupContainer containerAllegatiUpload) {
    this.containerAllegatiUpload = containerAllegatiUpload;
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    formUploadAllegati.setVisible(isEnabled());
  }
}
