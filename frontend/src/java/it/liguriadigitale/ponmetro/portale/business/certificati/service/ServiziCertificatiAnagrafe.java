package it.liguriadigitale.ponmetro.portale.business.certificati.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Certificato;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoPersona;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.ListItem;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.UtilizzoItem;
import java.util.List;

public interface ServiziCertificatiAnagrafe {

  public List<CertificatoPersona> elencoCertificatiRichiesti(Utente utente);

  public GetScaricoCertificatoResponse getScaricoCertificato(
      PostRichiestaEmissioneCertificatoRequest request, Integer idCertificato, Utente utente)
      throws BusinessException, ApiException;

  public boolean postRichiestaEmissioneCertificato(PostRichiestaEmissioneCertificatoRequest request)
      throws BusinessException;

  public List<Certificato> getCertificati(
      Utente utente, PostRichiestaEmissioneCertificatoRequest request);

  public List<ListItem> getTipologiaCertificati();

  public List<UtilizzoItem> getUtilizzoCertificati();

  public List<ListItem> getRicercaMezziConsegna();

  public boolean getCertificabilita(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni);

  public boolean isCittadinoResidente(PostRichiestaEmissioneCertificatoRequest request);

  public boolean inviaRichiestaPerEmail(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni);

  public boolean inviaRichiestaPerEmailConPost(
      PostRichiestaEmissioneCertificatoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException;

  public GetScaricoCertificatoResponse getScaricoCertificatoDopoPost(
      PostRichiestaEmissioneCertificatoRequest modelloForm,
      InformazioniAccessorieCertificato informazioni,
      Utente utente)
      throws BusinessException, ApiException;
}
