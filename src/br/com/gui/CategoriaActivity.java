package br.com.gui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalogoproduto.R;

public class CategoriaActivity extends Activity implements OnClickListener{

	TextView descricao;
	TextView cor;
	Button salvar;
	DatabaseHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria);
		
		descricao = (TextView) findViewById(R.id.edDescricao);
		cor = (TextView) findViewById(R.id.edCor);
		helper = new DatabaseHelper(this);
		
		salvar = (Button) findViewById(R.id.btnSalvarCategoria);
		salvar.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		salvar();
	}

	private void salvar() {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("descricao", descricao.getText().toString());
		values.put("cor", cor.getText().toString());
		db.insert("categorias", null, values);
		Toast.makeText(this, "Categoria cadastrada com sucesso!!", Toast.LENGTH_SHORT).show();
		
		finish();
	}
}
