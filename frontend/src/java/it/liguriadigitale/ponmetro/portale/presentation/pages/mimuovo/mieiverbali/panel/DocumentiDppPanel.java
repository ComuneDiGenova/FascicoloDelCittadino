package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Allegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class DocumentiDppPanel extends BasePanel {

  private static final long serialVersionUID = -5778804594803298430L;

  private DettaglioVerbale dettaglioVerbale;

  private List<Allegato> listaDocumentiDpp;

  public DocumentiDppPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public DocumentiDppPanel(
      String id, DettaglioVerbale dettaglioVerbale, List<Allegato> listaDocumentiDpp) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;
    this.listaDocumentiDpp = listaDocumentiDpp;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Allegato> listaDocumentiDpp = (List<Allegato>) dati;

    Label titoloDocumentiDpp =
        new Label("titoloDocumentiDpp", getString("DocumentiDppPanel.titoloDocumentiDpp"));
    addOrReplace(titoloDocumentiDpp);

    ListView<Allegato> listViewDocumentiDpp =
        new ListView<Allegato>("box", listaDocumentiDpp) {

          private static final long serialVersionUID = 3988903639999245614L;

          @Override
          protected void populateItem(ListItem<Allegato> item) {
            Allegato allegatoItem = item.getModelObject();

            item.setOutputMarkupId(true);

            String descrizioneStatoCompleta = allegatoItem.getDescrizione();
            String[] descrizioneStatoCompletaSplitted =
                descrizioneStatoCompleta.split(Pattern.quote("*"));

            String descrizioneAllegato = descrizioneStatoCompletaSplitted[0];
            Label descrizione = new Label("descrizione", descrizioneAllegato);
            descrizione.setVisible(PageUtil.isStringValid(allegatoItem.getDescrizione()));
            item.addOrReplace(descrizione);

            AttributeModifier coloreStatoVerde = new AttributeModifier("style", "color: #008758");
            AttributeModifier coloreStatoGiallo = new AttributeModifier("style", "color: #a66300");
            AttributeModifier coloreStatoRosso = new AttributeModifier("style", "color: #d9364f");
            AttributeModifier coloreStatoGrigio = new AttributeModifier("style", "color: #5c6f82");
            AttributeModifier coloreStatoNero = new AttributeModifier("style", "color: #000");

            String statoDpp = descrizioneStatoCompletaSplitted[1];
            String statoDppTrim = statoDpp.trim();

            if (statoDppTrim.equalsIgnoreCase("inElaborazione")) {
              statoDpp = "In Elaborazione";
            }
            if (statoDppTrim.equalsIgnoreCase("nonPresente")) {
              statoDpp = "Non presente";
            }
            Label stato = new Label("stato", statoDpp.toUpperCase());
            stato.setVisible(PageUtil.isStringValid(allegatoItem.getDescrizione()));

            if (statoDppTrim.equalsIgnoreCase("accettata")) {
              stato.add(coloreStatoVerde);
            } else if (statoDppTrim.equalsIgnoreCase("inElaborazione")) {
              stato.add(coloreStatoGiallo);
            } else if (statoDppTrim.equalsIgnoreCase("rifiutata")) {
              stato.add(coloreStatoRosso);
            } else if (statoDppTrim.equalsIgnoreCase("nonPresente")) {
              stato.add(coloreStatoGrigio);
            } else {
              stato.add(coloreStatoNero);
            }

            item.addOrReplace(stato);

            WebMarkupContainer infoStato = new WebMarkupContainer("infoStato");
            infoStato.setVisible(statoDppTrim.equalsIgnoreCase("inElaborazione"));
            item.addOrReplace(infoStato);

            String dim = "";
            if (allegatoItem != null) {
              if (allegatoItem.getDimensione() != null) {
                Integer dimensioneByte = allegatoItem.getDimensione();
                String dimensioneFile = FileFdCUtil.getSizeFile(dimensioneByte);
                dim = String.valueOf(dimensioneFile);
              }
            }
            Label dimensione = new Label("dimensione", dim);
            dimensione.setVisible(
                allegatoItem.getDimensione() != null && allegatoItem.getDimensione() != 0);
            item.addOrReplace(dimensione);

            ResourceLink linkFile = null;

            String estensioneFile = "";
            String mimeType = "";

            if (allegatoItem.getId() != null && allegatoItem.getId() != 0.0) {
              FileAllegato fileAllegato = new FileAllegato();
              try {
                fileAllegato =
                    ServiceLocator.getInstance()
                        .getServiziMieiVerbali()
                        .getFileAllegatoAlVerbaleByAllegatoId(
                            getUtente(),
                            dettaglioVerbale.getNumeroProtocollo(),
                            Long.toString(allegatoItem.getId()));
                mimeType = FileFdCUtil.getMimeTypeFileUploadAllegato(fileAllegato.getFile());

                /*
                 * int indexOf = mimeType.indexOf("/"); estensioneFile =
                 * mimeType.substring(indexOf + 1, mimeType.length());
                 */

                estensioneFile = FileFdCUtil.getEstensionFileUploadAllegato(fileAllegato.getFile());

              } catch (BusinessException
                  | ApiException
                  | IOException
                  | MagicMatchNotFoundException e) {
                log.error("Errore download allegato dpp");
              }

              linkFile =
                  downloadFileAllegatoId(
                      mimeType,
                      estensioneFile,
                      dettaglioVerbale.getNumeroProtocollo(),
                      allegatoItem.getDescrizione(),
                      fileAllegato.getFile());
            }

            Label estensione = new Label("estensione", estensioneFile);
            estensione.setVisible(PageUtil.isStringValid(estensioneFile));
            item.addOrReplace(estensione);

            if (linkFile != null) {
              item.addOrReplace(linkFile);
            }
          }
        };

    listViewDocumentiDpp.setVisible(listaDocumentiDpp != null && !listaDocumentiDpp.isEmpty());
    addOrReplace(listViewDocumentiDpp);
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadFileAllegatoId(
      String mimeType,
      String estensioneFile,
      String numeroProtocollo,
      String descrizione,
      byte[] fileBytes) {
    final AbstractResource fileResourceByte;

    String nomeFile =
        numeroProtocollo.concat("_").concat(descrizione).concat(".").concat(estensioneFile);

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = -4121513386226078972L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              if (estensioneFile.equalsIgnoreCase("pdf")) {
                r.setFileName(nomeFile);
                r.setContentType(mimeType);
                r.setContentDisposition(ContentDisposition.INLINE);
                r.setContentLength(fileBytes.length);
                r.setWriteCallback(
                    new WriteCallback() {
                      @Override
                      public void writeData(final Attributes attributes) {
                        attributes.getResponse().write(fileBytes);
                      }
                    });
              } else {
                r.setFileName(nomeFile);
                r.setContentType(mimeType);
                r.setContentDisposition(ContentDisposition.INLINE);
                r.setContentLength(fileBytes.length);
                r.setWriteCallback(
                    new WriteCallback() {
                      @Override
                      public void writeData(final Attributes attributes) {
                        attributes.getResponse().write(fileBytes);
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

    final ResourceLink linkFile =
        new ResourceLink("btnDownload", fileResourceByte) {

          private static final long serialVersionUID = 2201601593345713167L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };
    return linkFile;
  }
}
