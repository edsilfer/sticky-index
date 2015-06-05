package br.com.stickyindexsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.stickyindex.Main;
import br.com.stickyindexsample.adapter.SimpleAdapter;
import br.com.stikyindexsample.R;

/**
 * Created by edgar on 6/4/15.
 */
public class MainActivity extends Activity {

    private String[] data = {
            "Henrique",
            "Aline",
            "Agata",
            "Aline",
            "Agata",
            "Allan",
            "Alan",
            "Allan",
            "Alan",
            "Adilson",
            "Albertina",
            "Camila",
            "Carlos",
            "Camila",
            "Carlos",
            "Josemar",
            "Jose",
            "Joao",
            "Jose",
            "Joao",
            "Joaquina",
            "Melissa",
            "Lucas",
            "Ziraldo"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Creates RecyclerView and its layout
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        SimpleAdapter adapter = new SimpleAdapter(data);
        recyclerView.setAdapter(adapter);

        // Creates index viewer
        Main indexContainer = (Main) this.findViewById(R.id.sticky_index_container);
        indexContainer.setDataSet(getIndexList(data));
        indexContainer.setReferenceList(recyclerView);
    }

    private char[] getIndexList (String[] model) {
        char[] result = new char[model.length];
        int i = 0;

        for (String curr : model) {
            result[i] = curr.charAt(0);
            i++;
        }

        return result;
    }
}
