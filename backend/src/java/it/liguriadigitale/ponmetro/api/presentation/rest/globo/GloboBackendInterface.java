package it.liguriadigitale.ponmetro.api.presentation.rest.globo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/globo")
public interface GloboBackendInterface {

  @GET
  @Path("/funzioni")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public ListaGlobo getListaFunzioni();
}
