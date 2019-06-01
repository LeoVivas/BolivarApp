package com.bolivarapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.bolivarapp.R;
import com.bolivarapp.Util.Data;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PlaceholderFragment1 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    static final int MAX_ROWS_TABLE = 50;
    static final String []ROW_0 = {"Pesos","Tasa","BsS Efectivo","Tasa","BsS Transf."};
    ArrayList<String[]> lista;
    Button btn_save;
    Button btn_cleanRow;
    Button btn_clean;
    NumberFormat numberFormat =  NumberFormat.getCurrencyInstance(Locale.US);
    double factorEfectivo = 0.0;
    double factorTransferencia = 0.0;
    double bsEfectivo = 0.0;
    double bsTransferencia = 0.0;
    double peso = 0.0;
    EditText inputCOP;
    EditText tasa1;
    EditText tasa2;
    String temp;
    TableLayout tabla;
    TextView resultE;
    TextView resultT;
    TextView lblDev;
    TextView lblVer;
    String inCOP;

    public PlaceholderFragment1() {
    }

    public static PlaceholderFragment1 newInstance (int sectionNumber) {
        PlaceholderFragment1 fragment = new PlaceholderFragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, container, false);

        resultE = (TextView) v.findViewById(R.id.textResultE);
        resultT = (TextView) v.findViewById(R.id.textResultT);
        inputCOP = (EditText) v.findViewById(R.id.inputCOP);
        temp = String.valueOf(inputCOP.getText());
        tasa1 = (EditText) v.findViewById(R.id.tasa1);
        tasa2 = (EditText) v.findViewById(R.id.tasa2);
        tabla = (TableLayout) v.findViewById(R.id.lista);
        btn_save = (Button) v.findViewById(R.id.btn_save);
        btn_cleanRow = (Button) v.findViewById(R.id.btn_cleanRow);
        btn_clean = (Button) v.findViewById(R.id.btn_clean);
        lblDev = (TextView) v.findViewById(R.id.lbl_developers);
        lblVer = (TextView) v.findViewById(R.id.lbl_version);
        //int h = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //lblDev.setY(h-200);
        //lblVer.setY(h-200);
        inCOP = "";
        lista = new ArrayList<>();
        init_data();
        configEvents();

        return v;
    }

    public void configEvents() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a,b,c,d,e;
                a = inputCOP.getText().toString().replaceAll("[^\\d.,]", "");
                b = String.valueOf(tasa1.getText());
                b = (b.equals("") ? "-" : b);
                c = resultE.getText().toString().replaceAll("[^\\d.,]", "");
                d = String.valueOf(tasa2.getText());
                d = (d.equals("") ? "-" : d);
                e = resultT.getText().toString().replaceAll("[^\\d.,]", "");
                int r = tabla.getChildCount();


                if(!isRepetido(a,b,d)) {
                    if ((!String.valueOf(resultE.getText()).equals(getString(R.string.etiqBs)) || !String.valueOf(resultT.getText()).equals(getString(R.string.etiqBs)))) {
                        if (r < MAX_ROWS_TABLE) {
                            String[] cadena = {a, b, c, d, e};
                            lista.add(cadena);
                            TableRow row = new TableRow(getActivity().getBaseContext());
                            TextView textView;

                            for (int i = 0; i < cadena.length; i++) {
                                textView = new TextView(getActivity().getBaseContext());
                                textView.setGravity(Gravity.CENTER_VERTICAL);
                                textView.setPadding(15, 15, 15, 15);
                                textView.setBackgroundResource(R.color.colorPrimary);
                                textView.setText(cadena[i]);
                                textView.setTextColor(Color.WHITE);
                                row.addView(textView);
                            }
                            tabla.addView(row,1);

                        } else {
                            tabla.removeViewAt(r-1);
                            lista.remove(r-2);
                            onClick(view);
                        }
                        try { guardar(); } catch (IOException ef) {}
                    }
                }
            }
        });

        btn_cleanRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int r = (int) tabla.getChildCount();
                if(r > 1) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_menu_close_clear_cancel)
                            .setTitle("Limpiar Tabla")
                            .setMessage("Se borrarán datos de la tabla,\ncontinuar?")
                            .setNegativeButton(android.R.string.cancel, null)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    tabla.removeViews(1, r - 1);
                                    lista = new ArrayList<>();
                                    try { guardar(); } catch (IOException ef) {}
                                }
                            })
                            .show();
                }
                else
                    Toast.makeText(getActivity(), "Tabla Vacía", Toast.LENGTH_SHORT).show();
            }
        });

        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clean();
            }
        });

        inputCOP.addTextChangedListener(new TextWatcher() {
            boolean mEditing = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!mEditing) {
                    mEditing = true;

                    String digits = s.toString().replaceAll("\\D", "");

                    try{
                        String formatted = numberFormat.format(Double.parseDouble(digits) / 100);
                        s.replace(0, s.length(), formatted);
                        inCOP = s.toString().replaceAll("[^\\d.]", "");
                        calcularControler();
                    } catch (NumberFormatException nfe) {
                        s.clear();
                    }
                    mEditing = false;
                }

                if(!isValidCOP(inputCOP.getText().toString())) {
                    cleanResultados();
                    inCOP = "";
                }
            }
        });

        tasa1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calcularControler();
                try { guardar(); } catch (IOException e) {}
            }
        });

        tasa2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calcularControler();
                try { guardar(); } catch (IOException e) {}
            }
        });
    }

    public void init_data(){
        lista.add(ROW_0);
        ArrayList<String[]> bd = Data.getLista(getActivity().getFilesDir(), getString(R.string.patchXMLB1));
        ArrayList<String[]> bdTasas = Data.getLista(getActivity().getFilesDir(), getString(R.string.patchXMLB2));


        if(!bdTasas.isEmpty()) {
            tasa1.setText(bdTasas.get(0)[1]);
            tasa2.setText(bdTasas.get(0)[3]);
        }

        if(bd.size()>0)
            for(String[] row : bd)
                lista.add(row);

        for (String[] list : lista) {
            TableRow row = new TableRow(getActivity().getBaseContext());
            TextView textView;

            for (int i = 0; i < list.length; i++) {
                textView = new TextView(getActivity().getBaseContext());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(15, 15, 15, 15);
                if(list.equals(ROW_0)) textView.setBackgroundResource(R.color.holo_red_Dark);
                else textView.setBackgroundColor(Color.BLACK);
                textView.setText(list[i]);
                textView.setTextColor(Color.WHITE);
                row.addView(textView);
            }
            tabla.addView(row);
        }
        lista.remove(0);
    }

    public void calcularControler(){
        if(isValidCOP(inCOP)){
            temp = inCOP;
            if(isValidTasa(tasa1.getText().toString()))
                calcularEfectivo();
            else
                resultE.setText(getString(R.string.etiqBs));

            if(isValidTasa(tasa2.getText().toString()))
                calcularTransferencia();
            else
                resultT.setText(getString(R.string.etiqBs));
        }
    }

    public void calcularEfectivo(){
        factorEfectivo = Double.valueOf(String.valueOf(tasa1.getText()));
        peso = Double.valueOf(inCOP);
        bsEfectivo = peso / factorEfectivo;
        resultE.setText("BsS " + (numberFormat.format(bsEfectivo)).replaceAll("[^\\d.,]", ""));
    }

    public void calcularTransferencia(){
        factorTransferencia = Double.valueOf(String.valueOf(tasa2.getText()));
        peso = Double.valueOf(inCOP);
        bsTransferencia = peso / factorTransferencia;
        resultT.setText("BsS " + (numberFormat.format(bsTransferencia)).replaceAll("[^\\d.,]", ""));
    }

    public boolean tieneDecimal(double bs){
        int entero = (int) bs;
        double resta = bs - entero;
        return (resta>0);
    }

    public boolean isRepetido(String a, String b, String d){
        temp = "";
        String a1="", b2="",c3="";

        if(lista.size() > 0){
            int i = lista.size()-1;
            a1 = lista.get(i)[0];
            b2 = lista.get(i)[1];
            c3 = lista.get(i)[3];
        }
        return ( (a.equals(a1) && b.equals(b2)) && (a.equals(a1) && d.equals(c3)) );
    }

    public boolean isValidCOP(String cop){
        if(!cop.equals("") && !cop.equals(".")){
            Double dCop = Double.valueOf(cop.replaceAll("[^\\d.]", ""));
            if(dCop>0)
                return true;
        }
        return false;
    }

    public boolean isValidTasa(String tasa){
        if(!tasa.equals("") && !tasa.equals(".")){
           Double dTasa = Double.valueOf(tasa);
           if(dTasa>0)
               return true;
        }
        return false;
    }

    public void clean(){
        inputCOP.setText("");
        tasa1.setText("");
        tasa2.setText("");
        cleanResultados();
        try { guardar(); } catch (IOException ef) {}
    }

    public void cleanResultados(){
        resultE.setText(getString(R.string.etiqBs));
        resultT.setText(getString(R.string.etiqBs));
    }

    public void guardar() throws IOException {

        String t1 = String.valueOf(tasa1.getText());
        String t2 = String.valueOf(tasa2.getText());
        ArrayList<String[]> bdTasas = new ArrayList<>();

        if(t1.equals("")) t1 = getString(R.string.t1Default);
        if(t2.equals("")) t2 = getString(R.string.t2Default);
        bdTasas.add(new String[]{" ",t1," ",t2," "});

        Data.makeXML(lista, getActivity().getFilesDir(), getString(R.string.patchXMLB1));
        Data.makeXML(bdTasas, getActivity().getFilesDir(), getString(R.string.patchXMLB2));

    }
}