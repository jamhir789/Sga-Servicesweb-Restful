/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.lol;

/**
 *
 * @author erick.medina
 */
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.com.gm.sga.domain.Usuario;
import mx.com.gm.sga.servicio.UsuarioService;

@Path("/Usuarios")
@Stateless
public class UserServicesRs {
    
        @EJB
    private UsuarioService usuarioService;

    @GET
    @Produces(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> listarPersonas() {
        return usuarioService.listarUsuarios();
    }

    @GET
    @Produces(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}") //hace referencia a /personas/{id}    
    public Usuario encontrarUsuarioPorId(@PathParam("id") int id) {
        return usuarioService.encontrarUsuarioPorId(new Usuario(id));
    }

    @POST
    @Produces(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response agregarUsuario(Usuario usuario) {
        try {
            usuario.registrarUsuario(usuario);
            return Response.ok().entity(usuario).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Produces(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes(value={MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response modificarUsuario(@PathParam("id") int id, Usuario usuarioModificado) {
        try {
            Usuario usuario = usuarioService.encontrarUsuarioPorId(new Usuario(id));
            if (usuario != null) {
                usuarioService.modificarUsuario(usuarioModificado);
                return Response.ok().entity(usuarioModificado).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response eliminarPersonaPorId(@PathParam("id") int id) {
        try {
            usuarioService.eliminarUsuario(new Usuario(id));
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }
}
