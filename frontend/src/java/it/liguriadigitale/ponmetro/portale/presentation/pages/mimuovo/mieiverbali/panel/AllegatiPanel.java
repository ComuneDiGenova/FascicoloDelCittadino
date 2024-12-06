package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.mieiverbali.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.Allegato;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.DettaglioVerbale;
import it.liguriadigitale.ponmetro.verbaliContravvenzioni.model.FileAllegato;
import java.io.IOException;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;

public class AllegatiPanel extends BasePanel {

  private static final long serialVersionUID = 7552354273264155620L;

  private DettaglioVerbale dettaglioVerbale;

  private List<Allegato> listaTuttiAllegatiSenzaNull;

  @SuppressWarnings("unused")
  private static final long MEGABYTE = 1024L * 1024L;

  public AllegatiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public AllegatiPanel(
      String id, DettaglioVerbale dettaglioVerbale, List<Allegato> listaTuttiAllegatiSenzaNull) {
    super(id);

    this.dettaglioVerbale = dettaglioVerbale;
    this.listaTuttiAllegatiSenzaNull = listaTuttiAllegatiSenzaNull;

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {

    List<Allegato> listaAllegatiSenzaNull = (List<Allegato>) dati;

    String descrizioneTitolo = "";
    if (listaAllegatiSenzaNull != null) {
      if (listaAllegatiSenzaNull.isEmpty()) {
        descrizioneTitolo = getString("AllegatiPanel.nessunAllegato");
      } else {
        descrizioneTitolo = getString("AllegatiPanel.titoloAllegati");
      }
    } else {
      descrizioneTitolo = getString("AllegatiPanel.nessunAllegato");
    }
    Label titoloAllegati = new Label("titoloAllegati", descrizioneTitolo);
    add(titoloAllegati);

    ListView<Allegato> listViewAllegati =
        new ListView<Allegato>("box", listaAllegatiSenzaNull) {

          private static final long serialVersionUID = -1972525733545680996L;

          @SuppressWarnings("rawtypes")
          @Override
          protected void populateItem(ListItem<Allegato> item) {
            Allegato allegatoItem = item.getModelObject();

            item.setOutputMarkupId(true);

            String descrizioneAllegato = "";
            if (allegatoItem != null) {
              descrizioneAllegato = allegatoItem.getDescrizione();
            }
            Label descrizione = new Label("descrizione", descrizioneAllegato);
            item.add(descrizione);

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
            item.add(dimensione);

            ResourceLink linkFile = null;
            ExternalLink linkAmt = null;

            String estensioneFile = "";
            String mimeType = "";

            if (allegatoItem.getPath() != null && !allegatoItem.getPath().isEmpty()) {

              if (allegatoItem.getDescrizione().equalsIgnoreCase("Corsie Bus")) {
                linkAmt =
                    openFileAllegatoAMT(
                        dettaglioVerbale.getNumeroProtocollo(),
                        allegatoItem.getDescrizione(),
                        allegatoItem.getPath());
              } else {
                String path = allegatoItem.getPath();
                String fileExtension = path.substring(path.length() - 3, path.length());

                estensioneFile = fileExtension;

                FileAllegato fileAllegato = new FileAllegato();
                try {
                  fileAllegato =
                      ServiceLocator.getInstance()
                          .getServiziMieiVerbali()
                          .getFileAllegatoAlVerbaleByAllegatoUri(
                              getUtente(),
                              dettaglioVerbale.getNumeroProtocollo(),
                              allegatoItem.getPath());
                } catch (BusinessException | ApiException | IOException e) {
                  log.error("Errore download allegato");
                }

                linkFile =
                    downloadFileAllegatoPath(
                        fileExtension,
                        dettaglioVerbale.getNumeroProtocollo(),
                        allegatoItem.getDescrizione(),
                        fileAllegato.getFile());
              }
            } else {
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
                   * int indexOf = mimeType.indexOf("/");
                   * estensioneFile = mimeType.substring(indexOf + 1,
                   * mimeType.length());
                   */

                  estensioneFile =
                      FileFdCUtil.getEstensionFileUploadAllegato(fileAllegato.getFile());

                } catch (BusinessException
                    | ApiException
                    | IOException
                    | MagicMatchNotFoundException e) {
                  log.error("Errore download allegato");
                }

                linkFile =
                    downloadFileAllegatoId(
                        mimeType,
                        estensioneFile,
                        dettaglioVerbale.getNumeroProtocollo(),
                        allegatoItem.getDescrizione(),
                        fileAllegato.getFile());
              }
            }

            Label estensione = new Label("estensione", estensioneFile);
            estensione.setVisible(!estensioneFile.isEmpty());
            item.add(estensione);

            if (linkFile != null) {
              item.add(linkFile);
            }

            if (linkAmt != null) {
              item.add(linkAmt);
            }
          }
        };

    listViewAllegati.setVisible(
        listaAllegatiSenzaNull != null && !listaAllegatiSenzaNull.isEmpty());
    add(listViewAllegati);
  }

  @SuppressWarnings("rawtypes")
  private ResourceLink downloadFileAllegatoPath(
      String estensioneFile, String numeroProtocollo, String descrizione, byte[] fileBytes) {
    final AbstractResource fileResourceByte;

    String nomeFile =
        numeroProtocollo.concat("_").concat(descrizione).concat(".").concat(estensioneFile);

    fileResourceByte =
        new AbstractResource() {

          private static final long serialVersionUID = 7537910329746254943L;

          @Override
          protected ResourceResponse newResourceResponse(final Attributes attributes) {
            final ResourceResponse r = new ResourceResponse();
            try {

              if (estensioneFile.equalsIgnoreCase("pdf")) {
                r.setFileName(nomeFile);
                r.setContentType("application/pdf");
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
                r.setContentType("image/tiff");
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

          private static final long serialVersionUID = -1799261347673338473L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };
    return linkFile;
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

          private static final long serialVersionUID = 7537910329746254943L;

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

          private static final long serialVersionUID = -1799261347673338473L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "scarica FILE: " + nomeFile);
          }
        };
    return linkFile;
  }

  private ExternalLink openFileAllegatoAMT(
      String numeroProtocollo, String descrizione, String path) {

    String nomeFile = numeroProtocollo.concat("_").concat(descrizione);

    ExternalLink linkAmt =
        new ExternalLink("btnDownload", path) {

          private static final long serialVersionUID = -6645521459188126396L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
            tag.put("title", "apri FILE: " + nomeFile);
          }
        };

    return linkAmt;
  }
}
