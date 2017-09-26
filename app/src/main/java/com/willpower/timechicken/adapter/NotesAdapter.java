package com.willpower.timechicken.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willpower.timechicken.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NotesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NotesAdapter(List<String> data) {
        super(R.layout.item_rv_note, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,String item) {
        helper.setText(R.id.tv_note_date,item);
        helper.addOnClickListener(R.id.image_note);
    }
}
