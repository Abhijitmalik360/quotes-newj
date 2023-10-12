package com.attitud.ssc.fragments;

import android.Manifest;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attitud.ssc.MyApplication;
import com.attitud.ssc.R;
import com.attitud.ssc.adapters.QuoteAdapter;
import com.attitud.ssc.cls.MyFunction;
import com.attitud.ssc.cls.MyReference;
import com.attitud.ssc.cls.QuoteData;
import com.attitud.ssc.databinding.FragmentHomeBinding;
import com.attitud.ssc.roomdb.FavoriteData;
import com.attitud.ssc.roomdb.MyDao;
import com.attitud.ssc.roomdb.MyDatabase;
import com.attitud.ssc.roomdb.MyRepository;
import com.attitud.ssc.roomdb.MyViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.FileProvider.getUriForFile;


public class HomeFragment extends Fragment implements QuoteAdapter.OnButtonsClickListener {

private  static final String TAG = "Home Fragment";


    private int[] imageResources = { R.drawable.ac, R.drawable.ad,R.drawable.ae,R.drawable.af,R.drawable.ag,R.drawable.ai,R.drawable.aj,R.drawable.ak,R.drawable.al,R.drawable.am,R.drawable.an,R.drawable.ao,R.drawable.ap,R.drawable.aq,R.drawable.dg,R.drawable.dgd,R.drawable.fd,R.drawable.fh,R.drawable.gt,R.drawable.jf,R.drawable.kj,R.drawable.kkj,R.drawable.mh,R.drawable.ng,R.drawable.one,R.drawable.sd,R.drawable.se,R.drawable.ty,R.drawable.we,R.drawable.yt};
    private FragmentHomeBinding binding;
    private QuoteAdapter adapter;
    private ArrayList<QuoteData> arrayList;
    private DocumentSnapshot lastDocumentSnapshot;

     ImageView image_view;
    private MyRepository repository;
    private MyViewModel myViewModel;
    private String catId;
    TextView quote;


    private ArrayList<String> favoriteIdList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(String catId) {
        this.catId = catId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




       /* Activity view = ;
        Button buttonShowToast =view.findViewById(R.id.copy);*/

        // Set an OnClickListener for the button
       /* buttonShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a toast on button click
                Toast.makeText(getActivity(), "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
*/
       
    


        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);



