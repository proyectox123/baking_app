package com.mho.bakingapp.features.recipesteppage;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mho.bakingapp.R;
import com.mho.bakingapp.data.remote.models.Step;

import java.util.ArrayList;
import java.util.List;

import static com.mho.bakingapp.utils.Constants.EXTRA_STEP;

public class RecipeStepPageAdapter extends FragmentPagerAdapter {

    //region Fields

    private Context context;

    private List<Step> stepList;

    //endregion

    //region Constructors

    public RecipeStepPageAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

        updateStepList(new ArrayList<Step>(0));
    }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_STEP, stepList.get(position));

        return RecipeStepPageFragment.newInstance(args);
    }

    @Override
    public int getCount() {
        return stepList == null ? 0 : stepList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(R.string.title_recipe_step_page, stepList.get(position).getId() + 1);
    }

    //endregion

    //region Public Methods

    public void updateStepList(@NonNull List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }

    //endregion
}