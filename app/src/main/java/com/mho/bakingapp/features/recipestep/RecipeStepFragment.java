package com.mho.bakingapp.features.recipestep;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.mho.bakingapp.BR;
import com.mho.bakingapp.R;
import com.mho.bakingapp.bases.BaseFragment;
import com.mho.bakingapp.data.remote.models.Step;
import com.mho.bakingapp.databinding.FragmentRecipeStepBinding;
import com.mho.bakingapp.features.recipesteppage.RecipeStepPageAdapter;

import java.util.List;

public class RecipeStepFragment extends BaseFragment<FragmentRecipeStepBinding, RecipeStepViewModel>
        implements RecipeStepNavigator{

    //region Fields

    private boolean isTwoPane;

    private RecipeStepPageAdapter recipeStepPageAdapter;

    private RecipeStepViewModel recipeStepViewModel;

    private OnRecipeStepFragmentListener onRecipeStepFragmentListener;

    //endregion

    //region Constructor

    public RecipeStepFragment() { }

    //endregion

    //region Override Methods & Callbacks

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onRecipeStepFragmentListener = (OnRecipeStepFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeStepFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipeStepViewModel.validateRecipeStepArguments(getArguments());

        isTwoPane = getResources().getBoolean(R.bool.two_pane_mode);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipeStepPageAdapter = new RecipeStepPageAdapter(getFragmentManager(), getContext());
        binding.viewPagerRecipeStepPages.setAdapter(recipeStepPageAdapter);
        binding.viewPagerRecipeStepPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                //stepId = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        binding.tabRecipeSteps.setupWithViewPager(binding.viewPagerRecipeStepPages);

        recipeStepViewModel.initStepTabs();

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTwoPane) {
            binding.tabRecipeSteps.setVisibility(View.GONE);
        }
    }

    @Override
    public int getIdLayout() {
        return R.layout.fragment_recipe_step;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public RecipeStepViewModel getViewModel() {
        recipeStepViewModel = ViewModelProviders.of(this).get(RecipeStepViewModel.class);
        return recipeStepViewModel;
    }

    @Override
    public void setNavigator() {
        recipeStepViewModel.setNavigator(this);
    }

    @Override
    public void finishActivity() {
        onRecipeStepFragmentListener.finishActivity();
    }

    @Override
    public void updateStepTabs(int currentPosition, List<Step> stepList) {
        recipeStepPageAdapter.updateStepList(stepList);

        binding.tabRecipeSteps.setScrollPosition(currentPosition,0f,true);
        binding.viewPagerRecipeStepPages.setCurrentItem(currentPosition);
    }

    //endregion

    //region Public Methods

    static RecipeStepFragment newInstance(Bundle bundle){
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    //endregion

    //region Inner Classes & Callbacks

    public interface OnRecipeStepFragmentListener {
        void finishActivity();
    }

    //endregion
}