        return binding.getRoot();



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        init();
        loadQuotesData();






    }


    private void init() {
        arrayList = new ArrayList<>();
        adapter = new QuoteAdapter(arrayList, this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);


        MyDao myDao = MyDatabase.getInstance(getContext()).myDao();
        repository = new MyRepository(myDao);
        myViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MyViewModel.class);
        myViewModel.allData.observe(this, new Observer<List<FavoriteData>>() {
            @Override
            public void onChanged(List<FavoriteData> favoriteData) {
                favoriteIdList.clear();
                for (FavoriteData data : favoriteData) {
                    if (data.getId() != null) {
                        favoriteIdList.add(data.getId());
                    }
                }
                for (int i = 0;i<arrayList.size();i++){
                    QuoteData data = arrayList.get(i);
                    if (favoriteIdList.contains(data.getId())){
                        data.setFavorite(true);
                    }else {
                        data.setFavorite(false);
                    }
                    adapter.notifyItemChanged(i);
                }
            }
        });


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int remainItems = arrayList.size() - position;
                if (remainItems <= 2) {
                    Log.d("TAG", "onPageSelected: Loding new data ");
                    loadQuotesData();
                }

            }
        });


    }

    private void loadQuotesData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Query query;
        if (catId == null) {
            // Category Id is null
            if (lastDocumentSnapshot == null) {
                // first time loading data
                query = MyApplication.firestore.collection(MyReference.QUOTES).limit(5);
            } else {
                // loading more data
                query = MyApplication.firestore.collection(MyReference.QUOTES).startAfter(lastDocumentSnapshot).limit(5);
            }
        } else {
            // Category id is not null
            if (lastDocumentSnapshot == null) {
                // first time loading data
                query = MyApplication.firestore.collection(MyReference.QUOTES).whereEqualTo(MyReference.CATEGORY_ID, catId).limit(5);
            } else {
                // loading more data
                query = MyApplication.firestore.collection(MyReference.QUOTES).whereEqualTo(MyReference.CATEGORY_ID, catId).startAfter(lastDocumentSnapshot).limit(5);
            }
        }

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                binding.progressBar.setVisibility(View.GONE);
                if (querySnapshot.isEmpty()) {
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                    QuoteData data = documentSnapshot.toObject(QuoteData.class);
                    data.setId(documentSnapshot.getId());
                    boolean isFav = favoriteIdList.contains(documentSnapshot.getId());
                    data.setFavorite(isFav);
                    arrayList.add(data);
                    adapter.notifyDataSetChanged();
                }

                lastDocumentSnapshot = querySnapshot.getDocuments().get(querySnapshot.size() - 1);
            }
        }).addOnFailureListener(e -> {
            binding.progressBar.setVisibility(View.GONE);
            Log.d("TAG", "loadQuotesData: Error is " + e.toString());
        });
    }


    @Override




    public void onDownloadClick(int position,View view) {
     if (isStoragePermissionGranted()){
         // Download the image
         Bitmap bitmap = getBitmapFromView(view);
     if (bitmap==null){
    return;
       }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
        try {
          saveBitmapQ(bitmap);
    }     catch (IOException e) {
          e.printStackTrace();
    }
          }else {
          try {
        saveBitmap(bitmap);
              } catch (IOException e) {
        e.printStackTrace();
                }
          }



         }


    }




    @Override
    public void onShareClick(int position,View view) {

      //  if (isStoragePermissionGranted()){

           /* Bitmap bitmap = getBitmapFromView(view);
            Uri uri =    saveImage(bitmap,getContext());
            shareImageUri(uri);*/






    }

    @Override
    public void onFavoriteClick(int position) {
        QuoteData quoteData = arrayList.get(position);
        FavoriteData favoriteData = MyFunction.getFavoriteDataFromQuoteData(quoteData);

        if (quoteData.isFavorite()) {
            // already added in fav then remove this data
            repository.deleteFavoriteDataById(quoteData.getId());
            Toast.makeText(getContext(), "Item removed from Favorite", Toast.LENGTH_SHORT).show();
            quoteData.setFavorite(false);
        } else {
            repository.addFavoriteData(favoriteData);
            Toast.makeText(getContext(), "Item added to Favorite", Toast.LENGTH_SHORT).show();
            quoteData.setFavorite(true);
        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void onWallpaperClick(int position,View view) {

        Bitmap bitmap = getBitmapFromView(view);

        setWallpaper(bitmap);


    }




    @Override
    public void onCopyClick(int position,View view) {

     /*   Bitmap bitmap = getBitmapFromView(view);

        setWallpaper(bitmap);*/

        Log.e("copybutton", "copy button work" );


      //  hiddenButton.setVisibility(View.GONE);
       // ClipboardManager clipboardManager=(ClipboardManager) getSy
        quote=view.findViewById(R.id.quote);

      //  quote.setText("hi test");
        String textToCopy =  quote.getText().toString();
        copyToClipboard(textToCopy);



    }
    @Override
    public void onbackimagelistClick(int position,View view) {

     /*   Bitmap bitmap = getBitmapFromView(view);

        setWallpaper(bitmap);*/

        Log.e("copybutton", "copy button work" );


        //  hiddenButton.setVisibility(View.GONE);
        // ClipboardManager clipboardManager=(ClipboardManager) getSy
        image_view=view.findViewById(R.id.backimagelist);

        //  quote.setText("hi test");
        int randomIndex = (int) (Math.random() * imageResources.length);
        image_view.setImageResource(imageResources[randomIndex]);
        //Toast.makeText(getContext(), "😊Copied successfully😊", Toast.LENGTH_SHORT).show();



    }






      //




    private void copyToClipboard(String textToCopy) {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager != null) {
            // Create a ClipData object to store the text to copy
            ClipData clipData = ClipData.newPlainText("Copied Text", textToCopy);

            // Set the ClipData to clipboard
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(getContext(), "😊Copied successfully😊", Toast.LENGTH_SHORT).show();

        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);

            //resume tasks needing this permission
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void saveBitmapQ(@NonNull final Bitmap bitmap) throws IOException {
//        final String relativeLocation = Environment.DIRECTORY_PICTURES + File.separator + R.string.app_name;
        final String relativeLocation = Environment.DIRECTORY_PICTURES;
        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis()+".png");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);
        final ContentResolver resolver = getActivity().getContentResolver();
        OutputStream stream = null;
        Uri uri = null;

        try {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, contentValues);

            if (uri == null) {
                throw new IOException("Failed to create new MediaStore record.");
            }

            stream = resolver.openOutputStream(uri);
            Toast.makeText(getContext(), "😊Image Saved Successfully In Gallery😄 ", Toast.LENGTH_SHORT).show();

            if (stream == null) {
                throw new IOException("Failed to get output stream.");
            }

            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                throw new IOException("Failed to save bitmap.");
            }
        } catch (IOException e) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null);
            }

            throw e;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public void saveBitmap(Bitmap bm) throws IOException {
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory( "Daily Quotes"); //Creates app specific folder
        path.mkdirs();
        File imageFile = new File(path, System.currentTimeMillis() + ".png"); // Imagename.png
        FileOutputStream out = new FileOutputStream(imageFile);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            out.flush();
            out.close();

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(getContext(), new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
            Toast.makeText(getContext(), "😊Image Saved Successfully In Gallery😄 ", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            throw new IOException();
        }
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    private void setWallpaper(Bitmap bitmap){
        if (bitmap==null){
            return;
        }
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            String [] items = {"Home Screen","Lock Screen","Home and Lock Screen"};
       new AlertDialog.Builder(getContext()).setTitle("Set as Wallpaper")
               .setItems(items, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int position) {

                       switch (position){
                           case 0:
                               // Home screen

                               try {
                                   wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                               break;
                           case 1:
                               // Lock screen
                               try {
                                   wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                               break;
                           case 2:
                               // Home and Lock screen
                               try {
                                   wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM);
                                   wallpaperManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                               break;
                       }

                   }
               }).create().show();


        }else {
            try {
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(getContext(), "Wallpaper set", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public  Uri saveImage(Bitmap image,Context context) {
        if (image==null){
            return null;
        }

        File imagesFolder = new File(context.getCacheDir(), "images");

        Uri uri = null;
        try {
            imagesFolder.mkdirs();
            String fileName = System.currentTimeMillis()+".jpg";
            File file = new File(imagesFolder, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            uri = getUriForFile(context, "com.attitude.ssc.fileprovider", file);
        } catch (IOException e) {
            Log.d("TAG", "IOException while trying to write file for sharing: " + e.getMessage());
        }
        return uri;
    }

    public void shareImageUri(Uri uri){
        if (uri==null){
            return;
        }
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT,"Download this app ");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}