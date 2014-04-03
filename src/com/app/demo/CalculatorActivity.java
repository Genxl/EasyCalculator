package com.app.demo;

import java.util.Arrays;

import bsh.EvalError;
import bsh.Interpreter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends Activity implements OnClickListener {
	
	EditText rsText = null;   //显示器
	Boolean isClear = false;  //用于记录数字
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        
        rsText = (EditText)findViewById(R.id.rsText);
        //功能按钮
        Button btnLeft = (Button)findViewById(R.id.left);
        Button btnRight = (Button)findViewById(R.id.right);
        Button btnBack = (Button)findViewById(R.id.back);
        Button btnDel = (Button)findViewById(R.id.delete);
        Button btnDiv = (Button)findViewById(R.id.div);
        Button btnMul = (Button)findViewById(R.id.mul);
        Button btnSub = (Button)findViewById(R.id.sub);
        Button btnPlu = (Button)findViewById(R.id.plu);
        Button btnDot = (Button)findViewById(R.id.dot);
        Button btnEqu = (Button)findViewById(R.id.equ);
        
        //数字按钮
        Button num0 = (Button)findViewById(R.id.num0);
        Button num1 = (Button)findViewById(R.id.num1);
        Button num2 = (Button)findViewById(R.id.num2);
        Button num3 = (Button)findViewById(R.id.num3);
        Button num4 = (Button)findViewById(R.id.num4);
        Button num5 = (Button)findViewById(R.id.num5);
        Button num6 = (Button)findViewById(R.id.num6);
        Button num7 = (Button)findViewById(R.id.num7);
        Button num8 = (Button)findViewById(R.id.num8);
        Button num9 = (Button)findViewById(R.id.num9);
        
        //设置侦听
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnPlu.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnEqu.setOnClickListener(this);
        
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Button btn = (Button) v;
		String text = rsText.getText().toString();
		
		if(isClear &&(btn.getText().equals("0")
				|| btn.getText().equals("1")
				|| btn.getText().equals("2")
				|| btn.getText().equals("3")
				|| btn.getText().equals("4")
				|| btn.getText().equals("5")
				|| btn.getText().equals("6")
				|| btn.getText().equals("7")
				|| btn.getText().equals("8")
				|| btn.getText().equals("9")
				|| btn.getText().equals(".")
				|| btn.getText().equals("error"))){
			rsText.setText("");
			isClear = false;
		}
		
		if(btn.getText().equals("C")){
			rsText.setText("");
		}
		//退格
		else if(btn.getText().equals("←")){
			if(text == null || text.length() == 0){
				return;
			}else{
				rsText.setText(text.substring(0, text.length()-1));
			}
		}
		
		else if(btn.getText().equals("=")){
			if(text == null || text.length() == 0){
				return;
			}
			text = getRs(text);
			if(text.endsWith(".0")){
				text = text.substring(0, text.indexOf(".0"));
			}
			rsText.setText(text); 
			isClear = false;
		}else{
			rsText.setText(rsText.getText()+""+btn.getText());
			isClear = false;
		}
		
		//按键完成后始终保持光标在最后一位
		rsText.setSelection(rsText.getText().length());
	}
	
	/***
	 * @param  exp 算数表达式
	 * @return 根据表达式返回结果
	 */
	private String getRs(String exp){
			Interpreter bsh = new Interpreter();
	        Number result = null;
			try {
				exp = filterExp(exp);
				result = (Number)bsh.eval(exp);
			} catch (EvalError e) {
				e.printStackTrace();
				isClear = true;
				return "error";
			}        
			return result.doubleValue()+"";
	}

		
	/**
	 * @param exp 算数表达式
	 * @return 因为计算过程中,全程需要有小数参与.
	 */
	private String filterExp(String exp) {
		String num[] = exp.split("");
		String temp = null;
		int begin=0,end=0;
		 for (int i = 1; i < num.length; i++) {
			 temp = num[i];
			 if(temp.matches("[+-/()*]")){
				 if(temp.equals(".")) continue;
				 end = i - 1;  
				 temp = exp.substring(begin, end);
				 if(temp.trim().length() > 0 && temp.indexOf(".")<0)
					 num[i-1] = num[i-1]+".0";
				 begin = end + 1;
			 }
		 }
		 return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
	}
}