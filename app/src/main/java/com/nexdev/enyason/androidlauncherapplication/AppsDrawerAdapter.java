package com.nexdev.enyason.androidlauncherapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Enyason on 3/5/2018.
 */

public class AppsDrawerAdapter extends RecyclerView.Adapter<AppsDrawerAdapter.ViewHolder> {

    private static Context context;
    private static List<AppInfo> appsList;


    public AppsDrawerAdapter(Context c ) {

        //This is where we build our list of app details, using the app
        //object we created to store the label, package name and icon

        context = c;
        setUpApps();


    }

    public static void setUpApps(){

        PackageManager pManager = context.getPackageManager();
        appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();
            app.label = ri.loadLabel(pManager);
            app.packageName = ri.activityInfo.packageName;

            Log.i(" Log package ",app.packageName.toString());
            app.icon = ri.activityInfo.loadIcon(pManager);
            appsList.add(app);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_view_list, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        String appLabel = appsList.get(position).label.toString();
        String appPackage = appsList.get(position).packageName.toString();
        Drawable appIcon = appsList.get(position).icon;

        TextView textView = holder.textView;
        textView.setText(appLabel);
        ImageView imageView = holder.img;
        imageView.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextToSpeech textToSpeech;
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            textView = (TextView) itemView.findViewById(R.id.tv_app_name);
            img = (ImageView) itemView.findViewById(R.id.app_icon);



        }


    }
}


