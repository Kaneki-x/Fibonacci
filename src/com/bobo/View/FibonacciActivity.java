package com.bobo.View;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/***
 * FibonacciActivity
 * 
 * @author BoBo
 * 
 */
public class FibonacciActivity extends Activity {

	private MyListView listView;  //自定义ListView
	private ArrayList<Map<String, String>> listData;  //ListView数据
	private ArrayList<Map<String, String>> tempData;
	private BigInteger format_num = BigInteger.valueOf((long)Math.pow(10, 10)); //科学计数基准
	private int fibonacci_index = 1; //斐波那契游标
	private Button btn_reverse;
	private SimpleAdapter simpleAdapter;
	private BigInteger[] rember;
	private String str_num; //字符串大数

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_reverse = (Button) findViewById(R.id.btn_reverse);
		listView = (MyListView) findViewById(R.id.mylistview); //获得句柄
		listView.setAdapter(getSimpleAdapter_1()); //设置适配器
		setListViewHeightBasedOnChildren(listView); //动态设置ListView的高度
		btn_reverse.setOnClickListener(new OnClickListener() { //绑定监听器
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempData = (ArrayList<Map<String, String>>) listData.clone(); //临时listData
				for(int i = 0; i < tempData.size(); i++){ //转置
					listData.set(i, tempData.get(tempData.size()-i-1));
				}
				simpleAdapter.notifyDataSetChanged(); //通知Adapter更新
			}
		});
	}

	//设置ListView数据，返回适配器
	private SimpleAdapter getSimpleAdapter_1() {
		Map<String, String> map = new HashMap<String, String>();
		listData = new ArrayList<Map<String, String>>();
		rember = new BigInteger[2];  //当前的斐波那契数和前一个斐波纳契数
		
		rember[0] = new BigInteger("0"); //初始化第一个斐波那契数
		rember[1] = new BigInteger("1"); //初始化第二个斐波那契数
		map.put("text", "f(" + 0 + "^3 = " + Math.pow(0, 3)+")" + " = " + "0"); //设置第一个斐波那契数
		listData.add(map); //将数据放入ListData数组
		
		while(fibonacci_index <= 40){
			
			rember = fibonacciNormal((long)Math.pow(fibonacci_index, 3) //获得当前的斐波那契数和前一个斐波那契数
					- (long)Math.pow(fibonacci_index-1, 3), rember[0], rember[1]);
			map = new HashMap<String, String>();
			
			if(rember[1].compareTo(format_num) == 1) //判断是否超过10^10
			{
				str_num = rember[1].toString(); //获得字符串型数
				map.put("text", "f(" + fibonacci_index + "^3 = "
						+ ((long)Math.pow(fibonacci_index++, 3)) + ")" + " = "
							+ str_num.substring(0, 1)+" e " 
								+ (str_num.length() - 1));//获得科学技术法的斐波那契数
			}else{
				map.put("text", "f(" + fibonacci_index + "^3 = "
						 + ((long)Math.pow(fibonacci_index++, 3)) + ")" 
						 	+ " = " + rember[1]);
			}
			
			listData.add(map); //将数据放入ListData数组
		}
		
		simpleAdapter = new SimpleAdapter(this, listData,
				R.layout.list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item }); //返回SimpleAdapter
		
		return simpleAdapter;

	}

	//动态设置ListView的高度 
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { 
			View listItem = listAdapter.getView(i, null, listView); //获得ListView的Item对象
			listItem.measure(0, 0); //计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度

		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams(); //获得ListView的布局对象
		params.height = totalHeight //设置布局高度
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	//非递推实现方式  
	public static BigInteger[] fibonacciNormal(long n, BigInteger former_big, BigInteger current_big){ 
		BigInteger[] last = new BigInteger[2]; //初始化斐波那契数组
		BigInteger n0 = former_big, n1 = current_big, sn = new BigInteger("0"); //设置前一次的斐波那契数，和当前斐波那契数
		
		for(int i = 0; i < n ; i ++){ //计算斐波那契数
			sn = n0.add(n1);
			n0 = n1; 
			n1 = sn; 
		}
		
		last[0] = n0; //返回前一个斐波那契数
		last[1] = n1; //返回当前斐波那契数
		
		return last; 
	} 
}