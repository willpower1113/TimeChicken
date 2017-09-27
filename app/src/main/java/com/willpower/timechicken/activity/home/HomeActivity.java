package com.willpower.timechicken.activity.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.willpower.timechicken.R;
import com.willpower.timechicken.activity.note.NoteActivity;
import com.willpower.timechicken.activity.setting.AppSettingActivity;
import com.willpower.timechicken.adapter.NotesAdapter;
import com.willpower.timechicken.base.BaseActivity;

import java.util.Arrays;

public class HomeActivity extends BaseActivity {
    private static final String[] arrays = {"2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11", "2017-14-11"};
    RecyclerView rv_notes;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        findViewById(R.id.toolbar_back).setVisibility(View.GONE);
        setToolbarTitle(getResources().getString(R.string.app_name));
        init();
        initRecyclerView();
    }

    private void init() {
        rv_notes = (RecyclerView) findViewById(R.id.rv_notes);
        setToolbarAction("个性化", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(new Intent(context, AppSettingActivity.class));
            }
        });
    }

    private void initRecyclerView() {
        rv_notes.setLayoutManager(new GridLayoutManager(context, 2));
        rv_notes.setAdapter(new NotesAdapter(Arrays.asList(arrays)));
        rv_notes.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                jumpActivity(new Intent(context, NoteActivity.class));
            }
        });
    }
}
