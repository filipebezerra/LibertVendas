package br.com.libertsolutions.libertvendas.app.presentation.widget;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class StepperAdapter extends AbstractFragmentStepAdapter {

    private final List<Step> mSteps = new ArrayList<>();

    private final List<String> mStepTitles = new ArrayList<>();

    public StepperAdapter(@NonNull final FragmentManager fm, @NonNull final Context context) {
        super(fm, context);
    }

    public void addStep(@NonNull Step step, @Nullable String title) {
        mSteps.add(step);
        if (!TextUtils.isEmpty(title)) {
            mStepTitles.add(title);
        }
        notifyDataSetChanged();
    }

    @Override public Step createStep(@IntRange(from = 0L) final int position) {
        return mSteps.get(position);
    }

    @Override public int getCount() {
        return mSteps.size();
    }

    @NonNull @Override public StepViewModel getViewModel(@IntRange(from = 0L) final int position) {
        if (position < 0 || position > mStepTitles.size()) {
            return super.getViewModel(position);
        }

        return new StepViewModel.Builder(context)
                .setTitle(mStepTitles.get(position))
                .create();
    }
}
