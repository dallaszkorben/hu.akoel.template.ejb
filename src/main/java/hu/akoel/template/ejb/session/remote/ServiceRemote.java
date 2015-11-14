package hu.akoel.template.ejb.session.remote;

import javax.ejb.Remote;

@Remote
public interface ServiceRemote {

	public void clearCache();

}
