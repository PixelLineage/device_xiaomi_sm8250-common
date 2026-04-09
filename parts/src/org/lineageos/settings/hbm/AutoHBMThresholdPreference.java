/*
* Copyright (C) 2016 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.lineageos.settings.hbm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.widget.SliderPreference;
import com.google.android.material.slider.Slider;

import org.lineageos.settings.R;

public class AutoHBMThresholdPreference extends SliderPreference
        implements Slider.OnSliderTouchListener {

    public static final int DEF_VAL = 6000;
    private static final int MIN_VAL = 0;
    private static final int MAX_VAL = 60000;
    private static final int STEP = 1000;

    private TextView mSummaryView;

    public AutoHBMThresholdPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMin(MIN_VAL);
        setMax(MAX_VAL);
        setSliderIncrement(STEP);
        setShowSliderValue(true);
        setLabelFormater(value -> (int) value + " lux");
        setExtraTouchListener(this);
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = DEF_VAL;
        }
        setValue(getPersistedInt((Integer) defaultValue));
    }

    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mSummaryView = (TextView) holder.findViewById(android.R.id.summary);
        if (mSummaryView != null) {
            mSummaryView.setVisibility(View.VISIBLE);
            updateSummaryView(getValue());
        }
    }

    @Override
    public void onStartTrackingTouch(@NonNull Slider slider) {
    }

    @Override
    public void onStopTrackingTouch(@NonNull Slider slider) {
        updateSummaryView((int) slider.getValue());
    }

    public void resetToDefault() {
        setValue(DEF_VAL);
        persistInt(DEF_VAL);
        updateSummaryView(DEF_VAL);
    }

    private void updateSummaryView(int value) {
        if (mSummaryView != null) {
            mSummaryView.setText(
                    getContext().getString(R.string.auto_hbm_threshold_summary, value));
        }
    }
}
