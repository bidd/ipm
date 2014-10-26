package com.planetexpress.bender.ipm_mdb;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.util.Log;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Accounts extends Activity {
    private static final String TAG = Accounts.class.getSimpleName();


    private AccountManager _accountManager;
    private ArrayList<AccountWrapper> _availableAccounts;
    private ArrayAdapter<AccountWrapper> _accountsAdapter;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        _accountManager = AccountManager.get(this);
        _availableAccounts = new ArrayList<AccountWrapper>();
        ListView accountsListView = (ListView) findViewById(R.id.accounts_listview);
        _accountsAdapter = new ArrayAdapter<AccountWrapper>(this,
                android.R.layout.simple_list_item_1,
                _availableAccounts);
        accountsListView.setAdapter(_accountsAdapter);
        accountsListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final Account account = _availableAccounts.get(position).account();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(_getActionAfterAccountManagerFuture(account));
                    }
                }).start();
            }
        });
    }

    private Runnable _getActionAfterAccountManagerFuture(final Account account) {
        AccountManagerFuture<Bundle> future = _accountManager.getAuthToken(account, GlobalNames.AUTH_TOKEN_TYPE, null, this, null, null);
        try {
            Bundle bundle = future.getResult();
            final String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            if (authToken != null) {
                final Intent intent = new Intent(this, MainWindow.class);
                return new Runnable() {
                    @Override
                    public void run() {
                        intent.putExtra(GlobalNames.ARG_AUTH_TOKEN, authToken);
                        intent.putExtra(GlobalNames.ARG_ACCOUNT_NAME, account.name);
                        startActivity(intent);
                    }
                };
            }
            else {
                return new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Unable to get auth token",
                                Toast.LENGTH_LONG).show();
                    }
                };
            }
        } catch (final Exception e) {
            e.printStackTrace();
            return new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                };
            };
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();

        Account[] accounts = _accountManager.getAccountsByType(GlobalNames.ACCOUNT_TYPE);
        _accountsAdapter.clear();

        if (accounts.length == 0) {
            startActivity(new Intent(this, Accounts_empty.class));
        }
        for(Account account : accounts) {
            Log.d(TAG, "Account: " + account.name + " " + account.toString());
            _accountsAdapter.add(new AccountWrapper(account));
        }
        _accountsAdapter.notifyDataSetChanged();
    }


    private class AccountWrapper {
        private Account _account;

        public AccountWrapper(Account account) {
            _account = account;
        }

        public Account account() {
            return _account;
        }

        public String toString() {
            return _account.name;
        }
    }
}
