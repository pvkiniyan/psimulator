/*
 * Erstellt am 26.10.2011.
 */

package networkModule;
//TODO: prejmenovat, ud2lat javadoc

import networkDevice.AbstractNetworkDevice;

/**
 * Síťový modul pro počítač, tedy včetně rozhraní pro aplikace.
 * @author neiss
 */
public abstract class NetworkModuleForAplications extends NetworkModule {

    public NetworkModuleForAplications(AbstractNetworkDevice device) {
        super(device);
    }

    


    //tady budou muset bejt metody pro posilani dat a pro registraci aplikaci, tedy komunikaci s aplikacema


}
