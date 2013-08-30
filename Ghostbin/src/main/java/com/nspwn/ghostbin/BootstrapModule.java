package com.nspwn.ghostbin;

import com.nspwn.ghostbin.core.TimerService;
import com.nspwn.ghostbin.ui.BootstrapTimerActivity;
import com.nspwn.ghostbin.ui.CarouselActivity;
import com.nspwn.ghostbin.ui.CheckInsListFragment;
import com.nspwn.ghostbin.ui.NewsActivity;
import com.nspwn.ghostbin.ui.NewsListFragment;
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
                CarouselActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                TimerService.class
        }

)
public class BootstrapModule  {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }
}
