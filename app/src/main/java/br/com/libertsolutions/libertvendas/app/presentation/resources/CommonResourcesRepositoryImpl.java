package br.com.libertsolutions.libertvendas.app.presentation.resources;

import android.content.res.Resources;

/**
 * @author Filipe Bezerra
 */
public class CommonResourcesRepositoryImpl implements CommonResourcesRepository {
    private final Resources mResources;

    public CommonResourcesRepositoryImpl(Resources resources) {
        mResources = resources;
    }

    Resources getResources() {
        return mResources;
    }
}
