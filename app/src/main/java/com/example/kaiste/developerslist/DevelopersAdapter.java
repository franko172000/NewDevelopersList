package com.example.kaiste.developerslist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Kaiste on 6/29/2017.
 */

public class DevelopersAdapter extends ArrayAdapter<DeveloperObj> {

    public DevelopersAdapter(@NonNull Context context, ArrayList<DeveloperObj> developers) {
        super(context,0, developers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listLayout = convertView;//using recycled view

        //inflate a new view if we don't have an existing view to be recycled.
        if(listLayout == null){
            listLayout = LayoutInflater.from(getContext()).inflate(R.layout.custom_list,parent,false);
        }
        //get the position of the list
        DeveloperObj developer = getItem(position);

        //get the image view in on the layout file
        ImageView avatar = (ImageView) listLayout.findViewById(R.id.avatar);
        //download the image from the url ad set it on the view
        Picasso.with(getContext()).load(developer.getmAvatarUrl()).into(avatar);
        //get the user's display image on GitHub from the json result and display the image on the image view
        //Resources resources = listLayout.getResources();

        //Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();

        //Bitmap bitImg = BitmapFactory.decodeResource(resources,avatar.getId());
        
        //make the image corners rounded
        //Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
        //display the image
        //avatar.setImageBitmap(roundCornerImage(bitmap,20));

        //get the username text view
        TextView username  = (TextView) listLayout.findViewById(R.id.username);
        //get the user's display name on GitHub from the json result and set it on the text view
        username.setText(developer.getmUsername());

        //get the url text view
       // TextView pageUrl = (TextView) listLayout.findViewById(R.id.pageUrl);
        //get the user's custom url on GitHub from the json result and set it on the text view
        //pageUrl.setText(developer.getmPageUrl());

        return listLayout;
    }


    private Bitmap roundCornerImage(Bitmap raw, float round) {
        //get image width
        int width = raw.getWidth();
        //get image height
        int height = raw.getHeight();
        //get the source image
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#000000"));

        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(raw, rect, rect, paint);

        return result;
    }
}
