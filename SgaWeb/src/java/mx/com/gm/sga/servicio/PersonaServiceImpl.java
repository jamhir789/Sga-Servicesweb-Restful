/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gm.sga.servicio;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import mx.com.gm.sga.domain.Persona;
import mx.com.gm.sga.eis.PersonaDao;

//libreria para que se pueda utilisar como serviceweb
import javax.jws.WebService;

/**
 *
 * @author erick.medina
 */
@Stateless
@WebService(endpointInterface = "mx.com.gm.sga.servicio.PersonaServiceWS") //se agrega la clase para que pueda utlizarce como servicio
public class PersonaServiceImpl implements PersonaserviceRemote, PersonaService{
    
  
	
	@Resource
	private SessionContext contexto;
	
	@EJB
	private PersonaDao personaDao;

	@Override
	public List<Persona> listarPersonas() {
		return personaDao.findAllPersonas();
	}

        @Override
	public Persona encontrarPersonaPorId(Persona persona) {
		return personaDao.findPersonaById(persona);
	}

        @Override
	public Persona encontrarPersonaPorEmail(Persona persona) {
		return personaDao.findPersonaByEmail(persona);
	}

	@Override
	public void registrarPersona(Persona persona) {
		personaDao.insertPersona(persona);
	}

        @Override
	public void modificarPersona(Persona persona) {
		try{
			personaDao.updatePersona(persona);
		}catch(Throwable t){
			contexto.setRollbackOnly();
		}	
	}

        @Override
	public void eliminarPersona(Persona persona) {
		personaDao.deletePersona(persona);
	}

}
