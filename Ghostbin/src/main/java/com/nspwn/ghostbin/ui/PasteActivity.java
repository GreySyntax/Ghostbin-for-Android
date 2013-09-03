

package com.nspwn.ghostbin.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.nspwn.ghostbin.BootstrapApplication;
import com.nspwn.ghostbin.BootstrapServiceProvider;
import com.nspwn.ghostbin.R;
import com.nspwn.ghostbin.core.BootstrapService;
import com.nspwn.ghostbin.core.beans.Language;
import com.nspwn.ghostbin.core.beans.LanguageGroup;
import com.nspwn.ghostbin.ui.view.CapitalizedTextView;
import com.nspwn.ghostbin.util.SafeAsyncTask;
import com.viewpagerindicator.TitlePageIndicator;

import net.simonvt.menudrawer.MenuDrawer;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;


/**
 * Activity to view the carousel and view pager indicator with fragments.
 */
public class PasteActivity extends BootstrapFragmentActivity {
    private static final String TAG = "com.nspwn.ghostbin.ui.PasteActivity";
    @InjectView(R.id.tpi_header) TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages) ViewPager pager;

    @Inject BootstrapServiceProvider serviceProvider;
    private SafeAsyncTask<List<LanguageGroup>> languageTask;
    private MenuDrawer menuDrawer;
    private Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        // Set up navigation drawer
        menuDrawer = MenuDrawer.attach(this);
//        menuDrawer.setMenuView(R.layout.navigation_drawer);
        initMenu();
//        initScreen();
    }

    private void setLanguage(Language language) {
        Log.d(TAG, String.format("Setting language %s", language));
        this.language = language;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void drawMenu(List<LanguageGroup> languageGroups) {
        ScrollView scrollView = (ScrollView)getLayoutInflater().inflate(R.layout.navigation_drawer, menuDrawer.getMenuContainer(), false);
        LinearLayout linearLayout = (LinearLayout)scrollView.getChildAt(0);

        for (LanguageGroup languageGroup : languageGroups) {
            CapitalizedTextView capitalizedTextView = new CapitalizedTextView(this);
            capitalizedTextView.setText(languageGroup.getTitle());
            capitalizedTextView.setTextColor(getResources().getColor(R.color.nav_text_selector));
            capitalizedTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            capitalizedTextView.setBackground(getResources().getDrawable(R.drawable.nav_menu_button_background_selector));
            capitalizedTextView.setVisibility(View.VISIBLE);
            linearLayout.addView(capitalizedTextView);
            View splitterG = getLayoutInflater().inflate(R.layout.menu_splitter, linearLayout, false);
            splitterG.setVisibility(View.VISIBLE);
            linearLayout.addView(splitterG);

            for (final Language language : languageGroup.getLanguages()) {
                Button button = new Button(this);
                button.setText(language.getTitle());
                button.setTextColor(getResources().getColor(R.color.nav_text_selector));
                button.setBackground(getResources().getDrawable(R.drawable.nav_menu_button_background_selector));
                button.setVisibility(View.VISIBLE);

                // Handle click event
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Hide menu drawer
                        menuDrawer.toggleMenu();
                        setLanguage(language);
                    }
                });

                linearLayout.addView(button);
                View splitter = getLayoutInflater().inflate(R.layout.menu_splitter, linearLayout, false);
                splitter.setVisibility(View.VISIBLE);
                linearLayout.addView(splitter);
            }
        }

        menuDrawer.setMenuView(scrollView);
        menuDrawer.setContentView(R.layout.carousel_view);
        menuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        menuDrawer.setDrawerIndicatorEnabled(true);
        Views.inject(this);
    }

    private void initMenu() {
        try {
            BootstrapApplication.getInstance().getCacheDir();
            final BootstrapService provider = serviceProvider.getService(this);

            languageTask = new SafeAsyncTask<List<LanguageGroup>>() {
                @Override
                public List<LanguageGroup> call() throws Exception {
                    return provider.getLanguages(getCacheDir());
                }

                @Override
                protected void onSuccess(List<LanguageGroup> languageGroups) throws Exception {
                    drawMenu(languageGroups);
                }
            };

            languageTask.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initScreen() {
        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getSupportFragmentManager()));

        indicator.setViewPager(pager);
        pager.setCurrentItem(1);

//        setNavListeners();
    }

//    private void setNavListeners() {
//        menuDrawer.findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                menuDrawer.toggleMenu();
//            }
//        });
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                menuDrawer.toggleMenu();
                return true;
//            case R.id.timer:
//                navigateToTimer();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
