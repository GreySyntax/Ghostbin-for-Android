
package com.nspwn.ghostbin;

import android.accounts.AccountsException;
import android.app.Activity;

import com.nspwn.ghostbin.core.BootstrapService;
import com.nspwn.ghostbin.core.UserAgentProvider;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Provider for a {@link com.nspwn.ghostbin.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

    @Inject UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public BootstrapService getService(Activity activity) throws IOException {
        return new BootstrapService(userAgentProvider);
    }
}
