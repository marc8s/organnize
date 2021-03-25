package com.example.organnize.view;

import android.content.Intent;
import android.os.Bundle;

import com.example.organnize.adapter.AdapterTransaction;
import com.example.organnize.config.ConfigFirebase;
import com.example.organnize.helper.Base64Custom;
import com.example.organnize.model.Transaction;
import com.example.organnize.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.organnize.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private MaterialCalendarView mCalendarView;
    private TextView mTextSalutation, mTextValueBalance;

    private FirebaseAuth mAuthentication = ConfigFirebase.getFirebaseAuthentication();
    private DatabaseReference mReferenceFirebase = ConfigFirebase.getFirebaseDatabase();
    private DatabaseReference mUserRef;

    private ValueEventListener mValueEventListenerUser;
    private ValueEventListener mValueEventListenerTransaction;

    private Double mExpenseTotal = 0.0;
    private Double mRecipeTotal = 0.0;
    private Double mBalanceUser = 0.0;

    private RecyclerView mRecyclerView;
    private AdapterTransaction mAdapterTransaction;
    private List<Transaction> mTransactions = new ArrayList<>();
    private DatabaseReference mTransactionRef;
    private String mMonthYearSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze");
        setSupportActionBar(toolbar);

        mTextValueBalance = findViewById(R.id.textViewValueBalance);
        mTextSalutation = findViewById(R.id.textViewTitleContentHome);
        mCalendarView = findViewById(R.id.calendarViewContentHome);
        mRecyclerView = findViewById(R.id.recyclerViewTransactions);
        configCalendarView();

        mAdapterTransaction = new AdapterTransaction(mTransactions, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapterTransaction);


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void recoverTransactions(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        mTransactionRef = mReferenceFirebase.child("transaction")
                .child(idUser)
                .child(mMonthYearSelected);
        mValueEventListenerTransaction = mTransactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mTransactions.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Transaction transaction = dados.getValue(Transaction.class);
                    mTransactions.add(transaction);
                }
                mAdapterTransaction.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recoverAbstract(){
        String emailUser = mAuthentication.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(emailUser);
        mUserRef = mReferenceFirebase.child("user").child(idUser);

        mValueEventListenerUser = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mExpenseTotal = user.getExpenseTotal();
                mRecipeTotal = user.getRecipeTotal();
                mBalanceUser = mRecipeTotal - mExpenseTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String balanceFormated = decimalFormat.format(mBalanceUser);

                mTextSalutation.setText("Olá, " + user.getName());
                mTextValueBalance.setText("€ " + balanceFormated);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                mAuthentication.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addRecipe(View view){
        startActivity(new Intent(this, RecipesActivity.class));
    }

    public void addExpense(View view){
        startActivity(new Intent(this, ExpensesActivity.class));
    }

    public void configCalendarView(){

        CalendarDay dateToday = mCalendarView.getCurrentDate();
        //logica para recuperar mês 2 e similares como 02
        String monthSelected = String.format("%02d", (dateToday.getMonth() + 1));
        mMonthYearSelected = String.valueOf(monthSelected + "" + dateToday.getYear());
        mCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String monthSelected = String.format("%02d", (date.getMonth() + 1));
                mMonthYearSelected = String.valueOf(monthSelected + "" + date.getYear());
                //remove o evento anterior antes de adicionar um novo
                mTransactionRef.removeEventListener(mValueEventListenerTransaction);
                recoverTransactions();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        recoverAbstract();
        recoverTransactions();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mUserRef.removeEventListener(mValueEventListenerUser);
        mTransactionRef.removeEventListener(mValueEventListenerTransaction);
    }
}