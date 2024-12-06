package it.liguriadigitale.ponmetro.portale.business.certificati.service;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.WrapperGetSelezioneAttoResponseGenericResponse;
import it.liguriadigitale.ponmetro.portale.pojo.certificati.InformazioniAccessorieCertificato;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.servizianagrafici.model.Atto;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoAttoPersona;
import it.liguriadigitale.ponmetro.servizianagrafici.model.GetScaricoCertificatoAttiResponse;
import it.liguriadigitale.ponmetro.servizianagrafici.model.MezzoConsegna;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoAttoRequest;
import it.liguriadigitale.ponmetro.servizianagrafici.model.PostRichiestaEmissioneCertificatoSCResponseGenericResponse;
import java.util.List;

public interface ServiziCertificatiStatoCivile {

  public List<CertificatoAttoPersona> elencoCertificatiRichiesti(Utente utente);

  public GetScaricoCertificatoAttiResponse getScaricoCertificatoAtti(
      PostRichiestaEmissioneCertificatoAttoRequest request, Integer idCertificato, Utente utente)
      throws BusinessException, ApiException;

  public PostRichiestaEmissioneCertificatoSCResponseGenericResponse
      postRichiestaEmissioneCertificatoAtto(PostRichiestaEmissioneCertificatoAttoRequest request)
          throws BusinessException;

  public List<Atto> getRicercaAtti(
      Utente utente, PostRichiestaEmissioneCertificatoAttoRequest request);

  public List<MezzoConsegna> getRicercaMezziConsegna();

  public WrapperGetSelezioneAttoResponseGenericResponse checkCertificatoRichiedibile(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException;

  public boolean inviaRichiestaPerEmail(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni);

  public boolean inviaRichiestaPerEmailConPost(
      PostRichiestaEmissioneCertificatoAttoRequest request,
      InformazioniAccessorieCertificato informazioni)
      throws BusinessException;

  public boolean checkCertificatoRichiedibile(CertificatoAttoPersona certificato);

  public GetScaricoCertificatoAttiResponse getScaricoCertificatoDopoPost(
      PostRichiestaEmissioneCertificatoAttoRequest request, Utente utente)
      throws BusinessException, ApiException;
}
