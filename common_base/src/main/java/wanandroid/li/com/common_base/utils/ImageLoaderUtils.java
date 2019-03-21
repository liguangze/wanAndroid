package wanandroid.li.com.common_base.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import wanandroid.li.com.common_base.R;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {


    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.bg_no_data)
                        .error(new ColorDrawable(Color.WHITE))
                        .fallback(new ColorDrawable(Color.RED)))
                .into(imageView);
    }






}
