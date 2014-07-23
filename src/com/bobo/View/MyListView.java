package com.bobo.View;

import com.bobo.View.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

/***
 * �Զ���ListView
 * 
 * @author BoBo
 * 
 */
public class MyListView extends ListView {
	
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/****
	 * ���ش����¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			int itemnum = pointToPosition(x, y);
			if (itemnum == AdapterView.INVALID_POSITION)
				break;
			else {
				if (itemnum == 0) {
					if (itemnum == (getAdapter().getCount() - 1)) {
						// ֻ��һ��
						setSelector(R.drawable.list_round);
					} else {
						// ��һ��
						setSelector(R.drawable.list_top_round);
					}
				} else if (itemnum == (getAdapter().getCount() - 1))
					// ���һ��
					setSelector(R.drawable.list_bottom_round);
				else {
					// �м���
					setSelector(R.drawable.list_center_round);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return super.onTouchEvent(ev);
	}
}