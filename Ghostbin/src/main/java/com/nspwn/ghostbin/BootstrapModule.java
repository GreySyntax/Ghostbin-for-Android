package com.nspwn.ghostbin;

import com.nspwn.ghostbin.ui.PasteActivity;

import dagger.Module;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                PasteActivity.class
        }

)
public class BootstrapModule  {

//    @Singleton
//    @Provides
//    Bus provideOttoBus() {
//        return new Bus();
//    }
}
