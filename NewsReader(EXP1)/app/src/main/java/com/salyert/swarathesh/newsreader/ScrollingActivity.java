package com.salyert.swarathesh.newsreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {
    public AlertDialog.Builder alertdialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"swarathesh60@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "NEWS APP");
                email.putExtra(Intent.EXTRA_TEXT, "MINI PROJECT SUCCESSFULL");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "choose an appropriate mail service"));
                Toast.makeText(getApplicationContext(),"Choose Appropriate Mail service",Toast.LENGTH_LONG).show();
                Snackbar.make(view, "Sending Mail...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void InternationalNews(View view){
        startActivity(new Intent(ScrollingActivity.this,InternationalNewsActivity.class));
    }

    public void BusinessNews(View view){
        startActivity(new Intent(ScrollingActivity.this,BussinessNewsActivity.class));
    }
    public void CinemaNews(View view){
        startActivity(new Intent(ScrollingActivity.this,CinemaNewsActivity.class));
    }
    public void CrimeNews(View view){
        startActivity(new Intent(ScrollingActivity.this,CrimeNewsActivity.class));
    }
    public void FamilyNews(View view){
        startActivity(new Intent(ScrollingActivity.this,FamilyNewsActivity.class));
    }
    public void SportsNews(View view){
        startActivity(new Intent(ScrollingActivity.this,SportsNewsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AboutProject(MenuItem item) {
        //show our alert dialog
        alertdialogue = new AlertDialog.Builder(ScrollingActivity.this);
        //set title
        alertdialogue.setTitle("MiniProject : NewsReader");
        //set message]
        alertdialogue.setMessage("Project Guide :Jevan Pradeep\nTeam leader:A.Chandra Swarathesh\nTeam members:G.V.N. Tarun\nBalu Baigalla\nNikhil Jangam ");
        //to not cancel the dialogue when user press outside the dialogue
        alertdialogue.setCancelable(false);
        alertdialogue.setIcon(R.drawable.news_icon);
        //create dialog
        //-ve button
        alertdialogue.setNegativeButton(getResources().getString(R.string.neg),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertD = alertdialogue.create();
        //show the dialog
        alertD.show();
    }
}
