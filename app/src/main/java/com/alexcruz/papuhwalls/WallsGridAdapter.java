package com.alexcruz.papuhwalls;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static com.alexcruz.papuhwalls.Palette.PaletteCallback;

import com.alexcruz.papuhwalls.Walls.AllWalls;
import com.balysv.materialripple.MaterialRippleLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class WallsGridAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> data;
    public String wallurl;
    private int numColumns;
    private HashMap<String, String> jsondata = new HashMap<String, String>();

    private WallsHolder holder;

    private Context context;
    Preferences Preferences;

    public WallsGridAdapter(Context context,
                            ArrayList<HashMap<String, String>> arraylist, int numColumns) {
        super();
        this.context = context;
        this.numColumns = numColumns;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View wallitem = convertView;
        holder = null;
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.fade_in);

        jsondata = data.get(position);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int imageWidth = (int) (width / numColumns);

        if (wallitem == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            wallitem = inflater.inflate(R.layout.wallpaper_item, parent, false);
            holder = new WallsHolder(wallitem);
            wallitem.setTag(holder);
        } else {
            holder = (WallsHolder) wallitem.getTag();

        }

        holder.name.setText(jsondata.get(AllWalls.NAME));

        holder.author.setText(jsondata.get(AllWalls.AUTHOR));

        wallurl = jsondata.get(AllWalls.WALL);

        holder.wall.startAnimation(anim);
        Picasso.with(context)
                .load(wallurl)
                .resize(imageWidth, imageWidth)
                .centerCrop()
                .transform(Palette.instance())
                .into(holder.wall,
                        new PaletteCallback(holder.wall){
                            @Override
                            public void onSuccess(android.support.v7.graphics.Palette palette){
                            }

                            @Override
                            public void onError() {

                            }
                        });

        return wallitem;
    }

    class WallsHolder {
        ImageView wall;
        TextView name;
        TextView author;
        final MaterialRippleLayout content;

        WallsHolder(View v) {
            wall = (ImageView) v.findViewById(R.id.wall);
            name = (TextView) v.findViewById(R.id.name);
            author = (TextView) v.findViewById(R.id.author);
            content = (MaterialRippleLayout) v.findViewById(R.id.walls_ripple);
        }

    }

}