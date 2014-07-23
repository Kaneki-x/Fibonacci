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

	private MyListView listView;  //�Զ���ListView
	private ArrayList<Map<String, String>> listData;  //ListView����
	private ArrayList<Map<String, String>> tempData;
	private BigInteger format_num = BigInteger.valueOf((long)Math.pow(10, 10)); //��ѧ������׼
	private int fibonacci_index = 1; //쳲������α�
	private Button btn_reverse;
	private SimpleAdapter simpleAdapter;
	private BigInteger[] rember;
	private String str_num; //�ַ�������

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn_reverse = (Button) findViewById(R.id.btn_reverse);
		listView = (MyListView) findViewById(R.id.mylistview); //��þ��
		listView.setAdapter(getSimpleAdapter_1()); //����������
		setListViewHeightBasedOnChildren(listView); //��̬����ListView�ĸ߶�
		btn_reverse.setOnClickListener(new OnClickListener() { //�󶨼�����
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tempData = (ArrayList<Map<String, String>>) listData.clone(); //��ʱlistData
				for(int i = 0; i < tempData.size(); i++){ //ת��
					listData.set(i, tempData.get(tempData.size()-i-1));
				}
				simpleAdapter.notifyDataSetChanged(); //֪ͨAdapter����
			}
		});
	}

	//����ListView���ݣ�����������
	private SimpleAdapter getSimpleAdapter_1() {
		Map<String, String> map = new HashMap<String, String>();
		listData = new ArrayList<Map<String, String>>();
		rember = new BigInteger[2];  //��ǰ��쳲���������ǰһ��쳲�������
		
		rember[0] = new BigInteger("0"); //��ʼ����һ��쳲�������
		rember[1] = new BigInteger("1"); //��ʼ���ڶ���쳲�������
		map.put("text", "f(" + 0 + "^3 = " + Math.pow(0, 3)+")" + " = " + "0"); //���õ�һ��쳲�������
		listData.add(map); //�����ݷ���ListData����
		
		while(fibonacci_index <= 40){
			
			rember = fibonacciNormal((long)Math.pow(fibonacci_index, 3) //��õ�ǰ��쳲���������ǰһ��쳲�������
					- (long)Math.pow(fibonacci_index-1, 3), rember[0], rember[1]);
			map = new HashMap<String, String>();
			
			if(rember[1].compareTo(format_num) == 1) //�ж��Ƿ񳬹�10^10
			{
				str_num = rember[1].toString(); //����ַ�������
				map.put("text", "f(" + fibonacci_index + "^3 = "
						+ ((long)Math.pow(fibonacci_index++, 3)) + ")" + " = "
							+ str_num.substring(0, 1)+" e " 
								+ (str_num.length() - 1));//��ÿ�ѧ��������쳲�������
			}else{
				map.put("text", "f(" + fibonacci_index + "^3 = "
						 + ((long)Math.pow(fibonacci_index++, 3)) + ")" 
						 	+ " = " + rember[1]);
			}
			
			listData.add(map); //�����ݷ���ListData����
		}
		
		simpleAdapter = new SimpleAdapter(this, listData,
				R.layout.list_item, new String[] { "text" },
				new int[] { R.id.tv_list_item }); //����SimpleAdapter
		
		return simpleAdapter;

	}

	//��̬����ListView�ĸ߶� 
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) { 
			View listItem = listAdapter.getView(i, null, listView); //���ListView��Item����
			listItem.measure(0, 0); //��������View �Ŀ��
			totalHeight += listItem.getMeasuredHeight(); //ͳ������������ܸ߶�

		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams(); //���ListView�Ĳ��ֶ���
		params.height = totalHeight //���ò��ָ߶�
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	//�ǵ���ʵ�ַ�ʽ  
	public static BigInteger[] fibonacciNormal(long n, BigInteger former_big, BigInteger current_big){ 
		BigInteger[] last = new BigInteger[2]; //��ʼ��쳲���������
		BigInteger n0 = former_big, n1 = current_big, sn = new BigInteger("0"); //����ǰһ�ε�쳲����������͵�ǰ쳲�������
		
		for(int i = 0; i < n ; i ++){ //����쳲�������
			sn = n0.add(n1);
			n0 = n1; 
			n1 = sn; 
		}
		
		last[0] = n0; //����ǰһ��쳲�������
		last[1] = n1; //���ص�ǰ쳲�������
		
		return last; 
	} 
}