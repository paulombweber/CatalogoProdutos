package br.com.gui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catalogoproduto.R;

public class ProdutoActivity extends Activity implements OnClickListener{

	long id;
	TextView codigo;
	TextView nome;
	TextView preco;
	TextView estoque;
	Spinner categoria;
	Button btSalvar;
	DatabaseHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		codigo = (TextView) findViewById(R.id.edCodigo);
		nome = (TextView) findViewById(R.id.edNome);
		preco = (TextView) findViewById(R.id.edPreco);
		estoque = (TextView) findViewById(R.id.edEstoque);
		categoria = (Spinner) findViewById(R.id.spCategoria);
		helper = new DatabaseHelper(this);
		
		btSalvar = (Button) findViewById(R.id.btnSalvar);
		btSalvar.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.novaCategoria) {
			Intent i = new Intent(this, CategoriaActivity.class);
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		helper.close();
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadCategorias();
	}
	
	private void loadCategorias() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("Select _id, descricao, cor from categorias", null);
		int count = cursor.getCount();
		if (count > 0){
			Categoria[] categorias = new Categoria[cursor.getCount()];
			int i = 0;
			cursor.moveToFirst();
			do{
				Categoria cat = new Categoria();
				cat.setId(cursor.getInt(0));
				cat.setDescricao(cursor.getString(1));
				cat.setCor(cursor.getString(2));
				categorias[i] = cat;
				i++;
			}while(cursor.moveToNext());
			cursor.close();
			categoria.setAdapter(new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias));
		}
	}

	@Override
	public void onClick(View arg0) {
		salvar();
	}

	private void salvar() {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("codigo", codigo.getText().toString());
		values.put("nome", nome.getText().toString());
		values.put("preco", preco.getText().toString());
		values.put("estoque", estoque.getText().toString());
		values.put("categoria", ((Categoria) categoria.getSelectedItem()).getId());
		
		db.insert("produtos", null, values);
		Toast.makeText(this, "Produto cadastrado com sucesso!!", Toast.LENGTH_SHORT).show();
		
		codigo.setText("");
		nome.setText("");
		preco.setText("");
		estoque.setText("");
		categoria.setSelection(0);
	}
}
