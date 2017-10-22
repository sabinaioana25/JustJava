package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateToppingCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateTopping = chocolateToppingCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolateTopping);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolateTopping);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        final Intent intent1 = intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava orders for" + name);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * @param addWhippedCream     is whether or not the customer wants whipped cream on coffe;
     * @param addChocolateTopping is whether or not the customer wants chocolaate on coffe;
     *                            <p>
     *                            Calculates the price of the order.
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolateTopping) {
        // Price of one cup of coffee
        int basePrice = 5;

        // Price of one cup of coffee with added whipped cream of $1
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // Price of one cup of coffee with added chocolate of $2
        if (addChocolateTopping) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * Create the summary of the order
     *
     * @param name                of the buyer
     * @param price               of the order
     * @param addWhippedCream     is wether or not the user wants whipped cream topping
     * @param addChocolateTopping is wether or not the user wants chocolate topping
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolateTopping) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream) + " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate) + " " + addChocolateTopping;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) + " " + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_price, android.icu.text.NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return (priceMessage);
    }

    public void Increment(View view) {
        quantity = quantity + 1;

        if (quantity > 100) {
            quantity = 100;
            Toast.makeText(this, "You can't have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void Decrement(View view) {
        quantity = quantity - 1;

        if (quantity < 1) {
            quantity = 1;
            Toast.makeText(this, "You can't have less than one coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}