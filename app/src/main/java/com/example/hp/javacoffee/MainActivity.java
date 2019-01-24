package com.example.hp.javacoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int noofCoffee=0;
    String priceMessage="";
    boolean val;
    String name;

    //Resettting the info

    public void submitOrder(View view){
        EditText namev=(EditText) findViewById(R.id.album_description_view);
        name=namev.getText().toString();
        CheckBox whippedCream = findViewById(R.id.notify_me_checkbox);
        val= whippedCream.isChecked();
        int quantity= noofCoffee*5;
        display(noofCoffee);
        displayPrice(quantity,val,name);

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for : "+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }
    public void resetOrder(View view){
        noofCoffee=0;
        display(0);
        displayPrice(0,val,name);
        val=false;
        name="";
    }
    private void display(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+ number);
    }
    private String createSummary(int number, boolean val,String name){
        if(val)number+=1;
        if(number==0){priceMessage="Total Price : $0";return priceMessage;}
        else {priceMessage="Name : "+ name;
            priceMessage+="\nQuantity : "+noofCoffee;
            priceMessage+="\nToppings Required?? "+val;
            priceMessage+= "\nTotal Price : " + NumberFormat.getCurrencyInstance().format(number) + "\nThank You!";
        }
        priceMessage+="\nYour order is due";
        return(priceMessage);
    }

    private void displayPrice(int number, boolean val,String name){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        String priceMessage = createSummary(number,val,name);
        priceTextView.setText(priceMessage);
    }
    public void increment(View view){
        if(noofCoffee==100){
            Toast.makeText(this, "You cannot have more than 100 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        noofCoffee+=1;
        display(noofCoffee);
    }
    public void decrement(View view){
        if(noofCoffee==1) {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        noofCoffee-=1;
        display(noofCoffee);
    }
}
