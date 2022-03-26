package com.example.mobile_icp9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 11;
    private static final Integer CHICKEN_PRICE = 7;
    private static final Integer VEGGIE_PRICE = 4;
    private static final Integer OP_PRICE = 6;
    final int ONION = 1;
    final int EXTRA_CHEESE = 2;
    int quantity = 3;
    CheckBox chickenChecked, veggieChecked,  opChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public String getOrderSummary(View view)
    {
        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_name);
        String userInputName = userInputNameView.getText().toString();

        chickenChecked = findViewById(R.id.nonveg_checkBox);
        boolean hasChicken = chickenChecked.isChecked();

        veggieChecked = findViewById(R.id.veg_checkBox);
        boolean hasVeg = veggieChecked.isChecked();

        opChecked = findViewById(R.id.others_checkBox);
        boolean hasOther = opChecked.isChecked();

        // check if whipped cream is selected
        CheckBox onion = (CheckBox) findViewById(R.id.onion_checked);
        boolean hasOnion = onion.isChecked();

        // check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.cheese_checked);
        boolean hasExtraCheese = chocolate.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasChicken,hasVeg,hasOther,hasOnion, hasExtraCheese);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasChicken, hasVeg,hasOther,hasOnion, hasExtraCheese, totalPrice);
        return orderSummaryMessage;
    }

    public void submitOrder(View view) {
        String orderSummaryMessage = getOrderSummary(view);
        Intent i = new Intent(Intent.ACTION_SEND);
        EditText userInputNameView = (EditText) findViewById(R.id.user_name);
        String userInputName = userInputNameView.getText().toString();
        sendEmail(userInputName,orderSummaryMessage);
        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
    }
    public void submitSummary(View view) {
        String orderSummaryMessage = getOrderSummary(view);
        // get user input
        Intent summary = new Intent(this,Order_Summary.class);
        summary.putExtra("message",orderSummaryMessage);
        startActivity(summary);
    }


    public void sendEmail(String name, String output) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
        String subject = name + "'s Order";
        Intent sendIntent = new Intent();
        sendIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"anushanelluri10@gmail.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, output);
        sendIntent.setType("text/plain");
        // Try to invoke the intent.
        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            // Define what your app should do if no activity can handle the intent.
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasChicken, boolean hasVeg, boolean hasOther,boolean hasOnion, boolean hasExtraCheese, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_chicken, boolToString(hasChicken)) + "\n" +
                getString(R.string.order_summary_veg, boolToString(hasVeg)) + "\n" +
                getString(R.string.order_summary_Other, boolToString(hasOther)) + "\n" +
                getString(R.string.order_summary_onion, boolToString(hasOnion)) + "\n" +
                getString(R.string.order_summary_extra_cheese, boolToString(hasExtraCheese)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasChicken, boolean hasVeg, boolean hasOther, boolean hasOnion, boolean hasExtraCheese) {
        int basePrice = PIZZA_PRICE;

        if(hasChicken){
            basePrice += CHICKEN_PRICE;
        }
        if(hasVeg) {
            basePrice += VEGGIE_PRICE;
        }
        if(hasOther) {
            basePrice += OP_PRICE;
        }
        if (hasOnion) {
            basePrice += ONION;
        }
        if (hasExtraCheese) {
            basePrice += EXTRA_CHEESE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}