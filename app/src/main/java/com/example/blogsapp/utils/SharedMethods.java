package com.example.blogsapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SharedMethods {

    public static String convertImageToBase64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos); // reduce quality
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return base64Image;
    }

    public static Bitmap uriToBitmap(Context context, Uri uri) throws IOException {
        Bitmap bitmap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
            bitmap = ImageDecoder.decodeBitmap(source);
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        }

        return bitmap;
    }
}
