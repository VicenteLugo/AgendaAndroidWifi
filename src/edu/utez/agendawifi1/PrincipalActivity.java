package edu.utez.agendawifi1;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PrincipalActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	public void agregar(View v){
		startActivity(new Intent(this, ThirdActivity.class));
	}
	public void consultar(View v){
		startActivity(new Intent(this, MainActivity.class));		
	}
	
}
