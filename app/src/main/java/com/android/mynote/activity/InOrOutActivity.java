package com.android.mynote.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.mynote.R;
import com.android.mynote.adapter.GridViewAdapter;
import com.android.mynote.iconlib.IconLib;
import com.android.mynote.operatedb.OperateAccount;
import com.android.mynote.operatedb.OperateBill;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InOrOutActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

    private List<Map<String, Object>> outlist; // 保存支出的图标
    private List<Map<String, Object>> inlist; // 保存收入的图标
    private Button out; // 支出按钮
    private Button in; // 收入按钮
    private ImageView icon; // 上方显示的图标
    private TextView iconName;// 上方显示的图标名称
    private GridView gridview;
    private TextView number; // 计算器显示的结果

    private int selectedIndex; // 图片的索引
    private int inOrout; // 判断是支出还是收入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_or_out);
        init();    //初始化操作
        reset();    //将计算器重置
    }

    private void init() {
        // TODO Auto-generated method stub
        out = (Button) findViewById(R.id.button_expense);        //实例化支出按钮
        in = (Button) findViewById(R.id.button_income);        //实例化收入按钮
        icon = (ImageView) findViewById(R.id.imageview_icon);    //实例化展示所选择的图片
        iconName = (TextView) findViewById(R.id.textview_iconname);
        gridview = (GridView) findViewById(R.id.gridview);    //网格
        number = (TextView) findViewById(R.id.textview_number);        //计算器显示器
        findViewById(R.id.button_one).setOnClickListener(this);
        findViewById(R.id.button_two).setOnClickListener(this);
        findViewById(R.id.button_three).setOnClickListener(this);
        findViewById(R.id.button_four).setOnClickListener(this);
        findViewById(R.id.button_five).setOnClickListener(this);
        findViewById(R.id.button_six).setOnClickListener(this);
        findViewById(R.id.button_seven).setOnClickListener(this);
        findViewById(R.id.button_eight).setOnClickListener(this);
        findViewById(R.id.button_nine).setOnClickListener(this);
        findViewById(R.id.button_zero).setOnClickListener(this);
        findViewById(R.id.button_point).setOnClickListener(this);
        findViewById(R.id.button_ac).setOnClickListener(this);
        findViewById(R.id.button_del).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);
        findViewById(R.id.button_plus).setOnClickListener(this);
        out.setOnClickListener(this);
        in.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
        selectedIndex = 0;    //保存选择的item索引
        inOrout = 1;    //标记收入或支出-----1.支出  2.收入

        outlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 12; ++i) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", IconLib.imageArray[i]);
            map.put("iconname", IconLib.imageName[i]);
            outlist.add(map);
        }

        inlist = new ArrayList<Map<String, Object>>();
        for (int i = 12; i < 19; ++i) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", IconLib.imageArray[i]);
            map.put("iconname", IconLib.imageName[i]);
            inlist.add(map);
        }
        gridview.setAdapter(new GridViewAdapter(outlist, this));
    }

    private int numCount;    //数字位数，限制输入位数上限为9位
    private boolean hasPoint;    //记录是否有小数点
    private boolean isPressOp;    //记录上一次操作是否按下了操作符
    private double sum;    //保存最终结果
    private double temp;    //保存上一步输入的数字
    private boolean reInput;    //判断数字是否应该重新显示

    private void reset() { // 计算器重置
        numCount = 0;
        hasPoint = false;
        reInput = false;
        sum = 0;
        temp = 0;
        number.setText("0");
        isPressOp = false;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Button b = (Button) findViewById(v.getId()); // 获取按下的按钮
        String text = number.getText().toString(); // 获取计算器显示的值
        switch (v.getId()) {
            case R.id.button_expense: // 按下支出按钮
                if (inOrout == 0) { //如果当前选择的是收入，则执行
                    out.setTextColor(getResources().getColor(R.color.white));
                    out.setBackgroundResource(R.drawable.boy_selected);
                    in.setTextColor(getResources().getColor(R.color.black));
                    in.setBackgroundResource(R.drawable.girl_not_selected);
                    gridview.setAdapter(new GridViewAdapter(outlist, this));
                    icon.setImageResource(IconLib.imageArray[0]);
                    iconName.setText(IconLib.imageName[0]);
                    inOrout = 1; //修改标记为支出
                }
                break;
            case R.id.button_income: // 按下收入按钮
                if (inOrout == 1) {        //如果当前选择的是支出，则执行
                    out.setTextColor(getResources().getColor(R.color.black));
                    out.setBackgroundResource(R.drawable.boy_not_selected);
                    in.setTextColor(getResources().getColor(R.color.white));
                    in.setBackgroundResource(R.drawable.girl_selected);
                    gridview.setAdapter(new GridViewAdapter(inlist, this));
                    icon.setImageResource(IconLib.imageArray[12]);
                    iconName.setText(IconLib.imageName[12]);
                    inOrout = 0;    //修改标记为收入
                }
                break;
            case R.id.button_one:
            case R.id.button_two:
            case R.id.button_three:
            case R.id.button_four:
            case R.id.button_five:
            case R.id.button_six:
            case R.id.button_seven:
            case R.id.button_eight:
            case R.id.button_nine:    //按下数字按钮
                if (!reInput) {    //当显示器需要重新显示
                    if (numCount < 9) {    //当未达到最大位数
                        if (text.charAt(0) != '0') {    //当第一位不为0的时候直接将输入并入后面
                            number.setText(text + b.getText());
                        } else {    //当第一位为0的时候，判断是否有小数点，有则将输入并入后面，无则修改数字
                            if (hasPoint) {
                                number.setText(text + b.getText());
                            } else {
                                number.setText(b.getText());
                            }
                        }
                        ++numCount;
                    }
                } else {
                    number.setText(b.getText());
                    reInput = false;
                    ++numCount;
                }
                isPressOp = false; //一旦输入数字要将符号为标记修改为无
                break;
            case R.id.button_zero:    //输入0的时候判断是否合理
                if (numCount < 9 && (text.charAt(0) != '0' || hasPoint)) {
                    number.setText(text + '0');
                    ++numCount;
                }
                break;
            case R.id.button_point:    //小数点按钮
                if (!hasPoint) {
                    if (text.charAt(0) == '0') {
                        ++numCount;
                    }
                    number.setText(text + b.getText());
                    hasPoint = true;
                }
                isPressOp = false;
                break;
            case R.id.button_ok:    //OK按钮
                if (isPressOp == false) {
                    temp = Double.parseDouble(number.getText().toString());
                    sum += temp; //将上一步输入的金额加上获得总的金额
                    String result = String.format("%.1f", sum);    //将总额保留一位小数
                    sum = inOrout == 1 ? -1 * sum : sum;    //如果inOrout标志位为1，表示支出，将总额改为负数
                    new OperateAccount(this).insert(1, sum);    //将操作以修改现金的方式插入的第三张表
                    String message = IconLib.imageName[selectedIndex] + result;
                    int image = IconLib.imageArray[selectedIndex];
                    new OperateBill(this).insert(message, image, inOrout);    //将图片地址，信息以及支出收入标志位插入第二张表
                    finish();
                } else {
                    Toast.makeText(this, "输入格式有误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_plus:
                if (isPressOp == false) {  //当未按下符号键时，执行
                    isPressOp = true;
                    temp = Double.parseDouble(number.getText().toString());
                    sum += temp;
                    reInput = true;
                    hasPoint = false;
                }
                break;
            case R.id.button_del:    //逐位删除按钮
                if (text.length() > 1) {
                    number.setText(text.substring(0, text.length() - 1));
                } else {
                    number.setText("0");
                }
                break;
            case R.id.button_ac:    //重置计算器
                reset();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        //将点击的图片放入上方
        // TODO Auto-generated method stub
        if (inOrout == 1) {
            icon.setImageResource(IconLib.imageArray[position]);
            iconName.setText(IconLib.imageName[position]);
            selectedIndex = position;
        } else {
            icon.setImageResource(IconLib.imageArray[position + 12]);
            iconName.setText(IconLib.imageName[position + 12]);
            selectedIndex = position + 12;
        }
    }

}
