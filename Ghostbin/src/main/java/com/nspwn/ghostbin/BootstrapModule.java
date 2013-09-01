package com.nspwn.ghostbin;

import com.nspwn.ghostbin.ui.CarouselActivity;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
(
        complete = false,

        injects = {
                BootstrapApplication.class,
                CarouselActivity.class
        }

)
public class BootstrapModule  {

//    @Singleton
//    @Provides
//    Bus provideOttoBus() {
//        return new Bus();
//    }
}
