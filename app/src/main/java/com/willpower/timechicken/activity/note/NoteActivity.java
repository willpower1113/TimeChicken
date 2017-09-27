package com.willpower.timechicken.activity.note;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.willpower.timechicken.R;
import com.willpower.timechicken.base.BaseActivity;
import com.willpower.timechicken.fragment.note.CanvasNoteFragment;
import com.willpower.timechicken.fragment.note.TextNoteFragment;
import com.willpower.timechicken.weight.AppButton;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NoteActivity extends BaseActivity {
    AppButton appText, appCanvas;
    CanvasNoteFragment canvasNoteFragment;
    TextNoteFragment textNoteFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_notes;
    }
    @Override
    protected void initView() {
        setToolbarTitle("写日记");
        init();
        initFragment();
    }

    private void init() {
        appText = (AppButton) findViewById(R.id.appText);
        appCanvas = (AppButton) findViewById(R.id.appCanvas);
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        textNoteFragment = new TextNoteFragment();
        canvasNoteFragment = new CanvasNoteFragment();
        transaction.add(R.id.viewGroup,textNoteFragment);
        transaction.add(R.id.viewGroup,canvasNoteFragment);
        transaction.commit();
        changeFragment(textNoteFragment, canvasNoteFragment);
    }

    public void onTextClick(View view) {
        appText.setNormalColor(getResources().getColor(R.color.colorPrimary));
        appCanvas.setNormalColor(Color.TRANSPARENT);
        changeFragment(textNoteFragment, canvasNoteFragment);
    }

    public void onCanvasClick(View view) {
        appText.setNormalColor(Color.TRANSPARENT);
        appCanvas.setNormalColor(getResources().getColor(R.color.colorPrimary));
        changeFragment(canvasNoteFragment,textNoteFragment);
    }

    private void changeFragment(Fragment show, Fragment hide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(show);
        transaction.hide(hide);
        transaction.commit();
    }
}
