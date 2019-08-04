package com.mho.bakingapp.features;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<D extends ViewDataBinding> extends AppCompatActivity {

    public D binding;

    public abstract int getIdLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getIdLayout());
    }
}