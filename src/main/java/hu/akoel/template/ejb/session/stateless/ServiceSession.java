package hu.akoel.template.ejb.session.stateless;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import hu.akoel.template.ejb.session.remote.ServiceRemote;

@Stateless(name="ServiceSession", mappedName="session/ServiceSession")
@LocalBean
public class ServiceSession implements ServiceRemote{

	@PersistenceContext(unitName="dev")
	private EntityManager em;
	
	@Resource
	private SessionContext ctx;

	@Override
	public void clearCache() {
		em.getEntityManagerFactory().getCache().evictAll();		
	}
}
