package com.bolivarapp.Tutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolivarapp.R;

public class SliderAdapter extends PagerAdapter {


    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.code_icon,
            R.drawable.icon_copvef,
            R.drawable.icon_vefcop,
            R.drawable.icon_table,
            R.drawable.icon_menu
    };

    public String[] slide_headings = {
            "BIENVENIDO",
            "CONVIERTE DE PESOS A BOLIVARES",
            "CONVIERTE DE BOLIVARES A PESOS",
            "GUARDA TUS CONVERSIONES",
            "TUTORIAL"
    };

    public String[] slide_text = {
            "Con BolívarApp ahora puedes convertir\n " +
            "las monedas fronterizas COP y VEF\n" +
            "de forma sencilla y rápida.\n\n" +
            "Desliza para conocer más!",
            "Digita el monto en pesos que desea convertir\n"+
            "y automáticamente se mostrarán los resultados.",
            "Digita el monto en bolívares que desea convertir\n"+
            "y automáticamente se mostrarán los resultados.\n\n"+
            "NOTA: PARA MOSTRAR ESTA VENTANA \n" +
            "SIMPLEMENTE DESLIZA HACIA LA IZQUIERDA",
            "Puedes almacenar tus conversiones\n" +
            "en la tabla inferior, pulsando el botón \"AGREGAR\".",
            "Regresar al tutorial pulsando\n" +
            "la opcion \"Ir a Tutorial\"\n" +
            "del menú principal.\n\n" +
            "Pulsa INICIAR para comenzar a usar BolívarApp!"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideText = (TextView) view.findViewById(R.id.slide_text);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideText.setText(slide_text[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
