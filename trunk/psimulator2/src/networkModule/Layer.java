/*
 * Erstellt am 27.10.2011.
 */

package networkModule;

import utils.WorkerThread;

/**
 *
 * @author neiss
 */
public abstract class Layer {

    protected NetMod netMod;

    public Layer(NetMod netMod) {
        this.netMod = netMod;
        
    }

    public NetMod getNetMod() {
        return netMod;
    }
}
